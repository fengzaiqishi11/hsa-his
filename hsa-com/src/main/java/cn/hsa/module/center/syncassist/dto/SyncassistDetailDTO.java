package cn.hsa.module.center.syncassist.dto;


import cn.hsa.module.center.syncassist.entity.SyncassistDetailDO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.center.syncassist.dto
 * @Class_name:: SyncassistDetailDTO
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/6 17:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class SyncassistDetailDTO extends SyncassistDetailDO implements Serializable {
    //单价
    private BigDecimal price;
    //单位
    private String unitCode;
    //规格
    private String spec;
}
