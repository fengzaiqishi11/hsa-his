package cn.hsa.emr.emrclassifyelement.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassifyelement.bo.EmrClassifyElementBO;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import cn.hsa.module.emr.emrclassifyelement.service.EmrClassifyElementService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrclassifyelement.bo.impl
 * @Class_name: EmrClassifyElementBOImpl
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/9/27 14:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/emr/emrclassifyelement")
@Slf4j
@Service("emrElementClassifyServcie_provider")
public class EmrClassifyElementServiceImpl extends HsafService implements EmrClassifyElementService {

    @Resource
    EmrClassifyElementBO emrClassifyElementBO;


    /**
    * @Menthod queryAll
    * @Desrciption  根据条件筛选电子病历文档元素管理表中的数据
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/28 10:17 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO>>
    **/
    @Override
    public WrapperResponse<List<EmrClassifyElementDTO>> queryAll(Map map) {
        return WrapperResponse.success(emrClassifyElementBO.queryAll(MapUtils.get(map,"emrClassifyElementDTO")));
    }

    /**
    * @Menthod save
    * @Desrciption  修改文档分类已勾选的元素节点
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/28 10:16 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(emrClassifyElementBO.save(MapUtils.get(map,"emrClassifyElementDTO")));
    }

    /**
    * @Menthod queryTreeByEmrClassify
    * @Desrciption  根据文档分类已选择的元素分类节点生成树
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/28 10:16 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
    **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> queryTreeByEmrClassify(Map map) {
        return WrapperResponse.success(TreeUtils.buildByRecursive(emrClassifyElementBO.queryTreeByEmrClassify(MapUtils.get(map,"emrClassifyElementDTO")),"-1"));
    }
    
    /**
    * @Menthod queryTreeIsAble
    * @Desrciption  根据文档分类code查询元素分类树和已勾选的节点
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/28 10:16 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
    **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> queryTreeIsAble(Map map) {
        return WrapperResponse.success(TreeUtils.buildByRecursive(emrClassifyElementBO.queryTreeIsAble(MapUtils.get(map,"emrClassifyElementDTO")),"-1"));
    }

    /**
    * @Menthod queryEmrClassifyCodesByElementCodes
    * @Desrciption  根据元素编码查询出所有的文档分类编码
     * @param map 医院编码，元素编码list
    * @Author xingyu.xie
    * @Date   2020/10/9 11:35
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
    **/
    @Override
    public WrapperResponse<List<String>> queryEmrClassifyCodesByElementCodes(Map map) {
        return WrapperResponse.success(emrClassifyElementBO.queryEmrClassifyCodesByElementCodes(map));
    }
}
