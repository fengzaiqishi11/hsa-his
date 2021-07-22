package cn.hsa.sync.disease.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncdisease.bo.SyncDiseaseBO;
import cn.hsa.module.sync.syncdisease.dto.SyncDiseaseDTO;
import cn.hsa.module.sync.syncdisease.service.SyncDiseaseService;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.base.bd.service.impl
 * @Class_name: BaseDiseaseServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sync/disease")
@Slf4j
@Service("syncDiseaseService_provider")
public class SyncDiseaseServiceImpl extends HsafService implements SyncDiseaseService {
    /**
     * 疾病管理业务逻辑接口
     */
    @Resource
    private SyncDiseaseBO syncDiseaseBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取疾病信息
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 9:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
     **/
    @Override
    public WrapperResponse<SyncDiseaseDTO> getById(SyncDiseaseDTO syncDiseaseDTO) {
        return WrapperResponse.success(syncDiseaseBO.getById(syncDiseaseDTO));
    }

    /**
     * @Method queryPage()
     * @Description 分页查询所有疾病信息(默认状态为有效)
     *
     * @Param
     * [syncDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncDiseaseDTO syncDiseaseDTO) {
        return WrapperResponse.success(syncDiseaseBO.queryPage(syncDiseaseDTO));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有疾病消息
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/18 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>>
     **/
    @Override
    public WrapperResponse<List<SyncDiseaseDTO>> queryAll(SyncDiseaseDTO syncDiseaseDTO) {
        return WrapperResponse.success(syncDiseaseBO.queryAll(syncDiseaseDTO));
    }

    /**
     * @Method syncDiseaseDTO
     * @Description 修改审核状态
     *
     * @Param
     * [syncDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncDiseaseDTO syncDiseaseDTO) {
        return WrapperResponse.success(syncDiseaseBO.updateStatus(syncDiseaseDTO));
    }

    /**
     * @Method save
     * @Desrciption 新增/修改单条疾病信息
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/24 16:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(SyncDiseaseDTO syncDiseaseDTO) {
        return WrapperResponse.success(syncDiseaseBO.save(syncDiseaseDTO));
    }
}
