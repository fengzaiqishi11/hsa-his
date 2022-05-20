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
 * 收费明细费用类别映射(TbFylbys)实体类
 *
 * @author liudawen
 * @date 2022-05-18 10:26:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbFylbys implements Serializable {
    private static final long serialVersionUID = 563198962833963116L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 院内收费明细费用类别编码
     */
    private String ynsfmxbm;
    /**
     * 院内收费明细费用类别名称
     */
    private String ynsfmxmc;
    /**
     * 平台标准收费明细费用类别编码
     */
    private String ptbzsfmxbm;
    /**
     * 平台标准收费明细费用类别名称
     */
    private String ptbzsfmxmc;
    /**
     * 映射时间
     */
    private Date yssj;
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

