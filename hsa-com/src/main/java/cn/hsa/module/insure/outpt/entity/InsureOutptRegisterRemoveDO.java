package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptRegisterRemoveDO
 * @Describe: 统一支付平台-门诊挂号撤销-输入（节点标识：data）
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-09 14:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptRegisterRemoveDO implements Serializable {
    private static final long serialVersionUID = 3446463592611387172L;
    private String mdtrtId; //就诊ID
    private String psnNo; //人员编号
    private String iptOtpNo; //住院/门诊号
    private String mdtrtMode; //就诊方式
}
