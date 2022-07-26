package cn.hsa.module.inpt.inspectionreport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
   * @Class_name: InspectionReportDTO
   * @Describe: 检验检测报告传输实体
   * @author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/18 10:00
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InspectionReportDTO  implements Serializable {


    private static final long serialVersionUID = 4571445874061166345L;

    /** 医院名称 **/
    private String hospName;
    /** 申请单号 **/
    private String applyNo;
    /** 处方单号 **/
    private String orderNo;
    /** 姓名 **/
    private String name;
    /** 性别 **/
    private String gender;
    /** 性别 **/
    private String age;
    /** 住院号 **/
    private String inNo;
    /** 检验类型 **/
    private String content;
    /** 床号 **/
    private String bedName;
    /** 申请科室名称 **/
    private String deptName;
    /** 申请医生名称 **/
    private String doctorName;
    /** 申请时间 **/
    private String applyTime;
    /** 报告人 **/
    private String reporter;
    /** 备注 **/
    private String remark;
    /** 报告时间 **/
    private String reportTime;
    /** 创建人 **/
    private String crteName;
    /** 创建时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;
    /** 采血人 **/
    private String collBloodName;
    /** 采血时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date collBloodTime;
    /**检查人*/
    private String checker;
    /**检查时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date checkTime;
    /**审核人*/
    private String reviewer;
    /**审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;

    /** 检验检测具体项目列表 **/
    private List<ExaminationItem> examinationItems;
}
