package cn.hsa.module.oper.operInforecord.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.inpt.operInfoRecord.entity
 *@Class_name: OperInfoRecordDO
 *@Describe: 手术登记表
 *@Author: marong
 *@Date: 2020-09-17 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperInfoRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 347917824284171913L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 就诊ID
    */
    private String visitId;
    /**
    * 婴儿ID
    */
    private String babyId;
    /**
    * 住院号
    */
    private String inNo;
    /**
    * 医嘱ID（inpt_advice.id）
    */
    private String adviceId;
    /**
    * 姓名
    */
    private String name;
    /**
    * 性别代码（XB）
    */
    private String genderCode;
    /**
    * 年龄
    */
    private Integer age;
    /**
    * 年龄单位代码（NLDW）
    */
    private String ageUnitCode;
    /**
    * 血型代码（XX）
    */
    private String bloodCode;
    /**
    * 就诊科室ID（住院科室）
    */
    private String deptId;
    /**
    * 手术科室ID
    */
    private String operDeptId;
    /**
    * 当前床位ID
    */
    private String bedId;
    /**
    * 当前床位名称
    */
    private String bedName;
    /**
    * 手术疾病ID（base_disease）
    */
    private String operDiseaseId;
    /**
    * 手术疾病编码ICD-9（base_disease）
    */
    private String operDiseaseIcd9;
    /**
    * 手术疾病名称（base_disease）
    */
    private String operDiseaseName;
    /**
    * 入院主诊断ID（base_disease）
    */
    private String inDiseaseId;
    /**
    * 入院主诊断名称（base_disease）
    */
    private String inDiseaseName;
    /**
    * 入院主诊断ICD-10码（base_disease）
    */
    private String inDiseaseIcd10;
    /**
    * 手术主刀医生ID
    */
    private String doctorId;
    /**
    * 手术主刀医生姓名
    */
    private String doctorName;
    /**
    * 手术一助ID
    */
    private String assistantId1;
    /**
    * 手术一助姓名
    */
    private String assistantName1;
    /**
    * 手术二助ID
    */
    private String assistantId2;
    /**
    * 手术二助姓名
    */
    private String assistantName2;
    /**
    * 手术三助ID
    */
    private String assistantId3;
    /**
    * 手术三助姓名
    */
    private String assistantName3;
    /**
    * 手术特殊要求
    */
    private String specialRequest;
    /**
    * 手术内容
    */
    private String content;
    /**
     * 手术执行类别
     */
    private String operType;
    /**
    * 审核医生ID
    */
    private String auditId;
    /**
    * 审核医生姓名
    */
    private String auditName;
    /**
    * 审核时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
    * 审核备注
    */
    private String auditRemark;
    /**
    * 手术安排时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operPlanTime;
    /**
    * 手术室ID
    */
    private String operRoomId;
    /**
    * 手术室名称
    */
    private String operRoomName;
    /**
    * 巡回护士ID
    */
    private String xhNurseId;
    /**
    * 巡回护士姓名
    */
    private String xhNurseName;
    /**
    * 器械护士ID
    */
    private String qxNurseId;
    /**
    * 器械护士姓名
    */
    private String qxNurseName;
    /**
    * 麻醉方式代码（MZFS）
    */
    private String anaCode;
    /**
    * 第一麻醉医生ID
    */
    private String anaId1;
    /**
    * 第一麻醉医生姓名
    */
    private String anaName1;
    /**
    * 第二麻醉医生ID
    */
    private String anaId2;
    /**
    * 第二麻醉医生姓名
    */
    private String anaName2;
    /**
    * 第三麻醉医生ID
    */
    private String anaId3;
    /**
    * 第三麻醉医生姓名
    */
    private String anaName3;
    /**
    * 手术完成时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operEndTime;
    /**
    * 切口等级代码（QKDJ）
    */
    private String notchedCode;
    /**
    * 愈合情况代码（YHQK）
    */
    private String healCode;
    /**
    * 手术耗时（按小时：1.5小时）
    */
    private BigDecimal operTimeHour;
    /**
    * 手术状态代码（SSZT）
    */
    private String statusCode;
    /**
    * 是否计费（SF）
    */
    private String isCost;
    /**
    * 计费总金额
    */
    private BigDecimal totalPrice;
    /**
    * 计费时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  costTime;
    /**
    * 计费人ID
    */
    private String costId;
    /**
    * 计费人姓名
    */
    private String costName;
    /**
    * 备注
    */
    private String remark;
    /**
    * 创建人ID/手术申请人ID
    */
    private String crteId;
    /**
    * 创建人姓名/手术申请人姓名
    */
    private String crteName;
    /**
    * 创建时间/手术申请时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    private String wardId;  //病区id

    private String rank;  //手术级别
}