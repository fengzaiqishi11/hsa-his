package cn.hsa.module.inpt.inspectionreport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
   * @Class_name: PatientInfoDTO
   * @Describe: 病人信息传输对象
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/17 11:07
**/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatientInfoDTO implements Serializable {

    private static final long serialVersionUID = 7375166387349668271L;
    /** 就诊号 **/
    private String visitNo;
    /** 病人姓名  **/
    private String name;

    private String id;

    /** 性别 **/
    private String gender;
    /** 年龄 **/
    private String age;
    /** 住院号 **/
    private String inNo;
    /** 床位  **/
    private String bedName;
    /** 主治医师 **/
    private String zzDoctorName;
    /** 入院时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;
    /** 入院诊断 **/
    private String inDiseaseName;
    /** 出院时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;
    /** 病人检查项目 **/
    private List<PatientInspectItem> patientInspectItems;
}
