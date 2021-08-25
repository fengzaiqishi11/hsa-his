package cn.hsa.sync.disease.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncdisease.dto.SyncDiseaseDTO;
import cn.hsa.module.sync.syncdisease.service.SyncDiseaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name:: CenterDiseaseController
 * @Description: 疾病管理控制层
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/sync/disease")
@Slf4j
public class SyncDiseaseController extends CenterBaseController {
    /**
     * 疾病管理dubbo消费者接口
     */
    @Resource
    private SyncDiseaseService syncDiseaseService_consumer;

    /**
     * @Method getById
     * @Desrciption  通过id获取项目信息
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.disease.dto.CenterDiseaseDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SyncDiseaseDTO> getById(SyncDiseaseDTO syncDiseaseDTO) {
        return this.syncDiseaseService_consumer.getById(syncDiseaseDTO);
    }

    /**
     * @Method queryPage
     * @Desrciption  多条件查询信息(包括初始加载)
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncDiseaseDTO syncDiseaseDTO) {
        return this.syncDiseaseService_consumer.queryPage(syncDiseaseDTO);
    }

    /**
     * @Method queryAll
     * @Desrciption  查询所有疾病信息
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.disease.dto.CenterDiseaseDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncDiseaseDTO>> queryAll(SyncDiseaseDTO syncDiseaseDTO) {
        return this.syncDiseaseService_consumer.queryAll(syncDiseaseDTO);
    }

    /**
     * @Method save
     * @Desrciption  新增/修改单条疾病信息
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncDiseaseDTO syncDiseaseDTO) {
        syncDiseaseDTO.setCrteId(userId);
        syncDiseaseDTO.setCrteName(userName);
        return this.syncDiseaseService_consumer.save(syncDiseaseDTO);
    }

    /**
     * @Method delete
     * @Desrciption  删除单/多条疾病信息
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncDiseaseDTO syncDiseaseDTO) {
        return this.syncDiseaseService_consumer.updateStatus(syncDiseaseDTO);
    }
}
