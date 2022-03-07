package cn.hsa.module.outpt.medictocare.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.OutptMedicalCareConfigurationDTO;
import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @author powersi
 * @create 2022-03-07 14:32
 * @desc
 **/
@FeignClient(value = "hsa-outpt")
public interface OutptMedicalCareConfigurationService {
    /**
     * @author yuelong.chen
     * @create 2022-03-07 14:32
     * @desc分页查询匹配养老院数据
     **/
    WrapperResponse<PageDTO> queryAllByLimit(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);

    WrapperResponse<Boolean> insertConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);

    WrapperResponse<Boolean> deleteById(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);

    WrapperResponse<Boolean> updateConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);
}