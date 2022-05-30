package cn.hsa.module.insure.clinica.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
* @ClassName InsureClinicalCheckoutDO
* @Deacription 临床检验报告信息表
* @Author liuhuiming
* @Date 2022-05-07
* @Version 1.0
**/
@Data
public class InsureClinicalCheckoutDO extends PageDO implements Serializable {

    /**
     * 主键
     */
    private Long uuid;
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
     * 住院号/就诊号
     */
    private String visitNo;
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
     * 检查科室代码
     */
    private String examDeptName;
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
    /**
     * 上传状态
     */
    private String isUplod;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 医院编码
     */
    private String hospCode;

}
