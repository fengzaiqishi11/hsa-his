package cn.hsa.interf.medicalCare.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.interf.medicalCare.bo.MedicalCareInterfBO;
import cn.hsa.module.interf.medicalCare.dao.MedicalCareInterfDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.medicalCare.bo.impl
 * @Class_name: MedicalCareInterfBOImpl
 * @Describe: 医养转换his接口bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2022-02-28 11:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@Slf4j
@Component
public class MedicalCareInterfBOImpl extends HsafBO implements MedicalCareInterfBO {

    @Resource
    private MedicalCareInterfDAO medicalCareInterfDAO;

    /**
     * @Menthod: getVisitInfoRecord
     * @Desrciption: 获取医转养就诊信息
     * @Param: hospCode：医院编码，medical_info_id：就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-02-28 11:57
     * @Return:
     **/
    @Override
    public Map<String, Object> getVisitInfoRecord(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        OutptVisitDTO outptVisitDTO = medicalCareInterfDAO.queryVisitById(map);
        if (outptVisitDTO == null) {
            throw new RuntimeException("未查询到相关就诊档案信息");
        }
        // 患者信息
        Map<String, Object> visitInfo = new HashMap<>();
        this.handeleVisit(visitInfo, outptVisitDTO);
        // 诊断信息
        List<OutptDiagnoseDTO> diagnoseDTOS = medicalCareInterfDAO.queryDiagnoseByVisitId(map);
        List<Map<String, Object>> zdInfo = new ArrayList<>();
        this.handeleZd(zdInfo, diagnoseDTOS);
        // 医嘱信息
        List<InptAdviceDTO> adviceDTOS = medicalCareInterfDAO.queryAdviceByVisitId(map);
        List<Map<String, Object>> yzInfo = new ArrayList<>();
        this.handeleYz(yzInfo, adviceDTOS);
        // 返参
        result.put("visitInfo", visitInfo);
        result.put("zdInfo", zdInfo);
        result.put("yzInfo", yzInfo);
        return result;
    }

    // 患者信息
    private void handeleVisit(Map<String, Object> visitInfo, OutptVisitDTO outptVisitDTO) {
        visitInfo.put("name", outptVisitDTO.getName());
        visitInfo.put("genderCode", outptVisitDTO.getGenderCode());
        visitInfo.put("age", outptVisitDTO.getAge());
        visitInfo.put("certNo", outptVisitDTO.getCertNo());
        visitInfo.put("phone", outptVisitDTO.getPhone());
        visitInfo.put("changeType", "1".equals(outptVisitDTO.getChangeType()) ? "医转养" : "养转医");
        visitInfo.put("deptId", outptVisitDTO.getDeptId());
    }
    // 诊断信息
    private void handeleZd(List<Map<String, Object>> zdInfo, List<OutptDiagnoseDTO> diagnoseDTOS) {
        if (!ListUtils.isEmpty(diagnoseDTOS)) {
            for (OutptDiagnoseDTO diagnoseDTO : diagnoseDTOS) {
                Map<String, Object> map = new HashMap<>();
                map.put("diseaseName", diagnoseDTO.getDiseaseName());
                map.put("diseaseType", diagnoseDTO.getTypeCode());
                map.put("diseaseDate", DateUtils.parse(DateUtils.format(diagnoseDTO.getCrteTime(), DateUtils.Y_M_D), DateUtils.Y_M_D));
                map.put("isMain", diagnoseDTO.getIsMain());
                zdInfo.add(map);
            }
        }
    }
    // 医嘱信息
    private void handeleYz(List<Map<String, Object>> yzMap, List<InptAdviceDTO> adviceDTOS) {
        if (!ListUtils.isEmpty(adviceDTOS)) {
            for (InptAdviceDTO adviceDTO : adviceDTOS) {
                Map<String, Object> map = new HashMap<>();
                map.put("isLong", adviceDTO.getIsLong());
                map.put("startTime", adviceDTO.getLongStartTime());
                map.put("itemCode", adviceDTO.getItemCode());
                map.put("itemName", adviceDTO.getItemName());
                map.put("spec", adviceDTO.getSpec());
                map.put("dosage", adviceDTO.getDosage() + adviceDTO.getDosageUnitCode());
                map.put("num", adviceDTO.getNum() + adviceDTO.getUnitCode());
                map.put("usageCode", adviceDTO.getUsageCode());
                map.put("rateName", adviceDTO.getRateName());
                map.put("useDays", adviceDTO.getUseDays());
                map.put("remark", adviceDTO.getRemark());
                map.put("isSkin", adviceDTO.getIsSkin());
                map.put("doctorName", adviceDTO.getCrteName());
                map.put("stopTime", adviceDTO.getStopTime());
                yzMap.add(map);
            }
        }
    }
}
