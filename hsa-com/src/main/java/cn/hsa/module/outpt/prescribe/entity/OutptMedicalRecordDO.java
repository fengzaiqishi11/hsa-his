package cn.hsa.module.outpt.prescribe.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.entity
 *@Class_name: OutptPrescribeTempDO
 *@Describe: 门诊病历
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020/9/3 14:00
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptMedicalRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 104114576908635128L;
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
     * 个人档案ID
     */
    private String profileId;
    /**
     * 诊断ID
     */
    private String diseaseId;
    /**
     * 发病日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 主诉
     */
    private String chiefComplaint;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    private String remarke;
    /**
     * 中医症候id
     */
    private String tcmSyndromesId;

    /**
     * 中医诊断id
     */
    private String tcmDiseaseId;

    /**
     * 月经史
     */
    private String menstrualHistory;

}
