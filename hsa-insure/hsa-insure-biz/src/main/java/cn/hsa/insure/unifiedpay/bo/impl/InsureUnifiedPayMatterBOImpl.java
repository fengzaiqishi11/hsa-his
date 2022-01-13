package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;

import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.inregister.bo.InptVisitBO;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.module.bo.InsureIndividualVisitBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayMatterBO;
import cn.hsa.module.insure.outpt.dao.InsureUnifiedPayMatterDAO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.module.insure.outpt.service.InsureVisitInfoService;
import cn.hsa.module.mris.mrisHome.dao.MrisHomeDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: InsureUnifiedPayMatterBOImpl
 * @Describe: 统一支付平台---事前事中分析
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-24 15:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class InsureUnifiedPayMatterBOImpl implements InsureUnifiedPayMatterBO {

    //统一支付平台人员信息获取服务
    @Resource
    private InsureVisitInfoService insureVisitInfoService;

    //医保就诊服务
    @Resource
    private InsureIndividualVisitBO insureIndividualVisitBO;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    //医保配置dao
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService;

    @Resource
    private InsureUnifiedPayRestService insureUnifiedPayRestService;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private DoctorAdviceService doctorAdviceService_consumer;

    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Menthod: UP_3660
     * @Desrciption: 事前提醒
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 15:12
     * @Return:
     **/
    @Override
    public Map<String, Object> UP_3660(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        /**
         * 查询医保患者的就诊信息
         */
        map.put("visitId",MapUtils.get(map,"id"));
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);

        /**
         * 查询医保的配置信息
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        /**
         * 封装医疗就诊信息入参
         */
        Map<String, Object> encounterMap= this.handleEncounterList(insureIndividualVisitDTO,insureConfigurationDTO);

        /**
         * 参保人基本信息入参
         */
        List<Map<String, Object>> patientDTOS = this.handlePatientList(insureIndividualVisitDTO,encounterMap);

        Map<String, Object> paramMap = new HashMap<>(); //统一支付平台入参Map
        Map<String, Object> inptMap = new HashMap<>(); //交易输入
        /**
         * 触发场景（节点标识：trigScen）
         * 必选，如有多值时，用英文逗号分隔，
         * 取值参考【事前：1:门诊挂号，2:门诊收费登记，3:住院登记，4:住院收费登记，5:住院执行医嘱；
         * 事中：6:门诊结算，7:门诊预结算，8:住院结算，9:住院预结算，10:购药划卡】
         */
        if("1".equals(insureIndividualVisitDTO.getIsHospital())){
            inptMap.put("trig_scen", "3"); //触发场景
        }else{
            inptMap.put("trig_scen", "1,2"); //触发场景
        }

        inptMap.put("patientDtos", patientDTOS);

        Map<String,Object> trigScenMap = new HashMap<>();
        trigScenMap.put("trigScen",inptMap);

        /**
         * TODO 统一支付平台入参
         */
        paramMap.put("infno", "3660"); //交易编号
        paramMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());  //TODO 参保地医保区划
        paramMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        paramMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        paramMap.put("msgid",StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        paramMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区
        paramMap.put("input", trigScenMap); //交易输入

        //TODO 调用统一支付平台
        String url = insureConfigurationDTO.getUrl();
        String json = JSONObject.toJSONString(paramMap);
        logger.info("统一支付平台事前提醒服务 [3660] 入参====>");
        logger.info(json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("统一支付平台事前提醒服务 [3660] 回参<====");
        logger.info(resultJson);

        //TODO 统一支付平台回参
        Map<String,Object> resultMap = JSONObject.parseObject(resultJson);
        if(!"0".equals(resultMap.get("infcode").toString())){
            throw new AppException((String)resultMap.get("err_msg"));
        }
        return resultMap;
    }


    /**
     * @Menthod: UP_3661
     * @Desrciption: 事中预警
     * @Param:
     * @Author: luoyong
     * @Return:
     **/
    @Override
    public Map<String, Object> UP_3661(Map<String, Object> map) {
        Map<String, Object> stringObjectMap = UP_3660(map);
        return stringObjectMap;
    }

   /**
    * @Method handlePatientList
    * @Desrciption  参保人信息列表:
    * @Param 
    * 
    * @Author fuhui
    * @Date   2021/7/12 19:49 
    * @Return 
   **/
    private List<Map<String, Object>> handlePatientList(InsureIndividualVisitDTO insureIndividualVisitDTO,
                                                        Map<String, Object> encounterMap) {
        List<Map<String, Object>> patientDTOS = new ArrayList<>(); //参保人信息入参List
        Map<String, Object> patientMap = new HashMap<>(); //参保人信息 info
        patientMap.put("psn_no", insureIndividualVisitDTO.getAac001());// 参保人标识
        patientMap.put("psn_name", insureIndividualVisitDTO.getAac003());// 姓名
        patientMap.put("gend", insureIndividualVisitDTO.getGend());// 性别
        patientMap.put("brdy", DateUtils.format(insureIndividualVisitDTO.getAac006(),DateUtils.Y_M_D));// 出生日期
        patientMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());// 统筹区编码
        patientMap.put("curr_mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());// 当前就诊标识
        patientMap.put("encounterDtos", encounterMap);// 就诊信息集合
        patientDTOS.add(patientMap);
        return patientDTOS;
    }

    /**
     * @Method commonGetVisitInfo
     * @Desrciption  医保统一支付：住院结算，统一患者就诊信息查询接口
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public  InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map){
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
     * @Method handleOrderList
     * @Desrciption  医保项目信息列表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/12 19:52
     * @Return
    **/
    private List<Map<String, Object>> handleOrderList(Map<String,Object> mapParam) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(mapParam,"insureIndividualVisitDTO");
        List<Map<String, Object>> orderList = new ArrayList<>();
        if(insureIndividualVisitDTO !=null && "1".equals(insureIndividualVisitDTO.getIsHospital())){
             orderList =  getInptOrderInfo(mapParam);
        }else{
             orderList =  getOutptOrderInfo(mapParam);
        }
        List<Map<String, Object>> orderDTOS = new ArrayList<>();
        if (!ListUtils.isEmpty(orderList)) {
            orderList.forEach(map -> {
                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("rx_id", MapUtils.get(map,"opId"));  // 处方(医嘱)标识
                orderMap.put("rxno", MapUtils.get(map,"rxNo"));  // 处方号
                orderMap.put("grpno", MapUtils.get(map,"groupNo"));  // 组编号
                orderMap.put("long_drord_flag", MapUtils.get(map,"isLong"));  // 是否为长期医嘱
                orderMap.put("hilist_type", MapUtils.get(map,"insureItemType"));  // 目录类别
                orderMap.put("chrg_type", "");  //TODO 收费类别
                orderMap.put("drord_bhvr", map.get("use_code"));  // 医嘱行为
                orderMap.put("hilist_code", MapUtils.get(map,"insureItemCode"));  //TODO 医保目录代码
                orderMap.put("hilist_name",MapUtils.get(map,"insureItemName"));  //TODO 医保目录名称
                orderMap.put("hilist_dosform", "");  // 医保目录(药品)剂型（非必填）
                orderMap.put("hilist_lv", "");  //TODO 医保目录等级
                orderMap.put("hilist_pric", "");  //TODO 医保目录价格
                orderMap.put("lv1_hospItem_pric", null);  // 一级医院目录价格（非必填）
                orderMap.put("lv2_hospItem_pric", null);  // 二级医院目录价格（非必填）
                orderMap.put("lv3_hospItem_pric", null);  // 三级医院目录价格（非必填）
                orderMap.put("hilist_memo", "");  // 医保目录备注（非必填）
                orderMap.put("hosplist_code", MapUtils.get(map,"hospItemCode"));  //TODO 医院目录代码
                orderMap.put("hosplist_name", MapUtils.get(map,"hospItemName"));  //TODO 医院目录名称
                orderMap.put("hosplist_dosform", "");  // 医院目录(药品)剂型（非必填）
                DecimalFormat df1 = new DecimalFormat("0.00");
                String num = df1.format(BigDecimalUtils.convert(map.get("totalNum").toString()));
                BigDecimal convertNum = BigDecimalUtils.convert(num);
                orderMap.put("cnt", convertNum);  // 数量
                String price = df1.format(BigDecimalUtils.convert(map.get("price").toString()));
                BigDecimal convertPrice= BigDecimalUtils.convert(price);
                orderMap.put("pric", convertPrice);  // 单价
                String realityPrice = df1.format(BigDecimalUtils.convert(map.get("realityPrice").toString()));
                BigDecimal convertRealityPrice= BigDecimalUtils.convert(realityPrice);
                orderMap.put("sumamt",convertRealityPrice);  // 总费用
                orderMap.put("ownpay_amt", new BigDecimal(0.00));  //TODO 自费金额
                orderMap.put("selfpay_amt", new BigDecimal(0.00));  //TODO 自付金额
                orderMap.put("spec", MapUtils.get(map,"spec"));  // 规格
                orderMap.put("spec_unt", MapUtils.get(map,"numUnitCode"));  // 数量单位
                orderMap.put("drord_begn_date", DateUtils.format(MapUtils.get(map,"crteTime"),DateUtils.Y_M_DH_M_S));
                if(!"1".equals(insureIndividualVisitDTO.getIsHospital())){
                    orderMap.put("drord_stop_date", null);  // 医嘱停止日期
                }
                else{
                    orderMap.put("drord_stop_date", DateUtils.format(MapUtils.get(map,"stopTime"),DateUtils.Y_M_DH_M_S));  // 医嘱停止日期
                }
                orderMap.put("drord_dept_codg", MapUtils.get(map,"deptId"));  // 下达医嘱的科室标识
                orderMap.put("drord_dept_name", MapUtils.get(map,"deptName"));  // 下达医嘱科室名称
                orderMap.put("drord_dr_codg", MapUtils.get(map,"pracCertiNo"));  // 开处方(医嘱)医生标识
                orderMap.put("drord_dr_name", MapUtils.get(map,"doctorName"));  // 开处方(医嘱)医生姓名
                if("103".equals(MapUtils.get(map,"workTypeCode"))){
                    orderMap.put("drord_dr_profttl", "3");
                }
                if("102".equals(MapUtils.get(map,"workTypeCode"))){
                    orderMap.put("drord_dr_profttl", "4");
                }
                if("101".equals(MapUtils.get(map,"workTypeCode"))){
                    orderMap.put("drord_dr_profttl", "5");
                }
                if("104".equals(MapUtils.get(map,"workTypeCode"))){
                    orderMap.put("drord_dr_profttl", "2");
                }else{
                    orderMap.put("drord_dr_profttl", "9");
                }
                orderMap.put("curr_drord_flag", Constants.SF.S);  //TODO 是否当前处方(医嘱)
                orderDTOS.add(orderMap);
            });
        }
        return orderDTOS;
    }

    /**
     * @Method getOutptOrderInfo
     * @Desrciption   查询门诊患者医保病人项目信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/13 14:05
     * @Return
    **/
    private List<Map<String, Object>> getOutptOrderInfo(Map<String,Object> map) {
        Map<String, Object> stringObjectMap = commonInsureCost(map);
        List<Map<String,Object>> insureCostList = MapUtils.get(stringObjectMap,"insureCostList");
        return insureCostList;
    }

    /**
     * @Method getInptOrderInfo
     * @Desrciption  查询住院患者医保病人项目信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/13 14:02
     * @Return
    **/
    private List<Map<String, Object>> getInptOrderInfo(Map<String,Object> map) {
        return new ArrayList<>();
    }

    /**
     * @Method handleDiagnoseList
     * @Desrciption  诊断信息列表
     * @Param insureIndividualVisitDTO:医保患者的就诊信息
     *
     * @Author fuhui
     * @Date   2021/7/12 19:53
     * @Return
    **/
    private  List<Map<String, Object>> handleDiagnoseList(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map<String,Object> unifiedPayMap = new HashMap<>();
        String hospCode = insureIndividualVisitDTO.getHospCode();
        if("1".equals(insureIndividualVisitDTO.getIsHospital())){
            return getInptDiagnose(hospCode,insureIndividualVisitDTO,unifiedPayMap);
        }else{
            return getOutptDiagnose(hospCode,insureIndividualVisitDTO,unifiedPayMap);
        }
    }

    /**
     * @Method getOutptDiagnose
     * @Desrciption  查询住院病人的诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/13 11:12
     * @Return
     **/
    private List<Map<String, Object>> getInptDiagnose(String hospCode, InsureIndividualVisitDTO
            insureIndividualVisitDTO, Map<String, Object> unifiedPayMap) {
        InptVisitDTO inptVisitDTO= new InptVisitDTO();
        List<Map<String, Object>> diagnoseDTOS = new ArrayList<>();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());
        inptVisitDTO.setId(insureIndividualVisitDTO.getVisitId());
        unifiedPayMap.put("hospCode",hospCode);
        unifiedPayMap.put("inptVisitDTO",inptVisitDTO);
        List<InptDiagnoseDTO> inptDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(unifiedPayMap).getData();
        Map<String, Object> diagnoseMap = null;
        if (!ListUtils.isEmpty(inptDiagnoseDTOList)) {
            for(int i=0;i<inptDiagnoseDTOList.size();i++){
                diagnoseMap = new HashMap<>();
                diagnoseMap.put("dise_id", inptDiagnoseDTOList.get(i).getId());// 诊断标识
                if("204".equals(inptDiagnoseDTOList.get(i).getTypeCode()) || "303".equals(inptDiagnoseDTOList.get(i).getTypeCode())){
                    diagnoseMap.put("inout_dise_type", Constants.CRYZD.CYZD);// 出入诊断类别
                }else{
                    diagnoseMap.put("inout_dise_type", Constants.CRYZD.RYZD);// 出入诊断类别
                }
                diagnoseMap.put("dise_cgy", 0);// 诊断类目(非必填)
                diagnoseMap.put("dise_codg", inptDiagnoseDTOList.get(i).getInsureInllnessCode());// 诊断(疾病)编码
                diagnoseMap.put("dise_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());// 诊断(疾病)名称
                diagnoseMap.put("dise_date",DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));// 诊断日期
                diagnoseMap.put("maindise_flag", inptDiagnoseDTOList.get(i).getIsMain());// 主诊断标志
                diagnoseMap.put("dias_srt_no", "");// 诊断排序号(非必填)
                diagnoseDTOS.add(diagnoseMap);
            }
        }
        return diagnoseDTOS;
    }

    /**
     * @Method getOutptDiagnose
     * @Desrciption  查询门诊病人的诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/13 11:12
     * @Return
    **/
    private List<Map<String, Object>> getOutptDiagnose(String hospCode, InsureIndividualVisitDTO insureIndividualVisitDTO,
                                                       Map<String,Object> unifiedPayMap ) {
        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
        outptDiagnoseDTO.setHospCode(hospCode);
        outptDiagnoseDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        outptDiagnoseDTO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());
        unifiedPayMap.put("hospCode",hospCode);
        unifiedPayMap.put("outptDiagnoseDTO", outptDiagnoseDTO);
        List<OutptDiagnoseDTO> data = outptDoctorPrescribeService.queryOutptDiagnose(unifiedPayMap).getData();
        List<Map<String, Object>> diagnoseDTOS = new ArrayList<>();
        Map<String, Object> diagnoseMap = null;
        if (!ListUtils.isEmpty(data)) {
            for(int i=0;i<data.size();i++){
                diagnoseMap = new HashMap<>();
                diagnoseMap.put("dise_id", data.get(i).getId());// 诊断标识
                diagnoseMap.put("inout_dise_type", Constants.CRYZD.RYZD);// 出入诊断类别
                diagnoseMap.put("dise_cgy", "0");// 诊断类目(非必填)
                diagnoseMap.put("dise_codg", data.get(i).getInsureInllnessCode());// 诊断(疾病)编码
                diagnoseMap.put("dise_name", data.get(i).getInsureInllnessName());// 诊断(疾病)名称
                diagnoseMap.put("dise_date",DateUtils.format(data.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));// 诊断日期
                diagnoseMap.put("maindise_flag", data.get(i).getIsMain());// 主诊断标志
                diagnoseMap.put("dias_srt_no", "");// 诊断排序号(非必填)
                diagnoseDTOS.add(diagnoseMap);
            }
        }
        return diagnoseDTOS;
    }
    /**
     * @Method commonInsureCost
     * @Desrciption  查询医保匹配的数据集
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/12 14:05
     * @Return
     **/
    private Map<String,Object> commonInsureCost(Map<String, Object> map){
        String visitId = MapUtils.get(map,"visitId");
        String hospCode = MapUtils.get(map,"hospCode");
        //获取个人信息
        Map<String,Object> insureVisitParam = new HashMap<String,Object>();
        insureVisitParam.put("id",visitId);
        insureVisitParam.put("hospCode",hospCode);
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getMedicalRegNo())) {
            throw new AppException("未查找到医保个人信息，请做医保登记。");
        }
        String insureRegCode = insureIndividualVisitDTO.getInsureRegCode();
        //判断是否有传输费用信息
        Map<String,Object> insureCostParam = new HashMap<String,Object>();
        insureCostParam.put("hospCode",hospCode);//医院编码
        insureCostParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId",visitId);//就诊id
        insureCostParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode",Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode",insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital",Constants.SF.F); // 区分门诊还是住院
        List<Map<String,Object>> insureCostList = insureIndividualCostService_consumer.queryOutptInsureCostByVisit(insureCostParam);
        if(ListUtils.isEmpty(insureCostList)){
            throw new AppException("该病人没有可以上传的费用");
        }
        map.put("insureCostList",insureCostList);
        return map;
    }

    /**
     * @Method handleEncounterList
     * @Desrciption  医疗就诊信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/12 20:10
     * @Return
    **/
    private Map<String, Object> handleEncounterList(InsureIndividualVisitDTO insureIndividualVisitDTO,
                                                          InsureConfigurationDTO insureConfigurationDTO ) {
        List<Map<String, Object>> diagnoseList = handleDiagnoseList(insureIndividualVisitDTO);
        String hospCode = insureIndividualVisitDTO.getHospCode();
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("visitId",insureIndividualVisitDTO.getVisitId());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        List<Map<String, Object>> orderList = handleOrderList(map);
        map.put("insureServiceType","1"); //默认根据定点医疗机构去查找  1：定点医疗机构 2：定点零售药店
        map.put("medinsCode",insureConfigurationDTO.getOrgCode());
        map.put("orgCode",insureConfigurationDTO.getOrgCode());
        Map<String,Object> medisnInfMap = insureUnifiedPayRestService.getMedisnInfo(map).getData();
        Map<String, Object> encounterMap = new HashMap<>(); //医疗就诊信息info
        encounterMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());// 就诊标识
        encounterMap.put("medins_code", insureIndividualVisitDTO.getMedicineOrgCode());// 医疗服务机构标识
        encounterMap.put("medins_name", MapUtils.get(medisnInfMap,"fixmedins_name"));// 医疗机构名称

        encounterMap.put("medins_admdvs",insureConfigurationDTO.getMdtrtareaAdmvs());// 医疗机构行政区划编码
        encounterMap.put("medins_type", MapUtils.get(medisnInfMap,"fixmedins_type"));//TODO 医疗服务机构类型
        encounterMap.put("medins_lv", MapUtils.get(medisnInfMap,"hosp_lv"));//TODO 医疗机构等级
        encounterMap.put("wardarea_codg", null);// 病区标识
        encounterMap.put("wardno", null);// 病房号(非必填)
        encounterMap.put("bedno", insureIndividualVisitDTO.getVisitBerth());// 病床号
        if("1".equals(insureIndividualVisitDTO.getIsHospital())){
            encounterMap.put("adm_date", DateUtils.format(insureIndividualVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));// 入院日期
            encounterMap.put("dscg_date",DateUtils.format( insureIndividualVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));// 出院日期
        }else{
            encounterMap.put("adm_date", DateUtils.format(insureIndividualVisitDTO.getVisitTime(),DateUtils.Y_M_DH_M_S));// 入院日期
            encounterMap.put("dscg_date",DateUtils.format( insureIndividualVisitDTO.getVisitTime(),DateUtils.Y_M_DH_M_S));// 出院日期
        }
        encounterMap.put("dr_codg", insureIndividualVisitDTO.getPracCertiNo());// 医师标识
        encounterMap.put("dscg_main_dise_codg", insureIndividualVisitDTO.getVisitIcdCode());// 主诊断编码
        encounterMap.put("dscg_main_dise_name", insureIndividualVisitDTO.getVisitIcdName());// 主诊断名称
        encounterMap.put("diagnoseDtos", diagnoseList);// 诊断信息DTO
        encounterMap.put("adm_dept_codg", insureIndividualVisitDTO.getVisitDrptId());// 入院科室标识
        encounterMap.put("adm_dept_name", insureIndividualVisitDTO.getVisitDrptName());// 入院科室名称
        encounterMap.put("dscg_dept_codg", insureIndividualVisitDTO.getVisitDrptId());// 出院科室标识
        encounterMap.put("dscg_dept_name", insureIndividualVisitDTO.getVisitDrptName());// 出院科室名称
        encounterMap.put("med_type", insureIndividualVisitDTO.getAka130());//insureVisit.getAka130());// 医疗类别
        encounterMap.put("orderDtos", orderList);// 处方(医嘱)信息
        encounterMap.put("matn_stas", "0");//TODO 生育状态
        encounterMap.put("medfee_sumamt", "");//TODO 总费用
        encounterMap.put("ownpay_amt", 0);//TODO 自费金额
        encounterMap.put("selfpay_amt", 0);//TODO 自付金额
        encounterMap.put("acct_payamt", ""); // 个人账户支付金额(非必填)
        encounterMap.put("ma_amt", ""); // 救助金支付金额(非必填)
        encounterMap.put("hifp_payamt", ""); // 统筹金支付金额(非必填)
        encounterMap.put("setl_totlnum", "1");//TODO 结算总次数
        encounterMap.put("insutype", insureIndividualVisitDTO.getAae140());// 险种
        encounterMap.put("reim_flag", "0");//TODO 报销标志
        if(StringUtils.isEmpty(insureIndividualVisitDTO.getBac001())){
            encounterMap.put("cvlserv_flag", Constants.SF.F);//TODO 公务员标志
        }else{
            encounterMap.put("cvlserv_flag", Constants.SF.S);//TODO 公务员标志
        }

        if (!insureConfigurationDTO.getMdtrtareaAdmvs().equals(insureIndividualVisitDTO.getInsuplcAdmdvs())) {
            encounterMap.put("out_setl_flag", Constants.SF.S);// 异地结算标志
        } else {
            encounterMap.put("out_setl_flag", Constants.SF.F);// 异地结算标志
        }
        if("1".equals(insureIndividualVisitDTO.getIsHospital())){
            encounterMap.put("med_mdtrt_type", "2");//TODO 医疗就诊类别 1:门诊  2：住院  3：购药 4：其他
        }else{
            encounterMap.put("med_mdtrt_type", "1");//TODO 医疗就诊类别 1:门诊  2：住院  3：购药 4：其他
        }
        return encounterMap;
    }

}
