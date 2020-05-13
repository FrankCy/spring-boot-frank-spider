package com.frank.jsoup.test;

import com.frank.jsoup.test.util.HtmlUnitUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * @author cy
 * @version $Id: Demo18.java, v 0.1 2020年05月12日 22:03 cy Exp $
 */
public class Demo18 {

    private static String keyword = "小黑瓶";

    private static String platformListUrl = "https://search.jd.com/Search?keyword=";

    public static void jdPlatformList() throws IOException {
        Document jdPlatformList = HtmlUnitUtil.getHtmlUnitDocument(platformListUrl + keyword, true, false, false, 0);
        // 获取列表中商品所在位置
        Elements platformGoodsListElements = jdPlatformList.select("#J_goodsList > ul > li");
        if(platformGoodsListElements == null || platformGoodsListElements.size() == 0) {
            System.out.println("代理失效，请更换代理后重新尝试");
            return;
        }

        for(Element platformGoods : platformGoodsListElements) {
            // 标题
            String platformGoodsTitle = Optional.ofNullable(platformGoods.select("div > div.p-name.p-name-type-2 > a > em")).orElse(null).get(0).text();
            printHtmlVal("标题", platformGoodsTitle);

            // 价格
            String platformGoodsPrice = Optional.ofNullable(platformGoods.select(" div > div.p-price > strong > i")).orElse(null).get(0).text();
            printHtmlVal("价格", platformGoodsPrice);

            // 商品名称
            String platformGoodsShopName = Optional.ofNullable(platformGoods.select("div > div.p-shop > span > a")).orElse(null).attr("title");
            printHtmlVal("商品名称", platformGoodsShopName);

            // 商品店铺地址
            String platformGoodsShopUrl = Optional.ofNullable(platformGoods.select("div > div.p-shop > span > a")).orElse(null).attr("href");
            printHtmlVal("商品店铺地址", platformGoodsShopUrl);

            // 总评论数（评价在列表中是动态js加载的，不建议在这里获取）

            // ******************************** 分隔（进入店铺进行搜索）********************************

            /***
             * 店铺地址：//mall.jd.com/index-173957.html?from=pc
             * 店铺搜索地址： https://mall.jd.hk/view_search-1595129-133041-133041-0-0-0-0-1-1-60.html?keyword=%E5%B0%8F%E6%A3%95%E7%93%B6&
             * 规律：保留{https://mall.jd.hk/view_search-1595129-}173957-173957{-0-0-0-0-1-1-60.html?keyword=}
             * 总结：需要替换未被大括号包围的内容（不确定是否准确，目前测的几个可以取到）
             */

            // 按照上述组装店铺商品搜索地址
            // 根据店铺地址提取店铺关键字，如上述例子中的(173957)
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(platformGoodsShopName);
            String shopCode = m.replaceAll("").trim();

            // 拼接店铺搜索地址
            String shopSearchRealUrl = "https://mall.jd.hk/view_search-1595129-"+shopCode+"-"+shopCode+"-0-0-0-0-1-1-60.html?keyword="+keyword;
            System.out.println("店铺内商品搜索地址为：" + shopSearchRealUrl);

            Document shopGoodsDocument = HtmlUnitUtil.getHtmlUnitDocument(shopSearchRealUrl, true, true, true, 5000);
            System.out.println("搜索结果为：" + shopGoodsDocument.html());

            // 获取商品在店铺搜索结果的所在位置
            Elements shopGoodsElements = shopGoodsDocument.select("div.j-module.jCurrent > ul > li");
            System.out.println("店铺内商品搜索个数： " + shopGoodsElements.size());
            for(Element shopGoodsElement : shopGoodsElements) {
                System.out.println("店铺内商品搜索结果为： \n " + shopGoodsElement.html());
            }


        }

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


    public static void main(String[] args) throws IOException {
        jdPlatformList();
    }

}
