package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.healthInfo.bo.HealthPatientInfoBO;
import cn.hsa.module.interf.healthInfo.dao.HealthPatientDAO;
import cn.hsa.module.interf.healthInfo.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.service
 * @Class_name: HealthPatientInfoService
 * @Describe: 患者基础信息业务传输层接口
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class HealthInfoBoImpl extends HsafBO implements HealthPatientInfoBO {

    @Resource
    private HealthPatientDAO healthPatientDAO;

    /**
     * @Description: 查询患者基础信息（base_profile_file）
     * @Param: [map]
     * @return: List<TbHzjbxx>
     * @Author: liuliyun
     * @Date: 2022-05-10 15:48
     */
    @Override
    public List<TbHzjbxx> queryBaseProfile(Map map) {
        return healthPatientDAO.queryBaseProfile(map);
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
        return healthPatientDAO.queryRegisterInfo(map);
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
        return healthPatientDAO.queryOutptVisitInfo(map);
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
        return healthPatientDAO.queryPrescribeInfo(map);
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
        return healthPatientDAO.queryOutptPrescribeDetailInfo(map);
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
        return healthPatientDAO.queryOutptPrescribeExecuteInfo(map);
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
        return healthPatientDAO.queryOutptSettleInfo(map);
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
        return healthPatientDAO.queryOutptSettleCostDetailInfo(map);
    }

    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: List<TbZyjzjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:43
     */
    @Override
    public List<TbZyjzjl> queryInptVisitInfo(Map map) {
        return healthPatientDAO.queryInptVisitInfo(map);
    }


    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: List<TbZkjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:43
     */
    @Override
    public List<TbZkjl> queryInptTurnDeptInfo(Map map) {
        return healthPatientDAO.queryInptTurnDeptInfo(map);
    }

    /**
     * @Description: 查询住院医嘱信息
     * @Param: [map]
     * @return: List<TbZyyzmx>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:05
     */
    @Override
    public List<TbZyyzmx> queryInptAdviceInfo(Map map) {
        return healthPatientDAO.queryInptAdviceInfo(map);
    }


    /**
     * @Description: 查询住院医嘱执行记录
     * @Param: [map]
     * @return: List<TbZyyzzxjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:06
     */
    @Override
    public List<TbZyyzzxjl> queryInptAdviceExecuteInfo(Map map) {
        return healthPatientDAO.queryInptAdviceExecuteInfo(map);
    }

    /**
     * @Description: 按时间段查询住院收费明细
     * @Param: [map]
     * @return: List<TbZysfmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    @Override
    public List<TbZysfmx> queryInptCostInfo(Map map) {
        return healthPatientDAO.queryInptCostInfo(map);
    }


    /**
     * @Description: 按时间段查询住院收费结算信息
     * @Param: [map]
     * @return: List<TbZysfjs>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 09:22
     */
    @Override
    public List<TbZysfjs> queryInptSettleInfo(Map map) {
        return healthPatientDAO.queryInptSettleInfo(map);
    }

    /**
     * @Description: 按时间段查询住院护理三测单记录信息
     * @Param: [map]
     * @return: List<TbHlscdjl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 11:22
     */
    @Override
    public List<TbHlscdjl> queryInptThirdMeasurementInfo(Map map) {
        return healthPatientDAO.queryInptThirdMeasurementInfo(map);
    }


    /**
     * @Description: 查询电子病历索引信息
     * @Param: [map]
     * @return: List<TbDzblsyxx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 14:22
     */
    @Override
    public List<TbDzblsyxx> queryEmrIndexInfo(Map map) {
        return healthPatientDAO.queryEmrIndexInfo(map);
    }


    /**
     * @Description: 查询一般护理记录信息
     * @Param: [map]
     * @return: List<TbEmrYbhljl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 16:02
     */
    @Override
    public List<TbEmrYbhljl> queryInptNurseRecordInfo(Map map) {
        return healthPatientDAO.queryInptNurseRecordInfo(map);
    }


    /**
     * @Description: 查询出院小结信息
     * @Param: [map]
     * @return: List<TbEmrCyxj>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 09:34
     */
    @Override
    public List<TbEmrCyxj> queryInptEmrOutInfo(Map map) {
        return healthPatientDAO.queryInptEmrOutInfo(map);
    }

    /**
     * @Description: 查询住院床位信息
     * @Param: [map]
     * @return: List<TbZycwjl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 11:28
     */
    @Override
    public List<TbZycwjl> queryInptBedInfo(Map map) {
        return healthPatientDAO.queryInptBedInfo(map);
    }

    /**
     * @Description: 查询住院病案首页主体信息
     * @Param: [map]
     * @return: List<TbBasyztb>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 19:41
     */
    @Override
    public List<TbBasyztb> queryInptMrisInfo(Map map) {
        return healthPatientDAO.queryInptMrisInfo(map);
    }


    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: List<TbBasyzdmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:42
     */
    @Override
    public List<TbBasyzdmx> queryInptMrisDiagnoseInfo(Map map) {
        return healthPatientDAO.queryInptMrisDiagnoseInfo(map);
    }


    /**
     * @Description: 查询住院病案首页--手术信息
     * @Param: [map]
     * @return: List<TbBasyssmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 16:31
     */
    @Override
    public List<TbBasyssmx> queryInptMrisOperInfo(Map map) {
        return healthPatientDAO.queryInptMrisOperInfo(map);
    }


}
