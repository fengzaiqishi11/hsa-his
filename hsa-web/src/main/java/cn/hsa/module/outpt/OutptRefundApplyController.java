package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.service.OutptOutTmakePriceFormService;
import cn.hsa.module.outpt.outptrefundapply.service.OutptRefundApplyService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptRefundApplyController
 * @Describe: 门诊医生退费申请controller层
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 15:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/outpt/outptRefundApply")
@Slf4j
public class OutptRefundApplyController extends BaseController {

	@Resource
	private OutptRefundApplyService outptRefundApplyService_consumer;

	/**
	 * @Description: 查询当前登录账号创建的处方病人（已经结算）
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 14:38
	 * @Return
	 */
	@GetMapping("/queryOutptChargePageBySelf")
	public WrapperResponse queryOutptChargePageBySelf(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO userDTO = getSession(req, res) ;
		outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
		outptSettleDTO.setDoctorId(userDTO.getId());
		Map param = new HashMap();
		param.put("hospCode",userDTO.getHospCode());
		param.put("outptSettleDTO",outptSettleDTO);
		return outptRefundApplyService_consumer.queryOutChargePage(param);
	}

	/**
	 * @Description: 门诊医生退费申请保存
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 16:06
	 * @Return
	 */
	@PostMapping("/saveOutptRefundAppy")
	public WrapperResponse<Boolean> saveOutptRefundAppy(@RequestBody Map<String,Object> param,HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO userDTO = getSession(req, res) ;
		param.put("hospCode", userDTO.getHospCode());
		param.put("crteId", userDTO.getId());
		param.put("crteName", userDTO.getName());
		return outptRefundApplyService_consumer.saveOutptRefundAppy(param);
	}

	/**
	 * @Description: 查询处方明细费用，并带出已经申请的数量
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 15:24
	 * @Return
	 */
	@GetMapping(value = "/queryOutptPrescribes")
	public WrapperResponse queryOutptPrescribes(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO userDTO = getSession(req, res) ;
		outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
		Map param = new HashMap();
		if (!StringUtils.isEmpty(outptSettleDTO.getRedId())) {
			outptSettleDTO.setId(outptSettleDTO.getRedId());
		}
		param.put("hospCode",userDTO.getHospCode());
		param.put("outptSettleDTO",outptSettleDTO);
		return outptRefundApplyService_consumer.queryOutptPrescribes(param);
	}

	/**
	 * @Description: 门诊医生退费申请确认
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 16:40
	 * @Return 
	 */
	@PostMapping("/updateOutptRefundAppyStatus")
	public WrapperResponse<Boolean> updateOutptRefundAppyStatus(@RequestBody Map<String,Object> param,HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO userDTO = getSession(req, res) ;
		param.put("hospCode", userDTO.getHospCode());
		return outptRefundApplyService_consumer.updateOutptRefundAppyStatus(param);
	}

	/**
	 * @Description: 门诊医生取消退费确认
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 14:29
	 * @Return
	 */
	@PostMapping("/updateUnconfirmedOutptRefundAppy")
	public WrapperResponse<Boolean> updateUnconfirmedOutptRefundAppy(@RequestBody Map<String,Object> param,HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO userDTO = getSession(req, res) ;
		param.put("hospCode", userDTO.getHospCode());
		return outptRefundApplyService_consumer.updateUnconfirmedOutptRefundAppy(param);
	}

	/**
	 * @Description: 门诊医生退费取消申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 16:06
	 * @Return
	 */
	@PostMapping("/saveCancelOutptRefundAppy")
	public WrapperResponse<Boolean> saveCancelOutptRefundAppy(@RequestBody Map<String,Object> param,HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO userDTO = getSession(req, res) ;
		param.put("hospCode", userDTO.getHospCode());
		param.put("crteId", userDTO.getId());
		param.put("crteName", userDTO.getName());
		return outptRefundApplyService_consumer.saveCancelOutptRefundAppy(param);
	}

}
