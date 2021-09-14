package cn.hsa.module.clinical.clinicalpathlist.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.bo
 * @Class_name: ClinicPathListBO
 * @Describe: 临床路径目录维护BO接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathListBO {

  /**
  * @Menthod getClinicPathById
  * @Desrciption 临床路径目录根据id查询
  *
  * @Param
  * [clinicPathList]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:17
  * @Return cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO
  **/
  ClinicPathListDTO getClinicPathById(ClinicPathListDTO clinicPathList);

  /**
  * @Menthod queryClinicPathAll
  * @Desrciption 查询全部临床路径目录
  *
  * @Param
  * [clinicPathList]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:18
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>
  **/
  List<ClinicPathListDTO> queryClinicPathAll(ClinicPathListDTO clinicPathList);

  /**
  * @Menthod queryClinicPathPage
  * @Desrciption 分页查询临床路径目录
  *
  * @Param
  * [clinicPathList]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:18
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryClinicPathPage(ClinicPathListDTO clinicPathList);

  /**
  * @Menthod saveClinicPath
  * @Desrciption 保存(新增或编辑)临床路径目录
  *
  * @Param
  * [clinicPathList]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:18
  * @Return java.lang.Boolean
  **/
  Boolean saveClinicPath(ClinicPathListDTO clinicPathList);

  /**
  * @Menthod updateClinicPath
  * @Desrciption 临床路径目录
  *
  * @Param
  * [clinicPathList]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:18
  * @Return java.lang.Boolean
  **/
  Boolean updateClinicPathAuditCode(ClinicPathListDTO clinicPathList);

  /**
  * @Menthod deleteClinicPathById
  * @Desrciption 删除临床路径目录
  *
  * @Param
  * [clinicPathList]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:18
  * @Return java.lang.Boolean
  **/
  Boolean deleteClinicPathById(ClinicPathListDTO clinicPathList);

}
