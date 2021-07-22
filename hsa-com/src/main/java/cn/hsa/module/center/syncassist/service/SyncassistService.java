package cn.hsa.module.center.syncassist.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncassist.dto.SyncassistDTO;
import cn.hsa.module.center.syncassist.dto.SyncassistDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
public interface SyncassistService {


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
    WrapperResponse<List<SyncassistDTO>> queryAll(SyncassistDTO syncassistDTO);


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
    WrapperResponse<Boolean> insert(SyncassistDTO syncassistDTO);

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
    WrapperResponse<Boolean> update(SyncassistDTO syncassistDTO);

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
    WrapperResponse<Boolean> updateStatus(SyncassistDTO syncassistDTO);

    @PostMapping("/service/base/bac/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncassistDTO syncassistDTO);


    @PostMapping("/service/base/bacd/queryPage")
    WrapperResponse<PageDTO> detailqueryPage(SyncassistDetailDTO syncassistDetailDTO);
}
