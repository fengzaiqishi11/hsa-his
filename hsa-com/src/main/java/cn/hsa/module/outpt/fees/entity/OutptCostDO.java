package cn.hsa.module.outpt.fees.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
* @Package_name: cn.hsa.module.outpt.fees.entity
* @Class_name: OutptCostDO
* @Describe: 门诊费用Model；费用来源方式代码：0：处方；1：直接；2：药房退药退费；3：动静态计费，4:皮试 5：皮试换药药品，9：其他费用
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptCostDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -8142117462251296112L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊ID（outpt_visit）
        private String visitId;
        //处方ID（outpt_prescribe）
        private String opId;
        //处方明细id（outpt_prescribe_detail）
        private String opdId;
        //处方执行签名ID（outpt_prescribe_exec）
        private String opeId;
        //费用来源方式代码（FYLYFS）
        private String sourceCode;
        //费用来源ID
        private String sourceId;
        //原费用ID
        private String oldCostId;
        //来源科室ID（直接划价来源收费室，划价收费来源开方科室）
        private String sourceDeptId;
        //财务分类ID
        private String bfcId;
        //项目类型代码（XMLB）
        private String itemCode;
        //项目ID（药品、项目、材料、医嘱目录）
        private String itemId;
        //项目名称（药品、项目、材料、医嘱目录）
        private String itemName;
        //单价
        private BigDecimal price;
        //数量
        private BigDecimal num;
        //规格
        private String spec;
        //剂型代码（JXFL）
        private String prepCode;
        //剂量
        private BigDecimal dosage;
        //剂量单位代码（JLDW）
        private String dosageUnitCode;
        //用法代码（YF）
        private String usageCode;
        //频率ID
        private String rateId;
        //用药天数
        private Integer useDays;
        //数量单位（DW）
        private String numUnitCode;
        //总数量（数量*频率*用药天数）
        private BigDecimal totalNum;
        //中草药脚注代码（ZYJZ）（中药调剂方法）
        private String herbNoteCode;
        //用药性质代码（YYXZ）
        private String useCode;
        //中草药付（剂）数
        private BigDecimal herbNum;
        //项目总金额
        private BigDecimal totalPrice;
        //优惠总金额
        private BigDecimal preferentialPrice;
        //优惠后总金额
        private BigDecimal realityPrice;
        //状态标志代码（ZTBZ）
        private String statusCode;
        //发药药房ID
        private String pharId;
        //是否已发药（SF）
        private String isDist;
        //退药数量
        private BigDecimal backNum;
        //结算ID（outpt_settle）
        private String settleId;
        //结算状态代码： 0未结算，1预结算，2已结算
        private String settleCode;
        //医保自费比例
        private BigDecimal selfRatio;
        //是否医技确费：0、未确认，1、已确认
        private String isTechnologyOk;
        //医技确费人ID
        private String technologyOkId;
        //医技确费人姓名
        private String technologyOkName;
        //医技确费时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date technologyOkTime;
        //平均购进价（药品、材料中的平均购进价）
        private BigDecimal avgBuyPrice;
        //平均零售价（药品、材料中的平均零售价）
        private BigDecimal avgSellPrice;
        //开方医生ID
        private String doctorId;
        //开方医生名称
        private String doctorName;
        //开方科室ID
        private String deptId;
        //执行科室ID
        private String execDeptId;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        // 发药批次汇总id
        private String distributeAllDetailId;
        // 原结算id
        private String oneSettleId;
        /**
         * 限制使用标志
         */
        private String lmtUserFlag;
        /**
         * 限制使用说明
         */
        private String limUserExplain;
        /**
         * 是否报销
         */
        private String isReimburse;

        /**
         * 发票信息ID
         */
        private String settleInvoiceId;

        /**
         * 第一次的费用id
         */
        private String oneDistCostId;
}
