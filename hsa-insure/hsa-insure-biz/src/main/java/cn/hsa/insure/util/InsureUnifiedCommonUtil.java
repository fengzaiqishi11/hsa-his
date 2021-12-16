package cn.hsa.insure.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.util.Constants;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.util
 * @class_name: InsureUifiedCommonUtil
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 14:48
 * @Company: 创智和宇
 **/
@Component
public class InsureUnifiedCommonUtil {
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Method commonInsureUnified
     * @Desrciption 调用统一支付平台公共接口方法
     * @Param hospCode:医院编码
     * orgCode:医疗机构编码
     * functionCode：功能号
     * paramMap:调用医保参数入参
     * logParamMap:日志入参
     * @Author fuhui
     * @Date 2021/4/28 19:51
     * @Return
     **/
    public Map<String, Object> commonInsureUnified(String hospCode, String orgCode, String functionCode, Map<String, Object> paramMap,Map<String,Object> logParamMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO ==null){
            throw new AppException("查询医保机构配置信息为空");
        }
        StringBuilder stringBuilder = new StringBuilder();
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        if(StringUtils.isEmpty(MapUtils.get(paramMap,"insuplcAdmdvs"))){
            httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        }else{
            httpParam.put("insuplc_admdvs", MapUtils.get(paramMap,"insuplcAdmdvs")); //参保地医保区划分
            MapUtils.remove(paramMap,"insuplcAdmdvs");
        }
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        String msgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("调用功能号【" + functionCode + "】的入参为" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logParamMap.put("medisCode",insureConfigurationDTO.getOrgCode());
        logParamMap.put("paramMapJson",json);
        logParamMap.put("msgId",msgId);
        logParamMap.put("msgInfo",functionCode);
        logParamMap.put("resultStr",resultJson);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(logParamMap);
        MapUtils.remove(logParamMap,"msgName");
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        logger.info("调用功能号【" + functionCode + "】的反参为" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        return resultMap;
    }

    /**
     * @Method commonGetVisitInfo
     * @Desrciption 医保统一支付：住院结算，统一患者就诊信息查询接口
     * @Param map
     * @Author fuhui
     * @Date 2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO;
        String hospCode = (String) map.get("hospCode");//医院编码
        String visitId = (String) map.get("visitId");//就诊id
        String medicalRegNo = MapUtils.get(map, "medicalRegNo");
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("medicalRegNo", medicalRegNo);
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }

    /**
     * @Method getInsureInsureConfiguration
     * @Desrciption  根据医院编码和医保机构编码查找医保机构配置信息
     * @Param hospCode:医院编码 regCode:医保机构配置编码
     *
     * @Author fuhui
     * @Date   2021/11/25 13:54
     * @Return
    **/
    public InsureConfigurationDTO getInsureInsureConfiguration(String hospCode,String regCode){
        if(StringUtils.isEmpty(hospCode)){
            throw new AppException("医院编码参数为空");
        }
        if(StringUtils.isEmpty(regCode)){
            throw new AppException("医保机构编码参数为空");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setCode(regCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO == null){
            throw new AppException("根据医保机构编码未查找到医保机构配置信息");
        }
        return insureConfigurationDTO;
    }
}
