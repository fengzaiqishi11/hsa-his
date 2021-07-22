package cn.hsa.module.inpt.inspectionreport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/***
   * @Describe: pacs影像检测报告传输实体
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/19 18:04
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PacsImageReportDTO implements Serializable {

    private static final long serialVersionUID = 8063218998824070057L;

    /** 病人基本信息 **/
    /** 医院名称 **/
    private String hospName;
    /** 姓名 **/
    private String name;
    /** 性别 **/
    private String gender;
    /** 年龄 **/
    private String age;
    /** 床号 **/
    private String bedName;
    /** 检查项目内容 **/
    private String content;

    /** 申请单号 **/
    private String applyNo;
    /** 就诊号/住院号 **/
    private String inNo;
    /** 处方单号 **/
    private String orderNo;
    /** 送检科室 **/
    private String deptName;
    /** 送检医生 **/
    private String submittingPhysician;
    /** 申请时间 （检查时间）**/
    private String applyTime;
    /** 检测结果ID **/
    private String resultId;
    /** 检查所见（检测结果） **/
    private String result;
    /** 检查部位 **/
    private String checkPart;
    /** 诊断结论 **/
    private String diagnosis;
    /** 报告医生 **/
    private String  reportPhysician;
    /** 报告时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date reportTime;
    /** 审核人 **/
    private String auditor;
    /** 临床诊断 **/
    private String clinicalDiagnosis;
}
