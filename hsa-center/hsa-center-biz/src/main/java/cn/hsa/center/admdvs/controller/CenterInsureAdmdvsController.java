package cn.hsa.center.admdvs.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.admdvs.service.CenterInsureAdmdvsService;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.util.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/center/centerInsureAdmdvs")
public class CenterInsureAdmdvsController extends CenterBaseController {

    @Resource
    private CenterInsureAdmdvsService centerInsureAdmdvsService;

    @GetMapping("/queryAdmdvsInfoPage")
    public WrapperResponse<PageDTO> queryAdmdvsInfoPage(InsureDictDTO insureDictDTO, HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", "100001");

        HttpSession session =  ServletUtils.getCurrentRequest().get().getSession();
        System.err.println(session.getAttribute("SESSION_USER_INFO"));
        insureDictDTO.setHospCode("100001");
        map.put("insureDictDTO", insureDictDTO);
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        return centerInsureAdmdvsService.queryAdmdvsInfoPage(map);
    }

    /**
         查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     * @author yuelong.chen
     * @return java.util.List
     **/
    @GetMapping("/queryAdmdvsInfo")
    public WrapperResponse<List<Map<String,Object>>> queryAdmdvsInfo(HttpServletResponse res){
        Map<String,String> map = new HashMap<>();
        map.put("hospCode","100001");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        return centerInsureAdmdvsService.queryAdmdvsInfo(map);
    }

    /**
     * @Method updateReadIdCard
     * @Desrciption 查询医保区划
     * @Param map
     * @Author liuhuiming
     * @Date   2022-08-11 10:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<>
     **/
    @GetMapping("/queryAdmdvs")
    public WrapperResponse<List<Map<String,Object>>> queryAdmdvs(@RequestParam Map<String,Object> map, HttpServletResponse res){
        map.put("hospCode", "100001");
        HttpSession session =  ServletUtils.getCurrentRequest().get().getSession();
        System.err.println(session.getAttribute("SESSION_USER_INFO"));
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        return centerInsureAdmdvsService.queryAdmdvs(map);
    }
}
