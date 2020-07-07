package com.frank.jsoup.test.demo;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Demo21 {

    private static String HTTP_OR_HTTPS = "https:";

    // 兰蔻小黑瓶（包含组合套装的）
    private static String URL = "//item.jd.com/100011697752.html";
    // 希芸（单一商品）
    //private static String URL = "//item.jd.com/10341557045.html";

    public static void jdDetailInfo() {
        // 获取WebClient
        Document shopGoodsDocument = null;
        try {
            shopGoodsDocument = HtmlUnitUtil.getHtmlUnitDocument(HTTP_OR_HTTPS + URL, true, false, false, 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取商品头信息
        Elements goodsHeaders = shopGoodsDocument.select("#choose-attr-1 > div.dd");
        System.out.println("goodsHeaders size : " + goodsHeaders.size());
        System.out.println("goodsHeaders html : " + goodsHeaders.html());

        // 获取商品详情信息（代理，非Css、js）
        Elements goodsDetail = shopGoodsDocument.select("#detail > div.tab-con > div:nth-child(1) > div.p-parameter > ul.parameter2.p-parameter-list > li");
        System.out.println("goodsDetail size : " + goodsDetail.size());
        System.out.println("goodsDetail html : " + goodsDetail.html());

        // 标题
        Elements goodsTitle = shopGoodsDocument.select("div > div.itemInfo-wrap > div.sku-name");
        System.out.println("goodsTitle size : " + goodsTitle.size());
        System.out.println("goodsTitle html : " + goodsTitle.text());

        // SKU 通过"降价通知"获取
        String goodsDataSku = shopGoodsDocument.select("div > div.summary-price.J-summary-price > div.dd > a").attr("data-sku");
        System.out.println("goodsDataSku : " + goodsDataSku);
    }



    public static Document spiderJdGoodsList(String storeSearchUrl, String searchAlias) throws IOException {

        // 实际店铺搜索的URL，包含名称、别名
        StringBuilder searchUrl = new StringBuilder();

        // 先通过name爬取
        searchUrl = searchUrl.append(storeSearchUrl).append(searchAlias);
        Document jdNameGoodsList = HtmlUnitUtil.getHtmlUnitDocument(searchUrl.toString(), true, false, true, 5000);
        if(jdNameGoodsList == null || jdNameGoodsList.select("#J_searchWrap > div.check-error").size() > 0) {
            log.error("通过名称{}搜索{}没有返回京东商品列表", searchAlias, searchUrl);
        } else {
            // 如果通过name可以获取，直接return，否则通过Alias获取
            return jdNameGoodsList;
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        //jdDetailInfo();
        Document document = spiderJdGoodsList("https://mall.jd.hk/view_search-1595129-1000003270-1000003270-0-0-0-0-1-1-60.html?keyword=", "雅诗兰黛紧实抗皱竹粹水紫");
        System.out.println(document.html());
    }

}