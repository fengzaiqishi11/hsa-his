package cn.hsa.module.emr.emrclassifyelement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名含义：
 * emr:电子病历缩写
 * classify：文档分类
 * element：元素
 * (EmrClassifyElement)实体类
 *
 * @author xingyu.xie
 * @since 2020-09-25 10:40:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrClassifyElementDO implements Serializable {
    private static final long serialVersionUID = -13046039349282877L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病历文档分类编码（emr_classify.code）
     */
    private String classinfoCode;
    /**
     * 元素编码（emr_element.code）
     */
    private String elementCode;
    /**
     * 使用科室id
     */
    private String deptId;
    /**
     * 是否有效
     */
    private String isValid;
    /**
     * 创建人id
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