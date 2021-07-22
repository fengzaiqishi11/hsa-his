package cn.hsa.module.base.bi.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bi.service
 * @Class_name:: BaseItemService
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/7/30 14:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseItemService {
    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/14 14:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bd.dto.BaseItemDTO>
     **/
    @GetMapping("/service/base/baseItem/getById")
    WrapperResponse<BaseItemDTO> getById(Map map);

    /**
     * @Method queryPage
     * @Desrciption 分页查询(默认状态为有效)
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/14 14:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/base/baseItem/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有项目
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 11:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<BaseItemDTO>>
     **/
    @GetMapping("/service/base/baseItem/queryAll")
    WrapperResponse<List<BaseItemDTO>> queryAll(Map map);

    /**
     * @Method updateStatus
     * @Desrciption 修改有效标识状态
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/14 14:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseItem/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
     * @Method save()
     * @Description 新增/修改单条项目信息
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/baseItem/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method getById
     * @Desrciption 通过code获取项目信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/14 14:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bd.dto.BaseItemDTO>
     **/
    @GetMapping("/service/base/baseItem/getByCode")
    WrapperResponse<BaseItemDTO> getByCode(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询item带bfcId
       @params [map]
     * @Author chenjun
     * @Date   2020/10/29 15:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>>
    **/
    @GetMapping("/service/base/baseItem/queryAllBfcId")
    WrapperResponse<List<BaseItemDTO>> queryAllBfcId(Map map);

    WrapperResponse<Boolean> upLoad(Map map);

    /**
     * @Menthod updateNationCodeById
     * @Desrciption  根据ID修改国家编码
     * @param map 材料信息数据传输对象List
     * @Author pengbo
     * @Date   2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseItem/updateNationCodeById")
    WrapperResponse<Boolean> updateNationCodeById(Map map);

    /**
     * @Method insertInsureItemMatch
     * @Desrciption 医保统一支付平台： 同步项目数据到医保匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    WrapperResponse<Boolean> insertInsureItemMatch(Map<String, Object> map);

    WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map);
}
