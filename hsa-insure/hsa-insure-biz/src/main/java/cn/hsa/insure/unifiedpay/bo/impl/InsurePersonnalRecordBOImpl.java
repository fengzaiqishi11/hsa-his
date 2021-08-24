package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.bo.InsurePersonnalRecordBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureDiseaseRecordDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureInptRecordDO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsurePersonnalRecordBOImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 16:05
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsurePersonnalRecordBOImpl extends HsafBO implements InsurePersonnalRecordBO {

    @Resource
    InsureDiseaseRecordDAO insureDiseaseRecordDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureUnifiedPayOutptService insureUnifiedPayOutptService;

    @Resource
    private InsureIndividualVisitDAO  insureIndividualVisitDAO;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param insureDiseaseRecordDTO@Method insertInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备案
     * @Param insureDiseaseRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    @Override
    public Boolean insertInsureDiseaseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO) {
        String hospCode = insureDiseaseRecordDTO.getHospCode();
        String insureRegCode = insureDiseaseRecordDTO.getRegCode();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2503);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>(13);
        paramMap.put("psn_no", insureDiseaseRecordDTO.getPsnNo()); // 人员编号
        paramMap.put("insutype", insureDiseaseRecordDTO.getInsutype()); // 业业务类型
        paramMap.put("opsp_dise_code", insureDiseaseRecordDTO.getOpspDiseCode()); // 门慢门特病种目录代码
        paramMap.put("opsp_dise_name", insureDiseaseRecordDTO.getOpspDiseName()); // 门慢门特病种名称
        paramMap.put("tel", insureDiseaseRecordDTO.getTel()); // 联系电话
        paramMap.put("addr", insureDiseaseRecordDTO.getAddr()); // 联系地址
        paramMap.put("insu_optins", insureConfigurationDTO.getRegCode()); // 参保机构医保区划
        paramMap.put("ide_fixmedins_no", insureDiseaseRecordDTO.getIdeFixmedinsNo()); // 鉴定定点医药机构编号
        paramMap.put("ide_fixmedins_name", insureDiseaseRecordDTO.getIdeFixmedinsName()); // 鉴定定点医药机构名称
        paramMap.put("hosp_ide_date", DateUtils.format(insureDiseaseRecordDTO.getHospIdeDate(), DateUtils.Y_M_DH_M_S)); // 医院鉴定日期
        paramMap.put("diag_dr_codg", insureDiseaseRecordDTO.getDiagDrCodg()); // 诊断医师编码
        paramMap.put("diag_dr_name", insureDiseaseRecordDTO.getDiagDrName()); // 诊断医师姓名
        paramMap.put("begndate", DateUtils.format(insureDiseaseRecordDTO.getBegndate(), DateUtils.Y_M_DH_M_S));  // 开始日期
        paramMap.put("enddate", DateUtils.format(insureDiseaseRecordDTO.getEnddate(), DateUtils.Y_M_DH_M_S));  // 结束日期
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台人员慢特病备案入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if(StringUtils.isEmpty(resultJson)){
            throw new AppException("无法访问统一支付平台");
        }
        logger.info("统一支付平台人员慢特病备案回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson,Map.class);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> result = (Map<String, Object>) outputMap.get("result");
        if ("999".equals(MapUtils.get(resultMap,"code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap,"infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        if(StringUtils.isEmpty(MapUtils.get(result,"trt_dcla_detl_sn"))){
            throw new AppException("门慢门特备案失败,医保无备案流水号返回");
        }
        insureDiseaseRecordDTO.setTrtDclaDetlSn(result.get("trt_dcla_detl_sn").toString());
        insureDiseaseRecordDTO.setId(SnowflakeUtils.getId());
        insureDiseaseRecordDTO.setCrteTime(DateUtils.getNow());
        insureDiseaseRecordDAO.insertRecordDisease(insureDiseaseRecordDTO);
        return true;
    }

    /**
     * @param insureDiseaseRecordDTO
     * @Method deleteInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备案撤销
     * 备案撤销之前，先要判断该患者的备案信息，是否被医保中心审核
     * @Param insureDiseaseRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    @Override
    public Boolean deleteInsureDiseaseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO) {
        String memo = insureDiseaseRecordDTO.getMemo();
        insureDiseaseRecordDTO = insureDiseaseRecordDAO.getById(insureDiseaseRecordDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("psnNo", insureDiseaseRecordDTO.getPsnNo());
        map.put("hospCode", insureDiseaseRecordDTO.getHospCode());

        String orgCode = insureDiseaseRecordDTO.getRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(insureDiseaseRecordDTO.getHospCode());
        configurationDTO.setRegCode(orgCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);

        map.put("insureRegCode", configurationDTO.getOrgCode());
        Map<String, Object> data = insureUnifiedPayOutptService.UP_5301(map).getData();
        List<Map<String, Object>> mapList = MapUtils.get(data, "mapList");
        if (!ListUtils.isEmpty(mapList)) {
            StringBuilder stringBuilder = null;
            String str = "";
            for (Map<String, Object> item : mapList) {
                stringBuilder = new StringBuilder();
                str += stringBuilder.append(item.get("opsp_dise_name")).toString();
            }
            throw new AppException("该人员备案的【" + str + "】疾病已经在医保中心审核,医院无法撤销");
        }

        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2504);  //交易编号
        httpParam.put("insuplc_admdvs", configurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", configurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", configurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", configurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(configurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>(13);
        paramMap.put("psn_no", insureDiseaseRecordDTO.getPsnNo()); // 人员编号
        paramMap.put("trt_dcla_detl_sn", insureDiseaseRecordDTO.getTrtDclaDetlSn()); // 待遇申报明细流水号
        paramMap.put("memo", memo); // 备注 -- 填写撤销原因

        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台人员慢特病备案撤销入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(configurationDTO.getUrl(), json);
        logger.info("统一支付平台人员慢特病备案撤销回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap,"code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap,"infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        insureDiseaseRecordDAO.deleteRecordDisease(insureDiseaseRecordDTO);
        return true;
    }

    /**
     * @param map
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备查查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 16:32
     * @Return
     */
    @Override
    public PageDTO queryPageInsureDiseaseRecord(Map<String, Object> map) {
        InsureDiseaseRecordDTO diseaseRecordDTO = MapUtils.get(map, "insureDiseaseRecordDTO");
        PageHelper.startPage(diseaseRecordDTO.getPageNo(), diseaseRecordDTO.getPageSize());
        List<InsureDiseaseRecordDTO> diseaseRecordDTOList = insureDiseaseRecordDAO.queryPage(diseaseRecordDTO);
        return PageDTO.of(diseaseRecordDTOList);
    }

    /**
     * @param map
     * @Method insertInsureFixRecord
     * @Desrciption 医保统一支付平台：新增人员定点备案
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 17:36
     * @Return
     */
    @Override
    public Boolean insertInsureFixRecord(Map<String, Object> map) {
        InsureFixPersonnalRecordDTO fixPersonnalRecordDTO = MapUtils.get(map, "fixPersonnalRecordDTO");
        String hospCode = fixPersonnalRecordDTO.getHospCode();
        String insureRegCode = fixPersonnalRecordDTO.getRegCode();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();


        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        String orderNo = insureDiseaseRecordDAO.queryInptRecordNo(fixPersonnalRecordDTO);
        Integer count ;
        if (StringUtils.isEmpty(orderNo)) {
            count = 0;
        } else {
            count = Integer.parseInt(orderNo);
        }
        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2505);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("psn_no", fixPersonnalRecordDTO.getPsnNo());
        paramMap.put("tel", fixPersonnalRecordDTO.getTel());
        paramMap.put("addr", fixPersonnalRecordDTO.getAddr());
        paramMap.put("biz_appy_type", fixPersonnalRecordDTO.getBizAppyType());
        paramMap.put("begndate", fixPersonnalRecordDTO.getBegndate());
        paramMap.put("enddate", fixPersonnalRecordDTO.getEnddate());
        paramMap.put("agnter_name", fixPersonnalRecordDTO.getAgnterName());
        paramMap.put("agnter_cert_type", fixPersonnalRecordDTO.getAgnterCertType());
        paramMap.put("agnter_certno", fixPersonnalRecordDTO.getAgnterCertno());
        paramMap.put("agnter_tel", fixPersonnalRecordDTO.getAgnterTel());
        paramMap.put("agnter_addr", fixPersonnalRecordDTO.getAgnterAddr());
        paramMap.put("agnter_rlts", fixPersonnalRecordDTO.getAgnterRlts());
        paramMap.put("fix_srt_no", (count++) +"");
        paramMap.put("fixmedins_code", fixPersonnalRecordDTO.getFixmedinsCode());
        paramMap.put("fixmedins_name", fixPersonnalRecordDTO.getFixmedinsName());
        paramMap.put("memo", fixPersonnalRecordDTO.getMemo());
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台人员定点备案入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logger.info("统一支付平台人员定点备案回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap,"code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap,"infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> result = (Map<String, Object>) outputMap.get("result");
        if(StringUtils.isEmpty(MapUtils.get(result,"trt_dcla_detl_sn"))){
            throw new AppException("新增人员定点备案失败,医保无备案流水号返回");
        }
        fixPersonnalRecordDTO.setId(SnowflakeUtils.getId());
        fixPersonnalRecordDTO.setFixSrtNo(count +"");
        fixPersonnalRecordDTO.setFixmedinsCode(insureConfigurationDTO.getOrgCode());
        fixPersonnalRecordDTO.setInsureRegCode(insureConfigurationDTO.getOrgCode());
        fixPersonnalRecordDTO.setTrtDclaDetlSn(result.get("trt_dcla_detl_sn").toString());
        insureDiseaseRecordDAO.insertInsureFixPersonnalRecord(fixPersonnalRecordDTO);
        return true;
    }

    /**
     * @param map
     * @Method insertInsureFixRecord
     * @Desrciption 医保统一支付平台：撤销人员定点备案
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 17:36
     * @Return
     */
    @Override
    public Boolean deleteInsureFixRecord(Map<String, Object> map) {
        InsureFixPersonnalRecordDTO fixPersonnalRecordDTO = MapUtils.get(map, "fixPersonnalRecordDTO");
        String memo = fixPersonnalRecordDTO.getMemo();
        fixPersonnalRecordDTO = insureDiseaseRecordDAO.getFixRecordById(fixPersonnalRecordDTO);
        map.put("psnNo", fixPersonnalRecordDTO.getPsnNo());
        map.put("hospCode", fixPersonnalRecordDTO.getHospCode());
        map.put("bizAppyType",fixPersonnalRecordDTO.getBizAppyType());
        String orgCode = fixPersonnalRecordDTO.getInsureRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(fixPersonnalRecordDTO.getHospCode());
        configurationDTO.setOrgCode(orgCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);

        map.put("insureRegCode", configurationDTO.getOrgCode());
        Map<String, Object> data = insureUnifiedPayOutptService.UP_5302(map).getData();
        List<Map<String,Object>> psnfixmedinMap = MapUtils.get(data, "psnfixmedinMap");
//        if(!ListUtils.isEmpty(psnfixmedinMap)){
//            throw new AppException("备案已经审核,不允许撤销");
//        }
        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2506);  //交易编号
        httpParam.put("insuplc_admdvs", configurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", configurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", configurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", configurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(fixPersonnalRecordDTO.getFixmedinsCode()));
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("psn_no", fixPersonnalRecordDTO.getPsnNo());
        paramMap.put("trt_dcla_detl_sn", fixPersonnalRecordDTO.getTrtDclaDetlSn());
        paramMap.put("memo", memo);
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台人员定点备案撤销入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(configurationDTO.getUrl(), json);
        if(StringUtils.isEmpty(resultJson)){
            throw new AppException("无法访问医保统一支付平台");
        }
        logger.info("统一支付平台人员定点备案撤销回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        insureDiseaseRecordDAO.deleteInsureFixPersonnalRecord(fixPersonnalRecordDTO);
        return true;
    }

    /**
     * @param map
     * @Method queryPageInsureFixRecord
     * @Desrciption 医保统一支付平台：人员定点备案分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 16:32
     * @Return
     */
    @Override
    public PageDTO queryPageInsureFixRecord(Map<String, Object> map) {
        InsureFixPersonnalRecordDTO fixPersonnalRecordDTO = MapUtils.get(map, "fixPersonnalRecordDTO");
        PageHelper.startPage(fixPersonnalRecordDTO.getPageNo(), fixPersonnalRecordDTO.getPageSize());
        List<InsureFixPersonnalRecordDTO> recordDTOList = insureDiseaseRecordDAO.queryPageInsureFixRecord(fixPersonnalRecordDTO);
        return PageDTO.of(recordDTOList);
    }

    /**
     * @param insureInptRecordDTO
     * @Method queryPageInptRecord
     * @Desrciption 转院院备案分页查询
     * @Param insureInptRecordDTO
     * @Author fuhui
     * @Date 2021/4/7 10:26
     * @Return PageDTO
     */
    @Override
    public PageDTO queryPageInptRecord(InsureInptRecordDTO insureInptRecordDTO) {
        PageHelper.startPage(insureInptRecordDTO.getPageNo(), insureInptRecordDTO.getPageSize());
        List<InsureInptRecordDTO> recordDTOList = insureDiseaseRecordDAO.queryPageInptRecord(insureInptRecordDTO);
        return PageDTO.of(recordDTOList);
    }


    /**
     * @Method insertInsureInptRecord
     * @Desrciption 医保统一支付平台：新增转院备案信息
     * @Param insureDiseaseRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    public Boolean insertInptRecord(InsureInptRecordDTO insureInptRecordDTO) {
        Map<String,Object> map  = new HashMap<>();
        String hospCode = insureInptRecordDTO.getHospCode();
        map.put("visitId",insureInptRecordDTO.getVisitId());
        map.put("hospCode",hospCode);
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        String insureOrgCode = insureIndividualVisitDTO.getMedicineOrgCode();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureOrgCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2501);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));

        Map<String, Object> paramMap = new HashMap<>(13);
        paramMap.put("psn_no", insureIndividualVisitDTO.getAac001()); // 人员编号
        paramMap.put("insutype", insureIndividualVisitDTO.getAae140()); // 险种类型
        paramMap.put("tel", insureInptRecordDTO.getTel()); // 联系电话
        paramMap.put("addr", insureInptRecordDTO.getAddr()); // 联系地址
        paramMap.put("insu_optins", insureConfigurationDTO.getMdtrtareaAdmvs()); // 参保机构医保区划
        paramMap.put("diag_code", insureIndividualVisitDTO.getVisitIcdCode()); // 诊断代码
        paramMap.put("diag_name", insureIndividualVisitDTO.getVisitIcdName()); // 诊断名称
        paramMap.put("dise_cond_dscr", insureInptRecordDTO.getDiseCondDscr()); // 疾病病情描述
        paramMap.put("reflin_medins_no", insureInptRecordDTO.getReflinMedinsNo()); // 转往定点医药机构编号
        paramMap.put("reflin_medins_name",insureInptRecordDTO.getReflinMedinsName()); // 鉴定定点医药机构名称
        paramMap.put("mdtrtarea_admdvs", insureInptRecordDTO.getMdtrtareaAdmdvs()); // 就医地行政区划
        paramMap.put("hosp_agre_refl_flag", insureInptRecordDTO.getHospAgreReflFlag()); // 医院同意转院标志
        paramMap.put("refl_type", insureInptRecordDTO.getReflType()); // 转院类型
        paramMap.put("refl_date", DateUtils.format(insureInptRecordDTO.getReflDate(), DateUtils.Y_M_DH_M_S)); // 转院日期
        paramMap.put("refl_rea", insureInptRecordDTO.getReflRea()); // 转院原因
        paramMap.put("refl_opnn", insureInptRecordDTO.getReflOpnn()); // 转院意见
        paramMap.put("begndate", DateUtils.format(insureInptRecordDTO.getBegndate(),DateUtils.Y_M_DH_M_S));  // 开始日期
        paramMap.put("enddate", DateUtils.format(insureInptRecordDTO.getEnddate(),DateUtils.Y_M_DH_M_S));  // 结束日期
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("refmedin", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台转院备案入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if(StringUtils.isEmpty(resultJson)){
            throw new AppException("调用统一支付平台无响应");
        }
        logger.info("统一支付平台人员转院备案回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap,"code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap,"infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> result = (Map<String, Object>) outputMap.get("result");
        if(StringUtils.isEmpty(MapUtils.get(result,"trt_dcla_detl_sn"))){
            throw new AppException("新增转院备案失败,医保无备案流水号返回");
        }
        insureInptRecordDTO.setTrtDclaDetlSn(result.get("trt_dcla_detl_sn").toString());
        insureInptRecordDTO.setId(SnowflakeUtils.getId());
        insureInptRecordDTO.setInsutype(insureIndividualVisitDTO.getAae140());
        insureInptRecordDTO.setInsureRegCode(insureConfigurationDTO.getOrgCode());
        insureInptRecordDTO.setCrteTime(DateUtils.getNow());
        insureDiseaseRecordDAO.insertInptRecord(insureInptRecordDTO);
        return true;
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
    public InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map){
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
     * @param inptRecordDTO
     * @Method deleteInptRecord
     * @Desrciption 医保统一支付平台：撤销转院备案
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 17:36
     * @Return
     */
    public Boolean deleteInptRecord(InsureInptRecordDTO inptRecordDTO) {
        String memo = inptRecordDTO.getMemo();
        inptRecordDTO = insureDiseaseRecordDAO.getInptRecordById(inptRecordDTO);
        String hospCode = inptRecordDTO.getHospCode();
        String orgCode = inptRecordDTO.getInsureRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(inptRecordDTO.getHospCode());
        configurationDTO.setOrgCode(orgCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        map.put("regsiterTime",inptRecordDTO.getRegsiterTime());
        map.put("insureRegCode", configurationDTO.getOrgCode());
        map.put("psnNo", inptRecordDTO.getPsnNo());
//        Map<String, Object> queryMap = UP_5304(map);
//        List<Map<String, Object>> mapList = MapUtils.get(queryMap, "refmedin");
//        if (!ListUtils.isEmpty(mapList)) {
//            throw new AppException("该人员转院备案信息已经在医保中心审核,医院无法撤销");
//        }

        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2502);  //交易编号
        httpParam.put("insuplc_admdvs", configurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", configurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", configurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", configurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(configurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>(5);
        paramMap.put("psn_no", inptRecordDTO.getPsnNo());
        paramMap.put("trt_dcla_detl_sn", inptRecordDTO.getTrtDclaDetlSn());
        paramMap.put("memo", memo);
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台人员转院备案撤销入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(configurationDTO.getUrl(), json);
        if(StringUtils.isEmpty(resultJson)){
            throw new AppException("调用统一支付平台无响应");
        }
        logger.info("统一支付平台人员转院备案撤销回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        insureDiseaseRecordDAO.deleteInsureInptRecord(inptRecordDTO);
        return true;
    }

    /**
     * @Method UP_5303
     * @Desrciption 医保统一支付平台：转院备案查询
     * @Param map
     * @Author fuhui
     * @Date 2021/4/2 9:12
     * @Return
     **/
    public Map<String, Object> UP_5304(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_5304);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        if(StringUtils.isEmpty(MapUtils.get(map, "begntime"))){
            dataMap.put("begntime", DateUtils.format(MapUtils.get(map, "regsiterTime"), DateUtils.Y_M_DH_M_S));
        }else{
            dataMap.put("begntime", DateUtils.format(MapUtils.get(map, "begntime"), DateUtils.Y_M_DH_M_S));
        }
        dataMap.put("endtime", null);
        httpParam.put("input", dataMap);
        String dataJson = JSONObject.toJSONString(httpParam);
        logger.info("转院备案查询入参:" + dataJson);
        String url = insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);
        logger.info("转院备案查询回参:" + resultStr);
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr);
        if(StringUtils.isEmpty(resultStr)){
            throw new AppException("调用统一支付平台无响应");
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outptMap = new HashMap<>(1);
        outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> psnfixmedinMap = MapUtils.get(outptMap, "refmedin");
        map.put("psnfixmedinMap", psnfixmedinMap);
        return map;
    }

    /**
     * @Description: 门诊两病备案
     * @Param: [insureDiseaseRecordDTO]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @Override
    public Boolean insertOuptTowDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO) {
        String hospCode = insureDiseaseRecordDTO.getHospCode();
        String insureRegCode = insureDiseaseRecordDTO.getRegCode();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2562);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));


        Map<String, Object> paramMap = new HashMap<>(13);
        paramMap.put("psn_no", insureDiseaseRecordDTO.getPsnNo()); // 人员编号
        paramMap.put("opsp_dise_code", insureDiseaseRecordDTO.getOpspDiseCode()); // 门慢门特病种目录代码
        paramMap.put("opsp_dise_name", insureDiseaseRecordDTO.getOpspDiseName()); // 门慢门特病种名称
        paramMap.put("hosp_opnn", insureDiseaseRecordDTO.getHospOpnn()); // 医院意见
        paramMap.put("hosp_appy_time", DateUtils.format(insureDiseaseRecordDTO.getHospAppyTime(), DateUtils.Y_M_DH_M_S)); // 医院申请时间
        paramMap.put("begndate", DateUtils.format(insureDiseaseRecordDTO.getBegndate(), DateUtils.Y_M_DH_M_S)); // 开始日期
        paramMap.put("enddate", DateUtils.format(insureDiseaseRecordDTO.getEnddate(), DateUtils.Y_M_DH_M_S)); // 结束日期
        paramMap.put("opter_name", insureDiseaseRecordDTO.getOpterName()); // 经办人姓名
        paramMap.put("memo", insureDiseaseRecordDTO.getMemo()); // 备注
        paramMap.put("appy_rea", insureDiseaseRecordDTO.getAppyRea()); // 申请原因
        paramMap.put("symp_dscr", insureDiseaseRecordDTO.getSympDscr()); // 症状描述
        paramMap.put("appyer", insureDiseaseRecordDTO.getAppyer()); // 申请人
        paramMap.put("appyer_tel", insureDiseaseRecordDTO.getAppyerTel());  // 申请人电话
        paramMap.put("appyer_addr", insureDiseaseRecordDTO.getAppyerAddr());  // 申请人地址
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台门诊两病备案入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logger.info("统一支付平台门诊两病备案回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> result = (Map<String, Object>) outputMap.get("result");
        insureDiseaseRecordDTO.setTrtDclaDetlSn(result.get("trtDclaDetlSn").toString());//460000161970177910400030176021
        insureDiseaseRecordDTO.setId(SnowflakeUtils.getId());
        insureDiseaseRecordDTO.setCrteTime(DateUtils.getNow());
        insureDiseaseRecordDAO.insertOuptTowDiseRecord(insureDiseaseRecordDTO);
        return true;
    }

    /**
     * @Description: 门诊两病备案撤销
     * @Param: [insureDiseaseRecordDTO]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @Override
    public Boolean deleteOutptTwoDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO) {
        String memo = insureDiseaseRecordDTO.getMemo();
        insureDiseaseRecordDTO = insureDiseaseRecordDAO.getByIdTwoDise(insureDiseaseRecordDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("psnNo", insureDiseaseRecordDTO.getPsnNo());
        map.put("hospCode", insureDiseaseRecordDTO.getHospCode());

        String orgCode = insureDiseaseRecordDTO.getRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(insureDiseaseRecordDTO.getHospCode());
        configurationDTO.setRegCode(orgCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);

        map.put("insureRegCode", configurationDTO.getOrgCode());

        Map<String, Object> httpParam = new HashMap<String, Object>(6);
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2563);  //交易编号
        httpParam.put("insuplc_admdvs", configurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", configurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", configurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", configurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(configurationDTO.getOrgCode()));


        Map<String, Object> paramMap = new HashMap<>(13);
        paramMap.put("psn_no", insureDiseaseRecordDTO.getPsnNo()); // 人员编号
        paramMap.put("trt_dcla_detl_sn", insureDiseaseRecordDTO.getTrtDclaDetlSn()); // 待遇申报明细流水号
        paramMap.put("memo", memo); // 备注
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        httpParam.put("input", dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台门诊两病备案撤销入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(configurationDTO.getUrl(), json);
        logger.info("统一支付平台门诊两病备案回参撤销:" + resultJson);
        if(StringUtils.isNotEmpty(insureDiseaseRecordDTO.getTrtDclaDetlSn())){
            insureDiseaseRecordDAO.deleteOuptTwoDiseRecord(insureDiseaseRecordDTO);
        }
        return true;
    }

    /**
     * @Description: 门诊两病人员查询
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    @Override
    public PageDTO queryPageOutptTwoDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO) {
        PageHelper.startPage(insureDiseaseRecordDTO.getPageNo(), insureDiseaseRecordDTO.getPageSize());
        List<InsureDiseaseRecordDTO> diseaseRecordDTOList = insureDiseaseRecordDAO.queryPageOutptTwoDiseRecord(insureDiseaseRecordDTO);
        return PageDTO.of(diseaseRecordDTOList);
    }

    /**
     * @Description: 门诊两病备案查询
     * @Param: [map]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    @Override
    public Map<String, Object> queryOutptTwoDiseInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String regCode = MapUtils.get(map,"regCode");
        String psnNo = MapUtils.get(map,"psnNo");
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(regCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String,Object> paramMap = new HashMap<>();

        /**
         * 公共入参
         */
        inputMap.put("infno", Constant.UnifiedPay.REGISTER.UP_5364);  // 交易编号
        inputMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());  //TODO 参保地医保区划
        inputMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划

        dataMap.put("psn_no",psnNo);

        paramMap.put("data",dataMap);
        inputMap.put("input",paramMap);

        String json = JSONObject.toJSONString(inputMap);
        logger.info("人员定点信息查询入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        Map<String,Object> resultMap = JSONObject.parseObject(resultJson,Map.class);
        logger.info("人员定点信息查询回参:" + resultJson);
        Map<String,Object> outptMap = MapUtils.get(resultMap,"output");
        List<Map<String,Object>> resultDataMap =  MapUtils.get(outptMap,"result");
        map.put("resultDataMap",resultDataMap);
        return map;
    }



}
