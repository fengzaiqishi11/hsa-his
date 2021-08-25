package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.nurse.service.BackCostSureByInptService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt
 *@Class_name: BackCostSureByInptController
 *@Describe: 住院退费确认
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 16:17
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/nurse")
@Slf4j
public class BackCostSureByInptController extends BaseController {

    @Resource
    BackCostSureByInptService backCostSureWithInptService_consumer;

    /**
    * @Method queryBackCostSurePage
    * @Desrciption 退费确认分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 16:24
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/queryBackCostSurePage")
    WrapperResponse<PageDTO> queryBackCostSurePage(InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptCostDTO.setHospCode(sysUserDTO.getHospCode());
        inptCostDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptCostDTO", inptCostDTO);

        WrapperResponse<PageDTO> wrapperResponse = backCostSureWithInptService_consumer.queryBackCostSurePage(map);
        return wrapperResponse;
    }

    /**
       *  查询手术补记账记录
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/31 20:10
    **/
    @GetMapping("/queryOutpatientSurgeryCostPage")
    WrapperResponse<PageDTO> queryOutpatientSurgeryCostPage(InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptCostDTO.setHospCode(sysUserDTO.getHospCode());
        inptCostDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptCostDTO", inptCostDTO);

        WrapperResponse<PageDTO> wrapperResponse = backCostSureWithInptService_consumer.queryOutpatientSurgeryCostPage(map);
        return wrapperResponse;
    }


    /**
    * @Method updateBackCostSure
    * @Desrciption 退费确认
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 16:25
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateBackCostSure")
    @NoRepeatSubmit
    WrapperResponse<Boolean> updateBackCostSure(@RequestBody InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptCostDTO.setHospCode(sysUserDTO.getHospCode());
        inptCostDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        inptCostDTO.setOkId(sysUserDTO.getId());
        inptCostDTO.setOkName(sysUserDTO.getName());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptCostDTO", inptCostDTO);

        WrapperResponse<Boolean> booleanWrapperResponse = backCostSureWithInptService_consumer.updateBackCostSure(map);
        return booleanWrapperResponse;
    }
}
