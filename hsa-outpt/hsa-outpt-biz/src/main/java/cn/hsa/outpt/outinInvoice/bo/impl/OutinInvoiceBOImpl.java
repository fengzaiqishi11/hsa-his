package cn.hsa.outpt.outinInvoice.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.fees.entity.InptSettleInvoiceDO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.bo.OutinInvoiceBO;
import cn.hsa.module.outpt.outinInvoice.dao.OutinInvoiceDAO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinPartInvoiceDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.entity.SysParameterDO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Package_name: cn.hsa.outpt.fee.bo.bo.impl
 * @Class_name:: invoiceBOImpl
 * @Description: 发票管理实现类
 * @Author: liaojiguang
 * @Email: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/24 09:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutinInvoiceBOImpl implements OutinInvoiceBO {

	@Resource
	private OutinInvoiceDAO outinInvoiceDao;

	@Resource
	private SysParameterService sysParameterService;

	@Resource
	private SysParameterService sysParameterService_consumer;

	/**
	 * @Menthod: getInvoicePage
	 * @Desrciption: 根据条件分页查询发票领用信息
	 * @Param: [invoiceDTO]
	 * @Author: lioajiguang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date: 2020/8/24 09:33
	 * @Return: PageDTO
	 **/
	@Override
	public PageDTO getInvoicePage(OutinInvoiceDTO outinInvoiceDTO) {
		// 设置分页信息
		PageHelper.startPage(outinInvoiceDTO.getPageNo(), outinInvoiceDTO.getPageSize());

		// 根据条件查询所有数据
		List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceDao.getInvoicePage(outinInvoiceDTO);
		return PageDTO.of(outinInvoiceDTOS);
	}

	/**
	 * @param outinInvoiceDTO 入参(id,hospCode,prefix,curr_no,settleId)
	 * @Menthod updateInvoiceStatus
	 * @Desrciption 发票使用后，发票主表，明细表数据处理方法
	 * @Author liaojiguang
	 * @Date 2020/9/27 11:27
	 * @Return Boolean
	 **/
	@Override
	public OutinInvoiceDetailDO updateInvoiceStatus(OutinInvoiceDTO outinInvoiceDTO) {
		if (StringUtils.isEmpty(outinInvoiceDTO.getId())) {
			throw new AppException("发票号段ID不能为空");
		}

		if (StringUtils.isEmpty(outinInvoiceDTO.getHospCode())) {
			throw new AppException("医院编码丢失，请联系管理员");
		}

		if (StringUtils.isEmpty(outinInvoiceDTO.getCurrNo())) {
			throw new AppException("发票当前使用号码不能为空");
		}

		OutinInvoiceDO selectInvoice = outinInvoiceDao.getOutinInvoiceById(outinInvoiceDTO);
		if (selectInvoice == null) {
			throw new AppException("发票号段信息不存在");
		}
		int flag = BigDecimalUtils.compareTo(new BigDecimal(outinInvoiceDTO.getCurrNo()), new BigDecimal(selectInvoice.getEndNo()));
		if (flag == 1) {
			throw new AppException("当前发票号码不能大于发票截止号码");
		} else if (flag == 0) {
			selectInvoice.setStatusCode(Constants.PJSYZT.YW);
			selectInvoice.setCurrNo("");
			selectInvoice.setNum(0);
		} else {
			selectInvoice.setNum(BigDecimalUtils.subtract(selectInvoice.getEndNo(), outinInvoiceDTO.getCurrNo()).intValue());
			// 以0开头的发票号左侧补0
			String currentNo =changeInvoiceNo(outinInvoiceDTO.getDqCurrNo(),BigDecimalUtils.add(outinInvoiceDTO.getCurrNo(), "1").toString());
			selectInvoice.setCurrNo(currentNo);
		}
		outinInvoiceDao.updateOutinInvoice(selectInvoice);

		OutinInvoiceDetailDO outinInvoiceDetail = new OutinInvoiceDetailDO();
		outinInvoiceDetail.setId(SnowflakeUtils.getId());
		outinInvoiceDetail.setHospCode(selectInvoice.getHospCode());
		outinInvoiceDetail.setInvoiceId(selectInvoice.getId());
		// 以0开头的发票号左侧补0
		String invoiceNo =changeInvoiceNo(outinInvoiceDTO.getDqCurrNo(),outinInvoiceDTO.getCurrNo());
		outinInvoiceDetail.setInvoiceNo(invoiceNo);
		outinInvoiceDetail.setStatusCode(Constants.PJMXSYZT.YSY);
		outinInvoiceDetail.setUseTime(DateUtils.getNow());
		outinInvoiceDetail.setSettleId(outinInvoiceDTO.getSettleId());
		outinInvoiceDao.insertOutinInvoiceDetail(outinInvoiceDetail);
		return outinInvoiceDetail;

	}

	// 发票数字左边补0 lly 20211015
	public String changeInvoiceNo(String currNo,String userNo){
		if (currNo!=null&&userNo!=null){
			if (currNo.startsWith("0")) {
				return String.format("%0" + currNo.length() + "d", Integer.valueOf(userNo));
			}
		}
		return userNo;
	}

	@Override
	public List<OutinInvoiceDTO> getInvoiceList(OutinInvoiceDTO outinInvoiceDTO) {
		// 根据条件查询所有数据
		return outinInvoiceDao.getInvoiceList(outinInvoiceDTO);
	}

	@Override
	public Boolean updateOutinInvoiceStatus(OutinInvoiceDTO outinInvoiceDTO) {
		int flag = BigDecimalUtils.compareTo(new BigDecimal(outinInvoiceDTO.getCurrNo()), new BigDecimal(outinInvoiceDTO.getEndNo()));
		if (flag == 1) {
			throw new AppException("当前发票号码不能大于发票截止号码");
		}
		outinInvoiceDao.updateOutinInvoice(outinInvoiceDTO);
		return true;
	}

	@Override
	public Boolean insertOutinInvoiceDetail(OutinInvoiceDetailDO outinInvoiceDetailDO) {
		List<OutinInvoiceDetailDO> list = new ArrayList<>();
		list.add(outinInvoiceDetailDO);
		outinInvoiceDao.insertOutinInvoiceDetailBatch(list);
		return true;
	}

	/**
	 * @Menthod: updateInvoiceReceive
	 * @Desrciption: 发票领用
	 * <p>业务逻辑
	 * 1.检查起始号码是否大于结束号码
	 * 2.检查发票号是否已使用
	 * 3.发票主表新增一条记录（outin_invoice）
	 * </p>
	 * @Param: [invoiceDTO]
	 * @Author: lioajiguang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date: 2020/8/24 17:42
	 * @Return: Boolean
	 **/
	@Override
	public Boolean updateInvoiceReceive(OutinInvoiceDTO outinInvoiceDTO) {
		// 检查起始号码是否大于结束号码
		if (this.compareNum(outinInvoiceDTO.getStartNo(), outinInvoiceDTO.getEndNo())) {
			throw new AppException("起始号码不能大于结束号码");
		}

		// 检查发票号是否已使用
//		if (outinInvoiceDao.checkInvoice(outinInvoiceDTO) > 0) {
//			throw new AppException("发票号码已经被使用");
//		}
		// 2检查发票号是否已使用，需要重新计算
		// 2.1 直接查询发票主表，如果主表存在此号段发票，那么久需要查询详细表，如果不存在则直接使用
		if (outinInvoiceDao.checkOutinInvoiceExistence(outinInvoiceDTO) > 0) {
			throw new AppException("发票号码已经被使用");
		}

		// 发票主表数据主键
		outinInvoiceDTO.setId(SnowflakeUtils.getId());

		// 当前号码默认发票开始号码
		outinInvoiceDTO.setCurrNo(outinInvoiceDTO.getStartNo());

		// 发票主表使用状态代码
		outinInvoiceDTO.setStatusCode(Constants.PJSYZT.DY);
		outinInvoiceDTO.setCrteTime(DateUtils.getNow());
		outinInvoiceDTO.setReceiveTime(DateUtils.getNow());

		// 获取用户名称
		Map map = new HashMap();
		map.put("hospCode", outinInvoiceDTO.getHospCode());
		map.put("id", outinInvoiceDTO.getReceiveId());
		Map<String, Object> receiveMap = outinInvoiceDao.getUserNameByUserId(map);
		outinInvoiceDTO.setReceiveName(String.valueOf(receiveMap.get("name")));

		map.put("id", outinInvoiceDTO.getUseId());
		Map<String, Object> useMap = outinInvoiceDao.getUserNameByUserId(map);
		outinInvoiceDTO.setUseName(String.valueOf(useMap.get("name")));

		outinInvoiceDTO.setNum(this.getNum(outinInvoiceDTO.getStartNo(), outinInvoiceDTO.getEndNo()) + 1);
		return outinInvoiceDao.insertOutinInvoice(outinInvoiceDTO) > 0;
	}

	/**
	 * @Menthod: updateInvoiceOutReceive
	 * @Desrciption: 发票退领 - 发票主表使用状态代码：1、待用，2、在用，3、退领，4、作废
	 * <p>业务逻辑
	 * 1：检查开始号码是否大于结束号码
	 * 2: 判断退领发票是否用完或者退领
	 * 3: 判断退领开始号码是否小于当前号码
	 * 4: 判断退领结束号码是否大于截止号码
	 * 5: 批量插入状态为退领的退领号码段到明细表（outin_invoice_detail）
	 * 6: 更新发票主表信息（outin_invoice）
	 * </p>
	 * @Param: [invoiceDTO]
	 * @Author: lioajiguang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date: 2020/8/24 17:42
	 * @Return: Boolean
	 **/
	@Override
	public Boolean updateInvoiceOutReceive(OutinInvoiceDTO outinInvoiceDTO, String createId, String createName) {
		// 根据发票主表主键 + 医院编码获取发票记录
		OutinInvoiceDO outinInvoiceDO = outinInvoiceDao.getOutinInvoiceById(outinInvoiceDTO);
		if (outinInvoiceDO == null) {
			throw new AppException("未找到发票领用信息");
		}

		// 检查起始号码是否大于结束号码
		if (this.compareNum(outinInvoiceDTO.getInvalidStartNo(), outinInvoiceDTO.getInvalidEndNo())) {
			throw new AppException("起始号码不能大于结束号码");
		}

		// 判断退领发票是否用完
		if (outinInvoiceDao.checkInvoiceStatus(outinInvoiceDTO) > 0) {
			throw new AppException("发票已用完或退领或作废，不能退领");
		}

		// 判断退领开始号码是否等于当前号码
		if (outinInvoiceDao.checkInvoiceStartNum(outinInvoiceDTO) > 0) {
			throw new AppException("发票退领开始号码不等于当前号码");
		}

		// 判断退领结束号码是否大于截止号码
		if (outinInvoiceDao.checkInvoiceEndNum(outinInvoiceDTO) > 0) {
			throw new AppException("发票退领结束号码不能大于截止号码");
		}

		// 新增退领号码段到发票明细表
		String invoiceDetailStatusCode = Constants.PJMXSYZT.TL;
		// List<OutinInvoiceDetailDO> insertList = this.getInvoiceDetailInfo(outinInvoiceDTO, invoiceDetailStatusCode);
		// outinInvoiceDao.insertOutinInvoiceDetailBatch(insertList);

		// 更新发票主表信息
		Integer num = this.getNum(outinInvoiceDTO.getInvalidEndNo(), outinInvoiceDO.getEndNo());

		// 全部退领
		if (num == 0) {
			outinInvoiceDTO.setStatusCode(Constants.PJSYZT.TL);
			outinInvoiceDTO.setCurrNo("");
		} else { // 部分退领
			int endNo = Integer.valueOf(outinInvoiceDTO.getInvalidEndNo());
			String endNostr = String.valueOf(endNo + 1);
			outinInvoiceDTO.setCurrNo(endNostr);
		}
		outinInvoiceDTO.setNum(num);
		return outinInvoiceDao.updateOutinInvoice(outinInvoiceDTO) > 0;
	}

	/**
	 * @Menthod: updateInvoiceInvalid
	 * @Desrciption: 发票作废
	 * <p> 业务逻辑
	 * 1: 开始号码是否大于结束号码
	 * 2: 判断作废发票是否用完
	 * 3: 判断作废号码是否使用
	 * 4: 判断作废开始号码是否等于当前号码
	 * 5: 判断作废结束号码是否大于截止号码
	 * 6: 插入作废号码段到明细表
	 * 7: 判断作废结束号码是否等于截止号码。等于回写发票主表使用标识
	 * </p>
	 * @Param: [invoiceDTO]
	 * @Author: lioajiguang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date: 2020/8/24 17:42
	 * @Return: Boolean
	 **/
	@Override
	public Boolean updateInvoiceInvalid(OutinInvoiceDTO outinInvoiceDTO) {

		// 根据发票主表主键 + 医院编码获取发票记录
		OutinInvoiceDO outinInvoiceDO = outinInvoiceDao.getOutinInvoiceById(outinInvoiceDTO);
		if (outinInvoiceDO == null) {
			throw new AppException("未找到发票领用信息");
		}

		if (this.compareNum(outinInvoiceDTO.getInvalidStartNo(), outinInvoiceDTO.getInvalidEndNo())) {
			throw new AppException("起始号码不能大于结束号码");
		}

		if (this.checkNum(outinInvoiceDTO.getInvalidStartNo(), outinInvoiceDTO.getInvalidEndNo())) {
			throw new AppException("一次作废不能大于50张发票");
		}

		// 判断作废发票是否用完
		if (outinInvoiceDao.checkInvoiceStatus(outinInvoiceDTO) > 0) {
			throw new AppException("发票已用完或退领或作废，不能作废");
		}

		// 当前作废号码段是否存在已使用的号码
        /*if (outinInvoiceDao.checkInvoiceNumSegment(outinInvoiceDTO) > 0) {
            throw new AppException ("当前作废号码段有号码已使用，不能作废");
        }*/

		// 判断作废开始号码是否等于当前号码
		if (outinInvoiceDao.checkInvoiceStartNum(outinInvoiceDTO) > 0) {
			throw new AppException("发票作废开始号码不等于当前号码");
		}

		// 判断退领结束号码是否大于截止号码
		if (outinInvoiceDao.checkInvoiceEndNum(outinInvoiceDTO) > 0) {
			throw new AppException("发票作废结束号码不能大于截止号码");
		}

		// 新增作废号码段到发票明细表
		String invoiceDetailStatusCode = Constants.PJMXSYZT.ZF;
		List<OutinInvoiceDetailDO> insertList = this.getInvoiceDetailInfo(outinInvoiceDTO, invoiceDetailStatusCode);
		outinInvoiceDao.insertOutinInvoiceDetailBatch(insertList);

		Integer num = this.getNum(outinInvoiceDTO.getInvalidEndNo(), outinInvoiceDO.getEndNo());

		// 全部作废
		if (num == 0) {
			outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZF);
			outinInvoiceDTO.setCurrNo("");
		} else { // 部分作废
			int endNo = Integer.valueOf(outinInvoiceDTO.getInvalidEndNo());
			String endNostr = String.valueOf(endNo + 1);
			outinInvoiceDTO.setCurrNo(endNostr);
		}
		outinInvoiceDTO.setNum(num);
		return outinInvoiceDao.updateOutinInvoice(outinInvoiceDTO) > 0;

	}

	/**
	 * @Menthod: getOutinInvoiceById
	 * @Desrciption: 根据主键获取发票领用信息
	 * @Param: [outinInvoiceDTO]
	 * @Author: lioajiguang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date: 2020/8/26 10:00
	 * @Return: 实体对象
	 **/
	@Override
	public OutinInvoiceDO getOutinInvoiceById(OutinInvoiceDTO outinInvoiceDTO) {
		return outinInvoiceDao.getOutinInvoiceById(outinInvoiceDTO);
	}

	/**
	 * @Menthod: queryPatientInvoicePage
	 * @Desrciption: 根据条件获取发票重打补打的页面数据
	 * @Param: [map]
	 * @Author: liaojiguang
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date: 2020/8/26 10:00
	 * @Return: PageDTO集合对象
	 **/
	@Override
	public PageDTO queryPatientInvoicePage(OutinInvoiceDTO outinInvoiceDTO) {
		// 设置分页信息
		PageHelper.startPage(outinInvoiceDTO.getPageNo(), outinInvoiceDTO.getPageSize());

		// 根据条件查询所有数据
		List<OutinInvoiceDTO> selectList = new ArrayList<>();
		if (outinInvoiceDTO.getSystemCode().equals("RCYZTT")) {
			selectList = outinInvoiceDao.queryZYInvoicePage(outinInvoiceDTO);
		} else if (outinInvoiceDTO.getSystemCode().equals("MZSFZXT")) {
			selectList = outinInvoiceDao.queryMZInvoicePage(outinInvoiceDTO);
		} else {
			throw new AppException("当前功能只能在【门诊收费子系统】、【住院入出子系统】 中使用，请联系管理员配置");
		}
		return PageDTO.of(selectList);
	}

	/**
	 * @Menthod updateInvoicePrint
	 * @Desrciption 发票重打补打
	 * <p>
	 * 补打：发票号不变再次打印
	 * 重打：现作废当前发票，获取新的发票，再次打印
	 * </p>
	 * @Param [map] 参数
	 * @Author liaojiguang
	 * @Date 2020/08/26 10:00
	 * @Return Boolean
	 **/
	@Override
	public Boolean updateInvoicePrint(OutinInvoiceDTO outinInvoiceDTO) {
		String printType = outinInvoiceDTO.getPrintType();
		String invoiceType = outinInvoiceDTO.getInvoiceType();
		// printType: 1补打；2重打 3作废（自定义）
		if ("1".equals(printType)) { // 补打
			if (StringUtils.isEmpty(outinInvoiceDTO.getInvoiceId())) {
				throw new AppException("未获取到发票信息,只能重打");
			}

			OutinInvoiceDO selectEntity = outinInvoiceDao.getOutinInvoiceById(outinInvoiceDTO);
			if (selectEntity == null) {
				throw new AppException("未获取到此条发票号段");
			}

			Map<String, Object> map = new HashMap<>();
			map.put("invoiceId", outinInvoiceDTO.getInvoiceId());
			map.put("invoiceNo", outinInvoiceDTO.getInvoiceNo());
			map.put("hospCode", outinInvoiceDTO.getHospCode());
			OutinInvoiceDetailDO outinInvoiceDetailDO = outinInvoiceDao.getInvocieDetailInfo(map);
			if (outinInvoiceDetailDO == null) {
				throw new AppException("未找到发票详情信息");
			}

			// 发票是否已经作废
			if (Constants.PJMXSYZT.ZF.equals(outinInvoiceDetailDO.getStatusCode())) {
				throw new AppException("发票已经作废，只能重打");
			}
		} else if ("2".equals(printType)) { // 重打
			OutinInvoiceDO selectEntity = outinInvoiceDao.getOutinInvoiceById(outinInvoiceDTO);

			// 获取当前登录人的最新的发票号段
			int num = 0;
			if (selectEntity == null || selectEntity.getNum() == 0) {
				// 查询是否有新的在用发票
				OutinInvoiceDTO selectDto = new OutinInvoiceDTO();
				selectDto.setHospCode(outinInvoiceDTO.getHospCode());
				selectDto.setUseId(outinInvoiceDTO.getUseId());
				selectDto.setReceiveId(outinInvoiceDTO.getReceiveId());
				selectDto.setStatusCode(Constants.PJSYZT.ZY);

				List<String> strList = new ArrayList();
				if (Constants.PJLX.GH.equals(invoiceType)) {
					strList.add(Constants.PJLX.TY);
					strList.add(Constants.PJLX.GH);
					strList.add(Constants.PJLX.MZTY);
				} else if (Constants.PJLX.MZ.equals(invoiceType)) {
					strList.add(Constants.PJLX.TY);
					strList.add(Constants.PJLX.MZ);
					strList.add(Constants.PJLX.MZTY);
				} else if (Constants.PJLX.ZY.equals(invoiceType)) {
					strList.add(Constants.PJLX.TY);
					strList.add(Constants.PJLX.ZY);
				}
				selectDto.setTypeCodeList(strList);

				Map<String, Object> map = new HashMap<>();
				map.put("code", "FPHB_FS");
				map.put("hospCode", outinInvoiceDTO.getHospCode());
				WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(map);

				selectDto.setType(wr.getData().getValue());
				List list = outinInvoiceDao.getInvoiceList(selectDto);
				if (ListUtils.isEmpty(list)) {
					throw new AppException("当前类型发票已用完，请重新领用发票");
				}

				if (list.size() > 1) {
					throw new AppException("出现多个在用号段的发票，请联系管理员");
				}
				selectEntity = (OutinInvoiceDO) list.get(0);

				if (selectEntity.getNum() == 0) {
					throw new AppException("出现数量为0的在用发票，请联系管理员");
				}
			}

			num = selectEntity.getNum();
			String currNoStr = selectEntity.getCurrNo();

			// 获取新发票号
			int currNo = Integer.valueOf(currNoStr) + 1;

			// 剩余数量
			num = num - 1;
			if (num == 0) {// 用完
				selectEntity.setCurrNo("");
				selectEntity.setStatusCode(Constants.PJSYZT.YW);
			} else {// 未用完
				String currentNo = changeInvoiceNo(currNoStr,String.valueOf(currNo));
				selectEntity.setCurrNo(currentNo);
			}
			selectEntity.setNum(num);
			outinInvoiceDao.updateOutinInvoice(selectEntity);

			OutinInvoiceDetailDO invoiceDetailDO = new OutinInvoiceDetailDO();
			invoiceDetailDO.setId(SnowflakeUtils.getId());
			invoiceDetailDO.setHospCode(selectEntity.getHospCode());
			invoiceDetailDO.setInvoiceId(selectEntity.getId());
			invoiceDetailDO.setInvoiceNo(currNoStr);
			invoiceDetailDO.setUseTime(DateUtils.getNow());
			invoiceDetailDO.setSettleId(outinInvoiceDTO.getSettleId());
			invoiceDetailDO.setStatusCode(Constants.ZTBZ.ZC);
			outinInvoiceDao.insertOutinInvoiceDetail(invoiceDetailDO);

			//将新打的发票信息，更新至结算表
			selectEntity.setTypeCode(outinInvoiceDTO.getTypeCode());
			OutinInvoiceDTO updateEntity = new OutinInvoiceDTO();
			BeanUtils.copyProperties(selectEntity, updateEntity);
			updateEntity.setVisitId(outinInvoiceDTO.getVisitId());
			updateEntity.setInvoiceId(updateEntity.getId());
			updateEntity.setInvoiceNo(currNoStr);
			updateEntity.setSettleId(outinInvoiceDTO.getSettleId());
			updateEntity.setInvoiceDetailId(invoiceDetailDO.getId());
			if (Constants.PJLX.GH.equals(invoiceType)) { // 挂号使用
				outinInvoiceDao.updateRegisterSettleInvoice(updateEntity);
			} else if (Constants.PJLX.MZ.equals(invoiceType)) { // 门诊使用票
				OutptSettleInvoiceDTO outptSettleInvoiceDTO = new OutptSettleInvoiceDTO();
				outptSettleInvoiceDTO.setId(outinInvoiceDTO.getOutptSettleInvoiceId());
				outptSettleInvoiceDTO.setHospCode(outinInvoiceDTO.getHospCode());
				OutptSettleInvoiceDO outptSettleInvoiceDO = outinInvoiceDao.getOutptSettleInvoiceById(outptSettleInvoiceDTO);
				if (outptSettleInvoiceDO != null) {
					updateEntity.setOutptSettleInvoiceId(outptSettleInvoiceDO.getId());
					outinInvoiceDao.updateSettleInvoice(updateEntity);
				} else {
					OutptSettleInvoiceDO entity = new OutptSettleInvoiceDO();
					entity.setHospCode(updateEntity.getHospCode());
					entity.setId(SnowflakeUtils.getId());
					entity.setInvoiceDetailId(updateEntity.getInvoiceDetailId());
					entity.setInvoiceId(updateEntity.getInvoiceId());
					entity.setInvoiceNo(updateEntity.getInvoiceNo());
					entity.setSettleId(updateEntity.getSettleId());
					entity.setStatusCode(Constants.ZTBZ.ZC);
					entity.setVisitId(updateEntity.getVisitId());
					entity.setTotalPrice(outinInvoiceDTO.getSettleTotalPrice());
					outinInvoiceDao.insertOutptSettleInvoice(entity);
				}
			} else if (Constants.PJLX.ZY.equals(invoiceType)) { // 住院使用
				InptSettleInvoiceDO inptSettleInvoiceDO = outinInvoiceDao.getSettleInvoiceById(updateEntity);
				if (inptSettleInvoiceDO != null) {
					outinInvoiceDao.updateInptSettleInvoice(updateEntity);
				} else {
					InptSettleInvoiceDO entity = new InptSettleInvoiceDO();
					entity.setHospCode(updateEntity.getHospCode());
					entity.setId(SnowflakeUtils.getId());
					entity.setInvoiceDetailId(updateEntity.getInvoiceDetailId());
					entity.setInvoiceId(updateEntity.getInvoiceId());
					entity.setInvoiceNo(updateEntity.getInvoiceNo());
					entity.setSettleId(updateEntity.getSettleId());
					entity.setStatusCode(Constants.ZTBZ.ZC);
					entity.setVisitId(updateEntity.getVisitId());
					entity.setTotalPrice(outinInvoiceDTO.getSettleTotalPrice());
					outinInvoiceDao.insertInptSettleInvoice(entity);
				}
			}

			// 用新发票号参数调用收费完成后打印接口即可
		} else if ("3".equals(printType)) { // 作废
			Map<String, Object> map = new HashMap<>();
			map.put("invoiceId", outinInvoiceDTO.getInvoiceId());
			map.put("invoiceNo", outinInvoiceDTO.getInvoiceNo());
			map.put("hospCode", outinInvoiceDTO.getHospCode());
			map.put("statusCode", Constants.PJMXSYZT.ZF);
			outinInvoiceDao.updateOutinInvoiceDetail(map);

			// 清除 outpt_settle_invoice 发票信息
			OutptSettleInvoiceDTO outptSettleInvoiceDTO = new OutptSettleInvoiceDTO();
			outptSettleInvoiceDTO.setId(outinInvoiceDTO.getOutptSettleInvoiceId());
			outptSettleInvoiceDTO.setHospCode(outinInvoiceDTO.getHospCode());
			outinInvoiceDao.updateOutptSettleInvoiceInfo(outptSettleInvoiceDTO);

			// 更新主状态
		} else {
			throw new AppException("不存在此业务");
		}
		return true;
	}

	/**
	 * @Menthod getInvoiceDetailPage()
	 * @Desrciption 根据条件分页查询发票领用详细信息
	 * @Param 发票ID, 医院编码
	 * @Param
	 * @Author liaojiguang
	 * @Date 2020/09/02 10:04
	 * @Return PageDTO
	 **/
	@Override
	public PageDTO queryInvoiceDetailPage(OutinInvoiceDetailDO outinInvoiceDetailDO) {
		// 设置分页信息
		PageHelper.startPage(outinInvoiceDetailDO.getPageNo(), outinInvoiceDetailDO.getPageSize());

		// 根据条件查询所有数据
		List<OutinInvoiceDetailDO> outinInvoiceDetailDOList = outinInvoiceDao.queryInvoiceDetailPage(outinInvoiceDetailDO);
		return PageDTO.of(outinInvoiceDetailDOList);
	}


	/**
	 * @Menthod queryItemInfoDetails()
	 * @Desrciption 获取费用明细信息
	 * @Param outinInvoiceDTO
	 * @Author liaojiguang
	 * @Date 2020/09/04 10:04
	 * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
	 **/
	@Override
	public List<Map<String, Object>> queryItemInfoDetails(OutinInvoiceDTO outinInvoiceDTO) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String,Object> maxTime = new HashMap<>();
		if (Constants.PJLX.GH.equals(outinInvoiceDTO.getInvoiceType())) {// 挂号
			resultList = outinInvoiceDao.queryRegiestItemInfoDetail(outinInvoiceDTO);
		} else if (Constants.PJLX.MZ.equals(outinInvoiceDTO.getInvoiceType())) { // 门诊
			resultList = outinInvoiceDao.queryOutptItemInfoDetail(outinInvoiceDTO);
			maxTime = outinInvoiceDao.queryOutptItemInfoDetailDate(outinInvoiceDTO);
		} else if (Constants.PJLX.ZY.equals(outinInvoiceDTO.getInvoiceType())) { // 住院
			resultList = outinInvoiceDao.queryOutinItemInfoDetail(outinInvoiceDTO);
		}
		// 获取医院医保编码
		Map<String, Object> hMap = new HashMap<>();
		hMap.put("hospCode", outinInvoiceDTO.getHospCode());
		hMap.put("code", "HOSP_INSURE_CODE");
		SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(hMap).getData();
		if (sysParameterDTO != null ) {
			if(!ListUtils.isEmpty(resultList)){
				for(Map<String, Object> map :resultList){
					map.put("orgCode",sysParameterDTO.getValue());
					map.putAll(maxTime);
				}
			}
		}
		return resultList;
	}

	/**
	 * @Menthod queryChangeDetails()
	 * @Desrciption 获取门诊费用明细信息
	 * @Param outinInvoiceDTO
	 * @Author caoliang
	 * @Date 2021/05/20 10:52
	 * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
	 *
	 * @return*/
	@Override
	public PageDTO queryChangeDetails(OutinInvoiceDTO outinInvoiceDTO) {
		// 设置分页信息
		PageHelper.startPage(outinInvoiceDTO.getPageNo(), outinInvoiceDTO.getPageSize());

		List<Map<String, Object>> resultList =  outinInvoiceDao.queryChangeDetails(outinInvoiceDTO);
		return PageDTO.of(resultList);
	}

	/**
	 * @Menthod queryItemInfoByParams()
	 * @Desrciption 获取费用明细信息
	 * @Param outinInvoiceDTO
	 * @Author liaojiguang
	 * @Date 2020/09/04 10:04
	 * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
	 **/
	@Override
	public List<Map<String, Object>> queryItemInfoByParams(OutinInvoiceDTO outinInvoiceDTO) {
		List<Map<String, Object>> resultList = new ArrayList<>();

		SysParameterDO sys = getSysParameter(outinInvoiceDTO.getHospCode(), "MZFP_FDFS");
		// SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();

		// 挂号发票
		if (Constants.PJLX.GH.equals(outinInvoiceDTO.getInvoiceType())) {
			resultList = outinInvoiceDao.queryRegiestItemInfoByParams(outinInvoiceDTO);
		} else if (Constants.PJLX.MZ.equals(outinInvoiceDTO.getInvoiceType())) { // 门诊发票
			// 默认不分单，查询费用表
			if (sys == null || sys.getValue() == null || "1".equals(sys.getValue())) {
				resultList = outinInvoiceDao.queryOutptItemInfoByParams(outinInvoiceDTO);
			}
			// 发票分单，查询发票财务分类表
			if (sys != null && sys.getValue() != null && "2".equals(sys.getValue())) {
				resultList = outinInvoiceDao.queryOutptsettleInvoiceConent(outinInvoiceDTO);
			}
			//
			if (ListUtils.isEmpty(resultList)) {
				resultList = outinInvoiceDao.queryOutptItemInfoByParams(outinInvoiceDTO);
			}
		} else if (Constants.PJLX.ZY.equals(outinInvoiceDTO.getInvoiceType())) { // 住院发票
			resultList = outinInvoiceDao.queryOutinItemInfoByParams(outinInvoiceDTO);
		}
		return resultList;
	}

	/**
	 * @param outinInvoiceDTO 查询条件
	 * @Menthod updateForOutinInvoiceQuery
	 * @Desrciption 查询发票段信息
	 * @Author Ou·Mr
	 * @Date 2020/10/10 11:34
	 * @Return java.util.List<cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO>
	 */
	@Override
	public List<OutinInvoiceDTO> updateForOutinInvoiceQuery(OutinInvoiceDTO outinInvoiceDTO) {
		return outinInvoiceDao.queryOutinInvoiceForUpdate(outinInvoiceDTO);
	}

	/**
	 * @Menthod updatePrintChecklist()
	 * @Desrciption 打印清单
	 * @Param [outinInvoiceDTO]
	 * @Author liaojiguang
	 * @Date 2020/08/26 10:00
	 * @Return Boolean
	 **/
	@Override
	public Boolean updatePrintChecklist(OutinInvoiceDTO outinInvoiceDTO) {
		Boolean flag = false;
		if ("1".equals(outinInvoiceDTO.getTypeCode())) {
			flag = outinInvoiceDao.updatePrintChecklist(outinInvoiceDTO) > 0;
		}
		return flag;
	}

	/**
	 * @Menthod queryInactiveOutinInvoices()
	 * @Desrciption 获取待用发票
	 * @Param [outinInvoiceDTO]
	 * @Author liaojiguang
	 * @Date 2020/08/26 10:00
	 * @Return java.util.List
	 **/
	@Override
	public List<OutinInvoiceDTO> queryInactiveOutinInvoices(OutinInvoiceDTO outinInvoiceDTO) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "FPHB_FS");
		map.put("hospCode", outinInvoiceDTO.getHospCode());
		WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(map);
		outinInvoiceDTO.setType(wr.getData().getValue());
		// 查询系统参数，使用发票时是否需要校验使用人为自己
		Map<String, Object> xtcsMap = new HashMap<>();
		xtcsMap.put("hospCode", outinInvoiceDTO.getHospCode());
		xtcsMap.put("code", "USE_INVOICE_SEIF");

		SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(xtcsMap).getData();
		if (sys != null && StringUtils.isNotEmpty(sys.getValue()) && "2".equals(sys.getValue())) {
			outinInvoiceDTO.setUseId(null); // 如果可以使用所有人的发票，那么将发票使用人置空
			outinInvoiceDTO.setType(null);
		}
		return outinInvoiceDao.queryInactiveOutinInvoices(outinInvoiceDTO);
	}

	/**
	 * @Menthod getInvoiceDetailByInvoiceInfo()
	 * @Desrciption 发票id + 发票号码 获取发票详情信息
	 * @Param [outinInvoiceDTO]
	 * @Author liaojiguang
	 * @Date 2020/12/28 10:00
	 * @Return java.util.List
	 **/
	@Override
	public OutinInvoiceDetailDO getInvoiceDetailByInvoiceInfo(Map<String, Object> selectMap) {
		return outinInvoiceDao.getInvocieDetailInfo(selectMap);
	}

	/**
	 * @param outinInvoiceDetailDO 入参
	 * @Menthod updateInvoiceDetailById
	 * @Desrciption 更新发票详情
	 * @Author liaojiguang
	 * @Date 2020/12/28 10:51
	 * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
	 **/
	@Override
	public Boolean updateInvoiceDetailById(OutinInvoiceDetailDO outinInvoiceDetailDO) {
		return outinInvoiceDao.updateInvoiceDetailById(outinInvoiceDetailDO);
	}

	/**
	 * @param map [crteId] 当前日结缴款人 [hospCode] 医院编码
	 * @Menthod updateInvoiceUseInfo
	 * @Desrciption 清空发票号段使用人，状态改为待用状态
	 * @Author liaojiguang
	 * @Date 2020/12/28 17:28
	 * @Return java.lang.Boolean
	 **/
	@Override
	public Boolean updateInvoiceUseInfo(Map<String, Object> map) {
		return outinInvoiceDao.updateInvoiceUseInfo(map);
	}

	/**
	 * @Description: 查询费用清单，例如：材料费：200.00 西药费： 890.00 根据项目类别统计
	 * 分两种情况统计，然后一起返回给前端 1、按项目类别统计， 2、按发票归类统计
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/15 10:34
	 * @Return
	 */
	@Override
	public OutptCostDTO getCostInventory(Map<String, Object> map) {
		String inventoryType = "1";
		// 查询门诊结算费用
		List<OutptCostDTO> list = outinInvoiceDao.getCostInventory(map);  // 1、默认查询门诊的项目材料结算数据
		if (list.size() == 0) { // 2、没有查询到门诊的项目材料结算数据，则查询门诊挂号数据
			// 查询门诊挂号数据
			list = outinInvoiceDao.getMZGHCostInventory(map);
			inventoryType="0";
		}
		if (list.size() == 0) { // 3、既不是门诊挂号，也不是门诊项目材料，则认为需要查询住院数据
			// 查询住院数据
			list = outinInvoiceDao.getZyCostInventory(map);
			inventoryType="2";
		}
		OutptCostDTO dto = new OutptCostDTO();
		if (list != null && list.size() > 0) {
			dto = list.get(0);
			Map<String, Object> fylb = new HashMap<>();  // 费用类别
			Map<String, Object> fplb = new HashMap<>();  // 发票类别
			BigDecimal totalMoney = new BigDecimal(0);
			SysParameterDO sysParameterDO = getSysParameter(MapUtils.get(map, "hospCode"), Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
			// 计算项目组成中各费用类别总金额
			for (OutptCostDTO costDTO : list) {
				String bfcName = costDTO.getBfcName();
				String fpglKey = "A" + costDTO.getFpglKey(); // 为发票类别的key添加一个前缀，即发票归类的value添加前缀
				// 当前费用为单价X数量-优惠总金额
				BigDecimal xmFeiYong = costDTO.getRealityPrice(); // BigDecimalUtils.subtract(BigDecimalUtils.multiply(costDTO.getPrice(), costDTO.getTotalNum()), costDTO.getPreferentialPrice());
				totalMoney = BigDecimalUtils.add(totalMoney, xmFeiYong);
				// 项目类别
				if (fylb.containsKey(bfcName)) {
					fylb.put(bfcName, BigDecimalUtils.scale(BigDecimalUtils.add((BigDecimal) fylb.get(bfcName), xmFeiYong), 4));
				} else {
					fylb.put(bfcName, BigDecimalUtils.scale(xmFeiYong, 4));
				}
				// 发票归类类别
				if (fplb.containsKey(fpglKey)) {
					fplb.put(fpglKey, BigDecimalUtils.scale(BigDecimalUtils.add((BigDecimal) fplb.get(fpglKey), xmFeiYong), 4));
				} else {
					fplb.put(fpglKey, BigDecimalUtils.scale(xmFeiYong, 4));
				}
			}
			for (Map.Entry<String, Object> ent : fplb.entrySet()) {
				BigDecimal fplbfyje = (BigDecimal) ent.getValue();
				fplb.put(ent.getKey(), fplbfyje.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			dto.setFpglDataMap(fplb);
			// 将项目类别费用合计由map转为list
			List<OutptCostDTO.Xmlbfy> fylbList = new ArrayList<>();
			Map<String, Object> zuoBiaoCostData = new HashMap<>(); // 计费类别code的数据源(项目类别)
			for (Map.Entry<String, Object> entry : fylb.entrySet()) {
				OutptCostDTO.Xmlbfy xmlbfy = dto.new Xmlbfy();
				xmlbfy.setXmlb(entry.getKey());
				// 费用金额 = 费用金额-舌入金额
				// BigDecimal xmlbfyje = BigDecimalUtils.subtract((BigDecimal) entry.getValue(), BigDecimalUtils.rounding(sysParameterDO.getValue(), (BigDecimal) entry.getValue()));
				BigDecimal xmlbfyje = (BigDecimal) entry.getValue();
				xmlbfy.setXmlbfyhj(xmlbfyje.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				//xmlbfy.setXmlbfyhj(((BigDecimal) entry.getValue()).toString());
				fylbList.add(xmlbfy);
				// 转换key为code的map
				for(OutptCostDTO costdto : list) {
					if (entry.getKey().equals(costdto.getBfcName())) {
						zuoBiaoCostData.put(costdto.getBfcCode(), xmlbfy.getXmlbfyhj());
					}
				}
			}
			dto.setFylbList(fylbList);
			totalMoney = BigDecimalUtils.subtract(totalMoney, BigDecimalUtils.rounding(sysParameterDO.getValue(), totalMoney)); // 总金额 = 总金额-舌入金额
			//totalMoney = BigDecimalUtils.scale(totalMoney, 2);  // 2021年6月21日11:11:20 处理4位小数转2位小数报错问题
			totalMoney = totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
			dto.setTotalFy(totalMoney.toString());
			dto.setDxTotalFy(MoneyUtils.convert(totalMoney.doubleValue()));
			dto.setZuoBiaoCostData(zuoBiaoCostData);
			dto.setInventoryType(inventoryType);  // 收据类型 0: 挂号；1：门诊；2：住院   2022/5/30 10:09 lly
		}

		return dto;
	}

	/**
	 * @Description: 查询处方费用明细
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/23 10:34
	 * @Return
	 */
	@Override
	public List<Map<String, Object>> queryOutptCFCostDetails(OutinInvoiceDTO outinInvoiceDTO) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (Constants.PJLX.GH.equals(outinInvoiceDTO.getInvoiceType())) {// 挂号
			resultList = outinInvoiceDao.queryRegiestItemInfoDetail(outinInvoiceDTO);
		} else if (Constants.PJLX.MZ.equals(outinInvoiceDTO.getInvoiceType())) { // 门诊
			resultList = outinInvoiceDao.queryOutptCFItemInfoDetail(outinInvoiceDTO);
			// 获取计费类别、性别、年龄收费日期等
			List<Map<String, Object>> visitList = outinInvoiceDao.queryOutptBfcInfo(outinInvoiceDTO);
			if (resultList != null && resultList.size() > 0) {
				if (visitList != null && visitList.size() > 0) {
					for (int i = 0; i < resultList.size(); i++) {
						Map<String, Object> map = resultList.get(i);
						Map<String, Object> temp = new HashMap<>();
						String opdId = MapUtils.get(map, "opdId");
						if (opdId != null && !opdId.equals("")) {
							for (int j = 0; j < visitList.size(); j++) {
								Map<String, Object> costMap = visitList.get(j);
								if (MapUtils.get(costMap, "opdId").equals(opdId)) {
									if (!temp.containsValue(MapUtils.get(costMap, "bfcName"))) {
										temp.put("j", MapUtils.get(costMap, "bfcName"));
									}
								}
							}
							map.put("bfcName", temp.values().toString());
							map.put("settleTime", MapUtils.get(visitList.get(0), "settleTime"));
							map.put("genderCode", MapUtils.get(visitList.get(0), "genderCode"));
							map.put("age", MapUtils.get(visitList.get(0), "age"));
							map.put("ageUnitCode", MapUtils.get(visitList.get(0), "ageUnitCode"));
							resultList.set(i, map);
						}
					}
				}
			}
		} else if (Constants.PJLX.ZY.equals(outinInvoiceDTO.getInvoiceType())) { // 住院
			resultList = outinInvoiceDao.queryOutinItemInfoDetail(outinInvoiceDTO);
			// resultList = outinInvoiceDao.queryInptCFBfcInfo(outinInvoiceDTO);
		}
		return resultList;
	}

	/**
	 * @Description: 编辑发票信息（使用人，发票状态）
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/31 15:58
	 * @Return
	 */
	@Override
	public Boolean updateOutinInvoice(Map map) {
		OutinInvoiceDTO outinInvoiceDTO = MapUtils.get(map, "outinInvoiceDTO");
		OutinInvoiceDTO dto = new OutinInvoiceDTO();
		dto.setId(outinInvoiceDTO.getId());
		dto.setHospCode(outinInvoiceDTO.getHospCode());
		dto.setInvoiceCode(outinInvoiceDTO.getInvoiceCode());
		dto.setUseId(outinInvoiceDTO.getUseId());
		dto.setUseName(outinInvoiceDTO.getUseName());
		dto.setStatusCode(outinInvoiceDTO.getStatusCode());
		dto.setTypeCode(outinInvoiceDTO.getTypeCode());
		// 校验同一个使用人最多只能有一个在用状态发票
		int kyfp = outinInvoiceDao.selectKYFPs(dto);
		if (kyfp > 0 && "2".equals(outinInvoiceDTO.getStatusCode())) {
			throw new RuntimeException("使用人" + outinInvoiceDTO.getUseName() + "已经有一个号段再用，同一使用人不能同时启用两个号段");
		}
		return outinInvoiceDao.updateOutinInvoice(dto) > 0;
	}

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
	 * @param startNum 开始号码
	 * @param endNum   结束号码
	 * @return boolean
	 * @Description:判断号码大小
	 * @Author: liaojiguang
	 * @Date: 2020/8/24 9:54
	 **/
	private boolean compareNum(String startNum, String endNum) {
		try {
			// 开始号码转数字
			int startNumInt = Integer.parseInt(startNum);

			// 结束号码转数字
			int endNumInt = Integer.parseInt(endNum);

			return startNumInt > endNumInt;
		} catch (Exception e) {
			throw new RuntimeException("号码比较错误，检查是否为数字");
		}
	}

	/**
	 * @Description: 检查作废数量
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/12/8 21:41
	 * @Return
	 */
	private boolean checkNum(String startNum, String endNum) {
		try {
			// 开始号码转数字
			int startNumInt = Integer.parseInt(startNum);

			// 结束号码转数字
			int endNumInt = Integer.parseInt(endNum);

			return (endNumInt - startNumInt) > 50;
		} catch (Exception e) {
			throw new RuntimeException("号码比较错误，检查是否为数字");
		}
	}


	/**
	 * @param outinInvoiceDTO
	 * @param invoiceDetailStatusCode
	 * @return 对象集合
	 * @Description:组装发票明细信息数据
	 * @Author: liaojiguang
	 * @Date: 2020/8/25 09:24
	 * @Email: jiguang.liao@powersi.com.cn
	 */
	private List<OutinInvoiceDetailDO> getInvoiceDetailInfo(OutinInvoiceDTO outinInvoiceDTO, String invoiceDetailStatusCode) {
		List<OutinInvoiceDetailDO> list = new ArrayList<OutinInvoiceDetailDO>();
		try {
			// 开始号码
			int startNum = Integer.parseInt(outinInvoiceDTO.getInvalidStartNo());

			// 结束号码
			int endNum = Integer.parseInt(outinInvoiceDTO.getInvalidEndNo());

			for (int num = startNum; num <= endNum; num++) {
				OutinInvoiceDetailDO outinInvoiceDetailDO = new OutinInvoiceDetailDO();
				outinInvoiceDetailDO.setId(SnowflakeUtils.getId());
				outinInvoiceDetailDO.setHospCode(outinInvoiceDTO.getHospCode());
				outinInvoiceDetailDO.setInvoiceId(outinInvoiceDTO.getId());
				outinInvoiceDetailDO.setInvoiceNo(outinInvoiceDTO.getPrefix() + num);
				outinInvoiceDetailDO.setStatusCode(invoiceDetailStatusCode);
				outinInvoiceDetailDO.setUseTime(DateUtils.getNow());
				list.add(outinInvoiceDetailDO);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return list;
	}

	/**
	 * 获取剩余数量
	 *
	 * @param startNum 开始号码
	 * @param endNum   结束号码
	 * @return java.lang.Long
	 * @Description:
	 * @Author: liaojiguang
	 * @Date: 2020/8/25 11:06
	 */
	public Integer getNum(String startNum, String endNum) {
		try {
			int num;
			// 开始号码转数字
			int start = Integer.parseInt(startNum);

			// 结束号码转数字
			int end = Integer.parseInt(endNum);
			if (start == end) {
				num = Integer.valueOf(0);
			} else {
				num = Integer.valueOf((end - start));
			}
			return num;
		} catch (Exception e) {
			throw new RuntimeException("号码比较错误，检查是否为数字");
		}
	}

	@Override
	public Map<String, List<OutinPartInvoiceDO>> queryPartInvoice(OutinInvoiceDTO outinInvoiceDTO) {
		List<OutinPartInvoiceDO> invoiceDOS = outinInvoiceDao.queryPartInvoicePrint(outinInvoiceDTO);
		Map<String, List<OutinPartInvoiceDO>> collect = invoiceDOS.stream().collect(Collectors.groupingBy(OutinPartInvoiceDO::getId));
		return queryDetailInvoice(collect);
	}


	public Map queryDetailInvoice(Map<String, List<OutinPartInvoiceDO>> collect){
		List<Map> list= new ArrayList<>();
		Map mapdata = new HashMap();
		if(collect.size() > 0){
			for (String key : collect.keySet()) {
				List<OutinPartInvoiceDO> mid = Arrays.asList(new OutinPartInvoiceDO[collect.get(key).size()]);
				Collections.copy(mid,collect.get(key));
				BigDecimal sum = BigDecimal.valueOf(0);
				for (int i = 0; i < mid.size(); i++) {
					sum = BigDecimalUtils.add(sum,mid.get(i).getRealityPrice());
					if(BigDecimalUtils.isZero(BigDecimalUtils.nullToZero(mid.get(i).getRealityPrice()))){
						collect.get(key).remove(mid.get(i));
					}
				}
				Map map = new HashMap();
				map.put("data",collect.get(key));
				map.put("key",key);
				map.put("sum",sum);
				map.put("personalPrice", mid.get(0).getPersonalPrice());
				map.put("miPrice", mid.get(0).getMiPrice());
				list.add(map);
			}
		}
		mapdata.put("listData",list);
		return mapdata;
	}

}
