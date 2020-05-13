package com.frank.jsoup.test.util;

import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.javascript.host.Window;

/**
 *
 *
 * @author cy
 * @version $Id: MyJavaScriptEngine.java, v 0.1 2020年05月13日 15:32 cy Exp $
 */
public class MyJavaScriptEngine extends JavaScriptEngine {

    private static WebClient webClient;

    public MyJavaScriptEngine(WebClient webClient) {
        super(webClient);
        MyJavaScriptEngine.webClient = webClient;
    }
    @Override
    protected void handleJavaScriptException(final ScriptException scriptException, final boolean triggerOnError) {
        // Trigger window.onerror, if it has been set.
        final HtmlPage page = scriptException.getPage();
        if (triggerOnError && page != null) {
            final WebWindow window = page.getEnclosingWindow();
            if (window != null) {
                final Window w = (Window) window.getScriptableObject();
                if (w != null) {
                    try {
                        w.triggerOnError(scriptException);
                    }
                    catch (final Exception e) {
                        handleJavaScriptException(new ScriptException(page, e, null), false);
                    }
                }
            }
        }
        final JavaScriptErrorListener javaScriptErrorListener = webClient.getJavaScriptErrorListener();
        if (javaScriptErrorListener != null) {
            javaScriptErrorListener.scriptException(page, scriptException);
        }
        // Throw a Java exception if the user wants us to.
        if (webClient.getOptions().isThrowExceptionOnScriptError()) {
            throw scriptException;
        }
        // Log the error; ScriptException instances provide good debug info.
        // LOG.info("Caught script exception", scriptException);
    }
}
