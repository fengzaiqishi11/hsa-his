package cn.hsa.module.insure.module.bo;


import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseBO
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureRecruitPurchaseBO {
    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    Map<String,Object> queryAll(Map<String,Object> map);

    /**
     * @Method insertinvChgMedinsInfo
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    void insertinvChgMedinsInfo();
    /**
     * @Method queryAll
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    void updateinvChgMedinsInfo();
    /**
     * @Method queryCommoditySalesRecord
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    Map<String,Object> queryCommoditySalesRecord(Map<String,Object> map);
    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    void insertCommoditySalesReturnRecord();


}
