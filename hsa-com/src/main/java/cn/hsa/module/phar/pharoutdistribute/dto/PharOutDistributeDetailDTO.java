package cn.hsa.module.phar.pharoutdistribute.dto;

import cn.hsa.module.phar.pharoutdistribute.entity.PharOutDistributeDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.phar.phardistribute.dto
 * @Class_name: PharDistributeDetailDTO
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/31 15:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharOutDistributeDetailDTO extends PharOutDistributeDetailDO implements Serializable {
    private static final long serialVersionUID = 858795411707833982L;

    private String keyword;
    // 可退药数量
    private BigDecimal backNumOk;

    // 进销存目标名称
    private String invoicingTargetName;
}
