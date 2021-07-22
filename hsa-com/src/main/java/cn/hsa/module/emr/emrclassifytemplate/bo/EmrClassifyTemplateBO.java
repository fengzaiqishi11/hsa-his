package cn.hsa.module.emr.emrclassifytemplate.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.bo
 * @Class_name: EmrElementBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrClassifyTemplateBO {

    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/21 11:22
     * @Return cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO
     **/
    EmrClassifyTemplateDTO getById(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method save
     * @Desrciption 派发
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:29
     * @Return java.lang.Boolean
     **/
    Boolean save(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method saveTemplate
     * @Desrciption 新增/修改模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:29
     * @Return cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO
     **/
    EmrClassifyTemplateDTO saveTemplate(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryCheckCodes
     * @Desrciption 查询已经派发并且有效的模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:29
     * @Return java.util.List<java.lang.String>
     **/
    List<String>queryCheckCodes(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
     * @Method queryTemplateTree
     * @Desrciption 查询模板树
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:29
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> queryTemplateTree(EmrClassifyTemplateDTO emrClassifyTemplateDTO);

    /**
    * @Menthod queryTemplateAll
    * @Desrciption 根据条件查询全部模板
    *
    * @Param
    * [emrClassifyTemplateDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/10/20 15:57
    * @Return java.util.List<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
    **/
    List<EmrClassifyTemplateDTO> queryTemplateAll(EmrClassifyTemplateDTO emrClassifyTemplateDTO);
}
