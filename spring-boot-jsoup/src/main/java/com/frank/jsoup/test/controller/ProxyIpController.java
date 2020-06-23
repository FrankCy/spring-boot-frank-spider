package com.frank.jsoup.test.controller;

import com.frank.jsoup.test.service.ProxyIpService;
import com.frank.jsoup.test.vo.ProxyIpVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 *
 *
 * @author cy
 * @version ProxyIpController.java, v 0.1 2020年06月23日 15:20 cy Exp $
 */
@Slf4j
@Controller
@RequestMapping(value = "/proxyIp")
public class ProxyIpController {

    @Autowired
    private ProxyIpService proxyIpService;

    @GetMapping(value = "/getProxyIpList")
    public String getProxyIpList(int pageNo) {
        List<ProxyIpVO> proxyIpVOList = proxyIpService.crawlProxyIpListByPageNo(pageNo);
        for(ProxyIpVO proxyIpVO : proxyIpVOList) {
            System.out.println(proxyIpVO.toString());
        }
        return "OJBK";
    }

    @GetMapping(value = "/initProxyIpMap")
    public String initProxyIpMap(int pageNo) {
        proxyIpService.crawProxyIp();
        return "success";
    }

    @PostMapping("/useProxy")
    public String useProxy() {
        String result = "fail";
        try {
            String resultMsg = proxyIpService.useProxy();
            if("success".equals(resultMsg)) {
                result = resultMsg;
            }
        } catch(Exception e) {
            log.error("error, {}", e);
        }

        return result;
    }


}
