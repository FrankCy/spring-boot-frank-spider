package com.frank.jsoup.test.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日志的工具对象
 *
 * @author sky.lixin
 * @version DateUtil.java, v 0.1 2020年06月01日 15:06 sky.lixin Exp $
 */
@Slf4j
public class DateUtil {

    /**
     * 完整日期时间的格式化
     */
    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 完整日期的格式化
     */
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 1天的时间，单位为毫秒
     */
    private static final long ONE_DAY = 1L * 24 * 60 * 60 * 1000;

    /**
     * 1小时的时间，单位为毫秒
     */
    private static final long ONE_HOUR = 1L * 60 * 60 * 1000;

    /**
     * 1分钟的时间，单位为毫秒
     */
    private static final long ONE_MINUTE = 1L * 60 * 1000;

    /**
     * 1秒钟的时间，单位为毫秒
     */
    private static final long ONE_SECOND = 1L * 1000;

    /**
     * 开始时间的字符串
     */
    private static final String START_TIME = " 00:00:00";

    /**
     * 结束时间的字符串
     */
    private static final String END_TIME = " 23:59:59";

    /**
     * 日期时间格式化的ThreadLocal对象
     */
    private static final ThreadLocal<DateFormat> DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATE_TIME_FORMAT);
        }
    };

    /**
     * 日期格式化的ThreadLocal对象
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };

    /**
     * 将日期时间的字符串转换为对应的日期对象
     *
     * @param date 日期
     * @param dateTimeStr 日期时间的字符，格式为：yyyy-MM-dd HH:mm:ss
     * @return 对应的日期对象
     */
    public static Date parseDateByDateTimeStr(String date, String dateTimeStr) {
        return null;
    }

    /**
     * 将日期时间的字符串转换为对应的日期对象
     *
     * @param dateTimeStr 日期时间的字符，格式为：yyyy-MM-dd HH:mm:ss
     * @return 对应的日期对象
     */
    public static Date parseDateByDateTimeStr(String dateTimeStr) {
        if (StringUtils.isEmpty(dateTimeStr)) {
            return null;
        }
        try {
            return DATE_TIME_FORMAT_THREAD_LOCAL.get().parse(dateTimeStr);
        } catch (ParseException e) {
            log.error("getDateByDateTimeStr failed, dateTimeStr={}", dateTimeStr, e);
        }
        return null;
    }

    /**
     * 将时期时间戳转换为对应的日期时间的字符，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime 时期时间戳，单位为毫秒
     * @return 对应的日期时间的字符，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeStr(long dateTime) {
        return DATE_TIME_FORMAT_THREAD_LOCAL.get().format(new Date(dateTime));
    }

    /**
     * 将日期对象转换为对应的日期时间的字符，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期对象
     * @return 对应的日期时间的字符，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeStr(Date date) {
        return DATE_TIME_FORMAT_THREAD_LOCAL.get().format(date);
    }

    /**
     * 将日期的字符串转换为对应的日期对象
     *
     * @param dateStr 日期的字符，格式为：yyyy-MM-dd
     * @return 对应的日期对象
     */
    public static Date parseDateByDateStr(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            return DATE_FORMAT_THREAD_LOCAL.get().parse(dateStr);
        } catch (ParseException e) {
            log.error("getDateByDateStr failed, dateStr={}", dateStr, e);
        }
        return null;
    }

    /**
     * 获取日期时间的字符串，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param daysDiff               需要获取的日期距离今天的差距，0表示今天，1表示明天，-1表示昨天
     * @param joinStartTimeOrEndTime 时间字符串加入开始时间还是结束时间，true表示开始时间，false表示结束时间
     * @return 对应的日期时间的字符串，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeStr(int daysDiff, boolean joinStartTimeOrEndTime) {
        long now = System.currentTimeMillis();
        long dateTime = now + daysDiff * ONE_DAY;
        if (joinStartTimeOrEndTime) {
            return getDateStr(dateTime) + START_TIME;
        } else {
            return getDateStr(dateTime) + END_TIME;
        }
    }

    /**
     * 将日期对象转换为对应的日期的字符，格式为：yyyy-MM-dd
     *
     * @param date 日期对象
     * @return 对应的日期时间的字符，格式为：yyyy-MM-dd
     */
    public static String getDateStr(Date date) {
        return DATE_FORMAT_THREAD_LOCAL.get().format(date);
    }

    /**
     * 将时期时间戳转换为对应的日期的字符，格式为：yyyy-MM-dd
     *
     * @param dateTime 时期时间戳，单位为毫秒
     * @return 对应的日期的字符，格式为：yyyy-MM-dd
     */
    public static String getDateStr(long dateTime) {
        return DATE_FORMAT_THREAD_LOCAL.get().format(new Date(dateTime));
    }

    /**
     * 获取两个日期的时间差
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 两个日期的时间差
     */
    public static String getTimeDiff(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long days = diff / ONE_DAY;
        long hours = (diff - days * ONE_DAY) / ONE_HOUR;
        long minutes = (diff - days * ONE_DAY - hours * ONE_HOUR) / ONE_MINUTE;
        long seconds = diff / ONE_SECOND - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60;
        StringBuilder diffInfo = new StringBuilder();
        if (days > 0) {
            diffInfo.append(days).append("天");
        }
        if (hours > 0) {
            diffInfo.append(hours).append("小时");
        }
        if (minutes > 0) {
            diffInfo.append(minutes).append("分");
        }
        if (seconds > 0) {
            diffInfo.append(seconds).append("秒");
        }
        return diffInfo.toString();
    }

    /**
     * 获取今日日期，格式为：yyyy-MM-dd
     *
     * @return 对应的今日日期，格式为：yyyy-MM-dd
     */
    public static String getTodayStr() {
        Date today = new Date();
        return getDateStr(today);
    }

    /**
     * 获取昨日日期，格式为：yyyy-MM-dd
     *
     * @return 对应的昨日日期，格式为：yyyy-MM-dd
     */
    public static String getYesterdayStr() {
        Date yesterday = new Date(System.currentTimeMillis() - ONE_DAY);
        return getDateStr(yesterday);
    }

    public static void main(String[] args) {
        Date date = new Date();
        String dateStr = DateUtil.getDateStr(date);
        log.info("dateStr={}", dateStr);
        String dateTimeStr = DateUtil.getDateTimeStr(date);
        log.info("dateTimeStr={}", dateTimeStr);
        Date date1 = DateUtil.parseDateByDateStr(dateStr);
        log.info("date1={}", date1);
        Date date2 = DateUtil.parseDateByDateTimeStr(dateTimeStr);
        log.info("date2={}", date1);
        log.info("date2.equals(date1)={}", date2.equals(date1));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.HOUR, -2);
        calendar.add(Calendar.MINUTE, -3);
        Date yestoday = calendar.getTime();
        String yestodayStr = DateUtil.getDateTimeStr(yestoday);
        log.info("yestodayStr={}", yestodayStr);
        String diffInfo = DateUtil.getTimeDiff(date, yestoday);
        log.info("diffInfo={}", diffInfo);

    }

}