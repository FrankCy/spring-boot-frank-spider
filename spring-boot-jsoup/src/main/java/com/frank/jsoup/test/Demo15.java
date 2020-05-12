package com.frank.jsoup.test;

import com.frank.jsoup.test.util.JsoupUtil;
import com.frank.jsoup.test.util.SeleniumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        // 店铺信息
        List<WebElement> shopsElements = getWebElement(platformSearchUrl+goodsKeywords);
        System.out.println("找到"+ shopsElements.size() + "个店铺");

        for(WebElement shop : shopsElements) {
            // 店铺名称
            String shopName = SeleniumUtil.getInfo(shop, "div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag']");
            System.out.println("店铺名称:" + shopName);
            // 商品名称
            String goodsName = SeleniumUtil.getInfo(shop, "div[class='goodswrap promotion'] div[class='desc clearfix'] div[class='titlewrap'] a[class='title']");
            System.out.println("商品名称:" + goodsName);
            // 商品店铺地址
            String goodsShopUrl = SeleniumUtil.getInfo(shop, "div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag'] a");
            System.out.println("商品店铺地址:" + goodsShopUrl);
            // 评论信息（总评论数）
            String commentCount = SeleniumUtil.getInfo(shop, "div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='goodsinfo clearfix'] a[class='comments']");
            System.out.println("评论信息（总评论数）:" +commentCount);

            //************************************************************ 分隔【爬取商品详情】*************************************************************//*
            if(StringUtils.isEmpty(goodsShopUrl)) {
                // 当店铺为空时，是考la旗舰店，不考虑，直接下一个 OR 直接访问商品详情（旗舰店的），把数据抓取保存
                continue;
            }
            // 地址 goodsShopUrl
            String[] homeAddrArr = goodsShopUrl.split("/");
            // kaola店铺主键
            String kaolaShopId = homeAddrArr[homeAddrArr.length - 1];
            // 固定格式，后期优化
            String realUrl = "https://mall.kaola.com/search.html?shopId="+kaolaShopId+"&key="+goodsKeywords;

            List<WebElement> goodsInfoByShopElements = getWebElement(realUrl);
            if(goodsInfoByShopElements == null || goodsInfoByShopElements.size() == 0) {
                System.out.println("地址：" + realUrl + "的店铺无法搜索到商品信息");
                continue;
            }
            System.out.println("商品在店铺中结果集有：" + goodsInfoByShopElements.size() + "个");

/*
            for(Element goodsInfoByShopElement : goodsInfoByShopElements) {
                // 标题
                String title = JsoupUtil.formatNode(goodsInfoByShopElement.select("div[class='goodswrap promotion'] a").attr("title"));
                System.out.println("商品标题为：" + title);
                // 商品详情地址
                String goodsDetailUrl = JsoupUtil.formatNode(goodsInfoByShopElement.select("div[class='goodswrap promotion'] a").attr("href"));
                System.out.println("商品详情地址：" + goodsDetailUrl);
                // 组装详情页请求地址
                String goodsDetailRealUrl = "https:"+goodsDetailUrl;

                // 通过WebDriver获取详情页面
                WebDriver webDriver = SeleniumUtil.getChromeDriver(true);
                // 请求商品详情，获取商品详情页面
                System.out.println("商品详情真实地址为：" + goodsDetailRealUrl);
                webDriver.get(goodsDetailRealUrl);
                Thread.sleep(1000);
                WebElement detailElement = null;

                try {
                    detailElement = webDriver.findElement(By.xpath("//*[@id=\"j-producthead\"]/div[1]/dl"));
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    System.out.println("详情为空");
                }
                if(detailElement == null) {
                    System.out.println("xPath无效或代理失效，无法取得详情信息");
                    continue;
                }

                try {
                    // 详情标题
                    String goodsTitle = isEmpty(detailElement.findElement(By.xpath("//*[@id=\"j-producthead\"]/div[1]/dl/dt[3]/span")));
                    System.out.println("详情标题 ：" + goodsTitle);
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    System.out.println("详情标题为空");
                }

                try {
                    // 折扣
                    String discount = isEmpty(detailElement.findElement(By.xpath("//*[@id=\"j-producthead\"]/div[1]/dl/dd[2]/div[1]/div/span[2]")));
                    System.out.println("折扣 ：" + discount);
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    System.out.println("折扣为空");
                }

                try {
                    // 售价
                    String currentPrice = isEmpty(detailElement.findElement(By.xpath("//*[@id=\"j-producthead\"]/div[1]/dl/dd[2]/div[1]/div/span[1]/span")));
                    System.out.println("售价 ：" + currentPrice);
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    System.out.println("售价为空");
                }

                try {
                    // 参考价
                    String marketPrice = isEmpty(detailElement.findElement(By.xpath("//*[@id=\"j-producthead\"]/div[1]/dl/dd[2]/div[1]/div/span[3]/span")));
                    System.out.println("参考价 ：" + marketPrice);
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    System.out.println("参考价为空");
                }
            }

            */
        }

        // 根据详情爬取评论和图片
    }

    public static List<WebElement> getWebElement(String url) throws IOException, InterruptedException {
        // 获取webDriver，传递参数是否使用代理
        WebDriver webDriver = SeleniumUtil.getChromeDriver(true);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get(url);
        Thread.sleep(3000);

        List<WebElement> goodsWebElements = webDriver.findElements(By.cssSelector("div.m-result ul.clearfix li.goods"));
        if(goodsWebElements == null || goodsWebElements.size() == 0) {
            System.out.println("未在店铺找到对应商品信息");
            return null;
        }
        return goodsWebElements;
    }

    public static void getGoodsDetail(String url) throws IOException {
        System.out.println("开始在商店内搜索商品【" + url + "】");
        Elements goodsDetailByShopElements = JsoupUtil.getElements(url, 3000, "div.m-result ul.clearfix li.goods", true, null);
        System.out.println("结束在商店内搜索商品【" + url + "】");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        kaolaSipder();
    }

}
