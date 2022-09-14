package cn.hsa.medical.payment.factory;

import cn.hsa.medical.payment.bo.impl.PaymentTransferBoImpl;
import cn.hsa.medical.payment.enums.PaymentExceptionEnums;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

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
    @Resource
    private BasePaymentFactory basePaymentFactory;

    @Resource
    private PaymentTransferBoImpl paymentTransferBo;

    /**
     * his结算方法,写好调用,提供模板给子类使用
     */
    public void hisSettle(){

    }

    /**
     * 支付平台结算接口,写好调用,提供模板给子类使用
     */
    public void paymentSettle(Map param){
        BasePaymentInterf paymentSettleRequest= basePaymentFactory.getBasePaymentInterf("结算");
        PaymentInterfParamDTO paymentInterfParamDTO=paymentSettleRequest.initParam(param);
        paymentTransferBo.transferPayment(PaymentExceptionEnums.INSUR_BASE_INFO,paymentInterfParamDTO);
    }
    /**
     * his退款方法,写好调用,提供模板给子类使用
     */
    public void paymentRefundQuery(Map param){
        BasePaymentInterf paymentRefundQueryRequest= basePaymentFactory.getBasePaymentInterf("退款查询");
        PaymentInterfParamDTO paymentInterfParamDTO=paymentRefundQueryRequest.initParam(param);
        paymentTransferBo.transferPayment(PaymentExceptionEnums.INSUR_BASE_INFO,paymentInterfParamDTO);
    }

    /**
     * 支付平台退款方法接口,写好调用,提供模板给子类使用
     */
    public void paymentRefund(Map param){
        BasePaymentInterf paymentSettleRequest= basePaymentFactory.getBasePaymentInterf("退款");
        PaymentInterfParamDTO paymentInterfParamDTO=paymentSettleRequest.initParam(param);
        paymentTransferBo.transferPayment(PaymentExceptionEnums.INSUR_BASE_INFO,paymentInterfParamDTO);
    }
}
