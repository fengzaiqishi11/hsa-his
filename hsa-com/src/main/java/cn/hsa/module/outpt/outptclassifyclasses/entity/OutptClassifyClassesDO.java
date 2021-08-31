package cn.hsa.module.outpt.outptclassifyclasses.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 星期，使用1....7 表示星期1-7
 * 分诊台ID：科室表中科室性质为分诊台的科室ID(OutptClassifyClasses)实体类
 *
 * @author xingyu.xie
 * @since 2020-08-11 08:51:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptClassifyClassesDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 907233685219044357L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 班次ID
     */
    private String csId;
    /**
     * 挂号类别ID
     */
    private String cyId;
    /**
     * 分诊台ID
     */
    private String triageId;
    /**
     * 分诊方式代码(FZFS)
     */
    private String triageCode;
    /**
     * 挂号科室ID
     */
    private String deptId;
    /**
     * 星期
     */
    private String weeks;
    /**
     * 限号数
     */
    private Integer registerNum;
    /**
     * 是否叫号（SF）
     */
    private String isCall;
    /**
     * 是否有效（SF）
     */
    private String isValid;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;


}