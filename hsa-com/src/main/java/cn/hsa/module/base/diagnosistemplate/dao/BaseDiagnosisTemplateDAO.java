package cn.hsa.module.base.diagnosistemplate.dao;

import cn.hsa.module.base.diagnosistemplate.dto.BaseDiagnosisTemplateDTO;

import java.util.List;

public interface BaseDiagnosisTemplateDAO {
    /**
     * @Menthod getById
     * @Desrciption 通过主键ID查询诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    BaseDiagnosisTemplateDTO getById(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询诊断模板
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    List<BaseDiagnosisTemplateDTO> queryAll(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO);

    /**
     * @Menthod save
     * @Desrciption 保存诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    Boolean save(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据ID删除诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    Boolean deleteById(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO);

    /**
     * @Menthod updateById
     * @Desrciption 根据ID 修改诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    Boolean updateById(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO);

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 审核/作废诊断管理
     * @Param: baseDiagnosisTemplateDTO
     *  审核：checkFlag = 1，作废：checkFlag = 2
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-12 13:49
     * @Return: Boolean
     **/
    int updateStatusCode(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO);
}
