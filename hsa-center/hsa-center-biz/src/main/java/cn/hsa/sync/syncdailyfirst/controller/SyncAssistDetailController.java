package cn.hsa.sync.syncdailyfirst.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncassistdetail.dto.SyncAssistDetailDTO;
import cn.hsa.module.center.syncassistdetail.service.SyncAssistDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_ame: cn.hsa.module.base
 * @Class_name: BaseDailyfirstCalcController
 * @Description:  首日计费
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/base/BaseDailyfirstCalc")
@Slf4j
public class SyncAssistDetailController extends CenterBaseController {
    /**
     * 首日计费dubbo消费者接口
     */
    @Resource
    SyncAssistDetailService syncAssistDetailServiceService_consumer;
    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param
     * 1、SyncAssistDetailDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<SyncAssistDetailDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncAssistDetailDTO>> queryAll( SyncAssistDetailDTO syncAssistDetailDTO) {

        return syncAssistDetailServiceService_consumer.queryAll(syncAssistDetailDTO);
    }
    /**
     * @Method insert()s
     * @Description 更新
     * @Param
     * 1、SyncAssistDetailDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody List<SyncAssistDetailDTO> syncAssistDetailDTO) {
        for (int i = 0; i < syncAssistDetailDTO.size(); i++) {
            syncAssistDetailDTO.get(i).setCrteId(userId);
            syncAssistDetailDTO.get(i).setCrteName(userName);
        }

        return syncAssistDetailServiceService_consumer.insert(syncAssistDetailDTO);
    }
    /**
     * @Method deleteById()
     * @Description 删除
     * @Param
     * 1、SyncAssistDetailDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/deleteById")
    public WrapperResponse<Boolean> deleteById(@RequestBody SyncAssistDetailDTO syncAssistDetailDTO) {


        return syncAssistDetailServiceService_consumer.deleteById(syncAssistDetailDTO);
    }
    /**
     * @Method queryPage()
     * @Description 分页查询
     * @Param
     * 1、SyncAssistDetailDTO
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncAssistDetailDTO syncAssistDetailDTO) {

        return syncAssistDetailServiceService_consumer.queryPage(syncAssistDetailDTO);
    }
}