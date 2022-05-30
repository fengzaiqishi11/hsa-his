package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 违规详细
 */
@Data
public class AnaResJudgeDetlDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 违规明细标识
     */
    @JSONField(name = "jrd_id")
    private String jrdId;
    /**
     * 参保人ID
     */
    @JSONField(name = "patn_id")
    private String patnId;
    /**
     * 就诊ID
     */
    @JSONField(name = "mdtrt_id")
    private String mdtrtId;
    /**
     * 处方(医嘱)标识
     */
    @JSONField(name = "rx_id")
    private String rxId;
    /**
     * 违规明细类型
     */
    @JSONField(name = "vola_item_type")
    private String volaItemType;
    /**
     * 违规明细类型
     */
    private String volaItemTypeName;
    /**
     * 违规金额
     */
    @JSONField(name = "vola_amt")
    private BigDecimal volaAmt;
}
