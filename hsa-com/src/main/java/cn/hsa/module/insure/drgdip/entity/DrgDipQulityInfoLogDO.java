package cn.hsa.module.insure.drgdip.entity;

import cn.hsa.base.PageDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: DIP/DRG质控信息日志表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
@Data
public class DrgDipQulityInfoLogDO extends PageDO implements Serializable {
	private static final long serialVersionUID = 1L;

  /**
   * 主键ID
   */
  private String id;
  /**
   * 交易流水号
   */
  private String msgId;
  /**
   * 机构编码
   */
  private String orgCode;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 就诊ID
   */
  private String visitId;
  /**
   * 功能编号
   */
  private String infNo;
  /**
   * 功能名称
   */
  private String infName;
  /**
   * 请求时间
   */
  private Date reqTime;
  /**
   * 响应时间
   */
  private Date respTime;
  /**
   * 请求入参
   */
  private String reqContent;
  /**
   * 返回入参
   */
  private String respContent;
  /**
   * 状态 Y正常 N异常
   */
  private String status;
  /**
   * 类型 1.DRG 2.DIP
   */
  private String type;
  /**
   * 业务类型 1结算清单 2病案首页
   */
  private String businessType;
  /**
   * 质控提示信息
   */
  private String forceUploadInfo;
  /**
   * 有效标志
   */
  private String validFlag;
  /**
   * 创建人ID
   */
  private String crtId;
  /**
   * 创建人名称
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

  public void setMsgId(String msgId) {
    this.msgId = msgId;
  }

  public String getMsgId() {
    return msgId;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setHospCode(String hospCode) {
    this.hospCode = hospCode;
  }

  public String getHospCode() {
    return hospCode;
  }

  public void setVisitId(String visitId) {
    this.visitId = visitId;
  }

  public String getVisitId() {
    return visitId;
  }

  public void setInfNo(String infNo) {
    this.infNo = infNo;
  }

  public String getInfNo() {
    return infNo;
  }

  public void setInfName(String infName) {
    this.infName = infName;
  }

  public String getInfName() {
    return infName;
  }

  public void setReqTime(Date reqTime) {
    this.reqTime = reqTime;
  }

  public Date getReqTime() {
    return reqTime;
  }

  public void setRespTime(Date respTime) {
    this.respTime = respTime;
  }

  public Date getRespTime() {
    return respTime;
  }

  public void setReqContent(String reqContent) {
    this.reqContent = reqContent;
  }

  public String getReqContent() {
    return reqContent;
  }

  public void setRespContent(String respContent) {
    this.respContent = respContent;
  }

  public String getRespContent() {
    return respContent;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
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

  public void setForceUploadInfo(String forceUploadInfo) {
    this.forceUploadInfo = forceUploadInfo;
  }

  public String getForceUploadInfo() {
    return forceUploadInfo;
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
