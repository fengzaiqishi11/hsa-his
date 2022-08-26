package cn.hsa.module.insure.stock.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.stock.entity.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.stock.service
 * @Class_name: InsureStockManagerBO
 * @Describe(描述): 新医保【库存管理】统一开放调用 BO 接口
 * @Author: Pengbo
 * @Date: 2021/08/21 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureStockManagerBO {

    /**
     * 查询商品采购信息
     * @param insureGoodBuy
     * @return
     */
    List<InsureGoodBuy> queryInsureGoodBuyPage(InsureGoodBuy insureGoodBuy);
    /**
     * 批量修改商品采购信息
     * @param map
     * @return
     */
    Boolean uploadInsureGoodBuy(Map<String, Object> map);


    /**
     * 查询商品采购退货信息
     * @param insureGoodBuyBack
     * @return
     */
    List<InsureGoodBuyBack> queryInsureGoodBuyBackPage(InsureGoodBuyBack insureGoodBuyBack);
    /**
     * 批量修改商品采购退货信息
     * @param map
     * @return
     */
    Boolean uploadInsureGoodBuyBack(Map<String, Object> map);


    /**
     * 查询商品删除信息
     * @param insureGoodInfoDelete
     * @return
     */
    List<InsureGoodInfoDelete> queryInsureGoodInfoDeletePage(InsureGoodInfoDelete insureGoodInfoDelete);
    /**
     * 批量修改商品删除信息
     * @param map
     * @return
     */
    Boolean uploadInsureGoodInfoDelete(Map<String, Object> map);


    /**
     * 查询商品销售信息
     * @param InsureGoodSell
     * @return
     */
    List<InsureGoodSell> queryInsureGoodSellPage(InsureGoodSell InsureGoodSell);
    /**
     * 查询商品销售信息-- 海南医保接口
     * @param InsureGoodSell
     * @return
     */
    List<InsureGoodSell> queryInsureGoodSellPageForHainan(InsureGoodSell InsureGoodSell);
    /**
     * 批量修改商品销售信息
     * @param map
     * @return
     */
    Boolean uploadInsureGoodSell(Map<String, Object> map);


    /**
     * 查询商品销售退货信息
     * @param insureGoodSellBack
     * @return
     */
    List<InsureGoodSellBack> queryInsureGoodSellBackPage(InsureGoodSellBack insureGoodSellBack);
    /**
     * 批量修改商品销售退货信息
     * @param map
     * @return
     */
    Boolean uploadInsureGoodSellBack(Map<String, Object> map);



    /**
     * 查询商品盘点信息
     *
     * @param insureInventoryCheck
     * @return
     */
    List<InsureInventoryCheck> queryInsureInventoryCheckPage(InsureInventoryCheck insureInventoryCheck);
    /**
     * 批量修改商品盘点信息
     * @param map
     * @return
     */
    Boolean uploadInsureInventoryCheck(Map<String, Object> map);


    /**
     * 查询商品库存变更信息
     * @param insureInventoryStockUpdate
     * @return
     */
    List<InsureInventoryStockUpdate> queryInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate);
    /**
     * 批量修改商品库存变更信息
     * @param map
     * @return
     */
    Boolean uploadInsureInventoryStock(Map<String, Object> map);
    /**
     * @Meth: queryPersonList
     * @Description: 查询销售或者退货人员
     * @Param: [map]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    PageDTO queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);

    List<InsureGoodSellBack> queryInsureGoodSellBackPageForHainan(InsureGoodSellBack insureGoodSellBack);
}
