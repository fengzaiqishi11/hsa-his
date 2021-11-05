package cn.hsa.module.insure.stock.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInventoryStockUpdate extends PageDO implements Serializable {
    private String medListCodg	;//医疗目录编码	字符型	50	　	Y　	新医保
    private String invChgType	;//库存变更类型	字符型	6	Y　	Y　	新医保
    private String fixmedinsHilistId	;//定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：drug_code
    private String fixmedinsHilistName	;//定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3：drug_name
    private String fixmedinsBchno	;//定点医药机构批次流水号	字符型	30	　	Y　	新医保
    private String pric	;//单价	数值型	16,6	　	Y　	新医保/核三	核3：akc225
    private String cnt	;//数量	数值型	16,4	　	Y　	新医保/核三	核3：akc226
    private String rxFlag	;//处方药标志	字符型	3	Y　	Y　	新医保
    private String invChgTime	;//库存变更时间	日期时间型	　	　	Y　	新医保
    private String invChgOpterName	;//库存变更经办人姓名	字符型	50	　	　	新医保
    private String memo	;//备注	字符型	500	　	　	新医保
    private String trdnFlag	;//拆零标志	字符型	2			新医保
    private String hospCode;
}
