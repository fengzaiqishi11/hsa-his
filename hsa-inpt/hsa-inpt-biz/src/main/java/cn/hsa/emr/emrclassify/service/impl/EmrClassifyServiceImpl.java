package cn.hsa.emr.emrclassify.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassify.bo.EmrClassifyBO;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrclassify.service.EmrClassifyServcie;
import cn.hsa.util.MapUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrclassify.service.impl
 * @Class_name: EmrClassifyServiceImpl
 * @Describe: \
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/27 10:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrclassify")
@Slf4j
@Service("emrClassifyServcie_provider")
public class EmrClassifyServiceImpl implements EmrClassifyServcie {

    @Resource
    private EmrClassifyBO emrClassifyBO;

    /**
     * @Method getByIdorCode
     * @Desrciption 通过id或者code进行查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    @Override
    public WrapperResponse<EmrClassifyDTO> getByIdorCode(Map<String, Object> map) {
        return WrapperResponse.success(emrClassifyBO.getByIdOrCode(MapUtils.get(map,"emrClassifyDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>>
     **/
    @Override
    public WrapperResponse<List<EmrClassifyDTO>> queryAll(Map map) {
        return WrapperResponse.success(emrClassifyBO.queryAll(MapUtils.get(map,"emrClassifyDTO")));
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(emrClassifyBO.queryPage(MapUtils.get(map,"emrClassifyDTO")));
    }

    /**
     * @Method save
     * @Desrciption 修改、新增电子病历分类
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(emrClassifyBO.save(MapUtils.get(map,"emrClassifyDTO")));
    }

    /**
     * @Method getEmrClassifyTree
     * @Desrciption 获取电子病历分类树
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> getEmrClassifyTree(Map map) {
        return WrapperResponse.success(TreeUtils.buildByRecursive(emrClassifyBO.getEmrClassifyTree(MapUtils.get(map,"emrClassifyDTO")),"-2"));
    }

    @Override
    public WrapperResponse<Integer> getMaxCode(Map map) {
        return WrapperResponse.success(emrClassifyBO.getMaxCode(MapUtils.get(map,"emrClassifyDTO")));
    }
}
