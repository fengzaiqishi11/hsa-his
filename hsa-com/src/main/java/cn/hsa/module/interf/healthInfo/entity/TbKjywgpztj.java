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
 * 抗菌药物各品种统计(TbKjywgpztj)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbKjywgpztj implements Serializable {
    private static final long serialVersionUID = 226418665594875736L;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 药品编码
     */
    private String ypdm;
    /**
     * 药品名称
     */
    private String ypmc;
    /**
     * 业务类型
     */
    private String ywlx;
    /**
     * 字典项目名称
     */
    private String zdxmmc;
    /**
     * 规格
     */
    private String ypgg;
    /**
     * 剂型
     */
    private String ypjxms;
    /**
     * 包装数量
     */
    private BigDecimal bzsl;
    /**
     * 计量单位
     */
    private String jldw;
    /**
     * 单价
     */
    private BigDecimal dj;
    /**
     * 使用金额
     */
    private BigDecimal syje;
    /**
     * 管理级别
     */
    private String gljb;
    /**
     * 招标网流水号
     */
    private String zbwlsh;
    /**
     * 生产厂家
     */
    private String scqy;
    /**
     * 抗菌药物管理级别
     */
    private String kjywgljb;
    /**
     * 数量
     */
    private BigDecimal sl;
    /**
     * 单位
     */
    private String dw;
    /**
     * 金额
     */
    private BigDecimal je;
    /**
     * 业务编码
     */
    private String ywdm;
    /**
     * 抗菌药物标识
     */
    private String kjywbs;
    /**
     * 限定日dd值
     */
    private String xdddz;
    /**
     * 开放科室编码
     */
    private String kfksbm;
    /**
     * 执行日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zxrq;
    /**
     * 唯一标识码
     */
    private String wybsm;
    /**
     * 科室名称
     */
    private String kfksmc;
    /**
     * 医生编码
     */
    private String ysbm;
    /**
     * 医生名称
     */
    private String ysmc;
    /**
     * 商品名
     */
    private String spm;
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

