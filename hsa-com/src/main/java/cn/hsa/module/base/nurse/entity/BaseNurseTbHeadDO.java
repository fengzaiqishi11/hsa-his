package cn.hsa.module.base.nurse.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.nurse.entity
 * @Class_name: BaseNurseTbheadDO
 * @Describe: 护理单据表头格式DO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseNurseTbHeadDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -4084180358346327441L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 护理单编码
     */
    private String bnoCode;
    /**
     * 列顺序号
     */
    private Integer seqNo;
    /**
     * 上级编码
     */
    private String upCode;
    /**
     * 表头编码
     */
    private String code;
    /**
     * 表头名称
     */
    private String name;
    /**
     * 项目编码（19、护理记录明细表：扩展字段1-40）
     */
    private String itemCode;
    /**
     * 最小数值（为数值型时使用）
     */
    private Integer minNum;
    /**
     * 最大数值（为数值型时使用）
     */
    private Integer maxNum;
    /**
     * 性别限制代码
     */
    private String genderCode;
    /**
     * 数据类型代码（SJLX）
     */
    private String dateTypeCode;
    /**
     * 数据显示宽度
     */
    private Integer dataWidth;
    /**
     * 数据长度
     */
    private Integer dataLength;
    /**
     * 数据来源方式代码（SJLYFS）
     */
    private String sourceCode;
    /**
     * 数据来源方式值
     */
    private String sourceValue;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 是否上级标题（SF）
     */
    private String isUp;
    /**
     * 是否汇总
     */
    private String isSum;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
