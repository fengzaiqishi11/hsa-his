package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureReadCardBO;
import cn.hsa.module.insure.inpt.service.InsureReadCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.InsureReadCardServiceImpl
 * @Class_name: InsureReadCardServiceImpl
 * @Describe: 读卡接口
 * @Author: LiaoJiGuang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/7/29 15:00
 * @Company: CopyRight@2021 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureReadCard")
@Service("insureReadCardService_provider")
public class InsureReadCardServiceImpl extends HsafService implements InsureReadCardService {

	@Resource
	private InsureReadCardBO insureReadCardBO;

	/**
	 * @Description: 读身份证密码校验接口
	 * @Param: map
	 * @Author: LiaoJiGuang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date 2021/7/29 15:03
	 * @Return
	 */
	@Override
	public WrapperResponse<Map<String, Object>> getReadIdCard(Map<String, Object> map) {
		return WrapperResponse.success(insureReadCardBO.getReadIdCard(map));
	}

	/**
	 * @Description: 读身份证修改密码接口
	 * @Param: map
	 * @Author: LiaoJiGuang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date 2021/7/30 15:03
	 * @Return
	 */
	@Override
	public WrapperResponse<Map<String, Object>> updateReadIdCard(Map<String, Object> map) {
		return WrapperResponse.success(insureReadCardBO.updateReadIdCard(map));
	}

}
