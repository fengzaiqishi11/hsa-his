package cn.hsa.sync.drug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncdrug.bo.SyncDrugBO;
import cn.hsa.module.sync.syncdrug.dto.SyncDrugDTO;
import cn.hsa.module.sync.syncdrug.service.SyncDrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.drug.service.impl
 * @Class_name:: CenterDrugServiceImpl
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/6 8:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/sync/drug")
@Slf4j
@Service("syncDrugService_provider")
public class SyncDrugServiceImpl extends HsafService implements SyncDrugService {

    /**
     * 药品管理业务逻辑接口
     */
    @Resource
    private SyncDrugBO syncDrugBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取药品信息
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 9:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseDrugDTO>
     **/
    @Override
    public WrapperResponse<SyncDrugDTO> getById(SyncDrugDTO syncDrugDTO){
        return WrapperResponse.success(syncDrugBO.getById(syncDrugDTO));
    }

    /**
     * @Method queryPage()
     * @Description 分页查询所有药品信息(默认状态为有效)
     *
     * @Param
     * [syncDrugDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncDrugDTO syncDrugDTO) {
        return WrapperResponse.success(syncDrugBO.queryPage(syncDrugDTO));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有药品信息
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/18 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>>
     **/
    @Override
    public WrapperResponse<List<SyncDrugDTO>> queryAll(SyncDrugDTO syncDrugDTO) {
        return WrapperResponse.success(syncDrugBO.queryAll(syncDrugDTO));
    }

    /**
     * @Method updateStatus()
     * @Description 删除单/多条药品管理信息(修改状态为无效)
     *
     * @Param
     * [syncDrugDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/16 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncDrugDTO syncDrugDTO) {
        return WrapperResponse.success(syncDrugBO.updateStatus(syncDrugDTO));
    }

    /**
     * @Method save
     * @Desrciption 新增/修改单条药品信息
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/24 16:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(SyncDrugDTO syncDrugDTO) {
        return WrapperResponse.success(syncDrugBO.save(syncDrugDTO));
    }
}
