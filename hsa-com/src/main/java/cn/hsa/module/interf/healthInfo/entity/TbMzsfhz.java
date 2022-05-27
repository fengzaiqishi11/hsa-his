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
 * 门诊收费汇总表(TbMzsfhz)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbMzsfhz implements Serializable {
    private static final long serialVersionUID = 770864100662428681L;
    /**
     * 门诊收费结算单号
     */
    private String mzsfjsdh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 缴款单号
     */
    private String jkdh;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 门诊就诊流水号
     */
    private String mzlsh;
    /**
     * 患者姓名
     */
    private String xm;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 性别名称
     */
    private String xbmc;
    /**
     * 年龄
     */
    private String nl;
    /**
     * 发票号
     */
    private String fph;
    /**
     * 费用总额
     */
    private BigDecimal fyze;
    /**
     * 补偿总额
     */
    private BigDecimal bcze;
    /**
     * 自付总额
     */
    private BigDecimal zfze;
    /**
     * 退费标识
     */
    private String tfbz;
    /**
     * 收费员编码
     */
    private String sfrybm;
    /**
     * 收费人名称
     */
    private String sfrymc;
    /**
     * 收费时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sfsj;
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

