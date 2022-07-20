package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.bo.InsureVisitInfoBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
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
	@Resource
	private InsureItfBOImpl insureItfBO;

	@Resource
	private BaseReqUtilFactory baseReqUtilFactory;

  @Resource
  private InsureDictService insureDictService;

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
		String regCode;
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
		String medType = insureIndividualBasicDTO.getAka130();

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.putAll(params);
		// 参保地医保区划
		paramMap.put("insuplcAdmdvs", insureIndividualBasicDTO.getInsuplc_admdvs());
		paramMap.put("configRegCode", regCode);
		//参数校验,规则校验和请求初始化
		BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INSUR_BASE_INFO.getCode());
		InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
		interfaceParamDTO.setHospCode((String) params.get("hospCode"));
		if (Constants.SF.F.equals(MapUtils.get(params,"isHospital"))) {
			interfaceParamDTO.setIsHospital(Constants.SF.F);
		}else{
			interfaceParamDTO.setIsHospital(Constants.SF.S);
		}
		interfaceParamDTO.setVisitId(insureIndividualBasicDTO.getVisitId());

		Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.INSUR_BASE_INFO, interfaceParamDTO);

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
			list.get(0).put("bka035",MapUtils.get(insuinfoMap,"psn_type"));
			xzMap.put("bka008", insuinfoMap.get("emp_name"));  // 单位名称
			xzMap.put("aae140", insuinfoMap.get("insutype"));  // 险种类型
			xzMap.put("cvlserv_flag", insuinfoMap.get("cvlserv_flag"));  // 公务员标志
			xzMap.put("aae140Name", insuinfoMap.get("insutype"));  // 险种类型
			xzMap.put("bacu18", MapUtils.get(insuinfoMap,"balc"));  // 账户余额
			xzMap.put("insuplc_admdvs", insuinfoMap.get("insuplc_admdvs"));  // 医保参保区划
			ybxzList.add(xzMap);
		}


    //先查询出人员身份类别对应码值
    Map<String,Object> dictMap = new HashMap<>();
    InsureDictDO insureDictDO = new InsureDictDO();
    insureDictDO.setCode("PSN_IDET_TYPE");
    insureDictDO.setHospCode((String) params.get("hospCode"));
    dictMap.put("insureDictDO",insureDictDO);
    dictMap.put("hospCode",(String) params.get("hospCode"));
    List<InsureDictDO> dictList = insureDictService.queryInsureDict(dictMap).getData();

    List<Map<String,Object>> idetinList = new ArrayList<>();
    if (!ListUtils.isEmpty(idetinfoArray)) {
      for (int i = 0; i < idetinfoArray.size(); i++) {
        Integer flag = 0;
        Map<String, Object> idetinfoMap = idetinfoArray.getJSONObject(i);
        if(ObjectUtil.isNotEmpty(MapUtils.get(idetinfoMap, "begntime"))&&ObjectUtil.isNotEmpty(MapUtils.get(idetinfoMap, "endtime"))){
          // 开始时间
          String begntime = MapUtils.get(idetinfoMap, "begntime");
          // 结束时间
          String endtime = MapUtils.get(idetinfoMap, "endtime");
          Date begnDate = DateUtils.parse(DateUtils.calculateDate(begntime, -1), DateUtils.Y_M_D);
          Date endDate = DateUtils.parse(DateUtils.calculateDate(endtime, 1), DateUtils.Y_M_D);
          for (InsureDictDO dict : dictList){
            if (DateUtils.getNow().before(endDate) && DateUtils.getNow().after(begnDate)) {
              if (dict.getId().equals(MapUtils.get(idetinfoMap, "psn_idet_type"))){
                idetinfoMap.put("psn_idet_type_name",dict.getName());
                flag = 1;
                break;
              }
            }
          }
          //在有效期之内展示
          if (1 == flag){
            idetinList.add(idetinfoMap);
          }
        }
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
