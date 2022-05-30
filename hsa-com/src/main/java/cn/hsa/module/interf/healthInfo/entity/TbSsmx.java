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
 * 手术明细表(TbSsmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbSsmx implements Serializable {
    private static final long serialVersionUID = 142243216615092864L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 手术明细流水号
     */
    private String ssmxlsh;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 就诊流水号
     */
    private String jzlsh;
    /**
     * 患者类型代码
     */
    private String hzlxdm;
    /**
     * 病人姓名
     */
    private String xm;
    /**
     * 病人性别代码
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
     * 电子申请单编号
     */
    private String dzsqdbh;
    /**
     * 科室代码
     */
    private String ksdm;
    /**
     * 科室名称
     */
    private String ksmc;
    /**
     * 病区名称
     */
    private String bqmc;
    /**
     * 病房号
     */
    private String bfh;
    /**
     * 病床号
     */
    private String bch;
    /**
     * 日间手术标志
     */
    private String rjssbz;
    /**
     * 手术类型
     */
    private String sslx;
    /**
     * 手术操作编码
     */
    private String ssczbm;
    /**
     * 手术操作名称
     */
    private String ssczmc;
    /**
     * 手术前诊断
     */
    private String ssqzd;
    /**
     * 手术后诊断
     */
    private String sshzd;
    /**
     * 手术起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sskssj;
    /**
     * 手术结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ssjssj;
    /**
     * 手术医生编码
     */
    private String ssysbm;
    /**
     * 手术医生姓名
     */
    private String ssysxm;
    /**
     * 手术医生I助编码
     */
    private String ssysz1bm;
    /**
     * 手术医生I助姓名
     */
    private String ssysz1xm;
    /**
     * 手术医生II助编码
     */
    private String ssysz2bm;
    /**
     * 手术医生II助姓名
     */
    private String ssysz2xm;
    /**
     * 麻醉医师编码
     */
    private String mzysbm;
    /**
     * 麻醉医师姓名
     */
    private String mzysxm;
    /**
     * 麻醉方式代码
     */
    private String mzfsdm;
    /**
     * 麻醉方式名称
     */
    private String mzfsmc;
    /**
     * 切口愈合等级
     */
    private String qkyhdj;
    /**
     * 手术间编号
     */
    private String ssjbh;
    /**
     * 手术级别代码
     */
    private String ssjbdm;
    /**
     * 介入物名称
     */
    private String jrwmc;
    /**
     * 手术体位代码
     */
    private String sstwdm;
    /**
     * 手术过程描述
     */
    private String ssgcms;
    /**
     * 手术史标志
     */
    private String sssbz;
    /**
     * 皮肤消毒描述
     */
    private String pfxdms;
    /**
     * 手术切口描述
     */
    private String ssqkms;
    /**
     * 出血量（mL)
     */
    private Integer cxl;
    /**
     * 输液量(mL)
     */
    private Integer syl;
    /**
     * 输血量(mL)
     */
    private Integer sxl;
    /**
     * 术前用药
     */
    private String sqyy;
    /**
     * 术中用药
     */
    private String szyy;
    /**
     * 输血异常反应标志
     */
    private String sxfybz;
    /**
     * 器械护士姓名
     */
    private String qxhsxm;
    /**
     * 器械护士编码
     */
    private String qxhsbm;
    /**
     * 巡台护士姓名
     */
    private String xthsxm;
    /**
     * 巡台护士编码
     */
    private String xthsbm;
    /**
     * 手术目标部位名称
     */
    private String ssmbbwmc;
    /**
     * 手术及操作方法
     */
    private String ssjczff;
    /**
     * 手术及操作次数
     */
    private Integer ssjczcs;
    /**
     * 手术切口类别代码
     */
    private String ssqklbdm;
    /**
     * 手术过程
     */
    private String ssgc;
    /**
     * 引流材料名称
     */
    private String ylclmc;
    /**
     * 引流材料数目
     */
    private String ylclsm;
    /**
     * 放置部位
     */
    private String fzbw;
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

