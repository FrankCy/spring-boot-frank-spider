package com.frank.jsoup.test;

import com.frank.jsoup.test.util.JsoupUtil;
import com.frank.jsoup.test.util.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
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
@Slf4j
public class Demo8 {

    /**
     * 考拉列表（Xpath版）
     */
    public static void kaolaXpath() {

        // 关键字
        String input = "神仙水";
        // 需要爬取商品信息的网站地址
        String url = "https://search.kaola.com/search.html?key==" + input;
        // 提取HTML得到商品信息结果
        //输出doc可以看到所获取到的页面源代码
//        System.out.println(doc);
        // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
        Elements ulList = JsoupUtil.getElements(url, 1, 3000, true, "div.m-result");
        JXDocument jxd = new JXDocument(ulList);

        // 商品列表
        List<JXNode> goods = jxd.selN("//*[@id=\"result\"]/li");
        for(int i=0; i<goods.size(); i++) {
            System.out.println("---------------------- 分隔 ----------------------");
            JXNode jxNode = goods.get(i);
            // 详情页地址
            String detailUrl = formatNode(jxNode.sel("//li[1]/div/a/@href"));
            System.out.println("详情页地址: " +detailUrl);
            // 售价
            String price = formatNode(jxNode.sel("//li[1]/div/div/p[1]/span[1]/text()"));
            System.out.println("售价: " +price);
            // 会员价
            String memberPrice = formatNode(jxNode.sel("//li[5]/div/div/p[1]/span[2]/text()"));
            System.out.println("会员价: " +memberPrice);
            // 市场价
            String marketprice = formatNode(jxNode.sel("//li[1]/div/div/p[1]/span[2]/del/text()"));
            System.out.println("市场价: " +marketprice);
            // 折扣
            String discountstr = formatNode(jxNode.sel("//li[6]/div/div/p[1]/span[2]/span/text()"));
            System.out.println("折扣: " +discountstr);
            // 商品名称
            String goodsName = formatNode(jxNode.sel("//li[1]/div/div/div/a/h2/text()"));
            System.out.println("商品名称: " +goodsName);
            // 销售信息1
            String saelsinfo1 = formatNode(jxNode.sel("//li[1]/div/div/p[2]/span[1]/text()"));
            System.out.println("销售信息1: " + saelsinfo1);
            // 销售信息2
            String saelsinfo2 = formatNode(jxNode.sel("//li[1]/div/div/p[2]/span[2]/text()"));
            System.out.println("销售信息2: " +saelsinfo2);
            // 销售信息3
            String saelsinfo3 = formatNode(jxNode.sel("//li[1]/div/div/p[2]/span[3]/text()"));
            System.out.println("销售信息3: " +saelsinfo3);
            // 销售信息4
            String saelsinfo4 = formatNode(jxNode.sel("//li[1]/div/div/p[2]/span[4]/text()"));
            System.out.println("销售信息4: " +saelsinfo4);
            // 评论总条目数
            String totalComments = formatNode(jxNode.sel("//li[1]/div/div/p[3]/a/text()"));
            System.out.println("评论总条目数: " +totalComments);
            // 原产地
            String origin = formatNode(jxNode.sel("//li[1]/div/div/p[3]/span/text()"));
            System.out.println("原产地: " +origin);
            // 店铺名称
            String shopName = formatNode(jxNode.sel("//li[1]/div/div/p[4]/span/text()"));
            System.out.println("店铺名称: " + shopName);
        }

    }

    /**
     * 考拉列表（表达式版）
     */
    public static void kaolaSelect() {

        // 关键字
        String input = "神仙水";
        // 需要爬取商品信息的网站地址
        String url = "https://search.kaola.com/search.html?key==" + input;
        // 提取HTML得到商品信息结果
        Elements elements = null;
        try {
            elements = JsoupUtil.getElements(url, 3000, "div.m-result ul.clearfix li.goods", true, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //输出doc可以看到所获取到的页面源代码
//        System.out.println(doc);
        // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
        // 商品列表
        for(Element element : elements) {
            System.out.println("---------------------- 分隔 ----------------------");
            // 详情地址
            String detailUrl = formatNode(element.select("div[class='goodswrap promotion'] a").attr("href"));
            System.out.println("详情地址: " +detailUrl);
            // 售价
            String price = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='price'] span[class='cur']").text());
            System.out.println("售价: " +price);
            // 会员价
            String memberPrice = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='price'] span[class='memberprice']").text());
            System.out.println("会员价: " +memberPrice);
            // 市场价
            String marketprice = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='price'] span[class='marketprice']").text());
            System.out.println("市场价: " +marketprice);
            // 折扣
            String discountstr = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='price'] span[class='discounticon'] span[class='discountstr']").text());
            System.out.println("折扣: " +discountstr);
            // 商品名称
            String goodsName = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] div[class='titlewrap'] a[class='title']").text());
            System.out.println("商品名称: " +goodsName);
            // 销售信息1
            String saelsinfo1 = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='saelsinfo'] span[class='activity z-interestfree']").text());
            System.out.println("销售信息1: " + saelsinfo1);
            // 销售信息2
            String saelsinfo2 = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='saelsinfo'] span[class='activity z-self']").text());
            System.out.println("销售信息2: " +saelsinfo2);
            // 销售信息3
            String saelsinfo3 = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='saelsinfo'] span[class='activity z-benefit']").text());
            System.out.println("销售信息3: " +saelsinfo3);
            // 评论总条目数
            String totalComments = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='goodsinfo clearfix'] a[class='comments']").text());
            System.out.println("评论总条目数: " +totalComments);
            // 原产地
            String origin = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='goodsinfo clearfix'] span[class='proPlace ellipsis']").text());
            System.out.println("原产地: " +origin);
            // 店铺名称
            String shopName = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag']").text());
            System.out.println("店铺名称: " + shopName);
            // 店铺地址
            String homeAddr = formatNode(element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag'] a").attr("href"));
            System.out.println("店铺地址: " + homeAddr);
        }

    }

    /**
     * 天猫列表
     * @throws IOException
     */
    public static void tmall() throws IOException {
        try {
            // 列表看是位置
            String listRangeEx = "div.product";
            String goodsName = "小黑瓶";
            goodsName =  URLEncoder.encode(goodsName, "gb2312");
            Elements elements = JsoupUtil.getElements("https://list.tmall.com/search_product.htm?q="+goodsName, 1, 1001, false, listRangeEx);

            System.out.println(elements.size());
            for(Element e : elements) {
                // 店铺名称
                String shopName = formatNode(e.select("div[class='product-iWrap'] div[class='productShop'] a[class='productShop-name']").text());
                System.out.println("店铺名称: " + shopName);
                // 店铺首页地址
                String homeAddr = formatNode(e.select("div[class='product-iWrap'] div[class='productShop'] a").attr("href"));
                System.out.println("店铺首页地址: " + homeAddr);
                // 商品详情地址
                String listGoodsUrl = formatNode(e.select("div[class='product-iWrap']  div[class='productImg-wrap'] a").attr("href"));
                System.out.println("商品详情地址: " + listGoodsUrl);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatNode(List<JXNode> list) {
        if(list.size() == 0) {
            return "";
        }
        return list.get(0).toString();
    }

    public static String formatNode(String str) {
        if(StringUtils.isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static void main(String[] args) {
        kaolaSelect();
//        kaolaXpath();
    }
}
