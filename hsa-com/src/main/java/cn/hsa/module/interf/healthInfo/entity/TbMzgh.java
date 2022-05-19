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
 * 门诊挂号信息实体类(TbMzgh) *
 * @author liuliyun
 * @since 2022-05-10 14:54:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class TbMzgh extends PageDO implements Serializable {

    private static final long serialVersionUID = 8151610919645454025L;
    private String MZLSH; // 门诊挂号流水号
    private String YLJGDM; // 医疗机构编码
    private String YLJGMC; // 医疗机构名称
    private String HZJGNWYID; // 患者机构内唯一 i d
    private String MZH; // 门诊号、挂号单号
    private String KLX; // 卡类型
    private String KH; // 卡号
    private String XM; // 姓名
    private String XBDM; // 性别代码
    private String XBMC; // 性别名称
    private String NL; // 年龄
    private String GTHSJ; // 挂号/退号时间
    private String GHLB; // 挂号类别
    private String YLBXLBDM; // 医疗保险类别
    private String YLBXLBMC; // 医疗保险类别名称
    private String KSBM; // 科室编码
    private String KSMC; // 科室名称
    private String ZFZLF; // 挂号费
    private String GHFS; // 挂号方式
    private String TXBZ; // 特殊标志
    private String WDBZ; // 外地标志
    private String GHZT; // 就诊状态
    private String GHYSBM; // 挂号医生编码
    private String GHYSXM; // 挂号医生姓名
    private String GHYSZCDM; // 挂号医生职称代码
    private String GHYSZCMC; // 挂号医生职称名称
    private  String VALIDFLAG; // 数据有效状态
    private  String APPETIME; // 数据产生时间
    private Date MODIFYTIME; // 最后修改时间
    private String MODIFYTCODE;//修改人编码
    private String MODIFYTNAME;//修改人名称
}
