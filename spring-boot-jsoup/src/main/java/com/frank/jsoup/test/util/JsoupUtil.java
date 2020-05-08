package com.frank.jsoup.test.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.seimicrawler.xpath.JXNode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test.util
 * @ClassName: JsoupUtil
 * @Author: cy
 * @Description:
 *
 * 抓取页面渲染之后通过js填充的数据，需要通过本地的浏览器模拟器，模拟加载，完成之后获取dom
 * 这是一个工具类(phantomjs-2.1.1-macosx作为浏览器模拟器），实现上述功能
 *
 * @Date: 2020-04-26 15:23
 */
public class JsoupUtil {

    private static DesiredCapabilities dcaps = new DesiredCapabilities();
    private static WebDriver driver =null;
    private static Document document=null;
    static {
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持（第二参数表明的是你的phantomjs引擎所在的路径，which/whereis phantomjs可以查看）
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/cy/operation-tools/phantomjs-2.1.1-macosx/bin/phantomjs");
    }

    public static WebDriver getDriver(){
        return new PhantomJSDriver(dcaps);
    }

    /**
     * 爬取数据工具类
     * @param src 爬取路径
     * @param time 找不到元素默认等待时间
     * @param sleep 抓取等待间隔
     * @param isProxy 是否使用代理
     * @param listRangeEx 商品列表的具体位置
     * @return
     */
    public static Elements getDocument(String src, int time, long sleep, boolean isProxy, String listRangeEx){
        System.out.println("src --------- " + src);
        System.out.println("listRangeEx --------- " + listRangeEx);

        if(isProxy) {
            String proxyInfo = "";
            // 设置代理
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyInfo).setFtpProxy(proxyInfo).setSslProxy(proxyInfo);
            dcaps.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
            dcaps.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
            System.setProperty("http.nonProxyHosts", "localhost");
            dcaps.setCapability(CapabilityType.PROXY, proxy);
        }

        driver = getDriver();

        try {
            driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
            driver.get(src);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("被打断错误");
        }

        String html = driver.getPageSource();
        Document document = Jsoup.parse(html);
        return document.select(listRangeEx);
    }


    /**
     * 爬取数据工具类
     * @param src 爬取路径
     * @param sleep 抓取等待间隔
     * @param targetEx 商品列表的具体位置
     * @param isProxy 代理
     * @param cookieMap cookies
     * @return
     */
    public static Elements getDocument(String src, int sleep, String targetEx, boolean isProxy, Map<String, String> cookieMap) throws IOException {

        //输入要爬取的页面
        String url = src;
        try {
            if(sleep < 5000) {
                sleep = 5000;
            }
            // 添加时间间隔 5s，至少5s，解决 418问题。
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Document doc = null;
        java.net.Proxy proxy = null;
        if(isProxy) {
            // 使用代理
            proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("", 0));
        }

        if(cookieMap != null) {
            doc = Jsoup.connect(url).proxy(proxy).userAgent(UserAgentUtil.getRandomUserAgent()).cookies(cookieMap).timeout(50000).get();
        } else {
            doc = Jsoup.connect(url).proxy(proxy).userAgent(UserAgentUtil.getRandomUserAgent()).timeout(50000).get();
        }

        Elements elements = doc.select(targetEx);
        return elements;
    }


    /**
     * 获取目标对象Cookie信息
     * @param src 爬取路径
     * @param sleep 抓取等待间隔
     * @return
     */
    public static Map<String, String> getCookie(String src, int sleep) throws IOException {
        // 设置代理
        Connection.Response response = Jsoup.connect(src).timeout(sleep).execute();
        Map<String, String> cookies = response.cookies();
        return cookies;
    }

    /**
     * 获取目标对象Header信息
     * @param src 爬取路径
     * @param sleep 抓取等待间隔
     * @return
     */
    public static Map<String, String> getHeader(String src, int sleep) throws IOException {
        Connection.Response response = Jsoup.connect(src).timeout(sleep).execute();
        return response.headers();
    }

    public static void cloose(){
        driver.close();
        driver.quit();
    }

    public static String formatNode(List<JXNode> list) {
        if(list.size() == 0) {
            return "";
        }
        return list.get(0).toString();
    }

    public static void main(String[] args) {
        String goods = "商品名称";
        try {
            String url = URLEncoder.encode(goods, "gb2312");
            System.out.println(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
