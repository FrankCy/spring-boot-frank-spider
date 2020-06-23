package com.frank.jsoup.test.service;

import com.frank.jsoup.test.vo.ProxyIpVO;

import java.util.List;

/**
 *
 *
 * @author cy
 * @version ProxyIpService.java, v 0.1 2020年06月23日 13:51 cy Exp $
 */
public interface ProxyIpService {

    /**
     * 校验代理IP
     */
    //void checkProxyIp();

    /**
     * 爬取代理IP
     */
    //void crawProxyIp();

    /**
     * 爬取
     * @param pageNo
     * @return
     */
    List<ProxyIpVO> crawlProxyIpListByPageNo(int pageNo);

}
