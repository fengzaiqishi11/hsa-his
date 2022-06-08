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
 * 出院小结(TbEmrCyxj)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbEmrCyxj implements Serializable {
    private static final long serialVersionUID = -68519131673261815L;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 科室编码
     */
    private String ksbm;
    /**
     * 科室名称
     */
    private String ksmc;
    /**
     * 床号
     */
    private String ch;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 性别代码
     */
    private String xb;
    /**
     * 年龄
     */
    private String nl;
    /**
     * 住院天数
     */
    private String zyts;
    /**
     * 门诊诊断
     */
    private String mzzd;
    /**
     * 入院诊断
     */
    private String ryzd;
    /**
     * 出院诊断
     */
    private String cyzd;
    /**
     * 入院时主要症状及体征
     */
    private String ryzztz;
    /**
     * 实验室检查及主要会诊
     */
    private String jchz;
    /**
     * 住院期间特殊检查
     */
    private String tsjc;
    /**
     * 诊疗过程
     */
    private String zlgc;
    /**
     * 合并症
     */
    private String hbz;
    /**
     * 出院时情况
     */
    private String cyqk;
    /**
     * 出院医嘱
     */
    private String cyyz;
    /**
     * 治疗结果
     */
    private String zljg;
    /**
     * 主治医师编码
     */
    private String zzysbm;
    /**
     * 主治医师姓名
     */
    private String zzysxm;
    /**
     * 住院医师编码
     */
    private String zyysbm;
    /**
     * 住院医师姓名
     */
    private String zyysxm;
    /**
     * 医院自填报内容1
     */
    private String yyztb1;
    /**
     * 医院自填报内容2
     */
    private String yyztb2;
    /**
     * 病区名称
     */
    private String bqmc;
    /**
     * 病房号
     */
    private String bfh;
    /**
     * 职业类别代码
     */
    private String zylbdm;
    /**
     * 患者电话号码
     */
    private String hzdhhm;
    /**
     * 麻醉方法代码
     */
    private String mzffdm;
    /**
     * 出院时症状与体征
     */
    private String cyszzytz;
    /**
     * 病情转归代码
     */
    private String bqzgdm;
    /**
     * 是否有中医“四诊”
     */
    private String sfyzysz;
    /**
     * 是否有手术明细
     */
    private String sfyssjl;
    /**
     * 居民健康卡号
     */
    private String jmjkdabm;
    /**
     * 年龄（岁）
     */
    private Integer nls;
    /**
     * 年龄（月）
     */
    private String nly;
    /**
     * 婚姻状况代码
     */
    private String hyzkdm;
    /**
     * 地址类别代码
     */
    private String dzlbdm;
    /**
     * 地址-省（自治区、直辖市）
     */
    private String dzs;
    /**
     * 地址-市（地区、州）
     */
    private String dzsq;
    /**
     * 地址-县（区）
     */
    private String dzx;
    /**
     * 地址-乡（镇、街道办事处）
     */
    private String dzxz;
    /**
     * 地址-村（街、路、弄等）
     */
    private String dzc;
    /**
     * 地址-门牌号码
     */
    private String dzmph;
    /**
     * 邮政编码
     */
    private String yb;
    /**
     * 联系人姓名
     */
    private String lxrxm;
    /**
     * 联系人电话号码
     */
    private String lxrdhhm;
    /**
     * 入院日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ryrq;
    /**
     * 出院日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cyrq;
    /**
     * 入院情况
     */
    private String ryqk;
    /**
     * 阳性辅助检查结果
     */
    private String yxfzjjg;
    /**
     * 中医“四诊”观察结果
     */
    private String zyszgcjg;
    /**
     * 入院诊断-西医诊断编码
     */
    private String ryzdXyzdbm;
    /**
     * 入院诊断-中医病名代码
     */
    private String ryzdZybmdm;
    /**
     * 入院诊断-中医证候代码
     */
    private String ryzdZyhzdm;
    /**
     * 出院诊断-西医诊断编码
     */
    private String cyzdXyzdbm;
    /**
     * 出院诊断-中医病名代码
     */
    private String cyzdZybmdm;
    /**
     * 出院诊断-中医证候代码
     */
    private String cyzdZyhzdm;
    /**
     * 手术切口类别代码
     */
    private String ssqklbdm;
    /**
     * 切口愈合等级代码
     */
    private String qkyhdjdm;
    /**
     * 手术及操作编码
     */
    private String ssjczbm;
    /**
     * 手术及操作开始日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ssczrq;
    /**
     * 手术过程
     */
    private String ssgc;
    /**
     * 治则治法
     */
    private String zzfz;
    /**
     * 中药煎煮方法
     */
    private String zyjzff;
    /**
     * 中药用药方法
     */
    private String zyyyff;
    /**
     * 诊疗过程描述
     */
    private String zlgcms;
    /**
     * 治疗结果代码
     */
    private String zljgdm;
    /**
     * 住院医师签名
     */
    private String zyysqm;
    /**
     * 上级医师签名
     */
    private String sjysqm;
    /**
     * 签名日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qmrqsj;
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

