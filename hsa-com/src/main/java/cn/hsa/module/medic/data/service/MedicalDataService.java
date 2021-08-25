package cn.hsa.module.medic.data.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
@FeignClient(value = "hsa-medical")
public interface MedicalDataService {

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    @GetMapping("/service/medical/data/getMedicalDatas")
    WrapperResponse<PageDTO> getMedicalDatas(Map map);

    /**
     * @Method: getTyeps
     * @Description: 获取类型
     * @Param: []
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/2/2 16:46
     * @Return: java.util.List<java.util.Map>
     **/
    @GetMapping("/service/medical/data/getTyeps")
    WrapperResponse<List<Map>> getTyeps(Map map);

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置明细列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    @GetMapping("/service/medical/data/getMedicalDataDetails")
    WrapperResponse<PageDTO> getMedicalDataDetails(Map map);

    /**
     * @Method: getMedicalData
     * @Description: 根据参数获取配置对象
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:20
     * @Return:
     **/
    @GetMapping("/service/medical/data/getMedicalData")
    WrapperResponse<MedicalDataDTO> getMedicalData(Map map);

    /**
     * @Method: getMedicalData
     * @Description: 根据参数获取配置对象
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:20
     * @Return:
     **/
    @GetMapping("/service/medical/data/getMedicalDataDetail")
    WrapperResponse<MedicalDataDetailDTO> getMedicalDataDetail(Map map);

    /**
     * @Method: insertMedicalData
     * @Description: 新增配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:14
     * @Return:
     **/
    @PostMapping("/service/medical/data/insertMedicalData")
    WrapperResponse<Boolean> insertMedicalData(Map map);

    /**
     * @Method: insertMedicalDataDetails
     * @Description: 批量新增配置明细
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    @PostMapping("/service/medical/data/insertMedicalDataDetails")
    WrapperResponse<Boolean> insertMedicalDataDetails(Map map);

    /**
     * @Method: updateMedicalData
     * @Description: 修改配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:16
     * @Return:
     **/
    @PostMapping("/service/medical/data/updateMedicalData")
    WrapperResponse<Boolean> updateMedicalData(Map map);

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    @PostMapping("/service/medical/data/deleteMedicalData")
    WrapperResponse<Boolean> deleteMedicalData(Map map);

}
