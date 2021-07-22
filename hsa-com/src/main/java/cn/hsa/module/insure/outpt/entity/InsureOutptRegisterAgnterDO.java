package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptRegisterAgnterDO
 * @Describe: 统一支付平台-门诊挂号-输入（代办人信息agnterinfo）
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-09 13:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptRegisterAgnterDO implements Serializable {
    private static final long serialVersionUID = 636728682251061880L;
    private String agnterName; //代办人姓名
    private String agnterRlts; //代办人关系
    private String agnterCertType; //代办人证件类型
    private String agnterCertno; //代办人证件号码
    private String agnterTel; //代办人联系电话
    private String agnterAddr; //代办人联系地址
    private String agnterPhoto; //代办人照片
}
