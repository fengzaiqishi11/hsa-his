package cn.hsa.module.insure.stock.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInventoryStockUpdate extends PageDO implements Serializable {
    private String medListCodg;//医疗目录编码
    private String invChgType;//库存变更类型
    private String fixmedinsHilistId;//定点医药机构目录编号
    private String fixmedinsHilistName;//定点医药机构目录名称
    private String fixmedinsBchno;//定点医药机构批次流水号
    private String pric;//单价
    private String cnt;//数量
    private String rxFlag;//处方药标志
    private String invChgTime;//库存变更时间
    private String invChgOpterName;//库存变更经办人姓名
    private String memo;//备注
    private String trdnFlag;//拆零标志
    private String hospCode;
    private List<String> outinCodeList;
    private String keyword;
    private String currUnitCode;
    private String insureItemCode;// 医保中心编码
    private String insureItemName;// 医保中心项目名称
    private String insureRegCode;// 医保机构编码
    private String itemCode;//项目类别1药品 2耗材
}
