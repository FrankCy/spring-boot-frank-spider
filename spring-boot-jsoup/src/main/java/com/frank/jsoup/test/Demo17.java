package com.frank.jsoup.test;

import com.frank.jsoup.test.util.SeleniumUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author cy
 * @version $Id: Demo17.java, v 0.1 2020年05月12日 14:19 cy Exp $
 */
public class Demo17 {

    private static String url = "https://goods.kaola.com/product/8208875.html?zn=result&zp=page1-0";

    /**
     * 考la商品明细（爬取图片并下载）
     */
    public static void spiderDetailImg() throws InterruptedException {
        // 获取webDriver，传递参数是否使用代理
        WebDriver webDriver = SeleniumUtil.getChromeDriver(true);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get(url);
        Thread.sleep(3000);

        // ********* 商品头信息 *********
        WebElement goodsHeader = webDriver.findElement(By.cssSelector("#j-producthead > div.PInfoWrap.clearfix > dl"));
        if(goodsHeader == null || "".equals(goodsHeader.getText())) {
            System.out.println("未找到商品头信息");
            return;
        }
        //System.out.println("商品头信息：" + goodsHeader.getText());
        try {
            System.out.println("标题 : " + getInfo(goodsHeader,"dt.product-title > span"));
            System.out.println("描述 : " + getInfo(goodsHeader,"dt.subTit"));
            System.out.println("售价 : " + getInfo(goodsHeader, "dd.m-price-wrap > div.m-price > div > span.PInfo_r.currentPrice > span"));
            System.out.println("特价标 : " + getInfo(goodsHeader, "dd.m-price-wrap > div.m-price > div > span.m-memberLabel"));
            System.out.println("参考价 : " +  getInfo(goodsHeader,"dd.m-price-wrap > div.m-price > div > span.PInfo_r.marketPrice.addprice.j-marketprice > span"));
            System.out.println("新人价 : " + getInfo(goodsHeader,"dd.m-price-wrap > div.m-price > div > span.PInfo_r.newuserprice.nd___highlighted"));
            System.out.println("考拉价 : " + getInfo(goodsHeader,"dd.m-price-wrap > div.m-price > div > span.kaolaprice"));
            System.out.println("分期信息 : " + getInfo(goodsHeader,"dd.m-price-wrap > div.m-huabei-wrap"));
            System.out.println("黑卡会员预计节省金额 : " + getInfo(goodsHeader,"dd.m-price-wrap > div.m-vipmember > div:nth-child(1) > span > i"));
            System.out.println("满减折扣 : " + getInfo(goodsHeader,"dd.m-price-wrap > div.m-vipmember > div:nth-child(2) > span"));
            System.out.println("津贴信息 : " + getInfo(goodsHeader,"dd.m-price-wrap > div.m-allowance > span"));
            System.out.println("星级评价 : " + getInfo(goodsHeader,"dd.m-comment-bar.j-commentbar > span.emptyStar.percentStar > i"));
            System.out.println("评价度 : " + getInfo(goodsHeader,"dd.m-comment-bar.j-commentbar > span.goodPercent"));
            System.out.println("评价人数 : " + getInfo(goodsHeader,"dd.m-comment-bar.j-commentbar > span.comnum.comm > a"));
            System.out.println("晒单人数 : " + getInfo(goodsHeader,"dd.m-comment-bar.j-commentbar > span.commWithImg.comm > a"));
        } catch (NoSuchElementException n) {
            System.out.println(n.getSupportUrl() + "获取内容失败");
        }
        Thread.sleep(2000);

        // ********* 获取考la商品图片 *********
        Document document = Jsoup.parse(webDriver.getPageSource());
        Elements elements = document.select("#goodsDetail > div.m-textarea > div:nth-child(2) > div > p > img");
        for(Element e : elements) {
            // 商品详情图片
            System.out.println(e.attr("data-src"));
        }

        // ********* 获取评论信息 *********
        // 获取到"用户评价 {数量}"的位置
        WebElement clickWebElement=  webDriver.findElement(By.xpath("/html/body/article/div[4]/div[2]/div[1]/span[2]"));
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", clickWebElement);
        System.out.println("点击用户评价等待4s.... begin");
        Thread.sleep(4000);
        System.out.println("点击用户评价等待4s.... end");

        // 评论下分页区
        boolean isNextPage = true;
        while(isNextPage) {
            Document commentDocument = Jsoup.parse(webDriver.getPageSource());
            //评论区
            Elements commentElements = commentDocument.select("body > article > div.mainBtmWrap.clearfix > div.goodsDetailWrap.j-goodsdetailwrap > div.j-userrating > div > div.commWrap > div");
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
            Elements commentPageNavWrap = commentDocument.select("#pageNavWrap");
            if(commentPageNavWrap == null) {
                System.out.println("未找到分页信息");
                return;
            }

            // 获取分页位置
            Element commentPage = commentPageNavWrap.get(0);
            if(commentPage.select("li.pagenxt").hasClass("disabled")) {
                isNextPage = false;
                continue;
            }
            WebElement nextPage = webDriver.findElement(By.cssSelector("li.pagenxt"));
            ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", nextPage);
            System.out.println("执行翻页等待4s.... begin");
            Thread.sleep(4000);
            System.out.println("执行翻页等待4s.... end");
        }

        // 关闭webDriver
        webDriver.quit();
    }

    public static String getInfo(WebElement goodsHeader, String ex) {
        try {
            return goodsHeader.findElement(By.cssSelector(ex)).getText();
        } catch (NoSuchElementException n) {
            System.out.println(n.getSupportUrl() + "获取内容失败");
        }
        return "";
    }

    public static void main(String[] args) throws InterruptedException {
        spiderDetailImg();
    }

}
