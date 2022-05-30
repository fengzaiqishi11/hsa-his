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
 * 医护人员基本信息表(TbYhryjbxx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYhryjbxx implements Serializable {
    private static final long serialVersionUID = 523447949366955588L;
    /**
     * 医生编码
     */
    private String ysbm;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 注册名称
     */
    private String zcm;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 身份证号
     */
    private String sfzh;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 科室编码
     */
    private String ksbm;
    /**
     * 职务编码
     */
    private String zwdm;
    /**
     * 职务名称
     */
    private String zhiw;
    /**
     * 职称编码
     */
    private String zcdm;
    /**
     * 职称名称
     */
    private String zhic;
    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rzsj;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date csrq;
    /**
     * 人员类别
     */
    private String lb;
    /**
     * 专业代码
     */
    private String zydm;
    /**
     * 专业名称
     */
    private String zymc;
    /**
     * 文化程度代码
     */
    private String whcddm;
    /**
     * 文化程度名称
     */
    private String whcdmc;
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

