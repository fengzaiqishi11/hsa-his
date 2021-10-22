package cn.hsa.outpt.outptmedicaltemplate.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptmedicaltemplate.bo.OutptMedicalTemplateBO;
import cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO;
import cn.hsa.module.outpt.outptmedicaltemplate.service.OutptMedicalTemplateService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptmedicaltemplate.service.impl
 * @Class_name: OutptMedicalTemplateServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/9 15:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/outpt/outptMedicalTemplate")
@Slf4j
@Service("outptMedicalTemplateService_provider")
public class OutptMedicalTemplateServiceImpl extends HsafService implements OutptMedicalTemplateService {

  @Resource
  private OutptMedicalTemplateBO outptMedicalTemplateBO;

  /**
  * @Menthod getById
  * @Desrciption 根据主键获取病历模板
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
  **/
  @Override
  public WrapperResponse<OutptMedicalTemplateDTO> getById(Map map) {
    OutptMedicalTemplateDTO outptMedicalTemplateDTO = MapUtils.get(map,"outptMedicalTemplateDTO");
    return WrapperResponse.success(outptMedicalTemplateBO.getById(outptMedicalTemplateDTO));
  }

  /**
  * @Menthod queryMedicalTemplatePage
  * @Desrciption 分页查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryMedicalTemplatePage(Map map) {
    OutptMedicalTemplateDTO outptMedicalTemplateDTO = MapUtils.get(map,"outptMedicalTemplateDTO");
    return WrapperResponse.success(outptMedicalTemplateBO.queryMedicalTemplatePage(outptMedicalTemplateDTO));
  }

  /**
  * @Menthod saveOutptMedicalTemplate
  * @Desrciption 新增门诊电子病历模板
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:18
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> insertMedicalTemplate(Map map) {
    OutptMedicalTemplateDTO outptMedicalTemplateDTO = MapUtils.get(map,"outptMedicalTemplateDTO");
    return WrapperResponse.success(outptMedicalTemplateBO.insertMedicalTemplate(outptMedicalTemplateDTO));
  }

  /**
  * @Menthod insertMedicalTemplate
  * @Desrciption 编辑门诊电子病历模板
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 19:28
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateMedicalTemplate(Map map) {
    return WrapperResponse.success(outptMedicalTemplateBO.updateMedicalTemplate(map));
  }

  /**
  * @Menthod updateStatus
  * @Desrciption 修改门诊电子病历状态
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:18
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateStatus(Map map) {
    OutptMedicalTemplateDTO outptMedicalTemplateDTO = MapUtils.get(map,"outptMedicalTemplateDTO");
    return WrapperResponse.success(outptMedicalTemplateBO.updateStatus(outptMedicalTemplateDTO));
  }

  @Override
  public WrapperResponse<Boolean> deleteById(Map map) {
    return null;
  }


  /**
   * @Menthod queryAllMedicalTemplate
   * @Desrciption 查询所有门诊病历模板
   * @Param [outptMedicalTemplateDTO]
   * @Author liuliyun
   * @Date   2021/10/21 15:35
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @Override
  public WrapperResponse<PageDTO> queryAllMedicalTemplate(Map map) {
    OutptMedicalTemplateDTO outptMedicalTemplateDTO = MapUtils.get(map,"outptMedicalTemplateDTO");
    return WrapperResponse.success(outptMedicalTemplateBO.queryAllMedicalTemplate(outptMedicalTemplateDTO));
  }
}
