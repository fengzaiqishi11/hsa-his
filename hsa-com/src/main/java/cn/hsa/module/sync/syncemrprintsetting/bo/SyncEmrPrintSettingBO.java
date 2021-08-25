package cn.hsa.module.sync.syncemrprintsetting.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.syncemrprintsetting.dto.SyncEmrPrintSettingDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrprintsetting.bo
 * @Class_name: EmrPrintSettingBO
 * @Describe: 电子病历打印设置业务层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 15:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncEmrPrintSettingBO {

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO
  **/
  SyncEmrPrintSettingDTO getById(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod queryPage
  * @Desrciption 分页查询电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryPage(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return java.util.List<cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO>
  **/
  List<SyncEmrPrintSettingDTO> queryAll(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod save
  * @Desrciption 保存电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return boolean
  **/
  boolean save(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod updateStatus
  * @Desrciption 保存电子病历打印设置状态
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return boolean
  **/
  boolean updateStatus(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);
}
