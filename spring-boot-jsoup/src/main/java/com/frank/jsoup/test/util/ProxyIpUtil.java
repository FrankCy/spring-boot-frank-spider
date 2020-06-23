package com.frank.jsoup.test.util;

import com.frank.jsoup.test.constant.CommonStatusEnum;
import com.frank.jsoup.test.constant.ErrorCodeEnum;
import com.frank.jsoup.test.exception.ServiceException;
import com.frank.jsoup.test.vo.ProxyIpVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 *
 * @author cy
 * @version ProxyIpUtil.java, v 0.1 2020年06月23日 13:48 cy Exp $
 */
@Slf4j
public class ProxyIpUtil {

    /**
     * 爬取代理IP地址的网站URL链接地址
     */
    private static final String PROXY_IP_URL = "https://www.xicidaili.com/nt/";

    /**
     * 使用https检查的URL链接地址
     */
    private static final String CHECK_URL_BY_HTTPS = "https://www.xicidaili.com/nt/";

    /**
     * 使用http检查的URL链接地址
     */
    private static final String CHECK_URL_BY_HTTP = "http://www.xicidaili.com/nt/";

    /**
     * 爬取的最大的页数
     */
    private static final int MAX_PAGE_SIZE = 30;

    /**
     * 超时时间, 单位为: 毫秒
     */
    private static final int TIMEOUT = 3000;

    private static final String TYPE_HTTPS = "https";

    /**
     * IP列表的元素ID
     */
    private static final String IP_LIST_ID = "ip_list";

    private static final int RANDOM_MAX = 0;

    private static final int RANDOM_MIN = 0;


    /**
     * 获取代理IP地址对应的Element元素对象
     *
     * @param type
     *            类型, HTTP 或 HTTPS
     * @param ip
     *            IP地址
     * @param port
     *            端口号
     * @return 对应的Element元素对象
     * @throws IOException
     *             IO异常
     */
    private Element getIpElement(String type, String ip, Integer port) throws IOException {
        boolean needProxy = false;
        if (StringUtils.isNotBlank(ip)) {
            needProxy = true;
            if (port == null) {
                if (log.isWarnEnabled()) {
                    log.warn("port 不能为空");
                }
                throw new ServiceException(ErrorCodeEnum.INVALID_REQUEST_PARAMS.getErrCode(), "port 不能为空");
            }
        }
        String checkUrl = CHECK_URL_BY_HTTP;
        if (StringUtils.equalsIgnoreCase(type, TYPE_HTTPS)) {
            checkUrl = CHECK_URL_BY_HTTPS;
        }

        Connection connection = Jsoup.connect(checkUrl).method(Connection.Method.GET).ignoreContentType(true)
                .followRedirects(true).timeout(TIMEOUT);
        if (needProxy) {
            connection = connection.proxy(ip, port);
        }
        Connection.Response response = connection.execute();
        Document document = response.parse();
        Element ipElement = document.getElementById(IP_LIST_ID);
        return ipElement;
    }

    /**
     * 获取可用的代理IP地址的值对象组成的List对象
     *
     * @param pageNo
     *            页数
     * @return 对应的可用的代理IP地址的值对象组成的List对象
     */
    private List<ProxyIpVO> crawlProxyIpListByPageNo(int pageNo) {
        List<ProxyIpVO> proxyIpVOList = new ArrayList<ProxyIpVO>(1024);
        try {
            String url = PROXY_IP_URL + pageNo;
            Element element = this.getIpElement(url, null, null);
            if (StringUtils.isNotBlank(element.text())) {
                Elements trElements = element.select("tr");
                for (Element trElement : trElements) {
                    Elements tdElements = trElement.select("td");
                    if (tdElements.size() > 0) {
                        String tdIp = tdElements.get(1).text();
                        String tdPort = tdElements.get(2).text();
                        String tdServerAddress = tdElements.get(3).text();
                        String tdType = tdElements.get(5).text();
                        String tdSpeed = tdElements.get(6).select("div").attr("title");
                        String tdConnectionTime = tdElements.get(7).select("div").attr("title");
                        String tdAliveDays = tdElements.get(8).text();
                        String tdGmtPublished = tdElements.get(9).text();
                        if (NumberUtils.isDigits(tdPort) && StringUtils.contains(tdAliveDays, "天")) {
                            try {
                                ProxyIpVO proxyIpVO = new ProxyIpVO();
                                proxyIpVO.setIp(tdIp);
                                int port = NumberUtils.toInt(tdPort);
                                proxyIpVO.setPort(port);
                                ProxyIpVO existProxyIpVO = this.getByUk(tdIp, port);
                                if (existProxyIpVO != null) {
                                    if (log.isWarnEnabled()) {
                                        log.warn("ip={}, port={}对应的代理IP已经存在, 所以跳过, existProxyIpVO={}", tdIp, port, existProxyIpVO);
                                    }
                                    continue;
                                }
                                proxyIpVO.setServerAddress(tdServerAddress);
                                proxyIpVO.setType(tdType);
                                tdSpeed = tdSpeed.replaceAll("秒", "");
                                proxyIpVO.setSpeed(new BigDecimal(tdSpeed).multiply(new BigDecimal("1000")).intValue());
                                tdConnectionTime = tdConnectionTime.replaceAll("秒", "");
                                proxyIpVO.setConnectionTime(new BigDecimal(tdConnectionTime).multiply(new BigDecimal("1000"))
                                        .intValue());
                                tdAliveDays = tdAliveDays.replaceAll("天", "");
                                proxyIpVO.setAliveDays(NumberUtils.toInt(tdAliveDays));
                                proxyIpVO.setGmtPublished(DateUtil.parseDateByDateTimeStr(tdGmtPublished, "yyyy-MM-dd HH:mm"));
                                proxyIpVO.setStatusEnum(CommonStatusEnum.INVALID.getCode());
                                proxyIpVOList.add(proxyIpVO);
                            } catch (Exception e) {
                                log.error("crawlProxyIpListByPageNo Exception, element={}", element, e);
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            log.error("crawlProxyIpListByPageNo exception, pageNo={}", pageNo, e);
        }

        return proxyIpVOList;
    }

    /**
     * 检查代理IP地址是否可用
     *
     * @param proxyIpVO 代理IP地址对应的值对象
     * @return true表示可用, false表示不可用
     */
    private boolean checkEnableProxyIp(ProxyIpVO proxyIpVO) {
        try {
            long number = this.getRandomNumber();
            if (log.isInfoEnabled()) {
                log.info("需要休眠 {} 毫秒", number);
            }
            Thread.sleep(number);
            Element element = this.getIpElement(PROXY_IP_URL, proxyIpVO.getIp(), proxyIpVO.getPort());
            if (element != null) {
                return true;
            }
        } catch (Throwable e) {
            log.error("checkProxyIp Exception, proxyIpVO={}", proxyIpVO, e);
        }
        return false;
    }

    /**
     *
     * @param tdIp
     * @param port
     * @return
     */
    private ProxyIpVO getByUk(String tdIp, int port) {
        return null;
    }


    /**
     * 获取一个随机数
     *
     * @return 新生成的一个随机数
     */
    private long getRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(RANDOM_MAX) % (RANDOM_MAX - RANDOM_MIN + 1) + RANDOM_MIN;
        return number;
    }
}
