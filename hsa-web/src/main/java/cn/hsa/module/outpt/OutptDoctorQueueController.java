package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.queue.service.OutptDoctorQueueService;
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
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptDoctorQueueController
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/13 8:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/doctorQueue")
@Slf4j
public class OutptDoctorQueueController extends BaseController {

    @Resource
    OutptDoctorQueueService outptDoctorQueueService_consumer;

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage( OutptDoctorQueueDto outptDoctorQueueDto,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptDoctorQueueDto.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptDoctorQueueDto",outptDoctorQueueDto);
        return outptDoctorQueueService_consumer.queryPage(map);
    }

    @GetMapping("/queryAll")
    public WrapperResponse<List<OutptDoctorQueueDto>> queryAll(OutptDoctorQueueDto outptDoctorQueueDto,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
       outptDoctorQueueDto.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptDoctorQueueDto",outptDoctorQueueDto);
        return outptDoctorQueueService_consumer.queryAll(map);
    }
}