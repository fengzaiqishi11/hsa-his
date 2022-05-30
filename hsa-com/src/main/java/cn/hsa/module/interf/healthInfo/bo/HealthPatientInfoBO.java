package cn.hsa.module.interf.healthInfo.bo;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.bo
 * @Class_name: HealthPatientInfoBO
 * @Describe: 基础健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthPatientInfoBO {
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

    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: List<TbZyjzjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:46
     */
    List<TbZyjzjl> queryInptVisitInfo(Map map);

    /**
     * @Description: 查询住院转科记录
     * @Param: [map]
     * @return: List<TbZkjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 14:57
     */
    List<TbZkjl> queryInptTurnDeptInfo(Map map);

    /**
     * @Description: 查询住院医嘱信息
     * @Param: [map]
     * @return: List<TbZyyzmx>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:07
     */
    List<TbZyyzmx> queryInptAdviceInfo(Map map);
    /**
     * @Description: 查询住院医嘱执行信息
     * @Param: [map]
     * @return: List<TbZyyzzxjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:07
     */
    List<TbZyyzzxjl> queryInptAdviceExecuteInfo(Map map);

    /**
     * @Description: 查询住院住院收费明细信息
     * @Param: [map]
     * @return: List<TbZysfmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    List<TbZysfmx> queryInptCostInfo(Map map);

    /**
     * @Description: 查询住院住院收费结算信息
     * @Param: [map]
     * @return: List<TbZysfjs>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 09:26
     */
    List<TbZysfjs> queryInptSettleInfo(Map map);


    /**
     * @Description: 查询住院护理三测单记录信息
     * @Param: [map]
     * @return: List<TbHlscdjl>
     * @Author: liuliyun
     * @Date: 2022-05-16 11:23
     */
    List<TbHlscdjl> queryInptThirdMeasurementInfo(Map map);

    /**
     * @Description: 查询电子病历索引信息
     * @Param: [map]
     * @return: List<TbDzblsyxx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 14:23
     */
    List<TbDzblsyxx> queryEmrIndexInfo(Map map);


    /**
     * @Description: 查询一般护理记录信息
     * @Param: [map]
     * @return: List<TbEmrYbhljl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 16:04
     */
    List<TbEmrYbhljl> queryInptNurseRecordInfo(Map map);

    /**
     * @Description: 查询出院小结信息
     * @Param: [map]
     * @return: List<TbEmrCyxj>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 09:35
     */
    List<TbEmrCyxj> queryInptEmrOutInfo(Map map);


    /**
     * @Description: 查询住院床位信息
     * @Param: [map]
     * @return: List<TbZycwjl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 11:34
     */
    List<TbZycwjl> queryInptBedInfo(Map map);

    /**
     * @Description: 查询住院病案首页主体信息
     * @Param: [map]
     * @return: List<TbBasyztb>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 19:44
     */
    List<TbBasyztb> queryInptMrisInfo(Map map);

    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: List<TbBasyzdmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:43
     */
    List<TbBasyzdmx> queryInptMrisDiagnoseInfo(Map map);


    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: List<TbBasyssmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:43
     */
    List<TbBasyssmx> queryInptMrisOperInfo(Map map);





}
