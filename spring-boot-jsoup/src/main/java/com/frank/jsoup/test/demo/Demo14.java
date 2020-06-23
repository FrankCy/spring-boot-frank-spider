package com.frank.jsoup.test.demo;

import com.frank.jsoup.test.util.JsoupUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo14
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-09 14:55
 */
public class Demo14 {

    /**
     * 模拟考la店铺搜索商品，并获取商品列表信息
     */
    public static void kaolaShopListDetail() {
        try {
            // 列表看是位置
            String listRangeEx = "li.goods";
            String goodsName = "神仙水";
            String url = "https://mall.kaola.com/search.html?shopId=36098255&key=";
            Elements elements = JsoupUtil.getElements(url+goodsName, 1000, listRangeEx, true, null);

            System.out.println(elements.size());
            for(Element e : elements) {
                System.out.println("---------------------------- 信息 ----------------------------");
                // 商品详情地址
                String goodsDetailUrl = e.select("div[class='goodswrap promotion'] a").attr("href");
                System.out.println("商品详情地址：" + goodsDetailUrl);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        kaolaShopListDetail();
    }

}
