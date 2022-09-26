package cn.hsa.outpt.event;


import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.fees.dao.OutptSettleDAO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentOrderDO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import cn.hsa.module.payment.service.OutptPaymentService;
import cn.hsa.module.payment.service.PaymentOrderService;
import cn.hsa.module.payment.service.PaymentSettleService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Package_name: cn.hsa.outpt.event
 * @Class_name: PaymentReceiver
 * @Describe(描述): 诊间支付kafka消息接收类
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/09/21 09:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class PaymentReceiver {

    @Resource
    private OutptPaymentService outptPaymentService_consumer;

    @Resource
    private PaymentSettleService paymentSettleService_consummer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private PaymentOrderService paymentOrderService_consummer;

    @Resource
    private OutptSettleDAO outptSettleDAO;

    /**
     * @Menthod consumerPaymentSettleQuery
     * @Desrciption 诊间支付支付查询接口消费者
     * @param consumerRecord
     * @Author liuliyun
     * @Date 2022/09/21 10:35
     * @email liyun.liu@powersi.com
     * @return
     */
    @KafkaListener(topics = "payment_product_topic", groupId = "payment_product_topic")
    public void consumerPaymentSettleQuery(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("hsaKafkaListener receive msg,topic is {},groupId is {}, content is {}","payment_product_topic","payment_product_topic", kafkaMessage);
        if (kafkaMessage.isPresent()) {
           PaymentSettleDTO paymentSettleDTO = JSON.parseObject(kafkaMessage.get(), PaymentSettleDTO.class);
            Map<String,Object> requestParam = new HashMap<>();
            requestParam.put("paymentSettleDTO", paymentSettleDTO);
            requestParam.put("hospCode", paymentSettleDTO.getHospCode());
            PaymentSettleDTO paymentSettleDTOInfo = paymentSettleService_consummer.quyeryPaymentInfoByCondition(requestParam);
            if (paymentSettleDTOInfo!=null&&Constants.SF.F.equals(paymentSettleDTOInfo.getIsSettle())) {  // 只有诊间支付订单为未结算时，才需要调用支付查询接口
                Map<String, Object> queryResult = outptPaymentService_consumer.updatePaymentSettleQuery(requestParam);
                String payStatus = MapUtils.get(queryResult, "orderStatus"); // 支付平台支付状态
                if (StringUtils.isNotEmpty(payStatus) && Constants.ZJ_PAY_ZFZT.ZFCG.equals(payStatus)) {   // 支付成功
                    this.updatePaymentSettleStatus(queryResult, paymentSettleDTO); // 更新结算表状态
                } else if (StringUtils.isNotEmpty(payStatus) && Constants.ZJ_PAY_ZFZT.ZFZ.equals(payStatus)) { // 支付中
                    this.sendMessage(paymentSettleDTO, Constants.MSG_TOPIC.paymentProducerTopicKey); // 发送支付查询接口消息
                } else if (StringUtils.isNotEmpty(payStatus) && Constants.ZJ_PAY_ZFZT.ZFSB.equals(payStatus)) { // 支付失败
                    this.sendMessage(paymentSettleDTO, Constants.MSG_TOPIC.paymentRevokePoductTopicKey);   // 发送撤销接口消息
                } else if (StringUtils.isNotEmpty(payStatus) && Constants.ZJ_PAY_ZFZT.DDGB.equals(payStatus)) { // 订单已关闭
                    /*todo 交易关闭 his本地结算处理*/

                }
            }
        }

    }

    /**
     * @Menthod consumerPaymentRefundQueryInfo
     * @Desrciption 诊间支付退款查询接口消费者
     * @param consumerRecord
     * @Author liuliyun
     * @Date 2022/09/21 09:55
     * @email liyun.liu@powersi.com
     * @return
     */
    @KafkaListener(topics = "payment_refund_product_topic", groupId = "payment_refund_product_topic")
    public void consumerPaymentRefundQueryInfo(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("hsaKafkaListener receive msg,topic is {},groupId is {}, content is {}","payment_refund_product_topic","payment_refund_product_topic", kafkaMessage);
        if (kafkaMessage.isPresent()) {
            PaymentSettleDTO paymentSettleDTO = JSON.parseObject(kafkaMessage.get(), PaymentSettleDTO.class);
            Map<String,Object> requestParam = new HashMap<>();
            requestParam.put("paymentSettleDTO", paymentSettleDTO);
            requestParam.put("hospCode", paymentSettleDTO.getHospCode());
            this.paymentRefundQuery(requestParam);
        }

    }


    /**
     * @Menthod consumerPaymentRevokeInfo
     * @Desrciption 诊间支付撤销接口消费者
     * @param consumerRecord
     * @Author liuliyun
     * @Date 2022/09/21 09:53
     * @email liyun.liu@powersi.com
     * @return
     */
    @KafkaListener(topics = "payment_revoke_product_topic", groupId = "payment_revoke_product_topic")
    public void consumerPaymentRevokeInfo(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("hsaKafkaListener receive msg,topic is {},groupId is {}, content is {}","payment_revoke_product_topic","payment_revoke_product_topic", kafkaMessage);
        if (kafkaMessage.isPresent()) {
            PaymentSettleDTO paymentSettleDTO = JSON.parseObject(kafkaMessage.get(), PaymentSettleDTO.class);
            Map<String,Object> requestParam = new HashMap<>();
            requestParam.put("paymentSettleDTO", paymentSettleDTO);
            requestParam.put("hospCode", paymentSettleDTO.getHospCode());
            this.revokePaymentSettle(requestParam);
        }

    }

    /**
     * @Menthod updatePaymentSettleStatus
     * @Desrciption 修改诊间支付订单状态
     * @param queryResult pPaymentSettleDTO
     * @Author liuliyun
     * @Date 2022/09/21 10:04
     * @email liyun.liu@powersi.com
     * @return
     */
    public void updatePaymentSettleStatus(Map<String,Object> queryResult,PaymentSettleDTO pPaymentSettleDTO){
        try {
        String orderId = MapUtils.get(queryResult,"orderId"); // 支付平台订单号
        String payType = MapUtils.get(queryResult,"payType"); // 支付平台支付类型
        String payCode ="";
        if (Constants.ZJ_PAY_TYPE.WX.equals(payType)){
            payCode = Constants.ZFFS.WX;
        }else if (Constants.ZJ_PAY_TYPE.ZFB.equals(payType)){
            payCode = Constants.ZFFS.ZFB;
        }
        Map<String,Object> paymentParam = new HashMap<>();
        paymentParam.put("paymentSettleDTO", pPaymentSettleDTO);
        paymentParam.put("hospCode", pPaymentSettleDTO.getHospCode());
        //修改诊间支付结算表 payment_settle；结算状态 = 结算
        PaymentSettleDO paymentSettleDO = new PaymentSettleDO();
        paymentSettleDO.setId(pPaymentSettleDTO.getId());//id
        paymentSettleDO.setSettleId(pPaymentSettleDTO.getSettleId());
        paymentSettleDO.setVisitId(pPaymentSettleDTO.getVisitId()); // 就诊id
        paymentSettleDO.setPaymentSettleId(orderId); // 支付平台结算id
        paymentSettleDO.setSettleCode(Constants.SETTLECODE.YJS);//结算状态 = 结算
        paymentSettleDO.setIsSettle(Constants.SF.S); // 是否结算 = 是
        paymentSettleDO.setPayCode(payCode); // 支付方式: 微信
        paymentSettleDO.setSettleTime(DateUtils.getNow());
        paymentParam.put("paymentSettleDO", paymentSettleDO);
        paymentSettleService_consummer.updatePaymentSettleInfo(paymentParam);
        //修改诊间支付订单表 payment_order；结算状态 = 结算
        PaymentOrderDO paymentOrderDO = new PaymentOrderDO();
        paymentOrderDO.setId(pPaymentSettleDTO.getId());//id
        paymentOrderDO.setSettleId(pPaymentSettleDTO.getSettleId());
        paymentOrderDO.setVisitId(pPaymentSettleDTO.getVisitId()); // 就诊id
        paymentOrderDO.setSettleCode(Constants.SETTLECODE.YJS);//结算状态 = 结算
        paymentOrderDO.setPayCode(payCode); // 支付方式: 微信
        paymentOrderDO.setPaymentSettleId(orderId); // 支付平台结算id
        paymentOrderDO.setUpdateTime(DateUtils.getNow());
        paymentOrderDO.setUpdateId(pPaymentSettleDTO.getCrteId());
        paymentOrderDO.setUpdateName(pPaymentSettleDTO.getCrteName());
        paymentParam.put("paymentOrderDO", paymentOrderDO);
        paymentOrderService_consummer.updatePaymentOrder(paymentParam);
        }catch (Exception e){
            throw new AppException("诊间支付订单更新出错："+e.getMessage());
        }finally {
            return;
        }
    }



    /**
     * @Menthod paymentRefundQuery
     * @Desrciption 诊间支付退款查询
     * @param paymentParam
     * @Author liuliyun
     * @Date 2022/09/21 10:05
     * @email liyun.liu@powersi.com
     * @return
     */
    public void paymentRefundQuery(Map<String,Object> paymentParam){
        PaymentSettleDTO paymentSettleDTO = MapUtils.get(paymentParam,"paymentSettleDTO");
        String hospCode = MapUtils.get(paymentParam,"hospCode");
        // 校验诊间支付订单状态
        PaymentSettleDTO paymentSettleDO = paymentSettleService_consummer.queryPaymentSettle(paymentParam);
        if (paymentSettleDO!=null&&Constants.ZTBZ.ZC.equals(paymentSettleDO.getStatusCode())) {
            Map<String, Object> result = outptPaymentService_consumer.updatePaymentRefundQuery(paymentParam);
            if (ObjectUtil.isNotEmpty(result)) {
                String refundStatus = MapUtils.get(result, "refundStatus"); // 支付平台退款状态
                if (Constants.ZJ_PAY_TKZT.TKCG.equals(refundStatus)) { // 退款成功
                    log.info("退款成功");
                    this.outptPaymentSettleChangeRed(paymentSettleDTO);  // 诊间支付结算信息冲红
                } else if (Constants.ZJ_PAY_TKZT.TKSB.equals(refundStatus) || Constants.ZJ_PAY_TKZT.TKYC.equals(refundStatus)) { // 退款失败、退款异常

                } else if (Constants.ZJ_PAY_TKZT.TKZ.equals(refundStatus)) { // 退款中
                    log.info("退款中消息发送");
                    this.sendMessage(paymentSettleDTO, Constants.MSG_TOPIC.paymentRefundPoductTopicKey);
                }
            }
        }
    }


    /**
     * @Menthod revokePaymentSettle
     * @Desrciption 诊间支付撤销订单
     * @param paymentSettleParam
     * @Author liuliyun
     * @Date 2022/09/21 10:05
     * @email liyun.liu@powersi.com
     * @return
     */
    public void revokePaymentSettle(Map<String,Object> paymentSettleParam){
        PaymentSettleDTO paymentSettleDTO = MapUtils.get(paymentSettleParam,"paymentSettleDTO");
        String hospCode = MapUtils.get(paymentSettleParam,"hospCode");
        Map<String,Object> revokeResult = outptPaymentService_consumer.updatePaymentRevoke(paymentSettleParam);
        if (ObjectUtil.isNotEmpty(revokeResult)) {
            String resultCode = MapUtils.get(revokeResult, "resultCode"); // 支付平台支付状态
            if (StringUtils.isNotEmpty(resultCode) && "FAIL".equals(resultCode)) {
                String recall = MapUtils.get(revokeResult, "recall"); // 是否需要重试
                if (StringUtils.isNotEmpty(recall) && "Y".equals(recall)) { // 需要重试
                    this.sendMessage(paymentSettleDTO,Constants.MSG_TOPIC.paymentRevokePoductTopicKey);
                }
            }else if (StringUtils.isNotEmpty(resultCode) && "SUCCESS".equals(resultCode)){
                /*todo 撤销成功 his本地结算处理*/

            }
        }
    }

    /**
     * @param paymentSettleDTO
     * @Menthod outptPaymentSettleChangeRed
     * @Desrciption 对冲诊间支付结算信息
     * @Author liuliyun
     * @Date 2022/09/21 16:24
     */
    private void outptPaymentSettleChangeRed(PaymentSettleDTO paymentSettleDTO) {
        try {
            String redSettleId = paymentSettleDTO.getSettleId();   // 冲红id
            OutptSettleDTO outptSettleDTO = paymentSettleDTO.getOutptSettleDTO(); // his 结算信息
            String hospCode = paymentSettleDTO.getHospCode();
            outptSettleDTO.setSettleId(outptSettleDTO.getId());
            paymentSettleDTO.setVisitId(outptSettleDTO.getVisitId());
            paymentSettleDTO.setSettleId(outptSettleDTO.getSettleId());
            Map<String, Object> queryParam = new HashMap<>();
            queryParam.put("hospCode", hospCode);
            queryParam.put("paymentSettleDTO", paymentSettleDTO);
            // 诊间支付结算表数据对冲
            PaymentSettleDTO paymentSettleDO = paymentSettleService_consummer.queryPaymentSettle(queryParam);
            if (paymentSettleDO == null) {
                throw new AppException("退费提示：未获取到诊间支付结算记录");
            }
            // 被冲红
            paymentSettleDO.setStatusCode(Constants.ZTBZ.BCH);
            Map<String, Object> updateParam = new HashMap<>();
            updateParam.put("hospCode", hospCode);
            updateParam.put("paymentSettleDO", paymentSettleDO);
            paymentSettleService_consummer.updatePaymentSettleInfo(updateParam);
            // 冲红
            paymentSettleDO.setId(SnowflakeUtils.getId());
            paymentSettleDO.setSettleId(redSettleId);
            paymentSettleDO.setRedId(paymentSettleDO.getId());
            paymentSettleDO.setOldSettleId(outptSettleDTO.getId());
            paymentSettleDO.setOneSettleId(paymentSettleDO.getOneSettleId());
            paymentSettleDO.setStatusCode(Constants.ZTBZ.CH); // 状态标志： 冲红
            // 金额置反
            paymentSettleDO.setTotalPrice(BigDecimalUtils.negate(paymentSettleDO.getTotalPrice())); // 总金额
            paymentSettleDO.setPaymentPrice(BigDecimalUtils.negate(paymentSettleDO.getPaymentPrice())); // 实际支付金额
            // 创建信息
            paymentSettleDO.setCrteId(paymentSettleDO.getCrteId());
            paymentSettleDO.setCrteName(paymentSettleDO.getCrteName());
            paymentSettleDO.setSettleTime(DateUtils.getNow());
            paymentSettleDO.setCrteTime(DateUtils.getNow());
            Map<String, Object> insertParam = new HashMap<>();
            insertParam.put("hospCode", hospCode);
            insertParam.put("paymentSettleDO", paymentSettleDO);
            paymentSettleService_consummer.insert(insertParam);
        }catch (Exception e){
            throw new AppException("诊间支付冲红出错："+e.getMessage());
        }finally {
            return;
        }
    }

    /**
     * @Menthod sendMessage
     * @Desrciption 创建kafka 生产者 发送消息
     * @param paymentSettleDTO 诊间支付订单实体类  producerTopic  消息主题
     * @Author liuliyun
     * @Date 2022/09/21 10:08
     * @email liyun.liu@powersi.com
     * @return
     */
    public void sendMessage(PaymentSettleDTO paymentSettleDTO,String producerTopic){
        // 获取医院kafka 的IP与端口
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", paymentSettleDTO.getHospCode());
        sysMap.put("code", "KAFKA_MSG_IP");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (sys == null || sys.getValue() == null) {
            return;
        }
        String server = sys.getValue();
        // 1. 创建一个kafka生产者
        KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
        String message = JSONObject.toJSONString(paymentSettleDTO);
        KafkaUtil.sendMessage(kafkaProducer,producerTopic,message);
    }

}
