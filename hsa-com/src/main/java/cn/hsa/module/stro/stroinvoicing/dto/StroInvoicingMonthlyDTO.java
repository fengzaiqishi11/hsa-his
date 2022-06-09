package cn.hsa.module.stro.stroinvoicing.dto;

import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    // 当前时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nowDate;
    private String monthlyId;
    // 明细
    List<StroInvoicingMonthlyDetailDTO> stroInvoicingDetails;
}
