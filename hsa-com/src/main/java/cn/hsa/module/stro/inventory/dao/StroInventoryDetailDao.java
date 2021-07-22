package cn.hsa.module.stro.inventory.dao;

import cn.hsa.module.stro.inventory.dto.InventoryDTO;
import cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.stro.inventory.dao
 * @Class_name:: StroInventoryDetailDao
 * @Description: 
 * @Author: ljh
 * @Date: 2020/8/11 8:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroInventoryDetailDao {


    /**
     * @Package_name: cn.hsa.module.stro.inventory.dao
     * @Class_name:: StroInventoryDetailDao
     * @Description: 通过ID查询单条数据
     * @Author: ljh
     * @Date: 2020/8/26 16:15
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    StroInventoryDetailDTO queryById(String id, String hospCode);

    /**
     * @Package_name: cn.hsa.module.stro.inventory.dao
     * @Class_name:: StroInventoryDetailDao
     * @Description: 通过实体作为筛选条件查询
     * @Author: ljh
     * @Date: 2020/8/26 16:15
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<StroInventoryDetailDTO> queryAll(StroInventoryDetailDTO stroInventoryDetailDTO);



    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id, String hospCode);



       /**
     * 批量新增所有列，列表长度不能为0，且列表id统一为null或者统一不为null
     *
     * @param list 实例对象list集合
     * @return 影响行数
     */

    int insertList(List<StroInventoryDetailDTO> list);



    /**
     * 批量查询
     *
     * @param list 实例对象list集合
     * @return 影响行数
     */
    List<StroInventoryDetailDTO> queryAllid(List<String> list,String hospCode);

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