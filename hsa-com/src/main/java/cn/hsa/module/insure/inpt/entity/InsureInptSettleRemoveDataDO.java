package cn.hsa.module.insure.inpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.inpt
 * @Class_name: InsureInptSettleRemoveDataDO
 * @Describe: 统一支付平台-住院结算撤销-输入（节点标识：data）
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-09 15:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptSettleRemoveDataDO implements Serializable {
    private static final long serialVersionUID = 5825867951243188211L;
    private String mdtrtId; //就诊ID
    private String setlId; //结算ID
    private String psnNo; //人员编号
    private String medinsCode; //医疗机构编码
    private String serialNo; //就医登记号
}
