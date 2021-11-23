package cn.hsa.interf.dzpz.controller;

import cn.hsa.module.interf.outpt.service.HaiNanDZPZPayCallBackService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("interf/dzpz/haiNanDZPZPayCallBack")
@Slf4j
public class HaiNanDZPZPayCallBackController {

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
	@PostMapping("/saveDZPZSettle")
	public void saveDZPZSettle(@RequestBody String param){
		String[] str = param.split("&&&&&");
		Map<String, String> map = JSON.parseObject(str[0], HashMap.class);  // 移动支付返回数据
		map.put("hospCode", str[1]);  // 医院编码
		haiNanDZPZPayCallBackService.haiNanDZPZPayCallBack(map);
	}
}
