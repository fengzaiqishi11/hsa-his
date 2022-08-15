package cn.hsa.module.outpt.queue.dto;

import cn.hsa.module.outpt.queue.entity.OutptDoctorQueueDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.queue.dto
 * @Class_name: OutptDoctorQueueDto
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptDoctorQueueDto extends OutptDoctorQueueDO {
    private Date queueDate;
    private String deptId;
    private String classStartDate;
    private String classEndDate;

    private String isUserRegister;

    private String numOrUseNum;  // 号源/总数
    /**
     *  分诊室名称
     */
    private String clinicName;

    /**
     * @Description: 用于微信小程序接口
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2021-06-22 16:35
     */
    // 挂号类别id
    private String cyId;
    // 挂号类别名称
    private String cyName;
    // 科室名称
    private String deptName;
    // 科室编码
    private String deptCode;
    // 就诊类别
    private String visitCode;
    // 挂号类别代码
    private String registerCode;
    // 医生号源开始时间
    private String startTime;
    // 医生号源结束时间
    private String endTime;
    // 已使用号源数
    private String usedRegisterNum;
    // 剩余号源数
    private String surplusRegisterNum;

    private String bcName; //班次名称
    // 医生队列id
    private String dqId;
    // 类别排班id
    private String ccId;
    // 班次id
    private String csId;
    // 挂号类别总金额
    private String classifyPrice;
    // 挂号类别名称
    private String classifyName;
    // 班次名称
    private String classesName;
    // 医生队列日期
    private String queueDateStr;

    //历史挂号总量
    private Integer registerCount;

}
