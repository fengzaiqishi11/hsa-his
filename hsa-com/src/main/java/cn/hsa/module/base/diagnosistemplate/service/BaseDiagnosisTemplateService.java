package cn.hsa.module.base.diagnosistemplate.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.diagnosistemplate.dto.BaseDiagnosisTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.diagnosistemplate.service
 * @Class_name: BaseDiagnosisTemplateService
 * @Describe: 诊断模板信息Service接口层（提供给dubbo调用）
 * @Author: pengbo
 * @Date: 2021/3/27 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseDiagnosisTemplateService {

    /**
     * @Menthod getById
     * @Desrciption 通过主键ID查询诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    WrapperResponse<BaseDiagnosisTemplateDTO> getById(Map<String,Object>  map);

    /**
     * @Menthod queryAll
     * @Desrciption 查询诊断模板
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    WrapperResponse<PageDTO> queryAll(Map<String,Object>  map);

    /**
     * @Menthod save
     * @Desrciption 保存诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    WrapperResponse<Boolean> save(Map<String,Object>  map);

    /**
     * @Menthod deleteById
     * @Desrciption 根据ID删除诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    WrapperResponse<Boolean> deleteById(Map<String,Object>  map);

    /**
     * @Menthod updateById
     * @Desrciption 根据ID 修改诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    WrapperResponse<Boolean> updateById(Map<String,Object> map);
}
