package cn.hsa.module.emr.emrclassify.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrclassify.bo
 * @Class_name:: EmrClassifyBO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/9/25 17:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface EmrClassifyBO {

    /**
     * @Method getByIdOrCode
     * @Desrciption 通过id或者code进行查询
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:12
     * @Return cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO
     **/
    EmrClassifyDTO getByIdOrCode(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:12
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method queryAll
     * @Desrciption  查询所有
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:12
     * @Return java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    List<EmrClassifyDTO> queryAll(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method save
     * @Desrciption 修改、新增电子病历分类
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:13
     * @Return java.lang.Boolean
     **/
    Boolean save(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method getEmrClassifyTree
     * @Desrciption 获取电子病历分类树
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:13
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getEmrClassifyTree(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method getMaxCode
     * @Desrciption 获取最大序号
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/21 17:28
     * @Return java.lang.String
     **/
    Integer getMaxCode(EmrClassifyDTO emrClassifyDTO);
}
