package cn.hsa.module.inpt.pasttreat.dto;

import cn.hsa.module.inpt.pasttreat.entity.InptPastDrugDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.entity
 * @Class_name: InptPastDrugDTO
 * @Description: 既往用药史数据传输对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class InptPastDrugDTO extends InptPastDrugDO implements Serializable {
    private static final long serialVersionUID = 466367368525174209L;
    //  频次
    private String rateName;
    // 频率编码
    private String rateCode;
}
