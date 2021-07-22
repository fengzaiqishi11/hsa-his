package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptSettleAccountFundOutDO
 * @Describe: 门诊预结算基金分项信息（输出）
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 17:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptSettleAccountFundOutDO implements Serializable {
	private static final long serialVersionUID = -350214334800458175L;

	// 基金支付类型
	private String fundPayType;

	// 符合政策范围金额
	private BigDecimal inscpScpAmt;

	// 本次可支付限额金额
	private BigDecimal crtPaybLmtAmt;

	// 基金支付金额
	private BigDecimal fundPayamt;

	// 基金支付类型名称
	private String fundPayTypeName;

	// 结算过程信息
	private String setlProcInfo;
}
