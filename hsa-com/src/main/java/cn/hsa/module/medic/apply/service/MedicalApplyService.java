package cn.hsa.module.medic.apply.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Package_name
 * @class_nameMedicalApplyDAO
 * @Description 医技BO
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:30
 * @Company 创智和宇
 **/
@FeignClient(value = "hsa-medical")
public interface MedicalApplyService {

    @PostMapping("/service/medical/apply/insertMedicalApplys")
    WrapperResponse<Boolean> insertMedicalApplys(List<MedicalApplyDTO> medicalApplyDTOList);

    @PostMapping("/service/medical/applyDetail/insertMedicalApplyDetails")
    WrapperResponse<Boolean> insertMedicalApplyDetails(List<MedicalApplyDetailDTO> medicalApplyDetailDTOList);

}
