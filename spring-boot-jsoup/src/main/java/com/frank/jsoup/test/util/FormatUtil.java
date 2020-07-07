package com.frank.jsoup.test.util;

import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 * @author cy
 * @version FormatUtil.java, v 0.1 2020年06月30日 14:23 cy Exp $
 */
public class FormatUtil {

    /**
     * 正则格式化字符串（去除字符串中的英文，但保留小数点）
     * @param str
     * @return
     */
    public static String filterNumber(String str) {
        if (StringUtils.isNotEmpty(str)){
            return str.replaceAll("[^\\d.]","");
        }
        return null;
    }
    /**
     * 去除字符串中的中文
     * @param s 接收一个字符串
     * @return  返回一个去掉了中文的字符串
     */
    public static String removeChinese (String s){
        //匹配中文
        String s1 = "[\u4e00-\u9fa5]";
        // 去除中文
        return s.replaceAll(s1, "");
    }

    /**
     * 去掉字符串中的特殊符号
     * @param s 接收一个字符串
     * @return 返回一个新的去除了特殊字符的字符串
     */
    public static String removeCharacters(String s) {
        //正则 往括号中添加要去除的特殊字符
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        //使用正则替换特殊字符
        return s.replaceAll(regEx,"");
    }


    /**
     * 去掉字符串中的特殊符号(排除"～"和"."这两个符号)
     * @param s 接收一个字符串
     * @return 返回一个新的去除了特殊字符的字符串
     */
    public static String removeCharactersNon(String s) {
        //正则 往括号中添加要去除的特殊字符
        String regEx="[\n`!@#$%^&*()+=|{}':;',\\[\\]<>/?！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        //使用正则替换特殊字符
        return s.replaceAll(regEx,"");
    }

    /**
     * 去除字符串中的字母
     * @param s 接收一个字符串
     * @return 返回一个新的去除过字母的字符串
     */
    public static String removeLetter(String s){
        //去除字母
        return s.replaceAll("[a-zA-Z]","");
    }

    public static void main(String[] args) {
        String str = "七色 abc 28##@66";
        str = removeChinese(str);
        System.out.println(str);
        str = removeCharacters(str);
        System.out.println(str);
        str = removeLetter(str);
        System.out.println(str);
    }
}
