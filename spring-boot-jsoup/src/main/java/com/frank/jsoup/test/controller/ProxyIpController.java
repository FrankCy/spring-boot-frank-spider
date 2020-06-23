package com.frank.jsoup.test.controller;

import com.frank.jsoup.test.service.ProxyIpService;
import com.frank.jsoup.test.vo.ProxyIpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 *
 *
 * @author cy
 * @version ProxyIpController.java, v 0.1 2020年06月23日 15:20 cy Exp $
 */
@Controller
@RequestMapping(value = "/proxyIp")
public class ProxyIpController {

    @Autowired
    private ProxyIpService proxyIpService;

    @RequestMapping(value = "/getProxyIpList", method = RequestMethod.GET)
    public String listByParam() {
        List<ProxyIpVO> proxyIpVOList = proxyIpService.crawlProxyIpListByPageNo(50);
        for(ProxyIpVO proxyIpVO : proxyIpVOList) {
            System.out.println(proxyIpVO.toString());
        }
        return "OJBK";
    }

}
