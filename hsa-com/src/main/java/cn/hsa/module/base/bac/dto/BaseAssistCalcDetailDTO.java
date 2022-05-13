package cn.hsa.module.base.bac.dto;

import cn.hsa.module.base.bac.entity.BaseAssistCalcDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ljh
 * @date 2020/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseAssistCalcDetailDTO extends BaseAssistCalcDetailDO implements Serializable {
    private String itemId;
    private String itemName;
    private BigDecimal price;
    private String bfcCode;
    private String unitName;
    private String spec;

    private String Zdksbz;
    private String Zdxmbz;
    //
    private String itemTypeCode;
    private String itemUnitCode;
    private String bfcId;
    //主表ID
    private String assistId;
    private String name;
    private String splitPrice;
    private String splitUnitCode;
    private String  checkUnitCode;

}
