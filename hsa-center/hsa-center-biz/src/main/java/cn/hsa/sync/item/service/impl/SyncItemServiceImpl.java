package cn.hsa.sync.item.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncitem.bo.SyncItemBO;
import cn.hsa.module.sync.syncitem.dto.SyncItemDTO;
import cn.hsa.module.sync.syncitem.service.SyncItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.base.bi.service.impl
 * @Class_name: BaseItemImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 14:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sync/item")
@Slf4j
@Service("syncItemService_provider")
public class SyncItemServiceImpl extends HsafService implements SyncItemService {

    /**
     * 项目管理业务逻辑接口
     */
    @Resource
    private SyncItemBO syncItemBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 9:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.CenterItemDTO>
     **/
    @Override
    public WrapperResponse<SyncItemDTO> getById(SyncItemDTO syncItemDTO) {
        return WrapperResponse.success(syncItemBO.getById(syncItemDTO));
    }

    /**
     * @Method queryPage()
     * @Description 分页查询所有疾病信息(默认状态为有效)
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncItemDTO syncItemDTO) {
        return WrapperResponse.success(syncItemBO.queryPage(syncItemDTO));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有项目消息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/18 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bi.dto.CenterItemDTO>>
     **/
    @Override
    public WrapperResponse<List<SyncItemDTO>> queryAll(SyncItemDTO syncItemDTO) {
        return WrapperResponse.success(syncItemBO.queryAll(syncItemDTO));
    }

    /**
     * @Method delete()
     * @Description 删除单/多条疾病管理信息(修改状态为无效)
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncItemDTO syncItemDTO) {
        return WrapperResponse.success(syncItemBO.updateStatus(syncItemDTO));
    }

    /**
     * @Method save
     * @Desrciption 新增/修改单条疾病信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/24 16:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(SyncItemDTO syncItemDTO) {
        return WrapperResponse.success(syncItemBO.save(syncItemDTO));
    }
}
