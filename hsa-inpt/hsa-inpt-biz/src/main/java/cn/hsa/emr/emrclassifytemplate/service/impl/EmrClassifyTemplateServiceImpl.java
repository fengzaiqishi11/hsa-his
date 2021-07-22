package cn.hsa.emr.emrclassifytemplate.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassifytemplate.bo.EmrClassifyTemplateBO;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import cn.hsa.module.emr.emrclassifytemplate.service.EmrClassifyTemplateService;
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
@HsafRestPath("/service/emr/emrclassifytemplate")
@Slf4j
@Service("emrClassifyTemplateServcie_provider")
public class EmrClassifyTemplateServiceImpl extends HsafService implements EmrClassifyTemplateService {
    @Resource
    private EmrClassifyTemplateBO emrClassifyTemplateBO;

    @Override
    public WrapperResponse<EmrClassifyTemplateDTO> getById(Map map) {
        return WrapperResponse.success(emrClassifyTemplateBO.getById(MapUtils.get(map, "emrClassifyTemplateDTO")));
    }

    /**
     * @Method save
     * @Desrciption 派发
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(emrClassifyTemplateBO.save(MapUtils.get(map, "emrClassifyTemplateDTO")));

    }

    /**
     * @Method saveTemplate
     * @Desrciption 新增/修改模板
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
     **/
    @Override
    public WrapperResponse<EmrClassifyTemplateDTO> saveTemplate(Map map) {
        return WrapperResponse.success(emrClassifyTemplateBO.saveTemplate(MapUtils.get(map, "emrClassifyTemplateDTO")));
    }

    /**
     * @Method queryCheckCodes
     * @Desrciption 查询已经派发并且有效的模板
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
     **/
    @Override
    public WrapperResponse<List<String>> queryCheckCodes(Map map) {
        return WrapperResponse.success(emrClassifyTemplateBO.queryCheckCodes(MapUtils.get(map, "emrClassifyTemplateDTO")));
    }

    /**
     * @Method queryTemplateTree
     * @Desrciption 查询模板树
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> queryTemplateTree(Map map) {
        EmrClassifyTemplateDTO emrClassifyTemplateDTO = MapUtils.get(map, "emrClassifyTemplateDTO");
        List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(emrClassifyTemplateBO.queryTemplateTree(emrClassifyTemplateDTO), "-2");
        return WrapperResponse.success(treeMenuNodes);
    }

    /**
    * @Menthod queryTemplateAll
    * @Desrciption 根据条件查询全部模板
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/10/20 15:57
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>>
    **/
  @Override
  public WrapperResponse<List<EmrClassifyTemplateDTO>> queryTemplateAll(Map map) {
    EmrClassifyTemplateDTO emrClassifyTemplateDTO = MapUtils.get(map, "emrClassifyTemplateDTO");
    return WrapperResponse.success(emrClassifyTemplateBO.queryTemplateAll(emrClassifyTemplateDTO));
  }
}
