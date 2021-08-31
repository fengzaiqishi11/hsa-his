package cn.hsa.module.inpt.pasttreat.dto;

import cn.hsa.module.inpt.pasttreat.entity.InptPastOperationDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.entity
 * @Class_name:: InptPastOperationDTO
 * @Description: 既往手术史数据传输对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class InptPastOperationDTO extends InptPastOperationDO implements Serializable {
    private static final long serialVersionUID = -7658885548945289769L;
    private String diseaseName;
}
