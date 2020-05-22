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
    //private static String SHOP_SEARCH_URL = "https://mall.jd.com/view_search-416683-1000003270-1000003270-0-1-0-0-1-1-24.html?keyword=小棕瓶";
    private static String SHOP_SEARCH_URL = "https://mall.jd.com/view_search-56012-22622-21977-0-0-0-0-1-1-60.html?keyword=小蓝瓶";

    public static void jdShopGoodsList() throws IOException {
        // 获取WebClient
        WebClient webClient = HtmlUnitUtil.getWebClient(true, false, true);
        // 获取开始时间
        long startTime = System.currentTimeMillis();
        HtmlPage shopGoodsHtml = webClient.getPage(SHOP_SEARCH_URL);
        webClient.waitForBackgroundJavaScript(2000);
        // 获取结束时间
        long endTime = System.currentTimeMillis();

        System.out.println("总耗时：" +  (endTime - startTime) / 1000 + "s");

        Document shopGoodsDocument = Jsoup.parse(shopGoodsHtml.asXml());

        // 获取商品在店铺搜索结果的所在位置
        Elements shopGoodsElements = shopGoodsDocument.select("div.j-module > ul > li > div > div.jGoodsInfo > div.jDesc");
        System.out.println("店铺内商品搜索个数： " + shopGoodsElements.size());
        for(Element shopGoodsElement : shopGoodsElements) {
            System.out.println("店铺内商品详情地址为： \n " + shopGoodsElement.select("a").attr("href"));
            /**
             * 店铺内商品搜索个数： 3
             * 店铺内商品搜索结果为：
             * //item.jd.com/1006634870.html
             * 店铺内商品搜索结果为：
             * //item.jd.com/1068258866.html
             * 店铺内商品搜索结果为：
             * //item.jd.com/10341557045.html
             */
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
