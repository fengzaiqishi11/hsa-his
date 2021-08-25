package cn.hsa.module.sync.orderreceive.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.orderreceive.dto.SyncOrderReceiveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.bmm.service
 * @Class_name: syncOrderReceiveManagementService
 * @Describe: 领药单据类型Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Date: 2020/09/17 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-sync")
public interface SyncOrderReceiveService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询领药单据类型分类信息
     * @param syncOrderReceiveDTO id 领药单据类型表主键ID
    * @Author xingyu.xie
    * @Date   2020/09/17 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncOrderReceiveDTO>
    **/
    @PostMapping("/service/sync/syncOrderReceive/getById")
    WrapperResponse<SyncOrderReceiveDTO> getById(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有领药单据类型
     * @param syncOrderReceiveDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @RequestMapping("/service/sync/syncOrderReceive/queryAll")
    WrapperResponse<List<SyncOrderReceiveDTO>> queryAll(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询领药单据类型
     * @param syncOrderReceiveDTO 领药单据类型数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @PostMapping("/service/sync/syncOrderReceive/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod save
     * @Desrciption  新增或修改领药单据类型(无判空)
     * @param syncOrderReceiveDTO 领药单据类型参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/syncOrderReceive/save")
    WrapperResponse<Boolean> save(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod save
     * @Desrciption  修改领药单据类型(有判空)
     * @param syncOrderReceiveDTO 领药单据类型参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/syncOrderReceive/updateSyncOrderReceiveS")
    WrapperResponse<Boolean> updateSyncOrderReceiveS(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID删除领药单据类型和
     * @param syncOrderReceiveDTO  领药单据类型参数对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/sync/syncOrderReceive/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncOrderReceiveDTO syncOrderReceiveDTO);
}
