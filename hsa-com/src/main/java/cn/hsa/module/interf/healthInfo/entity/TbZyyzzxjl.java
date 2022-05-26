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
 * 住院医嘱执行记录实体类(TbZyyzzxjl)
 * @author liuliyun
 * @since 2022-05-12 19:00:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbZyyzzxjl extends PageDO implements Serializable {
    private static final long serialVersionUID = -15433560107370417L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 执行单号
     */
    private String zxdh;
    /**
     * 执行次数/序号
     */
    private Integer zxcs;
    /**
     * 医嘱序号
     */
    private String yzxh;
    /**
     * 医嘱ID
     */
    private String yzid;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 病人姓名
     */
    private String xm;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
    /**
     * 医嘱明细编码
     */
    private String yzmxbm;
    /**
     * 医嘱组号
     */
    private String yzzh;
    /**
     * 执行人
     */
    private String zxrbm;
    /**
     * 执行人姓名
     */
    private String zxrxm;
    /**
     * 执行时间
     */
    private Date zxsj;
    /**
     * 执行说明
     */
    private String zxsm;
    /**
     * 配药人编码
     */
    private String pyrbm;
    /**
     * 配药人姓名
     */
    private String pyrxm;
    /**
     * 配药时间
     */
    private Date pysj;
    /**
     * 操作人编码
     */
    private String czrbm;
    /**
     * 操作人姓名
     */
    private String czrxm;
    /**
     * 操作时间
     */
    private Date czsj;
    /**
     * 用法
     */
    private String yf;
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
