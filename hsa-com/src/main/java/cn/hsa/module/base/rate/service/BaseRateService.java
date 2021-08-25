package cn.hsa.module.base.rate.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.base.rate.StockInQueryService
 * @Class_name: BaseRateService
 * @Description: 医嘱频率业务服务层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 10:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseRateService {

    /**
     * @Method getById()
     * @Description 查询医嘱频率
     *
     * @Param  map
     * 1、id：医嘱频率表主键ID
     * 2、hospCode 医院编码
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @GetMapping("/api/base/baseRate/getById")
    WrapperResponse<BaseRateDTO> getById(Map map);

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     *
     * @Param
     * 1、 baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @GetMapping("/api/base/baseRate/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @PostMapping("/api/base/baseRate/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Method update()
     * @Description 修改医嘱频率信息
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @PutMapping ("/api/base/baseRate/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method: 查询病区编码 提供给科室维护信息 住院时用
     * @Description:
     * @Param: hospCode医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return:
     */
    @PostMapping("/api/base/baseRate/updateIsValid")
    WrapperResponse<Boolean> updateIsValid(Map map);

    /**
     * @Method queryAll()
     * @Description 查询全部医嘱频率
     * @Param
     * 1、baseRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/api/base/baseRate/queryAll")
    WrapperResponse<List<BaseRateDTO>> queryAll(Map map);

    /**
     * @Method getByRateCode()
     * @Desrciption 根据频率编码查询医嘱频率信息
     * @Param hospCode医院编码,code:频率编码
     *
     * @Author fuhui
     * @Date   2020/10/22 17:37
     * @Return 频率id
    **/
    @GetMapping("/api/base/baseRate/getByRateCode")
    WrapperResponse<String> getByRateCode(Map map);
}