package cn.hsa.outpt.lis.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import cn.hsa.module.outpt.lis.bo.LisBO;
import cn.hsa.module.outpt.lis.service.LisService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.lis.service.impl
 * @Class_name: LisServiceImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-04 10:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/lis")
@Slf4j
@Service("lisService_provider")
public class LisServiceImpl implements LisService {

    @Resource
    LisBO lisBO;

    @Override
    public WrapperResponse<Boolean> getDeptList(Map map) {
        return WrapperResponse.success(lisBO.modifyDeptList(map));
    }

    @Override
    public WrapperResponse<Boolean> getDocList(Map map) {
        return WrapperResponse.success(lisBO.getDocList(map));
    }

    @Override
    public WrapperResponse<Boolean> getItemList(Map map) {
        return WrapperResponse.success(lisBO.getItemList(map));
    }

    @Override
    public WrapperResponse<Boolean> saveInspectApply(Map map) {
        return WrapperResponse.success(lisBO.saveInspectApply(map));
    }

    @Override
    public WrapperResponse<Boolean> saveInspectResultActive(Map map) {
        return WrapperResponse.success(lisBO.saveInspectResultActive(map));
    }

    @Override
    public WrapperResponse<Boolean> saveInspectResult(Map map) {
        return WrapperResponse.success(lisBO.saveInspectResult(map));
    }

    @Override
    public WrapperResponse<Boolean> getPDFReport(Map map) {
        return WrapperResponse.success(lisBO.getPDFReport(map));
    }

    @Override
    public WrapperResponse<Boolean> callbackStatus(Map map) {
        return WrapperResponse.success(lisBO.callbackStatus(map));
    }

    @Override
    public WrapperResponse<Boolean> callbackCriticalValue(Map map) {
        return WrapperResponse.success(lisBO.callbackCriticalValue(map));
    }

    @Override
    public WrapperResponse<Boolean> criticalValue(Map map) {
        return WrapperResponse.success(lisBO.criticalValue(map));
    }


    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置集合
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:49
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getMedicalDatas(Map map) {
        return WrapperResponse.success(lisBO.getMedicalDatas(MapUtils.get(map,"medicalDataDTO")));
    }

    /**
     * @Method: getTyeps
     * @Description: 获取类型
     * @Param: []
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/2/2 16:46
     * @Return: java.util.List<java.util.Map>
     **/
    @Override
    public WrapperResponse<List<Map>> getTyeps(Map map) {
        return WrapperResponse.success(lisBO.getTyeps());
    }

    /**
     * @Method: getMedicalDataDetails
     * @Description: 获取配置明细集合
     * @Param: [medicalDataDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getMedicalDataDetails(Map map) {
        return WrapperResponse.success(lisBO.getMedicalDataDetails(MapUtils.get(map,"medicalDataDetailDTO")));
    }

    /**
     * @Method: getMedicalData
     * @Description: 获取配置对象
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDTO>
     **/
    @Override
    public WrapperResponse<MedicalDataDTO> getMedicalData(Map map) {
        return WrapperResponse.success(lisBO.getMedicalData(MapUtils.get(map,"medicalDataDTO")));
    }

    /**
     * @Method: getMedicalData
     * @Description: 获取配置明细对象
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDTO>
     **/
    @Override
    public WrapperResponse<MedicalDataDetailDTO> getMedicalDataDetail(Map map) {
        return WrapperResponse.success(lisBO.getMedicalDataDetail(MapUtils.get(map,"medicalDataDetailDTO")));
    }

    /**
     * @Method: insertMedicalData
     * @Description: 新增配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:44
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDetailDTO>
     **/
    @Override
    public WrapperResponse<Boolean> insertMedicalData(Map map) {
        return WrapperResponse.success(lisBO.insertMedicalData(MapUtils.get(map,"medicalDataDTO")));
    }

    /**
     * @Method: insertMedicalDataDetails
     * @Description: 批量新增配置明细
     * @Param: [medicalDataDetailDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:47
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insertMedicalDataDetails(Map map) {
        return WrapperResponse.success(lisBO.insertMedicalDataDetails(MapUtils.get(map,"medicalDataDTO")));
    }

    /**
     * @Method: updateMedicalData
     * @Description: 更新配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:49
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateMedicalData(Map map) {
        return WrapperResponse.success(lisBO.updateMedicalData(MapUtils.get(map,"medicalDataDTO")));
    }

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:54
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteMedicalData(Map map) {
        return WrapperResponse.success(lisBO.deleteMedicalData(MapUtils.get(map,"medicalDataDTOList")));
    }
}