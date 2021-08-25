package cn.hsa.module.outpt.infectious.entity;

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
 * @Package_name: cn.hsa.module.outpt.infectious.entity
 * @Class_name:: InfectiousDiseaseDO
 * @Description: 传染病管理数据库映射对象
 * @Author: liuliyun
 * @Date: 2021/4/20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfectiousDiseaseDO extends PageDO implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 个人Id
     */
    private String profileId;
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 婴儿Id
     */
    private String babyId;
    /**
     * 业务类型代码
     */
    private String busTypeCode;
    /**
     * 报告编号
     */
    private String reportNo;
    /**
     * 报告卡类型编码
     */
    private String reportTypeCode;
    /**
     * 就诊人姓名
     */
    private String visitName;
    /**
     * 家人姓名
     */
    private String familyName;
    /**
     * 证件类型代码
     */
    private String certCode;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 性别代码
     */
    private String genderCode;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 年龄单位代码
     */
    private String ageUnitCode;

    /**
     * 工作单位或学校
     */
    private String workUnit;
    /**
     * 联系电话
     */
    private String phone;

    /**
     * 归属区域代码
     */
    private String belongCode;
    /**
     * 居住区域编码
     */
    private String liveCode;
    /**
     * 居住地址
     */
    private String liveAddress;
    /**
     * 居住详细地址
     */
    private String liveAdderssDetail;
    /**
     * 人群分类代码
     */
    private String popClassCode;
    /**
     * 其他人群分类说明
     */
    private String otherPopExp;
    /**
     * 病例分类代码
     */
    private String recordTypeCode;
    /**
     * 发病类别代码
     */
    private String attackTypeCode;

    /**
     * 发病日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date attackDate;

    /**
     * 诊断日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date diagnosisDate;
    /**
     * 死亡日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dieDate;

    /**
     * 传染病类别编码（截取传染病代码的第一位）
     */
   private String  infectiousTypeNo;
    /**
     * 传染病代码
     */
    private String infectiousCode;
    /**
     * 监测性病类别编码
     */
    private String monitorStdNo;

    /**
     * 监测性病代码（BGCRB）
     */
    private String monitorCode;

    /**
     * 传染途径代码（BGCRB9开头）
     */
    private String routeInfectionCode;
    /**
     * 婚姻状况代码（HYZK）
     */
     private String marryCode;
    /**
     * 文化程度代码（XL）
     */
    private String educationCode;
    /**
     * 订正病名
     */
    private String revision;
    /**
     * 退卡原因
     */
    private String refuseReason;
    /**
     * 报告单位
     */
    private String reportUnit;
    /**
     * 填卡科室ID
     */
    private String fillDeptId;
    /**
     * 填卡科室名称
     */
    private String fillDept;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人id
     */
    private String creatId;
    /**
     * 创建人名称
     */
    private String creatName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date crteTime;
    /**
     * 审核状态代码
     */
    private String auditStatusCode;
     /**
     * 审核人Id2
     */
     private String auditId;

     /**
     * 审核人姓名
     */
     private String auditName;

     /**
     * 审核时间2
     */
     @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date auditTime;

}
