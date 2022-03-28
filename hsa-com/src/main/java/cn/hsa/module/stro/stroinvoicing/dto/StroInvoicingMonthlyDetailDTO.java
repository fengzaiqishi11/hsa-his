package cn.hsa.module.stro.stroinvoicing.dto;

import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Class_name: StroInvoicingMonthlyDetailDTO
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/28 11:10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroInvoicingMonthlyDetailDTO extends StroInvoicingMonthlyDetailDO implements Serializable {
    private String monthly;
    // 当前时间
    private Date date;
    // itemids
    private List<String> itemIds;
}
