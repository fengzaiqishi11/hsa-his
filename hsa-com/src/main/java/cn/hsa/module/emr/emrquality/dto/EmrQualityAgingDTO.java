package cn.hsa.module.emr.emrquality.dto;

import cn.hsa.module.emr.emrquality.entity.EmrQualityAgingDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.entity
 * @Class_name: EmrQualityAgingDO
 * @Describe:  电子病历时效质控
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/23 13:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrQualityAgingDTO extends EmrQualityAgingDO implements Serializable {

    private static final long serialVersionUID = 4465102086367034154L;
    private String keyword; // 搜索关键字
    private String label; // 病历模板名称label
    private String emrName; // 病历模板名称
}
