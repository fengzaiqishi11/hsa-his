package cn.hsa.module.outpt.lis.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.outpt.lis.entity
 * @Class_name: CallbackCriticalValueDO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-08 16:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallbackCriticalValueDO implements Serializable {

    private String areaCode;
    private String patInfoId;
    private String testItemCode;
    private String criticalResult;
    private String criticalState;
    private String criticalTime;
}