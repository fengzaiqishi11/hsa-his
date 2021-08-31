package cn.hsa.module.outpt.lis.dto;

import cn.hsa.module.outpt.lis.entity.LisInspectApplyActiveDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.lis.dto
 * @Class_name: InspectApplyActiveDTO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-07 09:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LisInspectApplyActiveDTO extends LisInspectApplyActiveDO {

    private List<Map> resultlist;

    private String resultlistStr;
}