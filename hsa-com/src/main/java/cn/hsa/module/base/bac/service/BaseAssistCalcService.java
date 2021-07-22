package cn.hsa.module.base.bac.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.base.syncassist.service
 * @Class_name: SyncassistService
 * @Description:  service接口层（提供给dubbo调用）
 * @Author: ljh
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseAssistCalcService {


    /**
     * @Method queryById()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、SyncassistDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return  WrapperResponse<List<SyncassistDTO>>
     **/

    @PostMapping("/service/base/bac/queryAll")
    WrapperResponse<List<BaseAssistCalcDTO>> queryAll(Map map);


    /**
     * @Method queryById()
     * @Description 新增数据
     *
     * @Param
     * 1、SyncassistDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return  WrapperResponse<List<SyncassistDTO>>
     **/
    @PostMapping("/service/base/bac/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Method update()
     * @Description 修改
     *
     * @Param
     * 1、SyncassistDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return  WrapperResponse<List<SyncassistDTO>>
     **/
    @PostMapping("/service/base/bac/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method queryById()
     * @Description 查询
     *
     * @Param
     * 1、ids
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return  WrapperResponse<List<SyncassistDTO>>
     **/
    @PostMapping("/service/base/bac/deleteById")
    WrapperResponse<Boolean> updateStatus(Map map);
    /**
     * @Method queryPage
     * @Desrciption 分页
     * @Param
     * [map]
     * @Author ljh
     * @Date   2020/8/7 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/bac/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method detailqueryPage
     * @Desrciption 分页
     * @Param
     * [map]
     * @Author ljh
     * @Date   2020/8/7 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/bacd/queryPage")
    WrapperResponse<PageDTO> detailqueryPage(Map map);

    /**
     * @Method: queryAssists
     * @Description: 查询辅助计费
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 14:12
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDTO>
     **/
    @GetMapping("/service/base/bacd/queryAssists")
    WrapperResponse<List<BaseAssistCalcDTO>> queryAssists(Map map);

    /**
     * @Method: queryAssistDetails
     * @Description: 根据编码获取辅助计费明细
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 15:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO>>
     **/
    @GetMapping("/service/base/bacd/queryAssistDetails")
    WrapperResponse<List<BaseAssistCalcDetailDTO>> queryAssistDetails(Map map);
}
