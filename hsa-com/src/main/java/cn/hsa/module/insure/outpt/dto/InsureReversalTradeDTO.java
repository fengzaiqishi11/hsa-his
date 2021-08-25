package cn.hsa.module.insure.outpt.dto;

import cn.hsa.module.insure.outpt.entity.InsureReversalTradeDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**冲正交易记录
 *@Package_name: cn.hsa.module.insure.outpt.dto
 *@Class_name: InsureReversalTrade
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 15:55
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureReversalTradeDTO extends InsureReversalTradeDO implements Serializable {
    private static final long serialVersionUID = 1367912535823762683L;

    //医保注册编码
    private String insureRegCode;
    //患者名称
    private String psnName;
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;
}
