package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.UnifiedCommon;
import cn.hsa.module.insure.clinica.dao.ClinicalExaminationInfoDAO;
import cn.hsa.module.insure.clinica.dto.CommentDTO;
import cn.hsa.module.insure.clinica.dto.ClinicalExaminationInfoDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedClinicalBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureUnifiedClinicalDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.entity.InsureBactreailReportDO;
import cn.hsa.module.insure.module.entity.InsureDrugSensitiveReportDO;
import cn.hsa.module.insure.module.entity.InsureNoStructReportDO;
import cn.hsa.module.insure.module.entity.InsurePathologicalReportDO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static cn.hutool.core.map.MapUtil.toCamelCaseMap;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsureUnifiedClinicalBOImpl
 * @Description: TODO  临床辅助业务模块
 *
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/3 9:45
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedClinicalBOImpl extends HsafBO implements InsureUnifiedClinicalBO {

    @Resource
    private UnifiedCommon unifiedCommon;

    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private InsureUnifiedClinicalDAO insureUnifiedClinicalDAO;

    @Resource
    private ClinicalExaminationInfoDAO clinicalExaminationInfoDAO;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录 -- 上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/

    public boolean updateClinicalExaminationReportRecord(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO){
        if(clinicalExaminationInfoDTO.getUuid() == null){
            throw new AppException("传入的uuid主键为空为空");
        }
        ClinicalExaminationInfoDTO byUuid = clinicalExaminationInfoDAO.queryByUuid(clinicalExaminationInfoDTO.getUuid());
        if(byUuid == null){
            throw new AppException("根据uui未查询到临床检查报告记录");
        }
        String hospCode = byUuid.getHospCode();
        String visitId = byUuid.getMdtrtSn();
        String medicalRegNo = byUuid.getMdtrtId();
        Map<String,Object> map = new HashMap<>() ;
        map.put("hospCode",hospCode);
        map.put("visitId",visitId);
        map.put("medicalRegNo",medicalRegNo);
        //查询患者信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = unifiedCommon.commonGetVisitInfo(map);

        Map<String,Object> configMap = new HashMap<>() ;
        configMap.put("hospCode",hospCode);
        configMap.put("insureRegCode",insureIndividualVisitDTO.getInsureRegCode());
        configMap.put("medicineOrgCode",insureIndividualVisitDTO.getMedicineOrgCode());

        //查询医保机构信息
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(configMap);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clinicalExaminationInfoDTO",byUuid);
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", visitId);
        paramMap.put("hospCode",hospCode);
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", insureIndividualVisitDTO.getIsHospital());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.CLINICAL_EXAMINATION_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
        interfaceParamDTO.setVisitId(visitId);
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.CLINICAL_EXAMINATION_UPLOAD, interfaceParamDTO);
        return true;
    }

    /**
     * @param map
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--新增数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertNoStructReportRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsureNoStructReportDO insureNoStructReportDO = MapUtils.get(map,"insureNoStructReportDO");
        insureNoStructReportDO.setHospCode(hospCode);
        insureNoStructReportDO.setId(SnowflakeUtils.getId());
        return insureUnifiedClinicalDAO.insertNoStructReportRecord(insureNoStructReportDO);
    }

    /**
     * @param map
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPageNoStructReportRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsureNoStructReportDO insureNoStructReportDO = MapUtils.get(map,"insureNoStructReportDO");
        insureNoStructReportDO.setHospCode(hospCode);
        PageHelper.startPage(insureNoStructReportDO.getPageNo(),insureNoStructReportDO.getPageSize());
        List<InsureNoStructReportDO> noStructReportDOList  =insureUnifiedClinicalDAO.queryPageNoStructReportRecord(insureNoStructReportDO);
        return PageDTO.of(noStructReportDOList);
    }

    /**
     * @param map
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertPathologicalReportRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsurePathologicalReportDO insurePathologicalReportDO = MapUtils.get(map,"insurePathologicalReportDO");
        insurePathologicalReportDO.setId(SnowflakeUtils.getId());
        insurePathologicalReportDO.setHospCode(hospCode);
        return  insureUnifiedClinicalDAO.insertPathologicalReportRecord(insurePathologicalReportDO) ;
    }

    /**
     * @param map
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPagePathologicalReportRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsurePathologicalReportDO insurePathologicalReportDO = MapUtils.get(map,"insurePathologicalReportDO");
        insurePathologicalReportDO.setHospCode(hospCode);
        PageHelper.startPage(insurePathologicalReportDO.getPageNo(),insurePathologicalReportDO.getPageSize());
        List<InsurePathologicalReportDO> reportDOList = insureUnifiedClinicalDAO.queryPagePathologicalReportRecord(insurePathologicalReportDO);
        return PageDTO.of(reportDOList);
    }

    /**
     * @param map
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertBacterialReportRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsureBactreailReportDO insureBactreailReportDO = MapUtils.get(map,"insureBactreailReportDO");
        insureBactreailReportDO.setId(SnowflakeUtils.getId());
        insureBactreailReportDO.setHospCode(hospCode);
        return insureUnifiedClinicalDAO.insertBacterialReportRecord(insureBactreailReportDO);
    }

    /**
     * @param map
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPageBacterialReportRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsureBactreailReportDO insureBactreailReportDO = MapUtils.get(map,"insureBactreailReportDO");
        insureBactreailReportDO.setHospCode(hospCode);
        PageHelper.startPage(insureBactreailReportDO.getPageNo(),insureBactreailReportDO.getPageSize());
        List<InsureBactreailReportDO> reportDOList = insureUnifiedClinicalDAO.queryPageBacterialReportRecord(insureBactreailReportDO);
        return PageDTO.of(reportDOList);
    }

    /**
     * @param map
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertDrugSensitivityReportRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsureDrugSensitiveReportDO insureDrugSensitiveReportDO = MapUtils.get(map,"insureDrugSensitiveReportDO");
        insureDrugSensitiveReportDO.setId(SnowflakeUtils.getId());
        insureDrugSensitiveReportDO.setHospCode(hospCode);
        return insureUnifiedClinicalDAO.insertDrugSensitivityReportRecord(insureDrugSensitiveReportDO);
    }

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertClinicalReportRecord(Map<String, Object> map) {

        return false;
    }

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 分页查询his数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean queryPageClinicalReportRecord(Map<String, Object> map) {
        return false;
    }

    /**
     * @param clinicalExaminationInfoDTO
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertClinicalExaminationReportRecord(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO) {
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        ClinicalExaminationInfoDTO clinicalExaminationInfoDTO1 = clinicalExaminationInfoDAO.queryByUuid(clinicalExaminationInfoDTO.getUuid());
        if(clinicalExaminationInfoDTO1 == null){
            if(StringUtils.isEmpty(clinicalExaminationInfoDTO.getVisitNo())){
                throw new AppException("传入的visitNo住院号/门诊号为空");
            }
            //根据住院号/门诊号查询医保登记信息
            InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
            insureIndividualVisitDTO.setVisitNo(clinicalExaminationInfoDTO.getVisitNo());
            insureIndividualVisitDTO.setHospCode(clinicalExaminationInfoDTO.getHospCode());
            InsureIndividualVisitDTO byVisitNo = insureIndividualVisitDAO.getByVisitNo(insureIndividualVisitDTO);
            if (byVisitNo == null || StringUtils.isEmpty(byVisitNo.getId())) {
                throw new AppException("未查找到医保就诊信息，请做医保登记。");
            }
            clinicalExaminationInfoDTO.setMdtrtSn(byVisitNo.getVisitId());
            clinicalExaminationInfoDTO.setMdtrtId(byVisitNo.getMedicalRegNo());
            clinicalExaminationInfoDTO.setPsnNo(byVisitNo.getAac001());
            if(clinicalExaminationInfoDTO.getUuid() == null){
                clinicalExaminationInfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            clinicalExaminationInfoDTO.setIsUplod("0");
            clinicalExaminationInfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            clinicalExaminationInfoDAO.insertClinicalExamination(clinicalExaminationInfoDTO);
        }
        else {
            clinicalExaminationInfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            if("0".equals(clinicalExaminationInfoDTO.getValiFlag()) && "1".equals(clinicalExaminationInfoDTO1.getIsUplod())){
                throw new AppException("已上传数据不可以删除");
            }
            clinicalExaminationInfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(clinicalExaminationInfoDTO)));
        }
        return true;
    }

    /**
     * @param clinicalExaminationInfoDTO
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPageClinicalExaminationReportRecord(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO) {
        // 设置分页信息
        PageHelper.startPage(clinicalExaminationInfoDTO.getPageNo(), clinicalExaminationInfoDTO.getPageSize());
        List<ClinicalExaminationInfoDTO> clinicalExaminationInfoDTOList = clinicalExaminationInfoDAO.queryPageClinicalExamination(clinicalExaminationInfoDTO);
        return PageDTO.of(clinicalExaminationInfoDTOList);
    }


    /**
     * @Method updateClinicalReportRecord
     * @Desrciption  临床检验报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updateClinicalReportRecord(Map<String,Object>map){
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = unifiedCommon.commonGetVisitInfo(map);
        Map<String,Object> paramMap;
        List<Map<String,Object>> mapArrayList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        List<Map<String,Object>> mapList = new ArrayList<>();
        List<Map<String,Object>> itemInfoList = new ArrayList<>();
        List<Map<String,Object>> sampleInfoList = new ArrayList<>();
        if(!ListUtils.isEmpty(mapList)){
            for(Map<String,Object> item : mapList){
                paramMap = new HashMap<>();
                paramMap.put("mdtrt_sn","");
                paramMap.put("mdtrt_id","");
                paramMap.put("psn_no","");
                paramMap.put("appy_org_code","");
                paramMap.put("appy_org_name","");
                paramMap.put("bilg_dr_codg","");
                paramMap.put("bilg_dr_name","");
                paramMap.put("exam_org_code","");
                paramMap.put("exam_org_name","");
                paramMap.put("appy_dept_code","");
                paramMap.put("exam_dept_code","");
                paramMap.put("exam_mtd","");
                paramMap.put("rpotc_no","");
                paramMap.put("exam_item_code","");
                paramMap.put("exam_item_name","");
                paramMap.put("inhosp_exam_item_code","");


                paramMap.put("inhosp_exam_item_name","");
                paramMap.put("rpt_date","");
                paramMap.put("rpot_doc","");
                paramMap.put("exam_charge","");
                paramMap.put("vali_flag","");
                mapArrayList.add(paramMap);
            }
        }else{
            throw new AppException("该患者的临床检验报告记录数据为空");
        }
        if(!ListUtils.isEmpty(itemInfoList)){
            for(Map<String,Object> item : itemInfoList){
                paramMap = new HashMap<>();
                paramMap.put("rpotc_no","");
                paramMap.put("appy_no","");
                paramMap.put("exam_mtd","");
                paramMap.put("ref_val","");
                paramMap.put("exam_unt","");
                paramMap.put("exam_rslt_val","");
                paramMap.put("exam_rslt_dicm","");
                paramMap.put("exam_item_detl_code","");
                paramMap.put("exam_item_detl_name","");
                paramMap.put("exam_rslt_abn","");
                mapArrayList.add(paramMap);
            }
        }

        if(!ListUtils.isEmpty(sampleInfoList)){
            for(Map<String,Object> item : sampleInfoList){
                paramMap = new HashMap<>();
                paramMap.put("rpotc_no","");
                paramMap.put("appy_no","");
                paramMap.put("sapl_date","");
                paramMap.put("spcm_no","");
                paramMap.put("spcm_name","");
                mapArrayList.add(paramMap);
            }
        }

        dataMap.put("data", mapArrayList);
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), "4504", dataMap);
        return true;
    }


    /**
     * @Method updateDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updateDrugSensitivityReportRecord(Map<String,Object>map){
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = unifiedCommon.commonGetVisitInfo(map);
        Map<String,Object> paramMap;
        List<InsureDrugSensitiveReportDO> insureDrugSensitiveReportDOList = insureUnifiedClinicalDAO.queryAllDrugSensitivityReportRecord(map);
        Map<String, Object> dataMap = new HashMap<>();
        List<Map<String,Object>> mapList = new ArrayList<>();
        if(!ListUtils.isEmpty(insureDrugSensitiveReportDOList)){
            for(InsureDrugSensitiveReportDO drugSensitiveReportDO : insureDrugSensitiveReportDOList){
                paramMap = new HashMap<>();
                paramMap.put("mdtrt_sn",drugSensitiveReportDO.getId()); // 就医流水号
                paramMap.put("mdtrt_id",drugSensitiveReportDO.getMdtrtId()); // 就诊ID
                paramMap.put("psn_no",drugSensitiveReportDO.getPsnNo()); // 人员编号
                paramMap.put("appy_no",drugSensitiveReportDO.getAppyNo()); // 申请单号
                paramMap.put("rpotc_no",drugSensitiveReportDO.getRpotcNo()); // 报告单号
                paramMap.put("germ_code",drugSensitiveReportDO.getGermCode()); // 细菌代号
                paramMap.put("germ_name",drugSensitiveReportDO.getGermName()); // 细菌名称
                paramMap.put("sstb_code",drugSensitiveReportDO.getSstbCode()); // 药敏代码
                paramMap.put("sstb_name",drugSensitiveReportDO.getSstbName()); // 药敏名称
                paramMap.put("reta_rslt_code",drugSensitiveReportDO.getRetaRsltCode()); // 抗药结果代码
                paramMap.put("reta_rslt_name",drugSensitiveReportDO.getRetaRsltName()); // 抗药结果
                paramMap.put("ref_val",drugSensitiveReportDO.getRefVal()); // 参考值
                paramMap.put("exam_mtd",drugSensitiveReportDO.getExamMtd()); // 检验方法
                paramMap.put("exam_rslt",drugSensitiveReportDO.getExamRslt()); // 检验结果
                paramMap.put("exam_org_name",drugSensitiveReportDO.getExamOrgName()); // 检验机构名称
                paramMap.put("vali_flag",drugSensitiveReportDO.getValiFlag()); // 有效标志
                mapList.add(paramMap);
            }
        }else{
            throw new AppException("该患者的药敏记录报告记录数据为空");
        }
        dataMap.put("data", mapList);
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), "4504", dataMap);
        return true;
    }

    /**
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updateBacterialReportRecord(Map<String,Object>map){
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = unifiedCommon.commonGetVisitInfo(map);
        Map<String,Object> paramMap;
        List<Map<String,Object>> mapArrayList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        List<InsureBactreailReportDO> bactreailReportDOList = insureUnifiedClinicalDAO.queryAllBacterialReportRecord(map);
        if(!ListUtils.isEmpty(bactreailReportDOList)){
            for(InsureBactreailReportDO bactreailReportDO : bactreailReportDOList){
                paramMap = new HashMap<>();
                paramMap.put("mdtrt_sn",bactreailReportDO.getId()); // 就医流水号
                paramMap.put("mdtrt_id",bactreailReportDO.getMdtrtId()); // 就诊ID
                paramMap.put("psn_no",bactreailReportDO.getPsnNo()); // 人员编号
                paramMap.put("appy_no",bactreailReportDO.getAppyNo()); // 申请单号
                paramMap.put("rpotc_no",bactreailReportDO.getRpotcNo()); // 报告单号
                paramMap.put("germ_code",bactreailReportDO.getGermCode()); // 细菌代号
                paramMap.put("germ_name",bactreailReportDO.getGermName()); // 细菌名称
                paramMap.put("coly_cntg",bactreailReportDO.getColyCntg()); // 菌落计数
                paramMap.put("clte_medm",bactreailReportDO.getClteMedm()); // 培养基
                paramMap.put("clte_time",bactreailReportDO.getClteTime()); // 培养时间
                paramMap.put("clte_cond",bactreailReportDO.getClteCond()); // 检验结果
                paramMap.put("exam_rslt",bactreailReportDO.getExamRslt()); // 检验结果
                paramMap.put("fnd_way",bactreailReportDO.getFndWay()); // 发现方式
                paramMap.put("exam_org_name",bactreailReportDO.getExamOrgName()); // 检验机构名称
                paramMap.put("vali_flag",bactreailReportDO.getValiFlag()); // 有效标志
                mapArrayList.add(paramMap);
            }
        }else{
            throw new AppException("细菌培养报告记录");
        }
        dataMap.put("data", mapArrayList);
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), "4504", dataMap);
        return true;
    }


    /**
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updatePathologicalReportRecord(Map<String,Object>map){
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = unifiedCommon.commonGetVisitInfo(map);
        Map<String,Object> paramMap;
        List<Map<String,Object>> mapArrayList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        List<InsurePathologicalReportDO> insurePathologicalReportDOList = insureUnifiedClinicalDAO.queryAllPathologicalReportRecord(map);
        if(!ListUtils.isEmpty(insurePathologicalReportDOList)){
            for(InsurePathologicalReportDO pathologicalReportDO : insurePathologicalReportDOList){
                paramMap = new HashMap<>();
                paramMap.put("mdtrt_sn",pathologicalReportDO.getId());
                paramMap.put("mdtrt_id",pathologicalReportDO.getMdtrtId());
                paramMap.put("psn_no",pathologicalReportDO.getPsnNo());
                paramMap.put("palg_no",pathologicalReportDO.getPalgDiag());
                paramMap.put("frozen_no",pathologicalReportDO.getFrozenNo());
                paramMap.put("cma_date",pathologicalReportDO.getCmaDate());
                paramMap.put("rpot_date",pathologicalReportDO.getRpotDate());
                paramMap.put("cma_matl",pathologicalReportDO.getCmaMatl());
                paramMap.put("clnc_dise",pathologicalReportDO.getClncDise());
                paramMap.put("exam_fnd",pathologicalReportDO.getExamFnd());
                paramMap.put("sabc",pathologicalReportDO.getSabc());
                paramMap.put("palg_diag",pathologicalReportDO.getPalgDiag());
                paramMap.put("rpot_doc",pathologicalReportDO.getRpotDoc());
                paramMap.put("vali_flag",pathologicalReportDO.getValiFlag());
                mapArrayList.add(paramMap);
            }
        }else{
            throw new AppException("该患者的病理检查报告记录数据为空");
        }
        dataMap.put("data", mapArrayList);
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), "4505", dataMap);
        return true;
    }



    /**
     * @Method updateNoStructReportRecord
     * @Desrciption  非结构化报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updateNoStructReportRecord(Map<String,Object> map){
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = unifiedCommon.commonGetVisitInfo(map);
        Map<String,Object> paramMap;
        List<Map<String,Object>> mapArrayList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        List<InsureNoStructReportDO> insureNoStructReportDOList = insureUnifiedClinicalDAO.queryAllNoStructReportRecord(map);
        if(!ListUtils.isEmpty(insureNoStructReportDOList)){
            for(InsureNoStructReportDO insureNoStructReportDO : insureNoStructReportDOList){
                paramMap = new HashMap<>();
                paramMap.put("mdtrt_sn",insureNoStructReportDO.getId()); // 就医流水号
                paramMap.put("mdtrt_id",insureNoStructReportDO.getMdtrtId()); // 就诊ID
                paramMap.put("psn_no",insureNoStructReportDO.getPsnNo()); // 人员编号
                paramMap.put("otp_ipt_flag",insureNoStructReportDO.getOtpIptFlag()); // 门诊/住院标志
                paramMap.put("exam_test_flag",insureNoStructReportDO.getExamTestFlag()); // 检查/检验标志
                paramMap.put("appy_no",insureNoStructReportDO.getAppyNo()); // 申请号
                paramMap.put("file_name",insureNoStructReportDO.getFileName()); // 文件名
                paramMap.put("file_formate",insureNoStructReportDO.getFileFormate()); // 文件格式类型
                paramMap.put("exam_test_rpot",insureNoStructReportDO.getExamTestRpot()); // 检查/检验报告
                paramMap.put("vali_flag",insureNoStructReportDO.getValiFlag()); // 有效标志
                mapArrayList.add(paramMap);
            }
        }else{
            throw new AppException("该患者的非结构化报告记录数据为空");
        }
        dataMap.put("data", mapArrayList);
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), "4506", dataMap);

        return true;
    }

    /**
     * @Method checkInsureConfig
     * @Desrciption 检查医保机构是否已选择
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:58
     * @Return
     **/
    private InsureConfigurationDTO checkInsureConfig(Map map) {
        if (MapUtils.get(map, "insureRegCode") == null) {
            throw new AppException("患者的医保机构为空");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(MapUtils.get(map, "hospCode"));
        insureConfigurationDTO.setOrgCode(MapUtils.get(map, "medicineOrgCode"));
        insureConfigurationDTO.setRegCode(MapUtils.get(map, "insureRegCode"));
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("医保机构配置信息为空");
        }
        return insureConfigurationDTO;
    }
}
