package cn.hsa.module.interf.extract.dao;

import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;

import java.util.List;

/**
 * @author gory
 * @date 2022/7/12 13:40
 * 消费报表dao层
 */
public interface ExtraConsumptionDAO {
    /**
     * @Author gory
     * @Description 查询药房药库消耗报表
     * @Date 2022/7/12 13:42
     * @Param [extractConsumptionDTO]
     **/
    List<ExtractConsumptionDTO> queryExtractConsumptions(ExtractConsumptionDTO extractConsumptionDTO);
    /**
     * @Author gory
     * @Description 根据项目id，对数据进行汇总
     * @Date 2022/7/12 15:57
     * @Param [extractConsumptionDTO]
     **/
    List<ExtractConsumptionDTO> queryExtractConsumptionsByItemId(ExtractConsumptionDTO extractConsumptionDTO);
}
