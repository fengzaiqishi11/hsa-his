package cn.hsa.module.outpt.prescribe.dto;

import cn.hsa.module.outpt.prescribe.entity.OutptMedicalRecordDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.entity
 *@Class_name: OutptPrescribeTempDO
 *@Describe: 门诊病历
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020/9/3 14:00
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptMedicalRecordDTO extends OutptMedicalRecordDO implements Serializable {

    private static final long serialVersionUID = 2681588503570906404L;
    private String conent;
    // 是否食源性
    private String isFoodBorne;
    /**
     * 就诊日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitTime;
    /**
     * 就诊科室id
     */
    private String deptId;
    /**
     * 就诊科室id
     */
    private String deptCode;
    /**
     * 就诊科室name
     */
    private String deptName;

}
