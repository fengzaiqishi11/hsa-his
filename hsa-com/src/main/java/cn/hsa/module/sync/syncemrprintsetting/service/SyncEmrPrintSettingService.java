package cn.hsa.module.sync.syncemrprintsetting.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncemrprintsetting.dto.SyncEmrPrintSettingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrprintsetting.service
 * @Class_name: EmrPrintSettingService
 * @Describe: 电子病历打印设置业务传输层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 15:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-center")
public interface SyncEmrPrintSettingService {

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO>
  **/
  @PostMapping("/service/sync/emrprintsetting/getById")
  WrapperResponse<SyncEmrPrintSettingDTO> getById(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod queryPage
  * @Desrciption 分页查询电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @PostMapping("/service/sync/emrprintsetting/queryPage")
  WrapperResponse<PageDTO> queryPage(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO>>
  **/
  @PostMapping("/service/sync/emrprintsetting/queryAll")
  WrapperResponse<List<SyncEmrPrintSettingDTO>> queryAll(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod insert
  * @Desrciption 保存电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/sync/emrprintsetting/insert")
  WrapperResponse<Boolean> save(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);

  /**
  * @Menthod updateStatus
  * @Desrciption 保存电子病历打印设置状态
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/sync/emrprintsetting/delete")
  WrapperResponse<Boolean> updateStatus(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO);
}
