package com.frank.jsoup.test;

import com.frank.jsoup.test.util.SeleniumUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo12
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-07 10:13
 */
public class Demo12 {



    public static void main(String[] args) {

        // 爬博客园信息（可用）
        /*
        String url = "https://www.cnblogs.com/";
        String keyword = "";
        String targetEx = "//*[@id=\"post_list\"]";
        Elements elements = SeleniumUtil.getDocument(url, keyword, targetEx);
        if(elements == null) {
            System.out.println("没有获取到数据！");
        }
        System.out.println(elements);

        for(Element e : elements) {
            String titleLink = e.select("div.post_item_body a.titlelnk").html();
            System.out.println("------------- titleLink -------------");
            System.out.println(titleLink);
        }
        */

        // 爬天猫列表（要实际传递Cookie，可以模拟登陆获取完整Cookie再请求天猫列表，才会生效）
        String url = "http://lancome.tmall.com/i/asynSearch.htm?mid=w-14640892229-0";
        String keyword = "";
        String targetEx = "/html/body/div/div[3]";
        Elements elements = SeleniumUtil.getDocument(url, keyword, targetEx, 20, 20000);
        if(elements == null) {
            System.out.println("没有获取到数据！");
        }
        System.out.println(elements);

        for(Element e : elements) {
            String titleLink = e.select("div.post_item_body a.titlelnk").html();
            System.out.println("------------- titleLink -------------");
            System.out.println(titleLink);
        }
    }
}
