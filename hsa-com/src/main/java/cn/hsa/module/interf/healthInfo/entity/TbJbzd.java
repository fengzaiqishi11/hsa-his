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
 * 疾病字典(TbJbzd)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbJbzd implements Serializable {
    private static final long serialVersionUID = 249117038830337562L;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 疾病诊断编码
     */
    private String jbzdbm;
    /**
     * 疾病诊断名称
     */
    private String jbzdmc;
    /**
     * 诊断通用名称
     */
    private String zdtymc;
    /**
     * ICD10编码
     */
    private String icd10bm;
    /**
     * ICD10名称
     */
    private String icd10mc;
    /**
     * 疾病种类编码
     */
    private String jbzlbm;
    /**
     * 疾病种类描述
     */
    private String jbzlms;
    /**
     * 病种类型
     */
    private String bzlx;
    /**
     * 是否单病种标识
     */
    private String dbzbs;
    /**
     * 卫生局标准编码
     */
    private String wsjbzbm;
    /**
     * 医保局标准编码
     */
    private String ybjbm;
    /**
     * 新农合标准编码
     */
    private String xnhbjbm;
    /**
     * 是否有效标志
     */
    private String sfyxbz;
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

