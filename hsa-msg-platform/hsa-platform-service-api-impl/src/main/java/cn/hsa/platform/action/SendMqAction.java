package cn.hsa.platform.action;

import cn.hsa.platform.domain.SendTaskModel;
import cn.hsa.platform.enums.RespStatusEnum;
import cn.hsa.platform.pipeline.BusinessProcess;
import cn.hsa.platform.pipeline.ProcessContext;
import cn.hsa.platform.vo.BasicResultVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author unkown
 * 将消息发送到MQ
 */
@Slf4j
public class SendMqAction implements BusinessProcess {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${mq.kafka.topic.name}")
    private String topicName;

    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();
        try {
            kafkaTemplate.send(topicName, JSON.toJSONString(sendTaskModel.getTaskInfo(),
                    new SerializerFeature[] {SerializerFeature.WriteClassName}));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send kafka fail! e:{},params:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(sendTaskModel.getTaskInfo().get(0)));
        }
    }
}
