package cn.hsa.medical.payment.interf;

import cn.hsa.medical.payment.factory.BasePaymentInterf;
import cn.hsa.medical.payment.factory.PaymentInterCommonTemplate;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.util.Constants;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.interf
 * @class_name: PaymentRefundRequest
 * @Description: 诊间支付退款接口参数封装
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/9/13 11:24
 * @Company: 创智和宇
 **/
@Service("payment" + Constants.PAYMENT.PAY_REFUND)
public class PaymentRefundRequest<T> extends PaymentInterCommonTemplate implements BasePaymentInterf<T> {
    @Override
    public PaymentInterfParamDTO initParam(T param) {
        Map map = (Map) param;
        checkParam(map);
        PaymentSettleDTO paymentSettleDTO = (PaymentSettleDTO)map.get("paymentSettleDTO");
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paymentSettleDTO);
        map.put("input", inputMap);
        map.put("orgId",paymentSettleDTO.getHospCode()); // 医院编码
        map.put("orgOrderId",paymentSettleDTO.getOldSettleId()); // 机构原支付订单号
        map.put("orgRefundId",paymentSettleDTO.getSettleId());  //  机构退款订单号
        map.put("refundFee",paymentSettleDTO.getPaymentPrice()); // 退款金额
        map.put("refundReason","门诊退费"); // 退款理由
        map.put("infno", "");
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkParam(Map param) {
        return true;
    }
}
