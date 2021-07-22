package cn.hsa.module.insure.inpt.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Package_name: cn.hsa.module.insure.inpt.entity
* @Class_name: InsureInptRegisterMdtrtinfoDO
* @Describe:  入院登记 - 就诊信息实体类（Mdtrtinfo）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRegisterMdtrtinfoDO extends PageDO implements Serializable {
        private String psnNo;	           // 人员编号	字符型	30	　	Y　	新医保/核3/核2
        private String insutype;	           // 险种类型	字符型	6	Y	Y	新医保/核3
        private String conerName;	       // 联系人姓名	字符型	50	　	　	新医保/核3
        private String tel;	               // 联系电话	字符型	50	　	　	新医保/核3
        private Date begntime;	           // 开始时间	日期时间型	　	　	Y　	新医保/核3/核2
        private String mdtrtCertType;	   // 就诊凭证类型	字符型	3	Y	Y　	新医保
        private String mdtrtCertNo;	   // 就诊凭证编号	字符型	50			新医保/核3/核2
        private String medTtype;	           // 医疗类别	字符型	6	Y	Y　	新医保/核3/核2
        private String iptNo;	           // 住院号	字符型	30	　	Y　	新医保/核3/核2
        private String medrcdno;	           // 病历号	字符型	30	　	　	新医保
        private String atddrNo;	           // 主治医生编码	字符型	30	　	Y　	新医保/核3/核2
        private String chfpdrName;	       // 主诊医师姓名	字符型	50	　	Y　	新医保/核2
        private String admDiagDscr;	   // 入院诊断描述	字符型	200	　	Y　	新医保
        private String admDeptCodg;	   // 入院科室编码	字符型	30	　	Y　	新医保/核3/核2
        private String admDeptName;	   // 入院科室名称	字符型	100	　	Y　	新医保/核3/核2
        private String admBed;	           // 入院床位	字符型	30	　	Y　	新医保/核3/核2
        private String dscgMaindiagCode;  // 住院主诊断代码	字符型	20	　	Y　	新医保/核3/核2
        private String dscgMaindiagName;  // 住院主诊断名称	字符型	300	　	Y　	新医保/核2
        private String mainCondDscr;	   // 主要病情描述	字符型	1000	　	　	新医保/核3/核2
        private String diseCode;	       // 病种编码	字符型	30	　	　	新医保
        private String diseName;	       // 病种名称	字符型	500	　	　	新医保
        private String oprnOprtCode;	   // 手术操作代码	字符型	30	　	　	新医保
        private String oprnOprtName;	   // 手术操作名称	字符型	500	　	　	新医保
        private String fpscNno;	           // 计划生育服务证号	字符型	50	　	　	新医保/核2
        private String matnType;	       // 生育类别	字符型	6	Y	　	新医保
        private String birctrlType;	       // 计划生育手术类别	字符型	6	Y　	　	新医保
        private String latechbFlag;	       // 晚育标志	字符型	3	Y	　	新医保
        private BigDecimal gesoVal;	           // 孕周数	数值型	2	　	　	新医保
        private BigDecimal fetts;	           // 胎次	数值型	3	　	　	新医保
        private BigDecimal fetusCnt;	       // 胎儿数	数值型	3	　	　	新医保
        private String pretFlag;	       // 早产标志	字符型	3	Y	　	新医保
        private Date birctrlMatnDate;   // 计划生育手术或生育日期	日期型	　	　	　	新医保
        private String diseTypeCode;	   // 病种类型	字符型	6			新医保
        private String insuplcAdmdvs;	   // 医保中心编码	字符型	6			核3/核2
        private String medinsCode;	       // 医疗机构编码					核3/核2
        private String psnType;	           // 人员类别	字符型	2	　		核3/核2
        private String medType;	           // 业务类型	字符型	2	　	Y	核3/核2
        private String medMdtrrType;	   // 待遇类型	字符型	3	　	Y	核3/核2
        private String optTime;	           // 入院登记时间	字符型	20	　	Y	核2
        private String opter;	           // 登记人员工号	字符型	20	　	Y	核3/核2
        private String opterName;	       // 登记人姓名	字符型	20	　	Y	核3/核2
        private String admWay;	           // 入院方式	字符型	1	　	Y	核2
        private String serialApply;	       // 业务申请序列号	字符型	12	　	N	核2
        private String reltMedinsCode;	   // 关联医疗机构编码	字符型	20	　	N	核2
        private String reltSerialN;	   // 关联就医登记号	字符型	12	　	N	核2
        private String admSumtms;	       // 本年住院次数	字符型	2	　	N	核2
        private String wardareaNo;	       // 入院病区编号	字符型	3	　	N(核3必填)	核3/核2
        private String wardareaName;	   // 入院病区名称	字符型	20	　	N(核3必填)	核3/核2
        private String bedType; 	       // 床位类型	字符型	1	　	Y	核2
        private String prepPay;	           // 预付款总额	字符型	10	　	N	核3/核2
        private String hospAdmDiag;	   // 入院诊断(医疗机构)	字符型	20	　	N	核2
        private String areaNo;	           // 区/县	字符型	6	　	N	核3
        private String empNo;	           // 单位电脑号	字符型	20	　	N	核3
        private String empType;	           // 单位类型	字符型	6	　	N	核3
        private String empCode;	           // 单位管理码	字符型	90	　	N	核3
        private String name;	               // 姓名	字符型	150	　	N	核3
        private String gend;               // 性别	字符型	18	　	N	核3
        private String brdy;	               // 出生日期	字符型	8	　	N	核3
        private String aimType;	           // 补助类型	字符型	18	　	N	核3
        private String conerAdr;	       // 联系地址	字符型	256	　	N	核3
        private String admdv;	           // 人员所属中心	字符型	6	　	N	核3
        private String cvlservLv;	       // 公务员级别	字符型	18	　	N	核3
        private String empName;	           // 单位名称	字符型	300	　	N	核3
        private String diseCondSev;	   // 病情严重程度	字符型	18	　	N	核3
        private String clncFlag;	       // 临床试验标志	字符型	3	Y		新医保
        private String erFlag;	           // 急诊标志	字符型	3	Y		新医保
        private String advpay;	           // 预付款	数值型	16,2			新医保
        private String repeatIptFlag;	   // 重复住院标志	字符型	3	Y	Y	新医保
        private String ttpResp;	           // 是否第三方责任标志	字符型	6		Y	新医保
        private String mergSetlFlag;	   // 合并结算标志	字符型	6		Y	新医保
}