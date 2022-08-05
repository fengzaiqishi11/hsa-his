package cn.hsa.center.message.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.message.bo.MessageInfoBO;
import cn.hsa.module.center.message.dao.MessageInfoDAO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.module.center.parameter.service.CenterParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.KafkaUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.message.bo.impl
 * @Class_name:: CenterMsgInfoBOImpl
 * @Description: 系统消息BO层实现类
 * @Author: liuliyun
 * @Date: 2022-1-5 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class CenterMsgInfoBOImpl extends HsafBO implements MessageInfoBO {
    @Resource
    MessageInfoDAO messageInfoDAO;

    /**
     * 参数信息维护dubbo消费者接口
     */
    @Resource
    private CenterParameterService centerParameterService;

    @Override
    public Boolean insertMessageInfo(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.insertMessageInfo(messageInfoDTO)>0;
    }


    @Override
    public Boolean updateMessageInfo(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.updateMssageInfo(messageInfoDTO)>0;
    }

    @Override
    public Boolean deleteMessageInfo(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.deleteMessageInfo(messageInfoDTO)>0;
    }

    @Override
    public Boolean deleteMessageInfoBatch(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.deleteMessageInfoBatch(messageInfoDTO)>0;
    }

    @Override
    public PageDTO queryMessageInfoByType(MessageInfoDTO messageInfoDTO) {
        // 设置分页
        PageHelper.startPage(messageInfoDTO.getPageNo(),messageInfoDTO.getPageSize());
        return PageDTO.of(messageInfoDAO.queryMessageInfoByType(messageInfoDTO));
    }

    @Override
    public Boolean updateMssageInfoStatusById(MessageInfoDTO messageInfoDTO) {
        int count = messageInfoDAO.updateMssageInfoStatusById(messageInfoDTO);
        if (count>0){
            sendMessage(messageInfoDTO);
        }
        return count>0;
    }
    /**
     * @Author gory
     * @Description 查询推送的系统消息
     * @Date 2022/8/4 15:30
     * @Param [messageInfoDTO]
     **/
    @Override
    public List<MessageInfoDTO> queryMessageInfos(MessageInfoDTO messageInfoDTO) {
        // 设置分页
        PageHelper.startPage(messageInfoDTO.getPageNo(),messageInfoDTO.getPageSize());
        return messageInfoDAO.queryMessageInfos(messageInfoDTO);
    }

    // 发送消息
    public void sendMessage(MessageInfoDTO messageInfoDTO){
        messageInfoDTO.setStatusCode(Constants.MSGZT.MSG_WD);
        List<MessageInfoDTO> messageInfoDTOList =new ArrayList<>();
        // 获取医院kafka 的IP与端口
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("code", "KAFKA_MSG_IP");
        CenterParameterDTO sys = centerParameterService.getParameterByCode(sysMap).getData();
        if (sys == null || sys.getValue() == null) {
            return;
        }
        String server = sys.getValue();
        // 1. 创建一个kafka生产者
        String producerTopic = Constants.MSG_TOPIC.producerTopicKey;//生产者消息推送Topic
        KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
        messageInfoDTOList.add(messageInfoDTO);
        String message = JSONObject.toJSONString(messageInfoDTOList);
        KafkaUtil.sendMessage(kafkaProducer,producerTopic,message);
    }

}
