package cn.hsa.module.outpt.card.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.card.service
 * @Class_name: BaseCardRechargeChangeService
 * @Describe: 一卡通余额相关查询
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/10 10:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "his-outpt")
public interface BaseCardRechargeChangeService {

	/**
	 * @Description: 查询一卡通余额与一卡通信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/10 11:27
	 * @Return
	 */
	WrapperResponse<BaseCardRechargeChangeDTO> getBaseCardRechargeChangeDTO(Map map);

	/**
	 * @Description: 更新一卡通充值消费异动表
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 8:47
	 * @Return
	 */
	WrapperResponse<Boolean> insertBaseCardRechargeChange(Map map);

	/**
	 * @Description: 更新一卡通主表余额
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 9:13
	 * @Return
	 */
	WrapperResponse<Boolean> updateCardAccountBalance(Map map);

	/**
	 * @Description: 门诊挂号退费，一卡通异动
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 16:04
	 * @Return
	 */
	Boolean saveOutptRegisterTuiFei(Map map);

	/**
	 * @Description: 门诊退费，一卡通异动
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/13 10:54
	 * @Return
	 */
	Boolean saveOutptTuiFei(Map map);
}
