package cn.hsa.module.emr.emrborrow.entity;

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
 * 电子病历借阅记录表
 * 业务逻辑：
 * 借阅归还 实体类
 * @author liuliyun
 * @date 2021-08-23 14:16:04
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrBorrowDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -9118270255462902694L;
    private String id; // 主键
    private String hospCode; // 医院编码
    private String deptId; // 科室id
    private String borrowDoctor; // 借阅医生
    private String borrowId; // 借阅医生id
    private String borrowInNo; // 借阅病案号
    private String borrowPatient; // 借阅病人姓名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date borrowTime; // 借阅时间
    private Integer borrowDuration; // 借阅期限（天）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date revertTime; // 归还时间
    private String revertName; // 归还人
    private String revertId; // 归还人id
    private String remark; // 借阅原因
    private String crteName; // 创建人
    private String crteId;  // 创建人id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;  // 创建时间
    private String isReturn; // 是否归还

}
