package cn.hsa.medical.payment.bo.impl;


import org.springframework.stereotype.Component;

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

    /**支付平台调用方法与日志记录*/
    public Map<String, Object> transferPayment(){
        try {
            /*调用传输*/
        }finally {
            /*日志记录*/
        }
        return null;
    }
}
