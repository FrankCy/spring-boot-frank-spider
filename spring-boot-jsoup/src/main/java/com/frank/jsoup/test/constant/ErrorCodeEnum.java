package com.frank.jsoup.test.constant;

/**
 * 对外返回的错误码
 *
 * @author sky.lixin
 * @version ErrorCodeEnum.java, v 0.1 2020年06月01日 15:06 sky.lixin Exp $
 */
public enum ErrorCodeEnum {

    /**
     * 系统繁忙
     */
    SYSTEM_BUSY {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return -1;
        }

        @Override
        public String getErrMsg() {
            return "系统繁忙";
        }
    },
    /**
     * 成功
     */
    OK {
        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public int getErrCode() {
            return 0;
        }

        @Override
        public String getErrMsg() {
            return "成功";
        }
    },
    /**
     * URI不存在
     */
    URI_NOT_EXIST {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 404;
        }

        @Override
        public String getErrMsg() {
            return "URI不存在";
        }
    },
    /**
     * 禁止访问
     */
    ASSCESS_DEINED {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 503;
        }

        @Override
        public String getErrMsg() {
            return "禁止访问";
        }
    },
    /**
     * 参数需要多媒体类型
     */
    PARMS_NEED_MULTIPART {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1001;
        }

        @Override
        public String getErrMsg() {
            return "参数需要多媒体类型";
        }
    },
    /**
     * 参数需要JSON格式
     */
    PARMS_NEED_JSON {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1002;
        }

        @Override
        public String getErrMsg() {
            return "参数需要JSON格式";
        }
    },
    /**
     * http请求需要GET方式
     */
    NEED_HTTP_GET {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1003;
        }

        @Override
        public String getErrMsg() {
            return "http请求需要GET方式";
        }
    },
    /**
     * http请求需要POST方式
     */
    NEED_HTTP_POST {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1004;
        }

        @Override
        public String getErrMsg() {
            return "http请求需要POST方式";
        }
    },
    /**
     * content-type需要json形式
     */
    NEED_CONTENT_TYPE_JSON {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1005;
        }

        @Override
        public String getErrMsg() {
            return "content-type需要json形式";
        }
    },
    /**
     * 无效的content-type
     */
    INVALID_CONTENT_TYPE {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1006;
        }

        @Override
        public String getErrMsg() {
            return "无效的content-type";
        }
    },
    /**
     * 无效的请求参数
     */
    INVALID_REQUEST_PARAMS {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1007;
        }

        @Override
        public String getErrMsg() {
            return "无效的请求参数";
        }
    },
    /**
     * 服务内部处理错误
     */
    SERVICE_ERROR {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1008;
        }

        @Override
        public String getErrMsg() {
            return "服务内部处理错误";
        }
    },
    /**
     * 未登录
     */
    NO_LOGIN {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1009;
        }

        @Override
        public String getErrMsg() {
            return "未登录";
        }
    },
    /**
     * 没有权限
     */
    NO_PERMISION {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public int getErrCode() {
            return 1010;
        }

        @Override
        public String getErrMsg() {
            return "没有权限";
        }
    };

    /**
     * 获取是否成功，true表示成功，false表示失败
     *
     * @return 对应的是否成功，true表示成功，false表示失败
     */
    public abstract boolean isSuccess();

    /**
     * 获取错误编码
     *
     * @return 对应的错误编码
     */
    public abstract int getErrCode();

    /**
     * 获取错误信息
     *
     * @return 对应的错误信息
     */
    public abstract String getErrMsg();

    /**
     * 根据指定的错误编码获取对应的错误编码的枚举对象，未找到则返回null
     *
     * @param errCode 指定的错误编码
     * @return 对应的错误编码的枚举对象，未找到则返回null
     */
    public static ErrorCodeEnum getByCode(int errCode) {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if (errorCodeEnum.getErrCode() == errCode) {
                return errorCodeEnum;
            }
        }
        return null;
    }

}
