package cn.hsa.module.emr.emrquality.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.entity
 * @Class_name: EmrQualityAgingDO
 * @Describe:  电子病历数据缺失控制DO
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/26 19:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrQualityDataRulesDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -862302282579230668L;
    private String id;     // 主键
    private String hospCode;  // 医院编码
    private String emrCode;   // 病历模板编码
    private String rulesName; // 规则名称
    private String rulesSql; // 规则sql
    private String tips;  // 规则提示语
    private String isValid; // 是否有效（SF）
    private String crteId;   // 创建者id
    private String crteName; //创建者
    private Date crteTime;// 创建时间
    private String remark; // 备注

}
