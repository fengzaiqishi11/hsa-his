package cn.hsa.module.inpt.pasttreat.dto;

import cn.hsa.module.inpt.pasttreat.entity.InptPastAdrsDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.entity
 * @Class_name:: InptPastAdrs
 * @Description: 不良反应史数据传输对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class InptPastAdrsDTO extends InptPastAdrsDO implements Serializable {
    private static final long serialVersionUID = -4966480351448786480L;
}
