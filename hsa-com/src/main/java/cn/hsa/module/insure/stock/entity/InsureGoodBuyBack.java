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
public class InsureGoodBuyBack  extends PageDO implements Serializable {
    private String medListCodg	;// 医疗目录编码	字符型	50	　	Y　	新医保
    private String fixmedinsHilistId	;// 定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：drug_code
    private String fixmedinsHilistName	;// 定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3：drug_name
    private String fixmedinsBchno	;// 定点医药机构批次流水号	字符型	30	　	Y　	新医保
    private String splerName	;// 供应商名称	字符型	200	　	Y　	新医保
    private String splerPmtno	;// 供应商许可证号	字符型	50	　	　	新医保
    private String manuDate	;// 生产日期	日期型	　	　	Y　	新医保
    private String expyEnd	;// 有效期止	日期型	　	　	Y　	新医保
    private String finlTrnsPric	;// 最终成交单价	数值型	16,6	　	　	新医保/核三	核三：akc225
    private String purcRetnCnt	;// 采购/退货数量	数值型	16,4	　	Y　	新医保/核三	核三：akc226
    private String purcInvoCodg	;// 采购发票编码	字符型	50	　	　	新医保
    private String purcInvoNo	;// 采购发票号	字符型	50	　	Y　	新医保
    private String rxFlag	;// 处方药标志	字符型	3	Y　	Y　	新医保
    private String purcRetnStoinTime	;// 采购/退货入库时间	日期时间型	　	　	Y　	新医保/核三	核三：aae036
    private String purcRetnOpterName	;// 采购/退货经办人姓名	字符型	50	　	Y　	新医保
    private String memo	;// 备注	字符型	500	　	　	新医保
    private String medinsProdPurcNo	;// 商品采购流水号	字符型	50			新医保/核3	核3：aae072
    private String hospCode;
    private String keyword;
}
