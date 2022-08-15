package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.insure.xiangtan.outpt.OutptFunction;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureOutptOutFeeDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.insure.outpt.bo.InsureVisitInfoBO;
import cn.hsa.module.insure.outpt.bo.OutptBo;
import cn.hsa.module.mris.mrisHome.dao.MrisHomeDAO;
import cn.hsa.module.sys.parameter.dao.SysParameterDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: OutptBoImpl
 * @Describe(描述): 门诊医保开放统一接口 Bo实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 8:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class OutptBoImpl extends HsafBO implements OutptBo {

    private static final Logger logger = LoggerFactory.getLogger(OutptBoImpl.class);
    @Resource
    private Transpond transpond;

    @Resource
    private OutptFunction outptFunction;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Resource
    private InsureVisitInfoBO insureVisitInfoBO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Override
    public Map<String, Object> getInsureOutptOutFeeInfo(Map<String,Object>map) {
        String hospCode = map.get("hospCode").toString();
        String regCode = map.get("insureRegCode").toString();
        return transpond.<Map>to(hospCode,regCode, Constant.FUNCTION.BIZH110103,map);
    }

    /**
     * @Menthod getOutptVisitInfo
     * @Desrciption 门诊获取医保个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/10 14:33
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> getOutptVisitInfo(Map<String, Object> param) {
        String hospCode = (String) param.get("hospCode");
        String regCode = (String) param.get("regCode");
        // return transpond.<Map>to(hospCode,regCode, Constant.FUNCTION.BIZH110001,param);

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(regCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
		/*// 获取系统参数中配置的是否走统一支付平台
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();*/

        String bka895 = (String) param.get("bka895");
        if ("qrcode".equals(bka895)){//电子凭证
            return transpond.to(hospCode,regCode,Constant.FUNCTION.FC_EMD_11001,param);
//        } else if (sys != null && sys.getValue().equals("1")) {  // 调用统一支付平台
        } else if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {  // 调用统一支付平台
            param.put("isHospital",Constants.SF.F);
            Map<String, Object> resultMap = insureVisitInfoBO.getInsureVisitInfo(param);
            return resultMap;

        } else {  // 直接调用医保
            return transpond.<Map>to(hospCode,regCode, Constant.FUNCTION.BIZH110001,param);
        }
    }

    /**
     * @Menthod setOutptCostUpload
     * @Desrciption 门诊费用上传并试算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:02
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> setOutptCostUploadAndTrial(Map<String, Object> param) {
        return transpond.<Map>to((String) param.get("hospCode"),(String) param.get("insureRegCode"),Constant.FUNCTION.FC_2002,param);
    }

    /**
     * @Menthod setOutptCostUploadAndSettlement
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> setOutptCostUploadAndSettlement(Map<String, Object> param) {
        String hospCode = (String) param.get("hospCode");
        Map<String,Object> trialMap = transpond.<Map>to(hospCode,(String) param.get("insureRegCode"),Constant.FUNCTION.FC_2003,param);
        logger.info("++++++++++++++++++++++++工伤结算反参打印开始(暂时)++++++++++++++++++++++++++++");
        logger.info(trialMap.toString());
        logger.info("++++++++++++++++++++++++工伤结算反参打印结束++++++++++++++++++++++++++++");
        try {
                if ("settle".equals(param.get("action")) && trialMap != null && trialMap.containsKey("payinfo")) {
                Map<String, String> payinfo = (Map<String, String>) trialMap.get("payinfo");
                logger.info("++++++++++++++++++++++++工伤结算支付信息打印开始(暂时)++++++++++++++++++++++++++++");
                logger.info(payinfo.toString());
                logger.info("++++++++++++++++++++++++工伤结算支付信息打印结束++++++++++++++++++++++++++++");
                String visitId = (String) param.get("visitId");//就诊id
                String akb020 = payinfo.get("akb020");//医院编码
                String aaz217 = payinfo.get("aaz217");//就医登记号
                //编辑医保患者信息表
                InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
                insureIndividualVisitDTO.setVisitId(visitId);//就诊id
                insureIndividualVisitDTO.setMedicalRegNo(aaz217);//医保登记号
                    insureIndividualVisitDTO.setHospCode(hospCode);
                    logger.info("++++++++++++++++++++++++工伤结算支付信息打印开始(暂时)++++++++++++++++++++++++++++");
                    logger.info("visitId = "+visitId+"hospCode = "+hospCode+ "medicalRegNo = "+ aaz217);
                    logger.info("insureIndividualVisitDTO-------visitId = " +insureIndividualVisitDTO.getHospCode()+ "insureIndividualVisitDTO-------visitId = " +insureIndividualVisitDTO.getVisitId()+ "insureIndividualVisitDTO-------visitId = " +insureIndividualVisitDTO.getMedicalRegNo());
                    logger.info(insureIndividualVisitDTO.toString());
                    logger.info("++++++++++++++++++++++++工伤结算支付信息打印结束++++++++++++++++++++++++++++");
                insureIndividualVisitDAO.updateInsureMedicalRegNo(insureIndividualVisitDTO);
                if (trialMap.containsKey("insureIndividualCostDOList")) {
                    List<InsureIndividualCostDO> insureIndividualCostDOList = (List<InsureIndividualCostDO>) trialMap.get("insureIndividualCostDOList");
                    insureIndividualCostDAO.batchInsertInsureCost(insureIndividualCostDOList);
                }
            }
        }catch (RuntimeException e){
            logger.error("医保结算：门诊费用上传并结算出现异常：",e);
            throw new AppException("医保结算成功，系统出现异常，请到医保结算系统取消本次结算。");
        }
        return trialMap;
    }

    /**
     * @Method selectCheckInfo
     * @Desrciption  读取审批信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:31
     * @Return map
     **/
    @Override
    public Map<String, Object> selectCheckInfo(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        String insureRegCode = (String) map.get("insureRegCode");
        return transpond.to(hospCode, insureRegCode, Constant.ChangSha.RESTS.CS_1806, map);
    }

    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
    **/
    @Override
    public Boolean uploadCheckInfo(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        String insureRegCode = (String) map.get("insureRegCode");
        return transpond.to(hospCode, insureRegCode, Constant.ChangSha.RESTS.CS_3110, map);
    }

    /**
     * @param map
     * @Desrciption uploadCheckInfo 门特病人取消结算以后 取消登记
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     */
    @Override
    public Boolean cancelRegister(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO =new InsureIndividualVisitDTO();
        String visitId = map.get("visitId").toString();
        String hospCode = map.get("hospCode").toString();
        insureIndividualVisitDTO.setVisitId(visitId);
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.selectInsureIndividualVisit(insureIndividualVisitDTO);
        if(insureIndividualVisitDTO ==null){
            throw new AppException("该病人还未进行医保登记,不能取消登记");
        }
        String insureRegCode  = insureIndividualVisitDTO.getInsureOrgCode();
        map.put("insureRegCode",insureRegCode);
        map.put("aac001",insureIndividualVisitDTO.getAac001());
        return transpond.to(hospCode, insureRegCode, Constant.ChangSha.OUTPT.CS_2240, map);
    }
}
