package cn.hsa.module.emr.emrquality.dto;

import cn.hsa.module.emr.emrquality.entity.EmrQualityDataRulesDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.dto
 * @Class_name: EmrQualityDataRulesDTO
 * @Describe:  电子病历数据缺失控制DTO
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/26 19:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrQualityDataRulesDTO extends EmrQualityDataRulesDO {
    private static final long serialVersionUID = -8914448831637665203L;
    private String keyword;
    private String emrName;
}
