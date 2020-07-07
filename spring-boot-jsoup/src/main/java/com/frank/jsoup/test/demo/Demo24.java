package com.frank.jsoup.test.demo;

import com.frank.jsoup.test.util.FormatUtil;
import com.frank.jsoup.test.util.HtmlUnitUtil;
import com.frank.jsoup.test.util.ProxyIpUtil;
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
        for(int i=0; i<elements.size(); i++) {
            System.out.println("i = " + i);
            Element element = elements.get(i);
            Elements detailUrlElements = element.select("div > div > a");
            if(detailUrlElements == null || detailUrlElements.size() == 0) {
                System.out.println("不是商品详情地址");
                continue;
            }
            System.out.println("地址为： " + detailUrlElements.get(0).attr("href"));
            Elements priceElements = element.select("div > div.p_discount.commonFontPrice");
            System.out.println(FormatUtil.removeCharactersNon(priceElements.text()));
            Elements titleElements = element.select("div > div.p_productCN");
            System.out.println(titleElements.text());
        }

    }

    /**
     * 商品详情页搜索
     */
    public static void spiderGoodsDetail() throws IOException {

        String storeSearchUrl = "https://www.sephora.cn/product/990638.html" ;

        // 不加载js 取不到信息
        // 加载js 取到信息不准确
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
