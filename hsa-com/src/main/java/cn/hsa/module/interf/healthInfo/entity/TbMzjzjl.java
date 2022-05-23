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
 * 门诊就诊信息实体类(TbMzjzjl) *
 * @author liuliyun
 * @since 2022-05-10 14:54:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class TbMzjzjl extends PageDO implements Serializable {
    private String YLJGDM; // 医疗机构编码
    private String MZLSH; // 门诊就诊流水号
    private String YLJGMC; // 医疗机构名称
    private String GRJKDABM; // 个人健康档案编码
    private String HZJGNWYID; // 患者机构内唯一 i d
    private String MZH; // 门诊号、挂号单号
    private String KLX; // 卡类型
    private String KH; // 卡号
    private String XM; // 姓名
    private String SFZHM; // 身份证号码
    private String XBDM; // 性别代码
    private String XBMC; // 性别名称
    private String NL; // 年龄
    private String JZLX; // 就诊类型
    private String YLBXLBDM; // 医疗保险类别
    private String YLBXLBMC; // 医疗保险类别名称
    private String TXBZ; // 特殊标志
    private String WDBZ; // 外地标志
    private String JZKSBM; //就诊科室编码
    private String JZKSMC; // 就诊科室名称
    private String JZKSRQ; // 就诊日期
    private String JZYSBM; // 就诊医生编码
    private String JZYSXM; // 就诊医生名称
    private String JZYSZCDM; // 接诊医生职称代码
    private String  JZYSZCMC; // 接诊医生职称名称
    private String MZZDBM; // 门诊诊断编码（主要诊断)
    private String MZZDMC; // 门诊诊断名称
    private String MZZDSM; // 门诊诊断说明
    private String ZS; // 主诉
    private String GMS; // 过敏史
    private String JBS; // 疾病史
    private String ZZMS; // 症状描述
    private String CZBZDM; // 出诊标志
    private  String VALIDFLAG; // 数据有效状态
    private  String APPETIME; // 数据产生时间
    private Date MODIFYTIME; // 最后修改时间
    private String MODIFYTCODE;//修改人编码
    private String MODIFYTNAME;//修改人名称
}
