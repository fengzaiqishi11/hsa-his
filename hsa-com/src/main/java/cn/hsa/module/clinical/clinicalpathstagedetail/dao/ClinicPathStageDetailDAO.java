package cn.hsa.module.clinical.clinicalpathstagedetail.dao;

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
public interface ClinicPathStageDetailDAO {

  ClinicPathStageDetailDTO getClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetail);


  List<ClinicPathStageDetailDTO> queryAllClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetail);


  int insertClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);


  int updateClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);


  int deleteClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO);
}
