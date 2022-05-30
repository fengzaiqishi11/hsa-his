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
 * 患者基本信息表(TbHzjbxx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbHzjbxx implements Serializable {
    private static final long serialVersionUID = 979106504239473596L;
    private String hzjgnwyid;
    private String yljgdm;
    private String yljgmc;
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
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date csrq;
    /**
     * 个人健康档案编码
     */
    private String grjkdabm;
    /**
     * 居民健康卡号
     */
    private String jmjkkh;
    /**
     * 社保卡号
     */
    private String sbkh;
    /**
     * 医保卡号
     */
    private String ybkh;
    /**
     * 新农合卡号
     */
    private String xnhkh;
    /**
     * 院内诊疗卡号
     */
    private String ynzlkh;
    /**
     * 身份证号
     */
    private String sfzh;
    /**
     * 证件类型（ZJLX）
     */
    private String zjlx;
    /**
     * 证件号码
     */
    private String zjhm;
    /**
     * 婚姻状况代码
     */
    private String hyzkdm;
    /**
     * 婚姻状况名称
     */
    private String hyzkmc;
    /**
     * 文化程度代码
     */
    private String whcddm;
    /**
     * 文化程度名称
     */
    private String whcdmc;
    /**
     * 职业类别代码
     */
    private String zylbdm;
    /**
     * 职业类别名称
     */
    private String zylbmc;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 出生地行政区划代
     */
    private String csdxzqhd;
    /**
     * 出生地名称
     */
    private String csdmc;
    /**
     * 民族代码
     */
    private String mzdm;
    /**
     * 民族名称
     */
    private String mzmc;
    /**
     * 过敏史
     */
    private String gms;
    /**
     * 疾病史
     */
    private String jbs;
    /**
     * 血型代码
     */
    private String xxdm;
    /**
     * 血型名称
     */
    private String xxmc;
    /**
     * Rh（D）血型代码
     */
    private String rhDXxd;
    /**
     * Rh（D）血型名称
     */
    private String rhDXxm;
    /**
     * 国籍代码
     */
    private String gjdm;
    /**
     * 国籍名称
     */
    private String gjmc;
    /**
     * 现住址
     */
    private String xzz;
    /**
     * 现住址邮政编码
     */
    private String xzzyzbm;
    /**
     * 现住址-省代码
     */
    private String xzzsXzq;
    /**
     * 现住址-省名称
     */
    private String xzzsMc;
    /**
     * 现住址-市代码
     */
    private String xzzszXz;
    /**
     * 现住址-市名称
     */
    private String xzzszMc;
    /**
     * 现住址-县代码
     */
    private String xzzxqXz;
    /**
     * 现住址-县名称
     */
    private String xzzxqMc;
    /**
     * 现住址-乡代码
     */
    private String xzzxzXz;
    /**
     * 现住址-乡名称
     */
    private String xzzxzMc;
    /**
     * 现住址-村代码
     */
    private String xzzcXzq;
    /**
     * 现住址村名称
     */
    private String xzzcMc;
    /**
     * 工作单位邮编
     */
    private String gzdwyb;
    /**
     * 工作单位名称
     */
    private String gzdwmc;
    /**
     * 工作单位地址
     */
    private String gzdwdz;
    /**
     * 工作单位电话号码
     */
    private String gzdwdhhm;
    /**
     * 参加工作日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cjgzrq;
    /**
     * 死亡日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date swrqhrq;
    /**
     * 联系人姓名
     */
    private String lxrxm;
    /**
     * 联系人与患者的关
     */
    private String lxryhzgxdm;
    /**
     * 与患者关系名称
     */
    private String lxryhzgxmc;
    /**
     * 联系人地址
     */
    private String lxrdz;
    /**
     * 联系人邮编
     */
    private String lxryb;
    /**
     * 联系人电话
     */
    private String lxrdh;
    /**
     * 建档者名称
     */
    private String jdzxm;
    /**
     * 建档日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jdrqsj;
    /**
     * 数据有效状态
     */
    private String validflag;
    /**
     * 日期时间
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
     * 修改人编码
     */
    private String modifytcode;
    /**
     * 修改人名称
     */
    private String modifytname;


}

