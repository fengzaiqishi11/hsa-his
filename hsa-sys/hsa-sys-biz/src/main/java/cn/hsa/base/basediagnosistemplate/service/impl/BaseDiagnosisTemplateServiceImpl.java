package cn.hsa.base.basediagnosistemplate.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.diagnosistemplate.bo.BaseDiagnosisTemplateBO;
import cn.hsa.module.base.diagnosistemplate.dto.BaseDiagnosisTemplateDTO;
import cn.hsa.module.base.diagnosistemplate.service.BaseDiagnosisTemplateService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.inventory.service.impl
 * @Class_name: BaseDiagnosisTemplateServiceImpl
 * @Describe: 医嘱信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/baseDiagnosisTemplate")
@Slf4j
@Service("baseDiagnosisTemplateService_provider")
public class BaseDiagnosisTemplateServiceImpl extends HsafService implements BaseDiagnosisTemplateService {

    @Resource
    BaseDiagnosisTemplateBO baseDiagnosisTemplateBO;


    /**
     * @param map 诊断模板信息
     * @Menthod getById
     * @Desrciption 通过主键ID查询诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public WrapperResponse<BaseDiagnosisTemplateDTO> getById(Map<String, Object> map) {
        return WrapperResponse.success(baseDiagnosisTemplateBO.getById(map));
    }

    /**
     * @param map 诊断模板信息
     * @Menthod queryAll
     * @Desrciption 查询诊断模板
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryAll(Map<String, Object> map) {

        BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO = (BaseDiagnosisTemplateDTO) map.get("baseDiagnosisTemplateDTO");
        // 设置分页信息
        PageHelper.startPage(baseDiagnosisTemplateDTO.getPageNo(), baseDiagnosisTemplateDTO.getPageSize());
        List<BaseDiagnosisTemplateDTO>  list =baseDiagnosisTemplateBO.queryAll(baseDiagnosisTemplateDTO);
        return WrapperResponse.success(PageDTO.of(list));
    }

    /**
     * @param map 诊断模板信息
     * @Menthod save
     * @Desrciption 保存诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map<String, Object> map) {
        return WrapperResponse.success(baseDiagnosisTemplateBO.save(map));
    }

    /**
     * @param map 诊断模板信息
     * @Menthod deleteById
     * @Desrciption 根据ID删除诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public WrapperResponse<Boolean> deleteById(Map<String, Object> map) {
        return WrapperResponse.success(baseDiagnosisTemplateBO.deleteById(map));
    }

    /**
     * @param map 诊断模板信息
     * @Menthod updateById
     * @Desrciption 根据ID 修改诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public WrapperResponse<Boolean> updateById(Map<String, Object> map) {
        return WrapperResponse.success(baseDiagnosisTemplateBO.updateById(map));
    }
}
