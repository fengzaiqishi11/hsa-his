package cn.hsa.module.inpt.pasttreat.dto;

import cn.hsa.module.inpt.pasttreat.entity.InptPastTreatDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.entity
 * @Class_name:: InptPastTreatDTO
 * @Description:  既往诊疗史数据传输对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class InptPastTreatDTO extends InptPastTreatDO implements Serializable {
    private static final long serialVersionUID = -16443362147413508L;
    private String diseaseName;
}
