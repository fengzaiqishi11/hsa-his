package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.dao
 * @Class_name: HealthPatientDAO
 * @Describe: 门诊基础健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/19 17:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthOutptDAO {

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

}
