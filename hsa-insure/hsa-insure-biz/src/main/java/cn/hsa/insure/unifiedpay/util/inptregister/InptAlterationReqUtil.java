package cn.hsa.insure.unifiedpay.util.inptregister;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InptAlterationReqUtil
 * @Deacription 住院信息变更-2403
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_2403)
public class InptAlterationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>(3);
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        List<InptDiagnoseDTO> inptDiagnoseDTOList = MapUtils.get(map, "inptDiagnoseDTOList");

        dataMap.put("mdtrtinfo", initMdtrtinfoMap(insureIndividualVisitDTO));
        dataMap.put("diseinfo", initDiseinfoList(insureIndividualVisitDTO,inptDiagnoseDTOList));
        dataMap.put("agnterinfo", initAgnterinfoMap());

        HashMap commParam = new HashMap();
        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_2403);

        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);
    }

    private Object initDiseinfoList(InsureIndividualVisitDTO insureIndividualVisitDTO, List<InptDiagnoseDTO> inptDiagnoseDTOList) {
        //住院变更诊断信息参数diseinfoMap
        Map<String, Object> diseinfoMap = null;
        //入院诊断信息参数diseinfoList
        List<Map<String, Object>> diseinfoList = new ArrayList<Map<String, Object>>();
        for(int i=0;i<inptDiagnoseDTOList.size();i++){
            diseinfoMap = new HashMap<>();
            diseinfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
            if("101".equals(inptDiagnoseDTOList.get(i).getTypeCode()) ||
                    "102".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                    "201".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                    "202".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                    "203".equals(inptDiagnoseDTOList.get(i).getTypeCode())){
                diseinfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());//	诊断类别
                diseinfoMap.put("diag_type", "1");//	诊断类别
                diseinfoMap.put("maindiag_flag", inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
                diseinfoMap.put("diag_srt_no", i);//	诊断排序号
                diseinfoMap.put("diag_code", inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
                diseinfoMap.put("diag_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
                diseinfoMap.put("adm_cond", null);//	入院病情
                diseinfoMap.put("diag_dept", inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
                diseinfoMap.put("dise_dor_no",  inptDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
                diseinfoMap.put("dise_dor_name",  inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
                diseinfoMap.put("diag_time",  DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(),DateUtils.Y_M_DH_M_S));//	诊断时间
                diseinfoMap.put("vali_flag", "1");//	有效标志
//                diseinfoMap.put("medins_diag_code",orgCode);//	医疗机构诊断编码
                diseinfoList.add(diseinfoMap);
            }

        }
        return diseinfoList;
    }

    private Object initAgnterinfoMap() {
        //代办人信息参数agnterinfo
        Map<String, Object> agnterinfoMap = new HashMap<>();

        agnterinfoMap.put("agnter_name", null);//	代办人姓名
        agnterinfoMap.put("agnter_rlts", null);//	代办人关系
        agnterinfoMap.put("agnter_cert_type", null);//	代办人证件类型
        agnterinfoMap.put("agnter_certno", null);//	代办人证件号码
        agnterinfoMap.put("agnter_tel", null);//	代办人联系电话
        agnterinfoMap.put("agnter_addr", null);//	代办人联系地址
        agnterinfoMap.put("agnter_photo", null);//	代办人照片
        return agnterinfoMap;
    }

    private Object initMdtrtinfoMap(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        //入院登记信息参数dscginfo
        Map<String, Object> adminfoMap = new HashMap<String, Object>();
        adminfoMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());//	就诊ID
        adminfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
        adminfoMap.put("insutype", insureIndividualVisitDTO.getAae140());//	险种类型
        adminfoMap.put("coner_name", null);//	联系人姓名
        adminfoMap.put("tel", null);//	联系电话
        adminfoMap.put("begntime", DateUtils.format(insureIndividualVisitDTO.getCrteTime(),DateUtils.Y_M_DH_M_S));//	开始时间
        adminfoMap.put("endtime", null);//	结束时间
        adminfoMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType());//	就诊凭证类型
        adminfoMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo());//	就诊凭证号码
        adminfoMap.put("med_type", insureIndividualVisitDTO.getAka130());//	医疗类别
        adminfoMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo());//	住院/门诊号
        adminfoMap.put("medrcdno", null);//	病历号
        adminfoMap.put("atddr_no",insureIndividualVisitDTO.getPracCertiNo());//	主治医生编码
        adminfoMap.put("chfpdr_name", insureIndividualVisitDTO.getZzDoctorName());//	主诊医师姓名
        adminfoMap.put("adm_diag_dscr", insureIndividualVisitDTO.getVisitDrptName());//	入院诊断描述
        adminfoMap.put("adm_dept_codg", insureIndividualVisitDTO.getVisitDrptId());//	入院科室编码
        adminfoMap.put("adm_dept_name", insureIndividualVisitDTO.getVisitDrptName());//	入院科室名称
        adminfoMap.put("adm_bed", insureIndividualVisitDTO.getVisitBerth());//	入院床位
        adminfoMap.put("dscg_maindiag_code", insureIndividualVisitDTO.getVisitIcdCode());//	住院主诊断代码
        adminfoMap.put("dscg_maindiag_name", insureIndividualVisitDTO.getVisitDrptName());//	住院主诊断名称
        adminfoMap.put("main_cond_dscr", null);//	主要病情描述
        adminfoMap.put("dise_code", insureIndividualVisitDTO.getVisitIcdCode());//	病种编码
        adminfoMap.put("dise_name", insureIndividualVisitDTO.getVisitIcdName());//	病种名称
        adminfoMap.put("oprn_oprt_code", null);//	手术操作代码
        adminfoMap.put("oprn_oprt_name", null);//	手术操作名称
        adminfoMap.put("fpsc_no",insureIndividualVisitDTO.getFpscNo());//	计划生育服务证号
        adminfoMap.put("matn_type", insureIndividualVisitDTO.getMatnType());//	生育类别
        adminfoMap.put("birctrl_type", insureIndividualVisitDTO.getBirctrlType());//	计划生育手术类别
        adminfoMap.put("latechb_flag", insureIndividualVisitDTO.getLatechbFlag());//	晚育标志
        adminfoMap.put("geso_val", insureIndividualVisitDTO.getGesoVal());//	孕周数
        adminfoMap.put("fetts", insureIndividualVisitDTO.getFetts());//	胎次
        adminfoMap.put("fetus_cnt", insureIndividualVisitDTO.getFetusCnt());//	胎儿数
        adminfoMap.put("pret_flag", insureIndividualVisitDTO.getPretFlag());//	早产标志
        adminfoMap.put("birctrl_matn_date", insureIndividualVisitDTO.getBirctrlMatnDate());//	计划生育手术或生育日期
        adminfoMap.put("dise_type_code", null);//	病种编号
        adminfoMap.put("medins_code", null);//	医疗机构编码
        adminfoMap.put("serial_no", null);//	就医登记号
        adminfoMap.put("opter", null);//	操作员工号
        adminfoMap.put("opter_name", null);//	操作员姓名
        adminfoMap.put("wardarea_no", null);//	入院病区编号
        adminfoMap.put("wardarea_no", null);//	入院病区名称
        adminfoMap.put("bed_type", null);// 	床位类型
        adminfoMap.put("old_ipt_no", null);//	原住院号
        adminfoMap.put("med_mdtrt_type", null);//	待遇类别
        adminfoMap.put("memo", null);//	备注
        adminfoMap.put("coner_adr", null);//	联系地址
        adminfoMap.put("adm_time", null);//	入院时间
        adminfoMap.put("opt_time", null);//	经办时间
        adminfoMap.put("clnc_flag", "0");//	临床试验标志
        adminfoMap.put("advpay", null);//	预付款
        return adminfoMap;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
