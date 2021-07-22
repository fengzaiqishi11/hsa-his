package cn.hsa.module.emr.emrclassifyelement.dao;


import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.dao
 * @Class_name: EmrElementDO
 * @Describe:
 * @Author: xiexingyu
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/9/17 16:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrClassifyElementDAO {
    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param
     * [emrClassifyElementDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:24
     * @Return java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO>
     **/
    List<EmrClassifyElementDTO> queryAll(EmrClassifyElementDTO emrClassifyElementDTO);

    /**
     * @Method insertList
     * @Desrciption 批量插入
     * @Param
     * [list]
     * @Author liaojunjie
     * @Date   2020/12/16 11:24
     * @Return boolean
     **/
    boolean insertList(@Param("list")List<EmrClassifyElementDTO> list);

    /**
     * @Method updateEmrClassifyElementList
     * @Desrciption 批量修改
     * @Param
     * [emrClassifyElementDTOList]
     * @Author liaojunjie
     * @Date   2020/12/16 11:24
     * @Return boolean
     **/
    boolean updateEmrClassifyElementList(List<EmrClassifyElementDTO> emrClassifyElementDTOList);

    /**
     * @Method deleteByClassinfoCode
     * @Desrciption 通过主键删除
     * @Param
     * [emrClassifyElementDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:24
     * @Return boolean
     **/
    boolean deleteByClassinfoCode(EmrClassifyElementDTO emrClassifyElementDTO);

    /**
     * @Method queryTreeIsAble
     * @Desrciption 查询树
     * @Param
     * [emrClassifyElementDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:24
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> queryTreeIsAble(EmrClassifyElementDTO emrClassifyElementDTO);


    List<EmrClassifyElementDTO> queryCheckElement(EmrClassifyElementDTO emrClassifyElementDTO);


    /**
     * @Method queryEmrClassifyCodesByElementCodes
     * @Desrciption 查询关系
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:24
     * @Return java.util.List<java.lang.String>
     **/
    List<String> queryEmrClassifyCodesByElementCodes(Map map);


}
