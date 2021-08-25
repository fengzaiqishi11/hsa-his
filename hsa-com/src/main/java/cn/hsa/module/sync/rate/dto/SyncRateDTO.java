package cn.hsa.module.sync.rate.dto;

import cn.hsa.module.sync.rate.entity.SyncRateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @PackageName: cn.hsa.module.base.rate.dto
 * @Class_name: SyncRateDTO
 * @Description: 医嘱频率数据传输DTO对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 10:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncRateDTO extends SyncRateDO implements Serializable {
    private static final long serialVersionUID = -816359272277325373L;
    private String keyword;  //医嘱频率查询参数
    private List<String> rateIdList; // 医嘱频率id集合 用来做频率修改标识符使用
}
