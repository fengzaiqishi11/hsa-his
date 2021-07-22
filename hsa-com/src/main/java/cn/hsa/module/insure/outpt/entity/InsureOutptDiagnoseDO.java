package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptDiagnose
 * @Describe: 门诊诊断信息
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 13:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptDiagnoseDO implements Serializable {
	private static final long serialVersionUID = 4123659186445569977L;

	// 诊断类别
	private String diagType;

	// 诊断排序号
	private int diagSrtNo;

	// 诊断代码
	private String diagCode;

	// 诊断名称
	private String diagName;

	// 诊断科室
	private String diagDept;

	// 诊断医生编码
	private String diagDorNo;

	// 诊断医生姓名
	private String diagDorName;

	// 诊断时间
	private Date diagTime;

	// 有效标志
	private String valiFlag;
}
