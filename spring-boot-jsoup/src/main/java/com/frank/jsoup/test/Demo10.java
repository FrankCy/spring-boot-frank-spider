package com.frank.jsoup.test;

import com.frank.jsoup.test.util.JsoupUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo10
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 10:26
 */
public class Demo10 {

    /**
     * 医药网药品信息
     * @throws IOException
     */
    public static void tmallDetail() throws IOException {
        try {
            // 列表看是位置
            String listRangeEx = "div.layui-tab-item layui-show";
            String goodsName = "西药";
            goodsName =  URLEncoder.encode(goodsName, "gb2312");
            System.out.println("goodsName: " + goodsName);
            Elements elements = JsoupUtil.getDocument("https://search.mypharma.com/?s_classify1="+goodsName, 1, 1000, false, listRangeEx);

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
