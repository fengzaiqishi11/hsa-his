package cn.hsa.interf.healthInfo.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.HealthOutptInfoBO;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.HealthOutptInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.interf.healthInfo.service.impl
 *@Class_name: HealthOutptServiceImpl
 *@Describe:  患者门诊健康信息查询
 *@Author: liuliyun
 *@Eamil: liyun.liu@powersi.com
 *@Date: 2022/05/10 9:48
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/health/outpt")
@Slf4j
@Service("healthOutptInfoService_provider")
public class HealthOutptServiceImpl extends HsafService implements HealthOutptInfoService {

    @Resource
    HealthOutptInfoBO healthOutptInfoBO;

    /**
    * @Method queryBaseProfile
    * @Desrciption 查询患者基本信息
    * @param map
    * @Author liuliyun
    * @Date   2022/05/10 9:52
    * @Return WrapperResponse<List<TbHzjbxx>>
    **/
    @Override
    public WrapperResponse<List<TbHzjbxx>> queryBaseProfile(Map map) {
        List<TbHzjbxx> tbHzjbxxes = healthOutptInfoBO.queryBaseProfile(map);
        return WrapperResponse.success(tbHzjbxxes);
    }

    /**
     * @Method queryRegisterInfo
     * @Desrciption 查询门诊挂号信息
     * @param map
     * @Author liuliyun
     * @Date   2022/05/10 9:52
     * @Return WrapperResponse<List<TbMzgh>>
     **/
    @Override
    public WrapperResponse<List<TbMzgh>> queryRegisterInfo(Map map) {
        List<TbMzgh> tbMzghs = healthOutptInfoBO.queryRegisterInfo(map);
        return WrapperResponse.success(tbMzghs);
    }

    /**
     * @Method queryRegisterInfo
     * @Desrciption 查询门诊挂号信息
     * @param map
     * @Author liuliyun
     * @Date   2022/05/11 9:52
     * @Return WrapperResponse<List<TbMzjzjl>>
     **/
    @Override
    public WrapperResponse<List<TbMzjzjl>> queryOutptVisitInfo(Map map) {
        List<TbMzjzjl>  outptRegisterInfoDOS= healthOutptInfoBO.queryOutptVisitInfo(map);
        return WrapperResponse.success(outptRegisterInfoDOS);
    }

    /**
     * @Description: 查询就诊信息（outpt_visit）
     * @Param: [map]
     * @return: WrapperResponse<List<TbMzcfzxx>>
     * @Author: liuliyun
     * @Date: 2022-05-10 19:58
     */
    @Override
    public WrapperResponse<List<TbMzcfzxx>> queryPrescribeInfo(Map map) {
        List<TbMzcfzxx> tbMzcfzxxes = healthOutptInfoBO.queryPrescribeInfo(map);
        return WrapperResponse.success(tbMzcfzxxes);
    }


    /**
     * @Description: 查询处方信息（outpt_prescribe_detail）
     * @Param: [map]
     * @return: WrapperResponse<List<TbMzcfmx>>
     * @Author: liuliyun
     * @Date: 2022-05-11 14:32
     */
    @Override
    public WrapperResponse<List<TbMzcfmx>> queryOutptPrescribeDetailInfo(Map map) {
        List<TbMzcfmx>  outptPrescribeInfoDOS= healthOutptInfoBO.queryOutptPrescribeDetailInfo(map);
        return WrapperResponse.success(outptPrescribeInfoDOS);
    }


    /**
     * @Description: 查询处方执行信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbMzcfzxjl>>
     * @Author: liuliyun
     * @Date: 2022-05-11 14:32
     */
    @Override
    public WrapperResponse<List<TbMzcfzxjl>> queryOutptPrescribeExecuteInfo(Map map) {
        List<TbMzcfzxjl> tbMzcfzxjls = healthOutptInfoBO.queryOutptPrescribeExecuteInfo(map);
        return WrapperResponse.success(tbMzcfzxjls);
    }

    /**
     * @Description: 查询门诊结算信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbMzsfhz>>
     * @Author: liuliyun
     * @Date: 2022-05-11 16:55
     */
    @Override
    public WrapperResponse<List<TbMzsfhz>> queryOutptSettleInfo(Map map) {
        List<TbMzsfhz>  outptPrecribeDetailExecuteInfoDOS= healthOutptInfoBO.queryOutptSettleInfo(map);
        return WrapperResponse.success(outptPrecribeDetailExecuteInfoDOS);
    }


    /**
     * @Description: 查询门诊结算信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbMzsfmx>>
     * @Author: liuliyun
     * @Date: 2022-05-11 16:55
     */
    @Override
    public WrapperResponse<List<TbMzsfmx>> queryOutptSettleCostDetailInfo(Map map) {
        List<TbMzsfmx> tbMzsfmxes = healthOutptInfoBO.queryOutptSettleCostDetailInfo(map);
        return WrapperResponse.success(tbMzsfmxes);
    }

}
