package cn.hsa.module.interf.extract.dao;

import cn.hsa.module.interf.extract.dto.ExtractDataDTO;
import cn.hsa.module.interf.extract.entity.ExtractConsumptionDetailDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/6 11:31
 * 抽取Dao层
 */

public interface ExtractStroInvoicingDAO {
    /**
     * @Author gory
     * @Description 查询某报表类型的最近成功时间
     * 需要封装的参数：
     * 1.医院编码：hospCode
     * 2.抽取类型：extractType
     * 3.同步状态：extractStatus
     * @Date 2022/7/6 14:19
     * @Param [extractDataDTO]
     **/
    Date queryRecentlyExtractTime(ExtractDataDTO extractDataDTO);
    /**
     * @Author gory
     * @Description
     * map需要封装的参数
     * 1.hospCode: 医院编码
     * 2.recentlyTime: 同步时间
     * 3.includeInc:是否包含盘点
     * 4.同步表主表id
     * @Date 2022/7/6 16:53
     * @Param [requestMapIncludeIncMap]
     **/
    List<ExtractConsumptionDetailDO> queryStroInvoicingByComsume(Map<String, Object> requestMapIncludeIncMap);
    /**
     * @Author gory
     * @Description 插入同步主表
     * @Date 2022/7/7 9:04
     * @Param [addExtractDataDTO]
     **/
    void insertExtractData(ExtractDataDTO addExtractDataDTO);
    /**
     * @Author gory
     * @Description 插入消耗表
     * @Date 2022/7/7 9:06
     * @Param [extractConsumptionDetails]
     **/
    void insertBatchToConsumption(List<ExtractConsumptionDetailDO> extractConsumptionDetails);
}
