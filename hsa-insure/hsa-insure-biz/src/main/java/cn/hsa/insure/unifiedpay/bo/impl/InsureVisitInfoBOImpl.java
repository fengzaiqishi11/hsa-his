package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.bo.InsureVisitInfoBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: InsureVisitInfoBOImpl
 * @Describe: 医保患者信息获取
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/22 14:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class InsureVisitInfoBOImpl extends HsafBO implements InsureVisitInfoBO {

	@Resource
	private InsureConfigurationDAO insureConfigurationDAO;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private InsureUnifiedPayOutptService insureUnifiedPayOutptService;
	@Resource
	private InsureUnifiedLogService insureUnifiedLogService_consumer;
	@Resource
	private SysParameterService sysParameterService_consumer;

	/**
	 * @Description: 获取人员信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/2 9:44
	 * @Return
	 */
	@Override
	public Map<String, Object> getInsureVisitInfo(Map<String, Object> params) {
		InsureConfigurationDTO dto = new InsureConfigurationDTO();
		dto.setHospCode((String) params.get("hospCode"));
		InsureIndividualBasicDTO insureIndividualBasicDTO = (InsureIndividualBasicDTO) params.get("insureIndividualBasicDTO");
		String regCode = null;
		if (insureIndividualBasicDTO != null) {
			regCode = insureIndividualBasicDTO.getInsureRegCode();
		} else {
			regCode = (String) params.get("regCode");
		}
		if (regCode == null || regCode.equals("")) {
			throw new AppException("获取患者医保信息时没有拿到指定医保机构注册编码，请联系管理员");
		}
		dto.setRegCode(regCode);
		InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(dto);
		if (insureConfigurationDTO == null) {
			throw new AppException("未查询到就诊地医保区划");
		}
		Map<String, Object> inputMap = new HashMap<>();
		Map<String, Object> dataMap = new HashMap<>();
		String functionCode = "1101";
		inputMap.put("infno", functionCode);  // 交易编号
		inputMap.put("medins_code",  insureConfigurationDTO.getOrgCode()); // 医疗机构编码
		Map<String, Object> visitMap = new HashMap<>();
		String mdtrtCertType = insureIndividualBasicDTO.getBka895();
		//  电子凭证 - 需根据各市政策需要选择性传值 add 2021-06-16 by liaojiguang QrCodePolicy
		if(Constant.UnifiedPay.CKLX.DZPZ.equals(mdtrtCertType)) {
			visitMap.put("mdtrt_cert_type", Constant.UnifiedPay.CKLX.DZPZ);  // 就诊凭证类型  传值01
			visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896()); // 传值证件号码

			// 查询电子凭证系统参数读卡政策，湖南省需要证件号码/类型/姓名
			Map<String, Object> tempMap = new HashMap<>();
			tempMap.put("hospCode", params.get("hospCode"));
			tempMap.put("code", "QrCodePolicy");
			SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(tempMap).getData();
			String QrCodePolicy = sys.getValue();
			if (Constant.UnifiedPay.QrCodePolicy.HN.equals(QrCodePolicy)) {
				visitMap.put("psn_cert_type", insureIndividualBasicDTO.getPsnCertType());  // 证件类型
				visitMap.put("psn_name", insureIndividualBasicDTO.getAac003()); // 传值姓名，
				visitMap.put("certno", insureIndividualBasicDTO.getAac002()); // 传值证件号码
			}
		}
		// 身份证  本地社保卡 和省内异地社保卡
		else if(Constant.UnifiedPay.CKLX.SFZ.equals(mdtrtCertType)  || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)) {
			visitMap.put("mdtrt_cert_type", Constant.UnifiedPay.CKLX.SFZ);  // 就诊凭证类型  传值02
			if("null".equals(insureIndividualBasicDTO.getAac002())  || StringUtils.isEmpty(insureIndividualBasicDTO.getAac002()) ){
				visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896()); // 传值证件号码
			}else{
				visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getAac002()); // 传值证件号码
			}
			visitMap.put("psn_name","null".equals(insureIndividualBasicDTO.getAac003())?"":insureIndividualBasicDTO.getAac003()); // 传值姓名，
			visitMap.put("certno", "null".equals(insureIndividualBasicDTO.getAac002())?"":insureIndividualBasicDTO.getAac002()); // 传值证件号码
		}
		// 跨省异地读卡
		else if(Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType)){
			visitMap.put("mdtrt_cert_type",mdtrtCertType);  // 就诊凭证类型  传值03
			visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896()); // 传值证件号码
			visitMap.put("card_sn", insureIndividualBasicDTO.getCardIden()); // 传值社保卡识别码
			visitMap.put("psn_cert_type", "1");  //1
			visitMap.put("psn_name", insureIndividualBasicDTO.getAac003()); // 传值姓名，
			visitMap.put("certno", insureIndividualBasicDTO.getAac002()); // 传值证件号码
		}
		// 澳门证件类型  香港
		else if(Constant.UnifiedPay.CKLX.AM.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.XG.equals(mdtrtCertType)){
			visitMap.put("mdtrt_cert_type", mdtrtCertType);  // 就诊凭证类型
			visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896()); // 传值证件号码
			visitMap.put("card_sn", insureIndividualBasicDTO.getCardIden()); // 传值证件号码
			visitMap.put("psn_cert_type", insureIndividualBasicDTO.getBka895());  // 传值05 或 04
			visitMap.put("certno", insureIndividualBasicDTO.getAac002()); // 传值证件号码
			visitMap.put("psn_name", insureIndividualBasicDTO.getAac003()); // 传值姓名，
		}
		// 其他证件
		else{
			visitMap.put("mdtrt_cert_type",mdtrtCertType);  // 就诊凭证类型  传值05
			visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896()); // 传值证件号码
			visitMap.put("psn_cert_type", "99");  // 传值99
			visitMap.put("certno", insureIndividualBasicDTO.getBka896()); // 传值证件号码
			visitMap.put("psn_name", insureIndividualBasicDTO.getAac003()); // 传值姓名，
		}
		String medisCode = insureConfigurationDTO.getOrgCode();
		visitMap.put("medins_code", medisCode);
		String medType = insureIndividualBasicDTO.getAka130();
		visitMap.put("med_type", medType);
		visitMap.put("med_mdtrt_type", insureIndividualBasicDTO.getBka006());
		dataMap.put("data", visitMap);
		inputMap.put("input", dataMap);  // 入参具体数据
		inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());  // 就医地医保区划
		inputMap.put("insur_code", insureConfigurationDTO.getRegCode());  // 医保中心编码
		if("03".equals(mdtrtCertType)){
			if(StringUtils.isEmpty(insureIndividualBasicDTO.getInsuplc_admdvs())){
				inputMap.put("insuplc_admdvs", ""); //参保地医保区划分
			}else{
				inputMap.put("insuplc_admdvs", insureIndividualBasicDTO.getInsuplc_admdvs()); //参保地医保区划分
			}
		}else{
			inputMap.put("insuplc_admdvs", ""); //参保地医保区划分
		}
		String omsgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
		inputMap.put("msgid", omsgId);
		String url = insureConfigurationDTO.getUrl();
		String json = JSONObject.toJSONString(inputMap);
		logger.info("统一支付平台获取人员信息 [1101] 入参====>");
		logger.info(json);
		// 调用统一支付平台接口
		String result = HttpConnectUtil.unifiedPayPostUtil(url, json);
		params.put("medisCode",medisCode);
		params.put("msgId",omsgId);
		params.put("msgInfo",functionCode);
		params.put("msgName","医保个人信息获取");
		if (Constants.SF.F.equals(MapUtils.get(params,"isHospital"))) {
			params.put("isHospital",Constants.SF.F) ;
		}else{
			params.put("isHospital",Constants.SF.S) ;
		}
		params.put("paramMapJson",json);
		params.put("resultStr",result);
		insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();

		logger.info("统一支付平台获取人员信息 [1101] 回参<====");
		logger.info(result);
		if(StringUtils.isEmpty(result)){
			throw new AppException("无法访问医保统一支付平台");
		}
		Map<String, Object> resultMap = JSON.parseObject(result);

		if (!resultMap.get("infcode").equals("0")) {
			throw new AppException("调用统一支付平台出错" + resultMap.get("err_msg"));
		}
		// 14: 门慢门特
		List<Map<String,Object>> mapList = null;
		Map<String, Object> tempMap = new HashMap<>();
		Object output = MapUtils.get(resultMap, "output");
		if(output instanceof  JSONArray){
			List<Map<String,Object>> list2 = MapUtils.get(resultMap,"output");
			tempMap = list2.get(0);
		}else if(output instanceof  JSONObject){
			tempMap = (Map<String, Object>) resultMap.get("output");
		}
		Map<String, Object> baseInfoMap = (Map<String, Object>) tempMap.get("baseinfo");
		JSONArray jsonArray = (JSONArray) tempMap.get("insuinfo");
		JSONArray idetinfoArray = (JSONArray) tempMap.get("idetinfo");
		if("14".equals(medType)) {
			params.put("psnNo",baseInfoMap.get("psn_no"));
			params.put("insureRegCode",insureConfigurationDTO.getOrgCode()); // 医疗机构编码
			params.put("regCode",insureConfigurationDTO.getRegCode()); // 医疗机构编码
			Map<String,Object> outptMap = insureUnifiedPayOutptService.UP_5301(params).getData();
			mapList =(List<Map<String,Object>>) MapUtils.get(outptMap,"mapList");
			if(ListUtils.isEmpty(mapList)){
				throw new AppException("未获取到该人员的慢特病备案查询信息");
			}

			mapList = mapList.stream().filter(filterMap -> StringUtils.isEmpty(MapUtils.get(filterMap, "enddate")) ||
					DateUtils.dateCompare(DateUtils.parse(DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D), DateUtils.Y_M_D),
							DateUtils.parse(MapUtils.get(filterMap, "enddate"), DateUtils.Y_M_D)
					)).collect(Collectors.toList());
		}
		// 需要将统一支付平台的个人信息返回数据转换格式
		Map<String, Object> returnMap = new HashMap<>();
		Map<String, Object> personinfoMap = new HashMap<>();
		// 医保患者信息
		List<Map<String, Object>> list = new ArrayList<>();
		// 患者享受的医保险种
		List<Map<String, Object>> ybxzList = new ArrayList<>();
		// 个人基础信息
		if (baseInfoMap != null ) {
			personinfoMap.put("aac001", baseInfoMap.get("psn_no"));  // 人员编号
			personinfoMap.put("aac003", baseInfoMap.get("psn_name"));  // 姓名
			personinfoMap.put("psn_cert_type", baseInfoMap.get("psn_cert_type"));  // 身份证
			personinfoMap.put("aac002", baseInfoMap.get("certno"));  // 身份证
			personinfoMap.put("aac004", baseInfoMap.get("gend"));  // 性别
			personinfoMap.put("aac006", baseInfoMap.get("brdy"));  //出生日期
			personinfoMap.put("age", MapUtils.get(baseInfoMap,"age"));  //年龄
			list.add(personinfoMap);
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			Map<String, Object> insuinfoMap = jsonArray.getJSONObject(i);
			Map<String, Object> xzMap = new HashMap<>();
			list.get(0).put("balc",MapUtils.get(insuinfoMap,"balc"));
			xzMap.put("bka035", insuinfoMap.get("psn_type"));  // 人员类别
			list.get(0).put("bka008",MapUtils.get(insuinfoMap,"emp_name"));
			list.get(0).put("aae140",MapUtils.get(insuinfoMap,"insutype"));
			xzMap.put("bka008", insuinfoMap.get("emp_name"));  // 单位名称
			xzMap.put("aae140", insuinfoMap.get("insutype"));  // 险种类型
			xzMap.put("cvlserv_flag", insuinfoMap.get("cvlserv_flag"));  // 公务员标志
			xzMap.put("aae140Name", insuinfoMap.get("insutype"));  // 险种类型
			xzMap.put("bacu18", MapUtils.get(insuinfoMap,"balc"));  // 账户余额
			xzMap.put("insuplc_admdvs", insuinfoMap.get("insuplc_admdvs"));  // 医保参保区划
			ybxzList.add(xzMap);
		}

		List<Map<String,Object>> idetinList = new ArrayList<>();
		if (!ListUtils.isEmpty(idetinfoArray)) {
			for (int i = 0; i < idetinfoArray.size(); i++) {
				Map<String, Object> idetinfoMap = idetinfoArray.getJSONObject(i);
				idetinList.add(idetinfoMap);
			}
		}

		Map<String, Object> spinfoMap = new HashMap<>();
		Map<String, Object> injuryorbirthinfoMap = new HashMap<>();
		returnMap.put("tempMap",tempMap);
		returnMap.put("personinfo", list);
		returnMap.put("spinfo", spinfoMap);
		returnMap.put("injuryorbirthinfo", injuryorbirthinfoMap);
		returnMap.put("ybxzList", ybxzList);
		returnMap.put("mapList",mapList);
		returnMap.put("idetinList",idetinList);
		return returnMap;
	}

	/**
	 * @Description: 统一支付平台 医保目录下载
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:59
	 * @Return
	 */
	@Override
	public Map<String, Object> DOWN_3301(Map<String, Object> params) {
		return null;
	}

	/**
	 * @Description: 统一支付平台 医保匹配目录上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 14:00
	 * @Return
	 */
	@Override
	public void UP_3302(Map<String, Object> params) {
		String hosp_code = MapUtils.get(params, "hospCode");
		String Hosp_Name = MapUtils.get(params, "hospName");
	}

	/**
	 * @Description: 统一支付平台 医保已经完成匹配目录查询
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:59
	 * @Return
	 */
	@Override
	public Map<String, Object> DOWN_5163(Map<String, Object> params) {
		return null;
	}
}
