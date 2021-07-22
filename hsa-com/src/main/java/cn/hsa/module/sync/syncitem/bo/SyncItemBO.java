package cn.hsa.module.sync.syncitem.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.syncitem.dto.SyncItemDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.item.bo
 * @Class_name:: CenterItemBO
 * @Description: 项目管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncItemBO {

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
     * @Date   2020/8/5 17:41
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(SyncItemDTO syncItemDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:41
     * @Return java.util.List<cn.hsa.module.center.item.dto.CenterItemDTO>
     **/
    List<SyncItemDTO> queryAll(SyncItemDTO syncItemDTO);

    /**
     * @Method save
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:41
     * @Return java.lang.Boolean
     **/
    Boolean save(SyncItemDTO syncItemDTO);

    /**
     * @Method updateStatus
     * @Desrciption
     * @Param
     * [syncItemDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:41
     * @Return java.lang.Boolean
     **/
    Boolean updateStatus(SyncItemDTO syncItemDTO);
}