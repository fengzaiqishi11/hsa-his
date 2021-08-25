package cn.hsa.module.base.bmc.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 25. 病案费用归类表（同步）(BaseMrisClassify)实体类
 *
 * @author makejava
 * @since 2020-09-17 11:45:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseMrisClassifyDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -13758738983579794L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病案费用代码（BAFY）
     */
    private String mrisCode;
    /**
     * 财务分类编码集合（表base_finance_classify）
     */
    private String bfcCodes;
    /**
     * 是否有效（SF）
     */
    private String isValid;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;

}