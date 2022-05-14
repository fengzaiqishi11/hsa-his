package cn.hsa.module.outpt.fees.dto;

import cn.hsa.module.outpt.fees.entity.OutptCostDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 订单支付-费用明细上传DTO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-25 11:44
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnlinePayFeeDTO implements Serializable {

  /**
   * 序列号
   */
  private static final long serialVersionUID = -8142117462251296112L;

  /**
   * 机构编码
   */
  private String orgCodg;

  /**
   * 医保注册编码
   */
  private String insureRegCode;

  /**
   * 就诊号
   */
  private String visitId;

  /**
   * 电子凭证机构号
   */
  private String orgId;

  /**
   * 险种类型
   */
  private String insutype;

  /**
   * 人员编号
   */
  private String psnNo;

  /**
   * 医疗机构订单号
   */
  private String medOrgOrd;

  /**
   * 要续方的原处方流水
   */
  private String initRxOrd;

  /**
   * 电子处方流转标志
   */
  private String rxCircFlag;

  /**
   * 开始时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date begntime;

  /**
   * 证件号码
   */
  private String idNo;

  /**
   * 用户姓名
   */
  private String userName;

  /**
   * 证件类别
   */
  private String idType;

  /**
   * 电子凭证授权 ecToken
   */
  private String ecToken;

  /**
   * 参保地
   */
  private String insuCode;

  /**
   * 住院/门诊号
   */
  private String iptOtpNo;

  /**
   * 医师编码
   */
  private String atddrNo;

  /**
   * 医师姓名
   */
  private String drName;

  /**
   * 科室编码
   */
  private String deptCode;

  /**
   * 科室名称
   */
  private String deptName;

  /**
   * 科别
   */
  private String caty;

  /**
   * 就诊 ID
   */
  private String mdtrtId;

  /**
   * 医疗类别
   */
  private String medType;

  /**
   * 费用类别
   */
  private String feeType;

  /**
   * 医疗费总额
   */
  private BigDecimal medfeeSumamt;

  /**
   * 个人账户使用标志
   */
  private String acctUsedFlag;

  /**
   * 主要病情描述
   */
  private String mainCondDscr;

  /**
   * 病种编码
   */
  private String diseCodg;

  /**
   * 病种名称
   */
  private String diseName;

  /**
   * 个人结算方式
   */
  private String psnSetlway;

  /**
   * 收费批次号
   */
  private String chrgBchno;

  /**
   * 出院时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endtime;

  /**
   * 发票号
   */
  private String invono;

  /**
   * 全自费金额
   */
  private BigDecimal fulamtOwnpayAmt;

  /**
   * 超限价金额
   */
  private BigDecimal overlmtSelfpay;

  /**
   * 先行自付金额
   */
  private BigDecimal preselfpayAmt;

  /**
   * 符合政策范围金额
   */
  private BigDecimal inscpScpAmt;

  /**
   * 手术操作代码
   */
  private String oprnOprtCode;

  /**
   * 手术操作名称
   */
  private String oprnOprtName;

  /**
   * 计划生育服务证号
   */
  private String fpscNo;

  /**
   * 晚育标志
   */
  private String latechbFlag;

  /**
   * 孕周数
   */
  private BigDecimal gesoVal;

  /**
   * 胎次
   */
  private BigDecimal fetts;

  /**
   * 胎儿数
   */
  private BigDecimal fetusCnt;

  /**
   * 早产标志
   */
  private String pretFlag;

  /**
   * 计划生育手术类别
   */
  private String birctrlType;

  /**
   * 计划生育手术或生育日期
   */
  private String birctrlMatnDate;

  /**
   * 伴有并发症标志
   */
  private String copFlag;

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
  private String dscgDed;

  /**
   * 离院方式
   */
  private String dscgWay;

  /**
   * 死亡日期
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date dieDate;

  /**
   * 生育类别
   */
  private String matnType;

  /**
   * 扩展字段
   */
  private String extData;

  /**
   * 中途结算标志
   */
  private String midSetlFlag;

  /**
   * 入院诊断描述
   */
  private String admDiagDscr;

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
   * 支付授权码
   */
  private String payAuthNo;

  /**
   * 经纬度
   */
  private String uldLatlnt;

  /**
   * 就诊凭证类型
   */
  private String mdtrtCertType;

  /**
   * 诊断集合
   */
  private List<DiseInfoDTO> diseinfoList;

  /**
   * 费用明细集合
   */
  private List<FeeDetailDTO> feedetailList;

  /**
   * 界面费用明细集合
   */
  private List<OutptCostDTO> costList;

}
