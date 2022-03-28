package cn.hsa.module.stro.stroinvoicing.dao;


import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO;
import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDetailDO;

import java.util.List;

public interface StroInvoicingMonthlyDetailDAO {
    int deleteByPrimaryKey(String id);

    int insert(StroInvoicingMonthlyDetailDO record);

    int insertSelective(StroInvoicingMonthlyDetailDO record);

    StroInvoicingMonthlyDetailDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StroInvoicingMonthlyDetailDO record);

    int updateByPrimaryKey(StroInvoicingMonthlyDetailDO record);
    /**
     * @Meth: getAllStroInvoicingByDate
     * @Description: 获得所有药库的进销存
     * @Param: [stroInvoicingMonthlyDetailDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO>
     * @Author: zhangguorui
     * @Date: 2022/3/28
     */
    List<StroInvoicingMonthlyDetailDTO> getAllStroInvoicingByDate(StroInvoicingMonthlyDetailDTO 
                                                                          stroInvoicingMonthlyDetailDTO);
    /**
     * @Meth: getRecentlyStroInvoicingByDate
     * @Description: 查询药库最近一次进销存的数据
     * @Param: [stroInvoicingMonthlyDetailDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO>
     * @Author: zhangguorui
     * @Date: 2022/3/28
     */
    List<StroInvoicingMonthlyDetailDTO> getRecentlyStroInvoicingByDate(StroInvoicingMonthlyDetailDTO
                                                                               stroInvoicingMonthlyDetailDTO);
    /**
     * @Meth: queryItemIdList
     * @Description: 查询所有库存项目id
     * @Param: [stroInvoicingMonthlyDetailDTO]
     * @return: java.util.List<java.lang.String>
     * @Author: zhangguorui
     * @Date: 2022/3/28
     */
    List<String> queryItemIdList(StroInvoicingMonthlyDetailDTO stroInvoicingMonthlyDetailDTO);
    /**
     * @Meth: getAllPharStroInvoicingByDate
     * @Description: 查询药房当日所有的进销存
     * @Param: [stroInvoicingMonthlyDetailDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO>
     * @Author: zhangguorui
     * @Date: 2022/3/28
     */
    List<StroInvoicingMonthlyDetailDTO> getAllPharStroInvoicingByDate(StroInvoicingMonthlyDetailDTO
                                                                              stroInvoicingMonthlyDetailDTO);
}