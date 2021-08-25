package cn.hsa.module.insure.outpt.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.bo
 * @Class_name: InsureVisitInfoBO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/22 14:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

public interface InsureVisitInfoBO {

	/**
	 * @Description: 获取医保患者信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/22 14:10
	 * @Return
	 */
	Map<String, Object> getInsureVisitInfo (Map<String, Object> params);

	/**
	 * @Description: 统一支付平台 医保目录下载
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:58
	 * @Return 
	 */
	Map<String, Object> DOWN_3301(Map<String, Object> params);

	/**
	 * @Description: 统一支付平台 医保匹配目录上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:59
	 * @Return
	 */
	void UP_3302(Map<String, Object> params);

	/**
	 * @Description: 统一支付平台 医保已经完成匹配目录查询
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:59
	 * @Return
	 */
	Map<String, Object> DOWN_5163(Map<String, Object> params);
}
