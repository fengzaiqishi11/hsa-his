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
 * 药品出入库信息(TbYprkxx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYprkxx implements Serializable {
    private static final long serialVersionUID = 127706596115315066L;
    /**
     * 医疗机构编码
     */
    private String yljgdm;
    /**
     * 出入库明细序号
     */
    private String crkmxxh;
    /**
     * 出入库标识
     */
    private String crkbz;
    /**
     * 省招标网流水号
     */
    private String szbwlsh;
    /**
     * 药品编码
     */
    private String ypbm;
    /**
     * 药品通用名称
     */
    private String ypmc;
    /**
     * 药品商品名称
     */
    private String ypspmc;
    /**
     * 发票号
     */
    private String fph;
    /**
     * 开票日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kprq;
    /**
     * 到票日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dprq;
    /**
     * 批准文号
     */
    private String pzwh;
    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scrq;
    /**
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yxq;
    /**
     * 批发价
     */
    private BigDecimal pfj;
    /**
     * 零售价
     */
    private BigDecimal lsj;
    /**
     * 购进价
     */
    private BigDecimal gjj;
    /**
     * 购进总金额
     */
    private BigDecimal gjzje;
    /**
     * 数量
     */
    private BigDecimal sl;
    /**
     * 交易前库存
     */
    private BigDecimal jyqkc;
    /**
     * 交易后库存
     */
    private BigDecimal jyhkc;
    /**
     * 供方交易前库存
     */
    private BigDecimal gfjyqkc;
    /**
     * 供方交易后库存
     */
    private BigDecimal gfjyhkc;
    /**
     * 供方交易时单位
     */
    private String gfjysdw;
    /**
     * 需方交易前库存
     */
    private BigDecimal xfjyqkc;
    /**
     * 需方交易后库存
     */
    private BigDecimal xfjyhkc;
    /**
     * 需方交易时单位
     */
    private String xfjysj;
    /**
     * 出入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crksj;
    /**
     * 出入库操作人员编码
     */
    private String czrybm;
    /**
     * 出入库操作人员姓名
     */
    private String czryxm;
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

