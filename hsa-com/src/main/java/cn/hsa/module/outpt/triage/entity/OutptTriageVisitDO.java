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
 * 分诊队列表实体对象
 * @author  pengbo,luonianxin
 * @Eamil: pengbo@powersi.com.cn
 * @date  2021/6/21 10:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptTriageVisitDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -8604887933420377457L;
    /** 主键ID **/
    private String id;
    /** 医院编码 **/
    private String hospCode;
    /** 挂号ID **/
    private String registerId;
    /** 就诊ID **/
    private String visitId;
    /** 班次队列Id **/
    private String cqId;
    /** 医生队列ID, **/
    private String dqId;
    /** 分诊台ID **/
    private String triageId;
    /** 分诊号 **/
    private String triageNo;
    /** 分诊台名称 **/
    private String triageName;
    /** 科室ID **/
    private String deptId;
    /** 科室名称 **/
    private String deptName;
    /** 诊室ID **/
    private String clinicId;
    /** 诊室名称 **/
    private String clinicName;
    /** 医生ID **/
    private String doctorId;
    /** 医生姓名 **/
    private String doctorName;
    /** 排序号 **/
    private Integer sortNo;
    /** 病人姓名 **/
    private String name;
    /** 分诊状态（FZZT） **/
    private String triageStartCode;
    /** 是否叫号 **/
    private String isCall;
    /** 叫号时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date callTime;
    /** 叫号次数 **/
    private String callNumber;
    /** 是否过号 **/
    private String isLoss;
    /** 过号时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lossTime;
    /** 备注 **/
    private String remark;
    /** 分诊人ID **/
    private String triagePeoId;
    /** 分诊人姓名 **/
    private String triagePeoName;
    /** 是否有效 **/
    private String isValid;
    // 时间戳转换为标准时间格式
    /** 分诊时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date triagePeoTime;
    /** 创建人ID **/
    private String crteId;
    /** 创建人姓名 **/
    private String crteName;
    // 时间戳转换为标准时间格式
    /** 创建时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
