package cn.hsa.module.inpt.msg.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.msg.dto.MsgTempRecordDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.msg.service
 * @Class_name: MsgTempRecordService
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/27 10:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MsgTempRecordService {

  /**
  * @Menthod queryMsgTempRecord
  * @Desrciption 缺药查询
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 10:23
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<PageDTO> queryMsgTempRecord(Map map);

  /**
  * @Menthod updateMsgTempRecordPage
  * @Desrciption 批量处理信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 20:03
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<Boolean> updateMsgTempRecordPage(Map map);
}
