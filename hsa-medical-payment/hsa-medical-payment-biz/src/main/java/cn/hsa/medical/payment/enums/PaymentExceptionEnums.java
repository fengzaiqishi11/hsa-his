package cn.hsa.medical.payment.enums;

import cn.hsa.util.Constants;

/**
 * @Package_name: cn.hsa.medical.payment.enums
 * @class_name: PaymentExceptionEnums
 * @Description: 支付异常枚举类
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
public enum PaymentExceptionEnums {
    INSUR_BASE_INFO(Constants.SF.S, "人员信息获取");

    private final String code;
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getValue(String code) {
        for (PaymentExceptionEnums ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getDesc();
            }
        }
        return null;
    }

    PaymentExceptionEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PaymentExceptionEnums getEnum(String code) {
        for (PaymentExceptionEnums sign : PaymentExceptionEnums.values()) {
            if (sign.getCode().equals(code)) {
                return sign;
            }
        }
        return null;
    }
}
