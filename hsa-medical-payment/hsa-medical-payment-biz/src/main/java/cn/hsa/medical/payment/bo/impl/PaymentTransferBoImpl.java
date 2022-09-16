package cn.hsa.medical.payment.bo.impl;


import cn.hsa.medical.payment.enums.PaymentExceptionEnums;
import cn.hsa.module.payment.bo.PaymentExecuteLogBO;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: BasePaymentFactory
 * @Description: his对接支付平台传输层接口
 * todo 可采用异步传输方式,需要讨论
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
@Component
public class PaymentTransferBoImpl {
    @Resource
    PaymentExecuteLogBO executeLogBO;

    /**支付平台调用方法与日志记录*/
    public Map<String, Object> transferPayment(PaymentExceptionEnums paymentExceptionEnums, PaymentInterfParamDTO paymentInterfParamDTO){
        Map param =new HashMap();
        try {
            /*调用传输*/
        }finally {
            executeLogBO.insert(param);  // 记录诊间支付日志
        }
        return null;
    }
}
