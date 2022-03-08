package cn.hsa.module.outpt.medictocare.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.medictocare.dto.OutptMedicalCareConfigurationDTO;
import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;

/**
 * @author powersi
 * @create 2022-03-07 14:32
 * @desc
 **/
public interface OutptMedicalCareConfigurationBO {
    /**
     * @author yuelong.chen
     * @create 2022-03-07 14:32
     * @desc分页查询匹配养老院数据
     **/
    OutptMedicalCareConfigurationDTO queryById(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);
    /**
     * @author yuelong.chen
     * @create 2022-03-07 14:32
     * @desc分页查询匹配养老院数据
     **/
    PageDTO queryAllByLimit(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);
    /**
     * @author yuelong.chen
     * @create 2022-03-07 14:32
     * @desc 新增数据
     **/
    Boolean insertConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);
    /**
     * @author yuelong.chen
     * @create 2022-03-07 14:32
     * @desc 删除数据
     **/
    Boolean deleteById(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);
    /**
     * @author yuelong.chen
     * @create 2022-03-07 14:32
     * @desc 修改数据
     **/
    Boolean updateConfiguration(OutptMedicalCareConfigurationDTO outptMedicalCareConfigurationDO);
}