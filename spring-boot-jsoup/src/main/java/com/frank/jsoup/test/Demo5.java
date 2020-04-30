package com.frank.jsoup.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @ProjectName: Spring-Boot-Jsoup
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo5
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-20 09:58
 */
public class Demo5 {

    public static void tmallList() throws IOException {
        // 关键字
        String input = "神仙水";
        // 需要爬取商品信息的网站地址
        String url = "https://list.tmall.com/search_product.htm?q=" + input;
        // 提取HTML得到商品信息结果
        Document doc = null;
        // doc获取整个页面的所有数据
        Connection connection = Jsoup.connect(url);
        connection.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        doc = connection.get();
        //输出doc可以看到所获取到的页面源代码
        System.out.println(doc);
        // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
        Elements ulList = doc.select("div[class='view grid-nosku view-noCom']");
        Elements liList = ulList.select("div[class='product  ']");
        // 循环liList的数据（具体获取的数据值还得看doc的页面源代码来获取，可能稍有变动）
        for (Element item : liList) {
            // 商品ID
            String id = item.select("div[class='product  '] p[class='productStatus'] span[class='ww-light ww-small m_wangwang J_WangWang']").attr("data-item");
            System.out.println("商品ID：" + id);
            // 商品名称
            String name = item.select("p[class='productTitle'] a").attr("title");
            System.out.println("商品名称：" + name);
            // 商品价格
            String price = item.select("p[class='productPrice'] em").attr("title");
            System.out.println("商品价格：" + price);
            // 商品网址
            String goodsUrl = item.select("p[class='productTitle'] a").attr("href");
            System.out.println("商品网址：" + goodsUrl);
            // 商品图片网址
            String imgUrl = item.select("div[class='productImg-wrap'] a img").attr("data-ks-lazyload");
            System.out.println("商品图片网址：" + imgUrl);
            System.out.println("------------------------------------");
        }
        Element contentEle = doc.getElementById("content");
        // 下一页
        String href = contentEle.select(".main .ui-page .ui-page-wrap .ui-page-num .ui-page-next ").attr("href");
        System.out.println("下一页：" + href);
    }

    public static void main(String[] args){
        try {
            tmallList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
