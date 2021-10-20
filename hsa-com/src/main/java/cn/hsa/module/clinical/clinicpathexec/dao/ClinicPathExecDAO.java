package cn.hsa.module.clinical.clinicpathexec.dao;

import cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicpathexec.dao
 * @Class_name: ClinicPathExecDAO1
 * @Describe:  临床路径执行情况Dao层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 14:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathExecDAO {

  /**
  * @Menthod queryClinicPathExecById
  * @Desrciption  编辑临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:22
  * @Return cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO
  **/
  ClinicPathExecDTO queryClinicPathExecById(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod queryClinicPathExecAll
  * @Desrciption  根据条件查询全部临床路径执行
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:22
  * @Return java.util.List<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>
  **/
  List<ClinicPathExecDTO> queryClinicPathExecAll(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod insertClinicPathExec
  * @Desrciption 新增临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:22
  * @Return int
  **/
  int insertClinicPathExec(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod updateClinicPathExec
  * @Desrciption  编辑临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:22
  * @Return int
  **/
  int updateClinicPathExec(ClinicPathExecDTO clinicPathExecDTO);

  /**
  * @Menthod deleteClinicPathExecById
  * @Desrciption 删除临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:22
  * @Return int
  **/
  int deleteClinicPathExecById(ClinicPathExecDTO clinicPathExecDTO);
}
