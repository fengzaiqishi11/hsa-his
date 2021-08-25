package cn.hsa.sync.mrisclassify.controller;



import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.mrisclassify.dto.SyncMrisClassifyDTO;
import cn.hsa.module.sync.mrisclassify.service.SyncMrisClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync
 * @Class_name: SyncMrisClassifyManagementController
 * @Describe: 病案费用归类管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/syncMrisClassify")
@Slf4j
public class SyncMrisClassifyController extends CenterBaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private SyncMrisClassifyService syncMrisClassifyService_customer;

    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询病案费用归类信息
     * @param syncMrisClassifyDTO 病案费用归类信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncMrisClassifyDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<SyncMrisClassifyDTO> getById(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return syncMrisClassifyService_customer.getById(syncMrisClassifyDTO);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部病案费用归类信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncMrisClassifyDTO>> queryAll(SyncMrisClassifyDTO syncMrisClassifyDTO) {

        return syncMrisClassifyService_customer.queryAll(syncMrisClassifyDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询病案费用归类信息表
     * @param syncMrisClassifyDTO 病案费用归类信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncMrisClassifyDTO syncMrisClassifyDTO) {

        return syncMrisClassifyService_customer.queryPage(syncMrisClassifyDTO);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条病案费用归类信息(无判空条件)
     * @param syncMrisClassifyDTO 病案费用归类信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncMrisClassifyDTO syncMrisClassifyDTO) {

        syncMrisClassifyDTO.setCrteId(userId);
        syncMrisClassifyDTO.setCrteName(userName);

        return this.syncMrisClassifyService_customer.save(syncMrisClassifyDTO);
    }

    /**
     * @Menthod updateSyncMrisClassifyS
     * @Desrciption 修改单条病案费用归类信息(有判空条件)
     * @param syncMrisClassifyDTO 病案费用归类信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/17 9:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateSyncMrisClassifyS")
    public WrapperResponse<Boolean> updateSyncMrisClassifyS(@RequestBody SyncMrisClassifyDTO syncMrisClassifyDTO) {

        syncMrisClassifyDTO.setCrteId(userId);
        syncMrisClassifyDTO.setCrteName(userName);

        return this.syncMrisClassifyService_customer.updateSyncMrisClassifyS(syncMrisClassifyDTO);
    }


    /**
    * @Menthod updateStatus
    * @Desrciption  通过主键ID删除一条或多条病案费用归类信息
     * @param syncMrisClassifyDTO 病案费用归类数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncMrisClassifyDTO syncMrisClassifyDTO) {

        return this.syncMrisClassifyService_customer.updateStatus(syncMrisClassifyDTO);
    }


}
