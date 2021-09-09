package cn.hsa.module.clinical.clinicalpathlist.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.service
 * @Class_name: ClinicPathListService1
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 20:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathListService {

  WrapperResponse<ClinicPathListDTO> getClinicPathById(Map map);

  WrapperResponse<List<ClinicPathListDTO>> queryClinicPathAll(Map map);

  WrapperResponse<PageDTO> queryClinicPathPage(Map map);

  WrapperResponse<Boolean> insertClinicPath(Map map);

  WrapperResponse<Boolean> updateClinicPath(Map map);

  WrapperResponse<Boolean> deleteClinicPathById(Map map);
}
