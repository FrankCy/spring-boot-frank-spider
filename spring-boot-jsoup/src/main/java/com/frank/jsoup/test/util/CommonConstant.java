package com.frank.jsoup.test.util;

import java.math.BigDecimal;

/**
 * 通用的常量类
 *
 * @author sky.lixin
 * @version CommonConstants.java, v 0.1 2020年06月01日 15:06 sky.lixin Exp $
 */
public class CommonConstant {

    /**
     * 默认的字符集，默认值为 UTF-8
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 跟踪的Id对应的Key
     */
    public static final String TRACE_ID = "traceId";

    /**
     * uid对应的Key
     */
    public static final String UID = "uid";

    /**
     * uri对应的Key
     */
    public static final String URI = "uri";

    /**
     * 是否为静态文件对应的Key
     */
    public static final String IS_STATIC = "is_static";

    /**
     * 是静态文件
     */
    public static final String STATIC = "1";

    /**
     * 不是静态文件
     */
    public static final String NOT_STATIC = "0";

    /**
     * 默认的开始时间的后缀
     */
    public static final String DEFAULT_BEGIN_TIME_SUFFIX = " 00:00:00";

    /**
     * 默认的结束时间的后缀
     */
    public static final String DEFAULT_END_TIME_SUFFIX = " 23:59:59";

    /**
     * 默认的多值数据字符串分割的符号，默认值为"，"
     */
    public static final String DEFAULT_SPLIT_CHAR = "，";

    /**
     * 默认的链接上下文分割的符号，默认值为"/"
     */
    public static final String DEFAULT_URL_CONTEXT_SPLIT_CHAR = "/";

    /**
     * 默认的链接的查询条件分割的符号，默认值为"?"
     */
    public static final String DEFAULT_URL_QUERY_SPLIT_CHAR = "?";

    /**
     * 默认的链接的请求参数的key和value分割的符号，默认值为"="
     */
    public static final String DEFAULT_URL_PARAM_KEY_VALUE_SPLIT_CHAR = "=";

    /**
     * 默认的链接的请求参数分割的符号，默认值为"&"
     */
    public static final String DEFAULT_URL_PARAM_SPLIT_CHAR = "&";

    /**
     * 默认的邀请人的字符串分割的符号，默认值为"#"
     */
    public static final String DEFAULT_INVITER_SPLIT_CHAR = "#";

    /**
     * 默认的保存图片的类型，默认值为: "png"
     */
    public static final String DEFAULT_SAVE_IMAGE_TYPE = "png";

    /**
     * 默认的启始查询页
     */
    public static final int DEFAULT_QUERY_PAGE_NO = 1;

    /**
     * 默认的查询分页记录数
     */
    public static final int DEFAULT_QUERY_PAGE_SIZE = 10;

    /**
     * 最大的查询分页记录数
     */
    public static final int MAX_QUERY_PAGE_SIZE = 100;

    /**
     * 随机的唯一码的长度
     */
    public static final int RANDOM_CODE_LENGTH = 8;

    /**
     * 随机的邀请码的长度
     */
    public static final int RANDOM_INVITE_CODE_LENGTH = 8;

    /**
     * 随机的临时授权码的长度
     */
    public static final int RANDOM_TEMP_CODE_LENGTH = 6;

    /**
     * 默认的ID字段对应的Key
     */
    public static final String DEFAULT_COLUMN_ID_KEY = "id";

    /**
     * 默认的创建时间字段对应的Key
     */
    public static final String DEFAULT_COLUMN_GMT_CREATE_KEY = "gmtCreate";

    /**
     * 默认的最后修改时间字段对应的Key
     */
    public static final String DEFAULT_COLUMN_GMT_MODIFIED_KEY = "gmtModified";

    /**
     * 默认的状态字段对应的Key
     */
    public static final String DEFAULT_COLUMN_STATUS_KEY = "status";

    /**
     * 响应的内容类型
     */
    public static final String RESPONSE_CONTENT_TYPE = "content-type";

    /**
     * 默认的内容类型
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/json; charset=" + DEFAULT_ENCODING;

    /**
     * 默认的根节点的主键
     */
    public static final long DEFAULT_ROOT_ID = 0L;

    /**
     * 默认的用户的主键
     */
    public static final long DEFAULT_USER_ID = 0L;

    /**
     * 默认的顺序
     */
    public static final int DEFAULT_ORDER = 0;

    /**
     * spring开发工具是否可以重启的Key
     */
    public static final String SPRING_DEVTOOLS_RESTART_ENABLED_KEY = "spring.devtools.restart.enabled";

    /**
     * 默认的用户的昵称
     */
    public static final String DEFAULT_USER_NICK = "系统初始化";

    /**
     * 默认的临时验证码的超时时间，单位为：毫秒，默认值为：300000毫秒
     */
    public static final Long DEFAULT_SMS_CODE_TIME_OUT = 5L * 60 * 1000;

    /**
     * 默认的登录过期时间，单位为：秒，默认有效期为3天
     */
    public static final int DEFAULT_LOGIN_EXPIRED_TIME = 3 * 24 * 60 * 60;

    /**
     * 一元对应多少分
     */
    public static final BigDecimal ONE_YUAN = new BigDecimal("100");

    /**
     * 默认的转换为元保留的小数点后有效位数
     */
    public static final int DEFAULT_MONEY_YUAN_SCALE = 2;

    /**
     * 默认的转换为元保留的小数的取舍模式，默认为四舍五入
     */
    public static final int DEFAULT_MONEY_YUAN_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    /**
     * 默认的总数
     */
    public static final long DEFAULT_TOTAL = 0L;

    /**
     * 默认的金额，单位为：分
     */
    public static final long DEFAULT_AMOUNT = 0L;

    /**
     * 默认的金额的展示，单位为：元
     */
    public static final String DEFAULT_AMOUNT_SHOW = "0.00";

    /**
     * 默认的充值金额，单位为：分
     */
    public static final int DEFAULT_INVEST_AMOUNT = 0;

    /**
     * 默认查询页
     */
    public static final int DEFAULT_QUERY_NO = 1;
}