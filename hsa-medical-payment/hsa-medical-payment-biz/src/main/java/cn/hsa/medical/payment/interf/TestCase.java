package cn.hsa.medical.payment.interf;

import cn.hsa.medical.payment.factory.BasePaymentInterf;
import cn.hsa.medical.payment.factory.PaymentInterCommonTemplate;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: BasePaymentInterf
 * @Description: 支付接口参数封装示例案例
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
@Service("paymentCase")
public class TestCase<T> extends PaymentInterCommonTemplate implements BasePaymentInterf<T> {


    @Override
    public PaymentInterfParamDTO initParam(T param) {
        return getInsurCommonParam(new HashMap());
    }

    @Override
    public boolean checkParam(Map param) {
        return false;
    }
}
