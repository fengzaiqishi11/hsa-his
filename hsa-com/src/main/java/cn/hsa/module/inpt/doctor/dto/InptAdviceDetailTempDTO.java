package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.doctor.entity.InptAdviceDetailTempDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *@Package_name: cn.hsa.module.inpt.dto
 *@Class_name: InptAdviceDTO
 *@Describe: 住院医嘱明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdviceDetailTempDTO extends InptAdviceDetailTempDO implements Serializable {

    private static final long serialVersionUID = -2433100107270674149L;
    private String keywork;
    private String bigTypeCode;
    private String isWz;// 是否修改文字医嘱
    private String splitUnitCode;// 拆零单位
    private String bigUnitCode;// 大单位
    private String pharId;// 药房

}
