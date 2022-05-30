package cn.hsa.module.insure.clinica.entity;

import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
* @ClassName InsureBacterialReportDO
* @Deacription 细菌培养报告记录信息表
* @Author liuhuiming
* @Date 2022-05-09
* @Version 1.0
**/
@Data
public class InsureBacterialReportDO extends PageDO implements Serializable {

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
     * 就诊id
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
     * 菌落计数
     */
    private String colyCntg;
    /**
     * 培养基
     */
    private String clteMedm;
    /**
     * 培养时间
     */
    private String clteTime;
    /**
     * 培养条件
     */
    private String clteCond;
    /**
     * 检验结果
     */
    private String examRslt;
    /**
     * 发现方式
     */
    private String fndWay;
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
