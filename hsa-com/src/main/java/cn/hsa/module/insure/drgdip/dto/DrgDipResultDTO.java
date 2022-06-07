package cn.hsa.module.insure.drgdip.dto;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Description: DIP/DRG质控结果表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
@Data
public class DrgDipResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 权重
	 */
	private String weight;
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

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeight() {
		return weight;
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
