package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物资字典(TbWzzd)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbWzzd implements Serializable {
    private static final long serialVersionUID = -81639357827738750L;
    /**
     * 物资编码
     */
    private String wzbm;
    /**
     * 机构编码
     */
    private String jgbm;
    /**
     * 物资名称
     */
    private String wzmc;
    /**
     * 物资类型编码
     */
    private String wzlxbm;
    /**
     * 规格
     */
    private String wzgg;
    /**
     * 单位
     */
    private String wzdw;
    /**
     * 包装零售价
     */
    private BigDecimal wzlsj;
    /**
     * 生产厂家
     */
    private String sccj;
    /**
     * 备注
     */
    private String bz;
    /**
     * 物资库位
     */
    private String wzkw;
    /**
     * 批发价
     */
    private BigDecimal pfj;
    /**
     * 对应诊疗项目编码
     */
    private String dyzlxmbm;
    /**
     * 对应药品编码
     */
    private String dyypbm;
    /**
     * 是否高值耗材
     */
    private String sfgzhc;
    /**
     * 对应器械编码
     */
    private String dyqxbm;
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

