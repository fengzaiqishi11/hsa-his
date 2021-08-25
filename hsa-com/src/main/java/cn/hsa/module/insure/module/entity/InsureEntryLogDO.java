package cn.hsa.module.insure.module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureEntryLog
 * @project_name: hsa-his
 * @Description: 记录医嘱，病案上传到医保的日志信息
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/17 9:00
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureEntryLogDO implements Serializable {
    //主键id
    private String id;
    //医院编码
    private String hospCode;
    //医保机构编码
    private String insureOrgCode;
    //医疗机构编码
    private String medicineOrgCode;
    //病人类型
    private String patientCode;
    //患者就诊id
    private String visitId;
    //是否住院（SF）
    private String isHospital;
    //住院号/就诊号
    private String visitNo;
    // 是否录入上传
    private String is_entry;
    //个人电脑号
    private String aac001;
    //医保登记号
    private String medicalRegNo;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
