package cn.hsa.outpt.onecard.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.card.bo.BaseCardRechargeChangeBO;
import cn.hsa.module.outpt.card.dao.BaseCardRechargeChangeDAO;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.onecard.bo.impl
 * @Class_name: BaseCardRechargeChangeBOImpl
 * @Describe: 一卡通消费异动相关逻辑层
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/10 10:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseCardRechargeChangeBOImpl implements BaseCardRechargeChangeBO {

	@Resource
	private BaseCardRechargeChangeDAO baseCardRechargeChangeDao;

	/**
	 * @Description: 查询一卡通余额与一卡通相关信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/10 11:34
	 * @Return
	 */
	@Override
	public BaseCardRechargeChangeDTO getBaseCardRechargeChangeDTO(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO) {
		return baseCardRechargeChangeDao.getBaseCardRechargeChangeDTO(baseCardRechargeChangeDTO);
	}

	/**
	 * @Description: 更新一卡通消费异动表，
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 8:51
	 * @Return
	 */
	@Override
	public Boolean insertBaseCardRechargeChange(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO) {
		return baseCardRechargeChangeDao.insertBaseCardRechargeChange(baseCardRechargeChangeDTO) > 0;
	}

	/**
	 * @Description: 更新一卡通主表余额
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 9:16
	 * @Return
	 */
	@Override
	public Boolean updateCardAccountBalance(Map map) {
		return baseCardRechargeChangeDao.updateCardAccountBalance(map) > 0;
	}

	/**
	 * @Description: 门诊退费一卡通退费处理
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/12 16:07
	 * @Return
	 */
	@Override
	public Boolean saveOutptRegisterTuiFei(Map map) {
		// 支付异动信息
		BaseCardRechargeChangeDTO baseCardRechargeChangeDTO = baseCardRechargeChangeDao.selectRegisterBaseCardRechargeChange(map);
		// 根据卡id插叙最新的异动信息
		BaseCardRechargeChangeDTO lastDto = baseCardRechargeChangeDao.selectLastChangeByCardId(baseCardRechargeChangeDTO);
		if (lastDto == null || !BigDecimalUtils.equals(lastDto.getEndBalance(), lastDto.getAccountBalance())) {
			throw new AppException("一卡通余额与上次异动后金额不一致，请联系管理员");
		}
		lastDto.setId(SnowflakeUtils.getId());
		lastDto.setStatusCode("8"); // 卡异动状态 8： 消费
		lastDto.setPayCode(null);  // 支付方式
		BigDecimal price = BigDecimalUtils.negate(baseCardRechargeChangeDTO.getPrice());
		lastDto.setPrice(price);
		lastDto.setStartBalance(lastDto.getEndBalance());
		lastDto.setStartBalanceEncryption(lastDto.getStartBalanceEncryption());
		lastDto.setEndBalance(BigDecimalUtils.add(lastDto.getStartBalance(), price));
		lastDto.setEndBalanceEncryption(null);
		lastDto.setSettleType("02");
		lastDto.setSettleId(MapUtils.get(map, "settleId"));
		lastDto.setCrteId(MapUtils.get(map, "crteId"));
		lastDto.setCrteName(MapUtils.get(map, "crteName"));
		lastDto.setCrteTime(new Date());
		// 新增一卡通消费异动
		int temp1 = baseCardRechargeChangeDao.insertBaseCardRechargeChange(lastDto);

		Map<String, Object> cardMap = new HashMap<>();
		cardMap.put("hospCode", lastDto.getHospCode());
		cardMap.put("profileId", lastDto.getProfileId());
		cardMap.put("cardNo", lastDto.getCardNo());
		cardMap.put("accountBalance", lastDto.getEndBalance());
		int temp2 = baseCardRechargeChangeDao.updateCardAccountBalance(cardMap);
		if (temp1 ==1 && temp2 == 1) {
			return true;
		}
		return false;
	}

	/**
	 * @Description: 门诊划价退费一卡通异动
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/13 10:57
	 * @Return
	 */
	@Override
	public Boolean saveOutptTuiFei(Map map) {
		// 支付异动信息
		BaseCardRechargeChangeDTO baseCardRechargeChangeDTO = baseCardRechargeChangeDao.selectOutptBaseCardRechargeChange(map);
		// 根据卡id插叙最新的异动信息
		BaseCardRechargeChangeDTO lastDto = baseCardRechargeChangeDao.selectLastChangeByCardId(baseCardRechargeChangeDTO);
		if (lastDto == null || !BigDecimalUtils.equals(lastDto.getEndBalance(), lastDto.getAccountBalance())) {
			throw new AppException("一卡通余额与上次异动后金额不一致，请联系管理员");
		}
		lastDto.setId(SnowflakeUtils.getId());
		lastDto.setStatusCode("8"); // 卡异动状态 8： 消费
		lastDto.setPayCode(null);  // 支付方式
		BigDecimal price = BigDecimalUtils.negate(baseCardRechargeChangeDTO.getPrice());
		lastDto.setPrice(price);
		lastDto.setStartBalance(lastDto.getEndBalance());
		lastDto.setStartBalanceEncryption(lastDto.getEndBalanceEncryption());
		lastDto.setEndBalance(BigDecimalUtils.add(lastDto.getStartBalance(), price));
		lastDto.setEndBalanceEncryption(null);
		lastDto.setSettleType("04");
		lastDto.setSettleId(MapUtils.get(map, "redSettleId"));
		lastDto.setCrteId(MapUtils.get(map, "crteId"));
		lastDto.setCrteName(MapUtils.get(map, "crteName"));
		lastDto.setCrteTime(new Date());
		// 新增一卡通消费异动
		int temp1 = baseCardRechargeChangeDao.insertBaseCardRechargeChange(lastDto);

		Map<String, Object> cardMap = new HashMap<>();
		cardMap.put("hospCode", lastDto.getHospCode());
		cardMap.put("profileId", lastDto.getProfileId());
		cardMap.put("cardNo", lastDto.getCardNo());
		cardMap.put("accountBalance", lastDto.getEndBalance());
		int temp2 = baseCardRechargeChangeDao.updateCardAccountBalance(cardMap);
		if (temp1 ==1 && temp2 == 1) {
			return true;
		}
		return false;
	}
}
