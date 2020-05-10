package com.frank.jsoup.test;

import com.frank.jsoup.test.util.JsoupUtil;
import com.frank.jsoup.test.util.UrlUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test
 * @ClassName: Demo11
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 11:24
 */
public class Demo11 {

    /**
     * 获取天猫店铺搜索列表信息
     * 
     * 步骤
     * 1：获取天猫商品列表动态url
     * 2：拼接实际列表url
     * 3：处理反爬登陆
     * 4：通过动态ip执行请求（避免封了俺）
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //输入要爬取的页面
        String url = "https://store.taobao.com/search.htm?user_number_id=2360209412&keyword=%C0%BC%DE%A2";
        String targetEx = "input#J_ShopAsynSearchURL";
        Elements elements = JsoupUtil.getElements(url, 5000, targetEx, false, null);
        for(Element e : elements) {
            System.out.println(e.html() + " ------------------ ");
            System.out.println(e.val());
            System.out.println("--------------- ||| ----------------");
            System.out.println(e.attr("value"));
        }
        // 获取Cookie
        Map<String, String> cookieMap =  JsoupUtil.getCookie(url, 1000);

        // 获取实际请求地址
        String realUrl = UrlUtil.getRealUrl(url);
        System.out.println("真实商品搜索地址： " + realUrl);
        // https://lancome.tmall.com/search.htm?user_number_id=2360209412&keyword=%C0%BC%DE%A2
        // 按照天猫列表搜索规则格式化实际地址 (例：https://lancome.tmall.com/）
        realUrl = realUrl.substring(0, realUrl.lastIndexOf("/"));

        // 直接获取链接
        String searchUrl = elements.get(0).val();
        // 拼接店铺搜索链接，再次触发爬取
        searchUrl = realUrl + searchUrl;
        System.out.println("店铺实际展示数据的地址为："+ searchUrl);

        // 获取实际请求地址的Cookie信息，然后请求目标对象（程序获取的Cookie不可用，自己根据浏览器调试模式拿到）cookie key v组装的map传递进去就好用，tmall厉害！
//        Elements realElements = JsoupUtil.getElements(searchUrl, 10, 5000,  "163.204.218.144:4242","body");
        Elements realElements = JsoupUtil.getElements(searchUrl, 5000, "body", false, null);

        JXDocument jxd = new JXDocument(realElements);

        // 商品列表
        List<JXNode> goods = jxd.selN("/html/body/div/div[3]/div[1]/dl[1]");

        for(int i=0; i<goods.size(); i++) {
            System.out.println("---------------------- 分隔 ----------------------");
            JXNode jxNode = goods.get(i);
            // 名称
            String detailUrl = JsoupUtil.formatNode(jxNode.sel("/html/body/div/div[3]/div[1]/dl[2]/dd[2]/a"));
            System.out.println("名称: " +detailUrl);
        }

    }


}
