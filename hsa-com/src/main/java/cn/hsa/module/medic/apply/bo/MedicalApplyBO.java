package cn.hsa.module.medic.apply.bo;

import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;

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
public interface MedicalApplyBO {

    Boolean insertMedicalApplys(List<MedicalApplyDTO> medicalApplyDTOList);


    Boolean insertMedicalApplyDetails(List<MedicalApplyDetailDTO> medicalApplyDetailDTOList);

}
