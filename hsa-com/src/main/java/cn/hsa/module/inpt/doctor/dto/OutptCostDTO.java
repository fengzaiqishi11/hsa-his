package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.outpt.fees.entity.OutptCostDO;
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
   * 门诊费用
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/26 18:42
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptCostDTO extends OutptCostDO implements Serializable {

    private static final long serialVersionUID = -2386647394982031220L;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date iat_time;
    //执行科室名称
    private String execDeptName;
    //回退金额
    private BigDecimal backAmount;
    //财务分类名称(计费类别名称)
    private String bfcName;
    //入院时间
    private Date inTime;
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
    /** 自费比例 **/
    private String deductible;
    /** 是否传输 **/
    private String isTrans;
    private String insureCode;
    /** 医院项目编码 **/
    private String hospItemCode;
    /** 医院项目名称 **/
    private String hospItemName;
    /** 医保中心项目编码 **/
    private String insureItemCode;
    /** 医保中心项目名称 **/
    private String insureItemName;
    /** 传输标志 **/
    private String isTransmit;
    /** 自费比例 **/
    private String guestRatio;
    private String medicalRegNo;
    private String medicineOrgCode;
    private String remark;
    /** 材料/药品的生产厂家名 **/
    private String productName;
    /** 录入人员工号 **/
    private String code;
    /** 最近执行时间 **/
    private Date lastExecTime;
    /** 医保匹配项目类型 **/
    private String matchItemCode;
    /** 医院药品项目编码 **/
    private String hospDrugOrMaterialItemCode ;
    /** 是否匹配 **/
    private String isMatch;
    /** 是否手术 **/
    private String isSs;
    /** 来源ID **/
    private String sourceId;

    private String preferentialTypeId;
    /** 是否退费 **/
    private String isBackFee ;
    /** 计费时间 **/
    private String queryStartDate;

    /** 费用开始时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private String costStartTime;
    /** 费用结束时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private String costStopTime;
    /** 科室名称 **/
    private String deptName;
    /** 医疗类型 **/
    private String medType;
    /** 费用天数 **/
    private String feeDays ;

    /** 门诊病人的医嘱处方ID **/
    private String adviceId ;
    /** 记账时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date costTime;
}
