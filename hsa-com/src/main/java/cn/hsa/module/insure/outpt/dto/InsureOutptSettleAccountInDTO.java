package cn.hsa.module.insure.outpt.dto;

import cn.hsa.module.insure.outpt.entity.InsureOutptSettleAccountInDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.insure.outpt.dto
 * @Class_name: InsureOutptSettleAccountInDTO
 * @Describe: 门诊结算输入
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/15 13:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptSettleAccountInDTO extends InsureOutptSettleAccountInDO implements Serializable {
	private static final long serialVersionUID = 4915163392077147682L;


	// 人员编号
	private String psnNo;

	// 就诊凭证类型
	private String mdtrtCertType;

	// 就诊凭证编号
	private String mdtrtCertNo;

	// 医疗类别
	private String medType;

	// 医疗费总额
	private BigDecimal medfeeSumamt;

	// 个人结算方式
	private String psnSetlway;

	// 就诊ID
	private String mdtrtId;

	// 收费批次号
	private String chrgBchno;

	// 个人账户使用标志
	private String acctUsedFlag;

	// 险种类型
	private String insutype;

	// 医疗待遇类型
	private String medMdtrtType;

	// 科室编码
	private String deptCode;

	// 科室名称
	private String deptName;

	// 病区编码
	private String wardareaCode;

	// 病区名称
	private String wardareaName;

	// 保存标识
	private String saveFlag;

	// 结算标识
	private String seltFlag;

	// 照片base64编码
	private String photoBase64;

	// 是否医保电 子凭证刷卡
	private String isElectCert;

	// 扫描医保电 子凭证返回 的值
	private String electCertno;

	// 身份证号
	private String certno;

	// 证件类型
	private String psnCertType;

	// 医保电子凭 证 Token
	private String electCertToken;

	// 个人帐户支付金额
	private String acctPay;

	// 工伤/生育编码
	private String injuryBorthSn;

	// 处方号
	private String rxno;

	// 处方医生编码
	private String bilgDrCodg;

	// 处方医生名称
	private String bilgDrName;

	// 备注
	private String memo;

	// 特殊门诊申请序号
	private String serialApply;

	// 单据号
	private String billNo;

	// 现金支付
	private String cashPay;

	// 统筹区编码
	private String insuplcAdmdvs;

	// 登记人员工号
	private String opter;

	// 登记人姓名
	private String opterName;

	// 医疗机构编码
	private String medinsCode;

	// 医疗机构名称
	private String medinsName;

	// 就诊方式
	private String mdtrtMode;

	// 中途结算标志
	private String midSetlFlag;

	// 医疗机构订单号或医疗机构就医序列号
	private String orderNo;

	// 持卡就诊基本信息
	private String hcardBasinfo;

	// 持卡就诊校验信息
	private String hcardChkinfo;

	// 全自费金额
	private BigDecimal fulamtOwnpayAmt;

	// 超限金额
	private BigDecimal overlmtSelfPay;

	// 先行自付金额
	private BigDecimal preselfpayAmt;

	// 符合政策范围金额
	private BigDecimal inscpScpAmt;
}
