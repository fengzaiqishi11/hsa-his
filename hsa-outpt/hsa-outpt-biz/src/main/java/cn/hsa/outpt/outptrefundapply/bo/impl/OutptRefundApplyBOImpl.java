package cn.hsa.outpt.outptrefundapply.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.outptrefundapply.bo.OutptRefundApplyBO;
import cn.hsa.module.outpt.outptrefundapply.dao.OutptRefundApplyDAO;
import cn.hsa.module.outpt.outptrefundapply.dto.OutptRefundApplyDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptrefundapply.bo.impl
 * @Class_name: OutptRefundApplyBOImpl
 * @Describe: 门诊医生退费申请实现层
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 15:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class OutptRefundApplyBOImpl implements OutptRefundApplyBO {

	@Resource
	private OutptRefundApplyDAO outptRefundApplyDAO;


	/**
	 * @Description: 门诊医生退费申请保存
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 15:43
	 * @Return
	 */
	@Override
	public Boolean saveOutptRefundAppy(Map<String, Object> map) {
		List<OutptCostDTO> outptCostDTOList = JSON.parseArray(JSON.toJSONString(map.get("outptCostDTOList")),OutptCostDTO.class);
		OutptSettleDTO outptSettleDTO = JSON.parseObject(JSON.toJSONString(map.get("outptSettleDTO")),OutptSettleDTO.class);
		String settleId = outptSettleDTO.getId();
		String crteId = MapUtils.get(map, "crteId");
		String crteName = MapUtils.get(map, "crteName");
		String refundXplain = MapUtils.get(map, "refundXplain"); // 退费说明
		List<OutptRefundApplyDTO> list = new ArrayList<>();
		List<String> costIdList = new ArrayList();
		if (outptCostDTOList != null && outptCostDTOList.size() > 0) {
			for (OutptCostDTO dto : outptCostDTOList) {
				costIdList.add(dto.getId());
				if (!BigDecimalUtils.isZero(dto.getBackNum())) {
					OutptRefundApplyDTO applyDTO = new OutptRefundApplyDTO();
					BeanUtils.copyProperties(dto, applyDTO);
					applyDTO.setCrteId(crteId);
					applyDTO.setCrteName(crteName);
					applyDTO.setCrteTime(DateUtils.getNow());
					applyDTO.setRefundCode("1");  // 待定输入值
					applyDTO.setRefundXplain(refundXplain);
					applyDTO.setCostId(dto.getId());
					applyDTO.setNum(dto.getBackNum());
					applyDTO.setStatus("1"); // 1:已申请待确认  2：已确认
					applyDTO.setSettleId(settleId);
					applyDTO.setOneSettleId(dto.getOneSettleId());
					list.add(applyDTO);
				}
			}
		}
		// 需要将原来的数据删除再新增
		outptRefundApplyDAO.deleteOutptRefundApplyByCostId(costIdList);
		int result = outptRefundApplyDAO.saveOutptRefundAppy(list);
		return result > 0;
	}

	@Override
	public WrapperResponse queryOutChargePage(OutptSettleDTO outptSettleDTO) {
		//设置分页
		PageHelper.startPage(outptSettleDTO.getPageNo(), outptSettleDTO.getPageSize());

		List<Map<String, Object>> list = outptRefundApplyDAO.queryOutChargePage(outptSettleDTO);
		return WrapperResponse.success(PageDTO.of(list));
	}

	@Override
	public WrapperResponse queryOutptPrescribes(Map param) {
		OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");
		List<OutptCostDTO> list = outptRefundApplyDAO.queryOutptPrescribes(outptSettleDTO);
		return WrapperResponse.success(list);
	}

	/**
	 * @Description: 确认门诊医生退费申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 16:28
	 * @Return
	 */
	@Override
	public Boolean updateOutptRefundAppyStatus(Map<String, Object> param) {
		OutptSettleDTO outptSettleDTO = JSON.parseObject(JSON.toJSONString(param.get("outptSettleDTO")),OutptSettleDTO.class);
		outptSettleDTO.setHospCode(MapUtils.get(param, "hospCode"));
		if (outptSettleDTO == null) {
			throw new AppException("门诊医生确认退费申请失败");
		}
		return outptRefundApplyDAO.updateOutptRefundAppyStatus(outptSettleDTO) > 0;
	}

	/**
	 * @Description: 门诊医生取消退费确认
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 14:52
	 * @Return
	 */
	@Override
	public Boolean updateUnconfirmedOutptRefundAppy(Map<String, Object> param) {
		OutptSettleDTO outptSettleDTO = JSON.parseObject(JSON.toJSONString(param.get("outptSettleDTO")),OutptSettleDTO.class);
		List<OutptCostDTO> outptCostDTOList = JSON.parseArray(JSON.toJSONString(param.get("outptCostDTOList")),OutptCostDTO.class);
		outptSettleDTO.setHospCode(MapUtils.get(param, "hospCode"));
		if (outptSettleDTO == null) {
			throw new AppException("门诊医生取消退费确认失败");
		}
		List<String> costIdList = new ArrayList();
		if (outptCostDTOList != null && outptCostDTOList.size() > 0) {
			for (OutptCostDTO dto : outptCostDTOList) {
				if (!BigDecimalUtils.isZero(dto.getBackNum())) {
					costIdList.add(dto.getId());
				}
			}
		}
		if (costIdList==null || costIdList.size() ==0){
			throw new AppException("该申请已进行退费，不能取消申请");
		}
		outptSettleDTO.setCostIdList(costIdList);
		List<OutptCostDTO> outptCostDTOS =outptRefundApplyDAO.queryUncostList(outptSettleDTO);
		if (outptCostDTOS==null || outptCostDTOS.size() ==0){
			throw new AppException("该申请已进行退费，不能取消确认");
		}
		return outptRefundApplyDAO.updateUnconfirmedOutptRefundAppy(outptSettleDTO) > 0;
	}

	/**
	 * @Description: 门诊医生取消退费申请
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 15:47
	 * @Return
	 */
	@Override
	public Boolean deleteOutptRefundAppy(Map<String, Object> param) {
		OutptSettleDTO outptSettleDTO = JSON.parseObject(JSON.toJSONString(param.get("outptSettleDTO")),OutptSettleDTO.class);
		List<OutptCostDTO> outptCostDTOList = JSON.parseArray(JSON.toJSONString(param.get("outptCostDTOList")),OutptCostDTO.class);
		outptSettleDTO.setHospCode(MapUtils.get(param, "hospCode"));
		if (outptSettleDTO == null) {
			throw new AppException("门诊医生取消退费申请失败");
		}
		List<String> costIdList = new ArrayList();
		if (outptCostDTOList != null && outptCostDTOList.size() > 0) {
			for (OutptCostDTO dto : outptCostDTOList) {
				if (!BigDecimalUtils.isZero(dto.getBackNum())) {
					costIdList.add(dto.getId());
				}
			}
		}
		if (costIdList==null || costIdList.size() ==0){
			throw new AppException("该申请已进行退费，不能取消申请");
		}
		outptSettleDTO.setCostIdList(costIdList);
		List<OutptCostDTO> outptCostDTOS =outptRefundApplyDAO.queryUncostList(outptSettleDTO);
		if (outptCostDTOS==null || outptCostDTOS.size() ==0){
			throw new AppException("该申请已进行退费，不能取消申请");
		}
		return outptRefundApplyDAO.deleteOutptRefundAppy(outptSettleDTO) > 0;
	}
}
