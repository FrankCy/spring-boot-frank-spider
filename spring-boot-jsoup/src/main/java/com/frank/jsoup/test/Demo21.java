package com.frank.jsoup.test;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 *
 *
 * @author cy
 * @version $Id: Demo21.java, v 0.1 2020年05月15日 11:34 cy Exp $
 */
public class Demo21 {

    private static String HTTP_OR_HTTPS = "https:";

    // 兰蔻小黑瓶（包含组合套装的）
    //private static String URL = "//item.jd.com/100011697752.html";
    // 希芸（单一商品）
    private static String URL = "//item.jd.com/10341557045.html";

    public static void jdDetailInfo() {
        // 获取WebClient
        WebClient webClient = HtmlUnitUtil.getWebClient(true, false, false);
        // 获取开始时间
        long startTime = System.currentTimeMillis();
        HtmlPage shopGoodsHtml = null;
        try {
            shopGoodsHtml = webClient.getPage(HTTP_OR_HTTPS + URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        webClient.waitForBackgroundJavaScript(5000);
        // 获取结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时：" +  (endTime - startTime) / 1000 + "s");

        Document shopGoodsDocument = Jsoup.parse(shopGoodsHtml.asXml());

        // 获取商品头信息
        Elements goodsHeaders = shopGoodsDocument.select("#choose-attr-1 > div.dd");
        System.out.println("goodsHeaders size : " + goodsHeaders.size());
        System.out.println("goodsHeaders html : " + goodsHeaders.html());

        // 获取商品详情信息（代理，非Css、js）
        Elements goodsDetail = shopGoodsDocument.select("#detail > div.tab-con > div:nth-child(1) > div.p-parameter > ul.parameter2.p-parameter-list > li");
        System.out.println("goodsDetail size : " + goodsDetail.size());
        System.out.println("goodsDetail html : " + goodsDetail.html());
    }

    public static void main(String[] args) {
        jdDetailInfo();
    }

}
