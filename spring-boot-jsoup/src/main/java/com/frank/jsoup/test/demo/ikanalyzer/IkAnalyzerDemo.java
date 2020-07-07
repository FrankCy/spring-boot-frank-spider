package com.frank.jsoup.test.demo.ikanalyzer;

import static com.frank.jsoup.test.demo.ikanalyzer.IKAnalyzerSupport.iKSegmenterToList;

/**
 *
 *
 * @author cy
 * @version IkAnalyzerDemo.java, v 0.1 2020年07月07日 14:51 cy Exp $
 */
public class IkAnalyzerDemo {

    public static void main(String[] args) {
        String str="天气不错";
        try {
            System.out.println(iKSegmenterToList(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
