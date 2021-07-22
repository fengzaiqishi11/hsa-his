package cn.hsa.module.base.ba.dto;

import cn.hsa.module.base.ba.entity.BaseAdviceDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dto
 * @Class_name: BaseAdviceDTO
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseAdviceDetailDTO extends BaseAdviceDetailDO implements Serializable {
    // 搜索关键字
    private String keyword;
     //项目或材料ID
    private String itemId;
    // 项目或材料名称
    private String itemName;
    // 项目或材料的类别
    private String itemTypeCode;
    // 门诊科室
    private String outDeptName;
    // 住院科室
    private String inDeptName;
    // 门诊科室
    private String outDeptCode;
    // 住院科室
    private String inDeptCode;
    // 删除的ids列表
    private List<String> ids;
    // 频率名称
    private String rateName;
    // 医嘱名称
    private String adviceName;
    // 财务分类id
    private String bfcId;
    // 财务分类名称
    private String bfcName;
    //疾病大类
    private String bigTypeCode;
    //剂量
    private String dosage;

    private String icd9;          //疾病icd9
    private String operLevel;     //疾病级别

    private String queryCode; //查询类型 1：查询出有库存的数据      空或其它：表示没有库存限制
    private String dosageUnitCode;
    private String prepCode;
    private String loginDeptId;
    private String pharId;
    private String inUnitCode;//住院单位
    private String type;//类型

    private BigDecimal splitPrice;  //拆零单价

    private String splitUnitCode; // 拆零单位

}
