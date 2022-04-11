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
}
