package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptSettleAccountViolationCost
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 17:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptSettleAccountViolationCost implements Serializable {
	private static final long serialVersionUID = -1986660971184796826L;

	// 就诊ID
	private String mdtrtId;

	// 医疗机构目录编码
	private String medinsListCodg;

	// 医疗机构目录名称
	private String medinsListName;

	// 医疗目录编码
	private String medListCodg;

	// 违规类型
	private String volaType;

	// 违规说明
	private String volaDscr;
}
