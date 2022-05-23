package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.dao
 * @Class_name: HealthPatientDAO
 * @Describe: 病案健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 10:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthMrisDAO {




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
