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
 * 诊疗项目字典(TbZlxmzd)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:03
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbZlxmzd implements Serializable {
    private static final long serialVersionUID = -76325299244257492L;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 项目编码
     */
    private String xmbm;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * 项目通用名称
     */
    private String xmtym;
    /**
     * 项目规格
     */
    private String xmgg;
    /**
     * 项目单位描述
     */
    private String xmdwms;
    /**
     * 项目类别编码
     */
    private String xmlbbm;
    /**
     * 项目类别名称
     */
    private String xmlbmc;
    /**
     * 项目级别描述
     */
    private String xmjbms;
    /**
     * 卫生局标准编码
     */
    private String wsjbm;
    /**
     * 医保局标准编码
     */
    private String ybjbm;
    /**
     * 新农合标准编码
     */
    private String xnhbjbm;
    /**
     * 省标准编码
     */
    private String sbjbm;
    /**
     * 是否有效
     */
    private String yxbz;
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

