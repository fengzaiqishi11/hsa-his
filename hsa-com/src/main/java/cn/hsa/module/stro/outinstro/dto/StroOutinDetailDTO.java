package cn.hsa.module.stro.outinstro.dto;

import cn.hsa.module.stro.outinstro.entity.StroOutinDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.store.instore.dto
 * @Class_name: StroOutinDetailDto
 * @Describe:  药品出入库明细数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/21 21:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class StroOutinDetailDTO extends StroOutinDetailDO implements Serializable {
    private static final long serialVersionUID = -25487061965973397L;
}
