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
 * 抗菌药物基本情况统计(TbKjywjbjktj)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbKjywjbjktj implements Serializable {
    private static final long serialVersionUID = 935214285439680808L;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 药品编码
     */
    private String ypdm;
    /**
     * 业务类型
     */
    private String ywlx;
    /**
     * 结算日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jsrq;
    /**
     * 抗菌药物使用标识总和
     */
    private String kjywsybszh;
    /**
     * 限定日剂量总和
     */
    private String xdrjlzh;
    /**
     * 中药总剂量
     */
    private String zyzjl;
    /**
     * I类切口手术抗菌药物预防使用标识
     */
    private String ilqksskjyw;
    /**
     * 特殊抗菌药物dd值总和
     */
    private String tskjywddzzh;
    /**
     * 特殊抗菌药物使用标识总和
     */
    private String tskjywsybszh;
    /**
     * 住院天数
     */
    private BigDecimal zyts;
    /**
     * 就诊科室编码
     */
    private String jzksdm;
    /**
     * 就诊科室名称
     */
    private String jzksmc;
    /**
     * 医生编码
     */
    private String ysdm;
    /**
     * 医生名称
     */
    private String ysmc;
    /**
     * 唯一码
     */
    private String wym;
    /**
     * ddds
     */
    private String ddds;
    /**
     * 特殊ddds
     */
    private String tsddds;
    /**
     * 业务编码
     */
    private String ywbm;
    /**
     * 药品名称
     */
    private String ypmc;
    /**
     * 静脉使用标识
     */
    private String jmsybs;
    /**
     * 处方流水号
     */
    private String cflsh;
    /**
     * 入院日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ryrq;
    /**
     * 出院日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cyrq;
    /**
     * 药品规格
     */
    private String ypgg;
    /**
     * 招标流水号
     */
    private String zblsh;
    /**
     * 药品剂量单位
     */
    private String ypjldw;
    /**
     * 药品通用名称
     */
    private String yptymc;
    /**
     * 药品厂家
     */
    private String ypcj;
    /**
     * 单价
     */
    private BigDecimal dj;
    /**
     * 包装量
     */
    private BigDecimal bzl;
    /**
     * 使用数量
     */
    private BigDecimal sysl;
    /**
     * 药品剂型
     */
    private String ypjx;
    /**
     * 金额
     */
    private BigDecimal je;
    /**
     * 药品单剂量
     */
    private String ypdjl;
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

