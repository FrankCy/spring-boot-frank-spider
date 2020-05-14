package com.frank.jsoup.test;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import com.gargoylesoftware.htmlunit.html.HtmlAddress;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
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

    // 店铺搜索神仙水URL
    private static String SHOP_SEARCH_URL = "https://mall.jd.com/view_search-416683-1000003270-1000003270-0-1-0-0-1-1-24.html?keyword=%25E5%25B0%258F%25E6%25A3%2595%25E7%2593%25B6&isGlobalSearch=1";

    public static void jdDetailInfo() throws IOException, InterruptedException {
        HtmlPage shopGoodsHtml= HtmlUnitUtil.getHtmlPage(SHOP_SEARCH_URL, true, false, true, 10000);
        Document shopGoodsDocument = Jsoup.parse(shopGoodsHtml.asXml());

        // 获取商品在店铺搜索结果的所在位置
        // div.jSearchList > div.j-module > div.jSearchListArea
        //Elements shopGoodsElements = shopGoodsDocument.select("div.jSearchList > div.j-module > div.jSearchListArea");
        //Elements shopGoodsElements = shopGoodsDocument.select("div.jSearchList > div.j-module > div.jSearchListArea > div.j-module > ul > li");
        Elements shopGoodsElements = shopGoodsDocument.select("div.jSearchList > div.j-module > div.jSearchListArea > div.jTab > a");
        System.out.println("店铺内商品搜索个数： " + shopGoodsElements.size());
        for(Element shopGoodsElement : shopGoodsElements) {
            System.out.println("店铺内商品搜索结果为： \n " + shopGoodsElement.html());
            if("综合排序".equals(shopGoodsElement.html().trim())) {
                System.out.println("shopGoodsElement.cssSelector() :" + shopGoodsElement.cssSelector());
                // 点击销量，重新获取列表信息
                HtmlAnchor htmlAnchor = shopGoodsHtml.querySelector(shopGoodsElement.cssSelector());
                HtmlPage htmlPage = htmlAnchor.click();
                System.out.println("点击用户评价等待4s.... begin");
                Thread.sleep(5000);
                System.out.println("点击用户评价等待4s.... end");

                Document realDetailInfo = Jsoup.parse(htmlPage.asXml());
                System.out.println(" realDetailInfo ----------- \n" + realDetailInfo.html());
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        jdDetailInfo();
    }

}
