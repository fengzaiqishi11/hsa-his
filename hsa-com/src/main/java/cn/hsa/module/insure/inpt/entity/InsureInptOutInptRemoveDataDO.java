package cn.hsa.module.insure.inpt.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.insure.inpt.entity
* @Class_name: InsureInptOutInptRemoveDataDO
* @Describe:  出院办理撤销实体类（Result）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptOutInptRemoveDataDO extends PageDO implements Serializable {
        private String mdtrtId	    ; // 就诊ID	字符型	30		Y	新医保
        private String psnNo	    ; // 人员编号	字符型	30	　	Y	新医保
}