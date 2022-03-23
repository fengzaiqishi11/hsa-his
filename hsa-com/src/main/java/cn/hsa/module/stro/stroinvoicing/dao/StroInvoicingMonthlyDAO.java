package cn.hsa.module.stro.stroinvoicing.dao;


import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDO;

public interface StroInvoicingMonthlyDAO {
    int deleteByPrimaryKey(String id);

    int insert(StroInvoicingMonthlyDO record);

    int insertSelective(StroInvoicingMonthlyDO record);

    StroInvoicingMonthlyDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StroInvoicingMonthlyDO record);

    int updateByPrimaryKey(StroInvoicingMonthlyDO record);
}