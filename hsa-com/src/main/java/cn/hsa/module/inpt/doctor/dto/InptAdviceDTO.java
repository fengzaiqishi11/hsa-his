package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.consultation.dto.InptConsultationApplyDTO;
import cn.hsa.module.inpt.doctor.entity.InptAdviceDO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.inpt.dto
 *@Class_name: InptAdviceDTO
 *@Describe: 住院医嘱明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdviceDTO extends InptAdviceDO implements Serializable {

    private static final long serialVersionUID = -2433100107270674149L;

    private String visitName; // 就诊名称
    private String bedName; // 床位名称
    private String bedSeqNo; // 床位序号
    private String name ; // 患者姓名
    private String inNo ; // 住院号
    private String patientInfo; //患者信息
    private String inWardName; //入院病区名称
    private String newExecStatus; // 新开立医嘱（新开/新停）
    private String keyword; // 多条件查询
    private String statusCode; // 病人当前状态码（BRXT）
    private String costIsCheck; // 费用是否核对
    private String iatIds; // 医嘱id集合
    private List<String> iatIdList; // list集合
    private String sourceCode; // 来源代码
    private String rateName; // 频率
    private String genderCode; // 性别代码
    private String age; // 年龄
    private List<String> ids;
    private String signCode; // 执行签名状态
    private String exeSignCode;//单个医嘱执行状态标志
    private String pharId; // 是否当前科室
    private String startOnlyDate; //开始日期
    private String startOnlyTime; //开始时间
    private String stopOnlyDate; //停嘱日期
    private String stopOnlyTime; //停嘱时间
    private String execTimeNoM; //执行时间 不要秒


    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private String startTime; // 开始时间

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private String endTime; // 结束时间
    //类型
    private String type;
    private String wardName ; // 病区名称

    private String execName; // 医嘱执行人姓名
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date execTime ; // 医嘱执行日期

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planExecTime; // 医嘱计划执行日期
    private String yzlx; //医嘱类型 长嘱，临时医嘱
    private Integer execOrder ; //医嘱执行顺序
    private String printType ; //打印类型
    private String nursingCode; // 护理级别
    private String illnessCode; // 病情标识
    private String dietType; // 膳食类型
    private String execDept; // 执行科室
    private String execDeptKsxz; // 执行科室性质
    private String dailyTimes; //频次
    private String technologyCode; //医技类型代码
    private String containerCode; //容器类型代码
    private String specimenCode ; //标本类型代码
    private String bigTypeCode ; //药品大类
    private String splitUnitCode ; //拆零单位
    private String inDeptName; // 入院科室
    private String inProfile; //病案号
    //医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔
    private String wjsykc;
    private String execNum; // 执行次数
    private String isStopSame; // 是否停同类医嘱（SF）
    private String isStopSameNot; // 是否停非同类医嘱（SF）
    private String isStopMyself; // 是否停自身（SF）
    private String ageUnitCode; // 年龄单位
    private String adviceType;// 医嘱类型
    private String medicineOrgCode; // 医疗机构编码
    private String medicalRegNo ; // 医保就诊号
    private String deptCode;  // 开嘱科室编码
    private String deptName; // 开嘱科室名称
    private String execDeptName; // 执行科室名称
    private String creterCode; // 开嘱人工号
    private String checkerCode; // 医嘱核对人工号
    private String adviceExecDeptName; // 医嘱执行科室名称
    private String adviceExecDeptCode; // 医嘱执行科室编码
    private String adviceDetailId; // 医嘱明细Id
    private String adviceDetailItemName; // 医嘱明细名称


    private String  isWz;// 是否修改文字医嘱
    private String  isCx;// 是否采血
    private String isAdviceEntry; // 医嘱是否录入上传
    private String iatId; // 费用表里面的医嘱id
    private String isPrint; // 是否打印
    private String visitId; //就诊id
    //医嘱计划执行时间 只要年月日
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String execPlaneTime;
    //计划执行时间
    private String execPlaneTime1;
    private List<String> inptAdviceExecIds ;
    private String skinDurgName;
    private String bigUnitCode;
    //医嘱目录
    private String yzmlType;
    private String inptAdviceExecId;
    //皮试结果
    private String skinResultCode;

    private String costTime;
    private String prepCodeName; // 剂型名称

    // 20210526 liuliyun
    private String queryBaby;

    private List<InptAdviceDTO> inptAdviceDTOList ;

    /** 手术信息 **/
    private OperInfoRecordDTO operInfoRecordDTO;
    /**
     * 医嘱ids字符串
     */
    private String idsStr;
    // 处方总金额
    private BigDecimal printTotalPrice;
    /**
     * 会诊申请对象
     */
    private InptConsultationApplyDTO consulApplyInfo;
    /**
     * 医嘱组号list
     */
    private List<Integer> groupNos;
    // 病人类型
    private String patientCode;
    // 入院诊断
    private String inDiseaseName;
    // 居住地
    private String address;
    // 联系电话
    private String phone;
    private String certNo;
    /**
     * 国家卫健委编码
     */
    private String nationCode;
    /**
     * 国家卫健委编码名称
     */
    private String nationName;

    private String prescribeTypeCode;
    private String cfTypeCode;
    /**
     * 精麻分单时间
     */
    private String fdDrteTime;


}
