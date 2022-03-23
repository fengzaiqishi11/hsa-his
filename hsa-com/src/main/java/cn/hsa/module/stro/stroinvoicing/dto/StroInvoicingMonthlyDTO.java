package cn.hsa.module.stro.stroinvoicing.dto;

import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Class_name: StroInvoicingMonthlyDTO
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroInvoicingMonthlyDTO extends StroInvoicingMonthlyDO implements Serializable {
    private static final long serialVersionUID = -25487061965976799L;
    private String monthly;
}
