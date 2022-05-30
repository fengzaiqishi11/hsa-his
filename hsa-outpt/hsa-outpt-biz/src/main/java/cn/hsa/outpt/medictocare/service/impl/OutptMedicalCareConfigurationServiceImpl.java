package cn.hsa.outpt.medictocare.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.bo.OutptMedicalCareConfigurationBO;
import cn.hsa.module.outpt.medictocare.dto.OutptMedicalCareConfigurationDTO;

import cn.hsa.module.outpt.medictocare.service.OutptMedicalCareConfigurationService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public WrapperResponse<OutptMedicalCareConfigurationDTO> queryById(Map map) {
        OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO = MapUtils.get(map,"outptMedicalCareConfigurationDTO");
        return WrapperResponse.success(outptMedicalCareConfigurationBO.queryById(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<PageDTO> queryAllByLimit(Map map) {
        OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO = MapUtils.get(map,"outptMedicalCareConfigurationDTO");
        return WrapperResponse.success(outptMedicalCareConfigurationBO.queryAllByLimit(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<Boolean> insertConfiguration(Map map) {
        OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO = MapUtils.get(map,"outptMedicalCareConfigurationDTO");
        return WrapperResponse.success(outptMedicalCareConfigurationBO.insertConfiguration(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<Boolean> deleteById(Map<String, Object> map) {
        OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO = MapUtils.get(map,"outptMedicalCareConfigurationDTO");
        return WrapperResponse.success(outptMedicalCareConfigurationBO.deleteById(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<Boolean> updateConfiguration(Map<String, Object> map) {
        OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO = MapUtils.get(map,"outptMedicalCareConfigurationDTO");
        return WrapperResponse.success(outptMedicalCareConfigurationBO.updateConfiguration(outptMedicalCareConfigurationDO));
    }

    @Override
    public WrapperResponse<List<OutptMedicalCareConfigurationDTO>> queryConfigation(Map<String, Object> map) {
        OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO = MapUtils.get(map,"outptMedicalCareConfigurationDTO");
        return WrapperResponse.success(outptMedicalCareConfigurationBO.queryConfigation(outptMedicalCareConfigurationDO));
    }


}