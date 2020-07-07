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

    public static String removeChinese (String s){
        //匹配中文
        String s1 = "[\u4e00-\u9fa5]";
        // 去除中文
        return s.replaceAll(s1, "");
    }

    public static String removeLetter(String s){
        //去除字母
        return s.replaceAll("[a-zA-Z]","");
    }

    public static void main(String[] args) {
        String font = "3*.5g 克";
        font = removeLetter(font);
        System.out.println(font);
        font = removeChinese(font);
        System.out.println(font);
    }
    
}
