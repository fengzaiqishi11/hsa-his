package cn.hsa.medical.payment.bo.impl;


import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.medical.payment.enums.PaymentExceptionEnums;
import cn.hsa.module.payment.bo.PaymentExecuteLogBO;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: BasePaymentFactory
 * @Description: his对接支付平台传输层接口
 * todo 可采用异步传输方式,需要讨论
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
@Component
public class PaymentTransferBoImpl {
    @Resource
    PaymentExecuteLogBO executeLogBO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 支付平台调用方法与日志记录
     */
    public Map<String, Object> transferPayment(PaymentExceptionEnums paymentExceptionEnums, PaymentInterfParamDTO paymentInterfParamDTO) {
        Map<String, Object> params = new HashMap<>();
        // 医药机构编号
        params.put("orgId", paymentInterfParamDTO.getOrgId());
        // 机构订单号
        params.put("orgOrderId", paymentInterfParamDTO.getOrgOrderId());
        // 商品描述
        params.put("body", paymentInterfParamDTO.getBody());
        // 订单金额
        params.put("totalFee", paymentInterfParamDTO.getTotalFee());
        // 付款码
        params.put("authCode", paymentInterfParamDTO.getAuthCode());
        //付款码类型
        params.put("payType", paymentInterfParamDTO.getPayType());
        // 发送方报文
        params.put("msgid", paymentInterfParamDTO.getMsgid());
        // 开始日期
        params.put("billBeginDate", paymentInterfParamDTO.getBillBeginDate());
        // 结束日期
        params.put("billEndDate", paymentInterfParamDTO.getBillEndDate());
        // 页码
        params.put("pageNum", paymentInterfParamDTO.getPageNum());
        // 每页条数
        params.put("pageSize", paymentInterfParamDTO.getPageSize());
        // 退款金额
        params.put("refundFee", paymentInterfParamDTO.getRefundFee());
        // 退款理由
        params.put("refundReason", paymentInterfParamDTO.getRefundReason());
        // 操作人id
        params.put("crteId", paymentInterfParamDTO.getOpter());
        // 操作人名称
        params.put("crteName", paymentInterfParamDTO.getOpterName());

        String paramMapJson = JSON.toJSONString(paymentInterfParamDTO);
        params.put("paramMapJson", paramMapJson == null ? "null" : paramMapJson.length() > 4000 ? paramMapJson.substring(0, 4000) : paramMapJson);
        //请求医保接口日志记录
        logger.info("流水号-{},诊间支付业务功能号 {},请求参数-{}", paymentInterfParamDTO.getMsgid(), paymentExceptionEnums.getCode(), JSON.toJSONString(paymentInterfParamDTO));
        try {
            /*调用传输*/
            String result = HttpConnectUtil.unifiedPayPostUtil(paymentInterfParamDTO.getUrl(), JSON.toJSONString(paymentInterfParamDTO));
            params.put("resultStr", result);
            if (StringUtils.isEmpty(result)) {
                params.put("resultStr", "null");
                params.put("infcode", "null");
                throw new AppException("调用接口异常,返回结果信息为空");
            }
            Map<String, Object> resultMap = JSON.parseObject(result);
            /*todo 异常处理*/
            params.put("infcode", resultMap.get("infcode"));
            logger.info("流水号-{},诊间支付业务功能号 {},成功结果-{}", paymentInterfParamDTO.getMsgid(), paymentExceptionEnums.getCode(), result);
            params.put("resultStr", result == null ? "null" : result.length() > 4000 ? result.substring(0, 4000) : result);
            return resultMap;
        } catch (Exception e) {
            //调接口后，请求失败将异常信息保存
            params.put("resultStr", e.getMessage() == null ? "null" : e.getMessage().length() > 4000 ? e.getMessage().substring(0, 4000) : e.getMessage());
            throw new AppException(e.getMessage());
        } finally {
            executeLogBO.insert(params);
        }
    }
}
