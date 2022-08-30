package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.bo.InsureRecruitPurchaseBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.stock.bo.InsureStockManagerBO;
import cn.hsa.module.insure.stock.dao.InsureStockManagerDAO;
import cn.hsa.module.insure.stock.entity.*;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private InsureRecruitPurchaseBO insureRecruitPurchaseBO;

    /**
     * 查询商品采购信息,并且没有上传过的数据
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
        String certId = MapUtils.getEmptyErr(map, "certId", "上传人ID不能为空！");
        List<InsureGoodBuy> listInsureGoodBuy = MapUtils.getEmptyErr(map, "listInsureGoodBuy", "未获取到需要上传的数据！");
        if (!ListUtils.isEmpty(listInsureGoodBuy)) {
            listInsureGoodBuy = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodBuy), InsureGoodBuy.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodBuy insureGoodBuy : listInsureGoodBuy) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg", StringUtils.isEmpty(insureGoodBuy.getMedListCodg()) ? "无" : insureGoodBuy.getMedListCodg());// 医疗目录编码	字符型	50	　	Y　	新医保
            dataMap.put("fixmedins_hilist_id", StringUtils.isEmpty(insureGoodBuy.getFixmedinsHilistId()) ? "无" : insureGoodBuy.getFixmedinsHilistId());// 定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：drug_code
            dataMap.put("fixmedins_hilist_name", StringUtils.isEmpty(insureGoodBuy.getFixmedinsHilistName()) ? "无" : insureGoodBuy.getFixmedinsHilistName());// 定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3:drug_name
            dataMap.put("dynt_no", insureGoodBuy.getDyntNo());// 随货单号	字符型	50	　	　	新医保/核三	核三：aae072
            dataMap.put("fixmedins_bchno", insureGoodBuy.getFixmedinsBchno());// 定点医药机构批次流水号	字符型	30	　	Y　	新医保
            dataMap.put("spler_name", StringUtils.isEmpty(insureGoodBuy.getSplerName()) ? "无" : insureGoodBuy.getSplerName());// 供应商名称	字符型	200	　	Y　	新医保
            dataMap.put("spler_pmtno", insureGoodBuy.getSplerPmtno());// 供应商许可证号	字符型	50	　	　	新医保
            dataMap.put("manu_lotnum", insureGoodBuy.getManuLotnum());// 生产批号	字符型	30	　	Y　	新医保
            dataMap.put("prodentp_name", StringUtils.isEmpty(insureGoodBuy.getProdentpName()) ? "无" : insureGoodBuy.getProdentpName());// 生产厂家名称	字符型	200	　	Y　	新医保
            dataMap.put("aprvno", StringUtils.isEmpty(insureGoodBuy.getAprvno()) ? "无" : insureGoodBuy.getAprvno());// 批准文号	字符型	100	　	Y　	新医保
            dataMap.put("manu_date", insureGoodBuy.getManuDate());// 生产日期	日期型	　	　	Y　	新医保
            dataMap.put("expy_end", insureGoodBuy.getExpyEnd());// 有效期止	日期型	　	　	Y　	新医保
            dataMap.put("finl_trns_pric", insureGoodBuy.getFinlTrnsPric());// 最终成交单价	数值型	16,6	　	　	新医保/核三	核三：akc225
            dataMap.put("purc_retn_cnt", insureGoodBuy.getPurcRetnCnt());// 采购/退货数量	数值型	16,4	　	Y　	新医保/核三	核三：akc226
            dataMap.put("purc_invo_codg", insureGoodBuy.getPurcInvoCodg());// 采购发票编码	字符型	50	　	　	新医保
            dataMap.put("purc_invo_no", insureGoodBuy.getPurcInvoNo());// 采购发票号	字符型	50	　	　	新医保
            dataMap.put("rx_flag", insureGoodBuy.getRxFlag());// 处方药标志	字符型	3	　	Y　	新医保
            dataMap.put("purc_retn_stoin_time", insureGoodBuy.getPurcRetnStoinTime());// 采购/退货入库时间	日期时间型	　	　	Y　	新医保/核三	核三：aae036
            dataMap.put("purc_retn_opter_name", StringUtils.isEmpty(insureGoodBuy.getPurcRetnOpterName()) ? "无" : insureGoodBuy.getPurcRetnOpterName());// 采购/退货经办人姓名	字符型	50	　	Y　	新医保
            dataMap.put("prod_geay_flag", insureGoodBuy.getProdGeayFlag());// 商品赠送标志	字符型	3	Y　	　	新医保	0-否；1-是
            dataMap.put("memo", insureGoodBuy.getMemo());// 备注	字符型	500	　	　	新医保/核3	核3：aae013
            listMap.add(dataMap);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("purcinfo", listMap);

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3503, paramMap);
        //该接口在接口文档中输出为无返参返回，当没有参数返回时解析结果会报错
/*
        Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");

        //上传成功数据
        JSONArray sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //上传成功数据
        JSONArray failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);

        List<InsureGoodBuy> sucessDataList = JSONArray.parseArray(sucessData.toString(), InsureGoodBuy.class);
        if (ListUtils.isEmpty(sucessDataList)) {
            throw new AppException("上传失败： 本次上传数据为0");
        }
        //获取list对象 list属性 并进行去重
        List<String> fixmedinsBchnoList = sucessDataList.stream().map(InsureGoodBuy::getFixmedinsBchno).distinct().collect(Collectors.toList());*/

        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        for (InsureGoodBuy insureGoodBuy : listInsureGoodBuy) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(insureGoodBuy.getFixmedinsBchno());
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
            insureGoodInfoDelete.setInsureType(regCode);
            insureGoodInfoDelete.setInvDataType("3");
            insureGoodInfoDelete.setCertId(certId);
            listData.add(insureGoodInfoDelete);
        }
        updateStroAndSaveResultData(listData, hospCode, "1");
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
        String certId = MapUtils.getEmptyErr(map, "certId", "上传人ID不能为空！");
        List<InsureGoodBuyBack> listInsureGoodBuyBack = MapUtils.getEmptyErr(map, "listInsureGoodBuyBack", "未获取到需要上传的数据！");
        if (!ListUtils.isEmpty(listInsureGoodBuyBack)) {
            listInsureGoodBuyBack = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodBuyBack), InsureGoodBuyBack.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodBuyBack insureGoodBuyBack : listInsureGoodBuyBack) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg", StringUtils.isEmpty(insureGoodBuyBack.getMedListCodg()) ? "无" : insureGoodBuyBack.getMedListCodg());// 医疗目录编码	字符型	50	　	Y　	新医保
            dataMap.put("fixmedins_hilist_id", StringUtils.isEmpty(insureGoodBuyBack.getFixmedinsHilistId()) ? "无" : insureGoodBuyBack.getFixmedinsHilistId());// 定点医药机构目录编号	字符型	30	　	Y　	新医保/核3	核3：drug_code
            dataMap.put("fixmedins_hilist_name", StringUtils.isEmpty(insureGoodBuyBack.getFixmedinsHilistId()) ? "无" : insureGoodBuyBack.getFixmedinsHilistName());// 定点医药机构目录名称	字符型	200	　	Y　	新医保/核3	核3：drug_name
            dataMap.put("fixmedins_bchno", insureGoodBuyBack.getFixmedinsBchno());// 定点医药机构批次流水号	字符型	30	　	Y　	新医保
            dataMap.put("spler_name", StringUtils.isEmpty(insureGoodBuyBack.getSplerName()) ? "无" : insureGoodBuyBack.getSplerName());// 供应商名称	字符型	200	　	Y　	新医保
            dataMap.put("spler_pmtno", insureGoodBuyBack.getSplerPmtno());// 供应商许可证号	字符型	50	　	　	新医保
            dataMap.put("manu_date", insureGoodBuyBack.getManuDate());// 生产日期	日期型	　	　	Y　	新医保
            dataMap.put("expy_end", insureGoodBuyBack.getExpyEnd());// 有效期止	日期型	　	　	Y　	新医保
            dataMap.put("finl_trns_pric", insureGoodBuyBack.getFinlTrnsPric());// 最终成交单价	数值型	16,6	　	　	新医保/核三	核三：akc225
            dataMap.put("purc_retn_cnt", insureGoodBuyBack.getPurcRetnCnt());// 采购/退货数量	数值型	16,4	　	Y　	新医保/核三	核三：akc226
            dataMap.put("purc_invo_codg", insureGoodBuyBack.getPurcInvoCodg());// 采购发票编码	字符型	50	　	　	新医保
            dataMap.put("purc_invo_no", StringUtils.isEmpty(insureGoodBuyBack.getPurcInvoNo()) ? "无" : insureGoodBuyBack.getPurcInvoNo());// 采购发票号	字符型	50	　	Y　	新医保
            dataMap.put("rx_flag", insureGoodBuyBack.getRxFlag());// 处方药标志	字符型	3	Y　	Y　	新医保
            dataMap.put("purc_retn_stoin_time", insureGoodBuyBack.getPurcRetnStoinTime());// 采购/退货入库时间	日期时间型	　	　	Y　	新医保/核三	核三：aae036
            dataMap.put("purc_retn_opter_name", StringUtils.isEmpty(insureGoodBuyBack.getPurcRetnOpterName()) ? "无" : insureGoodBuyBack.getPurcRetnOpterName());// 采购/退货经办人姓名	字符型	50	　	Y　	新医保
            dataMap.put("memo", insureGoodBuyBack.getMemo());// 备注	字符型	500	　	　	新医保
            dataMap.put("medins_prod_purc_no", insureGoodBuyBack.getMedinsProdPurcNo());// 商品采购流水号	字符型	50			新医保/核3	核3：aae072
            listMap.add(dataMap);
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("purcinfo", listMap);

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3504, paramMap);
        //该接口在接口文档中输出为无返参返回，当没有参数返回时解析结果会报错
     /*   Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        //上传成功数据
        JSONArray sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //失败数据
        JSONArray failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);


        List<InsureGoodBuy> sucessDataList = JSONArray.parseArray(sucessData.toString(), InsureGoodBuy.class);
        if (ListUtils.isEmpty(sucessDataList)) {
            throw new AppException("上传失败： 本次上传数据为0");
        }
        //获取list对象 list属性 并进行去重
        List<String> fixmedinsBchnoList = sucessDataList.stream().map(InsureGoodBuy::getFixmedinsBchno).distinct().collect(Collectors.toList());
*/
        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        /*for (String fixmedinsBchno : fixmedinsBchnoList) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(fixmedinsBchno);
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
            insureGoodInfoDelete.setInsureType(regCode);
            insureGoodInfoDelete.setInvDataType("3");
            insureGoodInfoDelete.setCertId(certId);
            listData.add(insureGoodInfoDelete);
        }*/
        for (InsureGoodBuyBack insureGoodBuyBack : listInsureGoodBuyBack) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(insureGoodBuyBack.getFixmedinsBchno());
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
            insureGoodInfoDelete.setInsureType(regCode);
            insureGoodInfoDelete.setInvDataType("3");
            insureGoodInfoDelete.setCertId(certId);
            listData.add(insureGoodInfoDelete);
        }
        updateStroAndSaveResultData(listData, hospCode, "2");

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
        return insureStockManagerDAO.queryInsureUploadDataPage(insureGoodInfoDelete);
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
        String fixmedinsBchno = MapUtils.getEmptyErr(map, "fixmedinsBchno", "批次流水号不能为空！");
        String invDataType = MapUtils.getEmptyErr(map, "invDataType", "进销存数据类型不能为空！");
        String id = MapUtils.getEmptyErr(map, "id", "记录ID不存在！");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("fixmedins_bchno", fixmedinsBchno);
        dataMap.put("inv_data_type", invDataType);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("data", dataMap); //	交易输入
        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3507, paramMap);
        insureStockManagerDAO.deleteStockUpload(map);
        insureStockManagerDAO.updateStockUpload(map);
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
     * 查询商品销售信息
     *
     * @param insureGoodSell
     * @return  queryInsureGoodSellPageForHainan
     */
    @Override
    public List<InsureGoodSell> queryInsureGoodSellPageForHainan(InsureGoodSell insureGoodSell) {

        if (ObjectUtil.isEmpty(insureGoodSell)) {
            throw new AppException("入参不能为空！");
        }
        if (ObjectUtil.isEmpty(insureGoodSell.getGoodsType())) {
            throw new AppException("商品类型【goodsType】不能为空！");
        }
        //调用医保销售列表查询【8503】【8508】接口
        try {
            InsureRecruitPurchaseDTO purchaseDTO = new InsureRecruitPurchaseDTO();
            purchaseDTO.setHospCode(insureGoodSell.getHospCode());
            purchaseDTO.setInsureRegCode(insureGoodSell.getInsureRegCode());
            purchaseDTO.setPageNo(insureGoodSell.getPageNo());
            purchaseDTO.setPageSize(insureGoodSell.getPageSize());
            purchaseDTO.setItemCode(insureGoodSell.getGoodsType());
            insureRecruitPurchaseBO.queryDrugSells(purchaseDTO);
        } catch (Exception e) {
            log.error("调医保销售列表查询接口失败!"+e.getMessage());
        }
        //查询本地数据
        List<InsureGoodSell> insureGoodSells = new ArrayList<>();
        PageHelper.startPage(insureGoodSell.getPageNo(), insureGoodSell.getPageSize());
        if (Constants.XMLB.YP.equals(insureGoodSell.getGoodsType())) {
            insureGoodSells = insureStockManagerDAO.queryInsureDrugSellPage(insureGoodSell);
        }else if (Constants.XMLB.CL.equals(insureGoodSell.getGoodsType())){
            insureGoodSells = insureStockManagerDAO.queryInsureMaterialSellPage(insureGoodSell);
        }
        return insureGoodSells;
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
        String regCode = MapUtils.get(map, "orgCode");
        if (StringUtils.isEmpty(regCode)) {
            throw new AppException("医保机构编码不能为空");
        }
        String certId = MapUtils.getEmptyErr(map, "certId", "上传人ID不能为空！");
        List<InsureGoodSell> listInsureGoodSell = MapUtils.getEmptyErr(map, "listInsureGoodSell", "未获取到需要上传的数据！");
        if (!ListUtils.isEmpty(listInsureGoodSell)) {
            listInsureGoodSell = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodSell), InsureGoodSell.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodSell insureGoodSell : listInsureGoodSell) {
            dataMap = new HashMap<String, Object>();
            if ("".equals(insureGoodSell.getMedListCodg()) && StringUtils.isEmpty(insureGoodSell.getMedListCodg())) {
                throw new AppException("商品编码为空");
            }
            dataMap.put("med_list_codg", insureGoodSell.getMedListCodg());//医疗目录编码
            dataMap.put("fixmedins_hilist_id", insureGoodSell.getFixmedinsHilistId());//定点医药机构目录编号	　
            dataMap.put("fixmedins_hilist_name", insureGoodSell.getFixmedinsHilistName());//定点医药机构目录名称
            dataMap.put("fixmedins_bchno", insureGoodSell.getFixmedinsBchno());//定点医药机构批次流水号
            dataMap.put("prsc_dr_cert_type", insureGoodSell.getPrscDrCertType());//开方医师证件类型
            dataMap.put("prsc_dr_certno", insureGoodSell.getPrscDrCertno());//开方医师证件号码
            dataMap.put("prsc_dr_name", insureGoodSell.getPrscDrName());//开方医师姓名
            dataMap.put("phar_cert_type", insureGoodSell.getPharCertType());//药师证件类型
            dataMap.put("phar_certno", insureGoodSell.getPharCertno());//药师证件号码
            dataMap.put("phar_name", insureGoodSell.getPharName());//药师姓名
            dataMap.put("phar_prac_cert_no", insureGoodSell.getPharPracCertNo());//药师执业资格证号
            dataMap.put("hi_feesetl_type", insureGoodSell.getHiFeesetlType());//医保费用结算类型
            dataMap.put("setl_id", insureGoodSell.getSetlId());//结算ID
            dataMap.put("mdtrt_sn", insureGoodSell.getMdtrtSn());//就医流水号
            dataMap.put("psn_no", insureGoodSell.getPsnNo());//人员编号
            dataMap.put("psn_cert_type", insureGoodSell.getPsnCertType());//人员证件类型
            dataMap.put("certno", insureGoodSell.getPharCertno());//证件号码
            dataMap.put("psn_name", insureGoodSell.getPsnName());//人员姓名
            dataMap.put("manu_lotnum", insureGoodSell.getManuLotnum());//生产批号
            dataMap.put("manu_date", insureGoodSell.getManuDate());//生产日期
            dataMap.put("expy_end", insureGoodSell.getExpyEnd());//有效期止
            dataMap.put("rx_flag", insureGoodSell.getRxFlag());//处方药标志
            dataMap.put("trdn_flag", insureGoodSell.getTrdnFlag());//拆零标志
            dataMap.put("finl_trns_pric", insureGoodSell.getFinlTrnsPric());//最终成交单价
            dataMap.put("rxno", insureGoodSell.getRxno());//处方号
            dataMap.put("rx_circ_flag", insureGoodSell.getRxCircFlag());//外购处方标志
            dataMap.put("rtal_docno", insureGoodSell.getRtalDocno());//零售单据号
            dataMap.put("stoout_no", insureGoodSell.getStooutNo());//销售出库单2据号
            dataMap.put("bchno", insureGoodSell.getBchno());//批次号
            dataMap.put("drug_trac_codg", insureGoodSell.getDrugTracCodg());//药品追溯码
            dataMap.put("drug_prod_barc", insureGoodSell.getDrugProdBarc());//药品条形码
            dataMap.put("shelf_posi", insureGoodSell.getShelfPosi());//货架位
            dataMap.put("sel_retn_cnt", insureGoodSell.getSelRetnCnt());//销售/退货数量
            dataMap.put("sel_retn_time", insureGoodSell.getSelRetnTime());//销售/退货时间
            dataMap.put("sel_retn_opter_name", insureGoodSell.getSelRetnOpterName());//销售/退货经办人姓名
            dataMap.put("memo", insureGoodSell.getMemo());//备注
            listMap.add(dataMap);
        }
        Map dataMap2 = new HashMap();
        dataMap2.put("purcinfo", JSONObject.toJSONString(listMap));
        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3505, dataMap2);
        //该接口在接口文档中输出为无返参返回，当没有参数返回时解析结果会报错
       /* Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        //上传成功数据
        JSONArray sucessData = MapUtils.get(resultDataMap, "sucessData");
        // 3505接口的反参为空 不需要解析反参
        List<InsureGoodSell> sucessDataList = JSONArray.parseArray(sucessData.toString(), InsureGoodSell.class);
        if (ListUtils.isEmpty(sucessDataList)) {
            throw new AppException("上传失败： 本次上传数据为0");
        }
        //获取list对象 list属性 并进行去重
        List<String> fixmedinsBchnoList = sucessDataList.stream().map(InsureGoodSell::getFixmedinsBchno).distinct().collect(Collectors.toList());*/
        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        for (InsureGoodSell insureGoodSell : listInsureGoodSell) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(insureGoodSell.getFixmedinsBchno());
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
            insureGoodInfoDelete.setInsureType(regCode);
            insureGoodInfoDelete.setInvDataType("4");
            insureGoodInfoDelete.setCertId(certId);
            listData.add(insureGoodInfoDelete);
        }
        return this.updateStroAndSaveResultData(listData, hospCode, "1");
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

    @Override
    public List<InsureGoodSellBack> queryInsureGoodSellBackPageForHainan(InsureGoodSellBack insureGoodSellBack) {

        if (ObjectUtil.isEmpty(insureGoodSellBack)) {
            throw new AppException("入参不能为空！");
        }
        if (ObjectUtil.isEmpty(insureGoodSellBack.getGoodsType())) {
            throw new AppException("商品类型【goodsType】不能为空！");
        }
        List<InsureGoodSellBack> insureGoodSells = new ArrayList<>();
        PageHelper.startPage(insureGoodSellBack.getPageNo(), insureGoodSellBack.getPageSize());
        //查询药品
        if (Constants.XMLB.YP.equals(insureGoodSellBack.getGoodsType())) {
            insureGoodSells = insureStockManagerDAO.queryInsureDrugSellBackPage(insureGoodSellBack);
        //查询耗材
        }else if (Constants.XMLB.CL.equals(insureGoodSellBack.getGoodsType())){
            insureGoodSells = insureStockManagerDAO.queryInsureMaterialSellBackPage(insureGoodSellBack);
        }

        return insureGoodSells;
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
        String regCode = MapUtils.get(map, "orgCode");
        if (StringUtils.isEmpty(regCode)) {
            throw new AppException("医保机构编码不能为空");
        }
        String certId = MapUtils.getEmptyErr(map, "certId", "上传人ID不能为空！");
        List<InsureGoodSellBack> listInsureGoodSellBack = MapUtils.get(map, "listInsureGoodSellBack");
        if (ListUtils.isEmpty(listInsureGoodSellBack)) {
            throw new AppException("未获取到上传的数据");
        }
        if (!ListUtils.isEmpty(listInsureGoodSellBack)) {
            listInsureGoodSellBack = JSONObject.parseArray(JSONObject.toJSONString(listInsureGoodSellBack), InsureGoodSellBack.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureGoodSellBack insureGoodSellBack : listInsureGoodSellBack) {
            dataMap = new HashMap<String, Object>();
            dataMap.put("med_list_codg", insureGoodSellBack.getMedListCodg());//医疗目录编码
            dataMap.put("fixmedins_hilist_id", insureGoodSellBack.getFixmedinsHilistId());//定点医药机构目录编号
            dataMap.put("fixmedins_hilist_name", insureGoodSellBack.getFixmedinsHilistName());//定点医药机构目录名称
            dataMap.put("fixmedins_bchno", insureGoodSellBack.getFixmedinsBchno());//定点医药机构批次流水号
            dataMap.put("setl_id", insureGoodSellBack.getSetlId());//结算ID
            dataMap.put("psn_no", insureGoodSellBack.getPsnNo()); //人员编号
            dataMap.put("psn_cert_type", insureGoodSellBack.getPsnCertType());//人员证件类型
            dataMap.put("certno", insureGoodSellBack.getCertno());//证件号码
            dataMap.put("psn_name", insureGoodSellBack.getPsnName());//人员姓名
            dataMap.put("manu_lotnum", insureGoodSellBack.getManuLotnum());//生产批号
            dataMap.put("manu_date", insureGoodSellBack.getManuDate());//生产日期
            dataMap.put("expy_end", insureGoodSellBack.getExpyEnd());//有效期止
            dataMap.put("rx_flag", insureGoodSellBack.getRxFlag());//处方药标志
            dataMap.put("trdn_flag", insureGoodSellBack.getTrdnFlag());//拆零标志
            dataMap.put("finl_trns_pric", insureGoodSellBack.getFinlTrnsPric());//最终成交单价
            dataMap.put("sel_retn_cnt", insureGoodSellBack.getSelRetnCnt());//销售/退货数量
            dataMap.put("sel_retn_time", insureGoodSellBack.getSelRetnTime());//销售/退货时间/订单时间
            dataMap.put("sel_retn_opter_name", insureGoodSellBack.getSelRetnOpterName());//销售/退货经办人姓名
            dataMap.put("memo", insureGoodSellBack.getMemo());//备注
            dataMap.put("medins_prod_sel_no", insureGoodSellBack.getMedinsProdSelNo());//商品销售流水号
            listMap.add(dataMap);
        }
        Map map2 = new HashMap();
        map2.put("purcinfo", JSONObject.toJSONString(listMap));
        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3506, map2);
        //该接口在接口文档中输出为无返参返回，当没有参数返回时解析结果会报错
       /* Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        //上传成功数据
        JSONArray sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        List<InsureGoodSellBack> sucessDataList = JSONArray.parseArray(sucessData.toString(), InsureGoodSellBack.class);
        if (ListUtils.isEmpty(sucessDataList)) {
            throw new AppException("上传失败： 本次上传数据为0");
        }
        //获取list对象 list属性 并进行去重
        List<String> fixmedinsBchnoList = sucessDataList.stream().map(InsureGoodSellBack::getFixmedinsBchno).distinct().collect(Collectors.toList());*/
        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        for (InsureGoodSellBack insureGoodSellBack : listInsureGoodSellBack) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(insureGoodSellBack.getFixmedinsBchno());
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setInsureType(regCode);
            insureGoodInfoDelete.setInvDataType("4");
            insureGoodInfoDelete.setCertId(certId);
            listData.add(insureGoodInfoDelete);
        }
        return this.updateStroAndSaveResultData(listData, hospCode, "2");
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
        List<InsureInventoryCheck> insureInventoryChecks = insureStockManagerDAO.queryInsureInventoryCheckPage(insureInventoryCheck);
        return insureInventoryChecks;
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
        String regCode = MapUtils.get(map, "orgCode");
        if (StringUtils.isEmpty(regCode)) {
            throw new AppException("医保机构编码不能为空！");
        }
        String certId = MapUtils.getEmptyErr(map, "certId", "上传人ID不能为空！");
        List<InsureInventoryCheck> listInsureInventoryCheck = MapUtils.getEmptyErr(map, "listInsureInventoryCheck", "未获取到需要上传的数据！");
        if (!ListUtils.isEmpty(listInsureInventoryCheck)) {
            listInsureInventoryCheck = JSONObject.parseArray(JSONObject.toJSONString(listInsureInventoryCheck), InsureInventoryCheck.class);
        }
        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        for (InsureInventoryCheck insureInventoryCheck : listInsureInventoryCheck) {
            dataMap = new HashMap<String, Object>();
            if ("".equals(insureInventoryCheck.getMedListCodg()) && StringUtils.isEmpty(insureInventoryCheck.getMedListCodg())) {
                throw new AppException("商品国家编码为空！");
            }
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

        paramMap.put("purcinfo", listMap);

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3501, paramMap);
        //该接口在接口文档中输出为无返参返回，当没有参数返回时解析结果会报错
       /* Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        //上传成功数据
        JSONArray sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        //上传失败数据
        JSONArray failData = MapUtils.getEmptyErr(resultDataMap, "failData", null);
        // 3501接口 的反参是无 所以不需要解析反参
        List<InsureInventoryCheck> sucessDataList = JSONArray.parseArray(sucessData.toString(), InsureInventoryCheck.class);
        if (sucessData.isEmpty()) {
            throw new AppException("上传数据为空！");
        }
        //获取上传成功的数据
        List<String> fixmedinsBchnoList = sucessDataList.stream().map(InsureInventoryCheck::getFixmedinsBchno).distinct().collect(Collectors.toList());*/
        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        for (InsureInventoryCheck insureInventoryCheck : listInsureInventoryCheck) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(insureInventoryCheck.getFixmedinsBchno());
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setInsureType(regCode);
            insureGoodInfoDelete.setInvDataType("1");
            insureGoodInfoDelete.setCertId(certId);
            listData.add(insureGoodInfoDelete);
        }
        return updateStroAndSaveResultData(listData, hospCode, "1");
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
        String invChgType = insureInventoryStockUpdate.getInvChgType();
        if (StringUtils.isEmpty(invChgType)) {
            throw new AppException("变更类型不能为空！！！");
        }
        List<String> outinCodeList = new ArrayList<>();
        Map<String, List<String>> map = new HashMap();
        // 封装参数 后期把数字维护到常量或者枚举类里面
        // 调拨入库 对应 本地进销存 没有,先设置为它本身
        map.put("101", Arrays.asList("101"));
        // 调拨出库 对应 本地进销存 没有,先设置为它本身
        map.put("102", Arrays.asList("102"));
        // 盘盈 对应 本地进销存 7 盘盈盘亏
        map.put("103", Arrays.asList("7"));
        // 盘存 对应 本地进销存 8 报损报溢
        map.put("104", Arrays.asList("8"));
        // 销毁 对应 本地进销存 没有,先设置为它本身
        map.put("105", Arrays.asList("105"));
        // 其他入库 对应本地进销存 门诊退药、住院退药
        map.put("106", Arrays.asList("25", "28"));
        // 其他出库 对应本地进销存 门诊发药、住院发药
        map.put("107", Arrays.asList("23", "27"));
        // 初始化入库 对应 本地进销存 1,2 采购入库、直接入库
        map.put("108", Arrays.asList("1", "2"));
        outinCodeList = MapUtils.get(map, invChgType);
        if (ListUtils.isEmpty(outinCodeList)) {
            throw new AppException("请选择库存变更类型");
        } else {
            insureInventoryStockUpdate.setOutinCodeList(outinCodeList);
        }
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
        if (StringUtils.isEmpty(regCode)) {
            throw new AppException("医保机构编码不能为空!");
        }
        String certId = MapUtils.getEmptyErr(map, "certId", "上传人ID不能为空！");
        List<InsureInventoryStockUpdate> listInsureInventoryStockUpdate = MapUtils.getEmptyErr(map, "listInsureInventoryStockUpdate", "未获取到需要上传的数据！");
        if (!ListUtils.isEmpty(listInsureInventoryStockUpdate)) {
            listInsureInventoryStockUpdate = JSONObject.parseArray(JSONObject.toJSONString(listInsureInventoryStockUpdate), InsureInventoryStockUpdate.class);
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
            dataMap.put("rx_flag", insureInventoryStockUpdate.getRxFlag());//处方药标志
            dataMap.put("inv_chg_time", insureInventoryStockUpdate.getInvChgTime());//库存变更时间
            dataMap.put("inv_chg_opter_name", insureInventoryStockUpdate.getInvChgOpterName());//库存变更经办人姓名
            dataMap.put("memo", "备注");//备注
            dataMap.put("trdn_flag", insureInventoryStockUpdate.getTrdnFlag());//拆零标志
            listMap.add(dataMap);
        }
        Map map2 = new HashMap();
        map2.put("purcinfo", JSONObject.toJSONString(listMap));

        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.KCGL.UP_3502, map2);
        //该接口在接口文档中输出为无返参返回，当没有参数返回时解析结果会报错
    /*    Map<String, Object> resultDataMap = MapUtils.get(resultMap, "output");
        //上传成功数据
        JSONArray sucessData = MapUtils.getEmptyErr(resultDataMap, "sucessData", null);
        List<InsureInventoryStockUpdate> sucessDataList = JSONArray.parseArray(sucessData.toString(), InsureInventoryStockUpdate.class);
        //获取list对象 list属性 并进行去重
        List<String> fixmedinsBchnoList = sucessDataList.stream().map(InsureInventoryStockUpdate::getFixmedinsBchno).distinct().collect(Collectors.toList());*/

        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        for (InsureInventoryStockUpdate insureInventoryStockUpdate : listInsureInventoryStockUpdate) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(insureInventoryStockUpdate.getFixmedinsBchno());
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setInsureType(regCode);
            insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
            insureGoodInfoDelete.setInvDataType("2");
            insureGoodInfoDelete.setCertId(certId);
            listData.add(insureGoodInfoDelete);
        }

        return this.updateStroAndSaveResultData(listData, hospCode, "1");
    }

    /**
     * @Meth: queryPersonList
     * @Description: 查询销售 / 退货
     * @Param: [map]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    @Override
    public PageDTO queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO) {
        String itemType = insureRecruitPurchaseDTO.getItemType();
        String insureRegCode = insureRecruitPurchaseDTO.getInsureRegCode();
        String sellType = insureRecruitPurchaseDTO.getSellType();
        if (StringUtils.isEmpty(sellType)) {
            throw new RuntimeException("未选择业务类型，销售或者退货");
        }
        PageHelper.startPage(insureRecruitPurchaseDTO.getPageNo(), insureRecruitPurchaseDTO.getPageSize());
        List<Map<String, Object>> result = insureStockManagerDAO.queryPersonList(insureRecruitPurchaseDTO);
        return PageDTO.of(result);
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
        insureConfigurationDTO.setRegCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
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

    /**
     * @Meth: updateStroAndSaveResultData
     * @Description: 上传之后更新进销存、并插入到医保上传表
     * 注意： resultList中的对象必须要有 id, hosp_code,insure_type, fixmedins_bchno, inv_data_type, upload_time, cert_id
     * statusCode : 0 ： 未上传    1：采购/销售 上传  2：退货
     * @Param: [resultList, hospCode, statusCode]
     * @return: boolean
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    private boolean updateStroAndSaveResultData(List<InsureGoodInfoDelete> resultList, String hospCode, String statusCode) {
        if (!ListUtils.isEmpty(resultList)) {
            // 过滤出进销存id
            List<String> ids = resultList.stream().map(InsureGoodInfoDelete::getFixmedinsBchno).collect(Collectors.toList());
            //查询已上传的商品
            List<InsureGoodInfoDelete> fixmedinsBchnos = insureStockManagerDAO.queryStockUpBatch(ids);
            //过滤出需要新增上传表的数据
            resultList.removeIf(dto->fixmedinsBchnos.contains(dto.getFixmedinsBchno()));
            // 更新进销存的上传状态
            insureStockManagerDAO.updateStatus(ids, hospCode, statusCode);
            // 插入上传表
            insureStockManagerDAO.insertStockUploadBatch(resultList);
        }
        return true;
    }
}
