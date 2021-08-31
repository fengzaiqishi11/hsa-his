package cn.hsa.module.emr.emrprintsetting.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO;

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
public interface EmrPrintSettingBO {

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO
  **/
  EmrPrintSettingDTO getById(EmrPrintSettingDTO emrPrintSettingDTO);

  /**
  * @Menthod queryPage
  * @Desrciption 分页查询电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryPage(EmrPrintSettingDTO emrPrintSettingDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return java.util.List<cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO>
  **/
  List<EmrPrintSettingDTO> queryAll(EmrPrintSettingDTO emrPrintSettingDTO);

  /**
  * @Menthod save
  * @Desrciption 保存电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return boolean
  **/
  boolean save(EmrPrintSettingDTO emrPrintSettingDTO);

  /**
  * @Menthod updateStatus
  * @Desrciption 保存电子病历打印设置状态
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:57
  * @Return boolean
  **/
  boolean updateStatus(EmrPrintSettingDTO emrPrintSettingDTO);
}
