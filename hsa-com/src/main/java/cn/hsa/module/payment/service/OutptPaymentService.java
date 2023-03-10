package cn.hsa.module.payment.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.service
 * @Class_name: OutptPaymentService
 * @Describe(描述):诊间支付Service接口
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/08/30 19:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-payment")
public interface OutptPaymentService {

    /**@Method checkOutptPhonePayAuthority
     * @Author liuliyun
     * @Description 诊间支付权限校验
     * @Date 2022/08/30 19:17
     * @Param [map]
     * @return Boolean
     **/
    WrapperResponse<Boolean> checkOutptPhonePayAuthority(Map param);

    /**@Method checkOutptPrescriptionPayAuthority
     * @Author liuliyun
     * @Description 诊间支付(扫处方二维码)权限校验
     * @Date 2022/08/30 19:26
     * @Param [map]
     * @return Boolean
     **/
    Boolean checkOutptPrescriptionPayAuthority(Map param);

    /**@Method updatePaymentRefund
     * @Author liuliyun
     * @Description 诊间支付退款申请接口
     * @Date 2022/09/14 14:03
     * @Param [map]
     * @return  Map<String,Object>
     **/
    Map<String,Object> updatePaymentRefund(Map param);

    /**@Method updatePaymentRefundQuery
     * @Author liuliyun
     * @Description 诊间支付退款查询接口
     * @Date 2022/09/14 14:06
     * @Param [map]
     * @return  Map<String,Object>
     **/
    Map<String,Object> updatePaymentRefundQuery(Map param);

    /**@Menthod updatePaymentSettle
     * @description 诊间支付结算接口
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 11:33
     * @return map
     */
    Map<String,Object> updatePaymentSettle(Map<String, Object> param);

    /**@Method updatePaymentSettleQuery
     * @Author liuliyun
     * @Description 诊间支付结算查询接口
     * @Date 2022/09/14 14:07
     * @Param [map]
     * @return  Map<String,Object>
     **/
    Map<String,Object> updatePaymentSettleQuery(Map param);

    /**@Method updatePaymentRevoke
     * @Author liuliyun
     * @Description 诊间支付撤销接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    Map<String,Object> updatePaymentRevoke(Map param);

    /**@Method updatePaymentBill
     * @Author liuliyun
     * @Description 诊间支付对账查询（总账）接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    WrapperResponse<Map<String,Object>> updatePaymentBill(Map param);


    /**@Method updatePaymentBillDetail
     * @Author liuliyun
     * @Description 诊间支付对账查询（明细）接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    WrapperResponse<Map<String,Object>> updatePaymentBillDetail(Map param);


}
