package cn.hsa.module.inpt.msg.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.msg.dto.MsgTempRecordDTO;

/**
 * @Package_name: cn.hsa.module.inpt.msg.bo
 * @Class_name: MsgTempRecordBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/27 10:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MsgTempRecordBO {

  /**
  * @Menthod queryMsgTempRecord
  * @Desrciption 缺药查询
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 10:27
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryMsgTempRecord(MsgTempRecordDTO msgTempRecordDTO);

  /**
  * @Menthod updateMsgTempRecordPage
  * @Desrciption 批量修改信息状态
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 20:04
  * @Return java.lang.Boolean
  **/
  Boolean updateMsgTempRecordPage(MsgTempRecordDTO msgTempRecordDTO);
}
