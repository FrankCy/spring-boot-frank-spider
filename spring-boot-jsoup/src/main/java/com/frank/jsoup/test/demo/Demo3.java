package com.frank.jsoup.test.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @ProjectName: Spring-Boot-Jsoup
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo1
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-14 12:00
 */
public class Demo3 {
    public static void main(String[] args) {

        // 创建httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建httpget实例
        HttpGet httpGet = new HttpGet("http://www.cnblogs.com/");

        // 执行get
        CloseableHttpResponse response = null;
        // 初始化网页内容
        String content = "";
        try {
            response = httpClient.execute(httpGet);
            // 获取响应结果
            HttpEntity entity = response.getEntity();
            // 获取转码后的网页信息
            content = EntityUtils.toString(entity, "UTF-8");
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 使用Jsoup(类似获取页面节点)
        // 解析网页
        Document doc =  Jsoup.parse(content);

        // 通过选择器选择获取节点
        // 通过选择器按照博客链接查找帖子
        Elements linkElements = doc.select("#post_list .post_item .post_item_body h3 a");
        for(Element e : linkElements) {
            System.out.println("博客标题：" + e.text());
        }

        // 通过选择器获取包含href的a标签
        Elements aHrefElements = doc.select("a[href]");
        System.out.println("--------- 输出aHref -----------");
        for(Element e : aHrefElements) {
            System.out.println(e.toString());
        }

        // 通过选择器获取扩展名为.png的图片
        Elements pngElements = doc.select("img[src$=.png]");
        System.out.println("--------- 输出png -----------");
        for(Element e : pngElements) {
            System.out.println(e.toString());
        }

        // 通过选择器获取tag是title的节点
        Element titleElement = doc.getElementsByTag("title").first();
        System.out.println("--------- 输出title -----------");
        System.out.println(titleElement.text());

    }
}
