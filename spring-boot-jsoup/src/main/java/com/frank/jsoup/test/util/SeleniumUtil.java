package com.frank.jsoup.test.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.jsoup.test.util
 * @ClassName: SeleniumUtil
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 18:07
 */
@Slf4j
public class SeleniumUtil {

    /**
     * 通过Selenum获取信息
     * @param url
     * @param keyword
     * @param targetEx
     * @return
     */
    public static Elements getDocument(String url, String keyword, String targetEx) {
        System.setProperty("webdriver.chrome.driver", "/Users/cy/operation-tools/chromedriver/chromedriver");
        //ChromeDriver服务地址
        ArrayList<String> command = new ArrayList<String>();
        //command.add("--headless");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(command);
        WebDriver driver = new ChromeDriver(options);
        try{
            driver.manage().window().setSize(new Dimension(1920,1080));
            driver.get(url);
            ((ChromeDriver) driver).findElementByXPath(targetEx);
            String docString = driver.getPageSource();
            Document doc  =  Jsoup.parse(docString);
            System.out.println("------------ 数据 ------------" + doc);
            Elements elements = doc.select(targetEx);
            return elements;
        }catch (Exception ex){
            log.info(ex.toString());
        }finally {
            driver.quit();
        }
        return null;
    }

    /**
     * 通过Selenum获取信息
     * @param url
     * @param keyword
     * @param targetEx
     * @param time
     * @param sleep
     * @return
     */
    public static Elements getDocument(String url, String keyword, String targetEx, int time, long sleep) {
        System.setProperty("webdriver.chrome.driver", "/Users/cy/operation-tools/chromedriver/chromedriver");
        //ChromeDriver服务地址
        ChromeOptions options = new ChromeOptions();
        //取消 chrome正受到自动测试软件的控制的信息栏
        options.addArguments("disable-infobars");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        String proxyIpAndPort= "122.188.240.46:4254";
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
        options.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        options.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        options.setProxy(proxy);
        System.setProperty("http.nonProxyHosts", "localhost");

        WebDriver driver = new ChromeDriver(options);
        try{
            driver.manage().window().maximize();
//            driver.manage().window().setSize(new Dimension(1920,1080));
            driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
            driver.get(url);
            Thread.sleep(sleep);
            ((ChromeDriver) driver).findElementByXPath(targetEx);
            String docString = driver.getPageSource();
            Document doc  =  Jsoup.parse(docString);
            System.out.println("------------ 数据 ------------" + doc);
            Elements elements = doc.select(targetEx);
            driver.quit();
            return elements;
        }catch (Exception ex){
            log.info(ex.toString());
        }finally {
            driver.quit();
        }
        return null;
    }

    public static void main(String[] args) {
        // 爬博客园信息（可用）
        /*
        String url = "https://www.cnblogs.com/";
        String keyword = "";
        String targetEx = "//*[@id=\"post_list\"]";
        Elements elements = getDocument(url, keyword, targetEx);
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
        Elements elements = getDocument(url, keyword, targetEx, 20, 20000);
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
