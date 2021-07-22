package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.compositequery.service.CompositeQueryService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: CompositeQueryController
 * @Describe: 综合查询控制器
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/11 16:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@Slf4j
@RequestMapping("/web/inpt/compositeQuery")
public class CompositeQueryController extends BaseController {

    @Resource
    private CompositeQueryService compositeQueryService_consumer;

    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<PageDTO> queryAll(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return compositeQueryService_consumer.queryAll(map);
    }

    /**
     * @Method queryInptVisit
     * @Desrciption 查询患者基本信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<InptVisitDTO>
     **/
    @GetMapping("/queryInptVisit")
    public WrapperResponse<InptVisitDTO> queryInptVisit(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return compositeQueryService_consumer.queryInptVisit(map);
    }

    /**
     * @Method queryAdvance
     * @Desrciption 查询患者预交金信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @GetMapping("/queryAdvance")
    public WrapperResponse<List<Map<String, Object>>> queryAdvance(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return compositeQueryService_consumer.queryAdvance(map);
    }

    /**
     * @Method queryDisease
     * @Desrciption 查询患者诊断信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/14 16:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @GetMapping("/queryDisease")
    public WrapperResponse<List<Map<String, Object>>> queryDisease(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return compositeQueryService_consumer.queryDisease(map);
    }

    /**
    * @Method queryVisitsByCondition
    * @Param [inptVisitDTO]
    * @description   条件查询住院病人
    * @author marong
    * @date 2020/9/22 18:57
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
    * @throws
    */
    @GetMapping("/queryVisitsByCondition")
    public WrapperResponse<List<InptVisitDTO>> queryVisitsByCondition(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return compositeQueryService_consumer.queryVisitsByCondition(map);
    }

}
