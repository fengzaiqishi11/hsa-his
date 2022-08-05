package cn.hsa.sys.message.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.center.message.bo.MessageInfoBO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.center.message.entity.MessageInfoDO;
import cn.hsa.module.center.message.service.MessageInfoService;
import cn.hsa.module.sys.message.bo.UserReadMessageBO;
import cn.hsa.module.sys.message.dao.UserReadMessageDAO;
import cn.hsa.module.sys.message.dto.UserReadMessageDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        map.put("messageInfoDTO",messageInfoDTO);
        WrapperResponse<List<MessageInfoDTO>>  data = messageInfoService_consumer.queryMessageInfos(map);
        if (null == data){
            new AppException("未查询到系统消息");
        }
        List<MessageInfoDTO> messageInfoDTOList = data.getData();
        List<String> messageIds = messageInfoDTOList.stream().map(MessageInfoDTO::getId).collect(Collectors.toList());
        // 这部分逻辑后续可用部落过滤器代替
        userReadMessageDTO.setMessageIds(messageIds);
        List<UserReadMessageDTO> userReadMessageDTOS = userReadMessageDAO.queryMessageByUser(userReadMessageDTO);
        List<UserReadMessageDTO> resultList = new ArrayList<>();
        for (MessageInfoDTO messageInfo: messageInfoDTOList) {
            UserReadMessageDTO readMessageDTO = new UserReadMessageDTO();
            readMessageDTO.setMessageInfoDTO(messageInfo);
            readMessageDTO.setMessageStatus(Constants.SF.F);
            readMessageDTO.setMessageType(Constants.MESSAGETYPE.SYSTEMMESSAGE);
            for (UserReadMessageDTO readMessage:userReadMessageDTOS) {
                if (messageInfo.getId().equals(readMessage.getMessageId())){
                    readMessageDTO.setMessageStatus(Constants.SF.S);
                }
            }
            resultList.add(readMessageDTO);
        }
//        for (MessageInfoDTO messageInfo: messageInfoDTOList) {
//            // 通过布隆过滤器查询是否已读
//            // 如果在布隆过滤器中未查询到，则说明是未读
//            UserReadMessageDTO paramDTO = new UserReadMessageDTO();
//            paramDTO.setHospCode(userReadMessageDTO.getHospCode());
//            paramDTO.setMessageId(messageInfo.getId());
//            paramDTO.setUserId(userReadMessageDTO.getUserId());
//            paramDTO.setMessageType(Constants.MESSAGETYPE.SYSTEMMESSAGE);
//            userReadMessageDAO.insertUserReadMessage(paramDTO);
//        }
        return PageDTO.ofByManual(resultList,userReadMessageDTO.getPageNo(),userReadMessageDTO.getPageSize());
    }
}
