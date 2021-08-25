package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.triage.service.OutptTriageVisitService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name:: OutptExecutionCardPrintController
 * @Description: 门诊病人分诊
 * @Author: zhangxuan
 * @Date: 2020-08-17 17:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/outptTriageVisit")
@Slf4j
public class OutptTriageVisitController extends BaseController {

    @Resource
    private OutptTriageVisitService outptTriageVisitService_consumer;
    /**
     * @Method
     * @Desrciption  分页查询分诊病人信息
     * @Param
     * map
     * @Author Pengbo
     * @Date   2021-06-24 17:14
     * @Return map
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptTriageVisitDTO outptTriageVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptTriageVisitDTO.setHospCode(userDTO.getHospCode());
        outptTriageVisitDTO.setTriageId(userDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptTriageVisitDTO",outptTriageVisitDTO);
        return outptTriageVisitService_consumer.queryPage(map);
    }


    /**
     * @Method
     * @Desrciption  修改分诊病人信息
     * @Param
     * map
     * @Author Pengbo
     * @Date   2021-06-24 17:14
     * @Return map
     **/
    @PostMapping("/updateOutptTriageVisit")
    public WrapperResponse<Boolean> updateOutptTriageVisit(@RequestBody OutptTriageVisitDTO outptTriageVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptTriageVisitDTO.setHospCode(userDTO.getHospCode());
        outptTriageVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptTriageVisitDTO",outptTriageVisitDTO);
        return outptTriageVisitService_consumer.updateOutptTriageVisitById(map);
    }


    /**
     * @Method
     * @Desrciption  修改分诊病人信息
     * @Param
     * map
     * @Author Pengbo
     * @Date   2021-06-24 17:14
     * @Return map
     **/
    @PostMapping("/getDoctorByClinicIdAndQueueDate")
    public WrapperResponse<List<SysUserDTO>> getDoctorByClinicIdAndQueueDate(@RequestBody OutptTriageVisitDTO outptTriageVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptTriageVisitDTO.setHospCode(userDTO.getHospCode());
        outptTriageVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId());
        if(outptTriageVisitDTO.getQueueDate() == null) {
            outptTriageVisitDTO.setQueueDate(DateUtils.getNow());
        }
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptTriageVisitDTO",outptTriageVisitDTO);
        return outptTriageVisitService_consumer.getDoctorByClinicIdAndQueueDate(map);
    }
}
