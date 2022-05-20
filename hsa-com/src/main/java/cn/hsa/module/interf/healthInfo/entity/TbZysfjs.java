package cn.hsa.module.interf.healthInfo.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 住院收费结算表实体类(TbZysfjs)
 *
 * @author liuliyun
 * @since 2022-05-13 14:56:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbZysfjs extends PageDO implements Serializable {
    private static final long serialVersionUID = -74436561223400590L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 住院结算单号
     */
    private String zyjsdh;
    /**
     * 住院流水号
     */
    private String zylsh;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 缴款单号
     */
    private String jkdh;
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
     * 结算日期时间
     */
    private Date jssj;
    /**
     * 医疗付款方式编码
     */
    private String ylfkfsbm;
    /**
     * 住院费用金额（元）
     */
    private BigDecimal zyfyje;
    /**
     * 住院费用结算方式编码
     */
    private String zyfyjsfsbm;
    /**
     * 住院费用结算方式名称
     */
    private String zyfyjsfsmc;
    /**
     * 医保支付费用金额（元）
     */
    private BigDecimal ybzfyyje;
    /**
     * 自付费用金额（元）
     */
    private BigDecimal zffyje;
    /**
     * 优惠金额（元）
     */
    private BigDecimal yhje;
    /**
     * 预交金总额
     */
    private BigDecimal yjjze;
    /**
     * 结算员编码
     */
    private String jsybm;
    /**
     * 结算员名称
     */
    private String jsymc;
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
