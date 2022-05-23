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
 * 诊断明细表(TbZdmx)实体类
 *
 * @author makejava
 * @since 2022-05-19 10:23:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbZdmx extends PageDO implements Serializable {
    private static final long serialVersionUID = 505902109811125519L;
    /**
     * 诊断流水号
     */
    private String zyzdlsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 就诊流水号
     */
    private String jzlsh;
    /**
     * 患者类型代码
     */
    private String hzlxdm;
    /**
     * 病人姓名
     */
    private String xm;
    /**
     * 病人性别代码
     */
    private String xbdm;
    /**
     * 性别名称
     */
    private String xbmc;
    /**
     * 年龄
     */
    private String nl;
    /**
     * 诊断类型区分
     */
    private String zdlxqf;
    /**
     * 诊断类别代码
     */
    private String zdlb;
    /**
     * 诊断时间
     */
    private Date zdsj;
    /**
     * 诊断编码
     */
    private String zdbm;
    /**
     * 诊断名称
     */
    private String zdmc;
    /**
     * 诊断顺序
     */
    private Integer zdsx;
    /**
     * 诊断说明
     */
    private String zdsm;
    /**
     * 主要诊断标志
     */
    private String cyzdbz;
    /**
     * 疑似诊断标志
     */
    private String yzdbz;
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
