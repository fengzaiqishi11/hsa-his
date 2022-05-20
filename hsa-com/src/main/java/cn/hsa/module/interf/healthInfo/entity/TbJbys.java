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
 * 疾病字典映射(TbJbys)实体类
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
public class TbJbys implements Serializable {
    private static final long serialVersionUID = 826274269582620013L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 院内疾病代码
     */
    private String ynjbdm;
    /**
     * 院内疾病名称
     */
    private String ynjbmc;
    /**
     * 平台标准疾病代码
     */
    private String ptbzjbdm;
    /**
     * 平台标准疾病名称
     */
    private String ptbzjbmc;
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

