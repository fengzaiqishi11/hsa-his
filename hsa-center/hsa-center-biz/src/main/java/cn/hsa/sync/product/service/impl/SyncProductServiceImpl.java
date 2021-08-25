package cn.hsa.sync.product.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.product.bo.SyncProductBO;
import cn.hsa.module.sync.product.dto.SyncProductDTO;
import cn.hsa.module.sync.product.service.SyncProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.sync.bmm.service.impl
 * @Class_name: BaseProductManagementServiceImpl
 * @Describe: 生产企业信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/sync/syncProduct")
@Slf4j
@Service("syncProductService_provider")
public class SyncProductServiceImpl extends HsafService implements SyncProductService {
    
    @Resource
    SyncProductBO syncProductBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询生产企业信息
     * @param syncProductDTO 生产企业信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/17 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.CenterProductDTO>
    **/
    @Override
    public WrapperResponse<SyncProductDTO> getById(SyncProductDTO syncProductDTO) {
        SyncProductDTO dto = syncProductBO.getById(syncProductDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @param syncProductDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/17 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<SyncProductDTO>> queryAll(SyncProductDTO syncProductDTO) {
        List<SyncProductDTO> dto = syncProductBO.queryAll(syncProductDTO);
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param syncProductDTO 生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncProductDTO syncProductDTO) {
        PageDTO dto = syncProductBO.queryPage(syncProductDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改生产企业
     * @param syncProductDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(SyncProductDTO syncProductDTO) {
        return WrapperResponse.success(syncProductBO.save(syncProductDTO));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个生产企业信息
     * @param syncProductDTO 生产企业数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncProductDTO syncProductDTO) {
        return WrapperResponse.success(syncProductBO.updateStatus(syncProductDTO));
    }
}
