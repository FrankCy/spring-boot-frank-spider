package com.frank.jsoup.test.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

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
    public static Elements getElements(String url, String keyword, String targetEx) {
        WebDriver driver = getChromeDriver(false);
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
    public static Elements getElements(String url, String keyword, String targetEx, int time, long sleep) {

        WebDriver driver = getChromeDriver(false);
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

    /**
     * 获取ChromeDriver，传递是否使用代理
     * @param isProxy
     * @return
     */
    public static WebDriver getChromeDriver(boolean isProxy) {
        System.setProperty("webdriver.chrome.driver", "/Users/cy/operation-tools/chromedriver/chromedriver");
        //ChromeDriver服务地址
        ChromeOptions options = new ChromeOptions();
        //取消 chrome正受到自动测试软件的控制的信息栏
        options.addArguments("disable-infobars");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        String proxyIpAndPort = "";
        Proxy proxy = null;
        if(isProxy) {
            proxyIpAndPort = "58.218.214.130:2036";
            proxy = new Proxy();
            proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
            options.setProxy(proxy);
        }


        options.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        options.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        //设置不弹出ChromeDriver模拟器
        //options.addArguments("--headless");
        //options.addArguments("--disable-gpu");
        //(1) NONE: 当html下载完成之后，不等待解析完成，selenium会直接返回
        //(2) EAGER: 要等待整个dom树加载完成，即DOMContentLoaded这个事件完成，仅对html的内容进行下载解析
        //(3) NORMAL: 即正常情况下，selenium会等待整个界面加载完成（指对html和子资源的下载与解析,如JS文件，图片等，不包括ajax）
        //options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        System.setProperty("http.nonProxyHosts", "localhost");

        WebDriver driver = new ChromeDriver(options);

        return driver;
    }
}
