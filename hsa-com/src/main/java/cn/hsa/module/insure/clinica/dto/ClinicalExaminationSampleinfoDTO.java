package cn.hsa.module.insure.clinica.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName ClinicalExaminationInfoDTO
* @Deacription 临床检查报告信息表dto层
* @Author liuhuiming
* @Date 2022-05-05
* @Version 1.0
**/
@Data
public class ClinicalExaminationSampleinfoDTO implements Serializable {

    /**
     * 申请单号
     */
    private String appyNo;
    /**
     * 报告单号
     */
    private String rpotcNo;
    /**
     * 采样日期
     */
    private String saplDate;
    /**
     * 标本号
     */
    private String spcmNo;
    /**
     * 标本名称
     */
    private String spcmName;




}
