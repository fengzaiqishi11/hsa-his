package cn.hsa.module.insure.fmiownpaypatn.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName FmiOwnpayPatnFeeListDDTO
 * @Description TODO 自费病人就医费用明细信息信息 【4207】
 * @Author qiang.fan
 * @Date 2022/4/6 11:20
 * @Version 1.0
 **/
@Data
public class FmiOwnpayPatnFeeListDDTO {
    /**
     * 	记账流水号	字符型	30		Y
     */
    private String bkkpSn;
    /**
     * 	医药机构就诊 ID	字符型	30		Y
     */
    private String fixmedinsMdtrtId;
    /**
     * 	费用发生时间	时间类型			Y	yyyy-MM-dd HH:mm:ss
     */
    private String feeOcurTime;
    /**
     * 	定点医药机构编号	字符型	30		Y
     */
    private String fixmedinsCode;
    /**
     * 	定点医药机构名称	字符型	255		Y
     */
    private String fixmedinsName;
    /**
     * 	数量	数值型	16,4		Y
     */
    private BigDecimal cnt;
    /**
     * 	单价	数值型	16,6		Y
     */
    private BigDecimal pric;
    /**
     * 	明细项目费用总额	数值型	16,2		Y
     */
    private BigDecimal detItemFeeSumamt;
    /**
     * 		医疗目录编码	字符型	50
     */
    private String medListCodg;
    /**
     * 	医药机构目录编码	字符型	150
     */
    private String medinsListCodg;
    /**
     * 		医药机构目录名称	字符型	100
     */
    private String medinsListName;
    /**
     * 	医疗收费项目类别	字符型	6
     */
    private String medChrgitmType;
    /**
     * 	商品名	字符型	500
     */
    private String prodname;
    /**
     * 	开单科室编码	字符型	30
     */
    private String bilgDeptCodg;
    /**
     * 	开单科室名称	字符型	100
     */
    private String bilgDeptName;
    /**
     * 		开单医师代码	字符型	30
     */
    private String bilgDrCodg;
    /**
     * 	开单医师姓名	字符型	50
     */
    private String bilgDrName;
    /**
     * 	受单科室编码	字符型	30
     */
    private String ordersDeptCode;
    /**
     * 		受单科室名称	字符型	100
     */
    private String ordersDeptName;
    /**
     * 	受单医师代码	字符型	30
     */
    private String ordersDrCode;
    /**
     * 		受单医师姓名	字符型	50
     */
    private String ordersDrName;
    /**
     * 	中药使用方式	字符型	6
     */
    private String tcmdrugUsedWay;
    /**
     * 	外检标志	字符型	3
     */
    private String extinsFlag;
    /**
     * 	外检医院编码	字符型	30
     */
    private String extinsHospCode;
    /**
     * 	出院带药标志	字符型	3
     */
    private String dscgTkdrugFlag;
    /**
     * 	单次剂量描述	字符型	200
     */
    private String sinDosDscr;
    /**
     * 	使用频次描述	字符型	200
     */
    private String usedFrquDscr;
    /**
     * 		周期天数	数值型	4,2
     */
    private String prdDays;
    /**
     * 		用药途径描述	字符型	200
     */
    private String medcWayDscr;
    /**
     * 	备注	字符型	500
     */
    private String memo;
    /**
     * 	有效标志	字符型	3		Y	不传默认1有效
     */
    private String valiFlag;
    /**
     * 	全自费金额	数值型 16,2
     */
    private String fulamtOwnpayAmt;
    /**
     * 	超限价自费金额	数值型 16,2
     */
    private String overlmtSelfpay;
    /**
     * 	先行自付金额	数值型 16,2
     */
    private String preselfpayAmt;
    /**
     * 	符合政策范围金额	数值型 16,2
     */
    private String inscpAmt;
    /**
     * 	审核通过标 字符型	3
     */
    private String chkPassFlag;
    /**
     * 	处方号 字符型 30
     */
    private String rxno;
}
