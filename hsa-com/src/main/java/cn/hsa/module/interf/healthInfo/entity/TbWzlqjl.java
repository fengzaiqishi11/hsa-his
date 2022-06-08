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
 * 物资领取记录表(TbWzlqjl)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbWzlqjl implements Serializable {
    private static final long serialVersionUID = 890743232978612182L;
    /**
     * 物资领取单号
     */
    private String lqdh;
    /**
     * 物资领取序号
     */
    private Integer lqdhxh;
    /**
     * 机构编码
     */
    private String jgbm;
    /**
     * 物资编码
     */
    private String wzbm;
    /**
     * 物资名称
     */
    private String wzmc;
    /**
     * 物资类型编码
     */
    private String wzlxbm;
    /**
     * 物资类型名称
     */
    private String wzlxmc;
    /**
     * 机构名称
     */
    private String jgmc;
    /**
     * 领取科室编码
     */
    private String lqksbm;
    /**
     * 领取科室名称
     */
    private String lqksmc;
    /**
     * 领取数量
     */
    private String lqsl;
    /**
     * 物资规格
     */
    private String wzgg;
    /**
     * 物资单位
     */
    private String wzdw;
    /**
     * 物资单价
     */
    private String wzdj;
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sqsj;
    /**
     * 领取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lqsj;
    /**
     * 进货价格
     */
    private BigDecimal jhjg;
    /**
     * 领取人编码
     */
    private String lqrbm;
    /**
     * 领取人名称
     */
    private String lqrmc;
    /**
     * 发放人编码
     */
    private String ffrbm;
    /**
     * 发放人名称
     */
    private String ffrmc;
    /**
     * 患者住院流水号
     */
    private String zylsh;
    /**
     * 物资库位
     */
    private String wzkw;
    /**
     * 物资批号
     */
    private String wzph;
    /**
     * 诊疗项目编码
     */
    private String zlxmbm;
    /**
     * 诊疗项目名称
     */
    private String zlxmmc;
    /**
     * 诊疗项目价格
     */
    private BigDecimal zlxmjg;
    /**
     * 生产厂家名称
     */
    private String sccj;
    /**
     * 是否高值耗材
     */
    private String sfgzhc;
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

