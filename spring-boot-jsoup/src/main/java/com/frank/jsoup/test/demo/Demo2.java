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
public class Demo2 {
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

        // 通过Clas获取
        Elements postItemElements = doc.getElementsByClass("post_item");
        System.out.println("--------- 输出post_item -----------");
        for(Element element : postItemElements) {
            System.out.println(element.html());
            System.out.println("--------------------");
        }

        // 通过属性名获取对象
        Elements widthElments = doc.getElementsByAttribute("width");
        System.out.println("--------- 输出width -----------");
        for(Element element : widthElments) {
            System.out.println(element.toString());
            System.out.println("--------------------");
        }

        // 通过属性值和名查找
        Elements widthKvElments = doc.getElementsByAttributeValue("width", "142");
        System.out.println("--------- 输出widthKv -----------");
        for(Element element : widthKvElments) {
            System.out.println(element.toString());
            System.out.println("--------------------");
        }
    }
}
