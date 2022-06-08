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
 * 床位记录(TbZycwjl)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:03
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbZycwjl implements Serializable {
    private static final long serialVersionUID = 742150084043372632L;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cwtjsj;
    /**
     * 病床类型
     */
    private String bclx;
    /**
     * 床位费用
     */
    private BigDecimal cwfy;
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

