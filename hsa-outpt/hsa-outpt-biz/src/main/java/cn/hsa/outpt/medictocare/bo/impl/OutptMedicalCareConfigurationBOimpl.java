package cn.hsa.outpt.medictocare.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.medictocare.bo.OutptMedicalCareConfigurationBO;
import cn.hsa.module.outpt.medictocare.dao.OutptMedicalCareConfigurationDAO;
import cn.hsa.module.outpt.medictocare.dto.OutptMedicalCareConfigurationDTO;
import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author powersi
 * @create 2022-03-07 14:50
 * @desc
 **/
@Component
@Slf4j
public class OutptMedicalCareConfigurationBOimpl implements OutptMedicalCareConfigurationBO {

    @Resource
    private OutptMedicalCareConfigurationDAO outptMedicalCareConfigurationDAO;

    @Override
    public OutptMedicalCareConfigurationDTO queryById(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return outptMedicalCareConfigurationDAO.queryById(outptMedicalCareConfigurationDO);
    }

    @Override
    public PageDTO queryAllByLimit(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        PageHelper.startPage(outptMedicalCareConfigurationDO.getPageNo(),outptMedicalCareConfigurationDO.getPageSize());
        return PageDTO.of(outptMedicalCareConfigurationDAO.queryAllByLimit(outptMedicalCareConfigurationDO));
    }

    @Override
    public Boolean insertConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return outptMedicalCareConfigurationDAO.insertConfiguration(outptMedicalCareConfigurationDO)>0;
    }

    @Override
    public Boolean deleteById(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return outptMedicalCareConfigurationDAO.deleteById(outptMedicalCareConfigurationDO)>0;
    }

    @Override
    public Boolean updateConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO) {
        return outptMedicalCareConfigurationDAO.updateConfiguration(outptMedicalCareConfigurationDO)>0;
    }

    @Override
    public List<OutptMedicalCareConfigurationDTO> queryConfigation(OutptMedicalCareConfigurationDTO outptMedicalCareConfiguration) {
        return outptMedicalCareConfigurationDAO.queryAllByLimit(outptMedicalCareConfiguration);
    }
}