package cn.hsa.module.oper.operInforecord.dto;


import cn.hsa.module.oper.operInforecord.entity.OperAccountTempDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.oper.operInforecord.dto
 *@Class_name: OperAccountTempDetailDTO
 *@Describe:手术补记账模板明细(OperAccountTempDetail)表数据库传输层
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/12/4 17:25
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OperAccountTempDetailDTO extends OperAccountTempDetailDO implements Serializable {

    private static final long serialVersionUID = 6128366261732083066L;
    //id集合
    List<String> ids;
    //单位
    private String company;
    //价格
    private BigDecimal price;
    //金额
    private  BigDecimal totalPrice;
    //主键id
    private String tempDetailId;
    //药品大类
    private String bigTypeCode;
    //发药药房
    private String pharName;
    // 类型
    private String type;
    // 住院单位
    private String inUnitCode;
}
