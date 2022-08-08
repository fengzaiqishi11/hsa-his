package cn.hsa.sys.message.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.center.message.service.MessageInfoService;
import cn.hsa.module.sys.message.bo.UserReadMessageBO;
import cn.hsa.module.sys.message.dao.UserReadMessageDAO;
import cn.hsa.module.sys.message.dto.UserReadMessageDTO;
import cn.hsa.util.Constants;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gory
 * @date 2022/8/4 17:00
 */
@Component
@Slf4j
public class UserReadMessageBOImpl implements UserReadMessageBO {
    @Resource
    private MessageInfoService messageInfoService_consumer;
    @Resource
    private UserReadMessageDAO userReadMessageDAO;
    private long expectedVersionMessagesNums = 1000000L;

    BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),expectedVersionMessagesNums,0.02d);

    /**
     * @Author gory
     * @Description 获取消息推送
     * 1.查询中心端的消息
     * 2.获得消息之后，查询布隆过滤器，若已存在，说明已读，未存在则插入数据库，标记为已读
     * 3.封装数据
     * @Date 2022/8/4 17:05
     * @Param [messageInfoDTO]
     **/
    @Override
    public PageDTO queryMessageInfos(UserReadMessageDTO userReadMessageDTO) {
        // 获取系统中的消息推送
        Map map = new HashMap<>();
        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
        messageInfoDTO.setPageNo(userReadMessageDTO.getPageNo());
        messageInfoDTO.setPageSize(userReadMessageDTO.getPageSize());
        messageInfoDTO.setIsPublish(Constants.SF.S);
        map.put("messageInfoDTO",messageInfoDTO);
        WrapperResponse<List<MessageInfoDTO>>  data = messageInfoService_consumer.queryMessageInfos(map);
        if (null == data){
            new AppException("未查询到系统消息");
        }
        List<MessageInfoDTO> messageInfoDTOList = data.getData();
        List<UserReadMessageDTO> resultList = new ArrayList<>();
        // 未读数量
        int count = 0;
        for (MessageInfoDTO messageInfo: messageInfoDTOList) {
            UserReadMessageDTO readMessageDTO = new UserReadMessageDTO();
            readMessageDTO.setMessageInfoDTO(messageInfo);
            readMessageDTO.setMessageStatus(Constants.SF.F);
            readMessageDTO.setMessageType(Constants.MESSAGETYPE.SYSTEMMESSAGE);
            readMessageDTO.setMessageTime(messageInfo.getCrteTime());
            String bloomKey = userReadMessageDTO.getHospCode()+':'+userReadMessageDTO.getUserId()+':'+ messageInfo.getId();
            if(bloomFilter.mightContain(bloomKey)){
                readMessageDTO.setMessageStatus(Constants.SF.S);
            } else {
                count++;
            }
            resultList.add(readMessageDTO);
        }
        // todo 先根据读取状态进行升序，再根据消息时间降序
        List<UserReadMessageDTO> finallyResultList = resultList.stream().sorted(
                Comparator.comparing(UserReadMessageDTO::getMessageStatus).
                thenComparing(UserReadMessageDTO::getMessageTime,Comparator.reverseOrder())
        ).collect(Collectors.toList());
        return PageDTO.ofByManual(finallyResultList,userReadMessageDTO.getPageNo(),userReadMessageDTO.getPageSize(),count);
    }
    /**
     * @Author gory
     * @Description 一键修改已读状态
     * @Date 2022/8/5 13:52
     * @Param [messageInfoDTO]
     **/
    @Override
    public Boolean updateMessageStatus(UserReadMessageDTO messageInfoDTO) {
        List<String> messageIdList = messageInfoDTO.getMessageIds();
        messageInfoDTO.setMessageType(Constants.MESSAGETYPE.SYSTEMMESSAGE);
        messageInfoDTO.setMessageStatus(Constants.SF.S);
        // 1.插入userRead表
        for(String messageId : messageIdList){
            // 2.更新布隆过滤器
            String bloomKey = messageInfoDTO.getHospCode()+':'+messageInfoDTO.getUserId()+':'+messageId;
            bloomFilter.put(bloomKey);
        }
        return userReadMessageDAO.updateMessageStatus(messageInfoDTO) > 0;
    }
}
