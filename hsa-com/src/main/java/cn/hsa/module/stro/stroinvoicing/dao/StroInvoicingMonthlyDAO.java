package cn.hsa.module.stro.stroinvoicing.dao;


import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDO;

import java.util.List;

public interface StroInvoicingMonthlyDAO {
    int deleteByPrimaryKey(String id);

    int insert(StroInvoicingMonthlyDTO record);

    int insertSelective(StroInvoicingMonthlyDTO record);

    StroInvoicingMonthlyDTO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StroInvoicingMonthlyDTO record);

    int updateByPrimaryKey(StroInvoicingMonthlyDTO record);

    /**
     * @Meth: queryAllByDate
     * @Description: 查询这个月度的所有记录
     * @Param: [stroInvoicingMonthlyDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO>
     * @Author: zhangguorui
     * @Date: 2022/3/29
     */
    List<StroInvoicingMonthlyDTO> queryAllByDate(StroInvoicingMonthlyDTO stroInvoicingMonthlyDTO);

    int insertBatch(List<StroInvoicingMonthlyDTO> stroInvoicingMonthlyDTOS);
}