package cn.hsa.module.outpt.fees.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: TODO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-25 14:47
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeeDetailDTO implements Serializable {

  /**
   * 序列号
   */
  private static final long serialVersionUID = -8142117462251296112L;
  /**
   * 费用ID
   */
  private String costId;
  /**
   * 费用明细流水号
   */
  private String feedetlSn;

  /**
   * 就诊 ID
   */
  private String mdtrtId;

  /**
   * 人员编号
   */
  private String psnNo;

  /**
   * 收费批次号
   */
  private String chrgBchno;

  /**
   * 病种编码
   */
  private String diseCodg;

  /**
   * 处方号
   */
  private String rxno;

  /**
   * 外购处方标志
   */
  private String rxCircFlag;

  /**
   * 费用发生时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date feeOcurTime;

  /**
   * 医疗目录编码
   */
  private String medListCodg;

  /**
   * 医药机构目录编码
   */
  private String medinsListCodg;

  /**
   * 明细项目费用总额
   */
  private BigDecimal detItemFeeSumamt;

  /**
   * 数量
   */
  private BigDecimal cnt;

  /**
   * 单价
   */
  private BigDecimal pric;

  /**
   * 单次剂量描述
   */
  private String sinDosDscr;

  /**
   * 使用频次描述
   */
  private String usedFrquDscr;

  /**
   * 周期天数
   */
  private BigDecimal prdDays;

  /**
   * 用药途径描述
   */
  private String medcWayDscr;

  /**
   * 开单科室编码
   */
  private String bilgDeptCodg;

  /**
   * 开单科室名称
   */
  private String bilgDeptName;

  /**
   * 开单医生编码
   */
  private String bilgDrCodg;

  /**
   * 开单医师姓名
   */
  private String bilgDrName;

  /**
   * 受单科室编码
   */
  private String acordDeptCodg;

  /**
   * 受单科室名称
   */
  private String acordDeptName;

  /**
   * 受单医生编码
   */
  private String ordersDrCode;

  /**
   * 受单医生姓名
   */
  private String ordersDrName;

  /**
   * 医院审批标志
   */
  private String hospApprFlag;

  /**
   * 中药使用方式
   */
  private String tcmdrugUsedWay;

  /**
   * 外检标志
   */
  private String etipFlag;

  /**
   * 外检医院编码
   */
  private String etipHospCode;

  /**
   * 出院带药标志
   */
  private String dscgTkdrugFlag;

  /**
   * 生育费用标志
   */
  private String matnFeeFlag;

  /**
   * 原费用流水号
   */
  private String initFeedetlSn;

  /**
   * 医嘱号
   */
  private String drordNo;

  /**
   * 医疗类别
   */
  private String medType;

  /**
   * 备注
   */
  private String memo;

  /**
   * 扩展字段
   */
  private String extData;

  /**
   * 医疗目录名称
   */
  private String medListName;

  /**
   * 医疗目录规格
   */
  private String medListSpc;
  /**
   * 医保目录类别
   */
  private String itemtype;
}
