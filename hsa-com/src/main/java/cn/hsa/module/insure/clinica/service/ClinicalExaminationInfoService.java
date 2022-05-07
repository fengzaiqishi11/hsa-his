package cn.hsa.module.insure.clinica.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.clinica.dto.ClinicalExaminationInfoDTO;

/**
* @ClassName  ClinicalExaminationInfoService
* @Deacription 临床检查报告信息表服务接口层
* @Author liuhuiming
* @Date 2022-05-05
* @Version 1.0
**/
public interface ClinicalExaminationInfoService  {

    WrapperResponse<PageDTO> queryClinicalExaminationInfoListByPage(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);

    void saveClinicalExaminationInfo(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);

    void updateClinicalExaminationInfo(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);

    void deleteClinicalExaminationInfoByIds(Long[] ids);

    ClinicalExaminationInfoDTO queryClinicalExaminationInfoById(Long id);

}
