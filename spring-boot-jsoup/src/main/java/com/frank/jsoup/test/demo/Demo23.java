package com.frank.jsoup.test.demo;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 考拉爬取
 *
 * @author cy
 * @version Demo23.java, v 0.1 2020年06月18日 17:57 cy Exp $
 */
@Slf4j
public class Demo23 {


    // 当前系统颜色
    private static String localColor = "Ruby";

    // 当前系统规格
    private static String localSpecifications = "3g";

    /**
     * 根据店铺搜索爬取商品列表
     */
    public static void spiderStore() throws IOException {

        String spiderUrl = "https://search.kaola.com/search.html?key=施华洛世奇心跳图案手链";
        Document platformSearchResult = HtmlUnitUtil.getHtmlUnitDocument(spiderUrl, true, false, false, 5000);
        // 判断通过关键字查找是否取到了信息 #filterbox > div.correction
        Elements checkResultElements = platformSearchResult.select("#filterbox > div.correction");
        if(checkResultElements != null && checkResultElements.size() > 0 ) {
            log.info("无法找到当前商品{}", spiderUrl);
            return;
        }

        Elements elements = platformSearchResult.select("div.m-result ul.clearfix li.goods");
        for(Element element : elements) {
            String storeName = element.select("div[class='goodswrap promotion'] div[class='desc clearfix'] p[class='selfflag']").get(0).text();
            String storeUrl = "https:"+element.select("li > div > a").attr("href");
            String goodsDetail = "https:"+element.select("div[class='goodswrap promotion'] a").attr("href");
            String storeIndexUrl = "https:"+element.select("div > div > p.selfflag > a").attr("href");
            System.out.println("storeIndexUrl --- " + storeIndexUrl);
            if(StringUtils.isEmpty(storeIndexUrl) || "https:".equals(storeUrl)) {
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
        //String storeSearchUrl = "https://goods.kaola.com/product/2891969.html" ;
        // 非自营店的一条数据
        //String storeSearchUrl = "https://goods.kaola.com/product/8296696.html" ;
        // 口红，非自营店（有很多颜色，颜色不同价格也不同）
        String storeSearchUrl = "https://goods.kaola.com/product/2891969.html" ;

        HtmlPage htmlPage = HtmlUnitUtil.getHtmlPage(storeSearchUrl, true, false, true, 5000);
        Document goodsDetailResult = Jsoup.parse(htmlPage.asXml());

        Elements headerInfo = goodsDetailResult.select("#j-producthead > div.PInfoWrap.clearfix > dl");

        System.out.println("个数： " + headerInfo.size() + "\n \n \n \n");
        if(headerInfo == null || headerInfo.size() == 0) {
            System.out.println("无法获取头信息");
        }

        String title = validateData(headerInfo.select("dt.product-title > span"));
        System.out.println("标题 : " + title);

        /*
        if(StringUtils.isNotEmpty(localColor) && StringUtils.isNotEmpty(localSpecifications)) {
            // 有颜色，有规格
            // 先匹配颜色（从title中取，后续实现切换颜色）
            if(!title.contains(localColor)) {
                log.info("颜色没匹配上");
                return;
            }

            // 净含量（规格）
            String specifications = getSpecifications(headerInfo, "li.selectedLi > a > span");
            if (specifications.contains(localSpecifications) || title.contains(localSpecifications)) {
                // 如果净含量能匹配规格或者标题中有规格
                log.info("有规格，持久化数据");
                return;
            }
        } else
        if(StringUtils.isNotEmpty(localColor) && StringUtils.isEmpty(localSpecifications)) {
            // 有颜色，无规格
            if(!title.contains(localColor)) {
                log.info("颜色没匹配上");
                return;
            }
            log.info("有颜色，持久化");
        } else
        if(StringUtils.isEmpty(localColor) && StringUtils.isNotEmpty(localSpecifications)) {
            // 没有颜色，有规格
            String specifications = getSpecifications(headerInfo, "li.selectedLi > a > span");
            if (specifications.contains(localSpecifications) || title.contains(localSpecifications)) {
                // 如果净含量能匹配规格或者标题中有规格
                log.info("有规格，持久化数据");
                return;
            }
        }
        */

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
        System.out.println("净含量 : " + getSpecifications(headerInfo, "li.selectedLi > a > span"));
        System.out.println("考拉商品ID : " + headerInfo.select("#j-formEl > input[type=hidden]:nth-child(4)").val());
        System.out.println("组合 : " + headerInfo.select("div.skuBox.clearfix.first-skubox.last-skuBox > div > ul > li.imgbox").size());

        // 先判断是否有颜色区域（非口红类的商品没有颜色区域）
        if(StringUtils.isNotEmpty(validateData(headerInfo.select("#j_skuwrap > div.skuBox.clearfix.first-skubox.last-skuBox")))
        && !"无".equals(validateData(headerInfo.select("#j_skuwrap > div.skuBox.clearfix.first-skubox.last-skuBox")))) {
            DomNodeList<DomNode> liList = htmlPage.querySelectorAll("div.skuBox.clearfix.first-skubox.last-skuBox > div > ul > li.imgbox");
            for(int i=0; i<liList.size(); i++) {
                DomNode domNode = liList.get(i);
                //System.out.println(" domNode 颜色 : " + domNode.getAttributes().getNamedItem("title").getNodeValue());
                //System.out.println(" domNode 颜色 : "  + domNode.querySelector("li");
                //System.out.println(" domNode 售价 : " + validateData(headerInfo.select("dd.m-price-wrap > div.m-price > div > span.PInfo_r.currentPrice > span")));
                //System.out.println(" domNode 颜色 : "  + domNode.getNextElementSibling().getAttribute("title"));
                //String domNodeColor = domNode.querySelector("p.selectedDesc").asText();
                //System.out.println(" domNode 颜色 : "  + domNodeColor);
                DomElement nextElementSibling = domNode.getNextElementSibling();
                HtmlPage nextHtmlPage = nextElementSibling.click();
                // 执行点击事件
                System.out.println(" next domNode 价格 :" + nextHtmlPage.querySelector("dd.m-price-wrap > div.m-price > div > span.PInfo_r.currentPrice > span").asText());
                System.out.println(" next domNode 颜色 :" + nextHtmlPage.querySelector("li.selectedImgDesc.show > p.selectedDesc").asText());
                //System.out.println(" nextElementSibing 颜色 :" + nextElementSibing.querySelector("p.selectedDesc").asText());
                //#j_skuwrap > div.skuBox.clearfix.first-skubox.last-skuBox > div > ul > li.selectedImgDesc> p.selectedDesc
            }
        }
    }

    public static String getSpecifications(Elements elements, String cssSelector) {
        return validateData(elements.select(cssSelector));
    }

    public static String validateData(Elements detailInfo) {
        if(detailInfo == null || detailInfo.size() == 0) {
            System.out.println("无法获取参数");
            return "无";
        }
        return detailInfo.get(0).text();

    }

    public static void main(String[] args) {
        int length = 1;
        final CountDownLatch cdl = new CountDownLatch(length);

        ExecutorService executor = new ThreadPoolExecutor(50, 200, 0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(500),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        for(int i=0; i<length; i++) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.execute(() -> {
                try {
                    //spiderStore();
                    //spiderStoreList();
                    spiderGoodsDetail();
                } catch (Exception e) {
                    log.error("任务明细初始化失败：爬取商品详情失败！");
                    cdl.countDown();
                    e.printStackTrace();
                }
                cdl.countDown();
            });
        }
        executor.shutdown();
        try {
            // 需要捕获异常，当其中线程数为0时这里才会继续运行
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
