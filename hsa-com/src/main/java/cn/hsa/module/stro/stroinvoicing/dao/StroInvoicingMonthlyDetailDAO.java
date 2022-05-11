package cn.hsa.module.stro.stroinvoicing.dao;


import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO;
import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
    /**
     * @Meth: getRecentlyStroPharInvoicingByDate
     * @Description: 查询药房最近一次进销存的数据
     * @Param: [pharInvoicingMonthlyDetailDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO>
     * @Author: zhangguorui
     * @Date: 2022/3/29
     */
    List<StroInvoicingMonthlyDetailDTO> getRecentlyStroPharInvoicingByDate(StroInvoicingMonthlyDetailDTO
                                                                                   pharInvoicingMonthlyDetailDTO);
    /**
     * @Meth: insertBatch
     * @Description: 插入明细表
     * @Param: [stroInvoicingDetails]
     * @return: void
     * @Author: zhangguorui
     * @Date: 2022/3/29
     */
    void insertBatch(@Param("stroInvoicingDetails") List<StroInvoicingMonthlyDetailDTO> stroInvoicingDetails);
    /**
     * @Author gory
     * @Description 删除当前日期的数据
     * @Date 2022/5/11 10:55
     * @Param [hospCode, nowDate]
     **/
    void deleteBatch(@Param("hospCode") String hospCode,@Param("nowDate") Date nowDate);
    /**
     * @Author gory
     * @Description 根据id查询明细数据
     * @Date 2022/5/11 20:14
     * @Param [stroInvoicingMonthlyDTO]
     **/
    List<StroInvoicingMonthlyDetailDTO> queryDetailByMonthlyId(StroInvoicingMonthlyDTO stroInvoicingMonthlyDTO);
}