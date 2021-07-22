package cn.hsa.module.sync.syncparameter.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncparameter.dto.SyncParameterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.parameter.service
 * @Class_name: syncParameterservice
 * @Describe:  参数信息维护service接口层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/9/2 9:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-sync")
public interface SyncParameterService {
    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     *   map
     * @Author zhangxuan
     * @Date   2020/9/2 11:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/sync/parameter/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncParameterDTO syncParameterDTO);

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/9/2 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sync.parameter.dto.syncParameterDTO>>
     * @return*/
    @PostMapping("/service/sync/parameter/queryAll")
    WrapperResponse<List<SyncParameterDTO>> queryAll(SyncParameterDTO syncParameterDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/9/2 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.parameter.dto.syncParameterDTO>
     **/
    @PostMapping("/service/sync/parameter/insert")
    WrapperResponse<Boolean> insert(SyncParameterDTO syncParameterDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/9/2 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/parameter/delete")
    WrapperResponse<Boolean> delete(SyncParameterDTO syncParameterDTO);

    /**
     * @Menthod update()
     * @Desrciption  修改参数信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/9/2 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/parameter/update")
    WrapperResponse<Boolean> update(SyncParameterDTO syncParameterDTO);

}
