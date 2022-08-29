package cn.hsa.insure.unifiedpay.emr.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.enums.HsaSrvEnum;
import cn.hsa.exception.BizRtException;
import cn.hsa.exception.InsureExecCodesEnum;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.unifiedpay.bo.impl.InsureItfBOImpl;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.emr.emrpatient.service.EmrPatientService;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.emr.bo.InsureUnifiedEmrBO;
import cn.hsa.module.insure.emr.dao.InsureEmrAdminfoDAO;
import cn.hsa.module.insure.emr.entity.InsureEmrDscginfoDO;
import cn.hsa.module.insure.emr.entity.InsureEmrOprninfoDO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.util.*;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.*;
import cn.hsa.module.insure.emr.dto.*;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * @ClassName InsureUnifiedEmrBOImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/3/25 13:38
 * @Version 1.0
 **/
@Slf4j
@Component
public class InsureUnifiedEmrBOImpl extends HsafBO implements InsureUnifiedEmrBO {

    @Autowired
    private InsureEmrAdminfoDAO insureEmrAdminfoDAO;

    @Autowired
    private InsureEmrCoursrinfoDAO insureEmrCoursrinfoDAO;

    @Autowired
    private InsureEmrDieinfoDAO insureEmrDieinfoDAO;

    @Autowired
    private InsureEmrDiseinfoDAO insureEmrDiseinfoDAO;

    @Autowired
    private InsureEmrDscginfoDAO insureEmrDscginfoDAO;

    @Autowired
    private InsureEmrOprninfoDAO insureEmrOprninfoDAO;

    @Autowired
    private InsureEmrRescinfoDAO insureEmrRescinfoDAO;

    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;
    @Resource
    private EmrPatientService emrPatientService_consumer;

    @Resource
    private InsureDictService insureDictService_consumer;
    @Override
    public PageDTO queryInsureUnifiedEmrInfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        // 设置分页信息
        PageHelper.startPage(insureEmrUnifiedDTO.getPageNo(), insureEmrUnifiedDTO.getPageSize());
        List<InsureEmrUnifiedDTO> resultList = insureEmrAdminfoDAO.queryInsureUnifiedEmrInfo(insureEmrUnifiedDTO);
        return PageDTO.of(resultList);
    }

    @Override
    public InsureEmrDetailDTO queryInsureUnifiedEmrDetail(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        InsureEmrDetailDTO insureEmrDetailDTO = new InsureEmrDetailDTO();

        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();

        //入院记录
        InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrAdminfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        if(insureEmrAdminfoDTO == null){
            try{
                updateInsureUnifiedEmrSync(insureEmrUnifiedDTO);
            }catch (Exception e){
                log.error("同步失败", e);
            }
        }
        insureEmrDetailDTO.setInsureEmrAdminfoDTO(insureEmrAdminfoDTO);
        //病程记录
        List<InsureEmrCoursrinfoDTO> insureEmrCoursrinfoDTOList = insureEmrCoursrinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrCoursrinfoDTOList(insureEmrCoursrinfoDTOList);
        //死亡记录
        List<InsureEmrDieinfoDTO> insureEmrDieinfoDTOList = insureEmrDieinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrDieinfoDTOList(insureEmrDieinfoDTOList);
        //诊断信息
        List<InsureEmrDiseinfoDTO> insureEmrDiseinfoDTOList = insureEmrDiseinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrDiseinfoDTOList(insureEmrDiseinfoDTOList);
        //出院记录
        List<InsureEmrDscginfoDTO> insureEmrDscginfoDTOList = insureEmrDscginfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrDscginfoDTOList(insureEmrDscginfoDTOList);
        //手术记录
        List<InsureEmrOprninfoDTO> insureEmrOprninfoDTOList = insureEmrOprninfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrOprninfoDTOList(insureEmrOprninfoDTOList);
        //病情抢救记录
        List<InsureEmrRescinfoDTO> insureEmrRescinfoDTOList = insureEmrRescinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrRescinfoDTOList(insureEmrRescinfoDTOList);

        return insureEmrDetailDTO;
    }

    @Override
    public InsureEmrAdminfoDTO updateInsureUnifiedEmrAdminfo(InsureEmrAdminfoDTO insureEmrAdminfoDTO){
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureEmrAdminfoDTO insureEmrAdminfo = insureEmrAdminfoDAO.queryByUuid(insureEmrAdminfoDTO.getUuid());
        if(insureEmrAdminfo == null){
            if(insureEmrAdminfoDTO.getUuid() == null){
                insureEmrAdminfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureEmrAdminfoDTO.setSource("2");
            insureEmrAdminfoDTO.setStatu("1");
            insureEmrAdminfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrAdminfoDAO.insert(insureEmrAdminfoDTO);
        }else {
            insureEmrAdminfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrAdminfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrAdminfoDTO)));
        }
        return  insureEmrAdminfoDTO;
    }

    @Override
    public InsureEmrDiseinfoDTO updateInsureUnifiedEmrDiseinfo(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO){
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureEmrDiseinfoDTO insureEmrDiseinfoDTO1 = insureEmrDiseinfoDAO.queryByUuid(insureEmrDiseinfoDTO.getUuid());
        if(insureEmrDiseinfoDTO1 == null){
            if(StringUtils.isEmpty(insureEmrDiseinfoDTO.getMdtrtSn())){
                throw new AppException("传入的visitId就医流水号为空");
            }
            if(StringUtils.isEmpty(insureEmrDiseinfoDTO.getMdtrtId())){
                throw new AppException("传入的mdtrtId医保就诊id为空");
            }
            //诊断序列号处理
            int index = 0;
            List<InsureEmrDiseinfoDTO> insureEmrDiseinfoDTOList = insureEmrDiseinfoDAO.queryByMdtrtSn(insureEmrDiseinfoDTO.getMdtrtSn(),insureEmrDiseinfoDTO.getMdtrtId());
            if(insureEmrDiseinfoDTOList != null){
                index = insureEmrDiseinfoDTOList.size();
            }
            insureEmrDiseinfoDTO.setDiagSeq(index);
            if(insureEmrDiseinfoDTO.getUuid() == null){
                insureEmrDiseinfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureEmrDiseinfoDTO.setSource("2");
            insureEmrDiseinfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrDiseinfoDAO.insert(insureEmrDiseinfoDTO);
        }else {
            insureEmrDiseinfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrDiseinfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrDiseinfoDTO)));
        }
        return  insureEmrDiseinfoDTO;
    }

    @Override
    public InsureEmrCoursrinfoDTO updateInsureUnifiedEmrCoursrinfo(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO){
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO1 = insureEmrCoursrinfoDAO.queryByUuid(insureEmrCoursrinfoDTO.getUuid());
        InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrAdminfoDAO.queryByMdtrtSn(insureEmrCoursrinfoDTO.getMdtrtSn(),insureEmrCoursrinfoDTO.getMdtrtId());
        if(insureEmrAdminfoDTO != null){
            insureEmrCoursrinfoDTO.setDeptCode(insureEmrAdminfoDTO.getDeptCode());
            insureEmrCoursrinfoDTO.setDeptName(insureEmrAdminfoDTO.getDeptName());
            insureEmrCoursrinfoDTO.setWardareaName(insureEmrAdminfoDTO.getWardareaName());
            insureEmrCoursrinfoDTO.setBedno(insureEmrAdminfoDTO.getBedno());
        }
        if(insureEmrCoursrinfoDTO1 == null){
            if(insureEmrCoursrinfoDTO.getUuid() == null){
                insureEmrCoursrinfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureEmrCoursrinfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrCoursrinfoDTO.setSource("2");
            insureEmrCoursrinfoDAO.insert(insureEmrCoursrinfoDTO);
        }else {
            insureEmrCoursrinfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrCoursrinfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrCoursrinfoDTO)));
        }
        return  insureEmrCoursrinfoDTO;
    }

    @Override
    public InsureEmrOprninfoDTO updateInsureUnifiedEmrOprninfo(InsureEmrOprninfoDTO insureEmrOprninfoDTO){
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureEmrOprninfoDTO insureEmrOprninfoDTO1 = insureEmrOprninfoDAO.queryByUuid(insureEmrOprninfoDTO.getUuid());
        if(insureEmrOprninfoDTO1 == null){
            if(StringUtils.isEmpty(insureEmrOprninfoDTO.getMdtrtSn())){
                throw new AppException("传入的visitId就医流水号为空");
            }
            if(StringUtils.isEmpty(insureEmrOprninfoDTO.getMdtrtId())){
                throw new AppException("传入的mdtrtId医保就诊id为空");
            }
            //手术序列号处理
            String index ="0";
            List<InsureEmrOprninfoDTO> insureEmrOprninfoDTOList = insureEmrOprninfoDAO.queryByMdtrtSn(insureEmrOprninfoDTO.getMdtrtSn(),insureEmrOprninfoDTO.getMdtrtId());
            if(insureEmrOprninfoDTOList != null){
                index = String.valueOf(insureEmrOprninfoDTOList.size()) ;
            }
            insureEmrOprninfoDTO.setOprnSeq(index);
            if(insureEmrOprninfoDTO.getUuid() == null){
                insureEmrOprninfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureEmrOprninfoDTO.setSource("2");
            insureEmrOprninfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            //字典转义
            insureEmrOprninfoDTO.setOprnTypeName(getSysCodeName(insureEmrOprninfoDTO.getHospCode(),"SSFL",insureEmrOprninfoDTO.getOprnTypeCode()));
            insureEmrOprninfoDTO.setSincHealLvCode(getSysCodeName(insureEmrOprninfoDTO.getHospCode(),"YHDJ",insureEmrOprninfoDTO.getSincHealLv()));
            insureEmrOprninfoDTO.setOprnLvName(getSysCodeName(insureEmrOprninfoDTO.getHospCode(),"SSJB",insureEmrOprninfoDTO.getOprnLvCode()));
            insureEmrOprninfoDTO.setAnstMtdName(getSysCodeName(insureEmrOprninfoDTO.getHospCode(),"MZFS",insureEmrOprninfoDTO.getAnstMtdCode()));
            insureEmrOprninfoDTO.setAnstAsaLvName(getSysCodeName(insureEmrOprninfoDTO.getHospCode(),"MGMZYSXH(ASA)FJBZDM",insureEmrOprninfoDTO.getAnstAsaLvCode()));
            insureEmrOprninfoDAO.insert(insureEmrOprninfoDTO);
        }else {
            insureEmrOprninfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrOprninfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrOprninfoDTO)));
        }
        return  insureEmrOprninfoDTO;
    }

    @Override
    public InsureEmrRescinfoDTO updateInsureUnifiedEmrRescinfo(InsureEmrRescinfoDTO insureEmrRescinfoDTO){
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureEmrRescinfoDTO insureEmrRescinfoDTO1 = insureEmrRescinfoDAO.queryByUuid(insureEmrRescinfoDTO.getUuid());
        InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrAdminfoDAO.queryByMdtrtSn(insureEmrRescinfoDTO.getMdtrtSn(),insureEmrRescinfoDTO.getMdtrtId());
        if(insureEmrAdminfoDTO != null){
            insureEmrRescinfoDTO.setDept(insureEmrAdminfoDTO.getDeptCode());
            insureEmrRescinfoDTO.setDeptName(insureEmrAdminfoDTO.getDeptName());
            insureEmrRescinfoDTO.setWardareaName(insureEmrAdminfoDTO.getWardareaName());
            insureEmrRescinfoDTO.setBedno(insureEmrAdminfoDTO.getBedno());
        }
        if(insureEmrRescinfoDTO1 == null){
            if(insureEmrRescinfoDTO.getUuid() == null){
                insureEmrRescinfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureEmrRescinfoDTO.setSource("2");
            insureEmrRescinfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            //字典转义
            insureEmrRescinfoDTO.setDiseCcls(getSysCodeName(insureEmrRescinfoDTO.getHospCode(),"JC/JYJGDM",insureEmrRescinfoDTO.getDiseCclsCode()));
            insureEmrRescinfoDAO.insert(insureEmrRescinfoDTO);
        }else {
            insureEmrRescinfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrRescinfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrRescinfoDTO)));
        }
        return  insureEmrRescinfoDTO;
    }

    @Override
    public InsureEmrDieinfoDTO updateInsureUnifiedEmrDieinfo(InsureEmrDieinfoDTO insureEmrDieinfoDTO){
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureEmrDieinfoDTO insureEmrDieinfoDTO1 = insureEmrDieinfoDAO.queryByUuid(insureEmrDieinfoDTO.getUuid());
        InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrAdminfoDAO.queryByMdtrtSn(insureEmrDieinfoDTO.getMdtrtSn(),insureEmrDieinfoDTO.getMdtrtId());
        if(insureEmrAdminfoDTO != null){
            insureEmrDieinfoDTO.setDept(insureEmrAdminfoDTO.getDeptCode());
            insureEmrDieinfoDTO.setDeptName(insureEmrAdminfoDTO.getDeptName());
            insureEmrDieinfoDTO.setWardareaName(insureEmrAdminfoDTO.getWardareaName());
            insureEmrDieinfoDTO.setBedno(insureEmrAdminfoDTO.getBedno());
        }
        if(insureEmrDieinfoDTO1 == null){
            if(insureEmrDieinfoDTO.getUuid() == null){
                insureEmrDieinfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureEmrDieinfoDTO.setSource("2");
            insureEmrDieinfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrDieinfoDAO.insert(insureEmrDieinfoDTO);
        }else {
            insureEmrDieinfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrDieinfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrDieinfoDTO)));
        }
        return  insureEmrDieinfoDTO;
    }

    @Override
    public InsureEmrDscginfoDTO updateInsureUnifiedEmrDscginfo(InsureEmrDscginfoDTO insureEmrDscginfoDTO){
        //根据uuid 判断记录是否存在，不存在则新增，存在则修改
        InsureEmrDscginfoDTO insureEmrDscginfoDTO1 = insureEmrDscginfoDAO.queryByUuid(insureEmrDscginfoDTO.getUuid());
        if(insureEmrDscginfoDTO1 == null){
            if(insureEmrDscginfoDTO.getUuid() == null){
                insureEmrDscginfoDTO.setUuid(SnowflakeUtils.getLongId());
            }
            insureEmrDscginfoDTO.setSource("2");
            insureEmrDscginfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrDscginfoDAO.insert(insureEmrDscginfoDTO);
        }else {
            insureEmrDscginfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
            insureEmrDscginfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrDscginfoDTO)));
        }
        return  insureEmrDscginfoDTO;
    }

    @Override
    public List<InsureEmrUnifiedDTO> export(InsureEmrUnifiedDTO insureEmrUnifiedDTO){
        // 设置分页信息
        PageHelper.startPage(insureEmrUnifiedDTO.getPageNo(), insureEmrUnifiedDTO.getPageSize());
        List<InsureEmrUnifiedDTO> resultList = insureEmrAdminfoDAO.queryInsureUnifiedEmrInfo(insureEmrUnifiedDTO);
        return resultList;
    }

    @Override
    public List<InsureEmrDscginfoDTO> queryInsureUnifiedEmrDscginfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();
        //出院记录
        List<InsureEmrDscginfoDTO> insureEmrDscginfoDTOList = insureEmrDscginfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        return insureEmrDscginfoDTOList;
    }

    @Override
    public List<InsureEmrDieinfoDTO> queryInsureUnifiedEmrDieinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();
        //死亡记录
        List<InsureEmrDieinfoDTO> insureEmrDieinfoDTOList = insureEmrDieinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        return insureEmrDieinfoDTOList;
    }

    @Override
    public List<InsureEmrRescinfoDTO> queryInsureUnifiedEmrRescinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();

        //病情抢救记录
        List<InsureEmrRescinfoDTO> insureEmrRescinfoDTOList = insureEmrRescinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        return insureEmrRescinfoDTOList;
    }

    @Override
    public List<InsureEmrOprninfoDTO> queryInsureUnifiedEmrOprninfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();

        //手术记录
        List<InsureEmrOprninfoDTO> insureEmrOprninfoDTOList = insureEmrOprninfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        return insureEmrOprninfoDTOList;
    }

    @Override
    public List<InsureEmrCoursrinfoDTO> queryInsureUnifiedEmrCoursrinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();

        //病程记录
        List<InsureEmrCoursrinfoDTO> insureEmrCoursrinfoDTOList = insureEmrCoursrinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        return insureEmrCoursrinfoDTOList;
    }

    @Override
    public List<InsureEmrDiseinfoDTO> queryInsureUnifiedEmrDiseinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();

        //诊断信息
        List<InsureEmrDiseinfoDTO> insureEmrDiseinfoDTOList = insureEmrDiseinfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        return insureEmrDiseinfoDTOList;
    }

    @Override
    public InsureEmrAdminfoDTO queryInsureUnifiedEmrAdminfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new AppException("传入的visitId就医流水号为空");
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new AppException("传入的mdtrtId医保就诊id为空");
        }
        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();
        //入院记录
        InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrAdminfoDAO.queryByMdtrtSn(mdtrtSn,mdtrtId);
        return insureEmrAdminfoDTO;
    }

    /**
     * 根究mdrerSn查询在院信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:29
     * @return cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO
     */
    @Override
    public InsureEmrAdminfoDTO queryAdmInfoByMdtrtSn(String mdtrtSn) {
      if(StringUtils.isEmpty(mdtrtSn)){
        throw new AppException("传入的visitId就医流水号为空");
      }
      InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrAdminfoDAO.queryInfoByMdtrtSn(mdtrtSn);
      return insureEmrAdminfoDTO;
    }

    /**
     * 根据就诊ID查询首次病程信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:07
     * @return cn.hsa.module.insure.emr.dto.InsureEmrCoursrinfoDTO
     */
    @Override
    public InsureEmrCoursrinfoDTO queryCoursrInfoByMdtrtSn(String mdtrtSn) {
      if(StringUtils.isEmpty(mdtrtSn)){
        throw new AppException("传入的visitId就医流水号为空");
      }
      InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO = insureEmrCoursrinfoDAO.queryInfoByMdtrtSn(mdtrtSn);
      return insureEmrCoursrinfoDTO;
    }

    /**
     * 查询死亡记录信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 17:26
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO
     */
    @Override
    public InsureEmrDieinfoDTO queryDieInfoByMdtrtSn(String mdtrtSn) {
      if(StringUtils.isEmpty(mdtrtSn)){
        throw new AppException("传入的visitId就医流水号为空");
      }
      InsureEmrDieinfoDTO insureEmrDieinfoDTO = insureEmrDieinfoDAO.queryInfoByMdtrtSn(mdtrtSn);
      return insureEmrDieinfoDTO;
    }

    /**
     * 查询出院小结信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:34
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO
     */
    @Override
    public InsureEmrDscginfoDTO queryDscgInfoByMdtrtSn(String mdtrtSn) {
      if(StringUtils.isEmpty(mdtrtSn)){
        throw new AppException("传入的visitId就医流水号为空");
      }
      InsureEmrDscginfoDTO insureEmrDscginfoDTO = insureEmrDscginfoDAO.queryInfoByMdtrtSn(mdtrtSn);
      return insureEmrDscginfoDTO;
    }

    /**
     * 保存电子病历手术信息
     * @param templateId
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 20:05
     * @return cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO
     */
    @Override
    public InsureEmrOprninfoDTO queryOprnInfoByTemplateId(String templateId) {
      return insureEmrOprninfoDAO.queryInfoByTemplateId(templateId);
    }

    /**
     * 根据病历编号查询抢救信息
     * @param emrTemplateId
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:50
     * @return cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO
     */
    @Override
    public InsureEmrRescinfoDTO queryRescInfoByTemplateId(String emrTemplateId) {
      return insureEmrRescinfoDAO.queryInfoByTemplateId(emrTemplateId);
    }

  /**
     * 根据MdtrtSn更新入院信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:36
     * @return int
     */
    @Override
    public int updateAdmSelectiveByMdtrtSn(Map map) {
      EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
      Map<String, Object> resultMap = MapUtils.get(map, "resultMap");
      Map ParamMap = MapUtil.toCamelCaseMap(resultMap);
      ParamMap.put("visitId",emrPatientDTO.getVisitId());
      ParamMap.put("hospCode",emrPatientDTO.getHospCode());
      ParamMap.put("emrTemplateId",emrPatientDTO.getId());
      return insureEmrAdminfoDAO.updateSelectiveByMdtrtSn(ParamMap);
    }

    /**
     * 根据mdtrtsn更新首次病程信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:09
     * @return int
     */
    @Override
    public int updateCoursrSelectiveByMdtrtSn(Map map) {
      EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
      Map<String, Object> resultMap = MapUtils.get(map, "resultMap");
      Map ParamMap = MapUtil.toCamelCaseMap(resultMap);
      ParamMap.put("visitId",emrPatientDTO.getVisitId());
      ParamMap.put("hospCode",emrPatientDTO.getHospCode());
      return insureEmrCoursrinfoDAO.updateSelectiveByMdtrtSn(ParamMap);
    }

    /**
     * 更新死亡信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:38
     * @return int
     */
    @Override
    public int updateDieSelectiveByMdtrtSn(Map map) {
      EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
      Map<String, Object> resultMap = MapUtils.get(map, "resultMap");
      Map ParamMap = MapUtil.toCamelCaseMap(resultMap);
      ParamMap.put("visitId",emrPatientDTO.getVisitId());
      ParamMap.put("hospCode",emrPatientDTO.getHospCode());
      ParamMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      return insureEmrDieinfoDAO.updateDieSelective(ParamMap);
    }

    /**
     * 更新出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:49
     * @return int
     */
    @Override
    public int updateDscgSelectiveByMdtrtSn(Map map) {
      EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
      Map<String, Object> resultMap = MapUtils.get(map, "resultMap");
      Map ParamMap = MapUtil.toCamelCaseMap(resultMap);
      ParamMap.put("visitId",emrPatientDTO.getVisitId());
      ParamMap.put("hospCode",emrPatientDTO.getHospCode());
      ParamMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      return insureEmrDscginfoDAO.updateDscgSelective(ParamMap);
    }

    /**
     * 更新手术信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 20:25
     * @return int
     */
    @Override
    public int updateOprnSelectiveByTemplateId(Map map) {
      EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
      Map<String, Object> resultMap = MapUtils.get(map, "resultMap");
      Map ParamMap = MapUtil.toCamelCaseMap(resultMap);
      ParamMap.put("visitId",emrPatientDTO.getVisitId());
      ParamMap.put("hospCode",emrPatientDTO.getHospCode());
      ParamMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      ParamMap.put("templateId",emrPatientDTO.getId());
      return insureEmrOprninfoDAO.updateOprnSelective(ParamMap);
    }

    @Override
    public int updateRescSelectiveByTemplateId(Map map) {
      EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
      Map<String, Object> resultMap = MapUtils.get(map, "resultMap");
      Map ParamMap = MapUtil.toCamelCaseMap(resultMap);
      ParamMap.put("visitId",emrPatientDTO.getVisitId());
      ParamMap.put("hospCode",emrPatientDTO.getHospCode());
      ParamMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      ParamMap.put("templateId",emrPatientDTO.getId());
      return insureEmrRescinfoDAO.updateRescSelective(ParamMap);
    }

  /**
     * 插入医保电子病历入院信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 15:05
     * @return int
     */
    @Override
    public int insertAdminfo(Map map) {
      InsureEmrAdminfoDTO insureEmrAdminfoDTO = MapUtils.get(map,"insureEmrAdminfoDTO");
      //数据完善
      insureEmrAdminfoDTO.setUuid(SnowflakeUtils.getLongId());
      insureEmrAdminfoDTO.setValiFlag(Constants.SF.S);
      insureEmrAdminfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      return insureEmrAdminfoDAO.insert(insureEmrAdminfoDTO);
    }

    /**
     * 插入医保电子病历首次病程记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:24
     * @return int
     */
    @Override
    public int insertCoursrinfo(Map map) {
      InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO = MapUtils.get(map,"insureEmrCoursrinfoDTO");
      //数据完善
      insureEmrCoursrinfoDTO.setUuid(SnowflakeUtils.getLongId());
      insureEmrCoursrinfoDTO.setValiFlag(Constants.SF.S);
      insureEmrCoursrinfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      return insureEmrCoursrinfoDAO.insert(insureEmrCoursrinfoDTO);
    }

    /**
     * 插入医保电子病历死亡记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 8:57
     * @return int
     */
    @Override
    public int insertDieinfo(Map map) {
      InsureEmrDieinfoDTO insureEmrDieinfoDTO = MapUtils.get(map,"insureEmrDieinfoDTO");
      insureEmrDieinfoDTO.setUuid(SnowflakeUtils.getLongId());
      insureEmrDieinfoDTO.setValiFlag(Constants.SF.S);
      insureEmrDieinfoDTO.setSource("1");
      insureEmrDieinfoDTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      //非空判断，数据表必填，但是未上传数据默认传值
      //科室编码
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getDeptName())){
        insureEmrDieinfoDTO.setDeptName("-");
        insureEmrDieinfoDTO.setDept("-");
      }
      //病区
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getWardareaName())){
        insureEmrDieinfoDTO.setWardareaName("-");
      }
      //病床号
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getBedno())){
        insureEmrDieinfoDTO.setBedno("-");
      }
      //入院时间
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getAdmTime())){
        insureEmrDieinfoDTO.setAdmTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      }
      //入院诊断
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getAdmDise())){
        insureEmrDieinfoDTO.setAdmDise("-");
      }
      //死亡时间
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getDieTime())){
        insureEmrDieinfoDTO.setDieTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      }
      //死亡直接原因
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getDieDrtRea())){
        insureEmrDieinfoDTO.setDieDrtRea("-");
        insureEmrDieinfoDTO.setDieDrtReaCode("-");
      }
      //死亡诊断
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getDieDiseName())){
        insureEmrDieinfoDTO.setDieDiagCode("-");
        insureEmrDieinfoDTO.setDieDiseName("-");
      }
      //家属是否同意解刨
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getAgreCorpDset())){
        insureEmrDieinfoDTO.setAgreCorpDset("0");
      }
      //住院医生姓名
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getIpdrName())){
        insureEmrDieinfoDTO.setIpdrName("-");
      }
      //主诊医生
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getChfdrName())){
        insureEmrDieinfoDTO.setChfdrName("-");
      }
      //主任医生
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getChfpdrName())){
        insureEmrDieinfoDTO.setChfpdrName("-");
        insureEmrDieinfoDTO.setChfpdrCode("-");
      }
      //签字时间
      if (ObjectUtil.isEmpty(insureEmrDieinfoDTO.getSignTime())){
        insureEmrDieinfoDTO.setSignTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      }
      return insureEmrDieinfoDAO.insert(insureEmrDieinfoDTO);
    }

    /**
     * 插入出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 11:06
     * @return int
     */
    @Override
    public int insertDscgInfo(Map map) {
      InsureEmrDscginfoDO insureEmrDscginfoDO = MapUtils.get(map,"insureEmrDieinfoDTO");
      insureEmrDscginfoDO.setUuid(SnowflakeUtils.getLongId());
      insureEmrDscginfoDO.setValiFlag(Constants.SF.S);
      insureEmrDscginfoDO.setSource("1");
      insureEmrDscginfoDO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      //数据表必填字段补充
      if (ObjectUtil.isEmpty(insureEmrDscginfoDO.getCaty())){
        insureEmrDscginfoDO.setCaty("-");
      }
      if (ObjectUtil.isEmpty(insureEmrDscginfoDO.getRecDoc())){
        insureEmrDscginfoDO.setRecDoc("-");
      }
      return insureEmrDscginfoDAO.insertSelective(insureEmrDscginfoDO);
    }

    /**
     * 插入手术信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:19
     * @return int
     */
    @Override
    public int insertOprnInfo(Map map) {
      InsureEmrOprninfoDTO DTO = MapUtils.get(map,"insureEmrOprninfoDTO");
      DTO.setUuid(SnowflakeUtils.getLongId());
      DTO.setValiFlag(Constants.SF.S);
      DTO.setSource("1");
      DTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      DTO.setOprnSeq(String.valueOf(System.currentTimeMillis()));
      //数据表必填字段补充
      //手术申请单号
      if (ObjectUtil.isEmpty(DTO.getOprnAppyId())){
        DTO.setOprnAppyId("-");
      }
      //手术时间
      if (ObjectUtil.isEmpty(DTO.getOprnTime())){
        DTO.setOprnTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      }
      //手术分类代码
      if (ObjectUtil.isEmpty(DTO.getOprnTypeCode())){
        DTO.setOprnTypeCode("-");
        DTO.setOprnTypeName("-");
      }
      //术前是否发生院内感染
      if (ObjectUtil.isEmpty(DTO.getBfpnInhospIfet())){
        DTO.setBfpnInhospIfet("0");
      }
      //手术切口愈合等级代码
      if (ObjectUtil.isEmpty(DTO.getSincHealLv())){
        DTO.setSincHealLv("-");
        DTO.setSincHealLvCode("-");
      }
      //手术操作代码
      if (ObjectUtil.isEmpty(DTO.getOprnOprtName())){
        DTO.setOprnOprtName("-");
        DTO.setOprnOprtCode("-");
      }
      //手术级别代码
      if (ObjectUtil.isEmpty(DTO.getOprnLvName())){
        DTO.setOprnLvName("-");
        DTO.setOprnLvCode("-");
      }
      //麻醉分级代码
      if (ObjectUtil.isEmpty(DTO.getAnstLvName())){
        DTO.setAnstLvName("-");
        DTO.setAnstLvCode("-");
      }
      //手术开始时间
      if (ObjectUtil.isEmpty(DTO.getOprnBegntime())){
        DTO.setOprnBegntime(DateUtils.format(new Date(), DateUtils.YMDHMS));
        DTO.setOprnEndtime(DateUtils.format(new Date(), DateUtils.YMDHMS));
      }
      //麻醉药物代码
      if (ObjectUtil.isEmpty(DTO.getAnstMednCode())){
        DTO.setAnstMednCode("-");
      }
      //是否择期手术
      if (ObjectUtil.isEmpty(DTO.getOprnSelv())){
        DTO.setOprnSelv("-");
      }
      //是否取消手术
      if (ObjectUtil.isEmpty(DTO.getCancOprn())){
        DTO.setCancOprn("-");
      }
      return insureEmrOprninfoDAO.insert(DTO);
    }

    @Override
    public int insertRescInfo(Map map) {
      InsureEmrRescinfoDTO DTO = MapUtils.get(map,"InsureEmrRescinfoDTO");
      DTO.setUuid(SnowflakeUtils.getLongId());
      DTO.setValiFlag(Constants.SF.S);
      DTO.setSource("1");
      DTO.setCreateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
      //数据表必填字段补充
      //科室
      if (ObjectUtil.isEmpty(DTO.getDept())){
        DTO.setDept("-");
        DTO.setDeptName("-");
      }
      //病区名称
      if (ObjectUtil.isEmpty(DTO.getWardareaName())){
        DTO.setWardareaName("-");
      }
      //床位号
      if (ObjectUtil.isEmpty(DTO.getBedno())){
        DTO.setBedno("-");
      }
      //疾病编码
      if (ObjectUtil.isEmpty(DTO.getDiagCode())){
        DTO.setDiagCode("-");
        DTO.setDiagName("-");
      }
      //手术操作代码
      if (ObjectUtil.isEmpty(DTO.getOprnOprtCode())){
        DTO.setOprnOprtCode("-");
        DTO.setOprnOprtName("-");
      }
      //介入物名称
      if (ObjectUtil.isEmpty(DTO.getItvtName())){
        DTO.setItvtName("-");
      }
      //操作次数
      if (ObjectUtil.isEmpty(DTO.getOprtCnt())){
        DTO.setOprtCnt(0);
      }
      //抢救开始时间
      if (ObjectUtil.isEmpty(DTO.getRescBegntime())){
        DTO.setRescBegntime(DateUtils.format(new Date(), DateUtils.Y_M_D));
        DTO.setRescEndtime(DateUtils.format(new Date(), DateUtils.Y_M_D));
      }
      //检查/检验项目名称
      if (ObjectUtil.isEmpty(DTO.getDiseItemName())){
        DTO.setDiseItemName("-");
      }
      //检查/检验定量结果
      if (ObjectUtil.isEmpty(DTO.getDiseCclsQunt())){
        DTO.setDiseCclsQunt(new BigDecimal(0));
      }
      //检查/检验结果代码
      if (ObjectUtil.isEmpty(DTO.getDiseCclsCode())){
        DTO.setDiseCclsCode("-");
      }
      //参加抢救人员名单
      if (ObjectUtil.isEmpty(DTO.getRescPsnList())){
        DTO.setRescPsnList("-");
      }
      //专业技术职务类别代码
      if (ObjectUtil.isEmpty(DTO.getProftechttlCode())){
        DTO.setProftechttlCode("-");
      }
      //医师编号
      if (ObjectUtil.isEmpty(DTO.getDocCode())){
        DTO.setDocCode("-");
        DTO.setDrName("-");
      }
      return insureEmrRescinfoDAO.insert(DTO);
    }

  @Override
    public void updateInsureUnifiedEmrSync(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(insureEmrUnifiedDTO.getHospCode());
        inptVisitDTO.setVisitId(insureEmrUnifiedDTO.getVisitId());
        inptVisitDTO.setId(insureEmrUnifiedDTO.getVisitId());
        HashMap emrMapParam = new HashMap();
        // 数据来源
        emrMapParam.put("hospCode",insureEmrUnifiedDTO.getHospCode());
        emrMapParam.put("inptVisitDTO", inptVisitDTO);
        Map<String, Object> emrMap = emrPatientService_consumer.updateHisEmrJosnInfo(emrMapParam);

        commonGetVisitInfo(insureEmrUnifiedDTO.getHospCode(),insureEmrUnifiedDTO.getVisitId(),insureEmrUnifiedDTO.getMdtrtId());
        //先删除后新增
        HashMap map = new HashMap();
        // 数据来源
        map.put("source", "1");
        map.put("mdtrtId", insureEmrUnifiedDTO.getMdtrtId());
        map.put("mdtrtSn", insureEmrUnifiedDTO.getVisitId());
        // 入院信息
        InsureEmrAdminfoDTO insureEmrAdminfoDTO  = queryAdminfoInfo(emrMap);
        insureEmrAdminfoDAO.deleteByMap(map);
        insureEmrAdminfoDAO.insert(insureEmrAdminfoDTO);
        // 诊断信息
        List<InsureEmrDiseinfoDTO> insureEmrDiseinfoDTO = queryDiagnoseInfo(emrMap);
        insureEmrDiseinfoDAO.deleteByMap(map);
        insureEmrDiseinfoDAO.insertList(insureEmrDiseinfoDTO);
        // 病程记录信息
        List<InsureEmrCoursrinfoDTO> coursrinfoList = queryEmrCoursrInfo(emrMap,inptVisitDTO);
        insureEmrCoursrinfoDAO.deleteByMap(map);
        insureEmrCoursrinfoDAO.insertList(coursrinfoList);
        // 手术信息
        List<InsureEmrOprninfoDTO> operationInfoList = queryEmrOperationInfo(emrMap);
        insureEmrOprninfoDAO.deleteByMap(map);
        insureEmrOprninfoDAO.insertList(operationInfoList);
        // 病情抢救信息
        List<InsureEmrRescinfoDTO>  rescInfoList = queryEmrRescInfo(emrMap,inptVisitDTO);
        insureEmrRescinfoDAO.deleteByMap(map);
        insureEmrRescinfoDAO.insertList(rescInfoList);
        // 死亡记录
        List<InsureEmrDieinfoDTO>  dieInfoList = queryEmrDieInfo(emrMap,inptVisitDTO);
        insureEmrDieinfoDAO.deleteByMap(map);
        insureEmrDieinfoDAO.insertList(dieInfoList);
        // 出院小结
        List<InsureEmrDscginfoDTO> dscgoInfoList = queryEmrDscgoInfo(emrMap,inptVisitDTO);
        insureEmrDscginfoDAO.deleteByMap(map);
        insureEmrDscginfoDAO.insertList(dscgoInfoList);
    }

    @Override
    public void updateInsureUnifiedEmrUpload(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getVisitId())){
            throw new BizRtException(InsureExecCodesEnum.PARAM_CHANGE_ERROR,new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),"visitId就医流水号"});
        }
        if(StringUtils.isEmpty(insureEmrUnifiedDTO.getMdtrtId())){
            throw new BizRtException(InsureExecCodesEnum.PARAM_CHANGE_ERROR,new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),"mdtrtId医保就诊id"});
        }

        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();
        String hospCode = insureEmrUnifiedDTO.getHospCode();
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(hospCode,mdtrtSn,mdtrtId);

        //1.组装参数上传，2.修改状态
        Map<String, Object> paramMap = new HashMap<>();
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("hospCode", hospCode);
        InsureEmrDetailDTO insureEmrDetailDTO = queryInsureUnifiedEmrDetail(insureEmrUnifiedDTO);
        paramMap.put("insureEmrDetailDTO", insureEmrDetailDTO);

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INSUR_EMR_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.INSUR_EMR_UPLOAD, interfaceParamDTO);

        //2.修改状态
        InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrDetailDTO.getInsureEmrAdminfoDTO();
        insureEmrAdminfoDTO.setStatu("2");
        insureEmrAdminfoDTO.setUploadTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
        insureEmrAdminfoDTO.setUpdateTime(DateUtils.format(new Date(), DateUtils.Y_M_DH_M_S));
        insureEmrAdminfoDAO.updateSelective(JSONObject.parseObject(JSON.toJSONString(insureEmrAdminfoDTO)));
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
    public  InsureIndividualVisitDTO commonGetVisitInfo(String hospCode,String visitId,String medicalRegNo){
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("medicalRegNo",medicalRegNo);
        insureVisitParam.put("hospCode", hospCode);
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }

    /**
     * @Method queryAdminfoInfo
     * @Desrciption  医保统一支付平台：电子病历上传 入院信息
     *          入院记录的部分信息：由于是护士填写的 所以需要从护理记录单抽取数据
     * @Param map
     *
     * @Author liuliyun
     * @Date   2021/8/21 10:59
     * @Return
     **/
    private InsureEmrAdminfoDTO queryAdminfoInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisitDTO");
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        Map<String, Object> detailMap = new HashMap<>();
        detailMap.putAll(map);
        detailMap.put("mdtrt_sn",inptVisit.getId()); // 就医流水号
        detailMap.put("mdtrt_id",medicalRegNo); // 医保就诊ID（医保必填）
        detailMap.put("psn_no",inptVisit.getInsureNo()); // 人员编号(医保必填)
        detailMap.put("mdtrtsn",inptVisit.getInNo()); // 住院号
        detailMap.put("name",inptVisit.getName()); // 姓名
        detailMap.put("gend",inptVisit.getGenderCode()); // 性别
        detailMap.put("age",inptVisit.getAge()); // 年龄
        detailMap.put("adm_rec_no",inptVisit.getInNo()); // 入院记录流水号
        detailMap.put("wardarea_name",inptVisit.getWardName()); // 病区名称
        detailMap.put("dept_code",inptVisit.getNationCode()); // 科室编码
        detailMap.put("dept_name",inptVisit.getInDeptName()); // 科室名称
        detailMap.put("bedno",inptVisit.getBedName()); // 病床号
        detailMap.put("adm_time", DateUtils.format(inptVisit.getInTime(),DateUtils.Y_M_DH_M_S)); // 入院时间
        String zgPracCertiNo =  inptVisit.getOutptPracCertiNo();
        String outptDoctorName = inptVisit.getOutptDoctorName();
        String zzPracCertiNo = inptVisit.getZzPracCertiNo();
        String zzDoctorName = inptVisit.getZzDoctorName();
        detailMap.put("chfpdr_code",zzPracCertiNo); // 主诊医师代码
        detailMap.put("chfpdr_name",zzDoctorName); // 主诊医师姓名
        if(StringUtils.isEmpty(outptDoctorName)){
            detailMap.put("rec_doc_code",zgPracCertiNo); // 接诊医生编号
            detailMap.put("rec_doc_name",zgPracCertiNo); // 接诊医生姓名
        }else{
            detailMap.put("rec_doc_code",zzPracCertiNo); // 接诊医生编号
            detailMap.put("rec_doc_name",zzDoctorName); // 接诊医生姓名
        }
        detailMap.put("ipdr_code",inptVisit.getJzPracCertiNo()); // 住院医师编号
        detailMap.put("ipdr_name",inptVisit.getJzDoctorName()); // 住院医师姓名
        detailMap.put("chfdr_code",inptVisit.getZgPracCertiNo()); // 主任医师编号
        detailMap.put("chfdr_name",inptVisit.getZgDoctorName()); // 主任医师姓名
        detailMap.put("main_symp",inptVisit.getDiseaseName()); // 主要症状
        detailMap.put("adm_rea",inptVisit.getInRemark()); // 入院原因
        detailMap.put("adm_way",inptVisit.getInModeCode()); // 入院途径



//        MapUtils.isEmptyErr("illhis_stte_name","入院记录所属病历内容的病史陈述者姓名为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("illhis_stte_rltl","入院记录所属病历内容的陈述者与患者关系代码为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("stte_rele","入院记录所属病历内容的陈述内容是否可靠标识为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("chfcomp","入院记录所属病历内容的主诉为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("dise_now","入院记录所属病历内容的现病史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("hlcon","入院记录所属病历内容的健康状况为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("dise_his","入院记录所属病历内容的疾病史（含外伤）为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("ifet","入院记录所属病历内容的月经史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("ifet_his","入院记录所属病历内容的患者传染性标志为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("prev_vcnt","入院记录所属病历内容的传染病史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("oprn_his","入院记录所属病历内容的手术史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("bld_his","入院记录所属病历内容的输血史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("algs_his","入院记录所属病历内容的过敏史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("psn_his","入院记录所属病历内容的个人史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("mrg_his","入院记录所属病历内容的婚育史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("mena_his","入院记录所属病历内容的体格检查 -- 外生殖器检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("fmhis","入院记录所属病历内容的月经史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_tprt","入院记录所属病历内容的家族史为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_pule","入院记录所属病历内容的体格检查 -- 脉率（次 /mi数字）为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_vent_frqu","入院记录所属病历内容的体格检查--呼吸频率为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_systolic_pre","入院记录所属病历内容的体格检查 -- 收缩压 （mmHg）为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_dstl_pre","入院记录所属病历内容的体格检查 -- 舒张压 （mmHg）为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_height","入院记录所属病历内容的体格检查--身高（cm）为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_wt","入院记录所属病历内容的体格检查--体重（kg）为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_genital_area","入院记录所属病历内容的体格检查 -- 外生殖器检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_ordn_stas","入院记录所属病历内容的体格检查 -- 一般状况 检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_skin_musl","入院记录所属病历内容的体格检查 -- 皮肤和黏膜检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_spef_lymph","入院记录所属病历内容的体格检查 -- 全身浅表淋巴结检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_head","入院记录所属病历内容的体格检查 -- 头部及其器官检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_neck","入院记录所属病历内容的体格检查 -- 颈部检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_chst","入院记录所属病历内容的体格检查 -- 胸部检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_abd","入院记录所属病历内容的体格检查 -- 腹部检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_finger_exam","入院记录所属病历内容的体格检查 -- 肛门指诊检查结果描述为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_genital_area","入院记录所属病历内容的体格检查 -- 外生殖器检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_spin","入院记录所属病历内容的体格检查 -- 脊柱检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("physexm_all_fors","入院记录所属病历内容的体格检查 -- 四肢检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("nersys","入院记录所属病历内容的体格检查 -- 神经系统检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("spcy_info","入院记录所属病历内容的专科情况为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("asst_exam_rslt","入院记录所属病历内容的辅助检查结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("apgr","入院记录所属病历内容的评分值(Apgar)为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("diet_info","入院记录所属病历内容的饮食情况为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("growth_deg","入院记录所属病历内容的发育程度为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("mtl_stas_norm","入院记录所属病历内容的精神状态正常标志为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("slep_info","入院记录所属病历内容的睡眠状况为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("sp_info","入院记录所属病历内容的特殊情况为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("mind_info","入院记录所属病历内容的心理状态为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("nurt","入院记录所属病历内容的营养状态为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("self_ablt","入院记录所属病历内容的自理能力为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("nurscare_obsv_item_name","入院记录所属病历内容的护理观察项目名称为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("nurscare_obsv_rslt","入院记录所属病历内容的护理观察结果为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("smoke","入院记录所属病历内容的吸烟标志为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("stop_smok_days","入院记录所属病历内容的停止吸烟天数为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("smok_info","入院记录所属病历内容的吸烟状况为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("smok_day","入院记录所属病历内容的日吸烟量（支）为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("drnk","入院记录所属病历内容的饮酒标志为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("drnk_frqu","入院记录所属病历内容的饮酒频率为空,请先确认好是否匹配好元素,或者是否填写");
//        MapUtils.isEmptyErr("drnk_day","入院记录所属病历内容的日饮酒量（mL）为空,请先确认好是否匹配好元素,或者是否填写");
        detailMap.put("resp_nurs_name",inptVisit.getRespNurseName()); // 责任护士姓名
        detailMap.put("vali_flag", Constants.SF.S); // 有效标志

        detailMap.put("source", "1"); // 数据来源
        return HumpUnderlineUtils.underlineToHump(JSON.parseObject(JSON.toJSONString(detailMap)),InsureEmrAdminfoDTO.class);
    }

    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 获取诊断信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 14:52
     * @return*/
    private List<InsureEmrDiseinfoDTO> queryDiagnoseInfo(Map<String, Object> map) {
        List<InptDiagnoseDTO> diagnoseDTOS = MapUtils.get(map,"diagnoseDTOS");
        Map<String, Object> diseinfoMap;
        InptDiagnoseDTO diagnoseDTO ;
        List<Map<String,Object>> diagnoseList = new ArrayList<>();
        if(ListUtils.isEmpty(diagnoseDTOS)){
            throw new AppException("未获取到住院诊断信息");
        }
        for(int i=0;i<diagnoseDTOS.size();i++){
            diagnoseDTO=diagnoseDTOS.get(i);
            diseinfoMap  = new HashMap<>();
            String typeCode = diagnoseDTO.getTypeCode();
            if("201".equals(typeCode)){
                diseinfoMap.put("inout_diag_type","1");//	出入院诊断类别  1.代表入院诊断  2 代表出院诊断
            }else{
                diseinfoMap.put("inout_diag_type","2");//	出入院诊断类别  1.代表入院诊断  2 代表出院诊断
            }
            // 云his的入院，和出院是分别有一个主诊断 需要特殊处理
            if("201".equals(typeCode) && Constants.SF.S.equals(diagnoseDTO.getIsMain())){
                diseinfoMap.put("maindiag_flag", "0");//主诊断标志
            }else{
                diseinfoMap.put("maindiag_flag", diagnoseDTO.getIsMain());//主诊断标志
            }
            diseinfoMap.put("diag_seq",i+1);//	诊断序列号
            diseinfoMap.put("diag_time",DateUtils.format(diagnoseDTO.getCrteTime(),DateUtils.Y_M_DH_M_S));//诊断时间
            diseinfoMap.put("wm_diag_code",diagnoseDTO.getInsureInllnessName());//	西医诊断编码
            diseinfoMap.put("wm_diag_name",diagnoseDTO.getInsureInllnessCode());//	西医诊断名称
            diseinfoMap.put("tcm_dise_code","");//	中医病名代码
            diseinfoMap.put("tcm_dise_name","");//	中医病名
            diseinfoMap.put("tcmsymp_code","");//	中医证候代码
            diseinfoMap.put("tcmsymp","");//	中医证候
            diseinfoMap.put("vali_flag",Constants.SF.S);//	有效标志
            diseinfoMap.put("source", "1"); // 有效标志
            diagnoseList.add(diseinfoMap);
        }
        map.put("diagnoseList",diagnoseList);
        return HumpUnderlineUtils.underlineToHumpArray(JSON.parseArray(JSON.toJSONString(diagnoseList)),InsureEmrDiseinfoDTO.class);
    }

    /**
     * @Method queryOperationInfo
     * @Desrciption 获取手术信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 15:39
     * @return*/
    private List<InsureEmrOprninfoDTO> queryEmrOperationInfo(Map<String, Object> map) {
        List<OperInfoRecordDTO> operInfoRecordInfos = MapUtils.get(map,"operInfoRecordInfos");
        OperInfoRecordDTO operInfoRecordDTO;
        Map<String, Object> operInfoMap = null;
        String rank ="";
        List<Map<String,Object>> operationInfoList = new ArrayList<>();
        if(!ListUtils.isEmpty(operInfoRecordInfos)){
            for(int i=0;i<operInfoRecordInfos.size();i++){
                operInfoRecordDTO=operInfoRecordInfos.get(i);
                operInfoMap  = new HashMap<>();
                operInfoMap.put("oprn_appy_id",operInfoRecordDTO.getId());//	手术申请单号
                operInfoMap.put("oprn_seq",i);//	手术序列号
                operInfoMap.put("blotype_abo",operInfoRecordDTO.getBloodCode());//	血型代码
                operInfoMap.put("oprn_time",DateUtils.format(operInfoRecordDTO.getOperPlanTime(),DateUtils.Y_M_DH_M_S));//手术日期
                operInfoMap.put("oprn_type_code","无"); // 手术分类代码
                operInfoMap.put("oprn_type_name","无");//  手术分类名称
                operInfoMap.put("bfpn_diag_code",""); //术前诊断代码
                operInfoMap.put("bfpn_oprn_diag_name",operInfoRecordDTO.getOperDiseaseAfter()); //术前诊断名称
                operInfoMap.put("bfpn_inhosp_ifet","");//	术前是否发生院内感染
                operInfoMap.put("afpn_diag_code","");//	术后诊断代码
                operInfoMap.put("afpn_diag_name",operInfoRecordDTO.getOperDiseaseAfter());//	术后诊断名称
                operInfoMap.put("sinc_heal_lv",operInfoRecordDTO.getHealCode()); //手术切口愈合等级代码
                operInfoMap.put("sinc_heal_lv_code",""); // 手术切口愈合等级
                operInfoMap.put("back_oprn","");//	是否重返手术（明确定义）
                operInfoMap.put("selv","");//	是否择期
                operInfoMap.put("prev_abtl_medn","");//	是否预防使用抗菌药物
                operInfoMap.put("abtl_medn_days","");//	预防使用抗菌药物天数
                operInfoMap.put("oprn_oprt_code",operInfoRecordDTO.getOperDiseaseIcd9()); //手术操作代码
                operInfoMap.put("oprn_oprt_name",operInfoRecordDTO.getOperDiseaseName()); //手术操作名称
                if("1".equals(rank)){
                    operInfoMap.put("oprn_lv_code","1"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","一级");//手术级别名称
                }
                else if("2".equals(rank)){
                    operInfoMap.put("oprn_lv_code","2"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","二级手术");//手术级别名称
                }
                else if("3".equals(rank)){
                    operInfoMap.put("oprn_lv_code","3"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","三级手术");//手术级别名称
                }
                else if("4".equals(rank)){
                    operInfoMap.put("oprn_lv_code","4"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","四级手术");//手术级别名称
                }
                else{
                    operInfoMap.put("oprn_lv_code","5"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","其他等级");//手术级别名称
                }
                operInfoMap.put("anst_mtd_code",operInfoRecordDTO.getAnaCode());//	麻醉-方法代码
                operInfoMap.put("anst_mtd_name",operInfoRecordDTO.getAnaCode());//	麻醉-方法名称
                operInfoMap.put("anst_lv_code","无");//	麻醉分级代码
                operInfoMap.put("anst_lv_name","无");//	麻醉分级名称
                operInfoMap.put("exe_anst_dept_code","无");//	麻醉执行科室代码
                operInfoMap.put("exe_anst_dept_name","无");//	麻醉执行科室名称
                operInfoMap.put("anst_efft","");// 麻醉效果
                operInfoMap.put("oprn_begntime",operInfoRecordDTO.getCrteTime());//	手术开始时间
                operInfoMap.put("oprn_endtime",operInfoRecordDTO.getOperEndTime());//	手术结束时间
                operInfoMap.put("oprn_asps","");//	麻醉分级名称
                operInfoMap.put("oprn_asps_ifet","");//	无菌手术是否感染
                operInfoMap.put("afpn_info","");//	手术后情况
                operInfoMap.put("oprn_merg","");//	是否手术合并症
                operInfoMap.put("oprn_conc","");//	是否手术并发症
                operInfoMap.put("oprn_anst_dept_code",operInfoRecordDTO.getOperDeptId());//	手术执行科室代码
                operInfoMap.put("oprn_anst_dept_name",operInfoRecordDTO.getOperRoomName());//	手术执行科室名称
                operInfoMap.put("palg_dise","");// 病理检查
                operInfoMap.put("oth_med_dspo",""); //其他医学处置
                operInfoMap.put("out_std_oprn_time",""); //是否超出手术时间
                operInfoMap.put("oprn_oper_name",operInfoRecordDTO.getDoctorName());//	手术者姓名
                operInfoMap.put("oprn_asit_name1",operInfoRecordDTO.getAssistantName1());//	助手I姓名
                operInfoMap.put("oprn_asit_name2",operInfoRecordDTO.getAssistantName2());//	助手Ⅱ姓名
                operInfoMap.put("anst_dr_name",operInfoRecordDTO.getAnaName1());// 麻醉医师姓名
                operInfoMap.put("anst_asa_lv_code","");//麻醉ASA分级代码
                operInfoMap.put("anst_asa_lv_name","");// 麻醉ASA分级名称
                operInfoMap.put("anst_medn_code","");// 麻醉药物代码
                operInfoMap.put("anst_medn_name",""); // 麻醉药物名称
                operInfoMap.put("anst_medn_dos",""); // 麻醉药物剂量
                operInfoMap.put("anst_dosunt","");// 计量单位
                operInfoMap.put("anst_begntime",""); // 麻醉开始时间
                operInfoMap.put("anst_endtime",""); // 麻醉结束时间
                operInfoMap.put("anst_merg_symp_code",""); // 麻醉合并症代码
                operInfoMap.put("anst_merg_symp",""); // 麻醉合并症名称
                operInfoMap.put("anst_merg_symp_dscr",""); // 麻醉合并症描述
                operInfoMap.put("pacu_begntime",""); // 入复苏室时间
                operInfoMap.put("pacu_endtime",""); // 出复苏室时间
                operInfoMap.put("oprn_selv",""); // 是否择期手术
                operInfoMap.put("canc_oprn",""); // 是否择取消手术
                operInfoMap.put("vali_flag",Constants.SF.S); //有效标志

                operInfoMap.put("source", "1"); //
                operationInfoList.add(operInfoMap);
            }
        }

        return HumpUnderlineUtils.underlineToHumpArray(JSON.parseArray(JSON.toJSONString(operationInfoList)),InsureEmrOprninfoDTO.class);
    }

    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 获取病程记录
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 14:52
     * @return*/
    private List<InsureEmrCoursrinfoDTO> queryEmrCoursrInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrCoReList  = MapUtils.get(map,"coursrinfo");
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(!ListUtils.isEmpty(emrCoReList)){
            for(Map<String,Object> item : emrCoReList){
                // 科室代码
                item.put("dept",inptVisitDTO.getInDeptCode());
                // 科室名称
                item.put("dept_name",inptVisitDTO.getInDeptName());
                // 病区名称
                item.put("wardarea_name",inptVisitDTO.getWardName());
                // 床位号
                item.put("bedno",inptVisitDTO.getBedName());
//                MapUtils.checkEmptyErr(item,"dept","病程记录所属病历内容的科室代码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dept_name","病程记录所属病历内容的科室名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"wardarea_name","病程记录所属病历内容的病区名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"bedno","病程记录所属病历内容的病床号为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"chfcomp","病程记录所属病历内容的主诉为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"cas_ftur","病程记录所属病历内容的病例特点为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dise_evid","病程记录所属病历内容的诊断依据为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"prel_wm_diag_code","病程记录所属病历内容的初步诊断-西医诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"prel_tcm_dise_name","病程记录所属病历内容的初步诊断-西医诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"finl_wm_diag_code","病程记录所属病历内容的鉴别诊断-西医诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"finl_wm_diag_name","病程记录所属病历内容的鉴别诊断-西医诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dise_plan","病程记录所属病历内容的诊疗计划为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"ipdr_code","病程记录所属病历内容的住院医师编号为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"ipdr_name","病程记录所属病历内容的住院医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"prnt_doc_name","病程记录所属病历内容的上级医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"vali_flag",Constants.SF.S);
                item.put("vali_flag",Constants.SF.S);
                item.put("source", "1"); //
            }
        }
//        return resultList;
        return HumpUnderlineUtils.underlineToHumpArray(JSON.parseArray(JSON.toJSONString(resultList)),InsureEmrCoursrinfoDTO.class);
    }

    /**
     * @Method queryEmrRescInfo
     * @Desrciption  医保统一支付平台：电子病历上传 病情抢救记录
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:05
     * @Return
     **/
    private List<InsureEmrRescinfoDTO> queryEmrRescInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrRescReList = MapUtils.get(map,"rescinfo");
        if(!ListUtils.isEmpty(emrRescReList)){
            for(Map<String,Object> item : emrRescReList){
                // 科室代码
                item.put("dept",inptVisitDTO.getInDeptCode());
                // 科室名称
                item.put("dept_name",inptVisitDTO.getInDeptName());
                // 病区名称
                item.put("wardarea_name",inptVisitDTO.getWardName());
                // 床位号
                item.put("bedno",inptVisitDTO.getBedName());
                // 入院诊断名称
                item.put("adm_dise",inptVisitDTO.getInsureIllnessName());
                // 入院诊断编码
                item.put("diag_code",inptVisitDTO.getInsureIllnessCode());
//                MapUtils.checkEmptyErr(item,"dept","病情抢救记录所属病历内容的科室代码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dept_name","病情抢救记录所属病历内容的科室名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"wardarea_name","病情抢救记录所属病历内容的病区名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"bedno","病情抢救记录所属病历内容的病床号为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"diag_name","病情抢救记录所属病历内容的诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"diag_code","病情抢救记录所属病历内容的诊断代码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"cond_chg","病情抢救记录所属病历内容的病情变化情况为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"resc_mes","病情抢救记录所属病历内容的抢救措施为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"oprn_oprt_code","病情抢救记录所属病历内容的手术操作代码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"oprn_oprt_name","病情抢救记录所属病历内容的手术操作名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"oprn_oper_part","病情抢救记录所属病历内容的手术及操作目标部位名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"itvt_name","病情抢救记录所属病历内容的介入物名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"oprt_mtd","病情抢救记录所属病历内容的操作方法为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"oprt_cnt","病情抢救记录所属病历内容的操作次数为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"resc_begntime","病情抢救记录所属病历内容的抢救开始日期时间为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"resc_endtime","病情抢救记录所属病历内容的抢救结束日期时间为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dise_item_name","病情抢救记录所属病历内容的检查/检验项目名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dise_ccls","病情抢救记录所属病历内容的检查/检验结果为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dise_ccls_qunt","病情抢救记录所属病历内容的检查/检验定量结果为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dise_ccls_code","病情抢救记录所属病历内容的检查/检验结果代码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"mnan","病情抢救记录所属病历内容的注意事项为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"resc_psn_list","病情抢救记录所属病历内容的参加抢救人员名单为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"proftechttl_code","病情抢救记录所属病历内容的专业技术职务类别代码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"doc_code","病情抢救记录所属病历内容的医师编号为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dr_name","病情抢救记录所属病历内容的医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"vali_flag",Constants.SF.S);
                item.put("vali_flag",Constants.SF.S);
                item.put("source", "1"); //
            }
        }
        return HumpUnderlineUtils.underlineToHumpArray(JSON.parseArray(JSON.toJSONString(emrRescReList)),InsureEmrRescinfoDTO.class);
    }

    /**
     * @Method queryEmrDieInfo
     * @Desrciption  死亡记录
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:20
     * @Return
     **/
    private List<InsureEmrDieinfoDTO> queryEmrDieInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrDieReList = MapUtils.get(map,"dieinfo");
        if(!ListUtils.isEmpty(emrDieReList)){
            for(Map<String,Object> item : emrDieReList){
                // 科室代码
                item.put("dept",inptVisitDTO.getInDeptCode());
                // 科室名称
                item.put("dept_name",inptVisitDTO.getInDeptName());
                // 病区名称
                item.put("wardarea_name",inptVisitDTO.getWardName());
                // 床位号
                item.put("bedno",inptVisitDTO.getBedName());
                // 入院时间
                item.put("adm_time",DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));
                // 入院诊断编码
                item.put("adm_dise",inptVisitDTO.getInsureIllnessCode());
                // 入院情况
                item.put("adm_info",inptVisitDTO.getInSituationName());
//                MapUtils.checkEmptyErr(item,"dept","死亡记录所属病历内容的科室代码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"dept_name","死亡记录所属病历内容的科室名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"wardarea_name","死亡记录所属病历内容的病区名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"bedno","死亡记录所属病历内容的病床号为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"adm_time","死亡记录所属病历内容的入院时间为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"adm_dise","死亡记录所属病历内容的入院诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"adm_info","死亡记录所属病历内容的入院情况为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"trt_proc_dscr","死亡记录所属病历内容的诊疗过程描述为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"die_time","死亡记录所属病历内容的死亡时间为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"die_drt_rea","死亡记录所属病历内容的直接死亡原因名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"die_drt_rea_code","死亡记录所属病历内容的直接死亡原因编码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"die_dise_name","死亡记录所属病历内容的死亡诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"die_diag_code","死亡记录所属病历内容的死亡诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"agre_corp_dset","死亡记录所属病历内容的家属是否同意尸体解剖标志为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"ipdr_name","死亡记录所属病历内容的住院医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"chfpdr_name","死亡记录所属病历内容的主诊医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
//                MapUtils.checkEmptyErr(item,"chfdr_name","死亡记录所属病历内容的主任医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
                item.put("sign_time",DateUtils.format(DateUtils.Y_M_DH_M_S));//	签字日期时间
                item.put("vali_flag",Constants.SF.S);//	是否有效

                item.put("source", "1"); //
            }
        }
        return HumpUnderlineUtils.underlineToHumpArray(JSON.parseArray(JSON.toJSONString(emrDieReList)),InsureEmrDieinfoDTO.class);
    }

    /**
     * @Method queryEmrDscgoInfo
     * @Desrciption  出院小结
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:35
     * @Return
     **/
    private List<InsureEmrDscginfoDTO> queryEmrDscgoInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrOutReList = MapUtils.get(map,"dscginfo");
        if (!ListUtils.isEmpty(emrOutReList)){
            for(Map<String,Object> item : emrOutReList){
                // 出院日期
                item.put("dscg_date",DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));
                // 科别
                item.put("caty",inptVisitDTO.getOutptNationCode());
                // 出院诊断
                item.put("dscg_dise_dscr",inptVisitDTO.getInsureIllnessCode());
                //有效标志
                item.put("vali_flag",Constants.SF.S);

                item.put("source", "1"); //
            }
        }else{
            throw new AppException("请检查是否书写电子病历内容:出院小结信息等");
        }
        return HumpUnderlineUtils.underlineToHumpArray(JSON.parseArray(JSON.toJSONString(emrOutReList)),InsureEmrDscginfoDTO.class);
    }

    private String getSysCodeName(String hospCode, String code, String value) {
        Map map = new HashMap(2);
        map.put("hospCode", hospCode);
        map.put("code", code);
        Map<String, String> dictMap = insureDictService_consumer.querySysCodeByCode(map).getData();
        return MapUtils.isEmpty(dictMap) ? "" : dictMap.get(value);
    }
}
