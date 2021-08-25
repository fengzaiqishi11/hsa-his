package cn.hsa.module.center.nationstandardItem.entity;

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
 * material：国家标准项目
 * <p>
 * 表说明：
 * (nationStandardItemDTO)实体类
 *
 * @author makejava
 * @since 2021-01-26 09:18:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NationStandardItemDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -31823734958930171L;
    /**
     * 主键
     */
    private String id;
    /**
     * 项目编码
     */
    private String code;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 项目内涵
     */
    private String intension;
    /**
     * 除外内容
     */
    private String except;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效：0否、1是
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
    /** 五笔码 **/
    private String wbm;
    /** 拼音码 **/
    private String pym;
    /** 省份代码 **/
    private String provinceCode;
}