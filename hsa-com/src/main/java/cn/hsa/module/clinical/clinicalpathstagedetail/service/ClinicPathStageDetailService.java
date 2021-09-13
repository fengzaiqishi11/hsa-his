package cn.hsa.module.clinical.clinicalpathstagedetail.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetail.service
 * @Class_name: ClinicPathStageDetailService1
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/9 19:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathStageDetailService {

  WrapperResponse<ClinicPathStageDetailDTO> getClinicPathStageDetailById(Map map);

  WrapperResponse<List<ClinicPathStageDetailDTO>> queryAllClinicPathStageDetail(Map map);

  WrapperResponse<PageDTO> queryClinicPathStageDetailPage(Map map);

  WrapperResponse<Boolean> insertClinicPathStageDetail(Map map);

  WrapperResponse<Boolean>  updateClinicPathStageDetail(Map map);

  WrapperResponse<Boolean> deleteClinicPathStageDetailById(Map map);
}
