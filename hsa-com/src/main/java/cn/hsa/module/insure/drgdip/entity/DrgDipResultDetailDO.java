package cn.hsa.module.insure.drgdip.entity;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Description: DIP/DRG质控信息结果明细表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
@Data
public class DrgDipResultDetailDO implements Serializable {
	private static final long serialVersionUID = 1L;

  /**
   * id
   */
  private String id;
  /**
   * 质控结果主表id
   */
  private String resultId;
  /**
   * 规则等级
   */
  private String ruleLevel;
  /**
   * 规则分数
   */
  private String ruleScore;
  /**
   * 错误字段字段名
   */
  private String fieldId;
  /**
   * 错误字段字段中文名
   */
  private String checkFiled;
  /**
   * 质控结果信息
   */
  private String resultMsg;
  /**
   * 字段原始值
   */
  private String originalValue;
  /**
   * 主次标识
   */
  private String isMain;
  /**
   * 排序号
   */
  private String sort;
  /**
   * 规则类型
   */
  private String ruletType;
  /**
   * 有效标志
   */
  private String validFlag;
  /**
   * 创建人编号
   */
  private String crtId;
  /**
   * 创建人姓名
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

  public void setResultId(String resultId) {
    this.resultId = resultId;
  }

  public String getResultId() {
    return resultId;
  }

  public void setRuleLevel(String ruleLevel) {
    this.ruleLevel = ruleLevel;
  }

  public String getRuleLevel() {
    return ruleLevel;
  }

  public void setRuleScore(String ruleScore) {
    this.ruleScore = ruleScore;
  }

  public String getRuleScore() {
    return ruleScore;
  }

  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  public String getFieldId() {
    return fieldId;
  }

  public void setCheckFiled(String checkFiled) {
    this.checkFiled = checkFiled;
  }

  public String getCheckFiled() {
    return checkFiled;
  }

  public void setResultMsg(String resultMsg) {
    this.resultMsg = resultMsg;
  }

  public String getResultMsg() {
    return resultMsg;
  }

  public void setOriginalValue(String originalValue) {
    this.originalValue = originalValue;
  }

  public String getOriginalValue() {
    return originalValue;
  }

  public void setIsMain(String isMain) {
    this.isMain = isMain;
  }

  public String getIsMain() {
    return isMain;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getSort() {
    return sort;
  }

  public void setRuletType(String ruletType) {
    this.ruletType = ruletType;
  }

  public String getRuletType() {
    return ruletType;
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
