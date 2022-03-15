package cn.hsa.interf.medicalCare.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.interf.medicalCare.bo.MedicalCareInterfBO;
import cn.hsa.module.interf.medicalCare.dao.MedicalCareInterfDAO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.medictocare.service.MedicToCareService;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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

    /**
     * 医养接口服务
     */
    @Resource
    private MedicalCareInterfDAO medicalCareInterfDAO;

    /**
     * 本地医养服务接口
     */
    @Resource
    private MedicToCareService medicToCareService_consumer;

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
            throw new RuntimeException("未查询到相关就诊信息");
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
        log.debug("医养接口-就诊信息返参===" + JSON.toJSONString(result));
        return result;
    }

    /**
     * @Menthod: insertCare2Medic
     * @Desrciption: 插入养转医申请数据，调用本地插入医转养的申请接口
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-03-02 10:46
     * @Return:
     **/
    @Override
    public Boolean insertCare2Medic(Map<String, Object> map) {
        MedicToCareDTO medicToCareDTO = new MedicToCareDTO();
        // 将养转医申请过来的数据封装成本地医转养对象
        this.handeleLocalApplyInfo(map, medicToCareDTO);
        map.put("medicToCareDTO", medicToCareDTO);
        log.debug("医养接口-养转医申请接口入参===" + JSON.toJSONString(map));
        return medicToCareService_consumer.insertMedicToCare(map).getData();
    }

    // 将养转医申请过来的数据封装成本地医转养对象
    private void handeleLocalApplyInfo(Map<String, Object> map, MedicToCareDTO medicToCareDTO) {
        medicToCareDTO.setId(SnowflakeUtils.getId()); // 主键
        medicToCareDTO.setHospCode(MapUtils.get(map, "hospCode")); // 医院编码
        medicToCareDTO.setName(MapUtils.get(map, "name")); // 姓名
        medicToCareDTO.setGenderCode(MapUtils.get(map, "sex")); // 性别
        medicToCareDTO.setAge(MapUtils.get(map, "age")); // 年龄
        medicToCareDTO.setAgeUnitCode("1"); // 年龄单位
        medicToCareDTO.setCertNo(MapUtils.get(map, "id_no")); // 证件号码
        medicToCareDTO.setPhone(MapUtils.get(map, "phone")); // 联系方式
        medicToCareDTO.setChangeType("2"); // 转诊类别 1医转养，2养转医
        medicToCareDTO.setCareToMedicId(MapUtils.get(map, "apply_id")); // 养转医申请ID
        medicToCareDTO.setApplyDeptId(MapUtils.get(map, "clinic_dept")); // 申请科室id
        medicToCareDTO.setApplyCompanyCode(MapUtils.get(map, "referral_org")); //申请入住机构编码
        medicToCareDTO.setHopeInTime(DateUtils.parse(MapUtils.get(map, "expect_referral_date"), DateUtils.Y_M_D)); // 期望入住日期
        medicToCareDTO.setApplyId(MapUtils.get(map, "applicant")); // 申请人id
        medicToCareDTO.setApplyName(MapUtils.get(map, "applicant")); // 申请人姓名
        medicToCareDTO.setApplyTime(DateUtils.parse(MapUtils.get(map, "apply_date"), DateUtils.Y_M_D)); // 申请时间
        medicToCareDTO.setReferralMainSuit(MapUtils.get(map, "referral_main_suit")); // 转诊主诉
        medicToCareDTO.setNusreTypeCode(MapUtils.get(map, "nursing_level")); // 护理级别
        medicToCareDTO.setStatusCode(Constants.YYSQZT.YSQ); // 医养申请状态
        medicToCareDTO.setRemark(MapUtils.get(map, "remark")); // 备注
        medicToCareDTO.setCrteId("养转医申请人【"+ MapUtils.get(map, "applicant") + "】"); // 创建人ID
        medicToCareDTO.setCrteName("养转医申请人【"+ MapUtils.get(map, "applicant") + "】"); // 创建人姓名
        medicToCareDTO.setCrteTime(DateUtils.getNow()); // 创建人
    }

    /**
     * @Menthod: updateApplyStatus
     * @Desrciption: 更新医转养申请状态
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-03-02 10:48
     * @Return:
     **/
    @Override
    public Boolean updateApplyStatus(Map<String, Object> map) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("hospCode", MapUtils.get(map, "hospCode")); // 医院编码
        param.put("id", MapUtils.get(map, "apply_id")); // 主键
        Map<String, Object> data = medicToCareService_consumer.getMedicToCareInfoById(param).getData();
        if (data.isEmpty()) {
            throw new RuntimeException("未查询到对应的申请记录");
        }
        String statusCode = MapUtils.get(data, "statusCode");
        if (Constants.YYSQZT.YJZ.equals(statusCode)) throw new RuntimeException("该申请已经处理了，请勿重复处理！");
        if (Constants.YYSQZT.YJJ.equals(statusCode)) throw new RuntimeException("该申请已经拒绝了，请重新申请！");
        param.put("statusCode", MapUtils.get(map, "apply_status")); // 状态
        if("1".equals(String.valueOf(MapUtils.get(map, "apply_status")))){
            param.put("isHouse","1");
        }else {
            param.put("isHouse","0");
        }
        param.put("realityInTime", DateUtils.parse(MapUtils.get(map, "handle_date"), DateUtils.Y_M_D)); // 处理日期、实际入住时间
        param.put("remark", MapUtils.get(map, "remark")); // 备注
        log.debug("医养接口-医转养申请处理接口入参===" + JSON.toJSONString(param));
        return medicToCareService_consumer.updateMedicToCare(param).getData();
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
                map.put("dosage", adviceDTO.getDosage() == null ? adviceDTO.getDosage() : (adviceDTO.getDosage() + adviceDTO.getDosageUnitCode()));
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
