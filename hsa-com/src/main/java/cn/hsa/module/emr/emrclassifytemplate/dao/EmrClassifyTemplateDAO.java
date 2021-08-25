package cn.hsa.module.emr.emrclassifytemplate.dao;


import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifytemplate.dao
 * @Class_name:: EmrClassifyTemplateDAO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/9/28 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface EmrClassifyTemplateDAO {

    /**
     * @Method getById
     * @Desrciption 通过id进行查询模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/21 11:24
     * @Return cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO
     **/
    EmrClassifyTemplateDTO getById(EmrClassifyTemplateDTO emrClassifyTemplateDTO);


    /**
     * @Method queryTemplate
     * @Desrciption 查询有效病历模板信息
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:31
     * @Return java.util.List<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
     **/
    List<EmrClassifyTemplateDTO> queryTemplate(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryAllTemplate
     * @Desrciption 查询病历模板信息
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.util.List<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
     **/
    List<EmrClassifyTemplateDTO> queryAllTemplate(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryClassifyNode
     * @Desrciption 查询全院分类
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> queryClassifyNode(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryTemplates
     * @Desrciption 查询科室已经派发的分类id
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.util.List<java.lang.String>
     **/
    List<String> queryTemplates(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryClassifyCode
     * @Desrciption 查询模板id对应的分类Code
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.util.List<java.lang.String>
     **/
    List<String> queryClassifyCode(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method insert
     * @Desrciption 单个新增
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.lang.Integer
     **/
    Integer insert(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method insertBatch
     * @Desrciption 批量新增所有列
     * @Param
     * [list]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.lang.Integer
     **/
    Integer insertBatch(@Param("list") List<EmrClassifyTemplateDTO> list);

    /**
     * @Method queryClassified
     * @Desrciption 查询全院病历绑定的分类
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.util.List<java.lang.String>
     **/
    List<String> queryClassified(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryClassify
     * @Desrciption 查询全员病历所有分类
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:32
     * @Return java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    List<EmrClassifyDTO> queryClassify(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryClassifyElement
     * @Desrciption 查询全员病历所有分类元素
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:33
     * @Return java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO>
     **/
    List<EmrClassifyElementDTO> queryClassifyElement(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method updateTemplate
     * @Desrciption 通过主键批量修改数据(有判空条件))
     * @Param
     * [emrClassifyTemplateDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:33
     * @Return java.lang.Boolean
     **/
    Boolean updateTemplate(@Param("list") List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS);

    /**
     * @Method updateEmrClassifyTemplateS
     * @Desrciption 通过主键修改数据(有判空条件))
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:33
     * @Return java.lang.Boolean
     **/
    Boolean updateEmrClassifyTemplateS(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method deleteClassify
     * @Desrciption 批量删除分类
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:33
     * @Return java.lang.Boolean
     **/
    Boolean deleteClassify(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method deleteClassifyElement
     * @Desrciption 批量删除分类元素
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:33
     * @Return java.lang.Boolean
     **/
    Boolean deleteClassifyElement(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method updateClassify
     * @Desrciption 批量修改分类
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:34
     * @Return java.lang.Boolean
     **/
    Boolean updateClassify(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method insertClassify
     * @Desrciption 批量新增分类
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:34
     * @Return java.lang.Boolean
     **/
    Boolean insertClassify(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method insertClassifyElement
     * @Desrciption 批量新增分类元素
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:34
     * @Return java.lang.Boolean
     **/
    Boolean insertClassifyElement(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryCheckCodes
     * @Desrciption 根据科室查询模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:34
     * @Return java.util.List<java.lang.String>
     **/
    List<String>queryCheckCodes(EmrClassifyTemplateDTO emrClassifyTemplateDTO);
}
