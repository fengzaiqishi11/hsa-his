package cn.hsa.module.emr.emrclassify.dao;


import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassify.dao
 * @Class_name:: EmrClassifyDAO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/9/27 9:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface EmrClassifyDAO {
    /**
     * @Method getByIdOrCode
     * @Desrciption 通过id或者code进行查询分类
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:14
     * @Return cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO
     **/
    EmrClassifyDTO getByIdOrCode(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method queryPageOrAll
     * @Desrciption 分页或者查询所有
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:14
     * @Return java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    List<EmrClassifyDTO> queryPageOrAll(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method insert
     * @Desrciption 新增分类
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return java.lang.Integer
     **/
    Integer insert(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method updateEmrClassify
     * @Desrciption 修改分类（无判空条件）
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return java.lang.Integer
     **/
    Integer updateEmrClassify(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method updateEmrClassifyS
     * @Desrciption 修改分类（有判空条件）
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return java.lang.Integer
     **/
    Integer updateEmrClassifyS(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method getEmrClassifyTree
     * @Desrciption 获取电子病历分类树
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getEmrClassifyTree(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method getValidEmrClassifyTree
     * @Desrciption 获取有效的电子病历分类树
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getValidEmrClassifyTree(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method getMaxCode
     * @Desrciption 获取编码最大的分类
     * @Param
     * [EmrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO
     **/
    EmrClassifyDTO getMaxCode(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method queryByUpCode
     * @Desrciption 通过上级编码查询
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    List<EmrClassifyDTO> queryByUpCode(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Method updateList
     * @Desrciption 通过上级编码批量修改
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:15
     * @Return java.lang.Integer
     **/
    Integer updateList(EmrClassifyDTO emrClassifyDTO);

    /**
     * @Description: 根据医院编码，文档编码查询文档关联元素中类型为时间的元素
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/12/22 19:05
     * @Return
     */
    List<EmrElementDTO> getEmrClassifyRecordCode(EmrClassifyDTO emrClassifyDTO);
}
