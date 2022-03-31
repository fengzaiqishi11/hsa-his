package cn.hsa.module.insure.emr.entity;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
* @ClassName InsureEmrDieinfoDO
* @Deacription 医保电子病历上传-死亡记录
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrDieinfoDO extends PageDO implements Serializable {

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
     * 科室代码
     */
    private String dept;
    /**
     * 科室名称
     */
    private String deptName;
    /**
     * 病区名称
     */
    private String wardareaName;
    /**
     * 病床号
     */
    private String bedno;
    /**
     * 入院时间
     */
    private Date admTime;
    /**
     * 入院诊断编码
     */
    private String admDise;
    /**
     * 入院情况
     */
    private String admInfo;
    /**
     * 诊疗过程描述
     */
    private String trtProcDscr;
    /**
     * 死亡时间
     */
    private Date dieTime;
    /**
     * 直接死亡原因名称
     */
    private String dieDrtRea;
    /**
     * 直接死亡原因编码
     */
    private String dieDrtReaCode;
    /**
     * 死亡诊断名称
     */
    private String dieDiseName;
    /**
     * 死亡诊断编码
     */
    private String dieDiagCode;
    /**
     * 家属是否同意尸体解剖标志
     */
    private String agreCorpDset;
    /**
     * 住院医师姓名
     */
    private String ipdrName;
    /**
     * 主诊医师代码
     */
    private String chfpdrCode;
    /**
     * 主诊医师姓名
     */
    private String chfpdrName;
    /**
     * 主任医师姓名
     */
    private String chfdrName;
    /**
     * 签字日期时间
     */
    private String signTime;
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
