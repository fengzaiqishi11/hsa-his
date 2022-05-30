package cn.hsa.module.insure.clinica.dto;

import cn.hsa.module.insure.module.entity.InsureNoStructReportDO;
import lombok.Data;

import java.io.Serializable;
/**
 * @Author 医保二部-张金平
 * @Date 2022-05-11 14:31
 * @Description 非结构化报告DTO
 */
@Data
public class InsureNoStructReportDTO extends InsureNoStructReportDO implements Serializable {
    private String otpIptFlagName; // 门诊/住院标志
    private String examTestFlagName; // 检查/检验标志
}
