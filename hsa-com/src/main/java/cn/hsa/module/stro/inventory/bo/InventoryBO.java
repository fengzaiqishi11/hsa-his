package cn.hsa.module.stro.inventory.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.inventory.dto.InventoryDTO;
import cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.module.stro.inventory.bo
 * @Class_name: InventoryService
 * @Description:  盘点点管理
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InventoryBO {

    /**
     * @Method getById()
     * @Description 通过ID查询单条数据
     * @Param
     * id
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return <InventoryDTO>
     **/

    InventoryDTO queryById(String id, String hospCode);
    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * Map map
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return <List<InventoryDTO>>
     **/

    List<InventoryDTO> queryAll(InventoryDTO inventoryDTO);
    /**
     * @Method insert()
     * @Description 新增
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return <Boolean>
     **/

    InventoryDTO insert(InventoryDTO inventoryDTO);
    /**
     * @Method update()
     * @Description 更新
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return <Boolean>
     **/

    int update(InventoryDTO inventoryDTO);

    /**
     * @Method examine()
     * @Description 审核
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return <Boolean>
     **/

    boolean updateAuditCode(InventoryDTO inventoryDTO) throws Exception;


    /**
     * @Method queryPage()
     * @Description 查询
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return <PageDTO>
     **/

    PageDTO queryPage(InventoryDTO inventoryDTO);
    /**
     * @Method queryByIdDetail()
     * @Description 通过ID查询单条数据
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return <StroInventoryDetailDTO>
     **/

    StroInventoryDetailDTO queryByIdDetail(String id, String hospCode);
    /**
     * @Method queryAllDetail()
     * @Description  通过实体作为筛选条件查询
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return <Boolean>
     **/

    List<StroInventoryDetailDTO> queryAllDetail (StroInventoryDetailDTO stroInventoryDetailDTO);

    /**
     * @Method insertListDetail()
     * @Description  批量新增所有列
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return <Boolean>
     **/
    
    int insertListDetail(List<StroInventoryDetailDTO> list);

    /**
     * @Method queryPageDetail()
     * @Description  分页查询
     *
     * @Param
     * Map map
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return  <PageDTO>
     **/
    PageDTO queryPageDetail (StroInventoryDetailDTO stroInventoryDetailDTO);

    /**
     * @Method: printInventory
     * @Description: 盘点单打印接口
     * @Param: [inventoryDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/19 9:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.inventory.dto.InventoryDTO>
     **/
    List<InventoryDTO> printInventory(InventoryDTO inventoryDTO);
}
