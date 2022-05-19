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
 * 床位记录实体类（TbZycwjl）
 * @author liuliyun
 * @since 2022-05-17 09:57:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbZycwjl extends PageDO implements Serializable {
    private static final long serialVersionUID = 587823687207031273L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 科室编码
     */
    private String ksbm;
    /**
     * 床位编码
     */
    private String cwbm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 科室名称
     */
    private String ksmc;
    /**
     * 床位名称
     */
    private String cwmc;
    /**
     * 病床占用状态
     */
    private String bczyzt;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 床位统计时间
     */
    private Date cwtjsj;
    /**
     * 病床类型
     */
    private String bclx;
    /**
     * 床位费用
     */
    private Double cwfy;
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
