package cn.hsa.center.authorization.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.admdvs.service.CenterInsureAdmdvsService;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.util.ServletUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/center/centerFunctionAuthorization")
public class CenterInsureAuthorizationController {

    @Value("${rsa.public.key}")
    private String rsaPublicKey;

    private String rsaprivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiZ5ah6YBBP5FWhgJXZnCSn9Z3y1kl0t6Q/x9QzNecAoiK94CMz28gEWSjPHoV+UJPNS5EhaHK3KrQt/bn5cY8QIDAQABAkARx46kpdLN5Vfqat6S5DGQ1GE1DzVGwq6aJ/269+EEkmlgLJGq0J2PFQEozWcfTZyvqDFZxaOc5fjGl7n9q4AdAiEA3qyShES5SNCSwJCG+0x+vPNY5YGKo95654te8A+Ef9sCIQCeNwEt09dvjaMHv+49LR6kLBGUjk8xzgYD28U70yY6IwIhAJIkKLTudbw4R1hignSDq9pOy9U0w8zwwzEb418ikA9pAiBc9MJLk6CLGTOFNR4bcWwEVyQJHUeoYnykPbZ3PMrD8wIgUHstQTolAIfvDSNJtt5f7XAZDDqI+HXh+cjR2Tj49B0=";
    @Resource
    private CenterFunctionAuthorizationService centerFunctionAuthorizationService;

    @GetMapping("/queryAuthorizationInfo")
    public WrapperResponse<CenterFunctionAuthorizationDO> queryAdmdvsInfoPage(HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", "100001");
        map.put("orderTypeCode", "2");

        HttpSession session =  ServletUtils.getCurrentRequest().get().getSession();
        System.err.println(session.getAttribute("SESSION_USER_INFO"));
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        CenterFunctionAuthorizationDO authorizationDO = centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(map).getData();
        String str2EncryptStartDate = authorizationDO.getHospCode()+':'+authorizationDO.getOrderTypeCode()+':'+authorizationDO.getStartDate().getTime();
        String str2EncryptEndDate = authorizationDO.getHospCode()+':'+authorizationDO.getOrderTypeCode()+':'+authorizationDO.getEndDate().getTime();
        try {
            String encryptStartTime = RSAUtil.encryptByPublicKey(str2EncryptStartDate.getBytes(), rsaPublicKey);

            String decryptStartTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptStartTime.getBytes()), rsaprivateKey);

            String encryptEndTime = RSAUtil.encryptByPublicKey(str2EncryptEndDate.getBytes(), rsaPublicKey);

            String decryptEndTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptEndTime.getBytes()), rsaprivateKey);
            String decryptHospCode = decryptStartTime.substring(0,decryptStartTime.indexOf(':'));
            System.err.println("加密后开始时间:  "+encryptStartTime);
            System.err.println("解密后开始时间： "+ decryptStartTime);
            System.err.println("加密后结束时间:  "+encryptEndTime);
            System.err.println("解密后结束时间： "+ decryptEndTime);
            System.err.println("获取时间戳： "+ decryptEndTime.substring(decryptEndTime.lastIndexOf(':')+1));
            System.err.println("医院编码： "+ decryptHospCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(map);
    }


}
