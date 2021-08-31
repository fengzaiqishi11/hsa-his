package cn.hsa.module.oper.operpatientrecord.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.oper.operpatientrecord.entity
 * @Class_name: OperPatientRecordDO
 * @Describe: 手术病人信息查询
 * @Author: luonianxin
 * @Email: nianxin.luo@powersi.com
 * @Date: 2021/4/23 11:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperPatientRecordDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 8381760621998282850L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 手术id
     */
    private String operId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 病人姓名
     */
    private String  name;
    /**
     * 病人性别
     */
    private String  genderCode;
    /**
     * 年龄
     */
    private String age;
    /**
     * 年龄单位
     */
    private String ageUnitCode;
    /**
     * 血型
     */
    private String bloodCode;

    /**
     * 住院号
     */
    private String inNo;
    /**
     * 就诊科室ID
     */
    private String deptId;

    /**
     * 当前床位ID
     */
    private String bedId;
    /**
     * 当前床位名称
     */
    private String bedName;

    /**
     * 手术科室ID
     */
    private String operDeptId;

    /**
     * 入院主诊断Id
     */
    private String inDiseaseId;
    /**
     * 入院主诊断名称
     */
    private String inDiseaseName;
    /**
     * 入院主诊断ICD-10码头
     */
    private String inDiseaseIcd10;
    /**
     * 手术疾病编码ICD-9
     */
    private String operDiseaseIcd9;
    /**
     * 手术疾病ID
     */
    private String operDiseaseId;
    /**
     * 手术疾病ID
     */
    private String operDiseaseName;
    /**
     * 手术内容
     */
    private String content;
    /**
     * 切口等级代码（QKDJ）
     */
    private String notchedCode;
    /**
     * 愈合情况代码（YHQK）
     */
    private String healCode;
    /**
     * 手术耗时（按小时）
     */
    private BigDecimal operTimeHour;
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
     * 手书一助名称
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
     * 医嘱ID
     */
    private String adviceId;
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
     * 审核备注
     */
    private String auditRemark;
    /**
     * 手术安排时间
     */
    private String operPlanTime;
    /**
     * 手术室ID
     */
    private String operRoomId;
    /**
     * 手术室名称
     */
    private String operRoomName;
    /**
     * 巡回护士id
     */
    private String xhNurseId;
    /**
     * 巡回护士名称
     */
    private String xhNurseName;
    /**
     * 器械护士id
     */
    private String qxNurseId;
    /**
     * 器械护士姓名
     */
    private String qxNurseName;
    /**
     * 麻醉方式代码（SF）
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operEndTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 计费人姓名
     */
    private String costName;
    /**
     * 是否计费（SF）
     */
    private String isCost;
    /**
     * 计费总金额
     */
    private String totalPrice;
    /**
     * 计费时间
     */
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date costTime;
    /**
     * 手术状态代码（SSZT）
     * 手术状态标志代码：0、已申请，1、取消申请，3、已安排，4、取消安排，5、已完成
     */
    private String statusCode;
    /**
     * 计费人ID
     */
    private String costId;
    /**
     * 手术级别
     */
    private String rank;
    /**
     * 创建人姓名/手术申请人姓名
     */
    private String crteName;
    /**
     * 创建人ID/手术申请人ID
     */
    private String crteId;


    /**
     * 创建时间
     */
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
