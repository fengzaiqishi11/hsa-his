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
 * 护理三测单记录(TbHlscdjl)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbHlscdjl implements Serializable {
    private static final long serialVersionUID = 567137199590458271L;
    /**
     * 护理三测单记录明细序号
     */
    private String scdjlmxxh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 住院流水号
     */
    private String zylsh;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 患者姓名
     */
    private String xm;
    /**
     * 性别代码
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
     * 住院科室代码
     */
    private String zyksdm;
    /**
     * 住院科室名称
     */
    private String zyksmc;
    /**
     * 病区名称
     */
    private String bqmc;
    /**
     * 病房号
     */
    private String bah;
    /**
     * 病床号
     */
    private String bch;
    /**
     * 入院日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ryrqsj;
    /**
     * 分娩日数
     */
    private String fmrs;
    /**
     * 护理记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hljlsj;
    /**
     * 住院日数
     */
    private Integer zyrs;
    /**
     * 手术日数
     */
    private Integer ssrs;
    /**
     * 体温
     */
    private BigDecimal tw;
    /**
     * 脉搏
     */
    private BigDecimal mb;
    /**
     * 呼吸值
     */
    private String hxz;
    /**
     * 血压/低压
     */
    private String xyDy;
    /**
     * 血压/高压
     */
    private String xyGy;
    /**
     * 入量
     */
    private String rl;
    /**
     * 出量
     */
    private String cl;
    /**
     * 小便
     */
    private String xb;
    /**
     * 大便
     */
    private String db;
    /**
     * 体重
     */
    private String tz;
    /**
     * 尿量ml
     */
    private String niaol;
    /**
     * 身高cm
     */
    private String sg;
    /**
     * 40度以上体温栏内容
     */
    private String twnr;
    /**
     * 药品过敏
     */
    private String ypgmmc;
    /**
     * 护理护士编码
     */
    private String hlhsbm;
    /**
     * 体温测量仪表
     */
    private String twclyb;
    /**
     * 体温再次测量值
     */
    private BigDecimal twzcclz;
    /**
     * 心率
     */
    private BigDecimal xl;
    /**
     * 婴儿序号
     */
    private String yexh;
    /**
     * 当日是否手术
     */
    private String drsfss;
    /**
     * 记录是否没测
     */
    private String sfcl;
    /**
     * 血压复测值/高压
     */
    private String xyfcGy;
    /**
     * 血压复测值/低压
     */
    private String xyfcDy;
    /**
     * 是否使用呼吸机
     */
    private String sfsyhxj;
    /**
     * 其他内容备注录入
     */
    private String qtbz;
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

