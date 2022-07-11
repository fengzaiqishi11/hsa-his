package cn.hsa.module.interf.extract.dao;

import cn.hsa.module.interf.extract.dto.ExtractDataDTO;
import cn.hsa.module.interf.extract.entity.ExtractBusinessDO;
import cn.hsa.module.interf.extract.entity.ExtractConsumptionDetailDO;
import cn.hsa.module.interf.extract.entity.ExtractStroInvoicingDetailDO;
import org.apache.ibatis.annotations.Param;

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
    void insertBatchToConsumption(@Param("extractConsumptionDetails") List<ExtractConsumptionDetailDO> extractConsumptionDetails);
    /**
     * @Author gory
     * @Description 查询实时进销存数据
     * @Date 2022/7/8 9:53
     * @Param [requestMapIncludeIncMap]
     **/
    List<ExtractStroInvoicingDetailDO> queryStroInvoicingByInvoic(Map<String, Object> requestMapIncludeIncMap);

    /**
     * @Author gory
     * @Description 批量插入药房药库实时进销存
     * @Date 2022/7/8 11:48
     * @Param [stroInvoicingDetails]
     **/
    void insertBatchStroInvoic(@Param("list") List<ExtractStroInvoicingDetailDO> stroInvoicingDetails);
    /**
     * @Author 西瓜先生
     * @Description 按供应商分组查询
     * 参数：
     *  1.hospCode:医院编码
     *  2.recentlyTime: 同步时间
     *  3.extractId: 同步主表id
     * @Date 2022/7/11 9:28
     * @Param [requestMapIncludeIncMap]
     **/
    List<ExtractBusinessDO> queryBusinessBySup(Map<String, Object> queryMap);
    /**
     * @Author 西瓜先生
     * @Description 批量插入extractBusiness
     * @Date 2022/7/11 9:41
     * @Param [extractBusinessList]
     **/
    void insertBatchBusiness(@Param("list") List<ExtractBusinessDO> extractBusinessList);
}
