package cn.hsa.msg.message.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.emr.message.bo.MessageInfoBO;
import cn.hsa.module.emr.message.dao.MessageInfoDAO;
import cn.hsa.module.emr.message.dto.MessageInfoDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.message.bo.impl
 * @Class_name:: SysMessageBOImpl
 * @Description: 系统消息BO层实现类
 * @Author: liuliyun
 * @Date: 2021-11-26 17:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class MessageInfoBOImpl extends HsafBO implements MessageInfoBO {
    @Resource
    MessageInfoDAO messageInfoDAO;
    @Override
    public Boolean insertMessageInfo(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.insertMessageInfo(messageInfoDTO)>0;
    }

    @Override
    public Boolean insertMessageInfoBatch(List<MessageInfoDTO> messageInfoDTOS) {
        return messageInfoDAO.insertMessageInfoBatch(messageInfoDTOS)>0;
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
    public List<MessageInfoDTO> queryMessageInfoByType(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.queryMessageInfoByType(messageInfoDTO);
    }

    @Override
    public Boolean updateMessageInfoBatch(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.updateMssageInfoBatch(messageInfoDTO)>0;
    }

    @Override
    public PageDTO queryMessageInfoPage(MessageInfoDTO messageInfoDTO) {
        // 设置分页
        PageHelper.startPage(messageInfoDTO.getPageNo(),messageInfoDTO.getPageSize());
        return PageDTO.of(messageInfoDAO.queryMessageInfoByType(messageInfoDTO));
    }

    @Override
    public Boolean updateMssageInfoBatchById(MessageInfoDTO messageInfoDTO) {
        return messageInfoDAO.updateMssageInfoBatchById(messageInfoDTO)>0;
    }

}
