package cn.hsa.module.stro.adjust.dto;

import cn.hsa.module.stro.adjust.entity.StroAdjustDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 *@Package_name: cn.hsa.module.stro.adjust.dto
 *@Class_name: StroAdjustDetailDTO
 *@Describe: 调价明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/2 8:30
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroAdjustDetailDTO extends StroAdjustDetailDO implements Serializable {

    private static final long serialVersionUID = -95796487426240168L;

    //id集合
    private List<String> ids;
    //材料code
    private String materialCode;
    //项目编码
    private String code;
    //项目规格
    private String spec;
    //生产厂家
    private String prodName;
    // 调价后零售总金额
    private BigDecimal afterPriceAll;
    //调价前零售总金额
    private BigDecimal beforePriceAll;
    // 材料型号
    private String model;
    // 是否过滤科室库存
    private String sfdeptFilter;
    // 库位名称
    private String bizName;
    //材料药品分类
    private String typeCode;
}
