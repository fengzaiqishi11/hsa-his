package cn.hsa.module.stro.stroinvoicing.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;

import java.util.List;

/**
 * @author ljh
 * @date 2020/07/30.
 */
public interface StroInvoicingBO {

    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、StroInvoicingDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return PageDTO
     **/
    PageDTO queryPage(StroInvoicingDTO stroInventoryDetail);
    /**
     * @Method queryPagerk()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、StroInvoicingDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return PageDTO
     **/
    PageDTO queryPagerk(StroInvoicingDTO stroInventoryDetail);

    /**
     * @Method queryPageck()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、StroInvoicingDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return PageDTO
     **/
    PageDTO queryPageck(StroInvoicingDTO stroInventoryDetail);

    /**
     * 保存进销存台账
     * @param stroInvoicingDTOList
     * @return
     */
    boolean  insertInvoicing(List<StroInvoicingDTO> stroInvoicingDTOList);
}
