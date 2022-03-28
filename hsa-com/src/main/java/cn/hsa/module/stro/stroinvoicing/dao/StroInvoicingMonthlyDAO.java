package cn.hsa.module.stro.stroinvoicing.dao;


import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDO;

import java.util.List;

public interface StroInvoicingMonthlyDAO {
    int deleteByPrimaryKey(String id);

    int insert(StroInvoicingMonthlyDO record);

    int insertSelective(StroInvoicingMonthlyDO record);

    StroInvoicingMonthlyDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StroInvoicingMonthlyDO record);

    int updateByPrimaryKey(StroInvoicingMonthlyDO record);

    List<StroInvoicingMonthlyDTO> getAllStroInvoicingByDate(StroInvoicingMonthlyDTO stroInvoicingMonthlyDTO);

    List<StroInvoicingMonthlyDTO> getRecentlyStroInvoicingByDate(StroInvoicingMonthlyDTO stroInvoicingMonthlyDTO);
}