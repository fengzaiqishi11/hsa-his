package cn.hsa.center.authorization.bo.impl;

import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.bo.CenterFunctionAuthorizationBO;
import cn.hsa.module.center.authorization.dao.CenterFunctionAuthorizationDAO;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 16:30
 */
@Slf4j
@Component
public class CenterFunctionAuthorizationBOImpl implements CenterFunctionAuthorizationBO {

    @Value("${rsa.public.key}")
    private String rsaPublicKey;

    private final String rsaprivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiZ5ah6YBBP5FWhgJXZnCSn9Z3y1kl0t6Q/x9QzNecAoiK94CMz28gEWSjPHoV+UJPNS5EhaHK3KrQt/bn5cY8QIDAQABAkARx46kpdLN5Vfqat6S5DGQ1GE1DzVGwq6aJ/269+EEkmlgLJGq0J2PFQEozWcfTZyvqDFZxaOc5fjGl7n9q4AdAiEA3qyShES5SNCSwJCG+0x+vPNY5YGKo95654te8A+Ef9sCIQCeNwEt09dvjaMHv+49LR6kLBGUjk8xzgYD28U70yY6IwIhAJIkKLTudbw4R1hignSDq9pOy9U0w8zwwzEb418ikA9pAiBc9MJLk6CLGTOFNR4bcWwEVyQJHUeoYnykPbZ3PMrD8wIgUHstQTolAIfvDSNJtt5f7XAZDDqI+HXh+cjR2Tj49B0=";


    private CenterFunctionAuthorizationDAO centerFunctionAuthorizationDAO;

    @Autowired
    public void setCenterFunctionAuthorizationDAO(CenterFunctionAuthorizationDAO centerFunctionAuthorizationDAO) {
        this.centerFunctionAuthorizationDAO = centerFunctionAuthorizationDAO;
    }

    @Override
    public WrapperResponse<CenterFunctionAuthorizationDO> queryBizAuthorizationByOrderTypeCode(String hospCode, String orderTypeCode) {
        CenterFunctionAuthorizationDO functionAuthorizationDO = centerFunctionAuthorizationDAO.queryBizAuthorizationByOrderTypeCode(hospCode,orderTypeCode);
        if(null == functionAuthorizationDO) {
            log.error("==-==权限单据类型不存在==-==hospCode：{},orderTypeCode：{}", hospCode, orderTypeCode);
            return WrapperResponse.error(HttpStatus.NOT_FOUND.value(), "权限单据类型不存在", null);
        }
        // 1.校验是否审核完成
        if(!Constants.SF.S.equals(functionAuthorizationDO.getAuditStatus())) {
            log.info("==-==Permissions not audit==-==hospCode：{},orderTypeCode：{}", hospCode, orderTypeCode);
            return WrapperResponse.error(HttpStatus.UNAUTHORIZED.value(), "权限未审核，请等待管理员审核完成后再调用", null);
        }
        // 2.校验时间是否被人为修改，医院编码与权限类型是否一致  医院编码:单据类型:时间戳 加密串解密
        String encryptStartDate = functionAuthorizationDO.getEncryptStartDate();
        String encryptEndDate = functionAuthorizationDO.getEncryptEndDate();
        String decryptStartTime = null;
        String decryptEndTime = null;
        try {
            decryptStartTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptStartDate.getBytes()), rsaprivateKey);
            decryptEndTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptEndDate.getBytes()), rsaprivateKey);
        } catch (Exception e) {
            log.error("解密功能授权使用时间出现异常 "+e);
            return WrapperResponse.error(HttpStatus.EXPECTATION_FAILED.value(), "解密功能授权使用时间出现异常,请勿人为手动修改有效期",null);
        }
        Long  startDateTimeStamp = Long.parseLong(decryptStartTime.substring(decryptStartTime.lastIndexOf(':')+1));
        Long  endDateTimeStamp = Long.parseLong(decryptEndTime.substring(decryptEndTime.lastIndexOf(':')+1));
        String decryptHospCode = decryptStartTime.substring(0,decryptStartTime.indexOf(':'));
        if(!(startDateTimeStamp.equals(functionAuthorizationDO.getStartDate().getTime())
                && endDateTimeStamp.equals(functionAuthorizationDO.getEndDate().getTime())
                && hospCode.equals(decryptHospCode))){
            log.info("==-==医院编码【 {} 】非法调用授权接口,解密后的开始时间：{},结束时间：{};数据库数据读取的开始时间：{},结束时间戳 ：{}",hospCode,startDateTimeStamp,endDateTimeStamp,
                    functionAuthorizationDO.getStartDate().getTime(),functionAuthorizationDO.getEndDate().getTime());
            return WrapperResponse.error(HttpStatus.NOT_FOUND.value(), "医院授权时间已被非法篡改,调用不合法,请联系管理员! ",null);
        }
        // 3.校验是否在有效期内调用
        Long nowTimeStamp = DateUtils.getTimestamp();
        if(!(nowTimeStamp.compareTo(startDateTimeStamp) > 0 && nowTimeStamp.compareTo(endDateTimeStamp) < 0)){
            StringBuilder builder = new StringBuilder();
            builder.append("医院编码为【 ").append(functionAuthorizationDO.getHospCode()).append('】')
                    .append("功能授权已过期或未到授权开始时间,请在授权使用时间范围内调用").append("权限类型代码：").append(orderTypeCode);
            log.info("==-=="+builder);
            return WrapperResponse.error(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(),builder.toString(),null);
        }
        return WrapperResponse.success(functionAuthorizationDO);
    }

    /**
     * 新增功能授权数据
     *
     * @param functionAuthorizationDO
     * @return
     */
    @Override
    public WrapperResponse<CenterFunctionAuthorizationDO> insertBizAuthorization(CenterFunctionAuthorizationDO functionAuthorizationDO) {
        int affectRows = centerFunctionAuthorizationDAO.insertAuthorization(functionAuthorizationDO);
        return WrapperResponse.success(functionAuthorizationDO);
    }
}
