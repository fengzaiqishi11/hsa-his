package cn.hsa.module.interf.healthInfo.entity;

import java.util.Date;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 病案首页-手术操作明细实体类(TbBasyssmx)
 * @author liuliyun
 * @since 2022-05-18 11:15:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbBasyssmx extends PageDO implements Serializable {
    private static final long serialVersionUID = 837158014999417655L;
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
    private Date appetime;
    /**
     * 最后修改时间
     */
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
