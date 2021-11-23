package cn.hsa.module.interf.outpt.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.outpt.bo
 * @Class_name: HaiNanDZPZPayCallBackBO
 * @Describe: 海南电子凭证回调
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/11/17 11:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HaiNanDZPZPayCallBackBO {

	/**
	 * @Description: 电子凭证支付回调，保存结算信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/11/17 11:42
	 * @Return
	 */
	void saveDZPZSettle(Map<String, String> map);
}
