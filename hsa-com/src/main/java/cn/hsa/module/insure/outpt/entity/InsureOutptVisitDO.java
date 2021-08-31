package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.PPMT;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptVisitDO
 * @Describe: 门诊就诊信息上传
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 11:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptVisitDO implements Serializable {
	private static final long serialVersionUID = -5315843663438012876L;
	// 就诊ID
	private String mdtrtId;

	// 人员编号
	private String psnNo;

	// 医疗类别
	private String medType;

	// 开始时间
	private Date begntime;

	// 主要病情描述
	private String mainCondDscr;

	// 病种编码
	private String diseCode;

	// 病种名称
	private String diseName;

	// 计划生育手术类别
	private String birctrlType;

	// 计划生育手术或生育日期
	private String birctrlMatnDate;

	// 生育类别
	private String matnType;

	// 孕周数
	private int gesoVal;

	// 是否第三方责任申请
	private String ttpResp;

	// 终止妊娠月数
	private String expiGestationNubOfM;
}
