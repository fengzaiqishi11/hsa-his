package cn.hsa.module.stro.inventory.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.inventory.dto.InventoryDTO;
import cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.stro.inventory.service
 * @Class_name: InventoryService
 * @Description:  盘点点管理
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-stro")
public interface InventoryService {

    /**
     * @Method getById()
     * @Description 通过ID查询单条数据
     * @Param
     * Map map
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<InventoryDTO>
     **/
    @GetMapping("/service/stro/inventory/queryById")
    WrapperResponse<InventoryDTO> queryById(Map map);
    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * Map map
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<InventoryDTO>>
     **/

    @PostMapping("/service/stro/inventory/queryAll")
    WrapperResponse<List<InventoryDTO>> queryAll(Map map);
    /**
     * @Method insert()
     * @Description 新增
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/service/stro/inventory/insert")
    WrapperResponse<InventoryDTO> insert(Map map);
    /**
     * @Method update()
     * @Description 更新
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/
 
    @PostMapping("/service/stro/inventory/update")
    WrapperResponse<Boolean> update(Map map);


    /**
     * @Method examine()
     * @Description 审核
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/service/stro/inventory/examine")
    WrapperResponse<Boolean> updateAuditCode(Map map) throws Exception;

    /**
     * @Method queryPage()
     * @Description 批量更新
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/

    @GetMapping("/service/stro/inventory/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Method queryByIdDetail()
     * @Description 通过ID查询单条数据
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<StroInventoryDetailDTO>
     **/

    @GetMapping("/service/stro/inventory/queryByIdDetail")
    WrapperResponse<StroInventoryDetailDTO> queryByIdDetail (Map map);
    /**
     * @Method queryAllDetail()
     * @Description  通过实体作为筛选条件查询
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/service/stro/inventory/queryAllDetail")
    WrapperResponse<List<StroInventoryDetailDTO>> queryAllDetail (Map map);
    /**
     * @Method queryPageDetail()
     * @Description  分页查询
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return  WrapperResponse<PageDTO>
     **/

    @PostMapping("/service/stro/inventory/queryPageDetail")
    WrapperResponse<PageDTO> queryPageDetail (Map map);

    /**
     * @Method: printInventory
     * @Description: 盘点单打印接口
     * @Param: [inventoryDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/19 9:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.inventory.dto.InventoryDTO>
     **/
    @PostMapping("/service/stro/inventory/printInventory")
    WrapperResponse<List<InventoryDTO>> printInventory(Map map);
}
