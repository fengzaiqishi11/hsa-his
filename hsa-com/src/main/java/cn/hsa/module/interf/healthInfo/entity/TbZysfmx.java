package cn.hsa.module.interf.healthInfo.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 住院收费明细表实体类(TbZysfmx)
 * @author liuliyun
 * @since 2022-05-13 10:08:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbZysfmx extends PageDO implements Serializable {
    private static final long serialVersionUID = -11933589957765399L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 收费明细ID
     */
    private String sfmxid;
    /**
     * 退费标志
     */
    private String tfbz;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 住院结算单号
     */
    private String zyjsdh;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
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
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 相关医嘱ID
     */
    private String yzid;
    /**
     * 明细费用类别
     */
    private String mxfylb;
    /**
     * 收费/退费时间
     */
    private Date stfsj;
    /**
     * 明细项目编码
     */
    private String mxxmbm;
    /**
     * 明细项目名称
     */
    private String mxxmmc;
    /**
     * 明细项目单位
     */
    private String mxxmdw;
    /**
     * 明细项目单价
     */
    private Double mxxmdj;
    /**
     * 明细项目数量
     */
    private Double mxxmsl;
    /**
     * 明细项目金额
     */
    private Double mxxmje;
    /**
     * 院内明细项目名称
     */
    private String ylmxxmc;
    /**
     * 开单时间
     */
    private Date kdsj;
    /**
     * 开单科室代码
     */
    private String kdksdm;
    /**
     * 开单科室名称
     */
    private String kdksmc;
    /**
     * 开单医生代码
     */
    private String kdysdm;
    /**
     * 开单医生名称
     */
    private String kdysmc;
    /**
     * 收费科室代码
     */
    private String sfksdm;
    /**
     * 收费科室名称
     */
    private String sfksmc;
    /**
     * 收费医生代码
     */
    private String sfysdm;
    /**
     * 收费医生名称
     */
    private String sfysmc;
    /**
     * 执行科室代码
     */
    private String zxksdm;
    /**
     * 执行科室名称
     */
    private String zxksmc;
    /**
     * 执行人代码
     */
    private String zxrdm;
    /**
     * 执行人名称
     */
    private String zxrmc;
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
