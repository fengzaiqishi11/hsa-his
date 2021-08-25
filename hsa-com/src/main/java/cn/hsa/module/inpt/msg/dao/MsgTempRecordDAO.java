package cn.hsa.module.inpt.msg.dao;

import cn.hsa.module.msg.dto.MsgTempRecordDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.msg.dao
 * @Class_name: MsgTempRecordDAO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/27 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MsgTempRecordDAO {
  /**
  * @Menthod queryMsgTempRecord
  * @Desrciption 缺药查询
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 10:24
  * @Return java.util.List<cn.hsa.module.msg.dto.MsgTempRecordDTO>
  **/
  List<MsgTempRecordDTO> queryMsgTempRecord(MsgTempRecordDTO msgTempRecordDTO);

  /**
  * @Menthod getById
  * @Desrciption 根据主键获取缺药信息
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 10:44
  * @Return cn.hsa.module.msg.dto.MsgTempRecordDTO
  **/
  MsgTempRecordDTO getById(MsgTempRecordDTO msgTempRecordDTO);

  /**
  * @Menthod updateMsgTempRecordPage
  * @Desrciption 批量修改信息状态
  *
  * @Param
  * [msgTempRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/27 20:05
  * @Return int
  **/
  int updateMsgTempRecordPage(MsgTempRecordDTO msgTempRecordDTO);
}
