package cn.hsa.module.sync.orderreceive.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名含义：
 * base：基础模块
 * order：单据
 * receive：领药
 * (SyncOrderReceive)实体类
 *
 * @author makejava
 * @since 2020-09-17 11:46:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncOrderReceiveDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 568621234708112868L;
    /**
     * 主键
     */
    private String id;
    /**
     * 单据编码
     */
    private String code;
    /**
     * 单据名称
     */
    private String name;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 用法代码集合
     */
    private String usageCodes;
    /**
     * 使用科室ID集合
     */
    private String deptIds;
    /**
     * 是否大输液：0否、1是（SF）
     */
    private String isLvp;
    /**
     * 是否特殊药品：0否、1是（SF）
     */
    private String isPh;
    /**
     * 是否中草药（SF）
     */
    private String isHerb;
    /**
     * 是否出院带药（SF）
     */
    private String isGive;
    /**
     * 是否紧急领药（SF）
     */
    private String isEmergency;
    /**
     * 是否材料（SF）
     */
    private String isMaterial;
    /**
     * 是否按病人（SF）
     */
    private String isPatient;
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