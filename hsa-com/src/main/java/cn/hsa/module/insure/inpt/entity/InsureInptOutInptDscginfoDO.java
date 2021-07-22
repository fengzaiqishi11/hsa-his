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
* @Class_name: InsureInptOutInptDscginfoDO
* @Describe:  出院登记实体类（dscginfo）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptOutInptDscginfoDO extends PageDO implements Serializable {
        private String mdtrtId	         ; // 就诊ID	字符型	30	　	Y	新医保
        private String psnNo	         ; // 人员编号	字符型	30	　	Y　	新医保
        private String insutype	         ; // 险种类型	字符型	6	Y	Y	新医保
        private Date endtime	         ; // 结束时间	日期时间型	　	　	Y　	新医保
        private String diseCode	     ; // 病种编码	字符型	30	　	　	新医保
        private String diseName	     ; // 病种名称	字符型	500	　	　	新医保
        private String oprnOprtCode	 ; // 手术操作代码	字符型	30	　	　	新医保
        private String oprnOprtName	 ; // 手术操作名称	字符型	500	　	　	新医保
        private String fpscNo	         ; // 计划生育服务证号	字符型	50	　	　	新医保
        private String matnType	     ; // 生育类别	字符型	6	Y	　	新医保
        private String birctrlType	     ; // 计划生育手术类别	字符型	6	Y　	　	新医保
        private String latechbFlag	     ; // 晚育标志	字符型	3	Y	　	新医保
        private BigDecimal gesoVal	         ; // 孕周数	数值型	2	　	　	新医保
        private BigDecimal fetts	         ; // 胎次	数值型	3	　	　	新医保
        private BigDecimal fetusCnt	     ; // 胎儿数	数值型	3	　	　	新医保
        private String pretFlag	     ; // 早产标志	字符型	3	Y	　	新医保
        private Date birctrlMatnDate	; // 计划生育手术或生育日期	日期型	　	　	　	新医保
        private String copFlag	         ; // 伴有并发症标志	字符型	3	Y		新医保
        private String dscgDeptCode	 ; // 出院科室编码	字符型	30	　	Y　	新医保
        private String dscgDeptName	 ; // 出院科室名称	字符型	100	　	Y　	新医保
        private String dscgBed;	//出院床位 ; 	字符型	30	　	　	新医保
        private String dscgWay;	//离院方式;	字符型	3	Y	Y　	新医保
        private Date dieDate;	//死亡日期;	// 日期型	　	　	　	新医保
        private BigDecimal expiGestationNnubOfM;	//终止妊娠月数	数值型	3			新医保
}