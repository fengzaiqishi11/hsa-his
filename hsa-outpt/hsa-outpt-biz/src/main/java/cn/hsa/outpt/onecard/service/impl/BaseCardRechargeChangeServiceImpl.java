package cn.hsa.outpt.onecard.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.card.bo.BaseCardRechargeChangeBO;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.outpt.card.service.BaseCardRechargeChangeService;
import cn.hsa.util.MapUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.onecard.service.impl
 * @Class_name: BaseCardRechargeChangeServiceImpl
 * @Describe: 一卡通消费异动
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/10 10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/outpt/baseCardRechargeChange")
@Service("baseCardRechargeChangeService_provider")
public class BaseCardRechargeChangeServiceImpl implements BaseCardRechargeChangeService {

	@Resource
	private BaseCardRechargeChangeBO baseCardRechargeChangeBO;

	/**
	 * @Description: 查询一卡通余额与一卡通信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/10 11:30
	 * @Return
	 */
	@Override
	public WrapperResponse<BaseCardRechargeChangeDTO> getBaseCardRechargeChangeDTO(Map map) {
		return WrapperResponse.success(baseCardRechargeChangeBO.getBaseCardRechargeChangeDTO(MapUtils.get(map, "baseCardRechargeChangeDTO")));
	}

	/**
	 * @Description: 更新一卡通充值、消费异动表
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 8:47
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> insertBaseCardRechargeChange(Map map) {
		return WrapperResponse.success(baseCardRechargeChangeBO.insertBaseCardRechargeChange(MapUtils.get(map, "baseCardRechargeChangeDTO")));
	}

	/**
	 * @Description: 更新一卡通主表余额
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 9:14
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> updateCardAccountBalance(Map map) {
		return WrapperResponse.success(baseCardRechargeChangeBO.updateCardAccountBalance(map));
	}

	/**
	 * @Description: 门诊挂号退费一卡通退费处理
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 16:05
	 * @Return
	 */
	@Override
	public Boolean saveOutptRegisterTuiFei(Map map) {
		return baseCardRechargeChangeBO.saveOutptRegisterTuiFei(map);
	}

	/**
	 * @Description: 门诊收费退费一卡通异动
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/13 10:55
	 * @Return 
	 */
	@Override
	public Boolean saveOutptTuiFei(Map map) {
		return baseCardRechargeChangeBO.saveOutptTuiFei(map);
	}
}
