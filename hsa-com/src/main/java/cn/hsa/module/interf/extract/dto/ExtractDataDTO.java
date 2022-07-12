package cn.hsa.module.interf.extract.dto;

import cn.hsa.module.interf.extract.entity.ExtractDataDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gory
 * @date 2022/7/6 14:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ExtractDataDTO extends ExtractDataDO  implements Serializable {
    private static final long serialVersionUID = -2122671344737728200L;
    private String startTime;
}
