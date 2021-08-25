package cn.hsa.module.insure.inpt.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
* @Package_name: cn.hsa.module.insure.inpt.entity
* @Class_name: InsureInptRegisterMdtrtinfoDO
* @Describe:  入院登记 - 诊断信息实体类（Diseinfo）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRegisterDiseinfoDO extends PageDO implements Serializable {
        private String psnNo	        ; // 人员编号	字符型	30	　	　Y	新医保
        private String diagType	    ; // 诊断类别	字符型	3	　Y	　Y	新医保/核3/核2
        private String maindiagFlag	; // 主诊断标志	字符型	3	　Y	　Y	新医保
        private String diagSrtNo	    ; // 诊断排序号	数值型	2	　	　Y	新医保/核3/核2
        private String diagCode	    ; // 诊断代码	字符型	20	　	　Y	新医保/核3/核2
        private String diagNname	    ; // 诊断名称	字符型	100	　	　Y	新医保
        private String admCond	        ; // 入院病情	字符型	500	　	　	新医保
        private String diagDept	    ; // 诊断科室	字符型	50	　	　Y	新医保
        private String diseDorNo	    ; // 诊断医生编码	字符型	30	　	　Y	新医保
        private String diseDorName	; // 诊断医生姓名	字符型	50	　	　Y	新医保
        private Date diagTime	        ; // 诊断时间	日期时间型	　	　	　Y	新医保
        private String medinsDiagCode	; // 医疗机构诊断编码	字符型	20			核2
}