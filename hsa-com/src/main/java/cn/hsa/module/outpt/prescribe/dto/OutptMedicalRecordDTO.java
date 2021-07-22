package cn.hsa.module.outpt.prescribe.dto;

import cn.hsa.module.outpt.prescribe.entity.OutptMedicalRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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

}
