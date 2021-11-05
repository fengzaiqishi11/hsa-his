package cn.hsa.module.insure.stock.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureGoodInfoDelete extends PageDO implements Serializable {
    private String fixmedinsBchno;//定点医药机构批次流水号	字符型	30	　	Y　
    private String invDataType;//	进销存数据类型	字符型	30	Y	Y	1-盘存信息；2-库存变更信息；3-采购信息；4-销售信息
    private String id;
    private String insureType;
    private String hospCode;
    private String itemName;
    private Date uploadTime;
    private String certId;
    private String certName;
}
