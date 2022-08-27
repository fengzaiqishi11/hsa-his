package cn.hsa.module.insure.module.dao;


import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.stock.entity.InsureGoodInfoDelete;
import cn.hsa.module.insure.stock.entity.InsureInventoryStockUpdate;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseDAO
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureRecruitPurchaseDAO {

    /**
     * @param
     * @Desrciption<!--    获取当前医院库存列表-->
     * @Param
     * @Author yuelong.chen
     * @Date 2021/8/24 10:22
     * @Return
     */
    Map<String,Object> queryAll(Map<String,Object> map);

    /**
     * @param
     * @Desrciption<!--    新增、修改当前医院库存表-->
     * @Param
     * @Author yuelong.chen
     * @Date 2021/8/24 10:22
     * @Return
     */
    void insertinvChgMedinsInfo(Map<String,Object> map);

    /**
     * @param
     * @Desrciption<!--    新增、修改当前医院库存表-->
     * @Param
     * @Author yuelong.chen
     * @Date 2021/8/24 10:22
     * @Return
     */
    void updateinvChgMedinsInfo(Map<String,Object> map);

    /**
     * @param
     * @Desrciption<!--    获取当前医院库存列表-->
     * @Param
     * @Author yuelong.chen
     * @Date 2021/8/24 10:22
     * @Return
     */
    Map<String,Object> queryCommoditySalesRecord(Map<String,Object> map);

    /**
     * @param
     * @Desrciption新增商品销售记录
     * @Param
     * @Author yuelong.chen
     * @Date 2021/8/24 10:22
     * @Return
     */
    Map<String,Object> insertCommoditySalesRecord(Map<String,Object> map);


    /**
     * @param
     * @Desrciption新增商品销售退货记录
     * @Param
     * @Author yuelong.chen
     * @Date 2021/8/24 10:22
     * @Return
     */
    Map<String,Object> insertCommoditySalesReturnRecord(Map<String,Object> map);

    // 查询存在【药品/材料】销售/退货记录的人员列表
    List<Map<String, Object>> queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);

    // 根据就诊id查询【门诊药品】销售列表
    List<Map<String, Object>> queryDrugList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO);

    // 更新费用表中上传状态
    int updateCostIsUpload(Map<String, Object> map);
    // 更新进销存的上传状态
    void updateStatus(List<String> ids, String hospCode, String statusCode);
    // 插入上传表
    void insertStockUploadBatch(List<InsureGoodInfoDelete> resultList);

    List<InsureGoodInfoDelete> queryStockUpBatch(List<String> ids);

    List<InsureInventoryStockUpdate> queryInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate);
}
