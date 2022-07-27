package cn.hsa.module.interf.extract.dao;

import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.module.interf.extract.dto.ExtractStroInvoicingDetailDTO;

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
    List<ExtractStroInvoicingDetailDTO> queryStroInvoicingBuy(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO);
    List<ExtractStroInvoicingDetailDTO> queryStroInvoicingSell(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO);

    List<ExtractStroInvoicingDetailDTO> queryRoomInvoicingBuy(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO);
    List<ExtractStroInvoicingDetailDTO> queryRoomInvoicingSell(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO);

    List<ExtractStroInvoicingDetailDTO> queryExtraInvoicingByItemId(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO);
    /**
     * @Author gory
     * @Description 查询出根据库存关联所有的最近的一条数据
     * @Date 2022/7/26 17:22
     * @Param [extractStroInvoicingDetailDTO]
     **/
    List<ExtractStroInvoicingDetailDTO> queryAllExtractData(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO);
}
