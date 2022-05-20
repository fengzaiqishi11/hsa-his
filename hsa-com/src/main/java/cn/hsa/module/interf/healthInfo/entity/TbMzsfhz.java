package cn.hsa.module.interf.healthInfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 门诊收费汇总表实体类（TbMzsfhz） *
 * @author liuliyun
 * @since 2022-05-11 10:25:48
 * @Email liyun.liu@powersi.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbMzsfhz extends PageDO implements Serializable {
    private static final long serialVersionUID = 106675307793385045L;
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
    private Date sfsj;
    /**
     * 数据有效标志
     */
    private String validflag;
    /**
     * 数据产生时间
     */
    private Date appetime;
    /**
     * 最后修改时间
     */
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
