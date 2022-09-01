package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.enums.TrigScen;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.syshospital.service.SysHospitalService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.AnaDiagnoseDTO;
import cn.hsa.module.insure.module.dto.AnaInsuDTO;
import cn.hsa.module.insure.module.dto.AnaMdtrtDTO;
import cn.hsa.module.insure.module.dto.AnaOrderDTO;
import cn.hsa.module.insure.module.dto.AnaResJudgeDTO;
import cn.hsa.module.insure.module.dto.AnalysisDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.service.InsureDetailAuditService;
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
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DataTypeUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
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

    @Resource
    private InsureDetailAuditService insureDetailAuditService;

    @Resource
    private InsureIndividualBasicDAO insureIndividualBasicDAO;

    @Resource
    private SysHospitalService sysHospitalService_consummer;

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
        log.info("======调用医保门诊就诊信息上传接口UP_2203开始======");
        String hospCode = MapUtils.get(unifiedPayMap, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);

        List<String> diagnoseList = Stream.of("101", "102").collect(Collectors.toList());
        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
        outptDiagnoseDTO.setHospCode(hospCode);
        outptDiagnoseDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        outptDiagnoseDTO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());
        unifiedPayMap.put("outptDiagnoseDTO", outptDiagnoseDTO);

        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        outptPrescribeDTO.setHospCode(hospCode);
        outptPrescribeDTO.setDiagnoseList(diagnoseList);
        unifiedPayMap.put("outptPrescribeDTO", outptPrescribeDTO);
        List<OutptDiagnoseDTO> data = outptDoctorPrescribeService.getOutptDiagnose(unifiedPayMap).getData();
        if (ListUtils.isEmpty(data)) {
            throw new AppException("该患者没有开诊断信息");
        }
        List<OutptDiagnoseDTO> diagnoseDTOList = outptDoctorPrescribeService.queryOutptDiagnose(unifiedPayMap).getData();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(unifiedPayMap);
        paramMap.put("diagnoseDTOListData", data);
        paramMap.put("diagnoseDTOList", diagnoseDTOList);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insureIndividualVisit", insureIndividualVisitDTO);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_VISIT.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_VISIT, interfaceParamDTO);
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
    public Boolean UP_2204(Map<String, Object> unifiedPayMap) {
        String hospCode = unifiedPayMap.get("hospCode").toString();
        InsureIndividualVisitDTO insureIndividualVisitDTO = null;
        /**
         * 获取医保就诊信息
         */
        insureIndividualVisitDTO = MapUtils.get(unifiedPayMap, "insureIndividualVisitDTO");
        if (insureIndividualVisitDTO == null) {
            insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);
        }
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
        String hnFeedetlSn = ""; // 湖南费用流水号特有标识
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
                    if ("hnFeedetlSn".equals(key)) { // 湖南省特有的费流水号标识
                        hnFeedetlSn = MapUtils.get(stringObjectMap, key);
                    }
                    if ("huNanSpecial".equals(key)) {
                        huNanSpecial = MapUtils.get(stringObjectMap, key);
                    }
                    if ("patientSum".equals(key)) {
                        patientSum = MapUtils.get(stringObjectMap, key);
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
        String isUniversity = MapUtils.get(unifiedPayMap, "isUniversity");
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        unifiedPayMap.put("medicalRegNo", medicalRegNo);
        unifiedPayMap.put("medinsCode", medinsCode);
        unifiedPayMap.put("psnNo", insureIndividualVisitDTO.getAac001());
        unifiedPayMap.put("visitId", insureIndividualVisitDTO.getVisitId());
        String feeNum = insureIndividualCostDAO.selectLastFeedSn(unifiedPayMap); // 判读是否是第一次传输
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

            Integer k = 1;
            String regCode = insureConfigurationDTO.getRegCode();
            Integer maxUseDays=0;
            if("54".equals(regCode.substring(0, 2))){//西藏业务
                for (Map<String, Object> map1 : costList) {
                    if(ObjectUtil.isNotEmpty( MapUtils.get(map1, "useDays"))){
                        //获取费用明细中最大用药天数
                        Integer useDays =  MapUtils.get(map1, "useDays");
                        if(useDays!=null&&useDays>maxUseDays){
                            maxUseDays = useDays;
                        }
                    }

                }
            }
            for (Map<String, Object> map : costList) {
                costInfoMap = new HashMap<>();
                if ("1".equals(hnFeedetlSn)) {
                    if (StringUtils.isEmpty(feeNum)) {
                        atomicInteger.getAndAdd(2);
                        costInfoMap.put("feedetl_sn", atomicInteger.get()); // 费用明细流水号
                    } else {
                        k = Integer.parseInt(feeNum);
                        k += 2;
                        feeNum = k.toString();
                        costInfoMap.put("feedetl_sn", k); // 费用明细流水号
                    }
                } else {
                    costInfoMap.put("feedetl_sn", MapUtils.get(map, "id")); // 费用明细流水号
                }
                k = k + 2;
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
                String totalPirce = df1.format(BigDecimalUtils.convert(map.get("totalPrice").toString()));
                BigDecimal totalNum = BigDecimalUtils.scale((BigDecimal) map.get("totalNum"), 4);
                BigDecimal sumTotalPrice = new BigDecimal(0.00);
                if ("true".equals(isUniversity)) {
                    sumTotalPrice = BigDecimalUtils.convert(totalPirce);
                    costInfoMap.put("pric", MapUtils.get(map, "price"));// 单价
                } else {
                    sumTotalPrice = BigDecimalUtils.convert(realityPrice);
                    costInfoMap.put("pric", BigDecimalUtils.divide(sumTotalPrice, totalNum));// 单价
                }
                costInfoMap.put("cnt", totalNum);//  数量
                costInfoMap.put("det_item_fee_sumamt", sumTotalPrice); // 明细项目费用总

                costInfoMap.put("sin_dos_dscr", ""); // 单次计量描述
                costInfoMap.put("used_frqu_dscr", null); // 使用频次描述
                //2022-06-14 zhangjinping 西藏门特病人费用上传报周期天数不能为空

                if("54".equals(regCode.substring(0, 2))){
                    String medType = insureIndividualVisitDTO.getAka130();
                       if(Constant.UnifiedPay.YWLX.MZMXB.equals(medType)&&(maxUseDays==null||maxUseDays==0)) {
                           throw new AppException("该门慢门特病人的用药天数为空或为0");
                       }
                    costInfoMap.put("prd_days", maxUseDays); // 最大周期天数
                }else {
                    costInfoMap.put("prd_days",null); // 周期天数
                }

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
                costInfoMap.put("orders_dr_code", MapUtil.getStr(costInfoMap,"bilg_dr_codg")); //受单医生编码
                costInfoMap.put("orders_dr_name", MapUtil.getStr(costInfoMap,"bilg_dr_name")); //受单医生姓名
                String lmtUserFlag = MapUtils.get(map, "lmtUserFlag");
                String insureItemType = MapUtils.get(map, "insureItemType");
                String isReimburse = MapUtils.get(map, "isReimburse");
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

                // 湖南省 hosp_appr_flag 用法接口新加 西药中成药 + 湖南 + 限制级
                // 当药品本身是限制用药时，医院审批标志传0走住院自付比例，传1时走门诊自付比例 2全自费
                if (Constants.SF.S.equals(huNanSpecial) && Constants.SF.S.equals(lmtUserFlag) &&
                        (Constant.UnifiedPay.DOWNLOADTYPE.XY.equals(insureItemType) || Constant.UnifiedPay.DOWNLOADTYPE.ZCY.equals(insureItemType))) {
                   //2022-05-24 zjp 加判空条件
                    if(StringUtils.isNotEmpty(isReimburse)){
                        switch (isReimburse) {
                            case Constants.SF.S:
                                costInfoMap.put("hosp_appr_flag", "1");
                                break;
                            case Constants.SF.F:
                                costInfoMap.put("hosp_appr_flag", "2");
                                break;
                            default:
                                break;
                        }
                    }
                }

                // 湖南省医保中药饮片中出现了复方药物，则中药饮片全部报销
                if ("1".equals(huNanSpecial) && isCompound && "103".equals(insureItemType)) {
                    costInfoMap.put("hosp_appr_flag", "1");
                    costInfoMap.put("tcmdrug_used_way", "1");
                } else if ("1".equals(huNanSpecial) && !isCompound && "103".equals(insureItemType)) {
                    costInfoMap.put("hosp_appr_flag", "0");
                    costInfoMap.put("tcmdrug_used_way", "2");
                } else if ("1".equals(huNanSpecial) && "0".equals(lmtUserFlag)) {
                    costInfoMap.put("hosp_appr_flag", "0");
                } else if ("1".equals(guangZhouSpecial) && isCompound && "102".equals(insureItemType)) {
                    // 广州的102是中药饮片
                    costInfoMap.put("hosp_appr_flag", "1");
                    costInfoMap.put("tcmdrug_used_way", "1");
                }
                costInfoMap.put("etip_flag", null); // TODO 外检标志
                costInfoMap.put("etip_hosp_code", null); // TODO 外检医院编码
                costInfoMap.put("dscg_tkdrug_flag", null); // TODO 出院带药标志
                costInfoMap.put("matn_fee_flag", null); // TODO 生育费用标志
                costInfoMap.put("id", MapUtils.get(map, "id"));
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
        insertInsureCost(resultDataMap, unifiedPayMap, list);
        // 湖南这边系统参数值设置不为1  需要保存个人累计信息 方便打印结算单
        if (!"1".equals(patientSum)) {
            insertPatientSumInfo(unifiedPayMap);
        }
        return true;
    }


    /**
     * @Method updateInsureCost
     * @Desrciption 费用传输以后：更新医保的反参数据
     * @Param
     * @Author fuhui
     * @Date 2021/5/21 8:35
     * @Return
     **/
    private boolean insertInsureCost(List<Map<String, Object>> resultDataMap, Map<String, Object> unifiedPayMap, List<Map<String, Object>> list) {
        String batchNo = MapUtils.get(unifiedPayMap, "batchNo").toString();
        String hospCode = MapUtils.get(unifiedPayMap, "hospCode");
        String visitId = MapUtils.get(unifiedPayMap, "visitId");
        String insureRegisterNo = MapUtils.get(unifiedPayMap, "medicalRegNo");
        String crteName = MapUtils.get(unifiedPayMap, "crteName");
        String crteId = MapUtils.get(unifiedPayMap, "crteId");
        String selfpayProp = ""; // 自付比例
        String overlmtAmt = ""; // 超限价金额
        String preselfpayAmt = ""; // 先行自付金额
        String inscpScpAmt = ""; // 符合政策范围金额
        String fulamtOwnpayAmt = ""; // 全自费金额
        String detItemFeeSumamt = ""; // 明细金额
        InsureIndividualCostDTO insureIndividualCostDTO = null;
        List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            Map<Object, Map<String, Object>> feedetlSnMap = list.stream().collect(Collectors.toMap(Map -> Map.get("feedetl_sn"), Map -> Map, (k1, k2) -> k1));
            Map<Object, Map<String, Object>> newFeedetlSnMap = new HashMap<>();
            if (!MapUtils.isEmpty(feedetlSnMap)) {
                for (Map.Entry entry : feedetlSnMap.entrySet()) {
                    newFeedetlSnMap.put(entry.getKey().toString(), (Map<String, Object>) entry.getValue());
                }
                if (!ListUtils.isEmpty(resultDataMap)) {
                    for (Map<String, Object> item : resultDataMap) {
                        DecimalFormat df1 = new DecimalFormat("0.00");
                        String feedetlSn = MapUtils.get(item, "feedetl_sn");
                        Map<String, Object> feedetlSnObjectMap = newFeedetlSnMap.get(feedetlSn);
                        insureIndividualCostDTO = new InsureIndividualCostDTO();
                        insureIndividualCostDTO.setId(SnowflakeUtils.getId());
                        insureIndividualCostDTO.setHospCode(hospCode);
                        insureIndividualCostDTO.setVisitId(visitId);//就诊id
                        insureIndividualCostDTO.setSettleId(null);
                        insureIndividualCostDTO.setBatchNo(batchNo);
                        insureIndividualCostDTO.setIsHospital(Constants.SF.F);
                        insureIndividualCostDTO.setItemType(MapUtils.get(feedetlSnObjectMap, "list_type"));
                        insureIndividualCostDTO.setItemCode(MapUtils.get(feedetlSnObjectMap, "med_list_codg"));
                        insureIndividualCostDTO.setItemName(MapUtils.get(feedetlSnObjectMap, "med_list_name"));
                        insureIndividualCostDTO.setCostId(MapUtils.get(feedetlSnObjectMap, "id"));//费用id
                        insureIndividualCostDTO.setFeedetlSn(MapUtils.get(item, "feedetl_sn") == null ? "" : MapUtils.get(item, "feedetl_sn").toString()); // 费用明细流水号(上传到医保)
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
                        insureIndividualCostDTO.setPrimaryPrice(BigDecimalUtils.convert(MapUtils.get(feedetlSnObjectMap, "det_item_fee_sumamt") == null ? "" : MapUtils.get(feedetlSnObjectMap, "det_item_fee_sumamt").toString())); // 上传到医保的费用
                        insureIndividualCostDTO.setGuestRatio(selfpayProp); // 自付比例
                        insureIndividualCostDTO.setInscpScpAmt(BigDecimalUtils.convert(inscpScpAmt)); // 符合政策范围金额
                        insureIndividualCostDTO.setFulamtOwnpayAmt(BigDecimalUtils.convert(fulamtOwnpayAmt));
                        insureIndividualCostDTO.setOverlmtSelfpay(BigDecimalUtils.convert(overlmtAmt));
                        insureIndividualCostDTO.setOverlmtAmt(BigDecimalUtils.convert(overlmtAmt));
                        insureIndividualCostDTO.setPreselfpayAmt(BigDecimalUtils.convert(preselfpayAmt)); // 先行自付金额
                        insureIndividualCostDTO.setDetItemFeeSumamt(BigDecimalUtils.convert(detItemFeeSumamt)); // 总金额
                        insureIndividualCostDTO.setMedChrgitmType(MapUtils.get(item, "med_chrgitm_type")); //医疗收费项目类别
                        insureIndividualCostDTO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag"));
                        insureIndividualCostDTO.setChrgItemLv(MapUtils.get(item, "chrgitm_lv"));
                        insureIndividualCostDTO.setSumFee(null); // 本次上传到医保的费用总金额
                        insureIndividualCostDTO.setFeeStartTime(null);
                        insureIndividualCostDTO.setFeeEndTime(null);
                        insureIndividualCostDTO.setSettleCount(0); // 中途结算次数
                        individualCostDTOList.add(insureIndividualCostDTO);
                    }
                }
            }
        }
        if (!ListUtils.isEmpty(individualCostDTOList)) {
            insureIndividualCostDAO.batchInsertCost(individualCostDTOList);
        }
        return true;
    }

    /**
     * @return
     * @Method 门诊费用传输以后，需要保存个人累计信息
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/10/23 14:27
     * @Return
     */
    private boolean insertPatientSumInfo(Map<String, Object> map) {
        String crteName = MapUtils.get(map, "crteName"); //  创建人姓名
        String crteId = MapUtils.get(map, "crteId"); // 创建人id
        String visitId = MapUtils.get(map, "visitId"); // 就诊id
        String hospCode = MapUtils.get(map, "hospCode"); // 医院编码
        String psnNo = MapUtils.get(map, "psnNo"); // 个人编号
        String medisCode = MapUtils.get(map, "medinsCode"); // 医疗机构编码
        String medicalRegNo = MapUtils.get(map, "medicalRegNo");
        String batchNo = MapUtils.get(map, "batchNo").toString(); // 批次号
        Map<String, Object> dataMap = insureUnifiedBaseService.queryPatientSumInfo(map).getData();
        List<Map<String, Object>> resultDataMap = MapUtils.get(dataMap, "resultDataMap");
        if (!ListUtils.isEmpty(resultDataMap)) {
            resultDataMap.stream().forEach(item -> {
                item.put("id", SnowflakeUtils.getId());
                item.put("crteName", crteName);
                item.put("medicalRegNo", medicalRegNo);
                item.put("crteId", crteId);
                item.put("visitId", visitId);
                item.put("hospCode", hospCode);
                item.put("psnNo", psnNo);
                item.put("medisCode", medisCode);
                item.put("batchNo", batchNo);
                item.put("insureSettleId", null);
                item.put("crteTime", DateUtils.getNow());
                Object cum = item.get("cum");
                if (cum == null || StringUtils.isEmpty(cum.toString())) {
                    item.put("cum", 0);
                }
            });
            insureIndividualVisitDAO.deletePatientSumInfo(map);
            insureIndividualVisitDAO.insertPatientSumInfo(resultDataMap);
        }
        return true;
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
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) unifiedPayMap.get("insureIndividualVisitDTO");
        if (insureIndividualVisitDTO == null) {
            insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(unifiedPayMap);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insureIndividualVisit", insureIndividualVisitDTO);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_FEE_UPLOAD_BACK.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(MapUtils.get(unifiedPayMap, "hospCode"));
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_FEE_UPLOAD_BACK, interfaceParamDTO);
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
       //增加标志 判断是结算还是试算
        unifiedPayMap.put("settleType","2206");
        return UP_2206_2207(unifiedPayMap, FunctionEnum.OUTPATIENT_PRE_SETTLE);
    }

    private Map<String, Object> UP_2206_2207(Map<String, Object> unifiedPayMap, FunctionEnum functionEnum) {
        String hospCode = MapUtils.get(unifiedPayMap, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(unifiedPayMap);
        String action = MapUtils.get(unifiedPayMap,"action");
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);

        unifiedPayMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);

        String omsgid = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(unifiedPayMap);
        paramMap.put("visitId", insureIndividualVisitDTO.getVisitId());
        paramMap.put("msgId", omsgid);
        paramMap.put("opter", MapUtils.get(unifiedPayMap, "code"));
        paramMap.put("opter_name", MapUtils.get(unifiedPayMap, "crteName"));
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insureIndividualVisit", insureIndividualVisitDTO);
        // 费用信息
        paramMap.put("costMapInfo", getInptTrialCostInfo(unifiedPayMap));

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + functionEnum.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> resultMap = insureItfBO.executeInsur(functionEnum, interfaceParamDTO);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> settleDataMap = (Map<String, Object>) outputMap.get("setlinfo");
        settleDataMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo());
        settleDataMap.put("hospCode", hospCode);
        Map<String,Object> acctPayMap = null;
        if ("settle".equals(action)) {
            Map<String,Object> paramAcctMap = new HashMap<>();
            paramAcctMap.put("code","HN_INSURE_ACCT_PAY");
            paramAcctMap.put("hospCode",hospCode);
            SysParameterDTO data = sysParameterService_consumer.getParameterByCode(paramAcctMap).getData();
            if(data !=null && Constants.SF.S.equals(data.getValue())){
                settleDataMap.put("visitId",insureIndividualVisitDTO.getVisitId());
                settleDataMap.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
                settleDataMap.put("hospCode",hospCode);
                settleDataMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
                settleDataMap.put("INSURE_ACCT_PAY_PARAM",data.getValue());
                OutptVisitDTO outptVisitDTO = MapUtils.get(unifiedPayMap, "outptVisitDTO");
                settleDataMap.put("acct_used_flag",outptVisitDTO.getIsUseAccount());
                settleDataMap.put("orgCode",insureConfigurationDTO.getRegCode());
                acctPayMap = handlerAcctPayBalance(settleDataMap);
            }
            settleDataMap.put("action", "settle");
            settleDataMap.put("omsgid", omsgid);
            settleDataMap.put("oinfno", functionEnum.getCode());
            List<Map<String, Object>> setldetailList = MapUtils.get(outputMap, "setldetail");
            settleDataMap.put("setldetailList", setldetailList);
        }
        settleDataMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo());
        settleDataMap.put("visitId", insureIndividualVisitDTO.getVisitId());
        Map<String, Object> stringObjectMap = updateOutptTrialSettleInfo(settleDataMap, hospCode, insureConfigurationDTO.getRegCode(),acctPayMap);
        Map<String, Object> payInfo = new HashMap<>();
        payInfo.put("payinfo", stringObjectMap);
        //==============================事中明细审核=================================
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode",hospCode);
        sysMap.put("code","DETAILAUDIT_SWITCH");
        WrapperResponse<SysParameterDTO> response = sysParameterService_consumer.getParameterByCode(sysMap);
        if (WrapperResponse.SUCCESS != response.getCode()) {
            throw new AppException(response.getMessage());
        }
        //开关开启并且是试算才调明细审核
        if (ObjectUtil.isNotEmpty(response.getData())&&"1".equals(response.getData().getValue())
                && "2206".equals(MapUtil.getStr(unifiedPayMap,"settleType"))){
            if (ObjectUtil.isEmpty(unifiedPayMap.get("outptVisitDTO"))) {
                throw new AppException("事中明细审核未获取到门诊就诊信息！");
            }
            OutptVisitDTO outptVisitDTO = (OutptVisitDTO) unifiedPayMap.get("outptVisitDTO");
            List<Map<String, Object>> insureCostList = (List<Map<String, Object>>)unifiedPayMap.get("insureCostList");

            AnalysisDTO analysisDTO = this.initAnalysisDTO(outptVisitDTO,insureIndividualVisitDTO,insureCostList);
            Map<String, Object> inMap = new HashMap<>();
            inMap.put("hospCode",hospCode);
            inMap.put("analysisDTO",analysisDTO);
            inMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
            AnaResJudgeDTO anaResJudgeDTO = insureDetailAuditService.upldMidAnalysisDTO(inMap);
        }
        //========================================================================
        return payInfo;
    }

    /**
     * @Method handlerAcctPayBalance
     * @Desrciption  处理海南地区：余额扣减问题
     *          1.先根据结算反参的证件类型和证件号码查询是否还有余额
     *          2.当余额大于0.00时 调用余额扣减接口
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/21 13:51
     * @Return
    **/
    private Map<String, Object> handlerAcctPayBalance(Map<String, Object> settleDataMap) {
        Map<String, Object> balanceMap = insureUnifiedBaseService.queryBalanceCount(settleDataMap).getData();
        Map<String,Object> resultMap  =  MapUtils.get(balanceMap,"result");
        Map<String,Object> balanceDataMap = null;
        Object balance = MapUtils.get(resultMap, "balance");
        String string = DataTypeUtils.dataToNumString(balance);
        BigDecimal decimal = BigDecimalUtils.convert(string);
        if(BigDecimalUtils.greaterZero(decimal)){
            settleDataMap.put("insureSettleId",MapUtils.get(settleDataMap,"setl_id"));
            Map<String, Object> dataMap = insureUnifiedBaseService.updateBalanceCountDecrease(settleDataMap).getData();
            balanceDataMap = MapUtils.get(dataMap,"result");
        }
        return balanceDataMap;
    }

    /**
     * @Method updateOutptTrialSettleInfo
     * @Desrciption 医保统一支付，处理医保试算的费用反参
     * @Param outDataMap
     * @Author fuhui
     * @Date 2021/3/10 14:46
     * @Return Map<String, Object>
     **/
    public Map<String, Object> updateOutptTrialSettleInfo(Map<String, Object> outDataMap, String hospCode,
                                                          String regCode, Map<String, Object> acctPayMap) {

        String balanceValue = MapUtils.get(outDataMap,"INSURE_ACCT_PAY_PARAM");  // 海南地区开启个账参数判断
        String acctUsedFlag= MapUtils.get(outDataMap,"acct_used_flag"); // 是否使用个人账户标志
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("balanceValue",balanceValue);
        paramMap.put("acctUsedFlag",acctUsedFlag);
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
        paramMap.put("inscpScpAmt", outDataMap.get("inscp_scp_amt").toString()); // 符合政策范围金额
        paramMap.put("aka151", outDataMap.get("act_pay_dedc").toString()); //起付线费用 -----  实际支付起付线
        String ake039 = outDataMap.get("hifp_pay").toString(); //医疗保险统筹基金支付
        paramMap.put("poolPropSelfpay", outDataMap.get("pool_prop_selfpay").toString());// 基本医疗保险统筹基金支付比例
        paramMap.put("ake035", outDataMap.get("cvlserv_pay").toString());//公务员医疗补助基金支付  ---- 公务员医疗补助资金支出
        paramMap.put("ake026", outDataMap.get("hifes_pay").toString());  //企业补充医疗保险基金支付  -----企业补充医疗保险基金支出
        String hifmiPay = outDataMap.get("hifmi_pay").toString();
        String hifobPay = outDataMap.get("hifob_pay").toString();
        paramMap.put("ake029", BigDecimalUtils.add(hifmiPay, hifobPay).toString());//大额医疗费用补助基金支付 ---- 职工+居民合计一个字段
        paramMap.put("mafPay", outDataMap.get("maf_pay").toString());// 医疗救助基金支出
        //paramMap.put("bka821", outDataMap.get("maf_pay").toString());//民政救助金支付
        String bka839 = outDataMap.get("oth_pay").toString();//其他支付
        paramMap.put("akb067", outDataMap.get("psn_cash_pay").toString()); //个人现金支付 ----------个人现金支出
        paramMap.put("ake039", ake039); //医疗保险统筹基金支付      - --- 基本医疗保险统筹基金支出
        paramMap.put("hospPrice", outDataMap.get("hosp_part_amt").toString()); //医院负担金额
        paramMap.put("acctMulaidPay", outDataMap.get("acct_mulaid_pay").toString()); //个人账户共计支付金额
        String fundPaySumamt = outDataMap.get("fund_pay_sumamt").toString(); // 医保支付
        String acctPay ="";
        if(Constants.SF.S.equals(balanceValue) && Constants.SF.S.equals(acctUsedFlag)){
            if(!MapUtils.isEmpty(acctPayMap)){
                acctPay  = DataTypeUtils.dataToNumString(MapUtils.get(acctPayMap,"enttAcctPay"));
            }
        }else{
             acctPay = outDataMap.get("acct_pay").toString(); // 个人账户
        }
        String bka832 = fundPaySumamt;
        paramMap.put("akb066", acctPay);//个人账户支付 --------- 个人账户支出
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
            paramMap.put("acct_pay", outDataMap.get("acct_pay").toString());
            paramMap.put("clr_optins", MapUtils.get(outDataMap, "clr_optins"));
            paramMap.put("clr_way", MapUtils.get(outDataMap, "clr_way"));
            paramMap.put("balc", MapUtils.get(outDataMap, "balc"));// 余额账户
            paramMap.put("clr_type", MapUtils.get(outDataMap, "clr_type"));
            paramMap.put("setldetailList", MapUtils.get(outDataMap, "setldetailList"));
        }
        paramMap.put("aaz217", MapUtils.get(outDataMap, "aaz217"));

        // 处理基金信息
        Map<String, Object> setDetailMap = this.doResultSetdetailList(outDataMap);

        // 合并
        Map<String, Object> combineResultMap = new HashMap<>();
        combineResultMap.putAll(paramMap);
        combineResultMap.putAll(setDetailMap);
        return combineResultMap;
    }

    // 保存试算基金信息
    private Map<String, Object> doResultSetdetailList(Map<String, Object> outDataMap) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> setldetailList = MapUtils.get(outDataMap, "setldetailList");
        if (!ListUtils.isEmpty(setldetailList)) {
            BigDecimal othPay = BigDecimal.ZERO;
            for (Map<String, Object> map : setldetailList) {
                String fundPayType = MapUtils.get(map, "fund_pay_type");
                String fundPayamt = MapUtils.get(map, "fund_payamt").toString();
                String setlProcInfo = MapUtils.get(map, "setl_proc_info");
                switch (fundPayType) {
                    case "630100": // 医院减免金额
                        resultMap.put("hospExemAmount", fundPayamt);
                        break;
                    case "610100": // 医疗救助基金
                        resultMap.put("mafPay", fundPayamt);
                        break;
                    case "330200": // 职工意外伤害基金
                        resultMap.put("acctInjPay", fundPayamt);
                        break;
                    case "390400": // 居民意外伤害基金
                        resultMap.put("retAcctInjPay", fundPayamt);
                        break;
                    case "640100": // 政府兜底基金
                        resultMap.put("governmentPay", fundPayamt);
                        break;
                    case "620100": // 特惠保补偿金
                        resultMap.put("thbPay", fundPayamt);
                        break;
                    case "999996": // 医院垫付基金
                        resultMap.put("hospPrice", fundPayamt);
                        break;
                    case "610200": // 优抚对象医疗补助基金
                        resultMap.put("carePay", fundPayamt);
                        break;
                    case "999109": // 农村低收入人口医疗补充保险
                        resultMap.put("lowInPay", fundPayamt);
                        break;
                    case "510100": // 生育基金
                        resultMap.put("fertilityPay", fundPayamt);
                        break;
                    case "340100": // 离休人员医疗保障基金
                        resultMap.put("retiredPay", fundPayamt);
                        break;
                    case "350100": // 一至六级残疾军人医疗补助基金
                        resultMap.put("soldierPay", fundPayamt);
                        break;
                    case "340200": // 离休老工人门慢保障基金
                        resultMap.put("retiredOutptPay", fundPayamt);
                        break;
                    case "410100": // 工伤保险基金
                        resultMap.put("injuryPay", fundPayamt);
                        break;
                    case "320200": //  厅级干部补助基金
                        resultMap.put("hallPay", fundPayamt);
                        break;
                    case "310400": //  军转干部医疗补助基金
                        resultMap.put("soldierToPay", fundPayamt);
                        break;
                    case "370200": //  公益补充保险基金
                        resultMap.put("welfarePay", fundPayamt);
                        break;
                    case "99999707": //  新冠肺炎核酸检测财政补助
                        resultMap.put("COVIDPay", fundPayamt);
                        break;
                    case "390500": //  居民家庭账户金
                        resultMap.put("familyPay", fundPayamt);
                        break;
                    case "310500": //  代缴基金（破产改制）
                        resultMap.put("behalfPay", fundPayamt);
                        break;
                    case "999997": // 其他基金
                        othPay = BigDecimalUtils.add(othPay.toString(), fundPayamt);
                        resultMap.put("othPay", othPay.toString());
                        if ("640101".equals(setlProcInfo)) {
                            resultMap.put("governmentPay", fundPayamt);
                        }
                        if ("630101".equals(setlProcInfo)) {
                            resultMap.put("hospExemAmount", fundPayamt);
                        }
                        if ("620101".equals(setlProcInfo)) {
                            resultMap.put("thbPay", fundPayamt);
                        }
                        if ("610101".equals(setlProcInfo)) {
                            resultMap.put("mafPay", fundPayamt);
                        }
                        break;
                    case "610101":
                        resultMap.put("mafPay", fundPayamt);
                        break;
                    case "620101":
                        resultMap.put("thbPay", fundPayamt);
                        break;
                    case "630101":
                        resultMap.put("hospExemAmount", fundPayamt);
                        break;
                    case "640101":
                        resultMap.put("governmentPay", fundPayamt);
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
        unifiedPayMap.put("settleType","2207");
        return UP_2206_2207(unifiedPayMap, FunctionEnum.OUTPATIENT_SETTLE);
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
        log.info("======调用医保门诊挂号接口UP_2201开始======");
        String hospCode = MapUtils.get(map, "hospCode");
        String visitId = MapUtils.get(map, "visitId");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(map);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insureIndividualVisit", insureIndividualVisitDTO);

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_REG.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_REG, interfaceParamDTO);
        /**
         * 处理门诊挂号的回参
         */
        Map<String, Object> outDataMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> resultDataMap = (Map<String, Object>) outDataMap.get("data");
        insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(visitId);
        insureIndividualVisitDTO.setVisitNo(MapUtils.get(resultDataMap, "ipt_otp_no")); // 院内唯一流水 住院/门诊号
        insureIndividualVisitDTO.setMedicalRegNo(MapUtils.get(resultDataMap, "mdtrt_id"));  // 医保返回唯一流水 就诊ID
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
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(map);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insureIndividualVisit", insureIndividualVisitDTO);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_REG_CANCEL.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(MapUtils.get(map, "hospCode"));
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_REG_CANCEL, interfaceParamDTO);
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
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        String msgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(map);
        paramMap.put("msgId", msgId);
        paramMap.put("opter", MapUtils.get(map, "crteId"));
        paramMap.put("opter_name", MapUtils.get(map, "crteName"));
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insureIndividualVisit", insureIndividualVisitDTO);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_SETTLE_BACK.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_SETTLE_BACK, interfaceParamDTO);
        resultMap.put("msgId", msgId);
        resultMap.put("funtionCode", FunctionEnum.OUTPATIENT_SETTLE_BACK.getCode());
        resultMap.put("mdtrtareaAdmvs", insureConfigurationDTO.getMdtrtareaAdmvs());
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
        String funtionCode = Constant.UnifiedPay.REGISTER.UP_5301;
        String msgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        String medisCode = insureConfigurationDTO.getOrgCode();
        httpParam.put("infno", funtionCode);  //交易编号
        httpParam.put("msgid", msgId);
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", medisCode); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        httpParam.put("input", dataMap);
        String dataJson = JSONObject.toJSONString(httpParam);
        logger.info("人员慢特病备案查询入参:" + dataJson);
        String url = insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);

        map.put("medisCode", medisCode);
        map.put("visitId", "");
        map.put("msgId", msgId);
        map.put("msgInfo", funtionCode);
        map.put("msgName", "人员慢特病备案查询");
        map.put("isHospital", Constants.SF.F);
        map.put("paramMapJson", dataJson);
        map.put("resultStr", resultStr);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(map);

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


    /**
     * @Description 拼装明细审核入参
     * @Author 产品三部-郭来
     * @Date 2022-05-09 15:37
     * @return cn.hsa.module.insure.module.dto.AnalysisDTO
     */
    public AnalysisDTO initAnalysisDTO(OutptVisitDTO outptVisitDTO, InsureIndividualVisitDTO insureVisitDTO,List<Map<String, Object>> insureCostList){
        if (ObjectUtil.isEmpty(outptVisitDTO)) {
            throw new AppException("入参不能为空！");
        }

        List<AnaOrderDTO> anaOrderDTOS = new ArrayList<>();
        for (Map<String, Object> map : insureCostList) {
            AnaOrderDTO anaOrderDTO = new AnaOrderDTO();
            //*处方(医嘱)标识
            anaOrderDTO.setRxId(ObjectUtil.isNotEmpty(MapUtil.getStr(map,"opId"))?MapUtil.getStr(map,"opId"):MapUtil.getStr(map,"id"));
            //*处方号
            anaOrderDTO.setRxno(ObjectUtil.isNotEmpty(MapUtil.getStr(map,"rxNo"))?MapUtil.getStr(map,"rxNo"):MapUtil.getStr(map,"id"));
            //组编号
            anaOrderDTO.setGrpno(MapUtil.getStr(map,"iatdGroupNo"));
            //*是否为长期医嘱  1:是   0：否
            anaOrderDTO.setLongDrordFlag("0".equals(MapUtil.getStr(map,"isLong"))?"1":"0");
            //*目录类别
            anaOrderDTO.setHilistType(ObjectUtil.isEmpty(MapUtil.getStr(map,"insureItemType"))?"101":MapUtil.getStr(map,"insureItemType"));
            //*收费类别
            anaOrderDTO.setChrgType(ObjectUtil.isEmpty(MapUtil.getStr(map,"chrgType"))?"1":MapUtil.getStr(map,"chrgType"));
            //*医嘱行为
            anaOrderDTO.setDrordBhvr("-");
            //*医保目录代码
            anaOrderDTO.setHilistCode(MapUtil.getStr(map,"insureItemCode"));
            //*医保目录名称
            anaOrderDTO.setHilistName(MapUtil.getStr(map,"insureItemName"));
            //医保目录(药品)剂型
            anaOrderDTO.setHilistDosform(MapUtil.getStr(map,"insureItemPrepCode"));
            //*医保目录等级
            anaOrderDTO.setHilistLv(ObjectUtil.isEmpty(MapUtil.getStr(map,"hilistLv"))?"1":MapUtil.getStr(map,"hilistLv"));
            //*医保目录价格

            anaOrderDTO.setHilistPric(ObjectUtil.isEmpty(MapUtil.getStr(map,"insureItemPrice"))?BigDecimal.ZERO:new BigDecimal(MapUtil.getStr(map,"insureItemPrice")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //医保目录备注
            anaOrderDTO.setHilistMemo("");
            //*医院目录代码
            anaOrderDTO.setHosplistCode(MapUtil.getStr(map,"hospItemCode"));
            //*医院目录名称
            anaOrderDTO.setHosplistName(MapUtil.getStr(map,"hospItemName"));
            //医院目录(药品)剂型
            anaOrderDTO.setHosplistDosform(MapUtil.getStr(map,"hospItemPrepCode"));
            //*数量
            anaOrderDTO.setCnt(BigDecimalUtils.convert(MapUtil.getStr(map,"num")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*单价
            anaOrderDTO.setPric(BigDecimalUtils.convert(MapUtil.getStr(map,"price")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*总费用
            anaOrderDTO.setSumamt(BigDecimalUtils.convert(MapUtil.getStr(map,"totalPrice")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*自费金额
            anaOrderDTO.setOwnpayAmt(BigDecimalUtils.convert(MapUtil.getStr(map,"fulamtOwnpayAmt")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*自付金额
            anaOrderDTO.setSelfpayAmt(BigDecimalUtils.convert(MapUtil.getStr(map,"preselfpayAmt")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*规格
            anaOrderDTO.setSpec(ObjectUtil.isEmpty(MapUtil.getStr(map,"spec"))?"-":MapUtil.getStr(map,"spec"));
            //*数量单位
            anaOrderDTO.setSpecUnt(ObjectUtil.isEmpty(MapUtil.getStr(map,"numUnitCode"))?"-":MapUtil.getStr(map,"numUnitCode"));
            //*医嘱开始日期
            anaOrderDTO.setDrordBegnDate(MapUtil.getDate(map,"crteTime"));
            //*下达医嘱的科室标识
            anaOrderDTO.setDrordDeptCodg(ObjectUtil.isEmpty(MapUtil.getStr(map,"execDeptId"))?"-":MapUtil.getStr(map,"execDeptId"));
            //*下达医嘱科室名称
            anaOrderDTO.setDrordDeptName(ObjectUtil.isEmpty(MapUtil.getStr(map,"execDeptName"))?"-":MapUtil.getStr(map,"execDeptName"));
            //*开处方(医嘱)医生标识
            anaOrderDTO.setDrordDrCodg(ObjectUtil.isEmpty(MapUtil.getStr(map,"crteId"))?"-":MapUtil.getStr(map,"crteId"));
            //*开处方(医嘱)医生姓名
            anaOrderDTO.setDrordDrName(ObjectUtil.isEmpty(MapUtil.getStr(map,"crteName"))?"-":MapUtil.getStr(map,"crteName"));
            //*开处方(医嘱)医职称
            anaOrderDTO.setDrordDrProfttl("-");
            //*是否当前处方(医嘱)  1=是,0=否
            anaOrderDTO.setCurrDrordFlag("1");
            anaOrderDTOS.add(anaOrderDTO);
        }
        //诊断信息DTO
        List<AnaDiagnoseDTO> anaDiagnoseDTOS = new ArrayList<>();
        OutptPrescribeDTO inPut = new OutptPrescribeDTO();
        inPut.setVisitId(outptVisitDTO.getId());
        inPut.setHospCode(outptVisitDTO.getHospCode());
        List<OutptDiagnoseDTO> outptDiagnose = insureIndividualVisitDAO.getOutptDiagnose(inPut);
        if (ObjectUtil.isEmpty(outptDiagnose)) {
            throw new AppException("未查询到诊断信息！");
        }
        for (int i = 0; i < outptDiagnose.size(); i++) {
            AnaDiagnoseDTO diagnoseDTO = new AnaDiagnoseDTO();
            //*诊断标识
            diagnoseDTO.setDiseId(outptDiagnose.get(i).getId());
            //*出入诊断类别
            diagnoseDTO.setInoutDiseType("1");
            //*主诊断标志
            diagnoseDTO.setMaindiseFlag(outptDiagnose.get(i).getIsMain());
            //*诊断排序号
            diagnoseDTO.setDiasSrtNo(BigDecimal.valueOf(i));
            //*诊断(疾病)编码
            diagnoseDTO.setDiseCodg(outptDiagnose.get(i).getDiseaseCode());
            //*诊断(疾病)名称
            diagnoseDTO.setDiseName(outptDiagnose.get(i).getDiseaseName());
            //*诊断日期
            diagnoseDTO.setDiseDate(outptDiagnose.get(i).getCrteTime());
            //*诊断类目  --海南必传
            diagnoseDTO.setDiseCgy("1");
            anaDiagnoseDTOS.add(diagnoseDTO);
        }
        //就诊信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("hospCode",outptVisitDTO.getHospCode());
        map.put("visitId",outptVisitDTO.getId());
        OutptVisitDTO visitDTO = insureIndividualVisitDAO.selectOutptVisitById(map);
        if (ObjectUtil.isEmpty(visitDTO)) {
            throw new AppException("明细审核未查询到就诊信息，请检查！");
        }
        InsureIndividualBasicDTO basicInParm = new InsureIndividualBasicDTO();
        basicInParm.setVisitId(outptVisitDTO.getId());
        basicInParm.setHospCode(outptVisitDTO.getHospCode());
        basicInParm.setMedicalRegNo(insureVisitDTO.getMedicalRegNo());
        InsureIndividualBasicDTO basicDTO = insureIndividualBasicDAO.getByVisitId(basicInParm);
        if (ObjectUtil.isEmpty(basicDTO)) {
            throw new AppException("明细审核查询人员医保基本信息为空，请检查！");
        }
        //
        CenterHospitalDTO input = new CenterHospitalDTO();
        input.setCode(outptVisitDTO.getHospCode());
        HashMap<String, Object> inMap = new HashMap<>();
        inMap.put("centerHospitalDTO",input);
        WrapperResponse<CenterHospitalDTO> response = sysHospitalService_consummer.getByHospCode(inMap);
        if (WrapperResponse.SUCCESS != response.getCode()) {
            throw new AppException("查询医院信息失败！"+response.getMessage());
        }
        CenterHospitalDTO hospitalDTO = response.getData();
        AnaMdtrtDTO anaMdtrtDTO = new AnaMdtrtDTO();
        //*就诊标识
        anaMdtrtDTO.setMdtrtId(visitDTO.getId());
        //*医疗服务机构标识
        anaMdtrtDTO.setMedinsId(visitDTO.getHospCode());
        //*医疗机构名称
        anaMdtrtDTO.setMedinsName(ObjectUtil.isNotEmpty(hospitalDTO.getName())?hospitalDTO.getName():"-");
        //*医疗机构行政区划编码
        anaMdtrtDTO.setMedinsAdmdvs(insureVisitDTO.getMdtrtareaAdmvs());
        //*医疗服务机构类型
        anaMdtrtDTO.setMedinsType("1");
        //*医疗机构等级
        anaMdtrtDTO.setMedinsLv(ObjectUtil.isNotEmpty(hospitalDTO.getLevelCode())?hospitalDTO.getLevelCode():"02");
        //病区标识
        anaMdtrtDTO.setWardareaCodg("");
        //病房号
        anaMdtrtDTO.setWardno("");
        //病床号
        anaMdtrtDTO.setBedno("");
        //*入院日期
        anaMdtrtDTO.setAdmDate(visitDTO.getVisitTime());
        //*出院日期
        anaMdtrtDTO.setDscgDate(visitDTO.getVisitTime());
        //*主诊断编码
        anaMdtrtDTO.setDscgMainDiseCodg(insureVisitDTO.getVisitIcdCode());
        //*主诊断名称
        anaMdtrtDTO.setDscgMainDiseName(insureVisitDTO.getVisitIcdName());
        //*医师标识
        anaMdtrtDTO.setDrCodg(visitDTO.getDoctorId());
        //*入院科室标识
        anaMdtrtDTO.setAdmDeptCodg(visitDTO.getDeptId());
        //*入院科室名称
        anaMdtrtDTO.setAdmDeptName(visitDTO.getDeptName());
        //*出院科室标识
        anaMdtrtDTO.setDscgDeptCodg(visitDTO.getDeptId());
        //*出院科室名称
        anaMdtrtDTO.setDscgDeptName(visitDTO.getDeptName());
        //*就诊类型
        anaMdtrtDTO.setMedMdtrtType(insureVisitDTO.getAka130()+"0");
        //*医疗类别
        anaMdtrtDTO.setMedType(insureVisitDTO.getAka130());
        //*生育状态
        anaMdtrtDTO.setMatnStas("0");
        //*总费用
        anaMdtrtDTO.setMedfeeSumamt(new BigDecimal("0"));
        //*自费金额
        anaMdtrtDTO.setOwnpayAmt(new BigDecimal("0"));
        //*自付金额
        anaMdtrtDTO.setSelfpayAmt(new BigDecimal("0"));
        //个人账户支付金额
        anaMdtrtDTO.setMaAmt(new BigDecimal("0"));
        //救助金支付金额
        anaMdtrtDTO.setAcctPayamt(new BigDecimal("0"));
        //统筹金支付金额
        anaMdtrtDTO.setHifpPayamt(new BigDecimal("0"));
        //*结算总次数
        anaMdtrtDTO.setSetlTotlnum(new BigDecimal("1"));
        //*险种
        anaMdtrtDTO.setInsutype(basicDTO.getAae140());
        //*报销标志
        anaMdtrtDTO.setReimFlag("0");
        //*异地结算标志
        anaMdtrtDTO.setOutSetlFlag("0");
        //处方(医嘱)信息
        anaMdtrtDTO.setFsiOrderDtos(anaOrderDTOS);
        //诊断信息DTO
        anaMdtrtDTO.setFsiDiagnoseDtos(anaDiagnoseDTOS);
        //公务员标志  --海南必传
        anaMdtrtDTO.setCvlservFlag(ObjectUtil.isNotEmpty(basicDTO.getBac001())?basicDTO.getBac001():"0");
        anaMdtrtDTO.setOrderDtos(anaOrderDTOS);
        anaMdtrtDTO.setDiagnoseDtos(anaDiagnoseDTOS);

        //参保信息
        AnaInsuDTO anaInsuDTO = new AnaInsuDTO();
        //*参保人标识
        anaInsuDTO.setPatnId(basicDTO.getAac001());
        //*姓名
        anaInsuDTO.setPatnName(basicDTO.getAac003());
        //*性别
        anaInsuDTO.setGend(basicDTO.getAac004());
        //*出生日期
        anaInsuDTO.setBrdy(DateUtils.parse(basicDTO.getAac006(), DatePattern.NORM_DATE_PATTERN));
        //*统筹区编码
        anaInsuDTO.setPoolarea(insureVisitDTO.getInsuplcAdmdvs());
        //*当前就诊标识
        anaInsuDTO.setCurrMdtrtId(outptVisitDTO.getId());
        //*就诊信息集合
        anaInsuDTO.setFsiEncounterDtos(anaMdtrtDTO);
        //*就诊信息集合  --海南
        anaInsuDTO.setEncounterDtos(anaMdtrtDTO);

        //入参DTO
        AnalysisDTO analysisDTO = new AnalysisDTO();
        //*系统编码
        analysisDTO.setSyscode("YYDS");
        //任务ID
        analysisDTO.setTaskId("");
        //触发场景
        analysisDTO.setTrigScen(TrigScen.TRIG_SCEN_7.getCode());
        //规则标识集合
        analysisDTO.setRuleIds("");
        //*参保人信息
        analysisDTO.setPatientDtos(anaInsuDTO);

        return analysisDTO;
    }
}
