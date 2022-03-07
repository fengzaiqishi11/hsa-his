package cn.hsa.outpt.medictocare.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.bo.OutptMedicalCareConfigurationBO;
import cn.hsa.module.outpt.medictocare.dto.OutptMedicalCareConfigurationDTO;
import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;
import cn.hsa.module.outpt.medictocare.service.OutptMedicalCareConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author powersi
 * @create 2022-03-07 14:50
 * @desc
 **/
@HsafRestPath("/service/outpt/outptMedicalCareConfiguration")
@Slf4j
@Service("outptMedicalCareConfigurationService_provider")
public class OutptMedicalCareConfigurationServiceImpl implements OutptMedicalCareConfigurationService {

    @Resource
    private OutptMedicalCareConfigurationBO outptMedicalCareConfigurationBO;
    @Override
    public WrapperResponse<PageDTO> queryAllByLimit(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return WrapperResponse.success(outptMedicalCareConfigurationBO.queryAllByLimit(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<Boolean> insertConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return WrapperResponse.success(outptMedicalCareConfigurationBO.insertConfiguration(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<Boolean> deleteById(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return WrapperResponse.success(outptMedicalCareConfigurationBO.deleteById(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<Boolean> updateConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return WrapperResponse.success(outptMedicalCareConfigurationBO.updateConfiguration(outptMedicalCareConfigurationDO));
    }
}