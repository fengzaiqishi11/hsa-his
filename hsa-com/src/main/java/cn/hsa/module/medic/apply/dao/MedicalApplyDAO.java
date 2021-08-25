package cn.hsa.module.medic.apply.dao;

import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;

import java.util.List;

/**
 * @Package_name
 * @class_nameMedicalApplyDAO
 * @Description 医技DAO
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:30
 * @Company 创智和宇
 **/
public interface MedicalApplyDAO {

    int insertMedicalApplys(List<MedicalApplyDTO> medicalApplyDTOList);


    int insertMedicalApplyDetails(List<MedicalApplyDetailDTO> medicalApplyDetailDTOList);

}
