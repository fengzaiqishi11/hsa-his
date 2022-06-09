package cn.hsa.module.insure.drgdip.dto;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Description: drg/dip质控业务操作日志表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
@Data
public class DrgDipBusinessOptInfoLogDTO implements Serializable {
	private static final long serialVersionUID = 1L;

  /**
   * 主键id
   */
  private String id;
  /**
   * 质控结果主表id
   */
  private String qulityId;
  /**
   * 业务id
   */
  private String businessId;
  /**
   * 操作类型1.修改 2.保存 3.质控 4.上传
   */
  private String optType;
  /**
   * 操作类型名称
   */
  private String optTypeName;
  /**
   * 类型1.DRG 2.DIP
   */
  private String type;
  /**
   * 业务类型1.结算清单 2.病案首页
   */
  private String businessType;
  /**
   * 是否强制上传1.是 2.否（默认）
   */
  private String isForce;
  /**
   * 操作提示信息
   */
  private String forceUploadInfo;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 医保注册编码
   */
  private String insureRegCode;
  /**
   * 医院名称
   */
  private String hospName;
  /**
   * 机构编码（H码）
   */
  private String orgCode;
  /**
   * 医保结算id
   */
  private String insureSettleId;
  /**
   * 就医登记号
   */
  private String medicalRegNo;
  /**
   * his结算id
   */
  private String settleId;
  /**
   * 就诊id
   */
  private String visitId;
  /**
   * 人员编号
   */
  private String psnNo;
  /**
   * 患者姓名
   */
  private String psName;
  /**
   * 患者身份证号码
   */
  private String certno;
  /**
   * 科室id
   */
  private String deptId;
  /**
   * 性别
   */
  private String sex;
  /**
   * 年龄
   */
  private String age;
  /**
   * 险种类型
   */
  private String insueType;
  /**
   * 入院时间
   */
  private Date inptTime;
  /**
   * 出院时间
   */
  private Date outptTime;
  /**
   * 业务类型
   */
  private String medType;
  /**
   * 业务类型名称
   */
  private String medTypeName;
  /**
   * 科室名称
   */
  private String deptName;
  /**
   * 医生id
   */
  private String doctorId;
  /**
   * 医生姓名
   */
  private String doctorName;
  /**
   * 入院诊断
   */
  private String inptDiagnose;
  /**
   * 出院诊断
   */
  private String outptDiagnose;
  /**
   * 总费用
   */
  private BigDecimal totalFee;
  /**
   * 基金支付合计
   */
  private BigDecimal payFee;
  /**
   * 个人账户支付
   */
  private BigDecimal selfFee;
  /**
   * 现金支付
   */
  private BigDecimal cashPayFee;
  /**
   * 数据保存入参
   */
  private String inputJson;
  /**
   * 有效标志
   */
  private String validFlag;
  /**
   * 创建人
   */
  private String crtId;
  /**
   * 创建姓名
   */
  private String crtName;
  /**
   * 创建时间
   */
  private Date crtTime;


  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setQulityId(String qulityId) {
    this.qulityId = qulityId;
  }

  public String getQulityId() {
    return qulityId;
  }

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  public String getBusinessId() {
    return businessId;
  }

  public void setOptType(String optType) {
    this.optType = optType;
  }

  public String getOptType() {
    return optType;
  }

  public void setOptTypeName(String optTypeName) {
    this.optTypeName = optTypeName;
  }

  public String getOptTypeName() {
    return optTypeName;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public String getBusinessType() {
    return businessType;
  }

  public void setIsForce(String isForce) {
    this.isForce = isForce;
  }

  public String getIsForce() {
    return isForce;
  }

  public void setForceUploadInfo(String forceUploadInfo) {
    this.forceUploadInfo = forceUploadInfo;
  }

  public String getForceUploadInfo() {
    return forceUploadInfo;
  }

  public void setHospCode(String hospCode) {
    this.hospCode = hospCode;
  }

  public String getHospCode() {
    return hospCode;
  }

  public void setInsureRegCode(String insureRegCode) {
    this.insureRegCode = insureRegCode;
  }

  public String getInsureRegCode() {
    return insureRegCode;
  }

  public void setHospName(String hospName) {
    this.hospName = hospName;
  }

  public String getHospName() {
    return hospName;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setInsureSettleId(String insureSettleId) {
    this.insureSettleId = insureSettleId;
  }

  public String getInsureSettleId() {
    return insureSettleId;
  }

  public void setMedicalRegNo(String medicalRegNo) {
    this.medicalRegNo = medicalRegNo;
  }

  public String getMedicalRegNo() {
    return medicalRegNo;
  }

  public void setSettleId(String settleId) {
    this.settleId = settleId;
  }

  public String getSettleId() {
    return settleId;
  }

  public void setVisitId(String visitId) {
    this.visitId = visitId;
  }

  public String getVisitId() {
    return visitId;
  }

  public void setPsnNo(String psnNo) {
    this.psnNo = psnNo;
  }

  public String getPsnNo() {
    return psnNo;
  }

  public void setPsName(String psName) {
    this.psName = psName;
  }

  public String getPsName() {
    return psName;
  }

  public void setCertno(String certno) {
    this.certno = certno;
  }

  public String getCertno() {
    return certno;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getSex() {
    return sex;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getAge() {
    return age;
  }

  public void setInsueType(String insueType) {
    this.insueType = insueType;
  }

  public String getInsueType() {
    return insueType;
  }

  public void setInptTime(Date inptTime) {
    this.inptTime = inptTime;
  }

  public Date getInptTime() {
    return inptTime;
  }

  public void setOutptTime(Date outptTime) {
    this.outptTime = outptTime;
  }

  public Date getOutptTime() {
    return outptTime;
  }

  public void setMedType(String medType) {
    this.medType = medType;
  }

  public String getMedType() {
    return medType;
  }

  public void setMedTypeName(String medTypeName) {
    this.medTypeName = medTypeName;
  }

  public String getMedTypeName() {
    return medTypeName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public void setInptDiagnose(String inptDiagnose) {
    this.inptDiagnose = inptDiagnose;
  }

  public String getInptDiagnose() {
    return inptDiagnose;
  }

  public void setOutptDiagnose(String outptDiagnose) {
    this.outptDiagnose = outptDiagnose;
  }

  public String getOutptDiagnose() {
    return outptDiagnose;
  }

  public void setTotalFee(BigDecimal totalFee) {
    this.totalFee = totalFee;
  }

  public BigDecimal getTotalFee() {
    return totalFee;
  }

  public void setPayFee(BigDecimal payFee) {
    this.payFee = payFee;
  }

  public BigDecimal getPayFee() {
    return payFee;
  }

  public void setSelfFee(BigDecimal selfFee) {
    this.selfFee = selfFee;
  }

  public BigDecimal getSelfFee() {
    return selfFee;
  }

  public void setCashPayFee(BigDecimal cashPayFee) {
    this.cashPayFee = cashPayFee;
  }

  public BigDecimal getCashPayFee() {
    return cashPayFee;
  }

  public void setInputJson(String inputJson) {
    this.inputJson = inputJson;
  }

  public String getInputJson() {
    return inputJson;
  }

  public void setValidFlag(String validFlag) {
    this.validFlag = validFlag;
  }

  public String getValidFlag() {
    return validFlag;
  }

  public void setCrtId(String crtId) {
    this.crtId = crtId;
  }

  public String getCrtId() {
    return crtId;
  }

  public void setCrtName(String crtName) {
    this.crtName = crtName;
  }

  public String getCrtName() {
    return crtName;
  }

  public void setCrtTime(Date crtTime) {
    this.crtTime = crtTime;
  }

  public Date getCrtTime() {
    return crtTime;
  }
}
