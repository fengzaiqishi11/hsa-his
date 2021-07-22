package cn.hsa.module.sync.syncdrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncdrug.dto.SyncDrugDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.drug.service
 * @Class_name:: CenterDrugService
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface SyncDrugService {
    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.drug.dto.CenterDrugDTO>
     **/
    @GetMapping("/service/sync/drug/getById")
    WrapperResponse<SyncDrugDTO> getById(SyncDrugDTO syncDrugDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/sync/drug/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncDrugDTO syncDrugDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.drug.dto.CenterDrugDTO>>
     **/
    @GetMapping("/service/sync/drug/queryAll")
    WrapperResponse<List<SyncDrugDTO>> queryAll(SyncDrugDTO syncDrugDTO);

    /**
     * @Method delete
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/drug/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncDrugDTO syncDrugDTO);

    /**
     * @Method save
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/drug/save")
    WrapperResponse<Boolean> save(SyncDrugDTO syncDrugDTO);
}
