package cn.hsa.module.inpt.bedlist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 录入：只能录入项目(InptLongCost)实体类
 *
 * @author chenjun
 * @since 2020-09-09 16:31:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptLongCostDO implements Serializable {
    private static final long serialVersionUID = -56640335538618084L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 科室ID
     */
    private String deptId;
    /**
     * 项目ID
     */
    private String itemId;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 项目总金额
     */
    private BigDecimal totalPrice;
    /**
     * 最近执行日期
     */
    private Date lastExecTime;
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 来源类型代码（LYLX）
     */
    private String sourceTypeCode;
    /**
     * 来源ID（床位ID）
     */
    private String sourceId;
    /**
     * 是否取消（SF）
     */
    private String isCancel;
    /**
     * 取消人ID
     */
    private String cancelId;
    /**
     * 取消人姓名
     */
    private String cancelName;
    /**
     * 取消时间
     */
    private Date cancelTime;
    /**
     * 取消备注
     */
    private String cancelRemark;
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

    /**
     * 用药性质
     */
    private String useCode;
    /** 发药药房 **/
    private String pharId;
    /**
     * 项目类别（XMLB）
     */
    private String itemCode;

    /**
     * 药房ID（XMLB）
     */
    private String pharId;
}
