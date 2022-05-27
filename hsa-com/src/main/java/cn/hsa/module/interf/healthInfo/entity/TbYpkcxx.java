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
 * 药品库存信息(TbYpkcxx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYpkcxx implements Serializable {
    private static final long serialVersionUID = 386118355180413671L;
    /**
     * 医疗机构编码
     */
    private String yljgdm;
    /**
     * 药品编码
     */
    private String ypbm;
    /**
     * 药品库位
     */
    private String kcwz;
    /**
     * 省招标网流水号
     */
    private String szbwlsh;
    /**
     * 药品通用名称
     */
    private String ypmc;
    /**
     * 药品商品名称
     */
    private String ypspmc;
    /**
     * 生产企业
     */
    private String scqy;
    /**
     * 规格
     */
    private String gg;
    /**
     * 包装数量
     */
    private BigDecimal bzsl;
    /**
     * 单位
     */
    private String dw;
    /**
     * 库存数量
     */
    private BigDecimal kcsl;
    /**
     * 最大储量
     */
    private BigDecimal zdcl;
    /**
     * 报警储量
     */
    private BigDecimal bjcl;
    /**
     * 最小储量
     */
    private BigDecimal zxcl;
    /**
     * 药品批发价
     */
    private BigDecimal pfj;
    /**
     * 药品零售价
     */
    private BigDecimal lsj;
    /**
     * 药品失效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sxrq;
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

