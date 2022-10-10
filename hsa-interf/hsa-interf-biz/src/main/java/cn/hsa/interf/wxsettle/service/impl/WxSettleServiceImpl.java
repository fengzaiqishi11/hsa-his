package cn.hsa.interf.wxsettle.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.wxsettle.bo.WxSettleBO;
import cn.hsa.module.interf.wxsettle.service.WxSettleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.wxsettle.service.impl
 * @Class_name: WxSettleServiceImpl
 * @Describe: 微信门诊结算试算接口
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/6/28 19:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Service("/wxSettleService_provider")
@HsafRestPath("/service/interf/wxSettle")
public class WxSettleServiceImpl extends HsafService implements WxSettleService {

	@Resource
	private WxSettleBO wxSettleBO;

	/**
	 * @Description: 微信小程序门诊收费 试算接口
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/29 9:31
	 * @Return
	 */
	@Override
	public WrapperResponse<String> saveTestSettle(Map<String, Object> map) {
		return wxSettleBO.saveTestSettle(map);
	}

	/**
	 * @Description: 微信小程序门诊收费 结算接口
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/29 9:32
	 * @Return
	 */
	@Override
	public WrapperResponse<String> saveSettle(Map<String, Object> map) {
		return wxSettleBO.saveSettle(map);
	}


	/**
	 * @Description: 校验处方是否结算
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 09:10
	 * @Return WrapperResponse<String>
	 */
	@Override
	public WrapperResponse<String> checkPrescribeIsSettle(Map<String, Object> map) {
		return wxSettleBO.checkPrescribeIsSettle(map);
	}

	@Override
	public WrapperResponse<String> checkPrescribeCodeValid(Map<String, Object> map) {
		return wxSettleBO.checkPrescribeCodeIsValid(map);
	}
}
