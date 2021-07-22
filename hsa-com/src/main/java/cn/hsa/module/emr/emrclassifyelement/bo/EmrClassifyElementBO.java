package cn.hsa.module.emr.emrclassifyelement.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.bo
 * @Class_name: EmrElementBO
 * @Describe:
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/9/17 16:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrClassifyElementBO {


    /**
    * @Menthod queryAll
    * @Desrciption  根据条件筛选电子病历文档元素管理表中的数据
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:18 
    * @Return java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO>
    **/
    List<EmrClassifyElementDTO> queryAll(EmrClassifyElementDTO emrClassifyElementDTO);

    /**
    * @Menthod save
    * @Desrciption  修改文档分类已勾选的元素节点
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:17 
    * @Return java.lang.Boolean
    **/
    Boolean save(EmrClassifyElementDTO emrClassifyElementDTO);

    /**
    * @Menthod queryTreeByEmrClassify
    * @Desrciption  根据文档分类已选择的元素分类节点生成树
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:17 
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    List<TreeMenuNode> queryTreeByEmrClassify(EmrClassifyElementDTO emrClassifyElementDTO);
    
    /**
    * @Menthod queryTreeIsAble
    * @Desrciption  根据文档分类code查询元素分类树和已勾选的节点
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:17 
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    List<TreeMenuNode> queryTreeIsAble(EmrClassifyElementDTO emrClassifyElementDTO);

    /**
    * @Menthod queryEmrClassifyCodesByElementCodes
    * @Desrciption  根据元素编码查询出所有的文档分类编码
     * @param map 医院编码，元素编码list
    * @Author xingyu.xie
    * @Date   2020/10/9 11:27
    * @Return java.util.List<java.lang.String>
    **/
    List<String> queryEmrClassifyCodesByElementCodes(Map map);

}
