package cn.hsa.module.center.message.dao;

import cn.hsa.module.center.message.dto.MessageInfoDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.message.dao
 * @Class_name: MessageInfoDAO
 * @Describe:
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/11/25 17:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MessageInfoDAO {

   /**
    * 新增消息
    */
   int insertMessageInfo(MessageInfoDTO messageInfoDTO);

   /**
    * 更新消息
    */
   int updateMssageInfo(MessageInfoDTO messageInfoDTO);

   /**
    * 删除消息
    */
   int deleteMessageInfo(MessageInfoDTO messageInfoDTO);
   /**
    * 批量删除消息
    */
   int deleteMessageInfoBatch(MessageInfoDTO messageInfoDTO);

   /**
    * 查询消息列表
    */
   List<MessageInfoDTO> queryMessageInfoByType(MessageInfoDTO messageInfoDTO);

   /**
    * 根据消息id更新消息发布状态
    */
   int updateMssageInfoStatusById(MessageInfoDTO messageInfoDTO);

   /**
    * @Author gory
    * @Description 查询消息推送
    * @Date 2022/8/4 15:31
    * @Param [messageInfoDTO]
    **/
   List<MessageInfoDTO> queryMessageInfos(MessageInfoDTO messageInfoDTO);
}
