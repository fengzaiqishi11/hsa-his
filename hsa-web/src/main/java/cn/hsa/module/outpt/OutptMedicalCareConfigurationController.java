package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.OutptMedicalCareConfigurationDTO;
import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;
import cn.hsa.module.outpt.medictocare.service.OutptMedicalCareConfigurationService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author powersi
 * @create 2022-03-07 15:05
 * @desc
 **/
@RestController
@RequestMapping("/web/outpt/outptMedicalCareConfiguration")
@Slf4j
public class OutptMedicalCareConfigurationController extends BaseController {
    @Resource
    OutptMedicalCareConfigurationService outptMedicalCareConfigurationService_consumer;

    @PostMapping("/queryAllByLimit")
    public WrapperResponse<PageDTO> queryAllByLimit(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res);
        Map<String, Object> map  = new HashMap<>();
        map.put("outptMedicalCareConfigurationDTO",outptMedicalCareConfigurationDTO);
        map.put("hospCode",userDTO.getHospCode());
        return outptMedicalCareConfigurationService_consumer.queryAllByLimit(map);
    }
    @PostMapping("/insertConfiguration")
    public WrapperResponse<Boolean> insertConfiguration(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res);
        Map<String, Object> map  = new HashMap<>();
        outptMedicalCareConfigurationDTO.setHospCode(userDTO.getHospCode());
        outptMedicalCareConfigurationDTO.setHospName(userDTO.getHospName());
        outptMedicalCareConfigurationDTO.setId(SnowflakeUtils.getId());
        outptMedicalCareConfigurationDTO.setCrteId(userDTO.getId());
        outptMedicalCareConfigurationDTO.setCrteName(userDTO.getName());
        outptMedicalCareConfigurationDTO.setCrteTime(new Date());
        map.put("outptMedicalCareConfigurationDTO",outptMedicalCareConfigurationDTO);
        map.put("hospCode",userDTO.getHospCode());
        return outptMedicalCareConfigurationService_consumer.insertConfiguration(map);
    }
    @PostMapping("/deleteById")
    public WrapperResponse<Boolean> deleteById(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res);
        Map<String, Object> map  = new HashMap<>();
        map.put("outptMedicalCareConfigurationDTO",outptMedicalCareConfigurationDTO);
        map.put("hospCode",userDTO.getHospCode());
        return outptMedicalCareConfigurationService_consumer.deleteById(map);
    }
    @PostMapping("/updateConfiguration")
    public WrapperResponse<Boolean> updateConfiguration(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res);
        Map<String, Object> map  = new HashMap<>();
        map.put("outptMedicalCareConfigurationDTO",outptMedicalCareConfigurationDTO);
        map.put("hospCode",userDTO.getHospCode());
        return outptMedicalCareConfigurationService_consumer.updateConfiguration(map);
    }

}