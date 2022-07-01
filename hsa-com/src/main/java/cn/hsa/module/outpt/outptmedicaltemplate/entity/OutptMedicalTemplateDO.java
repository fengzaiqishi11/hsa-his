package cn.hsa.module.outpt.outptmedicaltemplate.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.outptmedicaltemplate.entity
 * @Class_name: OutptMedicalTemplateDO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/9 14:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptMedicalTemplateDO extends PageDO implements Serializable {
  private static final long serialVersionUID = -23818809317121328L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 模板名称
   */
  private String name;
  /**
   * 模板类型代码（MBLX），0全院，1科室，2个人
   */
  private String typeCode;
  /**
   * 模板使用科室ID（科室、个人）
   */
  private String deptId;
  /**
   * 模板使用科室名称（科室、个人）
   */
  private String deptName;
  /**
   * 模板使用医生ID（个人）
   */
  private String doctorId;
  /**
   * 模板使用医生名称（个人）
   */
  private String doctorName;
  /**
   * 是否有效（SF）
   */
  private String isValid;
  /**
   * 拼音码
   */
  private String pym;
  /**
   * 五笔码
   */
  private String wbm;
  /**
   * 审核状态代码（SHZT）
   */
  private String auditCode;
  /**
   * 审核人ID
   */
  private String auditId;
  /**
   * 审核人姓名
   */
  private String auditName;
  /**
   * 审核时间
   */
  private Date auditTime;
  /**
   * 主诉
   */
  private String chiefComplaint;
  /**
   * 疾病id
   */
  private String diseaseId;
  /**
   * 现病史
   */
  private String presentIllness;
  /**
   * 既往史
   */
  private String pastHistory;
  /**
   * 个人史
   */
  private String oneselfHistory;
  /**
   * 家族史
   */
  private String familyHistory;
  /**
   * 过敏史
   */
  private String allergyHistory;
  /**
   * 预防接种史
   */
  private String vaccinationHistory;
  /**
   * 辅助检查
   */
  private String auxiliaryInspect;
  /**
   * 病种分析
   */
  private String diseaseAnalysis;
  /**
   * 处理意见
   */
  private String handleSuggestion;
  /**
   * 专科检查
   */
  private String specialtyCheck;
  /**
   * 体温
   */
  private String temperature;
  /**
   * 最低血压
   */
  private String minBloodPressure;
  /**
   * 最高血压
   */
  private String maxBloodPressure;
  /**
   * 呼吸
   */
  private String breath;
  /**
   * 身高
   */
  private String height;
  /**
   * 血糖
   */
  private String bloodSugar;
  /**
   * 脉搏
   */
  private String pulse;
  /**
   * 体重
   */
  private String weight;
  /**
   * BMI(计算公式:体重（kg）÷身高(米)^2)
   */
  private String bmi;
  /**
   * 嘱托
   */
  private String entrust;
  /**
   * 创建人ID
   */
  private String crteId;
  /**
   * 创建人姓名
   */
  private String crteName;
  /**
   * 创建时间
   */
  private Date crteTime;

  private String remarke;

  private String menstrualHistory;
}
