package cn.hsa.module.base.ba.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 表名含义：
 * base：基础模块
 * advice：医嘱
 * <p>
 * 表说明：
 * -&#(BaseAdvice)实体类
 *
 * @author xingyu.xie
 * @since 2020-09-04 08:43:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseAdviceDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -84907456675227620L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 医嘱编码
     */
    private String code;
    /**
     * 医嘱名称
     */
    private String name;

    /**
     * 医嘱别名
     */
    private String otherName;
    /**
     * 医嘱类别代码（YZLB）
     */
    private String typeCode;
    /**
     * 医技分类代码（动态取码表）
     */
    private String technologyCode;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 住院执行科室编码（表base_dept）
     */
    private String outDeptCode;
    /**
     * 门诊执行科室编码（表base_dept）
     */
    private String inDeptCode;
    /**
     * 业务类别
     */
    private String bizType;
    /**
     * 业务类别代码（动态取码表）
     */
    private String bizCode;
    /**
     * 容器类型代码（RQ）
     */
    private String containerCode;
    /**
     * 标本类型代码（BBLX）
     */
    private String specimenCode;
    /**
     * 使用范围（SYFW）
     */
    private String useScopeCode;
    /**
     * 使用科室编码集合（多个用逗号隔开）
     */
    private String deptCode;
    /**
     * 开嘱医生级别集合（多个用逗号）
     */
    private String doctorLevelCode;
    /**
     * 手术级别代码（SSJB）
     */
    private String opeartionCode;
    /**
     * 医保目录标识代码（YBMLBS）
     */
    private String insureListCode;
    /**
     * 性别限制代码（XB）
     */
    private String genderCode;
    /**
     * 最小年龄
     */
    private Integer minAge;
    /**
     * 最大年龄
     */
    private Integer maxAge;
    /**
     * 是否计费（SF）
     */
    private String isCost;
    /**
     * 是否停同类医嘱（SF）
     */
    private String isStopSame;
    /**
     * 是否停非同类医嘱（SF）
     */
    private String isStopSameNot;
    /**
     * 是否停自身（SF）
     */
    private String isStopMyself;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 是否有效：0否、1是（SF）
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
    /**
     * 手术icd9编码
     */
    private String operDiseaseIcd9;

    // 报销比例
    private String reimbursementRatio;

    //手术操作国家编码
    private String operNationCode;
    private String operNationName;
    private String unionNationCode;
    private String unionNationName;
}
