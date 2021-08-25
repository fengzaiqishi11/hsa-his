package cn.hsa.module.outpt.prescribeExec.dto;

import cn.hsa.module.outpt.prescribeExec.entity.OutptPrescribeExecDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeExec.dto
 * @Class_name: OutptPrescribeExecDTO
 * @Describe: 门诊处方执行DTO传输对象
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptPrescribeExecDTO extends OutptPrescribeExecDO {
    /**
     *
     */
    private String keyword;
    /**
     * 页面选择实际执行的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String execDate;
    /**
     * 处方明细id
     */
    private String opdId;
    /**
     * 输液登记id
     */
    private String oirId;
    /**
     * 处方组号
     */
    private Integer groupNo;
    /**
     * 患者姓名
     */
    private String name;
    /**
     * 患者性别
     */
    private String genderCode;
    /**
     * 患者年龄
     */
    private Integer age;
    /**
     * 患者年龄单位
     */
    private String ageUnitCode;
    /**
     * 开方日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     * 输液登记时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;
    /**
     * 执行科室名称
     */
    private String execDeptName;
    /**
     * 处方内容
     */
    private String content;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 规格
     */
    private String spec;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 数量单位
     */
    private String numUnitCode;
    /**
     * 剂量
     */
    private BigDecimal dosage;
    /**
     * 数量单位
     */
    private String dosageUnitCode;
    /**
     * 用法
     */
    private String usageCode;
    /**
     * 开方医生姓名
     */
    private String doctorName;

    //费用Id
    private String costId;
    //辅助计费ID
    private String sourceId;
    //结算ID
    private String settleId;

}
