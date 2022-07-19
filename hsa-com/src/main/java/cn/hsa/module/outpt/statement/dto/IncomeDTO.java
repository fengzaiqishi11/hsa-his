package cn.hsa.module.outpt.statement.dto;

import cn.hsa.base.PageDO;
import cn.hsa.util.BigDecimalUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Class_name: IncomeDTO
 * @Describe: 全院月收入DTO
 * @Author: zhangguorui
 * @Email: guorui.zhang@powersi.com
 * @Date: 2021/12/10 14:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class IncomeDTO   implements Serializable {
    private static final long serialVersionUID = -816969372277325373L;
    private String bfcId;
    private String upCode;
    private String bfcName;// 收入分类
    private BigDecimal yardCurrentRealityPrice;// 全院本月收入
    private BigDecimal yardYearRealityPrice;// 全院去年同期
    private String yardSameCompare;// 全院去年同比
    private BigDecimal yardMonthRealityPrice;// 全院上月金额
    private String yardLinkCompare;// 全院环比
    private BigDecimal outCurrentRealityPrice;// 门诊本月收入
    private BigDecimal outYearRealityPrice;// 门诊去年同期
    private String outSameCompare;// 门诊去年同比
    private BigDecimal outMonthRealityPrice;// 门诊上月金额
    private String outLinkCompare;// 门诊环比
    private BigDecimal inCurrentRealityPrice;// 住院本月收入
    private BigDecimal inYearRealityPrice;// 住院去年同期
    private String inSameCompare;// 住院去年同比
    private BigDecimal inMonthRealityPrice;// 住院上月金额
    private String inLinkCompare;// 住院环比

    private int pageNo;
    private int pageSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String sumCode;
    private List<String> list = new ArrayList<>();
    private String hospCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastMonthStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastMonthEndDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastYearStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastYearEndDate;
}
