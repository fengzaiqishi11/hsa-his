package cn.hsa.stro.stroinvoicing.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.stro.stroinvoicing.bo.StroInvoicingBO;
import cn.hsa.module.stro.stroinvoicing.dao.StroInvoicingDAO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ljh
 * @date 2020/07/30.
 */
@Component
@Slf4j
public class StroInvoicingBOImpl extends HsafBO implements StroInvoicingBO {
    @Resource
    StroInvoicingDAO stroInvoicingDAO;
    @Override
    public PageDTO queryPage(StroInvoicingDTO stroInventoryDetail) {
        PageHelper.startPage(stroInventoryDetail.getPageNo(), stroInventoryDetail.getPageSize());

        // 查询所有
        List<StroInvoicingDTO> list = stroInvoicingDAO.queryAll(stroInventoryDetail);

        // 返回分页结果
        return PageDTO.of(list);

    }

    /**
     * @param stroInventoryDetail
     * @Method queryPagerk()
     * @Description 通过实体作为筛选条件查询
     * @Param 1、StroInvoicingDTO 实例对象
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return PageDTO
     */
    @Override
    public PageDTO queryPagerk(StroInvoicingDTO stroInventoryDetail) {
        PageHelper.startPage(stroInventoryDetail.getPageNo(), stroInventoryDetail.getPageSize());

        // 查询所有
        List<StroInvoicingDTO> list = stroInvoicingDAO.queryAllrk(stroInventoryDetail);

        // 返回分页结果
        return PageDTO.of(list);

    }

    /**
     * @param stroInventoryDetail
     * @Method queryPageck()
     * @Description 通过实体作为筛选条件查询
     * @Param 1、StroInvoicingDTO 实例对象
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return PageDTO
     */
    @Override
    public PageDTO queryPageck(StroInvoicingDTO stroInventoryDetail) {
        PageHelper.startPage(stroInventoryDetail.getPageNo(), stroInventoryDetail.getPageSize());

        // 查询所有
        List<StroInvoicingDTO> list = stroInvoicingDAO.queryAllck(stroInventoryDetail);

        // 返回分页结果
        return PageDTO.of(list);

    }

    /**
     * 保存进销存台账信息
     * @param stroInvoicingDTOList
     * @return
     */
    @Override
    public boolean insertInvoicing(List<StroInvoicingDTO> stroInvoicingDTOList) {
        stroInvoicingDAO.insertInvoicing(stroInvoicingDTOList);
        return false;
    }
}
