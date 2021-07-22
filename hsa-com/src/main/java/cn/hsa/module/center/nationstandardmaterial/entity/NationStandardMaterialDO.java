package cn.hsa.module.center.nationstandardmaterial.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准材料
 * <p>
 * 表说明：
 * (NationStandardMaterial)实体类
 *
 * @author makejava
 * @since 2021-01-26 09:18:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NationStandardMaterialDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -40529771349029198L;
    /**
     * 主键
     */
    private String id;
    /**
     * 材料编码
     */
    private String code;
    /**
     * 材料分类1级
     */
    private String typeCode1;
    /**
     * 材料分类2级
     */
    private String typeCode2;
    /**
     * 材料分类3级
     */
    private String typeCode3;
    /**
     * 医保名称
     */
    private String insuranceName;
    /**
     * 材质
     */
    private String materialQuality;
    /**
     * 特征
     */
    private String features;
    /**
     * 注册证号
     */
    private String regCertNo;
    /**
     * 材料名称
     */
    private String name;
    /**
     * 生产厂家
     */
    private String prod;
    /**
     * 规格
     */
    private String spec;
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
    /** 五笔码 **/
    private String wbm;
    /** 拼音码 **/
    private String pym;
    /** 省份代码 **/
    private String provinceCode;
}