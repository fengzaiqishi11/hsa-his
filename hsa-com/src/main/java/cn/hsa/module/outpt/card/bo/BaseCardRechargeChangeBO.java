package cn.hsa.module.outpt.card.bo;

import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.card.bo
 * @Class_name: BaseCardRechargeChangeBO
 * @Describe: 一卡通消费BO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/10 10:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseCardRechargeChangeBO {


	/**
	 * @Description: 查询一卡通余额与一卡通信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/10 11:31
	 * @Return
	 */
	public BaseCardRechargeChangeDTO getBaseCardRechargeChangeDTO(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);

	/**
	 * @Description: 更新一卡通充值、消费异动表
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 8:49
	 * @Return
	 */
	Boolean insertBaseCardRechargeChange(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);

	/**
	 * @Description: 更新一卡通主表余额
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 9:15
	 * @Return
	 */
	Boolean updateCardAccountBalance(Map map);

	/**
	 * @Description: 门诊挂号退费一卡通退费处理
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 16:06
	 * @Return
	 */
	Boolean saveOutptRegisterTuiFei(Map map);

	/**
	 * @Description: 门诊划价退费一卡通异动
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/13 10:56
	 * @Return
	 */
	Boolean saveOutptTuiFei(Map map);
}
