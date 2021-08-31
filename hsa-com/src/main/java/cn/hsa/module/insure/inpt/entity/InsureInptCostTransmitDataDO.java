package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptCostTransmitData
 * @Description: 医保统一支付：住院结算费用明细上传参数（第一部分）实体类
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 14:41
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptCostTransmitDataDO implements Serializable {
    private static final long serialVersionUID = -3021949507061179836L;
    // 医疗机构编码
    private String medinsCode ;
    // 个人电脑号
    private String psnNo ;
    //业务类型
    private String medType;
    // 就医登记号
    private String serialNo;
    // 录入人工号
    private String opter;
    // 录入人姓名
    private String opterName;
    // 处方号
    private String rxno ;
    // 处方医生编号
    private String bilgDrCode;
    // 处方医生姓名
    private String bilgDrName;
}
