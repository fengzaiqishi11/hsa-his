package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayOutptBO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class InsureUnifiedPayOutptBOImpl extends HsafBO implements InsureUnifiedPayOutptBO {
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
    private BaseDeptService baseDeptService_consumer;
    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService;
    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;
    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;

    private final AtomicInteger atomicInteger = new AtomicInteger(1);


    /**
     * @Description: 门诊患者就诊信息上传，封装入参，调用统一支付平台接口，解析回参
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/10 9:29
     * @Return
     */
    @Override
    public void UP_2203(Map<String, Object> unifiedPayMap) {
        String hospCode = unifiedPayMap.get("hospCode").toString();
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        // 封装患者信息
        Map<String, Object> patientInfo = new HashMap<>(13);
        patientInfo.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo()); // 就诊id Y
        patientInfo.put("psn_no", insureIndividualVisitDTO.getAac001()); // 人员编号 Y
        patientInfo.put("med_type", insureIndividualVisitDTO.getAka130()); // TODO 医疗类别 Y
        patientInfo.put("begntime", DateUtils.format(insureIndividualVisitDTO.getVisitTime(), DateUtils.Y_M_DH_M_S)); // 开始时间 Y
        patientInfo.put("main_cond_dscr", ""); // TODO 主要病情
        patientInfo.put("birctrl_type", insureIndividualVisitDTO.getBirctrlType()); // TODO 计划生育手术类别
        patientInfo.put("birctrl_matn_date", insureIndividualVisitDTO.getBirctrlMatnDate()); // TODO 计划生育手术或生育日期
        patientInfo.put("matn_type", insureIndividualVisitDTO.getMatnType()); // TODO 生育类别
        patientInfo.put("geso_val", "");  // TODO 孕周数
        patientInfo.put("ttp_resp", ""); // TODO 是否第三方责任申请
        patientInfo.put("expi_gestation_nub_of_m", "");  // TODO 是否第三方责任申请

        List<String> diagnoseList = Stream.of("101","102").collect(Collectors.toList());
        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
        outptDiagnoseDTO.setHospCode(hospCode);
        outptDiagnoseDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        outptDiagnoseDTO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());
        unifiedPayMap.put("outptDiagnoseDTO", outptDiagnoseDTO);

        OutptVisitDTO outptVisitDTO = MapUtils.get(unifiedPayMap, "outptVisitDTO");
        SysUserDTO  sysUserDTO = insureIndividualVisitDAO.queryDoctorPracCertiNo(outptVisitDTO);
        String doctorName = sysUserDTO.getName();
        String doctorId = sysUserDTO.getPracCertiNo();
        if(StringUtils.isEmpty(doctorId)){
            throw new AppException("该" +doctorName+"医生的医师国家码没有维护,请去用户管理里面维护");
        }
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        outptPrescribeDTO.setHospCode(hospCode);
        outptPrescribeDTO.setDiagnoseList(diagnoseList);
        unifiedPayMap.put("outptPrescribeDTO",outptPrescribeDTO);
        List<OutptDiagnoseDTO> data = outptDoctorPrescribeService.getOutptDiagnose(unifiedPayMap).getData();
        if(ListUtils.isEmpty(data)) {
            throw new AppException("该患者没有开诊断信息");
        }
        outptDiagnoseDTO.setDiagnoseList(diagnoseList);
        List<OutptDiagnoseDTO> diagnoseDTOList = outptDoctorPrescribeService.queryOutptDiagnose(unifiedPayMap).getData();
        List<Map<String, Object>> mapList = new ArrayList<>();
            commonHandlerDisease(diagnoseDTOList,data);
            // 封装诊断信
        for (int i = 0; i < diagnoseDTOList.size(); i++) {
                Map<String, Object> diagnoseMap = new HashMap<>();
                diagnoseMap.put("diag_type", data.get(i).getTypeCode()); // TODO 诊断类别
                diagnoseMap.put("diag_srt_no", i + 1); // TODO 诊断排序号
                diagnoseMap.put("diag_code", diagnoseDTOList.get(i).getInsureInllnessCode()); //  诊断代码
                diagnoseMap.put("diag_name", diagnoseDTOList.get(i).getInsureInllnessName()); //  诊断名称
                diagnoseMap.put("diag_dept", diagnoseDTOList.get(i).getInDeptName()); //  诊断科室
                if ("1".equals(diagnoseDTOList.get(i).getIsMain())) {
                    diagnoseMap.put("maindiag_flag", "1");
                    diagnoseMap.put("diag_type", "1"); // TODO 诊断类别
                }
                if (StringUtils.isEmpty(diagnoseDTOList.get(i).getPracCertiNo())) {
                    diagnoseMap.put("dise_dor_no", doctorId); //  诊断医生编码
                } else {
                    diagnoseMap.put("dise_dor_no", diagnoseDTOList.get(i).getPracCertiNo()); //  诊断医生编码
                }
                if (StringUtils.isEmpty(diagnoseDTOList.get(i).getZzDoctorName())) {
                    diagnoseMap.put("dise_dor_name", doctorName); //  诊断医生编码
                } else {
                    diagnoseMap.put("dise_dor_name", diagnoseDTOList.get(i).getZzDoctorName()); // 诊断医生姓名
                }
                diagnoseMap.put("diag_time", DateUtils.format(diagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S)); //  诊断时间
                diagnoseMap.put("vali_flag", Constants.SF.S); // TODO 有效标志
                mapList.add(diagnoseMap);
        }
        JSONArray jsonArray = (JSONArray) unifiedPayMap.get("jsonArray");
        List<OutptDiagnoseDTO> collect = diagnoseDTOList.stream().filter(outptDiagnoseDTO1 -> "1".equals(outptDiagnoseDTO1.getIsMain())).collect(Collectors.toList());
        if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006())) {
            patientInfo.put("dise_code", jsonArray.getJSONObject(0).get("insureIllnessCode")); // TODO 病种编号
            patientInfo.put("dise_codg", jsonArray.getJSONObject(0).get("insureIllnessCode")); // add by ljg 病种编号
            patientInfo.put("dise_name", jsonArray.getJSONObject(0).get("insureIllnessName"));  // TODO 病种名称

        } else {
            patientInfo.put("dise_code", insureIndividualVisitDTO.getBka006()); // TODO 病种编号
            patientInfo.put("dise_codg", insureIndividualVisitDTO.getBka006()); // add by ljg 病种编号
            patientInfo.put("dise_name", insureIndividualVisitDTO.getBka006Name());  // TODO 病种名称
        }

        // 调用统一支付平台接口 入参准备
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        String functionCode = Constant.UnifiedPay.OUTPT.UP_2203;
        inputMap.put("infno", functionCode);  // 交易编号
        inputMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        String omsgId =  StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        inputMap.put("msgid", omsgId);
        String medisCode = insureConfigurationDTO.getOrgCode();
        inputMap.put("medins_code", medisCode); // 医疗机构编码
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        dataMap.put("mdtrtinfo", patientInfo);
        dataMap.put("diseinfo", mapList);
        inputMap.put("input", dataMap);  // 入参具体数据
        String url = insureConfigurationDTO.getUrl();
        String json = JSONObject.toJSONString(inputMap);
        logger.info("统一医保支付平台门诊就诊信息入参:" + json);
        // 调用统一支付平台接口
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        unifiedPayMap.put("medisCode",medisCode);
        unifiedPayMap.put("msgId",omsgId);
        unifiedPayMap.put("msgInfo",functionCode);
        unifiedPayMap.put("msgName","门诊就诊信息上传");
        unifiedPayMap.put("isHospital",Constants.SF.F) ;
        unifiedPayMap.put("paramMapJson",json);
        unifiedPayMap.put("resultStr",resultJson);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(unifiedPayMap).getData();

        logger.info("统一医保支付平台门诊就诊信息回参:" + resultJson);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }

    }
    /**
     * @Method commonHandlerDisease
     * @Desrciption  医保入院登记和出院办理时，验证诊断是否匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 9:01
     * @Return
     **/
    private void commonHandlerDisease(List<OutptDiagnoseDTO> inptDiagnoseDTOList,List<OutptDiagnoseDTO> data) {
        List<OutptDiagnoseDTO> list = data.stream().filter(inptDiagnoseDTO ->
                Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())).collect(Collectors.toList());
        int size = list.size();
        if(size == 0){
            throw new AppException("没有门诊主诊断");
        }
        if(size >1){
            throw new AppException("门诊主诊断数量大于1");
        }
        if(inptDiagnoseDTOList.size() != data.size()){
            List<String> dataCollect = data.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> inptDataCollect = inptDiagnoseDTOList.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> collect = dataCollect.stream().filter(item -> !inptDataCollect.contains(item)).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            if(!ListUtils.isEmpty(collect)) {
                for (String s : collect) {
                    stringBuilder.append(s).append(",");
                }
                throw new AppException("该患者开的"+stringBuilder+"还没有进行疾病匹配,请先做好匹配工作");
            }
        }

    }
    /**
     * @Description: 门诊患者就诊费用明细上传
     * 1.根据就诊id 和医院编码获取医保的就诊信息。判断是否是医保病人
     * 2.根据医保就诊表的参保地区划和医院编码获得调用医保的url的地址
     * 3.判断门诊费用是否已经上传到了医保
     * 4.如果没有上传则保存到医保费用表 否则给出提示
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/10 11:44
     * @Return
     */
    @Override
    public Boolean insertOutptUnifiedFee(Map<String, Object> unifiedPayMap) {
        String hospCode = unifiedPayMap.get("hospCode").toString();
        /**
         * 获取医保就诊信息
         */
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);
        unifiedPayMap.put("visitId", insureIndividualVisitDTO.getVisitId());
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        unifiedPayMap.put("code", "HOSP_APPR_FLAG");
        String batchNo = MapUtils.get(unifiedPayMap, "batchNo").toString();
        String hospApprFlag = ""; // 医院审批标志
        String hnFeedetlSn= ""; // 湖南费用流水号特有标识
        String hnSpecial = ""; // 针对海南特有的报销药
        String huNanSpecial = ""; // 针对湖南中药饮片报销
        String patientSum = ""; // 个人累计
        String guangZhouSpecial = "";//针对广州的中药饮片报销
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(unifiedPayMap).getData();
        if (sysParameterDTO != null && !StringUtils.isEmpty(sysParameterDTO.getValue())) {
            String value = sysParameterDTO.getValue();
            Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
            if (stringObjectMap.containsKey(insureConfigurationDTO.getRegCode())) {
                for (String key : stringObjectMap.keySet()) {
                    if (key.equals(insureConfigurationDTO.getRegCode())) {
                        hospApprFlag = MapUtils.get(stringObjectMap, key);
                    }
                    if ("hnSpecial".equals(key)) {
                        hnSpecial = MapUtils.get(stringObjectMap, key);
                    }
                    if("hnFeedetlSn".equals(key)){ // 湖南省特有的费流水号标识
                        hnFeedetlSn = MapUtils.get(stringObjectMap,key);
                    }
                    if ("huNanSpecial".equals(key)) {
                        huNanSpecial = MapUtils.get(stringObjectMap, key);
                    }
                    if("patientSum".equals(key)){
                        patientSum =  MapUtils.get(stringObjectMap, key);
                    }
                    if ("guangZhouSpecial".equals(key)) {
                        guangZhouSpecial = MapUtils.get(stringObjectMap, key);
                    }

                }
            }
        }
        // 门诊费用明细（调统一支付平台入参）
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> costList = (List<Map<String, Object>>) unifiedPayMap.get("insureCostList");
        String medinsCode = insureConfigurationDTO.getOrgCode();
        String medicalRegNo  = insureIndividualVisitDTO.getMedicalRegNo();
        unifiedPayMap.put("medicalRegNo",medicalRegNo);
        unifiedPayMap.put("medinsCode",medinsCode);
        unifiedPayMap.put("psnNo",insureIndividualVisitDTO.getAac001());
        unifiedPayMap.put("visitId",insureIndividualVisitDTO.getVisitId());
        String feeNum =  insureIndividualCostDAO.selectLastFeedSn(unifiedPayMap); // 判读是否是第一次传输
        if (!ListUtils.isEmpty(costList)) {
            Map<String, Object> tempMap = costList.get(0);
            String pracCertiNo = MapUtils.get(tempMap, "pracCertiNo");
            String doctorName = MapUtils.get(tempMap, "doctorName");
            Map<String, Object> costInfoMap = null;
            Boolean isCompound = false;
            for (Map<String, Object> map : costList) {
                if ("103".equals(MapUtils.get(map, "insureItemType")) && "1".equals(MapUtils.get(map, "tcmdrugUsedWay"))) {
                    isCompound = true;
                    break;
                }
            }
            Integer k =1;
            for (Map<String, Object> map : costList) {
                costInfoMap = new HashMap<>();
                if("1".equals(hnFeedetlSn)){
                    if(StringUtils.isEmpty(feeNum)){
                        atomicInteger.getAndAdd(2);
                        costInfoMap.put("feedetl_sn", atomicInteger.get()); // 费用明细流水号
                    }else{
                        k = Integer.parseInt(feeNum);
                        k+=2;
                        feeNum = k.toString();
                        costInfoMap.put("feedetl_sn",k) ; // 费用明细流水号
                    }
                }
                else{
                    costInfoMap.put("feedetl_sn", MapUtils.get(map, "id")); // 费用明细流水号
                }
                k=k+2;
                costInfoMap.put("id", MapUtils.get(map, "id")); // 费用明细流水号
                costInfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo()); // 就诊id
                costInfoMap.put("psn_no", insureIndividualVisitDTO.getAac001()); // 人员编号
                if (StringUtils.isEmpty(batchNo)) {
                    costInfoMap.put("chrg_bchno", "0"); // 收费批次号
                } else {
                    costInfoMap.put("chrg_bchno", batchNo); // 收费批次号
                }
                if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006())) {
                    costInfoMap.put("dise_code", insureIndividualVisitDTO.getVisitIcdCode());  // TODO 病种编码
                } else {
                    costInfoMap.put("dise_code", insureIndividualVisitDTO.getBka006());
                }
                costInfoMap.put("rxno", map.get("opId") == null ? "" : map.get("opId").toString()); // 处方号
                costInfoMap.put("rx_circ_flag", Constants.SF.F); // TODO 外购处方标志
                costInfoMap.put("fee_ocur_time", DateUtils.format((Date) map.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间
                costInfoMap.put("med_list_codg", map.get("insureItemCode") == null ? "" : map.get("insureItemCode").toString()); // TODO 医疗目录编码
                costInfoMap.put("medins_list_codg", map.get("hospItemCode") == null ? "" : map.get("hospItemCode").toString()); // TODO 医疗机构目录编码
                costInfoMap.put("med_list_name", map.get("insureItemName") == null ? "" : map.get("insureItemName").toString()); // TODO 医疗机构目录编码
                costInfoMap.put("lis_type", map.get("insureItemType") == null ? "" : map.get("insureItemType").toString()); // TODO 医疗机构目录编码
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(map.get("realityPrice").toString()));
                costInfoMap.put("det_item_fee_sumamt", BigDecimalUtils.convert(realityPrice)); // 明细项目费用总额
                costInfoMap.put("cnt", BigDecimalUtils.scale((BigDecimal) map.get("totalNum"), 4));//  数量
                costInfoMap.put("pric", MapUtils.get(map, "price"));// 单价


                costInfoMap.put("sin_dos_dscr", ""); // 单次计量描述
                costInfoMap.put("used_frqu_dscr", null); // 使用频次描述
                costInfoMap.put("prd_days", null); // 周期天数
                costInfoMap.put("medc_way_dscr", null); // TODO 用药途径描述
                costInfoMap.put("bilg_dept_codg", MapUtils.get(map, "deptId")); // 开单科室编码
                costInfoMap.put("bilg_dept_name", MapUtils.get(map, "deptName")); // 开单科室名称
                if (StringUtils.isEmpty(MapUtils.get(map, "pracCertiNo"))) {
                    costInfoMap.put("bilg_dr_codg", pracCertiNo); // 开单医生编码
                } else {
                    costInfoMap.put("bilg_dr_codg", MapUtils.get(map, "pracCertiNo")); // 开单医生编码
                }
                if (StringUtils.isEmpty(MapUtils.get(map, "doctorName"))) {
                    costInfoMap.put("bilg_dr_name", doctorName); // 开单医生姓名
                } else {
                    costInfoMap.put("bilg_dr_name", MapUtils.get(map, "doctorName")); // 开单医生姓名
                }
                costInfoMap.put("acord_dept_codg", null); // 受单科室编码
                costInfoMap.put("acord_dept_name", null); // 受单科室名称
                costInfoMap.put("orders_dr_code", null); // TODO 受单医生编码
                costInfoMap.put("orders_dr_name", null); // TODO 受单医生姓名
                String lmtUserFlag = MapUtils.get(map, "lmtUserFlag");

                /**
                 * hnSpecial:'1' 表示海南
                 * huNanSpecial:'1' 表示湖南
                 * hosp_appr_flag: 医院审批标志
                 */
                costInfoMap.put("hosp_appr_flag", hospApprFlag);
                // 海南省 + (药品和材料) + 限制级用药标志为 '0' ,则报销
                if ("1".equals(hnSpecial) && "1".equals(lmtUserFlag)) {
                    costInfoMap.put("hosp_appr_flag", "1");
                // 其他省份：(药品和材料) + (xzbzFlag = 1) +  (xzbzbxFlag != 1)  不报销
                } else if ("0".equals(lmtUserFlag)) {
                        costInfoMap.put("hosp_appr_flag", "2");
                }

                // 湖南省医保中药饮片中出现了复方药物，则中药饮片全部报销
                if ("1".equals(huNanSpecial) && isCompound && "103".equals(MapUtils.get(map, "insureItemType"))) {
                    costInfoMap.put("hosp_appr_flag", "1");
                    costInfoMap.put("tcmdrug_used_way","1");
                } else if ("1".equals(huNanSpecial) && !isCompound && "103".equals(MapUtils.get(map, "insureItemType"))) {
                    costInfoMap.put("hosp_appr_flag", "0");
                    costInfoMap.put("tcmdrug_used_way","2");
                }else if("1".equals(huNanSpecial) && "0".equals(lmtUserFlag)){
                    costInfoMap.put("hosp_appr_flag", "0");
                } else if ("1".equals(guangZhouSpecial) && isCompound && "102".equals(MapUtils.get(map, "insureItemType"))) {
                    // 广州的102是中药饮片
                    costInfoMap.put("hosp_appr_flag", "1");
                    costInfoMap.put("tcmdrug_used_way","1");
                }

                costInfoMap.put("tcmdrug_used_way", null); // TODO 中药使用方式
                costInfoMap.put("etip_flag", null); // TODO 外检标志
                costInfoMap.put("etip_hosp_code", null); // TODO 外检医院编码
                costInfoMap.put("dscg_tkdrug_flag", null); // TODO 出院带药标志
                costInfoMap.put("matn_fee_flag", null); // TODO 生育费用标志
                costInfoMap.put("id",MapUtils.get(map, "id"));
                list.add(costInfoMap);
            }
        }
        // 调用统一支付平台 上传费用明细
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();

        /**
         * 公共入参
         */
        inputMap.put("infno", Constant.UnifiedPay.OUTPT.UP_2204);  // 交易编号
        inputMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());  //TODO 参保地医保区划
        inputMap.put("medins_code", medinsCode); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        dataMap.put("feedetail", list);
        inputMap.put("input", dataMap);  // 入参具体数据

        String json = JSONObject.toJSONString(inputMap);
        logger.info("统一支付平台门诊费用传输入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new RuntimeException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new RuntimeException((String) resultMap.get("err_msg"));
        }
        logger.info("统一支付平台门诊费用传输回参:" + resultJson);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "result");
        insertInsureCost(resultDataMap, unifiedPayMap,list);
        // 湖南这边系统参数值设置不为1  需要保存个人累计信息 方便打印结算单
        if(!"1".equals(patientSum)){
            insertPatientSumInfo(unifiedPayMap);
        }
        return true;
    }



    /**
     * @Method updateInsureCost
     * @Desrciption  费用传输以后：更新医保的反参数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/21 8:35
     * @Return
     **/
    private void insertInsureCost(List<Map<String, Object>> resultDataMap, Map<String, Object> unifiedPayMap, List<Map<String, Object>> list) {
        String batchNo = MapUtils.get(unifiedPayMap,"batchNo").toString();
        String hospCode  = MapUtils.get(unifiedPayMap,"hospCode");
        String visitId = MapUtils.get(unifiedPayMap,"visitId");
        String insureRegisterNo = MapUtils.get(unifiedPayMap,"medicalRegNo");
        String crteName = MapUtils.get(unifiedPayMap,"crteName");
        String crteId  = MapUtils.get(unifiedPayMap,"crteId");
        String selfpayProp = ""; // 自付比例
        String overlmtAmt = ""; // 超限价金额
        String preselfpayAmt = ""; // 先行自付金额
        String inscpScpAmt = ""; // 符合政策范围金额
        String fulamtOwnpayAmt =  ""; // 全自费金额
        String detItemFeeSumamt = ""; // 明细金额
        InsureIndividualCostDTO insureIndividualCostDTO = null;
        List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
        if(!ListUtils.isEmpty(list)){
            Map<Object, Map<String, Object>> feedetlSnMap = list.stream().collect(Collectors.toMap(Map -> Map.get("feedetl_sn"), Map -> Map, (k1, k2) -> k1));
            Map<Object, Map<String, Object>> newFeedetlSnMap = new HashMap<>();
            if(!MapUtils.isEmpty(feedetlSnMap)){
                for(Map.Entry entry : feedetlSnMap.entrySet()){
                    newFeedetlSnMap.put(entry.getKey().toString(), (Map<String, Object>) entry.getValue());
                }
                if(!ListUtils.isEmpty(resultDataMap)){
                    for(Map<String, Object> item : resultDataMap){
                        DecimalFormat df1 = new DecimalFormat("0.00");
                        String feedetlSn = MapUtils.get(item,"feedetl_sn");
                        Map<String, Object> feedetlSnObjectMap = newFeedetlSnMap.get(feedetlSn);
                        insureIndividualCostDTO = new InsureIndividualCostDTO();
                        insureIndividualCostDTO.setId(SnowflakeUtils.getId());
                        insureIndividualCostDTO.setHospCode(hospCode);
                        insureIndividualCostDTO.setVisitId(visitId);//就诊id
                        insureIndividualCostDTO.setSettleId(null);
                        insureIndividualCostDTO.setBatchNo(batchNo);
                        insureIndividualCostDTO.setIsHospital(Constants.SF.F);
                        insureIndividualCostDTO.setItemType(MapUtils.get(feedetlSnObjectMap,"list_type"));
                        insureIndividualCostDTO.setItemCode(MapUtils.get(feedetlSnObjectMap,"med_list_codg"));
                        insureIndividualCostDTO.setItemName(MapUtils.get(feedetlSnObjectMap,"med_list_name"));
                        insureIndividualCostDTO.setCostId(MapUtils.get(feedetlSnObjectMap,"id"));//费用id
                        insureIndividualCostDTO.setFeedetlSn(MapUtils.get(item,"feedetl_sn")==null?"":MapUtils.get(item,"feedetl_sn").toString()); // 费用明细流水号(上传到医保)
                        insureIndividualCostDTO.setApplyLastPrice(null);
                        insureIndividualCostDTO.setOrderNo(""); // 顺序号
                        insureIndividualCostDTO.setTransmitCode("1"); // 是否传输标志
                        insureIndividualCostDTO.setCrteId(crteId);
                        insureIndividualCostDTO.setRxSn(null);
                        insureIndividualCostDTO.setInsureSettleId(null);
                        insureIndividualCostDTO.setCrteName(crteName);
                        insureIndividualCostDTO.setCrteTime(DateUtils.getNow());
                        insureIndividualCostDTO.setInsureRegisterNo(insureRegisterNo); // 就医登记号
                        insureIndividualCostDTO.setIsHalfSettle(Constants.SF.F); // 是否中途结算
                        insureIndividualCostDTO.setInsureIsTransmit("1");
                        insureIndividualCostDTO.setPricUplmtAmt(null);
                        insureIndividualCostDTO.setHiLmtpric(null);
                        insureIndividualCostDTO.setOverlmtSelfpay(null);
                        selfpayProp = DataTypeUtils.dataToNumString(MapUtils.get(item, "selfpay_prop")); // 自付比例
                        overlmtAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "overlmt_amt")); // 超限价金额
                        preselfpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "preselfpay_amt")); // 先行自付金额
                        inscpScpAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "inscp_scp_amt")); // 符合政策范围金额
                        fulamtOwnpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "fulamt_ownpay_amt")); // 全自费金额
                        detItemFeeSumamt = DataTypeUtils.dataToNumString(MapUtils.get(item, "det_item_fee_sumamt")); // 明细金额
                        insureIndividualCostDTO.setPrimaryPrice(BigDecimalUtils.convert(MapUtils.get(feedetlSnObjectMap,"det_item_fee_sumamt")==null?"":MapUtils.get(feedetlSnObjectMap,"det_item_fee_sumamt").toString())); // 上传到医保的费用
                        insureIndividualCostDTO.setGuestRatio(selfpayProp); // 自付比例
                        insureIndividualCostDTO.setInscpScpAmt(BigDecimalUtils.convert(inscpScpAmt)); // 符合政策范围金额
                        insureIndividualCostDTO.setFulamtOwnpayAmt(BigDecimalUtils.convert(fulamtOwnpayAmt));
                        insureIndividualCostDTO.setOverlmtSelfpay(BigDecimalUtils.convert(overlmtAmt));
                        insureIndividualCostDTO.setOverlmtAmt(BigDecimalUtils.convert(overlmtAmt));
                        insureIndividualCostDTO.setPreselfpayAmt(BigDecimalUtils.convert(preselfpayAmt)); // 先行自付金额
                        insureIndividualCostDTO.setDetItemFeeSumamt(BigDecimalUtils.convert(detItemFeeSumamt)); // 总金额
                        insureIndividualCostDTO.setMedChrgitmType(MapUtils.get(item,"med_chrgitm_type")); //医疗收费项目类别
                        insureIndividualCostDTO.setLmtUsedFlag(MapUtils.get(item,"lmt_used_flag"));
                        insureIndividualCostDTO.setChrgItemLv(MapUtils.get(item,"chrgitm_lv"));
                        insureIndividualCostDTO.setSumFee(null); // 本次上传到医保的费用总金额
                        insureIndividualCostDTO.setFeeStartTime(null);
                        insureIndividualCostDTO.setFeeEndTime(null);
                        insureIndividualCostDTO.setSettleCount(0); // 中途结算次数
                        individualCostDTOList.add(insureIndividualCostDTO);
                    }
                }
            }
        }
        if(!ListUtils.isEmpty(individualCostDTOList)){
            insureIndividualCostDAO.batchInsertCost(individualCostDTOList);
        }
    }

    /**
     * @Method 门诊费用传输以后，需要保存个人累计信息
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/23 14:27
     * @Return
     **/
    private void insertPatientSumInfo(Map<String, Object> map) {
        String crteName = MapUtils.get(map,"crteName"); //  创建人姓名
        String crteId = MapUtils.get(map,"crteId"); // 创建人id
        String visitId = MapUtils.get(map,"visitId"); // 就诊id
        String hospCode = MapUtils.get(map,"hospCode"); // 医院编码
        String psnNo = MapUtils.get(map,"psnNo"); // 个人编号
        String medisCode = MapUtils.get(map,"medinsCode"); // 医疗机构编码
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        String batchNo = MapUtils.get(map,"batchNo").toString(); // 批次号
        Map<String, Object> dataMap = insureUnifiedBaseService.queryPatientSumInfo(map).getData();
        List<Map<String, Object>> resultDataMap = MapUtils.get(dataMap,"resultDataMap");
        if(!ListUtils.isEmpty(resultDataMap)){
            resultDataMap.stream().forEach(item->{
                item.put("id",SnowflakeUtils.getId());
                item.put("crteName",crteName);
                item.put("medicalRegNo",medicalRegNo);
                item.put("crteId",crteId);
                item.put("visitId",visitId);
                item.put("hospCode",hospCode);
                item.put("psnNo",psnNo);
                item.put("medisCode",medisCode);
                item.put("batchNo",batchNo);
                item.put("crteTime",DateUtils.getNow());
            });
            insureIndividualVisitDAO.deletePatientSumInfo(map);
            insureIndividualVisitDAO.insertPatientSumInfo(resultDataMap);
        }
    }

    /**
     * @Method updateInsureCost
     * @Desrciption  费用传输成功以后保留医保费用反参
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/29 8:56
     * @Return
    **/
    private void updateInsureCost(List<Map<String, Object>> resultDataMap, Map<String, Object> unifiedPayMap) {
        String hospCode = MapUtils.get(unifiedPayMap, "hospCode");
        String visitId = MapUtils.get(unifiedPayMap, "visitId");
        InsureIndividualCostDTO insureIndividualCostDTO;
        List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
        for (Map<String, Object> item : resultDataMap) {
            insureIndividualCostDTO = new InsureIndividualCostDTO();
            insureIndividualCostDTO.setVisitId(visitId);
            insureIndividualCostDTO.setFeedetlSn(MapUtils.get(item, "feedetl_sn")); // 费用明细流水号
            insureIndividualCostDTO.setHospCode(hospCode);
            String selfpayProp = DataTypeUtils.dataToNumString(MapUtils.get(item, "selfpay_prop")); // 自付比例
            String overlmtAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "overlmt_amt")); // 超限价金额
            String preselfpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "preselfpay_amt")); // 先行自付金额
            String inscpScpAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "inscp_scp_amt")); // 符合政策范围金额
            String fulamtOwnpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "fulamt_ownpay_amt")); // 全自费金额
            insureIndividualCostDTO.setGuestRatio(selfpayProp);
            insureIndividualCostDTO.setOverlmtAmt(BigDecimalUtils.convert(overlmtAmt));
            insureIndividualCostDTO.setPreselfpayAmt(BigDecimalUtils.convert(preselfpayAmt));
            insureIndividualCostDTO.setInscpScpAmt(BigDecimalUtils.convert(inscpScpAmt));
            insureIndividualCostDTO.setFulamtOwnpayAmt(BigDecimalUtils.convert(fulamtOwnpayAmt));
            insureIndividualCostDTO.setMedChrgitmType(MapUtils.get(item, "med_chrgitm_type")); //医疗收费项目类别
            insureIndividualCostDTO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag"));
            insureIndividualCostDTO.setChrgItemLv(MapUtils.get(item, "chrgitm_lv"));
            individualCostDTOList.add(insureIndividualCostDTO);
        }
        if (!ListUtils.isEmpty(individualCostDTOList)) {
            insureIndividualCostDAO.updateFeeUploadResult(individualCostDTOList);
        }
    }


    /**
     * @Description: 门诊费用明细撤销
     * @Param: 根据批次号 来进行门诊费用明细的撤销操作
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/15 11:44
     * @Return
     */
    @Override
    public Boolean UP_2205(Map<String, Object> unifiedPayMap) {
        String hospCode = unifiedPayMap.get("hospCode").toString();
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) unifiedPayMap.get("insureIndividualVisitDTO");
        if (insureIndividualVisitDTO == null) {
            insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);
        }
        /**
         * 获取访问的url地址
         */
        String visitId = insureIndividualVisitDTO.getVisitId();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        String feeBatch = insureIndividualCostDAO.selectLastCostInfo(unifiedPayMap);
        // TODO 调用统一支付平台
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        String functionCode = Constant.UnifiedPay.OUTPT.UP_2205;
        inputMap.put("infno", functionCode);  // 交易编号
        inputMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());//insureVisit.getInsuplcAdmdvs());  //TODO 参保地医保区划
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        inputMap.put("medins_code", medisCode); // 医疗机构编码
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        String omsgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        inputMap.put("msgid", omsgId);
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo()); // 就诊ID
        // 当费用批次号不为空时 撤销已经上传的费用信息  批次号为空时 撤销所有的费用的信息
        dataMap.put("chrg_bchno", "0000");
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());

        inputMap.put("input", dataMap);  // 入参具体数据

        String url = insureConfigurationDTO.getUrl();
        String json = JSONObject.toJSONString(inputMap);
        logger.info("统一支付平台门诊费用撤销入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        unifiedPayMap.put("medisCode",medisCode);
        unifiedPayMap.put("visitId",visitId);
        unifiedPayMap.put("msgId",omsgId);
        unifiedPayMap.put("msgInfo",functionCode);
        unifiedPayMap.put("msgName","门诊费用明细撤销");
        unifiedPayMap.put("isHospital",Constants.SF.F) ;
        unifiedPayMap.put("paramMapJson",json);
        unifiedPayMap.put("resultStr",resultJson);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(unifiedPayMap).getData();


        logger.info("统一支付平台门诊费用撤销回参:" + resultJson);
        // 统一支付平台回参

        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        return true;

    }

    /**
     * @Description: 门诊预结算
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/15 12:00
     * @Return
     */
    @Override
    public Map<String, Object> UP_2206(Map<String, Object> unifiedPayMap) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(unifiedPayMap, "outptVisitDTO");
        String hospCode = MapUtils.get(unifiedPayMap, "hospCode");
        String batchNo = MapUtils.get(unifiedPayMap, "batchNo").toString();
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);
        String visitId = insureIndividualVisitDTO.getVisitId();
        String userName = MapUtils.get(unifiedPayMap,"crteName");
        String code = MapUtils.get(unifiedPayMap,"code");
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);

        /**
         * 获取预结算费用信息
         */

        Map<String, Object> costMapInfo = getInptTrialCostInfo(unifiedPayMap);
        unifiedPayMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
//		Map<String, Object> commonInsureCostMap = commonInsureCost(unifiedPayMap);
        // 入参，患者信息
        Map<String, Object> patientDataMap = new HashMap<>();
        patientDataMap.put("psn_no", insureIndividualVisitDTO.getAac001()); // 人员编号
        patientDataMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType()); //  就诊凭证类型
        patientDataMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo()); //  就诊凭证编号
        patientDataMap.put("med_type", insureIndividualVisitDTO.getAka130()); //  医疗类别
        DecimalFormat df1 = new DecimalFormat("0.00");
        String realityPrice = df1.format(BigDecimalUtils.convert(costMapInfo.get("costStr").toString()));
        patientDataMap.put("medfee_sumamt", BigDecimalUtils.convert(realityPrice));// 医疗费总额
        patientDataMap.put("psn_setlway", Constants.JSFS.PTJS); //  个人结算方式
        patientDataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo()); // 就诊id
        if (StringUtils.isEmpty(batchNo)) {
            patientDataMap.put("chrg_bchno", "0"); // 收费批次号
        } else {
            patientDataMap.put("chrg_bchno", batchNo); // 收费批次号
        }
        patientDataMap.put("acct_used_flag", outptVisitDTO.getIsUseAccount()); // TODO 个人账户使用标志
        patientDataMap.put("insutype", insureIndividualVisitDTO.getAae140()); // 险种类型
        patientDataMap.put("med_mdtrt_type", insureIndividualVisitDTO.getBka006()); //  医疗待遇类型
        patientDataMap.put("dept_code", insureIndividualVisitDTO.getVisitDrptId()); // 科室编码
        patientDataMap.put("dept_name", insureIndividualVisitDTO.getVisitDrptName()); // 科室名称
        patientDataMap.put("wardarea_code", null); // 病区编码
        patientDataMap.put("wardarea_name", null); // 病区名称
        if ("settle".equals(unifiedPayMap.get("action") == null ? "" : unifiedPayMap.get("action").toString())) {
            patientDataMap.put("save_flag", Constants.SF.S); // TODO 保存标识
            patientDataMap.put("selt_flag", Constants.SF.S); // 结算标识
        } else {
            patientDataMap.put("save_flag", Constants.SF.F); // TODO 保存标识
            patientDataMap.put("selt_flag", Constants.SF.F); // 结算标识
        }
        patientDataMap.put("photo_base64", null); // TODO 照片base64编码
        patientDataMap.put("is_elect_cert", Constants.SF.F); // TODO 是否医保电 子凭证刷卡
        patientDataMap.put("elect_certno", null); // TODO 扫描医保电 子凭证返回 的值
        patientDataMap.put("certno", null); // 身份证号
        patientDataMap.put("psn_cert_type", null); // 证件类型
        patientDataMap.put("elect_cert_token", ""); // TODO 医保电子凭 证 Token
        patientDataMap.put("acct_pay", ""); // TODO 个人账户支付金额
        patientDataMap.put("injury_borth_sn", ""); // TODO 工伤/生育编码
        patientDataMap.put("rxno", ""); // TODO 处方号
        patientDataMap.put("bilg_dr_codg", ""); // TODO 处方医生编号
        patientDataMap.put("bilg_dr_name", ""); // TODO 处方医生名称
        patientDataMap.put("memo", ""); // TODO 备注
        patientDataMap.put("serial_apply", ""); // TODO 特殊门诊申请序号
        patientDataMap.put("bill_no", ""); // TODO 单据号
        patientDataMap.put("cash_pay", ""); // TODO 现金支付
        patientDataMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs()); //  统筹区编码
        patientDataMap.put("opter", insureIndividualVisitDTO.getCrteId()); // 登记人员工号
        patientDataMap.put("opter_name", insureIndividualVisitDTO.getCrteName()); // 登记人姓名
        patientDataMap.put("medins_code", outptVisitDTO.getHospCode()); // 医疗机构编码
        patientDataMap.put("medins_name", outptVisitDTO.getHospName()); // TODO 医疗机构名称
        patientDataMap.put("mdtrt_mode", ""); // TODO 就诊方式
        patientDataMap.put("order_no", ""); // TODO 医疗机构订单号或医疗机构就医序列号
        patientDataMap.put("hcard_basinfo", ""); // TODO 持卡就诊基本信息
        patientDataMap.put("hcard_chkinfo", ""); // TODO 持卡就诊校验信息
        // 门诊费用明细
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> costList = new ArrayList<>();
        if (ListUtils.isEmpty(MapUtils.get(unifiedPayMap, "costList"))) {
            costList = MapUtils.get(unifiedPayMap, "costList");
        } else {
            Map<String, Object> stringObjectMap = commonInsureCost(unifiedPayMap);
            costList = MapUtils.get(stringObjectMap, "insureCostList");
        }
        if (!ListUtils.isEmpty(costList)) {
            for (Map<String, Object> map : costList) {
                Map<String, Object> costMap = new HashMap<>();
                costMap.put("feedetl_sn", map.get("id").toString()); // 费用明细流水号
                costMap.put("rxno", map.get("opId") == null ? "" : map.get("opId").toString()); // 处方号
                costMap.put("rx_circ_flag", Constants.SF.F); //  外购处方标志
                costMap.put("fee_ocur_time", DateUtils.format((Date) map.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间
                costMap.put("med_list_code", map.get("insureItemCode") == null ? "" : map.get("insureItemCode").toString()); //  医疗目录编码
                costMap.put("medins_list_code", map.get("hospItemCode") == null ? "" : map.get("hospItemCode").toString()); //  医药机构目录编码

                String realityDetailPrice = df1.format(BigDecimalUtils.convert(map.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityDetailPrice);
                costMap.put("det_item_fee_sumamt",convertPrice); // 明细项目费用总额
                costMap.put("cnt", BigDecimalUtils.scale(MapUtils.get(map, "totalNum"), 4)); // 数量
//                costMap.put("pric",  BigDecimalUtils.scale(MapUtils.get(map, "price"), 4)); // 单价
                costMap.put("sin_dos_dscr", null); // 单次剂量描述
                costMap.put("used_frqu_dscr", null); // 使用频次描述
                costMap.put("prd_days", null); // 周期天数
                costMap.put("medc_way_dscr", null); // TODO 用药途径描述
                costMap.put("bilg_dr_codg", null); // 开单医生编码
                costMap.put("bilg_dr_name", null); // 开单医师姓名
                costMap.put("tcmdrug_used_way", null); //  中药使用方式
                costMap.put("medins_list_name", null); //  医疗机构目录名称
                costMap.put("drug_dosform", null); // 剂型
                costMap.put("spec", null); // 规格
                costMap.put("prdr_name", null); // TODO 厂家
                String insureItemType = MapUtils.get(map, "insureItemType");
                costMap.put("list_type", insureItemType); // 项目药品类型
                costMap.put("med_chrgitm_type", ""); // TODO 统计类别
                String yybs = "0";
                if (Constants.YYXZ.CG.equals(map.get("useCode") == null ? "" : map.get("useCode").toString())) {
                    yybs = "0";
                } else if (Constants.YYXZ.CYDY.equals(map.get("useCode") == null ? "" : map.get("useCode").toString())) {
                    yybs = "1";
                } else {
                    yybs = "2";
                }
                costMap.put("used_flag", yybs); //  用药标识
                costMap.put("used_days", null); // TODO 出院带药天数
                costMap.put("opp_serial_fee", null); // TODO 对应费用序列号
                costMap.put("opter", outptVisitDTO.getCrteId()); //  录入工号
                costMap.put("opter_name", outptVisitDTO.getCrteName()); //  录入人
                costMap.put("opt_time", outptVisitDTO.getCrteTime()); //  录入日期
                list.add(costMap);
            }
        }
        // TODO 调用统一支付平台接口
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();

        String functionCode = "";
        if ("settle".equals(unifiedPayMap.get("action") == null ? "" : unifiedPayMap.get("action").toString())) {
            /**
             * 公共入参
             */
            functionCode = Constant.UnifiedPay.OUTPT.UP_2207;
            inputMap.put("infno", functionCode);  // 交易编号

        } else {
            functionCode = Constant.UnifiedPay.OUTPT.UP_2206;
            inputMap.put("infno", functionCode);  // 交易编号
        }
        String omsgid = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        inputMap.put("msgid",omsgid);
        inputMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());  //参保地医保区划
        String medisCode = insureConfigurationDTO.getOrgCode();
        inputMap.put("medins_code", medisCode); // 医疗机构编码
        inputMap.put("opter",code);
        inputMap.put("opter_name",userName);
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        dataMap.put("data", patientDataMap);
        dataMap.put("feeinfo", list);
        inputMap.put("input", dataMap);  // 入参具体数据

        String url = insureConfigurationDTO.getUrl();
        String json = JSONObject.toJSONString(inputMap);
        logger.info("医保通统一支付平台门诊预结算/结算入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);

        if("settle".equals(unifiedPayMap.get("action") == null ? "" :unifiedPayMap.get("action").toString())) {
            unifiedPayMap.put("msgName","医保门诊结算");
        }else{
            unifiedPayMap.put("msgName","医保门诊试算");
        }
        unifiedPayMap.put("medisCode",medisCode);
        unifiedPayMap.put("visitId",visitId);
        unifiedPayMap.put("msgId",omsgid);
        unifiedPayMap.put("msgInfo",functionCode);
        unifiedPayMap.put("isHospital",Constants.SF.F) ;
        unifiedPayMap.put("paramMapJson",json);
        unifiedPayMap.put("resultStr",resultJson);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(unifiedPayMap);

        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        logger.info("医保通统一支付平台门诊预结算/结算回参:" + resultJson);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> settleDataMap = (Map<String, Object>) outputMap.get("setlinfo");
        settleDataMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo());
        settleDataMap.put("hospCode", hospCode);
        if ("settle".equals(unifiedPayMap.get("action") == null ? "" : unifiedPayMap.get("action").toString())) {
            settleDataMap.put("action", "settle");
            settleDataMap.put("omsgid", omsgid);
            settleDataMap.put("oinfno", functionCode);
            List<Map<String, Object>> setldetailList = MapUtils.get(outputMap, "setldetail");
            settleDataMap.put("setldetailList", setldetailList);
        }
        settleDataMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo());
        settleDataMap.put("visitId", insureIndividualVisitDTO.getVisitId());
        Map<String, Object> stringObjectMap = updateOutptTrialSettleInfo(settleDataMap, hospCode, insureConfigurationDTO.getRegCode());
        Map<String, Object> payInfo = new HashMap<>();
        payInfo.put("payinfo", stringObjectMap);
        return payInfo;
    }

    /**
     * @Method updateOutptTrialSettleInfo
     * @Desrciption 医保统一支付，处理医保试算的费用反参
     * @Param outDataMap
     * @Author fuhui
     * @Date 2021/3/10 14:46
     * @Return Map<String, Object>
     **/
    public Map<String, Object> updateOutptTrialSettleInfo(Map<java.lang.String, Object> outDataMap, String hospCode, String regCode) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map sysParamMap = new HashMap<>();
        sysParamMap.put("hospCode", hospCode);
        sysParamMap.put("code", regCode);
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(sysParamMap);
        if (wr == null || wr.getData() == null || wr.getData().getValue() == null) {
            throw new AppException("请配置系统参数(" + regCode + ")");
        }
        String calculation = JSONObject.parseObject(wr.getData().getValue()).getString("calculation");
        /**
         * 第一部分回参
         */
        paramMap.put("akc264", outDataMap.get("medfee_sumamt").toString());//医疗总费用
        // 全自费费用
        String fulamtOwnpayAmt = outDataMap.get("fulamt_ownpay_amt") == null ? "0" : outDataMap.get("fulamt_ownpay_amt").toString();
        paramMap.put("bka825", fulamtOwnpayAmt);//全自费费用
        paramMap.put("bka826", outDataMap.get("overlmt_selfpay").toString());//部分自费费用 ---  超限价自费费用
        paramMap.put("preselfpayAmt", outDataMap.get("preselfpay_amt").toString());//先行自付金额
        paramMap.put("inscpScpAmt",outDataMap.get("inscp_scp_amt").toString()); // 符合政策范围金额
        paramMap.put("aka151", outDataMap.get("act_pay_dedc").toString()); //起付线费用 -----  实际支付起付线
        String ake039 = outDataMap.get("hifp_pay").toString(); //医疗保险统筹基金支付
        paramMap.put("poolPropSelfpay",outDataMap.get("pool_prop_selfpay").toString());// 基本医疗保险统筹基金支付比例
        paramMap.put("ake035", outDataMap.get("cvlserv_pay").toString());//公务员医疗补助基金支付  ---- 公务员医疗补助资金支出
        paramMap.put("ake026", outDataMap.get("hifes_pay").toString());  //企业补充医疗保险基金支付  -----企业补充医疗保险基金支出
        String hifmiPay = outDataMap.get("hifmi_pay").toString();
        String hifobPay = outDataMap.get("hifob_pay").toString();
        paramMap.put("ake029", BigDecimalUtils.add(hifmiPay,hifobPay).toString());//大额医疗费用补助基金支付 ---- 职工+居民合计一个字段
        paramMap.put("mafPay",outDataMap.get("maf_pay").toString());// 医疗救助基金支出
        //paramMap.put("bka821", outDataMap.get("maf_pay").toString());//民政救助金支付
        String bka839 = outDataMap.get("oth_pay").toString();//其他支付
        paramMap.put("akb066", outDataMap.get("acct_pay").toString());//个人账户支付 --------- 个人账户支出
        paramMap.put("akb067", outDataMap.get("psn_cash_pay").toString()); //个人现金支付 ----------个人现金支出
        paramMap.put("ake039", ake039); //医疗保险统筹基金支付      - --- 基本医疗保险统筹基金支出
        paramMap.put("hospPrice", outDataMap.get("hosp_part_amt").toString()); //医院负担金额
        paramMap.put("acctMulaidPay", outDataMap.get("acct_mulaid_pay").toString()); //个人账户共计支付金额
        String fundPaySumamt = outDataMap.get("fund_pay_sumamt").toString(); // 医保支付
        String acctPay = outDataMap.get("acct_pay").toString(); // 个人账户
        String bka832 = fundPaySumamt;
        // 湖南省的 fundPaySumamt 字段不包括个人账户支付部分，广东省fundPaySumamt 字段包括，所以需区分！
        if (Constant.UnifiedPay.calculation.HN.equals(calculation)) {
            bka832 = BigDecimalUtils.add(fundPaySumamt, acctPay).toString();
        }

        paramMap.put("bka831", BigDecimalUtils.subtract(outDataMap.get("medfee_sumamt").toString(), bka832).toString()); // 个人现金自付bka831 = 医疗总费用 - 医保基金 - 刷卡金额
        paramMap.put("bka832", bka832);//医保支付bka832 = ake039 + ake035 + ake026 + ake029+ bka841 + bka840 + bka821 + bka843 + bka844 + bka845
        if ("settle".equals(outDataMap.get("action") == null ? "" : outDataMap.get("action").toString())) {
            paramMap.put("action", "settle");
            paramMap.put("setl_id", outDataMap.get("setl_id").toString());
            paramMap.put("mdtrt_id", outDataMap.get("mdtrt_id").toString());
            paramMap.put("oinfno", outDataMap.get("oinfno").toString());
            paramMap.put("omsgid", outDataMap.get("omsgid").toString());
            paramMap.put("acct_pay",outDataMap.get("acct_pay").toString());
            paramMap.put("clr_optins", MapUtils.get(outDataMap, "clr_optins"));
            paramMap.put("clr_way", MapUtils.get(outDataMap, "clr_way"));
            paramMap.put("balc",MapUtils.get(outDataMap,"balc"));// 余额账户
            paramMap.put("clr_type", MapUtils.get(outDataMap, "clr_type"));
            paramMap.put("setldetailList", MapUtils.get(outDataMap, "setldetailList"));
        }
        paramMap.put("aaz217", MapUtils.get(outDataMap, "aaz217"));

        // 处理基金信息
        Map<String,Object> setDetailMap = this.doResultSetdetailList(outDataMap);

        // 合并
        Map<String, Object> combineResultMap = new HashMap<>();
        combineResultMap.putAll(paramMap);
        combineResultMap.putAll(setDetailMap);
        return paramMap;
    }

    // 保存试算基金信息
    private Map<String, Object> doResultSetdetailList(Map<String, Object> outDataMap) {
        Map <String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> setldetailList = MapUtils.get(outDataMap,"setldetailList");
        if (!ListUtils.isEmpty(setldetailList)) {
            for (Map<String,Object> map : setldetailList) {
                String fundPayType = MapUtils.get(map,"fund_pay_type");
                String fundPayamt = MapUtils.get(map,"fund_payamt");
                switch (fundPayType) {
                    case "630100": // 医院减免金额
                        resultMap.put("hospExemAmount",fundPayamt);
                        break;
                    /*case "610100": // 医疗救助基金
                        resultMap.put("mafPay",fundPayamt);
                        break;*/
                    case "330200": // 职工意外伤害基金
                        resultMap.put("acctInjPay",fundPayamt);
                        break;
                    case "390400": // 居民意外伤害基金
                        resultMap.put("retAcctInjPay",fundPayamt);
                        break;
                    case "640100": // 政府兜底基金
                        resultMap.put("governmentPay",fundPayamt);
                        break;
                    case "620100": // 特惠保补偿金
                        resultMap.put("thbPay",fundPayamt);
                        break;
                    case "999996": // 医院垫付基金
                        resultMap.put("hospPrice",fundPayamt);
                        break;
                    case "610200": // 优抚对象医疗补助基金
                        resultMap.put("carePay",fundPayamt);
                        break;
                    case "999109": // 农村低收入人口医疗补充保险
                        resultMap.put("lowInPay",fundPayamt);
                        break;
                    case "999997": // 其他基金
                        resultMap.put("othPay",fundPayamt);
                        break;
                    case "510100": // 生育基金
                        resultMap.put("fertilityPay",fundPayamt);
                        break;
                    case "340100": // 离休人员医疗保障基金
                        resultMap.put("retiredPay",fundPayamt);
                        break;
                    case "350100": // 一至六级残疾军人医疗补助基金
                        resultMap.put("soldierPay",fundPayamt);
                        break;
                    case "340200": // 离休老工人门慢保障基金
                        resultMap.put("retiredOutptPay",fundPayamt);
                        break;
                    case "410100": // 工伤保险基金
                        resultMap.put("injuryPay",fundPayamt);
                        break;
                    case "320200": //  厅级干部补助基金
                        resultMap.put("hallPay",fundPayamt);
                        break;
                    case "310400": //  军转干部医疗补助基金
                        resultMap.put("soldierToPay",fundPayamt);
                        break;
                    case "370200": //  公益补充保险基金
                        resultMap.put("welfarePay",fundPayamt);
                        break;
                    case "99999707": //  新冠肺炎核酸检测财政补助
                        resultMap.put("COVIDPay",fundPayamt);
                        break;
                    case "390500": //  居民家庭账户金
                        resultMap.put("familyPay",fundPayamt);
                        break;
                    case "310500": //  代缴基金（破产改制）
                        resultMap.put("behalfPay",fundPayamt);
                        break;
                    default:
                        break;
                }
            }
        }
        return resultMap;
    }


    /**
     * @Method getInptTrialCostInfo
     * @Desrciption 医保统一支付：计算住院结算.预结算费用信息
     * @Param
     * @Author fuhui
     * @Date 2021/2/15 22:30
     * @Return
     **/
    public Map<String, Object> getInptTrialCostInfo(Map<String, Object> map) {
        //获取患者所有费用信息
        Map<String, Object> costParam = new HashMap<String, Object>();
        costParam.put("hospCode", map.get("hospCode").toString());//医院编码
        costParam.put("visitId", map.get("visitId").toString());//就诊id
        String costStr = insureIndividualCostDAO.queryInptCostList(costParam);
        if (StringUtils.isEmpty(costStr)) {
            throw new AppException("该患者没有产生费用信息,请先进行费用传输");
        }
        costParam.put("costStr", costStr);
        return costParam;
    }

    /**
     * @Description: 门诊结算
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/15 13:17
     * @Return
     */
    @Override
    public Map<String, Object> UP_2207(Map<String, Object> unifiedPayMap) {
        unifiedPayMap.put("action", "settle");
        String hospCode = MapUtils.get(unifiedPayMap, "hospCode");
        String visitId = MapUtils.get(unifiedPayMap, "visitId");
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setHospCode(visitId);
        String batchNo = insureIndividualCostDAO.selectLastCostInfo(unifiedPayMap);
        unifiedPayMap.put("batchNo", batchNo);
        Map<String, Object> objectMap = UP_2206(unifiedPayMap);
        return objectMap;
    }

    /**
     * @Method UP_2201
     * @Desrciption 医保统一支付平台 - 门诊挂号
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 11:09
     * @Return
     **/
    @Override
    public InsureIndividualVisitDTO UP_2201(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        String visitId = map.get("visitId").toString();
        Map<String, Object> httpMap = new HashMap<>();
        Map<String, Object> agnterinfoMap = new HashMap<>(7);
        Map<String, Object> dataMap = new HashMap<>(11);
        Map<String, Object> objectMap = new HashMap<>();
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        String functionCode = Constant.UnifiedPay.OUTPT.UP_2201;
        String omsgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        httpMap.put("infno", functionCode);//交易编号
        httpMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());//参保地医保区划
        String medisCode = insureConfigurationDTO.getOrgCode();
        httpMap.put("medins_code", medisCode);//定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("msgid", omsgId);

        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());   // 人员编号
        dataMap.put("insutype", insureIndividualVisitDTO.getAae140());  // 险种类型
        dataMap.put("begntime", DateUtils.format(insureIndividualVisitDTO.getVisitTime(), DateUtils.Y_M_DH_M_S)); // 开始时间
        dataMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType()); // 就诊凭证类型
        dataMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo());  // 就诊凭证编号
        dataMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo()); // 住院/门诊号
        if(StringUtils.isEmpty(insureIndividualVisitDTO.getPracCertiNo())){
            throw  new AppException("该"+insureIndividualVisitDTO.getDoctorName()+"医生的医师编码没有维护,请先去用户管理里面维护");
        }
        dataMap.put("atddr_no", insureIndividualVisitDTO.getPracCertiNo()); // 医师编码
        dataMap.put("dr_name", insureIndividualVisitDTO.getDoctorName()); // 医师姓名
        dataMap.put("dept_code", insureIndividualVisitDTO.getVisitDrptId()); // 科室编码
        dataMap.put("dept_name", insureIndividualVisitDTO.getVisitDrptName()); // 科室名称

        dataMap.put("card_sn", insureIndividualVisitDTO.getCardIden()); // 传值社保卡识别码
        dataMap.put("psn_cert_type", "1");
        dataMap.put("certno", insureIndividualVisitDTO.getAac002()); // 传值证件号码

        Map deptMap = new HashMap();
        deptMap.put("hospCode", hospCode);
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setId(insureIndividualVisitDTO.getVisitDrptId());
        deptMap.put("baseDeptDTO", baseDeptDTO);
        baseDeptDTO = baseDeptService_consumer.getById(deptMap).getData();
        dataMap.put("caty", baseDeptDTO.getTypeCode()); // 科别
        objectMap.put("data", dataMap);

        agnterinfoMap.put("agnter_name", "");
        agnterinfoMap.put("agnter_rlts", "");
        agnterinfoMap.put("agnter_cert_type", "");
        agnterinfoMap.put("agnter_certno", "");
        agnterinfoMap.put("agnter_tel", "");
        agnterinfoMap.put("agnter_addr", "");
        agnterinfoMap.put("agnter_photo", "");

        objectMap.put("agnterinfo", agnterinfoMap);
        httpMap.put("input", objectMap);
        String dataJson = JSONObject.toJSONString(httpMap);
        logger.info("医保统一支付平台门诊挂号入参:" + dataJson);
        String url = insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);
        map.put("medisCode",medisCode);
        map.put("visitId",visitId);
        map.put("msgId",omsgId);
        map.put("msgInfo",functionCode);
        map.put("msgName","门诊医保登记");
        map.put("isHospital",Constants.SF.F) ;
        map.put("paramMapJson",dataJson);
        map.put("resultStr",resultStr);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(map).getData();


        logger.info("医保统一支付平台门诊挂号回参:" + resultStr);
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        /**
         * 处理门诊挂号的回参
         */
        Map<String, Object> outDataMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> resultDataMap = (Map<String, Object>) outDataMap.get("data");
        insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(visitId);
        insureIndividualVisitDTO.setVisitNo(resultDataMap.get("ipt_otp_no").toString()); // 院内唯一流水 住院/门诊号
        insureIndividualVisitDTO.setMedicalRegNo(resultDataMap.get("mdtrt_id").toString());  // 医保返回唯一流水 就诊ID
        return insureIndividualVisitDTO;
    }

    /**
     * @Menthod: UP_2202
     * @Desrciption: 统一支付平台-门诊挂号撤销
     * @Param: his门诊挂号实体-outptRegisterDTO, 节点标识-flag
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-10 11:00
     * @Return:
     **/
    @Override
    public Boolean UP_2202(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        String visitId = insureIndividualVisitDTO.getVisitId();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        /**
         * 公共入参
         */
        String functionCode =  Constant.UnifiedPay.OUTPT.UP_2202;
        paramMap.put("infno", functionCode);//交易编号
        paramMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());//参保地医保区划
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        paramMap.put("medins_code", insureIndividualVisitDTO.getMedicineOrgCode());//定点医药机构编号
        paramMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        String omsgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        paramMap.put("msgid", omsgId);
        paramMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        /**
         * data节点入参
         */
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo()); //就诊ID
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001()); //人员编号
        dataMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo()); //住院/门诊号
        dataMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo()); //就诊凭证号
        dataMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType()); //就诊类型
        Map<String, Object> dataInputMap = new HashMap<>(2);
        dataInputMap.put("data", dataMap);
        paramMap.put("input", dataInputMap);//交易输入
        String dataJson = JSONObject.toJSONString(paramMap);
        logger.info("医保统一支付平台门诊挂号撤销入参:" + dataJson);
        String url = insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);

        map.put("medisCode",medisCode);
        map.put("visitId",visitId);
        map.put("msgId",omsgId);
        map.put("msgInfo",functionCode);
        map.put("msgName","门诊医保登记取消");
        map.put("isHospital",Constants.SF.F) ;
        map.put("paramMapJson",dataJson);
        map.put("resultStr",resultStr);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(map).getData();


        logger.info("医保统一支付平台门诊挂号撤销回参:" + resultStr);
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        return true;

    }

    /**
     * @Menthod: UP_2208
     * @Desrciption: 统一支付平台-门诊结算撤销
     * @Param: 节点标识-flag
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-10 11:00
     * @Return:
     **/
    @Override
    public Map<String, Object> UP_2208(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String hospName = MapUtils.get(map, "hospName");
        String crteId = MapUtils.get(map, "crteId");
        String insureSettleId = MapUtils.get(map, "insureSettleId");
        String crteName = MapUtils.get(map, "crteName");
        BigDecimal personalPrice = MapUtils.get(map, "personalPrice");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        String visitId = insureIndividualVisitDTO.getVisitId();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> httpMap = new HashMap<>();
        /**
         * 公共入参
         */
        String msgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        String funtionCode = Constant.UnifiedPay.OUTPT.UP_2208;
        httpMap.put("infno", funtionCode);//交易编号
        httpMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());//参保地医保区划
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        httpMap.put("medins_code", medisCode);//定点医药机构编号
        httpMap.put("msgid", msgId);
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        /**
         * data节点
         */
        Map<String, Object> dataMap = new HashMap<>(26);
        dataMap.put("setl_id", insureSettleId); // 结算ID
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo()); // 就诊ID
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001()); // 人员编号
        dataMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs()); // 医保中心编码
        dataMap.put("medins_code", insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码
        dataMap.put("medins_name", hospName); //医疗机构名称
        dataMap.put("med_type", insureIndividualVisitDTO.getAka130()); // 业务类型
        dataMap.put("med_mdtrt_type", insureIndividualVisitDTO.getBka006()); // 医疗待遇类型
        dataMap.put("mdtrt_date", DateUtils.format(insureIndividualVisitDTO.getVisitTime(), DateUtils.Y_M_DH_M_S)); //就诊发生日期
        dataMap.put("opter", crteId); // 登记人员工号
        dataMap.put("opter_name", crteName); // 登记人姓名
        dataMap.put("wardarea_code", ""); // 病区编码
        dataMap.put("wardarea_name", ""); //病区名称
        dataMap.put("dept_code", insureIndividualVisitDTO.getVisitDrptId()); // 就诊科室
        dataMap.put("dept_name", insureIndividualVisitDTO.getVisitDrptName()); //就诊科室名称
        dataMap.put("dise_code", insureIndividualVisitDTO.getVisitIcdCode()); // 诊断/登记诊断
        dataMap.put("dise_name", insureIndividualVisitDTO.getVisitIcdName()); // 诊断名称/登记诊断名称
        dataMap.put("save_flag", Constants.SF.F); // 保存标志
        dataMap.put("setl_flag", Constants.SF.S); // 结算标识   1：退费
        dataMap.put("photo_base64", ""); // 照片base64编码
        dataMap.put("balc", personalPrice); // 个人帐户支付金额
        dataMap.put("injury_borth_sn", ""); // 工伤个人业务序号 （或生育资格认定号）
        dataMap.put("rxno", ""); // 处方号
        dataMap.put("bilg_dr_code", ""); // 处方医生编号
        dataMap.put("bilg_dr_name", ""); // 处方医生姓名
        dataMap.put("memo", ""); // 备注
        dataMap.put("serial_apply", ""); // 门诊特殊病业务申请号
        dataMap.put("cash_pay", "");  // 刷卡金额

        List<Map<String, Object>> insureCostList = MapUtils.get(map, "insureCostList");
        List<Map<String, Object>> mapList = new ArrayList<>();
        /**
         * feeInfo节点
         */
        int count = 0;
        for (Map<String, Object> item : insureCostList) {
            Map<String, Object> feeInfoMap = new HashMap<>();
            String itemStr = ""; //项目药品类型
            String feeType = ""; //费用统计类别
            feeInfoMap.put("medins_list_code", item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString());   // 医院药品项目编码
            feeInfoMap.put("medins_list_name", item.get("hospItemName") == null ? "" : item.get("hospItemName").toString()); // 医院药品项目名称
            feeInfoMap.put("drug_dosform", item.get("prepCode") == null ? "" : item.get("prepCode").toString()); // 剂型/药品剂型
            feeInfoMap.put("prdr_name", ""); // 厂家
            feeInfoMap.put("spec", item.get("spec") == null ? "" : item.get("spec").toString()); // 规格
            feeInfoMap.put("fee_ocur_time", DateUtils.format((Date) item.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生日期
            feeInfoMap.put("sin_dos_dscr", item.get("dosageUnitCode") == null ? "" : item.get("dosageUnitCode").toString()); // 计量单位
            DecimalFormat df1 = new DecimalFormat("0.00");
            String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice") ==null ?"":item.get("realityPrice").toString()));
            feeInfoMap.put("det_item_fee_sumamt", BigDecimalUtils.convert(realityPrice)); // 明细项目费用总额
            feeInfoMap.put("cnt", MapUtils.get(item,"totalNum"));//  数量
            feeInfoMap.put("pric", MapUtils.get(item, "price"));// 单价
            feeInfoMap.put("opp_serial_fee", count++); //费用序号
            feeInfoMap.put("rxno", item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 处方号
            feeInfoMap.put("bilg_dr_code", ""); // 处方医生编号
            feeInfoMap.put("bilg_dr_code", ""); // 处方医生姓名
            feeInfoMap.put("hosp_serial", item.get("id").toString()); // 医院费用的唯一标识
            feeInfoMap.put("list_type", MapUtils.get(item, "insureItemType")); // 项目药品类型
            feeInfoMap.put("med_chrgitm_type", feeType); // 费用统计类别
            feeInfoMap.put("med_list_code", item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); //中心药品项目编码
            feeInfoMap.put("usage_flag", "0"); //用药标志
            feeInfoMap.put("usage_days", ""); //出院带药天数
            feeInfoMap.put("opter", crteId); // 录入工号
            feeInfoMap.put("opter_name", crteName); // 录入人
            feeInfoMap.put("opt_time", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); //录入日期
            mapList.add(feeInfoMap);
        }
        Map<String, Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("data", dataMap);
        stringObjectMap.put("feeinfo", mapList);
        httpMap.put("input", stringObjectMap);
        String dataJson = JSONObject.toJSONString(httpMap);
        logger.info("统一支付平台门诊取消结算入参:" + dataJson);
        String url = insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);

        map.put("medisCode",medisCode);
        map.put("visitId",visitId);
        map.put("msgId",msgId);
        map.put("msgInfo",funtionCode);
        map.put("msgName","医保门诊取消结算");
        map.put("isHospital",Constants.SF.F) ;
        map.put("paramMapJson",dataJson);
        map.put("resultStr",resultStr);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(map);
        logger.info("统一支付平台门诊取消结算回参:" + resultStr);
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        resultMap.put("msgId", msgId);
        resultMap.put("funtionCode", funtionCode);
        resultMap.put("mdtrtareaAdmvs",insureConfigurationDTO.getMdtrtareaAdmvs());
        return resultMap;
    }

    /**
     * @Method getInsureConfiguration
     * @Desrciption 根据医院编码和医疗机构编码查询对应医保的url和参保区划地址
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 10:52
     * @Return
     **/
    public InsureConfigurationDTO getInsureConfiguration(InsureConfigurationDTO insureConfigurationDTO) {
        return insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
    }

    /**
     * @Method commonGetVisitInfo
     * @Desrciption 医保统一支付：门诊患者，统一患者就诊信息查询接口
     * @Param map
     * @Author fuhui
     * @Date 2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String hospCode = (String) map.get("hospCode");//医院编码
        String visitId = (String) map.get("visitId");//就诊id
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }

    /**
     * @Method commonInsureCost
     * @Desrciption 查询医保匹配的数据集
     * @Param
     * @Author fuhui
     * @Date 2021/3/12 14:05
     * @Return
     **/
    private Map<String, Object> commonInsureCost(Map<String, Object> map) {
        String visitId = MapUtils.get(map, "visitId");
        String hospCode = MapUtils.get(map, "hospCode");
        //获取个人信息
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("hospCode", hospCode);
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        String insureRegCode = insureIndividualVisitDTO.getInsureRegCode();
        //判断是否有传输费用信息
        Map<String, Object> insureCostParam = new HashMap<String, Object>();
        insureCostParam.put("hospCode", hospCode);//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", visitId);//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode", Constants.SF.S);//传输标志 = 已传输
        insureCostParam.put("insureRegCode", insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital", Constants.SF.F); // 区分门诊还是住院
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryOutptInsureCostByVisit(insureCostParam);
        map.put("insureCostList", insureCostList);
        return map;
    }

    /**
     * @Method UP_5301
     * @Desrciption 人员慢特病备案查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/25 10:41
     * @Return
     **/
    public Map<String, Object> UP_5301(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String regCode = MapUtils.get(map, "regCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureRegCode);
        insureConfigurationDTO.setRegCode(regCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_5301);  //交易编号
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        httpParam.put("input", dataMap);
        String dataJson = JSONObject.toJSONString(httpParam);
        logger.info("人员慢特病备案查询入参:" + dataJson);
        String url = insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);
        logger.info("人员慢特病备案查询回参:" + resultStr);
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outptMap = new HashMap<>(1);
        outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = MapUtils.get(outptMap, "feedetail");
        map.put("mapList", mapList);
        return map;
    }


    /**
     * @Method UP_5302
     * @Desrciption 医保统一支付平台：人员定点备案查询
     * @Param map
     * @Author fuhui
     * @Date 2021/4/2 9:12
     * @Return
     **/
    public Map<String, Object> UP_5302(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_5302);  //交易编号
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        dataMap.put("biz_appy_type", MapUtils.get(map, "bizAppyType"));
        httpParam.put("input", dataMap);
        String dataJson = JSONObject.toJSONString(httpParam);
        logger.info("人员定点备案查询入参:" + dataJson);
        String url = insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);
        logger.info("人员定点备案查询回参:" + resultStr);
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outptMap = new HashMap<>(1);
        outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> psnfixmedinMap = MapUtils.get(outptMap, "psnfixmedin");
        map.put("psnfixmedinMap", psnfixmedinMap);
        return map;
    }

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
        httpMap.put("infno", Constant.UnifiedPay.OUTPT.UP_4301); // 交易编号
        httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); // 发送方报文
        httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 参保地医保区划
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("medins_code", insureConfigurationDTO.getOrgCode()); // 定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        httpMap.put("input", inputMap); // 交易输入
        String dataJson = JSONObject.toJSONString(httpMap);
        logger.debug("统一支付平台-门急诊诊疗记录【4301】入参：" + dataJson);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), dataJson);
        logger.debug("统一支付平台-门急诊诊疗记录【4301】返参：" + resultStr);

        // 解析返参
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            String msg = MapUtils.get(resultMap, "msg");
            throw new AppException(msg);
        }
        if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
            String err_msg = MapUtils.get(resultMap, "err_msg");
            throw new AppException(err_msg);
        }
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
    public Map<String, Object> UP4302(Map<String, Object> map) {
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
        httpMap.put("infno", Constant.UnifiedPay.OUTPT.UP_4302); // 交易编号
        httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); // 发送方报文
        httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 参保地医保区划
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("medins_code", insureConfigurationDTO.getOrgCode()); // 定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        httpMap.put("input", inputMap); // 交易输入
        String dataJson = JSONObject.toJSONString(httpMap);
        logger.debug("统一支付平台-急诊留观手术及抢救信息【4302】入参：" + dataJson);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), dataJson);
        logger.debug("统一支付平台-急诊留观手术及抢救信息【4302】返参：" + resultStr);

        // 解析返参
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            String msg = MapUtils.get(resultMap, "msg");
            throw new AppException(msg);
        }
        if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
            String err_msg = MapUtils.get(resultMap, "err_msg");
            throw new AppException(err_msg);
        }
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
                    throw new RuntimeException("患者【"+ dto.getName() +"】的【"+ dto.getOperDiseaseName() + "】未完成登记，请先完成手术相关信息");
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
                map.put("drug_medc_way",  dto.getUsageCode()); //药物使用-途径，即用药方式
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
