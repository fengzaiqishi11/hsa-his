package cn.hsa.sync.item.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncitem.dto.SyncItemDTO;
import cn.hsa.module.sync.syncitem.service.SyncItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name:: BaseItemController
 * @Description: 项目管理控制层
 * @Author: liaojunjie
 * @Date: 2020/7/14 15:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/item")
@Slf4j
public class SyncItemController extends CenterBaseController {
    /**
     * 项目管理dubbo消费者接口
     */
    @Resource
    private SyncItemService syncItemService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 15:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SyncItemDTO> getById(SyncItemDTO syncItemDTO) {
        return this.syncItemService_consumer.getById(syncItemDTO);

    }

    /**
     * @Method queryPage()
     * @Description 多条件查询信息(包括初始加载)
     *
     * @Param
     *[syncItemDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<PageDTO>
     **/

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncItemDTO syncItemDTO) {
        return this.syncItemService_consumer.queryPage(syncItemDTO);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有项目
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:08
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>>
     **/
    @RequestMapping("/queryAll")
    public WrapperResponse<List<SyncItemDTO>> queryAll(SyncItemDTO syncItemDTO) {
        return this.syncItemService_consumer.queryAll(syncItemDTO);
    }

    /**
     * @Method save()
     * @Description 新增/修改单条项目信息
     *
     * @Param
     * [baseItemDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncItemDTO syncItemDTO) {
        syncItemDTO.setCrteId(userId);
        syncItemDTO.setCrteName(userName);
        return this.syncItemService_consumer.save(syncItemDTO);
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     *
     * @Param
     * [baseItemDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncItemDTO syncItemDTO) {
        return this.syncItemService_consumer.updateStatus(syncItemDTO);
    }


}
