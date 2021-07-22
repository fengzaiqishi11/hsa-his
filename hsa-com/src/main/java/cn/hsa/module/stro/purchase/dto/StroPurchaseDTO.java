package cn.hsa.module.stro.purchase.dto;

import cn.hsa.module.stro.purchase.entity.StroPurchaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.stro.purchase.dto
 * @Class_name: StroPurchaseDTO
 * @Describe: 采购计划累加参数实体
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroPurchaseDTO extends StroPurchaseDO implements Serializable {
    private static final long serialVersionUID = 890629419097358533L;
    /* 采购明细集合参数 */
    private List<StroPurchaseDetailDTO> stroPurchaseDetails;
    /* 审核开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTimeStart;
    /* 审核结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTimeEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String supplierName;
    private String bizName;
    private String keyword;
    private String[] ids;
}
