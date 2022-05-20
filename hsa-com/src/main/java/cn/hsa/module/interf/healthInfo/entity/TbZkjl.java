package cn.hsa.module.interf.healthInfo.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 转科记录表实体类(TbZkjl)*
 * @author liuliyun
 * @since 2022-05-12 14:10:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbZkjl extends PageDO implements Serializable {
    private static final long serialVersionUID = -39349312930727523L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 转科时间
     */
    private Date zksj;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 转入科室代码
     */
    private String zrksdm;
    /**
     * 转入科室名称
     */
    private String zrksmc;
    /**
     * 转出科室代码
     */
    private String zcksdm;
    /**
     * 转出科室名称
     */
    private String zcksmc;
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
