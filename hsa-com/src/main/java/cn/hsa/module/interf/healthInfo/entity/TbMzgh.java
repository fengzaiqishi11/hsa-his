package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门诊挂号表(TbMzgh)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbMzgh implements Serializable {
    private static final long serialVersionUID = 500610939334601568L;
    /**
     * 门诊就诊流水号
     */
    private String mzlsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
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
     * 挂/退号时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gthsj;
    /**
     * 挂号类别
     */
    private String ghlb;
    /**
     * 医疗保险类别代码
     */
    private String ylbxlbdm;
    /**
     * 医疗保险类别名称
     */
    private String ylbxlbmc;
    /**
     * 科室编码
     */
    private String ksbm;
    /**
     * 科室名称
     */
    private String ksmc;
    /**
     * 挂号费
     */
    private BigDecimal zfzlf;
    /**
     * 挂号方式
     */
    private String ghfs;
    /**
     * 特需标志
     */
    private String txbz;
    /**
     * 外地标志
     */
    private String wdbz;
    /**
     * 就诊状态
     */
    private String ghzt;
    /**
     * 挂号医生编码
     */
    private String ghysbm;
    /**
     * 挂号医生姓名
     */
    private String ghysxm;
    /**
     * 挂号医生职称代码
     */
    private String ghyszcdm;
    /**
     * 挂号医生职称名称
     */
    private String ghyszcmc;
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

