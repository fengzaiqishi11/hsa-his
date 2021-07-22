package cn.hsa.module.outpt.lis.service;

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
 * @Package_name: cn.hsa.module.outpt.lis.service
 * @Class_name: LisService
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-04 10:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface LisService {

    @PostMapping("/service/outpt/lis/getDeptList")
    WrapperResponse<Boolean> getDeptList(Map map);

    @PostMapping("/service/outpt/lis/getDocList")
    WrapperResponse<Boolean> getDocList(Map map);

    @PostMapping("/service/outpt/lis/getItemList")
    WrapperResponse<Boolean> getItemList(Map map);

    @PostMapping("/service/outpt/lis/saveInspectApply")
    WrapperResponse<Boolean> saveInspectApply(Map map);

    @PostMapping("/service/outpt/lis/saveInspectResultActive")
    WrapperResponse<Boolean> saveInspectResultActive(Map map);

    @PostMapping("/service/outpt/lis/saveInspectResult")
    WrapperResponse<Boolean> saveInspectResult(Map map);

    @PostMapping("/service/outpt/lis/getPDFReport")
    WrapperResponse<Boolean> getPDFReport(Map map);

    @PostMapping("/service/outpt/lis/callbackStatus")
    WrapperResponse<Boolean> callbackStatus(Map map);

    @PostMapping("/service/outpt/lis/callbackCriticalValue")
    WrapperResponse<Boolean> callbackCriticalValue(Map map);

    @PostMapping("/service/outpt/lis/criticalValue")
    WrapperResponse<Boolean> criticalValue(Map map);






    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    @GetMapping("/service/outpt/lis/getMedicalDatas")
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
    @GetMapping("/service/outpt/lis/getTyeps")
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
    @GetMapping("/service/outpt/lis/getMedicalDataDetails")
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
    @GetMapping("/service/outpt/lis/getMedicalData")
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
    @GetMapping("/service/outpt/lis/getMedicalDataDetail")
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
    @PostMapping("/service/outpt/lis/insertMedicalData")
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
    @PostMapping("/service/outpt/lis/insertMedicalDataDetails")
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
    @PostMapping("/service/outpt/lis/updateMedicalData")
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
    @PostMapping("/service/outpt/lis/deleteMedicalData")
    WrapperResponse<Boolean> deleteMedicalData(Map map);

}