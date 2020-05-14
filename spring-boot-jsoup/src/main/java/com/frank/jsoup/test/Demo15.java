package com.frank.jsoup.test;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo15
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-09 17:50
 */
@Slf4j
public class Demo15 {

    /**
     * 获取考la图片和评论信息
     *
     * 要求：
     * 指定商品全平台店铺信息
     * 评论获取
     * 图片下载到本地
     *
     * 技术需要解决问题：
     * 翻页处理
     * 图片URL获取下载
     *
     */
    public static void kaolaSipder() throws IOException,InterruptedException {

        // 商品关键字
        String goodsKeywords = "神仙水";
        // https://www.kaola.com/
        // https://search.kaola.com/search.html?key=
        // 平台信息
        String platformUrl = "https://www.kaola.com/";
        // 平台搜索地址
        String platformSearchUrl = "https://search.kaola.com/search.html?key=";
        String realGoods = URLEncoder.encode(goodsKeywords, "utf-8");
        Document kaolaPlatformGoodsList = HtmlUnitUtil.getHtmlUnitDocument(platformSearchUrl+realGoods, true, false, false, 1000);
        // 获取列表中商品所在位置
        Elements platformShopsListElements = kaolaPlatformGoodsList.select("#result > li");
        if(platformShopsListElements == null || platformShopsListElements.size() == 0) {
            System.out.println("代理失效，请更换代理后重新尝试");
            return;
        }
        for(Element element : platformShopsListElements) {
            System.out.println(" ---------------- shop ---------------");
            // 店铺名称
            String shopName = Optional.ofNullable(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag']")).orElse(null).get(0).text();
            printHtmlVal("店铺名称", shopName);

            // 商品名称
            String goodsName = Optional.ofNullable(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] div[class='titlewrap'] a[class='title']")).orElse(null).get(0).text();
            printHtmlVal("商品名称", goodsName);

            // 商品店铺地址
            String goodsShopUrl = Optional.ofNullable(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag'] a")).orElse(null).attr("href");
            printHtmlVal("商品店铺地址", goodsShopUrl);

            // 总评论数
            String commentCount = Optional.ofNullable(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='goodsinfo clearfix'] a[class='comments']")).orElse(null).get(0).text();
            printHtmlVal("总评论数", commentCount);

            if(StringUtils.isEmpty(goodsShopUrl)) {
                // 当店铺为空时，是考la旗舰店，不考虑，直接下一个 OR 直接访问商品详情（旗舰店的），把数据抓取保存
                continue;
            }

            //************************************************************ 分隔【店铺内搜索商品】*************************************************************//*

            // 地址 goodsShopUrl
            String[] homeAddrArr = goodsShopUrl.split("/");
            // kaola店铺主键
            String kaolaShopId = homeAddrArr[homeAddrArr.length - 1];
            // 固定格式，后期优化
            String realUrl = "https://mall.kaola.com/search.html?shopId="+kaolaShopId+"&key="+goodsKeywords;
            Document shopGoodsList = HtmlUnitUtil.getHtmlUnitDocument(realUrl, true, false, false, 1000);
            if(shopGoodsList == null) {
                System.out.println("地址：" + realUrl + "的店铺无法搜索到商品信息");
                continue;
            }
            Elements shopGoodsElements = shopGoodsList.select("div.m-result ul.clearfix li.goods");
            System.out.println("商品在店铺"+ shopName +"中结果集有：" + shopGoodsElements.size() + "个");

            for(Element shopGoodsElement : shopGoodsElements) {
                // 标题
                String shopGoodsTitile = Optional.ofNullable(shopGoodsElement.select("div[class='goodswrap promotion'] a")).orElse(null).attr("title");
                printHtmlVal("标题", shopGoodsTitile);

                // 商品详情地址
                String goodsDetailUrl = Optional.ofNullable(shopGoodsElement.select("div[class='goodswrap promotion'] a")).orElse(null).attr("href");
                printHtmlVal("商品详情地址", goodsDetailUrl);
                Thread.sleep(1000);
                if(StringUtils.isEmpty(goodsDetailUrl)) {
                    System.out.println("店铺["+shopName+"]中无法获取商品["+shopGoodsTitile+"]的详情真实地址");
                    continue;
                }
            }
        }
    }

    public static String spiderText(Document document, String cssSelector) {
        try {
            Elements elements = document.select(cssSelector);
            if(elements == null || elements.size() == 0) {
                return "";
            }
            return Optional.ofNullable(document.select(cssSelector)).orElse(null).get(0).text();
        } catch (NoSuchElementException n) {
            System.out.println(n.getSupportUrl() + "获取内容失败");
        }
        return "";
    }

    /**
     * 格式化数据
     * @param description
     * @param val
     */
    public static void printHtmlVal(String description, String val) {
        if(StringUtils.isEmpty(val)) {
            val = "";
        }
        System.out.println(description+":"+val);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        kaolaSipder();
    }

}
