package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.insure.module.bo.InsurePersonnalRecordBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureDiseaseRecordDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.*;

import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;

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

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;

    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;

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
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","慢特病备案");
        logMap.put("visitId","");
        logMap.put("hospCode",hospCode);
        logMap.put("crteName",insureDiseaseRecordDTO.getCrteName());
        logMap.put("crteId",insureDiseaseRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","0");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.REGISTER.UP_2503, dataMap, logMap);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> result = (Map<String, Object>) outputMap.get("result");
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
        String hospCode =insureDiseaseRecordDTO.getHospCode();
        insureDiseaseRecordDTO = insureDiseaseRecordDAO.getById(insureDiseaseRecordDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("psnNo", insureDiseaseRecordDTO.getPsnNo());
        map.put("hospCode", insureDiseaseRecordDTO.getHospCode());
        String orgCode = insureDiseaseRecordDTO.getRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(insureDiseaseRecordDTO.getHospCode());
        configurationDTO.setRegCode(orgCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);
        Map<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("psn_no", insureDiseaseRecordDTO.getPsnNo()); // 人员编号
        paramMap.put("trt_dcla_detl_sn", insureDiseaseRecordDTO.getTrtDclaDetlSn()); // 待遇申报明细流水号
        paramMap.put("memo", memo); // 备注 -- 填写撤销原因
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","慢特病备案撤销");
        logMap.put("visitId","");
        logMap.put("hospCode",hospCode);
        logMap.put("crteName",insureDiseaseRecordDTO.getCrteName());
        logMap.put("crteId",insureDiseaseRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","0");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, configurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2504, dataMap, logMap);
        if(!MapUtils.isEmpty(resultMap)){
            insureDiseaseRecordDAO.deleteRecordDisease(insureDiseaseRecordDTO);
        }
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
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","人员定点备案");
        logMap.put("visitId","");
        logMap.put("hospCode",hospCode);
        logMap.put("crteName",fixPersonnalRecordDTO.getCrteName());
        logMap.put("crteId",fixPersonnalRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","0");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2505, dataMap, logMap);
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
        String hospCode = MapUtils.get(map,"hospCode");
        fixPersonnalRecordDTO = insureDiseaseRecordDAO.getFixRecordById(fixPersonnalRecordDTO);
        map.put("psnNo", fixPersonnalRecordDTO.getPsnNo());
        map.put("hospCode", fixPersonnalRecordDTO.getHospCode());
        map.put("bizAppyType",fixPersonnalRecordDTO.getBizAppyType());
        String orgCode = fixPersonnalRecordDTO.getInsureRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(fixPersonnalRecordDTO.getHospCode());
        configurationDTO.setOrgCode(orgCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("psn_no", fixPersonnalRecordDTO.getPsnNo());
        paramMap.put("trt_dcla_detl_sn", fixPersonnalRecordDTO.getTrtDclaDetlSn());
        paramMap.put("memo", memo);
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","撤销人员定点备案");
        logMap.put("visitId","");
        logMap.put("hospCode",hospCode);
        logMap.put("crteName",fixPersonnalRecordDTO.getCrteName());
        logMap.put("crteId",fixPersonnalRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, configurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2506, dataMap, logMap);
        if(!MapUtils.isEmpty(resultMap)){
            insureDiseaseRecordDAO.deleteInsureFixPersonnalRecord(fixPersonnalRecordDTO);
        }
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
        if(StringUtils.isEmpty(insureInptRecordDTO.getIsHospital())){
            insureInptRecordDTO.setIsHospital(Constants.SF.F);
        }
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
    @Override
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
        insureConfigurationDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("根据" + insureOrgCode + "医保机构编码获取医保机构配置信息为空");
        }
        //参数校验
        checkRequest(insureInptRecordDTO);
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
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","新增转院备案信息");
        logMap.put("hospCode",hospCode);
        logMap.put("visitId",insureIndividualVisitDTO.getVisitId());
        logMap.put("crteName",insureInptRecordDTO.getCrteName());
        logMap.put("crteId",insureInptRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2501, dataMap, logMap);
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
    @Override
    public Boolean deleteInptRecord(InsureInptRecordDTO inptRecordDTO) {
        String memo = inptRecordDTO.getMemo();
        inptRecordDTO = insureDiseaseRecordDAO.getInptRecordById(inptRecordDTO);
        if(ObjectUtil.isEmpty(inptRecordDTO)){
            throw new AppException("未查询到转院备案数据");
        }
        Map<String,Object> visitMap  = new HashMap<>();
        String hospCode = inptRecordDTO.getHospCode();
        visitMap.put("visitId",inptRecordDTO.getVisitId());
        visitMap.put("hospCode",hospCode);
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(visitMap);
        String orgCode = insureIndividualVisitDTO.getMedicineOrgCode();
        String regCode = insureIndividualVisitDTO.getInsureRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(inptRecordDTO.getHospCode());
        configurationDTO.setOrgCode(orgCode);
        configurationDTO.setRegCode(regCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);
        if (configurationDTO == null) {
            throw new AppException("根据" + orgCode + "医保机构编码获取医保机构配置信息为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        map.put("regsiterTime",inptRecordDTO.getRegsiterTime());
        map.put("insureRegCode", configurationDTO.getRegCode());
        map.put("psnNo", inptRecordDTO.getPsnNo());
        Map<String, Object> paramMap = new HashMap<>(5);
        paramMap.put("psn_no", inptRecordDTO.getPsnNo());
        paramMap.put("trt_dcla_detl_sn", inptRecordDTO.getTrtDclaDetlSn());
        paramMap.put("memo", memo);
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","撤销转院备案");
        logMap.put("visitId",inptRecordDTO.getVisitId());
        logMap.put("crteName",inptRecordDTO.getCrteName());
        logMap.put("hospCode",hospCode);
        logMap.put("crteId",inptRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, configurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2502, dataMap, logMap);
        if(!MapUtils.isEmpty(resultMap)){
            insureDiseaseRecordDAO.deleteInsureInptRecord(inptRecordDTO);
        }
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
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        if(StringUtils.isEmpty(MapUtils.get(map, "begntime"))){
            dataMap.put("begntime", DateUtils.format(MapUtils.get(map, "regsiterTime"), DateUtils.Y_M_DH_M_S));
        }else{
            dataMap.put("begntime", DateUtils.format(MapUtils.get(map, "begntime"), DateUtils.Y_M_DH_M_S));
        }
        dataMap.put("endtime", null);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","转院备案查询");
        logMap.put("hospCode",hospCode);
        logMap.put("visitId","");
        logMap.put("isHospital","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(),  Constant.UnifiedPay.REGISTER.UP_5304, dataMap, logMap);
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
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","门诊两病备案");
        logMap.put("hospCode",hospCode);
        logMap.put("visitId",insureDiseaseRecordDTO.getVisitId());
        logMap.put("crteName",insureDiseaseRecordDTO.getCrteName());
        logMap.put("crteId",insureDiseaseRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2562, dataMap, logMap);
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
        String hospCode =  insureDiseaseRecordDTO.getHospCode();
        insureDiseaseRecordDTO = insureDiseaseRecordDAO.getByIdTwoDise(insureDiseaseRecordDTO);
        String orgCode = insureDiseaseRecordDTO.getRegCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(insureDiseaseRecordDTO.getHospCode());
        configurationDTO.setRegCode(orgCode);
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);
        Map<String, Object> paramMap = new HashMap<>(13);
        paramMap.put("psn_no", insureDiseaseRecordDTO.getPsnNo()); // 人员编号
        paramMap.put("trt_dcla_detl_sn", insureDiseaseRecordDTO.getTrtDclaDetlSn()); // 待遇申报明细流水号
        paramMap.put("memo", memo); // 备注
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","门诊两病备案撤销");
        logMap.put("hospCode",hospCode);
        logMap.put("visitId",insureDiseaseRecordDTO.getVisitId());
        logMap.put("crteName",insureDiseaseRecordDTO.getCrteName());
        logMap.put("crteId",insureDiseaseRecordDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, configurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2562, dataMap, logMap);
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
        Map<String, Object> dataMap = new HashMap<>();
        Map<String,Object> paramMap = new HashMap<>();
        dataMap.put("psn_no",psnNo);
        paramMap.put("data",dataMap);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","门诊两病备案查询");
        logMap.put("hospCode",hospCode);
        logMap.put("crteId",MapUtils.get(map,"crteId"));
        logMap.put("crteName",MapUtils.get(map,"crteName"));
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2562, dataMap, logMap);
        Map<String,Object> outptMap = MapUtils.get(resultMap,"output");
        List<Map<String,Object>> resultDataMap =  MapUtils.get(outptMap,"result");
        map.put("resultDataMap",resultDataMap);
        return map;
    }

    /**
     * @param map
     * @Method insertSpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    @Override
    public Boolean insertSpecialOutptRecord(Map<String, Object> map) {
        String hospCode  = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"regCode");
        InsureConfigurationDTO insureConfigurationDTO = insureUnifiedCommonUtil.getInsureInsureConfiguration(hospCode, insureRegCode);
        map.put("evt_type","");
        map.put("dcla_souc","");
        map.put("expContent","");
        map.put("fixmedins_code",insureConfigurationDTO.getOrgCode());
        map.put("fix_blng_admdvs",insureConfigurationDTO.getMdtrtareaAdmvs());
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("data",map);

        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","门诊单病种备案");
        logMap.put("hospCode",hospCode);
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","0");
        logMap.put("crteId",MapUtils.get(map,"crteId"));
        logMap.put("crteName",MapUtils.get(map,"crteName"));
        Map<String, Object> resultDataMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), "2585", paramMap,logMap);
        Map<String,Object> outptMap = MapUtils.get(resultDataMap, "output");
        Map<String,Object> dataMap = MapUtils.get(outptMap, "data");
        map.put("id",SnowflakeUtils.getId());
        map.put("evtsn",MapUtils.get(dataMap,"evtsn")); // 事件流水号
        map.put("serv_matt_inst_id",MapUtils.get(dataMap,"serv_matt_inst_id")); // 服务事项实例ID
        map.put("serv_matt_node_inst_id",MapUtils.get(dataMap,"serv_matt_node_inst_id")); // 服务事项环节实例ID
        map.put("evt_inst_id",MapUtils.get(dataMap,"evt_inst_id")); // 事件实例ID
        map.put("trt_dcla_detl_sn",MapUtils.get(dataMap,"trt_dcla_detl_sn")); // 待遇申报明细流水号
        insureDiseaseRecordDAO.insertSpecialOutptRecord(map);
        return true;
    }

    /**
     * @param map
     * @Method insertSpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案撤销
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    @Override
    public Boolean deleteSpecialOutptRecord(Map<String, Object> map) {
        String hospCode  = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"insureRegCode");
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",map);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","门诊单病种备案撤销");
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","0");
        logMap.put("hospCode",hospCode);
        logMap.put("crteId",MapUtils.get(map,"crteId"));
        logMap.put("crteName",MapUtils.get(map,"crteName"));
        insureUnifiedCommonUtil.commonInsureUnified(hospCode,insureRegCode, "2586", dataMap,logMap);
        insureDiseaseRecordDAO.deleteSpecialOutptRecord(map);
        return true;
    }

    /**
     * @param map
     * @Method querySpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案登记查询
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    @Override
    public PageDTO querySpecialOutptRecord(Map<String, Object> map) {
        String hospCode  = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"regCode");
        InsureConfigurationDTO insureInsureConfiguration = insureUnifiedCommonUtil.getInsureInsureConfiguration(hospCode, insureRegCode);
        Map<String,Object> dataMap = new HashMap<>();
        map.put("fixmedins_code",insureInsureConfiguration.getOrgCode());
        dataMap.put("data",map);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","门诊单病种备案登记查询");
        logMap.put("isHospital","0");
        logMap.put("hospCode",hospCode);
        logMap.put("crteId",MapUtils.get(map,"crteId"));
        logMap.put("crteName",MapUtils.get(map,"crteName"));
        logMap.put("crteTime",DateUtils.getNow());
        Map<String,Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode,insureRegCode, "5385", dataMap,logMap);
        Map<String,Object> outptMap = MapUtils.get(resultMap,"output");
        List<Map<String,Object> > dataMapList = MapUtils.get(outptMap,"data");
        if(ListUtils.isEmpty(dataMapList)){
            throw  new AppException("没有查询到备案信息");
        }
        return PageDTO.of(dataMapList);
    }

    /**
     * @param insureSpecialRecordDTO
     * @Method queryPage
     * @Desrciption 江西省：门诊单病种备案分页查询（his）
     * @Param
     * @Author fuhui
     * @Date 2021/11/29 10:24
     * @Return
     */
    @Override
    public PageDTO queryPageSpecialRecord(InsureSpecialRecordDTO insureSpecialRecordDTO) {
        PageHelper.startPage(insureSpecialRecordDTO.getPageNo(),insureSpecialRecordDTO.getPageSize());
        List<InsureSpecialRecordDTO> list = insureDiseaseRecordDAO.queryPageSpecialRecord(insureSpecialRecordDTO);
        return PageDTO.of(list);
    }

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:34
     * @Description 人员意外伤害备案
     * @param insureAccidentalInjuryDTO
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryPageInsureAccidentInjureRecord(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO) {
        PageHelper.startPage(insureAccidentalInjuryDTO.getPageNo(),insureAccidentalInjuryDTO.getPageSize());
        List<InsureAccidentalInjuryDTO> list = insureDiseaseRecordDAO.queryPageInsureAccidentInjureRecord(insureAccidentalInjuryDTO);
        return PageDTO.of(list);
    }
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:34
     * @Description 人员意外伤害备案
     * @param insureAccidentalInjuryDTO
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public Boolean insertInsureAccidentInjureRecord(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO) {
        insureAccidentalInjuryDTO.setDclaSouc("01");
        insureAccidentalInjuryDTO.setChkPayFlag("01");
       // String requestBody =  ApiUtils.requestJson("data",reginsApiDTO, ApiNoEnum.NAT_8506.getCode());
        String hospCode = insureAccidentalInjuryDTO.getHospCode();
        String insureRegCode = insureAccidentalInjuryDTO.getRegCode();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map<String, Object> paramMap = new HashMap<>(13);
        paramMap.put("dcla_souc", insureAccidentalInjuryDTO.getDclaSouc()); // 申报来源
        paramMap.put("chk_pay_flag", insureAccidentalInjuryDTO.getChkPayFlag()); // 审核支付标志
        paramMap.put("psn_no", insureAccidentalInjuryDTO.getPsnNo()); // 人员编号
        paramMap.put("insutype", insureAccidentalInjuryDTO.getInsutype()); // 险种类型
        paramMap.put("psn_insu_rlts_id", insureAccidentalInjuryDTO.getPsnInsuRltsId()); //人员参保关系ID
        paramMap.put("psn_cert_type", insureAccidentalInjuryDTO.getPsnCertType()); //人员证件类型
        paramMap.put("certno", insureAccidentalInjuryDTO.getCertno()); //人员证件号码
        paramMap.put("psn_name", insureAccidentalInjuryDTO.getPsnName()); //人员姓名
        paramMap.put("gend", insureAccidentalInjuryDTO.getGend()); //性别
        paramMap.put("naty", insureAccidentalInjuryDTO.getNaty()); //民族
        paramMap.put("brdy", insureAccidentalInjuryDTO.getBrdy()); //出生日期
        paramMap.put("tel", insureAccidentalInjuryDTO.getTel()); //联系电话
        paramMap.put("insu_admdvs", insureConfigurationDTO.getRegCode()); // 所属医保区划
        paramMap.put("addr", insureAccidentalInjuryDTO.getAddr()); // 联系地址
        paramMap.put("begndate", DateUtils.format(insureAccidentalInjuryDTO.getBegndate(), DateUtils.Y_M_DH_M_S));  // 开始日期
        paramMap.put("enddate", DateUtils.format(insureAccidentalInjuryDTO.getEnddate(), DateUtils.Y_M_DH_M_S));  // 结束日期
        paramMap.put("emp_no", insureAccidentalInjuryDTO.getEmpNo()); // 单位编号
        paramMap.put("emp_name", insureAccidentalInjuryDTO.getEmpName()); // 联系地址
        paramMap.put("mdtrtarea_admdvs", insureAccidentalInjuryDTO.getMdtrtareaAdmdvs()); // 联系地址
        paramMap.put("fixmedins_code", insureAccidentalInjuryDTO.getFixmedinsCode()); // 联系地址
        paramMap.put("fixmedins_name", insureAccidentalInjuryDTO.getFixmedinsName()); // 联系地址
        paramMap.put("hosp_lv", insureAccidentalInjuryDTO.getHospLv()); // 联系地址
        paramMap.put("adm_time", insureAccidentalInjuryDTO.getAdmTime()); // 入院时间
        paramMap.put("trum_time", insureAccidentalInjuryDTO.getTrumTime()); // 受伤时间
        paramMap.put("trum_site", insureAccidentalInjuryDTO.getTrumSite()); // 受伤地点
        paramMap.put("trum_rea", insureAccidentalInjuryDTO.getTrumRea()); // 致伤原因
        paramMap.put("chk_pay_flag", insureAccidentalInjuryDTO.getChkPayFlag()); // 审核支付标志
        paramMap.put("agnter_name", insureAccidentalInjuryDTO.getAgnterName()); // 代办人姓名
        paramMap.put("agnter_cert_type", insureAccidentalInjuryDTO.getAgnterCertType()); // 代办人证件类型
        paramMap.put("agnter_certno", insureAccidentalInjuryDTO.getAgnterCertno()); // 代办人证件号码
        paramMap.put("agnter_tel", insureAccidentalInjuryDTO.getAgnterTel()); // 代办人联系方式
        paramMap.put("agnter_addr", insureAccidentalInjuryDTO.getAgnterAddr()); // 代办人联系地址
        paramMap.put("memo", insureAccidentalInjuryDTO.getMemo()); // 备注
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("data", paramMap);
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("msgName","人员意外伤害备案");
        logMap.put("visitId","");
        logMap.put("hospCode",hospCode);
        logMap.put("crteName",insureAccidentalInjuryDTO.getCrteName());
        logMap.put("crteId",insureAccidentalInjuryDTO.getCrteId());
        logMap.put("crteTime",DateUtils.getNow());
        logMap.put("isHospital","0");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.REGISTER.UP_2507, dataMap, logMap);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> result = (Map<String, Object>) outputMap.get("result");
      /*  if(StringUtils.isEmpty(MapUtils.get(result,"trt_dcla_detl_sn "))){
            throw new AppException("人员意外伤害备案失败,医保无备案流水号返回");
        }*/
        try{
            insureAccidentalInjuryDTO.setTrtDclaDetlSn(result.get("trt_dcla_detl_sn").toString());
            insureAccidentalInjuryDTO.setId(SnowflakeUtils.getId());
            insureAccidentalInjuryDTO.setCrteTime(DateUtils.getNow());
            //insureDiseaseRecordDAO.insertRecordAccidentInjury(insureAccidentalInjuryDTO);
        }catch (Exception e){
            throw new AppException(e.getMessage());
        }

        return true;
    }

    private boolean checkRequest(InsureInptRecordDTO insureInptRecordDTO) {
        if(insureInptRecordDTO.getReflinMedinsNo() == null){
            throw new AppException("转往定点医药机构编号不能为空");
        }
        if(insureInptRecordDTO.getReflinMedinsName() == null){
            throw new AppException("鉴定定点医药机构名称不能为空");
        }
        if(insureInptRecordDTO.getMdtrtareaAdmdvs() == null){
            throw new AppException("就医地行政区划不能为空");
        }
        if(insureInptRecordDTO.getHospAgreReflFlag() == null){
            throw new AppException("医院同意转院标志不能为空");
        }
        if(insureInptRecordDTO.getReflType() == null){
            throw new AppException("转院类型不能为空");
        }
        if(insureInptRecordDTO.getReflDate() == null){
            throw new AppException("转院日期不能为空");
        }
        if(insureInptRecordDTO.getReflRea() == null){
            throw new AppException("转院原因不能为空");
        }
        if(insureInptRecordDTO.getReflOpnn() == null){
            throw new AppException("转院意见不能为空");
        }
        if(insureInptRecordDTO.getBegndate() == null){
            throw new AppException("开始日期不能为空");
        }
        if(insureInptRecordDTO.getEnddate() == null){
            throw new AppException("结束日期不能为空");
        }
        return true;
    }
}
