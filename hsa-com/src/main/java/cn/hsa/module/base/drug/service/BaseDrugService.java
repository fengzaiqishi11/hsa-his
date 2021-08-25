package cn.hsa.module.base.drug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.drug.service
 * @Class_name: BaseDrugService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 15:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseDrugService {
    /**
     * @Method getById
     * @Desrciption 通过id获取药品信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 14:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     **/
    @GetMapping("/service/base/baseDrug/getById")
    WrapperResponse<BaseDrugDTO> getById(Map map);

    /**
    * @Method: getByCode
    * @Description: 根据编码获取药品信息
    * @Param: map map
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/10/9 15:58
    * @Return:
    **/
    @GetMapping("/service/base/baseDrug/getByCode")
    WrapperResponse<BaseDrugDTO> getByCode(Map map);

    /**
     * @Method queryPage
     * @Desrciption 分页查询(默认状态为有效)
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 14:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/base/baseDrug/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有药品
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 11:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<BaseDrugDTO>>
     **/
    @GetMapping("/service/base/baseDrug/queryAll")
    WrapperResponse<List<BaseDrugDTO>> queryAll(Map map);

    /**
     * @Method updateStatus
     * @Desrciption 修改有效标识状态
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 14:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseDrug/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
     * @Method save()
     * @Description 新增/修改单条药品信息
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/baseDrug/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method updateAllById
     * @Desrciption 通过ID数组修改所有药品的信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/14 10:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseDrug/updateAllById")
    WrapperResponse<Boolean> updateAllById(Map map);

    /**
    * @Method queryStockDrugInfoOfDept
    * @Desrciption 查询某库位的项目(药品或材料)信息
    * @param map
    * @Author liuqi1
    * @Date   2020/8/12 11:58
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>>
    **/
    @GetMapping("/service/base/baseDrug/queryStockItemInfoPage")
    WrapperResponse<PageDTO> queryStockItemInfoPage(Map<String,Object> map);

    WrapperResponse<Boolean> upLoad(Map map);
    /**
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步药品数据到医保匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    WrapperResponse<Boolean> insertInsureDrugMatch(Map<String, Object> map);

    /**
     * @Method updateBaseDrugS()
     * @Description 修改单条药品信息 (修改国家编码)
     *
     * @Param
     * [baseDrugDTO]
     *
     * @Author pengbo
     * @Date 2021/3/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/baseDrug/updateBaseDrugS")
    WrapperResponse<Boolean> updateBaseDrugS(Map<String,Object> map);

    /**
     * @Method queryUnifiedPage
     * @Desrciption  医保统一支付平台：药品匹配优化
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/16 16:21
     * @Return
    **/
    WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map);
}
