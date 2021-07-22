package cn.hsa.sync.syncparameter.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncparameter.bo.SyncParameterBO;
import cn.hsa.module.sync.syncparameter.dto.SyncParameterDTO;
import cn.hsa.module.sync.syncparameter.service.SyncParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.sync.data.service.impl
 * @Class_name: syncParameterserviceImpl
 * @Describe:  参数service接口实现层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/9/2 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sync/parameter")
@Slf4j
@Service("syncParameterService_provider")
public class SyncParameterServiceImpl extends HsafService implements SyncParameterService {

    /**
     * 参数业务逻辑接口
     */
    @Resource
    private SyncParameterBO syncParameterBO;

    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询参数信息
     * @Param
     * 1. syncParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/9/2 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncParameterDTO syncParameterDTO) {
        PageDTO pageDTO = syncParameterBO.queryPage(syncParameterDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数接口
     * @Param
     * [1. syncParameterDTO]
     * @Author zhangxuan
     * @Date   2020/28 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sync.parameter.dto.syncParameterDTO>>
     **/
    @Override
    public WrapperResponse<List<SyncParameterDTO>> queryAll(SyncParameterDTO syncParameterDTO) {
        List<SyncParameterDTO> syncParameterDTOS = syncParameterBO.queryAll(syncParameterDTO);
        return WrapperResponse.success(syncParameterDTOS);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1. syncParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/9/2 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insert(SyncParameterDTO syncParameterDTO) {
        return WrapperResponse.success(syncParameterBO.insert(syncParameterDTO));
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除参数根据主键id
     * @Param
     * 1.map
     * @Author zhangxuan
     * @Date   2020/9/2 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> delete(SyncParameterDTO syncParameterDTO) {
        return WrapperResponse.success(syncParameterBO.delete(syncParameterDTO));
    }

    /**
     * @Menthod update()
     * @Desrciption 修改参数信息
     * @Param
     * 1. syncParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/9/2 9:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(SyncParameterDTO syncParameterDTO) {
        return WrapperResponse.success(syncParameterBO.update(syncParameterDTO));
    }
}
