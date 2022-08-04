package cn.hsa.module.center.message.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.message.bo
 * @Class_name: SysMessageServiceBO
 * @Describe:
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/11/26 16:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MessageInfoBO {


  /**
   * 新增消息数据
   * @Author liuliyun
   * @Date 2021/11/26 16:21
   * @param messageInfoDTO 实例对象
   * @return Boolean
   */
  Boolean insertMessageInfo(MessageInfoDTO messageInfoDTO);


  /**
   * 修改消息数据
   * @Author liuliyun
   * @Date 2021/11/26 17:15
   * @param messageInfoDTO 实例对象
   * @return Boolean
   */
  Boolean updateMessageInfo(MessageInfoDTO messageInfoDTO);

  /**
   * 删除消息数据
   * @Author liuliyun
   * @Date 2021/11/26 17:16
   * @param messageInfoDTO 实例对象
   * @return Boolean
   */
  Boolean deleteMessageInfo(MessageInfoDTO messageInfoDTO);

  /** deleteMessageInfoBatch
   * 批量删除消息数据
   * @Author liuliyun
   * @Date 2021/11/26 17:17
   * @param messageInfoDTO 实例对象
   * @return Boolean
   */
  Boolean deleteMessageInfoBatch(MessageInfoDTO messageInfoDTO);

  /**queryMessageInfoPage
   * 批量修改消息数据
   * @Author liuliyun
   * @Date 2021/12/03 14:23
   * @param messageInfoDTO 实例对象
   * @return Boolean
   */
  PageDTO queryMessageInfoByType(MessageInfoDTO messageInfoDTO);

  /**updateMssageInfoStatusById
   * 根据消息id修改消息数据
   * @Author liuliyun
   * @Date 2021/12/03 14:23
   * @param messageInfoDTO 实例对象
   * @return Boolean
   */
  Boolean updateMssageInfoStatusById(MessageInfoDTO messageInfoDTO);
  /**
   * @Author gory
   * @Description 查询消息推送的内容
   * @Date 2022/8/4 15:30
   * @Param [messageInfoDTO]
   **/
  PageDTO queryMessageInfos(MessageInfoDTO messageInfoDTO);
}
