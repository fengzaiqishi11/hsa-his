package cn.hsa.module.insure.module.dao;


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

}
