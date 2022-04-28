package cn.hsa.module.stro.stroinvoicing.dao;


import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDO;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
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
    /**
     * @Meth: updateByDate
     * @Description: 回写主表的值
     * @Param: [hospCode, date]
     * @return: void
     * @Author: zhangguorui
     * @Date: 2022/4/7
     */
    void updateByDate(@Param("hospCode") String hospCode,@Param("date") Date date);
    /**
     * @Meth: queryRecentlyUpdateTime
     * @Description: 查询最近同步的时间
     * @Param: [date, hospCode]
     * @return: java.sql.Date
     * @Author: zhangguorui
     * @Date: 2022/4/8
     */
    Date queryRecentlyUpdateTime(@Param("date") Date date,@Param("hospCode") String hospCode);
}