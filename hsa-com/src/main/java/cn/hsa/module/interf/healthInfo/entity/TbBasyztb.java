package cn.hsa.module.interf.healthInfo.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 住院病案首页主体表实体类(TbBasyztb)
 * @author liuliyun
 * @since 2022-05-17 14:57:36
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbBasyztb extends PageDO implements Serializable {
    private static final long serialVersionUID = 734110862197271974L;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 住院号
     */
    private String zyh;
    /**
     * 住院次数
     */
    private Integer zycs;
    /**
     * 医疗付费方式代码
     */
    private String ylfffs;
    /**
     * 病房号
     */
    private String bfh;
    /**
     * 床号
     */
    private String ch;
    /**
     * 病案号
     */
    private String bah;
    /**
     * 入院病区（房）
     */
    private String rybq;
    /**
     * 出院病区（房）
     */
    private String cybq;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 出生日期
     */
    private String csny;
    /**
     * 年龄（岁）
     */
    private Integer nls;
    /**
     * 年龄（月）
     */
    private Integer nly;
    /**
     * 身份证号码
     */
    private String sfzhm;
    /**
     * 社保卡号码
     */
    private String sbh;
    /**
     * 新生儿出生体重
     */
    private String xsecstz;
    /**
     * 新生儿入院体重
     */
    private String xserytz;
    /**
     * 婚姻状况代码
     */
    private String hyzkdm;
    /**
     * 民族代码
     */
    private String mzdm;
    /**
     * ABO血型代码
     */
    private String aboxxdm;
    /**
     * ABO血型名称
     */
    private String aboxxmc;
    /**
     * Rh血型代码
     */
    private String xxdm;
    /**
     * Rh血型名称
     */
    private String rhxxmc;
    /**
     * 国籍
     */
    private String gj;
    /**
     * 职业代码
     */
    private String zydm;
    /**
     * 职业名称
     */
    private String zymc;
    /**
     * 出生地-省（自治区、直辖市）
     */
    private String csds;
    /**
     * 出生地-市（地区、州）
     */
    private String csdsz;
    /**
     * 出生地-县（区）
     */
    private String csdx;
    /**
     * 籍贯-省（自治区、直辖市）
     */
    private String jgs;
    /**
     * 籍贯-市（地区、州）
     */
    private String jgsz;
    /**
     * 身份证件类别代码
     */
    private String sfzjlbdm;
    /**
     * 现住址-省（自治区、直辖市）
     */
    private String xzzs;
    /**
     * 现住址-市（地区、州）
     */
    private String xzzsz;
    /**
     * 现住址-县（区）
     */
    private String xzzx;
    /**
     * 现住址-乡（镇、街道办事处）
     */
    private String xzzxz;
    /**
     * 现住址-村（街、路、弄等）
     */
    private String xjzc;
    /**
     * 现住址-门牌号码
     */
    private String xjzmphm;
    /**
     * 现住址电话
     */
    private String xzzdh;
    /**
     * 现住址邮编
     */
    private String xzzyb;
    /**
     * 户口地址-省（自治区、直辖市）
     */
    private String hkdzs;
    /**
     * 户口地址-市（地区、州）
     */
    private String hkdzsz;
    /**
     * 户口地址-县（区）
     */
    private String hjdzxq;
    /**
     * 户口地址-乡（镇、街道办事处）
     */
    private String hjdzxz;
    /**
     * 户口地址-村（街、路、弄等）
     */
    private String hjdzc;
    /**
     * 户口地址-门牌号码
     */
    private String hjdzmphm;
    /**
     * 户口地址-邮政编码
     */
    private String hjdzyzbm;
    /**
     * 工作单位地址-省（自治区、直辖市）
     */
    private String gzdwdzs;
    /**
     * 工作单位地址-市（地区、州）
     */
    private String gzdwdzsz;
    /**
     * 工作单位地址-县（区）
     */
    private String gzdwdzxq;
    /**
     * 工作单位地址-乡（镇、街道办事处）
     */
    private String gzdwdzxz;
    /**
     * 工作单位地址-村（街、路、弄等）
     */
    private String gzdwdzc;
    /**
     * 工作单位地址-门牌号码
     */
    private String gzdwdzmphm;
    /**
     * 工作单位详细地址
     */
    private String gzdwxxdz;
    /**
     * 工作单位
     */
    private String gzdw;
    /**
     * 工作单位电话
     */
    private String gzdwdh;
    /**
     * 工作单位邮编
     */
    private String gzdwyb;
    /**
     * 联系人地址-省（自治区、直辖市）
     */
    private String lxrdzs;
    /**
     * 联系人地址-市（地区、州）
     */
    private String lxrdzsz;
    /**
     * 联系人地址-县（区）
     */
    private String lxrdzxq;
    /**
     * 联系人地址-乡（镇、街道办事处）
     */
    private String lxrdzxz;
    /**
     * 联系人地址-村（街、路、弄等）
     */
    private String lxrdzc;
    /**
     * 联系人地址-门牌号码
     */
    private String lxrdzmphm;
    /**
     * 入院时间
     */
    private Date rysj;
    /**
     * 入院途径代码
     */
    private String rytjdm;
    /**
     * 入院途径名称
     */
    private String rytjmc;
    /**
     * 入院科室编码
     */
    private String ryksbm;
    /**
     * 实际住院天数
     */
    private Integer sjzyts;
    /**
     * 出院方式
     */
    private String cyfs;
    /**
     * 入院时情况
     */
    private String ryqk;
    /**
     * 出院科室编码
     */
    private String cyksbm;
    /**
     * 出院时间
     */
    private Date cysj;
    /**
     * 保险类型
     */
    private String bxlx;
    /**
     * 健康卡号
     */
    private String jkkh;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 居住地（现住址）
     */
    private String jzd;
    /**
     * 联系人姓名
     */
    private String lxrxm;
    /**
     * 联系人关系代码
     */
    private String lxrgxdm;
    /**
     * 联系人关系名称
     */
    private String lxrgxmc;
    /**
     * 联系人地址
     */
    private String lxrdz;
    /**
     * 联系人电话
     */
    private String lxrdh;
    /**
     * 联系人通信地址
     */
    private String lxrtxdz;
    /**
     * 转科科室编码1
     */
    private String zkksbm1;
    /**
     * 转科科室编码2
     */
    private String zkksbm2;
    /**
     * 转科科室编码3
     */
    private String zkksbm3;
    /**
     * 所转病区
     */
    private String szbq;
    /**
     * 入院前经外院诊治
     */
    private String ryqwyzz;
    /**
     * 确诊日期
     */
    private Date qzrq;
    /**
     * 门（急诊）诊断
     */
    private String mjzzd;
    /**
     * 门（急诊）诊断疾病编码
     */
    private String mjzjbbm;
    /**
     * 门（急诊）诊断疾病名称
     */
    private String mjzjbmc;
    /**
     * 损伤中毒的外部因素
     */
    private String sszd;
    /**
     * 损伤中毒的外部原因的疾病编码
     */
    private String sszdbm;
    /**
     * 抢救次数
     */
    private BigDecimal qjcs;
    /**
     * 成功次数
     */
    private BigDecimal cgcs;
    /**
     * 住院是否出现危重、急症、疑难
     */
    private String sfcxwjn;
    /**
     * 有传染病报告
     */
    private String crbbg;
    /**
     * 有肿瘤报告
     */
    private String zlbg;
    /**
     * 有新生儿死亡报告
     */
    private String xsebg;
    /**
     * 孕产妇死亡报告
     */
    private String swbg;
    /**
     * 有其它报告
     */
    private String qtbg;
    /**
     * 是否随诊
     */
    private String sz;
    /**
     * 是否示教病例
     */
    private String sjbl;
    /**
     * （死亡患者）是否尸检
     */
    private String sj;
    /**
     * 是否妊娠梅毒筛查
     */
    private String rsmdsc;
    /**
     * 新生儿疾病筛查
     */
    private String xsejbsc;
    /**
     * 产后出血量
     */
    private Double chcyl;
    /**
     * 新生儿性别
     */
    private String xseXb;
    /**
     * 主任医师编码
     */
    private String zrysbm;
    /**
     * 主任医师姓名
     */
    private String zrysxm;
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
     * 护士长编码
     */
    private String hszbm;
    /**
     * 护士长姓名
     */
    private String hszxm;
    /**
     * 责任护士编码
     */
    private String zrhsbm;
    /**
     * 责任护士姓名
     */
    private String zrhsxm;
    /**
     * 进修医师编码
     */
    private String jxysbm;
    /**
     * 进修医师姓名
     */
    private String jxysxm;
    /**
     * 实习医师编码
     */
    private String sxysbm;
    /**
     * 实习医师姓名
     */
    private String sxysxm;
    /**
     * 病案质量
     */
    private String bazl;
    /**
     * 质控医师编码
     */
    private String zkysbm;
    /**
     * 质控医师姓名
     */
    private String zkysxm;
    /**
     * 质控护士编码
     */
    private String zkhsbm;
    /**
     * 质控护士姓名
     */
    private String zkhszm;
    /**
     * 质控日期
     */
    private Date zkrq;
    /**
     * 病理号
     */
    private String blh;
    /**
     * 死亡根本原因
     */
    private String swgbyy;
    /**
     * 死亡时间
     */
    private Date swsj;
    /**
     * 门诊医师编码
     */
    private String mzysbm;
    /**
     * 门诊医师姓名
     */
    private String mzysxm;
    /**
     * 输液反应
     */
    private String syfy;
    /**
     * 是否为科研病案
     */
    private String sfkyba;
    /**
     * 离院方式代码
     */
    private String lyfsdm;
    /**
     * 离院后拟接收医疗机构名称
     */
    private String njsyymc;
    /**
     * 是否有出院31天内再住院计划
     */
    private String zzyjh31;
    /**
     * 颅脑损伤患者入院前昏迷时间
     */
    private String ryqhmsj;
    /**
     * 颅脑损伤患者入院后昏迷时间
     */
    private String ryhhmsj;
    /**
     * 住院费
     */
    private Double zyf;
    /**
     * 诊疗费
     */
    private Double zlf;
    /**
     * 治疗费
     */
    private Double zhf;
    /**
     * 护理费
     */
    private Double hlf;
    /**
     * 手术费
     */
    private Double ssclf;
    /**
     * 检查费
     */
    private Double jcf;
    /**
     * 化验费
     */
    private Double hyf;
    /**
     * 透视费
     */
    private Double tsf;
    /**
     * 摄片费
     */
    private Double spf;
    /**
     * 输血费
     */
    private Double sxf;
    /**
     * 输氧费
     */
    private Double syf;
    /**
     * 西药费
     */
    private Double xyf;
    /**
     * 中成药费
     */
    private Double zcyf;
    /**
     * 中草药费
     */
    private Double zcaf;
    /**
     * 其他费用
     */
    private Double qtf;
    /**
     * 总费用
     */
    private Double zfy;
    /**
     * 自付金额
     */
    private Double zfje;
    /**
     * 一般医疗服务费
     */
    private Double ylfwf;
    /**
     * 一般治疗操作费
     */
    private Double zlczf;
    /**
     * 病理诊断费
     */
    private Double blzdf;
    /**
     * 实验室诊断费
     */
    private Double syszdf;
    /**
     * 影像学诊断费
     */
    private Double yxxzdf;
    /**
     * 临床诊断项目费
     */
    private Double lczdxmf;
    /**
     * 非手术治疗项目费
     */
    private Double fsszlxmf;
    /**
     * 临床物理治疗费
     */
    private Double lcwlzlf;
    /**
     * 手术治疗费
     */
    private Double sszlf;
    /**
     * 麻醉费
     */
    private Double mzf;
    /**
     * 手术费
     */
    private Double ssf;
    /**
     * 康复费
     */
    private Double kff;
    /**
     * 中医治疗费
     */
    private Double zyzlf;
    /**
     * 抗菌药物费用
     */
    private Double kjywfy;
    /**
     * 血费
     */
    private Double xf;
    /**
     * 白蛋白类制品费
     */
    private Double bdbzpf;
    /**
     * 球蛋白类制品费
     */
    private Double qdbzpf;
    /**
     * 凝血因子类制品费
     */
    private Double nxyzzpf;
    /**
     * 细胞因子类制品费
     */
    private Double xbyzzpf;
    /**
     * 检查用一次性医用材料费
     */
    private Double jcyyclf;
    /**
     * 治疗用一次性医用材料费
     */
    private Double zlyyclf;
    /**
     * 手术用一次性医用材料费
     */
    private Double ssyyclf;
    /**
     * 其他费
     */
    private Double qtf1;
    /**
     * 治疗类别代码
     */
    private String zllbdm;
    /**
     * 实施临床路径标志代码
     */
    private String sslcljbzdm;
    /**
     * 使用医疗机构中药制剂标志
     */
    private String yljgzyzjbz;
    /**
     * 使用中医诊疗设备标志
     */
    private String syzyzlsbbz;
    /**
     * 使用中医诊疗技术标志
     */
    private String syzyzljsbz;
    /**
     * 辨证施护标志
     */
    private String bzshbz;
    /**
     * 药物过敏标志
     */
    private String ywgmbz;
    /**
     * 过敏药物
     */
    private String gmyw;
    /**
     * 拟接收医疗机构名称
     */
    private String njsyljgmc;
    /**
     * 联系人与患者的关系代码
     */
    private String lxryhzgxdm;
    /**
     * 病例分型
     */
    private String blfx;
    /**
     * 实施重症监护
     */
    private String sszzjh;
    /**
     * 监护总时间（天）
     */
    private String jhzsjt;
    /**
     * 监护总时间（小时）
     */
    private String jhzsjxs;
    /**
     * 单病种管理
     */
    private String dbzgl;
    /**
     * 实施临床路径管理
     */
    private String sslcljgl;
    /**
     * 实施DRGS管理
     */
    private String ssdrgsgl;
    /**
     * 是否使用抗生素
     */
    private String sfsykss;
    /**
     * 细菌培养标本送检
     */
    private String xjpybbsj;
    /**
     * 法定传染病
     */
    private String fdcrb;
    /**
     * 肿瘤TNM分期
     */
    private String zlfq;
    /**
     * 肿瘤分期代码
     */
    private String zlfqdm;
    /**
     * 新生儿Apgar评分
     */
    private String xseagpf;
    /**
     * 病历类型
     */
    private String bllx;
    /**
     * 治疗类别名称
     */
    private String zllbmc;
    /**
     * 入院科室名称
     */
    private String ryksmc;
    /**
     * 出院科室名称
     */
    private String cyksmc;
    /**
     * 实施临床路径标志名称
     */
    private String sslcljbzmc;
    /**
     * 损伤中毒的外部原因
     */
    private String sszddwbyy;
    /**
     * 损伤中毒疾病编码
     */
    private String sszdjbbm;
    /**
     * 损伤中毒疾病名称
     */
    private String sszdjbmc;
    /**
     * 死亡患者尸检标志
     */
    private String swhzsjbz;
    /**
     * 主任（副主任）医师签名
     */
    private String zrysqm;
    /**
     * 离院方式名称
     */
    private String lyfsmc;
    /**
     * 出院31天内再住院目的
     */
    private String cy31tnzzymd;
    /**
     * 综合医疗服务费-一般医疗服务费-中医辨证论治费
     */
    private Double zybzlzf;
    /**
     * 综合医疗服务费-一般医疗服务费-中医辨证论治会诊费
     */
    private Double zybzlhzf;
    /**
     * 中医类-中医诊断费
     */
    private Double zyzdf;
    /**
     * 中医类-中医治疗费-中医外治费
     */
    private Double zywzf;
    /**
     * 中医类-中医治疗费-中医骨伤费
     */
    private Double gsf;
    /**
     * 中医类-中医治疗费-针刺与灸法费
     */
    private Double zcyjff;
    /**
     * 中医类-中医治疗费-中医推拿治疗费
     */
    private Double zytnzlf;
    /**
     * 中医类-中医治疗费-中医肛肠治疗费
     */
    private Double zygczlf;
    /**
     * 中医类-中医治疗费-中医特殊治疗费
     */
    private Double zytxzlf;
    /**
     * 中医类-中医其他费
     */
    private Double zyqtf;
    /**
     * 中医类-中医其他费-中药特殊调配加工
     */
    private Double zytxtpjg;
    /**
     * 中医类-中医其他费-辨证施膳费
     */
    private BigDecimal blssf;
    /**
     * 中药类-中成药费-医疗机构中药制剂费
     */
    private Double yljgzyzjf;
    /**
     * 病情转归代码
     */
    private String bqzgdm;
    /**
     * 病情转归名称
     */
    private String bqzgmc;
    /**
     * 传染病标识
     */
    private String crbbs;
    /**
     * 传染病报告上报标识
     */
    private String crbbgsbbs;
    /**
     * 是否再入院
     */
    private String sfzry;
    /**
     * 是否ICU转出重症患者
     */
    private String sficuzczzhz;
    /**
     * 是否48H重返ICU
     */
    private String sf48hcficu;
    /**
     * 是否ICU危及患者安全事件发生
     */
    private String sficuwjhzaqsjfs;
    /**
     * 住院期间是否相关之情告知
     */
    private String zyqjsfxgzqgz;
    /**
     * 是否辩证使用中成药
     */
    private String sfbzsyzcy;
    /**
     * 妊娠出血
     */
    private String rscx;
    /**
     * 科主任姓名
     */
    private String kzrxm;
    /**
     * 科主任编码
     */
    private String kzrgh;
    /**
     * 主诊医师编号
     */
    private String zzdysgh;
    /**
     * 主诊医师姓名
     */
    private String zzdysxm;
    /**
     * 抗菌药物使用情况
     */
    private String kjywsyqk;
    /**
     * 引发反应的药物
     */
    private String yffyyw;
    /**
     * 输液反应临床表现
     */
    private String syfylcbx;
    /**
     * 住院有无跌倒或坠床及伤害程度
     */
    private String zyddhzcsh;
    /**
     * 跌倒或坠床原因
     */
    private String ddhzcyy;
    /**
     * 出院情况编码
     */
    private String cyqkbm;
    /**
     * 感染部位
     */
    private String grbw;
    /**
     * 颅脑损伤患者入院前昏迷时间-d
     */
    private String ryqhmsjd;
    /**
     * 颅脑损伤患者入院前昏迷时间-h
     */
    private String ryqhmsjh;
    /**
     * 颅脑损伤患者入院前昏迷时间-min
     */
    private String ryqhmsjm;
    /**
     * 颅脑损伤患者入院后昏迷时间-d
     */
    private String ryhhmsjd;
    /**
     * 颅脑损伤患者入院后昏迷时间-h
     */
    private String ryhhmsjh;
    /**
     * 颅脑损伤患者入院后昏迷时间-min
     */
    private String ryhhmsjm;
    /**
     * 中医骨伤费
     */
    private Double zygsf;
    /**
     * 病理诊断
     */
    private String blzd;
    /**
     * 病理诊断疾病编码
     */
    private String blzdjbbm;
    /**
     * 病理诊断疾病名称
     */
    private String blzdjbmc;
    /**
     * 辩证施膳
     */
    private Double bzss;
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
