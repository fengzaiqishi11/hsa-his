package cn.hsa.module.inpt.bedlist.dto;

import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.inpt.bedlist.entity.InptLongCostDO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.bedlist.dto
 * @Class_name: InptLongCostDto
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/9 16:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptLongCostDTO extends InptLongCostDO {
    private  List<String> bedIdList;

    private InptVisitDTO inptVisit;

    private BaseItemDTO baseItem;

    private String bfcId ;

    private String bfcName ;

    private BigDecimal totalNum;

    private String changeCode;
}
