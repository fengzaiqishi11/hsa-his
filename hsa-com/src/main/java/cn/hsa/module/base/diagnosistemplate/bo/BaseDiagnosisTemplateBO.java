package cn.hsa.module.base.diagnosistemplate.bo;

import cn.hsa.module.base.diagnosistemplate.dto.BaseDiagnosisTemplateDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.diagnosistemplate.bo
 * @Class_name: BaseDiagnosisTemplateBO
 * @Describe: 诊断模板业务逻辑实现层接口
 * @Author: pengbo
 * @Date: 2021/3/27 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseDiagnosisTemplateBO {
    /**
     * @Menthod getById
     * @Desrciption 通过主键ID查询诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    BaseDiagnosisTemplateDTO getById(Map<String,Object> map);

    /**
     * @Menthod queryAll
     * @Desrciption 查询诊断模板
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    List<BaseDiagnosisTemplateDTO> queryAll(BaseDiagnosisTemplateDTO  baseDiagnosisTemplateDTO);

    /**
     * @Menthod save
     * @Desrciption 保存诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    Boolean save(Map<String,Object>  map);

    /**
     * @Menthod deleteById
     * @Desrciption 根据ID删除诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    Boolean deleteById(Map<String,Object>  map);

    /**
     * @Menthod updateById
     * @Desrciption 根据ID 修改诊断模板信息
     * @param map 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    Boolean updateById(Map<String,Object> map);
}
