package cn.hsa.module.center.hospital.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

/**
 * @author gory
 * @date 2022/8/4 16:35
 */
public interface UserReadMessageService {
    /**
     * @Author gory
     * @Description 查询消息推送
     * @Date 2022/8/4 16:36
     * @Param [map]
     **/
    WrapperResponse<PageDTO> queryMessageInfos(Map map);
}
