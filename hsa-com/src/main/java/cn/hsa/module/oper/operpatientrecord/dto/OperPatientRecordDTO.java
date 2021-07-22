package cn.hsa.module.oper.operpatientrecord.dto;

import cn.hsa.module.oper.operpatientrecord.entity.OperPatientRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
   * @Package_name: cn.hsa.module.oper.operpatientrecord.dto
   * @Class_name: OperPatientRecordDTO
   * @Describe: 手术病人记录传输对象
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/23 11:16
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OperPatientRecordDTO extends OperPatientRecordDO implements Serializable {

    private static final long serialVersionUID = -5963420485828854606L;

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

    /**就诊号**/
    private String outptVisitNo;

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

}
