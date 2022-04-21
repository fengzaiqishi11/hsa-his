package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedOutptBO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: InsureUnifiedPayOutptBoImpl
 * @Describe: 统一支付平台，业务实现层
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/10 8:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class InsureUnifiedOutptBOImpl extends HsafBO implements InsureUnifiedOutptBO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;
    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;
    @Resource
    private SysParameterService sysParameterService_consumer;
    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService;
    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;
    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;
    @Resource
    private InsureItfBOImpl insureItfBO;
    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    private final AtomicInteger atomicInteger = new AtomicInteger(1);


    /**
     * @Menthod: UP_4301
     * @Desrciption: 医保统一支付平台：门急诊诊疗记录，上传单次病人就诊信息
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-17 19:45
     * @Return:
     **/
    @Override
    public Map<String, Object> UP_4301(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        if (StringUtils.isEmpty(insureRegCode)) throw new RuntimeException("未传入医保机构编码");
        OutptRegisterDTO outptRegisterDTO = MapUtils.get(map, "outptRegisterDTO");
        List<OutptMedicalRecordDTO> blList = MapUtils.get(map, "blList");
        List<OutptDiagnoseDTO> zdList = MapUtils.get(map, "zdList");
        List<OutptPrescribeDetailsExtDTO> cfList = MapUtils.get(map, "cfList");

        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode);
        configDTO.setRegCode(insureRegCode);
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null) throw new RuntimeException("未发现【" + insureRegCode + "】相关医保配置信息");

        // 封装挂号信息
        Map<String, Object> rgstinfo = this.handleRegisterData(outptRegisterDTO);
        // 封装病历信息
        List<Map<String, Object>> caseinfo = this.handleBlData(blList);
        // 封装诊断信息
        List<Map<String, Object>> diseinfo = this.handleZdData(zdList);
        // 封装处方信息
        List<Map<String, Object>> rxinfo = this.handleCfData(cfList);

        Map inputMap = new HashMap();
        inputMap.put("rgstinfo", rgstinfo); // 挂号信息dto
        inputMap.put("caseinfo", caseinfo); // 病历信息list
        inputMap.put("diseinfo", diseinfo); // 诊断信息list
        inputMap.put("rxinfo", rxinfo); // 处方信息list

        // 调用统一支付平台接口
        Map httpMap = new HashMap();
        httpMap.put("hospCode",hospCode);
        httpMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        httpMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        httpMap.put("infno", Constant.UnifiedPay.OUTPT.UP_4301); // 交易编号
        httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); // 发送方报文
        httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 参保地医保区划
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("medins_code", insureConfigurationDTO.getOrgCode()); // 定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        httpMap.put("input", inputMap); // 交易输入

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_DIAGNOSIS_TREATMENT_INFO.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital("0");
        interfaceParamDTO.setVisitId(outptRegisterDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_DIAGNOSIS_TREATMENT_INFO, interfaceParamDTO);

        return resultMap;
    }

    /**
     * @Menthod: UP_4302
     * @Desrciption: 医保统一支付平台：急诊留观手术及抢救信息
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-17 19:45
     * @Return:
     **/
    @Override
    public Map<String, Object> UP_4302(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new RuntimeException("未传入医保机构编码");
        }
        List<OperInfoRecordDTO> ssList = MapUtils.get(map, "ssList");
        List<OutptDiagnoseDTO> qjList = MapUtils.get(map, "qjList");
        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");

        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode);
        configDTO.setRegCode(insureRegCode);
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null) throw new RuntimeException("未发现【" + insureRegCode + "】相关医保配置信息");

        // 封装手术信息
        List<Map<String, Object>> oprninfo = this.handleSsData(ssList);
        // 封装抢救信息 todo his没有相关数据，暂时写死
        List<Map<String, Object>> rescinfo = this.handleQjData(qjList);

        Map inputMap = new HashMap();
        inputMap.put("oprninfo", oprninfo); // 手术信息list
        inputMap.put("rescinfo", rescinfo); // 抢救信息list

        // 调用统一支付平台接口
        Map httpMap = new HashMap();
        httpMap.put("hospCode",hospCode);
        httpMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        httpMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        httpMap.put("infno", Constant.UnifiedPay.OUTPT.UP_4302); // 交易编号
        httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); // 发送方报文
        httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 参保地医保区划
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("medins_code", insureConfigurationDTO.getOrgCode()); // 定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        httpMap.put("input", inputMap); // 交易输入


        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_EMERGENCY_RESCUE_INFO.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital("0");
        interfaceParamDTO.setVisitId(outptVisitDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_EMERGENCY_RESCUE_INFO, interfaceParamDTO);

        return resultMap;
    }

    // 封装抢救信息
    private List<Map<String, Object>> handleQjData(List<OutptDiagnoseDTO> qjList) {
        List<Map<String, Object>> rescinfo = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("resc_begntime", DateUtils.getNow()); //抢救开始时间
        map.put("resc_endtime", DateUtils.getNow()); //抢救结束时间
        map.put("er_resc_rec", " "); //急诊抢救记录
        map.put("resc_psn_list", "管理员,医生1"); //参加抢救人员名单
        map.put("vali_flag", Constants.SF.S); //有效标志
        rescinfo.add(map);
        return rescinfo;
    }

    // 封装手术信息
    private List<Map<String, Object>> handleSsData(List<OperInfoRecordDTO> ssList) {
        List<Map<String, Object>> oprninfo = new ArrayList<>();
        if (!ListUtils.isEmpty(ssList)) {
            ssList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("mdtrt_sn", dto.getVisitId()); //就医流水号
                if (!Constants.BRLX.PTBR.equals(dto.getPatientCode())) {
                    if (StringUtils.isNotEmpty(dto.getAac001()) && StringUtils.isNotEmpty(dto.getMedicalRegNo())) {
                        map.put("mdtrt_id", dto.getMedicalRegNo()); //就诊ID，医保登记号
                        map.put("psn_no", dto.getAac001()); //人员编号，个人电脑号
                    } else {
                        throw new RuntimeException("【" + dto.getName() + "】为医保病人，请先进行医保登记");
                    }
                }
                map.put("medrcdno", dto.getOutProfile()); //病历号
                for (int i = 1; i <= ssList.size(); i++) {
                    map.put("oprn_oprt_sn", i); //手术操作序号
                }
                map.put("oprn_oprt_code", dto.getOperDiseaseCode()); //手术操作代码
                map.put("oprn_oprt_name", dto.getOperDiseaseName()); //手术操作名称
                map.put("oprn_oprt_tagt_part_name", " "); //手术操作目标部位名称
                map.put("itvt_name", " "); //介入物名称
                map.put("oprn_oprt_mtd_dscr", dto.getContent()); //手术及操作方法描述
                map.put("oprn_oprt_cnt", 1); //手术及操作次数
                if (dto.getOperEndTime() != null) {
                    map.put("oprn_oprt_time", dto.getOperEndTime()); //手术及操作时间
                } else {
                    throw new RuntimeException("患者【" + dto.getName() + "】的【" + dto.getOperDiseaseName() + "】未完成登记，请先完成手术相关信息");
                }
                map.put("vali_flag", Constants.SF.S); //有效标志
                oprninfo.add(map);
            });
        }
        return oprninfo;
    }

    // 封装处方信息
    private List<Map<String, Object>> handleCfData(List<OutptPrescribeDetailsExtDTO> cfList) {
        List<Map<String, Object>> rxinfo = new ArrayList<>();
        if (!ListUtils.isEmpty(cfList)) {
            cfList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("rxno", dto.getOrderNo()); //处方号
                map.put("rx_prsc_time", dto.getCrteTime()); //处方开方时间
                map.put("rx_type_code", dto.getTypeCode()); //处方类别代码
                map.put("rx_item_type_code", dto.getItemCode()); //处方项目分类代码
                map.put("rx_item_type_name", dto.getItemCodeName()); //处方项目分类名称
                map.put("rx_detl_id", dto.getItemId()); //处方明细代码
                map.put("rx_detl_name", dto.getItemName()); //处方明细名称
                map.put("tcmdrug_type_name", null); //中药类别名称
                map.put("tcmdrug_type_code", null); //中药类别代码
                map.put("tcmherb_foote", dto.getHerbNoteName()); //草药脚注
                map.put("medn_type_code", dto.getDrugTypeCode()); //药物类型代码
                map.put("medn_type_name", dto.getDrugTypeName()); //药物类型
                map.put("drug_dosform", dto.getPrepCode()); //药品剂型
                map.put("drug_dosform_name", dto.getPrepName()); //药品剂型名称
                map.put("drug_spec", dto.getSpec()); //药品规格
                map.put("drug_used_frqu", dto.getRateName()); //药物使用-频率
                map.put("drug_used_idose", dto.getDosage()); //药物使用-总剂量
                map.put("drug_used_sdose", dto.getDosage()); //药物使用-次剂量
                map.put("drug_used_dosunt", dto.getDosageUnitName()); //药物使用-剂量单位
                map.put("drug_used_way_code", dto.getUsageCode()); //药物使用-途径代码，即用药方式
                map.put("drug_medc_way", dto.getUsageCode()); //药物使用-途径，即用药方式
                map.put("skintst_dicm", dto.getIsSkin()); //皮试判别
                map.put("medc_begntime", dto.getMinPlanExecDate()); //用药开始时间
                map.put("medc_endtime", dto.getMaxPlanExecDate()); //用药停止日期时间
                map.put("medc_days", dto.getUseDays()); //用药天数
                map.put("main_medc_flag", dto.getUsageName()); //主要用药标志
                map.put("urgt_flag", null); //加急标志
                map.put("unif_purc_drug", null); //统一采购药品
                map.put("drug_purc_code", null); //药品采购代码
                map.put("drug_mgt_plaf_code", null); //药品管理平台代码
                map.put("bas_medn_flag", null); //基本药物标志
                map.put("vali_flag", Constants.SF.S); //有效标志
                rxinfo.add(map);
            });
        }
        return rxinfo;
    }

    // 封装诊断信息
    private List<Map<String, Object>> handleZdData(List<OutptDiagnoseDTO> zdList) {
        List<Map<String, Object>> diseinfo = new ArrayList<>();
        if (!ListUtils.isEmpty(zdList)) {
            zdList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("tcm_diag_flag", null); //中医诊断标志
                map.put("maindiag_flag", dto.getIsMain()); //主诊断标志
                map.put("diag_code", dto.getDiseaseCode()); //诊断代码
                map.put("diag_name", dto.getDiseaseName()); //诊断名称
                map.put("tcm_dise_code", null); //中医病名代码
                map.put("tcm_dise_name", null); //中医病名名称
                map.put("tcmsymp_code", null); //中医证候代码
                map.put("tcmsymp", null); //中医证候
                map.put("vali_flag", Constants.SF.S); //有效标志
                diseinfo.add(map);
            });
        }
        return diseinfo;
    }

    // 封装病历信息
    private List<Map<String, Object>> handleBlData(List<OutptMedicalRecordDTO> blList) {
        List<Map<String, Object>> caseinfo = new ArrayList<>();
        if (!ListUtils.isEmpty(blList)) {
            blList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("mdtrt_date", dto.getVisitTime()); // 就诊日期
                map.put("chfcomp", dto.getChiefComplaint()); // 主诉
                map.put("attk_date_time", dto.getStartDate()); // 发病日期时间
                map.put("mdtrt_rea", null); // 就诊原因
                map.put("illhis", dto.getPresentIllness()); // 病史
                map.put("algs", dto.getAllergyHistory()); // 过敏史
                map.put("aise_code", null); // 过敏源代码
                map.put("phex", null); // 查体
                map.put("disa_info_code", null); // 残疾情况代码
                map.put("symp_name", null); // 症状名称
                map.put("symp_code", null); // 症状代码
                map.put("dspo_opnn", dto.getHandleSuggestion()); // 处置意见
                map.put("dept_code", dto.getDeptCode()); // 科室代码
                map.put("dept_name", dto.getDeptName()); // 科室名称
                map.put("vali_flag", Constants.SF.S); // 有效标志
                caseinfo.add(map);
            });
        }
        return caseinfo;
    }

    // 封装挂号信息
    private Map<String, Object> handleRegisterData(OutptRegisterDTO outptRegisterDTO) {
        Map<String, Object> rgstinfo = new HashMap();
        rgstinfo.put("mdtrt_sn", outptRegisterDTO.getVisitId()); // 就医流水号
        if (!Constants.BRLX.PTBR.equals(outptRegisterDTO.getPatientCode())) {
            if (StringUtils.isNotEmpty(outptRegisterDTO.getAac001()) && StringUtils.isNotEmpty(outptRegisterDTO.getMedicalRegNo())) {
                rgstinfo.put("mdtrt_id", outptRegisterDTO.getMedicalRegNo()); // 就诊ID，医保登记号
                rgstinfo.put("psn_no", outptRegisterDTO.getAac001()); // 人员编号
            } else {
                throw new RuntimeException("【" + outptRegisterDTO.getName() + "】为医保病人，请先进行医保登记");
            }
        }
        rgstinfo.put("rgst_type_code", outptRegisterDTO.getVisitCode()); // 挂号类别代码
        rgstinfo.put("rgst_way_code", outptRegisterDTO.getSourceBzCode()); // 挂号方式代码
        rgstinfo.put("rgst_serv_fee", outptRegisterDTO.getRealityPrice()); // 挂号费
        rgstinfo.put("ordr_way_code", outptRegisterDTO.getSourceTjCode()); // 预约途径代码
        rgstinfo.put("retnr_flag", outptRegisterDTO.getIsCancel()); // 退号标志
        rgstinfo.put("fstdiag_flag", outptRegisterDTO.getIsFirstVisit()); // 初诊标志
        rgstinfo.put("mdtrt_flag", outptRegisterDTO.getIsVisit()); // 就诊标志
        rgstinfo.put("rgst_retnr_time", outptRegisterDTO.getRegisterTime()); // 挂号/退号时间
        rgstinfo.put("medfee_paymtd_code", null); // 医疗费用支付方式代码
        rgstinfo.put("vali_flag", Constants.SF.S); // 有效标志
        return rgstinfo;
    }

}
