package cn.hsa.module.interf.bill.entity;

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
 * 门诊票据费用对象
 * @author liudawen
 * @date 2021/11/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class MzpjFyDO extends PageDO implements Serializable{

    private static final long serialVersionUID = 5421780542975049501L;
    /**
     * 主键
     */
    private String id;
    /**
     * 接口信息id
     */
    private String jk_id;
    /**
     * 上传信息id
     */
    private String sc_id;
    /**
     * 单位组织机构代码（国家卫计委）
     */
    private String zzjgdm;
    /**
     * 票据代码
     */
    private String pjdm;
    /**
     * 票据号码
     */
    private String pjhm;
    /**
     * 开票日期 格式:20160808
     */
    private String kprq;
    /**
     * 财政收费项目编码见:3.1.1，药品类要到四级编码
     */
    private String czsfxmbm;
    /**
     * 财政收费项目名称见:3.1.1，药品类要到四级编码
     */
    private String czsfxmmc;
    /**
     * 项目数量
     */
    private BigDecimal xmsl;
    /**
     * 项目单位
     */
    private String xmdw;
    /**
     * 项目金额
     */
    private BigDecimal xmje;
    /**
     * 项目备注
     */
    private String xmbz;
    /**
     * 创建时间
     */
    private Date cjsj;
    /**
     * 业务流水号（医疗卫生机构收费系统）
     */
    private String ywlsh;

}
