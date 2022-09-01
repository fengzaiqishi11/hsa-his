package cn.hsa.module.inpt.inptprint.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.inptprint.entity
 * @Class_name: InptAdvicePrintDO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/15 10:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InptAdvicePrintDO extends PageDO implements Serializable {
  private static final long serialVersionUID = -58618438095047392L;
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
   * 住院医嘱ID（inpt_advice.id）
   */
  private String iaId;
  /**
   * 医嘱单号
   */
  private String orderNo;
  /**
   * 医嘱组号
   */
  private Integer groupNo;
  /**
   * 医嘱组内序号
   */
  private Integer groupSeqNo;
  /**
   * 医嘱内容
   */
  private String content;
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
   * 是否皮试：0否、1是（SF）
   */
  private String isSkin;
  /**
   * 是否阳性（SF）
   */
  private String isPositive;
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
   * 最近执行时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastExecTime;
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
   * 开嘱信息是否打印
   */
  private String isInPrint;
  /**
   * 停嘱信息是否打印
   */
  private String isStopPrint;
  /**
   * 执行信息是否打印
   */
  private String isExecPrint;
  /**
   * 皮试信息是否打印
   */
  private String isSkinPrint;
  /**
   * 开嘱核收信息是否打印
   */
  private String isInCheckPrint;
  /**
   * 停嘱核收信息是否打印
   */
  private String isStopCheckPrint;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date longStartTime;
  /**
   * 序号
   */
  private Integer seqNo;
  /**
   * 是否有效
   */
  private String isValid;
  /**
   * 取消人id
   */
  private String cancelId;
  /**
   * 取消人
   */
  private String cancelName;
  /**
   * 取消时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date cancelTime;
  /**
   * 皮试结果
   */
  private String skinResultCode;

  /**
   * 医嘱核收核对人ID
   */
  private String checkSecondId;
  /**
   * 医嘱核收核对人姓名
   */
  private String checkSecondName;
  /**
   * 医嘱核收核对时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date checkSecondTime;

  /**
   * 停嘱医嘱核收核对人ID
   */
  private String stopCheckSecondId;
  /**
   * 停嘱医嘱核收核对人姓名
   */
  private String stopCheckSecondName;
  /**
   * 停嘱医嘱核收核对时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date stopCheckSecondTime;

  /**
   * 核对签名名称
   */
  private String isNewStatus;
}
