package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.inpt.bo.InsureReadCardBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
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
        String hospCode =MapUtils.get(map, "hospCpde");
        String orgCode =MapUtils.get(map, "insureRegCode");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("idcard",MapUtils.get(map, "idcard")); // 身份证号码
        dataMap.put("insuAdmdvs",MapUtils.get(map, "insuAdmdvs")); // 行政区划
        dataMap.put("fixmedinsCode",MapUtils.get(map, "fixmedinsCode")); // 医疗机构编码
        dataMap.put("inputPassword",MD5Utils.encrypt32(MapUtils.get(map, "inputPassword"))); // 密码

        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("data",dataMap);
        return commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.CARD.UP_1602, inptMap);
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
        String hospCode =MapUtils.get(map, "hospCode");
        String regCode =MapUtils.get(map, "insureRegCode");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("idcard",MapUtils.get(map, "idcard")); // 身份证号码
        dataMap.put("psnName",MapUtils.get(map, "psnName")); // 姓名
        dataMap.put("insuAdmdvs",MapUtils.get(map, "insuAdmdvs")); // 行政区划
        dataMap.put("password",MD5Utils.encrypt32(MapUtils.get(map, "password"))); // 修改后的密码
        dataMap.put("oldPassword",MD5Utils.encrypt32(MapUtils.get(map, "oldPassword"))); // 修改前的密码

        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("data",dataMap);
        return commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.CARD.UP_1603, inptMap);
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
