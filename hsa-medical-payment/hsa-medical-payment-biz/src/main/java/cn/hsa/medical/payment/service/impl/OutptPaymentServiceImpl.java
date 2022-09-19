package cn.hsa.medical.payment.service.impl;

import cn.hsa.base.OpenAdditionalService;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.bo.OutptPaymentBO;
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
 * @Describe(描述): 诊间支付统一入口
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
    private PaymentSettleBO paymentSettleBO;

    @Resource
    private OutptPaymentBO outptPaymentBO;

    @OpenAdditionalService(desc = "诊间支付被扫",addEnable = true,orderTypeCode = Constants.ZZFW.ZJZF_HJSF)
    @Override
    public WrapperResponse<Boolean> checkOutptPhonePayAuthority(Map param) {
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


    /**@Method updatePaymentRefund
     * @Author liuliyun
     * @Description 诊间支付退款申请接口
     * @Date 2022/09/14 14:03
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentRefund(Map param) {
        return outptPaymentBO.updatePaymentRefund(param);
    }

    /**@Method updatePaymentRefundQuery
     * @Author liuliyun
     * @Description 诊间支付退款查询接口
     * @Date 2022/09/14 14:06
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentRefundQuery(Map param) {
        return outptPaymentBO.updatePaymentRefundQuery(param);
    }

    /**@Menthod updatePaymentSettle
     * @description 诊间支付结算接口
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/14 14:31
     * @return Map<String,Object>
     */
    @Override
    public Map updatePaymentSettle(Map<String, Object> param) {
        return outptPaymentBO.updatePaymentSettle(param);
    }

    /**@Method updatePaymentSettleQuery
     * @Author liuliyun
     * @Description 诊间支付结算查询接口
     * @Date 2022/09/14 14:07
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentSettleQuery(Map param) {
        return outptPaymentBO.updatePaymentSettleQuery(param);
    }

    /**@Method updatePaymentRevoke
     * @Author liuliyun
     * @Description 诊间支付撤销接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentRevoke(Map param) {
        return outptPaymentBO.updatePaymentRevoke(param);
    }

    /**@Method updatePaymentBill
     * @Author liuliyun
     * @Description 诊间支付对账查询（总账）接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public WrapperResponse<Map<String,Object>> updatePaymentBill(Map param) {
        return WrapperResponse.success(outptPaymentBO.updatePaymentBill(param));
    }

    /**@Method updatePaymentBillDetail
     * @Author liuliyun
     * @Description 诊间支付对账查询（明细）接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public WrapperResponse<Map<String,Object>> updatePaymentBillDetail(Map param) {
        return WrapperResponse.success(outptPaymentBO.updatePaymentBillDetail(param));
    }


}
