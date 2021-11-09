package cn.hsa.module.insure.stock.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 盘存信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInventoryCheck   extends PageDO implements Serializable {



    private String id;
    private String hospCode;
    private String medListCodg	;//医疗目录编码	字符型	50		Y	新医保
    private String fixmedinsHilistId	;//定点医药机构目录编号	字符型	20	Y	Y	新医保/核3	核3：drug_code
    private String fixmedinsHilistName	;//定点医药机构目录名称	字符型	200		Y	新医保/核3	核3：drug_name
    private String rxFlag	;//处方药标志	字符型	3	Y　	Y　	新医保
    private String invdate	;//盘存日期	日期型	　	　	Y　	新医保
    private String invCnt	;//库存数量	数值型	16,2	　	Y　	新医保
    private String manuLotnum	;//生产批号	字符型	30	　	　	新医保
    private String fixmedinsBchno	;//定点医药机构批次流水号	字符型	30	　	Y　	新医保
    private String manuDate	;//生产日期	日期型	　	　	Y　	新医保
    private String expyEnd	;//有效期止	日期型	　	　	Y　	新医保
    private String memo	;//备注	字符型	500	　	　	新医保
    private String price;//单价	字符型	500	　	　	新医保
    private String cnt	;//盘存数量	字符型	500	　	　	新医保
    private String isUpload	;//是否上传	字符型	500	　	　



    /*med_list_codg	医疗目录编码
    fixmedins_hilist_id	定点医药机构目录编号
    fixmedins_hilist_name	定点医药机构目录名称
    rx_flag	处方药标志
    invdate	盘存日期
    inv_cnt	库存数量
    manu_lotnum	生产批号
    fixmedins_bchno	定点医药机构批次流水号
    manu_date	生产日期
    expy_end	有效期止
    memo	备注*/
}
