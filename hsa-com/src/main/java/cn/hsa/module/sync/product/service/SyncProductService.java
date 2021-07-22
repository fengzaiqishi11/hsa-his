package cn.hsa.module.sync.product.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.product.dto.SyncProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.bmm.service
 * @Class_name: syncProductManagementService
 * @Describe: 生产企业信息Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Date: 2020/7/17 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-sync")
public interface SyncProductService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询生产企业分类信息
    * @param syncProductDTO id 生产企业信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.syncProductDTO>
    **/
    @PostMapping("/service/sync/syncProduct/getById")
    WrapperResponse<SyncProductDTO> getById(SyncProductDTO syncProductDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有生产企业信息
     * @param syncProductDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @RequestMapping("/service/sync/syncProduct/queryAll")
    WrapperResponse<List<SyncProductDTO>> queryAll(SyncProductDTO syncProductDTO);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询生产企业信息
     * @param syncProductDTO 生产企业信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @PostMapping("/service/sync/syncProduct/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncProductDTO syncProductDTO);

    /**
     * @Menthod save
     * @Desrciption  新增或修改生产企业
     * @param syncProductDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/syncProduct/save")
    WrapperResponse<Boolean> save(SyncProductDTO syncProductDTO);

    /**
    * @Menthod delete
    * @Desrciption   根据主键ID删除生产企业信息和
    * @param syncProductDTO  生产企业数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/17 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/sync/syncProduct/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncProductDTO syncProductDTO);
}
