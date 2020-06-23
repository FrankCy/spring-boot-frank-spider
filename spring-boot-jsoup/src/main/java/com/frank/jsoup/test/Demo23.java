package com.frank.jsoup.test;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 *
 * 考拉爬取
 *
 * @author cy
 * @version Demo23.java, v 0.1 2020年06月18日 17:57 cy Exp $
 */
public class Demo23 {

    /**
     * 根据店铺搜索爬取商品列表
     */
    public static void spiderStore() throws IOException {

        String spiderUrl = "https://search.kaola.com/search.html?key=小黑瓶发光眼霜";
        Document platformSearchResult = HtmlUnitUtil.getHtmlUnitDocument(spiderUrl, true, false, false, 5000);
        Elements elements = platformSearchResult.select("div.m-result ul.clearfix li.goods");
        for(Element element : elements) {
            String storeName = element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag']").get(0).text();
            String storeUrl = "https:"+element.select("li > div > a").attr("href");
            String goodsDetail = "https:"+element.select("div[class='goodswrap promotion'] a").attr("href");
            if(StringUtils.isEmpty(storeUrl) || "https:".equals(storeUrl)) {
                System.out.println("官方自营店没有店铺地址，迭代下一个！");
                continue;
            }
            String shopCode = storeUrl.split(".com/")[1];
            String storeSearchUrl = "https://mall.kaola.com/search.html?shopId="+shopCode+"&key=";

            System.out.println("店铺名："+ storeName);
            System.out.println("店铺首页："+ storeUrl);
            System.out.println("商品详情："+ goodsDetail);
            System.out.println("店铺搜索地址："+ storeSearchUrl);
        }

    }

    /**
     * 店铺列表搜索
     */
    public static void spiderStoreList() throws IOException {

        String storeSearchUrl = "https://mall.kaola.com/search.html?shopId=317770&key=小黑瓶" ;

        Document storeSearchResult = HtmlUnitUtil.getHtmlUnitDocument(storeSearchUrl, true, false, false, 5000);
        Elements elements = storeSearchResult.select("#result > li");
        for(Element element : elements) {
            String goodsDetailUrl = "https:"+element.select("div > div > div > a").attr("href");
            System.out.println("商品详情页地址："+goodsDetailUrl);
        }
    }

    /**
     * 商品详情页搜索
     */
    public static void spiderGoodsDetail() throws IOException {

        String storeSearchUrl = "https://goods.kaola.com/product/1402279.html?spm=a2v0d.b49118739.0.0.43c41278z4hsNw&referPage=searchPage&referId=%E6%81%B6%E9%AD%94%E4%B9%8B%E7%9C%BC%E6%89%8B%E9%93%BE&from=page1&position=0&istext=0&srId=dd36bbfdb23b5e4260c948e9ca1c6b8d&zn=result&zp=page1-0&ri=%E6%81%B6%E9%AD%94%E4%B9%8B%E7%9C%BC%E6%89%8B%E9%93%BE&rp=search" ;

        Document goodsDetailResult = HtmlUnitUtil.getHtmlUnitDocument(storeSearchUrl, true, true, true, 5000);
        Elements headerInfo = goodsDetailResult.select("#j-producthead > div.PInfoWrap.clearfix > dl");
        System.out.println("标题 : " + validateData(headerInfo.select("dt.product-title > span")));
        System.out.println("描述 : " + validateData(headerInfo.select("dt.subTit")));
        System.out.println("售价 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-price > div > span.PInfo_r.currentPrice > span")));
        System.out.println("特价标 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-price > div > span.m-memberLabel")));
        System.out.println("参考价 : " +  validateData(headerInfo.select("dd.m-price-wrap > div.m-price > div > span.PInfo_r.marketPrice.addprice.j-marketprice > span")));
        System.out.println("新人价 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-price > div > span.PInfo_r.newuserprice.nd___highlighted")));
        System.out.println("考拉价 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-price > div > span.kaolaprice")));
        System.out.println("分期信息 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-huabei-wrap")));
        System.out.println("黑卡会员预计节省金额 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-vipmember > div:nth-child(1) > span > i")));
        System.out.println("满减折扣 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-vipmember > div:nth-child(2) > span")));
        System.out.println("津贴信息 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-allowance > span")));
        System.out.println("星级评价 : " + validateData(headerInfo.select("dd.m-comment-bar.j-commentbar > span.emptyStar.percentStar > i")));
        System.out.println("评价度 : " + validateData(headerInfo.select("dd.m-comment-bar.j-commentbar > span.goodPercent")));
        System.out.println("评价人数 : " + validateData(headerInfo.select("dd.m-comment-bar.j-commentbar > span.comnum.comm > a")));
        System.out.println("晒单人数 : " + validateData(headerInfo.select("dd.m-comment-bar.j-commentbar > span.commWithImg.comm > a")));
    }

    public static String validateData(Elements detailInfo) {
        if(detailInfo == null || detailInfo.size() == 0) {
            System.out.println("无法获取参数");
            return "无";
        }
        return detailInfo.get(0).text();

    }

    public static void main(String[] args) {
        try {
            spiderStore();
            //spiderStoreList();
            //spiderGoodsDetail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
