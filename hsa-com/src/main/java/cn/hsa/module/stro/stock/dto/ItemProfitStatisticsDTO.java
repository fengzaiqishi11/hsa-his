package cn.hsa.module.stro.stock.dto;

import cn.hsa.module.stro.stock.entity.StroStockDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目（药品/材料）利润统计传输对象
 * @author liudawen
 * @date 2022/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemProfitStatisticsDTO extends StroStockDetailDO implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 业务类型编码
     */
    private String bizCode;

    /**
     * 科室id
     */
    private String deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 财务分类名称
     */
    private String bfcName;

    /**
     * 汇总方式 1：按批号和项目  2.按批号、项目和科室
     */
    private String sumCode;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 项目规格
     */
    private String itemSpec;

    /**
     * 销售数量 = 结算数量-退费数量
     */
    private BigDecimal sellCount;

    /**
     * 项目总利润 = （零售单价-进货单价） * 销售数量
     */
    private BigDecimal profit;

    /**
     * 利润率 = 零售总金额 /进货总金额 * 10
     */
    private BigDecimal profitRate;
    /**
     * 编码
     */
    private String code;
    /**
     * 零售单价单位
     */
    private String sellUnitCode;
    /**
     * 零售拆零单价
     */
    private BigDecimal sellSplitPrice;
    /**
     * 零售拆零单价单位
     */
    private String sellSplitUnitCode;

}
