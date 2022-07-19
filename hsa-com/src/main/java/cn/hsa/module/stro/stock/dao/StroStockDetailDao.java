package cn.hsa.module.stro.stock.dao;


import cn.hsa.module.stro.stock.dto.ItemProfitStatisticsDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;

import java.util.List;


/**
 * 表名含义：
 * stro：库房模块缩写，store仓库、room房间
 * stock：库存
 * (StroStockDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-27 20:21:13
 */
public interface StroStockDetailDao {


    /**
     * 通过实体作为筛选条件查询
     *
     * @param stroStockDetail 实例对象
     * @return 对象列表
     */
    List<StroStockDetailDTO> queryAllDrug(StroStockDetailDTO stroStockDetail);

    List<StroStockDetailDTO> queryAllDrugCancleNo(StroStockDetailDTO stroStockDetail);


    List<StroStockDetailDTO> queryAllMaterial(StroStockDetailDTO stroStockDetail);

    List<StroStockDetailDTO> queryAllMaterialCancleNo(StroStockDetailDTO stroStockDetail);

    List<StroStockDetailDTO> queryAll(StroStockDetailDTO stroStockDetail);

    List<StroStockDetailDTO> queryAllCancleNo(StroStockDetailDTO stroStockDetail);

    StroStockDetailDTO getStroStockDetailById(String id ,String hospCode);

    /**
     * @Method: queryByItemIdAndNo
     * @Description: 根据参数获取库存明细数据
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/16 17:02
     * @Return:
     **/
    StroStockDetailDTO queryByItemIdAndNo(String itemCode, String itemId, String batchNo, String bizId, String hospCode);

    /**
     * @Method: queryAllStroStock()
     * @Descrition: 根据医院编码查询库存明细中的所有数据
     * @Pramas: hospCode:医院编码
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/11/29
     * @Retrun: 库存明细数据集合
     */
    List<StroStockDetailDTO> queryAllStroStock(String param);

    /**
     * 查询门诊药品和材料利润统计信息
     * @param itemProfitStatisticsDTO
     * @return
     */
    List<ItemProfitStatisticsDTO> queryMZDrugAndMaterialProfit(ItemProfitStatisticsDTO itemProfitStatisticsDTO);

    /**
     * 查询住院药品和材料利润统计信息
     * @param itemProfitStatisticsDTO
     * @return
     */
    List<ItemProfitStatisticsDTO> queryZYDrugAndMaterialProfit(ItemProfitStatisticsDTO itemProfitStatisticsDTO);
    /**
     * @Author gory
     * @Description 查询门诊发药利润
     * @Date 2022/7/18 13:58
     * @Param [itemProfitStatisticsDTO]
     **/
    List<ItemProfitStatisticsDTO> queryNewMZDrugAndMaterialProfit(ItemProfitStatisticsDTO itemProfitStatisticsDTO);
    /**
     * @Author gory
     * @Description 按结算时间算利润
     * @Date 2022/7/18 15:34
     * @Param [itemProfitStatisticsDTO]
     **/
    List<ItemProfitStatisticsDTO> queryZYDrugAndMaterialProfitBySettleTime(ItemProfitStatisticsDTO itemProfitStatisticsDTO);
}