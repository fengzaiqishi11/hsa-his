package cn.hsa.module.insure.stock.dao;

import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.stock.entity.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    int updateInsureGoodBuyBackBatch(@Param("list") List<InsureGoodBuyBack> listInsureGoodBuyBack);


    /**
     * 查询商品删除信息
     * @param insureGoodInfoDelete
     * @return
     */
    List<InsureGoodInfoDelete> queryInsureUploadDataPage(InsureGoodInfoDelete insureGoodInfoDelete);
    /**
     * 批量修改商品删除信息
     * @param map
     * @return
     */
    int deleteStockUpload(Map<String, Object> map);
    /**
     * 批量插入商品删除信息
     * @param insureGoodInfoDelete
     * @return
     */
    int insertStockUploadBatch(@Param("list") List<InsureGoodInfoDelete> insureGoodInfoDelete);


    /**
     * 查询商品销售信息
     * @param InsureGoodSell
     * @return
     */
    List<InsureGoodSell> queryInsureGoodSellPage(InsureGoodSell InsureGoodSell);

    /**
     * 查询药品销售信息
     * @param InsureGoodSell
     * @return
     */
    List<InsureGoodSell> queryInsureDrugSellPage(InsureGoodSell InsureGoodSell);

    /**
     * 查询耗材销售信息
     * @param InsureGoodSell
     * @return
     */
    List<InsureGoodSell> queryInsureMaterialSellPage(InsureGoodSell InsureGoodSell);
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
     * @param
     * @return
     */
    int updateInsureInventoryCheckBatch(@Param("fixmedinsBchnoList")List<String> fixmedinsBchnoList);


    /**
     * 查询商品库存变更 信息
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
    /**
     * @Meth: queryPersonList
     * @Description: 查询就诊 销售/ 退货
     * @Param: [insureRecruitPurchaseDTO]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    List<Map<String, Object>> queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);
    /**
     * @Meth: updateStatus
     * @Description: 根据id 批量更新进销存中的上传状态
     * @Param: [ids, hospCode]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    int updateStatus(@Param("ids") List<String> ids, @Param("hospCode") String hospCode,@Param("statusCode")String statusCode);

    void updateStockUpload(Map<String, Object> map);
    //查询已上传的商品
    List<InsureGoodInfoDelete> queryStockUpBatch(List<String> ids);

    List<InsureGoodSellBack> queryInsureDrugSellBackPage(InsureGoodSellBack insureGoodSellBack);

    List<InsureGoodSellBack> queryInsureMaterialSellBackPage(InsureGoodSellBack insureGoodSellBack);
}
