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
 * 诊后随访表(TbZhsf)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbZhsf implements Serializable {
    private static final long serialVersionUID = 583672634242721833L;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 随访单号
     */
    private String ssdh;
    /**
     * 机构名称
     */
    private String yljgmc;
    /**
     * 随访类型
     */
    private String sflx;
    /**
     * 随访方式
     */
    private String sffs;
    /**
     * 就诊科室编码
     */
    private String jzksbm;
    /**
     * 就诊科室名称
     */
    private String jzksmc;
    /**
     * 就诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jzsj;
    /**
     * 就诊记录流水号
     */
    private String jzjllsh;
    /**
     * 姓名
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
     * 年龄
     */
    private String nl;
    /**
     * 诊断编码
     */
    private String zdbm;
    /**
     * 诊断名称
     */
    private String zdmc;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 随访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sfsj;
    /**
     * 随访医生编码
     */
    private String sfysbm;
    /**
     * 随访医生姓名
     */
    private String sfysxm;
    /**
     * 随访结果
     */
    private String sfjg;
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

