package cn.hsa.sync.product.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.product.dto.SyncProductDTO;
import cn.hsa.module.sync.product.service.SyncProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync
 * @Class_name: SyncProductManagementController
 * @Describe: 生产企业管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/syncProduct")
@Slf4j
public class SyncProductController extends CenterBaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private SyncProductService syncProductService;

    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询生产企业信息
     * @param syncProductDTO 生产企业信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.SyncProductDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<SyncProductDTO> getById(SyncProductDTO syncProductDTO) {

        return syncProductService.getById(syncProductDTO);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部生产企业信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncProductDTO>> queryAll(SyncProductDTO syncProductDTO) {
        return syncProductService.queryAll(syncProductDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询生产企业信息表
     * @param syncProductDTO 生产企业信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncProductDTO syncProductDTO) {

        return syncProductService.queryPage(syncProductDTO);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条生产企业信息
     * @param syncProductDTO 生产企业信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncProductDTO syncProductDTO) {
        syncProductDTO.setCrteId(userId);
        syncProductDTO.setCrteName(userName);
        return this.syncProductService.save(syncProductDTO);
    }


    /**
    * @Menthod delete
    * @Desrciption  通过主键ID删除一条或多条生产企业信息
     * @param syncProductDTO 生产企业数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncProductDTO syncProductDTO) {
        return this.syncProductService.updateStatus(syncProductDTO);
    }


}
