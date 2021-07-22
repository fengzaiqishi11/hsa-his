package cn.hsa.module.insure.inpt.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Package_name: 
* @Class_name: InsureInptRegisterRemoveDataDO
* @Describe:  入院登记撤销实体类（Result）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRegisterRemoveDataDO extends PageDO implements Serializable {
        private String mdtrtId	    ; // 就诊ID	字符型	30		Y	新医保
        private String psnNo	    ; // 人员编号	字符型	30	　	Y	新医保
        private String medinsCode	; // 医疗机构编码	字符型	20		Y	核3
        private String serialNo	    ; // 就医登记号	字符型	20		Y	核3
}