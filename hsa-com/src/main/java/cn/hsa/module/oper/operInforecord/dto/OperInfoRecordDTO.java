package cn.hsa.module.oper.operInforecord.dto;

import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 *@Package_name: cn.hsa.module.oper.operinforecord.dto
 *@Class_name: OperInfoRecordDTO
 *@Describe: 手术信息
 *@Author: 马荣
 *@Date: 2020/9/27 10:02
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OperInfoRecordDTO extends OperInfoRecordDO implements Serializable {

    private static final long serialVersionUID = 7814448764004506704L;
    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 手术申请搜索开始时间
     */
    private String startTime;
    /**
     * 手术申请搜索结束时间
     */
    private String endTime;

    /*就诊科室*/
    private String deptName;

    /*住院科室*/
    private String operDeptName;

    /*icd9名字*/
    private String Icd9Name;

    /*病区名字*/
    private String inWardName;
    /**
     * 病情标识
     */
    private String illnessCode;

    private String outptVisitNo; //就诊号

    private String deptCode;

    private String operInquiry;  //查询标识符

    private String inptOrOutpt; //住院或者门诊

    private String isOver;  //是否完成

    //累计预交金
    private String totalAdvance;
    //累计费用
    private String totalCost;
    //累计余额
    private String totalBalance;
    // 住院（主治医生ID）/ 门诊（就诊医生ID）
    private String zzDoctorId;
    // 基础数据医嘱id
    private String baseAdviceId;
    // 1 申请登记 2取消登记 3手术安排 4 取消安排 5完成登记 的标识
    private String updateStatusCode;

    /** 取消手术申请标识 1:表示取消手术申请 **/
    private String operApplyStatusChange;
    /**
     * 医保个人电脑号
     */
    private String aac001;
    /**
     * 医保登记号
     */
    private String medicalRegNo;
    /**
     * 门诊病历号
     */
    private String outProfile;
    /**
     * 病人类型
     */
    private String patientCode;
    /**
     * 手术疾病编码
     */
    private String operDiseaseCode;
    /**
     * 患者姓名
     */
    private String name;
    /**
     * 医嘱ids
     */
    private List<String> adviceIds;
    // 术者医师代码
    private String operDrCode;
    // 麻醉医师代码
    private String anstDrCode;
    // 手术操作类别
    private String oprnOprtType;
}
