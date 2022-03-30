package cn.hsa.module.insure.emr.entity;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
* @ClassName InsureEmrCoursrinfoDO
* @Deacription 医保电子病历上传-病程记录
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrCoursrinfoDO extends PageDO implements Serializable {

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
    private String deptCode;
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
     * 记录日期时间
     */
    private String rcdTime;
    /**
     * 主诉
     */
    private String chfcomp;
    /**
     * 病例特点
     */
    private String casFtur;
    /**
     * 中医“四诊”观察结果
     */
    private String tcm4dRslt;
    /**
     * 诊断依据
     */
    private String diseEvid;
    /**
     * 初步诊断-西医诊断编码
     */
    private String prelWmDiagCode;
    /**
     * 初步诊断-西医诊断名称
     */
    private String prelWmDiseName;
    /**
     * 初步诊断-中医病名代码
     */
    private String prelTcmDiagCode;
    /**
     * 初步诊断-中医病名
     */
    private String prelTcmDiseName;
    /**
     * 初步诊断-中医证候代码
     */
    private String prelTcmsympCode;
    /**
     * 初步诊断-中医证候
     */
    private String prelTcmsymp;
    /**
     * 鉴别诊断-西医诊断编码
     */
    private String finlWmDiagCode;
    /**
     * 鉴别诊断-西医诊断名称
     */
    private String finlWmDiagName;
    /**
     * 鉴别诊断-中医病名代码
     */
    private String finlTcmDiseCode;
    /**
     * 鉴别诊断-中医病名
     */
    private String finlTcmDiseName;
    /**
     * 鉴别诊断-中医证候代码
     */
    private String finlTcmsympCode;
    /**
     * 鉴别诊断-中医证候
     */
    private String finlTcmsymp;
    /**
     * 诊疗计划
     */
    private String disePlan;
    /**
     * 治则治法
     */
    private String prnpTrt;
    /**
     * 住院医师编号
     */
    private String ipdrCode;
    /**
     * 住院医师姓名
     */
    private String ipdrName;
    /**
     * 上级医师姓名
     */
    private String prntDocName;
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
