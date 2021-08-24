package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.stock.bo.InsureStockManagerBO;
import cn.hsa.module.insure.stock.dao.InsureStockManagerDAO;
import cn.hsa.module.insure.stock.entity.*;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InsureStockManagerBoImpl extends HsafBO implements InsureStockManagerBO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    InsureStockManagerDAO insureStockManagerDAO;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    /**
     * 查询商品采购信息
     *
     * @param insureGoodBuy
     * @return
     */
    @Override
    public List<InsureGoodBuy> queryInsureGoodBuyPage(InsureGoodBuy insureGoodBuy) {
        PageHelper.startPage(insureGoodBuy.getPageNo(), insureGoodBuy.getPageSize());
        return insureStockManagerDAO.queryInsureGoodBuyPage(insureGoodBuy);
    }

    /**
     * 批量修改商品采购信息
     *
     * @param map
     * @return
     */
    @Override
    public Boolean uploadInsureGoodBuy(Map<String, Object> map) {
        String hospCode = MapUtils.getEmptyErr(map, "hospCode", "医院编码不能为空！");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        List<InsureGoodBuy> listInsureGoodBuy = MapUtils.getEmptyErr(map, "listInsureGoodBuy", "未获取到需要上传的数据！");
        if(!ListUtils.isEmpty(listInsureGoodBuy)){
            listInsureGoodBuy = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodBuy),InsureGoodBuy.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodBuy insureGoodBuy : listInsureGoodBuy) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg", insureGoodBuy.getMedListCodg());// 医疗目录编码	字符型	50	　	Y　	新医保
            dataMap.put("fixmedins_hilist_id", insureGoodBuy.getFixmedinsHilistId());// 定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：drug_code
            dataMap.put("fixmedins_hilist_name", insureGoodBuy.getFixmedinsHilistName());// 定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3:drug_name
            dataMap.put("dynt_no", insureGoodBuy.getDyntNo());// 随货单号	字符型	50	　	　	新医保/核三	核三：aae072
            dataMap.put("fixmedins_bchno", insureGoodBuy.getFixmedinsBchno());// 定点医药机构批次流水号	字符型	30	　	Y　	新医保
            dataMap.put("spler_name", insureGoodBuy.getSplerName());// 供应商名称	字符型	200	　	Y　	新医保
            dataMap.put("spler_pmtno", insureGoodBuy.getSplerPmtno());// 供应商许可证号	字符型	50	　	　	新医保
            dataMap.put("manu_lotnum", insureGoodBuy.getManuLotnum());// 生产批号	字符型	30	　	Y　	新医保
            dataMap.put("prodentp_name", insureGoodBuy.getProdentpName());// 生产厂家名称	字符型	200	　	Y　	新医保
            dataMap.put("aprvno", insureGoodBuy.getAprvno());// 批准文号	字符型	100	　	Y　	新医保
            dataMap.put("manu_date", insureGoodBuy.getManuDate());// 生产日期	日期型	　	　	Y　	新医保
            dataMap.put("expy_end", insureGoodBuy.getExpyEnd());// 有效期止	日期型	　	　	Y　	新医保
            dataMap.put("finl_trns_pric", insureGoodBuy.getFinlTrnsPric());// 最终成交单价	数值型	16,6	　	　	新医保/核三	核三：akc225
            dataMap.put("purc_retn_cnt", insureGoodBuy.getPurcRetnCnt());// 采购/退货数量	数值型	16,4	　	Y　	新医保/核三	核三：akc226
            dataMap.put("purc_invo_codg", insureGoodBuy.getPurcInvoCodg());// 采购发票编码	字符型	50	　	　	新医保
            dataMap.put("purc_invo_no", insureGoodBuy.getPurcInvoNo());// 采购发票号	字符型	50	　	　	新医保
            dataMap.put("rx_flag", insureGoodBuy.getRxFlag());// 处方药标志	字符型	3	　	Y　	新医保
            dataMap.put("purc_retn_stoin_time", insureGoodBuy.getPurcRetnStoinTime());// 采购/退货入库时间	日期时间型	　	　	Y　	新医保/核三	核三：aae036
            dataMap.put("purc_retn_opter_name", insureGoodBuy.getPurcRetnOpterName());// 采购/退货经办人姓名	字符型	50	　	Y　	新医保
            dataMap.put("prod_geay_flag", insureGoodBuy.getProdGeayFlag());// 商品赠送标志	字符型	3	Y　	　	新医保	0-否；1-是
            dataMap.put("memo", insureGoodBuy.getMemo());// 备注	字符型	500	　	　	新医保/核3	核3：aae013
            listMap.add(dataMap);
        }
        dataMap.clear();
        dataMap.put("purcinfo", JSONObject.toJSONString(listMap));

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3503, dataMap);

        Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");

        //上传成功数据
        List<InsureInventoryStockUpdate> sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //上传成功数据
        List<InsureInventoryStockUpdate> failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);
        return true;
    }


    /**
     * 查询商品采购退货信息
     *
     * @param insureGoodBuyBack
     * @return
     */
    @Override
    public List<InsureGoodBuyBack> queryInsureGoodBuyBackPage(InsureGoodBuyBack insureGoodBuyBack) {
        PageHelper.startPage(insureGoodBuyBack.getPageNo(), insureGoodBuyBack.getPageSize());
        return insureStockManagerDAO.queryInsureGoodBuyBackPage(insureGoodBuyBack);
    }

    /**
     * 批量修改商品采购退货信息
     *
     * @param map
     * @return
     */
    @Override
    public Boolean uploadInsureGoodBuyBack(Map<String, Object> map) {
        String hospCode = MapUtils.getEmptyErr(map, "hospCode", "医院编码不能为空！");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        List<InsureGoodBuyBack> listInsureGoodBuyBack = MapUtils.getEmptyErr(map, "listInsureGoodBuyBack", "未获取到需要上传的数据！");
        if(!ListUtils.isEmpty(listInsureGoodBuyBack)){
            listInsureGoodBuyBack = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodBuyBack),InsureGoodBuyBack.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodBuyBack insureGoodBuyBack : listInsureGoodBuyBack) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg",insureGoodBuyBack.getMedListCodg())	;// 医疗目录编码	字符型	50	　	Y　	新医保
            dataMap.put("fixmedins_hilist_id",insureGoodBuyBack.getFixmedinsHilistId())	;// 定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：drug_code
            dataMap.put("fixmedins_hilist_name",insureGoodBuyBack.getFixmedinsHilistName())	;// 定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3：drug_name
            dataMap.put("fixmedins_bchno",insureGoodBuyBack.getFixmedinsBchno())	;// 定点医药机构批次流水号	字符型	30	　	Y　	新医保
            dataMap.put("spler_name",insureGoodBuyBack.getSplerName())	;// 供应商名称	字符型	200	　	Y　	新医保
            dataMap.put("spler_pmtno",insureGoodBuyBack.getSplerPmtno())	;// 供应商许可证号	字符型	50	　	　	新医保
            dataMap.put("manu_date",insureGoodBuyBack.getManuDate())	;// 生产日期	日期型	　	　	Y　	新医保
            dataMap.put("expy_end",insureGoodBuyBack.getExpyEnd())	;// 有效期止	日期型	　	　	Y　	新医保
            dataMap.put("finl_trns_pric",insureGoodBuyBack.getFinlTrnsPric())	;// 最终成交单价	数值型	16,6	　	　	新医保/核三	核三：akc225
            dataMap.put("purc_retn_cnt",insureGoodBuyBack.getPurcRetnCnt())	;// 采购/退货数量	数值型	16,4	　	Y　	新医保/核三	核三：akc226
            dataMap.put("purc_invo_codg",insureGoodBuyBack.getPurcInvoCodg())	;// 采购发票编码	字符型	50	　	　	新医保
            dataMap.put("purc_invo_no",insureGoodBuyBack.getPurcInvoNo())	;// 采购发票号	字符型	50	　	Y　	新医保
            dataMap.put("rx_flag",insureGoodBuyBack.getRxFlag())	;// 处方药标志	字符型	3	Y　	Y　	新医保
            dataMap.put("purc_retn_stoin_time",insureGoodBuyBack.getPurcRetnStoinTime())	;// 采购/退货入库时间	日期时间型	　	　	Y　	新医保/核三	核三：aae036
            dataMap.put("purc_retn_opter_name",insureGoodBuyBack.getPurcRetnOpterName())	;// 采购/退货经办人姓名	字符型	50	　	Y　	新医保
            dataMap.put("memo",insureGoodBuyBack.getMemo())	;// 备注	字符型	500	　	　	新医保
            dataMap.put("medins_prod_purc_no",insureGoodBuyBack.getMedinsProdPurcNo())	;// 商品采购流水号	字符型	50			新医保/核3	核3：aae072
            listMap.add(dataMap);
        }


        dataMap.clear();
        dataMap.put("purcinfo", JSONObject.toJSONString(listMap));

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3504, dataMap);

        Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        return true;
    }


    /**
     * 查询商品删除信息
     *
     * @param insureGoodInfoDelete
     * @return
     */
    @Override
    public List<InsureGoodInfoDelete> queryInsureGoodInfoDeletePage(InsureGoodInfoDelete insureGoodInfoDelete) {
        PageHelper.startPage(insureGoodInfoDelete.getPageNo(), insureGoodInfoDelete.getPageSize());
        return insureStockManagerDAO.queryInsureGoodInfoDeletePage(insureGoodInfoDelete);
    }

    /**
     * 批量修改商品删除信息
     *
     * @param map
     * @return
     */
    @Override
    public Boolean uploadInsureGoodInfoDelete(Map<String, Object> map) {
        String hospCode = MapUtils.getEmptyErr(map, "hospCode", "医院编码不能为空！");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        String fixmedins_bchno = MapUtils.getEmptyErr(map, "fixmedins_bchno", "批次流水号不能为空！");
        String inv_data_type = MapUtils.getEmptyErr(map, "inv_data_type", "进销存数据类型不能为空！");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("fixmedins_bchno",fixmedins_bchno);
        dataMap.put("inv_data_type",inv_data_type);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("data", dataMap); //	交易输入
        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3507, paramMap);
        return true;
    }


    /**
     * 查询商品销售信息
     *
     * @param insureGoodSell
     * @return
     */
    @Override
    public List<InsureGoodSell> queryInsureGoodSellPage(InsureGoodSell insureGoodSell) {
        PageHelper.startPage(insureGoodSell.getPageNo(), insureGoodSell.getPageSize());
        return insureStockManagerDAO.queryInsureGoodSellPage(insureGoodSell);
    }

    /**
     * 批量修改商品销售信息
     *
     * @param map
     * @return
     */
    @Override
    public Boolean uploadInsureGoodSell(Map<String, Object> map) {
        String hospCode = MapUtils.getEmptyErr(map, "hospCode", "医院编码不能为空！");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        List<InsureGoodSell> listInsureGoodSell = MapUtils.getEmptyErr(map, "listInsureGoodSell", "未获取到需要上传的数据！");
        if(!ListUtils.isEmpty(listInsureGoodSell)){
            listInsureGoodSell = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodSell),InsureGoodSell.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodSell insureGoodSell : listInsureGoodSell) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg",insureGoodSell.getMedListCodg())	;//医疗目录编码	字符型	50	　	Y　	新医保
            dataMap.put("fixmedins_hilist_id",insureGoodSell.getFixmedinsHilistId())	;//定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：drug_code
            dataMap.put("fixmedins_hilist_name",insureGoodSell.getFixmedinsHilistName())	;//定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3：drug_name
            dataMap.put("fixmedins_bchno",insureGoodSell.getFixmedinsBchno())	;//定点医药机构批次流水号	字符型	30	　	Y　	新医保
            dataMap.put("prsc_dr_cert_type",insureGoodSell.getPrscDrCertType())	;//开方医师证件类型	字符型	6	Y　	　	新医保
            dataMap.put("prsc_dr_certno",insureGoodSell.getPrscDrCertno())	;//开方医师证件号码	字符型	50	　	　	新医保
            dataMap.put("prsc_dr_name",insureGoodSell.getPrscDrName())	;//开方医师姓名	字符型	50	　	Y　	新医保
            dataMap.put("phar_cert_type",insureGoodSell.getPharCertType())	;//药师证件类型	字符型	6	Y　	　	新医保
            dataMap.put("phar_certno",insureGoodSell.getPharCertno())	;//药师证件号码	字符型	50	　	　	新医保
            dataMap.put("phar_name",insureGoodSell.getPharName())	;//药师姓名	字符型	50	　	Y　	新医保
            dataMap.put("phar_prac_cert_no",insureGoodSell.getPharPracCertNo())	;//药师执业资格证号	字符型	50	　	Y　	新医保
            dataMap.put("hi_feesetl_type",insureGoodSell.getHiFeesetlType())	;//医保费用结算类型	字符型	6	Y　	　	新医保
            dataMap.put("setl_id",insureGoodSell.getSetlId())	;//结算ID	字符型	30	　	　	新医保
            dataMap.put("mdtrt_sn",insureGoodSell.getMdtrtSn())	;//就医流水号	字符型	30	　	Y	新医保
            dataMap.put("psn_no",insureGoodSell.getPsnNo())	;//人员编号	字符型	30	　	　	新医保
            dataMap.put("psn_cert_type",insureGoodSell.getPsnCertType())	;//人员证件类型	字符型	6	Y　	　	新医保
            dataMap.put("certno",insureGoodSell.getPharCertno())	;//证件号码	字符型	50	　	　	新医保
            dataMap.put("psn_name",insureGoodSell.getPsnName())	;//人员姓名	字符型	50	　	　	新医保
            dataMap.put("manu_lotnum",insureGoodSell.getManuLotnum())	;//生产批号	字符型	30	　	Y　	新医保
            dataMap.put("manu_date",insureGoodSell.getManuDate())	;//生产日期	日期型	　	　	Y　	新医保
            dataMap.put("expy_end",insureGoodSell.getExpyEnd())	;//有效期止	日期型	　	　	　	新医保
            dataMap.put("rx_flag",insureGoodSell.getRxFlag())	;//处方药标志	字符型	3	Y　	Y　	新医保
            dataMap.put("trdn_flag",insureGoodSell.getTrdnFlag())	;//拆零标志	字符型	3	Y　	Y　	新医保
            dataMap.put("finl_trns_pric",insureGoodSell.getFinlTrnsPric())	;//最终成交单价	数值型	16,6	　	　	新医保/核三	核三：akc225
            dataMap.put("rxno",insureGoodSell.getRxno())	;//处方号	字符型	40			新医保
            dataMap.put("rx_circ_flag",insureGoodSell.getRxCircFlag())	;//外购处方标志	字符型	3	Y		新医保
            dataMap.put("rtal_docno",insureGoodSell.getRtalDocno())	;//零售单据号	字符型	40		Y	新医保/核3	核3：aae072
            dataMap.put("stoout_no",insureGoodSell.getStooutNo())	;//销售出库单2据号	字符型	40			新医保
            dataMap.put("bchno",insureGoodSell.getBchno())	;//批次号	字符型	30			新医保
            dataMap.put("drug_trac_codg",insureGoodSell.getDrugTracCodg())	;//药品追溯码	字符型	30			新医保
            dataMap.put("drug_prod_barc",insureGoodSell.getDrugProdBarc())	;//药品条形码	字符型	30			新医保
            dataMap.put("shelf_posi",insureGoodSell.getShelfPosi())	;//货架位	字符型	20			新医保
            dataMap.put("sel_retn_cnt",insureGoodSell.getSelRetnCnt())	;//销售/退货数量	数值型	16,4	　	Y　	新医保/核三	核三：akc226
            dataMap.put("sel_retn_time",insureGoodSell.getSelRetnTime())	;//销售/退货时间	日期时间型	　	　	Y　	新医保
            dataMap.put("sel_retn_opter_name",insureGoodSell.getSelRetnOpterName())	;//销售/退货经办人姓名	字符型	50	　	Y　	新医保
            dataMap.put("memo",insureGoodSell.getMemo())	;//备注	字符型	500	　	　	新医保/核三	核三：aae013
            listMap.add(dataMap);
        }


        dataMap.clear();
        dataMap.put("purcinfo", JSONObject.toJSONString(listMap));

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3505, dataMap);

        Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");

        //上传成功数据
        List<InsureInventoryStockUpdate> sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //上传成功数据
        List<InsureInventoryStockUpdate> failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);
        return true;
    }


    /**
     * 查询商品销售退货信息
     *
     * @param insureGoodSellBack
     * @return
     */
    @Override
    public List<InsureGoodSellBack> queryInsureGoodSellBackPage(InsureGoodSellBack insureGoodSellBack) {
        PageHelper.startPage(insureGoodSellBack.getPageNo(), insureGoodSellBack.getPageSize());
        return insureStockManagerDAO.queryInsureGoodSellBackPage(insureGoodSellBack);
    }

    /**
     * 批量修改商品销售退货信息
     *
     * @param map
     * @return
     */
    @Override
    public Boolean uploadInsureGoodSellBack(Map<String, Object> map) {
        String hospCode = MapUtils.getEmptyErr(map, "hospCode", "医院编码不能为空！");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        List<InsureGoodSellBack> listInsureGoodSellBack = MapUtils.getEmptyErr(map, "listInsureGoodSellBack", "未获取到需要上传的数据！");
        if(!ListUtils.isEmpty(listInsureGoodSellBack)){
            listInsureGoodSellBack = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodSellBack),InsureGoodSellBack.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodSellBack insureGoodSellBack : listInsureGoodSellBack) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg",insureGoodSellBack.getMedListCodg())	;//医疗目录编码	字符型	50	　	Y　	新医保
            dataMap.put("fixmedins_hilist_id",insureGoodSellBack.getFixmedinsHilistId())	;//定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：ake005
            dataMap.put("fixmedins_hilist_name",insureGoodSellBack.getFixmedinsHilistName())	;//定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3：ake006
            dataMap.put("fixmedins_bchno",insureGoodSellBack.getFixmedinsBchno())	;//定点医药机构批次流水号	字符型	30	　	Y　	新医保
            dataMap.put("setl_id",insureGoodSellBack.getSetlId())	;//结算ID	字符型	30	　	　	新医保
            dataMap.put("psn_no",insureGoodSellBack.getPsnNo())	; //人员编号	字符型	30	　	　	新医保
            dataMap.put("psn_cert_type",insureGoodSellBack.getPsnCertType())	;//人员证件类型	字符型	6	Y　	　	新医保
            dataMap.put("certno",insureGoodSellBack.getCertno())	;//证件号码	字符型	50		　	新医保
            dataMap.put("psn_name",insureGoodSellBack.getPsnName())	;//人员姓名	字符型	50	　	　	新医保
            dataMap.put("manu_lotnum",insureGoodSellBack.getManuLotnum());//	生产批号	字符型	30	　	Y　	新医保
            dataMap.put("manu_date",insureGoodSellBack.getManuDate())	;//生产日期	日期型	　	　	Y　	新医保
            dataMap.put("expy_end",insureGoodSellBack.getExpyEnd())	;//有效期止	日期型	　	　	　	新医保
            dataMap.put("rx_flag",insureGoodSellBack.getRxFlag())	;//处方药标志	字符型	3	Y　	Y　	新医保
            dataMap.put("trdn_flag",insureGoodSellBack.getTrdnFlag())	;//拆零标志	字符型	3	Y　	Y　	新医保
            dataMap.put("finl_trns_pric",insureGoodSellBack.getFinlTrnsPric())	;//最终成交单价	数值型	16,6	　	　	新医保/核3	核3：akc225
            dataMap.put("sel_retn_cnt",insureGoodSellBack.getSelRetnCnt())	;//销售/退货数量	数值型	16,4	　	Y　	新医保/核3	核3：akc226
            dataMap.put("sel_retn_time",insureGoodSellBack.getSelRetnTime())	;//销售/退货时间/订单时间	日期时间型	　	　	Y　	新医保/核3	核3：aae036
            dataMap.put("sel_retn_opter_name",insureGoodSellBack.getSelRetnOpterName())	;//销售/退货经办人姓名	字符型	50	　	Y　	新医保
            dataMap.put("memo",insureGoodSellBack.getMemo())	;//备注	字符型	500	　	　	新医保/核3	核3：aae013
            dataMap.put("medins_prod_sel_no",insureGoodSellBack.getMedinsProdSelNo())	;//商品销售流水号	字符型	50			新医保/核3	核3：aae072
            listMap.add(dataMap);
        }


        dataMap.clear();
        dataMap.put("purcinfo", JSONObject.toJSONString(listMap));

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3506, dataMap);

        Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");

        //上传成功数据
        List<InsureInventoryStockUpdate> sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //上传成功数据
        List<InsureInventoryStockUpdate> failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);
        return true;
    }


    /**
     * 查询商品盘点信息
     *
     * @param insureInventoryCheck
     * @return
     */
    @Override
    public List<InsureInventoryCheck> queryInsureInventoryCheckPage(InsureInventoryCheck insureInventoryCheck) {
        PageHelper.startPage(insureInventoryCheck.getPageNo(), insureInventoryCheck.getPageSize());
        return insureStockManagerDAO.queryInsureInventoryCheckPage(insureInventoryCheck);
    }

    /**
     * 批量修改商品盘点信息
     *
     * @param map
     * @return
     */
    @Override
    public Boolean uploadInsureInventoryCheck(Map<String, Object> map) {
        String hospCode = MapUtils.getEmptyErr(map, "hospCode", "医院编码不能为空！");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        List<InsureInventoryCheck> listInsureInventoryCheck = MapUtils.getEmptyErr(map, "listInsureInventoryCheck", "未获取到需要上传的数据！");
        if(!ListUtils.isEmpty(listInsureInventoryCheck)){
            listInsureInventoryCheck = JSONObject.parseArray(JSONObject.toJSONString(listInsureInventoryCheck),InsureInventoryCheck.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureInventoryCheck insureInventoryCheck : listInsureInventoryCheck) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg", insureInventoryCheck.getMedListCodg());//医疗目录编码	字符型	50		Y	新医保
            dataMap.put("fixmedins_hilist_id", insureInventoryCheck.getFixmedinsHilistId());//定点医药机构目录编号	字符型	20	Y	Y	新医保/核3	核3：drug_code
            dataMap.put("fixmedins_hilist_name", insureInventoryCheck.getFixmedinsHilistName());//定点医药机构目录名称	字符型	200		Y	新医保/核3	核3：drug_name
            dataMap.put("rx_flag", insureInventoryCheck.getRxFlag());//处方药标志	字符型	3	Y　	Y　	新医保
            dataMap.put("invdate", insureInventoryCheck.getInvdate());//盘存日期	日期型	　	　	Y　	新医保
            dataMap.put("inv_cnt", insureInventoryCheck.getInvCnt());//库存数量	数值型	16,2	　	Y　	新医保
            dataMap.put("manu_lotnum", insureInventoryCheck.getManuLotnum());//生产批号	字符型	30	　	　	新医保
            dataMap.put("fixmedins_bchno", insureInventoryCheck.getFixmedinsBchno());//定点医药机构批次流水号	字符型	30	　	Y　	新医保
            dataMap.put("manu_date", insureInventoryCheck.getManuDate());//生产日期	日期型	　	　	Y　	新医保
            dataMap.put("expy_end", insureInventoryCheck.getExpyEnd());//有效期止	日期型	　	　	Y　	新医保
            dataMap.put("memo", insureInventoryCheck.getMemo());//备注	字符型	500	　	　	新医保
            listMap.add(dataMap);
        }
        dataMap.clear();
        dataMap.put("purcinfo", JSONObject.toJSONString(listMap));

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3501, dataMap);

        Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        //上传成功数据
        List<InsureInventoryCheck> sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //上传成功数据
        List<InsureInventoryCheck> failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);

        return true;
    }

    /**
     * 查询商品库存变更信息
     *
     * @param insureInventoryStockUpdate
     * @return
     */
    @Override
    public List<InsureInventoryStockUpdate> queryInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate) {
        PageHelper.startPage(insureInventoryStockUpdate.getPageNo(), insureInventoryStockUpdate.getPageSize());
        List<InsureInventoryStockUpdate> list = insureStockManagerDAO.queryInsureInventoryStockUpdatePage(insureInventoryStockUpdate);
        return list;
    }

    /**
     * 批量修改商品库存变更信息
     *
     * @param map
     * @return
     */
    @Override
    public Boolean uploadInsureInventoryStock(Map<String, Object> map) {
        String hospCode = MapUtils.getEmptyErr(map, "hospCode", "医院编码不能为空！");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        List<InsureInventoryStockUpdate> listInsureInventoryStockUpdate = MapUtils.getEmptyErr(map, "listInsureInventoryStockUpdate", "未获取到需要上传的数据！");
        if(!ListUtils.isEmpty(listInsureInventoryStockUpdate)){
            listInsureInventoryStockUpdate = JSONObject.parseArray(JSONObject.toJSONString(listInsureInventoryStockUpdate),InsureInventoryStockUpdate.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureInventoryStockUpdate insureInventoryStockUpdate : listInsureInventoryStockUpdate) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg", SnowflakeUtils.getId());//,insureInventoryStockUpdate.getMedListCodg())	;//医疗目录编码
            dataMap.put("inv_chg_type", insureInventoryStockUpdate.getInvChgType());//库存变更类型
            dataMap.put("fixmedins_hilist_id", insureInventoryStockUpdate.getFixmedinsHilistId());//定点医药机构目录编号
            dataMap.put("fixmedins_hilist_name", insureInventoryStockUpdate.getFixmedinsHilistName());//定点医药机构目录名称
            dataMap.put("fixmedins_bchno", insureInventoryStockUpdate.getFixmedinsBchno());//定点医药机构批次流水号
            dataMap.put("pric", insureInventoryStockUpdate.getPric());//单价
            dataMap.put("cnt", insureInventoryStockUpdate.getCnt());//数量
            dataMap.put("rx_flag", insureInventoryStockUpdate.getRxFlag());//处方药标志
            dataMap.put("inv_chg_time", insureInventoryStockUpdate.getInvChgTime());//库存变更时间
            dataMap.put("inv_chg_opter_name", insureInventoryStockUpdate.getInvChgOpterName());//库存变更经办人姓名
            dataMap.put("memo", insureInventoryStockUpdate.getMemo());//备注
            dataMap.put("rx_flag", "1");//处方药标志
            dataMap.put("inv_chg_time", DateUtils.format());//库存变更时间
            dataMap.put("inv_chg_opter_name", "张三");//库存变更经办人姓名
            dataMap.put("memo", "备注");//备注
            dataMap.put("trdn_flag", insureInventoryStockUpdate.getTrdnFlag());//拆零标志

            listMap.add(dataMap);
        }
        dataMap.clear();
        dataMap.put("purcinfo", JSONObject.toJSONString(listMap));

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3502, dataMap);

        Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        //上传成功数据
        List<InsureInventoryStockUpdate> sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //上传成功数据
        List<InsureInventoryStockUpdate> failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);
        return true;
    }


    /**
     * @Method commonInsureUnified
     * @Desrciption 调用统一支付平台公共接口方法
     * @Param hospCode:医院编码
     * orgCode:医疗机构编码
     * functionCode：功能号
     * paramMap:入参
     * @Author fuhui
     * @Date 2021/4/28 19:51
     * @Return
     **/
    private Map<String, Object> commonInsureUnified(String hospCode, String orgCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO == null){
            throw new AppException("未获取到医保信息");
        }
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("调用功能号【" + functionCode + "】的入参为" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        logger.info("调用功能号【" + functionCode + "】的反参为" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException(MapUtils.getVS(resultMap, "msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException(MapUtils.getVS(resultMap, "err_msg"));
        }
        return resultMap;
    }
}
