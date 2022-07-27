package cn.hsa.module.interf.extract.dto;

import cn.hsa.base.PageDO;
import cn.hsa.module.interf.extract.entity.ExtractStroInvoicingDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 同步药房实时进销存报表(ExtractStroInvoicingDetail)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractStroInvoicingDetailDTO extends ExtractStroInvoicingDetailDO implements Serializable {
    private static final long serialVersionUID = 869890362720398685L;

    private String deptName;

    private String keyWord;

    /**
     *  0:药库实时进销存查询,1:药房实时进销存查询
     */
    private String type;

    /**
     *  药品分类
     */
    private String drugType;

    /**
     *  材料分类
     */
    private String materialType;

    /**
     * 0：零售价，1：购进价
     */
    private String buyOrSell;


    private List<String> ids;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    // 期初零售总金额
    private BigDecimal upSellPrice;
    // 期末零售总金额
    private BigDecimal sellPrice;
    // 期初购进总金额
    private BigDecimal upBuyPrice;
    // 期末购进总金额
    private BigDecimal buyPrice;
}

