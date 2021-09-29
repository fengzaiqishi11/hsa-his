package cn.hsa.module.emr.emrpatient.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrPatientPrintDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -6279715242745966831L;
    private String id; // 主键
    private String hospCode; //医院编码
    private String emrId; // 病历id
    private String visitId; // 就诊id
    private String emrName; // 病历名称
    private String printName; // 打印人
    private String printId; // 打印人id
    private Date printTime;// 打印时间
}
