package cn.hsa.interf.wxsettle.controller;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
//import cn.hsa.interf.wxsettle.bo.impl.WxSettleBOImpl;
import cn.hsa.module.interf.wxsettle.service.WxSettleService;
import cn.hsa.util.AsymmetricEncryption;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.wxsettle.controller
 * @Class_name: WxSettleController
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/6/29 9:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/interf/wxSettle")
public class WxSettleController {

	@Resource
	private WxSettleService wxSettleService_consumer;

	/**
	 * @Description: 微信小程序门诊收费 试算接口
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/29 19:30
	 * @Return
	 */
	@PostMapping("/saveTestSettle")
	public WrapperResponse<String> saveTestSettle(@RequestBody Map<String, Object> paramMap) {
		String hospCode = (String) paramMap.get("hospCode");
		if (StringUtils.isEmpty(hospCode)) {
			throw new AppException("入参错误，请传入医院编码！");
		}
		String data = null;
		log.debug("微信小程序【门诊收费 试算】入参解密前：" + (String) paramMap.get("data"));
		try {
			data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
			log.debug("微信小程序【门诊收费 试算】入参解密后：" + (Map<String, Object>) JSON.parse(data));
		} catch (Exception e) {
			throw new AppException("【门诊收费 试算】入参错误，请联系管理员！" + e.getMessage());
		}
		if (StringUtils.isNotEmpty(data)) {
			paramMap.put("data", (Map<String, Object>) JSON.parse(data));
		}
		return wxSettleService_consumer.saveTestSettle(paramMap);
	}

	/**
	 * @Description: 微信小程序门诊收费  结算接口
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/29 19:32
	 * @Return 
	 */
	@PostMapping("/saveSettle")
	public WrapperResponse<String> saveSettle(@RequestBody Map<String, Object> paramMap){
		String hospCode = (String) paramMap.get("hospCode");
		if (StringUtils.isEmpty(hospCode)) {
			throw new AppException("入参错误，请传入医院编码！");
		}
		String data = null;
		log.debug("微信小程序【门诊收费  结算】入参解密前：" + (String) paramMap.get("data"));
		try {
			data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
			log.debug("微信小程序【门诊收费  结算】入参解密后：" + (Map<String, Object>) JSON.parse(data));
		} catch (Exception e) {
			throw new AppException("【门诊收费  结算】入参错误，请联系管理员！" + e.getMessage());
		}
		if (StringUtils.isNotEmpty(data)) {
			paramMap.put("data", (Map<String, Object>) JSON.parse(data));
		}
		return wxSettleService_consumer.saveSettle(paramMap);
	}

	/**
	 * @Description: 校验处方二维码是否失效
	 * @method: checkPrescribeIsSettle
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 09:02
	 * @Return WrapperResponse<String>
	 */
	@PostMapping("/checkPrescribeIsSettle")
	public WrapperResponse<String> checkPrescribeIsSettle(@RequestBody Map<String, Object> paramMap) {
		String hospCode = (String) paramMap.get("hospCode");
		if (StringUtils.isEmpty(hospCode)) {
			throw new AppException("入参错误，请传入医院编码！");
		}
		String data = null;
		log.debug("微信小程序【门诊收费 处方校验】入参解密前：" + (String) paramMap.get("data"));
		try {
			data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
			log.debug("微信小程序【门诊收费 处方校验】入参解密后：" + (Map<String, Object>) JSON.parse(data));
		} catch (Exception e) {
			throw new AppException("【门诊收费 处方校验】入参错误，请联系管理员！" + e.getMessage());
		}
		if (StringUtils.isNotEmpty(data)) {
			paramMap.put("data", (Map<String, Object>) JSON.parse(data));
		}
		return wxSettleService_consumer.checkPrescribeIsSettle(paramMap);
	}

	/**
	 * @Description: 校验处方二维码是否失效
	 * @method: checkPrescribeCodeValid
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 14:47
	 * @Return WrapperResponse<String>
	 */
	@PostMapping("/checkPrescribeCodeValid")
	public WrapperResponse<String> checkPrescribeCodeValid(@RequestBody Map<String, Object> paramMap) {
		String hospCode = (String) paramMap.get("hospCode");
		if (StringUtils.isEmpty(hospCode)) {
			throw new AppException("入参错误，请传入医院编码！");
		}
		String data = null;
		log.debug("微信小程序【门诊收费 处方校验】入参解密前：" + (String) paramMap.get("data"));
		try {
			data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
			log.debug("微信小程序【门诊收费 处方校验】入参解密后：" + (Map<String, Object>) JSON.parse(data));
		} catch (Exception e) {
			throw new AppException("【门诊收费 处方校验】入参错误，请联系管理员！" + e.getMessage());
		}
		if (StringUtils.isNotEmpty(data)) {
			paramMap.put("data", (Map<String, Object>) JSON.parse(data));
		}
		return wxSettleService_consumer.checkPrescribeCodeValid(paramMap);
	}
}
