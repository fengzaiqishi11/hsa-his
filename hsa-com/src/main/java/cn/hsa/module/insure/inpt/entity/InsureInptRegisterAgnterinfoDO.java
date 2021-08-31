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
* @Class_name: InsureInptRegisterAgnterinfoDO
* @Describe:  入院登记 - 代办人信息实体类（Agnterinfo）
* @Author: LiaoJiGuang
* @Email: jiguang.liao@powersi.com
* @Date: 2021/2/09 16:45
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRegisterAgnterinfoDO extends PageDO implements Serializable {
        private String agnterNname;	    // 代办人姓名	字符型	30		Y	新医保
        private String agnterRlts;	    // 代办人关系	字符型	3	Y	Y	新医保
        private String agnterCertType;	// 代办人证件类型	字符型	6	Y	Y	新医保
        private String agnterCertno;	// 代办人证件号码	字符型	30		Y	新医保
        private String agnterTel;	    // 代办人联系电话	字符型	30		Y	新医保
        private String agnterAddr;	    // 代办人联系地址	字符型	200			新医保
        private String agnterPhoto;	    // 代办人照片	文件流				新医保
}