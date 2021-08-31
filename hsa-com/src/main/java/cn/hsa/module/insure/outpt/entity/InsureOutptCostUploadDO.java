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
 * @Class_name: InsureOutptCostUpload
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 13:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptCostUploadDO implements Serializable {
	private static final long serialVersionUID = -8917826681894720729L;

	// 费用明细流水号
	private String feedetlSn;

	// 就诊ID
	private String mdtrtId;

	// 人员编号
	private String psnNo;

	// 收费批次号
	private String chrgBchno;

	// 病种编码
	private String diseCode;

	// 处方号
	private String rxno;

	// 外购处方标志
	private String rxCircFlag;

	// 费用发生时间
	private Date feeOcurTime;

	// 医疗目录编码
	private String medListCodg;

	// 医药机构目录编码
	private String medinsListCodg;

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

	// 开单科室编码
	private String bilgDeptCodg;

	// 开单科室名称
	private String bilgDeptName;

	// 开单医生编码
	private String bilgDrCodg;

	// 开单医师姓名
	private String bilgDrName;

	// 受单科室编码
	private String acordDeptCodg;

	// 受单科室名称
	private String acordDeptName;

	// 受单医生编码
	private String ordersDrCode;

	// 受单医生姓名
	private String ordersDrName;

	// 医院审批标志
	private String hospApprFlag;

	// 中药使用方式
	private String tcmdrugUsedWay;

	// 外检标志
	private String etipFlag;

	// 外检医院编码
	private String etipHospCode;

	// 出院带药标志
	private String dscgTkdrugFlag;

	// 生育费用标志
	private String matnFeeFlag;
}
