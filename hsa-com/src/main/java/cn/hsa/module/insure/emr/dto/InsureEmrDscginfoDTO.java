package cn.hsa.module.insure.emr.dto;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrDscginfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrDscginfoDTO
* @Deacription 医保电子病历上传-出院记录dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrDscginfoDTO extends InsureEmrDscginfoDO implements Serializable {

    /**
     * 主键ID
     */
    private Long uuid;
    /**
     * 源主键ID
     */
    private Long orgUuid;
    /**
     * 就医流水号,院内唯一号
     */
    private String mdtrtSn;
    /**
     * 医保病人必填,医保就诊ID
     */
    private String mdtrtId;
    /**
     * 医保病人必填,医保人员编号
     */
    private String psnNo;
    /**
     * 出院日期
     */
    private Date dscgDate;
    /**
     * 入院诊断描述
     */
    private String admDiagDscr;
    /**
     * 出院诊断
     */
    private String dscgDiseDscr;
    /**
     * 入院情况
     */
    private String admInfo;
    /**
     * 诊治经过及结果（含手术日期名称及结果）
     */
    private String trtProcRsltDscr;
    /**
     * 出院情况（含治疗效果）
     */
    private String dscgInfo;
    /**
     * 出院医嘱
     */
    private String dscgDrord;
    /**
     * 科别
     */
    private String caty;
    /**
     * 记录医师
     */
    private String recDoc;
    /**
     * 主要药品名称
     */
    private String mainDrugName;
    /**
     * 其他重要信息
     */
    private String othImpInfo;
    /**
     * 有效标志
     */
    private String valiFlag;
    /**
     * 数据来源：1.HIS，2.手动添加
     */
    private String source;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 医院编码
     */
    private String hospCode;

}
