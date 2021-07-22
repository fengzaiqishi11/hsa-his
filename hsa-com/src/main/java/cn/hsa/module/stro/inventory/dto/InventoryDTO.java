package cn.hsa.module.stro.inventory.dto;


import cn.hsa.module.stro.inventory.entity.InventoryDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ljh
 * @date 2020/07/21.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InventoryDTO extends InventoryDO implements Serializable {
    private static final long serialVersionUID = -26541423748732454L;
    private List<StroInventoryDetailDTO> stroInventoryDetailDTOList;
    private String starttime;
    private String endtime;
    private List<String> ids;
    private String keyword;

    //库房名称
    private String bizName;
    //实盘数量
    private BigDecimal finalNum;
    //盈亏比列
    private String rate;
    //盈亏数量
    private BigDecimal incdecNum;
    //盘点时间
    private String incentoryTime;
}
