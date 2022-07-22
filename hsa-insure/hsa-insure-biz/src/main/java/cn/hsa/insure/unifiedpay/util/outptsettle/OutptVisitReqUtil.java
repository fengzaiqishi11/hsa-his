package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName OutptVisitReqUtil
 * @Deacription 门诊就诊信息上传-2203
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2203)
public class OutptVisitReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("mdtrtinfo", initPatientInfo(map));
        dataMap.put("diseinfo", initMapList(map));
        // 校验参数
        HashMap commParam = new HashMap();

        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.OUTPT.UP_2203);

        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

    private Map<String, Object> initPatientInfo(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");
        JSONArray jsonArray = MapUtils.get(map, "jsonArray");
        String mainDiseCode = "";
        String mainDiseName = "";
        List<OutptDiagnoseDTO> diagnoseDTOList = MapUtils.get(map, "diagnoseDTOList");
        if (ObjectUtil.isNotEmpty(diagnoseDTOList)) {
            List<OutptDiagnoseDTO> mainDiseList = diagnoseDTOList.stream().filter(o -> Constants.SF.S.equals(o.getIsMain())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(mainDiseList)) {
                mainDiseCode = mainDiseList.get(0).getInsureInllnessCode();
                mainDiseName = mainDiseList.get(0).getInsureInllnessName();
            }
        }
        // 封装患者信息
        Map<String, Object> patientInfo = new HashMap<>(10);
        // 就诊id
        patientInfo.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        // 人员编号
        patientInfo.put("psn_no", insureIndividualVisitDTO.getAac001());
        // 医疗类别
        patientInfo.put("med_type", insureIndividualVisitDTO.getAka130());
        // 开始时间
        patientInfo.put("begntime", DateUtils.format(insureIndividualVisitDTO.getVisitTime(), DateUtils.Y_M_DH_M_S));
        // TODO 主要病情
        patientInfo.put("main_cond_dscr", "");
//        if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006())) {
//            // 病种编号
//            patientInfo.put("dise_code", jsonArray.getJSONObject(0).get("insureIllnessCode"));
//            // 病种编号
//            patientInfo.put("dise_codg", jsonArray.getJSONObject(0).get("insureIllnessCode"));
//            //病种名称
//            patientInfo.put("dise_name", jsonArray.getJSONObject(0).get("insureIllnessName"));
//        } else {
//            // 病种编号
//            patientInfo.put("dise_code", insureIndividualVisitDTO.getBka006());
//            // 病种编号
//            patientInfo.put("dise_codg", insureIndividualVisitDTO.getBka006());
//            // 病种名称
//            patientInfo.put("dise_name", insureIndividualVisitDTO.getBka006Name());
//        }
        //门特门慢业务的病种取bka006,其他业务取主诊断编码

        if (StringUtils.isNotEmpty(insureIndividualVisitDTO.getBka006())) {
            // 病种编号
            patientInfo.put("dise_code", insureIndividualVisitDTO.getBka006());
            // 病种编号
            patientInfo.put("dise_codg", insureIndividualVisitDTO.getBka006());
            // 病种名称
            patientInfo.put("dise_name", insureIndividualVisitDTO.getBka006Name());
        }else {
            // 病种编号
            patientInfo.put("dise_code", mainDiseCode);
            // 病种编号
            patientInfo.put("dise_codg", mainDiseCode);
            // 病种名称
            patientInfo.put("dise_name", mainDiseName);
        }

        // 计划生育手术类别
        patientInfo.put("birctrl_type", insureIndividualVisitDTO.getBirctrlType());
        // 计划生育手术或生育日期
        patientInfo.put("birctrl_matn_date", insureIndividualVisitDTO.getBirctrlMatnDate());
        patientInfo.put("geso_val", "");  //孕周数
        patientInfo.put("ttp_resp", ""); //是否第三方责任申请
        patientInfo.put("expi_gestation_nub_of_m", "");  //终止妊娠月数
        return patientInfo;
    }

    private List<Map<String, Object>> initMapList(Map map) {
        List<OutptDiagnoseDTO> data = MapUtils.get(map, "diagnoseDTOListData");
        List<OutptDiagnoseDTO> diagnoseDTOList = MapUtils.get(map, "diagnoseDTOList");

        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        SysUserDTO sysUserDTO = insureIndividualVisitDAO.queryDoctorPracCertiNo(outptVisitDTO);
        String doctorName = sysUserDTO.getName();
        String doctorId = sysUserDTO.getPracCertiNo();
        if (StringUtils.isEmpty(doctorId)) {
            throw new AppException("该" + doctorName + "医生的医师国家码没有维护,请去用户管理里面维护");
        }

        List<Map<String, Object>> mapList = new ArrayList<>();
        commonHandlerDisease(diagnoseDTOList, data);
        // 封装诊断信
        for (int i = 0; i < diagnoseDTOList.size(); i++) {
            Map<String, Object> diagnoseMap = new HashMap<>(10);
            // 诊断类别
            diagnoseMap.put("diag_type", data.get(i).getTypeCode());
            // 诊断排序号
            diagnoseMap.put("diag_srt_no", i + 1);
            // 诊断代码
            diagnoseMap.put("diag_code", diagnoseDTOList.get(i).getInsureInllnessCode());
            // 诊断名称
            diagnoseMap.put("diag_name", diagnoseDTOList.get(i).getInsureInllnessName());
            // 诊断科室
            diagnoseMap.put("diag_dept", diagnoseDTOList.get(i).getInDeptName());
            if ("1".equals(diagnoseDTOList.get(i).getIsMain())) {
                // 诊断类别
                diagnoseMap.put("diag_type", "1");
            }
            if (StringUtils.isEmpty(diagnoseDTOList.get(i).getPracCertiNo())) {
                // 诊断医生编码
                diagnoseMap.put("dise_dor_no", doctorId);
            } else {
                // 诊断医生编码
                diagnoseMap.put("dise_dor_no", diagnoseDTOList.get(i).getPracCertiNo());
            }
            if (StringUtils.isEmpty(diagnoseDTOList.get(i).getZzDoctorName())) {
                // 诊断医生编码
                diagnoseMap.put("dise_dor_name", doctorName);
            } else {
                // 诊断医生姓名
                diagnoseMap.put("dise_dor_name", diagnoseDTOList.get(i).getZzDoctorName());
            }
            // 诊断时间
            diagnoseMap.put("diag_time", DateUtils.format(diagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));
            // 有效标志
            diagnoseMap.put("vali_flag", Constants.SF.S);
            // 主诊断标志
            diagnoseMap.put("maindiag_flag",diagnoseDTOList.get(i).getIsMain());
            mapList.add(diagnoseMap);
        }
        return mapList;
    }

    /**
     * @Method commonHandlerDisease
     * @Desrciption 医保入院登记和出院办理时，验证诊断是否匹配
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 9:01
     * @Return
     **/
    private void commonHandlerDisease(List<OutptDiagnoseDTO> inptDiagnoseDTOList, List<OutptDiagnoseDTO> data) {
        List<OutptDiagnoseDTO> list = data.stream().filter(inptDiagnoseDTO ->
                Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())).collect(Collectors.toList());
        int size = list.size();
        if (size == 0) {
            throw new AppException("没有门诊主诊断");
        }
        if (size > 1) {
            throw new AppException("门诊主诊断数量大于1");
        }
        if (inptDiagnoseDTOList.size() != data.size()) {
            List<String> dataCollect = data.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> inptDataCollect = inptDiagnoseDTOList.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> collect = dataCollect.stream().filter(item -> !inptDataCollect.contains(item)).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            if (!ListUtils.isEmpty(collect)) {
                for (String s : collect) {
                    stringBuilder.append(s).append(",");
                }
                throw new AppException("该患者开的" + stringBuilder + "还没有进行疾病匹配,请先做好匹配工作");
            }
        }

    }

}
