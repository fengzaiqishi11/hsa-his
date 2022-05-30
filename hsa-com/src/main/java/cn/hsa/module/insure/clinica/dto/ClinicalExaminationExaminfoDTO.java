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
public class ClinicalExaminationExaminfoDTO implements Serializable {

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
     * 申请单据名称
     */
    private String appyDocName;
    /**
     * 报告单号
     */
    private String rpotcNo;
    /**
     * 报告单类别代码
     */
    private String rpotcTypeCode;
    /**
     * 检查报告单名称
     */
    private String examRpotcName;
    /**
     * 检查日期
     */
    private Date examDate;
    /**
     * 报告日期
     */
    private Date rptDate;
    /**
     * 送检日期
     */
    private Date cmaDate;
    /**
     * 采样日期
     */
    private Date saplDate;
    /**
     * 标本号
     */
    private String spcmNo;
    /**
     * 标本名称
     */
    private String spcmName;
    /**
     * 检查类别代码
     */
    private String examTypeCode;
    /**
     * 检查-项目代码
     */
    private String examItemCode;
    /**
     * 检查-类别名称
     */
    private String examTypeName;
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
     * 检查部位
     */
    private String examPart;
    /**
     * 检查结果阳性标志
     */
    private String examRsltPoitFlag;
    /**
     * 检查 / 检验结果异常标志
     */
    private String examRsltAbn;
    /**
     * 检查结论
     */
    private String examCcls;
    /**
     * 申请机构名称
     */
    private String appyOrgName;
    /**
     * 申请科室代码
     */
    private String appyDeptCode;
    /**
     * 检查科室代码
     */
    private String examDeptCode;
    /**
     * 住院科室代码
     */
    private String iptDeptCode;
    /**
     * 住院科室名称
     */
    private String iptDeptName;
    /**
     * 开单医生代码
     */
    private String bilgDrCodg;
    /**
     * 开单医生姓名
     */
    private String bilgDrName;
    /**
     * 执行机构名称
     */
    private String exeOrgName;
    /**
     * 有效标志
     */
    private String valiFlag;

}
