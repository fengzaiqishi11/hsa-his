package cn.hsa.module.sync.syncdrug.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.syncdrug.dto.SyncDrugDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.drug.bo
 * @Class_name:: CenterDrugBO
 * @Description: 药品管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncDrugBO {

    /**
     * @Method getById
     * @Desrciption
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 17:38
     * @Return cn.hsa.module.center.drug.dto.CenterDrugDTO
     **/
    SyncDrugDTO getById(SyncDrugDTO syncDrugDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 17:38
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(SyncDrugDTO syncDrugDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 17:38
     * @Return java.util.List<cn.hsa.module.center.drug.dto.CenterDrugDTO>
     **/
    List<SyncDrugDTO> queryAll(SyncDrugDTO syncDrugDTO);

    /**
     * @Method save
     * @Desrciption
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 17:38
     * @Return java.lang.Boolean
     **/
    Boolean save(SyncDrugDTO syncDrugDTO);

    /**
     * @Method updateStatus
     * @Desrciption
     * @Param [syncDrugDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 17:38
     * @Return java.lang.Boolean
     **/
    Boolean updateStatus(SyncDrugDTO syncDrugDTO);
}