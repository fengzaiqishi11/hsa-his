package cn.hsa.module.outpt.lis.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.lis.entity
 * @Class_name: LisCallbackStatusDO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-07 10:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LisCallbackStatusDO implements Serializable {

    private String barcode;

    private String patInfoId;

    private String hisId;

    private String feeItemName;

    private String feeItemCode;
    /**
     * 采集、 接收、 上机、 发布等
     */
    private String status;

    private Date date;

    private String crteId;

    private String crteName;

    private Date crteTime;
}