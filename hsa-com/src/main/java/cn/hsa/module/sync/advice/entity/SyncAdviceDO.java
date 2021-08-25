package cn.hsa.module.sync.advice.entity;

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
 * @Package_name: cn.hsa.module.base.data.entity
 * @Class_name: BaseMaterialDO
 * @Describe:  医嘱管理数据库映射对象
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/13 15:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncAdviceDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 412020922708327645L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医嘱编码
     */
    private String code;
    /**
     * 医嘱名称
     */
    private String name;
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

}