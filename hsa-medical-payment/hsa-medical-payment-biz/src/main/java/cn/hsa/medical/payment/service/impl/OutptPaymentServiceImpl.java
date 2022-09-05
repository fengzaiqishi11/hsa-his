package cn.hsa.medical.payment.service.impl;

import cn.hsa.base.OpenAdditionalService;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.payment.service.OutptPaymentService;
import cn.hsa.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @OpenAdditionalService(desc = "诊间支付被扫",addEnable = true,orderTypeCode = Constants.ZZFW.ZJZF_HJSF)
    @Override
    public Boolean checkOutptPhonePayAuthority(Map param) {
        return true;
    }

    @OpenAdditionalService(desc = "诊间支付主扫",addEnable = true,orderTypeCode = Constants.ZZFW.ZJZF_HJSF)
    @Override
    public Boolean checkOutptPrescriptionPayAuthority(Map param) {
        return true;
    }



}
