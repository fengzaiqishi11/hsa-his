package cn.hsa.module.clinical.clinicalpathlist.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.bo
 * @Class_name: ClinicPathListBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathListBO {

  ClinicPathListDTO getClinicPathById(String id);

  List<ClinicPathListDTO> queryClinicPathAll(ClinicPathListDTO clinicPathList);

  PageDTO queryClinicPathPage(ClinicPathListDTO clinicPathList);

  int insertClinicPath(ClinicPathListDTO clinicPathList);

  int updateClinicPath(ClinicPathListDTO clinicPathList);

  int deleteClinicPathById(ClinicPathListDTO clinicPathList);

}
