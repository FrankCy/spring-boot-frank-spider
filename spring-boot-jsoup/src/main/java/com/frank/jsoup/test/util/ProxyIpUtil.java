package com.frank.jsoup.test.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author cy
 * @version ProxyIpUtil.java, v 0.1 2020年06月23日 17:25 cy Exp $
 */
public class ProxyIpUtil {
    
    public static ConcurrentHashMap<String, String> proxyIpMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        String sss = "3.asdb.31a.csad51123";
        System.out.println(sss.replaceAll("[^\\d.]",""));

    }
    
}
