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
 * 门诊处方明细表(TbMzcfmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbMzcfmx implements Serializable {
    private static final long serialVersionUID = 825944334265700596L;
    /**
     * 处方项目明细号码
     */
    private String cfmxh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 处方流水号
     */
    private String cflsh;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 门诊就诊流水号
     */
    private String mzlsh;
    /**
     * 处方号
     */
    private String cfh;
    /**
     * 开方医生编码
     */
    private String kfysbm;
    /**
     * 开方医生姓名
     */
    private String kfysxm;
    /**
     * 开方时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kfsj;
    /**
     * 开方科室代码
     */
    private String kfksdm;
    /**
     * 开方科室名称
     */
    private String kfksmc;
    /**
     * 执行科室代码
     */
    private String zxksdm;
    /**
     * 执行科室名称
     */
    private String zxksmc;
    /**
     * 项目/药品编码
     */
    private String xmbm;
    /**
     * 项目/药品名称
     */
    private String xmmc;
    /**
     * 数量
     */
    private BigDecimal sl;
    /**
     * 单价
     */
    private BigDecimal dj;
    /**
     * 金额
     */
    private BigDecimal je;
    /**
     * 数量单位
     */
    private String ypdw;
    /**
     * 处方组号
     */
    private String yzzh;
    /**
     * 是否药品
     */
    private String sfyp;
    /**
     * 用量
     */
    private BigDecimal yl;
    /**
     * 用量单位
     */
    private String yldw;
    /**
     * 用药频次
     */
    private String sypc;
    /**
     * 用药速度
     */
    private String yysd;
    /**
     * 用药天数
     */
    private BigDecimal yyts;
    /**
     * 是否皮试
     */
    private String sfps;
    /**
     * 皮试结果
     */
    private String psjg;
    /**
     * 药品规格
     */
    private String ypgg;
    /**
     * 每次使用剂量
     */
    private BigDecimal jl;
    /**
     * 每次使用剂量单位
     */
    private String dw;
    /**
     * 每次使用数量
     */
    private BigDecimal mcsl;
    /**
     * 每次使用数量单位
     */
    private String mcdw;
    /**
     * 用药途径代码
     */
    private String yytjdm;
    /**
     * 用药途径名称
     */
    private String yytjmc;
    /**
     * 药物剂型
     */
    private String ywjx;
    /**
     * 中药饮片煎煮法
     */
    private String zyypjzf;
    /**
     * 中药用药方法
     */
    private String zyyyff;
    /**
     * 中药用法特殊要求
     */
    private String tsyq;
    /**
     * 中药用法调剂
     */
    private String tj;
    /**
     * 中药使用类别代码
     */
    private String zysylbdm;
    /**
     * 治则治法
     */
    private String zzzf;
    /**
     * 抗菌药物标志
     */
    private String kjywbz;
    /**
     * 基本药物标志
     */
    private String jbywbz;
    /**
     * 特殊药品分类
     */
    private String tsypfl;
    /**
     * 静脉使用标识
     */
    private String jmsybz;
    /**
     * 药理类别
     */
    private String yllb;
    /**
     * 限定日剂量（DDD）
     */
    private BigDecimal ddd;
    /**
     * 抗菌药物等级代码
     */
    private String kjywdjdm;
    /**
     * 检查部位
     */
    private String jcbw;
    /**
     * 备注信息
     */
    private String bz;
    /**
     * 处方状态
     */
    private String zfzt;
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

