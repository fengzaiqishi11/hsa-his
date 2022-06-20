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
public class InsureGoodSell   extends PageDO implements Serializable {
    private String medListCodg	;//医疗目录编码	字符型	50	　	Y　	新医保
    private String fixmedinsHilistId	;//定点医药机构目insureRegCode录编号	字符型	30	　	Y　	新医保/核3	核3：drugCode
    private String fixmedinsHilistName	;//定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3：drugName
    private String fixmedinsBchno	;//定点医药机构批次流水号	字符型	30	　	Y　	新医保
    private String prscDrCertType	;//开方医师证件类型	字符型	6	Y　	　	新医保
    private String prscDrCertno	;//开方医师证件号码	字符型	50	　	　	新医保
    private String prscDrName	;//开方医师姓名	字符型	50	　	Y　	新医保
    private String pharCertType	;//药师证件类型	字符型	6	Y　	　	新医保
    private String pharCertno	;//药师证件号码	字符型	50	　	　	新医保
    private String pharName	;//药师姓名	字符型	50	　	Y　	新医保
    private String pharPracCertNo	;//药师执业资格证号	字符型	50	　	Y　	新医保
    private String hiFeesetlType	;//医保费用结算类型	字符型	6	Y　	　	新医保
    private String setlId	;//结算ID	字符型	30	　	　	新医保
    private String mdtrtSn	;//就医流水号	字符型	30	　	Y	新医保
    private String psnNo	;//人员编号	字符型	30	　	　	新医保
    private String psnCertType	;//人员证件类型	字符型	6	Y　	　	新医保
    private String certno	;//证件号码	字符型	50	　	　	新医保
    private String psnName	;//人员姓名	字符型	50	　	　	新医保
    private String manuLotnum	;//生产批号	字符型	30	　	Y　	新医保
    private String manuDate	;//生产日期	日期型	　	　	Y　	新医保
    private String expyEnd	;//有效期止	日期型	　	　	　	新医保
    private String rxFlag	;//处方药标志	字符型	3	Y　	Y　	新医保
    private String trdnFlag	;//拆零标志	字符型	3	Y　	Y　	新医保
    private String finlTrnsPric	;//最终成交单价	数值型	16,6	　	　	新医保/核三	核三：akc225
    private String rxno	;//处方号	字符型	40			新医保
    private String rxCircFlag	;//外购处方标志	字符型	3	Y		新医保
    private String rtalDocno	;//零售单据号	字符型	40		Y	新医保/核3	核3：aae072
    private String stooutNo	;//销售出库单2据号	字符型	40			新医保
    private String bchno	;//批次号	字符型	30			新医保
    private String drugTracCodg	;//药品追溯码	字符型	30			新医保
    private String drugProdBarc	;//药品条形码	字符型	30			新医保
    private String shelfPosi	;//货架位	字符型	20			新医保
    private String selRetnCnt	;//销售/退货数量	数值型	16,4	　	Y　	新医保/核三	核三：akc226
    private String selRetnTime	;//销售/退货时间	日期时间型	　	　	Y　	新医保
    private String selRetnOpterName	;//销售/退货经办人姓名	字符型	50	　	Y　	新医保
    private String memo	;//备注	字符型	500	　	　	新医保/核三	核三：aae013
    private String hospCode;// 医院编码
    private String numUnitCode;// 数量单位
    private String sellType;// 销售类型
    private String visitId;// 就诊id
    private String keyword;
    private String insureItemCode;// 医保中心编码
    private String insureItemName;// 医保中心项目名称
    private String insureRegCode;// 医保机构编码
    /**
     * 结算的开始时间
     */
    private String startTime;
    /**
     * 结算结束时间
     */
    private String endTime;

}
