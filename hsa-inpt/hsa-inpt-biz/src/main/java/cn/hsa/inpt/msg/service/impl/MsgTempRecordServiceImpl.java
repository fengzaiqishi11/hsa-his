package cn.hsa.inpt.msg.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.msg.bo.MsgTempRecordBO;
import cn.hsa.module.msg.dto.MsgTempRecordDTO;
import cn.hsa.module.inpt.msg.service.MsgTempRecordService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.msg.service.impl
 * @Class_name: MsgTempRecordServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/27 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/inpt/msg")
@Slf4j
@Service("msgTempRecordService_provider")
public class MsgTempRecordServiceImpl extends HsafService implements MsgTempRecordService {

  @Resource
  private MsgTempRecordBO msgTempRecordBO;

  /**
  * @Menthod queryMsgTempRecord
  * @Desrciption 缺药查询
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 10:36
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryMsgTempRecord(Map map) {
    MsgTempRecordDTO msgTempRecordDTO = MapUtils.get(map,"msgTempRecordDTO");
    return WrapperResponse.success(msgTempRecordBO.queryMsgTempRecord(msgTempRecordDTO));
  }


  /**
  * @Menthod updateMsgTempRecordPage
  * @Desrciption 批量处理信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 20:04
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateMsgTempRecordPage(Map map) {
    MsgTempRecordDTO msgTempRecordDTO = MapUtils.get(map,"msgTempRecordDTO");
    return WrapperResponse.success(msgTempRecordBO.updateMsgTempRecordPage(msgTempRecordDTO));
  }
}
