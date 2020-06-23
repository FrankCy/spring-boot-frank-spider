package com.frank.jsoup.test.demo;

import com.frank.jsoup.test.util.JsoupUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo13
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-07 14:37
 */
public class Demo13 {

    public static void sflList() {
        try {
            // 列表看是位置
            String listRangeEx = "div.p_cont";
            String goodsName = "小黑瓶";
//            goodsName =  URLEncoder.encode(goodsName, "gb2312");
            System.out.println("goodsName: " + goodsName);
            Elements elements = JsoupUtil.getElements("https://www.sephora.cn/search/?k="+goodsName, 1000, listRangeEx, false, null);

            System.out.println(elements.size());
            for(Element e : elements) {
                System.out.println("---------------------------- 信息 ----------------------------");
                // 商品详情地址
                String goodsDetailUrl = e.select("div[class='p_img'] a").attr("href");
                System.out.println("商品详情地址：" + goodsDetailUrl);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sflList();
    }

}
