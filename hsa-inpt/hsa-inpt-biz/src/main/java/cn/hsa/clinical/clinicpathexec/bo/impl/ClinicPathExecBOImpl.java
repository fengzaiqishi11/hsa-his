package cn.hsa.clinical.clinicpathexec.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicpathexec.bo.ClinicPathExecBO;
import cn.hsa.module.clinical.clinicpathexec.dao.ClinicPathExecDAO;
import cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.clinical.clinicpathexec.bo.impl
 * @Class_name: ClinicPathExecBOImpl
 * @Describe: 临床路径执行情况业务逻辑处理接口实现
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 14:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class ClinicPathExecBOImpl implements ClinicPathExecBO {

  @Resource
  private ClinicPathExecDAO clinicPathExecDAO;

  /**
  * @Menthod queryClinicPathExecById
  * @Desrciption 根据id查询临床路径执行
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:55
  * @Return cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO
  **/
  @Override
  public ClinicPathExecDTO queryClinicPathExecById(ClinicPathExecDTO clinicPathExecDTO) {
    return clinicPathExecDAO.queryClinicPathExecById(clinicPathExecDTO);
  }

  /**
  * @Menthod queryClinicPathExecAll
  * @Desrciption  根据条件查询全部临床路径执行
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:55
  * @Return java.util.List<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>
  **/
  @Override
  public List<ClinicPathExecDTO> queryClinicPathExecAll(ClinicPathExecDTO clinicPathExecDTO) {
    return clinicPathExecDAO.queryClinicPathExecAll(clinicPathExecDTO);
  }

  /**
  * @Menthod queryClinicPathExecPage
  * @Desrciption  根据条件分页查询临床路径执行
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:56
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryClinicPathExecPage(ClinicPathExecDTO clinicPathExecDTO) {
    PageHelper.startPage(clinicPathExecDTO.getPageNo(),clinicPathExecDTO.getPageSize());
    List<ClinicPathExecDTO> clinicPathExecDTOS = clinicPathExecDAO.queryClinicPathExecAll(clinicPathExecDTO);
    return PageDTO.of(clinicPathExecDTOS);
  }

  /**
  * @Menthod insertClinicPathExec
  * @Desrciption  新增临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:56
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean insertClinicPathExec(ClinicPathExecDTO clinicPathExecDTO) {
    return clinicPathExecDAO.insertClinicPathExec(clinicPathExecDTO) > 0;
  }

  /**
  * @Menthod updateClinicPathExec
  * @Desrciption 编辑临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:56
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateClinicPathExec(ClinicPathExecDTO clinicPathExecDTO) {
    return clinicPathExecDAO.updateClinicPathExec(clinicPathExecDTO) > 0;
  }

  /**
  * @Menthod deleteClinicPathExecById
  * @Desrciption 删除临床路径执行记录
  *
  * @Param
  * [clinicPathExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 14:56
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean deleteClinicPathExecById(ClinicPathExecDTO clinicPathExecDTO) {
    return clinicPathExecDAO.deleteClinicPathExecById(clinicPathExecDTO) > 0;
  }
}
