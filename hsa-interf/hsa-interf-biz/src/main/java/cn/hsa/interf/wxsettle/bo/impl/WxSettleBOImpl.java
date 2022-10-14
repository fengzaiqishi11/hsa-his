package cn.hsa.interf.wxsettle.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.interf.wxBasicInfo.dao.WxOutptDAO;
import cn.hsa.module.interf.wxsettle.bo.WxSettleBO;
import cn.hsa.module.interf.wxsettle.dao.WxSettleDAO;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.module.outpt.fees.dao.OutptSettleDAO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDetailDO;
import cn.hsa.module.phar.pharoutreceive.service.PharOutReceiveDetailService;
import cn.hsa.module.phar.pharoutreceive.service.PharOutReceiveService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.entity.SysParameterDO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.interf.wxsettle.bo.impl
 * @Class_name: WxSettleBOImpl
 * @Describe: 微信小程序门诊收费接口
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/6/28 19:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class WxSettleBOImpl extends HsafBO implements WxSettleBO {

	@Resource
	private WxSettleDAO wxSettleDAO;

	@Resource
	private RedisUtils redisUtils;

	@Resource
	private BaseOrderRuleService baseOrderRuleService_consumer;

	@Resource
	private SysParameterService sysParameterService_consumer;

	@Resource
	private PharOutReceiveService pharOutReceiveService_consumer;

	@Resource
	private PharOutReceiveDetailService pharOutReceiveDetailService_consumer;

	@Resource
	private WxOutptDAO wxOutptDAO;


	/**
	 * @Description: 微信小程序门诊收费 试算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/29 9:33
	 * @Return
	 */
	@Override
	public WrapperResponse saveTestSettle(Map<String, Object> map) {
		String hospCode = MapUtils.get(map, "hospCode");   // 医院编码
		Map<String, Object> tempMap = MapUtils.get(map, "data");
		String visitId = MapUtils.get(tempMap, "visitId");     // 患者就诊id
		String sourcePayCode = MapUtils.get(tempMap, "sourcePayCode");  // 支付来源
		String outTradeNo = MapUtils.get(tempMap, "outTradeNo");    // 支付单号
		String crteId = MapUtils.get(tempMap, "crteId");         //创建人id
		String crteName = MapUtils.get(tempMap, "crteName");       //创建人姓名

		//生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
		String key = new StringBuilder(hospCode).append(visitId).append(Constants.OUTPT_FEES_REDIS_KEY).toString();
		if (StringUtils.isNotEmpty(redisUtils.get(key))) {
			return WrapperResponse.fail("划价收费提示：该患者正在别处结算；请稍后再试", null);
		}
		Map<String, Object> result = new HashMap<String, Object>();//返回结果
		try {
			// 1.使用redis锁病人，并设置自动过期时间600秒，防止异常情况没有结算成功且redis不会自动清除的问题
			redisUtils.set(key, visitId, 300);

			// 2.删除试算脏数据
			Map<String, String> settleParam = new HashMap<String, String>();
			settleParam.put("hospCode", hospCode); //医院编码
			settleParam.put("visitId", visitId);//就诊id
			settleParam.put("isSettle", Constants.SF.F);//是否结算 = 否
			settleParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
			wxSettleDAO.delOutptSettleByParam(settleParam);  // 删除上一次试算结果（未结算的结算表数据）

			// 3.根据就诊id查询病人未结算的费用集合
			// 3.1查询处方费用
			Map<String, Object> opParam = new HashMap<String, Object>();
			opParam.put("hospCode", hospCode);
			String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
			opParam.put("settleCodes", settleCodes);
			opParam.put("statusCode", Constants.ZTBZ.ZC);
			opParam.put("visitId", visitId);
			List<OutptCostDTO> opCostList = wxSettleDAO.queryOutptCostSourceidNotNUll(opParam);
			// 3.2查询其他费用
			List<OutptCostDTO> restCostList = wxSettleDAO.queryOutptCostRest(opParam);
			if (restCostList != null && !restCostList.isEmpty()) {
				opCostList.addAll(restCostList);
			}

			// 4.校验药品库存信息，都有库存才能成功试算
			this.checkKC(opCostList);

			// 5.生成结算id
			String settleId = SnowflakeUtils.getId();//结算id
			// 5.1 取第一次结算的结算id
			String oneSettleId = settleId;
			if (!opCostList.isEmpty() && opCostList.get(0).getOneSettleId() != null && !"".equals(opCostList.get(0).getOneSettleId())) {
				oneSettleId = opCostList.get(0).getOneSettleId();
			}

            // 5.2 处理病人费用信息，处方病人：更新费用状态；更新费用结算id
            this.saveOutptCost(opCostList, hospCode, settleId);

            // 5.2 计算总费用
			BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
			BigDecimal totalPrice = new BigDecimal(0);//项目总费用
			for (OutptCostDTO outptCostDTO : opCostList) {
				// i + 优惠后金额 = 优惠后总费用
				realityPrice = BigDecimalUtils.add(realityPrice, outptCostDTO.getRealityPrice());
				// i + 单价 = 项目总费用
				totalPrice = BigDecimalUtils.add(totalPrice, outptCostDTO.getTotalPrice());
			}

			// 6、生成结算数据，保存门诊结算表（试算状态）
			// 6.1 查询患者信息
			Map<String, String> visitMap = new HashMap<>();
			visitMap.put("id", visitId);
			visitMap.put("hospCode", hospCode);
			OutptVisitDTO outptVisitDTO = wxSettleDAO.queryByVisitID(visitMap);
			// 6.2 准备结算信息保存数据
			Map<String, Object> settleMap = new HashMap<>();
			settleMap.put("hospCode", hospCode);
			settleMap.put("settleId", settleId);
			settleMap.put("visitId", visitId);
			settleMap.put("realityPrice", realityPrice);
			settleMap.put("brlx", outptVisitDTO.getPatientCode());
			settleMap.put("totalPrice", totalPrice);
			settleMap.put("sourcePayCode", sourcePayCode);
			settleMap.put("outTradeNo", outTradeNo);
			settleMap.put("crteId", crteId);
			settleMap.put("crteName", crteName);
			settleMap.put("oneSettleId", oneSettleId);
			OutptSettleDO outptSettleDO = this.saveOutptSettle(settleMap);

			// 7、将试算结果返回给前端
			result.put("profileId", outptVisitDTO.getProfileId());//档案id
			result.put("settleNo", outptSettleDO.getSettleNo());//结算单号
			result.put("actualPrice", outptSettleDO.getSelfPrice().toString());//支付金额
			result.put("totalPrice", outptSettleDO.getTotalPrice().toString());//总金额
			result.put("settleId", outptSettleDO.getId());//结算id
			result.put("visitId", visitId); // 就诊id
		} catch (Exception e) {
			throw e;
		} finally {
			redisUtils.del(key);//删除结算key
		}
		// 试算结果加密返回给微信小程序
		log.debug("微信小程序【门诊收费 试算】返参加密前：" + result.toString());
		String res = null;
		try {
			res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
			log.debug("微信小程序【门诊收费 试算】返参加密后：" + res);
		} catch (UnsupportedEncodingException e) {
			throw new AppException("【门诊收费 试算】返参加密错误，请联系管理员！" + e.getMessage());
		}
		return WrapperResponse.success("成功。", res);
	}

	/**
	 * @Description: （试算）处理病人费用信息，处方病人：更新费用状态； 直接划价收费病人：删除原费用信息，新增当前费用信息
	 * @Param: settleId: 结算id; id:门诊就诊id; isDel: 是否删除费用信息
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/2 11:00
	 * @Return
	 */
	private void saveOutptCost(List<OutptCostDTO> outptCostDTOList, String hospCode, String settleId) {
		List<OutptCostDTO> prescribeList = new ArrayList<OutptCostDTO>();//处方费用
		List<OutptCostDTO> costList = new ArrayList<OutptCostDTO>(); //划价费用
		outptCostDTOList.stream().forEach(outptCostDTO -> {
			outptCostDTO.setHospCode(hospCode);//医院编码
			outptCostDTO.setSettleCode(Constants.JSZT.YUJS);//结算状态代码；设置预结算状态
			outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
			outptCostDTO.setSettleId(settleId);//结算id
			if (outptCostDTO.getOneSettleId() == null || "".equals(outptCostDTO.getOneSettleId())) {
				outptCostDTO.setOneSettleId(settleId);
			}

			//处方费用信息
			prescribeList.add(outptCostDTO);

			//药品材料检查库存
			if (Constants.XMLB.YP.equals(outptCostDTO.getItemCode()) || Constants.XMLB.CL.equals(outptCostDTO.getItemCode())) {
				// 获取页面上修改后的用药性质
				boolean isChange = false;

				if (Constants.YYXZ.CG.equals(outptCostDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(outptCostDTO.getUseCode())) {
					isChange = true;
				}

				//常规和出院带药
				if (isChange) {
					//校验库存
					Map<String, String> mapParameter = this.getParameterValue(outptCostDTO.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
					String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
					OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
					outptPrescribeDetailsDTO.setHospCode(outptCostDTO.getHospCode());
					outptPrescribeDetailsDTO.setItemId(outptCostDTO.getItemId());
					outptPrescribeDetailsDTO.setPharId(outptCostDTO.getPharId());
					outptPrescribeDetailsDTO.setTotalNum(outptCostDTO.getTotalNum());
					outptPrescribeDetailsDTO.setNumUnitCode(outptCostDTO.getNumUnitCode());
					outptPrescribeDetailsDTO.setWjsykc(wjsykc);
					outptPrescribeDetailsDTO.setCostId(outptCostDTO.getId());

					List<Map<String, Object>> prescribeMap = wxSettleDAO.checkStock(outptPrescribeDetailsDTO);
					//判断库存
					if (ListUtils.isEmpty(prescribeMap)) {
						throw new AppException(outptCostDTO.getItemName() + ":未指定药房或指定药房库存不足");
					}
				}
			}
		});

		//修改处方费用信息（根据优惠类型可能优惠金额发生改变；仅修改：项目总金额、项目优惠金额、项目优惠后总金额、结算状态、状态标志）
		if (!prescribeList.isEmpty()) {
			wxSettleDAO.batchEditCostPrice(prescribeList);
		}
	}


    /**
	 * @Description: 校验费用库存
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/29 14:30
	 * @Return
	 */
	private boolean checkKC (List<OutptCostDTO> opCostList) {
		for (OutptCostDTO dto : opCostList) {
			//药品材料检查库存
			if (Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode())) {
				// 获取页面上修改后的用药性质
				boolean isChange = false;
				if (Constants.FYLYFS.ZJHJSF.equals(dto.getSourceCode())) {
					if (Constants.YYXZ.CG.equals(dto.getUsageCode()) || Constants.YYXZ.CYDY.equals(dto.getUsageCode())) {
						isChange = true;
					}
				} else {
					if (Constants.YYXZ.CG.equals(dto.getUseCode()) || Constants.YYXZ.CYDY.equals(dto.getUseCode())) {
						isChange = true;
					}
				}
				//常规和出院带药
				if (isChange) {
					//校验库存
					Map<String, String> mapParameter = this.getParameterValue(dto.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
					String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
					OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
					outptPrescribeDetailsDTO.setHospCode(dto.getHospCode());
					outptPrescribeDetailsDTO.setItemId(dto.getItemId());
					outptPrescribeDetailsDTO.setPharId(dto.getPharId());
					outptPrescribeDetailsDTO.setTotalNum(dto.getTotalNum());
					outptPrescribeDetailsDTO.setNumUnitCode(dto.getNumUnitCode());
					outptPrescribeDetailsDTO.setWjsykc(wjsykc);
					outptPrescribeDetailsDTO.setCostId(dto.getId());

					List<Map<String, Object>> prescribeMap = wxSettleDAO.checkStock(outptPrescribeDetailsDTO);
					//判断库存
					if (ListUtils.isEmpty(prescribeMap)) {
						throw new AppException(dto.getItemName() + ":未指定药房或指定药房库存不足");
					}
				}
			}
		}
		return true;
	}

	/**
	 * 获取系统参数
	 * @param hospCode
	 * @param code
	 * @return
	 */
	public Map<String, String> getParameterValue(String hospCode, String[] code) {
		List<SysParameterDTO> list = wxSettleDAO.getParameterValue(hospCode, code);
		Map<String, String> retMap = new HashMap<>();
		if (!MapUtils.isEmpty(list)) {
			for (SysParameterDTO hit : list) {
				retMap.put(hit.getCode(), hit.getValue());
			}
		}
		return retMap;
	}

	/**
	 * @Description: (试算) 保存门诊结算数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/5 10:02
	 * @Return
	 */
	private OutptSettleDO saveOutptSettle(Map<String, Object> map) {
		SysParameterDO sysParameterDO = getSysParameter(MapUtils.get(map, "hospCode"), Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
		BigDecimal roundingCost = BigDecimalUtils.rounding(sysParameterDO.getValue(), MapUtils.get(map, "realityPrice")); //舍入费用
		// 生成结算数据，保存门诊结算表
		OutptSettleDO outptSettleDO = new OutptSettleDO();
		outptSettleDO.setId(MapUtils.get(map, "settleId"));//id
		outptSettleDO.setHospCode(MapUtils.get(map, "hospCode"));//医院编码
		outptSettleDO.setVisitId(MapUtils.get(map, "visitId"));//就诊id
		outptSettleDO.setSettleNo(getOrderNo(MapUtils.get(map, "hospCode"), Constants.ORDERRULE.JZ));//结算单号
		outptSettleDO.setPatientCode(MapUtils.get(map, "brlx"));//病人类型
		outptSettleDO.setSettleTime(new Date());//结算时间
		outptSettleDO.setTotalPrice(MapUtils.get(map, "totalPrice"));//总金额
		outptSettleDO.setRealityPrice(MapUtils.get(map, "realityPrice"));//优惠后总金额
		outptSettleDO.setTruncPrice(roundingCost);//舍入金额（存在正负金额）
		outptSettleDO.setActualPrice(null);//实收金额
		outptSettleDO.setSelfPrice(BigDecimalUtils.subtract(MapUtils.get(map, "realityPrice"), roundingCost));// 个人自付金额减去舍人金额
		outptSettleDO.setMiPrice(new BigDecimal(0));//统筹支付金额
		outptSettleDO.setIsSettle(Constants.SF.F);//是否结算（SF）
		outptSettleDO.setDailySettleId(null);//日结缴款ID
		outptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）;正常
		outptSettleDO.setRedId(null);//冲红ID
		outptSettleDO.setIsPrint(Constants.SF.F);//是否打印（SF）;否
		outptSettleDO.setOldSettleId(null);//原结算ID
		outptSettleDO.setIsPrintList(Constants.SF.F);//是否打印清单（SF）
		outptSettleDO.setPrintListTime(null);//清单打印时间
		outptSettleDO.setSourcePayCode(MapUtils.get(map, "sourcePayCode"));//支付来源代码（ZFLY，第三方对接）
		outptSettleDO.setOrderNo(MapUtils.get(map, "outTradeNo"));//支付订单号（第三方订单号）
		outptSettleDO.setCrteId(MapUtils.get(map, "crteId"));//创建人id
		outptSettleDO.setCrteName(MapUtils.get(map, "crteName"));//创建人名称
		outptSettleDO.setCrteTime(new Date());//创建时间
		outptSettleDO.setOneSettleId(MapUtils.get(map, "oneSettleId")); // 记录下第一次结算id
		// 保存门诊结算（试算）费用信息
		wxSettleDAO.insertSelective(outptSettleDO);
		// 将费用按照医院配置的舍入规则舍入总金额与合计金额
		outptSettleDO.setTotalPrice(BigDecimalUtils.subtract(outptSettleDO.getTotalPrice(), roundingCost));//总金额
		outptSettleDO.setRealityPrice(BigDecimalUtils.subtract(outptSettleDO.getRealityPrice(), roundingCost));//优惠后总金额
		return outptSettleDO;
	}

	/**
	 * @Menthod getOrderNo
	 * @Desrciption  生成规则单据号
	 * @param hospCode 医院编码
	 * @param typeCode 规则标志Code
	 * @Author Ou·Mr
	 * @Date 2020/8/28 17:26
	 * @Return java.lang.String 单据号
	 */
	private String getOrderNo(String hospCode, String typeCode) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("hospCode", hospCode);
		param.put("typeCode", typeCode);
		WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
		return orderNo.getData();
	}

	/**
	 * @Menthod getSysParameter
	 * @Desrciption  查询sys_parameter配置表
	 * @param hospCode 医院编码
	 * @param code 参数代码
	 * @Author Ou·Mr
	 * @Date 2020/8/28 15:22
	 * @Return cn.hsa.module.sys.parameter.entity.SysParameterDO
	 */
	private SysParameterDO getSysParameter(String hospCode, String code) {
		SysParameterDTO sysParameterDTO = new SysParameterDTO();//查询条件
		sysParameterDTO.setHospCode(hospCode);//医院编码
		sysParameterDTO.setIsValid(Constants.SF.S);//是否有效；设置：是
		sysParameterDTO.setCode(code);//字典code
		Map param = new HashMap();
		param.put("hospCode", hospCode);
		param.put("sysParameterDTO", sysParameterDTO);
		List<SysParameterDTO> sysParameterDTOS = sysParameterService_consumer.queryAll(param).getData();
		if (sysParameterDTOS == null || sysParameterDTOS.isEmpty()) {
			return new SysParameterDO();
		}
		return sysParameterDTOS.get(0);
	}


	/**
	 * @Description: 微信小程序门诊收费 结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/29 10:27
	 * @Return
	 */
	@Override
	public WrapperResponse saveSettle(Map<String, Object> map) {
		String hospCode = MapUtils.get(map, "hospCode"); //医院编码

		Map<String, Object> tempMapData = MapUtils.get(map, "data");
		String visitId = MapUtils.get(tempMapData, "visitId");     // 患者就诊id
		String sourcePayCode = MapUtils.get(tempMapData, "sourcePayCode");  // 支付来源
		String actualPrice = MapUtils.get(tempMapData, "actualPrice"); // 支付金额
		if (StringUtils.isEmpty(actualPrice)) {
			return WrapperResponse.fail("划价收费提示：支付金额为空。", null);
		}

		BigDecimal paySettleMoney = new BigDecimal(actualPrice);  // 支付金额
		String orderNo = MapUtils.get(tempMapData, "orderNo"); // 支付票据号（微信条码号、支付宝条码号、支票号码）
		String crteId = MapUtils.get(tempMapData, "crteId");         //创建人id
		String crteName = MapUtils.get(tempMapData, "crteName");       //创建人姓名
		String settleId =  MapUtils.get(tempMapData, "settleId");//结算id
		String deptId = "1000000000000000001"; // 默认微信，支付宝小程序的申请科室id

		//生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
		String key = new StringBuilder(hospCode).append(Constants.OUTPT_FEES_REDIS_KEY).toString();
		//校验该用户是否在结算
		if (StringUtils.isNotEmpty(redisUtils.get(key))) {
			return WrapperResponse.fail("划价收费提示：该患者正在别处结算；请稍后再试。", null);
		}
		String settleNo = "";
		try {
			redisUtils.set(key, key, 600);

			// 1、 校验费用信息是否正确（根据当前结算id，查询费用表）
			Map<String, Object> param = new HashMap<String, Object>();
			//hospCode（医院编码）、statusCode（状态标志）、settleCode（结算状态）、settleId（结算id）
			param.put("hospCode", hospCode);//hospCode（医院编码）
			param.put("statusCode", Constants.ZTBZ.ZC);//statusCode（状态标志 = 正常）
			param.put("settleCode", Constants.JSZT.YUJS);//settleCode（结算状态 = 预结算）
			param.put("settleId", settleId);//settleId（结算id）
			List<OutptCostDTO> outptCostDTOList = wxSettleDAO.queryBySettleId(param);

			if (outptCostDTOList == null && outptCostDTOList.isEmpty()) {
				return WrapperResponse.fail("支付失败，未找到本次结算费用信息，请确认是否已经结算或者刷新后重试。", null);
			}

			// 2 、根据费用项目id查询费用信息
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("hospCode", hospCode);//医院编码
			queryParam.put("pfTypeCode", "");//优惠类型
			queryParam.put("items", outptCostDTOList);//当前用户的费用信息
			List<OutptCostDTO> outptCostDTOS = wxSettleDAO.queryDrugMaterialListByIds(queryParam);

			//统计优惠总金额
			BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
			List<String> ids = new ArrayList<String>();//修改费用状态费用id
			List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = new ArrayList<PharOutReceiveDetailDO>();//领药申请明细参数接收
			Map<String, Map<String, Object>> pharOutReceiveMap = new HashMap<String, Map<String, Object>>();
			List<String> outptPrescribeIds = new ArrayList<String>();//存储处方id

			// 3、 校验药品、材料库存，并生成领药申请单明细
			Map<String, Object> tempMap = this.checkStockAndCreatePharOutReceiveDetail(outptCostDTOList, outptCostDTOS, hospCode);
			realityPrice = (BigDecimal) tempMap.get("realityPrice");
			ids = (List<String>) tempMap.get("ids");
			pharOutReceiveDetailDOList = (List<PharOutReceiveDetailDO>) tempMap.get("pharOutReceiveDetailDOList");
			pharOutReceiveMap = (Map<String, Map<String, Object>>) tempMap.get("pharOutReceiveMap");
			outptPrescribeIds = (List<String>) tempMap.get("outptPrescribeIds");

			OutptSettleDTO outptSettleDTO1 = wxSettleDAO.selectByPrimaryKey(settleId);//获取本次结算费用信息
			// 4、 校验个人支付金额，是否与本次结算的费用一致，不一致表示费用出现了问题
			if (!BigDecimalUtils.equals(realityPrice, outptSettleDTO1.getRealityPrice())) {
				return WrapperResponse.fail("支付失败，该患者费用信息错误，请刷新后重试。", null);
			}

			// 5、 保存结算信息（支付方式与各方式金额）
			boolean isChange = this.saveOutptPays(sourcePayCode, paySettleMoney, orderNo, hospCode, settleId, visitId, outptSettleDTO1);
			if (isChange) {
				return WrapperResponse.fail("支付失败；本次账户支付金额小于当前支付金额！", null);
			}
			// 6、 根据费用信息修改本次结算的费用状态
			Map<String, Object> costParam = new HashMap<String, Object>();
			costParam.put("settleCode", Constants.JSZT.YIJS);//费用状态 = 已结算
			costParam.put("ids", ids);//费用id
            if (!ListUtils.isEmpty(ids)) {
                wxSettleDAO.editCostSettleCodeByIDS(costParam);
            }

			// 7、 修改门诊结算表此次结算信息状态
			OutptSettleDO outptSettleDO = new OutptSettleDO();//修改参数
			outptSettleDO.setId(settleId);//结算id
			SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
			BigDecimal ssje = BigDecimalUtils.subtract(realityPrice, BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice));
			outptSettleDO.setActualPrice(ssje);//实收金额
			outptSettleDO.setIsSettle(Constants.SF.S);//是否结算 = 是
			outptSettleDO.setSourcePayCode(sourcePayCode);  // 0:HIS 1:微信  2：支付宝   3：自助机
			wxSettleDAO.updateByPrimaryKeySelective(outptSettleDO);//修改结算状态
			// 7.1 结算后需要将结算单号返回给前端
			Map<String, Object> settleMap = new HashMap<>();
			settleMap.put("id", settleId);
			settleMap.put("hospCode", hospCode);
			OutptSettleDTO dto = wxSettleDAO.getById(settleMap);
			settleNo = dto.getSettleNo();

			// 8、 修改处方表结算信息
			if (!outptPrescribeIds.isEmpty()) {
				Map<String, Object> outptPrescribeParam = new HashMap<String, Object>();
				outptPrescribeParam.put("hospCode", hospCode);//医院编码
				outptPrescribeParam.put("ids", outptPrescribeIds);//处方ids
				outptPrescribeParam.put("settleId", settleId);//结算id
				outptPrescribeParam.put("isSettle", Constants.SF.S);//是否结算 = 是
				wxSettleDAO.updateOutptPrescribeByIds(outptPrescribeParam);
			}

			// 10、 取最佳领药窗口，生成领药申请单（主单），保存领药申请单与领药申请单详单
			this.savePharOutReceive(hospCode, visitId, deptId, crteId, crteName, settleId,
					pharOutReceiveMap, outptCostDTOList, pharOutReceiveDetailDOList);

		} catch (Exception e) {
			throw e;
		} finally {
			redisUtils.del(key);//删除结算key
		}
		JSONObject result = new JSONObject();
		result.put("settleNo", settleNo);
		result.put("settleId", settleId);
		result.put("settleCode", "1");  // 1:已结算

		// 试算结果加密返回给微信小程序
		log.debug("微信小程序【门诊收费 结算】返参加密前：" + result.toString());
		String res = null;
		try {
			res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
			log.debug("微信小程序【门诊收费 结算】返参加密后：" + res);
		} catch (UnsupportedEncodingException e) {
			throw new AppException("【门诊收费 结算】返参加密错误，请联系管理员！" + e.getMessage());
		}
		return WrapperResponse.success("成功。", res);

	}

	/**
	 * @Description: 校验费用明细对应的药品或材料是否有库存，有库存的情况下生成领药申请详单
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/8 10:36
	 * @Return
	 */
	private Map<String, Object> checkStockAndCreatePharOutReceiveDetail(List<OutptCostDTO> outptCostDTOList, List<OutptCostDTO> outptCostDTOS, String hospCode) {
		List<String> outptPrescribeIds = new ArrayList<String>();//存储处方id
		Map<String, Map<String, Object>> pharOutReceiveMap = new HashMap<String, Map<String, Object>>();
		List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = new ArrayList<PharOutReceiveDetailDO>();//领药申请明细参数接收
		BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
		List<String> ids = new ArrayList<String>();//修改费用状态费用id

		for (OutptCostDTO outptCostDTO : outptCostDTOList) {
			//判断是否是处方费用，记录处方费用
			if (StringUtils.isNotEmpty(outptCostDTO.getOpId()) && !outptPrescribeIds.contains(outptCostDTO.getOpId())) {
				outptPrescribeIds.add(outptCostDTO.getOpId());
			}
			//药品材料检查库存
			if (Constants.XMLB.YP.equals(outptCostDTO.getItemCode()) || Constants.XMLB.CL.equals(outptCostDTO.getItemCode())) {
				//常规和出院带药
				if (Constants.YYXZ.CG.equals(outptCostDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(outptCostDTO.getUseCode())) {
					//校验库存数量
					Map<String, String> mapParameter = this.getParameterValue(outptCostDTO.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
					String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
					OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
					outptPrescribeDetailsDTO.setHospCode(outptCostDTO.getHospCode());
					outptPrescribeDetailsDTO.setItemId(outptCostDTO.getItemId());
					outptPrescribeDetailsDTO.setPharId(outptCostDTO.getPharId());
					outptPrescribeDetailsDTO.setTotalNum(outptCostDTO.getTotalNum());
					outptPrescribeDetailsDTO.setNumUnitCode(outptCostDTO.getNumUnitCode());
					outptPrescribeDetailsDTO.setWjsykc(wjsykc);
					outptPrescribeDetailsDTO.setCostId(outptCostDTO.getId());
					//判断库存
					if (ListUtils.isEmpty(wxSettleDAO.checkStock(outptPrescribeDetailsDTO))) {
						throw new AppException(outptCostDTO.getItemName() + ":未指定药房或指定药房库存不足");
					}

					//根据药房生成领药申请数据
					if (!pharOutReceiveMap.containsKey(outptCostDTO.getPharId())) {
						Map<String, Object> receiveMap = new HashMap<String, Object>();
						receiveMap.put("id", SnowflakeUtils.getId());//领药申请单id
						receiveMap.put("totalPrice", outptCostDTO.getRealityPrice());//总金额
						pharOutReceiveMap.put(outptCostDTO.getPharId(), receiveMap);
					} else {
						BigDecimal price = (BigDecimal) pharOutReceiveMap.get(outptCostDTO.getPharId()).get("totalPrice");
						pharOutReceiveMap.get(outptCostDTO.getPharId()).put("totalPrice", BigDecimalUtils.add(price, outptCostDTO.getRealityPrice()));
					}

					//生成领药申请单明细信息
					outptCostDTOS.stream().forEach(outptCostDTO1 -> {
						if (outptCostDTO1.getItemId().equals(outptCostDTO.getItemId())) {
							PharOutReceiveDetailDO pharOutReceiveDetailDO = new PharOutReceiveDetailDO();
							pharOutReceiveDetailDO.setId(SnowflakeUtils.getId());//id
							pharOutReceiveDetailDO.setHospCode(hospCode);//医院编码
							pharOutReceiveDetailDO.setOrId((String) pharOutReceiveMap.get(outptCostDTO.getPharId()).get("id"));//领药申请id
							pharOutReceiveDetailDO.setOpId(outptCostDTO.getOpId());//处方id
							pharOutReceiveDetailDO.setOpdId(outptCostDTO.getOpdId());//处方明细id
							pharOutReceiveDetailDO.setCostId(outptCostDTO.getId());//费用id
							pharOutReceiveDetailDO.setItemCode(outptCostDTO.getItemCode());//项目类型
							pharOutReceiveDetailDO.setItemId(outptCostDTO.getItemId());//项目id
							pharOutReceiveDetailDO.setItemName(outptCostDTO.getItemName());//项目名称
							pharOutReceiveDetailDO.setSpec(outptCostDTO.getSpec());//规格
							pharOutReceiveDetailDO.setDosage(outptCostDTO.getDosage());//剂量
							pharOutReceiveDetailDO.setDosageUnitCode(outptCostDTO.getDosageUnitCode());//剂量单位


							pharOutReceiveDetailDO.setUnitCode(outptCostDTO1.getUnitCode());//单位代码
							pharOutReceiveDetailDO.setPrice(outptCostDTO1.getPrimaryPrice());//单价
							pharOutReceiveDetailDO.setSplitRatio(outptCostDTO1.getSplitRatio());//拆分比
							pharOutReceiveDetailDO.setSplitUnitCode(outptCostDTO1.getSplitUnitCode());//拆零单位代码（DW）
							pharOutReceiveDetailDO.setSplitPrice(outptCostDTO1.getSplitPrice());//拆零单价
							if (outptCostDTO1.getUnitCode().equals(outptCostDTO.getNumUnitCode())) {//原单位
								pharOutReceiveDetailDO.setNum(outptCostDTO.getTotalNum());//数量
								pharOutReceiveDetailDO.setSplitNum(BigDecimalUtils.multiply(outptCostDTO.getTotalNum(), outptCostDTO1.getSplitRatio()));//拆零数量
							} else if (outptCostDTO1.getSplitUnitCode().equals(outptCostDTO.getNumUnitCode())) {//拆零单位
								pharOutReceiveDetailDO.setNum(BigDecimalUtils.divide(outptCostDTO.getTotalNum(), outptCostDTO1.getSplitRatio()));//数量
								pharOutReceiveDetailDO.setSplitNum(outptCostDTO.getTotalNum());//拆零数量
							}
							pharOutReceiveDetailDO.setTotalPrice(outptCostDTO.getTotalPrice());//总金额
							pharOutReceiveDetailDO.setChineseDrugNum(outptCostDTO.getHerbNum());//中药付数
							pharOutReceiveDetailDO.setUsageCode(outptCostDTO.getUsageCode());//用法代码（YF）
							pharOutReceiveDetailDO.setUseCode(outptCostDTO.getUseCode());//用药性质代码（YYXZ）
							//开单单位
							pharOutReceiveDetailDO.setCurrUnitCode(outptCostDTO.getNumUnitCode());
							pharOutReceiveDetailDOList.add(pharOutReceiveDetailDO);
						}
					});
				}
			}
			// i + 优惠后金额 = 本次结算总费用
			realityPrice = BigDecimalUtils.add(realityPrice, outptCostDTO.getRealityPrice());
			ids.add(outptCostDTO.getId());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("outptPrescribeIds", outptPrescribeIds);
		map.put("ids", ids);
		map.put("realityPrice", realityPrice);
		map.put("pharOutReceiveMap", pharOutReceiveMap);
		map.put("pharOutReceiveDetailDOList", pharOutReceiveDetailDOList);
		return map;
	}

	/**
	 * @Description: 保存支付方式（结算）
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/8 11:19
	 * @Return
	 */
	private boolean saveOutptPays(String sourcePayCode, BigDecimal paySettleMoney, String orderNo, String hospCode, String settleId, String visitId, OutptSettleDTO outptSettleDTO1) {
		boolean isChange = false;
		List<OutptPayDO> outptPayDOS = new ArrayList<OutptPayDO>();
		BigDecimal actualPrice = new BigDecimal(0);

		OutptPayDO outptPayDO = new OutptPayDO();
		outptPayDO.setId(SnowflakeUtils.getId());//id
		outptPayDO.setHospCode(hospCode);//医院编码
		outptPayDO.setSettleId(settleId);//结算id
		outptPayDO.setVisitId(visitId);//就诊id
		outptPayDO.setPayCode(sourcePayCode);  // 支付方式
		outptPayDO.setPrice(paySettleMoney);  // 支付金额
		outptPayDO.setOrderNo(orderNo);  // 票据号
		actualPrice = BigDecimalUtils.add(actualPrice, outptPayDO.getPrice());
		outptPayDOS.add(outptPayDO);


		//BigDecimal ssje = BigDecimalUtils.subtract(outptSettleDTO1.getSelfPrice(), outptSettleDTO1.getTruncPrice()); // 实收金额= 个人自付金额-舍人金额
		//判断 实收金额 >= 需支付金额
		int greater = BigDecimalUtils.compareTo(outptSettleDTO1.getRealityPrice(), actualPrice);
		//int greater = BigDecimalUtils.compareTo(ssje, actualPrice);
		if (greater > 0) {
			isChange = true;
		}
		//有退款（找零）费用
		if (greater < 0) {
			//相差值(实收金额-应收金额)
			BigDecimal dif = BigDecimalUtils.subtract(outptSettleDTO1.getRealityPrice(), actualPrice);
			// ============================官红强修改  2021年1月8日15:25:42=====
			// 有退款，不再存储负数，直接将现金支付金额减去退款金额保存，例如：应收180，现金付100，支付宝付100，那么保存时直接在现金上减去20 ，保存现金支付金额为80=================
			for (int i = 0; i < outptPayDOS.size(); i++) {
				// 将现金支付金额减去退款金额
				if (outptPayDOS.get(i).getPayCode().equals(Constants.ZFFS.XJ)) {
					outptPayDOS.get(i).setPrice(BigDecimalUtils.add(outptPayDOS.get(i).getPrice(), dif));
					break;
				}
			}
			// ============================官红强修改  2021年1月8日15:25:42===========================================
		}
		//批量保存结算信息
		if (!ListUtils.isEmpty(outptPayDOS)) {
			wxSettleDAO.batchInsertOutptPay(outptPayDOS);
		}
		return isChange;
	}

	/**
	 * @Description: 取最佳领药窗口，生成领药申请单（主单），保存领药申请单与领药申请单详单
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/8 11:32
	 * @Return
	 */
	private void savePharOutReceive(String hospCode, String visitId, String depId, String userId, String userName, String settleId,
									Map<String, Map<String, Object>> pharOutReceiveMap, List<OutptCostDTO> outptCostDTOList, List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList) {
		Map<String, String> bwParam = new HashMap<String, String>();
		bwParam.put("hospCode", hospCode);//医院编码
		bwParam.put("isValid", Constants.SF.S);//是否有效
		List<PharOutReceiveDO> pharOutReceiveDOList = new ArrayList<>();

		for (String pharId : pharOutReceiveMap.keySet()) {
			//获取最佳领药窗口
			bwParam.put("pharId", pharId);//药房id
			List<Map<String, Object>> windosList = wxSettleDAO.queryShortcutWindows(bwParam);
			if (windosList == null || windosList.isEmpty()) {
				String deptName = wxSettleDAO.queryBaseDeptById(bwParam);
				throw new AppException("【" + deptName + "】未找到发药窗口");
			}
			//生成领药申请单
			PharOutReceiveDO pharOutReceiveDO = new PharOutReceiveDO();
			pharOutReceiveDO.setId((String) pharOutReceiveMap.get(pharId).get("id"));//id
			pharOutReceiveDO.setHospCode(hospCode);//医院编码
			pharOutReceiveDO.setVisitId(visitId);//就诊id
			pharOutReceiveDO.setSettleId(settleId);//结算id
			pharOutReceiveDO.setPharId(pharId);//发药药房ID
			pharOutReceiveDO.setWindowId((String) windosList.get(0).get("id"));//发药窗口
			pharOutReceiveDO.setTotalPrice((BigDecimal) pharOutReceiveMap.get(pharId).get("totalPrice"));//总金额
			Boolean isDist = Constants.SF.F.equals(outptCostDTOList.get(0).getIsDist());//判断是否已发药
			pharOutReceiveDO.setStatusCode(isDist ? Constants.LYZT.QL : Constants.LYZT.FY);//发药状态
			pharOutReceiveDO.setDeptId(depId);//申请科室
			pharOutReceiveDO.setCrteId(userId);//创建人
			pharOutReceiveDO.setCrteName(userName);//创建人姓名
			pharOutReceiveDO.setCrteTime(new Date());//创建时间
			pharOutReceiveDOList.add(pharOutReceiveDO);
		}

		if (!pharOutReceiveDOList.isEmpty() || !pharOutReceiveDetailDOList.isEmpty()) {
			//生成领药申请单
			Map<String, Object> pharOutReceiveParam = new HashMap<String, Object>();
			pharOutReceiveParam.put("hospCode", hospCode);//医院编码
			pharOutReceiveParam.put("pharOutReceiveDOList", pharOutReceiveDOList);
			pharOutReceiveService_consumer.batchInsert(pharOutReceiveParam);
			pharOutReceiveParam.put("pharOutReceiveDetailDOList", pharOutReceiveDetailDOList);
			pharOutReceiveDetailService_consumer.batchInsert(pharOutReceiveParam);
		}
	}

	/**
	 * @Description: 校验处方是否结算
	 * @Param: paramMap
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2022/10/10 09:10
	 * @Return WrapperResponse<String>
	 */
	@Override
	public WrapperResponse checkPrescribeIsSettle(Map<String, Object> map) {
		String hospCode = MapUtils.get(map, "hospCode");   // 医院编码
		Map<String, Object> tempMap = MapUtils.get(map, "data");
		String visitId = MapUtils.get(tempMap, "visitId");     // 患者就诊id
		String opId = MapUtils.get(tempMap, "opId");  // 处方id
        Map<String,Object> param =new HashMap<>();
        param.put("id",opId);
        param.put("visitId",visitId);
        param.put("hospCode",hospCode);
		int prescribeSettle = wxOutptDAO.queryPrescribeIsSettle(param);
		int costSettle = wxOutptDAO.queryCostIsSettle(param);
		if (prescribeSettle>0 || costSettle>0){
			throw new AppException("当前处方已结算，无需再次结算");
		}
		Map<String,Object> result =new HashMap<>();
		result.put("isSettle",false);
		// 处方校验结果加密返回给微信小程序
		log.debug("微信小程序【门诊收费 处方校验】返参加密前：" + result.toString());
		String res = null;
		try {
			res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
			log.debug("微信小程序【门诊收费 处方校验】返参加密后：" + res);
		} catch (UnsupportedEncodingException e) {
			throw new AppException("【门诊收费 处方校验】返参加密错误，请联系管理员！" + e.getMessage());
		}
		return WrapperResponse.success("成功。", res);
	}

	@Override
	public WrapperResponse checkPrescribeCodeIsValid(Map<String, Object> map) {
		String hospCode = MapUtils.get(map, "hospCode");   // 医院编码
		Map<String, Object> tempMap = MapUtils.get(map, "data");
		String visitId = MapUtils.get(tempMap, "visitId");     // 患者就诊id
		String opId = MapUtils.get(tempMap, "opId");  // 处方id
		String codeTime = MapUtils.get(tempMap,"codeTime"); // 打印二维码时间
		if (StringUtils.isEmpty(codeTime)){
			throw new AppException("参数错误：二维码打印时间codeTime参数未传！");
		}
		Map<String,Object> param =new HashMap<>();
		param.put("id",opId);
		param.put("visitId",visitId);
		param.put("hospCode",hospCode);
		Map<String,Object> prescribeIsValidInfo = wxOutptDAO.queryPrescribeIsValid(param);
		int codeValidTime = MapUtils.get(prescribeIsValidInfo,"codeValidTime",0);
		Date validTime = DateUtils.dateAddMinute(DateUtils.parse(codeTime,DateUtils.Y_M_DH_M_S), codeValidTime);
        if (validTime!=null){
        	if (DateUtils.dateCompare(validTime,DateUtils.getNow())){
				throw new AppException("失效错误：当前二维码已失效！");
			}
		}
		Map<String,Object> resultInfo =new HashMap<>();
		resultInfo.put("isValid",true);
		// 二维码有效期校验结果加密返回给微信小程序
		log.debug("微信小程序【门诊收费 二维码有效期校验】返参加密前：" + resultInfo.toString());
		String res = null;
		try {
			res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(resultInfo));
			log.debug("微信小程序【门诊收费 二维码有效期校验】返参加密后：" + res);
		} catch (UnsupportedEncodingException e) {
			throw new AppException("【门诊收费 二维码有效期校验】返参加密错误，请联系管理员！" + e.getMessage());
		}
		return WrapperResponse.success("成功。", res);
	}


}
