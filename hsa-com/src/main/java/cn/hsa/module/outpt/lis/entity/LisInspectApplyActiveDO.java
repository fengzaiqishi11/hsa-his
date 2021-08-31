package cn.hsa.module.outpt.lis.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.lis.entity
 * @Class_name: InspectApplyActiveDO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-07 09:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LisInspectApplyActiveDO implements Serializable {

    /**
     * 1成功0失败
     */
    private String status;

    private String msg;

    private String data;
    /**
     * 医院编码
     */
    private String areaCode;
    /**
     * lis_pat_info表主键（报告单唯一主键）
     */
    private String patInfoid;

    private Date testDate;

    private String scode;

    private String ecode;

    private String eqpName;

    private String testNum;

    private String emergencyTag;

    private String chargetag;

    private String criticalStatus;

    private String reviewTag;

    private String reviewTimes;

    private String positiveTag;

    private String pid;

    private String name;

    private String pyCode;

    private String age;

    private String ageUnit;

    private String fullAge;

    private String sex;

    private String ssid;

    private String mobile;

    private String birthday;

    private String patientCompany;

    private String patType;

    private String patTypeName;

    private String dptCode;

    private String dptName;

    private String dctCode;

    private String dctName;

    private String bedNum;

    private String lczd;

    private String drugRemark;

    private String ordersRemark;

    private Date orderTime;

    private String barcode;

    private Date gatherTime;

    private Date receiveTime;

    private Date inputTime;

    private Date testTime;

    private Date checkTime;

    private String testTserCode;

    private String testUserName;

    private Object testUserSign;

    private String checkUserCode;

    private String check_UserName;

    private String sampleState;

    private String caseid;

    private String patientUniqueid;

    private String sampleCode;

    private String sampleName;

    private String samplePart;

    private String securityLevel;

    private Date hospitalTimes;

    private String infectiousTag;

    private String remark;

    private String comment;

    private String resultComment;

    private String warnRemark;

    private String ageUnitName;

    private String testitemCode;

    private String testitemname;

    private String testitemename;

    private Date testtime;

    private Object resultType;

    private String result;

    private String resultUnit;

    private String rangeState;

    private String referenceRange;

    private String rangeStateColor;

    private String feeitemName;

    private String feeitemCode;

    private String hisId;

    private String crteId;

    private String crteName;

    private Date crteTime;

}


