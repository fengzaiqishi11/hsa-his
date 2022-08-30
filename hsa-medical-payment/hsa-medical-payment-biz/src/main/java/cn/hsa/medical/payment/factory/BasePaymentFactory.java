package cn.hsa.medical.payment.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: BasePaymentFactory
 * @Description: 对接支付接口顶层 策略工厂
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
@Component
public class BasePaymentFactory {

    /**最大初始化容量*/
    private static final  int MAX_INITIAL_CAPACITY =  20;

    /**工厂map实例
     * @Autowired 注入单例bean
     */
    @Autowired
    private static Map<String,BasePaymentInterf> map = new ConcurrentHashMap<String, BasePaymentInterf>(MAX_INITIAL_CAPACITY);

    /**获取唯一实例方法*/
    public BasePaymentInterf getBasePaymentInterf(String interfName) {
        return map.get(interfName);
    }

}
