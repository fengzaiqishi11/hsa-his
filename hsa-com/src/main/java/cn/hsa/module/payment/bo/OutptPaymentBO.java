package cn.hsa.module.payment.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.payment.bo
 * @Class_name: OutptPaymentBO
 * @Describe:
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022-09-14 14:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptPaymentBO {

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

    /**@Method updatePaymentSettle
     * @Author liuliyun
     * @Description 诊间支付结算接口
     * @Date 2022/09/14 14:07
     * @Param [map]
     * @return  Map<String,Object>
     **/
    Map<String,Object> updatePaymentSettle(Map param);

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
    Map<String,Object> updatePaymentBill(Map param);


    /**@Method updatePaymentBillDetail
     * @Author liuliyun
     * @Description 诊间支付对账查询（明细）接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    Map<String,Object> updatePaymentBillDetail(Map param);
}
