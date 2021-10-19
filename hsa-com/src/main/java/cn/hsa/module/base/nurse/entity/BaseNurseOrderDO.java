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
 * @Class_name: BaseNurseOrderDO
 * @Describe: 护理单据DO
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
public class BaseNurseOrderDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -314241285138743508L;
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
    private String code;
    /**
     * 护理单名称
     */
    private String name;
    /**
     * 是否指定科室（SF）
     */
    private String isAssign;
    /**
     * 是否竖向打印（SF）
     */
    private String isVertical;
    /**
     * 是否有效（SF）
     */
    private String isValid;
    /**
     * 科室ID列表
     */
    private String deptIds;
    /**
     * 每页行数
     */
    private Integer pgSize;
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
