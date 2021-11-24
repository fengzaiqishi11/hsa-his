package cn.hsa.module.interf.bill.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门诊票据主体对象
 * @author liudawen
 * @date 2021/11/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MzpjZtDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 接口id
     */
    private String jk_id;
    /**
     * 上传id
     */
    private String sc_id;
    /**
     * 单位组织机构代码（国家卫计委）
     */
    private String zzjgdm;
    /**
     * 单位所属地区行政编码（全国唯一）
     */
    private String xzqhdm;
    /**
     * 单位编码（财政分配编码）
     */
    private String dwbm;
    /**
     * 单位编码（财政分配编码）
     */
    private String czqhbm;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 票据代码
     */
    private String pjdm;
    /**
     * 票据号码
     */
    private String pjhm;
    /**
     * 必须填写，缴款人统一社会信用代码（身份证号码）
     */
    private String zjhm;
    /**
     * 票据校验码
     */
    private String pjjym;
    /**
     * 缴款人
     */
    private String xm;
    /**
     * 开票日期 格式:20160808
     */
    private String kprq;
    /**
     * 业务流水号（医疗卫生机构收费系统）
     */
    private String ywlsh;
    /**
     * 医疗机构类型代码见：4.1.6
     */
    private String yljglx;
    /**
     * 性别
     */
    private String xb;
    /**
     * 门诊流水号
     */
    private String mzlsh;
    /**
     * 就诊日期 格式:20160808
     */
    private String jzrq;
    /**
     * 医保类型
     */
    private String yblx;
    /**
     * 医保编号
     */
    private String ybbh;
    /**
     * 医保统筹基金支付
     */
    private BigDecimal jjzf;
    /**
     * 其他支付
     */
    private BigDecimal qt;
    /**
     * 个人账户支付
     */
    private BigDecimal grzhzf;
    /**
     * 个人现金支付
     */
    private BigDecimal grxjzf;
    /**
     * 个人自付
     */
    private BigDecimal grzf;
    /**
     * 个人自费
     */
    private BigDecimal grzf1;
    /**
     * 总金额小写
     */
    private BigDecimal zje;
    /**
     * 复核人
     */
    private String fhrxm;
    /**
     * 收款人
     */
    private String skrxm;
    /**
     * 是否作废 1 是0 否
     */
    private String sfzf;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cjsj;

}
