package cn.hsa.module.clinical.clinicpathexec.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicpathexec.bo
 * @Class_name: ClinicPathExecBO
 * @Describe: 临床路径执行业务处理接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 14:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathExecBO {

  /**
  * @Menthod queryClinicPathExecById
  * @Desrciption 根据id查询临床路径执行
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:21
  * @Return cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO
  **/
  ClinicPathExecDTO queryClinicPathExecById(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod queryClinicPathExecAll
  * @Desrciption 根据条件查询全部临床路径执行
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:21
  * @Return java.util.List<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>
  **/
  List<ClinicPathExecDTO> queryClinicPathExecAll(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod querClinicPathExecPage
  * @Desrciption 根据条件分页查询临床路径执行
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:21
  * @Return java.util.List<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>
  **/
  PageDTO queryClinicPathExecPage(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod insertClinicPathExec
  * @Desrciption 新增临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:21
  * @Return java.lang.Boolean
  **/
  Boolean insertClinicPathExec(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod updateClinicPathExec
  * @Desrciption 编辑临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:21
  * @Return java.lang.Boolean
  **/
  Boolean updateClinicPathExec(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod deleteClinicPathExecById
  * @Desrciption 删除临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:21
  * @Return java.lang.Boolean
  **/
  Boolean deleteClinicPathExecById(ClinicPathExecDTO clinicPathExecDTO);
}
