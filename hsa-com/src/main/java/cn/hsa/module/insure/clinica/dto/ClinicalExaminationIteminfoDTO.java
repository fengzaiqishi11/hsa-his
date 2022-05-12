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
public class ClinicalExaminationIteminfoDTO implements Serializable {


    /**
     * 申请单号
     */
    private String appyNo;
    /**
     * 报告单号
     */
    private String rpotcNo;
    /**
     * 检查-项目代码
     */
    private String examItemCode;
    /**
     * 检查-项目名称
     */
    private String examItemName;
    /**
     * 院内检查-项目代码
     */
    private String inhospExamItemCode;
    /**
     * 院内检查-项目名称
     */
    private String inhospExamItemName;
    /**
     * 检查费用
     */
    private BigDecimal examCharge;
    /**
     * 检验方法
     */
    private String examMtd;
    /**
     * 参考值
     */
    private String refVal;
    /**
     * 检验-计量单位
     */
    private String examUnt;
    /**
     * 检验-结果(数值)
     */
    private BigDecimal examRsltVal;
    /**
     * 检验 - 结果(定性)
     */
    private String examRsltDicm;
    /**
     * 检验 - 项目明细代码
     */
    private String examItemDetlCode;
    /**
     * 检验 - 项目明细名称
     */
    private String examItemDetlName;
    /**
     * 检查 / 检验结果异常标志
     */
    private String examRsltAbn;

}
