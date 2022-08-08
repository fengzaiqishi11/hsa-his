package cn.hsa.platform;


import cn.hsa.platform.domain.TaskInfo;
import cn.hsa.platform.dto.MessageInfoModel;
import cn.hsa.platform.handler.MessageInfoHandler;
import cn.hsa.platform.handler.SmsHandler;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author unkown
 * 消费MQ的消息
 */
@Component
@Slf4j
public class Receiver {

    @Autowired
    private SmsHandler smsHandler;

    @Autowired
    private MessageInfoHandler messageInfoHandler;

    @KafkaListener(topics = "#{'${mq.kafka.topic.name}'}", groupId = "hsaMessagePlatform")
    public void consumer(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            List<TaskInfo> lists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            for (TaskInfo taskInfo : lists) {
                smsHandler.doHandler(taskInfo);
            }
            log.info("receiver message:{}", JSON.toJSONString(lists));
        }

    }

    @KafkaListener(topics = "msg_product_topic", groupId = "msg_product_topic")
    public void consumerMessageInfo(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("hsaKafkaListener receive msg,topic is {},groupId is {}, content is {}","msg_product_topic","msg_product_topic", kafkaMessage);
        if (kafkaMessage.isPresent()) {
            List<MessageInfoModel> lists = JSON.parseArray(kafkaMessage.get(), MessageInfoModel.class);
            List<TaskInfo> taskInfos =new ArrayList<>();
            for (MessageInfoModel messageInfoModel: lists) {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setContentModel(messageInfoModel);
                taskInfos.add(taskInfo);
            }
            for (TaskInfo taskInfo : taskInfos) {
                messageInfoHandler.doHandler(taskInfo);
            }
            log.info("receiver message:{}", JSON.toJSONString(lists));
        }

    }

    @KafkaListener(topics = "msg_consumer_topic", groupId = "msg_consumer_topic")
    public void consumerUpdateMessageInfo(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("hsaKafkaListener receive msg,topic is {},groupId is {}, content is {}","msg_consumer_topic","msg_consumer_topic", kafkaMessage);
        if (kafkaMessage.isPresent()) {
            List<MessageInfoModel> lists = JSON.parseArray(kafkaMessage.get(), MessageInfoModel.class);
            List<TaskInfo> taskInfos =new ArrayList<>();
            for (MessageInfoModel messageInfoModel: lists) {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setContentModel(messageInfoModel);
                taskInfos.add(taskInfo);
            }
            for (TaskInfo taskInfo : taskInfos) {
                messageInfoHandler.doHandlerUpdate(taskInfo);
            }
            log.info("receiver message:{}", JSON.toJSONString(lists));
        }

    }

}
