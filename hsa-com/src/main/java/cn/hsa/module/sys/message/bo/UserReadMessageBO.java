package cn.hsa.module.sys.message.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.message.dto.UserReadMessageDTO;

/**
 * @author gory
 * @date 2022/8/4 16:58
 */
public interface UserReadMessageBO {
    /**
     * @Author gory
     * @Description 获取消息推送
     * @Date 2022/8/4 17:05
     * @Param [messageInfoDTO]
     **/
    PageDTO queryMessageInfos(UserReadMessageDTO messageInfoDTO);

    Boolean updateMessageStatus(UserReadMessageDTO messageInfoDTO);
}
