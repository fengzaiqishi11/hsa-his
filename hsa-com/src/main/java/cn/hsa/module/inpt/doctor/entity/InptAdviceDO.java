package cn.hsa.module.inpt.doctor.entity;

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
 *@Package_name: cn.hsa.module.inpt.entity
 *@Class_name: InptAdviceDO
 *@Describe: 住院医嘱表
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptAdviceDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -66173986604315233L;
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
     * 医嘱单号
     */
    private String orderNo;
    /**
     * 模板ID
     */
    private String iatId;
    /**
     * 模板内组号
     */
    private Integer iatdGroupNo;
    /**
     * 模板组内序号
     */
    private Integer iatdGroupSeqNo;
    /**
     * 模板明细ID
     */
    private String iatdId;
    /**
     * 就诊/住院科室id
     */
    private String inDeptId;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 开嘱科室ID
     */
    private String deptId;
    /**
     * 医嘱组号
     */
    private Integer groupNo;
    /**
     * 医嘱组内序号
     */
    private Integer groupSeqNo;
    /**
     * 医嘱分类代码（YZFL）
     */
    private String typeCode;
    /**
     * 签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行
     */
    private String signCode;
    /**
     * 开嘱当日执行次数
     */
    private Integer startExecNum;
    /**
     * 停嘱当天执行次数
     */
    private Integer endExecNum;
    /**
     * 财务分类ID
     */
    private String bfcId;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品、项目、材料、医嘱目录）
     */
    private String itemId;
    /**
     * 项目名称（药品、项目、材料、医嘱目录）
     */
    private String itemName;
    /**
     * 医嘱内容
     */
    private String content;
    /**
     * 规格
     */
    private String spec;
    /**
     * 剂型代码（JXFL）
     */
    private String prepCode;
    /**
     * 剂量
     */
    private BigDecimal dosage;
    /**
     * 剂量单位代码（JLDW）
     */
    private String dosageUnitCode;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 频率ID
     */
    private String rateId;
    /**
     * 速度代码（SD）
     */
    private String speedCode;
    /**
     * 数量
     */
    private BigDecimal num = BigDecimal.valueOf(0);
    /**
     * 数量单位（DW）
     */
    private String unitCode;
    /**
     * 单价
     */
    private BigDecimal price = BigDecimal.valueOf(0);
    /**
     * 总金额
     */
    private BigDecimal totalPrice = BigDecimal.valueOf(0);
    /**
     * 总数量（数量*频率*用药天数）
     */
    private BigDecimal totalNum = BigDecimal.valueOf(0);
    /**
     * 总数量单位（DW）
     */
    private String totalNumUnitCode;
    /**
     * 中草药付（剂）数
     */
    private BigDecimal herbNum;
    /**
     * 用药天数
     */
    private Integer useDays;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 是否皮试：0否、1是（SF）
     */
    private String isSkin;
    /**
     * 是否阳性（SF）
     */
    private String isPositive;
    /**
     * 中草药脚注代码（ZYJZ）（中药调剂方法）
     */
    private String herbNoteCode;
    /**
     * 领药药房ID
     */
    private String pharId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 医嘱预停时间（长期）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planStopTime;
    /**
     * 医技申请单号
     */
    private String technologyNo;
    /**
     * 中草药用法（ZYYF）
     */
    private String herbUseCode;
    /**
     * 是否交病人（SF）：临时医嘱
     */
    private String isGive;
    /**
     * 第一执行人ID
     */
    private String execId;
    /**
     * 第一执行人姓名
     */
    private String execName;
    /**
     * 第二执行人id
     */
    private String secondExecId;
    /**
     * 第二执行人姓名
     */
    private String secondExecName;
    /**
     * 医嘱开始时间，长期医嘱生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date longStartTime;
    /**
     * 最近执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastExecTime;
    /**
     * 带教医生id
     */
    private String teachDoctorId;
    /**
     * 带教医生姓名
     */
    private String teachDoctorName;
    /**
     * 是否核收（SF）
     */
    private String isCheck;
    /**
     * 医嘱核收人ID
     */
    private String checkId;
    /**
     * 医嘱核收人姓名
     */
    private String checkName;
    /**
     * 医嘱核收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;
    /**
     * 停嘱医生ID
     */
    private String stopDoctorId;
    /**
     * 停嘱医生姓名
     */
    private String stopDoctorName;
    /**
     * 停嘱时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stopTime;
    /**
     * 停嘱核收人ID
     */
    private String stopCheckId;
    /**
     * 停嘱核收人姓名
     */
    private String stopCheckName;
    /**
     * 停嘱核收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stopCheckTime;
    /**
     * 是否临嘱（SF）（0：长期，1：临时）
     */
    private String isLong;
    /**
     * 是否停嘱（SF）
     */
    private String isStop;
    /**
     * 是否拒收（SF）
     */
    private String isReject;
    /**
     * 拒收备注
     */
    private String rejectRemark;
    /**
     * 创建人/开嘱医生ID
     */
    private String crteId;
    /**
     * 创建人/开嘱医生姓名
     */
    private String crteName;
    /**
     * 创建/录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    /**
     * 是否停嘱核收
     */
    private String isStopCheck;
    /**
     * 皮试换药药品ID
     */
    private String skinDurgId;
    /**
     * 皮试换药药品药房ID
     */
    private String skinPharId;
    /**
     * 皮试换药药品单位
     */
    private String skinUnitCode;
    /**
     * 皮试来源ID
     */
    private String sourceIaId;
    /**
     * 是否提交
     */
    private String isSubmit;

    /**
     * 提交人ID
     */
    private String submitId;
    /**
     * 提交人
     */
    private String submitName;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
    private Date submitTime;

    /**
     * 医嘱前缀
     */
    private String advicePrefix;
    /**
     * 医嘱后缀
     */
    private String adviceSuffix;

    /**
     * 预计采血时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date collectBloodTime;

    /**
     * 是否取消
     */
    private String isCancel;

    /**
     * 取消人ID
     */
    private String cancelId;

    /**
     * 取消人
     */
    private String cancelName;

    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
    private Date cancelTime;
    /**
     * 用药类型
     */
    private String yylx;

    /**
     * 提前领药天数
     */
    private String advanceDays;

    /**
     * 是否上传
     */
    private String isInsureUpload;

  // 临床路径项目明细ID
  private String stageDetailItemId;

  private String clinicalPathStageId;
  private String decoctionMethod;
    /**
     * 是否代办
     */
  private String isAgent;
    /**
     * 代办人
     */
  private String agentName;
    /**
     * 代办人身份证
     */
  private String agentCertNo;
    /**
     * 停嘱带教医生id
     */
  private String stopTeachDoctorId;
    /**
     * 停嘱带教医生名称
     */
  private String stopTeachDoctorName;
}
