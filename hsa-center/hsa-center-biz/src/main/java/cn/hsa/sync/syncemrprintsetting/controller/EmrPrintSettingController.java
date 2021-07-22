package cn.hsa.sync.syncemrprintsetting.controller;

import cn.hsa.base.BaseController;
import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncemrprintsetting.dto.SyncEmrPrintSettingDTO;
import cn.hsa.module.sync.syncemrprintsetting.service.SyncEmrPrintSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrPrintSettingController
 * @Describe: 电子病历打印设置控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/sync/emrPrintSetting")
@Slf4j
public class EmrPrintSettingController extends CenterBaseController {

  /**
   * 电子病历打印设置消费者接口
   */
  @Resource
  private SyncEmrPrintSettingService syncEmrPrintSettingService;

  /**
   * @Menthod getById()
   * @Desrciption  根据主键id查询供应商信息
   *
   * @Param
   * 1。 [id] 供应商主键id
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:27
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspl.dto.SyncEmrPrintSettingDTO>
   **/
  @GetMapping("/getById")
  public WrapperResponse<SyncEmrPrintSettingDTO> getById(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO){
    return syncEmrPrintSettingService.getById(syncEmrPrintSettingDTO);
  }

  /**
   * @Menthod queryPage()
   * @Desrciption   根据条件分页查询供应商信息
   *
   * @Param
   * 1. [syncEmrPrintSettingDTO] 供应商数据传输DTO对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:30
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO){
    return syncEmrPrintSettingService.queryPage(syncEmrPrintSettingDTO);
  }

  /**
   * @Menthod queryAll()
   * @Desrciption  查询所有供应商信息
   *
   * @Param
   * [1. syncEmrPrintSettingDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/18 14:45
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<SyncEmrPrintSettingDTO>> queryAll(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO){
    return syncEmrPrintSettingService.queryAll(syncEmrPrintSettingDTO);
  }

  /**
   * @Menthod insert()
   * @Desrciption 新增供应商
   *
   * @Param
   * 1.[syncEmrPrintSettingDTO] 供应商数据传输DTO对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:28
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspl.dto.SyncEmrPrintSettingDTO>
   **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> insert(@RequestBody SyncEmrPrintSettingDTO syncEmrPrintSettingDTO){
    syncEmrPrintSettingDTO.setCrteId(userId);
    syncEmrPrintSettingDTO.setCrteName(userName);
    return syncEmrPrintSettingService.save(syncEmrPrintSettingDTO);
  }


  /**
   * @Menthod updateStatus()
   * @Desrciption 启用禁用供应商
   *
   * @Param
   * 1. [ids] 主键id
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:29
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/updateStatus")
  public WrapperResponse<Boolean> updateStatus(@RequestBody SyncEmrPrintSettingDTO syncEmrPrintSettingDTO){
    return syncEmrPrintSettingService.updateStatus(syncEmrPrintSettingDTO);
  }
}
