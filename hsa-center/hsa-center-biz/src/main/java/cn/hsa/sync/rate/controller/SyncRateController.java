package cn.hsa.sync.rate.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.rate.dto.SyncRateDTO;
import cn.hsa.module.sync.rate.service.SyncRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @PackageName: cn.hsa.module.base
 * @Class_name: BaseRateControllers
 * @Description: 医嘱频率控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 14:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/sync/rate")
@Slf4j
public class SyncRateController extends CenterBaseController {
    /**
     * 医嘱频率Dubbo消费者接口
     */
    @Resource
    private SyncRateService syncRateService_consumer;
    /**
     * @Method getById()
     * @Description 查询医嘱频率
     * @Param syncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<SyncRateDTO> getById(SyncRateDTO syncRateDTO) {
        return this.syncRateService_consumer.getById(syncRateDTO);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询医嘱频率
     * @Param
     * 1、syncRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncRateDTO syncRateDTO) { ;
        return this.syncRateService_consumer.queryPage(syncRateDTO);
    }

    /**
     * @Method queryAll()
     * @Description 查询医嘱频率
     * @Param
     * 1、syncRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncRateDTO>> queryAll(SyncRateDTO syncRateDTO) { ;
        return this.syncRateService_consumer.queryAll(syncRateDTO);
    }

    /**
     * @Method insert()
     * @Description 新增医嘱频率
     *
     * @Param
     * 1、syncRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 14:45
     * @Return Boolean
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody SyncRateDTO syncRateDTO){
        syncRateDTO.setCrteName(userName);
        syncRateDTO.setCrteId(userId);
        return this.syncRateService_consumer.insert(syncRateDTO);
    }
    /**
     * @Method update()
     * @Description 修改医嘱频率
     *
     * @Param
     * 1、syncRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return Boolean
     **/
    @PutMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody SyncRateDTO syncRateDTO){
        return this.syncRateService_consumer.update(syncRateDTO);
    }
    /**
     * @Method  updateIsValid()
     * @Description 根据医嘱频率数据参数对象()医嘱频率标识符
     *
     * @Param
     * 1、医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return Boolean
     **/
    @PostMapping("/updateIsValid")
    public WrapperResponse<Boolean> updateIsValid(@RequestBody SyncRateDTO syncRateDTO){
        return this.syncRateService_consumer.updateIsValid(syncRateDTO);
    }
}
