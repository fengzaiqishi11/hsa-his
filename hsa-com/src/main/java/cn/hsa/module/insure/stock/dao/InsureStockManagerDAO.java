package cn.hsa.module.insure.stock.dao;

import cn.hsa.module.insure.stock.entity.*;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.insure.stock.service
 * @Class_name: InsureStockManagerService
 * @Describe(描述): 新医保【库存管理】统一开放调用 DAO 接口
 * @Author: Pengbo
 * @Date: 2021/08/21 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureStockManagerDAO {

    /**
     * 查询商品采购信息
     * @param insureGoodBuy
     * @return
     */
    List<InsureGoodBuy> queryInsureGoodBuyPage(InsureGoodBuy insureGoodBuy);
    /**
     * 批量修改商品采购信息
     * @param listInsureGoodBuy
     * @return
     */
    int updateInsureGoodBuyBatch(List<InsureGoodBuy> listInsureGoodBuy);


    /**
     * 查询商品采购退货信息
     * @param insureGoodBuyBack
     * @return
     */
    List<InsureGoodBuyBack> queryInsureGoodBuyBackPage(InsureGoodBuyBack insureGoodBuyBack);
    /**
     * 批量修改商品采购退货信息
     * @param listInsureGoodBuyBack
     * @return
     */
    int updateInsureGoodBuyBackBatch(List<InsureGoodBuyBack> listInsureGoodBuyBack);


    /**
     * 查询商品删除信息
     * @param insureGoodInfoDelete
     * @return
     */
    List<InsureGoodInfoDelete> queryInsureGoodInfoDeletePage(InsureGoodInfoDelete insureGoodInfoDelete);
    /**
     * 批量修改商品删除信息
     * @param listInsureGoodInfoDelete
     * @return
     */
    int updateInsureGoodInfoDelete(List<InsureGoodInfoDelete> listInsureGoodInfoDelete);


    /**
     * 查询商品销售信息
     * @param InsureGoodSell
     * @return
     */
    List<InsureGoodSell> queryInsureGoodSellPage(InsureGoodSell InsureGoodSell);
    /**
     * 批量修改商品销售信息
     * @param listInsureGoodSell
     * @return
     */
    int updateInsureGoodSellBatch(List<InsureGoodSell> listInsureGoodSell);


    /**
     * 查询商品销售退货信息
     * @param insureGoodSellBack
     * @return
     */
    List<InsureGoodSellBack> queryInsureGoodSellBackPage(InsureGoodSellBack insureGoodSellBack);
    /**
     * 批量修改商品销售退货信息
     * @param listInsureGoodSellBack
     * @return
     */
    int updateInsureGoodSellBackBatch(List<InsureGoodSellBack> listInsureGoodSellBack);



    /**
     * 查询商品盘点信息
     * @param insureInventoryCheck
     * @return
     */
    List<InsureInventoryCheck> queryInsureInventoryCheckPage(InsureInventoryCheck insureInventoryCheck);
    /**
     * 批量修改商品盘点信息
     * @param listInsureInventoryCheck
     * @return
     */
    int updateInsureInventoryCheckBatch(List<InsureInventoryCheck> listInsureInventoryCheck);


    /**
     * 查询商品库存变更信息
     * @param insureInventoryStockUpdate
     * @return
     */
    List<InsureInventoryStockUpdate> queryInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate);
    /**
     * 批量修改商品库存变更信息
     * @param listInsureInventoryStockUpdate
     * @return
     */
    int updateInsureInventoryStockUpdateBatch(List<InsureInventoryStockUpdate> listInsureInventoryStockUpdate);
}