package cn.hsa.module.insure.module.dto;

import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 Individual：就诊 cost：费用                                            -&#&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualCostDTO extends InsureIndividualCostDO implements Serializable {
        // 医保机构编码
        private String insureRegCode;
        private String medicineOrgCode; // 医疗机构编码

        private String aac001;   //个人电脑号

        private String aka130;  //业务类型

        private String aaz217;   //就医登记号

        private String bka063;   //录入人工号

        private String bka064;  //录入人姓名

        private String bka070;  //处方号

        private String bka074;  //处方医生编号

        private String bka075;  //处方医生姓名

        private String orgCode; //医保注册编码

        private String ake003;  //项目药品类型

        private String aka063; //费用统计类别

        private String ake005; //医院药品项目编码

        /*费用传输*/
        private String ake001; //中心药品项目编码
        private String ake006;	//	医院药品项目名称
        private String aka070;	//	剂型
        private String bka073;	//	厂家
        private String aka074;	//	规格
        private String ake007;	//	费用发生时间
        private String aka067;	//	计量单位
        private String bka040;	//	单价
        private String akc226;	//	用量
        private String aae019;	//	金额
        private String bkz103;	//	用药标志
        private String bka061;	//	出院带药天数
        private String bka062;	//	对应费用序列号
        private String aaz213;	//	医院费用序列号
        private String aae013;	//	备注
        private String bkm109;	//	非工伤费用标志


        /*费用试算*/
        private String akb020; 	//	医疗机构编码
        private String akc252;	//	本次业务个人帐户可用金额
        private String bka066;	//	保存标志
        private String bka006;	//	待遇类别
        private String akc196;	//	出院诊断
        private String aae031;	//	出院日期
        private String bka016;	//	生育就诊类型
        private String bke550;	//	卡识别码

        /*费用试算 回参*/
        private String aaa157;	//	基金编码
        private String bka573;	//	基金名称

        private String localOrRemote; //本地或异地就医
        private List<InptCostDO> costList;//费用集合
        private BigDecimal preselfpayAmt ; // 先行自付金额
        private BigDecimal inscpScpAmt ; // 符合政策范围金额
        private BigDecimal overlmtSelfpay ;  // 超限价金额
        private BigDecimal fulamtOwnpayAmt ; // 全自费金额
        private String uploadRealityPrice;
        private String medChrgitmType;  // 医疗收费项目类别
        private BigDecimal overlmtAmt; // 超限价金额
        private String  lmtUsedFlag ; // 限制使用标志
        private BigDecimal pricUplmtAmt; // 定价上限金额
        private String insureSettleId ; // 医保结算id
        private BigDecimal sumBigDecimalFee; // 本次费用传输总金额
        private BigDecimal detItemFeeSumamt ; // 明细项目费用总额
}