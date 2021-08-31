package cn.hsa.module.sync.syncitem.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncitem.dto.SyncItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.item.service
 * @Class_name:: CenterItemService
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface SyncItemService {
    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.item.dto.CenterItemDTO>
     **/
    @GetMapping("/service/sync/item/getById")
    WrapperResponse<SyncItemDTO> getById(SyncItemDTO syncItemDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/sync/item/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncItemDTO syncItemDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.item.dto.CenterItemDTO>>
     **/
    @RequestMapping("/service/sync/item/queryAll")
    WrapperResponse<List<SyncItemDTO>> queryAll(SyncItemDTO syncItemDTO);

    /**
     * @Method delete
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/item/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncItemDTO syncItemDTO);

    /**
     * @Method save
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/item/save")
    WrapperResponse<Boolean> save(SyncItemDTO syncItemDTO);

}
