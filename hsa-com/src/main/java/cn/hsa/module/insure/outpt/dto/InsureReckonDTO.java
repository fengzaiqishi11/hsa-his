package cn.hsa.module.insure.outpt.dto;

import cn.hsa.module.insure.outpt.entity.InsureReckonDO;
import cn.hsa.module.insure.outpt.entity.InsureReversalTradeDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/** 清算申请
 * @Package_name:
 * @Class_name: InsureReckonDO
 * @Describe:
 * @Author: liaojiguang
 * @Email: liaojiguang@powersi.com.cn
 * @Date: 2021/9/8 17:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureReckonDTO extends InsureReckonDO implements Serializable {
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;

    // 医疗机构名称
    private String hospName;

    // 医疗机构编码
    private String fixmedinsCode;

    // 医疗机构名称
    private String fixmedinsName;

    // 清算id
    private String feeClrId;

    // 清算状态
    private String clrStas;
}
