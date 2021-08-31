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
 * @Class_name: InsureOutptCostDownload
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 14:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptCostDownloadDO implements Serializable {
	private static final long serialVersionUID = 3758315251450297026L;

	// 费用明细流水号
	private String feedetlSn;

	// 明细项目费用总额
	private BigDecimal detItemFeeSumamt;

	// 数量
	private BigDecimal cnt;

	// 单价
	private BigDecimal price;

	// 定价上限金额
	private BigDecimal pricUplmtAmt;

	// 自付比例
	private BigDecimal selfpayProp;

	// 全自费金额
	private BigDecimal fulamtOwnpayAmt;

	// 超限价金额
	private BigDecimal overlmtAmt;

	// 先行自付金额
	private BigDecimal preselfpayAmt;

	// 符合政策范围金额
	private BigDecimal inscpScpAmt;

	// 收费项目等级
	private String chrgitmLv;

	// 医疗收费项目类别
	private String medChrgitmType;

	// 基本药物标志
	private String basMednFlag;

	// 医保谈判药品标志
	private String hiNegoDrugFlag;

	// 儿童用药标志
	private String chldMedcFlag;

	// 目录特项标志
	private String listSpItemFlag;

	// 限制使用标志
	private String lmtUsedFlag;

	// 直报标志
	private String drtReimFlag;

	// 备注
	private String memo;
}
