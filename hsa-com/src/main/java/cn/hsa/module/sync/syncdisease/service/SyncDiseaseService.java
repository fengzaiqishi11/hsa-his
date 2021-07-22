package cn.hsa.module.sync.syncdisease.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncdisease.dto.SyncDiseaseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.disease.service
 * @Class_name:: CenterDiseaseService
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface SyncDiseaseService {

    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.disease.dto.CenterDiseaseDTO>
     **/
    @GetMapping("/service/sync/disease/getById")
    WrapperResponse<SyncDiseaseDTO> getById(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/sync/disease/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.disease.dto.CenterDiseaseDTO>>
     **/
    @RequestMapping("/service/sync/disease/queryAll")
    WrapperResponse<List<SyncDiseaseDTO>> queryAll(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method delete
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/disease/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method save
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 17:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/disease/save")
    WrapperResponse<Boolean> save(SyncDiseaseDTO syncDiseaseDTO);

}
