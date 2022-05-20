package cn.hsa.insure.unifiedpay.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.enums.HsaSrvEnum;
import cn.hsa.exception.BizRtException;
import cn.hsa.exception.InsureExecCodesEnum;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.module.bo.InsureSpecialAttributeBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Package_name:
 * @class_name: InsureSpecialAttributeServiceImpl
 * @Description: TODO
 * @Author: yuelong.chen
 * @Date: 2022/5/16 21:22
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureSpecialAttributeBOImpl implements InsureSpecialAttributeBO {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private InsureGetInfoDAO insureGetInfoDAO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;
    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  就医特殊属性上传病人查询
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     *
     * @return*/
    @Override
    public PageDTO queryInsureSpecialAttributeInfoPage(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        // 设置分页信息
        PageHelper.startPage(insureIndividualVisitDTO.getPageNo(), insureIndividualVisitDTO.getPageSize());
        List<InsureIndividualVisitDTO> visitDTOList = insureGetInfoDAO.queryInsureSpecialAttributeInfoPage(insureIndividualVisitDTO);
        return PageDTO.of(visitDTOList);
    }
    /**
     * @Method uploadInsureSpecialAttribute
     * @Desrciption  就医特殊属性上传
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/16 10:03
     * @Return
     **/
    @Override
    public void UPloadInsureSpecialAttribute(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        //参数校验
        if(StringUtils.isEmpty(insureIndividualVisitDTO.getMedicalRegNo())){
            throw new BizRtException(InsureExecCodesEnum.PARAM_CHANGE_ERROR,new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),"visitId就医流水号"});
        }
        InsureIndividualVisitDTO individualVisitDTO = insureGetInfoDAO.queryVisitById(insureIndividualVisitDTO);
        if(individualVisitDTO == null){
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        //入参处理
        //1.组装参数上传，2.修改状态
        insureIndividualVisitDTO.setMedicineOrgCode(individualVisitDTO.getMedicineOrgCode());
        insureIndividualVisitDTO.setInsureRegCode(individualVisitDTO.getInsureRegCode());
        insureIndividualVisitDTO.setInsureOrgCode(individualVisitDTO.getInsureOrgCode());
        Map<String, Object> paramMap = uploadparam(insureIndividualVisitDTO);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.SPECIAL_ATTRIBUTE_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        interfaceParamDTO.setInsuplc_admdvs(insureIndividualVisitDTO.getInsuplcAdmdvs());
        // 调用统一支付平台接口
        Map<String, Object> stringObjectMap = insureItfBO.executeInsur(FunctionEnum.SPECIAL_ATTRIBUTE_UPLOAD, interfaceParamDTO);
        //获取回参，并写回数据库
        Map<String,Object> outMap = MapUtils.get(stringObjectMap,"output");
        Map<String,Object> data = MapUtils.get(outMap,"data");
        String detlId = MapUtils.get(data,"ipt_psn_sp_flag_detl_id");
        if(StringUtils.isEmpty(detlId)){
            throw new AppException("未接收到回参，请重新上传！");
        }
        insureIndividualVisitDTO.setIptPsnSpFlagDetlId(detlId);
        //回写
        insureGetInfoDAO.updateSpecialAttribute(insureIndividualVisitDTO);
    }
    /**
     * @Method uploadInsureSpecialAttribute
     * @Desrciption  就医特殊属性查询
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/16 10:03
     * @Return
     **/
    @Override
    public List<Map<String, Object>> qureyInsureSpecialAttribute(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        //获取医保配置
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        // 医疗机构编码
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());
        insureConfigurationDTO.setRegCode(insureIndividualVisitDTO.getInsureOrgCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("根据" + insureIndividualVisitDTO.getInsureOrgCode() + "医保机构编码获取医保机构配置信息为空");
        }
        //入参处理
        //1.组装参数上传，2.修改状态
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page_num",insureIndividualVisitDTO.getPageNo());
        paramMap.put("page_size",insureIndividualVisitDTO.getPageSize());
        paramMap.put("ipt_psn_sp_flag_detl_id",insureIndividualVisitDTO.getIptPsnSpFlagDetlId());
        paramMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());
        paramMap.put("psn_no",insureIndividualVisitDTO.getAac001());
        paramMap.put("psn_cert_type",insureIndividualVisitDTO.getMdtrtCertType());
        paramMap.put("certno",insureIndividualVisitDTO.getMdtrtCertNo());
        paramMap.put("psn_name",insureIndividualVisitDTO.getAac003());
        paramMap.put("fixmedins_code",insureIndividualVisitDTO.getFixmedinsCode());
        paramMap.put("fixmedins_name",insureIndividualVisitDTO.getFixmedinsName());
        paramMap.put("ipt_otp_no",insureIndividualVisitDTO.getVisitNo());
        paramMap.put("ipt_psn_sp_flag_type",insureIndividualVisitDTO.getIptPsnSpFlagType());
        paramMap.put("ipt_psn_sp_flag",insureIndividualVisitDTO.getIptPsnSpFlag());
        paramMap.put("admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("configCode", insureConfigurationDTO.getCode());
        paramMap.put("hospCode", insureIndividualVisitDTO.getHospCode());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.SPECIAL_ATTRIBUTE_QUERY.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> stringObjectMap = insureItfBO.executeInsur(FunctionEnum.SPECIAL_ATTRIBUTE_QUERY, interfaceParamDTO);
        //获取回参
        Map<String,Object> outMap = MapUtils.get(stringObjectMap,"output");
        Map<String, Object> resultDataMap  = MapUtils.get(outMap,"IptPsnSpFlagReg");
        List<Map<String, Object>> resultDataList = MapUtils.get(resultDataMap,"data");
        return resultDataList;
    }

    private Map<String, Object> uploadparam(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map<String, Object> paramMap = new HashMap<>();
        // 就诊id
        paramMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        //人员编号
        paramMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        //人员证件类型
        paramMap.put("psn_cert_type", insureIndividualVisitDTO.getMdtrtCertType());
        //证件号码
        paramMap.put("certno", insureIndividualVisitDTO.getMdtrtCertNo());
        //人员姓名
        paramMap.put("psn_name", insureIndividualVisitDTO.getAac003());
        //住院/门诊号
        paramMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo());
        //住院人员特殊标识类型
        paramMap.put("ipt_psn_sp_flag_type", insureIndividualVisitDTO.getIptPsnSpFlagType());
        //住院人员特殊标识
        paramMap.put("ipt_psn_sp_flag", insureIndividualVisitDTO.getIptPsnSpFlag());
        paramMap.put("memo", "");
        //医保区划
        paramMap.put("admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureOrgCode());
        paramMap.put("hospCode", insureIndividualVisitDTO.getHospCode());
        return paramMap;
    }
}
