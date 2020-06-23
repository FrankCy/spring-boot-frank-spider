package com.frank.jsoup.test.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务层异常
 *
 * @author sky.lixin
 * @version ServiceException.java, v 0.1 2020年06月01日 15:06 sky.lixin Exp $
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 6383991280586120821L;

    /**
     * 错误的编码
     */
    private int errCode;

    /**
     * 错误的信息
     */
    private String errMsg;

    /**
     * 默认的构造函数
     */
    public ServiceException() {
        super();
    }

    /**
     * 带参数的构造函数
     *
     * @param errCode 错误的编码
     * @param errMsg  错误的信息
     */
    public ServiceException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * 带参数的构造函数
     *
     * @param errCode 错误的编码
     * @param errMsg  错误的信息
     * @param e       异常对象
     */
    public ServiceException(int errCode, String errMsg, Throwable e) {
        super(errMsg, e);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

}
