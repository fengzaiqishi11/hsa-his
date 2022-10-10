package cn.hsa.module.interf.wxsettle.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.wxsettle.service
 * @Class_name: WxSettleService
 * @Describe: 微信试算，结算接口
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/6/28 19:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-interf")
public interface WxSettleService {

	/**
	 * @Description: 微信小程序门诊收费试算接口
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/28 19:25
	 * @Return
	 */
	@PostMapping("/service/interf/wxsettle/saveTestSettle")
	WrapperResponse<String> saveTestSettle(@RequestBody Map<String, Object> map);

	/**
	 * @Description: 微信小程序结算接口
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/28 19:26
	 * @Return
	 */
	@PostMapping("/service/interf/wxsettle/saveSettle")
	WrapperResponse<String> saveSettle(@RequestBody Map<String, Object> map);

	/**
	 * @Description: 校验处方是否结算
	 * @method: checkPrescribeIsSettle
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 09:02
	 * @Return WrapperResponse<String>
	 */
	@PostMapping("/service/interf/wxsettle/checkPrescribeIsSettle")
	WrapperResponse<String> checkPrescribeIsSettle(@RequestBody Map<String, Object> map);


	/**
	 * @Description: 校验处方二维码是否失效
	 * @method: checkPrescribeCodeValid
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 14:43
	 * @Return WrapperResponse<String>
	 */
	@PostMapping("/service/interf/wxsettle/checkPrescribeCodeValid")
	WrapperResponse<String> checkPrescribeCodeValid(@RequestBody Map<String, Object> map);


}
