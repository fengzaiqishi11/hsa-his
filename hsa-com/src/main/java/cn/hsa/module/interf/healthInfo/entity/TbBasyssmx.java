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
 * 病案首页-手术操作明细(TbBasyssmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:42:59
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbBasyssmx implements Serializable {
    private static final long serialVersionUID = -93182584301827879L;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 手术记录流水号
     */
    private String ssjllsh;
    /**
     * 手术及操作编码
     */
    private String ssjczbm;
    /**
     * 手术及操作日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ssjczrq;
    /**
     * 手术切口类别代码
     */
    private String ssqklxdm;
    /**
     * 手术级别
     */
    private String ssjb;
    /**
     * 手术及操作名称
     */
    private String ssjczmc;
    /**
     * 手术术者
     */
    private String sssz;
    /**
     * 手术Ⅰ助
     */
    private String ssyz;
    /**
     * 手术Ⅱ助
     */
    private String ssez;
    /**
     * 切口愈合等级
     */
    private String qkyhdj;
    /**
     * 麻醉方式代码
     */
    private String mzfsdm;
    /**
     * 麻醉方式名称
     */
    private String mzfsmc;
    /**
     * 麻醉医师
     */
    private String mzys;
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

