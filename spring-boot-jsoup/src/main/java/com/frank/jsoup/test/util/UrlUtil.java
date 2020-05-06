package com.frank.jsoup.test.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test.util
 * @ClassName: UrlUtil
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 12:04
 */
public class UrlUtil {

    /**
     * 获取真实地址
     * @param targetUrl
     * @return
     */
    public static String getRealUrl(String targetUrl) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.getResponseCode();
        String realUrl=conn.getURL().toString();
        conn.disconnect();
        return realUrl;
    }


}
