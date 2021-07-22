package cn.hsa.module.outpt.daily.dto;

import cn.hsa.module.outpt.daily.entity.OutinDailyAdvancePayDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * (OutinDailyPay)实体类
 *
 * @author zhongming
 * @since 2020-09-24 14:25:02
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class OutinDailyAdvancePayDTO extends OutinDailyAdvancePayDO implements Serializable {
}