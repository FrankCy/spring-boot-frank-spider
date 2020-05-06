package com.frank.jsoup.test.util;

import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test.util
 * @ClassName: ProxyUtil
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 16:18
 */
public class ProxyUtil {

    /**
     * 验证代理是否可用
     * @param ip
     * @param port
     */
    public static boolean checkValidIP(String ip, int port) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://www.ip138.com");
            //代理服务器
            InetSocketAddress proxyAddr = new InetSocketAddress(ip, port);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
            connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setReadTimeout(4000);
            connection.setConnectTimeout(4000);
            connection.setRequestMethod("GET");

            if(connection.getResponseCode() == 200){
                connection.disconnect();
                return true;
            }

        } catch (Exception e) {
            connection.disconnect();
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(ProxyUtil.checkValidIP("118.181.226.166",44640));
    }
}
