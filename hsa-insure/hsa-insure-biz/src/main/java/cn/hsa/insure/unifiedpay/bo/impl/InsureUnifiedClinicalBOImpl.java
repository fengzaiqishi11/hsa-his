package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.UnifiedCommon;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedClinicalBO;
import cn.hsa.module.insure.module.dao.InsureUnifiedClinicalDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureBactreailReportDO;
import cn.hsa.module.insure.module.entity.InsureDrugSensitiveReportDO;
import cn.hsa.module.insure.module.entity.InsureNoStructReportDO;
import cn.hsa.module.insure.module.entity.InsurePathologicalReportDO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private InsureUnifiedClinicalDAO insureUnifiedClinicalDAO;

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录 -- 上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/

    public boolean updateClinicalExaminationReportRecord(Map<String,Object>map){
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
                paramMap.put("appy_no","");
                paramMap.put("appy_doc_name","");
                paramMap.put("rpotc_no","");
                paramMap.put("rpotc_type_code","");
                paramMap.put("exam_rpotc_name","");
                paramMap.put("exam_date","");
                paramMap.put("rpt_date","");
                paramMap.put("cma_date","");
                paramMap.put("sapl_date","");
                paramMap.put("spcm_no","");
                paramMap.put("spcm_name","");
                paramMap.put("exam_type_code","");
                paramMap.put("exam_item_code","");


                paramMap.put("exam_type_name","");
                paramMap.put("exam_item_name","");
                paramMap.put("inhosp_exam_item_code","");
                paramMap.put("inhosp_exam_item_name","");
                paramMap.put("exam_part","");

                paramMap.put("exam_rslt_poit_flag","");
                paramMap.put("exam_rslt_abn","");
                paramMap.put("exam_ccls","");
                paramMap.put("appy_org_name","");
                paramMap.put("appy_dept_code","");

                paramMap.put("exam_dept_code","");
                paramMap.put("ipt_dept_code","");
                paramMap.put("ipt_dept_name","");
                paramMap.put("bilg_dr_codg","");
                paramMap.put("bilg_dr_name","");
                paramMap.put("exe_org_name","");
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
                paramMap.put("exam_item_code","");
                paramMap.put("exam_item_name","");
                paramMap.put("inhosp_exam_item_code","");
                paramMap.put("inhosp_exam_item_name","");
                paramMap.put("exam_charge","");

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

        if(!ListUtils.isEmpty(sampleInfoList)){
            for(Map<String,Object> item : sampleInfoList){
                paramMap = new HashMap<>();
                paramMap.put("rpotc_no","");
                paramMap.put("study_uid","");
                paramMap.put("patient_id","");
                paramMap.put("patient_name","");
                paramMap.put("acession_no","");

                paramMap.put("study_time","");
                paramMap.put("modality","");
                paramMap.put("store_path","");
                paramMap.put("series_count","");
                paramMap.put("image_count","");
                mapArrayList.add(paramMap);
            }
        }

        dataMap.put("data", mapArrayList);
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), "4504", dataMap);
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
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertClinicalExaminationReportRecord(Map<String, Object> map) {
        return false;
    }

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPageClinicalExaminationReportRecord(Map<String, Object> map) {

        return null;
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
}
