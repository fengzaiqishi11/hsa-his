package cn.hsa.interf.dzpz.controller;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.dzpz.hainan.ExtDataDTO;
import cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO;
import cn.hsa.module.dzpz.hainan.SetlInfoDTO;
import cn.hsa.module.interf.outpt.service.HaiNanDZPZPayCallBackService;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.outpt.controller
 * @Class_name: HaiNanDZPZPayCallBack
 * @Describe: 海南电子凭证移动支付后回调方法，用于his生成领药申请，生成医保结算信息等
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/11/17 11:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("pmc/api/hos")
@Slf4j
public class HaiNanDZPZPayCallBackController {

  @Value("${dzpz.hospcode:#{null}}")
  private String dzpzHospCode;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private HaiNanDZPZPayCallBackService haiNanDZPZPayCallBackService;

	/**
	 * @Description: 保存电子凭证结算回调信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/11/17 11:46
	 * @Return
	 */
	@PostMapping("/seltSucCallback")
	public void saveDZPZSettle(@RequestBody SeltSucCallbackDTO seltSucCallbackDTO){
	  //海南医院遍历
		String[] codes = dzpzHospCode.split(";");
		//医院名称对应数据库
    Map<String, String> codeMap = new HashMap<String, String>();
    for (int i = 0; i < codes.length; i++) {
      String[] hosp = codes[i].split(":");
      codeMap.put(hosp[0],hosp[1]);
    }
    //收集入参
    Map<String, String> map = new HashMap<String, String>();
    if (codeMap.containsKey(seltSucCallbackDTO.getOrgName())){
      map.put("hospCode",codeMap.get(seltSucCallbackDTO.getOrgName()));
    }else{
      throw new AppException("系统参数中未匹配到"+seltSucCallbackDTO.getOrgName()+"这家医院，请确认！");
    }
    //额外数据
    ExtDataDTO extData = seltSucCallbackDTO.getExtData();
    if (CollectionUtil.isNotEmpty(extData.getSetlinfo())){
      SetlInfoDTO setlInfo = extData.getSetlinfo().get(0);
    }
    map.put("medOrgOrd",seltSucCallbackDTO.getMedOrgOrd());
    map.put("payOrdId",seltSucCallbackDTO.getPayOrdId());
    map.put("feeSumamt",seltSucCallbackDTO.getFeeSumamt().toString());
    map.put("ownpayAmt",seltSucCallbackDTO.getOwnpayAmt().toString());
    map.put("psnAcctPay",seltSucCallbackDTO.getPsnAcctPay().toString());
    map.put("fundPay",seltSucCallbackDTO.getFundPay().toString());
    map.put("setlType",seltSucCallbackDTO.getSetlType());
    logger.info("医保中心回调入参=======================》"+map);
		haiNanDZPZPayCallBackService.haiNanDZPZPayCallBack(map);
	}
}
