package cn.hsa.module.stro.stroinvoicing.dao;

import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;

import java.util.List;

/**
 * @author ljh
 * @date 2020/07/30.
 */
public interface StroInvoicingDAO {
    /**
     * 通过实体作为筛选条件查询
     *
     * @param stroInventoryDetail 实例对象
     * @return 对象列表
     */
    List<StroInvoicingDTO> queryAll(StroInvoicingDTO stroInventoryDetail);

/**
 * @Package_name: cn.hsa.module.stro.stroinvoicing.dao
 * @Class_name:: StroInvoicingDAO
 * @Description: 入库查询
 * @Author: ljh
 * @Date: 2020/8/12 14:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
    List<StroInvoicingDTO> queryAllrk(StroInvoicingDTO stroInventoryDetail);


    /**
     * @Package_name: cn.hsa.module.stro.stroinvoicing.dao
     * @Class_name:: StroInvoicingDAO
     * @Description:  出库查询
     * @Author: ljh
     * @Date: 2020/8/12 14:46
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<StroInvoicingDTO> queryAllck(StroInvoicingDTO stroInventoryDetail);

    /**
     * 保存进销存台账
     *
     * @param stroInvoicingDTOList 实例对象
     * @return 对象列表
     */
    int insertInvoicing(List<StroInvoicingDTO> stroInvoicingDTOList);
}
