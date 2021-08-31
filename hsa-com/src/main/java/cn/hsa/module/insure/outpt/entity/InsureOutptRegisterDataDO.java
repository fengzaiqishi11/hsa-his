package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptRegisterDataDO
 * @Describe: 统一支付平台-门诊挂号-输入（节点标识：data）
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-09 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptRegisterDataDO implements Serializable {
    private static final long serialVersionUID = -5870845253584509611L;

    private String psnNo; //人员编号
    private String insutype; //险种类型
    private Date begntime; //开始时间
    private String mdtrtCertType; //就诊凭证类型
    private String mdtrtCertNo; //就诊凭证编号
    private String iptOtpNo; //住院/门诊号
    private String atddrNo; //医师编码
    private String drName; //医师姓名
    private String deptCode; //科室编码
    private String deptName; //科室名称
    private String caty; //科别
}
