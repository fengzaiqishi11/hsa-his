package cn.hsa.module.interf.healthInfo.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 患者基本信息实体类(TbHzjbxx) *
 * @author liuliyun
 * @since 2022-05-10 09:51:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class TbHzjbxx extends PageDO implements Serializable {
    private static final long serialVersionUID = -5976037314224252169L;
    private String HZJGNWYID; // 档案id
    private String YLJGDM; // 医疗机构编码
    private String YLJGMC; // 医疗机构名称
    private String XM; // 姓名
    private String XBDM; // 性别代码
    private String XBMC; // 性别名称
    private Date CSRQ; // 出生日期
    private String GRJKDABM; // 个人健康档案
    private String JMJKKH; // 居民健康卡号
    private String SBKH; // 居民社保卡号
    private String YBKH; // 医保卡号
    private String XNHKH; // 新农合卡号
    private String YNZLKH; // 院内诊疗卡号
    private String SFZH;// 身份证号
    private String ZJLX;// 证件类型
    private String ZJHM;// 证件号码
    private String HYZKDM; // 婚姻状况
    private String HYZKMC; // 婚姻状况名称
    private String WHCDDM; // 文化程度
    private String WHCDMC; // 文化程度名称
    private String ZYLBDM; // 职业类别代码
    private String ZYLBMC; //  职业类别名称
    private String LXDH; // 联系电话
    private String CSDXZQHDM; //出生地行政区划代码
    private String CSDMC; // 出生地名称
    private String MZDM; //民族代码
    private String MZMC; // 民族名称
    private  String GMS; // 过敏史
    private String JBS; // 疾病史
    private String XXDM; // 血型代码
    private String XXMC; // 血型名称
    private String Rh_D_XXDM; // Rh（D）血型代码
    private String Rh_D_XXMC; // Rh（D）血型名称
    private String GJDM; // 国籍代码
    private  String GJMC; // 国籍名称
    private String XZZ; // 现住址
    private String XZZYZBM; // 现住址名称
    private  String XZZS_XZQHDM; // 现住址-省（自治区、直辖 市行政区划代码
    private String XZZS_MC; // 现住址-省（自治区、直辖 市中文描述
    private  String XZZSZ_XZQHDM; // 现住址-市（地区、州行政区划代码
    private  String XZZSZ_MC; // 现住址-市（地区、州中文描述
    private  String XZZXQ_XZQHDM; // 现住址-县（区）行政区域代码
    private String XZZXQ_MC; // 现住址-县（区)中文描述
    private  String XZZXZ_XZQHDM; // 现住址-乡（镇、街道办事 处)行政区划代码
    private String XZZXZ_MC; // 现住址-乡（镇、街道办事 处
    private  String XZZC_XZQHDM; // 现住址-村(街、路、弄等行政区划代码
    private  String XZZC_MC; // 现住址-村(街、路、弄等
    private  String GZDWYB; // 工作邮编
    private String GZDWMC; // 工作单位名称
    private  String GZDWDZ; // 工作单位地址
    private  String GZDWDHHM; // 工作单位电话号码
    private String CJGZRQ; // 参加工作日期
    private  String SWRQHRQ; // 死亡日期和时间
    private String LXRXM; // 联系人姓名
    private  String LXRYHZGXDM; // 联系人与患者的关系代码
    private String  LXRYHZGXMC ; // 联系人与患者的关系名称
    private String LXRDZ; // 联系人地址
    private String LXRYB; // 联系人邮编
    private String LXRDH; // 联系人电话
    private String JDZXM; // 建档者名称
    private Date JDRQSJ; //建档日期时间
    private  String VALIDFLAG; // 数据有效状态
    private  String APPETIME; // 数据产生时间
    private Date MODIFYTIME; // 最后修改时间
    private String MODIFYTCODE;//修改人编码
    private String MODIFYTNAME;//修改人名称



}
