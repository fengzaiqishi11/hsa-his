package cn.hsa.module.mris.mrisHome.dto;

import cn.hsa.module.mris.mrisHome.entity.InptBedChangeDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.mris.mrisHome.dto
 * @Class_name:: InptBedChangeInfoDTO
 * @Description: 床位异动信息dto
 * @Author: zhangxuan
 * @Date: 2020-10-15 14:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class InptBedChangeInfoDTO extends InptBedChangeDO implements Serializable {
    private String keywords;
}
