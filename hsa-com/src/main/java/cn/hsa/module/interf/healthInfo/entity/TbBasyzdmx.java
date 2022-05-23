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
 * 病案首页-诊断明细实体类(TbBasyzdmx)
 * @author liuliyun
 * @since 2022-05-18 09:04:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbBasyzdmx extends PageDO implements Serializable {
    private static final long serialVersionUID = -12672793457252652L;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 诊断记录流水号
     */
    private String zdjllsh;
    /**
     * 诊断主次编码
     */
    private String zdzcbm;
    /**
     * 诊断顺序
     */
    private Integer zdsx;
    /**
     * 出院诊断编码
     */
    private String cyzdbm;
    /**
     * 出院诊断名称
     */
    private String cyzdmc;
    /**
     * 出院诊断疾病编码
     */
    private String cyzdjbbm;
    /**
     * 出院诊断疾病名称
     */
    private String cyzdjbmc;
    /**
     * 入院病情代码
     */
    private String rybqdm;
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
