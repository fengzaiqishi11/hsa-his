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
 * 双向转诊表(TbSxzz)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbSxzz implements Serializable {
    private static final long serialVersionUID = -21403669454915589L;
    /**
     * 转诊机构编码
     */
    private String yljgdm;
    /**
     * 转诊单号
     */
    private String zzdh;
    /**
     * 转诊机构名称
     */
    private String yljgmc;
    /**
     * 转诊科室编码
     */
    private String ksbm;
    /**
     * 转诊科室名称
     */
    private String ksmc;
    /**
     * 申请医生姓名
     */
    private String sqysxm;
    /**
     * 患者姓名
     */
    private String xm;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 身份证号码
     */
    private String sfzhm;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 诊断编码
     */
    private String zdbm;
    /**
     * 诊断名称
     */
    private String zdmc;
    /**
     * 转诊类型
     */
    private String zzlx;
    /**
     * 申请转诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sqzzsj;
    /**
     * 转诊原因说明
     */
    private String zzyysm;
    /**
     * 转诊状态
     */
    private String zzzt;
    /**
     * 接收机构编码
     */
    private String jsjgbm;
    /**
     * 接收机构名称
     */
    private String jsjgmc;
    /**
     * 接收科室编码
     */
    private String jsksbm;
    /**
     * 接收科室名称
     */
    private String jsksmc;
    /**
     * 接收医生姓名
     */
    private String jsysxm;
    /**
     * 接收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jssj;
    /**
     * 是否有效
     */
    private String sfyx;
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

