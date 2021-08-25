package cn.hsa.module.outpt.card.dao;

import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.card.dao
 * @Class_name: BaseCardRechargeChangeDAO
 * @Describe: 一卡通相关查询
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/10 10:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseCardRechargeChangeDAO {

	/**
	 * @Description: 查询一卡通余额与相关信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/10 11:54
	 * @Return
	 */
	BaseCardRechargeChangeDTO getBaseCardRechargeChangeDTO(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);

	/**
	 * @Description: 更新一卡通消费异动表
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 8:53
	 * @Return 
	 */
	int insertBaseCardRechargeChange(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);

	/**
	 * @Description: 使用一卡通结算后更新一卡通主表余额
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 15:46
	 * @Return
	 */
	int updateCardAccountBalance(Map map);

	/**
	 * @Description: 根据挂号id查询一卡通异动记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 17:26
	 * @Return
	 */
	BaseCardRechargeChangeDTO selectRegisterBaseCardRechargeChange(Map map);

	/**
	 * @Description: 根据卡id查询最新的异动信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 16:36
	 * @Return
	 */
	BaseCardRechargeChangeDTO selectLastChangeByCardId(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);

	/**
	 * @Description: 根据结算id查询门诊划价收费结算费用时，一卡通异动记录
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/13 11:00
	 * @Return 
	 */
	BaseCardRechargeChangeDTO selectOutptBaseCardRechargeChange(Map map);
}
