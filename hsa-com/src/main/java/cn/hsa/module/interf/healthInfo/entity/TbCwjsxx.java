package cn.hsa.module.interf.healthInfo.entity;

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
 * 财务结算信息(TbCwjsxx)实体类
 *
 * @author makejava
 * @since 2022-05-20 09:46:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbCwjsxx extends PageDO implements Serializable {
    private static final long serialVersionUID = 308715491696139930L;
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
    private Date jkrq;
    /**
     * 附件张数
     */
    private Integer fjzs;
    /**
     * 金额
     */
    private Double je;
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
