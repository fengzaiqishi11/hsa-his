package cn.hsa.interf.healthInfo.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.HealthPatientInfoBO;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.HealthPatientInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.interf.healthInfo.service.impl
 *@Class_name: HealthInfoServiceImpl
 *@Describe:  患者健康信息查询
 *@Author: liuliyun
 *@Eamil: liyun.liu@powersi.com
 *@Date: 2022/05/10 9:48
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/health/info")
@Slf4j
@Service("healthPatientInfoService_provider")
public class HealthInfoServiceImpl extends HsafService implements HealthPatientInfoService {

    @Resource
    HealthPatientInfoBO healthPatientInfoBO;

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
        List<TbHzjbxx> tbHzjbxxes = healthPatientInfoBO.queryBaseProfile(map);
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
        List<TbMzgh> tbMzghs = healthPatientInfoBO.queryRegisterInfo(map);
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
        List<TbMzjzjl>  outptRegisterInfoDOS= healthPatientInfoBO.queryOutptVisitInfo(map);
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
        List<TbMzcfzxx> tbMzcfzxxes = healthPatientInfoBO.queryPrescribeInfo(map);
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
        List<TbMzcfmx>  outptPrescribeInfoDOS= healthPatientInfoBO.queryOutptPrescribeDetailInfo(map);
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
        List<TbMzcfzxjl> tbMzcfzxjls = healthPatientInfoBO.queryOutptPrescribeExecuteInfo(map);
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
        List<TbMzsfhz>  outptPrecribeDetailExecuteInfoDOS= healthPatientInfoBO.queryOutptSettleInfo(map);
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
        List<TbMzsfmx> tbMzsfmxes = healthPatientInfoBO.queryOutptSettleCostDetailInfo(map);
        return WrapperResponse.success(tbMzsfmxes);
    }


    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: WrapperResponse<List<TbZyjzjl>>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:45
     */
    @Override
    public WrapperResponse<List<TbZyjzjl>> queryInptVisitInfo(Map map) {
        List<TbZyjzjl> tbZyjzjls = healthPatientInfoBO.queryInptVisitInfo(map);
        return WrapperResponse.success(tbZyjzjls);
    }

    /**
     * @Description: 查询住院转科信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZkjl>>
     * @Author: liuliyun
     * @Date: 2022-05-12 14:56
     */
    @Override
    public WrapperResponse<List<TbZkjl>> queryInptTurnDeptInfo(Map map) {
        List<TbZkjl> tbZkjls = healthPatientInfoBO.queryInptTurnDeptInfo(map);
        return WrapperResponse.success(tbZkjls);
    }


    /**
     * @Description: 查询住院医嘱信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZyyzmx>>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:08
     */
    @Override
    public WrapperResponse<List<TbZyyzmx>> queryInptAdviceInfo(Map map) {
        List<TbZyyzmx> tbZyyzmxes = healthPatientInfoBO.queryInptAdviceInfo(map);
        return WrapperResponse.success(tbZyyzmxes);
    }



    /**
     * @Description: 查询住院医嘱执行信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZyyzzxjl>>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:10
     */
    @Override
    public WrapperResponse<List<TbZyyzzxjl>> queryInptAdviceExecuteInfo(Map map) {
        List<TbZyyzzxjl> tbZyyzzxjls = healthPatientInfoBO.queryInptAdviceExecuteInfo(map);
        return WrapperResponse.success(tbZyyzzxjls);
    }


    /**
     * @Description: 查询住院收费明细信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZysfmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    @Override
    public WrapperResponse<List<TbZysfmx>> queryInptCostInfo(Map map) {
        List<TbZysfmx> tbZysfmxes = healthPatientInfoBO.queryInptCostInfo(map);
        return WrapperResponse.success(tbZysfmxes);
    }


    /**
     * @Description: 查询住院收费结算信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZysfjs>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    @Override
    public WrapperResponse<List<TbZysfjs>> queryInptSettleInfo(Map map) {
        List<TbZysfjs> tbZysfjs = healthPatientInfoBO.queryInptSettleInfo(map);
        return WrapperResponse.success(tbZysfjs);
    }


    /**
     * @Description: 查询住院三测单记录信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbHlscdjl>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 11:21
     */
    @Override
    public WrapperResponse<List<TbHlscdjl>> queryInptThirdMeasurementInfo(Map map) {
        List<TbHlscdjl> tbHlscdjls = healthPatientInfoBO.queryInptThirdMeasurementInfo(map);
        return WrapperResponse.success(tbHlscdjls);
    }


    /**
     * @Description: 查询电子病历索引信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbDzblsyxx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 14:23
     */

    @Override
    public WrapperResponse<List<TbDzblsyxx>> queryEmrIndexInfo(Map map) {
        List<TbDzblsyxx> tbDzblsyxxes = healthPatientInfoBO.queryEmrIndexInfo(map);
        return WrapperResponse.success(tbDzblsyxxes);
    }

    /**
     * @Description: 查询一般护理记录信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbEmrYbhljl>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 16:03
     */

    @Override
    public WrapperResponse<List<TbEmrYbhljl>> queryInptNurseRecordInfo(Map map) {
        List<TbEmrYbhljl> tbEmrYbhljls = healthPatientInfoBO.queryInptNurseRecordInfo(map);
        return WrapperResponse.success(tbEmrYbhljls);
    }


    /**
     * @Description: 查询出院小结信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbEmrCyxj>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 09:38
     */

    @Override
    public WrapperResponse<List<TbEmrCyxj>> queryInptEmrOutInfo(Map map) {
        List<TbEmrCyxj> tbEmrCyxjs = healthPatientInfoBO.queryInptEmrOutInfo(map);
        return WrapperResponse.success(tbEmrCyxjs);
    }


    /**
     * @Description: 查询住院床位信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZycwjl>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 11:31
     */

    @Override
    public WrapperResponse<List<TbZycwjl>> queryInptBedInfo(Map map) {
        List<TbZycwjl> tbZycwjls = healthPatientInfoBO.queryInptBedInfo(map);
        return WrapperResponse.success(tbZycwjls);
    }


    /**
     * @Description: 查询住院病案首页主体信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbBasyztb>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 19:42
     */

    @Override
    public WrapperResponse<List<TbBasyztb>> queryInptMrisInfo(Map map) {
        List<TbBasyztb> tbBasyztbs = healthPatientInfoBO.queryInptMrisInfo(map);
        return WrapperResponse.success(tbBasyztbs);
    }


    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbBasyzdmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:41
     */

    @Override
    public WrapperResponse<List<TbBasyzdmx>> queryInptMrisDiagnoseInfo(Map map) {
        List<TbBasyzdmx> tbBasyzdmxes = healthPatientInfoBO.queryInptMrisDiagnoseInfo(map);
        return WrapperResponse.success(tbBasyzdmxes);
    }


    /**
     * @Description: 查询住院病案首页--手术信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbBasyssmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 08:54
     */

    @Override
    public WrapperResponse<List<TbBasyssmx>> queryInptMrisOperInfo(Map map) {
        List<TbBasyssmx> tbBasyssmxes = healthPatientInfoBO.queryInptMrisOperInfo(map);
        return WrapperResponse.success(tbBasyssmxes);
    }





}
