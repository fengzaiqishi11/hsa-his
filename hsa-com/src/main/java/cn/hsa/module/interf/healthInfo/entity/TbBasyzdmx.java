package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 病案首页-诊断明细(TbBasyzdmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbBasyzdmx implements Serializable {
    private static final long serialVersionUID = 115441278106742262L;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appetime;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

