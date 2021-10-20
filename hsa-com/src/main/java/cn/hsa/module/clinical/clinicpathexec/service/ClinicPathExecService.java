package cn.hsa.module.clinical.clinicpathexec.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicpathexec.service
 * @Class_name: ClinicPathExecService1
 * @Describe: 临床路径执行情况业务传输层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 14:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathExecService {

  /**
  * @Menthod queryClinicPathExecById
  * @Desrciption  根据id查询临床路径执行
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:17
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>
  **/
  WrapperResponse<ClinicPathExecDTO> queryClinicPathExecById(Map map);

  /**
  * @Menthod queryClinicPathExecAll
  * @Desrciption 根据条件查询全部临床路径执行
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:17
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>>
  **/
  WrapperResponse<List<ClinicPathExecDTO>> queryClinicPathExecAll(Map map);

  /**
  * @Menthod queryClinicPathExecPage
  * @Desrciption 根据条件分页查询临床路径执行
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:17
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<PageDTO> queryClinicPathExecPage(Map map);

  /**
  * @Menthod insertClinicPathExec
  * @Desrciption 新增临床路径执行记录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:17
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> insertClinicPathExec(Map map);

  /**
  * @Menthod updateClinicPathExec
  * @Desrciption 编辑临床路径执行记录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:17
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateClinicPathExec(Map map);

  /**
  * @Menthod deleteClinicPathExecById
  * @Desrciption 删除临床路径执行记录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:17
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> deleteClinicPathExecById(Map map);
}
