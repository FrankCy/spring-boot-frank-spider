package com.frank.jsoup.test.constant;

/**
 * 通用状态对应的枚举对象
 *
 * @author sky.lixin
 * @version CommonStatusEnum.java, v 0.1 2020年06月01日 15:06 sky.lixin Exp $
 */
public enum CommonStatusEnum {

    /**
     * 全部
     */
    ALL {
        @Override
        public Integer getCode() {
            return -1;
        }

        @Override
        public String getDesc() {
            return "全部";
        }
    },
    /**
     * 无效
     */
    INVALID {
        @Override
        public Integer getCode() {
            return 0;
        }

        @Override
        public String getDesc() {
            return "无效";
        }
    },
    /**
     * 有效
     */
    VALID {
        @Override
        public Integer getCode() {
            return 1;
        }

        @Override
        public String getDesc() {
            return "有效";
        }
    };

    /**
     * 获取编码
     *
     * @return 对应的编码
     */
    public abstract Integer getCode();

    /**
     * 获取描述
     *
     * @return 对应的描述
     */
    public abstract String getDesc();

    /**
     * 根据指定的编码获取对应的通用状态的枚举对象，未找到则返回null
     *
     * @param code 指定的编码
     * @return 对应的通用状态的枚举对象，未找到则返回null
     */
    public static CommonStatusEnum getByCode(Integer code) {
        for (CommonStatusEnum commonStatusEnum : CommonStatusEnum.values()) {
            if (commonStatusEnum.getCode().equals(code)) {
                return commonStatusEnum;
            }
        }
        return null;
    }
}