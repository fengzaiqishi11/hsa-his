package cn.hsa.module.interf.healthInfo.bo;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.bo
 * @Class_name: HealthOutptInfoBO
 * @Describe: 门诊健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthOutptInfoBO {
    /**
     * @Description: 查询病人基础信息（档案信息base_profile_file）
     * @Param: [map]
     * @return: List<TbHzjbxx>
     * @Author: liuliyun
     * @Date: 2022/5/10 11:22
     */
    List<TbHzjbxx> queryBaseProfile(Map map);

    /**
     * @Description: 查询挂号信息（outpt_register）
     * @Param: [map]
     * @return: List<TbMzgh>
     * @Author: liuliyun
     * @Date: 2022/5/10 15:44
     */
    List<TbMzgh> queryRegisterInfo(Map map);


    /**
     * @Description: 查询就诊信息（outpt_visit）
     * @Param: [map]
     * @return: List<TbMzjzjl>
     * @Author: liuliyun
     * @Date: 2022/5/10 17:00
     */
    List<TbMzjzjl> queryOutptVisitInfo(Map map);

    /**
     * @Description: 查询处方信息（outpt_prescribe）
     * @Param: [map]
     * @return: List<TbMzcfzxx>
     * @Author: liuliyun
     * @Date: 2022-05-10 20:03
     */
    List<TbMzcfzxx> queryPrescribeInfo(Map map);

    /**
     * @Description: 查询处方明细（outpt_prescribe_detail）
     * @Param: [map]
     * @return: List<TbMzcfmx>
     * @Author: liuliyun
     * @Date: 2022-05-11 14:33
     */
    List<TbMzcfmx> queryOutptPrescribeDetailInfo(Map map);


    /**
     * @Description: 查询处方执行明细
     * @Param: [map]
     * @return: List<TbMzcfzxjl>
     * @Author: liuliyun
     * @Date: 2022-05-11 15:51
     */
    List<TbMzcfzxjl> queryOutptPrescribeExecuteInfo(Map map);

    /**
     * @Description: 查询门诊结算信息
     * @Param: [map]
     * @return: List<TbMzsfhz>
     * @Author: liuliyun
     * @Date: 2022-05-11 16:51
     */
    List<TbMzsfhz> queryOutptSettleInfo(Map map);

    /**
     * @Description: 查询门诊费用明细信息
     * @Param: [map]
     * @return: List<TbMzsfmx>
     * @Author: liuliyun
     * @Date: 2022-05-11 19:54
     */
    List<TbMzsfmx> queryOutptSettleCostDetailInfo(Map map);

}
