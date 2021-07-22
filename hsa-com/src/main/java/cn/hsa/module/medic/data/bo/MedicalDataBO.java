package cn.hsa.module.medic.data.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name
 * @class_nameMedicalApplyDAO
 * @Description 医技BO
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:30
 * @Company 创智和宇
 **/
public interface MedicalDataBO {

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    PageDTO getMedicalDatas(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置明细列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    PageDTO getMedicalDataDetails(MedicalDataDetailDTO medicalDataDetailDTO);

    /**
     * @Method: getMedicalData
     * @Description: 根据参数获取配置对象
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:20
     * @Return:
     **/
    MedicalDataDTO getMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: getMedicalData
     * @Description: 根据参数获取配置对象
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:20
     * @Return:
     **/
    MedicalDataDetailDTO getMedicalDataDetail(MedicalDataDetailDTO medicalDataDetailDTO);

    /**
     * @Method: insertMedicalData
     * @Description: 新增配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:14
     * @Return:
     **/
    Boolean insertMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: insertMedicalDataDetails
     * @Description: 批量新增配置明细
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    Boolean insertMedicalDataDetails(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: updateMedicalData
     * @Description: 修改配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:16
     * @Return:
     **/
    Boolean updateMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    Boolean deleteMedicalData(List<MedicalDataDTO> medicalDataDTOList);

    List<Map> getTyeps();
}
