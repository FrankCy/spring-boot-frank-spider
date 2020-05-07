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
}
