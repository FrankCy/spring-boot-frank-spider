package com.frank.jsoup.test;

import com.frank.jsoup.test.util.JsoupUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo9
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-30 20:27
 */
public class Demo9 {

    /**
     * 天猫详情页
     * @throws IOException
     */
    public static void tmallDetail() throws IOException {
        try {
            // 列表看是位置
            String listRangeEx = "dl.J_TModule";
            String goodsName = "兰蔻";
            goodsName =  URLEncoder.encode(goodsName, "gb2312");
            System.out.println("goodsName: " + goodsName);
            Elements elements = JsoupUtil.getElements("https://store.taobao.com/search.htm?user_number_id=2360209412&keyword="+goodsName, 10, 3000, false, listRangeEx);

            System.out.println(elements.size());
            for(Element e : elements) {
                System.out.println("---------------------------- 信息 ----------------------------");
                System.out.println(e.html());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        tmallDetail();
    }

}
