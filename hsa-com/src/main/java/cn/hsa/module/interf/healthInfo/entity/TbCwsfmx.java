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
 * 财务结算-收费明细(TbCwsfmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbCwsfmx implements Serializable {
    private static final long serialVersionUID = 775980903722993869L;
    /**
     * 医疗机构编码
     */
    private String yljgdm;
    /**
     * 结算单号
     */
    private String jsdh;
    /**
     * 收费明细ID
     */
    private String sfmxid;
    /**
     * 就诊记录流水号
     */
    private String jzlsh;
    /**
     * 患者类型代码
     */
    private String hzlxdm;
    /**
     * 患者姓名
     */
    private String hzxm;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 性别名称
     */
    private String xbmc;
    /**
     * 缴款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jkrq;
    /**
     * 缴款班次号
     */
    private String jkbch;
    /**
     * 收费项目代码
     */
    private String sfxmdm;
    /**
     * 收费项目名称
     */
    private String sfxmmc;
    /**
     * 就诊科室编码
     */
    private String jzksbm;
    /**
     * 就诊科室名称
     */
    private String jzksmc;
    /**
     * 收费类型代码
     */
    private String sflxdm;
    /**
     * 收费类型名称
     */
    private String sflxmc;
    /**
     * 金额
     */
    private BigDecimal je;
    /**
     * 摘要
     */
    private String zy;
    /**
     * 开单医生编码
     */
    private String kdysbm;
    /**
     * 开单医生姓名
     */
    private String kdysxm;
    /**
     * 业务类型
     */
    private String ywlx;
    /**
     * 收费人员编码
     */
    private String sfrybm;
    /**
     * 收费人员姓名
     */
    private String sfryxm;
    /**
     * 发票号
     */
    private String fph;
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

