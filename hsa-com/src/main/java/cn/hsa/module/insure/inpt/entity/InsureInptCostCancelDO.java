package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptCostCancelDO
 * @Description: 住院费用明细撤销
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 15:34
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptCostCancelDO implements Serializable {
    // 费用明细流水号
    private String feedetlSn;
    // 就诊ID
    private String mdtrtId;
    // 人员编号
    private String psnNo ;
    // 医疗机构编码
    private String fixmedinsCode;

}
