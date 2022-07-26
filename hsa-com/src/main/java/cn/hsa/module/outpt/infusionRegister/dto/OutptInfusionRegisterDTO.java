package cn.hsa.module.outpt.infusionRegister.dto;

import cn.hsa.module.outpt.infusionRegister.entity.OutptInfusionRegisterDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.infusionRegister.dto
 * @Class_name: OutptInfusionRegisterDTO
 * @Describe: 门诊输液登记DTO传输对象
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptInfusionRegisterDTO extends OutptInfusionRegisterDO {
    /**
     * 当前登陆的科室id
     */
    private String deptId;
    /**
     * 就诊时间
     */

    private String visitTime;
    /**
     * 项目ID（项目/材料）
     */
    private String itemId;
    /**
     * 项目名称（项目/材料）
     */
    private String itemName;
    /**
     * 年龄
     */
    private String age;
    /**
     * 开方时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startTime;
    /**
     * 开方时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endTime;
    /**
     * 姓名/就诊号
     * 姓名/就诊号/证件号
     */
    private String keyword;
    /**
     * 票据类型（处方类型代码）
     */
    private String typeCode;
    /**
     * 打印标识
     */
    private String printSign;
    /**
     * 打印标识
     */
    private String isPrint;
    /**
     * 姓名
     */
    private String name;
    /**
     * 开方医生
     */
    private String doctorName;
    /**
     * 开方科室
     */
    private String deptName;
    /**
     * 处方内容
     */
    private String content;
    /**
     * 速度代码
     */
    private String speedCode;
    /**
     * 性别代码
     */
    private String genderCode;
    /**
     * 年龄单位代码
     */
    private String ageUnitCode;
    /**
     * 处方id
     */
    private String opId;
    /**
     * 处方明细id
     */
    private String opdId;

    //计划执行时间
    private String planExecDate;
    //就诊单号
    private String visitNo;
    //剂量
    private String dosage;
    //剂量单位
    private String dosageUnitCode;
    /** 用法;
     *  多个用法之间使用单引号括起来存入数据库码表中,
     * 查询时去掉单引号用逗号拼接后整体传入后台
     * **/
    private String usageCode;
    //频率id
    private String rateId;
    //频率名称
    private String rateName;
    //id
    private List<String> ids;
    //数量
    private String num;
    /**
     * 登记时间
     */
    private Date registerTime;
    //处方组号
    private String groupNo;
    //处方组内序号
    private String groupSeqNo;

    //数量单位
    private String numUnitCode;
    //规格
    private String spec;
    //每日执行次数
    private BigDecimal dailyTimes;

    //用法list
    private List<String> usageCodeList;
    //是否皮试
    private String isSkin;
    //皮试单打印标志，用法为输液的，过滤掉处方不需要皮试的数据
    private String skinPrintFlag;

    /**
     * 是否打印，页面标志
     */
    private String printFlag;

    private String isPositive; //是否阳性

    private String skinResultCode; // 皮试结果编码
    /**
     * 护理执行卡打印数据是否共享，判断是否根据单据类型区分打印状态
     */
    private String isShared;
    private String remark;
}
