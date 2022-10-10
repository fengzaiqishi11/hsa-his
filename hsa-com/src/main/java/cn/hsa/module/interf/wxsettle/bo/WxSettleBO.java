package cn.hsa.module.interf.wxsettle.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.wxsettle.bo
 * @Class_name: WxSettleBO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/6/28 19:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface WxSettleBO {

	/**
	 * @Description: 微信小程序门诊收费试算接口
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/28 19:32
	 * @Return
	 */
	WrapperResponse saveTestSettle(Map<String, Object> map);

	/**
	 * @Description: 微信小程序结算接口
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/28 19:32
	 * @Return
	 */
	WrapperResponse saveSettle(Map<String, Object> map);

	/**
	 * @Description: 校验处方状态
	 * @method: checkPrescribeIsSettle
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 09:11
	 * @Return WrapperResponse<String>
	 */
	WrapperResponse checkPrescribeIsSettle(Map<String, Object> map);

	/**
	 * @Description: 校验处方二维码有效性
	 * @method: checkPrescribeCodeIsValid
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 14:08
	 * @Return WrapperResponse<String>
	 */
	WrapperResponse checkPrescribeCodeIsValid(Map<String, Object> map);
}
