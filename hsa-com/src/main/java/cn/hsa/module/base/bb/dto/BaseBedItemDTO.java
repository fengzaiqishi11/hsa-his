package cn.hsa.module.base.bb.dto;


import cn.hsa.module.base.bb.entity.BaseBedItemDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_ame: cn.hsa.module.base.bfc.dto
 * @Class_name: BaseBedDTO
 * @Description: 床位信息表据传输DTO对象
 * @Author: LJH
 * @Email: 30775310@qq.com
 * @Date: 2020/7/1 21:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class BaseBedItemDTO extends BaseBedItemDO implements Serializable {
}
