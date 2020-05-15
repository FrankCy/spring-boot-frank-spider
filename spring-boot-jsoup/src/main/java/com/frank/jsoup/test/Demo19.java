package com.frank.jsoup.test;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;

import java.io.IOException;
import java.util.Optional;

/**
 *
 *
 * @author cy
 * @version $Id: Demo19.java, v 0.1 2020年05月13日 19:52 cy Exp $
 */
public class Demo19 {

    // 商品详情地址
    private static String url = "https://goods.kaola.com/product/1422150.html?spm=a2v0d.b49138598.0.0.7839433e9f0vsq&ri=brand&from=page1&zn=result&zp=page1-1&position=1&istext=0&srId=36f8671c6915a340c1090afefba53a2e";

    public static void kaolaHtmlUnitGoodsDetails() throws InterruptedException, IOException {

        //************************************************************ 分隔【爬取商品详情】*************************************************************//*
        HtmlPage htmlPage = HtmlUnitUtil.getHtmlPage(url, true, false, true, 50000);
        Document shopDetailInfo = Jsoup.parse(htmlPage.asXml());

        // 标题
        String goodsHeaderTitle = spiderText(shopDetailInfo, "dt.product-title > span");
        printHtmlVal("标题", goodsHeaderTitle);
        // 描述
        String subTit = spiderText(shopDetailInfo, "dt.subTit");
        printHtmlVal("描述", goodsHeaderTitle);
        // 售价
        String currentPrice = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-price > div > span.PInfo_r.currentPrice > span");
        printHtmlVal("售价", currentPrice);
        // 特价标
        String memberLabel = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-price > div > span.m-memberLabel");
        printHtmlVal("特价标", memberLabel);
        // 参考价
        String marketPrice = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-price > div > span.PInfo_r.marketPrice.addprice.j-marketprice > span");
        printHtmlVal("参考价", marketPrice);
        // 新人价
        String newPrice = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-price > div > span.PInfo_r.newuserprice.nd___highlighted");
        printHtmlVal("新人价", newPrice);
        // 考拉价
        String kaolaPrice = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-price > div > span.kaolaprice");
        printHtmlVal("考拉价", kaolaPrice);
        // 分期信息
        String huabeiWrap = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-huabei-wrap");
        printHtmlVal("分期信息", huabeiWrap);
        // 黑卡会员预计节省金额
        String vipMember = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-vipmember > div:nth-child(1) > span > i");
        printHtmlVal("黑卡会员预计节省金额", vipMember);
        // 满减折扣
        String vipMemberZk = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-vipmember > div:nth-child(2) > span");
        printHtmlVal("满减折扣", vipMemberZk);
        // 津贴信息
        String allowance = spiderText(shopDetailInfo, "dd.m-price-wrap > div.m-allowance > span");
        printHtmlVal("津贴信息", allowance);
        // 星级评价
        String percentStar = spiderText(shopDetailInfo, "dd.m-comment-bar.j-commentbar > span.emptyStar.percentStar > i");
        printHtmlVal("星级评价", percentStar);
        // 评价度
        String goodsPercent = spiderText(shopDetailInfo,"dd.m-comment-bar.j-commentbar > span.goodPercent");
        printHtmlVal("评价度", goodsPercent);
        // 评价人数
        String commentSum = spiderText(shopDetailInfo, "dd.m-comment-bar.j-commentbar > span.comnum.comm > a");
        printHtmlVal("评价人数", commentSum);
        // 晒单人数
        String withImgCount = spiderText(shopDetailInfo, "dd.m-comment-bar.j-commentbar > span.commWithImg.comm > a");
        printHtmlVal("晒单人数", withImgCount);

        Thread.sleep(2000);

        // ********* 获取考la商品图片 *********
        // 获取"用户评价"，执行点击
        HtmlSpan userCommentSpan = htmlPage.querySelector("span.j-userratingTab.j-navtab");
        HtmlPage clickHtmlPage = userCommentSpan.click();
        System.out.println("点击用户评价等待4s.... begin");
        Thread.sleep(5000);
        System.out.println("点击用户评价等待4s.... end");

        Document realDetailInfo = Jsoup.parse(clickHtmlPage.asXml());
        // 再获取页面
        //Elements elements = realDetailInfo.select("#goodsDetail > div.m-textarea");
        Elements elements = realDetailInfo.select("#goodsDetail > div.m-textarea > div.textareabox > p > img");
        for(Element e : elements) {
            // 商品详情图片
            System.out.println("商品详情图片: " + e.attr("data-src"));
        }

        // 获取评论信息
        // 评论下分页区
        boolean isNextPage = true;
        while(isNextPage) {
            //评论区
            Elements commentElements = realDetailInfo.select("body > article > div.mainBtmWrap.clearfix > div.goodsDetailWrap.j-goodsdetailwrap > div.j-userrating > div > div.commWrap > div");
            for(Element commentElement : commentElements) {
                //评星个数
                System.out.println("-------- 评星个数 ---------");
                System.out.println(commentElement.select("ul.commItem.commcnt.f-fl > li.commcnttop.clearfix > span > span > span.fullStar.smw10 > i").size());

                //颜色：规格
                System.out.println("-------- 颜色：规格 ---------");
                System.out.println(commentElement.select("ul.commItem.commcnt.f-fl > li.commcnttop.clearfix > div > span").html());

                //评论信息
                System.out.println("-------- 评论信息 ---------");
                System.out.println(commentElement.select("ul.commItem.commcnt.f-fl > li:nth-child(2) > span").text());

                //图片地址
                System.out.println("-------- 图片地址 ---------");
                Elements commentImgs = commentElement.select("ul.commItem.commcnt.f-fl > li:nth-child(3) > div.pic_list.clearfix > a > img");
                for(Element commentImg : commentImgs) {
                    System.out.println(commentImg.attr("src"));
                }
            }
            Elements commentPageNavWrap = realDetailInfo.select("#pageNavWrap");
            if(commentPageNavWrap == null || commentPageNavWrap.size() == 0) {
                System.out.println("未找到分页'下一页'按钮");
                return;
            }

            // 获取分页位置
            Element commentPage = commentPageNavWrap.get(0);
            if(commentPage.select("li.pagenxt").hasClass("disabled")) {
                isNextPage = false;
                continue;
            }
            HtmlInput nextPage = htmlPage.querySelector("li.pagenxt");
            nextPage.click();
            System.out.println("执行翻页等待4s.... begin");
            Thread.sleep(4000);
            System.out.println("执行翻页等待4s.... end");
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

    public static void main(String[] args) throws InterruptedException, IOException {
        kaolaHtmlUnitGoodsDetails();
    }

}
