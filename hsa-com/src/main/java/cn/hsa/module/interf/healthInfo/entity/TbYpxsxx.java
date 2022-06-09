package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 药品销售信息(TbYpxsxx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYpxsxx implements Serializable {
    private static final long serialVersionUID = 438920038190472971L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 药品代码
     */
    private String ypdm;
    /**
     * 就诊号
     */
    private String jzh;
    /**
     * 业务类型
     */
    private String ywlx;
    /**
     * 药房代码
     */
    private String yfdm;
    /**
     * 省招标网流水号
     */
    private String szbwlsh;
    /**
     * 药品名称
     */
    private String ypmc;
    /**
     * 商品名
     */
    private String spm;
    /**
     * 生产企业
     */
    private String scqy;
    /**
     * 剂型
     */
    private String ypjxms;
    /**
     * 规格
     */
    private String ypgg;
    /**
     * 包装量
     */
    private BigDecimal bzl;
    /**
     * 单位
     */
    private String dw;
    /**
     * 采购价格
     */
    private String cgj;
    /**
     * 销售价格
     */
    private String xsjg;
    /**
     * 销售数量
     */
    private BigDecimal xssl;
    /**
     * 销售金额
     */
    private BigDecimal xsje;
    /**
     * 药品属性
     */
    private String ypsx;
    /**
     * 药品类型
     */
    private String xplx;
    /**
     * 抗菌药物标志
     */
    private String kjybz;
    /**
     * 抗菌药物级别
     */
    private String kjyjb;
    /**
     * 销售时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xssj;
    /**
     * 销售科室代码
     */
    private String xsksdm;
    /**
     * 销售科室名称
     */
    private String xsksmc;
    /**
     * 销售医生代码
     */
    private String xsysdm;
    /**
     * 销售医生名称
     */
    private String xsysmc;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 数据有效标志
     */
    private String validflag;
    /**
     * 数据产生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appetime;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifytime;
    /**
     * 最后修改人编码
     */
    private String modifytcode;
    /**
     * 最后修改人名称
     */
    private String modifytname;


}

