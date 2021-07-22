package cn.hsa.module.sync.mrisclassify.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.mrisclassify.dto.SyncMrisClassifyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.bmm.service
 * @Class_name: syncMrisClassifyManagementService
 * @Describe: 病案费用归类Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Date: 2020/09/17 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-sync")
public interface SyncMrisClassifyService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询病案费用归类分类信息
     * @param syncMrisClassifyDTO id 病案费用归类表主键ID
    * @Author xingyu.xie
    * @Date   2020/09/17 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncMrisClassifyDTO>
    **/
    @PostMapping("/service/sync/syncMrisClassify/getById")
    WrapperResponse<SyncMrisClassifyDTO> getById(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有病案费用归类
     * @param syncMrisClassifyDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @RequestMapping("/service/sync/syncMrisClassify/queryAll")
    WrapperResponse<List<SyncMrisClassifyDTO>> queryAll(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询病案费用归类
     * @param syncMrisClassifyDTO 病案费用归类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @PostMapping("/service/sync/syncMrisClassify/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
     * @Menthod save
     * @Desrciption  新增或修改病案费用归类(无判空)
     * @param syncMrisClassifyDTO 病案费用归类参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/syncMrisClassify/save")
    WrapperResponse<Boolean> save(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
     * @Menthod save
     * @Desrciption  修改病案费用归类(有判空)
     * @param syncMrisClassifyDTO 病案费用归类参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/syncMrisClassify/updateSyncMrisClassifyS")
    WrapperResponse<Boolean> updateSyncMrisClassifyS(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID删除病案费用归类和
     * @param syncMrisClassifyDTO  病案费用归类参数对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/sync/syncMrisClassify/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncMrisClassifyDTO syncMrisClassifyDTO);
}
