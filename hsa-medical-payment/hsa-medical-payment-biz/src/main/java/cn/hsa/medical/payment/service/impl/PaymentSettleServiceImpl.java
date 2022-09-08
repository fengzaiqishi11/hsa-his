package cn.hsa.medical.payment.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.payment.bo.PaymentSettleBO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import cn.hsa.module.payment.service.PaymentSettleService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.service.impl
 * @Class_name: PaymentSettleServiceImpl
 * @Describe(描述):诊间支付结算ServiceImpl
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/09/05 10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/payment/paymentSettle")
@Service("paymentSettleServiceImpl_provider")
public class PaymentSettleServiceImpl extends HsafService implements PaymentSettleService {
    @Resource
    PaymentSettleBO paymentSettleBO;
    @Override
    public PaymentSettleDO queryById(String id) {
        return null;
    }

    @Override
    public Page<PaymentSettleDO> queryByPage(PaymentSettleDO paymentSettle, PageRequest pageRequest) {
        return null;
    }

    @Override
    public PaymentSettleDO insert(PaymentSettleDO paymentSettle) {
        return null;
    }

    /**@Menthod updatePaymentSettleInfo
     * @description 根据医院编码、就诊id、结算id 更新诊间支付信息
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:37
     * @return PaymentSettleDTO
     */
    @Override
    public Boolean updatePaymentSettleInfo(Map param) {
        String hospCode = MapUtils.get(param,"hospCode");
        PaymentSettleDO paymentSettleDO = MapUtils.get(param,"PaymentSettleDO");
        paymentSettleDO.setHospCode(hospCode);
        return paymentSettleBO.update(paymentSettleDO);
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    /**@Menthod quyeryPaymentInfoByCondition
     * @description 根据医院编码、就诊id、结算id 查询诊间支付结算信息
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:37
     * @return PaymentSettleDTO
     */
    @Override
    public PaymentSettleDTO quyeryPaymentInfoByCondition(Map<String, Object> param) {
        PaymentSettleDTO paymentSettleDTO = MapUtils.get(param,"paymentSettleDTO");
        return paymentSettleBO.quyeryPaymentInfoByCondition(paymentSettleDTO);
    }

    /**@Menthod updatePaymentSettleStatus
     * @description 调用支付平台支付
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 11:47
     * @return Map
     */
    @Override
    public Map updatePaymentSettleStatus(Map<String, Object> param) {
        /*todo 支付接口入参*/
      return paymentSettleBO.updatePaymentSettleStatus(param);
    }
}
