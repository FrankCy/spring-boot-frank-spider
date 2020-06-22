package com.frank.jsoup.test.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 * @author cy
 * @version $Id: SpiderProxyUtil.java, v 0.1 2020年05月15日 22:25 cy Exp $
 */
public class SpiderProxyUtil {

    public static String IP = "58.218.92.76";

    public static int PORT = 8849;

    private static String PROXY_URL = "http://http.tiqu.alicdns.com/getip3?num=1&type=2&pro=&city=0&yys=0&port=1&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";

    public static boolean replaceProxy() {
        String result = HttpClientUtil.get(PROXY_URL, false,"UTF-8");
        if(StringUtils.isEmpty(result)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        String data = jsonObject.getString("data");
        if(StringUtils.isNotEmpty(code) && jsonObject.containsKey("success")) {
            JSONArray dataJsonArray = JSONArray.parseArray(data);
            JSONObject proxyJson =  JSONObject.parseObject(dataJsonArray.get(0).toString());
            IP = proxyJson.getString("ip");
            PORT = proxyJson.getInteger("port");
            return true;
        }
        return false;
    }

}
