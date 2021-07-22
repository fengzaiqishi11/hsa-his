package cn.hsa.module.base.bw.entity;

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
base：基础模块
window：药房窗口

表说明：
                     (BaseWindow)实体类
 *
 * @author xingyu.xie
 * @since 2020-07-23 14:09:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseWindowDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 805686937484800127L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 科室编码（表base_dept）
    */
    private String deptCode;
    /**
    * 窗口编码
    */
    private String code;
    /**
    * 窗口名称
    */
    private String name;
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
    // 时间戳转换为标准时间格式
    private Date crteTime;



}