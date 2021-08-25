package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;

import cn.hsa.module.insure.module.bo.InsureRecruitPurchaseBO;
import cn.hsa.module.insure.module.dao.InsureRecruitPurchaseDAO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseBOImpl
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InsureRecruitPurchaseBOImpl extends HsafBO implements InsureRecruitPurchaseBO {

    @Resource
    private InsureRecruitPurchaseDAO insureRecruitPurchaseDAO;

    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public Map<String,Object> queryAll(Map<String,Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        return insureRecruitPurchaseDAO.queryAll(map);
    }
    /**
     * @Method insertinvChgMedinsInfo
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public void insertinvChgMedinsInfo() {

    }
    /**
     * @Method queryAll
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public void updateinvChgMedinsInfo() {

    }
    /**
     * @Method queryCommoditySalesRecord
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public Map<String,Object> queryCommoditySalesRecord(Map<String,Object> map){
        return insureRecruitPurchaseDAO.queryCommoditySalesRecord(map);
    }
    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public void insertCommoditySalesReturnRecord() {

    }
}
