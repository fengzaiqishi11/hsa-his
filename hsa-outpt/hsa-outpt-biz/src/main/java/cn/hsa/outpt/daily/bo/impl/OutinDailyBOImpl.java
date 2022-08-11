package cn.hsa.outpt.daily.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.advancepay.entity.InptAdvancePayDO;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.outpt.daily.bo.OutinDailyBO;
import cn.hsa.module.outpt.daily.dao.OutinDailyDAO;
import cn.hsa.module.outpt.daily.dto.*;
import cn.hsa.module.outpt.daily.entity.OutinDailyAdvancePayDO;
import cn.hsa.module.outpt.daily.entity.OutinDailyCardPayDO;
import cn.hsa.module.outpt.daily.entity.OutinDailyDO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.daily.bo.impl
 * @Class_name: OutptDaliyBOImpl
 * @Description: 门诊、住院日结缴款BOImpl层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 10:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class OutinDailyBOImpl implements OutinDailyBO {
    @Resource
    private OutinDailyDAO outinDailyDAO;
    @Resource
    private BaseOrderRuleService baseOrderRuleService;
    @Resource
    private SysParameterService sysParameterService;

    /**
     * @Method 查询日结开始、结束时间
     * @Description
     * 日结开始时间：为最后一次日结结束时间（如果医院第一次日结缴款，则无最近一次日结信息，日结开始时间默认为2020-01-01 00:00:00）
     * 日结结束时间：为当前系统时间
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @Override
    public OutinDailyDTO getLastDaily(OutinDailyDTO outinDailyDTO) {
        fullJklxList(outinDailyDTO);
        return outinDailyDAO.getLastDaily(outinDailyDTO);
    }

    /**
     * @Method 填充缴款类型列表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/28 21:23
     * @Return 
     **/
    private void fullJklxList(OutinDailyDTO outinDailyDTO) {
        // 门诊
        if (Constants.JKLX.MZGH.equals(outinDailyDTO.getTypeCode()) || Constants.JKLX.MZSF.equals(outinDailyDTO.getTypeCode())) {
            // 日结缴款参数代码
            // 日结缴款方式（0：门诊挂号、门诊收费合并日结缴款，1：门诊挂号、门诊收费分开日结缴款）
            String value = getSysParameter(outinDailyDTO.getHospCode(), "RJJKFS");
            // 门诊挂号、门诊收费合并日结缴款
            if ("0".equals(value) || Constants.JKLX.MZGH.equals(outinDailyDTO.getTypeCode())) {
                outinDailyDTO.getJklxList().add(Constants.JKLX.MZGH);
            }
            // 门诊挂号、门诊收费分开日结缴款
            if ("0".equals(value) || Constants.JKLX.MZSF.equals(outinDailyDTO.getTypeCode())) {
                outinDailyDTO.getJklxList().add(Constants.JKLX.MZSF);
            }
        }
        // 住院
        else {
            outinDailyDTO.getJklxList().add(Constants.JKLX.ZY);
        }
    }

    /**
     * @Method 获取系统参数值
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/28 21:05
     * @Return 
     **/
    private String getSysParameter(String hospCode, String code) {
        Map paramMap = new HashMap();
        paramMap.put("hospCode", hospCode);
        paramMap.put("code", code);
        return sysParameterService.getParameterByCode(paramMap).getData().getValue();
    }
    
    /**
     * @Method 获取日结单号
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/28 21:05
     * @Return 
     **/
    private String getOrderNo(String hospCode, String typeCode) {
        Map paramMap = new HashMap();
        paramMap.put("hospCode", hospCode);
        paramMap.put("typeCode", typeCode);
        return baseOrderRuleService.getOrderNo(paramMap).getData();
    }

    /**
     * @Method 生成待确认日结缴款
     * @Description
     * 1、根据缴款人、是否结算（已结算）、结算时间查询需汇总的挂号、门诊结算数据
     * 2、根据结算ID查询状态标志代码为0（正常）、结算状态为1（已结算）费用数据
     *
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 15:01
     * @Return
     **/
    @Override
    public boolean  insert(OutinDailyDTO outinDailyDTO) {
        // 校验缴款开始时间是否为上次缴款结束时间
        OutinDailyDTO lastDailyDTO = getLastDaily(outinDailyDTO);
        if (lastDailyDTO != null && lastDailyDTO.getStartTime().getTime() != outinDailyDTO.getStartTime().getTime()) {
            throw new AppException("缴款开始时间必须等于上次缴款结束时间");
        }
        if (DateUtils.dateCompare(lastDailyDTO.getEndTime(), outinDailyDTO.getEndTime())) {
            throw new AppException("缴款结束时间不能晚于当前时间");
        }
        // 日结缴款单号
        outinDailyDTO.setDailyNo(getOrderNo(outinDailyDTO.getHospCode(), Constants.ORDERRULE.RJJK));

        // 日结主表
        List<OutinDailyDTO> odList = new ArrayList<>();
        // 日结发票表
        List<OutinDailyInvoiceDTO> odiList = new ArrayList<>();
        // 日结支付方式表
        List<OutinDailyPayDTO> odpList = new ArrayList<>();
        // 日结财务分类表
        List<OutinDailyFinclassifyDTO> odfList = new ArrayList<>();
        // 日结合同单位表
        List<OutinDailyInsureDTO> odinList = new ArrayList<>();
        // 预交金支付方式表
        List<OutinDailyAdvancePayDO> odapList = new ArrayList<>();
        // 一卡通充值退费记录
        List<OutinDailyCardPayDO> odcpList = new ArrayList<>();

        // 门诊缴款
        if (Constants.JKLX.MZGH.equals(outinDailyDTO.getTypeCode()) || Constants.JKLX.MZSF.equals(outinDailyDTO.getTypeCode())) {
            // 日结缴款参数代码
            // 日结缴款方式（0：门诊挂号、门诊收费合并日结缴款，1：门诊挂号、门诊收费分开日结缴款）
            String value = getSysParameter(outinDailyDTO.getHospCode(), "RJJKFS");
            // 1、生成门诊挂号日结缴款
            if ("0".equals(value) || Constants.JKLX.MZGH.equals(outinDailyDTO.getTypeCode())) {
                handleDailyRegister(outinDailyDTO, Constants.JKLX.MZGH, odList, odiList, odpList, odfList);
            }
            // 2、生成门诊收费日结缴款
            if ("0".equals(value) || Constants.JKLX.MZSF.equals(outinDailyDTO.getTypeCode())) {
                handleDailyOutpt(outinDailyDTO, Constants.JKLX.MZSF, odList, odiList, odpList, odfList, odinList, odcpList);
            }
        }
        // 住院缴款
        else {
            // 3、汇总住院收费日结缴款
            handleDailyInpt(outinDailyDTO, Constants.JKLX.ZY, odList, odiList, odpList, odfList, odinList, odapList);
        }

        // 日结主表
        if (!ListUtils.isEmpty(odList)) {
            outinDailyDAO.insertOutinDailyList(odList);
        }
        // 日结发票表
        if (!ListUtils.isEmpty(odiList)) {
            outinDailyDAO.insertOutinDailyInvoiceList(odiList);
        }
        // 日结支付方式表
        if (!ListUtils.isEmpty(odpList)) {
            outinDailyDAO.insertOutinDailyPayList(odpList);
        }
        // 日结财务分类表
        if (!ListUtils.isEmpty(odfList)) {
            outinDailyDAO.insertOutinDailyFinclassifyList(odfList);
        }
        // 日结合同单位表
        if (!ListUtils.isEmpty(odinList)) {
            outinDailyDAO.insertOutinDailyInsureList(odinList);
        }
        // 日结预交金支付方式
        if (!ListUtils.isEmpty(odapList)) {
            outinDailyDAO.insertOutinDailyAdvancePayList(odapList);
        }
        // 一卡通支付方式
        if (!ListUtils.isEmpty(odcpList)) {
            outinDailyDAO.insertOutinDailyCardPayList(odcpList);
        }
        return true;
    }

    /**
     * @Method 日结缴款 - 挂号
     * @Description
     * 日结主表：
     * 1、总费用合计（包含退款总金额） = 汇总【门诊挂号结算表（正常、被冲红）.挂号总金额】
     * 2、合同单位总金额 = 0
     * 3、预交总金额 = 0
     * 4、预交冲抵总金额 = 0
     * 5、退款总金额 = 汇总【门诊挂号结算表（冲红）.实收总金额】
     * 6、优惠总金额 = 汇总【门诊挂号结算表（正常）.优惠总金额】
     * 7、舍入总金额 = 0
     * 8、实缴总金额 = 汇总【门诊挂号结算表（正常）.实收总金额】
     *
     * 业务
     * 冲红总金额
     * 1、挂号费10元（正常），如果退款10元
     * 2、挂号费10元改为被冲红状态
     * 3、增加一条10元冲红记录
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 20:49
     * @Return
     **/
    private void handleDailyRegister(OutinDailyDTO dto, String typeCode, List<OutinDailyDTO> odList, List<OutinDailyInvoiceDTO> odiList, List<OutinDailyPayDTO> odpList, List<OutinDailyFinclassifyDTO> odfList) {
        // *******************************门诊挂号日结 - 日结主表*******************************
        OutinDailyDTO outinDailyDTO = buildOutinDailyDTO(dto, typeCode);
        odList.add(outinDailyDTO);
        // 挂号结算记录
        Map<String, Map<String, Object>> allMap = outinDailyDAO.queryOutptRegisterSettleAll(dto);
        if (MapUtils.isEmpty(allMap)) {
            return ;
        }
        // 总费用合计（包含退款总金额） = 汇总（正常）【门诊挂号结算表.挂号总金额】
        outinDailyDTO.setTotalPrice(MapUtils.get(allMap, "total_price"));
        // 优惠总金额 = 汇总【门诊挂号结算表（正常）.优惠总金额】
        outinDailyDTO.setPreferentialTotalPrice(MapUtils.get(allMap, "preferential_price"));
        // 实缴总金额 = 汇总【门诊挂号结算表（正常）.实收总金额】
        outinDailyDTO.setRealityTotalPrice(MapUtils.get(allMap, "reality_price"));
        // 一卡通支付总金额 = 汇总【门诊挂号结算表（正常）.一卡通支付总金额】
        outinDailyDTO.setCardTotalPrice(MapUtils.get(allMap, "card_price"));
        // 挂账总金额 = 汇总【门诊结算表（正常）.挂账金额】
        outinDailyDTO.setCreditTotalPrice(MapUtils.get(allMap, "credit_price"));
        // 挂号结算记录
        Map<String, Map<String, Object>> orsMap = outinDailyDAO.queryOutptRegisterSettle(dto);
        if (MapUtils.isEmpty(orsMap)) {
            return ;
        }
        //  冲红
        Map<String, Object> chMap = orsMap.get(Constants.ZTBZ.CH);
        if (!MapUtils.isEmpty(chMap)) {
            // 退款总金额 = 汇总【门诊挂号结算表（冲红）.实收总金额】
            outinDailyDTO.setBackTotalPrice(MapUtils.get(chMap, "reality_price", new BigDecimal(0)).abs());
        }
        // 总费用合计（包含退款总金额） = 汇总（正常、冲红）【门诊挂号结算表.挂号总金额】
        outinDailyDTO.setTotalPrice(outinDailyDTO.getTotalPrice().add(outinDailyDTO.getBackTotalPrice()));

        // *******************************门诊挂号日结 - 日结发票表*******************************
        List<Map<String, Object>> orsbList = outinDailyDAO.queryOutptRegisterSettleByBillId(dto);
        for (Map map : orsbList) {
            // 日结发票表
            OutinDailyInvoiceDTO outinDailyInvoiceDTO = buildOutinDailyInvoiceDTO(outinDailyDTO, typeCode);
            // 发票张数
            outinDailyInvoiceDTO.setNum(((Long)MapUtils.get(map, "num")).intValue());
            // 发票起始号码
            outinDailyInvoiceDTO.setStartNo(MapUtils.get(map, "start_no"));
            // 发票结束号码
            outinDailyInvoiceDTO.setEndNo(MapUtils.get(map, "end_no"));
            // 发票费用合计
            outinDailyInvoiceDTO.setTotalPrice(MapUtils.get(map, "total_price"));
            odiList.add(outinDailyInvoiceDTO);
        }

        // *******************************门诊挂号日结 - 日结支付方式表*******************************
        List<Map<String, Object>> orpList = outinDailyDAO.queryOutptRegisterPay(dto);
        for (Map map : orpList) {
            // 日结支付方式表
            OutinDailyPayDTO outinDailyPayDTO = buildOutinDailyPayDTO(outinDailyDTO, typeCode);
            // 支付方式代码
            outinDailyPayDTO.setPayCode(MapUtils.get(map, "pay_code"));
            // 支付总金额
            outinDailyPayDTO.setTotalPrice(MapUtils.get(map, "total_price"));
            odpList.add(outinDailyPayDTO);
        }

        // *******************************门诊挂号日结 - 日结财务分类表*******************************
        List<Map<String, Object>> ordList = outinDailyDAO.queryOutptRegisterDetail(dto);
        for (Map map : ordList) {
            // 日结财务分类表
            OutinDailyFinclassifyDTO outinDailyFinclassifyDTO = buildOutinDailyFinclassifyDTO(outinDailyDTO, typeCode);
            // 财务分类ID
            outinDailyFinclassifyDTO.setBfcId(MapUtils.get(map, "bfc_id"));
            // 总费用合计
            outinDailyFinclassifyDTO.setTotalPrice(MapUtils.get(map, "total_price"));
            // 优惠总金额
            outinDailyFinclassifyDTO.setPreferentialTotalPrice(MapUtils.get(map, "preferential_price"));
            // 优惠后总金额
            outinDailyFinclassifyDTO.setRealityTotalPrice(MapUtils.get(map, "reality_price"));
            odfList.add(outinDailyFinclassifyDTO);
        }

        // 回写挂号结算表日结缴款ID
        outinDailyDAO.updateDailyIdByOutptRegisterSettle(outinDailyDTO);
    }

    /**
     * @Method 日结缴款 - 门诊
     * @Description
     * 日结主表：
     * 1、总费用合计 = 汇总【门诊结算表.总金额】
     * 2、合同单位总金额 = 汇总【门诊结算表（正常）.统筹支付金额】
     * 3、预交总金额 = 0
     * 4、预交冲抵总金额 = 0
     * 5、退款总金额 = 汇总【门诊结算表（冲红）.实收金额】
     * 6、优惠总金额 = 汇总【门诊结算表（正常）总金额 - 优惠后金额】
     * 7、舍入总金额 = 汇总【门诊结算表（正常）.舍入金额】
     * 8、实缴总金额 = 汇总【门诊结算表（正常）.实收金额】
     *
     * 业务
     * 冲红总金额
     * 1、挂号费10元（正常），如果退款8元
     * 2、挂号费10元改为被冲红状态
     * 3、增加一条10元冲红记录
     * 4、增加一条2元正常记录
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 20:49
     * @Return
     **/
    private void handleDailyOutpt(OutinDailyDTO dto, String typeCode, List<OutinDailyDTO> odList, List<OutinDailyInvoiceDTO> odiList, List<OutinDailyPayDTO> odpList, List<OutinDailyFinclassifyDTO> odfList, List<OutinDailyInsureDTO> odinList, List<OutinDailyCardPayDO> odcpList) {
        // *******************************门诊日结 - 日结主表*******************************
        OutinDailyDTO outinDailyDTO = buildOutinDailyDTO(dto, typeCode);
        odList.add(outinDailyDTO);

        // 一卡通充值
        List<BaseCardRechargeChangeDTO> cardCZList = outinDailyDAO.queryBaseCardCZ(dto);
        // 一卡通充值
        if (!ListUtils.isEmpty(cardCZList)) {
            // 充值金额
            Map<String, List<BaseCardRechargeChangeDTO>> iapPriceMap = cardCZList.stream().collect(Collectors.groupingBy(e -> e.getStatusCode(), LinkedHashMap::new, Collectors.toList()));

            // 充值
            List<BaseCardRechargeChangeDTO> czList = iapPriceMap.get("6");
            if (!ListUtils.isEmpty(czList)) {
                // 一卡通充值
                outinDailyDTO.setYktczTotalPrice(czList.stream().map(e -> e.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add).abs());
            }
            // 退款
            List<BaseCardRechargeChangeDTO> chList = iapPriceMap.get("7");
            if (!ListUtils.isEmpty(chList)) {
                // 一卡通退款
                outinDailyDTO.setYkttkTotalPrice(chList.stream().map(e -> e.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add).abs());
            }

            // 一卡通充值方式
            Map<String, List<BaseCardRechargeChangeDTO>> iapPayMap = cardCZList.stream().collect(Collectors.groupingBy(e -> e.getPayCode(), LinkedHashMap::new, Collectors.toList()));
            for (Map.Entry<String, List<BaseCardRechargeChangeDTO>> map : iapPayMap.entrySet()) {
                OutinDailyCardPayDO odcp = buildOutinDailyCardPayDTO(outinDailyDTO, typeCode);
                // 支付方式
                odcp.setPayCode(map.getKey());
                // 预交金支付金额
                odcp.setTotalPrice(map.getValue().stream().map(e -> e.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                odcpList.add(odcp);
            }
        }

        // 回写一卡通异动表日结缴款ID
        outinDailyDAO.updateDailyIdToBaseCardRechangeChargeDO(outinDailyDTO);

        // 门诊结算记录
        Map<String, Map<String, Object>>  mzMap = outinDailyDAO.queryOutptSettleAll(dto);
        if (MapUtils.isEmpty(mzMap)) {
            return ;
        }
        // 总费用合计（包含退款总金额） = 汇总（正常）【门诊结算表.总金额】
        outinDailyDTO.setTotalPrice(MapUtils.get(mzMap, "total_price"));
        // 合同单位总金额 = 汇总【门诊结算表（正常）.统筹支付金额】
        outinDailyDTO.setInsureTotalPrice(MapUtils.get(mzMap, "mi_price"));
        // 优惠总金额 = 汇总【门诊结算表（正常）总金额 - 优惠后金额】
        outinDailyDTO.setPreferentialTotalPrice(outinDailyDTO.getTotalPrice().subtract(MapUtils.get(mzMap, "reality_price")));
        // 舍入总金额 = 汇总【门诊结算表（正常）.舍入金额】
        outinDailyDTO.setRoundTotalPrice(MapUtils.get(mzMap, "trunc_price"));
        // 实缴总金额 = 汇总【门诊结算表（正常）.实收金额】
        outinDailyDTO.setRealityTotalPrice(MapUtils.get(mzMap, "actual_price"));
        // 一卡通支付总金额 = 汇总【门诊结算表（正常）.一卡通支付总金额】
        outinDailyDTO.setCardTotalPrice(MapUtils.get(mzMap, "card_price"));
        // 挂账总金额 = 汇总【门诊结算表（正常）.挂账金额】
        outinDailyDTO.setCreditTotalPrice(MapUtils.get(mzMap, "credit_price"));

        // 门诊结算记录
        Map<String, Map<String, Object>>  osMap = outinDailyDAO.queryOutptSettle(dto);
        if (MapUtils.isEmpty(osMap)) {
            return ;
        }
        //  冲红
        Map chMap = osMap.get(Constants.ZTBZ.CH);
        if (!MapUtils.isEmpty(chMap)) {
            // 退款总金额 = 汇总【门诊挂号结算表（冲红）.实收总金额】
            outinDailyDTO.setBackTotalPrice(MapUtils.get(chMap, "total_price", new BigDecimal(0)).abs());
        }
        // 总费用合计（包含退款总金额） = 汇总（正常、冲红）【门诊结算表.总金额】
        outinDailyDTO.setTotalPrice(outinDailyDTO.getTotalPrice().add(outinDailyDTO.getBackTotalPrice()));

        // *******************************门诊日结 - 日结发票表*******************************
        List<Map<String, Object>> osiList = outinDailyDAO.queryOutptSettleInvoice(dto);
        if (!ListUtils.isEmpty(osiList)) {
            for (Map map : osiList) {
                // 日结发票表
                OutinDailyInvoiceDTO outinDailyInvoiceDTO = buildOutinDailyInvoiceDTO(outinDailyDTO, typeCode);
                // 发票张数
                outinDailyInvoiceDTO.setNum(((Long)MapUtils.get(map, "num")).intValue());
                // 发票起始号码
                outinDailyInvoiceDTO.setStartNo(MapUtils.get(map, "start_no"));
                // 发票结束号码
                outinDailyInvoiceDTO.setEndNo(MapUtils.get(map, "end_no"));
                // 发票费用合计
                outinDailyInvoiceDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                outinDailyInvoiceDTO.setZfNum(((Long)MapUtils.get(map, "zfnum")).intValue());   // 作废数量
                outinDailyInvoiceDTO.setTfNum(((Long)MapUtils.get(map, "tfnum")).intValue());   // 退费数量
                outinDailyInvoiceDTO.setUseNum(((Long)MapUtils.get(map, "usenum")).intValue()); // 使用数量
                odiList.add(outinDailyInvoiceDTO);
            }
        }

        // *******************************门诊日结 - 支付方式表*******************************
        List<Map<String, Object>> opList = outinDailyDAO.queryOutptPay(dto);
        if (!ListUtils.isEmpty(opList)) {
            for (Map map : opList) {
                // 日结支付方式表
                OutinDailyPayDTO outinDailyPayDTO = buildOutinDailyPayDTO(outinDailyDTO, typeCode);
                // 支付方式代码
                outinDailyPayDTO.setPayCode(MapUtils.get(map, "pay_code"));
                // 支付总金额
                outinDailyPayDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                odpList.add(outinDailyPayDTO);
            }
        }

        // *******************************门诊日结 - 财务分类表*******************************
        List<Map<String, Object>> ocList = outinDailyDAO.queryOutptCost(dto);
        if (!ListUtils.isEmpty(ocList)) {
            for (Map map : ocList) {
                // 日结财务分类表
                OutinDailyFinclassifyDTO outinDailyFinclassifyDTO = buildOutinDailyFinclassifyDTO(outinDailyDTO, typeCode);
                // 财务分类ID
                outinDailyFinclassifyDTO.setBfcId(MapUtils.get(map, "bfc_id"));
                // 总费用合计
                outinDailyFinclassifyDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                // 优惠总金额
                outinDailyFinclassifyDTO.setPreferentialTotalPrice(MapUtils.get(map, "preferential_price"));
                // 优惠后总金额
                outinDailyFinclassifyDTO.setRealityTotalPrice(MapUtils.get(map, "reality_price"));
                odfList.add(outinDailyFinclassifyDTO);
            }
        }

        // *******************************门诊日结 - 日结合同单位支付表*******************************
        List<Map<String, Object>> oipList = outinDailyDAO.queryOutptInsurePay(dto);
        if (!ListUtils.isEmpty(oipList)) {
            for (Map map : oipList) {
                // 日结合同单位支付表
                OutinDailyInsureDTO outinDailyInsureDTO = buildOutinDailyInsureDTO(outinDailyDTO, typeCode);
                // 合同单位明细代码（HTDW）
                // outinDailyInsureDTO.setInsureCode(MapUtils.get(map, "type_code"));
                // 统筹支付总金额
                outinDailyInsureDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                // 医保支付总金额
                outinDailyInsureDTO.setInsurePrice(MapUtils.get(map, "insure_price"));
                // 个人账户按支付总金额
                outinDailyInsureDTO.setPersonalPrice(MapUtils.get(map, "personal_price"));
                odinList.add(outinDailyInsureDTO);
            }
        }
        // 回写门诊结算表日结缴款ID
        outinDailyDAO.updateDailyIdByOutptSettle(outinDailyDTO);
    }

    /**
     * @Method 日结缴款 - 住院
     * @Description
     * 日结主表：
     * 1、总费用合计 = 汇总【住院结算表.总金额】
     * 2、合同单位总金额 = 汇总【住院结算表（正常）.统筹支付金额】
     * 3、预交总金额 = 汇总【住院预交金表.预交金额】
     * 4、预交冲抵总金额 = 汇总【住院结算表.预交金冲抵】
     * 5、退款总金额 = 汇总【住院结算表（冲红）.实收金额】
     * 6、优惠总金额 = 汇总【住院结算表（正常）总金额 - 优惠后金额】
     * 7、舍入总金额 = 汇总【住院结算表（正常）.舍入金额】
     * 8、实缴总金额 = 汇总【住院结算表（正常）.实收金额】
     *
     * 业务
     * 冲红总金额
     * 1、挂号费10元（正常），如果退款8元
     * 2、挂号费10元改为被冲红状态
     * 3、增加一条10元冲红记录
     * 4、增加一条2元正常记录
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 20:49
     * @Return
     **/
    private void handleDailyInpt(OutinDailyDTO dto, String typeCode, List<OutinDailyDTO> odList, List<OutinDailyInvoiceDTO> odiList, List<OutinDailyPayDTO> odpList, List<OutinDailyFinclassifyDTO> odfList, List<OutinDailyInsureDTO> odinList,  List<OutinDailyAdvancePayDO> odapList) {
        // *******************************住院日结 - 日结主表*******************************
        OutinDailyDTO outinDailyDTO = buildOutinDailyDTO(dto, typeCode);
        odList.add(outinDailyDTO);

        // 住院结算记录
        Map<String, Map<String, Object>> isMap = outinDailyDAO.queryInptSettle(dto);
        // 住院预交金
        List<InptAdvancePayDO> iapList = outinDailyDAO.queryInptAdvancePay(dto);
        if (MapUtils.isEmpty(isMap) && ListUtils.isEmpty(iapList)) {
            return;
        }

        // 住院预交金
        if (!ListUtils.isEmpty(iapList)) {
            // 预交金金额
            Map<String, List<InptAdvancePayDO>> iapPriceMap = iapList.stream().collect(Collectors.groupingBy(e -> e.getStatusCode(), LinkedHashMap::new, Collectors.toList()));
            // 正常
//            List<InptAdvancePayDO> zcList = iapPriceMap.get(Constants.ZTBZ.ZC) ;
//            if (!ListUtils.isEmpty(zcList)) {
                // 预交金实收
                outinDailyDTO.setYjssTotalPrice(iapList.stream().map(e -> e.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
//            }
            // 冲红
            List<InptAdvancePayDO> chList = iapPriceMap.get(Constants.ZTBZ.CH);
            if (!ListUtils.isEmpty(chList)) {
                // 预交金退款
                outinDailyDTO.setYjtkTotalPrice(chList.stream().map(e -> e.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add).abs());
            }

            // 预交金支付方式
            Map<String, List<InptAdvancePayDO>> iapPayMap = iapList.stream().collect(Collectors.groupingBy(e -> e.getPayCode(), LinkedHashMap::new, Collectors.toList()));
            for (Map.Entry<String, List<InptAdvancePayDO>> map : iapPayMap.entrySet()) {
                OutinDailyAdvancePayDO odap = buildOutinDailyAdvancePayDTO(outinDailyDTO, typeCode);
                // 支付方式
                odap.setPayCode(map.getKey());
                // 预交金支付金额
                odap.setTotalPrice(map.getValue().stream().map(e -> e.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                odapList.add(odap);
            }
        }
        // 出院结算
        if (!MapUtils.isEmpty(isMap)) {
            // 住院结算记录
            Map<String, Map<String, Object>> allMap = outinDailyDAO.queryInptSettleAll(dto);
            if (!MapUtils.isEmpty(allMap)) {
                // 预交金结算总金额
                outinDailyDTO.setYjjsTotalPrice(MapUtils.get(allMap, "ap_total_price", new BigDecimal(0)));
                // 预交金冲抵
                outinDailyDTO.setYjcdTotalPrice(BigDecimalUtils.subtract(MapUtils.get(allMap, "ap_total_price", new BigDecimal(0)), MapUtils.get(allMap, "settle_back_price", new BigDecimal(0))));
//                //退款总金额（减）
//                outinDailyDTO.setBackTotalPrice( MapUtils.get(allMap, "settle_back_price", new BigDecimal(0)));
                // 总费用合计（包含退款总金额） = 汇总【住院结算表（正常）.总金额】 + 汇总【住院结算表（冲红）.总金额】
                outinDailyDTO.setTotalPrice(MapUtils.get(allMap, "reality_price", new BigDecimal(0)));
                // 合同单位总金额 = 汇总【住院结算表（正常）.统筹支付金额】
                outinDailyDTO.setInsureTotalPrice(MapUtils.get(allMap, "mi_price", new BigDecimal(0)));
                // 优惠总金额 = 汇总【住院结算表（正常）总金额 - 优惠后金额】
                outinDailyDTO.setPreferentialTotalPrice(BigDecimalUtils.subtract(MapUtils.get(allMap, "total_price", new BigDecimal(0)), MapUtils.get(allMap, "reality_price", new BigDecimal(0))));
                // 舍入总金额 = 汇总【住院结算表（正常）.舍入金额】
                outinDailyDTO.setRoundTotalPrice(MapUtils.get(allMap, "trunc_price", new BigDecimal(0)));
                // 实缴总金额 = 汇总【总费用合计 - 舍入金额】
                outinDailyDTO.setRealityTotalPrice(BigDecimalUtils.subtract(MapUtils.get(allMap, "reality_price", new BigDecimal(0)), MapUtils.get(allMap, "trunc_price", new BigDecimal(0))));
                // 挂账金额
                outinDailyDTO.setCreditTotalPrice(MapUtils.get(allMap, "credit_price", new BigDecimal(0)));
            }


            // 冲红状态
            Map chMap = isMap.get(Constants.ZTBZ.CH);
            if (!MapUtils.isEmpty(chMap)) {
                // 退款总金额 = 汇总【门诊结算表（冲红）.实收金额】
                outinDailyDTO.setBackTotalPrice(MapUtils.get(chMap, "total_price", new BigDecimal(0)).abs());
            }
            // 总费用合计（包含退款总金额） = 汇总【住院结算表（正常）.总金额】 + 汇总【住院结算表（冲红）.总金额】
            outinDailyDTO.setTotalPrice(BigDecimalUtils.add(outinDailyDTO.getTotalPrice(), outinDailyDTO.getBackTotalPrice()));
        }
        // *******************************住院日结 - 日结发票表*******************************
        List<Map<String, Object>> isiList = outinDailyDAO.queryInptSettleInvoice(dto);
        if (!ListUtils.isEmpty(isiList)) {
            for (Map map : isiList) {
                // 日结发票表
                OutinDailyInvoiceDTO outinDailyInvoiceDTO = buildOutinDailyInvoiceDTO(outinDailyDTO, typeCode);
                // 发票张数
                outinDailyInvoiceDTO.setNum(((Long)MapUtils.get(map, "num")).intValue());
                // 发票起始号码
                outinDailyInvoiceDTO.setStartNo(MapUtils.get(map, "start_no"));
                // 发票结束号码
                outinDailyInvoiceDTO.setEndNo(MapUtils.get(map, "end_no"));
                // 发票费用合计
                outinDailyInvoiceDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                outinDailyInvoiceDTO.setZfNum(((Long)MapUtils.get(map, "zfnum")).intValue());   // 作废数量
                outinDailyInvoiceDTO.setTfNum(((Long)MapUtils.get(map, "tfnum")).intValue());   // 退费数量
                outinDailyInvoiceDTO.setUseNum(((Long)MapUtils.get(map, "usenum")).intValue()); // 使用数量
                odiList.add(outinDailyInvoiceDTO);
            }
        }

        // *******************************住院日结 - 日结支付方式表*******************************
        List<Map<String, Object>> ipList = outinDailyDAO.queryInptPay(dto);
        if (!ListUtils.isEmpty(ipList)) {
            for (Map map : ipList) {
                // 日结支付方式表
                OutinDailyPayDTO outinDailyPayDTO = buildOutinDailyPayDTO(outinDailyDTO, typeCode);
                // 支付方式代码
                outinDailyPayDTO.setPayCode(MapUtils.get(map, "pay_code"));
                // 支付总金额
                outinDailyPayDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                odpList.add(outinDailyPayDTO);
            }
        }

        // *******************************住院日结 - 日结财务分类表*******************************
        List<Map<String, Object>> icList = outinDailyDAO.queryIntptCost(dto);
        if (!ListUtils.isEmpty(icList)) {
            for (Map map : icList) {
                // 日结财务分类表
                OutinDailyFinclassifyDTO outinDailyFinclassifyDTO = buildOutinDailyFinclassifyDTO(outinDailyDTO, typeCode);
                // 财务分类ID
                outinDailyFinclassifyDTO.setBfcId(MapUtils.get(map, "bfc_id"));
                // 总费用合计
                outinDailyFinclassifyDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                // 优惠总金额
                outinDailyFinclassifyDTO.setPreferentialTotalPrice(MapUtils.get(map, "preferential_price"));
                // 优惠后总金额
                outinDailyFinclassifyDTO.setRealityTotalPrice(MapUtils.get(map, "reality_price"));
                odfList.add(outinDailyFinclassifyDTO);
            }
        }

        // *******************************住院日结 - 日结合同单位支付表*******************************
        List<Map<String, Object>> iipList = outinDailyDAO.queryInptInsurePay(dto);
        if (!ListUtils.isEmpty(iipList)) {
            for (Map map : iipList) {
                // 日结合同单位支付表
                OutinDailyInsureDTO outinDailyInsureDTO = buildOutinDailyInsureDTO(outinDailyDTO, typeCode);
                // 合同单位明细代码（HTDW）
                // outinDailyInsureDTO.setInsureCode(MapUtils.get(map, "type_code"));
                // 统筹支付总金额
                outinDailyInsureDTO.setTotalPrice(MapUtils.get(map, "total_price"));
                // 医保支付总金额
                outinDailyInsureDTO.setInsurePrice(MapUtils.get(map, "insure_price"));
                // 个人账户按支付总金额
                outinDailyInsureDTO.setPersonalPrice(MapUtils.get(map, "personal_price"));
                odinList.add(outinDailyInsureDTO);
            }
        }

        // 回写住院结算表日结缴款ID
        outinDailyDAO.updateDailyIdByInptSettle(outinDailyDTO);
        // 回写住院预交金表日结缴款ID
        outinDailyDAO.updateDailyIdByInptAdvancePay(outinDailyDTO);
    }

    /**
     * @Method 构建日结缴款-主表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/24 21:02
     * @Return 
     **/
    private OutinDailyDTO buildOutinDailyDTO(OutinDailyDTO dto, String typeCode) {
        // 日结缴款主表
        OutinDailyDTO outinDailyDTO = new OutinDailyDTO();
        // 主键
        outinDailyDTO.setId(SnowflakeUtils.getId());
        // 医院编码
        outinDailyDTO.setHospCode(dto.getHospCode());
        // 日结单号
        outinDailyDTO.setDailyNo(dto.getDailyNo());
        // 开始时间
        outinDailyDTO.setStartTime(dto.getStartTime());
        // 结束时间
        outinDailyDTO.setEndTime(dto.getEndTime());
        // 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
        outinDailyDTO.setTypeCode(typeCode);
        // 总费用
        outinDailyDTO.setTotalPrice(new BigDecimal(0));
        // 统筹支付总金额
        outinDailyDTO.setInsureTotalPrice(new BigDecimal(0));
        // 预交金实收总金额
        outinDailyDTO.setYjssTotalPrice(new BigDecimal(0));
        // 预交金退款总金额
        outinDailyDTO.setYjtkTotalPrice(new BigDecimal(0));
        // 预交金结算总金额
        outinDailyDTO.setYjjsTotalPrice(new BigDecimal(0));
        // 预交金冲抵总金额
        outinDailyDTO.setYjcdTotalPrice(new BigDecimal(0));
        // 退款总金额
        outinDailyDTO.setBackTotalPrice(new BigDecimal(0));
        // 优惠总金额
        outinDailyDTO.setPreferentialTotalPrice(new BigDecimal(0));
        // 舍入总金额
        outinDailyDTO.setRoundTotalPrice(new BigDecimal(0));
        // 实缴总金额
        outinDailyDTO.setRealityTotalPrice(new BigDecimal(0));
        // 一卡通支付总金额
        outinDailyDTO.setCardTotalPrice(new BigDecimal(0));
        // 是否确认缴款
        outinDailyDTO.setIsOk(Constants.SF.F);
        // 创建人/缴款人ID
        outinDailyDTO.setCrteId(dto.getCrteId());
        // 创建人/缴款人姓名
        outinDailyDTO.setCrteName(dto.getCrteName());
        // 创建时间
        outinDailyDTO.setCrteTime(new Date());
        // 挂账总金额
        outinDailyDTO.setCreditTotalPrice(new BigDecimal(0));

        return outinDailyDTO;
    }

    /**
     * @Method 构建日结缴款-发票表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 21:02
     * @Return
     **/
    private OutinDailyInvoiceDTO buildOutinDailyInvoiceDTO(OutinDailyDTO dto, String typeCode) {
        // 日结缴款主表
        OutinDailyInvoiceDTO outinDailyInvoiceDTO = new OutinDailyInvoiceDTO();
        // 主键
        outinDailyInvoiceDTO.setId(SnowflakeUtils.getId());
        // 医院编码
        outinDailyInvoiceDTO.setHospCode(dto.getHospCode());
        // 日结ID
        outinDailyInvoiceDTO.setDailyId(dto.getId());
        // 日结单号
        outinDailyInvoiceDTO.setDailyNo(dto.getDailyNo());
        // 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
        outinDailyInvoiceDTO.setTypeCode(typeCode);
        // 发票张数
        outinDailyInvoiceDTO.setNum(0);
        // 发票起始号码
        outinDailyInvoiceDTO.setStartNo("");
        // 发票结束号码
        outinDailyInvoiceDTO.setEndNo("");
        // 发票费用合计
        outinDailyInvoiceDTO.setTotalPrice(new BigDecimal(0));
        // 发票退费数
        outinDailyInvoiceDTO.setTfNum(0);
        // 发票作废数
        outinDailyInvoiceDTO.setZfNum(0);
        // 发票使用数
        outinDailyInvoiceDTO.setUseNum(0);

        return outinDailyInvoiceDTO;
    }

    /**
     * @Method 构建日结缴款-支付方式表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 21:02
     * @Return
     **/
    private OutinDailyPayDTO buildOutinDailyPayDTO(OutinDailyDTO dto, String typeCode) {
        // 日结缴款支付方式表
        OutinDailyPayDTO outinDailyPayDTO = new OutinDailyPayDTO();
        // 主键
        outinDailyPayDTO.setId(SnowflakeUtils.getId());
        // 医院编码
        outinDailyPayDTO.setHospCode(dto.getHospCode());
        // 日结ID
        outinDailyPayDTO.setDailyId(dto.getId());
        // 日结单号
        outinDailyPayDTO.setDailyNo(dto.getDailyNo());
        // 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
        outinDailyPayDTO.setTypeCode(typeCode);
        // 支付方式代码（ZFFS）
        outinDailyPayDTO.setPayCode(Constants.ZFFS.XJ);
        // 支付总金额
        outinDailyPayDTO.setTotalPrice(new BigDecimal(0));
        return outinDailyPayDTO;
    }

    /**
     * @Method 构建日结缴款-财务分类表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 21:02
     * @Return
     **/
    private OutinDailyFinclassifyDTO buildOutinDailyFinclassifyDTO(OutinDailyDTO dto, String typeCode) {
        // 日结缴款财务分类表
        OutinDailyFinclassifyDTO outinDailyFinclassifyDTO = new OutinDailyFinclassifyDTO();
        // 主键
        outinDailyFinclassifyDTO.setId(SnowflakeUtils.getId());
        // 医院编码
        outinDailyFinclassifyDTO.setHospCode(dto.getHospCode());
        // 日结ID
        outinDailyFinclassifyDTO.setDailyId(dto.getId());
        // 日结单号
        outinDailyFinclassifyDTO.setDailyNo(dto.getDailyNo());
        // 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
        outinDailyFinclassifyDTO.setTypeCode(typeCode);
        // 总费用合计
        outinDailyFinclassifyDTO.setTotalPrice(new BigDecimal(0));
        // 优惠总金额
        outinDailyFinclassifyDTO.setPreferentialTotalPrice(new BigDecimal(0));
        // 优惠后总金额
        outinDailyFinclassifyDTO.setRealityTotalPrice(new BigDecimal(0));
        return outinDailyFinclassifyDTO;
    }

    /**
     * @Method 构建日结缴款-合同单位支付表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 21:02
     * @Return
     **/
    private OutinDailyInsureDTO buildOutinDailyInsureDTO(OutinDailyDTO dto, String typeCode) {
        // 日结缴款合同单位支付表
        OutinDailyInsureDTO outinDailyInsureDTO = new OutinDailyInsureDTO();
        // 主键
        outinDailyInsureDTO.setId(SnowflakeUtils.getId());
        // 医院编码
        outinDailyInsureDTO.setHospCode(dto.getHospCode());
        // 日结ID
        outinDailyInsureDTO.setDailyId(dto.getId());
        // 日结单号
        outinDailyInsureDTO.setDailyNo(dto.getDailyNo());
        // 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
        outinDailyInsureDTO.setTypeCode(typeCode);
        // 合同单位明细代码（HTDW）
        outinDailyInsureDTO.setInsureCode("");
        // 医保支付总金额- 个人账户支付总金额
        outinDailyInsureDTO.setTotalPrice(new BigDecimal(0));
        // 医保支付总金额
        outinDailyInsureDTO.setInsurePrice(new BigDecimal(0));
        // 个人账户支付总金额
        outinDailyInsureDTO.setPersonalPrice(new BigDecimal(0));
        return outinDailyInsureDTO;
    }

    /**
     * @Method 构建日结缴款-预交金支付方式表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 21:02
     * @Return
     **/
    private OutinDailyAdvancePayDO buildOutinDailyAdvancePayDTO(OutinDailyDTO dto, String typeCode) {
        // 日结缴款支付方式表
        OutinDailyAdvancePayDO outinDailyAdvancePayDO = new OutinDailyAdvancePayDO();
        // 主键
        outinDailyAdvancePayDO.setId(SnowflakeUtils.getId());
        // 医院编码
        outinDailyAdvancePayDO.setHospCode(dto.getHospCode());
        // 日结ID
        outinDailyAdvancePayDO.setDailyId(dto.getId());
        // 日结单号
        outinDailyAdvancePayDO.setDailyNo(dto.getDailyNo());
        // 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
        outinDailyAdvancePayDO.setTypeCode(typeCode);
        // 支付方式代码（ZFFS）
        outinDailyAdvancePayDO.setPayCode(Constants.ZFFS.XJ);
        // 支付总金额
        outinDailyAdvancePayDO.setTotalPrice(new BigDecimal(0));
        return outinDailyAdvancePayDO;
    }

    /**
     * @Method 构建日结缴款-预交金支付方式表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 21:02
     * @Return
     **/
    private OutinDailyCardPayDO buildOutinDailyCardPayDTO(OutinDailyDTO dto, String typeCode) {
        // 日结缴款支付方式表
        OutinDailyCardPayDO outinDailyCardPayDO = new OutinDailyCardPayDO();
        // 主键
        outinDailyCardPayDO.setId(SnowflakeUtils.getId());
        // 医院编码
        outinDailyCardPayDO.setHospCode(dto.getHospCode());
        // 日结ID
        outinDailyCardPayDO.setDailyId(dto.getId());
        // 日结单号
        outinDailyCardPayDO.setDailyNo(dto.getDailyNo());
        // 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
        outinDailyCardPayDO.setTypeCode(typeCode);
        // 支付方式代码（ZFFS）
        outinDailyCardPayDO.setPayCode(Constants.ZFFS.XJ);
        // 支付总金额
        outinDailyCardPayDO.setTotalPrice(new BigDecimal(0));
        return outinDailyCardPayDO;
    }

    /**
     * @Method 确认缴款
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/25 23:17
     * @Return 
     **/
    @Override
    public boolean update(OutinDailyDTO outinDailyDTO) {
        List<OutinDailyDTO> odList = outinDailyDAO.queryOutinDailyByDailyNo(outinDailyDTO);
        if (ListUtils.isEmpty(odList)) {
            throw new AppException("当前缴款记录不存在");
        }
        odList.stream().forEach(od -> {
            if (Constants.SF.S.equals(od.getIsOk())) {
                throw new AppException("当前缴款记录已确认，无需重复确认");
            }
        });
        return outinDailyDAO.update(outinDailyDTO) > 0;
    }

    /**
     * @Method 取消缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/25 23:17
     * @Return
     **/
    @Override
    public boolean delete(OutinDailyDTO outinDailyDTO) {
        List<OutinDailyDTO> odList = outinDailyDAO.queryOutinDailyByDailyNo(outinDailyDTO);
        if (ListUtils.isEmpty(odList)) {
            throw new AppException("当前缴款记录不存在");
        }
        outinDailyDTO.setTypeCode(odList.get(0).getTypeCode());
        OutinDailyDTO maxOutinDailyDTO = outinDailyDAO.queryOutinDailyByCreateId(outinDailyDTO);
        odList.stream().forEach(od -> {
            if (Constants.SF.S.equals(od.getIsOk())) {
                throw new AppException("当前缴款记录已确认，不能取消缴款");
            }
            if(DateUtils.dateCompare(od.getStartTime(), maxOutinDailyDTO.getStartTime())){
                throw new AppException("只能从最后一次缴款开始取消");
            }
        });


        // 清空结算表日结ID
        outinDailyDAO.updateDailyIdIsNull(outinDailyDTO);

        // 删除日结数据
        outinDailyDAO.delete(outinDailyDTO);
        return true;
    }

    /**
     * @Method 分页查询日结主表信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @Override
    public PageDTO queryPage(OutinDailyDTO outinDailyDTO) {
        // 设置分页信息
        PageHelper.startPage(outinDailyDTO.getPageNo(), outinDailyDTO.getPageSize());
        // 填充缴款类型
        fullJklxList(outinDailyDTO);
        // 查询所有
        List<OutinDailyDTO> outinDailyDTOList = outinDailyDAO.queryAll(outinDailyDTO);
        // 返回分页结果
        return PageDTO.of(outinDailyDTOList);
    }

    /**
     * @Method 根据单据类型查询门诊挂号缴款报表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/30 9:47
     * @Return 
     **/
    @Override
    public Map<String, Object> getOutinDaily(OutinDailyDTO dto) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        // 门诊缴款
        if (Constants.JKLX.MZGH.equals(dto.getTypeCode()) || Constants.JKLX.MZSF.equals(dto.getTypeCode())) {
            // 日结缴款参数代码
            // 日结缴款方式（0：门诊挂号、门诊收费合并日结缴款，1：门诊挂号、门诊收费分开日结缴款）
            String value = getSysParameter(dto.getHospCode(), "RJJKFS");

            OutinDailyDO odMzGh = null;
            OutinDailyDO odMzSF = null;
            List<Map<String, Object>> MZ_GH03_LIST = null;
            List<Map<String, Object>> MZ_SF03_LIST = null;
            List<Map<String, Object>> MZ_YKT_LIST = null;
            // 日结发票
            List<Map<String, Object>> MZ_05 = outinDailyDAO.queryOutinDailyInvoiceByDailyNo(dto);
            Map<String, List<Map<String, Object>>> MZ_05_MAP_LIST = MZ_05.stream().collect(Collectors.groupingBy(e -> MapUtils.get(e, "type_code"), LinkedHashMap::new, Collectors.toList()));
            // 门诊挂号收入
            if ("0".equals(value) || Constants.JKLX.MZGH.equals(dto.getTypeCode())) {
                dto.setTypeCode(Constants.JKLX.MZGH);

                // 门诊挂号 - 财务分类
                List<Map<String, Object>> RT_0201 = outinDailyDAO.queryDailyByDailyNo(dto);
                // 明细不平处理2021年12月23日11:25:57 ====== start
                // 2021年12月23日11:08:15 将不在缴款单上的财务分类都计算到其他费用
                BigDecimal huafenQiTa = new BigDecimal(0);
                for (Map<String, Object> tempMap : RT_0201) {
                    if (!"B".equals(tempMap.get("code")) && !"A".equals(tempMap.get("code")) && !"C".equals(tempMap.get("code")) && !"J".equals(tempMap.get("code"))) {
                        huafenQiTa = BigDecimalUtils.add(huafenQiTa, (BigDecimal) tempMap.get("total_price"));
                    }
                }
                // 更新其他费用，其他费用会加上不在缴款表示显示的财务分类
                for (int i = 0; i < RT_0201.size(); i++) {
                    if("I".equals(RT_0201.get(i).get("code"))) {
                        Map<String, Object> qitaMap = RT_0201.get(i);
                        qitaMap.put("total_price", BigDecimalUtils.add(huafenQiTa, (BigDecimal) qitaMap.get("total_price")));
                        RT_0201.set(i, qitaMap);
                    }
                }

                RT_0201 = RT_0201.stream().filter(map ->
                        ("A".equals(MapUtils.get(map, "code")) || "B".equals(MapUtils.get(map, "code")) ||
                                "J".equals(MapUtils.get(map, "code")) || "I".equals(MapUtils.get(map, "code")) ||
                                !BigDecimalUtils.isZero(MapUtils.get(map, "total_price")))
                ).collect(Collectors.toList());
                resultMap.put("RT_0201", RT_0201);

                // 门诊挂号 - 日结主表
                odMzGh = outinDailyDAO.getOutinDaily(dto);
                Map<String, Object> RT_0202 = new HashMap<>();
                // 费入合计
                RT_0202.put("RT_020201", odMzGh != null ? odMzGh.getTotalPrice() : "0.00");
                // 退费合计
                RT_0202.put("RT_020202", odMzGh != null ? odMzGh.getBackTotalPrice() : "0.00");
                // 舍入合计
                RT_0202.put("RT_020203", odMzGh != null ? odMzGh.getRoundTotalPrice() : "0.00");
                // 实收合计
                RT_0202.put("RT_020204", odMzGh != null ? odMzGh.getRealityTotalPrice() : "0.00");
                // 优惠合计
                RT_0202.put("RT_020205", odMzGh != null ? odMzGh.getPreferentialTotalPrice() : "0.00");
                resultMap.put("RT_0202", RT_0202);

                // 门诊挂号 - 费用构成  TODO 添加一卡通
                MZ_GH03_LIST = outinDailyDAO.queryOutinDailyPay(dto);
                StringBuffer RT_0203 = new StringBuffer();
                MZ_GH03_LIST.forEach(m -> {
                    if (RT_0203.length() != 0) {
                        RT_0203.append("，");
                    }
                    RT_0203.append(m.get("name")).append("：").append(m.get("total_price")).append("元");
                });

                // 2021年8月23日13:59:04 查询结算表一卡通支付金额
                Map<String, Object> cardTotalPriceMap = outinDailyDAO.getOutptRegisterSettleCardTotalPrice(dto);
                RT_0203.append("，一卡通：" + (cardTotalPriceMap != null ? MapUtils.get(cardTotalPriceMap, "cardTotalPrice") : "0.00") + "元");

                resultMap.put("RT_0203", RT_0203);

                // 挂号发票
                List<Map<String, Object>> MZ_05_MZGH = MZ_05_MAP_LIST.get(Constants.JKLX.MZGH);
                if (ListUtils.isEmpty(MZ_05_MZGH)) {
                    resultMap.put("RT_040204", "门诊挂号发票：(    -    )0张");
                } else {
                    StringBuffer RT_040204 = new StringBuffer("门诊挂号发票：");
                    MZ_05_MZGH.forEach(m -> {
                        RT_040204.append("(  ").append(m.get("start_no")).append("  -  ").append(m.get("end_no")).append("  ) 共使用").append(m.get("num")).append("张");
                        RT_040204.append("，");
                    });
                    RT_040204.deleteCharAt(RT_040204.length() - 1);
                    resultMap.put("RT_040204", RT_040204);
                }
            }
            // 门诊收费收入
            if ("0".equals(value) || Constants.JKLX.MZSF.equals(dto.getTypeCode())) {
                dto.setTypeCode(Constants.JKLX.MZSF);

                // 门诊收费 - 财务分类
                List<Map<String, Object>> RT_0101 = outinDailyDAO.queryDailyByDailyNo(dto);
                resultMap.put("RT_0101", RT_0101);

                // 门诊收费 - 费用合计
                odMzSF = outinDailyDAO.getOutinDaily(dto);
                Map<String, Object> RT_0102 = new HashMap<>();
                // 费入合计
                RT_0102.put("RT_010201", odMzSF != null ? odMzSF.getTotalPrice() : "0.00");
                // 退费合计
                RT_0102.put("RT_010202", odMzSF != null ? odMzSF.getBackTotalPrice() : "0.00");
                // 舍入合计
                RT_0102.put("RT_010203", odMzSF != null ? odMzSF.getRoundTotalPrice() : "0.00");
                // 实收合计
                RT_0102.put("RT_010204", odMzSF != null ? odMzSF.getRealityTotalPrice() : "0.00");
                // 优惠合计
                RT_0102.put("RT_010205", odMzSF != null ? odMzSF.getPreferentialTotalPrice() : "0.00");
                resultMap.put("RT_0102", RT_0102);

                // *******************************门诊日结 - 一卡通收入*******************************
                // 一卡通合计
                BigDecimal yktcz = odMzSF != null ? odMzSF.getYktczTotalPrice() != null ? odMzSF.getYktczTotalPrice() : new BigDecimal(0) : new BigDecimal(0);
                BigDecimal ykttk = odMzSF != null ? odMzSF.getYkttkTotalPrice() != null ? odMzSF.getYkttkTotalPrice() : new BigDecimal(0) : new BigDecimal(0);
                List<Map<String, Object>> RT_0301 = new ArrayList<>();
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("code", "B");
                tempMap.put("total_price", yktcz);
                tempMap.put("name", "充值合计");
                RT_0301.add(tempMap);
                Map<String, Object> tempMap1 = new HashMap<>();
                tempMap1.put("code", "C");
                tempMap1.put("total_price", ykttk);
                tempMap1.put("name", "退款合计");
                RT_0301.add(tempMap1);
                Map<String, Object> tempMap2 = new HashMap<>();
                tempMap2.put("code", "D");
                tempMap2.put("total_price", BigDecimalUtils.subtract(yktcz, ykttk));
                tempMap2.put("name", "实收合计");
                RT_0301.add(tempMap2);
                resultMap.put("RT_0301", RT_0301);

                // 一卡通收入 -->> 支付方式组成
                String RT_0302 = outinDailyDAO.queryOutinDailyCardPay(dto);
                resultMap.put("RT_0302", RT_0302);
                // *******************************门诊日结 - 一卡通收入*******************************

                // 门诊收费 - 费用构成
                MZ_SF03_LIST = outinDailyDAO.queryOutinDailyPay(dto);
                StringBuffer RT_0103 = new StringBuffer();
                MZ_SF03_LIST.forEach(m -> {
                    RT_0103.append(m.get("name")).append("：").append(m.get("total_price")).append("元，");
                });
                RT_0103.append("医保：" + (odMzSF != null ? odMzSF.getInsureTotalPrice() : "0.00") + "元(");
                // 查询医保支付信息
                Map<String, Object> insureMap = outinDailyDAO.getOutinDailyInsure(dto);
                RT_0103.append("统筹基金：" + (insureMap != null ? MapUtils.get(insureMap, "total_price") != null ? MapUtils.get(insureMap, "total_price") : "0.00" : "0.00") + "元，");
                RT_0103.append("个人账户：" + (insureMap != null ? MapUtils.get(insureMap, "personal_price") != null? MapUtils.get(insureMap, "personal_price") : "0.00" : "0.00") + "元),");
                // 2021年8月23日13:59:04 查询结算表一卡通支付金额
                Map<String, Object> cardTotalPriceMap = outinDailyDAO.getOutptSettleCardTotalPrice(dto);
                RT_0103.append("一卡通：" + (cardTotalPriceMap != null ? MapUtils.get(cardTotalPriceMap, "cardTotalPrice") : "0.00") + "元");

                resultMap.put("RT_0103", RT_0103);

                // 门诊发票
                List<Map<String, Object>> MZ_05_MZSF = MZ_05_MAP_LIST.get(Constants.JKLX.MZSF);
                if (ListUtils.isEmpty(MZ_05_MZSF)) {
                    resultMap.put("RT_040205", "门诊收费发票：(    -    )0张");
                } else {
                    StringBuffer RT_040205 = new StringBuffer("门诊收费发票：");
                    MZ_05_MZSF.forEach(m -> {
                        RT_040205.append("(  ").append(m.get("start_no")).append("  -  ").append(m.get("end_no")).append("  ) 共使用").append(m.get("use_num")).append("张,其中(正常").append(m.get("num")).append("张，作废").append(m.get("zf_num")).append("张，退费").append(m.get("tf_num")).append("张)");
                        RT_040205.append("，");
                    });
                    RT_040205.deleteCharAt(RT_040205.length() - 1);
                    resultMap.put("RT_040205", RT_040205);
                }
            }
            // 结算情况
            // 门诊挂号实收
            BigDecimal mzGhSS = odMzGh != null ? odMzGh.getRealityTotalPrice() : new BigDecimal(0);
            BigDecimal mzGhGz = odMzGh != null ? odMzGh.getCreditTotalPrice() != null ? odMzGh.getCreditTotalPrice() : new BigDecimal(0) : new BigDecimal(0);
            // 门诊收费实收
            BigDecimal mzSfSS = odMzSF != null ? odMzSF.getRealityTotalPrice() : new BigDecimal(0);
            // 医保金额
            BigDecimal mzYb = odMzSF != null ? odMzSF.getInsureTotalPrice() : new BigDecimal(0);
            // 门诊结算挂账金额  挂账金额小于0时取0
            BigDecimal mzGz = odMzSF != null ? odMzSF.getCreditTotalPrice() != null ? odMzSF.getCreditTotalPrice() : new BigDecimal(0) : new BigDecimal(0);
            // 一卡通实收
            BigDecimal yktczTotalPrice = odMzSF != null ? odMzSF.getYktczTotalPrice() != null ? odMzSF.getYktczTotalPrice() : new BigDecimal(0) : new BigDecimal(0);
            BigDecimal ykttkTotalPrice = odMzSF != null ? odMzSF.getYkttkTotalPrice() != null ? odMzSF.getYkttkTotalPrice() : new BigDecimal(0) : new BigDecimal(0);
            BigDecimal mzYKTSS = BigDecimalUtils.subtract(yktczTotalPrice, ykttkTotalPrice);
            // 实缴金额
            BigDecimal RT_040201 = mzGhSS.add(mzSfSS).add(mzYKTSS).subtract(mzYb).subtract(mzGz).subtract(mzGhGz);
            StringBuffer RT_0401 = new StringBuffer();
            // 门诊、挂号合并
            if ("0".equals(value)) {
                RT_0401.append("门诊挂号实收：").append(mzGhSS).append("元");
                RT_0401.append(" + ");
                RT_0401.append("门诊收费实收：").append(mzSfSS).append("元");
                RT_0401.append(" + ");
                RT_0401.append("门诊一卡通实收：").append(mzYKTSS).append("元");
                RT_0401.append(" - ");
                RT_0401.append("医保金额：").append(mzYb).append("元");
                RT_0401.append(" - ");
                RT_0401.append("门诊结算挂账金额：").append(mzGz).append("元");
                RT_0401.append(" - ");
                RT_0401.append("门诊挂号挂账金额：").append(mzGhGz).append("元");
            }
            // 门诊、挂号分开
            else {
                // 挂号
                if (Constants.JKLX.MZGH.equals(dto.getTypeCode())) {
                    RT_0401.append("门诊挂号实收：").append(mzGhSS).append("元");
                    RT_0401.append(" - ");
                    RT_0401.append("门诊挂号挂账金额：").append(mzGhGz).append("元");
                }
                // 门诊
                else {
                    RT_0401.append("门诊收费实收：").append(mzSfSS).append("元");
                    RT_0401.append(" + ");
                    RT_0401.append("门诊一卡通实收：").append(mzYKTSS).append("元");
                    RT_0401.append(" - ");
                    RT_0401.append("医保金额：").append(mzYb).append("元");
                    RT_0401.append(" - ");
                    RT_0401.append("门诊结算挂账金额：").append(mzGz).append("元");
                }
            }
            RT_0401.append(" = ");
            RT_0401.append("实缴金额：").append(RT_040201).append("元");
            resultMap.put("RT_0401", RT_0401);

            // 金额小写转大写
            resultMap.put("RT_040201", MoneyUtils.convert(RT_040201.doubleValue()));
            resultMap.put("RT_040202", RT_040201);

            // 支付方式
            List<Map<String, Object>> MZ_01_LIST = new ArrayList<>();
            if (!ListUtils.isEmpty(MZ_GH03_LIST)) {
                MZ_01_LIST.addAll(MZ_GH03_LIST);
            }
            if (!ListUtils.isEmpty(MZ_SF03_LIST)) {
                MZ_01_LIST.addAll(MZ_SF03_LIST);
            }
            // 查询一卡通充值退费支付方式
            MZ_YKT_LIST = outinDailyDAO.queryOutinDailyCardPays(dto);
            if (!ListUtils.isEmpty(MZ_YKT_LIST)) {
                MZ_01_LIST.addAll(MZ_YKT_LIST);
            }
            if (!ListUtils.isEmpty(MZ_01_LIST)) {
                Map<String, List<Map<String, Object>>> mapList = MZ_01_LIST.stream().collect(Collectors.groupingBy(e -> MapUtils.get(e, "name"), LinkedHashMap::new, Collectors.toList()));

                MZ_01_LIST.clear();
                mapList.forEach((key, list) -> {
                    BigDecimal totalPrice = list.stream().map(m -> (BigDecimal)m.get("total_price")).reduce(BigDecimal.ZERO, BigDecimal::add);
                    MZ_01_LIST.add(new HashMap(){{
                        put("name", key);
                        put("total_price", totalPrice);
                    }});
                });
                StringBuffer RT_040203 = new StringBuffer();
                MZ_01_LIST.forEach(m -> {
                    if (RT_040203.length() != 0) {
                        RT_040203.append("，");
                    }
                    RT_040203.append(m.get("name")).append("：").append(m.get("total_price")).append("元");
                });
                resultMap.put("RT_040203", RT_040203);
            }
        }
        // 住院缴款
        else {
            // *******************************住院日结 - 住院收入*******************************
            // 住院收入 -->> 财务分类
            List<Map<String, Object>> RT_0101 = outinDailyDAO.queryDailyByDailyNo(dto);
            // 过滤掉挂号收入
            RT_0101 = RT_0101.stream().filter(map -> !"A".equals(MapUtils.get(map, "code"))).collect(Collectors.toList());
            resultMap.put("RT_0101", RT_0101);

            // 住院日结缴款主表
            OutinDailyDO TMP_0102 = outinDailyDAO.getOutinDaily(dto);
            // 住院收入 -->> 收费合计、退费合计、舍入合计、实收合计
            Map<String, Object> RT_0102 = new HashMap<>();
            // 收费合计
            RT_0102.put("RT_010201", BigDecimalUtils.nullToZero(TMP_0102.getTotalPrice()));
            // 退费合计
            RT_0102.put("RT_010202", BigDecimalUtils.nullToZero(TMP_0102.getBackTotalPrice()));
            // 舍入合计
            RT_0102.put("RT_010203", BigDecimalUtils.nullToZero(TMP_0102.getRoundTotalPrice()));
            // 实收合计
            RT_0102.put("RT_010204",  BigDecimalUtils.nullToZero(TMP_0102.getRealityTotalPrice()));
            // 优惠合计
            RT_0102.put("RT_010205", BigDecimalUtils.nullToZero(TMP_0102.getPreferentialTotalPrice()));
            resultMap.put("RT_0102", RT_0102);

            // 住院收入 -->> 费用包含
            List<Map<String, Object>> TMP_0103 = outinDailyDAO.queryOutinDailyPay(dto);
            StringBuffer RT_0103 = new StringBuffer();
            TMP_0103.forEach(m -> {
//                if (RT_0103.length() != 0) {
//                    RT_0103.append("，");
//                }
                RT_0103.append(m.get("name")).append("：").append(m.get("total_price")).append("元,");
            });
            // 医保
            RT_0103.append("医保：" + BigDecimalUtils.nullToZero(TMP_0102.getInsureTotalPrice()) + "元(");
            // 查询医保支付信息
            Map<String, Object> insureMap = outinDailyDAO.getOutinDailyInsure(dto);
            RT_0103.append("统筹基金：" + (insureMap != null ? MapUtils.get(insureMap, "total_price") != null ? MapUtils.get(insureMap, "total_price") : "0.00" : "0.00") + "元，");
            RT_0103.append("个人账户：" + (insureMap != null ? MapUtils.get(insureMap, "personal_price") != null? MapUtils.get(insureMap, "personal_price") : "0.00" : "0.00") + "元),");
            // 预交金冲抵
            RT_0103.append("预交金结算：" + BigDecimalUtils.nullToZero(TMP_0102.getYjjsTotalPrice()) + "元");
            resultMap.put("RT_0103", RT_0103);

            // *******************************住院日结 - 预交金收入*******************************
            // 预交金收入 -->> 预交金合计、预交金退款、预交金实收
            // 预交金合计
            resultMap.put("RT_020101", TMP_0102.getYjssTotalPrice().add(TMP_0102.getYjtkTotalPrice()));
            resultMap.put("RT_020102", TMP_0102.getYjtkTotalPrice());
            resultMap.put("RT_020103", TMP_0102.getYjssTotalPrice());

            // 预交金收入 -->> 费用包含
            String RT_0202 = outinDailyDAO.queryOutinDailyAdvancePay(dto);
            resultMap.put("RT_0202", RT_0202);

            // *******************************住院日结 - 预交金结算*******************************
            // 预交金结算 -->> 预交金结算、预交金冲抵、 预交金补退
            resultMap.put("RT_0301", TMP_0102.getYjjsTotalPrice());
            resultMap.put("RT_0302", TMP_0102.getYjcdTotalPrice());
            resultMap.put("RT_0303", TMP_0102.getYjjsTotalPrice().subtract(TMP_0102.getYjcdTotalPrice()));

            // *******************************住院日结 - 结算详情*******************************
            // 实缴金额
            BigDecimal TMP_0401 = BigDecimalUtils.nullToZero(TMP_0102.getRealityTotalPrice()).add(TMP_0102.getYjssTotalPrice()).subtract(BigDecimalUtils.nullToZero(TMP_0102.getInsureTotalPrice())).subtract(TMP_0102.getYjjsTotalPrice()).subtract(TMP_0102.getCreditTotalPrice());
            StringBuffer RT_0401 = new StringBuffer();
            RT_0401.append("(住院收入实收：").append(BigDecimalUtils.nullToZero(TMP_0102.getRealityTotalPrice())).append("元");
            RT_0401.append(" + ");
            RT_0401.append("预交金实收：").append(BigDecimalUtils.nullToZero(TMP_0102.getYjssTotalPrice())).append("元)");
            RT_0401.append(" - ");
            RT_0401.append("医保报销金额：").append(BigDecimalUtils.nullToZero(TMP_0102.getInsureTotalPrice())).append("元");
            RT_0401.append(" - ");
            RT_0401.append("预交结算金额：").append(TMP_0102.getYjjsTotalPrice()).append("元");
            RT_0401.append(" - ");
            RT_0401.append("挂账金额：").append(TMP_0102.getCreditTotalPrice()).append("元");
            RT_0401.append(" = ");
            RT_0401.append("实缴金额：").append(TMP_0401).append("元");
            resultMap.put("RT_0401", RT_0401);
            // 本次上交 (大写）
            String RT_0402 = MoneyUtils.convert(TMP_0401.abs().doubleValue());
            resultMap.put("RT_0402", (TMP_0401.signum() == -1 ? "-" : "") + RT_0402);
            // 本次上交（小写）
            resultMap.put("RT_0403", TMP_0401);

            // 支付方式
            StringBuffer RT_0404 = new StringBuffer();
            List<Map<String, Object>> TMP_0404 = outinDailyDAO.queryOutinDailyPayAndAdvancePay(dto);
            TMP_0404.forEach(m -> {
                if (RT_0404.length() != 0) {
                    RT_0404.append("，");
                }
                RT_0404.append(m.get("name")).append("：").append(m.get("total_price")).append("元");
            });
//            // 预交金结算
//            RT_0404.append("，").append("预交金结算：" + TMP_0102.getYjjsTotalPrice() + "元");
//            // 预交金补退
//            RT_0404.append("，").append("预交金补退：" + TMP_0102.getYjjsTotalPrice().subtract(TMP_0102.getYjcdTotalPrice()) + "元");
            resultMap.put("RT_0404", RT_0404);

            // 发票
            List<Map<String, Object>> TMP_0405 = outinDailyDAO.queryOutinDailyInvoiceByDailyNo(dto);
            if (ListUtils.isEmpty(TMP_0405)) {
                resultMap.put("RT_0405", "住院收费发票：(    -    )0张");
            } else {
                StringBuffer RT_0405 = new StringBuffer("住院收费发票：");
                TMP_0405.forEach(m -> {
                    RT_0405.append("(  ").append(m.get("start_no")).append("  -  ").append(m.get("end_no")).append("  ) 共使用").append(m.get("use_num")).append("张,其中(正常").append(m.get("num")).append("张，作废").append(m.get("zf_num")).append("张，退费").append(m.get("tf_num")).append("张)");
                    RT_0405.append("，");
                });
                RT_0405.deleteCharAt(RT_0405.length() - 1);
                resultMap.put("RT_0405", RT_0405);
            }
        }

        return resultMap;
    }

    /**
     * @Method 日结缴款 - 缴款报表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/11/2 14:57
     * @Return
     **/
    @Override
    public PageDTO querySettle(OutinDailyDTO outinDailyDTO) {
        // 门诊挂号、门诊收费
        if (Constants.JKLX.MZGH.equals(outinDailyDTO.getTypeCode()) || Constants.JKLX.MZSF.equals(outinDailyDTO.getTypeCode())) {
            outinDailyDTO.setRjjkfs(getSysParameter(outinDailyDTO.getHospCode(), "RJJKFS"));
        }

        // 设置分页信息
        PageHelper.startPage(outinDailyDTO.getPageNo(), outinDailyDTO.getPageSize());
        // 查询所有
        List<Map<String, Object>> settleList = outinDailyDAO.querySettle(outinDailyDTO);
        // 返回分页结果
        return PageDTO.of(settleList);
    }

    /**
     * @Method 日结缴款 - 预交金明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/11/2 15:00
     * @Return
     **/
    @Override
    public PageDTO queryAdvancePay(OutinDailyDTO outinDailyDTO) {
        // 设置分页信息
        PageHelper.startPage(outinDailyDTO.getPageNo(), outinDailyDTO.getPageSize());
        // 查询所有
        List<Map<String, Object>> apList = outinDailyDAO.queryAdvancePay(outinDailyDTO);
        // 返回分页结果
        return PageDTO.of(apList);
    }

    /**
     * @Method 日结缴款 - 预交金冲抵列表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/11/2 15:07
     * @Return
     **/
    @Override
    public PageDTO queryAdvancePayCd(OutinDailyDTO outinDailyDTO) {
        // 设置分页信息
        PageHelper.startPage(outinDailyDTO.getPageNo(), outinDailyDTO.getPageSize());
        // 查询所有
        List<Map<String, Object>> apcdList = outinDailyDAO.queryAdvancePayCd(outinDailyDTO);
        // 返回分页结果
        return PageDTO.of(apcdList);
    }

    @Override
    public PageDTO queryCardConsumePay(OutinDailyDTO outinDailyDTO) {
        // 设置分页信息
        PageHelper.startPage(outinDailyDTO.getPageNo(), outinDailyDTO.getPageSize());
        // 查询所有
        List<Map<String, Object>> apcdList = outinDailyDAO.queryCardConsumePay(outinDailyDTO);
        // 返回分页结果
        return PageDTO.of(apcdList);
    }

    @Override
    public PageDTO queryCardCzOrTkPay(OutinDailyDTO outinDailyDTO) {
        // 设置分页信息
        PageHelper.startPage(outinDailyDTO.getPageNo(), outinDailyDTO.getPageSize());
        // 查询所有
        List<Map<String, Object>> apcdList = outinDailyDAO.queryBaseCardCZAndTK(outinDailyDTO);
        // 返回分页结果
        return PageDTO.of(apcdList);
    }
}
