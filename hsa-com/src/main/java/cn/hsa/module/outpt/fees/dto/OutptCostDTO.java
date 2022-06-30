package cn.hsa.module.outpt.fees.dto;

import cn.hsa.module.outpt.fees.entity.OutptCostDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.outpt.fees.dto
* @Class_name: OutptCostDTO
* @Describe: 门诊费用ModelDTO;费用来源方式代码：0：处方；1：直接划价收费；2：药房退药退费；3：动静态计费，4:皮试 5：皮试换药药品，9：其他费用
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptCostDTO extends OutptCostDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -8142117462251296112L;
        //库存数
        private BigDecimal stockNum;
        //商品名称拼音码
        private String goodPym;
        //商品名称五笔码
        private String goodWbm;
        //单位
        private String unitCode;
        //拆分比
        private BigDecimal splitRatio;
        //拆零单位代码（DW）
        private String splitUnitCode;
        //拆零单价
        private BigDecimal splitPrice;
        //说明
        private String drugRemark;
        //是否可改价
        private String isCg;
        // 财务类别编码
        private String bfcCode;
        // 财务类别名称
        private String bfcName;
        // 发药药房名称
        private String pharName;
        // 药品所在药房名称
        private String ykName;
        // 执行科室名称
        private String execDeptName;
        // 处方内容
        private String content;
        //优惠单价
        private BigDecimal reducedPrice;
        //优惠后单价
        private BigDecimal reducedAfterPrice;
        //拆零优惠金额
        private BigDecimal splitReducedPrice;
        //拆零优惠后金额
        private BigDecimal splitReducedAfterPrice;
        //药品大类代码（YPDL）
        private String bigTypeCode;
        //原单价
        private BigDecimal primaryPrice;
        //频率编码
        private String rateCode;
        //项目编码
        private String code;
        // 项目类别名称
        private String xmName;
        //结算时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date settleTime;
        // 患者姓名
        private String patientName;
        // 当前结算单总费用
        private String totalFy;
        // 当前结算单总费用大写
        private String dxTotalFy;
        // 费用类别集合
        private List<Xmlbfy> fylbList;
        // 费用类别类
        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public class Xmlbfy implements Serializable{
                // 项目类别 eg: 药品、材料、项目
                private String xmlb;
                // 项目类别费用合计  eg： 药品80.20   材料 200.00
                private String xmlbfyhj;
        }
        private Date outptPrescribeCrteTime;
        private String orderNo ;  // 处方号
        private String deptName;  // 开发科室名称
        private String rateName; // 频率名称
        // 项目类别名称
        private String xmlbName;
		// 最后一次费用明细优惠后总金额
        private BigDecimal lastRealityPrice;
        // 患者优惠类别
        private String preferentialTypeId;
        // 执行科室id
        private String execDeptId;
        private String sex;  //性别
        private String yblx;  // 医保类型
        private int age;
        private String ageUnitCode;
        private BigDecimal outptTotalFee;

        // 将收据按计费类别统计后返回给前端
        private Map<String, Object> zuoBiaoCostData;

        private BigDecimal miPrice;  // 统筹支付金额
        private BigDecimal selfPrice;  // 个人支付金额

        private BigDecimal settleTakePrice; // 结算补收
        private BigDecimal settleBackPrice; // 结算退款
        private BigDecimal apTotalPrice; // 预交金合计
        private BigDecimal inptSettleRealityPrice; // 住院结算支付的优惠后总金额



    //财务分类编码  yzb 0305
    private String cwflBm;
    // 财务发票内容 yzb 0305
    private String mzFpnr;

    // 发票归类名称
    private String fpglName;
    // 发票归类key
    private String fpglKey;
    // 发票归类数据集
    private Map<String, Object> fpglDataMap;

    // 费用来源方式名称
    private String  sourceName;
    /** 结算状态 **/
    private String  settleStatus;
    // 门诊使用的默认单位
    private String outUnitCode;

    //入院时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inptInTime;

    //出院时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inptOutTime;

    // 住院号
    private String inNo;
    // 住院天数
    private String inDays;

    // 占用库存
    private BigDecimal stockOccupy;
    // 拆零库存
    private BigDecimal splitNum;

    // 挂号医生姓名
    private String registerDoctorName;

    // 就诊类别
    private String visitCode;

    // 医保个人账户结算前余额
    private BigDecimal beforeSettle;

    // 医保个人账户结算后余额
    private BigDecimal lastSettle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    //就诊结束日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    // 是否医保类型
    private String SF;
    // 处方类别
    private String prescriptionCategory;
    // 收据类型 0: 挂号；1：门诊；2：住院
    private String inventoryType;
    //医保项目编码
    private String insureItemCode;
    //医疗机构项目编码
    private String hospItemCode;
}
