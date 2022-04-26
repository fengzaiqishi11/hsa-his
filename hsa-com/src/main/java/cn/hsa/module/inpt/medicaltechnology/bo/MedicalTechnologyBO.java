package cn.hsa.module.inpt.medicaltechnology.bo;

import cn.hsa.base.PageDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.medicaltechnology.bo
 * @Class_name: MedicalTechnologyBO
 * @Describe: 住院医技确费实现层接口
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/12 11:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MedicalTechnologyBO {

	/**
	 * @Description: 查询lis或pass需要确费信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/12 11:35
	 * @Return
	 */
	PageDTO getLISorPASSNeedConfirmCost(Map<String, Object> map);

	/**
	 * @Description: 查询康复理疗需要确费的数据
	 * @Param:
	 * @Author: yuelong.chen
	 * @Email: yuelong.chen@powersi.com.cn
	 * @Date 2022/4/25 16:54
	 * @Return
	 */
	PageDTO getRecoveryConfirmCost(Map<String, Object> map);

	/**
	 * @Description: 确认医技收费，
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/13 22:41
	 * @Return
	 */
	boolean saveMwdicalTechnologyConfirmCost(Map<String, Object> map);

	/**
	 * @Description: 取消医技确费
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 9:57
	 * @Return
	 */
	boolean updateMedicalTechnologyConfirmCost(Map<String, Object> map);
}
