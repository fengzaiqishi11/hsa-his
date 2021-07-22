package cn.hsa.module.base.bb.dto;

import cn.hsa.module.base.bb.entity.BaseBedDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_ame: cn.hsa.module.base.bfc.dto
 * @Class_name: BaseBedDTO
 * @Description: 床位信息表据传输DTO对象
 * @Author: LJH
 * @Email: 30775310@qq.com
 * @Date: 2020/7/1 21:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseBedDTO extends BaseBedDO implements Serializable {
    private static final long serialVersionUID = 500823640970529987L;
    List<String> ids;
    List<BaseBedItemDTO> BaseBedItemDTO;

    private String deptCodeName;
    private Integer min;
    private Integer max;
    private Boolean isBatch;

    private String visitFlag;

    private String deptId;
    private List<BaseBedDTO> baseBedDTOS;
}
