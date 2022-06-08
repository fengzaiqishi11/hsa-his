package cn.hsa.center.authorization.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.admdvs.service.CenterInsureAdmdvsService;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.util.ServletUtils;
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

    @Resource
    private CenterFunctionAuthorizationService centerFunctionAuthorizationService;

    @GetMapping("/queryAuthorizationInfo")
    public WrapperResponse<CenterFunctionAuthorizationDO> queryAdmdvsInfoPage(HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", "100001");
        map.put("orderTypeCode", "1");

        HttpSession session =  ServletUtils.getCurrentRequest().get().getSession();
        System.err.println(session.getAttribute("SESSION_USER_INFO"));
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        return centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(map);
    }


}
