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
* @Class_name: InsureInptOutInptDiseinfoDO
* @Describe:  出院登记实体类（Diseinfo）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptOutInptDiseinfoDO extends PageDO implements Serializable {
        private String mdtrtId; // 就诊ID	字符型	30	　	　Y	新医保
        private String psnNo; // 人员编号	字符型	30	　	　Y	新医保
        private String diagType; // 诊断类别	字符型	3	　Y	　Y	新医保
        private String maindiagFlag; // 	主诊断标志	字符型	3	　Y	　Y	新医保
        private BigDecimal diagSrtNo; // 诊断排序号	数值型	2	　	　Y	新医保
        private String diagCode; // 诊断代码	字符型	20	　	　Y	新医保
        private String diagName; // 诊断名称	字符型	100	　	　Y	新医保
        private String diagDept; // 诊断科室	字符型	50	　	　Y	新医保
        private String diseDorNo; // 诊断医生编码	字符型	30	　	　Y	新医保
        private String diseDorName; // 	诊断医生姓名	字符型	50	　	　Y	新医保
        private Date diagTime; // 诊断时间	日期时间型	　	　	　Y	新医保
}