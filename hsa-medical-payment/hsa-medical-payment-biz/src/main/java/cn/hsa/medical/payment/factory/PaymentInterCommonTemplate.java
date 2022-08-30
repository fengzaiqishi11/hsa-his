package cn.hsa.medical.payment.factory;



import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: PaymentInterCommonParam
 * @Description: 支付接口调用支付平台公共参数封装模板类
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
@Component
public class PaymentInterCommonTemplate {


    /**封装公共参数方法:模板方法,final不允许子类修改*/
    public final PaymentInterfParamDTO getInsurCommonParam(Map map) {

        /*todo 可以在此处直接调用支付业务,但是需要将回参写会前一个方法,进行其他业务处理*/

        return null;
    }
}
