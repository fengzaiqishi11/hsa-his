package cn.hsa.module.phar.pharinbackdrug.dto;

import cn.hsa.module.phar.pharinbackdrug.entity.PharInWaitReceiveDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dto
 *@Class_name: PharInWaitReceiveDTO
 *@Describe: 住院待领
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 15:32
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharInWaitReceiveDTO extends PharInWaitReceiveDO implements Serializable {

    private static final long serialVersionUID = -4279574505697083958L;

    //原来的发药状态
    private String oldStatusCode;
    //申请科室名称
    private String deptName;
    //就诊人姓名
    private String visitName;
    //id集合
    private List<String> ids;
    //项目id集合
    private List<String> itemIds;
    //费用id集合
    private List<String> costIds;
    //申请开始时间
    private String startDate;
    //费用时间
    private String costTime;
    //申请结束时间
    private String endDate;
    // 发药科室
    private String pahrName;
    // 用法
    private String usageCode;
    // 用量
    private BigDecimal dosage;
    // 开嘱医生
    private String tellName;
    // 执行人
    private String execName;
    // 频率
    private String rateName;

    private String phCode; //毒麻

    private String isLvp; //是否大输液

    private String bigTypeCode; //药品大类

    private String prepCode; //剂型代码

    private String bedName; //床位名称

    private String name;

    private BigDecimal allNum; //汇总总数

    private BigDecimal allTotalPrice; //汇总总金额

    private BigDecimal showNum;//用于展示的数量（正数）

    private BigDecimal showSplitNum;//用于展示的数量（正数）


    private String backCode;//1:已退费未退药,2:已退费已退药

    private String inNo;  //住院号

    private String drugCode;  //医院编码

    private String orderNo;//单据号

    private String adviceDosage;  // 医嘱剂量

    private Boolean checkFlag;

    private String productName;

    private String color;// 设置颜色 1. 是红色
    /**
     * 是否为查询提前领药
     */
    private String isAdvance;
    /**
     * 提前领药记录id
     */
    private String advanceId;
    /**
     * 医嘱类型 ：长期|临时
     */
    private String adviceType;
    /**
     * 紧急领药医嘱类型查询条件
     * 入参例如："0,1"
     */
    private String isLong;
}
