package cn.hsa.module.outpt.register.dto;

import cn.hsa.module.outpt.register.entity.OutptRegisterDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpo.register.dto
 * @class_name: OutptRegisterDTO
 * @Description:
 * @Author: liaojiguang
 * @Email: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/10 17:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptRegisterDTO extends OutptRegisterDO implements Serializable {

    /**
     *  发票号
     */
    private String billNo;

    /**
     *  挂号费
     */
    private BigDecimal totalPrice;

    /**
     *  优惠总金额
     */
    private BigDecimal preferentialPrice;

    /**
     *  实付金额
     */
    private BigDecimal realityPrice;

    /**
     *  结算时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

    /**
     *  证件类型名称
     */
    private String crteName;

    /**
     *  病人类型代码（码表）
     */
    private String patientCode;

    /**
     *  病人类型名称
     */
    private String patientTypeName;

    /**
     *  就诊类别名称
     */
    private String visitTypeName;

    /**
     *  挂号类型CODE
     */
    private String registerCode;

    /** 优惠类别  **/
    private String preferentialTypeId;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 查询条件
     */
    private String keyword;

    /** 挂号明细信息 **/
    List<OutptRegisterDetailDto> regDetailList;

    /** 挂号明细ID串 **/
    private String regDetailIds;
    /** 优惠类别ID **/
    private String preferentialId;
    /** 档案ID **/
    private String profileId;
    /** 医院级别 **/
    private String levelCode;
    /** 是否就诊 **/
    private String isVisit;
    /** 婚姻状况代码 **/
    private String marryCode;
    /** 民族代码 **/
    private String nationCode;

    /**
     *  用于微信小程序接口
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2021-06-22 16:35
     */
    /** 挂号类别名称 **/
    private String classifyName;
    /** 挂号明细表的状态标志 **/
    private String statusCode;
    /** 科室地址 **/
    private String place;
    /** 就诊时间 **/
    private String visitTime;

    /** 挂号时选择的挂号类别所属队列ID **/
    private String classQueueId;
    /** 分诊台Id **/
    private String triageId;
    /** 分诊台名称 **/
    private String triageName;
    /**
     * 居住地地址
     */
    private String nowAddress;

    // 付款一卡通卡号
    private String cardNo;

    // 一卡通当前挂号付款金额
    private BigDecimal cardPrice;
    /**
     * 门诊档案号
     */
    private String outProfile;
    /**
     * 医保个人号
     */
    private String aac001;
    /**
     * 医保登记号
     */
    private String medicalRegNo;

    // 挂账金额
    private BigDecimal creditPrice;

    /**
     * 预约挂号时间段：2021-12-08 ( 10:30-11:00 )
     */
    private String bookingTime;
    // 初/复诊标志
    private String isFirstVisit;

    /**
     *  养转医标志
     */
    private String careToMedicId;
}
