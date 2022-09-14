package cn.hsa.medical.payment.interf;

import cn.hsa.medical.payment.factory.BasePaymentInterf;
import cn.hsa.medical.payment.factory.PaymentInterCommonTemplate;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.interf
 * @class_name: PaymentOrderQueryRequest
 * @Description: 诊间支付结算订单查询接口参数封装
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/9/13 11:34
 * @Company: 创智和宇
 **/
@Service("paymentOrderQuery")
public class PaymentOrderQueryRequest<T> extends PaymentInterCommonTemplate implements BasePaymentInterf<T> {
    @Override
    public PaymentInterfParamDTO initParam(T param) {
        Map map = (Map) param;
        checkParam(map);
        PaymentSettleDTO paymentSettleDTO = (PaymentSettleDTO)map.get("paymentSettleDTO");
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paymentSettleDTO);
        map.put("input", inputMap);
        map.put("orgId",paymentSettleDTO.getHospCode()); // 医院编码
        map.put("orgOrderId",paymentSettleDTO.getSettleId()); // 机构支付订单号
        map.put("infno", "");
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkParam(Map param) {
        return true;
    }
}
