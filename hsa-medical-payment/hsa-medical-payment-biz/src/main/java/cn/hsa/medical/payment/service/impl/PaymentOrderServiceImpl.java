package cn.hsa.medical.payment.service.impl;

import cn.hsa.base.OpenAdditionalService;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.payment.bo.PaymentOrderBO;
import cn.hsa.module.payment.entity.PaymentOrderDO;
import cn.hsa.module.payment.service.OutptPaymentService;
import cn.hsa.module.payment.service.PaymentOrderService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.service.impl
 * @Class_name: PaymentOrderServiceImpl
 * @Describe(描述):诊间支付订单服务ServiceImpl
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/09/05 13:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/payment/paymentOrder")
@Service("paymentOrderService_provider")
public class PaymentOrderServiceImpl extends HsafService implements PaymentOrderService {
    @Resource
    PaymentOrderBO paymentOrderBO;

    @Override
    public WrapperResponse<PaymentOrderDO> queryById(String id) {
        return null;
    }

    @Override
    public WrapperResponse<PageDTO> queryAllPaymentOrderInfo(PaymentOrderDO paymentOrderDO) {
        return null;
    }

    @Override
    public WrapperResponse insert(PaymentOrderDO paymentOrderDO) {
        return null;
    }

    @Override
    public WrapperResponse updatePaymentOrder(Map param) {
        String hospCode = MapUtils.get(param,"hospCode");
        PaymentOrderDO paymentOrderDO = MapUtils.get(param,"paymentOrderDO");
        paymentOrderDO.setHospCode(hospCode);
        return WrapperResponse.success(paymentOrderBO.updatePaymentOrder(paymentOrderDO));
    }

    @Override
    public WrapperResponse deleteById(String id) {
        return null;
    }
}
