package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.inpt.bo.InsureReadCardBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.InsureReadCardBOImpl
 * @Class_name: InsureReadCardBOImpl
 * @Describe: 读卡接口
 * @Author: LiaoJiGuang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/7/29 15:00
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureReadCardBOImpl extends HsafBO implements InsureReadCardBO {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    /**
     * @Description: 读身份证密码校验接口
     * @Param: map
     * @Author: LiaoJiGuang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date 2021/7/29 15:03
     * @Return
     */
    @Override
    public Map<String, Object> getReadIdCard(Map<String, Object> map) {
        String idcard = MapUtils.get(map, "idcard");
        String inputPassword = MapUtils.get(map, "inputPassword");

        if(StringUtils.isEmpty(idcard)){
            throw new AppException("身份证密码校验,身份证卡号：idcard 内容为空");
        }
        if(StringUtils.isEmpty(inputPassword)){
            throw new AppException("身份证密码校验,身份证卡号：inputPassword 内容为空");
        }
        // 调用统一支付平台接口
        Map<String, Object> paramMap = new HashMap<>();
        // 参保地医保区划
        paramMap.putAll(map);
        paramMap.put("configRegCode", MapUtils.get(map, "insureRegCode"));

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.IDCARD_PASSWORD_CHECK.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(MapUtils.get(map, "hospCode"));

        return insureItfBO.executeInsur(FunctionEnum.IDCARD_PASSWORD_CHECK, interfaceParamDTO);

    }

    /**
     * @Description: 读身份证修改密码接口
     * @Param: map
     * @Author: LiaoJiGuang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date 2021/7/30 10:01
     * @Return
     */
    @Override
    public Map<String, Object> updateReadIdCard(Map<String, Object> map) {
        String idcard = MapUtils.get(map, "idcard");
        String password = MapUtils.get(map, "password");
        String oldPassword = MapUtils.get(map, "oldPassword");

        if(StringUtils.isEmpty(idcard)){
            throw new AppException("身份证密码校验,身份证卡号：idcard 内容为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new AppException("身份证密码校验,身份证卡号：password 内容为空");
        }
        if(StringUtils.isEmpty(oldPassword)){
            throw new AppException("身份证密码校验,身份证卡号：oldPassword 内容为空");
        }
        // 调用统一支付平台接口
        Map<String, Object> paramMap = new HashMap<>();
        // 参保地医保区划
        paramMap.putAll(map);
        paramMap.put("configRegCode", MapUtils.get(map, "insureRegCode"));

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.IDCARD_PASSWORD_UPDATE.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(MapUtils.get(map, "hospCode"));

        return insureItfBO.executeInsur(FunctionEnum.IDCARD_PASSWORD_UPDATE, interfaceParamDTO);
    }

    /**
     * @Method commonInsureUnified
     * @Desrciption 调用统一支付平台公共接口方法
     * @Param hospCode:医院编码
     * orgCode:医疗机构编码
     * functionCode：功能号
     * paramMap:入参
     * @Author fuhui
     * @Date 2021/4/28 19:51
     * @Return
     **/
    private Map<String, Object> commonInsureUnified(String hospCode, String regCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(regCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("调用功能号【" + functionCode + "】的入参为" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
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
}
