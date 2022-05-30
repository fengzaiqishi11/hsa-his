package cn.hsa.module.insure.clinica.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.hsa.module.insure.clinica.entity.InsureClinicalCheckoutDO;
import lombok.Data;
/**
 * @ClassName ClinicalExaminationInfoDTO
 * @Deacription 临床检查报告信息表dto层
 * @Author liuhuiming
 * @Date 2022-05-05
 * @Version 1.0
 **/
@Data
public class InsureClinicalCheckoutLabinfoDTO implements Serializable {

    /**
     * 就医流水号
     */
    private String mdtrtSn;
    /**
     * 就诊ID
     */
    private String mdtrtId;
    /**
     * 人员编号
     */
    private String psnNo;
    /**
     * 申请单号
     */
    private String appyNo;
    /**
     * 申请机构代码
     */
    private String appyOrgCode;
    /**
     * 申请机构名称
     */
    private String appyOrgName;
    /**
     * 开单医生代码
     */
    private String bilgDrCodg;
    /**
     * 开单医生姓名
     */
    private String bilgDrName;
    /**
     * 检验机构代码
     */
    private String examOrgCode;
    /**
     * 检验机构名称
     */
    private String examOrgName;
    /**
     * 申请科室代码
     */
    private String appyDeptCode;
    /**
     * 检查科室代码
     */
    private String examDeptCode;
    /**
     * 检验方法
     */
    private String examMtd;
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
     * 报告日期
     */
    private String rptDate;
    /**
     * 报告医师
     */
    private String rpotDoc;
    /**
     * 检查费用
     */
    private BigDecimal examCharge;
    /**
     * 有效标志
     */
    private String valiFlag;
}
