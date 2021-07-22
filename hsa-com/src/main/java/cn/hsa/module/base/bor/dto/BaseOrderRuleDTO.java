package cn.hsa.module.base.bor.dto;

import cn.hsa.module.base.bor.entity.BaseOrderRuleDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.base.bor.entity
 * @Class_name: BaseOrderRuleDTO
 * @Description: 单据生成规则数据传输DTO对象
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/07/13 20:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class BaseOrderRuleDTO extends BaseOrderRuleDO implements Serializable {
}
