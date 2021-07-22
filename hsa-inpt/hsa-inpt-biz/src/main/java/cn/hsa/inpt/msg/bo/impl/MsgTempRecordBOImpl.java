package cn.hsa.inpt.msg.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.msg.bo.MsgTempRecordBO;
import cn.hsa.module.inpt.msg.dao.MsgTempRecordDAO;
import cn.hsa.module.msg.dto.MsgTempRecordDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.inpt.msg.bo.impl
 * @Class_name: MsgTempRecordBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/27 10:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class MsgTempRecordBOImpl extends HsafBO implements MsgTempRecordBO {

  @Resource
  private MsgTempRecordDAO msgTempRecordDAO;
  /**
  * @Menthod queryMsgTempRecord
  * @Desrciption 缺药查询
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 10:38
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryMsgTempRecord(MsgTempRecordDTO msgTempRecordDTO) {
    PageHelper.startPage(msgTempRecordDTO.getPageNo(),msgTempRecordDTO.getPageSize());
    List<MsgTempRecordDTO> msgTempRecordDTOS = msgTempRecordDAO.queryMsgTempRecord(msgTempRecordDTO);
    return PageDTO.of(msgTempRecordDTOS);
  }

  /**
  * @Menthod updateMsgTempRecordPage
  * @Desrciption 批量修改信息状态
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 20:05
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateMsgTempRecordPage(MsgTempRecordDTO msgTempRecordDTO) {
    int i = msgTempRecordDAO.updateMsgTempRecordPage(msgTempRecordDTO);
    return i > 0;
  }
}
