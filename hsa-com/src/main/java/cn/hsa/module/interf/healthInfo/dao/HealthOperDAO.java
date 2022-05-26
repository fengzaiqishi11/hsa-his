package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.dao
 * @Class_name: HealthPatientDAO
 * @Describe: 手术健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 10:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthOperDAO {

	/**
	 * @Description: 查询住院手术明细信息
	 * @Param: [map]
	 * @return: List<TbZdmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-19 15:34
	 */
	List<TbSsmx> queryInptOperInfo(Map map);


}
