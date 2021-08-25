package cn.hsa.module.stro.inventory.dao;

import cn.hsa.module.stro.inventory.dto.InventoryDTO;

import java.util.List;
/**
 * @Package_name: cn.hsa.module.stro.inventory.dao
 * @Class_name:: InventoryDAO
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/20 8:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InventoryDAO {


    /**
     * @Package_name: cn.hsa.module.stro.inventory.dao
     * @Class_name:: InventoryDAO
     * @Description: 通过ID查询单条数据
     * @Author: ljh
     * @Date: 2020/8/24 9:07
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    InventoryDTO queryById(String id, String hospCode);

    /**
     * @Package_name: cn.hsa.module.stro.inventory.dao
     * @Class_name:: InventoryDAO
     * @Description: 通过实体作为筛选条件查询
     * @Author: ljh
     * @Date: 2020/8/24 9:07
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<InventoryDTO> queryAll(InventoryDTO inventoryDTO);

    /**
     * @Package_name: cn.hsa.module.stro.inventory.dao
     * @Class_name:: InventoryDAO
     * @Description: 新增
     * @Author: ljh
     * @Date: 2020/8/24 9:07
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insert(InventoryDTO inventoryDTO);
    /**
     * @Package_name: cn.hsa.module.stro.inventory.dao
     * @Class_name:: InventoryDAO
     * @Description: 修改
     * @Author: ljh
     * @Date: 2020/8/24 9:06
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int update(InventoryDTO inventoryDTO);
  /**
   * @Package_name: cn.hsa.module.stro.inventory.dao
   * @Class_name:: InventoryDAO
   * @Description:  审核
   * @Author: ljh
   * @Date: 2020/8/20 8:59
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   */
  int updateAuditCode(List<InventoryDTO> list,String hospCode);

    /**
     * @Package_name: cn.hsa.module.stro.inventory.dao
     * @Class_name:: InventoryDAO
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/7 17:11
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<InventoryDTO> queryByids(List<String> list,String hospCode);
}
