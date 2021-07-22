package cn.hsa.module.outpt.outptmedicaltemplate.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 门诊病历模板表(OutptMedicalTemplate)表服务接口
 *
 * @author makejava
 * @since 2021-03-09 14:13:05
 */
public interface OutptMedicalTemplateService {

    /**
    * @Menthod getById
    * @Desrciption 通过id查询
    *
    * @Param
    * [id]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 14:25
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
    **/
    @RequestMapping("/service/outpt/outptmedicaltemplate/getById")
    WrapperResponse<OutptMedicalTemplateDTO> getById(Map map);

    /**
    * @Menthod queryMedicalTemplatePage
    * @Desrciption 分页查询
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 14:25
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("/service/outpt/outptmedicaltemplate/queryMedicalTemplatePage")
    WrapperResponse<PageDTO> queryMedicalTemplatePage(Map map);

    /**
    * @Menthod insert
    * @Desrciption 新增门诊电子病历模板
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 14:27
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/outpt/outptmedicaltemplate/insertMedicalTemplate")
    WrapperResponse<Boolean> insertMedicalTemplate(Map map);

    /**
     * @Menthod insert
     * @Desrciption 编辑门诊电子病历模板
     *
     * @Param
     * [map]
     *
     * @Author jiahong.yang
     * @Date   2021/3/9 14:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/outpt/outptmedicaltemplate/updatetMedicalTemplate")
    WrapperResponse<Boolean> updateMedicalTemplate(Map map);

    /**
    * @Menthod update
    * @Desrciption 修改门诊电子病历模板
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 14:27
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/outpt/outptmedicaltemplate/update")
    WrapperResponse<Boolean> updateStatus(Map map);

    @PostMapping("/service/outpt/outptmedicaltemplate/deleteById")
    WrapperResponse<Boolean> deleteById(Map map);

}
