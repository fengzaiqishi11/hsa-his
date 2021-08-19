package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.doctor.entity.InptBabyDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.inpt.doctor.dto
 * @Class_name: InptBabyDTO
 * @Describe(描述): 住院婴儿实体 DTO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/01 9:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptBabyDTO extends InptBabyDO implements Serializable {
    //序列号
    private static final long serialVersionUID = 4712536984493561287L;
    /**
     * keyword
     */
    private String keyword;

    private BigDecimal totalBalance; // 总费用
}
