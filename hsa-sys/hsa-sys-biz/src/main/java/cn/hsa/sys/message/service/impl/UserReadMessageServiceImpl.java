package cn.hsa.sys.message.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.message.bo.UserReadMessageBO;
import cn.hsa.module.sys.message.service.UserReadMessageService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author gory
 * @date 2022/8/4 17:00
 */
@HsafRestPath("/service/sys/message")
@Slf4j
@Service("userReadMessageService_provider")
public class UserReadMessageServiceImpl implements UserReadMessageService {
    @Resource
    UserReadMessageBO userReadMessageBO;
    /**
     * @Author gory
     * @Description 获取消息推送
     * @Date 2022/8/4 17:04
     * @Param [map]
     **/
    @Override
    public WrapperResponse<PageDTO> queryMessageInfos(Map map) {
        return WrapperResponse.success(userReadMessageBO.queryMessageInfos(MapUtils.get(map,"messageInfoDTO")));
    }
    /**
     * @Author gory
     * @Description 修改一键已读
     * @Date 2022/8/5 13:51
     * @Param [map]
     **/
    @Override
    public WrapperResponse<Boolean> updateMessageStatus(Map map) {
        return WrapperResponse.success(userReadMessageBO.updateMessageStatus(MapUtils.get(map,"messageInfoDTO")));
    }
}
