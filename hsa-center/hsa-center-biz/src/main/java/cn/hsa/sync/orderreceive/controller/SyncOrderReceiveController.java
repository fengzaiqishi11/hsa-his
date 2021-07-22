package cn.hsa.sync.orderreceive.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.orderreceive.dto.SyncOrderReceiveDTO;
import cn.hsa.module.sync.orderreceive.service.SyncOrderReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync
 * @Class_name: SyncOrderReceiveManagementController
 * @Describe: 领药单据类型管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/syncOrderReceive")
@Slf4j
public class SyncOrderReceiveController extends CenterBaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private SyncOrderReceiveService syncOrderReceiveService_customer;

    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询领药单据类型信息
     * @param syncOrderReceiveDTO 领药单据类型信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncOrderReceiveDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<SyncOrderReceiveDTO> getById(SyncOrderReceiveDTO syncOrderReceiveDTO) {

        return syncOrderReceiveService_customer.getById(syncOrderReceiveDTO);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部领药单据类型信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncOrderReceiveDTO>> queryAll(SyncOrderReceiveDTO syncOrderReceiveDTO) {

        return syncOrderReceiveService_customer.queryAll(syncOrderReceiveDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询领药单据类型信息表
     * @param syncOrderReceiveDTO 领药单据类型信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncOrderReceiveDTO syncOrderReceiveDTO) {

        return syncOrderReceiveService_customer.queryPage(syncOrderReceiveDTO);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条领药单据类型信息(无判空条件)
     * @param syncOrderReceiveDTO 领药单据类型信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncOrderReceiveDTO syncOrderReceiveDTO) {

        syncOrderReceiveDTO.setCrteId(userId);
        syncOrderReceiveDTO.setCrteName(userName);

        return this.syncOrderReceiveService_customer.save(syncOrderReceiveDTO);
    }

    /**
     * @Menthod updateSyncOrderReceiveS
     * @Desrciption 修改单条领药单据类型信息(有判空条件)
     * @param syncOrderReceiveDTO 领药单据类型信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/17 9:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateSyncOrderReceiveS")
    public WrapperResponse<Boolean> updateSyncOrderReceiveS(@RequestBody SyncOrderReceiveDTO syncOrderReceiveDTO) {

        syncOrderReceiveDTO.setCrteId(userId);
        syncOrderReceiveDTO.setCrteName(userName);

        return this.syncOrderReceiveService_customer.updateSyncOrderReceiveS(syncOrderReceiveDTO);
    }


    /**
    * @Menthod updateStatus
    * @Desrciption  通过主键ID删除一条或多条领药单据类型信息
     * @param syncOrderReceiveDTO 领药单据类型数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncOrderReceiveDTO syncOrderReceiveDTO) {

        return this.syncOrderReceiveService_customer.updateStatus(syncOrderReceiveDTO);
    }


}
