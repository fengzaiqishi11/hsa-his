package cn.hsa.module.stro.incdec.dto;

import cn.hsa.module.stro.incdec.entity.StroIncdecDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.incdec.dto
 *@Class_name: StroIncdecDetailDTO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 9:21
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroIncdecDetailDTO extends StroIncdecDetailDO implements Serializable {

    private static final long serialVersionUID = -6067681424610554394L;

    //id集合
    private List<String> ids;
    //规格
    private String spec;
    //厂家名称
    private String prodName;
    // 编码
    private String code;
    // 材料型号
    private String model;
    //分类
    private String typeCode;
}
