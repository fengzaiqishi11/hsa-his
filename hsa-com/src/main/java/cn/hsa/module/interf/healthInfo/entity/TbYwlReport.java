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
 * 业务量-收入统计表(TbYwlReport)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYwlReport implements Serializable {
    private static final long serialVersionUID = 774976729991012184L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 科室编码
     */
    private String ksbm;
    /**
     * 医院科室名称
     */
    private String ksmc;
    /**
     * 业务时间
     */
    private String ywsj;
    /**
     * 门诊人次
     */
    private BigDecimal mzrc;
    /**
     * 急诊人次
     */
    private BigDecimal jzrc;
    /**
     * 入院人次
     */
    private BigDecimal ryrc;
    /**
     * 期内结算(出院)人次
     */
    private BigDecimal cyrc;
    /**
     * 在院人数
     */
    private BigDecimal zyrs;
    /**
     * 实有床位数
     */
    private BigDecimal sycws;
    /**
     * 门急诊医疗费用
     */
    private BigDecimal mjzylfy;
    /**
     * 住院医疗费用
     */
    private BigDecimal zyylfy;
    /**
     * 门急诊药品费用
     */
    private BigDecimal mjzypfy;
    /**
     * 住院药品费用
     */
    private BigDecimal zyypfy;
    /**
     * 门急诊医保医疗费用
     */
    private BigDecimal mjzybylfy;
    /**
     * 住院医保医疗费用
     */
    private BigDecimal zyybylfy;
    /**
     * 门急诊医保药品费用
     */
    private BigDecimal mjzybypfy;
    /**
     * 住院医保药品费用
     */
    private BigDecimal zyybypfy;
    /**
     * 住院实际发生费用
     */
    private BigDecimal zysjfsys;
    /**
     * 预留统计字段1
     */
    private BigDecimal tjyl1;
    /**
     * 预留统计字段2
     */
    private BigDecimal tjyl2;
    /**
     * 预留统计字段3
     */
    private BigDecimal tjyl3;
    /**
     * 预留统计字段4
     */
    private BigDecimal tjyl4;
    /**
     * 预留统计字段5
     */
    private BigDecimal tjyl5;
    /**
     * 预留统计字段6
     */
    private BigDecimal tjyl6;
    /**
     * 预留统计字段7
     */
    private BigDecimal tjyl7;
    /**
     * 预留统计字段8
     */
    private BigDecimal tjyl8;
    /**
     * 修改标志
     */
    private Integer xgbz;
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

