package cn.hsa.module.insure.drgdip.dto;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
 * @Description: DIP/DRG质控结果表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
@Data
public class DrgDipResultDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

  private int pageNo = 1;
  private int pageSize = 10;
  /**
   * 主键
   */
  private String id;
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
  private String psnName;
  /**
   * 患者身份证号码
   */
  private String certno;
  /**
   * 性别
   */
  private String gend;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 险种
   */
  private String insutype;
  /**
   * 业务类型
   */
  private String medType;
  /**
   * 业务类型名称
   */
  private String medTypeName;
  /**
   * 入院时间
   */
  private Date inTime;
  /**
   * 出院时间
   */
  private Date outTime;
  /**
   * 入院诊断
   */
  private String inptDiagnose;
  /**
   * 出院诊断
   */
  private String outptDiagnose;
  /**
   * 科室id
   */
  private String deptId;
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
   * 就医登记号
   */
  private String medicalRegNo;
  /**
   * his结算id
   */
  private String settleId;
  /**
   * 医保结算id
   */
  private String insureSettleId;
  /**
   * drg\dip组编码
   */
  private String drgDipCode;
  /**
   * drg\dip组名称
   */
  private String drgDipName;
  /**
   * 主要诊断
   */
  private String icd10;
  /**
   * 主要手术
   */
  private String icd9;
  /**
   * 总费用
   */
  private BigDecimal totalFee;
  /**
   * 标杆费用
   */
  private BigDecimal standFee;
  /**
   * 倍率
   */
  private BigDecimal override;
  /**
   * 统筹金额
   */
  private BigDecimal cdntnPay;
  /**
   * 预计偏差
   */
  private BigDecimal diffFee;
  /**
   * 药品费
   */
  private BigDecimal ypFee;
  /**
   * 耗材费
   */
  private BigDecimal clFee;
  /**
   * 质控完成状态
   */
  private String states;
  /**
   * 质控类型
   */
  private String type;
  /**
   * 业务类型
   */
  private String businessType;
  /**
   * 业务id
   */
  private String businessId;
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
   * 机构编码
   */
  private String orgCode;
  /**
   * 药占比
   */
  private String proMedicMater;
  /**
   * 权重
   */
  private String weightValue;
  /**
   * 耗材占比
   */
  private String proConsum;
  /**
   * 费用分值
   */
  private String diagFeeSco;
  /**
   * cc/mcc信息
   */
  private String withCcormcc;
  /**
   * cc/mcc信息
   */
  private String ccmccName;
  /**
   * 倍率
   */
  private String bl;
  /**
   * 费率
   */
  private String rate;
  /**
   * 支付费用
   */
  private BigDecimal feePay;
  /**
   * 标杆药占比
   */
  private String standProMedicMater;
  /**
   * 标杆耗材占比
   */
  private String standProConsum;
  /**
   * 盈亏额
   */
  private BigDecimal profit;
  /**
   * 可疑条数
   */
  private Integer suspiciousNum;
  /**
   * 违规条数
   */
  private Integer violationNum;
  /**
   * 分组结果
   */
  private String groupResult;
  /**
   * 分组提示
   */
  private String groupMessages;
  /**
   * 有效标志
   */
  private String validFlag;
  /**
   * 病案号
   */
  private String medcasno;
  /**
   * 住院号/就诊号
   */
  private String visitNo;
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
  /**
   * 是否上次
   */
  private String isUplod;
  /**
   * 是否存在可疑条数
   */
  private String suspiciousStates;
  /**
   * 是否存在违规条数
   */
  private String violationStates;
  /**
   * 结算时间
   */
  private String setlTime;
  /**
   * 模糊查询条件
   */
  private String keyword;

  /**
   * 模糊查询条件
   */
  private String keywords;
  /**
   * 主诊断代码
   */
  private String visitIcdCode;
  /**
   * 主诊断名称
   */
  private String visitIcdName;
  /**
   * 年份
   */
  private String year;
  /**
   * 开始年月
   */
  private String startYearMonth;
  /**
   * 结束年月
   */
  private String endYearMonth;
  /**
   * 开始时间
   */
  private String startDate;
  /**
   * 结束时间
   */
  private String endDate;
  /**
   * 在院状态
   */
  private String statusCode;

  /**
   * 上传状态
   */
  private String isUploadInsure;
  /**
   * 姓名年龄性别
   */
  private String nameGendAge;
  /**
   * 是否在院
   */
  private String isHospital;
  /**
   * 既有可疑又有违规
   */
  private String sv;
  /**
   * 分值单价
   */
  private BigDecimal scorePrice;
    /**
     * 科室id集合
     */
    private List<String> deptIds;
    /**
     * 医生id集合
     */
    private List<String> doctorIds;
    /**
     * 上传状态集合
     */
    private List<String> isUplods;
    /**
     * 质控状态集合
     */
    private List<String> statess;
    /**
     * 分组结果集合
     */
    private List<String> groupResults;
    /**
     * 在院状态集合
     */
    private List<String> statusCodes;
    /**
     * 是否住院集合
     */
    private List<String> isHospitals;
  /**
   * 年月份选择类型
   */
    private String yearFlag;
  /**
   * 违规等级
   */
    private String ruletType;
  /**
   * 错误字段
   */
    private String checkFiled;
  /**
   * 违规错误信息
   */
    private String resultMsg;
  /**
   * 违规等级
   */
  private List<String> level;


  public void setYearFlag(String yearFlag) {
    this.id = yearFlag;
  }

  public String getYearFlag() {
    return yearFlag;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
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

  public void setPsnName(String psnName) {
    this.psnName = psnName;
  }

  public String getPsnName() {
    return psnName;
  }

  public void setCertno(String certno) {
    this.certno = certno;
  }

  public String getCertno() {
    return certno;
  }

  public void setGend(String gend) {
    this.gend = gend;
  }

  public String getGend() {
    return gend;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getAge() {
    return age;
  }

  public void setInsutype(String insutype) {
    this.insutype = insutype;
  }

  public String getInsutype() {
    return insutype;
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

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setOutTime(Date outTime) {
    this.outTime = outTime;
  }

  public Date getOutTime() {
    return outTime;
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

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getDeptId() {
    return deptId;
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

  public void setInsureSettleId(String insureSettleId) {
    this.insureSettleId = insureSettleId;
  }

  public String getInsureSettleId() {
    return insureSettleId;
  }

  public void setDrgDipCode(String drgDipCode) {
    this.drgDipCode = drgDipCode;
  }

  public String getDrgDipCode() {
    return drgDipCode;
  }

  public void setDrgDipName(String drgDipName) {
    this.drgDipName = drgDipName;
  }

  public String getDrgDipName() {
    return drgDipName;
  }

  public void setIcd10(String icd10) {
    this.icd10 = icd10;
  }

  public String getIcd10() {
    return icd10;
  }

  public void setIcd9(String icd9) {
    this.icd9 = icd9;
  }

  public String getIcd9() {
    return icd9;
  }

  public void setTotalFee(BigDecimal totalFee) {
    this.totalFee = totalFee;
  }

  public BigDecimal getTotalFee() {
    return totalFee;
  }

  public void setStandFee(BigDecimal standFee) {
    this.standFee = standFee;
  }

  public BigDecimal getStandFee() {
    return standFee;
  }

  public void setOverride(BigDecimal override) {
    this.override = override;
  }

  public BigDecimal getOverride() {
    return override;
  }

  public void setCdntnPay(BigDecimal cdntnPay) {
    this.cdntnPay = cdntnPay;
  }

  public BigDecimal getCdntnPay() {
    return cdntnPay;
  }

  public void setDiffFee(BigDecimal diffFee) {
    this.diffFee = diffFee;
  }

  public BigDecimal getDiffFee() {
    return diffFee;
  }

  public void setYpFee(BigDecimal ypFee) {
    this.ypFee = ypFee;
  }

  public BigDecimal getYpFee() {
    return ypFee;
  }

  public void setClFee(BigDecimal clFee) {
    this.clFee = clFee;
  }

  public BigDecimal getClFee() {
    return clFee;
  }

  public void setStates(String states) {
    this.states = states;
  }

  public String getStates() {
    return states;
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

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  public String getBusinessId() {
    return businessId;
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

  public void setProMedicMater(String proMedicMater) {
    this.proMedicMater = proMedicMater;
  }

  public String getProMedicMater() {
    return proMedicMater;
  }

  public void setWeightValue(String weightValue) {
    this.weightValue = weightValue;
  }

  public String getWeightValue() {
    return weightValue;
  }

  public void setProConsum(String proConsum) {
    this.proConsum = proConsum;
  }

  public String getProConsum() {
    return proConsum;
  }

  public void setDiagFeeSco(String diagFeeSco) {
    this.diagFeeSco = diagFeeSco;
  }

  public String getDiagFeeSco() {
    return diagFeeSco;
  }

  public void setWithCcormcc(String withCcormcc) {
    this.withCcormcc = withCcormcc;
  }

  public String getWithCcormcc() {
    return withCcormcc;
  }

  public void setCcmccName(String ccmccName) {
    this.ccmccName = ccmccName;
  }

  public String getCcmccName() {
    return ccmccName;
  }

  public void setBl(String bl) {
    this.bl = bl;
  }

  public String getBl() {
    return bl;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getRate() {
    return rate;
  }

  public void setFeePay(BigDecimal feePay) {
    this.feePay = feePay;
  }

  public BigDecimal getFeePay() {
    return feePay;
  }

  public void setStandProMedicMater(String standProMedicMater) {
    this.standProMedicMater = standProMedicMater;
  }

  public String getStandProMedicMater() {
    return standProMedicMater;
  }

  public void setStandProConsum(String standProConsum) {
    this.standProConsum = standProConsum;
  }

  public String getStandProConsum() {
    return standProConsum;
  }

  public void setProfit(BigDecimal profit) {
    this.profit = profit;
  }

  public BigDecimal getProfit() {
    return profit;
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
