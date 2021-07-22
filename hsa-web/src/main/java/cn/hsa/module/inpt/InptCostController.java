package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.service.InptCostService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.inpt
 * @Class_name: InptCostController
 * @Description: 住院费用控制层
 * @Author: 马荣
 * @Date: 　2020/09/19　　14点24分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/inptCostController")
@Slf4j
public class InptCostController extends BaseController {

    @Resource
    private InptCostService inptCostService_consumer;

    /**
    * @Method insertInptCost
    * @Param [inptCostDTOList]
    * @description   增加 住院费用
    * @author marong
    * @date 2020/9/21 14:23
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @RequestMapping("insertInptCost")
    public WrapperResponse<Boolean> insertInptCost(@RequestBody List<InptCostDTO> inptCostDTOList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptCostDTOList",inptCostDTOList);
        for(InptCostDTO inptCostDTO:inptCostDTOList){
            inptCostDTO.setHospCode(sysUserDTO.getHospCode());
        }
        WrapperResponse<Boolean> insertInptCostResult= inptCostService_consumer.insertInptCost(map);;
        return insertInptCostResult;
    }

    /**
    * @Method updateInptCost
    * @Param [inptCostDTOList]
    * @description   住院费用修改
    * @author marong
    * @date 2020/9/21 14:23
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @RequestMapping()
    public WrapperResponse<Boolean> updateInptCost(@RequestBody List<InptCostDTO> inptCostDTOList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptCostDTOList",inptCostDTOList);
        for(InptCostDTO inptCostDTO:inptCostDTOList){
            inptCostDTO.setHospCode(sysUserDTO.getHospCode());
        }
        WrapperResponse<Boolean> updateInptCostResult= inptCostService_consumer.updateInptCostList(map);;
        return updateInptCostResult;
    }
}
