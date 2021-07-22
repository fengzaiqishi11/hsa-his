package cn.hsa.module.emr.emrclassify.service;


import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassify.service
 * @Class_name:: EmrClassifyServcie
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/9/27 9:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-emr")
public interface EmrClassifyServcie {

    /**
     * @Method getByIdorCode
     * @Desrciption 通过id或者code进行查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    @GetMapping("/service/emr/emrClassify/getByIdorCode")
    WrapperResponse<EmrClassifyDTO> getByIdorCode(Map<String, Object> map);

    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>>
     **/
    @GetMapping("/service/emr/emrClassify/queryAll")
    WrapperResponse<List<EmrClassifyDTO>> queryAll(Map map);

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/emr/emrClassify/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method save
     * @Desrciption 修改、新增电子病历分类
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/emr/emrClassify/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method getEmrClassifyTree
     * @Desrciption 获取电子病历分类树
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/service/emr/emrClassify/getEmrClassifyTree")
    WrapperResponse<List<TreeMenuNode>> getEmrClassifyTree(Map map);

    @GetMapping("/service/emr/emrClassify/getMaxCode")
    WrapperResponse<Integer> getMaxCode(Map map);
}
