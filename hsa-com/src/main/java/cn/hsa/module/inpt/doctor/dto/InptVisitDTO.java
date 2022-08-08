package cn.hsa.module.inpt.doctor.dto;


import cn.hsa.module.inpt.doctor.entity.InptVisitDO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
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
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.doctor.dto
 *@Class_name: InptVisitDTO
 *@Describe: 住院就诊
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/4 10:02
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptVisitDTO extends InptVisitDO implements Serializable {
    private static final long serialVersionUID = -6619117486595003229L;

    //id集合
    private List<String> ids;

    private String type;   //查询住院病人状态（1.入院登记时  2.病人综合查询）
    /**
     * 姓名/住院号
     */
    private String keyword;
    /**
     * 病区名称
     */
    private String wardId;
    /**
     * 病区名称
     */
    private String wardName;

    //新开医嘱病人
    private String isNewAdvice;  // 1 是 0 否
    //待执行医嘱病人
    private String isImplementAdvice; // 1 是 0 否
    //是否危重病人
    private String isIllness; // 1 是 0 否

    private String deptCode;
    /**
     * 入院开始时间
     */
    private String startTime;
    /**
     * 入院结束时间
     */
    private String endTime;

    //操作类型(0:继续住院  1:出院召回)
    private String handleType;

    // 是否按 科室，床位，患者名，入院时间病人类型排序
    private String isOrderBy;

    // 是否医保登记
    private String isInsureRegister;


    //存储门诊就诊信息
    //就诊ID
    private String outptId;
    //就诊医生
    private String doctorName;
    //就诊号
    private String visitNo;
    //就诊时间
    private Date visitTime;

    //存储门诊就诊信息对应档案
    //户口地
    private String nativeAddress;
    //户口地邮编
    private String nativePostCode;
    //居住地地址
    private String nowAddress;
    //居住地邮编
    private String nowPostCode;
    //工作单位
    private String work;
    //单位地址
    private String workAddress;
    //单位邮编
    private String workPostCode;
    //单位电话
    private String workPhone;
    //邮箱
    private String email;
    //籍贯
    private String nativePlace;
    //入院次数
    private String totalIn;

    /*查询用的两个时间范围*/
    private String Stime;
    private String Etime;
    private String isHalfSettle; // 是否开启中途结算



    //存储预交金对象信息的
    //预交金方式
    private String payCode;
    //预交金金额
    private BigDecimal price;
    //支票号码
    private String chequeNo;
    private String orderNo;

    //患者信息
    private String patientInfo;

    //三测单用
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inTimestring;
    private String visitId; // 就诊id
    //住院开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date feeEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date feeStartDate;
    //住院结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    //床位表的床位字段
    private String bbBedName;
    // 医嘱频率的频次
    private String dailyTimes;
    //床位状态
    private String bbStatusCode;
    //床位表id
    private String bbId;

    // 是否驳回
    private String isReject;

    // 是否审核
    private String isAudit;
    // 打印类型
    private String printType;
    // 金额预警线
    private String warningMoney;

    /**
     * 医保新增参数 start  add by LiaoJiGuang on 2020-11-02
     */

    // 电脑号
    private String computerNumber;
    //人员类别
    private String bka035Name;

    // 个人信息ID
    private String mibId;

    // 医保注册编码
    private String insureRegCode;

    // 医保业务类型名称
    private String insureBizName;

    // 医疗待遇类型
    private String insureTreatName;

    // 业务申请号
    private String injuryBorthSn;

    // 就医登记号
    private String medicalRegNo;

    // 传输标志
    private String isTransmit;

    // 欠费金额
    private String qfCose;

    private InsureIndividualBasicDTO insureIndividualBasicDTO;

    // 人员身份类别
    private List<Map<String,Object>> idetinList;

    //动态sql
    private String sqlStr;

    /**
     * 医保新增参数 end add by LiaoJiGuang on 2020-11-02
     */

    //病人财务分类集合
    private Map bfcMap;


    private String code ; //员工工号

    private String isMatch; // 是否匹配

    private String summMethod;  //病人费用台账方式

    private String medicineOrgCode; // 医疗机构编码
    private String adviceDoctorName ; // 开嘱医生姓名
    private String adviceId; // 医嘱号
    private String settleTakePrice;  //结算补收
    private String settleBackPrice; //结算退款
    private String isMain; // 是否主诊断
    private String diseaseId; // 诊断Id
    private String diagnoseCrteTime; //诊断时间

    private BigDecimal drugCost; //药品费用

    private String drugCostRate;   //药费比

    private String settleTime;  //结算时间

    private String timeFlag;  //查询时间类型
    private  String diseaseName; //疾病诊断名称
    private  String diseaseIcd10; //疾病诊断名称
    private String typeCode ;// 疾病分类
    private String diseaseCode ; // 疾病编码
    private String adviceType; // 医嘱类型
    private String itemId ; // 医嘱目录id
    //医保费用集合
    private List<Map<String,Object>> insureCostList;
    //是否打印
    private String printFlag;
    private String orgCode; // 医保机构编码
    // 大输液用药方式集合
    private List<String> paramList;
    // 查询日期
    private String queryDate;
    private List<String> usageCodeList; // 用法集合
    private String usageCode;  // 用法
    private String isStop; // 是否停嘱
    private List<String> typeCodeList; // 医嘱类别集合
    private String adviceTypeCode; // 医嘱类别
    private String aac001 ; // 个人电脑号
    private String aka130; // 业务类型

    // 长沙医保新增
    private String isUseAccount;
    private String commonRequestStr; // 公共请求入参
    private String userCode;
    private String userName;

    private List<InptDiagnoseDTO> inptDiagnoseDTOS;
    private String isChange; // 标记是否未登记时触发（事前接口用 1：登记  0：处方）
    private String electronicBillParam;//电子凭证个人信息参数
    private String treatmentCode; //治疗方式分型
    private String inDiagnoseId; // 诊断管理诊断id
    private String inDiagnoseName; // 诊断管理诊断名称
    private String [] statusCodes;

    /**
     * 入院开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ryStartTime;
    /**
     * 入院结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String ryEndTime;

    private String registerNo; // 门诊号

    private String lx;

    private String aka130Name; // 医疗类别

    private String cardIden;//卡识别码

    //查询类型(all：表示查询所有    除all之外的表示查询权限内的数据)
    private String queryType;
    private String visitIcdName; // 诊断疾病名称
    private String visitIcdCode; // 诊断疾病编码
    private String bka006 ; // 待遇类型
    private String bka006Name ; // 待遇类型名称
    private String pretFlag; //  早产标志
    private int  fetusCnt ; // 胎儿数
    private int fetts; // 胎次
    private String latechbFlag ; // 晚育标志
    private String birctrlType; // 计划生育手术类别
    private String matnType ; // 生育类别
    private String fpscNo ; // 计划生育服务证号
    private int gesoVal; //孕周数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birctrlMatnDate ; // 计划生育手术或生育日期
    private String isOut; // 是否医保出院登记

    private String babyId;

    private String babyName;

    /**
     * 住院/就诊号
     */
    private String inOrVisitNo;
    /**
     * 住院天数
     */
    private int inNum ;

    /**
     * 是否付费
     */
    private String isSettle;

    private String caseSqlStr ;

    private String sumSqlStr ;

    /**
     * 开医嘱是否查询主管病人（当前医生的病人）0查全部，1查询当前医生的,
     */
    private String zgbrQuery;
    // 是否出院
    private String isOutHosp;

    private String visitIds;
    /**
     * 病人来源
     */
    private String sourceTjCode ;
    /**
     * 个人支付金额
     */
    private BigDecimal selfPrice ;

    /**
     * 统筹支付金额（医保金额）
     */
    private BigDecimal miPrice ;

    /**
     * 补退费
     */
    private BigDecimal buTuiFei ;
    // 是否使用个人账户
    private String isUserInsureAccount;
    private String isUnified ;
    private String feeParam;
    private String insureSettleId;
    //中途结算住院开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date midWayStartDate;
    //中途结算住院结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date midWayEndDate;
    private String isMidWaySettle; // 是否中途结算
    private int  settleCount ;
    private String itemName; // 费用明细项目名称
    /**
     * 护理执行卡打印数据是否共享，判断是否根据单据类型区分打印状态
     */
    private String isShared;
    /**
     * 护理执行卡打印数据是否不关联领药申请表，默认关联
     */
    private String isRelevance;

    private String attributionCode; // 结算类型

    /**
     * 是否新医保，用于判断业务是否走统一支付平台
     */
    private String isUnifiedPay;
    /** 人员编号，insure_individual_visit.aac001 **/
    private String psnNo;
    private String pracCertiNo;  // 医师编码
    private List<String> diagnoseList;

    // 费用条数
    private BigDecimal costNumbers;

    // 医生类型
    private String doctorType;

    // 婴儿费用
    private BigDecimal babyCost;
    // 查询标志
    private String clinicalFlag;
    // 是否在路径内
    private String isClinicalList;
    // 路径状态
    private String pathState;
    // 路径名称
    private String clinicalListName;
    // 路径ID
    private String listId;
    private String settleId;
    // 住院天数
    private Integer inDays;
    /**
     * 申请科室
     */
    private String deptId;
    /**
     * 剂型
     */
    private String prepCode;

    //结算时间
    private String settleStartTime;
    //结算时间
    private String settleEndTime;
    /**
     * 合并结算总费用
     */
    private BigDecimal totalMergeCost;

    /**
     * 合并结算预交金余额
     */
    private BigDecimal totalMergeBalance;
    /**
     * 发药药房名字
     */
    private String pharName;
    /**
     * 发药药房id
     */
    private String pharId;
    /**
     * 是否会诊病人
     */
    private String isConsule;
    /**
     * 会诊申请科室id
     */
    private String applyDeptid;

    /**
     * 是否已写病案 1：已写；0：未写
     */
    private String isLoadMris;
    /**
     * 手术报表统计方式： 0：按明细；1：按科室
     */
    private String statisticType;

    private String regCode; // 机构编码
    /**
     *是否上传医保
     */
    private String isInsureUpload;
    // 科室国家代码
    private String deptNationCode;
    //  主治医生国家医生码
    private String zzPracCertiNo;
    //  经治医生国家医生码
    private String jzPracCertiNo;
    //  经管医生国家医生码
    private String zgPracCertiNo;
    //  门诊医生国家医生码
    private String outptPracCertiNo;
    // 责任护士
    private String respPracCertiNo;
    // 出院科室国家码
    private String outptNationCode;
    // 科室编码
    private String inDeptCode;
    // 入院情况
    private String inSituationName;
    // 医保疾病名称
    private String insureIllnessName;
    // 医保疾病编码
    private String insureIllnessCode;
    // 优惠类型名称
    private String preferentialTypeName;
    private String mdtrtCertType;   //证件类型
    private String mdtrtCertNo;  //入参值
    private String visitDate;  //入参值

    // 人员身份类型
    private String psnIdetType;
    // 人员身份类型码值
    private String psnIdetTypeName;
    // 人员身份类型list
    private String codes;
    private List<String> codeList;
    private String sqlStr1;

    //病人类型集合
    private List<String> brlxList;

    private String errorMessage;
}
