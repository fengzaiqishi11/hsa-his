package cn.hsa.sync.advice.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;
import cn.hsa.module.sync.advice.service.SyncAdviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseAdviceController
 * @Describe: 医嘱管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/14 19:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/syncAdvice")
@Slf4j
public class SyncAdviceController extends CenterBaseController {
    @Resource
    SyncAdviceService syncAdviceService;

//    /**
//     * 单据消费者
//     */
//    @Resource
//    private BaseOrderRuleService baseOrderRuleService;

    /**
     * @Menthod getById
     * @Desrciption 通过主键ID查询医嘱信息
     * @param syncAdviceDTO 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/15 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SyncAdviceDTO> getById(SyncAdviceDTO syncAdviceDTO) {

        return syncAdviceService.getById(syncAdviceDTO);
    }

    /**
     * @Menthod queryItemsByAdviceDetail
     * @Desrciption  根据医嘱编码查询其所有的项目详情
     * @param syncAdviceDTO 医嘱编码,医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/15 14:06
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     **/
    @GetMapping("/queryItemsByAdviceCode")
    public WrapperResponse<PageDTO> queryItemsByAdviceCode(SyncAdviceDTO syncAdviceDTO) {

        return syncAdviceService.queryItemsByAdviceCode(syncAdviceDTO);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部医嘱信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncAdviceDTO>> queryAll(SyncAdviceDTO syncAdviceDTO) {

        return syncAdviceService.queryAll(syncAdviceDTO);
    }

    /**
     * @Menthod queryPage
     * @Desrciption  通过数据传输对象分页查询医嘱信息表
     * @param syncAdviceDTO 医嘱信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncAdviceDTO syncAdviceDTO) {
        return syncAdviceService.queryPage(syncAdviceDTO);
    }

    /**
     * @Menthod insert
     * @Desrciption 插入单条医嘱信息
     * @param syncAdviceDTO 医嘱信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/15 9:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody SyncAdviceDTO syncAdviceDTO) {
        syncAdviceDTO.setCrteId(userId);
        syncAdviceDTO.setCrteName(userName);

        return this.syncAdviceService.insert(syncAdviceDTO);
    }

    /**
     * @Menthod update
     * @Desrciption 更新单条医嘱信息和多条项目
     * @param syncAdviceDTO 医嘱信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/15 9:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody SyncAdviceDTO syncAdviceDTO) {

        return this.syncAdviceService.update(syncAdviceDTO);
    }
    /**
     * @Menthod delete
     * @Desrciption  通过主键ID删除一条或多条医嘱信息
     * @param syncAdviceDTO 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/15 9:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncAdviceDTO syncAdviceDTO) {

        return this.syncAdviceService.updateStatus(syncAdviceDTO);
    }

    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption  查询和项目和材料二合一表
     * @param syncAdviceDetailDTO 医院编码，keyword等参数
     * @Author xingyu.xie
     * @Date   2020/7/15 9:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/queryItemAndMaterialPage")
    public WrapperResponse<PageDTO> queryItemAndMaterialPage( SyncAdviceDetailDTO syncAdviceDetailDTO) {
        return this.syncAdviceService.queryItemAndMaterialPage(syncAdviceDetailDTO);
    }
}
