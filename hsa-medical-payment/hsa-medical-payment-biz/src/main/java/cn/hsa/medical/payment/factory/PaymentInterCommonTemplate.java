package cn.hsa.medical.payment.factory;


import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.factory
 * @class_name: PaymentInterCommonParam
 * @Description: 支付接口调用支付平台公共参数封装模板类
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:05
 * @Company: 创智和宇
 **/
@Component
public class PaymentInterCommonTemplate {


    /**封装公共参数方法:模板方法,final不允许子类修改*/
    public final PaymentInterfParamDTO getInsurCommonParam(Map map) {

        /*todo 可以在此处直接调用支付业务,但是需要将回参写会前一个方法,进行其他业务处理*/
        PaymentInterfParamDTO paymentInterfParamDTO = new PaymentInterfParamDTO();

        String msgId = StringUtils.createMsgId(MapUtils.get(map,"hospCode"));
        // 交易编号
        paymentInterfParamDTO.setInfno(MapUtils.get(map,"infno"));
        // 医药机构编号
        paymentInterfParamDTO.setOrgId(MapUtils.get(map,"hospCode"));
        // 发送方报文
        paymentInterfParamDTO.setMsgid(msgId);
        // 操作人id
        paymentInterfParamDTO.setOpter(MapUtils.get(map,"opter"));
        // 操作人名称
        paymentInterfParamDTO.setOpterName(MapUtils.get(map,"opterName"));
        // 请求地址
        paymentInterfParamDTO.setUrl(MapUtils.get(map,"url"));
        // 机构订单号
        if (map.get("orgOrderId")!=null){
            paymentInterfParamDTO.setOrgOrderId(MapUtils.get(map,"orgOrderId"));
        }
        // 商品描述
        if (map.get("body")!=null){
            paymentInterfParamDTO.setBody(MapUtils.get(map,"body"));
        }
        // 订单金额
        if (map.get("totalFee")!=null){
            paymentInterfParamDTO.setTotalFee(MapUtils.get(map,"totalFee"));
        }
        // 付款码
        if (map.get("authCode")!=null){
            paymentInterfParamDTO.setAuthCode(MapUtils.get(map,"authCode"));
        }
        // 付款码类型 1：wx，2：alipay
        if (map.get("payType")!=null){
            paymentInterfParamDTO.setPayType(MapUtils.get(map,"payType"));
        }
        // 账单开始日期
        if (map.get("billBeginDate")!=null){
            paymentInterfParamDTO.setBillBeginDate(MapUtils.get(map,"billBeginDate"));
        }
        // 账单结束日期
        if (map.get("billEndDate")!=null){
            paymentInterfParamDTO.setBillEndDate(MapUtils.get(map,"billEndDate"));
        }
        // 页码
        if (map.get("pageNum")!=null){
            paymentInterfParamDTO.setPageNum(MapUtils.get(map,"pageNum"));
        }
        // 每页显示数量
        if (map.get("pageSize")!=null){
            paymentInterfParamDTO.setPageSize(MapUtils.get(map,"pageSize"));
        }
        // 退款订单号
        if (map.get("orgRefundId")!=null){
            paymentInterfParamDTO.setOrgRefundId(MapUtils.get(map,"orgRefundId"));
        }
        // 退款金额
        if (map.get("refundFee")!=null){
            paymentInterfParamDTO.setRefundFee(MapUtils.get(map,"refundFee"));
        }
        // 退款理由
        if (map.get("refundReason")!=null){
            paymentInterfParamDTO.setRefundReason(MapUtils.get(map,"refundReason"));
        }
        String input = MapUtils.isEmpty(map.get("input")) ? JSON.toJSONString(getInputMap(map)) : JSON.toJSONString(map.get("input"));
        // 请求入参
        paymentInterfParamDTO.setInput(input);
        return paymentInterfParamDTO;
    }

    private Map<String, Object> getInputMap(Map map) {
        Map<String, Object> inputMap = new HashMap<>(1);
        inputMap.put("data", map.get("dataMap"));
        return inputMap;
    }
}
