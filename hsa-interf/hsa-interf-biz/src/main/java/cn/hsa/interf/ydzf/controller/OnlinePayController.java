package cn.hsa.interf.ydzf.controller;
/**
 * @author wq
 * @date Created in 2022-06-30 8:40
 */

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *@ClassName OnlinePayController
 *@Author wang'qiao
 *@Date 2022/6/30 8:40
 *@Version 1.0
 *
 **/
@RestController
@RequestMapping("/web/online/onlinePay")
public class OnlinePayController extends BaseController {


	@Resource
	private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

	public <T> T getSession(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		Object value = session.getAttribute("SESSION_USER_INFO");
		return (T) (value == null ? null : value);
	}

	/**
	 * 查询结算结果
	 *
	 * @param param
	 * @param req
	 * @param res
	 * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
	 * @Author 医保开发二部-湛康
	 * @Date 2022-06-16 14:11
	 */
	@PostMapping("/querySettleResult")
	public Map<String, Object> querySettleResult(@RequestBody Map<String, Object> param,
																  HttpServletRequest req,
																  HttpServletResponse res) {
		String hospCode = req.getHeader("hospCode");
		param.put("hospCode", hospCode);
		return outptTmakePriceFormService_consumer.querySettleResult(param);
	}

	/**
	 * @param param, req, res
	 * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
	 * @method queryUnsettleList
	 * @author wang'qiao
	 * @date 2022/6/21 10:30
	 * @description 查询用户院内现在的待缴费费用列表，用于展示给用户进行确认和选择
	 **/
	@PostMapping("/queryUnsettleList")
	public Map<String, Object> queryUnsettleList(@RequestBody Map param, HttpServletRequest req, HttpServletResponse res) {
		String hospCode = req.getHeader("hospCode");

		param.put("hospCode", hospCode);
		return outptTmakePriceFormService_consumer.updateUnsettleList(param);
	}

	/**
	 * @param param, req, res
	 * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
	 * @method queryAccount
	 * @author wang'qiao
	 * @date 2022/6/22 14:22
	 * @description 查询用户在院内的账户信息，如果用户是住院患者需要返回住院所需要的住院病人信息字段
	 **/
	@PostMapping("/queryAccount")
	public Map<String, Object> queryAccount(@RequestBody Map param, HttpServletRequest req, HttpServletResponse res) {
		String hospCode = req.getHeader("hospCode");

		param.put("hospCode", hospCode);
		return outptTmakePriceFormService_consumer.queryAccount(param);
	}

	/**
	 * @param param, req, res
	 * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
	 * @method rechargeSettle
	 * @author wang'qiao
	 * @date 2022/6/23 15:26
	 * @description 用户在平台的收银台上完成结算后，平台会将结算的“结果明细”回写给机构，机构进行内部的充值结算流程
	 **/
	@PostMapping("/rechargeSettle")
	public Map<String, Object> rechargeSettle(@RequestBody Map param, HttpServletRequest req, HttpServletResponse res) {
		String hospCode = req.getHeader("hospCode");


		param.put("hospCode", hospCode);
		return outptTmakePriceFormService_consumer.updateRechargeSettle(param);
	}
}
