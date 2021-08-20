package cn.hsa.module.onecard;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.outpt.card.service.BaseCardRechargeChangeService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.onecard
 * @Class_name: BaseCardRechargeChangeController
 * @Describe: 一卡通异动
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/8/10 10:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/onecard/baseCardRechargeChange")
@Slf4j
public class BaseCardRechargeChangeController extends BaseController {

	// 一卡通异动服务
	@Resource
	private BaseCardRechargeChangeService baseCardRechargeChangeService_consumer;

	/**
	 * @Description: 根据一卡通卡号查询卡余额与卡信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/10 11:08
	 * @Return
	 */
	@RequestMapping("/getBaseCardRechargeChangeDTO")
	public WrapperResponse<BaseCardRechargeChangeDTO> getBaseCardRechargeChangeDTO(@RequestBody BaseCardRechargeChangeDTO baseCardRechargeChangeDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		baseCardRechargeChangeDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		if (baseCardRechargeChangeDTO == null || baseCardRechargeChangeDTO.getCardNo() == null || "".equals(baseCardRechargeChangeDTO.getCardNo())) {
			throw new AppException("未获取到一卡通卡号，请输入正确卡号");
		}
		map.put("baseCardRechargeChangeDTO",baseCardRechargeChangeDTO);
		map.put("hospCode",sysUserDTO.getHospCode());
		return baseCardRechargeChangeService_consumer.getBaseCardRechargeChangeDTO(map);
	}

}
