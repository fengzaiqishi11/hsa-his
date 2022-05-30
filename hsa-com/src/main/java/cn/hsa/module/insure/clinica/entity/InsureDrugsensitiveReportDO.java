package cn.hsa.module.insure.clinica.entity;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;
/**
* @ClassName InsureDrugsensitiveReportDO
* @Deacription 医保药敏记录上报表
* @Author liuhuiming
* @Date 2022-05-10
* @Version 1.0
**/
@Data
public class InsureDrugsensitiveReportDO extends PageDO implements Serializable {

    /**
     * 主键id
     */
    private Long uuid;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就医流水号
     */
    private String mdtrtSn;
    /**
     * 就医登记号
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
     * 报告单号
     */
    private String rpotcNo;
    /**
     * 细菌代号
     */
    private String germCode;
    /**
     * 细菌名称
     */
    private String germName;
    /**
     * 药敏代码
     */
    private String sstbCode;
    /**
     * 药敏名称
     */
    private String sstbName;
    /**
     * 抗药结果代码
     */
    private String retaRsltCode;
    /**
     * 抗药结果
     */
    private String retaRsltName;
    /**
     * 参考值
     */
    private String refVal;
    /**
     * 检验方法
     */
    private String examMtd;
    /**
     * 检验结果
     */
    private String examRslt;
    /**
     * 检验机构名称
     */
    private String examOrgName;
    /**
     * 有效标志
     */
    private String valiFlag;
    /**
     * 是否上传
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

}
