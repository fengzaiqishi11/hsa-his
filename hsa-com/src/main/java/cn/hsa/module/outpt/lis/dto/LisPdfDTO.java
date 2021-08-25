package cn.hsa.module.outpt.lis.dto;

import cn.hsa.module.outpt.lis.entity.LisPdfDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.outpt.lis.dto
 * @Class_name: LisPdfDTO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-07 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LisPdfDTO extends LisPdfDO {
    private String a ;
}