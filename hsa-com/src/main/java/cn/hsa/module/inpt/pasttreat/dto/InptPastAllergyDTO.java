package cn.hsa.module.inpt.pasttreat.dto;

import cn.hsa.module.inpt.pasttreat.entity.InptPastAllergyDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.entity
 * @Class_name:: InptPastAllergyDTO
 * @Description: 既往过敏史数据传输对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class InptPastAllergyDTO extends InptPastAllergyDO implements Serializable {
    private static final long serialVersionUID = 1524959023341297523L;
}
