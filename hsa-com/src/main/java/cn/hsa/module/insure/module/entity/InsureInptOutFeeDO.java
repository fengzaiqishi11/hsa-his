package cn.hsa.module.insure.module.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
/**
 * @Package_name: cn.hsa.module.insure.insureInptFee.entity
 * @Class_name:: insureOutptOutFeeDO
 * @Description: 退费时提取门诊业务信息入参实体类
 * @Author: 廖继广
 * @Date: 2020/10/19　　
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptOutFeeDO implements Serializable {

    /**
    * 交易号
    */
    private String function_id;

    /**
    * 医药机构编码
    */
    private String akb020;

    /**
    * 就医登记号
    */
    private String aaz217;



}