package cn.hsa.module.stro.stroin.dto;

import cn.hsa.module.stro.stroin.entity.StroInDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

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
@NoArgsConstructor
@ToString(callSuper = true)
public class StroInDetailDTO extends StroInDetailDO implements Serializable {
    private List<String> inIds;   //订单号
    private String orderNo;       //入库单号
    private String bizId;         //库位id
    private String prodName;      //生产厂家名称
    private String code;          //项目编码
    private static final long serialVersionUID = -25487061965973397L;
    private String model;       //材料型号
    /**
     * 供应商
     */
    private String supplierName;
}
