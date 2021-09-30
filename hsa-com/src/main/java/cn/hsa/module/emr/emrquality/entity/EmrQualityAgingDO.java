package cn.hsa.module.emr.emrquality.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class EmrQualityAgingDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -5283979084637513530L;

    private String id; // 主键
    private String hospCode; // 医院编码
    private String emrCode; // 病历模板编码
    private String tipsType; // 循环类型
    private BigDecimal datumTime; // 基准时间代码
    private BigDecimal timeOut; // 超时时间
    private String adviceList; // 医嘱id列表
    private String isValid;  // 是否有效
    private String crteId;  // 创建人id
    private String crteName; // 创建人姓名
    private Date crteTime; // 创建时间
}
