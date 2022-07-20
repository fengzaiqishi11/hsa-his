package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 处方信息
 */
@Data
public class AnaOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 处方(医嘱)标识
     */
//    @JSONField(name = "rx_id")
    private String rxId;
    /**
     * 处方号
     */
//    @JSONField(name = "rxno")
    private String rxno;
    /**
     * 组编号
     */
//    @JSONField(name = "grpno")
    private String grpno;
    /**
     * 是否为长期医嘱
     */
//    @JSONField(name = "long_drord_flag")
    private String longDrordFlag;
    /**
     * 目录类别
     */
//    @JSONField(name = "hilist_type")
    private String hilistType;
    /**
     * 收费类别
     */
//    @JSONField(name = "chrg_type")
    private String chrgType;
    /**
     * 医嘱行为
     */
//    @JSONField(name = "drord_bhvr")
    private String drordBhvr;
    /**
     * 医保目录代码
     */
//    @JSONField(name = "hilist_code")
    private String hilistCode;
    /**
     * 医保目录名称
     */
//    @JSONField(name = "hilist_name")
    private String hilistName;
    /**
     * 医保目录(药品)剂型
     */
//    @JSONField(name = "hilist_dosform")
    private String hilistDosform;
    /**
     * 医保目录等级
     */
//    @JSONField(name = "hilist_lv")
    private String hilistLv;
    /**
     * 医保目录价格
     */
//    @JSONField(name = "hilist_pric")
    private BigDecimal hilistPric;
    /**
     * 一级医院目录价格
     */
//    @JSONField(name = "lv1_hosp_item_pric")
    private BigDecimal lv1HospItemPric;
    /**
     * 二级医院目录价格
     */
//    @JSONField(name = "lv2_hosp_item_pric")
    private BigDecimal lv2HospItemPric;
    /**
     * 三级医院目录价格;
     */
//    @JSONField(name = "lv3_hosp_item_pric")
    private BigDecimal lv3HospItemPric;
    /**
     * 医保目录备注
     */
//    @JSONField(name = "hilist_memo")
    private String hilistMemo;
    /**
     * 医院目录代码
     */
//    @JSONField(name = "hosplist_code")
    private String hosplistCode;
    /**
     * 医院目录名称
     */
//    @JSONField(name = "hosplist_name")
    private String hosplistName;
    /**
     * 医院目录(药品)剂型
     */
//    @JSONField(name = "hosplist_dosform")
    private String hosplistDosform;
    /**
     * 数量
     */
//    @JSONField(name = "cnt")
    private BigDecimal cnt;
    /**
     * 单价
     */
//    @JSONField(name = "pric")
    private BigDecimal pric;
    /**
     * 总费用
     */
//    @JSONField(name = "sumamt")
    private BigDecimal sumamt;
    /**
     * 自费金额
     */
//    @JSONField(name = "ownpay_amt")
    private BigDecimal ownpayAmt;
    /**
     * 自付金额
     */
//    @JSONField(name = "selfpay_amt")
    private BigDecimal selfpayAmt;
    /**
     * 规格
     */
//    @JSONField(name = "spec")
    private String spec;
    /**
     * 数量单位
     */
//    @JSONField(name = "spec_unt")
    private String specUnt;
    /**
     * 医嘱开始日期
     */
//    @JSONField(name = "drord_begn_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date drordBegnDate;
    /**
     * 医嘱停止日期
     */
//    @JSONField(name = "drord_stop_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date drordStopDate;
    /**
     * 下达医嘱的科室标识
     */
//    @JSONField(name = "drord_dept_codg")
    private String drordDeptCodg;
    /**
     * 下达医嘱科室名称
     */
//    @JSONField(name = "drord_dept_name")
    private String drordDeptName;
    /**
     * 开处方(医嘱)医生标识
     */
//    @JSONField(name = "drord_dr_codg")
    private String drordDrCodg;
    /**
     * 开处方(医嘱)医生姓名
     */
//    @JSONField(name = "drord_dr_name")
    private String drordDrName;
    /**
     * 开处方(医嘱)医职称
     */
//    @JSONField(name = "drord_dr_profttl")
    private String drordDrProfttl;
    /**
     * 是否当前处方(医嘱)
     */
//    @JSONField(name = "curr_drord_flag")
    private String currDrordFlag;
}
