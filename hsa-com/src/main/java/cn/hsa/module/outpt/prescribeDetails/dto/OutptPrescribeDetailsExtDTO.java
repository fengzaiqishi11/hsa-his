package cn.hsa.module.outpt.prescribeDetails.dto;

import cn.hsa.module.outpt.prescribeDetails.entity.OutptPrescribeDetailsExtDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeDetails.entity
 * @Class_name: OutptPrescribeDetailsDO
 * @Describe: 门诊处方明细执行
 * @Author: zengfeng
 * @Email: zengfeng@powersi.com.cn
 * @Date: 2020/9/8 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptPrescribeDetailsExtDTO extends OutptPrescribeDetailsExtDO {
    /**
     * 搜索条件(姓名/就诊号)
     */
    private String keyword;
    //财务分类
    private String bfcCode;
    //财务分类ID
    private String bfcId;
    //费用来源
    private String sourceCode;
    //医嘱类别
    private String yzlb;
    //是否计费
    private String isCost;
}

