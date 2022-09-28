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
 * @class_name: PaymentSettleRequest
 * @Description: 支付接口参数封装
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/9/13 09:42
 * @Company: 创智和宇
 **/
@Service("payment" + Constants.PAYMENT.PAY_CHARGE)
public class PaymentSettleRequest<T> extends PaymentInterCommonTemplate implements BasePaymentInterf<T> {
    @Override
    public PaymentInterfParamDTO initParam(T param) {
        Map map = (Map) param;
        checkParam(map);
        PaymentSettleDTO paymentSettleDTO = (PaymentSettleDTO)map.get("paymentSettleDTO");
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paymentSettleDTO);
        map.put("input", inputMap);
        map.put("orgId",paymentSettleDTO.getHospCode()); // 医院编码
        map.put("orgOrderId",paymentSettleDTO.getSettleId()); // 结算单号
        map.put("body","商品描述");  // 商品描述
        map.put("totalFee",paymentSettleDTO.getPaymentPrice()); // 订单金额
        map.put("authCode",paymentSettleDTO.getAuthCode()); // 付款码
        map.put("payType",paymentSettleDTO.getPayCode()); // 付款码类型 1：wx，2：alipay
        map.put("infno", "");
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkParam(Map param) {
        return true;
    }
}
