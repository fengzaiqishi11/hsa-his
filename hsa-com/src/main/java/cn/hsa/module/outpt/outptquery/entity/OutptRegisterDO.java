package cn.hsa.module.outpt.outptquery.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: cn.hsa.module.outpt.outptquery.entity
 * @Class_name: OutptRegisterDO
 * @Description: 门诊挂号数据库映射对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/10 14:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptRegisterDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 996232901961301451L;
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
    * 挂号单号（单据生成规则表）
    */
    private String registerNo;
    /**
    * 姓名
    */
    private String name;
    /**
    * 性别（XB）
    */
    private String genderCode;
    /**
    * 年龄
    */
    private Integer age;
    /**
    * 出生日期
    */
    private Date birthday;
    /**
    * 证件类型代码
    */
    private String certCode;
    /**
    * 证件号码
    */
    private String certNo;
    /**
    * 联系电话
    */
    private String phone;
    /**
    * 来源标志代码（LYBZ）
    */
    private String sourceBzCode;
    /**
    * 就诊类别代码（JZLB）
    */
    private String visitCode;
    /**
    * 病人来源途径代码（LYTJ）
    */
    private String sourceTjCode;
    /**
    * 病人来源途径备注
    */
    private String sourceTjRemark;
    /**
    * 挂号时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date registerTime;
    /**
    * 挂号类别ID
    */
    private String cfId;
    /**
    * 坐诊班次ID
    */
    private String cqId;
    /**
    * 医生队列ID
    */
    private String dqId;
    /**
    * 医生号源明细ID
    */
    private String drId;
    /**
    * 挂号科室ID
    */
    private String deptId;
    /**
    * 挂号科室名称
    */
    private String deptName;
    /**
    * 挂号医生ID
    */
    private String doctorId;
    /**
    * 挂号医生姓名
    */
    private String doctorName;
    /**
    * 是否作废（SF）
    */
    private String isCancel;
    /**
    * 是否初诊（SF）
    */
    private String isFirstVisit;
    /**
    * 是否加号（SF）
    */
    private String isAdd;
    /**
    * 创建人ID
    */
    private String crteId;
    /**
    * 创建人姓名
    */
    private String crteName;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;

}