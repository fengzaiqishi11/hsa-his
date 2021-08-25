package cn.hsa.module.stro.purchase.dto;

import cn.hsa.module.stro.purchase.entity.StroPurchaseDetailDO;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.stro.purchase.dto
 * @Class_name: StroPurchaseDetailDTO
 * @Describe: 采购计划明细累加参数实体
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@ToString
public class StroPurchaseDetailDTO extends StroPurchaseDetailDO implements Serializable {
    private static final long serialVersionUID = 890629419097358533L;
    private String strIds;//字符串ids，以逗号分隔
    private String[] ids;//ids
    private String itemCodeName;//项目类型代码名称
    private String prodName;//生产厂家每名称
    private String supplierId; //供应商id
    private String bizId;
}
