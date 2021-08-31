package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptSettleAccountCost
 * @Describe: 门诊预结算费用明细
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 15:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptSettleAccountCostInDO implements Serializable {
	private static final long serialVersionUID = -7507660346733478994L;

	// 费用明细流水号
	private String feedetlSn;

	// 处方号
	private String rxno;

	// 外购处方标志
	private String rxCircFlag;

	// 费用发生时间
	private Date feeOcurTime;

	// 医疗目录编码
	private String medListCode;

	// 医药机构目录编码
	private String medinsListCode;

	// 明细项目费用总额
	private BigDecimal detItemFeeSumamt;

	// 数量
	private BigDecimal cnt;

	// 单价
	private BigDecimal pric;

	// 单次剂量描述
	private String sinDosDscr;

	// 使用频次描述
	private String usedFrquDscr;

	// 周期天数
	private BigDecimal prdDays;

	// 用药途径描述
	private String medcWayDscr;

	// 开单医生编码
	private String bilgDrCodg;

	// 开单医师姓名
	private String bilgDrName;

	// 中药使用方式
	private String tcmdrugUsedWay;

	// 医疗机构目录名称
	private String medinsListName;

	// 剂型
	private String drugDosform;

	// 规格
	private String spec;

	// 厂家
	private String prdrName;

	// 项目药品类型
	private String listType;

	// 统计类别
	private String medChrgitmType;

	// 用药标识
	private String usedFlag;

	// 出院带药天数
	private String usedDays;

	// 对应费用序列号
	private String oppSerialFee;

	// 录入工号
	private String opter;

	// 录入人
	private String opterName;

	// 录入日期
	private String optTime;
}
