package cn.hsa.emr.emrprintsetting.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrprintsetting.bo.EmrPrintSettingBO;
import cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO;
import cn.hsa.module.emr.emrprintsetting.service.EmrPrintSettingService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrprintsetting.service.impl
 * @Class_name: EmrPrintSettingServiceImpl
 * @Describe: 电子病历打印设置业务传输实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/18 13:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrprintsetting")
@Slf4j
@Service("emrPrintSettingService_provider")
public class EmrPrintSettingServiceImpl extends HsafService implements EmrPrintSettingService {
  /**
   * 电子病历打印设置逻辑接口
   */
  @Resource
  private EmrPrintSettingBO emrPrintSettingBO;

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:07
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO>
  **/
  @Override
  public WrapperResponse<EmrPrintSettingDTO> getById(Map<String, Object> map) {
    EmrPrintSettingDTO emrPrintSettingDTO = MapUtils.get(map,"emrPrintSettingDTO");
    return WrapperResponse.success(emrPrintSettingBO.getById(emrPrintSettingDTO));

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
  public WrapperResponse<PageDTO> queryPage(Map map) {
    EmrPrintSettingDTO emrPrintSettingDTO = MapUtils.get(map,"emrPrintSettingDTO");
    return WrapperResponse.success(emrPrintSettingBO.queryPage(emrPrintSettingDTO));
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
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO>>
  **/
  @Override
  public WrapperResponse<List<EmrPrintSettingDTO>> queryAll(Map map) {
    EmrPrintSettingDTO emrPrintSettingDTO = MapUtils.get(map,"emrPrintSettingDTO");
    return WrapperResponse.success(emrPrintSettingBO.queryAll(emrPrintSettingDTO));
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
  public WrapperResponse<Boolean> save(Map map) {
    EmrPrintSettingDTO emrPrintSettingDTO = MapUtils.get(map,"emrPrintSettingDTO");
    return WrapperResponse.success(emrPrintSettingBO.save(emrPrintSettingDTO));
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
  public WrapperResponse<Boolean> updateStatus(Map map) {
    EmrPrintSettingDTO emrPrintSettingDTO = MapUtils.get(map,"emrPrintSettingDTO");
    return WrapperResponse.success(emrPrintSettingBO.updateStatus(emrPrintSettingDTO));
  }
}
