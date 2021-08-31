package cn.hsa.module.inpt;


import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.service.LeaveHospitalRecallService;
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
 *@Class_name: LeaveHospitalRecallController
 *@Describe: 出院召回
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 15:52
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/leaveHospitalRecall")
@Slf4j
public class LeaveHospitalRecallController extends BaseController {

    @Resource
    LeaveHospitalRecallService leaveHospitalRecallService_consumer;

    /**
     * @Method queryLeaveHospitalRecallPage
     * @Desrciption 出院召回分页查询
     * @param inptVisitDTO
     * @Author liuqi1
     * @Date   2020/9/16 11:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/queryLeaveHospitalRecallPage")
    public WrapperResponse<PageDTO> queryLeaveHospitalRecallPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO",inptVisitDTO);

        WrapperResponse<PageDTO> response = leaveHospitalRecallService_consumer.queryLeaveHospitalRecallPage(map);
        return response;
    }
}
