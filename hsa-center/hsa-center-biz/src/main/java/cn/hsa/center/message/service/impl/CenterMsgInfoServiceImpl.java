package cn.hsa.center.message.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.message.bo.MessageInfoBO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.center.message.service.MessageInfoService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.message.service.impl
 * @class_name: CenterMsgInfoServiceImpl
 * @Description: 系统消息Service实现类
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022-1-5 14:25
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/msg/message")
@Service("messageInfoService_provider")
public class CenterMsgInfoServiceImpl extends HsafService implements MessageInfoService {
    @Resource
    MessageInfoBO messageInfoBO;

    @Override
    public WrapperResponse<Boolean> insertMessageInfo(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.insertMessageInfo(messageInfoDTO));
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
    public WrapperResponse<Boolean> updateMssageInfoStatusById(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.updateMssageInfoStatusById(messageInfoDTO));
    }

    @Override
    public WrapperResponse<PageDTO> queryMessageInfoByType(Map map) {
        MessageInfoDTO messageInfoDTO = MapUtils.get(map,"messageInfoDTO");
        return WrapperResponse.success(messageInfoBO.queryMessageInfoByType(messageInfoDTO));
    }

}