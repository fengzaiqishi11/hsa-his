package cn.hsa.sync.material.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.material.dto.SyncMaterialDTO;
import cn.hsa.module.sync.material.service.SyncMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseMaterialManagementController
 * @Describe: 材料管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/syncMaterial")
@Slf4j
public class SyncMaterialController extends CenterBaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private SyncMaterialService syncMaterialService;



    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询材料信息
     * @param syncMaterialDTO 材料信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/9 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.SyncMaterialDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<SyncMaterialDTO> getById(SyncMaterialDTO syncMaterialDTO) {
        return syncMaterialService.getById(syncMaterialDTO);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部医嘱信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncMaterialDTO>> queryAll() {
        return syncMaterialService.queryAll();
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询材料信息表
     * @param syncMaterialDTO 材料信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/9 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncMaterialDTO syncMaterialDTO) {
        return syncMaterialService.queryPage(syncMaterialDTO);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条材料信息
     * @param syncMaterialDTO 材料信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/9 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncMaterialDTO syncMaterialDTO) {
        log.debug("syncMaterialDTO:{}", syncMaterialDTO);
        syncMaterialDTO.setCrteId(userId);
        syncMaterialDTO.setCrteName(userName);
        return this.syncMaterialService.save(syncMaterialDTO);
    }

    /**
    * @Menthod delete
    * @Desrciption  通过主键ID删除一条或多条材料信息
     * @param syncMaterialDTO 材料信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/9 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncMaterialDTO syncMaterialDTO) {
        return this.syncMaterialService.updateStatus(syncMaterialDTO);
    }


}
