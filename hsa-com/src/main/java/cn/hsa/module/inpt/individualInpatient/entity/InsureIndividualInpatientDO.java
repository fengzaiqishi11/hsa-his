package cn.hsa.module.inpt.individualInpatient.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 表含义：
 insure：医保
 Individual：个人
 lastbizinfo：住(InsureIndividualInpatient)实体类
 *
 * @author makejava
 * @since 2020-10-13 10:30:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualInpatientDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -53524934669889547L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 个人基本信息id
     */
    private String mibId;


    private String aaa027;  //	统筹区编码
    private String aab001;	//	单位ID
    private String aab019; //	单位类型
    private String aac001;//	人员电脑号
    private String aac002;  //	公民身份号码
    private String aac003;  //	姓名
    private String aac004;  //	性别
    private Date aac006;	//	出生日期
    private String  aac148;  //	特殊补助类别（低保、五保、重残、精准扶贫、标准）文档删除
    private String aae004;  //	联系人姓名
    private String aae005;  //	联系电话
    private String aae006;  //	地址
    private Date aae030;	//	开始日期
    private Date aae031;	//	终止日期
    private Date aae036;	//		经办时间
    private String aae140;  //	险种类型（码表AAE140）
    private String aaz217;  //	就医登记号
    private String aaz267;  //	医疗待遇申请事件ID
    private String aka030;  //	结算类别（1、中心报账；2、医院上传）
    private String aka101;  ///	医院等级
    private String aka130;  //	医疗业务类型
    private String akb020;  //	医疗机构编码
    private String akb021;  //	医疗服务机构名称
    private String akc190;  //	住院号（门诊号）
    private String akc193;  //	入院诊断疾病编码
    private String ake020;  //	病区床位
    private String ake022;  //	入院诊断医生编码
    private String ake024; //	主要病情描述
    private String akf001;  //	科室
    private String baa027;  //	参保地中心编码
    private String bac001;  //	公务员级别（码表 BAC001）
    private String bka001;	//	结算批次号
    private String bka006;  //	待遇类别（码表BKA006）
    private String bka008;  //	单位名称
    private String bka015;  //	就诊登记名称
    private String bka020;  //	科室名称
    private String bka021;  //	病区
    private String bka022;  //	病区名称
    private String bka030;	//	住院天数（床日）
    private String bka034;  //	结算人名称
    private String bka035; //	人员类别（码表 BKA035）
    private String bka039;  //	完成标志（码表 BKA039）
    private String bka041;  //	锁定标志（1-锁定 其他未锁定）
    private String bka042;  //	生育序列号(生育待遇资格认定IDAAZ238)
    private String bka044;  //	传输标志(0:未传输 1:已成功传输 2:未成功传输
    private String bka061; //	病种严重程度(A：病种单纯 B：严重 C：严重并发 D：危重)
    private String bka072;  //	业务终结情况
    private String bka245;	//		预付款金额
    private String bke301;  //	医院级别（码表BKE301）
    private String bkz101;  //	入院疾病诊断名称
    private String bacu18;	//	个人账户余额

    private String crteId;

    private String crteName;

    private Date crteTime;



    /*
     *//**
     * 医疗机构编号
     *//*
    private String akb020;
    *//**
     * 中心编码
     *//*
    private String aaa027;
    *//**
     * 住院号
     *//*
    private String akc190;
    *//**
     * 登记人工号
     *//*
    private String aae011;
    *//**
     * 登记人
     *//*
    private String bka015;
    *//**
     * 登记标志
     *//*
    private String bka016;
    *//**
     * 业务类型
     *//*
    private String aka130;
    *//**
     * 业务登记日期
     *//*
    private Date aae036;
    *//**
     * 业务开始时间
     *//*
    private Date aae030;
    *//**
     * 业务开始情况
     *//*
    private Object bka018;
    *//**
     * 疾病名称
     *//*
    private String bkz101;
    *//**
     * 入院疾病诊断（akc193码）
     *//*
    private String akc193;
    *//**
     * 入院科室
     *//*
    private String akf001;
    *//**
     * 入院科室名称
     *//*
    private String bka020;
    *//**
     * 入院病区
     *//*
    private String bka021;
    *//**
     * 入院病区名称
     *//*
    private String bka021Name;
    *//**
     * 入院床位号
     *//*
    private String ake0201;
   */
}