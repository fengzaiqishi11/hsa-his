package cn.hsa.module.emr.emrclassifytemplate.service;


import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifytemplate.service
 * @Class_name:: EmrClassifyTemplateService
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/9/28 14:54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-emr")
public interface EmrClassifyTemplateService {

    /**
     * @Method getById
     * @Desrciption 通过模板ID查询模板
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/21 11:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
     **/
    WrapperResponse<EmrClassifyTemplateDTO>getById(Map map);

    /**
     * @Method save
     * @Desrciption 派发
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/12 16:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method savaTemplate
     * @Desrciption 新增/修改模板
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/12 16:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<EmrClassifyTemplateDTO> saveTemplate(Map map);

    /**
     * @Method queryCheckCodes
     * @Desrciption 查询已经派发并且有效的模板
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/12 16:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
     **/
    WrapperResponse<List<String>> queryCheckCodes(Map map);


    WrapperResponse<List<TreeMenuNode>> queryTemplateTree(Map map);

    /**
    * @Menthod queryTemplateAll
    * @Desrciption 根据条件查询全部模板
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/10/20 15:56
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>>
    **/
    WrapperResponse<List<EmrClassifyTemplateDTO>> queryTemplateAll(Map map);
}
