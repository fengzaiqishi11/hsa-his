package cn.hsa.clinical.clinicpathexec.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicpathexec.bo.ClinicPathExecBO;
import cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO;
import cn.hsa.module.clinical.clinicpathexec.service.ClinicPathExecService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.clinical.clinicpathexec.service.impl
 * @Class_name: ClinicPathExecServiceImpl
 * @Describe: 临床路径执行情况业务传输层实现
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/inpt/clinicPathExecService_provider")
@Slf4j
@Service("clinicPathExecService_provider")
public class ClinicPathExecServiceImpl implements ClinicPathExecService {
  @Resource
  private ClinicPathExecBO clinicPathExecBO;

  /**
  * @Menthod queryClinicPathExecById
  * @Desrciption  根据id查询临床路径执行
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>
  **/
  @Override
  public WrapperResponse<ClinicPathExecDTO> queryClinicPathExecById(Map map) {
    ClinicPathExecDTO clinicPathExecDTO = MapUtils.get(map, "clinicPathExecDTO");
    return WrapperResponse.success(clinicPathExecBO.queryClinicPathExecById(clinicPathExecDTO));
  }

  /**
  * @Menthod queryClinicPathExecAll
  * @Desrciption   根据条件查询全部临床路径执行
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>>
  **/
  @Override
  public WrapperResponse<List<ClinicPathExecDTO>> queryClinicPathExecAll(Map map) {
    ClinicPathExecDTO clinicPathExecDTO = MapUtils.get(map, "clinicPathExecDTO");
    return WrapperResponse.success(clinicPathExecBO.queryClinicPathExecAll(clinicPathExecDTO));
  }

  /**
  * @Menthod queryClinicPathExecPage
  * @Desrciption 根据条件分页查询临床路径执行
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryClinicPathExecPage(Map map) {
    ClinicPathExecDTO clinicPathExecDTO = MapUtils.get(map, "clinicPathExecDTO");
    return WrapperResponse.success(clinicPathExecBO.queryClinicPathExecPage(clinicPathExecDTO));
  }

  /**
  * @Menthod insertClinicPathExec
  * @Desrciption 新增临床路径执行记录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> insertClinicPathExec(Map map) {
    ClinicPathExecDTO clinicPathExecDTO = MapUtils.get(map, "clinicPathExecDTO");
    return WrapperResponse.success(clinicPathExecBO.insertClinicPathExec(clinicPathExecDTO));
  }

  /**
  * @Menthod updateClinicPathExec
  * @Desrciption  编辑临床路径执行记录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateClinicPathExec(Map map) {
    ClinicPathExecDTO clinicPathExecDTO = MapUtils.get(map, "clinicPathExecDTO");
    return WrapperResponse.success(clinicPathExecBO.updateClinicPathExec(clinicPathExecDTO));
  }

  /**
  * @Menthod deleteClinicPathExecById
  * @Desrciption 删除临床路径执行记录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> deleteClinicPathExecById(Map map) {
    ClinicPathExecDTO clinicPathExecDTO = MapUtils.get(map, "clinicPathExecDTO");
    return WrapperResponse.success(clinicPathExecBO.deleteClinicPathExecById(clinicPathExecDTO));
  }
}
