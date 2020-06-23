package com.frank.jsoup.test.demo;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 *
 *
 * @author cy
 * @version Demo24.java, v 0.1 2020年06月18日 20:23 cy Exp $
 */
public class Demo24 {

    /**
     * 根据店铺搜索爬取商品列表
     */
    public static void spiderStore() throws IOException {

        String spiderUrl = "https://www.sephora.cn/search/?k=小黑瓶";
        Document platformSearchResult = HtmlUnitUtil.getHtmlUnitDocument(spiderUrl, true, false, false, 5000);
        Elements elements = platformSearchResult.select("div:nth-child(4) > ul > li");
        for(Element element : elements) {
            Elements detailUrlElements = element.select("div > div > a");
            if(detailUrlElements == null || detailUrlElements.size() == 0) {
                System.out.println("不是商品详情地址");
                continue;
            }
            System.out.println("地址为： " + detailUrlElements.get(0).attr("href"));
        }

    }

    /**
     * 商品详情页搜索
     */
    public static void spiderGoodsDetail() throws IOException {

        String storeSearchUrl = "https://www.sephora.cn/product/988431.html" ;

        Document goodsDetailResult = HtmlUnitUtil.getHtmlUnitDocument(storeSearchUrl, true, true, true, 10000);
        System.out.println(goodsDetailResult);
        Elements headerInfo = goodsDetailResult.select("div.leftLine.clearFix > div.sku.clearFix > div");
        System.out.println(headerInfo.text());
    }

    public static void main(String[] args) {
        try {
            spiderStore();
            //spiderGoodsDetail();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
