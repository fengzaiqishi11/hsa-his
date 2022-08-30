package cn.hsa.medical.payment.factory;

import org.springframework.stereotype.Component;

/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: abstractPaymentTemplate
 * @Description: 主扫与被扫抽象(可以不用抽象,后续可改)模板类:处理相同接口
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
@Component
public abstract class abstractPaymentTemplate {

    /**
     * his结算方法,写好调用,提供模板给子类使用
     */
    public void hisSettle(){

    }

    /**
     * 支付平台结算接口,写好调用,提供模板给子类使用
     */
    public void paymentSettle(){

    }
    /**
     * his退款方法,写好调用,提供模板给子类使用
     */
    public void hisRefund(){

    }

    /**
     * 支付平台退款方法接口,写好调用,提供模板给子类使用
     */
    public void paymentRefund(){

    }
}
