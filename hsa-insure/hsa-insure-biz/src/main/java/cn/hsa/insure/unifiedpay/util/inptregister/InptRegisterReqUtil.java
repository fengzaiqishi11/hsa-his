package cn.hsa.insure.unifiedpay.util.inptregister;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInptRegisterDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName InptRegisterReqUtil
 * @Deacription 入院办理-2401
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_2401)
public class InptRegisterReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;
    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>(3);
        InsureInptRegisterDTO insureInptRegisterDTO = MapUtils.get(map, "insureInptRegisterDTO");
        List<InptDiagnoseDTO> inptDiagnoseDTOList = MapUtils.get(map, "inptDiagnoseDTOList");
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        List<InptDiagnoseDTO> list = inptDiagnoseDTOList.stream().filter(inptDiagnoseDTO ->
                Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())).collect(Collectors.toList());

        String dscg_maindiag_code = list.get(0).getInsureInllnessCode();
        String dscg_maindiag_name = list.get(0).getInsureInllnessName();;

        dataMap.put("mdtrtinfo", initMdtrtinfoMap(insureInptRegisterDTO, inptVisitDTO, dscg_maindiag_code, dscg_maindiag_name));
        dataMap.put("diseinfo", initDiseinfoList(insureInptRegisterDTO,inptDiagnoseDTOList,inptVisitDTO));
        dataMap.put("agnterinfo", initAgnterinfoMap());

        HashMap commParam = new HashMap();

        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_2401);

        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);
    }

    private Object initMdtrtinfoMap(InsureInptRegisterDTO insureInptRegisterDTO,InptVisitDTO inptVisitDTO,String dscg_maindiag_code, String dscg_maindiag_name) {

        //就诊信息参数mdtrtinfo
        Map<String, Object> mdtrtinfoMap = new HashMap<>();
        mdtrtinfoMap.put("ipt_otp_no", insureInptRegisterDTO.getInNo());//	住院/门诊号
        mdtrtinfoMap.put("psn_no", insureInptRegisterDTO.getAac001());//	人员编号
        mdtrtinfoMap.put("insutype", inptVisitDTO.getInsureIndividualBasicDTO().getAae140()); // // TODO	险种类型
        mdtrtinfoMap.put("coner_name", insureInptRegisterDTO.getAae004());//	联系人姓名
        mdtrtinfoMap.put("tel", insureInptRegisterDTO.getAae005());//	联系电话
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(inptVisitDTO.getHospCode());
        insureIndividualVisitDTO.setVisitId(inptVisitDTO.getVisitId());
        insureIndividualVisitDTO = insureIndividualVisitDAO.selectIsHalfSettleInfo(insureIndividualVisitDTO);
        // 如果是中途结算，且中途结算次数大于0 则入院开始时间取中途结算的结算时间
        if(insureIndividualVisitDTO !=null && "1".equals(insureIndividualVisitDTO.getIsHalfSettle()) && insureIndividualVisitDTO.getSettleCount() > 0){
            insureIndividualVisitDTO.setInsureSettleId("1");
            InsureIndividualCostDTO insureIndividualCostDTO = insureIndividualCostDAO.selectHalfTransmitFee(insureIndividualVisitDTO);
            if(insureIndividualCostDTO !=null && insureIndividualCostDTO.getFeeEndTime() !=null){
                Date feeEndTime =  insureIndividualCostDTO.getFeeEndTime();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(feeEndTime);
                calendar.add(Calendar.DATE,1);
                feeEndTime =calendar.getTime();
                mdtrtinfoMap.put("begntime", DateUtils.format(feeEndTime,DateUtils.Y_M_DH_M_S));//	开始时间
            }else{
                mdtrtinfoMap.put("begntime", DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));//	开始时间
            }
        }
        else{
            mdtrtinfoMap.put("begntime", DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));//	开始时间
        }
        String mdtrtCertType = inptVisitDTO.getInsureIndividualBasicDTO().getBka895();
        if("06".equals(mdtrtCertType) || "03".equals(mdtrtCertType)){
            mdtrtinfoMap.put("mdtrt_cert_type", "03");//inptVisitDTO.getCertCode());//	就诊凭证类型
            mdtrtinfoMap.put("mdtrt_cert_no", inptVisitDTO.getInsureIndividualBasicDTO().getBka896());// inptVisitDTO.getCertNo());//	就诊凭证编号
            mdtrtinfoMap.put("card_sn", insureInptRegisterDTO.getCardIden());//	卡识别码（跨省异地必传）
        }
        if("02".equals(mdtrtCertType)) {
            mdtrtinfoMap.put("mdtrt_cert_type", mdtrtCertType);
            mdtrtinfoMap.put("mdtrt_cert_no", inptVisitDTO.getInsureIndividualBasicDTO().getAac002());
        }
        if("01".equals(mdtrtCertType)) {
            mdtrtinfoMap.put("mdtrt_cert_type", mdtrtCertType);
            mdtrtinfoMap.put("mdtrt_cert_no", inptVisitDTO.getInsureIndividualBasicDTO().getBka896());
        }
        mdtrtinfoMap.put("med_type", inptVisitDTO.getInsureBizCode());//	医疗类别
        mdtrtinfoMap.put("ipt_no", inptVisitDTO.getInNo());//	住院号
        mdtrtinfoMap.put("medrcdno", null);//	病历号
        if(StringUtils.isEmpty(inptVisitDTO.getPracCertiNo())){
            throw  new AppException("该【"+inptVisitDTO.getZzDoctorName()+"】医生的医师编码没有维护,请先去用户管理里面维护");
        }
        mdtrtinfoMap.put("atddr_no", inptVisitDTO.getPracCertiNo());//inptVisitDTO.getZzDoctorId());//	主治医生编码
        mdtrtinfoMap.put("chfpdr_name",inptVisitDTO.getZzDoctorName());//inptVisitDTO.getZzDoctorName());//	主治医师姓名
        mdtrtinfoMap.put("adm_diag_dscr", dscg_maindiag_name);//	入院诊断描述
        mdtrtinfoMap.put("adm_dept_codg", inptVisitDTO.getInDeptId());//	入院科室编码
        mdtrtinfoMap.put("adm_dept_name", inptVisitDTO.getInDeptName());//	入院科室名称
        mdtrtinfoMap.put("adm_bed", inptVisitDTO.getBedName());//	入院床位
        mdtrtinfoMap.put("dscg_maindiag_code", dscg_maindiag_code);//	住院主诊断代码
        mdtrtinfoMap.put("dscg_maindiag_name", dscg_maindiag_name);//	住院主诊断名称
        mdtrtinfoMap.put("main_cond_dscr", dscg_maindiag_name);//	主要病情描述
//        String diseCode = inptVisitDTO.getInsureIndividualBasicDTO().getBka006();
//        String diseCodeName = inptVisitDTO.getInsureIndividualBasicDTO().getBka006Name();
//        if(StringUtils.isEmpty(diseCode) || "null".equals(diseCode)){
//            mdtrtinfoMap.put("dise_code", "");//	病种编码
//        }else{
//            mdtrtinfoMap.put("dise_code", diseCode);//	病种编码
//        }
//        if(StringUtils.isEmpty(diseCodeName) || "null".equals(diseCodeName)){
//            mdtrtinfoMap.put("dise_name", "");//	病种名称
//        }else{
//            mdtrtinfoMap.put("dise_name", diseCodeName);//	病种名称
//        }
        mdtrtinfoMap.put("dise_code", dscg_maindiag_code);//	病种编码
        mdtrtinfoMap.put("dise_name", dscg_maindiag_name);//	病种名称

        mdtrtinfoMap.put("oprn_oprt_code", null);//	手术操作代码
        mdtrtinfoMap.put("oprn_oprt_name", null);//	手术操作名称
        mdtrtinfoMap.put("fpsc_no", null);//	计划生育服务证号
        if("52".equals(insureInptRegisterDTO.getAka130())){
            mdtrtinfoMap.put("matn_type", "1");//	生育类别
            if(inptVisitDTO.getAge().compareTo(23) ==1){
                mdtrtinfoMap.put("latechb_flag", "1");//	晚育标志
            }else{
                mdtrtinfoMap.put("latechb_flag", "0");//	晚育标志
            }
            mdtrtinfoMap.put("pret_flag", "0");//	早产标志
        }
        else{
            mdtrtinfoMap.put("matn_type", null);//	生育类别
            mdtrtinfoMap.put("latechb_flag", null);//	晚育标志
            mdtrtinfoMap.put("pret_flag", null);//	早产标志
        }
        mdtrtinfoMap.put("birctrl_type", null);//	计划生育手术类别
        mdtrtinfoMap.put("geso_val", null);//	孕周数
        mdtrtinfoMap.put("fetts", null);//	胎次
        mdtrtinfoMap.put("fetus_cnt", null);//	胎儿数
        mdtrtinfoMap.put("birctrl_matn_date", null);//	计划生育手术或生育日期
        mdtrtinfoMap.put("dise_type_code", null);//	病种类型
        mdtrtinfoMap.put("insuplc_admdvs", inptVisitDTO.getInsureIndividualBasicDTO().getInsuplc_admdvs());//	医保中心编码
        mdtrtinfoMap.put("medins_code", inptVisitDTO.getInsureIndividualBasicDTO().getMedicineOrgCode());//	医疗机构编码
        mdtrtinfoMap.put("psn_type", null);//	人员类别
        mdtrtinfoMap.put("med_type", inptVisitDTO.getInsureBizCode());//	业务类型
        mdtrtinfoMap.put("med_mdtrr_type", inptVisitDTO.getInsureTreatCode());//	待遇类型
        mdtrtinfoMap.put("opt_time",DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));//	入院登记时间
        mdtrtinfoMap.put("opter", insureInptRegisterDTO.getBka015());//	登记人员工号
        mdtrtinfoMap.put("opter_name", insureInptRegisterDTO.getAae011());    //登记人姓名
        mdtrtinfoMap.put("adm_way", inptVisitDTO.getInModeCode());//	入院方式
        mdtrtinfoMap.put("serial_apply", null);//	业务申请序列号
        mdtrtinfoMap.put("relt_medins_code", null);//	关联医疗机构编码
        mdtrtinfoMap.put("relt_serial_n", null);//	关联就医登记号
        mdtrtinfoMap.put("adm_sumtms", inptVisitDTO.getTotalIn());//	本年住院次数
        mdtrtinfoMap.put("wardarea_no", inptVisitDTO.getInWardId());//	入院病区编号
        mdtrtinfoMap.put("wardarea_name", null);//	入院病区名称
        mdtrtinfoMap.put("bed_type", null);// 	床位类型
        mdtrtinfoMap.put("prep_pay", null);//	预付款总额
        mdtrtinfoMap.put("hosp_adm_diag", null);//	入院诊断(医疗机构)
        mdtrtinfoMap.put("area_no", null);//	区/县
        mdtrtinfoMap.put("emp_no", null);//	单位电脑号
        mdtrtinfoMap.put("emp_code", null);//	单位管理码
        mdtrtinfoMap.put("emp_type", null);//	单位类型
        mdtrtinfoMap.put("name", inptVisitDTO.getName());//	姓名
        mdtrtinfoMap.put("gend", inptVisitDTO.getGenderCode());//	性别
        mdtrtinfoMap.put("brdy", DateUtils.format(inptVisitDTO.getBirthday(),DateUtils.Y_M_DH_M_S));//	出生日期
        mdtrtinfoMap.put("aim_type", null);//	补助类型
        mdtrtinfoMap.put("coner_adr", inptVisitDTO.getContactAddress());//	联系地址
        mdtrtinfoMap.put("admdv", null);//	人员所属中心
        mdtrtinfoMap.put("cvlserv_lv", insureInptRegisterDTO.getBac001());//	公务员级别
        mdtrtinfoMap.put("emp_name", null);//	单位名称
        mdtrtinfoMap.put("dise_cond_sev", inptVisitDTO.getCriticalValueCode());//	病情严重程度
        mdtrtinfoMap.put("clnc_flag", null);//	临床试验标志
        mdtrtinfoMap.put("er_flag", "0");//	急诊标志
        mdtrtinfoMap.put("advpay", "0");//	预付款
        mdtrtinfoMap.put("repeat_ipt_flag", "0");//	重复住院标志
        mdtrtinfoMap.put("ttp_resp", "0");//	是否第三方责任标志
        mdtrtinfoMap.put("merg_setl_flag", "0");//	合并结算标志
        mdtrtinfoMap.put("card_sn", insureInptRegisterDTO.getCardIden());//	卡识别码（跨省异地必传）
        mdtrtinfoMap.put("cert_type", "01");//	证件类型（跨省异地必传）
        mdtrtinfoMap.put("certno", inptVisitDTO.getInsureIndividualBasicDTO().getAac002());//	证件号码（跨省异地必传）
        mdtrtinfoMap.put("hcard_basinfo", insureInptRegisterDTO.getHcardBasinfo());//	证件号码（广州读卡就医必传）
        mdtrtinfoMap.put("hcard_chkinfo", insureInptRegisterDTO.getHcardChkinfo());//	证件号码（广州读卡就医必传）
        // 人员姓名
        mdtrtinfoMap.put("psn_name",inptVisitDTO.getName());
        // 人员类别
        mdtrtinfoMap.put("psn_type",inptVisitDTO.getInsureIndividualBasicDTO().getBka035());
        // 证件类型
        mdtrtinfoMap.put("psn_cert_type",inptVisitDTO.getCertCode());

        return mdtrtinfoMap;
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

    private Object initDiseinfoList(InsureInptRegisterDTO insureInptRegisterDTO,List<InptDiagnoseDTO> inptDiagnoseDTOList,InptVisitDTO inptVisitDTO) {
        //入院诊断信息参数diseinfoMap
        Map<String, Object> diseinfoMap =null;
        List<Map<String, Object>> diseinfoList = new ArrayList<Map<String, Object>>();
        for(int i=0;i<inptDiagnoseDTOList.size();i++){
            diseinfoMap = new HashMap<>();
            diseinfoMap.put("psn_no", insureInptRegisterDTO.getAac001());//	人员编号
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
            diseinfoMap.put("medins_diag_code",inptVisitDTO.getInsureIndividualBasicDTO().getMedicineOrgCode());//	医疗机构编码
            diseinfoList.add(diseinfoMap);

        }
        return diseinfoList;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
