package cn.hsa.medical.payment.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.medical.payment.enums.PaymentExceptionEnums;
import cn.hsa.medical.payment.factory.BasePaymentFactory;
import cn.hsa.medical.payment.factory.BasePaymentInterf;
import cn.hsa.module.payment.bo.OutptPaymentBO;
import cn.hsa.module.payment.dao.PaymentSettleDAO;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.bo.impl
 * @class_name: OutptPaymentBOImpl
 * @Description: 诊间支付统一传输层接口
 * @Author: liyun.liu
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/09/14 14:16
 * @Company: 创智和宇
 **/
@Component
public class OutptPaymentBOImpl implements OutptPaymentBO {
    @Resource
    private PaymentTransferBoImpl paymentTransferBo;

    @Resource
    private BasePaymentFactory basePaymentFactory;

    @Resource
    private PaymentSettleDAO paymentSettleDAO;

    /**@Method updatePaymentRefund
     * @Author liuliyun
     * @Description 诊间支付退款申请接口
     * @Date 2022/09/14 14:03
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentRefund(Map param) {
        param.put("refundStatus","0");
        param.put("failCause", "");
//        BasePaymentInterf paymentSettleRequest= basePaymentFactory.getBasePaymentInterf("payment" + PaymentExceptionEnums.PAYMENT_REFUND.getCode());
//        PaymentInterfParamDTO paymentInterfParamDTO=paymentSettleRequest.initParam(param);
//        return paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_REFUND,paymentInterfParamDTO);
        return param;
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
//        BasePaymentInterf paymentRefundQueryRequest= basePaymentFactory.getBasePaymentInterf("payment" + PaymentExceptionEnums.PAYMENT_REFUND_QUERY.getCode());
//        PaymentInterfParamDTO paymentInterfParamDTO=paymentRefundQueryRequest.initParam(param);
//        return paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_REFUND_QUERY,paymentInterfParamDTO);
        param.put("refundStatus","1");
        param.put("failCause", "");
        return param;
    }


    /**@Method updatePaymentSettle
     * @Author liuliyun
     * @Description 诊间支付结算接口
     * @Date 2022/09/14 14:07
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentSettle(Map param) {
           param.put("orderStatus",Constants.ZJ_PAY_ZFZT.ZFZ);
           param.put("payTime", DateUtils.getNow());
           param.put("orderId","123");
           param.put("totalFee", 60);
           param.put("payType", "1");
           param.put("failCause", "");
//        BasePaymentInterf paymentSettleRequest= basePaymentFactory.getBasePaymentInterf("payment" + PaymentExceptionEnums.PAYMENT_CHARGE.getCode());
//        PaymentInterfParamDTO paymentInterfParamDTO=paymentSettleRequest.initParam(param);
//        return paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_CHARGE,paymentInterfParamDTO);
         return param;
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
//        BasePaymentInterf paymentSettleRequest= basePaymentFactory.getBasePaymentInterf("payment" + PaymentExceptionEnums.PAYMENT_CHARGE_QUERY.getCode());
//        PaymentInterfParamDTO paymentInterfParamDTO=paymentSettleRequest.initParam(param);
//        return paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_CHARGE_QUERY,paymentInterfParamDTO);
        param.put("orderStatus",Constants.ZJ_PAY_ZFZT.ZFCG);
        param.put("payTime", DateUtils.getNow());
        param.put("orderId","123");
        param.put("totalFee", 60);
        param.put("payType", "1");
        param.put("failCause", "");
        return param;
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
        BasePaymentInterf paymentSettleRequest= basePaymentFactory.getBasePaymentInterf("payment" + PaymentExceptionEnums.PAYMENT_REVOKE.getCode());
        PaymentInterfParamDTO paymentInterfParamDTO=paymentSettleRequest.initParam(param);
        return paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_REVOKE,paymentInterfParamDTO);
    }

    /**@Method updatePaymentBill
     * @Author liuliyun
     * @Description 诊间支付对账查询（总账）接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentBill(Map param) {
        Map result = new HashMap();
        try {
            PaymentSettleDTO paymentSettleDTO = MapUtils.get(param,"paymentSettleDTO");
            String hospCode = MapUtils.get(param,"hospCode");
            paymentSettleDTO.setHospCode(hospCode);
            BigDecimal totalBillNum =new BigDecimal(0.00);// his结算总数量
            BigDecimal totalPamentPrice = new BigDecimal(0.00); // his计算总金额
            BigDecimal totalOrderNum =new BigDecimal(0.00);   // 平台订单数量
            BigDecimal totalOrderFee = new BigDecimal(0.00); // 平台支付总金额
            Map<String,Object> hisResult = paymentSettleDAO.queryPaymentBillInfo(paymentSettleDTO);
            totalBillNum = MapUtils.get(hisResult,"totalBillNum");
            totalPamentPrice= MapUtils.get(hisResult,"paymentPrice");
            BasePaymentInterf paymentSettleRequest= basePaymentFactory.getBasePaymentInterf("payment" + PaymentExceptionEnums.PAYMENT_BILL.getCode());
            PaymentInterfParamDTO paymentInterfParamDTO=paymentSettleRequest.initParam(param);
            Map<String,Object> paymentResult =paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_BILL,paymentInterfParamDTO);
            if (paymentResult!=null){
                totalOrderNum = MapUtils.get(hisResult,"totalBillNum");
                totalOrderFee= MapUtils.get(hisResult,"paymentPrice");
            }
            if (!BigDecimalUtils.equals(totalBillNum,totalOrderNum)){
                result.put("billResult",Constants.SF.F);
                result.put("resultMsg","对账结果：系统与支付平台结算笔数不一致，his计算笔数为"+totalBillNum+",支付平台结算笔数为"+totalOrderNum);
                return result;
            }
            if (!BigDecimalUtils.equals(totalPamentPrice,totalOrderFee)){
                result.put("billResult",Constants.SF.F);
                result.put("resultMsg","对账结果：系统与支付平台结算笔数一致，总金额不一致，his结算总金额为"+totalPamentPrice+",支付平台总金额为"+totalOrderFee);
                return result;
            }
        } catch (Exception e){
            throw new AppException("诊间支付对总账失败，原因："+e.getMessage());
        }
        result.put("billResult",Constants.SF.S);
        result.put("resultMsg","");
        return result;
    }

    /**@Method updatePaymentBillDetail
     * @Author liuliyun
     * @Description 诊间支付对账查询（明细）接口
     * @Date 2022/09/14 14:08
     * @Param [map]
     * @return  Map<String,Object>
     **/
    @Override
    public Map<String, Object> updatePaymentBillDetail(Map param) {
        Map result = new HashMap();
        try {
            PaymentSettleDTO paymentSettleDTO = MapUtils.get(param,"paymentSettleDTO");
            String hospCode = MapUtils.get(param,"hospCode");
            paymentSettleDTO.setHospCode(hospCode);
            List<Map<String, Object>> wxPaymentSettleInfo = paymentSettleDAO.queryPaymentBillDetailList(paymentSettleDTO);
            if (wxPaymentSettleInfo!=null&&wxPaymentSettleInfo.size()>0) {
                BasePaymentInterf paymentSettleRequest = basePaymentFactory.getBasePaymentInterf("payment" + PaymentExceptionEnums.PAYMENT_BILL_DETAIL.getCode());
                PaymentInterfParamDTO paymentInterfParamDTO = paymentSettleRequest.initParam(param);
                Map<String, Object> paymentResult = paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_BILL_DETAIL, paymentInterfParamDTO);
                if (paymentResult != null) {
                    List<Map<String, Object>> billDataList = MapUtils.get(paymentResult, "billDataList");
                    if (billDataList != null && billDataList.size() > 0) {
                        for (Map map: wxPaymentSettleInfo){
                            String settleId = MapUtils.get(map,"settleId");
                            String paymentSettleId = MapUtils.get(map,"paymentSettleId");
                            BigDecimal paymentPrice = MapUtils.get(map,"paymentPrice");
                            for (Map paymentMap: billDataList) {
                                String orgOrderId = MapUtils.get(paymentMap,"orgOrderId");
                                BigDecimal totalFee = MapUtils.get(paymentMap,"totalFee");
                                if (settleId.equals(orgOrderId)&&BigDecimalUtils.equals(paymentPrice,totalFee)){
                                    map.put("resultMsg","对账成功");
                                }
                            }
                        }
//                        List<Map<String, Object>> existList = wxPaymentSettleInfo.stream().map(map -> {
//                            billDataList.stream().map(map1 -> Objects.equals(map.get("settleId"),map1.get("orgOrderId"))).forEach(s->map.put("",s));
//                            return map;
//                        }).collect(Collectors.toList());
                    }
                } else {
                    throw new AppException("查询支付平台信息对账明细信息无返回结果！");
                }
                result.put("wxPaymentSettleInfo",wxPaymentSettleInfo);
            }
        }catch (Exception e){
            throw new AppException("诊间支付对明细账失败，原因："+e.getMessage());
        }
        return result;
    }
}
