package com.frank.jsoup.test.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author cy
 * @version $Id: HttpClientUtil.java, v 0.1 2020年05月15日 21:19 cy Exp $
 */
@Slf4j
public class HttpClientUtil {

    /**
     * get获取最终结果
     * @param url
     * @param isProxy
     * @param encoding
     * @return
     */
    public static String get(String url, boolean isProxy, String encoding) {
        // 创建http连接池
        CloseableHttpClient httpClient = null;

        // 接收返回值
        CloseableHttpResponse response = null;

        try {

            if(isProxy) {
                httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig()).build();
            } else {
                httpClient = HttpClients.custom().build();
            }
            // 创建Http get请求
            HttpGet httpGet = new HttpGet(url);

            // 设置用户代理
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomUserAgent());

            response = httpClient.execute(httpGet);

            // 返回值
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() != 200) {
                System.out.println("statusLine : " + statusLine.getStatusCode());
                throw new IOException("获取数据失败");
            }

            // 返回页面信息
            HttpEntity httpEntity = response.getEntity();
            String info = httpEntity.toString();
            if(StringUtils.isEmpty(encoding)) {
                return info;
            }
            // 处理编码格式
            String responseInfo = EntityUtils.toString(httpEntity, encoding);

            if(StringUtils.isEmpty(responseInfo)) {
                // 编写重试机制，动态更换代理
                System.out.println("未获取到结果，手动抛出异常，触发重试！");
                throw new RuntimeException();
            }

            // 退出前，关闭连接
            httpClient.close();
            return responseInfo;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建Httpclient发生错误，更换代理，开始重试"+e);
            if(RetryUtil.RETRY_COUNTER > RetryUtil.RETRY_COUNT) {
                System.out.println("重试超过"+RetryUtil.RETRY_COUNT+"次");
                return null;
            }
            log.error("HttpClientUtil get 代理不可用，更换代理:", e);
            try {
                httpClient.close();
            } catch (IOException e1) {
                System.out.println("重试时，关闭http连接失败！"+ e1);
                e1.printStackTrace();
                return "";
            }
            SpiderProxyUtil.replaceProxy();
            try {
                Thread.sleep(RetryUtil.RETRY_WATI_TIME);
            } catch (InterruptedException ee) {
                System.out.println("重试时发生错误!"+ ee);
                return null;
            }
            RetryUtil.RETRY_COUNTER++;
            return get(url, true, "utf-8");
        }
    }

    /**
     * post方式获取HttpEntity
     * @param url
     * @param isProxy
     * @param parameters
     * @return
     * @throws IOException
     */
    public static HttpEntity post(String url, boolean isProxy, HashMap<String, String> parameters) throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = null;
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        // 判断是否有代理
        if(isProxy) {
            httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig()).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        // 伪装成浏览器
        httpPost.setHeader("User-Agent", UserAgentUtil.getRandomUserAgent());
        List<NameValuePair> params = null;
        if(parameters != null && parameters.size() > 0) {
            // 设置参数
            params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
            // 将参数放入Post
            httpPost.setEntity(formEntity);
        }

        // 设置响应信息
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        return response.getEntity();
    }

    /**
     * 获取config
     * @return
     */
    public static RequestConfig getRequestConfig() {
        HttpHost httpHost = new HttpHost(SpiderProxyUtil.IP, SpiderProxyUtil.PORT);
        return RequestConfig.custom().setProxy(httpHost).build();
    }

}
