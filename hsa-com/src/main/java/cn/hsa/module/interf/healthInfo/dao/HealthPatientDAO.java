package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.dao
 * @Class_name: HealthPatientDAO
 * @Describe: 基础健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 10:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthPatientDAO {

	/**
	 * @Description: 查询病人基础信息（档案信息base_profile_file）
	 * @Param: [map]
	 * @return: List<TbHzjbxx>
	 * @Author: liuliyun
	 * @Date: 2022-05-10
	 */
	List<TbHzjbxx> queryBaseProfile(Map map);

	/**
	 * @Description: 查询挂号信息（outpt_register）
	 * @Param: [map]
	 * @return: List<TbMzgh>
	 * @Author: liuliyun
	 * @Date: 2022-05-10
	 */
	List<TbMzgh> queryRegisterInfo(Map map);

	/**
	 * @Description: 查询就诊信息（outpt_visit）
	 * @Param: [map]
	 * @return: List<TbMzjzjl>
	 * @Author: liuliyun
	 * @Date: 2022-05-10 16:48
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
	 * @Description: 查询处方明细信息（outpt_prescribe_detail）
	 * @Param: [map]
	 * @return: List<TbMzcfmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-11 14:32
	 */
	List<TbMzcfmx> queryOutptPrescribeDetailInfo(Map map);


	/**
	 * @Description: 查询处方执行信息
	 * @Param: [map]
	 * @return: List<TbMzcfzxjl>
	 * @Author: liuliyun
	 * @Date: 2022-05-11 15:48
	 */
	List<TbMzcfzxjl> queryOutptPrescribeExecuteInfo(Map map);


	/**
	 * @Description: 查询门诊结算信息
	 * @Param: [map]
	 * @return: List<TbMzsfhz>
	 * @Author: liuliyun
	 * @Date: 2022-05-11 16:54
	 */
	List<TbMzsfhz> queryOutptSettleInfo(Map map);

	/**
	 * @Description: 查询门诊费用明细信息
	 * @Param: [map]
	 * @return: List<TbMzsfmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-11 19:32
	 */
	List<TbMzsfmx> queryOutptSettleCostDetailInfo(Map map);


	/**
	 * @Description: 查询住院就诊记录表
	 * @Param: [map]
	 * @return: List<TbZyjzjl>
	 * @Author: liuliyun
	 * @Date: 2022-05-12 11:56
	 */
	List<TbZyjzjl> queryInptVisitInfo(Map map);

	/**
	 * @Description: 查询住院转科记录
	 * @Param: [map]
	 * @return: List<TbZkjl>
	 * @Author: liuliyun
	 * @Date: 2022-05-12 14:50
	 */
	List<TbZkjl> queryInptTurnDeptInfo(Map map);

	/**
	 * @Description: 查询住院转科记录
	 * @Param: [map]
	 * @return: List<TbZyyzmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-12 20:12
	 */
	List<TbZyyzmx> queryInptAdviceInfo(Map map);

	/**
	 * @Description: 查询住院医嘱执行信息
	 * @Param: [map]
	 * @return: List<TbZyyzzxjl>
	 * @Author: liuliyun
	 * @Date: 2022-05-12 20:11
	 */
	List<TbZyyzzxjl> queryInptAdviceExecuteInfo(Map map);

	/**
	 * @Description: 查询住院收费明细信息
	 * @Param: [map]
	 * @return: List<TbZysfmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-13 10:59
	 */
	List<TbZysfmx> queryInptCostInfo(Map map);


	/**
	 * @Description: 查询住院收费结算信息
	 * @Param: [map]
	 * @return: List<TbZysfjs>
	 * @Author: liuliyun
	 * @Date: 2022-05-16 09:14
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
	 * @Date: 2022-05-16 14:16
	 */
	List<TbDzblsyxx> queryEmrIndexInfo(Map map);

	/**
	 * @Description: 查询—般护理记录信息
	 * @Param: [map]
	 * @return: List<TbEmrYbhljl>
	 * @Author: liuliyun
	 * @Date: 2022-05-16 15:59
	 */
	List<TbEmrYbhljl> queryInptNurseRecordInfo(Map map);

	/**
	 * @Description: 查询出院小结信息
	 * @Param: [map]
	 * @return: List<TbEmrCyxj>
	 * @Author: liuliyun
	 * @Date: 2022-05-17 09:32
	 */
	List<TbEmrCyxj> queryInptEmrOutInfo(Map map);

	/**
	 * @Description: 查询住院床位信息
	 * @Param: [map]
	 * @return: List<TbZycwjl>
	 * @Author: liuliyun
	 * @Date: 2022-05-17 11:24
	 */
	List<TbZycwjl> queryInptBedInfo(Map map);


	/**
	 * @Description: 查询住院病案主体信息
	 * @Param: [map]
	 * @return: List<TbBasyztb>
	 * @Author: liuliyun
	 * @Date: 2022-05-17 19:40
	 */
	List<TbBasyztb> queryInptMrisInfo(Map map);


	/**
	 * @Description: 查询住院病案首页--诊断信息
	 * @Param: [map]
	 * @return: List<TbBasyzdmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-18 10:46
	 */
	List<TbBasyzdmx> queryInptMrisDiagnoseInfo(Map map);

	/**
	 * @Description: 查询住院病案首页--手术信息
	 * @Param: [map]
	 * @return: List<TbBasyssmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-18 16:24
	 */
	List<TbBasyssmx> queryInptMrisOperInfo(Map map);




}
