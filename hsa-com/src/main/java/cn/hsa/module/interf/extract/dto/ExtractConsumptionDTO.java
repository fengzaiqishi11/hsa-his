package cn.hsa.module.interf.extract.dto;

import cn.hsa.module.interf.extract.entity.ExtractConsumptionDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/12 8:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractConsumptionDTO extends ExtractConsumptionDetailDO {

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 科室id集合
     */
    private List<String> deptList;
    /**
     * 项目名称/五笔码/拼音码
     */
    private String itemName;
    /**
     * 药房药库销售集合（行转列）
     * key:biz_id
     * value:ExtractConsumptionDTO
     */
    private Map<String,ExtractConsumptionDTO> extractConMap;
}
