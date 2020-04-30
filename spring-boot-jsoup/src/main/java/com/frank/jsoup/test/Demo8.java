package com.frank.jsoup.test;

import com.frank.jsoup.test.util.JsoupUtil;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo8
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-30 10:09
 */
public class Demo8 {

    public static void kaola() throws IOException {
        try {
            // 列表看是位置
            String listRangeEx = "div.m-result";
            String goodsName = "神仙水";
            Elements elements = JsoupUtil.getDocument("https://search.kaola.com/search.html?key="+goodsName, 1, 1001, null, listRangeEx);
            JXDocument jxd = new JXDocument(elements);

            System.out.println("elements : " + elements);

            // 商品
            List<JXNode> goods = jxd.selN("//*[@id=\"result\"]");
            System.out.println("goods : " + goods);

            for(int i=0; i<goods.size(); i++) {
                JXNode jxNode = goods.get(i);
                System.out.println("------------------------ jxNode --------------------------");
                System.out.println(jxNode);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tmall() throws IOException {
        try {
            // 列表看是位置
            String listRangeEx = "div[class='view grid-nosku view-noCom']";
            String goodsName = "小黑瓶";
            goodsName =  URLEncoder.encode(goodsName, "gb2312");
            Elements elements = JsoupUtil.getDocument("https://list.tmall.com/search_product.htm?q="+goodsName, 1, 1001, null, listRangeEx);
            JXDocument jxd = new JXDocument(elements);

            System.out.println("elements : " + elements);

            // 商品
            List<JXNode> goods = jxd.selN("//*[@id=\"J_ItemList\"]/div[1]");
            System.out.println("goods : " + goods);

            for(int i=0; i<goods.size(); i++) {
                JXNode jxNode = goods.get(i);
                System.out.println("------------------------ jxNode --------------------------");
                System.out.println(jxNode);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        tmall();
    }
}
