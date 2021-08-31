package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.nurse.service.BackCostByInputService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt
 *@Class_name: InptBackCostController
 *@Describe: 住院退费
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 10:38
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/nurse")
@Slf4j
public class BackCostByInptController extends BaseController {

    @Resource
    BackCostByInputService backCostByInputService_consumer;

    /**
    * @Method queryBackCostInfoPage
    * @Desrciption 住院退费费用查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 14:34
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryBackCostInfoPage")
    WrapperResponse<PageDTO> queryBackCostInfoPage(InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptCostDTO.setHospCode(sysUserDTO.getHospCode());
        inptCostDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptCostDTO", inptCostDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = backCostByInputService_consumer.queryBackCostInfoPage(map);
        return pageDTOWrapperResponse;
    }

    @GetMapping("/querySurgeryBackCostInfoPage")
    WrapperResponse<PageDTO> querySurgeryBackCostInfoPage(InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptCostDTO.setHospCode(sysUserDTO.getHospCode());
        inptCostDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        inptCostDTO.setSourceCode(Constants.FYLYFS.BJZ);
        map.put("inptCostDTO", inptCostDTO);
        map.put("id",inptCostDTO.getId());
        map.put("ids",inptCostDTO.getIds());
        map.put("visitId",inptCostDTO.getVisitId());
        map.put("statusCode",inptCostDTO.getStatusCode());
        map.put("sourceCode",inptCostDTO.getSourceCode());
        map.put("sourceDeptId",inptCostDTO.getSourceDeptId());
        map.put("queryStartDate",inptCostDTO.getQueryStartDate());
        map.put("itemCode",inptCostDTO.getItemCode());
        map.put("itemName",inptCostDTO.getItemName());
        WrapperResponse<PageDTO> pageDTOWrapperResponse = backCostByInputService_consumer.querySurgeryBackCostInfoPage(map);

        return pageDTOWrapperResponse;
    }

    /**
    * @Method saveBackCostWithInpt
    * @Desrciption 住院退费保存
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/9/10 14:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping("/saveBackCostWithInpt")
    @NoRepeatSubmit
    WrapperResponse<Boolean> saveBackCostWithInpt(@RequestBody List<InptCostDTO> inptCostDTOs, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("userId", sysUserDTO.getId());
        map.put("userName", sysUserDTO.getName());
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptCostDTOs", inptCostDTOs);

        WrapperResponse<Boolean> booleanWrapperResponse = backCostByInputService_consumer.saveBackCostWithInpt(map);
        return booleanWrapperResponse;
    }

}
