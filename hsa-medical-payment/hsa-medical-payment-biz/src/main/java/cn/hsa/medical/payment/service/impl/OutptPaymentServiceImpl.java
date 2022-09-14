package cn.hsa.medical.payment.service.impl;

import cn.hsa.base.OpenAdditionalService;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.bo.PaymentSettleBO;
import cn.hsa.module.payment.service.OutptPaymentService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.fees.service.impl
 * @Class_name: OutptPaymentServiceImpl
 * @Describe(描述):诊间支付ServiceImpl
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/08/30 19:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/outpt/OutptPayment")
@Service("outptPaymentService_provider")
public class OutptPaymentServiceImpl extends HsafService implements OutptPaymentService {
    @Resource
    PaymentSettleBO paymentSettleBO;

    @OpenAdditionalService(desc = "诊间支付被扫",addEnable = true,orderTypeCode = Constants.ZZFW.ZJZF_HJSF)
    @Override
    public WrapperResponse checkOutptPhonePayAuthority(Map param) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(param, "outptVisitDTO");//获取个人信息
        OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");//获取结算信息
        List<OutptPayDO> outptPayDOList = MapUtils.get(param, "outptPayDOList");//支付方式信息
        paymentSettleBO.saveSettleInfo(outptVisitDTO,outptSettleDTO,outptPayDOList);
        return WrapperResponse.success(true);
    }

    @OpenAdditionalService(desc = "诊间支付主扫",addEnable = true,orderTypeCode = Constants.ZZFW.ZJZF_HJSF)
    @Override
    public Boolean checkOutptPrescriptionPayAuthority(Map param) {
        return true;
    }



}
