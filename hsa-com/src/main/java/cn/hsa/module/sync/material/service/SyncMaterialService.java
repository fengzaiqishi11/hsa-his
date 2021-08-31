package cn.hsa.module.sync.material.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.material.dto.SyncMaterialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sync.bmm.service
 * @Class_name: BaseMaterialManagementService
 * @Describe: 材料信息Service接口层（提供给dubbo调用）
 * @Author: powersi
 * @Date: 2020/7/7 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-sync")
public interface SyncMaterialService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询材料分类信息
     * @param syncMaterialDTO id 材料信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/7 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncMaterialDTO>
    **/
    @PostMapping("/service/sync/syncMaterial/getById")
    WrapperResponse<SyncMaterialDTO> getById(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有材料信息
     * @Author xingyu.xie
     * @Date   2020/7/7 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @PostMapping("/service/sync/syncMaterial/queryAll")
    WrapperResponse<List<SyncMaterialDTO>> queryAll();

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询材料信息
     * @param syncMaterialDTO 材料信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @PostMapping("/service/sync/syncMaterial/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod save
     * @Desrciption  新增或修改材料分类
     * @param syncMaterialDTO 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sync/syncMaterial/save")
    WrapperResponse<Boolean> save(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod updateList
     * @Desrciption 修改多条材料
     * @param map 材料分类数据参数对象和医院编码
     * @Author xingyu.xie
     * @Date   2020/8/24 15:32
     * @Return boolean
     **/
    @PostMapping("/service/sync/syncMaterial/updateList")
    WrapperResponse<Boolean> updateList(Map map);

    /**
    * @Menthod delete
    * @Desrciption   根据主键ID删除材料信息和
     * @param syncMaterialDTO  材料信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/7 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/sync/syncMaterial/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncMaterialDTO syncMaterialDTO);
}
