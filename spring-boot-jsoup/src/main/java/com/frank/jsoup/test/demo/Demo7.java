package com.frank.jsoup.test.demo;

import com.frank.jsoup.test.util.JsoupUtil;
import org.jsoup.select.Elements;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo7
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-26 15:26
 */
public class Demo7 {

    public static void main(String[] args) {
        try {
            Elements elements = JsoupUtil.getElements("https://item.jd.com/63512697584.html", 1, 1001, false, "[class='price']");
            System.out.println(elements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
