package cn.hsa.module.clinical.clinicalpathstagedetail.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetail.dao
 * @Class_name: ClinicPathStageDetailDAO1
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/9 19:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathStageDetailBO {

  ClinicPathStageDetailDTO getClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  List<ClinicPathStageDetailDTO> queryAllClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  PageDTO queryClinicPathStageDetailPage(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  Boolean insertClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  Boolean updateClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  Boolean deleteClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO);
}
