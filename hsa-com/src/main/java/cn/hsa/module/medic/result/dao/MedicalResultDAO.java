package cn.hsa.module.medic.result.dao;

import cn.hsa.module.medic.result.dto.MedicalResultDTO;

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
public interface MedicalResultDAO {

    int insertResults(List<MedicalResultDTO> medicalResultDTOList);

}
