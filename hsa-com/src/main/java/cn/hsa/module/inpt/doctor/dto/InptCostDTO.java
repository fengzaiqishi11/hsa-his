package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.doctor.entity.InptCostDO;
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
 *@Class_name: InptCostDTO
 *@Describe: 住院费用
 *@Eamil: liuqi1@powersi.com.cn
 *@Author: liuqi1
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptCostDTO extends InptCostDO implements Serializable {

    private static final long serialVersionUID = -6835255983464404633L;

    //就诊名称
    private String visitName;
    //住院费用id集合
    private List<String> ids;
    //住院就诊ids
    private List<String> visitIds;
    //就诊人姓名
    private String name;
    //就诊人性别
    private String genderCode;
    //就诊人年龄
    private String age;
    //就诊人住院号
    private String inNo;
    // 医嘱ID集合字符串
    private String iatIds;
    // 医嘱开立时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date iat_time;
    //执行科室名称
    private String execDeptName;
    //回退金额
    private BigDecimal backAmount;
    //财务分类名称(计费类别名称)
    private String bfcName;
    //入院时间
    private Date inTime;
    //出院时间
    private Date outTime;
    //入院科室名称
    private String inDeptName;
    //入院科室ID
    private String indeptId;
    //患者状态
    private String inStatusCode;
    //床位名称
    private String bedName;
    // 多条件查询
    private String keyword;
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;
    //年龄单位代码（NLDW）
    private String ageUnitCode;
    //财务分类code(计费类别code)
    private String bfcCode;
    //拆零单价
    private BigDecimal splitPrice;
    //拆分比
    private BigDecimal splitRatio;
    //拆零单位代码（DW）
    private String splitUnitCode;
    //拆零数量
    private BigDecimal splitNum;
    //单位
    private String unitCode;
    //计费日期
    private String costDate;
    //打印标志
    private String printFlag;
    // 计价金额
    private BigDecimal amountMoney;
    //费用开始时间
    private String startCostDate;
    //费用结束时间
    private String endCostDate;
    private String deductible; // 自费比例
    private String isTrans; //是否传输
    private String insureCode;
    private String hospItemCode; // 医院项目编码
    private String hospItemName; // 医院项目名称
    private String insureItemCode; // 医保中心项目编码
    private String insureItemName; // 医保中心项目名称
    private String isTransmit; //传输标志
    private String guestRatio;  // 自费比例
    private String medicalRegNo;
    private String medicineOrgCode;
    private String remark;
    private String productName; //材料/药品的生产厂家名
    private String code; //录入人员工号
    private Date lastExecTime;//最近执行时间
    private String matchItemCode; // 医保匹配项目类型
    private String hospDrugOrMaterialItemCode; // 医院药品项目编码
    private String isMatch; // 是否匹配
    private String isSs; // 是否手术
    private String sourceId;//来源ID

    private String preferentialTypeId;
    private String isBackFee; // 是否退费
    private String queryStartDate; // 计费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stopTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String costStartTime; // 费用开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String costStopTime; // 费用结束时间

    private String deptName; // 科室名称

    private String medType; //医疗类型

    private String feeDays; //费用天数

    /**
     * 病人类型 1:表示门诊，2：表示住院
     **/
    private String inptOrOutpt;
    /**
     * 门诊处方ID
     **/
    private String adviceId;

    // 查询婴儿关键字
    private String queryBaby;
    /**
     * 婴儿姓名
     */
    private String babyName;

    /**
     * 药品项目编码
     */
    private String drugItemCode;
    /**
     * 材料项目编码
     */
    private String mateItemCode;
    /**
     * 项目的项目编码
     */
    private String itemItemCode;
    /**
     * 项目分类
     */
    private String itmeTypeCode;
    // 入院日期
    private String inDate;
    // 总费用
    private String totalCost;
    /**
     * 是否临嘱（SF）（0：长期，1：临时）
     */
    private String isLong;
    /**
     * 频率名
     */
    private String rateName;
    private String insureRegCode;
    private BigDecimal inptSumPrice;
    private BigDecimal insureSumPrice;

    /**
     * 国家编码
     */
    private String nationCode;
    /**
     * 国家名称
     */
    private String nationName;

    /*
    个人自付金额
     */
    private BigDecimal preselfpayAmt;

    // 医保报销类型
    private String chrgItemLv;

    // 医保编码
    private String ybCode;

    // 经治医生名称
    private String jzDoctorName;
    private String typeCode;
    //自付金额
    private BigDecimal guestRatioPriceend;

}