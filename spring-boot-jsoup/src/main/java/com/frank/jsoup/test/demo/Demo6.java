package com.frank.jsoup.test.demo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo6
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-24 15:33
 */
public class Demo6 {

    public static void main(String[] args) {
        // 关键字
        String input = "xxx";
        // 需要爬取商品信息的网站地址
        String url = "https://xxx.com/search.html?key==" + input;

        // 提取HTML得到商品信息结果
        Document doc = null;


        /** 方式1 begin **/
        // 设置代理
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("119.180.173.81", 8060));
        /** 方式1 end **/

        /** 方式2 begin **/
//        设置代理并提取内容
//        Connection connection1 = Jsoup.connect("https://www.baidu.com/").proxy("171.221.239.11", 808);
        /** 方式2 end **/
        Connection connection = Jsoup.connect(url).proxy(proxy);
        connection.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        try {
            // doc获取整个页面的所有数据
            doc = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
        Elements ulList = doc.select("div.m-result");
        JXDocument jxd = new JXDocument(ulList);

        // 商品
        List<JXNode> goods = jxd.selN("//*[@id=\"result\"]");

//        for(JXNode jxNode: goods) {
        for(int i=0; i<goods.size(); i++) {
            JXNode jxNode = goods.get(i);
            System.out.println("------------------------ jxNode --------------------------");
            System.out.println(jxNode);

        }
    }
}
