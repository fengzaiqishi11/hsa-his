package cn.hsa.sync.orderreceive.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.orderreceive.bo.SyncOrderReceiveBO;
import cn.hsa.module.sync.orderreceive.dto.SyncOrderReceiveDTO;
import cn.hsa.module.sync.orderreceive.service.SyncOrderReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.sync.bmm.service.impl
 * @Class_name: SyncOrderReceiveManagementServiceImpl
 * @Describe: 领药单据类型信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/09/17 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/sync/syncOrderReceive")
@Slf4j
@Service("syncOrderReceiveService_provider")
public class SyncOrderReceiveServiceImpl extends HsafService implements SyncOrderReceiveService {
    @Resource
    SyncOrderReceiveBO syncOrderReceiveBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询领药单据类型信息
     * @param syncOrderReceiveDTO 领药单据类型信息表主键
    * @Author xingyu.xie
    * @Date   2020/09/17 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncOrderReceiveDTO>
    **/
    @Override
    public WrapperResponse<SyncOrderReceiveDTO> getById(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        SyncOrderReceiveDTO dto = syncOrderReceiveBO.getById(syncOrderReceiveDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @param syncOrderReceiveDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<SyncOrderReceiveDTO>> queryAll(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        List<SyncOrderReceiveDTO> dto = syncOrderReceiveBO.queryAll(syncOrderReceiveDTO);
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param syncOrderReceiveDTO 领药单据类型分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        PageDTO dto = syncOrderReceiveBO.queryPage(syncOrderReceiveDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改领药单据类型
     * @param syncOrderReceiveDTO 领药单据类型数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        return WrapperResponse.success(syncOrderReceiveBO.save(syncOrderReceiveDTO));
    }

    /**
    * @Menthod updateSyncOrderReceiveS
    * @Desrciption  修改单条领药单据类型（有判空）
     * @param syncOrderReceiveDTO
    * @Author xingyu.xie
    * @Date   2020/9/17 15:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateSyncOrderReceiveS(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        return WrapperResponse.success(syncOrderReceiveBO.updateSyncOrderReceiveS(syncOrderReceiveDTO));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个领药单据类型信息
     * @param syncOrderReceiveDTO 领药单据类型数据传输对象
    * @Author xingyu.xie
    * @Date   2020/09/17 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        return WrapperResponse.success(syncOrderReceiveBO.updateStatus(syncOrderReceiveDTO));
    }
}
