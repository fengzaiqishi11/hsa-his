package cn.hsa.sync.syncparameter.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncparameter.dto.SyncParameterDTO;
import cn.hsa.module.sync.syncparameter.service.SyncParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.syncDate
 * @Class_name: syncParameterController
 * @Describe:  参数信息维护控制层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/9/2 9:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/sync/parameter")
@Slf4j
public class SyncParameterController extends CenterBaseController {
    /**
     * 参数信息维护dubbo消费者接口
     */
    @Resource
    private SyncParameterService syncParameterService;

    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [SyncParameterDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/9/2 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncParameterDTO syncParameterDTO){
        return syncParameterService.queryPage(syncParameterDTO);
    }
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     * [1. SyncParameterDTO]
     * @Author zhangxuan
     * @Date   2020/9/2 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.parameter.dto.syncParameterDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncParameterDTO>> queryAll(SyncParameterDTO syncParameterDTO){
        return syncParameterService.queryAll(syncParameterDTO);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1.[syncParameterDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/9/2 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody SyncParameterDTO syncParameterDTO){
        syncParameterDTO.setCrteId(userId);
        syncParameterDTO.setCrteName(userName);
        return syncParameterService.insert(syncParameterDTO);
    }


    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     * 1. [ids] 主键id
     * @Author zhangxuan
     * @Date   2020/9/2 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody SyncParameterDTO syncParameterDTO){
        return syncParameterService.delete(syncParameterDTO);
    }

    /**
     * @Menthod update()
     * @Desrciption  修改参数信息
     * @Param
     *  1. syncParameterDTO  参数数据对象
     * @Author zhangxuan
     * @Date   2020/9/2 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.parameter.dto.syncParameterDTO>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody SyncParameterDTO syncParameterDTO){
        return syncParameterService.update(syncParameterDTO);
    }

}
