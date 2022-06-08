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
 * 门诊收费明细表(TbMzsfmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbMzsfmx implements Serializable {
    private static final long serialVersionUID = -29105879000535059L;
    /**
     * 收费明细ID
     */
    private String sfmxid;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 是否退费
     */
    private String tfbz;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 门诊收费结算单号
     */
    private String mzsfjsdh;
    /**
     * 门诊就诊流水号
     */
    private String mzlsh;
    /**
     * 处方号
     */
    private String cfh;
    /**
     * 是否药品标识
     */
    private String sfypbz;
    /**
     * 明细费用类别
     */
    private String mxfylb;
    /**
     * 收费/退费时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stfsj;
    /**
     * 明细项目编码
     */
    private String mxxmbm;
    /**
     * 明细项目名称
     */
    private String mxxmmc;
    /**
     * 明细项目单位
     */
    private String mxxmdw;
    /**
     * 明细项目单价
     */
    private BigDecimal mxxmdj;
    /**
     * 明细项目数量
     */
    private BigDecimal mxxmsl;
    /**
     * 明细项目金额
     */
    private BigDecimal mxxmje;
    /**
     * 收费科室代码
     */
    private String sfksdm;
    /**
     * 收费科室名称
     */
    private String sfksmc;
    /**
     * 收费人员编码
     */
    private String sfrybm;
    /**
     * 收费人员姓名
     */
    private String sfryxm;
    /**
     * 开方医生编码
     */
    private String kfysbm;
    /**
     * 开方医生姓名
     */
    private String kfysxm;
    /**
     * 开方时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kfsj;
    /**
     * 开方科室代码
     */
    private String kfksdm;
    /**
     * 开方科室名称
     */
    private String kfksmc;
    /**
     * 执行科室代码
     */
    private String zxksdm;
    /**
     * 执行科室名称
     */
    private String zxksmc;
    /**
     * 发药人编码
     */
    private String fyrbm;
    /**
     * 发药人姓名
     */
    private String fyrxm;
    /**
     * 发药时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fysj;
    /**
     * 发药标志
     */
    private String fybz;
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

