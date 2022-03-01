package cn.hsa.module.interf.medicalCare.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.medicalCare.dao
 * @Class_name: MedicalCareInterfDAO
 * @Describe: 医养转换his接口dao
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2022-02-28 11:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MedicalCareInterfDAO {

    // 根据就诊id查询就诊信息
    OutptVisitDTO queryVisitById(Map<String, Object> map);

    // 根据就诊id查询诊断信息
    List<OutptDiagnoseDTO> queryDiagnoseByVisitId(Map<String, Object> map);

    // 根据就诊id查询医嘱信息
    List<InptAdviceDTO> queryAdviceByVisitId(Map<String, Object> map);
}
