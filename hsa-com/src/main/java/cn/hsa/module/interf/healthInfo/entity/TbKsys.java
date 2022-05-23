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
 * 科室字典映射(TbKsys)实体类
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
public class TbKsys implements Serializable {
    private static final long serialVersionUID = -13224372475902691L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 院内科室代码
     */
    private String ynksdm;
    /**
     * 院内科室名称
     */
    private String ynksmc;
    /**
     * 平台标准科室代码
     */
    private String ptbzksdm;
    /**
     * 平台标准科室名称
     */
    private String ptbzksmc;
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

