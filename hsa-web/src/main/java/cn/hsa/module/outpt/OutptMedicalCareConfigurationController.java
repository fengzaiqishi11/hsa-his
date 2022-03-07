package cn.hsa.module.outpt;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.OutptMedicalCareConfigurationDTO;
import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;
import cn.hsa.module.outpt.medictocare.service.OutptMedicalCareConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author powersi
 * @create 2022-03-07 15:05
 * @desc
 **/
@RestController
@RequestMapping("/web/outpt/outptMedicalCareConfiguration")
@Slf4j
public class OutptMedicalCareConfigurationController {
    @Resource
    OutptMedicalCareConfigurationService outptMedicalCareConfigurationService_consumer;

    @PostMapping("/queryAllByLimit")
    public WrapperResponse<PageDTO> queryAllByLimit(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO) {
        return outptMedicalCareConfigurationService_consumer.queryAllByLimit(outptMedicalCareConfigurationDTO);
    }
    @PostMapping("/insertConfiguration")
    public WrapperResponse<Boolean> insertConfiguration(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO){
        return outptMedicalCareConfigurationService_consumer.insertConfiguration(outptMedicalCareConfigurationDTO);
    }
    @PostMapping("/deleteById")
    public WrapperResponse<Boolean> deleteById(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO){
        return outptMedicalCareConfigurationService_consumer.deleteById(outptMedicalCareConfigurationDTO);
    }
    @PostMapping("/updateConfiguration")
    public WrapperResponse<Boolean> updateConfiguration(@RequestBody OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDTO){
        return outptMedicalCareConfigurationService_consumer.updateConfiguration(outptMedicalCareConfigurationDTO);
    }

}