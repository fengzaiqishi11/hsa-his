package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.bedlist.service.BedListService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: BedListController
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/8 14:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/bedList")
@Slf4j
public class BedListController extends BaseController {

    @Resource
    BedListService bedListService_consumer;

    /**
     * @Method queryPatientInfoPage
     * @Desrciption 分页查询床位列表信息
     * @params [inptVisitDTO]
     * @Author chenjun
     * @Date 2020/9/8 14:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping(value = "/queryPage")
    public WrapperResponse<Map<String, Object>> queryPage(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        inptVisitDTO.setCrteId(sysUserDTO.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return bedListService_consumer.queryPage(map);
    }

    /**
     * @Method 床位变动接口
     * @Description
     * 1、安床 = '0'
     * 2、换床 = '1'
     * 3、包床 = '2'
     * 4、转科 = '3'
     * 5、包床取消 = '4'
     * 6、预出院 = '5'
     * 7、出院召回/召回费用 = '6'
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 11:03
     * @Return
     **/
    @PostMapping(value = "/saveBedChange")
    public WrapperResponse<Boolean> saveBedChange(@RequestBody Map map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("userId", sysUserDTO.getId());
        map.put("userName", sysUserDTO.getName());
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("deptName", sysUserDTO.getLoginBaseDeptDTO().getName());
        return bedListService_consumer.saveBedChange(map);
    }

    /**
     * @Method 根据病区查询科室信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 9:43
     * @Return
     **/
    @GetMapping(value = "/queryDeptByWardId")
    public WrapperResponse<Map<String, Object>> queryDeptByWardId(@RequestParam(name = "wardId", required = true) String wardId, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("wardId", wardId);
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        return bedListService_consumer.queryDeptByWardId(map);
    }
}