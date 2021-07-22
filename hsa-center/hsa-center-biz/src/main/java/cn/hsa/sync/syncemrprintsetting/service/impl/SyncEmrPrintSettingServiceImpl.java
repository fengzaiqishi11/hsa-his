package cn.hsa.sync.syncemrprintsetting.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncemrprintsetting.bo.SyncEmrPrintSettingBO;
import cn.hsa.module.sync.syncemrprintsetting.dto.SyncEmrPrintSettingDTO;
import cn.hsa.module.sync.syncemrprintsetting.service.SyncEmrPrintSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.emr.emrprintsetting.service.impl
 * @Class_name: EmrPrintSettingServiceImpl
 * @Describe: 电子病历打印设置业务传输实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/18 13:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sync/emrprintsetting")
@Slf4j
@Service("emrPrintSettingService_provider")
public class SyncEmrPrintSettingServiceImpl extends HsafService implements SyncEmrPrintSettingService {
  /**
   * 电子病历打印设置逻辑接口
   */
  @Resource
  private SyncEmrPrintSettingBO syncEmrPrintSettingBO;

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:07
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO>
  **/
  @Override
  public WrapperResponse<SyncEmrPrintSettingDTO> getById(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    return WrapperResponse.success(syncEmrPrintSettingBO.getById(syncEmrPrintSettingDTO));
  }

  /**
  * @Menthod queryPage
  * @Desrciption 分页查询电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:07
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryPage(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    return WrapperResponse.success(syncEmrPrintSettingBO.queryPage(syncEmrPrintSettingDTO));
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:08
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO>>
  **/
  @Override
  public WrapperResponse<List<SyncEmrPrintSettingDTO>> queryAll(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    return WrapperResponse.success(syncEmrPrintSettingBO.queryAll(syncEmrPrintSettingDTO));
  }

  /**
  * @Menthod save
  * @Desrciption 保存电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:08
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> save(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    return WrapperResponse.success(syncEmrPrintSettingBO.save(syncEmrPrintSettingDTO));
  }

  /**
  * @Menthod updateStatus
  * @Desrciption 保存电子病历打印设置状态
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:08
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateStatus(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    return WrapperResponse.success(syncEmrPrintSettingBO.updateStatus(syncEmrPrintSettingDTO));
  }
}
