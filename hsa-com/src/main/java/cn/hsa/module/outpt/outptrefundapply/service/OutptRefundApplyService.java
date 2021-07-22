package cn.hsa.module.outpt.outptrefundapply.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptrefundapply.service
 * @Class_name: OutptRefundApplyService
 * @Describe: 门诊退费申请
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 15:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-outpt")
public interface OutptRefundApplyService {

	/**
	 * @Description: 保存门诊医生退费申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 15:28
	 * @Return
	 */
	WrapperResponse<Boolean> saveOutptRefundAppy(Map map);

	/**
	 * @Description: 查询当前登录账号创建的处方病人（已经结算）
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 11:18
	 * @Return
	 */
	WrapperResponse queryOutChargePage(Map param);

	@GetMapping(value = "/service/outpt/outptRefundApply/queryOutptPrescribes")
	WrapperResponse queryOutptPrescribes(Map param);

	/**
	 * @Description: 确认医生退费申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 16:26
	 * @Return
	 */
	@PostMapping(value = "/service/outpt/outptRefundApply/updateOutptRefundAppyStatus")
	WrapperResponse<Boolean> updateOutptRefundAppyStatus(Map<String, Object> param);
}
