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
 * 财务结算信息(TbCwjsxx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbCwjsxx implements Serializable {
    private static final long serialVersionUID = -83765137799160933L;
    /**
     * 医疗机构编码
     */
    private String yljgdm;
    /**
     * 结算单号
     */
    private String jsdh;
    /**
     * 缴款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jkrq;
    /**
     * 附件张数
     */
    private Integer fjzs;
    /**
     * 金额
     */
    private BigDecimal je;
    /**
     * 收费明细条数
     */
    private Integer sfmxts;
    /**
     * 收费方式条数
     */
    private Integer sffsts;
    /**
     * 交款人员编码
     */
    private String jkrybm;
    /**
     * 交款人员姓名
     */
    private String jkryxm;
    /**
     * 业务类型
     */
    private String ywlx;
    /**
     * 摘要
     */
    private String zy;
    /**
     * 发票总张数
     */
    private Integer fpzzs;
    /**
     * 发票段
     */
    private String fpd;
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

