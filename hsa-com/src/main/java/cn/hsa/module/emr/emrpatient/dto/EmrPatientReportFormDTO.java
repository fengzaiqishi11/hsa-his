package cn.hsa.module.emr.emrpatient.dto;

import cn.hsa.module.emr.emrpatient.entity.EmrPatientDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.emr.emrpatient.dto
 * @Class_name: EmrPatientReportFormDTO
 * @Describe: 电子病历统计报表dto
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021/09/13 19:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrPatientReportFormDTO extends EmrPatientDO implements Serializable {

    private static final long serialVersionUID = -1408380217642508977L;
    private String inNo;
    private String inProfile;
    private String name; // 姓名
    private String age;  // 年龄
    private String genderCode; // 性别
    private String deptName; // 科室名称
    private String emrName; // 病历名称
    private String upClassifyName; // 上级分类名称
    private String upCode; // 上次分类编码
    private String doctorName; // 医生名称
    private String archiveState; // 归档状态
    private String classifyCode; // 病历分类
    private String keyword; // 搜索关键字
    private String deptId;  // 科室id
    private String startDate; // 开始时间
    private String endDate; // 结束时间
    private String doctorId; // 书写医生id

}
