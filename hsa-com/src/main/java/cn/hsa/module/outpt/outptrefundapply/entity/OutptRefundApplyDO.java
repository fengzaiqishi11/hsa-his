package cn.hsa.module.outpt.outptrefundapply.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.outptrefundapply.entity
 * @Class_name: OutptRefundApply
 * @Describe: 门诊医生退费申请实体
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 13:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptRefundApplyDO extends PageDO implements Serializable {
	private static final long serialVersionUID = -4590783499173885412L;

	private String id;

	private String hospCode;

	private String costId;

	private String itemCode;

	private String itemId;

	private String itemName;

	private BigDecimal price;

	private BigDecimal num;

	private String numUnitCode;

	private String refundXplain;

	private String refundCode;

	private String crteId;

	private String crteName;

	private Date crteTime;

	private String status; // 申请状态，1：已申请待确认 2：已确认

	private String settleId;

	private String oneSettleId;
}
