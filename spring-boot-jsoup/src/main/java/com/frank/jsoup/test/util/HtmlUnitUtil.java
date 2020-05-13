package com.frank.jsoup.test.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 *
 * @author cy
 * @version $Id: HtmlUnitUtil.java, v 0.1 2020年05月12日 22:42 cy Exp $
 */
public class HtmlUnitUtil {

    /**
     * 获取HtmlPage
     * @param url
     * @param proxy
     * @return
     */
    public static HtmlPage getHtmlPage(String url, boolean proxy) throws IOException {
        WebClient webClient = initWebClient(proxy, false, false, 2000);
        HtmlPage page = webClient.getPage(url);
        return page;
    }

    /**
     * 获取HtmlPage
     * @param url
     * @param proxy
     * @return
     */
    public static Document getHtmlUnitDocument(String url, boolean proxy, boolean isCss, boolean isJs, long waitForBackgroundJavaScriptTime) throws IOException {
        System.out.println("url : " + url);
        WebClient webClient = initWebClient(proxy, isCss, isJs, waitForBackgroundJavaScriptTime);
        Document document = null;
        try {
            HtmlPage page = webClient.getPage(url);
            document = Jsoup.parse(page.asXml());
        } catch (SSLException sslex) {
            sslex.printStackTrace();
            System.out.println("发生 SSLException ----- 证书签名错误 [" + url + "]" );
        } catch (SocketTimeoutException ste) {
            ste.printStackTrace();
            System.out.println("连接超时 ----- 请更换代理 [" + url + "]");
        } catch (FailingHttpStatusCodeException fhsce) {
            fhsce.printStackTrace();
            System.out.println("连接错误 ----- 请更换代理 [" + url + "]");
        } catch (SocketException se) {
            se.printStackTrace();
            System.out.println("连接错误 ----- 请更换代理 [" + url + "]");
        }
        return document;
    }

    /**
     * 初始化WebClient
     * @param isProxy
     * @param isCss
     * @param isJs
     * @return
     */
    public static WebClient initWebClient(boolean isProxy,boolean isCss, boolean isJs, long waitForBackgroundJavaScriptTime) {
        WebClient webClient = null;
        // 判断是否使用代理并创建webclient,并设置对应的浏览器
        if(isProxy) {
            webClient = new WebClient(BrowserVersion.CHROME, "58.218.92.76", 3992);
        } else {
            webClient = new WebClient(BrowserVersion.CHROME);
        }
        webClient.setRefreshHandler(new ThreadedRefreshHandler());
        // 禁用css，避免二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        if(isCss) {
            // 启用css
            webClient.getOptions().setCssEnabled(true);
        } else  {
            // 禁用css，避免二次请求CSS进行渲染
            webClient.getOptions().setCssEnabled(false);
        }
        if(isJs) {
            // 启动js
            webClient.getOptions().setJavaScriptEnabled(true);
            // 设置支持Ajax
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        } else {
            webClient.getOptions().setJavaScriptEnabled(false);
        }
        // 当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(true);
        // 设置"浏览器"超时间5000毫秒
        webClient.getOptions().setTimeout(120000);
        // 忽略SSL认证
        webClient.getOptions().setUseInsecureSSL(true);
        // 设置js执行超时时间
        webClient.setJavaScriptTimeout(5000);

        webClient.setJavaScriptEngine(new MyJavaScriptEngine(webClient));
        // 设置阻塞线程时间
        webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScriptTime);
        return webClient;
    }

}
