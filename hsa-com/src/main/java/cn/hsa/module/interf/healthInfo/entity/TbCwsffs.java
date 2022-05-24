package cn.hsa.module.interf.healthInfo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 财务结算-收费方式(TbCwsffs)实体类
 * @author liuliyun
 * @since 2022-05-20 15:42:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbCwsffs implements Serializable {
    private static final long serialVersionUID = 393998331178620120L;
    /**
     * 医疗机构编码
     */
    private String yljgdm;
    /**
     * 结算方式ID
     */
    private String jsfs;
    /**
     * 结算单号
     */
    private String jsdh;
    /**
     * 结算方式代码
     */
    private String jsfsdm;
    /**
     * 结算方式名称
     */
    private String jsfsmc;
    /**
     * 缴款日期
     */
    private Date jkrq;
    /**
     * 金额
     */
    private Double je;
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
