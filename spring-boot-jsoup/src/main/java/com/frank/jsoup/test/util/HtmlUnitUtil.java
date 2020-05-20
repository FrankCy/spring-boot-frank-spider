package com.frank.jsoup.test.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import net.sourceforge.htmlunit.corejs.javascript.EcmaError;
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
     * 重试cishu
     */
    private static int RETRY_COUNT = 5;

    /**
     * 重试间隔
     */
    private static long WAIT_TIME = 2000;

    /**
     * 获取HtmlPage
     * @param url
     * @param proxy
     * @return
     */
    public static HtmlPage getHtmlPage(String url, boolean proxy, boolean isCss, boolean isJs, long waitForBackgroundJavaScriptTime) {
        WebClient webClient = initWebClient(proxy, isCss, isJs);
        webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScriptTime);
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);
        } catch (SocketTimeoutException ste) {
            ste.printStackTrace();
            System.out.println("创建连接失败 SocketTimeoutException，请重新获取代理");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建连接失败 IOException，请重新获取代理");
        }
        return page;
    }

    /**
     * 获取WebClient
     * @param proxy
     * @param isCss
     * @param isJs
     * @return
     */
    public static WebClient getWebClient(boolean proxy, boolean isCss, boolean isJs) {
        WebClient webClient = initWebClient(proxy, isCss, isJs);
        return webClient;
    }

    /**
     * 获取HtmlPage
     * @param url
     * @param proxy
     * @return
     */
    public static Document getHtmlUnitDocument(String url, boolean proxy, boolean isCss, boolean isJs, long waitForBackgroundJavaScriptTime) throws IOException {
        System.out.println("url : " + url);
        WebClient webClient = initWebClient(proxy, isCss, isJs);
        Document document = null;
        try {
            HtmlPage page = webClient.getPage(url);
            // 设置阻塞线程时间（js执行后）
            webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScriptTime);
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
        } catch (ClassCastException cce) {
            cce.printStackTrace();
            System.out.println("连接错误 ----- 解析错误，无法获取到类！");
        } catch (EcmaError ecmaError) {
            ecmaError.printStackTrace();
            System.out.println("解析错误 ----- 无法解析");
        }
        webClient.close();
        return document;
    }

    /**
     * 初始化WebClient
     * @param isProxy
     * @param isCss
     * @param isJs
     * @return
     */
    public static WebClient initWebClient(boolean isProxy,boolean isCss, boolean isJs) {
        WebClient webClient = null;
        // 判断是否使用代理并创建webclient,并设置对应的浏览器
        if(isProxy) {
            webClient = new WebClient(BrowserVersion.CHROME, SpiderProxyUtil.IP, SpiderProxyUtil.PORT);
        } else {
            webClient = new WebClient(BrowserVersion.CHROME);
        }
        webClient.setRefreshHandler(new ThreadedRefreshHandler());
        // 禁用css，避免二次请求CSS进行渲染
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
            // 设置支持Ajax
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        }
        // 当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        // 允许重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 设置连接超时时间 ，这里是5S。如果为0，则无限期等待
        webClient.getOptions().setTimeout(5000);
        // 设置是否允许使用ActiveX
        webClient.getOptions().setActiveXNative(false);
        // 忽略SSL认证
        webClient.getOptions().setUseInsecureSSL(false);
        // 设置js执行超时时间
        webClient.setJavaScriptTimeout(10000*3);
        // 设置不跟踪抓取
        webClient.getOptions().setDoNotTrackEnabled(false);
        // 设置Cookie
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setJavaScriptEngine(new MyJavaScriptEngine(webClient));

        return webClient;
    }


}
