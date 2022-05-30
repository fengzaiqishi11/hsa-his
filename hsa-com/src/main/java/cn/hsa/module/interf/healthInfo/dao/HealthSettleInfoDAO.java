package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.TbCwjsxx;
import cn.hsa.module.interf.healthInfo.entity.TbCwsffs;
import cn.hsa.module.interf.healthInfo.entity.TbCwsfmx;
import cn.hsa.module.interf.healthInfo.entity.TbZdmx;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.dao
 * @Class_name: HealthSettleInfoDAO
 * @Describe:  财务报告信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/20 15:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthSettleInfoDAO {

	/**
	 * @Description: 查询财务结算信息（日结缴款）
	 * @Param: [map]
	 * @return: List<TbCwjsxx>
	 * @Author: liuliyun
	 * @Date: 2022-05-20 15:33
	 */
	List<TbCwjsxx> queryCwSettleInfo(Map map);
	/**
	 * @Description: 查询财务结算详细信息（日结缴款）
	 * @Param: [map]
	 * @return: List<TbCwsfmx>
	 * @Author: liuliyun
	 * @Date: 2022-05-20 16:27
	 */
	List<TbCwsfmx> queryCwSettleDetailInfo(Map map);

	/**
	 * @Description: 查询财务结算-收费方式（日结缴款）
	 * @Param: [map]
	 * @return: List<TbCwsffs>
	 * @Author: liuliyun
	 * @Date: 2022-05-23 09:28
	 */
	List<TbCwsffs> queryCwPayInfo(Map map);



}
