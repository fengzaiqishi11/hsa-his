package cn.hsa.module.upload.healthreport.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 病案首页直报DO
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-30  19:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZybasyDO  extends PageDO implements Serializable {

    private String username;//varchar2(60)	n	机构名称
    private String ylfkfs;//varchar2(100)	y	医疗付款方式
    private String jkkh;//varchar2(100)	y	健康卡号
    private String zycs;//varchar2(100)	n	住院次数
    private String bah;//varchar2(100)	n	病案号
    private String xm;//varchar2(100)	y	姓名
    private String xb;//varchar2(100)	y	性别
    private String csrq;//varchar2(12)	y	出生日期
    private String nl;//number(10)	n	年龄
    private String gj;//varchar2(100)	y	国籍
    private String bzyzsnl;//number(4)	y	(年龄不足1周岁的)年龄(月)
    private String xsecstz;//number(12,2)	y	新生儿出生体重(克)
    private String xserytz;//number(12,2)	y	新生儿入院体重(克）
    private String csd;//varchar2(200)	y	出生地
    private String gg;//varchar2(200)	y	籍贯
    private String mz;//varchar2(100)	y	民族
    private String sfzh;//varchar2(100)	y	身份证号
    private String zy;//varchar2(100)	n	职业
    private String hy;//varchar2(100)	y	婚姻
    private String xzz;//varchar2(100)	y	现住址
    private String dh;//varchar2(100)	y	电话
    private String yb1;//varchar2(100)	y	邮编
    private String hkdz;//varchar2(100)	y	户口地址
    private String yb2;//varchar2(100)	y	邮编
    private String gzdwjdz;//varchar2(100)	y	工作单位及地址
    private String dwdh;//varchar2(100)	y	单位电话
    private String yb3;//varchar2(100)	y	邮编
    private String lxrxm;//varchar2(100)	y	联系人姓名
    private String gx;//varchar2(100)	y	关系
    private String dz;//varchar2(100)	y	地址
    private String dh2;//varchar2(100)	y	电话
    private String rytj;//varchar2(100)	y	入院途径
    private String rysj;//varchar2(12)	n	入院时间
    private String rysjs;//number(24)	y	时
    private String rykb;//varchar2(100)	y	入院科别
    private String rybf;//varchar2(100)	y	入院病房
    private String zkkb;//varchar2(100)	y	转科科别
    private String cysj;//varchar2(12)	n	出院时间
    private String cysjs;//number(24)	y	时
    private String cykb;//varchar2(100)	y	出院科别
    private String cybf;//varchar2(100)	y	出院病房
    private String sjzyts;//varchar2(100)	y	实际住院(天)
    private String mzzd;//varchar2(200)	y	门(急)诊诊断
    private String jbbm;//varchar2(100)	y	疾病编码
    private String zyzd;//varchar2(200)	n	主要诊断
    private String jbdm;//varchar2(100)	n	疾病编码
    private String rybq;//varchar2(100)	y	入院病情
    private String qtzd8;//varchar2(200)	y	其他诊断
    private String jbdm8;//varchar2(100)	y	疾病编码
    private String rybq8;//varchar2(100)	y	入院病情
    private String qtzd1;//varchar2(200)	y	其他诊断
    private String jbdm1;//varchar2(100)	y	疾病编码
    private String rybq1;//varchar2(100)	y	入院病情
    private String qtzd9;//varchar2(200)	y	其他诊断
    private String jbdm9;//varchar2(100)	y	疾病编码
    private String rybq9;//varchar2(100)	y	入院病情
    private String qtzd2;//varchar2(200)	y	其他诊断
    private String jbdm2;//varchar2(100)	y	疾病编码
    private String rybq2;//varchar2(100)	y	入院病情
    private String qtzd10;//varchar2(200)	y	其他诊断
    private String jbdm10;//varchar2(100)	y	疾病编码
    private String rybq10;//varchar2(100)	y	入院病情
    private String qtzd3;//varchar2(200)	y	其他诊断
    private String jbdm3;//varchar2(100)	y	疾病编码
    private String rybq3;//varchar2(100)	y	入院病情
    private String qtzd11;//varchar2(200)	y	其他诊断
    private String jbdm11;//varchar2(100)	y	疾病编码
    private String rybq11;//varchar2(100)	y	入院病情
    private String qtzd4;//varchar2(200)	y	其他诊断
    private String rybq4;//varchar2(100)	y	入院病情
    private String qtzd12;//varchar2(200)	y	其他诊断
    private String jbdm12;//varchar2(100)	y	疾病编码
    private String rybq12;//varchar2(100)	y	入院病情
    private String qtzd5;//varchar2(200)	y	其他诊断
    private String jbdm5;//varchar2(100)	y	疾病编码
    private String rybq5;//varchar2(100)	y	入院病情
    private String qtzd13;//varchar2(200)	y	其他诊断
    private String jbdm13;//varchar2(100)	y	疾病编码
    private String rybq13;//varchar2(100)	y	入院病情
    private String qtzd6;//varchar2(200)	y	其他诊断
    private String jbdm6;//varchar2(100)	y	疾病编码
    private String rybq6;//varchar2(100)	y	入院病情
    private String qtzd14;//varchar2(200)	y	其他诊断
    private String jbdm14;//varchar2(100)	y	疾病编码
    private String rybq14;//varchar2(100)	y	入院病情
    private String qtzd7;//varchar2(200)	y	其他诊断
    private String jbdm7;//varchar2(100)	y	疾病编码
    private String rybq7;//varchar2(100)	y	入院病情
    private String qtzd15;//varchar2(200)	y	其他诊断
    private String jbdm15;//varchar2(100)	y	疾病编码
    private String rybq15;//varchar2(100)	y	入院病情
    private String wbyy;//varchar2(254)	y	中毒的外部原因
    private String h23;//varchar2(100)	y	疾病编码
    private String blzd;//varchar2(100)	y	病理诊断出
    private String jbmm;//varchar2(100)	y	疾病编码
    private String blh;//varchar2(100)	y	病理号
    private String ywgm;//varchar2(100)	y	药物过敏
    private String gmyw;//varchar2(254)	y	过敏药物疾病
    private String swhzsj;//varchar2(100)	y	死亡患者尸检
    private String xx;//varchar2(100)	y	血型
    private String rh;//varchar2(100)	y	rh
    private String kzr;//varchar2(100)	y	科主任
    private String zrys;//varchar2(100)	y	主任（副主任）医师
    private String zzys;//varchar2(100)	y	主治医师
    private String zyys;//varchar2(100)	y	住院医师
    private String zrhs;//varchar2(100)	y	责任护士
    private String jxys;//varchar2(100)	y	进修医师住
    private String sxys;//varchar2(100)	y	实习医师
    private String bmy;//varchar2(100)	y	编码员
    private String bazl;//varchar2(100)	y	病案质量
    private String zkys;//varchar2(100)	y	质控医师
    private String zkhs;//varchar2(100)	y	质控护士
    private String zkrq;//varchar2(12)	y	质控日期
    private String ssjczbm1;//varchar2(100)	y	手术及操作编码
    private String ssjczrq1;//varchar2(12)	y	手术及操作日期
    private String ssjb1;//varchar2(100)	y	手术级别
    private String ssjczmc1;//varchar2(200)	y	手术及操作名称
    private String sz1;//varchar2(100)	y	术者
    private String yz1;//varchar2(100)	y	i助
    private String ez1;//varchar2(100)	y	ii助
    private String qkdj1;//varchar2(100)	y	切口等级
    private String qkyhlb1;//varchar2(100)	y	切口愈合类别
    private String mzfs1;//varchar2(100)	y	麻醉方式
    private String mzys1;//varchar2(100)	y	麻醉医师
    private String ssjczbm2;//varchar2(100)	y	手术及操作编码
    private String ssjczrq2;//varchar2(12)	y	手术及操作日期
    private String ssjb2;//varchar2(100)	y	手术级别
    private String ssjczmc2;//varchar2(200)	y	手术及操作名称
    private String sz2;//varchar2(100)	y	术者
    private String yz2;//varchar2(100)	y	i助
    private String ez2;//varchar2(100)	y	ii助
    private String qkdj2;//varchar2(100)	y	切口等级
    private String qkyhlb2;//varchar2(100)	y	切口愈合类别
    private String mzfs2;//varchar2(100)	y	麻醉方式
    private String mzys2;//varchar2(100)	y	麻醉医师
    private String ssjczbm3;//varchar2(100)	y	手术及操作编码
    private String ssjczrq3;//varchar2(12)	y	手术及操作日期
    private String ssjb3;//varchar2(100)	y	手术级别
    private String ssjczmc3;//varchar2(200)	y	手术及操作名称
    private String sz3;//varchar2(100)	y	术者
    private String yz3;//varchar2(100)	y	i助
    private String ez3;//varchar2(100)	y	ii助
    private String qkdj3;//varchar2(100)	y	切口等级
    private String qkyhlb3;//	varchar2(100)	y	切口愈合类别
    private String mzfs3;//varchar2(100)	y	麻醉方式
    private String mzys3;//varchar2(100)	y	麻醉医师
    private String ssjczbm4;//varchar2(100)	y	手术及操作编码
    private String ssjczrq4;//varchar2(12)	y	手术及操作日期
    private String ssjb4;//varchar2(100)	y	手术级别
    private String ssjczmc4;//varchar2(200)	y	手术及操作名称
    private String sz4;//varchar2(100)	y	术者
    private String yz4;//varchar2(100)	y	i助
    private String ez4;//varchar2(100)	y	ii助
    private String qkdj4;//varchar2(100)	y	切口等级
    private String qkyhlb4;//varchar2(100)	y	切口愈合类别
    private String mzfs4;//varchar2(100)	y	麻醉方式
    private String mzys4;//varchar2(100)	y	情况麻醉医师
    private String ssjczbm5;//varchar2(100)	y	手术及操作编码
    private String ssjczrq5;//varchar2(12)	y	手术及操作日期
    private String ssjb5;//varchar2(100)	y	手术级别
    private String ssjczmc5;//varchar2(200)	y	手术及操作名称
    private String sz5;//varchar2(100)	y	术者
    private String yz5;//varchar2(100)	y	i助
    private String ez5;//varchar2(100)	y	ii助
    private String qkdj5;//varchar2(100)	y	切口等级
    private String qkyhlb5;//varchar2(100)	y	切口愈合类别
    private String mzfs5;//varchar2(100)	y	麻醉方式
    private String mzys5;//varchar2(100)	y	麻醉医师
    private String ssjczbm6;//varchar2(100)	y	手术及操作编码
    private String ssjczrq6;//varchar2(12)	y	手术及操作日期
    private String ssjb6;//varchar2(100)	y	手术级别
    private String ssjczmc6;//varchar2(200)	y	手术及操作名称
    private String sz6;//varchar2(100)	y	术者
    private String yz6;//varchar2(100)	y	i助
    private String ez6;//varchar2(100)	y	ii助
    private String qkdj6;//varchar2(100)	y	切口等级
    private String qkyhlb6;//varchar2(100)	y	切口愈合类别
    private String mzfs6;//varchar2(100)	y	麻醉方式
    private String mzys6;//varchar2(100)	y	麻醉医师
    private String ssjczbm7;//varchar2(100)	y	手术及操作编码
    private String ssjczrq7;//varchar2(12)	y	手术及操作日期
    private String ssjb7;//varchar2(100)	y	手术级别
    private String ssjczmc7;//varchar2(200)	y	手术及操作名称
    private String sz7;//varchar2(100)	y	术者
    private String yz7;//varchar2(100)	y	i助
    private String ez7;//varchar2(100)	y	ii助
    private String qkdj7;//varchar2(100)	y	切口等级
    private String qkyhlb7;//varchar2(100)	y	切口愈合类别
    private String mzfs7;//varchar2(100)	y	麻醉方式
    private String mzys7;//varchar2(100)	y	麻醉医师
    private String lyfs;//varchar2(100)	y	离院方式
    private String yzzy_yljg;//varchar2(200)	y	医嘱转院，拟接收医疗机构名称
    private String wsy_yljg;//varchar2(200)	y	医嘱转社区卫生服务机构/乡镇卫生院，拟接收医疗机构名称
    private String sfzzyjh;//varchar2(100)	y	是否有出院31天内再住院计划手术情况
    private String md;//varchar2(100)	y	目的
    private BigDecimal ryq_t;//number(12)	y	颅脑损伤患者昏迷入院前时间：天
    private BigDecimal ryq_xs;//number(24)	y	颅脑损伤患者昏迷入院前时间：小时
    private BigDecimal ryq_f;//number(12)	y	颅脑损伤患者昏迷入院前时间：分
    private BigDecimal ryh_t;//number(12)	y	颅脑损伤患者昏迷入院后时间：天
    private BigDecimal ryh_xs;//number(24)	y	颅脑损伤患者昏迷入院后时间：小时
    private BigDecimal ryh_f;//number(12)	y	颅脑损伤患者昏迷入院后时间：分
    private BigDecimal zfy;//number(12,2)	n	住院费用(元)：总费用
    private BigDecimal zfje;//number(12,2)	y	自付金额
    private BigDecimal ylfuf;//number(12,2)	y	综合医疗服务类：(1)一般医疗服务费
    private BigDecimal zlczf;//number(12,2)	y	一般治疗操作费
    private BigDecimal hlf;//number(12,2)	y	护理费住院费
    private BigDecimal qtfy;//number(12,2)	y	其他费用
    private BigDecimal blzdf;//number(12,2)	y	诊断类：(5)病理诊断费
    private BigDecimal syszdf;//number(12,2)	y	实验室诊断费
    private BigDecimal yxxzdf;//number(12,2)	y	影像学诊断费
    private BigDecimal lczdxmf;//number(12,2)	y	临床诊断项目费
    private BigDecimal fsszlxmf;//number(12,2)	y	治疗类：(9)非手术治疗项目费
    private BigDecimal wlzlf;//number(12,2)	y	临床物理治疗费
    private BigDecimal sszlf;//number(12,2)	y	手术治疗费
    private BigDecimal maf;//number(12,2)	y	麻醉费
    private BigDecimal ssf;//number(12,2)	y	手术费
    private BigDecimal kff;//number(12,2)	y	康复类：(11)康复费
    private BigDecimal zyzlf;//number(12,2)	y	中医类:(12)中医治疗费
    private BigDecimal xyf;//number(12,2)	y	西药类:(13)西药费
    private BigDecimal kjywf;//number(12,2)	y	抗菌药物费
    private BigDecimal zcyf;//number(12,2)	y	中药类:(14)中成药费
    private BigDecimal zcyf1;//number(12,2)	y	中草药费
    private BigDecimal xf;//number(12,2)	y	血液和血液制品类:(16)血费
    private BigDecimal bdblzpf;//number(12,2)	y	白蛋白类制品费
    private BigDecimal qdblzpf;//	number(12,2)	y	球蛋白类制品费
    private BigDecimal nxyzlzpf;//number(12,2)	y	凝血因子类制品费
    private BigDecimal xbyzlzpf;//number(12,2)	y	细胞因子类制品费
    private BigDecimal hcyyclf;//number(12,2)	y	耗材类:(21)检查用一次性医用材料费
    private BigDecimal yyclf;//number(12,2)	y	(22)治疗用一次性医用材料费
    private BigDecimal ycxyyclf;//number(12,2)	y	(23)手术用一次性医用材料费
    private BigDecimal qtf;//number(12,2)	y	其他类：(24)其他费
}
