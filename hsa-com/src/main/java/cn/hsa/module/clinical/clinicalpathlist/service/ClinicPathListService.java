package cn.hsa.module.clinical.clinicalpathlist.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.service
 * @Class_name: ClinicPathListService1
 * @Describe: 临床路径目录维护service接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 20:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathListService {

  /**
  * @Menthod getClinicPathById
  * @Desrciption 临床路径目录根据id查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>
  **/
  WrapperResponse<ClinicPathListDTO> getClinicPathById(Map map);

  /**
  * @Menthod queryClinicPathAll
  * @Desrciption 查询全部临床路径目录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>>
  **/
  WrapperResponse<List<ClinicPathListDTO>> queryClinicPathAll(Map map);

  /**
  * @Menthod queryClinicPathPage
  * @Desrciption 分页查询临床路径目录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<PageDTO> queryClinicPathPage(Map map);

  /**
  * @Menthod saveClinicPath
  * @Desrciption 保存(新增或编辑)临床路径目录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> saveClinicPath(Map map);

  /**
  * @Menthod updateClinicPath
  * @Desrciption 临床路径目录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateClinicPathAuditCode(Map map);

  /**
  * @Menthod deleteClinicPathById
  * @Desrciption 删除临床路径目录
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> deleteClinicPathById(Map map);
}
