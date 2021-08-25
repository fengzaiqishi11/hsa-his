package cn.hsa.module.sync.syncitem.dao;

import cn.hsa.module.sync.syncitem.dto.SyncItemDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.item.dao
 * @Class_name:: CenterItemDAO
 * @Description: 项目管理访问层接口
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncItemDAO {

    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:42
     * @Return cn.hsa.module.center.item.dto.CenterItemDTO
     **/
    SyncItemDTO getById(SyncItemDTO syncItemDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:42
     * @Return java.util.List<cn.hsa.module.center.item.dto.CenterItemDTO>
     **/
    List<SyncItemDTO> queryPage(SyncItemDTO syncItemDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:42
     * @Return java.util.List<cn.hsa.module.center.item.dto.CenterItemDTO>
     **/
    List<SyncItemDTO> queryAll(SyncItemDTO syncItemDTO);

    /**
     * @Method insert
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:42
     * @Return java.lang.Integer
     **/
    Integer insert(SyncItemDTO syncItemDTO);

    /**
     * @Method update
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:42
     * @Return java.lang.Integer
     **/
    Integer update(SyncItemDTO syncItemDTO);

    /**
     * @Method updateStatus
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:42
     * @Return java.lang.Integer
     **/
    Integer updateStatus(SyncItemDTO syncItemDTO);

    /**
     * @Method isCodeExist
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:42
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(SyncItemDTO syncItemDTO);
}