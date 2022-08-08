package cn.hsa.module.sys.message.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @author gory
 * @date 2022/8/4 16:35
 */
@FeignClient(value = "hsa-sys")
public interface UserReadMessageService {
    /**
     * @Author gory
     * @Description 查询消息推送
     * @Date 2022/8/4 16:36
     * @Param [map]
     **/
    WrapperResponse<PageDTO> queryMessageInfos(Map map);

    WrapperResponse<Boolean> updateMessageStatus(Map map);
}
