package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 结算信息
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-20 16:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetlInfoDTO {
  /**
   * 结算ID
   */
  private String setlId;
  /**
   * 就诊ID
   */
  private String mdtrtId;
  /**
   * 关联就诊ID
   */
  private String mdtrtRelaId;
  /**
   * 数据来源
   */
  private String dataSource;
  /**
   * 中途结算标志
   */
  private String midSetlFlag;
  /**
   * 结算时间
   */
  private Date setltime;
  /**
   * 参保所属医保区划
   */
  private String insuAdmdvs;
  /**
   * 险种类型
   */
  private String insutype;
  /**
   * 人员类别
   */
  private String psnType;
  /**
   * 医疗类别
   */
  private String medType;
  /**
   * 待遇类型
   */
  private String medTrtType;
  /**
   * 医院订单号
   */
  private String orderNo;
  /**
   * 人员编号
   */
  private String psnNo;
  /**
   * 就诊凭证类型
   */
  private String psnCertType;
  /**
   * 就诊凭证编号
   */
  private String certno;
  /**
   * 人员姓名
   */
  private String psnName;
  /**
   * 性别
   */
  private String gend;
  /**
   * 出生日期
   */
  private Date brdy;
  /**
   * 年龄
   */
  private BigDecimal age;
  /**
   * 联系人姓名
   */
  private String conerName;
  /**
   * 联系电话
   */
  private String tel;
  /**
   * 联系地址
   */
  private String linkAddr;
  /**
   * 开始时间
   */
  private Date begntime;
  /**
   * 结束时间
   */
  private Date endtime;
  /**
   * 结束时间
   */
  private Date midSetlBegntime;
  /**
   * 结束时间
   */
  private Date midSetlEndtime;
  /**
   * 主诊医师代码
   */
  private String medstffCode;
  /**
   * 主诊医师姓名
   */
  private String medstffName;
  /**
   * 入院科室编码
   */
  private String admDeptCodg;
  /**
   * 入院科室名称
   */
  private String admDeptName;
  /**
   * 入院床位
   */
  private String admBed;
  /**
   * 出院科室编码
   */
  private String dscgDeptCodg;
  /**
   * 出院科室名称
   */
  private String dscgDeptName;
  /**
   * 出院床位
   */
  private String dscgBed;
  /**
   * 在院状态
   */
  private String inhospStas;
  /**
   * 离院方式
   */
  private String setlDscr;
  /**
   * 在院状态
   */
  private String setlStas;
  /**
   * 住院天数
   */
  private BigDecimal iptDays;
  /**
   * 取消结算标志
   */
  private String cancelSetlFlag;
  /**
   * 护理计划编号
   */
  private String nurscarePlanId;
  /**
   * 评估结果编号
   */
  private String evalRsltId;
  /**
   * 失能评估情况
   */
  private String dsabasmtEvalRslt;
  /**
   * 现住址所属区编号
   */
  private String addrInAreaCodg;
  /**
   * 现住址所属区名称
   */
  private String addrInAreaName;
  /**
   * 现住址所属街道编号
   */
  private String addrInSubdCodg;
  /**
   * 现住址所属街道名称
   */
  private String addrInSubdName;
  /**
   * 现住址详细地址
   */
  private String detlAddr;
  /**
   * 备注
   */
  private String memo;
  /**
   * 结算类别
   */
  private String setlType;
  /**
   * 清算类别
   */
  private String clrType;
  /**
   * 清算方式
   */
  private String clrWay;
  /**
   * 个人结算方式
   */
  private String psnSetlway;
  /**
   * 清算经办机构
   */
  private String clrOptins;
  /**
   * 医疗费总额
   */
  private BigDecimal medfeeSumamt;
  /**
   * 统筹基金支出
   */
  private BigDecimal hifpPay;
  /**
   * 基金支付总额
   */
  private BigDecimal fundPaySumamt;
  /**
   * 个人支付金额
   */
  private BigDecimal psnPay;
  /**
   *
   */
  private BigDecimal selfpayAmt;
  /**
   * 个人支付金额
   */
  private BigDecimal fundCumPay;
  /**
   * 全自费金额
   */
  private BigDecimal fulAmtOwnpayAmt;
  /**
   * 个人账户支出
   */
  private BigDecimal acctPay;
  /**
   * 其他支出
   */
  private BigDecimal othPay;
  /**
   * 个人负担总金额
   */
  private BigDecimal psnCashPay;
  /**
   * 个人账户支出
   */
  private BigDecimal psnPartAmt;
  /**
   * 医院负担金额
   */
  private BigDecimal hospPartAmt;
  /**
   * 医疗救助基金支出
   */
  private BigDecimal mafPay;
}
