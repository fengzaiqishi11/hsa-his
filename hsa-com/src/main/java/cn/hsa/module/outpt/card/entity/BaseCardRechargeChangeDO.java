package cn.hsa.module.outpt.card.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.card.entity
 * @Class_name: BaseCardRechargeChangeDO
 * @Describe:
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/5 19:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseCardRechargeChangeDO extends PageDO implements Serializable {
	private static final long serialVersionUID = 3028519173368205671L;

	private String id;

	private String hospCode;

	private String profileId;

	private String cardId;

	private String statusCode;

	private String payCode;

	private BigDecimal price;

	private BigDecimal startBalance;

	private BigDecimal startBalanceEncryption;

	private BigDecimal endBalance;

	private BigDecimal endBalanceEncryption;

	private String settleType;

	private String settleId;

	private String dailySettleId;

	private String crteId;

	private String crteName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crteTime;

}
