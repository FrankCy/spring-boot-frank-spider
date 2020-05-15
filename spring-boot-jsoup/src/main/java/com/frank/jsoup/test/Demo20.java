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
 * 失败！
 *
 * @author cy
 * @version $Id: Demo20.java, v 0.1 2020年05月14日 13:49 cy Exp $
 */
public class Demo20 {

    /**
     * 店铺搜索神仙水URL
      */
    private static String SHOP_SEARCH_URL = "https://mall.jd.com/view_search-416683-1000003270-1000003270-0-1-0-0-1-1-24.html?keyword=小棕瓶";

    public static void jdShopGoodsList() throws IOException {
        // 获取WebClient
        WebClient webClient = HtmlUnitUtil.getWebClient(true, false, true);
        // 获取开始时间
        long startTime = System.currentTimeMillis();
        HtmlPage shopGoodsHtml = webClient.getPage(SHOP_SEARCH_URL);
        webClient.waitForBackgroundJavaScript(5000);
        // 获取结束时间
        long endTime = System.currentTimeMillis();

        System.out.println("总耗时：" +  (endTime - startTime) / 1000 + "s");

        Document shopGoodsDocument = Jsoup.parse(shopGoodsHtml.asXml());

        // 获取商品在店铺搜索结果的所在位置
        Elements shopGoodsElements = shopGoodsDocument.select("div.jSearchList > div.j-module > div.jSearchListArea");
        System.out.println("店铺内商品搜索个数： " + shopGoodsElements.size());
        for(Element shopGoodsElement : shopGoodsElements) {
            System.out.println("店铺内商品搜索结果为： \n " + shopGoodsElement.html());
        }
    }

    public static void main(String[] args) throws IOException {
        jdShopGoodsList();
        //String s = "div.m-result ul.clearfix li.goodsspiltkeyhttps://list.tmall.com/search_product.htm?q=";
        //String[] sa = s.split("spiltkey");

        //System.out.println(sa[0]);
        //System.out.println(sa[1]);

    }

}
