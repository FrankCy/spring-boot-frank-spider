package com.frank.jsoup.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.frank.jsoup.test.constant.CommonStatusEnum;
import com.frank.jsoup.test.constant.ErrorCodeEnum;
import com.frank.jsoup.test.exception.ServiceException;
import com.frank.jsoup.test.service.ProxyIpService;
import com.frank.jsoup.test.util.DateUtil;
import com.frank.jsoup.test.util.ProxyIpUtil;
import com.frank.jsoup.test.vo.ProxyIpVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author cy
 * @version ProxyIpServiceImpl.java, v 0.1 2020年06月23日 13:52 cy Exp $
 */
@Slf4j
@Service("proxyIpService")
public class ProxyIpServiceImpl implements ProxyIpService {

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

    /**
     *
     */
    private static final String TYPE_HTTPS = "https";

    /**
     * IP列表的元素ID
     */
    private static final String IP_LIST_ID = "ip_list";

    /**
     * 随机最大值
     */
    private static final int RANDOM_MAX = 100;

    /**
     * 随机最小值
     */
    private static final int RANDOM_MIN = 50;

    /*
    @Override
    public void checkProxyIp() {
        ProxyIpQuery proxyQuery = new ProxyIpQuery();
        int pageNo = CommonConstant.DEFAULT_QUERY_NO;
        proxyQuery.setPageNo(pageNo);

        PageList<ProxyIpVO> pageList = this.listByParam(proxyQuery, CommonConstant.DEFAULT_QUERY_PAGE_SIZE);
        while (pageList.getHasMore()) {
            List<ProxyIpVO> proxyIpVOList = pageList.getData();
            for (ProxyIpVO proxyIpVO : proxyIpVOList) {
                boolean enable = this.checkEnableProxyIp(proxyIpVO);
                proxyIpVO.setGmtChecked(new Date());
                proxyIpVO.setStatusEnum(enable ? CommonStatusEnum.VALID.getCode() : CommonStatusEnum.INVALID.getCode());
                this.update(proxyIpVO);
            }
            proxyQuery.setPageNo(pageNo++);
            pageList = this.listByParam(proxyQuery, CommonConstant.DEFAULT_QUERY_PAGE_SIZE);
        }
        List<ProxyIpVO> proxyIpVOList = pageList.getData();
        if (CollectionUtils.isNotEmpty(proxyIpVOList)) {
            for (ProxyIpVO proxyIpVO : proxyIpVOList) {
                boolean enable = this.checkEnableProxyIp(proxyIpVO);
                proxyIpVO.setGmtChecked(new Date());
                proxyIpVO.setStatusEnum(enable ? CommonStatusEnum.VALID.getCode() : CommonStatusEnum.INVALID.getCode());
                this.update(proxyIpVO);
            }
        }
    }

    */
    @Override
    public void crawProxyIp() {
        log.info("获取代理");
        /*
        for (int pageNo = 1; pageNo <= MAX_PAGE_SIZE; pageNo++) {
            List<ProxyIpVO> proxyIpVOList = this.crawlProxyIpListByPageNo(pageNo);
            for (ProxyIpVO proxyIpVO : proxyIpVOList) {
                int connectionTime = proxyIpVO.getConnectionTime();
                // 只取500延时以内的代理
                if(connectionTime >= 500) {
                    continue;
                }
                StringBuffer str = new StringBuffer();
                String proxyInfo = str.append(proxyIpVO.getIp()).append(":").append(proxyIpVO.getPort()).toString();
                JSONObject json = new JSONObject();
                // 使用次数默认是0
                json.put("USAGE_TIMES", 0);
                // 设置有效时间（10分钟）
                json.put("EXPIRATION_DATE ", getExpirationDate());
                // 设置代理IP服务器地址
                json.put("SERVER_ADDRESS", proxyIpVO.getServerAddress());
                // 何止代理IP超文本协议
                json.put("PROXY_TYPE", proxyIpVO.getType());
                if(ProxyIpUtil.proxyIpMap != null) {
                    ProxyIpUtil.proxyIpMap.put(proxyInfo, json.toJSONString());
                } else {
                    ProxyIpUtil.proxyIpMap = new ConcurrentHashMap<>();
                    crawProxyIp();
                }
            }
        }
        */
        for(int i=0; i<100; i++) {
            JSONObject json = new JSONObject();
            // 使用次数默认是0
            json.put("USAGE_TIMES", 0);
            // 设置有效时间（10分钟）
            json.put("EXPIRATION_DATE ", getExpirationDate());
            // 设置代理IP服务器地址
            json.put("SERVER_ADDRESS", "local");
            // 何止代理IP超文本协议
            json.put("PROXY_TYPE", "https");
            if(ProxyIpUtil.proxyIpMap != null) {
                ProxyIpUtil.proxyIpMap.put("192.168.1."+i+":"+i, json.toJSONString());
            } else {
                ProxyIpUtil.proxyIpMap = new ConcurrentHashMap<>();
                crawProxyIp();
            }
        }
    }

    /**
     * 获取未来10分钟的时间数值（使用的时候用当前时间数值-这个值，得到当前代理是否失效）
     * @return
     */
    private static int getExpirationDate() {
        return 0;
    }

    /**
     * 获取可用的代理IP地址的值对象组成的List对象
     *
     * @param pageNo
     *            页数
     * @return 对应的可用的代理IP地址的值对象组成的List对象
     */
    @Override
    public List<ProxyIpVO> crawlProxyIpListByPageNo(int pageNo) {
        List<ProxyIpVO> proxyIpVOList = new ArrayList<>(1024);
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

    @Override
    public String useProxy() {
        log.info("useProxy begin");
        String resultMsg = "fail";
        // 如果代理为空，重新获取代理
        if(ProxyIpUtil.proxyIpMap == null || ProxyIpUtil.proxyIpMap.size() == 0) {
            crawProxyIp();
        }
        try {
            for(int i=0; i<100; i++) {
                Thread.sleep(1000);
                log.info("任务{} 开始执行", i);
                String executeProxy = "";
                Iterator<Entry<String, String>> iterator = ProxyIpUtil.proxyIpMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    String proxy = entry.getKey();
                    log.info("使用了proxy {} ", proxy);
                    String proxyIpMapVal = entry.getValue();
                    JSONObject jsonObject = JSONObject.parseObject(proxyIpMapVal);
                    int expirationDate = 0;
                    if(jsonObject.get("EXPIRATION_DATE") == null) {
                        expirationDate = 999;
                    } else {
                        expirationDate = jsonObject.getInteger("EXPIRATION_DATE");
                    }
                    // TODO:1000代表当前时间，当前时间小于等于有效期，则代理过期，删除即可
                    if(1000 <= expirationDate) {
                        ProxyIpUtil.proxyIpMap.entrySet().iterator().remove();
                        log.error("代理{}已过有效期，删除该代理，重新获取！",proxy);
                        continue;
                    }
                    int usageTimes = 0;
                    if(jsonObject.get("USAGE_TIMES") == null) {
                        usageTimes = 1;
                    } else {
                        usageTimes = jsonObject.getInteger("USAGE_TIMES");
                    }
                    // TODO:最大使用次数大于等于已用次数时，删除代理，重新获取
                    if(usageTimes >= 5) {
                        ProxyIpUtil.proxyIpMap.remove(proxy);
                        log.error("代理{}使用次数大于最大使用次数，删除该代理，重新获取！", proxy);
                        continue;
                    }
                    usageTimes++;
                    log.info("代理{}可用......", proxy);
                    // 用完了，要把更新的值放回去
                    jsonObject.put("USAGE_TIMES", usageTimes);
                    ProxyIpUtil.proxyIpMap.put(proxy, jsonObject.toJSONString());
                    log.info("代理使用完，更新使用次数后放回代理MAP END");
                    executeProxy = proxy;
                    break;
                }

                log.info("任务{}使用代理{}进行业务操作......", i, executeProxy);
                log.info("任务{} 结束执行\n \n \n", i);
            }
        } catch (Exception e) {
            log.error("useProxy error {}", e);
            return resultMsg;
        }
        resultMsg = "success";

        log.info("useProxy end");

        return resultMsg;
    }

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
     * 获取一个随机数
     *
     * @return 新生成的一个随机数
     */
    private long getRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(RANDOM_MAX) % (RANDOM_MAX - RANDOM_MIN + 1) + RANDOM_MIN;
        return number;
    }

    /**
     * 校验代理是否存在，存在返回原来的，不存在返回新的
     * @param ip
     * @param port
     * @return
     */
    private ProxyIpVO getByUk(String ip, int port) {
        return null;
    }
}
