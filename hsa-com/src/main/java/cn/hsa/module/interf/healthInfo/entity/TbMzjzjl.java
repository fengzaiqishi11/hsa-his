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
 * 门诊就诊表(TbMzjzjl)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbMzjzjl implements Serializable {
    private static final long serialVersionUID = 941261862955327500L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 门诊就诊流水号
     */
    private String mzlsh;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 个人健康档案编码
     */
    private String grjkdabm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 门诊号/挂号单号
     */
    private String mzh;
    /**
     * 卡类型
     */
    private String klx;
    /**
     * 卡号
     */
    private String kh;
    /**
     * 患者姓名
     */
    private String xm;
    /**
     * 身份证号码
     */
    private String sfzhm;
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
     * 就诊类型
     */
    private String jzlx;
    /**
     * 医疗保险类别代码
     */
    private String ylbxlbdm;
    /**
     * 医疗保险类别名称
     */
    private String ylbxlbmc;
    /**
     * 特需标志
     */
    private String txbz;
    /**
     * 外地标志
     */
    private String wdbz;
    /**
     * 就诊科室编码
     */
    private String jzksbm;
    /**
     * 就诊科室名称
     */
    private String jzksmc;
    /**
     * 门诊就诊日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jzksrq;
    /**
     * 接诊医生编码
     */
    private String jzysbm;
    /**
     * 接诊医生姓名
     */
    private String jzysxm;
    /**
     * 接诊医生职称代码
     */
    private String jzyszcdm;
    /**
     * 接诊医生职称名称
     */
    private String jzyszcmc;
    /**
     * 门诊诊断编码（主要诊断)
     */
    private String mzzdbm;
    /**
     * 门诊诊断名称
     */
    private String mzzdmc;
    /**
     * 门诊诊断说明
     */
    private String mzzdsm;
    /**
     * 主诉
     */
    private String zs;
    /**
     * 过敏史
     */
    private String gms;
    /**
     * 疾病史
     */
    private String jbs;
    /**
     * 症状描述
     */
    private String zzms;
    /**
     * 初诊标志代码
     */
    private String czbzdm;
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

