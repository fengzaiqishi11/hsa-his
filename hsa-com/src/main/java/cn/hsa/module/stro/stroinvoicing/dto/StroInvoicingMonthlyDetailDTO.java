package cn.hsa.module.stro.stroinvoicing.dto;

import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingMonthlyDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nowDate;
    // itemids
    private List<String> itemIds;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

}
