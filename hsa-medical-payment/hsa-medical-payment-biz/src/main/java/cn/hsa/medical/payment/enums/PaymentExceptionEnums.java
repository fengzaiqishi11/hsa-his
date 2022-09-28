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
    PAYMENT_CHARGE(Constants.PAYMENT.PAY_CHARGE, "诊间支付支付接口"),
    PAYMENT_CHARGE_QUERY(Constants.PAYMENT.PAY_CHARGE_QUERY, "诊间支付支付查询接口"),
    PAYMENT_REFUND(Constants.PAYMENT.PAY_REFUND, "诊间支付退款接口"),
    PAYMENT_REFUND_QUERY(Constants.PAYMENT.PAY_REFUND_QUERY, "诊间支付退款查询接口"),
    PAYMENT_REVOKE(Constants.PAYMENT.PAY_REVOKE, "诊间支付撤销接口"),
    PAYMENT_BILL(Constants.PAYMENT.PAY_BILL, "诊间支付对总账接口"),
    PAYMENT_BILL_DETAIL(Constants.PAYMENT.PAY_BILL_DETAIL, "诊间支付对明细接口");

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
