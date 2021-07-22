package cn.hsa.module.outpt.visit.dto;

import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.entity.OutptVisitDO;
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
 * @Package_name: cn.hsa.module.outpt.visit.dto
 * @Class_name: OutptVisitDTO
 * @Describe: 门诊就诊传输DTO对象(门诊病人)
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptVisitDTO extends OutptVisitDO implements Serializable {

    private static final long serialVersionUID = 3358648859964812185L;
    /**
     * 查询条件
     */
    private String keyword;
    /**
     * 诊断id
     */
    private String diseaseId;
    /**
     * 诊断名称
     */
    private String diseaseName;
    /**
     * 处方明细id
     */
    private List<String> opdIds;
    /**
     * 处方明细list
     */
    private List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList;
    /**
     * 处方号
     */
    private String orderNo;

    //就诊开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    //就诊结束日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    //划价收费；选择的项目、材料、药品信息
    private List<OutptCostDTO> outptCostDTOList;
    //诊断归类
    private String typeCode;
    //类型
    private String type;
    //查询类型
    private String operType;
    /**
     * 入院开始时间
     */
    private String startTime;
    /**
     * 入院结束时间
     */
    private String endTime;
    /**
     * 入院诊断名称
     */
    private String inDiseaseName;
    /**
     * 入院诊断编码
     */
    private String inDiseaseCode;
    /**
     * 门诊主诊断id
     */
    private String outptDiseaseId;
    /**
     * 门诊主诊断名称
     */
    private String outptDiseaseName;
    /**
     * 病区id
     */
    private String inWardId;

    //存储门诊就诊信息对应档案
    //国籍
    private String nationalityCation;
    //职业
    private String occupationCode;
    //学历
    private String educationCode;
    //联系人关系
    private String contactRelaCode;
    //联系人姓名
    private String contactName;
    //联系人电话
    private String contactPhone;
    //联系人地址
    private String contactAddress;
    //户口地
    private String nativeAddress;
    //诊断
    private String[] diagnose;
    //处方内容
    private String content;
    //登记时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;
    //结算时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;
    //登记人姓名
    private String registerName;
    //医保登记信息
    private String insure;
    //医院名称
    private String hospName;
    //结算id
    private String settleId;
    //结算单号
    private String settleNo;
    /**
     * 项目名称（项目/材料）
     */
    private String itemName;
    //规格
    private String spec;
    //数量单位
    private String numUnitCode;
    //数量
    private String num;
    //剂量
    private String dosage;
    //剂量单位
    private String dosageUnitCode;
    //用法
    private String usageCode;
    //优化后金额
    private BigDecimal realityPrice;

    // 自定义优惠  优惠类型
    private String customYhlx;
    // 自定义优惠  优惠折扣或只收价格
    private BigDecimal custom;
    // 是否按原价计算
    private String recoverOriginalCostRemark;
    // 电子凭证授权 ecToken
    private String ecToken;
    private String code ; // 操作人编码

    // 是否驳回
    private String isReject;

    // 是否审核
    private String isAudit;

    private String inDeptName; // 就诊科室名称  为了兼容页面

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;
    //处方id
    private String opId;
    //组号
    private String groupNo;
    private String medicalRegNo; // 医保统一支付-门诊挂号登记时返回的就医登记号
    private String isEcqr; // 判断是否电子凭证登记

    /**
     * 入院开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ryStartTime;
    /**
     * 入院结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ryEndTime;

    private String bloodCode; //血型代码

    private String sourceTjRemark; //病人来源途径备注

    private String visitId; //就诊id

    private String sourceBzCode; // 来源标志代码

    private String visitCode; // 就诊类型

    private String sourceTjCode; // 病人来源途径代码(1.门诊，2.住院，3.体检)

    // 门诊医生名称
    private String outptDoctorId;
    // 门诊医生姓名
    private String outptDoctorName;
}
