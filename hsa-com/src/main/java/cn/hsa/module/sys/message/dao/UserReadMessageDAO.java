package cn.hsa.module.sys.message.dao;

import cn.hsa.module.sys.message.dto.UserReadMessageDTO;

import java.util.List;

/**
 * @author gory
 * @date 2022/8/4 16:34
 */
public interface  UserReadMessageDAO {

    int insertUserReadMessage(UserReadMessageDTO userReadMessageDTO);
    /**
     * @Author gory
     * @Descrip 通过用户信息查询是否已读
     * @Date 2022/8/5 9:19
     * @Param [userReadMessageDTO]
     **/
    List<UserReadMessageDTO> queryMessageByUser(UserReadMessageDTO userReadMessageDTO);
    /**
     * @Author gory
     * @Description 一键修改已读状态
     * @Date 2022/8/5 13:54
     * @Param [messageInfoDTO]
     **/
    int updateMessageStatus(UserReadMessageDTO messageInfoDTO);
}
