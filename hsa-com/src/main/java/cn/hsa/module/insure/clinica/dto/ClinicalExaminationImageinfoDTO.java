package cn.hsa.module.insure.clinica.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @ClassName ClinicalExaminationInfoDTO
* @Deacription 临床检查报告信息表dto层
* @Author liuhuiming
* @Date 2022-05-05
* @Version 1.0
**/
@Data
public class ClinicalExaminationImageinfoDTO implements Serializable {

    /**
     * 报告单号
     */
    private String rpotcNo;
    /**
     * 全局唯一号
     */
    private String studyUid;
    /**
     * 检查号
     */
    private String patientId;
    /**
     * 患者姓名
     */
    private String patientName;
    /**
     * 图像流水号
     */
    private Integer acessionNo;
    /**
     * 检查时间
     */
    private Date studyTime;
    /**
     * 检查类型
     */
    private String modality;
    /**
     * 存储路径
     */
    private String storePath;
    /**
     * 序列数量
     */
    private BigDecimal seriesCount;
    /**
     * 图像数量
     */
    private BigDecimal imageCount;




}
