package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.healthInfo.bo.HealthOutptInfoBO;
import cn.hsa.module.interf.healthInfo.dao.HealthOutptDAO;
import cn.hsa.module.interf.healthInfo.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.healthInfo.bo.impl
 * @Class_name: HealthOutptBoImpl
 * @Describe: 门诊业务BO实现层
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class HealthOutptBoImpl extends HsafBO implements HealthOutptInfoBO {

    @Resource
    private HealthOutptDAO healthOutptDAO;

    /**
     * @Description: 查询患者基础信息（base_profile_file）
     * @Param: [map]
     * @return: List<TbHzjbxx>
     * @Author: liuliyun
     * @Date: 2022-05-10 15:48
     */
    @Override
    public List<TbHzjbxx> queryBaseProfile(Map map) {
        return healthOutptDAO.queryBaseProfile(map);
    }

    /**
     * @Description: 查询挂号信息（outpt_register）
     * @Param: [map]
     * @return: List<TbMzgh>
     * @Author: liuliyun
     * @Date: 2022-05-10 15:48
     */
    @Override
    public List<TbMzgh> queryRegisterInfo(Map map) {
        return healthOutptDAO.queryRegisterInfo(map);
    }


    /**
     * @Description: 查询就诊信息（outpt_visit）
     * @Param: [map]
     * @return: List<TbMzjzjl>
     * @Author: liuliyun
     * @Date: 2022-05-10 16:48
     */
    @Override
    public List<TbMzjzjl> queryOutptVisitInfo(Map map) {
        return healthOutptDAO.queryOutptVisitInfo(map);
    }

    /**
     * @Description: 查询门诊处方信息
     * @Param: [map]
     * @return: List<TbMzcfzxx>
     * @Author: liuliyun
     * @Date: 2022-05-10 19:58
     */
    @Override
    public List<TbMzcfzxx> queryPrescribeInfo(Map map) {
        return healthOutptDAO.queryPrescribeInfo(map);
    }

    /**
     * @Description: 查询门诊处方明细信息
     * @Param: [map]
     * @return: List<TbMzcfmx>
     * @Author: liuliyun
     * @Date: 2022-05-10 19:58
     */
    @Override
    public List<TbMzcfmx> queryOutptPrescribeDetailInfo(Map map) {
        return healthOutptDAO.queryOutptPrescribeDetailInfo(map);
    }

    /**
     * @Description: 查询门诊处方执行信息
     * @Param: [map]
     * @return: List<TbMzcfzxjl>
     * @Author: liuliyun
     * @Date: 2022-05-10 19:58
     */
    @Override
    public List<TbMzcfzxjl> queryOutptPrescribeExecuteInfo(Map map) {
        return healthOutptDAO.queryOutptPrescribeExecuteInfo(map);
    }


    /**
     * @Description: 查询门诊结算信息
     * @Param: [map]
     * @return: List<TbMzsfhz>
     * @Author: liuliyun
     * @Date: 2022-05-11 16:58
     */
    @Override
    public List<TbMzsfhz> queryOutptSettleInfo(Map map) {
        return healthOutptDAO.queryOutptSettleInfo(map);
    }


    /**
     * @Description: 查询门诊费用明细信息
     * @Param: [map]
     * @return: List<TbMzsfmx>
     * @Author: liuliyun
     * @Date: 2022-05-11 19:33
     */
    @Override
    public List<TbMzsfmx> queryOutptSettleCostDetailInfo(Map map) {
        return healthOutptDAO.queryOutptSettleCostDetailInfo(map);
    }

}
