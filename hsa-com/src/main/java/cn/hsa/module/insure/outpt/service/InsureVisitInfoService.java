package cn.hsa.module.insure.outpt.service;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @Class_name: InsureVisitInfo
 * @Describe: 医保患者信息
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/22 14:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-insure")
public interface InsureVisitInfoService {

	/**
	 * @Description: 获取医保患者信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/22 14:03
	 * @Return
	 */
	Map<String, Object> getInsureVisitInfo(Map<String, Object> params);

	/**
	 * @Description: 统一支付平台 医保目录下载
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:52
	 * @Return
	 */
	Map<String, Object> DOWN_3301(Map<String, Object> params);

	/**
	 * @Description: 统一支付平台 医保匹配目录上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:53
	 * @Return
	 */
	void UP_3302(Map<String, Object> params);

	/**
	 * @Description: 统一支付平台 医保已经匹配目录查询
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:53
	 * @Return
	 */
	Map<String, Object> DOWN_5163(Map<String, Object> params);
}
