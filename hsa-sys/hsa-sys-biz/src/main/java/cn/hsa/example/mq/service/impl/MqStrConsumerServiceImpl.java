//package cn.hsa.example.mq.service.impl;
//
//import cn.hsa.hsaf.core.mq.MQBusinessHandler;
//import cn.hsa.hsaf.core.mq.MQMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 消息队列业务处理对象
// * @author cz-lingang
// */
////@Service("mqStrConsumerService")
//public class MqStrConsumerServiceImpl implements MQBusinessHandler {
//
//    private Logger log = LoggerFactory.getLogger(MqStrConsumerServiceImpl.class);
//
//    @Override
//    public boolean doBusiness(MQMessage<?> mqMessage) {
//        try {
//            String topic = mqMessage.getTopic();
//            Object content = mqMessage.getContent();
//            log.info("DEMO接收消息队列，业务处理成功，消息主题：{}，消息内容：{}。", topic, content);
//            return true;
//        } catch (Exception e) {
//            log.error("DEMO接收消息队列，业务处理失败.", e);
//            return false;
//        }
//    }
//}
