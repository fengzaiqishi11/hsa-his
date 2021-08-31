package cn.hsa.sync.mrisclassify.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.mrisclassify.bo.SyncMrisClassifyBO;
import cn.hsa.module.sync.mrisclassify.dto.SyncMrisClassifyDTO;
import cn.hsa.module.sync.mrisclassify.service.SyncMrisClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.sync.bmm.service.impl
 * @Class_name: SyncMrisClassifyManagementServiceImpl
 * @Describe: 病案费用归类信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/sync/syncMrisClassify")
@Slf4j
@Service("syncMrisClassifyService_provider")
public class SyncMrisClassifyServiceImpl extends HsafService implements SyncMrisClassifyService {
    @Resource
    SyncMrisClassifyBO syncMrisClassifyBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询病案费用归类信息
     * @param syncMrisClassifyDTO 病案费用归类信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/17 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncMrisClassifyDTO>
    **/
    @Override
    public WrapperResponse<SyncMrisClassifyDTO> getById(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        SyncMrisClassifyDTO dto = syncMrisClassifyBO.getById(syncMrisClassifyDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @param syncMrisClassifyDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/17 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<SyncMrisClassifyDTO>> queryAll(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        List<SyncMrisClassifyDTO> dto = syncMrisClassifyBO.queryAll(syncMrisClassifyDTO);
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param syncMrisClassifyDTO 病案费用归类分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        PageDTO dto = syncMrisClassifyBO.queryPage(syncMrisClassifyDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改病案费用归类
     * @param syncMrisClassifyDTO 病案费用归类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return WrapperResponse.success(syncMrisClassifyBO.save(syncMrisClassifyDTO));
    }

    /**
    * @Menthod updateSyncMrisClassifyS
    * @Desrciption  修改单条病案费用归类（有判空）
     * @param syncMrisClassifyDTO
    * @Author xingyu.xie
    * @Date   2020/9/17 15:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateSyncMrisClassifyS(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return WrapperResponse.success(syncMrisClassifyBO.updateSyncMrisClassifyS(syncMrisClassifyDTO));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个病案费用归类信息
     * @param syncMrisClassifyDTO 病案费用归类数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return WrapperResponse.success(syncMrisClassifyBO.updateStatus(syncMrisClassifyDTO));
    }
}
