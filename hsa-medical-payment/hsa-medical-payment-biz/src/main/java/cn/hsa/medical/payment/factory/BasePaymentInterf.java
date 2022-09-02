package cn.hsa.medical.payment.factory;

import cn.hsa.module.payment.dto.PaymentInterfParamDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: BasePaymentInterf
 * @Description: 支付接口实现策略顶层
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
public interface BasePaymentInterf<T> {

    /**
     * 初始化参数
     * @param param
     * @return T
     * @method initRequest
     **/
    PaymentInterfParamDTO initParam(T param);

    /**
     * 参数校验
     * @param param
     * @return boolean
     * @method checkRequest
     **/
    boolean checkParam(Map param);

}
