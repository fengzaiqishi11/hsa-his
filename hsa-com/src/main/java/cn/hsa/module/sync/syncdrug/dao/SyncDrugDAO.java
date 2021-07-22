package cn.hsa.module.sync.syncdrug.dao;

import cn.hsa.module.sync.syncdrug.dto.SyncDrugDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.drug.dao
 * @Class_name:: CenterDrugDAO
 * @Description: 药品管理访问层接口
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncDrugDAO {
    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:39
     * @Return cn.hsa.module.center.drug.dto.CenterDrugDTO
     **/
    SyncDrugDTO getById(SyncDrugDTO syncDrugDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:39
     * @Return java.util.List<cn.hsa.module.center.drug.dto.CenterDrugDTO>
     **/
    List<SyncDrugDTO> queryPage(SyncDrugDTO syncDrugDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:39
     * @Return java.util.List<cn.hsa.module.center.drug.dto.CenterDrugDTO>
     **/
    List<SyncDrugDTO> queryAll(SyncDrugDTO syncDrugDTO);

    /**
     * @Method insert
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:39
     * @Return java.lang.Integer
     **/
    Integer insert(SyncDrugDTO syncDrugDTO);

    /**
     * @Method update
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:39
     * @Return java.lang.Integer
     **/
    Integer update(SyncDrugDTO syncDrugDTO);

    /**
     * @Method delete
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:39
     * @Return java.lang.Integer
     **/
    Integer updateStatus(SyncDrugDTO syncDrugDTO);
}