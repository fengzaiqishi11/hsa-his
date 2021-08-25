package cn.hsa.module.outpt.card.dto;

import cn.hsa.module.outpt.card.entity.BaseCardRechargeChangeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.base.card.dto
 * @Class_name: BaseCardRechargeChangeDTO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/10 9:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseCardRechargeChangeDTO extends BaseCardRechargeChangeDO implements Serializable {
	private static final long serialVersionUID = 5741265348183257433L;

	// 证件号码
	private String certNo;

	// 一卡通号码
	private String cardNo;

	// 一卡通状态
	private String cardStatusCode;

	// 一卡通密码
	private String cardPassword;

	// 一卡通余额
	private BigDecimal accountBalance;

	// 当前卡绑定的档案id的患者姓名
	private String patientName;

}
