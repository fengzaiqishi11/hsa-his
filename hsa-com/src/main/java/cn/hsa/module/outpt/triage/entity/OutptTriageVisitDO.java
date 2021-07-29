package cn.hsa.module.outpt.triage.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.register.entity
 * @Class_name: OutptTriageVisitDO
 * @Describe:
 * @Author: pengbo
 * @Eamil: pengbo@powersi.com.cn
 * @Date: 2021/6/21 10:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptTriageVisitDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -8604887933420377457L;
    private String id; // 主键ID,
    private String hospCode; // 医院编码,
    private String registerId; // 挂号ID,
    private String visitId; // 就诊ID,
    private String dqId; // 医生队列ID,
    private String triageId; // 分诊台ID,
    private String triageNo; // 分诊号,
    private String triageName;//分诊台名称
    private String deptId;//科室ID
    private String deptName;//科室名称
    private String clinicId; // 诊室ID,
    private String clinicName;//诊室名称
    private String doctorId;//医生ID
    private String doctorName;//医生ID
    private Integer sortNo; // 排序号,
    private String name; // 病人姓名,
    private String triageStartCode; // 分诊状态（FZZT）,
    private String isCall; //是否叫号,
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date callTime; // 叫号时间,
    private String callNumber; //叫号次数,
    private String isLoss; // 是否过号,
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lossTime; //过号时间,
    private String remark; //备注,
    private String triagePeoId; //分诊人ID,
    private String triagePeoName; //分诊人姓名,
    private String isValid;
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date triagePeoTime; //分诊时间,
    private String crteId; // 创建人ID,
    private String crteName; //创建人姓名,
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime; // 创建时间,
}
