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
    private Integer nl;//number(10)	n	年龄
    private String gj;//varchar2(100)	y	国籍
    private Integer bzyzsnl;//number(4)	y	(年龄不足1周岁的)年龄(月)
    private BigDecimal xsecstz;//number(12,2)	y	新生儿出生体重(克)
    private BigDecimal xserytz;//number(12,2)	y	新生儿入院体重(克）
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYlfkfs() {
        return ylfkfs;
    }

    public void setYlfkfs(String ylfkfs) {
        this.ylfkfs = ylfkfs;
    }

    public String getJkkh() {
        return jkkh;
    }

    public void setJkkh(String jkkh) {
        this.jkkh = jkkh;
    }

    public String getZycs() {
        return zycs;
    }

    public void setZycs(String zycs) {
        this.zycs = zycs;
    }

    public String getBah() {
        return bah;
    }

    public void setBah(String bah) {
        this.bah = bah;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public Integer getNl() {
        return nl;
    }

    public void setNl(Integer nl) {
        this.nl = nl;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public Integer getBzyzsnl() {
        return bzyzsnl;
    }

    public void setBzyzsnl(Integer bzyzsnl) {
        this.bzyzsnl = bzyzsnl;
    }

    public BigDecimal getXsecstz() {
        return xsecstz;
    }

    public void setXsecstz(BigDecimal xsecstz) {
        this.xsecstz = xsecstz;
    }

    public BigDecimal getXserytz() {
        return xserytz;
    }

    public void setXserytz(BigDecimal xserytz) {
        this.xserytz = xserytz;
    }

    public String getCsd() {
        return csd;
    }

    public void setCsd(String csd) {
        this.csd = csd;
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getHy() {
        return hy;
    }

    public void setHy(String hy) {
        this.hy = hy;
    }

    public String getXzz() {
        return xzz;
    }

    public void setXzz(String xzz) {
        this.xzz = xzz;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getYb1() {
        return yb1;
    }

    public void setYb1(String yb1) {
        this.yb1 = yb1;
    }

    public String getHkdz() {
        return hkdz;
    }

    public void setHkdz(String hkdz) {
        this.hkdz = hkdz;
    }

    public String getYb2() {
        return yb2;
    }

    public void setYb2(String yb2) {
        this.yb2 = yb2;
    }

    public String getGzdwjdz() {
        return gzdwjdz;
    }

    public void setGzdwjdz(String gzdwjdz) {
        this.gzdwjdz = gzdwjdz;
    }

    public String getDwdh() {
        return dwdh;
    }

    public void setDwdh(String dwdh) {
        this.dwdh = dwdh;
    }

    public String getYb3() {
        return yb3;
    }

    public void setYb3(String yb3) {
        this.yb3 = yb3;
    }

    public String getLxrxm() {
        return lxrxm;
    }

    public void setLxrxm(String lxrxm) {
        this.lxrxm = lxrxm;
    }

    public String getGx() {
        return gx;
    }

    public void setGx(String gx) {
        this.gx = gx;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getDh2() {
        return dh2;
    }

    public void setDh2(String dh2) {
        this.dh2 = dh2;
    }

    public String getRytj() {
        return rytj;
    }

    public void setRytj(String rytj) {
        this.rytj = rytj;
    }

    public String getRysj() {
        return rysj;
    }

    public void setRysj(String rysj) {
        this.rysj = rysj;
    }

    public String getRysjs() {
        return rysjs;
    }

    public void setRysjs(String rysjs) {
        this.rysjs = rysjs;
    }

    public String getRykb() {
        return rykb;
    }

    public void setRykb(String rykb) {
        this.rykb = rykb;
    }

    public String getRybf() {
        return rybf;
    }

    public void setRybf(String rybf) {
        this.rybf = rybf;
    }

    public String getZkkb() {
        return zkkb;
    }

    public void setZkkb(String zkkb) {
        this.zkkb = zkkb;
    }

    public String getCysj() {
        return cysj;
    }

    public void setCysj(String cysj) {
        this.cysj = cysj;
    }

    public String getCysjs() {
        return cysjs;
    }

    public void setCysjs(String cysjs) {
        this.cysjs = cysjs;
    }

    public String getCykb() {
        return cykb;
    }

    public void setCykb(String cykb) {
        this.cykb = cykb;
    }

    public String getCybf() {
        return cybf;
    }

    public void setCybf(String cybf) {
        this.cybf = cybf;
    }

    public String getSjzyts() {
        return sjzyts;
    }

    public void setSjzyts(String sjzyts) {
        this.sjzyts = sjzyts;
    }

    public String getMzzd() {
        return mzzd;
    }

    public void setMzzd(String mzzd) {
        this.mzzd = mzzd;
    }

    public String getJbbm() {
        return jbbm;
    }

    public void setJbbm(String jbbm) {
        this.jbbm = jbbm;
    }

    public String getZyzd() {
        return zyzd;
    }

    public void setZyzd(String zyzd) {
        this.zyzd = zyzd;
    }

    public String getJbdm() {
        return jbdm;
    }

    public void setJbdm(String jbdm) {
        this.jbdm = jbdm;
    }

    public String getRybq() {
        return rybq;
    }

    public void setRybq(String rybq) {
        this.rybq = rybq;
    }

    public String getQtzd8() {
        return qtzd8;
    }

    public void setQtzd8(String qtzd8) {
        this.qtzd8 = qtzd8;
    }

    public String getJbdm8() {
        return jbdm8;
    }

    public void setJbdm8(String jbdm8) {
        this.jbdm8 = jbdm8;
    }

    public String getRybq8() {
        return rybq8;
    }

    public void setRybq8(String rybq8) {
        this.rybq8 = rybq8;
    }

    public String getQtzd1() {
        return qtzd1;
    }

    public void setQtzd1(String qtzd1) {
        this.qtzd1 = qtzd1;
    }

    public String getJbdm1() {
        return jbdm1;
    }

    public void setJbdm1(String jbdm1) {
        this.jbdm1 = jbdm1;
    }

    public String getRybq1() {
        return rybq1;
    }

    public void setRybq1(String rybq1) {
        this.rybq1 = rybq1;
    }

    public String getQtzd9() {
        return qtzd9;
    }

    public void setQtzd9(String qtzd9) {
        this.qtzd9 = qtzd9;
    }

    public String getJbdm9() {
        return jbdm9;
    }

    public void setJbdm9(String jbdm9) {
        this.jbdm9 = jbdm9;
    }

    public String getRybq9() {
        return rybq9;
    }

    public void setRybq9(String rybq9) {
        this.rybq9 = rybq9;
    }

    public String getQtzd2() {
        return qtzd2;
    }

    public void setQtzd2(String qtzd2) {
        this.qtzd2 = qtzd2;
    }

    public String getJbdm2() {
        return jbdm2;
    }

    public void setJbdm2(String jbdm2) {
        this.jbdm2 = jbdm2;
    }

    public String getRybq2() {
        return rybq2;
    }

    public void setRybq2(String rybq2) {
        this.rybq2 = rybq2;
    }

    public String getQtzd10() {
        return qtzd10;
    }

    public void setQtzd10(String qtzd10) {
        this.qtzd10 = qtzd10;
    }

    public String getJbdm10() {
        return jbdm10;
    }

    public void setJbdm10(String jbdm10) {
        this.jbdm10 = jbdm10;
    }

    public String getRybq10() {
        return rybq10;
    }

    public void setRybq10(String rybq10) {
        this.rybq10 = rybq10;
    }

    public String getQtzd3() {
        return qtzd3;
    }

    public void setQtzd3(String qtzd3) {
        this.qtzd3 = qtzd3;
    }

    public String getJbdm3() {
        return jbdm3;
    }

    public void setJbdm3(String jbdm3) {
        this.jbdm3 = jbdm3;
    }

    public String getRybq3() {
        return rybq3;
    }

    public void setRybq3(String rybq3) {
        this.rybq3 = rybq3;
    }

    public String getQtzd11() {
        return qtzd11;
    }

    public void setQtzd11(String qtzd11) {
        this.qtzd11 = qtzd11;
    }

    public String getJbdm11() {
        return jbdm11;
    }

    public void setJbdm11(String jbdm11) {
        this.jbdm11 = jbdm11;
    }

    public String getRybq11() {
        return rybq11;
    }

    public void setRybq11(String rybq11) {
        this.rybq11 = rybq11;
    }

    public String getQtzd4() {
        return qtzd4;
    }

    public void setQtzd4(String qtzd4) {
        this.qtzd4 = qtzd4;
    }

    public String getRybq4() {
        return rybq4;
    }

    public void setRybq4(String rybq4) {
        this.rybq4 = rybq4;
    }

    public String getQtzd12() {
        return qtzd12;
    }

    public void setQtzd12(String qtzd12) {
        this.qtzd12 = qtzd12;
    }

    public String getJbdm12() {
        return jbdm12;
    }

    public void setJbdm12(String jbdm12) {
        this.jbdm12 = jbdm12;
    }

    public String getRybq12() {
        return rybq12;
    }

    public void setRybq12(String rybq12) {
        this.rybq12 = rybq12;
    }

    public String getQtzd5() {
        return qtzd5;
    }

    public void setQtzd5(String qtzd5) {
        this.qtzd5 = qtzd5;
    }

    public String getJbdm5() {
        return jbdm5;
    }

    public void setJbdm5(String jbdm5) {
        this.jbdm5 = jbdm5;
    }

    public String getRybq5() {
        return rybq5;
    }

    public void setRybq5(String rybq5) {
        this.rybq5 = rybq5;
    }

    public String getQtzd13() {
        return qtzd13;
    }

    public void setQtzd13(String qtzd13) {
        this.qtzd13 = qtzd13;
    }

    public String getJbdm13() {
        return jbdm13;
    }

    public void setJbdm13(String jbdm13) {
        this.jbdm13 = jbdm13;
    }

    public String getRybq13() {
        return rybq13;
    }

    public void setRybq13(String rybq13) {
        this.rybq13 = rybq13;
    }

    public String getQtzd6() {
        return qtzd6;
    }

    public void setQtzd6(String qtzd6) {
        this.qtzd6 = qtzd6;
    }

    public String getJbdm6() {
        return jbdm6;
    }

    public void setJbdm6(String jbdm6) {
        this.jbdm6 = jbdm6;
    }

    public String getRybq6() {
        return rybq6;
    }

    public void setRybq6(String rybq6) {
        this.rybq6 = rybq6;
    }

    public String getQtzd14() {
        return qtzd14;
    }

    public void setQtzd14(String qtzd14) {
        this.qtzd14 = qtzd14;
    }

    public String getJbdm14() {
        return jbdm14;
    }

    public void setJbdm14(String jbdm14) {
        this.jbdm14 = jbdm14;
    }

    public String getRybq14() {
        return rybq14;
    }

    public void setRybq14(String rybq14) {
        this.rybq14 = rybq14;
    }

    public String getQtzd7() {
        return qtzd7;
    }

    public void setQtzd7(String qtzd7) {
        this.qtzd7 = qtzd7;
    }

    public String getJbdm7() {
        return jbdm7;
    }

    public void setJbdm7(String jbdm7) {
        this.jbdm7 = jbdm7;
    }

    public String getRybq7() {
        return rybq7;
    }

    public void setRybq7(String rybq7) {
        this.rybq7 = rybq7;
    }

    public String getQtzd15() {
        return qtzd15;
    }

    public void setQtzd15(String qtzd15) {
        this.qtzd15 = qtzd15;
    }

    public String getJbdm15() {
        return jbdm15;
    }

    public void setJbdm15(String jbdm15) {
        this.jbdm15 = jbdm15;
    }

    public String getRybq15() {
        return rybq15;
    }

    public void setRybq15(String rybq15) {
        this.rybq15 = rybq15;
    }

    public String getWbyy() {
        return wbyy;
    }

    public void setWbyy(String wbyy) {
        this.wbyy = wbyy;
    }

    public String getH23() {
        return h23;
    }

    public void setH23(String h23) {
        this.h23 = h23;
    }

    public String getBlzd() {
        return blzd;
    }

    public void setBlzd(String blzd) {
        this.blzd = blzd;
    }

    public String getJbmm() {
        return jbmm;
    }

    public void setJbmm(String jbmm) {
        this.jbmm = jbmm;
    }

    public String getBlh() {
        return blh;
    }

    public void setBlh(String blh) {
        this.blh = blh;
    }

    public String getYwgm() {
        return ywgm;
    }

    public void setYwgm(String ywgm) {
        this.ywgm = ywgm;
    }

    public String getGmyw() {
        return gmyw;
    }

    public void setGmyw(String gmyw) {
        this.gmyw = gmyw;
    }

    public String getSwhzsj() {
        return swhzsj;
    }

    public void setSwhzsj(String swhzsj) {
        this.swhzsj = swhzsj;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getKzr() {
        return kzr;
    }

    public void setKzr(String kzr) {
        this.kzr = kzr;
    }

    public String getZrys() {
        return zrys;
    }

    public void setZrys(String zrys) {
        this.zrys = zrys;
    }

    public String getZzys() {
        return zzys;
    }

    public void setZzys(String zzys) {
        this.zzys = zzys;
    }

    public String getZyys() {
        return zyys;
    }

    public void setZyys(String zyys) {
        this.zyys = zyys;
    }

    public String getZrhs() {
        return zrhs;
    }

    public void setZrhs(String zrhs) {
        this.zrhs = zrhs;
    }

    public String getJxys() {
        return jxys;
    }

    public void setJxys(String jxys) {
        this.jxys = jxys;
    }

    public String getSxys() {
        return sxys;
    }

    public void setSxys(String sxys) {
        this.sxys = sxys;
    }

    public String getBmy() {
        return bmy;
    }

    public void setBmy(String bmy) {
        this.bmy = bmy;
    }

    public String getBazl() {
        return bazl;
    }

    public void setBazl(String bazl) {
        this.bazl = bazl;
    }

    public String getZkys() {
        return zkys;
    }

    public void setZkys(String zkys) {
        this.zkys = zkys;
    }

    public String getZkhs() {
        return zkhs;
    }

    public void setZkhs(String zkhs) {
        this.zkhs = zkhs;
    }

    public String getZkrq() {
        return zkrq;
    }

    public void setZkrq(String zkrq) {
        this.zkrq = zkrq;
    }

    public String getSsjczbm1() {
        return ssjczbm1;
    }

    public void setSsjczbm1(String ssjczbm1) {
        this.ssjczbm1 = ssjczbm1;
    }

    public String getSsjczrq1() {
        return ssjczrq1;
    }

    public void setSsjczrq1(String ssjczrq1) {
        this.ssjczrq1 = ssjczrq1;
    }

    public String getSsjb1() {
        return ssjb1;
    }

    public void setSsjb1(String ssjb1) {
        this.ssjb1 = ssjb1;
    }

    public String getSsjczmc1() {
        return ssjczmc1;
    }

    public void setSsjczmc1(String ssjczmc1) {
        this.ssjczmc1 = ssjczmc1;
    }

    public String getSz1() {
        return sz1;
    }

    public void setSz1(String sz1) {
        this.sz1 = sz1;
    }

    public String getYz1() {
        return yz1;
    }

    public void setYz1(String yz1) {
        this.yz1 = yz1;
    }

    public String getEz1() {
        return ez1;
    }

    public void setEz1(String ez1) {
        this.ez1 = ez1;
    }

    public String getQkdj1() {
        return qkdj1;
    }

    public void setQkdj1(String qkdj1) {
        this.qkdj1 = qkdj1;
    }

    public String getQkyhlb1() {
        return qkyhlb1;
    }

    public void setQkyhlb1(String qkyhlb1) {
        this.qkyhlb1 = qkyhlb1;
    }

    public String getMzfs1() {
        return mzfs1;
    }

    public void setMzfs1(String mzfs1) {
        this.mzfs1 = mzfs1;
    }

    public String getMzys1() {
        return mzys1;
    }

    public void setMzys1(String mzys1) {
        this.mzys1 = mzys1;
    }

    public String getSsjczbm2() {
        return ssjczbm2;
    }

    public void setSsjczbm2(String ssjczbm2) {
        this.ssjczbm2 = ssjczbm2;
    }

    public String getSsjczrq2() {
        return ssjczrq2;
    }

    public void setSsjczrq2(String ssjczrq2) {
        this.ssjczrq2 = ssjczrq2;
    }

    public String getSsjb2() {
        return ssjb2;
    }

    public void setSsjb2(String ssjb2) {
        this.ssjb2 = ssjb2;
    }

    public String getSsjczmc2() {
        return ssjczmc2;
    }

    public void setSsjczmc2(String ssjczmc2) {
        this.ssjczmc2 = ssjczmc2;
    }

    public String getSz2() {
        return sz2;
    }

    public void setSz2(String sz2) {
        this.sz2 = sz2;
    }

    public String getYz2() {
        return yz2;
    }

    public void setYz2(String yz2) {
        this.yz2 = yz2;
    }

    public String getEz2() {
        return ez2;
    }

    public void setEz2(String ez2) {
        this.ez2 = ez2;
    }

    public String getQkdj2() {
        return qkdj2;
    }

    public void setQkdj2(String qkdj2) {
        this.qkdj2 = qkdj2;
    }

    public String getQkyhlb2() {
        return qkyhlb2;
    }

    public void setQkyhlb2(String qkyhlb2) {
        this.qkyhlb2 = qkyhlb2;
    }

    public String getMzfs2() {
        return mzfs2;
    }

    public void setMzfs2(String mzfs2) {
        this.mzfs2 = mzfs2;
    }

    public String getMzys2() {
        return mzys2;
    }

    public void setMzys2(String mzys2) {
        this.mzys2 = mzys2;
    }

    public String getSsjczbm3() {
        return ssjczbm3;
    }

    public void setSsjczbm3(String ssjczbm3) {
        this.ssjczbm3 = ssjczbm3;
    }

    public String getSsjczrq3() {
        return ssjczrq3;
    }

    public void setSsjczrq3(String ssjczrq3) {
        this.ssjczrq3 = ssjczrq3;
    }

    public String getSsjb3() {
        return ssjb3;
    }

    public void setSsjb3(String ssjb3) {
        this.ssjb3 = ssjb3;
    }

    public String getSsjczmc3() {
        return ssjczmc3;
    }

    public void setSsjczmc3(String ssjczmc3) {
        this.ssjczmc3 = ssjczmc3;
    }

    public String getSz3() {
        return sz3;
    }

    public void setSz3(String sz3) {
        this.sz3 = sz3;
    }

    public String getYz3() {
        return yz3;
    }

    public void setYz3(String yz3) {
        this.yz3 = yz3;
    }

    public String getEz3() {
        return ez3;
    }

    public void setEz3(String ez3) {
        this.ez3 = ez3;
    }

    public String getQkdj3() {
        return qkdj3;
    }

    public void setQkdj3(String qkdj3) {
        this.qkdj3 = qkdj3;
    }

    public String getQkyhlb3() {
        return qkyhlb3;
    }

    public void setQkyhlb3(String qkyhlb3) {
        this.qkyhlb3 = qkyhlb3;
    }

    public String getMzfs3() {
        return mzfs3;
    }

    public void setMzfs3(String mzfs3) {
        this.mzfs3 = mzfs3;
    }

    public String getMzys3() {
        return mzys3;
    }

    public void setMzys3(String mzys3) {
        this.mzys3 = mzys3;
    }

    public String getSsjczbm4() {
        return ssjczbm4;
    }

    public void setSsjczbm4(String ssjczbm4) {
        this.ssjczbm4 = ssjczbm4;
    }

    public String getSsjczrq4() {
        return ssjczrq4;
    }

    public void setSsjczrq4(String ssjczrq4) {
        this.ssjczrq4 = ssjczrq4;
    }

    public String getSsjb4() {
        return ssjb4;
    }

    public void setSsjb4(String ssjb4) {
        this.ssjb4 = ssjb4;
    }

    public String getSsjczmc4() {
        return ssjczmc4;
    }

    public void setSsjczmc4(String ssjczmc4) {
        this.ssjczmc4 = ssjczmc4;
    }

    public String getSz4() {
        return sz4;
    }

    public void setSz4(String sz4) {
        this.sz4 = sz4;
    }

    public String getYz4() {
        return yz4;
    }

    public void setYz4(String yz4) {
        this.yz4 = yz4;
    }

    public String getEz4() {
        return ez4;
    }

    public void setEz4(String ez4) {
        this.ez4 = ez4;
    }

    public String getQkdj4() {
        return qkdj4;
    }

    public void setQkdj4(String qkdj4) {
        this.qkdj4 = qkdj4;
    }

    public String getQkyhlb4() {
        return qkyhlb4;
    }

    public void setQkyhlb4(String qkyhlb4) {
        this.qkyhlb4 = qkyhlb4;
    }

    public String getMzfs4() {
        return mzfs4;
    }

    public void setMzfs4(String mzfs4) {
        this.mzfs4 = mzfs4;
    }

    public String getMzys4() {
        return mzys4;
    }

    public void setMzys4(String mzys4) {
        this.mzys4 = mzys4;
    }

    public String getSsjczbm5() {
        return ssjczbm5;
    }

    public void setSsjczbm5(String ssjczbm5) {
        this.ssjczbm5 = ssjczbm5;
    }

    public String getSsjczrq5() {
        return ssjczrq5;
    }

    public void setSsjczrq5(String ssjczrq5) {
        this.ssjczrq5 = ssjczrq5;
    }

    public String getSsjb5() {
        return ssjb5;
    }

    public void setSsjb5(String ssjb5) {
        this.ssjb5 = ssjb5;
    }

    public String getSsjczmc5() {
        return ssjczmc5;
    }

    public void setSsjczmc5(String ssjczmc5) {
        this.ssjczmc5 = ssjczmc5;
    }

    public String getSz5() {
        return sz5;
    }

    public void setSz5(String sz5) {
        this.sz5 = sz5;
    }

    public String getYz5() {
        return yz5;
    }

    public void setYz5(String yz5) {
        this.yz5 = yz5;
    }

    public String getEz5() {
        return ez5;
    }

    public void setEz5(String ez5) {
        this.ez5 = ez5;
    }

    public String getQkdj5() {
        return qkdj5;
    }

    public void setQkdj5(String qkdj5) {
        this.qkdj5 = qkdj5;
    }

    public String getQkyhlb5() {
        return qkyhlb5;
    }

    public void setQkyhlb5(String qkyhlb5) {
        this.qkyhlb5 = qkyhlb5;
    }

    public String getMzfs5() {
        return mzfs5;
    }

    public void setMzfs5(String mzfs5) {
        this.mzfs5 = mzfs5;
    }

    public String getMzys5() {
        return mzys5;
    }

    public void setMzys5(String mzys5) {
        this.mzys5 = mzys5;
    }

    public String getSsjczbm6() {
        return ssjczbm6;
    }

    public void setSsjczbm6(String ssjczbm6) {
        this.ssjczbm6 = ssjczbm6;
    }

    public String getSsjczrq6() {
        return ssjczrq6;
    }

    public void setSsjczrq6(String ssjczrq6) {
        this.ssjczrq6 = ssjczrq6;
    }

    public String getSsjb6() {
        return ssjb6;
    }

    public void setSsjb6(String ssjb6) {
        this.ssjb6 = ssjb6;
    }

    public String getSsjczmc6() {
        return ssjczmc6;
    }

    public void setSsjczmc6(String ssjczmc6) {
        this.ssjczmc6 = ssjczmc6;
    }

    public String getSz6() {
        return sz6;
    }

    public void setSz6(String sz6) {
        this.sz6 = sz6;
    }

    public String getYz6() {
        return yz6;
    }

    public void setYz6(String yz6) {
        this.yz6 = yz6;
    }

    public String getEz6() {
        return ez6;
    }

    public void setEz6(String ez6) {
        this.ez6 = ez6;
    }

    public String getQkdj6() {
        return qkdj6;
    }

    public void setQkdj6(String qkdj6) {
        this.qkdj6 = qkdj6;
    }

    public String getQkyhlb6() {
        return qkyhlb6;
    }

    public void setQkyhlb6(String qkyhlb6) {
        this.qkyhlb6 = qkyhlb6;
    }

    public String getMzfs6() {
        return mzfs6;
    }

    public void setMzfs6(String mzfs6) {
        this.mzfs6 = mzfs6;
    }

    public String getMzys6() {
        return mzys6;
    }

    public void setMzys6(String mzys6) {
        this.mzys6 = mzys6;
    }

    public String getSsjczbm7() {
        return ssjczbm7;
    }

    public void setSsjczbm7(String ssjczbm7) {
        this.ssjczbm7 = ssjczbm7;
    }

    public String getSsjczrq7() {
        return ssjczrq7;
    }

    public void setSsjczrq7(String ssjczrq7) {
        this.ssjczrq7 = ssjczrq7;
    }

    public String getSsjb7() {
        return ssjb7;
    }

    public void setSsjb7(String ssjb7) {
        this.ssjb7 = ssjb7;
    }

    public String getSsjczmc7() {
        return ssjczmc7;
    }

    public void setSsjczmc7(String ssjczmc7) {
        this.ssjczmc7 = ssjczmc7;
    }

    public String getSz7() {
        return sz7;
    }

    public void setSz7(String sz7) {
        this.sz7 = sz7;
    }

    public String getYz7() {
        return yz7;
    }

    public void setYz7(String yz7) {
        this.yz7 = yz7;
    }

    public String getEz7() {
        return ez7;
    }

    public void setEz7(String ez7) {
        this.ez7 = ez7;
    }

    public String getQkdj7() {
        return qkdj7;
    }

    public void setQkdj7(String qkdj7) {
        this.qkdj7 = qkdj7;
    }

    public String getQkyhlb7() {
        return qkyhlb7;
    }

    public void setQkyhlb7(String qkyhlb7) {
        this.qkyhlb7 = qkyhlb7;
    }

    public String getMzfs7() {
        return mzfs7;
    }

    public void setMzfs7(String mzfs7) {
        this.mzfs7 = mzfs7;
    }

    public String getMzys7() {
        return mzys7;
    }

    public void setMzys7(String mzys7) {
        this.mzys7 = mzys7;
    }

    public String getLyfs() {
        return lyfs;
    }

    public void setLyfs(String lyfs) {
        this.lyfs = lyfs;
    }

    public String getYzzy_yljg() {
        return yzzy_yljg;
    }

    public void setYzzy_yljg(String yzzy_yljg) {
        this.yzzy_yljg = yzzy_yljg;
    }

    public String getWsy_yljg() {
        return wsy_yljg;
    }

    public void setWsy_yljg(String wsy_yljg) {
        this.wsy_yljg = wsy_yljg;
    }

    public String getSfzzyjh() {
        return sfzzyjh;
    }

    public void setSfzzyjh(String sfzzyjh) {
        this.sfzzyjh = sfzzyjh;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public BigDecimal getRyq_t() {
        return ryq_t;
    }

    public void setRyq_t(BigDecimal ryq_t) {
        this.ryq_t = ryq_t;
    }

    public BigDecimal getRyq_xs() {
        return ryq_xs;
    }

    public void setRyq_xs(BigDecimal ryq_xs) {
        this.ryq_xs = ryq_xs;
    }

    public BigDecimal getRyq_f() {
        return ryq_f;
    }

    public void setRyq_f(BigDecimal ryq_f) {
        this.ryq_f = ryq_f;
    }

    public BigDecimal getRyh_t() {
        return ryh_t;
    }

    public void setRyh_t(BigDecimal ryh_t) {
        this.ryh_t = ryh_t;
    }

    public BigDecimal getRyh_xs() {
        return ryh_xs;
    }

    public void setRyh_xs(BigDecimal ryh_xs) {
        this.ryh_xs = ryh_xs;
    }

    public BigDecimal getRyh_f() {
        return ryh_f;
    }

    public void setRyh_f(BigDecimal ryh_f) {
        this.ryh_f = ryh_f;
    }

    public BigDecimal getZfy() {
        return zfy;
    }

    public void setZfy(BigDecimal zfy) {
        this.zfy = zfy;
    }

    public BigDecimal getZfje() {
        return zfje;
    }

    public void setZfje(BigDecimal zfje) {
        this.zfje = zfje;
    }

    public BigDecimal getYlfuf() {
        return ylfuf;
    }

    public void setYlfuf(BigDecimal ylfuf) {
        this.ylfuf = ylfuf;
    }

    public BigDecimal getZlczf() {
        return zlczf;
    }

    public void setZlczf(BigDecimal zlczf) {
        this.zlczf = zlczf;
    }

    public BigDecimal getHlf() {
        return hlf;
    }

    public void setHlf(BigDecimal hlf) {
        this.hlf = hlf;
    }

    public BigDecimal getQtfy() {
        return qtfy;
    }

    public void setQtfy(BigDecimal qtfy) {
        this.qtfy = qtfy;
    }

    public BigDecimal getBlzdf() {
        return blzdf;
    }

    public void setBlzdf(BigDecimal blzdf) {
        this.blzdf = blzdf;
    }

    public BigDecimal getSyszdf() {
        return syszdf;
    }

    public void setSyszdf(BigDecimal syszdf) {
        this.syszdf = syszdf;
    }

    public BigDecimal getYxxzdf() {
        return yxxzdf;
    }

    public void setYxxzdf(BigDecimal yxxzdf) {
        this.yxxzdf = yxxzdf;
    }

    public BigDecimal getLczdxmf() {
        return lczdxmf;
    }

    public void setLczdxmf(BigDecimal lczdxmf) {
        this.lczdxmf = lczdxmf;
    }

    public BigDecimal getFsszlxmf() {
        return fsszlxmf;
    }

    public void setFsszlxmf(BigDecimal fsszlxmf) {
        this.fsszlxmf = fsszlxmf;
    }

    public BigDecimal getWlzlf() {
        return wlzlf;
    }

    public void setWlzlf(BigDecimal wlzlf) {
        this.wlzlf = wlzlf;
    }

    public BigDecimal getSszlf() {
        return sszlf;
    }

    public void setSszlf(BigDecimal sszlf) {
        this.sszlf = sszlf;
    }

    public BigDecimal getMaf() {
        return maf;
    }

    public void setMaf(BigDecimal maf) {
        this.maf = maf;
    }

    public BigDecimal getSsf() {
        return ssf;
    }

    public void setSsf(BigDecimal ssf) {
        this.ssf = ssf;
    }

    public BigDecimal getKff() {
        return kff;
    }

    public void setKff(BigDecimal kff) {
        this.kff = kff;
    }

    public BigDecimal getZyzlf() {
        return zyzlf;
    }

    public void setZyzlf(BigDecimal zyzlf) {
        this.zyzlf = zyzlf;
    }

    public BigDecimal getXyf() {
        return xyf;
    }

    public void setXyf(BigDecimal xyf) {
        this.xyf = xyf;
    }

    public BigDecimal getKjywf() {
        return kjywf;
    }

    public void setKjywf(BigDecimal kjywf) {
        this.kjywf = kjywf;
    }

    public BigDecimal getZcyf() {
        return zcyf;
    }

    public void setZcyf(BigDecimal zcyf) {
        this.zcyf = zcyf;
    }

    public BigDecimal getZcyf1() {
        return zcyf1;
    }

    public void setZcyf1(BigDecimal zcyf1) {
        this.zcyf1 = zcyf1;
    }

    public BigDecimal getXf() {
        return xf;
    }

    public void setXf(BigDecimal xf) {
        this.xf = xf;
    }

    public BigDecimal getBdblzpf() {
        return bdblzpf;
    }

    public void setBdblzpf(BigDecimal bdblzpf) {
        this.bdblzpf = bdblzpf;
    }

    public BigDecimal getQdblzpf() {
        return qdblzpf;
    }

    public void setQdblzpf(BigDecimal qdblzpf) {
        this.qdblzpf = qdblzpf;
    }

    public BigDecimal getNxyzlzpf() {
        return nxyzlzpf;
    }

    public void setNxyzlzpf(BigDecimal nxyzlzpf) {
        this.nxyzlzpf = nxyzlzpf;
    }

    public BigDecimal getXbyzlzpf() {
        return xbyzlzpf;
    }

    public void setXbyzlzpf(BigDecimal xbyzlzpf) {
        this.xbyzlzpf = xbyzlzpf;
    }

    public BigDecimal getHcyyclf() {
        return hcyyclf;
    }

    public void setHcyyclf(BigDecimal hcyyclf) {
        this.hcyyclf = hcyyclf;
    }

    public BigDecimal getYyclf() {
        return yyclf;
    }

    public void setYyclf(BigDecimal yyclf) {
        this.yyclf = yyclf;
    }

    public BigDecimal getYcxyyclf() {
        return ycxyyclf;
    }

    public void setYcxyyclf(BigDecimal ycxyyclf) {
        this.ycxyyclf = ycxyyclf;
    }

    public BigDecimal getQtf() {
        return qtf;
    }

    public void setQtf(BigDecimal qtf) {
        this.qtf = qtf;
    }
}
