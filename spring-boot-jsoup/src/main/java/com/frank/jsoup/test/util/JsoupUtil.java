package com.frank.jsoup.test.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

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

    public static Document getDocument(String src, int time,long sleep){
        driver=getDriver();
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
    public static void cloose(){
        driver.close();
        driver.quit();
    }


}
