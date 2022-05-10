package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.UnifiedCommon;
import cn.hsa.module.insure.clinica.dao.*;
import cn.hsa.module.insure.clinica.dto.*;
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
    private InsureClinicalCheckoutDAO insureClinicalCheckoutDAO;

    @Resource
    private InsureBacterialReportDAO insureBacterialReportDAO;

    @Resource
    private InsureDrugsensitiveReportDAO insureDrugsensitiveReportDAO;

    @Resource
    private InsurePathologicalReportDAO insurePathologicalReportDAO;

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
            throw new AppException("未查询到临床检查报告记录");
        }
        if("1".equals(byUuid.getIsUplod())){
            throw new AppException("临床检查报告记录已上传");
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
        //修改上传状态
        clinicalExaminationInfoDTO.setIsUplod("1");
        clinicalExaminationInfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
        clinicalExaminationInfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(clinicalExaminationInfoDTO)));
        return true;
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
            clinicalExaminationInfoDTO.setValiFlag("1");
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
     * @param
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertClinicalReportRecord(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO) {
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureClinicalCheckoutDTO insureClinicalCheckoutDTO1 = insureClinicalCheckoutDAO.queryByUuid(insureClinicalCheckoutDTO.getUuid());
        if(insureClinicalCheckoutDTO1 == null){
            if(StringUtils.isEmpty(insureClinicalCheckoutDTO.getVisitNo())){
                throw new AppException("传入的visitNo住院号/门诊号为空");
            }
            //根据住院号/门诊号查询医保登记信息
            InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
            insureIndividualVisitDTO.setVisitNo(insureClinicalCheckoutDTO.getVisitNo());
            insureIndividualVisitDTO.setHospCode(insureClinicalCheckoutDTO.getHospCode());
            InsureIndividualVisitDTO byVisitNo = insureIndividualVisitDAO.getByVisitNo(insureIndividualVisitDTO);
            if (byVisitNo == null || StringUtils.isEmpty(byVisitNo.getId())) {
                throw new AppException("未查找到医保就诊信息，请做医保登记。");
            }
            insureClinicalCheckoutDTO.setMdtrtSn(byVisitNo.getVisitId());
            insureClinicalCheckoutDTO.setMdtrtId(byVisitNo.getMedicalRegNo());
            insureClinicalCheckoutDTO.setPsnNo(byVisitNo.getAac001());
            if(insureClinicalCheckoutDTO.getUuid() == null){
                insureClinicalCheckoutDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureClinicalCheckoutDTO.setIsUplod("0");
            insureClinicalCheckoutDTO.setValiFlag("1");
            insureClinicalCheckoutDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureClinicalCheckoutDAO.insertInsureClinicalCheckout(insureClinicalCheckoutDTO);
        }
        else {
            insureClinicalCheckoutDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            if("0".equals(insureClinicalCheckoutDTO.getValiFlag()) && "1".equals(insureClinicalCheckoutDTO1.getIsUplod())){
                throw new AppException("已上传数据不可以删除");
            }
            insureClinicalCheckoutDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureClinicalCheckoutDTO)));
        }
        return true;
    }

    /**
     * @param
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 分页查询his数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPageClinicalReportRecord(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO) {
        List<CommentDTO>  commentDTOList=insureClinicalCheckoutDAO.queryComment();
        Map map = new HashMap();
        for(CommentDTO commentDTO1:commentDTOList){
            map.put(commentDTO1.getColumnName(),commentDTO1.getColumnComment());
        }
        map =toCamelCaseMap(map);
        JSONObject json = new JSONObject (map);
        System.out.print(json);
        // 设置分页信息
        PageHelper.startPage(insureClinicalCheckoutDTO.getPageNo(), insureClinicalCheckoutDTO.getPageSize());
        List<InsureClinicalCheckoutDTO> insureClinicalCheckoutDTOList = insureClinicalCheckoutDAO.queryPageInsureClinicalCheckout(insureClinicalCheckoutDTO);
        return PageDTO.of(insureClinicalCheckoutDTOList);
    }


    /**
     * @Method updateClinicalReportRecord
     * @Desrciption  临床检验报告记录 --上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updateClinicalReportRecord(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO){
        if(insureClinicalCheckoutDTO.getUuid() == null){
            throw new AppException("传入的uuid主键为空为空");
        }
        InsureClinicalCheckoutDTO byUuid = insureClinicalCheckoutDAO.queryByUuid(insureClinicalCheckoutDTO.getUuid());
        if(byUuid == null){
            throw new AppException("未查询到临床检验报告记录");
        }
        if("1".equals(byUuid.getIsUplod())){
            throw new AppException("临床检验报告记录已上传");
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
        paramMap.put("insureClinicalCheckoutDTO",byUuid);
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", visitId);
        paramMap.put("hospCode",hospCode);
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", insureIndividualVisitDTO.getIsHospital());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.CLINICAL_CHECKOUT_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
        interfaceParamDTO.setVisitId(visitId);
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.CLINICAL_CHECKOUT_UPLOAD, interfaceParamDTO);
        //修改上传状态
        insureClinicalCheckoutDTO.setIsUplod("1");
        insureClinicalCheckoutDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
        insureClinicalCheckoutDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureClinicalCheckoutDTO)));
        return true;
    }

    /**
     * @param
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertBacterialReportRecord(InsureBacterialReportDTO insureBacterialReportDTO) {
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureBacterialReportDTO insureBacterialReportDTO1 = insureBacterialReportDAO.queryByUuid(insureBacterialReportDTO.getUuid());
        if(insureBacterialReportDTO1 == null){
            if(StringUtils.isEmpty(insureBacterialReportDTO.getVisitNo())){
                throw new AppException("传入的visitNo住院号/门诊号为空");
            }
            //根据住院号/门诊号查询医保登记信息
            InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
            insureIndividualVisitDTO.setVisitNo(insureBacterialReportDTO.getVisitNo());
            insureIndividualVisitDTO.setHospCode(insureBacterialReportDTO.getHospCode());
            InsureIndividualVisitDTO byVisitNo = insureIndividualVisitDAO.getByVisitNo(insureIndividualVisitDTO);
            if (byVisitNo == null || StringUtils.isEmpty(byVisitNo.getId())) {
                throw new AppException("未查找到医保就诊信息，请做医保登记。");
            }
            insureBacterialReportDTO.setMdtrtSn(byVisitNo.getVisitId());
            insureBacterialReportDTO.setMdtrtId(byVisitNo.getMedicalRegNo());
            insureBacterialReportDTO.setPsnNo(byVisitNo.getAac001());
            if(insureBacterialReportDTO.getUuid() == null){
                insureBacterialReportDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureBacterialReportDTO.setIsUplod("0");
            insureBacterialReportDTO.setValiFlag("1");
            insureBacterialReportDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureBacterialReportDAO.insertInsureBacterialReport(insureBacterialReportDTO);
        }
        else {
            insureBacterialReportDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            if("0".equals(insureBacterialReportDTO.getValiFlag()) && "1".equals(insureBacterialReportDTO1.getIsUplod())){
                throw new AppException("已上传数据不可以删除");
            }
            insureBacterialReportDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureBacterialReportDTO)));
        }
        return true;
    }

    /**
     * @param
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPageBacterialReportRecord(InsureBacterialReportDTO insureBacterialReportDTO) {
        // 设置分页信息
        PageHelper.startPage(insureBacterialReportDTO.getPageNo(), insureBacterialReportDTO.getPageSize());
        List<InsureBacterialReportDTO> insureBacterialReportDTOList = insureBacterialReportDAO.queryPageInsureBacterialReport(insureBacterialReportDTO);
        return PageDTO.of(insureBacterialReportDTOList);
    }


    /**
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录 --上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updateBacterialReportRecord(InsureBacterialReportDTO insureBacterialReportDTO){
        if(insureBacterialReportDTO.getUuid() == null){
            throw new AppException("传入的uuid主键为空为空");
        }
        InsureBacterialReportDTO byUuid = insureBacterialReportDAO.queryByUuid(insureBacterialReportDTO.getUuid());
        if(byUuid == null){
            throw new AppException("未查询到细菌培养报告记录");
        }
        if("1".equals(byUuid.getIsUplod())){
            throw new AppException("细菌培养报告记录已上传");
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
        paramMap.put("insureBacterialReportDTO",byUuid);
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", visitId);
        paramMap.put("hospCode",hospCode);
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", insureIndividualVisitDTO.getIsHospital());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.BACTERIAL_REPORT_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
        interfaceParamDTO.setVisitId(visitId);
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.BACTERIAL_REPORT_UPLOAD, interfaceParamDTO);
        //修改上传状态
        insureBacterialReportDTO.setIsUplod("1");
        insureBacterialReportDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
        insureBacterialReportDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureBacterialReportDTO)));
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
     * @param
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---保存到his库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertPathologicalReportRecord(InsurePathologicalReportDTO insurePathologicalReportDTO) {
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsurePathologicalReportDTO insurePathologicalReportDTO1 = insurePathologicalReportDAO.queryByUuid(insurePathologicalReportDTO.getUuid());
        if(insurePathologicalReportDTO1 == null){
            if(StringUtils.isEmpty(insurePathologicalReportDTO.getVisitNo())){
                throw new AppException("传入的visitNo住院号/门诊号为空");
            }
            //根据住院号/门诊号查询医保登记信息
            InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
            insureIndividualVisitDTO.setVisitNo(insurePathologicalReportDTO.getVisitNo());
            insureIndividualVisitDTO.setHospCode(insurePathologicalReportDTO.getHospCode());
            InsureIndividualVisitDTO byVisitNo = insureIndividualVisitDAO.getByVisitNo(insureIndividualVisitDTO);
            if (byVisitNo == null || StringUtils.isEmpty(byVisitNo.getId())) {
                throw new AppException("未查找到医保就诊信息，请做医保登记。");
            }
            insurePathologicalReportDTO.setMdtrtSn(byVisitNo.getVisitId());
            insurePathologicalReportDTO.setMdtrtId(byVisitNo.getMedicalRegNo());
            insurePathologicalReportDTO.setPsnNo(byVisitNo.getAac001());
            if(insurePathologicalReportDTO.getUuid() == null){
                insurePathologicalReportDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insurePathologicalReportDTO.setIsUplod("0");
            insurePathologicalReportDTO.setValiFlag("1");
            insurePathologicalReportDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insurePathologicalReportDAO.insertInsurePathologicalReport(insurePathologicalReportDTO);
        }
        else {
            insurePathologicalReportDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            if("0".equals(insurePathologicalReportDTO.getValiFlag()) && "1".equals(insurePathologicalReportDTO1.getIsUplod())){
                throw new AppException("已上传数据不可以删除");
            }
            insurePathologicalReportDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insurePathologicalReportDTO)));
        }
        return true;
    }

    /**
     * @param
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryPagePathologicalReportRecord(InsurePathologicalReportDTO insurePathologicalReportDTO) {
        // 设置分页信息
        PageHelper.startPage(insurePathologicalReportDTO.getPageNo(), insurePathologicalReportDTO.getPageSize());
        List<InsurePathologicalReportDTO> insurePathologicalReportDTOList = insurePathologicalReportDAO.queryPagePathologicalReport(insurePathologicalReportDTO);
        return PageDTO.of(insurePathologicalReportDTOList);
    }

    /**
     * @param
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public boolean insertDrugSensitivityReportRecord(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO) {
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO1 = insureDrugsensitiveReportDAO.queryByUuid(insureDrugsensitiveReportDTO.getUuid());
        if(insureDrugsensitiveReportDTO1 == null){
            if(StringUtils.isEmpty(insureDrugsensitiveReportDTO.getVisitNo())){
                throw new AppException("传入的visitNo住院号/门诊号为空");
            }
            //根据住院号/门诊号查询医保登记信息
            InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
            insureIndividualVisitDTO.setVisitNo(insureDrugsensitiveReportDTO.getVisitNo());
            insureIndividualVisitDTO.setHospCode(insureDrugsensitiveReportDTO.getHospCode());
            InsureIndividualVisitDTO byVisitNo = insureIndividualVisitDAO.getByVisitNo(insureIndividualVisitDTO);
            if (byVisitNo == null || StringUtils.isEmpty(byVisitNo.getId())) {
                throw new AppException("未查找到医保就诊信息，请做医保登记。");
            }
            insureDrugsensitiveReportDTO.setMdtrtSn(byVisitNo.getVisitId());
            insureDrugsensitiveReportDTO.setMdtrtId(byVisitNo.getMedicalRegNo());
            insureDrugsensitiveReportDTO.setPsnNo(byVisitNo.getAac001());
            if(insureDrugsensitiveReportDTO.getUuid() == null){
                insureDrugsensitiveReportDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureDrugsensitiveReportDTO.setIsUplod("0");
            insureDrugsensitiveReportDTO.setValiFlag("1");
            insureDrugsensitiveReportDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureDrugsensitiveReportDAO.insertInsureDrugsensitiveReport(insureDrugsensitiveReportDTO);
        }
        else {
            insureDrugsensitiveReportDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            if("0".equals(insureDrugsensitiveReportDTO.getValiFlag()) && "1".equals(insureDrugsensitiveReportDTO1.getIsUplod())){
                throw new AppException("已上传数据不可以删除");
            }
            insureDrugsensitiveReportDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureDrugsensitiveReportDTO)));
        }
        return true;
    }

    /**
     * @param
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public PageDTO queryDrugSensitivityReportRecord(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO) {
        // 设置分页信息
        PageHelper.startPage(insureDrugsensitiveReportDTO.getPageNo(), insureDrugsensitiveReportDTO.getPageSize());
        List<InsureDrugsensitiveReportDTO> insureDrugsensitiveReportDTOList = insureDrugsensitiveReportDAO.queryPageInsureDrugsensitiveReport(insureDrugsensitiveReportDTO);
        return PageDTO.of(insureDrugsensitiveReportDTOList);
    }


    /**
     * @Method updateDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录 -- 上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updateDrugSensitivityReportRecord(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO){
        if(insureDrugsensitiveReportDTO.getUuid() == null){
            throw new AppException("传入的uuid主键为空为空");
        }
        InsureDrugsensitiveReportDTO byUuid = insureDrugsensitiveReportDAO.queryByUuid(insureDrugsensitiveReportDTO.getUuid());
        if(byUuid == null){
            throw new AppException("未查询到药敏记录报告记录");
        }
        if("1".equals(byUuid.getIsUplod())){
            throw new AppException("药敏记录报告记录已上传");
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
        paramMap.put("insureDrugsensitiveReportDTO",byUuid);
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", visitId);
        paramMap.put("hospCode",hospCode);
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", insureIndividualVisitDTO.getIsHospital());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.DRUGSENSITIVE_REPORT_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
        interfaceParamDTO.setVisitId(visitId);
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.DRUGSENSITIVE_REPORT_UPLOAD, interfaceParamDTO);
        //修改上传状态
        insureDrugsensitiveReportDTO.setIsUplod("1");
        insureDrugsensitiveReportDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
        insureDrugsensitiveReportDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureDrugsensitiveReportDTO)));
        return true;
    }


    /**
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录 -- 上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    public boolean updatePathologicalReportRecord(InsurePathologicalReportDTO insurePathologicalReportDTO){
        if(insurePathologicalReportDTO.getUuid() == null){
            throw new AppException("传入的uuid主键为空为空");
        }
        InsurePathologicalReportDTO byUuid = insurePathologicalReportDAO.queryByUuid(insurePathologicalReportDTO.getUuid());
        if(byUuid == null){
            throw new AppException("未查询到药敏记录报告记录");
        }
        if("1".equals(byUuid.getIsUplod())){
            throw new AppException("药敏记录报告记录已上传");
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
        paramMap.put("insurePathologicalReportDTO",byUuid);
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", visitId);
        paramMap.put("hospCode",hospCode);
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", insureIndividualVisitDTO.getIsHospital());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.PATHOLOGICAL_REPORT_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
        interfaceParamDTO.setVisitId(visitId);
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.PATHOLOGICAL_REPORT_UPLOAD, interfaceParamDTO);
        //修改上传状态
        insurePathologicalReportDTO.setIsUplod("1");
        insurePathologicalReportDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
        insurePathologicalReportDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insurePathologicalReportDTO)));
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
