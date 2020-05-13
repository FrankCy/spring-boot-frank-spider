package com.frank.jsoup.test.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLException;
import java.io.IOException;
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
        WebClient webClient=  initWebClient(proxy);
        HtmlPage page = webClient.getPage(url);
        return page;
    }

    /**
     * 获取HtmlPage
     * @param url
     * @param proxy
     * @return
     */
    public static Document getHtmlUnitDocument(String url, boolean proxy) throws IOException {
        System.out.println("url : " + url);
        WebClient webClient = initWebClient(proxy);
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
        }
        return document;
    }

    /**
     * 初始化WebClient
     * @param proxy
     * @return
     */
    public static WebClient initWebClient(boolean proxy) {
        WebClient webClient = null;
        // 判断是否使用代理并创建webclient,并设置对应的浏览器
        if(proxy) {
            webClient = new WebClient(BrowserVersion.CHROME, "58.218.214.143", 15537);
        } else {
            webClient = new WebClient(BrowserVersion.CHROME);
        }
        // htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        // 设置超时间5000毫秒
        webClient.getOptions().setTimeout(5000);
        // 设置允许执行2000毫秒javascript
        webClient.setJavaScriptTimeout(5000);
        return webClient;
    }

}
