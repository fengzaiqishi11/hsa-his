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
 * @class_name: PaymentBillRequest
 * @Description: 诊间支付对账接口参数封装
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/9/13 09:13
 * @Company: 创智和宇
 **/
@Service("PaymentBill")
public class PaymentBillRequest<T> extends PaymentInterCommonTemplate implements BasePaymentInterf<T> {
    @Override
    public PaymentInterfParamDTO initParam(T param) {
        Map map = (Map) param;
        checkParam(map);
        PaymentSettleDTO paymentSettleDTO = (PaymentSettleDTO)map.get("paymentSettleDTO");
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paymentSettleDTO);
        map.put("input", inputMap);
        map.put("orgId",paymentSettleDTO.getHospCode()); // 医院编码
        map.put("billBeginDate",paymentSettleDTO.getStartTime()); // 账单开始日期
        map.put("billEndDate",paymentSettleDTO.getEndTime()); // 账单结束日期
        map.put("pageNum",paymentSettleDTO.getPageNo()); // 页码
        map.put("pageSize",paymentSettleDTO.getPageSize()); // 每页显示数量
        map.put("infno", "");
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkParam(Map param) {
        return true;
    }
}
