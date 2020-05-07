package com.frank.jsoup.test.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
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
import java.util.Set;
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
     * @Method
     * @Author cy
     * @Version  1.0
     * @Description
     * @param src
     * @param time
     * @param sleep
     * @Return
     * @Date 2020-04-30
     */
    public static Document getDocument(String src, int time,long sleep){
        driver = getDriver();
        try {
            driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
            driver.get(src);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("被打断错误");
        }
        String html = driver.getPageSource();
        return document= Jsoup.parse(html);
    }

    /**
     * 获取Cookie
     * @param src
     * @param time
     * @param sleep
     * @return
     */
    public static Set<Cookie> getCookieToPhantomjs(String src, int time, int sleep, String proxyInfo, Cookie cookie){
        if(!StringUtils.isEmpty(proxyInfo)) {
            // 设置代理
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyInfo).setFtpProxy(proxyInfo).setSslProxy(proxyInfo);
            dcaps.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
            dcaps.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
            System.setProperty("http.nonProxyHosts", "localhost");
            dcaps.setCapability(CapabilityType.PROXY, proxy);
        }

        driver = getDriver();
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        driver.get(src);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<Cookie> cookies = driver.manage().getCookies();
        return cookies;
    }

    /**
     * @Method
     * @Author cy
     * @Version  1.0
     * @Description
     * @param src
     * @param time
     * @param sleep
     * @param proxyInfo
     * @param listRangeEx
     * @Return
     * @Date 2020-04-30
     */
    public static Elements getDocument(String src, int time, long sleep, String proxyInfo, String listRangeEx){
        if(!StringUtils.isEmpty(proxyInfo)) {
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
        System.out.println("获取结果：" + html);
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        Document document = Jsoup.parse(html);
        return document.select(listRangeEx);
    }


    /**
     * 爬取数据工具类
     * @param src 爬取路径
     * @param time 找不到元素默认等待时间
     * @param sleep 抓取等待间隔
     * @param proxyInfo 代理地址（xxx.xxx.xxx.xxx:xxxx)
     * @param listRangeEx 商品列表的具体位置
     * @param cookieList 携带Cookie
     * @return
     */
    public static Elements getDocument(String src, int time, long sleep, String proxyInfo, String listRangeEx, List<Cookie> cookieList){
        System.out.println("src --------- " + src);
        System.out.println("listRangeEx --------- " + listRangeEx);

        if(!StringUtils.isEmpty(proxyInfo)) {
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
            if(cookieList != null && cookieList.size() !=0 ) {
                for(Cookie ck : cookieList) {
                    driver.manage().addCookie(ck);
                }
            }
            driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
            driver.get(src);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("被打断错误");
        }

        String html = driver.getPageSource();
        System.out.println("爬取的数据： " + html);
        Document document = Jsoup.parse(html);
        return document.select(listRangeEx);
    }


    /**
     * 爬取数据工具类(解决天猫店铺列表无法获取的问题)
     * @param src 爬取路径
     * @param sleep 抓取等待间隔
     * @param targetEx 商品列表的具体位置
     * @return
     */
    public static Elements getDocument(String src, int sleep, String targetEx, Map<String, String> cookieMap, Map<String, String> headerMap) throws IOException {

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
        if(cookieMap != null && headerMap == null) {
            doc = Jsoup.connect(url).userAgent(UserAgentUtil.getRandomUserAgent()).cookies(cookieMap).get();
            System.out.println("---------------- 解析html Cookie ----------------" + doc.val());
        } else
        if(headerMap != null && cookieMap == null) {
            doc = Jsoup.connect(url).userAgent(UserAgentUtil.getRandomUserAgent()).headers(headerMap).get();
            System.out.println("---------------- 解析html Header ----------------" + doc.val());
        } else
        if(cookieMap != null && headerMap != null) {
            doc = Jsoup.connect(url).userAgent(UserAgentUtil.getRandomUserAgent()).headers(headerMap).cookies(cookieMap).get();
            System.out.println("---------------- 解析html Header Cookie ----------------" + doc.val());
        } else {
        //解析html
        doc = Jsoup.connect(url).userAgent(UserAgentUtil.getRandomUserAgent()).get();
        System.out.println("---------------- 解析html no Cookie no Header  ----------------" + doc.val());
        }
        Elements elements = doc.select(targetEx);
        return elements;
    }

    /**
     * 爬取数据工具类(解决天猫店铺列表无法获取的问题)
     * @param src 爬取路径
     * @param sleep 抓取等待间隔
     * @param targetEx 商品列表的具体位置
     * @return
     */
    public static Elements getDocument(String src, int sleep, String targetEx, Map<String, String> cookieMap) throws IOException {

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
        if(cookieMap != null) {
            doc = Jsoup.connect(url).userAgent(UserAgentUtil.getRandomUserAgent()).cookies(cookieMap).get();
            System.out.println("---------------- 解析html Header Cookie ----------------" + doc.val());
        } else {
            //解析html
            doc = Jsoup.connect(url).userAgent(UserAgentUtil.getRandomUserAgent()).get();
            System.out.println("---------------- 解析html no Cookie no Header  ----------------" + doc.val());
        }
        Elements elements = doc.select(targetEx);
        return elements;
    }

    /**
     * 爬取数据工具类(解决天猫店铺列表无法获取的问题)
     * @param src 爬取路径
     * @param sleep 抓取等待间隔
     * @param targetEx 商品列表的具体位置
     * @return
     */
    public static Elements getDocument(String src, int sleep, String targetEx,String ip, int port, Map<String, String> cookieMap) throws IOException {

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
        if(cookieMap != null) {
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            doc = Jsoup.connect(url).proxy(proxy).userAgent(UserAgentUtil.getRandomUserAgent()).cookies(cookieMap).get();
            System.out.println("---------------- 解析html Header Cookie ----------------" + doc.val());
        } else {
            //解析html
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            doc = Jsoup.connect(url).proxy(proxy).userAgent(UserAgentUtil.getRandomUserAgent()).get();
            System.out.println("---------------- 解析html no Cookie no Header  ----------------" + doc.val());
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
