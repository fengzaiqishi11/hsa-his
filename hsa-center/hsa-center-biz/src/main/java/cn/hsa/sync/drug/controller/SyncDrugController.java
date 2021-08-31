package cn.hsa.sync.drug.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncdrug.dto.SyncDrugDTO;
import cn.hsa.module.sync.syncdrug.service.SyncDrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name:: BaseDrugController
 * @Description: 药品管理控制层
 * @Author: liaojunjie
 * @Date: 2020/7/14 15:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/drug")
@Slf4j
public class SyncDrugController extends CenterBaseController {
    /**
     * 药品管理dubbo消费者接口
     */
    @Resource
    private SyncDrugService syncDrugService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过id获取药品信息
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<BaseDrugDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SyncDrugDTO> getById(SyncDrugDTO syncDrugDTO) {
        return this.syncDrugService_consumer.getById(syncDrugDTO);
    }

    /**
     * @Method queryPage()
     * @Description 多条件查询信息(包括初始加载)
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<PageDTO>
     **/

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncDrugDTO syncDrugDTO) {
        return this.syncDrugService_consumer.queryPage(syncDrugDTO);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有药品
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/16 11:08
     * @Return WrapperResponse<List < BaseDrugDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncDrugDTO>> queryAll(SyncDrugDTO syncDrugDTO) {
        return this.syncDrugService_consumer.queryAll(syncDrugDTO);
    }

    /**
     * @Method save()
     * @Description 新增/修改单条药品信息
     * @Param [baseDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncDrugDTO syncDrugDTO) {
        syncDrugDTO.setCrteId(userId);
        syncDrugDTO.setCrteName(userName);
        return this.syncDrugService_consumer.save(syncDrugDTO);
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     * @Param [centerDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncDrugDTO syncDrugDTO) {
        return this.syncDrugService_consumer.updateStatus(syncDrugDTO);
    }
}
