package cn.hsa.module.base.bd.dto;

import cn.hsa.module.base.bd.entity.BaseDiseaseRuleDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.base.bd.dto
 * @Class_name: BaseDiseaseRuleDTO
 * @Describe: 质安-疾病规则DTO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020-11-26 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseDiseaseRuleDTO extends BaseDiseaseRuleDO implements Serializable {
    private static final long serialVersionUID = 4798268620812938947L;
    /**
     * keyword
     */
    private String keyword;
    /**
     * 疾病名称
     */
    private String diseaseName;

    /**
     * 药品名称
     */
    private String drugName;
    /**
     * 药品规格
     */
    private String spec;
    /**
     * 药品单位
     */
    private String unitCode;
    /**
     * 药品单价
     */
    private BigDecimal price;
}
