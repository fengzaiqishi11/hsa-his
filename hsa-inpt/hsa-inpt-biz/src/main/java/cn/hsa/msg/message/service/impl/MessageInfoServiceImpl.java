package cn.hsa.msg.message.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.message.bo.MessageInfoBO;
import cn.hsa.module.emr.message.dto.MessageInfoDTO;
import cn.hsa.module.emr.message.service.MessageInfoService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.message.service.impl
 * @class_name: SysMessageServiceImpl
 * @Description: 系统消息Service实现类
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/11/26 16:16
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/msg/message")
@Service("messageInfoService_provider")
public class MessageInfoServiceImpl extends HsafService implements MessageInfoService {
    @Resource
    MessageInfoBO messageInfoBO;

    @Override
    public WrapperResponse<Boolean> insertMessageInfo(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.insertMessageInfo(messageInfoDTO));
    }

    @Override
    public WrapperResponse<Boolean> insertMessageInfoBatch(Map map) {
        List<MessageInfoDTO> messageInfoDTOList = MapUtils.get(map,"messageInfoDTOList");
        return WrapperResponse.success(messageInfoBO.insertMessageInfoBatch(messageInfoDTOList));
    }

    @Override
    public WrapperResponse<Boolean> updateMessageInfo(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.updateMessageInfo(messageInfoDTO));
    }

    @Override
    public WrapperResponse<Boolean> deleteMessageInfo(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.deleteMessageInfo(messageInfoDTO));
    }

    @Override
    public WrapperResponse<Boolean> deleteMessageInfoBatch(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.deleteMessageInfoBatch(messageInfoDTO));
    }

    @Override
    public WrapperResponse<Boolean> updateMssageInfoBatchById(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.updateMssageInfoBatchById(messageInfoDTO));
    }

    @Override
    public WrapperResponse<PageDTO> queryMessageInfoPage(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.queryMessageInfoPage(messageInfoDTO));
    }

    @Override
    public WrapperResponse<List<MessageInfoDTO>> queryMessageInfoList(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.queryMessageInfoByType(messageInfoDTO));
    }
}