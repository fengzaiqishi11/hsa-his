package cn.hsa.module.sync.syncemrprintsetting.dao;

import cn.hsa.module.sync.syncemrprintsetting.dto.SyncEmrPrintSettingDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrprintsetting.dao
 * @Class_name: EmrPrintSettingDAO
 * @Describe:syncEmrPrintSettingDTO 电子病历打印设置DAO层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncEmrPrintSettingDAO {

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:00
  * @Return cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO
  **/
  SyncEmrPrintSettingDTO getById(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod queryPage
  * @Desrciption 查询电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:00
  * @Return java.util.List<cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO>
  **/
  List<SyncEmrPrintSettingDTO> queryPageorAll(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod insert
  * @Desrciption 添加电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:01
  * @Return int
  **/
  int insert(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod updateStatus
  * @Desrciption 启用或禁用电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:01
  * @Return int
  **/
  int updateStatus(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod update
  * @Desrciption 修改电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:01
  * @Return int
  **/
  int update(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod queryCodeIsExist
  * @Desrciption 判断电子病历打印设置编码是否存在
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:01
  * @Return int
  **/
  int queryCodeIsExist(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);
}
