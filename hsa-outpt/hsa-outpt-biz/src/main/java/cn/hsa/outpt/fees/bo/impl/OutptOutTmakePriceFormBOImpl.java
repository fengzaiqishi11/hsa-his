package cn.hsa.outpt.fees.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.insure.emd.service.OutptElectronicBillService;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.insure.module.service.*;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.module.insure.outpt.service.OutptService;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.card.service.BaseCardRechargeChangeService;
import cn.hsa.module.outpt.fees.bo.OutptOutTmakePriceFormBO;
import cn.hsa.module.outpt.fees.dao.*;
import cn.hsa.module.outpt.fees.dto.*;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.fees.entity.OutptPrescribeDO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.register.dao.OutptRegisterDAO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import cn.hsa.module.payment.service.OutptPaymentService;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.fees.bo.impl
 * @Class_name: OutptOutTmakePriceFormBOImpl
 * @Describe(描述):门诊退费BOImpl层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/09/06 10:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class OutptOutTmakePriceFormBOImpl implements OutptOutTmakePriceFormBO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

    @Resource
    private OutptCostDAO outptCostDAO;

    @Resource
    private OutptVisitDAO outptVisitDAO;

    @Resource
    private OutptSettleDAO outptSettleDAO;

    @Resource
    private OutptSettleInvoiceDAO outptSettleInvoiceDAO;

    @Resource
    private OutptInsurePayDAO outptInsurePayDAO;

    @Resource
    private OutptPayDAO outptPayDAO;

    @Resource
    private OutptService outptService;

    @Resource
    private InsureIndividualSettleService insureIndividualSettleService_consumer;

	@Resource
	private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedPayOutptService insureUnifiedPayOutptService_consumer;

    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;
    @Resource
    private InsureIndividualBasicService insureIndividualBasicService_consumer;
    @Resource
    private BaseCardRechargeChangeService baseCardRechargeChangeService;
    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;
    @Resource
    private OutptRegisterDAO outptRegisterDAO;
    @Resource
    private OutptDoctorPrescribeDAO outptDoctorPrescribeDAO;

    @Resource
    private PayOnlineInfoDAO payOnlineInfoDAO;

    @Resource
    private OutptElectronicBillService outptElectronicBillService;

    @Resource
    private RedisUtils  redisUtils;

    @Resource
    private OutptPaymentService outptPaymentService_consumer;


    /**
     * @param outptVisitDTO,outptSettleDTO
     * @Menthod updateOutptOutFee
     * @Desrciption 门诊退费
     * * 2021年5月11日15:06:25 退费修改，支持部分退费自动收费，关键点：
     * 1、医保病人部分退费时，先全退，再自动生成未退部分的收费信息，再由收费员去收费
     * 2、自费病人部分退费时，只退要退的部分（暂不考虑支付方式，只需要校验输入金额与应退金额相等即可），未退部分自动收费，不再由收费员收费
     * @Author liaojiguang
     * @Date 2020/9/05 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    @Async
    public synchronized WrapperResponse updateOutptOutFee(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDTO> tkOutptPayDTOList) {
        // TODO 必要入参获取 [就诊ID：visitId , 医院编码：hospCode , 结算ID：settleId , 费用列表信息：allCostDTOList]
        Map selectMap = new HashMap<>();
        boolean isInsurePaitentPartBack = false;
        String visitId = outptSettleDTO.getVisitId();
        String hospCode = outptSettleDTO.getHospCode();
        String settleId = outptSettleDTO.getId();
        String crteId = outptVisitDTO.getCrteId();
        String hospName = outptVisitDTO.getHospName();
        String crteName = outptVisitDTO.getCrteName();
        BigDecimal settleSelfPrice = outptSettleDTO.getSelfPrice() == null? new BigDecimal(0) : outptSettleDTO.getSelfPrice();  // 已经支付的总金额
        BigDecimal settleCardPrice = outptSettleDTO.getCardPrice() == null? new BigDecimal(0) : outptSettleDTO.getCardPrice(); // 已结算记录卡支付金额
        BigDecimal settleActualPrice = outptSettleDTO.getActualPrice() == null ? new BigDecimal(0) : outptSettleDTO.getActualPrice();  // 已经支付的划价收费记录中 个人自付金额除一卡通支付后的其他方式支付总和

        selectMap.put("visitId", visitId);
        selectMap.put("hospCode", hospCode);
        selectMap.put("settleId", settleId);
        selectMap.put("oneSettleId", outptSettleDTO.getOneSettleId());

        // 门诊费用信息列表入参获取
        List<OutptCostDTO> allCostDTOList = outptVisitDTO.getOutptCostDTOList();

        // 部分退剩下要收费的项目
        List<OutptCostDTO> costDTOList = new ArrayList<>();

        // TODO 基本异常情况判断 - 获取原始结算数据
        OutptSettleDTO oldOutptSettleDTO = outptSettleDAO.selectByPrimaryKey(settleId);
        // 获取要退费的病人类型
        if (StringUtils.isEmpty(oldOutptSettleDTO.getPatientCode())){
            oldOutptSettleDTO.setPatientCode("0");
        }
        Integer patientCode = Integer.parseInt(oldOutptSettleDTO.getPatientCode());
        if (oldOutptSettleDTO == null) {
            throw new AppException("退费失败：未找到结算信息");
        }

        // 2021-9-10 14:21:58  官红强  门诊退费时，如果退了医技，需要将医技申请表数据删除  start===============================
        List<OutptCostDTO> deleteMedicApplyList = new ArrayList<>();
        // 2021-9-10 14:21:58  官红强  门诊退费时，如果退了医技，需要将医技申请表数据删除  end  ===============================

        // TODO 回退数量异常判断 - 回退总数量
        BigDecimal backNumSum = BigDecimal.ZERO;
        BigDecimal moneyAgain = new BigDecimal(0);
        for (OutptCostDTO outptCostDTO : allCostDTOList) {
            // 应收数量 = 收费数量 - 回退数量
            BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(), outptCostDTO.getBackNum());
            int compNum = BigDecimalUtils.compareTo(lastNum, BigDecimal.ZERO);
            if (compNum == -1) {
                throw new AppException(outptCostDTO.getItemName() + "退费数量不能大于已收费数量");
            }

            // 部分退: 获取未退项目
            if (compNum == 1) {
                costDTOList.add(this.getLastFeeList(outptCostDTO, lastNum,crteId,crteName,patientCode));
                BigDecimal rePrice = BigDecimalUtils.divide(outptCostDTO.getRealityPrice(), outptCostDTO.getTotalNum());
                moneyAgain = BigDecimalUtils.add(moneyAgain, BigDecimalUtils.multiply(rePrice, lastNum));
            } else { // 全退的记录下来，关于医技的需要更新状态（已退费）
                deleteMedicApplyList.add(outptCostDTO);
            }
            backNumSum = BigDecimalUtils.add(backNumSum,outptCostDTO.getBackNum());
        }
        if (BigDecimalUtils.compareTo(backNumSum, BigDecimal.ZERO) <= 0) {
            throw new AppException("退费数量不能全为零");
        }
        // 退费的项目需要更新医技申请状态
        if (!ListUtils.isEmpty(deleteMedicApplyList)) {
            outptCostDAO.updateMedicApply(visitId, hospCode, "05", deleteMedicApplyList);
        }

        // TODO costDTOList 为空表示全退，否则表示部分退(否:false，是:true)
        Boolean isAllOut = false;
        if (ListUtils.isEmpty(costDTOList)) {
            isAllOut = true;
        }

        // TODO 根据结算ID + 就诊ID + 医院编码（selectMap）获取门诊领药申请信息（phar_out_receive）
        List<PharOutReceiveDTO> pharOutReceiveDTOList = outptCostDAO.getPahrOutReceiceInfoBySelectMap(selectMap);
        if (!ListUtils.isEmpty(pharOutReceiveDTOList)) {
            for (PharOutReceiveDTO pharOutReceiveDTO : pharOutReceiveDTOList) {
                if (Constants.FYZT.PY.equals(pharOutReceiveDTO.getStatusCode())) {
                    throw new AppException("退费失败：已经完成配药，请先去药房取消配药");
                }
                // 1.处理门诊领药申请信息
                this.updateNoSendPharOutReceiveInfo(pharOutReceiveDTO);
            }
        }

        // TODO 是否已发药 - 能查询到发药记录说明存在发药记录,已完成退药的项目可退费
        List<PharOutDistributeDTO> distributeList = outptCostDAO.queryPharOutDistribute(selectMap);

        // 已发药项目集合
        List<String> itemIdList = new ArrayList<>();
        if (!ListUtils.isEmpty(distributeList)) {
          // 查询发药批次明细汇总表
          List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOS = outptCostDAO.queryPharOutDistributeDetailIByList(distributeList);
          // 3.处理门诊发药信息
            this.updateSendPharOutDistributeInfo(outptVisitDTO,pharOutDistributeBatchDetailDTOS,isAllOut,allCostDTOList,itemIdList);
        }

        // TODO 部分退处理未退项目，更新费用信息，处方结算标志修改，已结算 -> 未结算
        if (!isAllOut) {
            this.insertCostInfo(costDTOList,itemIdList);
            this.updateOutptPrescribe(oldOutptSettleDTO);
        }

        // TODO 门诊费用、结算、支付信息冲红
        String redSettleId = this.outptChargeChangeRed(allCostDTOList,outptSettleDTO,outptVisitDTO);

        /**START*****************医保病人处理********************************************************************/
        // TODO 医保病人处理
        selectMap.put("id",visitId);
        OutptVisitDTO outpt = outptVisitDAO.queryByVisitID(selectMap);
        if (outpt == null) {
            throw new AppException("患者就诊数据出现异常【" + visitId + "】，请联系管理员");
        }
        // 病人类型为空 给默认值  lly 20211026
        if (StringUtils.isEmpty(outpt.getPatientCode())){
            outpt.setPatientCode("0");
        }

        // add 退费时获取病人结算记录中病人类型进行退费，而不是取就诊信息表中的病人类型，防止复诊时出现异常情况
        // by liaojiguang on 2021-11-23
        if (StringUtils.isEmpty(oldOutptSettleDTO.getPatientCode())){
            oldOutptSettleDTO.setPatientCode("0");
        }
        Integer patientCodeValue = Integer.parseInt(oldOutptSettleDTO.getPatientCode());


        // 判断病人是否为电子凭证用户
        boolean isDZPZ = false;
        // 海南电子凭证退费  2021年11月30日19:22:37========================start===========================
        if (oldOutptSettleDTO.getSourcePayCode() != null && "4".equals(oldOutptSettleDTO.getSourcePayCode())) {
            isDZPZ = true;
            // 调用电子凭证退费
            Map<String,Object> outptElectronicParam = new HashMap<String,Object>();
            outptElectronicParam.put("hospCode",outptVisitDTO.getHospCode());//医院编码
            outptElectronicParam.put("outptCostDTOList",outptVisitDTO.getOutptCostDTOList());//费用信息
            outptElectronicParam.put("outptVisitDTO",outptVisitDTO);//个人信息
            outptElectronicParam.put("outptSettleDTO", outptSettleDTO);
            selectMap.put("state",Constants.ZTBZ.ZC);
            InsureIndividualSettleDO insureIndividualSettleDO = insureIndividualSettleService_consumer.getByParams(selectMap);
            if (insureIndividualSettleDO == null) {
                throw new AppException("未获取到医保结算数据");
            }
            outptElectronicParam.put("insureRegCode",insureIndividualSettleDO.getInsureOrgCode());//医保编码
            Map<String,Object> httpResult = (Map<String, Object>) outptElectronicBillService.deletePatientCostPremium(outptElectronicParam).getData();
            if (!"0".equals(httpResult.get("code"))) {
                throw new AppException("电子凭证退费失败");
            }
        }
        // 海南电子凭证退费  2021年11月30日19:22:37====================end===============================

        if (patientCodeValue > 0 && !isDZPZ) {
            InsureIndividualBasicDTO insureIndividualBasicDTO = outptVisitDAO.getInsureBasicById(selectMap);
            if (insureIndividualBasicDTO == null) {
                throw new AppException("未进行医保登记，医保退费失败");
            }

            // 冲红医保结算数据
            selectMap.put("state",Constants.ZTBZ.ZC);
            InsureIndividualSettleDTO insureIndividualSettleDO = insureIndividualSettleService_consumer.getByParams(selectMap);
            if (insureIndividualSettleDO == null) {
               throw new AppException("未获取到医保结算数据");
            }

            String insureSettleId = insureIndividualSettleDO.getInsureSettleId();
            BigDecimal personalPrice = insureIndividualSettleDO.getPersonalPrice();

            // 被冲红
            insureIndividualSettleDO.setState(Constants.ZTBZ.BCH);
            Map selectInsureMap = new HashMap();
            selectInsureMap.put("hospCode",hospCode);
            insureIndividualSettleDO.setSettleState("1");
            selectInsureMap.put("insureIndividualSettleDO",insureIndividualSettleDO);
            outptVisitDAO.updateInsureSettleInfo(insureIndividualSettleDO);
            // insureIndividualSettleService_consumer.updateByPrimaryKey(selectInsureMap);

            // 冲红
            String insureRedSettleId = SnowflakeUtils.getId();
            insureIndividualSettleDO.setSettleId(redSettleId);
            insureIndividualSettleDO.setId(insureRedSettleId);
            insureIndividualSettleDO.setState(Constants.ZTBZ.CH);
            insureIndividualSettleDO.setHospCode(hospCode);
            insureIndividualSettleDO.setTotalPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getTotalPrice()));
            insureIndividualSettleDO.setInsurePrice(BigDecimalUtils.negate(insureIndividualSettleDO.getInsurePrice()));
            insureIndividualSettleDO.setPlanPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getPlanPrice()));
            insureIndividualSettleDO.setSeriousPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getSeriousPrice()));
            insureIndividualSettleDO.setCivilPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getCivilPrice()));
            insureIndividualSettleDO.setRetirePrice(BigDecimalUtils.negate(insureIndividualSettleDO.getRetirePrice()));
            insureIndividualSettleDO.setPersonalPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getPersonalPrice()));
            insureIndividualSettleDO.setPersonPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getPersonPrice()));
            insureIndividualSettleDO.setHospPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getHospPrice()));
            insureIndividualSettleDO.setRestsPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getRestsPrice()));
            insureIndividualSettleDO.setAssignPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getAssignPrice()));
            insureIndividualSettleDO.setStartingPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getStartingPrice()));
            insureIndividualSettleDO.setTopPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getTopPrice()));
            insureIndividualSettleDO.setPlanAccountPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getPlanAccountPrice()));
            insureIndividualSettleDO.setPortionPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getPortionPrice()));
            insureIndividualSettleDO.setCrteId(outptVisitDTO.getCrteId());
            insureIndividualSettleDO.setCrteName(outptVisitDTO.getCrteName());
            insureIndividualSettleDO.setSettleState("1");
            insureIndividualSettleDO.setCrteTime(outptVisitDTO.getCrteTime());
            insureIndividualSettleDO.setPsnPartAmt(BigDecimalUtils.negate(insureIndividualSettleDO.getPsnPartAmt()));// 个人负担总金额
            insureIndividualSettleDO.setBeforeSettle(BigDecimalUtils.negate(insureIndividualSettleDO.getBeforeSettle()));// 结算后余额
            insureIndividualSettleDO.setLastSettle(BigDecimalUtils.negate(insureIndividualSettleDO.getLastSettle()));// 结算后余额

            // 生育基金
            insureIndividualSettleDO.setFertilityPay(BigDecimalUtils.negate(insureIndividualSettleDO.getFertilityPay()));
            // 代缴基金（破产改制）
            insureIndividualSettleDO.setBehalfPay(BigDecimalUtils.negate(insureIndividualSettleDO.getBehalfPay()));
            // 居民家庭账户金
            insureIndividualSettleDO.setFamilyPay(BigDecimalUtils.negate(insureIndividualSettleDO.getFamilyPay()));
            // 新冠肺炎核酸检测财政补助
            insureIndividualSettleDO.setCOVIDPay(BigDecimalUtils.negate(insureIndividualSettleDO.getCOVIDPay()));
            // 公益补充保险基金
            insureIndividualSettleDO.setWelfarePay(BigDecimalUtils.negate(insureIndividualSettleDO.getWelfarePay()));
            // 军转干部医疗补助基金
            insureIndividualSettleDO.setSoldierToPay(BigDecimalUtils.negate(insureIndividualSettleDO.getSoldierToPay()));
            // 厅级干部补助基金
            insureIndividualSettleDO.setHallPay(BigDecimalUtils.negate(insureIndividualSettleDO.getHallPay()));
            //工伤保险基金
            insureIndividualSettleDO.setInjuryPay(BigDecimalUtils.negate(insureIndividualSettleDO.getInjuryPay()));
            // 离休老工人门慢保障基金
            insureIndividualSettleDO.setRetiredOutptPay(BigDecimalUtils.negate(insureIndividualSettleDO.getRetiredOutptPay()));
            // 一至六级残疾军人医疗补助基金
            insureIndividualSettleDO.setSoldierPay(BigDecimalUtils.negate(insureIndividualSettleDO.getSoldierPay()));
            // 离休人员医疗保障基金
            insureIndividualSettleDO.setRetiredPay(BigDecimalUtils.negate(insureIndividualSettleDO.getSoldierPay()));
            // 其他基金
            insureIndividualSettleDO.setOthPay(BigDecimalUtils.negate(insureIndividualSettleDO.getOthPay()));
            // 农村低收入人口医疗补充保险
            insureIndividualSettleDO.setLowInPay(BigDecimalUtils.negate(insureIndividualSettleDO.getLowInPay()));
            // 优抚对象医疗补助基金
            insureIndividualSettleDO.setCarePay(BigDecimalUtils.negate(insureIndividualSettleDO.getCarePay()));
            // 特惠保补偿金
            insureIndividualSettleDO.setThbPay(BigDecimalUtils.negate(insureIndividualSettleDO.getThbPay()));
            // 政府兜底基金
            insureIndividualSettleDO.setGovernmentPay(BigDecimalUtils.negate(insureIndividualSettleDO.getGovernmentPay()));
            // 居民意外伤害基金
            insureIndividualSettleDO.setRetAcctInjPay(BigDecimalUtils.negate(insureIndividualSettleDO.getRetAcctInjPay()));
            // 职工意外伤害基金
            insureIndividualSettleDO.setAcctInjPay(BigDecimalUtils.negate(insureIndividualSettleDO.getAcctInjPay()));
            // 个人账户共计支付金额
            insureIndividualSettleDO.setAcctMulaidPay(BigDecimalUtils.negate(insureIndividualSettleDO.getAcctMulaidPay()));
            // 符合政策范围金额
            insureIndividualSettleDO.setInscpScpAmt(BigDecimalUtils.negate(insureIndividualSettleDO.getInscpScpAmt()));
            // 先行自付金额
            insureIndividualSettleDO.setPreselfpayAmt(BigDecimalUtils.negate(insureIndividualSettleDO.getPreselfpayAmt()));
            // 超限价
            insureIndividualSettleDO.setOverSelfPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getOverSelfPrice()));
            // 全自费金额
            insureIndividualSettleDO.setAllPortionPrice(BigDecimalUtils.negate(insureIndividualSettleDO.getAllPortionPrice()));
            // 企业支付
            insureIndividualSettleDO.setComPay(BigDecimalUtils.negate(insureIndividualSettleDO.getComPay()));
            // 医院减免金额
            insureIndividualSettleDO.setHospExemAmount(BigDecimalUtils.negate(insureIndividualSettleDO.getHospExemAmount()));
            //医疗救助金额
            insureIndividualSettleDO.setMafPay(BigDecimalUtils.negate(insureIndividualSettleDO.getMafPay()));
            // 伤残人员医疗保障基金
            insureIndividualSettleDO.setHifdmPay(BigDecimalUtils.negate(insureIndividualSettleDO.getHifdmPay()));

            Map insertMap = new HashMap();
            insertMap.put("hospCode",hospCode);
            insertMap.put("insureIndividualSettleDO",insureIndividualSettleDO);

            // 由于分布式事务不回滚问题，直接写sql处理
            // insureIndividualSettleService_consumer.insert(insertMap);
            outptVisitDAO.insertInsureSettleInfo(insureIndividualSettleDO);
            /**
             * 查询医院医保配置（直接走医保还是走统一支付平台）
             * 门诊取消结算前（也就是全退）需要删除his医保就诊费用表
             * 同时调用门诊挂号撤销（）
             *
             */
            Map<String, Object> map = new HashMap<>();
            map.put("code", "UNIFIED_PAY");
            map.put("hospName",hospName);
            map.put("insureRegCode",insureIndividualSettleDO.getInsureOrgCode());
            map.put("crteId",outptVisitDTO.getCrteId());
            map.put("crteName",outptVisitDTO.getCrteName());
            map.put("hospCode",hospCode);  // 医院编码
            map.put("visitId",visitId); //就诊id
            map.put("id",visitId); //就诊id
            map.put("oldOutptSettleDTO",oldOutptSettleDTO);

            // 根据医院编码、医保注册编码查询医保配置信息
            InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
            configDTO.setHospCode(hospCode); //医院编码
            configDTO.setRegCode(insureIndividualBasicDTO.getInsureRegCode()); // 医保注册编码
            configDTO.setIsValid(Constants.SF.S); // 是否有效
            Map configMap = new LinkedHashMap();
            configMap.put("hospCode", hospCode);
            configMap.put("insureConfigurationDTO", configDTO);
            List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
            if (ListUtils.isEmpty(configurationDTOList)) {
                throw new RuntimeException("未找到医保机构，请重新获取人员信息。");
            }
            InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
            // 获取该医保配置是否走统一支付平台，1走，0/null不走
            String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

            /*SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
            if (sys != null && Constants.SF.S.equals(sys.getValue())) {  // 调用统一支付平台*/
            if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {  // 调用统一支付平台
                List<Map<String,Object>> individualCostDTOList  = querySettleCost(map);
                map.put("insureCostList",individualCostDTOList);
                map.put("insureSettleId",insureSettleId);
                map.put("personalPrice",personalPrice);
                Map<String,Object> resultMap = insureUnifiedPayOutptService_consumer.UP_2208(map).getData();
                System.out.println("==========================resultMap:" + resultMap.toString());
                Map<String,Object> cancelReturnData =  MapUtils.get(resultMap,"output");
                Map<String,Object> setlInfoMap = MapUtils.get(cancelReturnData,"setlinfo");
                Boolean data = insureUnifiedPayOutptService_consumer.UP_2205(map).getData();
                if(data){
                    MapUtils.remove(map,"insureSettleId"); // 因为这是取消结算  所以要删除对应的费用数据
                    insureIndividualCostService_consumer.deleteOutptInsureCost(map);
                }
                InsureIndividualSettleDTO individualSettleDTO = new InsureIndividualSettleDTO();
                individualSettleDTO.setId(insureRedSettleId);
                individualSettleDTO.setHospCode(hospCode);
                individualSettleDTO.setOinfno(MapUtils.get(resultMap,"funtionCode"));
                individualSettleDTO.setOmsgid(MapUtils.get(resultMap,"msgId"));
                individualSettleDTO.setInsureSettleId(MapUtils.get(setlInfoMap,"setl_id"));
               if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_optins"))){
                    individualSettleDTO.setClrOptins(MapUtils.get(setlInfoMap,"clr_optins"));
                }else{
                    individualSettleDTO.setClrOptins(insureIndividualSettleDO.getClrOptins());
                }
                if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_way"))){
                    individualSettleDTO.setClrWay(MapUtils.get(setlInfoMap,"clr_way"));
                }else {
                    individualSettleDTO.setClrWay(insureIndividualSettleDO.getClrWay());
                }
                if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_type"))){
                    individualSettleDTO.setClrType(MapUtils.get(setlInfoMap,"clr_type"));
                }
                else {
                    individualSettleDTO.setClrType(insureIndividualSettleDO.getClrType());
                }
                individualSettleDTO.setHospCode(hospCode);
                individualSettleDTO.setVisitId(visitId);
                individualSettleDTO.setState("2");
                individualSettleDTO.setSettleId(redSettleId);
                individualSettleDTO.setSettleState("1");
                individualSettleDTO.setId(insureRedSettleId);
                outptVisitDAO.updateInsureSettle(individualSettleDTO);
            }
            else {
                /**
                 * 长沙医保退费所需参数
                 */
                map.put("insureRegCode",insureIndividualBasicDTO.getInsureRegCode());
                map.put("code",outptVisitDTO.getCode());  // 操作员编码
                InsureOutptOutFeeDTO insureOutptOutFeeDTO = new InsureOutptOutFeeDTO();
                insureOutptOutFeeDTO.setInsureRegCode(insureIndividualBasicDTO.getInsureRegCode());
                insureOutptOutFeeDTO.setHospCode(insureIndividualBasicDTO.getHospCode());

                // 默认写死就医登记号
                insureOutptOutFeeDTO.setBka895("aaz217");
                insureOutptOutFeeDTO.setBka896(insureIndividualBasicDTO.getAac001());
                insureOutptOutFeeDTO.setAkb020(insureIndividualBasicDTO.getInsureRegCode());
                insureOutptOutFeeDTO.setBka006(insureIndividualBasicDTO.getBka006());
                insureOutptOutFeeDTO.setAka130(insureIndividualBasicDTO.getAka130());
                insureOutptOutFeeDTO.setBka001("0"); //费用批次为0查询出所有
                insureOutptOutFeeDTO.setAaz217(insureIndividualBasicDTO.getAaz217());
                insureOutptOutFeeDTO.setVisitId(visitId);
                insureOutptOutFeeDTO.setInsureRegCode(insureIndividualBasicDTO.getInsureRegCode());
                insureOutptOutFeeDTO.setMedicalRegNo(insureIndividualBasicDTO.getAaz217());
                map.put("insureOutptOutFeeDTO",insureOutptOutFeeDTO);

                /*Map repMap = outptService.getInsureOutptOutFeeInfo(map);
                List<Map<String,Object>> feeList = (List<Map<String,Object>>)repMap.get("feeList");
                if (!ListUtils.isEmpty(feeList)) {

                }*/

                Map<String,Object> outFeeMap = new HashMap<>();
                outFeeMap.put("fees",allCostDTOList);
                outFeeMap.put("type","1");
                outFeeMap.put("hospCode",insureOutptOutFeeDTO.getHospCode());
                outFeeMap.put("visitId",insureOutptOutFeeDTO.getVisitId());
                outFeeMap.put("insureRegCode",insureOutptOutFeeDTO.getInsureRegCode());
                outFeeMap.put("bka893","1");
                outFeeMap.put("aaz217",insureIndividualBasicDTO.getAaz217());
                outFeeMap.put("saveFlag","1");
                outFeeMap.put("code",outptVisitDTO.getCode());
                outFeeMap.put("crteName",outptVisitDTO.getCrteName());
                outFeeMap.put("aac001",insureIndividualBasicDTO.getAac001());
                if (StringUtils.isEmpty(insureIndividualBasicDTO.getAaz217())) {
                    throw new AppException("医保退费失败：未获取病人医保登记号，请去医保前台系统退费");
                }
                outptService.setOutptCostUploadAndTrial(outFeeMap);
            }
        }

        // 2021年12月2日14:35:38 查询系统参数是否自动重收  如果没有配置会自动重收，配置为0不自动重收
        Map<String, String> sysMap = new HashMap<>();
        sysMap.put("code", "SF_AUTO_SETTLE");
        sysMap.put("hospCode", hospCode);
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if(sysParameterDTO !=null && "0".equals(sysParameterDTO.getValue())) {
            // 体检回调
            if (outptSettleDTO!=null &&"1".equals(outptSettleDTO.getIsPhys())) {
                phyIsCallBack(allCostDTOList);
            }
            Map insurePaitentInfo = new HashMap();
            insurePaitentInfo.put("outptVisit", outpt); // 返回病人信息
            insurePaitentInfo.put("isInsurePaitentPartBack", isInsurePaitentPartBack); // 是否医保病人部分退费
            return WrapperResponse.success(insurePaitentInfo);
        }

        //判断是否是移动支付，是移动支付退款则推送退款申请 todo 并且这一笔结算是移动支付
        /*Map<String, String> ydzfMap = new HashMap<>();
        ydzfMap.put("code", "HAINAN_YDZF_FLAG");
        ydzfMap.put("hospCode", hospCode);
        SysParameterDTO ydzfParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if(ydzfParameterDTO !=null && "1".equals(ydzfParameterDTO.getValue())) {
          Map<String, Object> map1 = new HashMap<>();
          map1.put("hospCode", hospCode);
          map1.put("id",visitId);
          InsureIndividualVisitDTO insureIndividualVisitDTO =
              insureIndividualVisitService_consumer.getInsureIndividualVisitById(map1);
          SetlRefundQueryDTO dto = new SetlRefundQueryDTO();
          dto.setVisitId(visitId);
          dto.setSettleId(settleId);
          dto.setRedSettleId(redSettleId);
          Map<String, Object> map = new HashMap<>();
          map.put("hospCode", hospCode);
          map.put("setlRefundQueryDTO", dto);
          map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
          Map<String, Object> response = outptTmakePriceFormService_consumer.ampRefund(map).getData();
          PayOnlineInfoDO pay = new PayOnlineInfoDO();
          pay.setVisitId(insureIndividualVisitDTO.getVisitId());
          pay.setRedSettleId(redSettleId);
          pay.setAmpTraceId(insureIndividualVisitDTO.getVisitId());
          pay.setTraceId(settleId);
          payOnlineInfoDAO.updateByVisitId(pay);
        }*/
        /**END*****************医保病人处理********************************************************************/
       // TODO 非医保病人自动退费
        // 如果有再收的费用且是普通病人，自动收费

        if (!ListUtils.isEmpty(costDTOList) && patientCodeValue < 1) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(outptSettleDTO.getVisitId())) {
            throw new AppException("未获取到就诊信息");
        }

        map.put("visitId",outptSettleDTO.getVisitId());
        map.put("hospCode",hospCode);
        OutptVisitDTO outptVisitDTOFee = outptVisitDAO.getVisitByParams(map);
        outptVisitDTOFee.setOutptCostDTOList(costDTOList);
        outptVisitDTOFee.setCrteName(crteName);
        outptVisitDTOFee.setCrteId(crteId);
        outptVisitDTOFee.setTfcsMark("tfcs"); // 退费重收标记
        // 退费重收取原始结算表中的病人类型  2022-03-28 lly
        outptVisitDTOFee.setPatientCode(oldOutptSettleDTO.getPatientCode());
        outptVisitDTOFee.setTruncPrice(outptSettleDTO.getTruncPrice()); // 退费时，原结算时舍入金额
        Map setteleParam = new HashMap();
        setteleParam.put("hospCode",hospCode);
        setteleParam.put("outptVisitDTO",outptVisitDTOFee);
        WrapperResponse wrapperResponse = outptTmakePriceFormService_consumer.saveOutptSettleMoney(setteleParam);
        if (wrapperResponse.getCode() == (-1)) {
            throw new AppException(wrapperResponse.getMessage());
        }

        Map chargeMap = (Map) wrapperResponse.getData();
        OutptSettleDO outptSettleDo = (OutptSettleDO)chargeMap.get("outptSettle");
        OutptSettleDTO newOutptSettleDTO = new OutptSettleDTO();
        BeanUtils.copyProperties(outptSettleDo,newOutptSettleDTO);

        // 需要重新设置创建人员
        OutptVisitDTO visitDTO = (OutptVisitDTO) chargeMap.get("outptVisit");
        visitDTO.setCrteId(crteId);
        visitDTO.setCrteName(crteName);
        visitDTO.setTfcsMark("tfcs"); // 退费重收标记
        visitDTO.setTruncPrice(outptSettleDTO.getTruncPrice()); // 退费时，原结算时舍入金额

        // 2021年9月1日15:25:54 在院支付方式上扣除费用  1、先查询原支付方式 ===========官红强==start===========================================================================
        List<OutptPayDTO> oldOutptPayList = outptPayDAO.selectOutptPatByVisitIdAndSettleId(selectMap);

        Map<String, Object> oldOutptPayMap = new HashMap<>();
        if (!ListUtils.isEmpty(oldOutptPayList)) {
            for (OutptPayDTO dto : oldOutptPayList) {
                oldOutptPayMap.put(dto.getPayCode(), dto.getPrice());
            }
        }

        Map<String, Object> tkMap = new HashMap<>();  // 将页面的退款支付信息转换为map
        for (OutptPayDTO outptPayDto : tkOutptPayDTOList) {
            tkMap.put(outptPayDto.getPayCode(), outptPayDto.getPrice());
        }
        BigDecimal otherPayCodePrice = new BigDecimal(0); // 需要其他支付方式扣除的金额
        for (Map.Entry<String, Object> entry : tkMap.entrySet()) {
            String tkPayCode = entry.getKey(); // 支付类型
            BigDecimal tkPrice = (BigDecimal) entry.getValue();  // 支付金额
            if (oldOutptPayMap.containsKey(tkPayCode)) {
                BigDecimal oldPrice = (BigDecimal) oldOutptPayMap.get(tkPayCode);  // 原支付方式的支付金额
                // 当前支付方式的退款金额 大于 原支付方式支付金额
                if (!BigDecimalUtils.isZero(tkPrice)&&BigDecimalUtils.greater(tkPrice, oldPrice)) {
                    oldOutptPayMap.put(tkPayCode, BigDecimalUtils.subtract(oldPrice, tkPrice)); // 更新原支付方式的支付金额为，原支付金额 - 现退款金额
                }else {
                    // 当前支付方式的退款金额与原支付方式支付金额相等
                    if (BigDecimalUtils.equals(tkPrice, oldPrice)) {
                        oldOutptPayMap.put(tkPayCode, BigDecimalUtils.negate(tkPrice)); // 原支付方式金额与退款金额一致，取负数
                    }else {
                        oldOutptPayMap.put(tkPayCode, BigDecimalUtils.subtract(oldPrice, tkPrice));
                    }
                }
            }else {
                oldOutptPayMap.put(tkPayCode, BigDecimalUtils.negate(tkPrice));
            }
//                BigDecimal oldPrice = (BigDecimal) oldOutptPayMap.get(tkPayCode);  // 原支付方式的支付金额
//                // 当前支付方式的退款金额与原支付方式支付金额相等
//                if (BigDecimalUtils.equals(tkPrice, oldPrice)) {
//                    oldOutptPayMap.remove(tkPayCode);
//                    continue;
//                }
//                // 当前支付方式的退款金额 大于 原支付方式支付金额
//                if (BigDecimalUtils.greater(tkPrice, oldPrice)) {
//                    otherPayCodePrice = BigDecimalUtils.add(otherPayCodePrice, BigDecimalUtils.subtract(tkPrice, oldPrice));
//                    oldOutptPayMap.remove(tkPayCode);
//                } else {
//                    oldOutptPayMap.put(tkPayCode, BigDecimalUtils.subtract(oldPrice, tkPrice)); // 更新原支付方式的支付金额为，原支付金额 - 现退款金额
//                }
//            } else {
//                otherPayCodePrice = BigDecimalUtils.add(otherPayCodePrice, tkPrice);
//            }
        }
            for (int i = 1; i < 8; i++) {
                if (oldOutptPayMap.containsKey(i + "")) {
                    BigDecimal oldPrice2 = (BigDecimal) oldOutptPayMap.get(i+"");
                    if (BigDecimalUtils.isZero(oldPrice2)){
                        oldOutptPayMap.remove(i+"");
                    }
                }
            }
        // 原支付方式对应的金额小于现支付方式的金额，缺的金额需要从其他支付方式扣除
//        if (BigDecimalUtils.greaterZero(otherPayCodePrice)) {
//            for (int i = 1; i < 8; i++) {
//                if (oldOutptPayMap.containsKey(i+"")) {
//                    BigDecimal oldPrice2 = (BigDecimal) oldOutptPayMap.get(i+"");
//                    // 如果需要补充扣除的支付金额 = 某个原支付金额剩余的金额，直接扣除原支付金额
//                    if (BigDecimalUtils.equals(otherPayCodePrice, oldPrice2)) {
//                        otherPayCodePrice = new BigDecimal(0);
//                        oldOutptPayMap.remove(i+"");
//                        break;
//                    }
//                    // 需要补充扣除的费用大于当前支付方式金额  eg： 还需要扣20元 但现金只剩10元， 那么现金扣完10元，剩余的10元从下一个支付方式扣除
//                    if (BigDecimalUtils.greater(otherPayCodePrice, oldPrice2)) {
//                        otherPayCodePrice = BigDecimalUtils.subtract(otherPayCodePrice, oldPrice2);
//                        oldOutptPayMap.remove(i+"");
//                    } else {
//                        oldOutptPayMap.put(i+"", BigDecimalUtils.subtract(oldPrice2, otherPayCodePrice));
//                        break;
//                    }
//                }
//            }
//        }
        BigDecimal totalOldPriceAgain = new BigDecimal(0);
        for (Map.Entry<String, Object> entry : oldOutptPayMap.entrySet()) {
            totalOldPriceAgain = BigDecimalUtils.add(totalOldPriceAgain, (BigDecimal) entry.getValue());
        }
        // 2021年9月1日15:58:15 ==============================================官红强==end==============================================================

        // 划价收费接口支付接口调用(支付方式默认为现金)
        List<OutptPayDO> outptPayDOlist = new ArrayList();

        // 没有使用卡支付,全部其他方式支付
        if (BigDecimalUtils.isZero(settleCardPrice)) {
//            if (!BigDecimalUtils.equals(totalOldPriceAgain, BigDecimalUtils.subtract(moneyAgain, outptSettleDo.getTruncPrice() == null? new BigDecimal(0): outptSettleDo.getTruncPrice()))) {
//                throw new AppException("退费自动重收(未使用一卡通)时，计算各支付方式支付金额出错,刷新重试");
//            }
            for (Map.Entry<String, Object> entry : oldOutptPayMap.entrySet()) {
                OutptPayDO OutptPayDO = new OutptPayDTO();
                OutptPayDO.setPrice((BigDecimal) entry.getValue());
                OutptPayDO.setPayCode(entry.getKey());
                outptPayDOlist.add(OutptPayDO);
            }
            // 设置一卡通卡号与卡支付金额
            visitDTO.setCardNo(null);
            visitDTO.setCardPrice(null);
            visitDTO.setProfileId(null);
        }
        // 部分卡支付，部分其他方式支付
        BigDecimal tempPrice = new BigDecimal(0);
        BigDecimal cardPriceAgain = new BigDecimal(0); // 一卡通再收金额
        if (BigDecimalUtils.greaterZero(settleCardPrice) && BigDecimalUtils.less(settleCardPrice, settleSelfPrice)) {
            tempPrice = BigDecimalUtils.subtract(moneyAgain, settleActualPrice); // 重收金额 - 已经付现的金额
            if (BigDecimalUtils.greaterZero(tempPrice)) { // 再收金额大于已经支付的现金金额
                cardPriceAgain = BigDecimalUtils.subtract(tempPrice, outptSettleDo.getTruncPrice() == null? new BigDecimal(0): outptSettleDo.getTruncPrice());
                // 设置一卡通卡号与卡支付金额
                visitDTO.setCardNo(outptSettleDTO.getCardNo());
                visitDTO.setCardPrice(cardPriceAgain);
                visitDTO.setProfileId(outptSettleDTO.getProfileId());
            } else {
                // 设置一卡通卡号与卡支付金额
                visitDTO.setCardNo(null);
                visitDTO.setCardPrice(null);
                visitDTO.setProfileId(null);
            }
            if (!BigDecimalUtils.equals(BigDecimalUtils.add(totalOldPriceAgain, outptSettleDo.getTruncPrice()), moneyAgain)) {
                throw new AppException("退费失败，自动重收时，计算一卡通与其他支付方式支付金额出错,请前往划价收费界面手动结算");
            }
            for (Map.Entry<String, Object> entry : oldOutptPayMap.entrySet()) {
                OutptPayDO OutptPayDO = new OutptPayDTO();
                OutptPayDO.setPrice((BigDecimal) entry.getValue());
                OutptPayDO.setPayCode(entry.getKey());
                outptPayDOlist.add(OutptPayDO);
            }
        }
        //  全部卡支付，退费自动收时其他支付方式为空
        if (BigDecimalUtils.equals(settleCardPrice, settleSelfPrice)) {
            cardPriceAgain = BigDecimalUtils.subtract(moneyAgain, outptSettleDo.getTruncPrice() == null? new BigDecimal(0): outptSettleDo.getTruncPrice());
            // 设置一卡通卡号与卡支付金额
            visitDTO.setCardNo(outptSettleDTO.getCardNo());
            visitDTO.setCardPrice(cardPriceAgain);
            visitDTO.setProfileId(outptSettleDTO.getProfileId());
        }

        Map<String,Object> chargeParams = new HashMap<>();


        // 判断是否使用发票
//        if (StringUtils.isNotEmpty(outptSettleDTO.getInvoiceNo())) {
//            newOutptSettleDTO.setIsInvoice(true);
//        } else {
//            newOutptSettleDTO.setIsInvoice(false);
//        }
        newOutptSettleDTO.setIsInvoice(false);

        // 金额保持一致
        newOutptSettleDTO.setActualPrice(newOutptSettleDTO.getRealityPrice());
        newOutptSettleDTO.setCrteId(crteId);
        newOutptSettleDTO.setCrteName(crteName);


        chargeParams.put("outptPayDOList",outptPayDOlist);
        chargeParams.put("outptVisitDTO",visitDTO);
        chargeParams.put("outptSettleDTO",newOutptSettleDTO);
        chargeParams.put("hospCode",hospCode);
        // outptTmakePriceFormService_consumer.saveOutptSettle(chargeParams);


            /**
             *   当取消结算的时候 出现医保和his单边账
             *   1.医保已经取消  而his没有取消结算 这个时候就需要调用单边账（取消结算单边功能）
             *   2.作废医保结算记录 置空医保结算id  把病人类型改完自费病人。his结算表变为自费病人
             *   3.再把患者变成自费病人结算，结算完成以后（需要判断医保是否有作废的数据）  再修改病人类型
             *   4.病人类型的值存在redis里面中（取消结算单边功能存入）
             */
            selectMap.put("settleId",settleId);
            List<InsureIndividualSettleDTO> settleDTOList =  outptSettleDAO.queryOutptSettle(selectMap);
            String redisKey = new StringBuilder().append(hospCode).append("^").append(visitId).
                    append("2208").append("^").toString();
            if(ListUtils.isEmpty(settleDTOList)){
                String redisValue = redisUtils.get(redisKey);
                if(redisUtils.hasKey(redisKey) && StringUtils.isNotEmpty(redisValue)){
                    OutptVisitDTO dto = new OutptVisitDTO();
                    dto.setHospCode(hospCode);//医院编码
                    dto.setId(visitId);//就诊id
                    dto.setPatientCode(redisValue);
                    outptVisitDAO.updateOutptVisit(dto);
                    redisUtils.del(redisKey);
                }
            }

        outptTmakePriceFormService_consumer.saveOutptSettleByTf(chargeParams);
        }
        /***********start 医保病人退部分费用标识**********/
        if (!ListUtils.isEmpty(costDTOList) && patientCodeValue > 0) {
            isInsurePaitentPartBack = true;
        } else {
            isInsurePaitentPartBack = false;
        }
        Map insurePaitentInfo = new HashMap();
        insurePaitentInfo.put("outptVisit", outpt); // 返回病人信息
        insurePaitentInfo.put("isInsurePaitentPartBack", isInsurePaitentPartBack); // 是否医保病人部分退费
        /***********End 医保病人退部分费用标识*********/

       /***********End*****************非医保病人自动收费************************************/
        // 体检回调
        if (outptSettleDTO!=null &&"1".equals(outptSettleDTO.getIsPhys())) {
            phyIsCallBack(allCostDTOList);
        }
        return WrapperResponse.success(insurePaitentInfo);
    }

    /**
     *
     * 更新门诊结算标志为未结算 is_settle
     * @param oldOutptSettleDTO
     */
    private void updateOutptPrescribe(OutptSettleDTO oldOutptSettleDTO) {
        outptSettleDAO.updateOutptPrescribe(oldOutptSettleDTO);
    }

    private List<Map<String, Object>> querySettleCost(Map<String,Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId =  MapUtils.get(map,"visitId");
        String insureRegCode =  MapUtils.get(map,"insureRegCode");
        //判断是否有传输费用信息
        Map<String,Object> insureCostParam = new HashMap<String,Object>();
        insureCostParam.put("hospCode",hospCode);//医院编码
        insureCostParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId",visitId);//就诊id
        insureCostParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode",Constants.SF.S);//传输标志 = 未传输
        insureCostParam.put("insureRegCode",insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital",Constants.SF.F); // 区分门诊还是住院
        List<Map<String,Object>> insureCostList = insureIndividualCostService_consumer.queryOutptInsureCostByVisit(insureCostParam);
        if(ListUtils.isEmpty(insureCostList)){
            return new ArrayList<>();
        }
        return insureCostList;
    }
    /**
     * @param outptSettleDTO
     * @Menthod queryOutChargePage
     * @Desrciption 门诊退费 - 查询门诊已结算患者信息
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutChargePage(OutptSettleDTO outptSettleDTO) {
        //设置分页
        PageHelper.startPage(outptSettleDTO.getPageNo(), outptSettleDTO.getPageSize());

        // 退费管理是否只显示申请退费病人 lly 20220422 start
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", outptSettleDTO.getHospCode());
        map.put("code", "SF_MZTF_APPLY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if(sys!=null&&Constants.SF.S.equals(sys.getValue())){
            List<Map<String, Object>> list = outptSettleDAO.queryApplyRefundCharge(outptSettleDTO);
            return WrapperResponse.success(PageDTO.of(list));
        // 退费管理是否只显示申请退费病人 lly 20220422 end
        }else {
            List<Map<String, Object>> list = outptSettleDAO.queryOutChargePage(outptSettleDTO);
            return WrapperResponse.success(PageDTO.of(list));
        }
    }

    /**
     * @param param
     * @Menthod queryOutptPrescribes
     * @Desrciption 门诊退费 - 查询门诊费用信息
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutptPrescribes(Map param) {
        OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");
		// 校验医院退费时退费项目是收费员确定还是走门诊医生申请
		Map<String, Object> map = new HashMap<>();
		map.put("hospCode", outptSettleDTO.getHospCode());
		map.put("code", "REFUND_APPLY");
		SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();

		List<OutptCostDTO> list = new ArrayList<>();
        if (outptSettleDTO.getIsPhys() == null || "".equals(outptSettleDTO.getIsPhys())) {  // 如果患者信息中不是体检患者，按医院配置走，如果是体检患者，则必须要走申请 2021年11月23日16:14:03
            if (sys != null && sys.getValue().equals("1")) {  // 1:门诊医生申请  2：收费室自己决定
                list = outptSettleDAO.queryOutptPrescribesandRefundApply(outptSettleDTO);
            } else {
                list = outptSettleDAO.queryOutptPrescribes(outptSettleDTO);
            }
        } else {
            list = outptSettleDAO.queryOutptPrescribesandRefundApply(outptSettleDTO);
        }
        return WrapperResponse.success(list);
    }

    /**
     * @param param
     * @Menthod queryOutptPrescribe
     * @Desrciption 门诊退费 - 查询门诊处方类型
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutptPrescribe(Map param) {
        OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");

        List<OutptPrescribeDO> list = outptSettleDAO.queryOutptPrescribe(outptSettleDTO);
        return WrapperResponse.success(list);
    }

    /**
     * @Menthod getDiagnoseInfo
     * @Desrciption 查询门诊诊断信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse getDiagnoseInfo(Map param) {
        OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");
        List<OutptDiagnoseDTO> list = outptSettleDAO.getDiagnoseInfo(outptSettleDTO);
        StringBuffer str = new StringBuffer();
        if (!ListUtils.isEmpty(list)) {
            for(OutptDiagnoseDTO outptDiagnoseDTO : list) {
                if (StringUtils.isNotEmpty(outptDiagnoseDTO.getDiseaseName())) {
                    str.append(outptDiagnoseDTO.getDiseaseName()).append(";");
                }
            }
        }
        return WrapperResponse.success(str);
    }

    /**
     * @Menthod getInvoiceInfo
     * @Desrciption 获取发票信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse getInvoiceInfo(Map param) {
        OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");
        Map <String,Object> map = outptSettleDAO.getInvoiceInfo(outptSettleDTO);
        return WrapperResponse.success(map);
    }

    /**
     * @Menthod updateOutptOPharInfo
     * @Desrciption 门诊退费 - 判断是否已经完成发药或退药
     * @param outptSettleDTO
     * @param outptVisitDTO
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse updateOutptOPharInfo(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO) {
        Map<String,Object> selectMap = new HashMap();
        selectMap.put("visitId", outptSettleDTO.getVisitId());
        selectMap.put("hospCode", outptSettleDTO.getHospCode());
        selectMap.put("settleId", outptSettleDTO.getId());

        // TODO 门诊费用信息列表入参获取
        List<OutptCostDTO> allCostDTOList = outptVisitDTO.getOutptCostDTOList();

        // TODO 回退数量异常判断 - 回退总数量
        BigDecimal backNumSum = BigDecimal.ZERO;
        for (OutptCostDTO outptCostDTO : allCostDTOList) {
            // 应收数量 = 收费数量 - 回退数量
            BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(), outptCostDTO.getBackNum());
            int compNum = BigDecimalUtils.compareTo(lastNum, BigDecimal.ZERO);
            if (compNum == -1) {
                throw new AppException(outptCostDTO.getItemName() + "退费数量不能大于已收费数量");
            }
            backNumSum = BigDecimalUtils.add(backNumSum,outptCostDTO.getBackNum());
        }
        if (BigDecimalUtils.compareTo(backNumSum, BigDecimal.ZERO) <= 0) {
            throw new AppException("退费数量不能全为零");
        }

        // TODO 根据结算ID + 就诊ID + 医院编码（selectMap）获取门诊领药申请信息（phar_out_receive）
        List<PharOutReceiveDTO> pharOutReceiveDTOList = outptCostDAO.getPahrOutReceiceInfoBySelectMap(selectMap);
        if (!ListUtils.isEmpty(pharOutReceiveDTOList)) {
            for (PharOutReceiveDTO pharOutReceiveDTO : pharOutReceiveDTOList) {
                if (Constants.FYZT.PY.equals(pharOutReceiveDTO.getStatusCode())) {
                    throw new AppException("退费失败：已经完成配药，请先去药房取消配药");
                }
            }
        }

        // 是否已发药 - 能查询到发药记录说明存在发药记录,已完成退药的项目可退费
//        List<PharOutDistributeDTO> distributeList = outptCostDAO.queryPharOutDistribute(selectMap);
//        if (ListUtils.isEmpty(distributeList)) {
//            return WrapperResponse.success(true);
//        }

        // 获取已发药的明细记录
        //List<PharOutDistributeDetailDTO> allPharOutDistributeDetailList = outptCostDAO.queryPharOutDistributeDetailIByList(distributeList);
        List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOS = outptCostDAO.queryPharOutDistributeDetailIBySettleId(outptSettleDTO);
        if (ListUtils.isEmpty(pharOutDistributeBatchDetailDTOS)) {
            return WrapperResponse.success(true);
        }

        // 3、判断病人类型，门诊直接划价收费 or 处方病人
        boolean patientIsCF = true;  // 处方病人：true   直接划价收费病人： false
        if (StringUtils.isEmpty(outptVisitDTO.getId())) {
            patientIsCF = false;
        } else {
            // 根据就诊id查询门诊费用明细，费用来源有处方，也有直接划价收费的为处方病人； 费用来源只有直接划价收费的表示是直接划价收费病人
            patientIsCF = this.checkPatientIsMZ(outptVisitDTO);
        }
        if (patientIsCF) {
            for (PharOutDistributeAllDetailDTO pharOutDistributeBatchDetailDTO : pharOutDistributeBatchDetailDTOS) {
                for (OutptCostDTO outptCostDTO : allCostDTOList) {
                    //为0的退药数据不计算
                    if(BigDecimalUtils.compareTo(outptCostDTO.getBackNum(), BigDecimal.ZERO ) <= 0){
                        continue;
                    }
                    if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
                            && ((pharOutDistributeBatchDetailDTO.getOpdId() == null && outptCostDTO.getOpdId() == null)
                            || (StringUtils.isNotEmpty(pharOutDistributeBatchDetailDTO.getOpdId())
                            &&pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())))
                            && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId())) {
                        // 药房未退药数量 = 药房总数量 - 药费退药总数量
                        BigDecimal pharLastNum = BigDecimalUtils.subtract(pharOutDistributeBatchDetailDTO.getNum(),pharOutDistributeBatchDetailDTO.getTotalBackNum());

                        // 前端未退药数量 = 总数量 - 退药数量
                        BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(),outptCostDTO.getBackNum());
                        if (BigDecimalUtils.equals(pharLastNum,lastNum)) {
                            continue;
                        }

                        if (pharOutDistributeBatchDetailDTO.getTotalBackNum().compareTo(BigDecimal.ZERO) == 0) {
                            throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                        } else {
                            // 前端退回数量-（药房未退的数量-前端未退药的数量）
                            BigDecimal infactackNum = BigDecimalUtils.subtract(outptCostDTO.getBackNum(),BigDecimalUtils.subtract(pharLastNum,lastNum));
                            String num = infactackNum.stripTrailingZeros().toPlainString();
                            if ("0".equals(num)) {
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                            }

                            if (!"0".equals(outptCostDTO.getBackNum().stripTrailingZeros().toPlainString())) {
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】药房退药数量为【" + num +"】,请核对数量");
                            }
                        }

                    }
                }
            }
        } else {
            for (PharOutDistributeAllDetailDTO pharOutDistributeBatchDetailDTO : pharOutDistributeBatchDetailDTOS) {
                for (OutptCostDTO outptCostDTO : allCostDTOList) {
                    //为0的退药数据不计算
                    if(BigDecimalUtils.compareTo(outptCostDTO.getBackNum(), BigDecimal.ZERO ) <= 0){
                        continue;
                    }
                    //todo 这部分判断存在问题，如果是部分退，药房批次汇总表的费用id（最原始的费用id）对不上此时的费用id
                    if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
//                            && pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())
                            && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId())
                            && pharOutDistributeBatchDetailDTO.getCostId().equals(outptCostDTO.getOneDistCostId())
                    ) {
                        // 药房未退药数量 = 药房总数量 - 药费退药总数量
                        BigDecimal pharLastNum = BigDecimalUtils.subtract(pharOutDistributeBatchDetailDTO.getNum(),pharOutDistributeBatchDetailDTO.getTotalBackNum());

                        // 前端未退药数量 = 总数量 - 退药数量
                        BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(),outptCostDTO.getBackNum());
                        if (BigDecimalUtils.equals(pharLastNum,lastNum)) {
                            continue;
                        }

                        if (pharOutDistributeBatchDetailDTO.getTotalBackNum().compareTo(BigDecimal.ZERO) == 0) {
                            throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                        } else {
                            // 前端退回数量-（药房未退的数量-前端未退药的数量）
                            BigDecimal infactackNum = BigDecimalUtils.subtract(outptCostDTO.getBackNum(),BigDecimalUtils.subtract(pharLastNum,lastNum));
                            String num = infactackNum.stripTrailingZeros().toPlainString();
                            if ("0".equals(num)) {
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                            }

                            if (!"0".equals(outptCostDTO.getBackNum().stripTrailingZeros().toPlainString())) {
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】药房退药数量为【" + num +"】,请核对数量");
                            }
                        }

                    }
                }
            }
        }
        // TODO 药房退药数量核对

        return WrapperResponse.success(true);
    }

    /**
     * @Description: 判断患者是门诊处方患者 还是 直接划价收费患者
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/2 10:04
     * @Return
     */
    private boolean checkPatientIsMZ(OutptVisitDTO outptVisitDTO) {
        boolean isChange = false;
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", outptVisitDTO.getHospCode());
        map.put("visitId", outptVisitDTO.getId());
        List<OutptCostDTO> list = outptCostDAO.selectByVisitId(map);
        if (list == null) {
            throw new AppException("当前病人没有费用信息，无法判断当前病人是否为直接划价收费病人");
        }
        Map<String, String> tempMap = new HashMap<>();
        for (OutptCostDTO dto : list) {
            if (!tempMap.containsKey(dto.getSourceCode())) {
                tempMap.put(dto.getSourceCode(), dto.getSourceCode());
            }
        }
        // 所有费用明细里面的费用来源只要有来源于处方的费用就代表是处方病人
        if (tempMap.containsKey(Constants.FYLYFS.CF)) {
            isChange = true;
        } else {
            isChange = false;
        }
        return isChange;
    }


    /**
     * @param map
     * @Method updateOutptRegister
     * @Desrciption 医保统一支付平台：门诊挂号取消,
     *     先调用医保接口，取消挂号登记
     *     然后删除insure_individual_visit、insure_individual_basic表登记的数据
     *     同时更新病人类型状态
     *
     * @Param
     * @Author fuhui
     * @Date 2021/5/8 8:37
     * @Return
     */
    @Override
    public Boolean updateOutptRegister(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(map);
        String hospCode = MapUtils.get(map,"hospCode");
        String id = MapUtils.get(map,"id");
        if(insureIndividualVisitDTO ==null ){
            throw new AppException("该病人尚未医保挂号登记");
        }
        if(StringUtils.isEmpty(insureIndividualVisitDTO.getMedicalRegNo())){
            throw new AppException("该病人尚未医保挂号登记");
        }
        InsureIndividualSettleDTO insureIndividualSettleDTO = new InsureIndividualSettleDTO();
        insureIndividualSettleDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        insureIndividualSettleDTO.setSettleState("1");
        insureIndividualSettleDTO.setState("0");
        insureIndividualSettleDTO.setHospCode(hospCode);
        map.put("insureIndividualSettleDTO",insureIndividualSettleDTO);
        insureIndividualSettleDTO = insureIndividualSettleService_consumer.querySettle(map).getData();
        if(insureIndividualSettleDTO !=null && StringUtils.isNotEmpty(insureIndividualSettleDTO.getInsureSettleId())){
            throw new AppException("改患者已经做了医保结算,不能取消门诊医保登记");
        }
        // 根据医院编码、医保注册编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        Map configMap = new LinkedHashMap();
        configMap.put("hospCode", hospCode);
        configMap.put("insureConfigurationDTO", configDTO);
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请重新获取人员信息。");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            map.put("visitId",id);
            insureUnifiedPayOutptService_consumer.UP_2202(map).getData();
            insureIndividualVisitService_consumer.deleteInsureVisitById(map);
            insureIndividualCostService_consumer.deleteOutptInsureCost(map);  // 删除医保费用表数据
            InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
            insureIndividualBasicDTO.setHospCode(hospCode);
            insureIndividualBasicDTO.setId(insureIndividualVisitDTO.getMibId());
            map.put("insureIndividualBasicDTO",insureIndividualBasicDTO);
            insureIndividualBasicService_consumer.deleteInsureBasic(map).getData();
            OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
            outptVisitDTO.setPatientCode(insureIndividualVisitDTO.getPatientCode()); // 取消之后修改为登记之前的病人类型，不一定是自费病人
            outptVisitDTO.setHospCode(hospCode);
            outptVisitDTO.setId(id);
            outptVisitDAO.updateOutptVisit(outptVisitDTO);
        }
        return true;
    }

    /**
     * @Method updateOutptRegister
     * @Desrciption  医保统一支付平台：门急诊诊疗记录【4301】
     * @Param id-就诊id
     *
     * @Author luoyong
     * @Date   2021/8/20 8:37
     * @Return
     **/
    @Override
    public Boolean addOutptVisitRecordUpload(Map<String, Object> map) {
        String visitId = MapUtils.get(map, "id");
        String name = MapUtils.get(map, "name");
        if (StringUtils.isEmpty(visitId)) throw new RuntimeException("未选择需要上传诊疗记录的患者");
        map.put("visitId", visitId);

        // 根据就诊id查询挂号记录(outpt_register)
        OutptRegisterDTO outptRegisterDTO = outptRegisterDAO.getOutptRegisterByVisitId(map);
        if (outptRegisterDTO == null) {
            throw new RuntimeException("未查询到【" + name + "】相关就诊信息");
        }
        // 根据就诊id查询病历信息(outpt_medical_record)
        List<OutptMedicalRecordDTO> blList = outptVisitDAO.queryMedicalRecordByVisitId(map);

        // 根据就诊id查询诊断信息(outpt_diagnose)
        List<OutptDiagnoseDTO> zdList = outptVisitDAO.queryDiagnoseByVisitId(map);

        // 根据就诊id查询处方信息(outpt_prescribe_detail_ext)
        List<OutptPrescribeDetailsExtDTO> cfList = outptVisitDAO.queryPreDetailExtByVisitId(map);

        map.put("outptRegisterDTO", outptRegisterDTO);
        map.put("blList", blList);
        map.put("zdList", zdList);
        map.put("cfList", cfList);
        // 调用统一支付平台门急诊诊疗记录【4301】接口
        insureUnifiedPayOutptService_consumer.UP_4301(map).getData();
        return true;
    }

    /**
     * @Menthod: addOperAndRescue
     * @Desrciption: 统一支付平台-急诊留观手术及抢救信息【4302】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-23 13:50
     * @Return:
     **/
    @Override
    public Boolean addOperAndRescue(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String visitId = MapUtils.get(map, "visitId");
        String name = MapUtils.get(map, "name");
        if (StringUtils.isEmpty(visitId)) {
            throw new RuntimeException("未选择需要上传手术及抢救信息的患者");
        }

        Map visitMap = new HashMap();
        visitMap.put("hospCode", hospCode);
        visitMap.put("id", visitId);
        // 查询就诊记录
        OutptVisitDTO outptVisitDTO = outptVisitDAO.queryByVisitID(visitMap);
        if (outptVisitDTO == null) {
            throw new RuntimeException("未查询到【" + name + "】相关就诊信息，请核对！");
        }

        // 根据就诊id查询手术信息
        List<OperInfoRecordDTO> ssList = outptVisitDAO.queryOperInfoRecordByVistiId(map);
        // 根据就诊id查询抢救信息
        List qjList = new ArrayList();

        map.put("outptVisitDTO", outptVisitDTO);
        map.put("ssList", ssList);
        map.put("qjList", qjList);
        // 调用 统一支付平台-急诊留观手术及抢救信息【4302】接口
        insureUnifiedPayOutptService_consumer.UP4302(map);
        return true;
    }

    /**
     * @param outptPayDTO
     * @Menthod: getPayInfoByParams
     * @Desrciption: 获取支付信息
     * @Param: outptPayDTO
     * @Author: 廖继广
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2021-10-13 13:44
     * @Return:
     */
    @Override
    public OutptPayDTO getPayInfoByParams(OutptPayDTO outptPayDTO) {
        return outptPayDAO.getPayInfoByParams(outptPayDTO);
    }
    /**
     * @Meth: saveBackCostWithOutpt
     * @Description: 门诊病人手术补记账退费接口
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2022/2/23
     */
    @Override
    public Boolean saveBackCostWithOutpt(Map<String, Object> map) {
        List<InptCostDTO> inptCostDTOs = MapUtils.get(map,"inptCostDTOs");
        String hospCode = MapUtils.get(map, "hospCode");
        if (ListUtils.isEmpty(inptCostDTOs)){
            throw new AppException("退费数据不能为空");
        }
        List<String> costIds = inptCostDTOs.stream().map(InptCostDTO::getId).collect(Collectors.toList());
        // *******校验begin********
        // 1.查看当前费用是否结算,查出门诊费用
        List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryCostByIds(hospCode,costIds);
        List<String> errorMessage = outptCostDTOS.stream().filter(outptCostDTO -> Constants.SETTLECODE.YJS.equals(outptCostDTO.getSettleCode())).
                map(OutptCostDTO::getItemName).distinct().collect(Collectors.toList());
        if (!ListUtils.isEmpty(errorMessage)){
            throw new AppException(errorMessage.toString()+ "已结算，请去门诊退费管理退费");
        }
        // 2.检查当前是否有结算操作，如果没有 要把结算操作锁住
        // 就诊id
        String visitId = inptCostDTOs.get(0).getVisitId();
        // 根据就诊id 查询就诊信息
        Map visitMap = new HashMap();
        visitMap.put("hospCode", hospCode);
        visitMap.put("id", visitId);
        // 查询就诊记录
        OutptVisitDTO outptVisitDTO = outptVisitDAO.queryByVisitID(visitMap);
        String key = new StringBuilder(hospCode).append(StringUtils.isEmpty(visitId) ? outptVisitDTO.getCertNo() : visitId)
                .append(Constants.OUTPT_FEES_REDIS_KEY).toString();
        if (!redisUtils.setIfAbsent(key,key,600)){
            throw new AppException("退费提示：该患者正在别处结算；此时不可进行退费。");
        }
        // *******校验end********
        // 删除费用
        try {
            outptVisitDAO.deleteCostByIds(hospCode,costIds);
        }catch (Exception e){
            throw new AppException("删除费用失败");
        }finally {
            redisUtils.del(key);
        }
        return null;
    }

    /**
     * @param outptSettleDTO
     * @Menthod chargeChangeRed
     * @Desrciption 原始结算支付数据冲红处理
     * <p>
     * 1.门诊结算表数据对冲（outpt_settle）
     * 2.门诊结算发票表冲红（outpt_settle_invoice）
     * 3.门诊支付方式表冲红（outpt_pay）
     * 4.门诊合同单位支付表冲红（outpt_insure_pay)
     * </p>
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private String outptChargeChangeRed(List<OutptCostDTO> outptCostDTOList,OutptSettleDTO outptSettleDTO,OutptVisitDTO outptVisitDTO) {
        OutptSettleDTO selectDTO = new OutptSettleDTO();
        BeanUtils.copyProperties(outptSettleDTO, selectDTO);
        selectDTO.setCrteId(outptVisitDTO.getCrteId());
        selectDTO.setCrteName(outptVisitDTO.getCrteName());

        // 冲红结算ID
        String redSettleId = SnowflakeUtils.getId();

        // 门诊费用信息冲红
        this.costInfoChangeRed(outptCostDTOList,outptVisitDTO, redSettleId);

        // 门诊结算发票表冲红
        this.outptInvoiceChangeRed(selectDTO,redSettleId);

        // 门诊支付方式冲红
        this.outptPayChangeRed(selectDTO,redSettleId);

        // 门诊结算信息对冲
        this.outptSettleInfoChangeRed(selectDTO,redSettleId);

        // 是否为在线支付
        if (Constants.SF.S.equals(outptSettleDTO.getOnlinePay())) {
            // 诊间支付结算信息对冲
            this.outptPaymentSettleChangeRed(selectDTO, redSettleId);
        }

        // 门诊退费时需要更新消费异动表
        if (outptSettleDTO.getCardPrice() != null && BigDecimalUtils.greaterZero(outptSettleDTO.getCardPrice())) {
            Map<String, Object> map = new HashMap<>();
            map.put("crteId", outptVisitDTO.getCrteId());
            map.put("crteName", outptVisitDTO.getCrteName());
            map.put("settleId", outptSettleDTO.getId());
            map.put("redSettleId", redSettleId );
            map.put("hospCode", outptSettleDTO.getHospCode());
            Boolean isTrue = baseCardRechargeChangeService.saveOutptTuiFei(map);
            if (!isTrue) {
                throw new AppException("一卡通退费失败，请联系管理员");
            }
        }

        // 门诊合同单位支付表冲红
        this.outptInsurePayChangeRed1(selectDTO,redSettleId);

        return redSettleId;
    }

    /**
     * @param outptSettleDTO
     * @Menthod outptPayChangeRed
     * @Desrciption 门诊支付方式冲红
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private void outptPayChangeRed(OutptSettleDTO outptSettleDTO,String redSettleId) {
        List<OutptPayDTO> outptPayDTOlist = outptPayDAO.findOutptPayByParams(outptSettleDTO);
        if (!ListUtils.isEmpty(outptPayDTOlist)) {
            for (OutptPayDTO entity : outptPayDTOlist) {
                entity.setId(SnowflakeUtils.getId());
                entity.setSettleId(redSettleId);
                entity.setPrice(BigDecimalUtils.negate(entity.getPrice()));

                // 手续费暂时直接冲红，不处理
                entity.setServicePrice(BigDecimalUtils.negate(entity.getServicePrice()));
            }
            outptPayDAO.insertOutptPays(outptPayDTOlist);
        }
    }


    /**
     * @param outptSettleDTO
     * @Menthod outptInsurePayChangeRed
     * @Desrciption 门诊合同单位支付表冲红
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private void outptInsurePayChangeRed(OutptSettleDTO outptSettleDTO ) {
        List<OutptInsurePayDTO> outptInsurePayDTOList = outptInsurePayDAO.getOutptInsurePayByParams(outptSettleDTO);
        if (!ListUtils.isEmpty(outptInsurePayDTOList)) {
            for (OutptInsurePayDTO outptInsurePayDTO : outptInsurePayDTOList) {
                 outptInsurePayDTO.setId(SnowflakeUtils.getId());
                 outptInsurePayDTO.setTotalPrice(BigDecimalUtils.negate(outptInsurePayDTO.getTotalPrice()));
            }
            outptInsurePayDAO.insertOutptInsurePays(outptInsurePayDTOList);
        }
    }

    /**
     * @param outptSettleDTO
     * @Menthod outptInsurePayChangeRed
     * @Desrciption 门诊合同单位支付表冲红
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private void outptInsurePayChangeRed1(OutptSettleDTO outptSettleDTO,String redSettleId) {
        List<OutptInsurePayDTO> outptInsurePayDTOList = outptInsurePayDAO.getOutptInsurePayByParams(outptSettleDTO);
        if (!ListUtils.isEmpty(outptInsurePayDTOList)) {
            for (OutptInsurePayDTO outptInsurePayDTO : outptInsurePayDTOList) {
                //设置冲红结算id zjp 2022-06-09 【ID1003392】
                //报表查询>财务报表>收费员收入明细 中，把收费和退费都统计到了 黄萍 的收入中 reason:医保支付冲红和被冲红结算id一致导致
                outptInsurePayDTO.setSettleId(redSettleId);
                ////
                outptInsurePayDTO.setId(SnowflakeUtils.getId());
                outptInsurePayDTO.setTotalPrice(BigDecimalUtils.negate(outptInsurePayDTO.getTotalPrice()));
            }
            outptInsurePayDAO.insertOutptInsurePays(outptInsurePayDTOList);
        }
    }

    /**
     * @param outptSettleDTO
     * @Menthod outptInvoiceChangeRed
     * @Desrciption 门诊结算发票表冲红
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private void outptInvoiceChangeRed(OutptSettleDTO outptSettleDTO,String redSettleId) {
        // 医院编码 + 结算ID + 就诊ID + 状态为正常查询门诊结算发票表信息(发票分单有可能会产生一个结算ID多张发票的情况，此处批量对冲)
        List<OutptSettleInvoiceDTO> outptSettleInvoiceDTOList = outptSettleInvoiceDAO.getSetteleInvoiceByParams(outptSettleDTO);
        if (!ListUtils.isEmpty(outptSettleInvoiceDTOList)) {
            // 被冲红
            for (OutptSettleInvoiceDTO updateDTO : outptSettleInvoiceDTOList) {
                updateDTO.setStatusCode(Constants.ZTBZ.BCH);
            }
            outptSettleInvoiceDAO.updateOutptSettleInvoices(outptSettleInvoiceDTOList);

            // 冲红
            for (OutptSettleInvoiceDTO updateDTO : outptSettleInvoiceDTOList) {
                updateDTO.setId(SnowflakeUtils.getId());
                updateDTO.setSettleId(redSettleId);
                updateDTO.setRedId(updateDTO.getId());
                updateDTO.setStatusCode(Constants.ZTBZ.CH);
                updateDTO.setTotalPrice(BigDecimalUtils.negate(updateDTO.getTotalPrice()));
            }
            outptSettleInvoiceDAO.insertOutptSettleInvoices(outptSettleInvoiceDTOList);
        }
    }

    /**
     * @param outptSettleDTO
     * @Menthod settleInfoChangeRed
     * @Desrciption 对冲结算信息
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private void outptSettleInfoChangeRed(OutptSettleDTO outptSettleDTO,String redSettleId) {
        // 结算表数据对冲
        OutptSettleDTO selectOutptSettleDTO = outptSettleDAO.selectByPrimaryKey(outptSettleDTO.getId());
        if (selectOutptSettleDTO == null) {
            throw new AppException("退费提示：未获取到结算记录");
        }

        // 被冲红
        selectOutptSettleDTO.setStatusCode(Constants.ZTBZ.BCH);
        outptSettleDAO.updateByPrimaryKeySelective(selectOutptSettleDTO);

        // 冲红
        selectOutptSettleDTO.setId(redSettleId);
        selectOutptSettleDTO.setRedId(selectOutptSettleDTO.getId());
        selectOutptSettleDTO.setOldSettleId(outptSettleDTO.getId());
        selectOutptSettleDTO.setOneSettleId(outptSettleDTO.getOneSettleId());
        selectOutptSettleDTO.setStatusCode(Constants.ZTBZ.CH);

        // 金额置反
        selectOutptSettleDTO.setInvoicePrice(BigDecimalUtils.negate(selectOutptSettleDTO.getInvoicePrice()));
        selectOutptSettleDTO.setCardPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getCardPrice()));
        selectOutptSettleDTO.setActualPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getActualPrice()));
        selectOutptSettleDTO.setMiPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getMiPrice()));
        selectOutptSettleDTO.setRealityPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getRealityPrice()));
        selectOutptSettleDTO.setSelfPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getSelfPrice()));
        selectOutptSettleDTO.setTotalPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getTotalPrice()));
        selectOutptSettleDTO.setTruncPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getTruncPrice()));
        selectOutptSettleDTO.setAcctPay(BigDecimalUtils.negate(selectOutptSettleDTO.getAcctPay()));
        selectOutptSettleDTO.setCreditPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getCreditPrice()));

        // 创建信息
        selectOutptSettleDTO.setCrteId(outptSettleDTO.getCrteId());
        selectOutptSettleDTO.setCrteName(outptSettleDTO.getCrteName());
        selectOutptSettleDTO.setSettleTime(DateUtils.getNow());
        selectOutptSettleDTO.setCrteTime(DateUtils.getNow());

        // 清空日结缴款ID
        selectOutptSettleDTO.setDailySettleId("");
        outptSettleDAO.insert(selectOutptSettleDTO);
    }

    /**
     * @param outptCostDTO
     * @Menthod getLastFeeList
     * @Desrciption 部分退款，获取剩余收费项目的详情列表
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     * @Return OutptCostDTO
     */
    private OutptCostDTO getLastFeeList(OutptCostDTO outptCostDTO, BigDecimal lastNum,String crteId,String crteName,Integer patientCode) {
        OutptCostDTO costDto = new OutptCostDTO();
        BeanUtils.copyProperties(outptCostDTO, costDto);

        // 新主键ID
        costDto.setId(SnowflakeUtils.getId());

        // 应收数量
        costDto.setTotalNum(lastNum);
        // 发药明细汇总id
        costDto.setDistributeAllDetailId(outptCostDTO.getDistributeAllDetailId());
        // 发药明细id对应的费用id
        costDto.setOneDistCostId(outptCostDTO.getOneDistCostId());

        // 回退数量
        costDto.setBackNum(BigDecimal.ZERO);

        // 项目总金额 = 单价 * 数量
        BigDecimal lastTotalPrice = BigDecimalUtils.multiply(outptCostDTO.getPrice(), lastNum);
        costDto.setTotalPrice(lastTotalPrice);

        BigDecimal lastRealityPrice = new BigDecimal(0);
        // 优惠后金额 = 原费用优惠后金额/原费用数量*需要收取的数量
        if (BigDecimalUtils.isZero(costDto.getRealityPrice())) {
            costDto.setRealityPrice(costDto.getRealityPrice());
        } else {
            lastRealityPrice = BigDecimalUtils.multiply(BigDecimalUtils.divide(costDto.getRealityPrice(), outptCostDTO.getTotalNum()), lastNum);
            costDto.setRealityPrice(lastRealityPrice);
        }

        // 优惠金额 = 项目总金额 - 优惠后金额
        BigDecimal lastPreferentialPrice = BigDecimalUtils.subtract(lastTotalPrice, lastRealityPrice);
        costDto.setPreferentialPrice(lastPreferentialPrice);

        // 未结算
        costDto.setSettleCode(Constants.JSZT.WJS);
		// 退费后重新生成的费用结算id需要置空
		costDto.setSettleId(null);
        costDto.setCrteId(crteId);
        costDto.setCrteName(crteName);
        costDto.setCrteTime(DateUtils.getNow());
        /*医保病人部分退费标识 start lly 2022-08-31*/
        if (patientCode!=null&&patientCode>0) {
            costDto.setIsBackReentry(Constants.SF.S);
        }else {
            costDto.setIsBackReentry(Constants.SF.F);
        }
        /*医保病人部分退费标识 end lly 2022-08-31*/
        // 官红强 部分退费重新收取部分需要记录原费用ID
		costDto.setOldCostId(outptCostDTO.getId());
        return costDto;
    }

    /**
     * @param outptCostDTOList，list
     * @Menthod updateCostInfo
     * @Desrciption 部分退款，剩余项目重新划价收费已发药的无需再发药。
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private void insertCostInfo(List<OutptCostDTO> outptCostDTOList,List<String> list) {
        if (!ListUtils.isEmpty(list)) {
            for (String itemId : list) {
                for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                    if (outptCostDTO.getItemId().equals(itemId)) {
                        outptCostDTO.setIsDist(Constants.FYZT.FY);
                        break;
                    }
                }
            }
        }
        // 1.部分未退部分批量插入
        outptCostDAO.batchInsert(outptCostDTOList);
    }

    /**
     * @param pharOutReceiveDTO
     * @Menthod updateNoSendPharOutReceiveInfo
     * @Desrciption 未发药-处理门诊领用申请信息
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private void updateNoSendPharOutReceiveInfo(PharOutReceiveDTO pharOutReceiveDTO) {
        // 删除门诊领药申请信息
        outptCostDAO.deletePharOutReceiveById(pharOutReceiveDTO);

        // 删除门诊领用申请详细信息
        outptCostDAO.deletePharOutReceiveDetailById(pharOutReceiveDTO);
    }

    /**
     * @Menthod updateSendPharOutDistributeInfo
     * @Desrciption 已发药-处理门诊发药和明细信息
     * <p>
     * 1、当前退药数量：门诊退药数量，当次退药数量。
     * 2、累计退药数量：针对多次退药设计，多次退药的累计退药数量。
     * 3、上期批号结余数量：未扣减库存明细表中库存时的批号结余数量。
     * 4、本期批号结余数量：已扣减库存明细表中库存时的批号结余数量。
     * 5、原费用明细ID：发药明细对应的费用明细ID。
     * 6、原发药明细ID：对应原始的发药明细ID，退药时如果不是全退，会产生两条记录，一条为冲红记录，一条为（原发药数量 - 退药数量）的发药记录，如果全退，只产生一条冲红记录。
     * 7、退药状态代码（TYZT）:0：已发药 1：退药未退费 2：退药已退费
     * </p>
     * @Author liaojiguang
     * @Date 2020/9/07 09:15
     */
    private Map<String,Object> updateSendPharOutDistributeInfo(OutptVisitDTO outptVisitDTO, List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOS, Boolean isAllOut, List<OutptCostDTO> outptCostDTOList, List<String> itemIdList) {
        Map<String,Object> resultMap = new HashMap<>();
        List<PharOutDistributeAllDetailDTO> updateList = new ArrayList<>();

        // 3、判断病人类型，门诊直接划价收费 or 处方病人
        boolean patientIsCF = true;  // 处方病人：true   直接划价收费病人： false
        if (StringUtils.isEmpty(outptVisitDTO.getId())) {
            patientIsCF = false;
        } else {
            // 根据就诊id查询门诊费用明细，费用来源有处方，也有直接划价收费的为处方病人； 费用来源只有直接划价收费的表示是直接划价收费病人
            patientIsCF = this.checkPatientIsMZ(outptVisitDTO);
        }
        if (patientIsCF) {
            for (PharOutDistributeAllDetailDTO pharOutDistributeBatchDetailDTO : pharOutDistributeBatchDetailDTOS) {
                // 已退部分-是否已经退药和数量核对
                for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                    //为0的退药数据不计算
                    if(BigDecimalUtils.compareTo(outptCostDTO.getBackNum(), BigDecimal.ZERO ) <= 0){
                        // 药品已全退，或者部分退时，校验退费数量是否与退药数量一致
                        if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
                                && ((pharOutDistributeBatchDetailDTO.getOpdId() == null && outptCostDTO.getOpdId() == null) || (StringUtils.isNotEmpty(pharOutDistributeBatchDetailDTO.getOpdId())&&pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())))
                                && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId()) && pharOutDistributeBatchDetailDTO.getCostId().equals(outptCostDTO.getId())) {
                            /**
                             *  退药数量核对逻辑处理
                             *  药房未退药数量 = 总数量 - 累计退药数量
                             *  前端未退药数量 = 新费用记录数量 - 回退数量
                             */

                            // 药房未退药数量
                            BigDecimal pharLastNum = BigDecimalUtils.subtract(pharOutDistributeBatchDetailDTO.getNum(), pharOutDistributeBatchDetailDTO.getTotalBackNum());

                            // 前端未退药数量
                            BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(), outptCostDTO.getBackNum());
                            Boolean isFlag = BigDecimalUtils.equals(pharLastNum, lastNum);
                            if (!isFlag) {
                                if (pharOutDistributeBatchDetailDTO.getTotalBackNum().compareTo(BigDecimal.ZERO) == 0) {
                                    throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                                } else {
                                    BigDecimal infactackNum = BigDecimalUtils.subtract(outptCostDTO.getBackNum(), BigDecimalUtils.subtract(pharLastNum, lastNum));
                                    throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】药房退药数量为【" + infactackNum + "】,请核对数量");
                                }
                            }
                        }
                        continue;
                    }
                    if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
                            && ((pharOutDistributeBatchDetailDTO.getOpdId() == null && outptCostDTO.getOpdId() == null) || (StringUtils.isNotEmpty(pharOutDistributeBatchDetailDTO.getOpdId())&&pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())))
                            && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId()) && pharOutDistributeBatchDetailDTO.getCostId().equals(outptCostDTO.getId())) {
                        // 部分退且已经发药
                        if (!isAllOut) {
                            itemIdList.add(outptCostDTO.getItemId());
                        }

                        /**
                         *  退药数量核对逻辑处理
                         *  药房未退药数量 = 总数量 - 累计退药数量
                         *  前端未退药数量 = 新费用记录数量 - 回退数量
                         */

                        // 药房未退药数量
                        BigDecimal pharLastNum = BigDecimalUtils.subtract(pharOutDistributeBatchDetailDTO.getNum(),pharOutDistributeBatchDetailDTO.getTotalBackNum());

                        // 前端未退药数量
                        BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(),outptCostDTO.getBackNum());
                        Boolean isFlag = BigDecimalUtils.equals(pharLastNum,lastNum);
                        if (!isFlag) {
                            if (pharOutDistributeBatchDetailDTO.getTotalBackNum().compareTo(BigDecimal.ZERO) == 0) {
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                            } else {
                                BigDecimal infactackNum = BigDecimalUtils.subtract(outptCostDTO.getBackNum(),BigDecimalUtils.subtract(pharLastNum,lastNum));
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】药房退药数量为【" + infactackNum +"】,请核对数量");
                            }
                        }
                    } else if (Constants.TYZT.TFWTY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())) {
                        pharOutDistributeBatchDetailDTO.setStatusCode(Constants.TYZT.TYYTF);
                        updateList.add(pharOutDistributeBatchDetailDTO);
                    }
                }
            }
        } else {
            for (PharOutDistributeAllDetailDTO pharOutDistributeBatchDetailDTO : pharOutDistributeBatchDetailDTOS) {
                // 已退部分-是否已经退药和数量核对
                for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                    //为0的退药数据不计算
                    if(BigDecimalUtils.compareTo(outptCostDTO.getBackNum(), BigDecimal.ZERO ) <= 0){
                        // 药品已全退，或者部分退时，校验退费数量是否与退药数量一致
                        if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
                                && ((pharOutDistributeBatchDetailDTO.getOpdId() == null && outptCostDTO.getOpdId() == null) || (StringUtils.isNotEmpty(pharOutDistributeBatchDetailDTO.getOpdId())&&pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())))
                                && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId()) && pharOutDistributeBatchDetailDTO.getCostId().equals(outptCostDTO.getId())) {
                            /**
                             *  退药数量核对逻辑处理
                             *  药房未退药数量 = 总数量 - 累计退药数量
                             *  前端未退药数量 = 新费用记录数量 - 回退数量
                             */
                            // 药房未退药数量
                            BigDecimal pharLastNum = BigDecimalUtils.subtract(pharOutDistributeBatchDetailDTO.getNum(), pharOutDistributeBatchDetailDTO.getTotalBackNum());

                            // 前端未退药数量
                            BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(), outptCostDTO.getBackNum());
                            Boolean isFlag = BigDecimalUtils.equals(pharLastNum, lastNum);
                            if (!isFlag) {
                                if (pharOutDistributeBatchDetailDTO.getTotalBackNum().compareTo(BigDecimal.ZERO) == 0) {
                                    throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                                } else {
                                    BigDecimal infactackNum = BigDecimalUtils.subtract(outptCostDTO.getBackNum(), BigDecimalUtils.subtract(pharLastNum, lastNum));
                                    throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】药房退药数量为【" + infactackNum + "】,请核对数量");
                                }
                            }
                        }
                        continue;
                    }
                    if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
 //                           && pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())
                            && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId()) && pharOutDistributeBatchDetailDTO.getCostId().equals(outptCostDTO.getId())) {
                        // 部分退且已经发药
                        if (!isAllOut) {
                            itemIdList.add(outptCostDTO.getItemId());
                        }

                        /**
                         *  退药数量核对逻辑处理
                         *  药房未退药数量 = 总数量 - 累计退药数量
                         *  前端未退药数量 = 新费用记录数量 - 回退数量
                         */

                        // 药房未退药数量
                        BigDecimal pharLastNum = BigDecimalUtils.subtract(pharOutDistributeBatchDetailDTO.getNum(),pharOutDistributeBatchDetailDTO.getTotalBackNum());

                        // 前端未退药数量
                        BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(),outptCostDTO.getBackNum());
                        Boolean isFlag = BigDecimalUtils.equals(pharLastNum,lastNum);
                        if (!isFlag) {
                            if (pharOutDistributeBatchDetailDTO.getTotalBackNum().compareTo(BigDecimal.ZERO) == 0) {
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】已发药，请先去药房退药");
                            } else {
                                BigDecimal infactackNum = BigDecimalUtils.subtract(outptCostDTO.getBackNum(),BigDecimalUtils.subtract(pharLastNum,lastNum));
                                throw new AppException("退费提示：【" + outptCostDTO.getItemName() + "】药房退药数量为【" + infactackNum +"】,请核对数量");
                            }
                        }
                    } else if (Constants.TYZT.TFWTY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())) {
                        pharOutDistributeBatchDetailDTO.setStatusCode(Constants.TYZT.TYYTF);
                        updateList.add(pharOutDistributeBatchDetailDTO);
                    }
                }
            }
        }


        if (!ListUtils.isEmpty(updateList)) {
            outptCostDAO.updatePharOutDistributeBatchDetails(updateList);
        }
        return resultMap;
    }


    /**
     * @param outptCostDTOList
     * @Menthod costInfoChangeRed
     * @Desrciption 门诊费用信息批量冲红
     * @Author liaojiguang
     * @Date 2020/9/07 15:21
     */
    private void costInfoChangeRed(List<OutptCostDTO> outptCostDTOList,OutptVisitDTO outptVisitDTO, String redSettleId) {
        if (!ListUtils.isEmpty(outptCostDTOList)) {
            // 被冲红
            for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                outptCostDTO.setStatusCode(Constants.ZTBZ.BCH);
            }
            outptCostDAO.updateList(outptCostDTOList);

            // 冲红
            for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                outptCostDTO.setOldCostId(outptCostDTO.getId());

                // 新主键ID
                outptCostDTO.setId(SnowflakeUtils.getId());
                outptCostDTO.setStatusCode(Constants.ZTBZ.CH);

                // 设置退费结算id
                outptCostDTO.setSettleId(redSettleId);

                // 金额置反
                outptCostDTO.setTotalPrice(BigDecimalUtils.negate(outptCostDTO.getTotalPrice()));
                outptCostDTO.setPreferentialPrice(BigDecimalUtils.negate(outptCostDTO.getPreferentialPrice()));
                outptCostDTO.setRealityPrice(BigDecimalUtils.negate(outptCostDTO.getRealityPrice()));
                // 数量置反
                outptCostDTO.setNum(BigDecimalUtils.negate(outptCostDTO.getNum()));
                outptCostDTO.setTotalNum(BigDecimalUtils.negate(outptCostDTO.getTotalNum()));

                // 创建信息
                outptCostDTO.setCrteId(outptVisitDTO.getCrteId());
                outptCostDTO.setCrteName(outptVisitDTO.getCrteName());
                outptCostDTO.setCrteTime(DateUtils.getNow());
            }
            outptCostDAO.batchInsert(outptCostDTOList);
        }
    }

    // 退费成功后，调用体检接口
    public void phyIsCallBack(List<OutptCostDTO> outptCostDTOS){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Map queryMap = new HashMap();
        queryMap.put("hospCode", outptCostDTOS.get(0).getHospCode());
        queryMap.put("code", "TJ_URL");
        SysParameterDTO sysParameterDTO= sysParameterService_consumer.getParameterByCode(queryMap).getData();
        String url ="";
        if(sysParameterDTO!=null){
            url = sysParameterDTO.getValue() +"/updateReturnCost";
        }else {
            url ="http://172.26.62.219:8899/hsa-phys/web/phys/interface/his/updateReturnCost";
        }
        stringObjectMap.put("url",url);
        List<Map> maps =new ArrayList<>();
        if(outptCostDTOS!=null && outptCostDTOS.size()>0) {
            for (OutptCostDTO outptCostDTO:outptCostDTOS) {
                Map<String, Object> item =new HashMap<>();
                if (BigDecimalUtils.less(new BigDecimal(0),outptCostDTO.getBackNum())) {
                    item.put("HospCode", outptCostDTO.getHospCode());
                    item.put("VisitId", outptCostDTO.getVisitId());
                    item.put("ItemId", outptCostDTO.getItemId());
                    item.put("returnPrice", outptCostDTO.getRealityPrice());
                    item.put("SettleId", outptCostDTO.getSettleId());
                    maps.add(item);
                }
            }
        }
        String json = JSONObject.toJSONString(maps);
        stringObjectMap.put("param",json);
        logger.info("体检退费入参:" + json);
        String resultStr = HttpConnectUtil.doPost(stringObjectMap);
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("体检退费反参信息为空，请联系管理员。");
        }
        logger.info("体检退费反参:" + resultStr);
        JSONObject resultObj = JSON.parseObject(resultStr);
        String code = resultObj.get("code").toString();
        if (!"0".equals(code)) {
            throw new AppException((String) resultObj.get("message"));
        }
    }

    /**
     * @param outptVisitDTO,outptSettleDTO
     * @Menthod updateOutptOnlinePayOutFee
     * @Desrciption  诊间支付支付门诊退费
     * * 2021年5月11日15:06:25 退费修改，支持部分退费自动收费，关键点：
     * 1、医保病人部分退费时，先全退，再自动生成未退部分的收费信息，再由收费员去收费
     * 2、自费病人部分退费时，只退要退的部分（暂不考虑支付方式，只需要校验输入金额与应退金额相等即可），未退部分自动收费，不再由收费员收费
     * 3、诊间支付部分退费时，先全退，再自动生成未退部分的收费信息，再由收费员去收费
     * @Author liuliyun
     * @Date 2022/09/06 09:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    @Async
    public synchronized WrapperResponse updateOutptOnlinePayOutFee(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDTO> tkOutptPayDTOList) {
        // TODO 必要入参获取 [就诊ID：visitId , 医院编码：hospCode , 结算ID：settleId , 费用列表信息：allCostDTOList]
        Map selectMap = new HashMap<>();
        boolean isInsurePaitentPartBack = false;
        String visitId = outptSettleDTO.getVisitId();
        String hospCode = outptSettleDTO.getHospCode();
        String settleId = outptSettleDTO.getId();
        String crteId = outptVisitDTO.getCrteId();
        String hospName = outptVisitDTO.getHospName();
        String crteName = outptVisitDTO.getCrteName();
        BigDecimal settleSelfPrice = outptSettleDTO.getSelfPrice() == null? new BigDecimal(0) : outptSettleDTO.getSelfPrice();  // 已经支付的总金额
        BigDecimal settleCardPrice = outptSettleDTO.getCardPrice() == null? new BigDecimal(0) : outptSettleDTO.getCardPrice(); // 已结算记录卡支付金额
        BigDecimal settleActualPrice = outptSettleDTO.getActualPrice() == null ? new BigDecimal(0) : outptSettleDTO.getActualPrice();  // 已经支付的划价收费记录中 个人自付金额除一卡通支付后的其他方式支付总和

        selectMap.put("visitId", visitId);
        selectMap.put("hospCode", hospCode);
        selectMap.put("settleId", settleId);
        selectMap.put("oneSettleId", outptSettleDTO.getOneSettleId());

        // 门诊费用信息列表入参获取
        List<OutptCostDTO> allCostDTOList = outptVisitDTO.getOutptCostDTOList();

        // 部分退剩下要收费的项目
        List<OutptCostDTO> costDTOList = new ArrayList<>();

        // TODO 基本异常情况判断 - 获取原始结算数据
        OutptSettleDTO oldOutptSettleDTO = outptSettleDAO.selectByPrimaryKey(settleId);
        // 获取原始诊间结算数据
        outptSettleDTO.setSettleId(settleId);
        outptSettleDTO.setVisitId(visitId);
        PaymentSettleDTO paymentSettleDTO = outptSettleDAO.queryPaymentSettle(outptSettleDTO);
        // 获取要退费的病人类型
        if (StringUtils.isEmpty(oldOutptSettleDTO.getPatientCode())){
            oldOutptSettleDTO.setPatientCode("0");
        }
        Integer patientCode = Integer.parseInt(oldOutptSettleDTO.getPatientCode());
        if (oldOutptSettleDTO == null) {
            throw new AppException("退费失败：未找到结算信息");
        }

        // 2021-9-10 14:21:58  官红强  门诊退费时，如果退了医技，需要将医技申请表数据删除  start===============================
        List<OutptCostDTO> deleteMedicApplyList = new ArrayList<>();
        // 2021-9-10 14:21:58  官红强  门诊退费时，如果退了医技，需要将医技申请表数据删除  end  ===============================

        // TODO 回退数量异常判断 - 回退总数量
        BigDecimal backNumSum = BigDecimal.ZERO;
        BigDecimal moneyAgain = new BigDecimal(0);
        for (OutptCostDTO outptCostDTO : allCostDTOList) {
            // 应收数量 = 收费数量 - 回退数量
            BigDecimal lastNum = BigDecimalUtils.subtract(outptCostDTO.getTotalNum(), outptCostDTO.getBackNum());
            int compNum = BigDecimalUtils.compareTo(lastNum, BigDecimal.ZERO);
            if (compNum == -1) {
                throw new AppException(outptCostDTO.getItemName() + "退费数量不能大于已收费数量");
            }

            // 部分退: 获取未退项目
            if (compNum == 1) {
                costDTOList.add(this.getLastFeeList(outptCostDTO, lastNum,crteId,crteName,patientCode));
                BigDecimal rePrice = BigDecimalUtils.divide(outptCostDTO.getRealityPrice(), outptCostDTO.getTotalNum());
                moneyAgain = BigDecimalUtils.add(moneyAgain, BigDecimalUtils.multiply(rePrice, lastNum));
            } else { // 全退的记录下来，关于医技的需要更新状态（已退费）
                deleteMedicApplyList.add(outptCostDTO);
            }
            backNumSum = BigDecimalUtils.add(backNumSum,outptCostDTO.getBackNum());
        }
        if (BigDecimalUtils.compareTo(backNumSum, BigDecimal.ZERO) <= 0) {
            throw new AppException("退费数量不能全为零");
        }
        // 退费的项目需要更新医技申请状态
        if (!ListUtils.isEmpty(deleteMedicApplyList)) {
            outptCostDAO.updateMedicApply(visitId, hospCode, "05", deleteMedicApplyList);
        }

        // TODO costDTOList 为空表示全退，否则表示部分退(否:false，是:true)
        Boolean isAllOut = false;
        if (ListUtils.isEmpty(costDTOList)) {
            isAllOut = true;
        }

        // TODO 根据结算ID + 就诊ID + 医院编码（selectMap）获取门诊领药申请信息（phar_out_receive）
        List<PharOutReceiveDTO> pharOutReceiveDTOList = outptCostDAO.getPahrOutReceiceInfoBySelectMap(selectMap);
        if (!ListUtils.isEmpty(pharOutReceiveDTOList)) {
            for (PharOutReceiveDTO pharOutReceiveDTO : pharOutReceiveDTOList) {
                if (Constants.FYZT.PY.equals(pharOutReceiveDTO.getStatusCode())) {
                    throw new AppException("退费失败：已经完成配药，请先去药房取消配药");
                }
                // 1.处理门诊领药申请信息
                this.updateNoSendPharOutReceiveInfo(pharOutReceiveDTO);
            }
        }

        // TODO 是否已发药 - 能查询到发药记录说明存在发药记录,已完成退药的项目可退费
        List<PharOutDistributeDTO> distributeList = outptCostDAO.queryPharOutDistribute(selectMap);

        // 已发药项目集合
        List<String> itemIdList = new ArrayList<>();
        if (!ListUtils.isEmpty(distributeList)) {
            // 查询发药批次明细汇总表
            List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOS = outptCostDAO.queryPharOutDistributeDetailIByList(distributeList);
            // 3.处理门诊发药信息
            this.updateSendPharOutDistributeInfo(outptVisitDTO,pharOutDistributeBatchDetailDTOS,isAllOut,allCostDTOList,itemIdList);
        }

        // TODO 部分退处理未退项目，更新费用信息，处方结算标志修改，已结算 -> 未结算
        if (!isAllOut) {
            this.insertCostInfo(costDTOList,itemIdList);
            this.updateOutptPrescribe(oldOutptSettleDTO);
        }

        // TODO 门诊费用、结算、支付信息冲红
        String redSettleId = this.outptChargeChangeRed(allCostDTOList,outptSettleDTO,outptVisitDTO);

        // TODO 医保病人处理
        selectMap.put("id",visitId);
        OutptVisitDTO outpt = outptVisitDAO.queryByVisitID(selectMap);
        if (outpt == null) {
            throw new AppException("患者就诊数据出现异常【" + visitId + "】，请联系管理员");
        }
        // 病人类型为空 给默认值  lly 20211026
        if (StringUtils.isEmpty(outpt.getPatientCode())){
            outpt.setPatientCode("0");
        }

        // add 退费时获取病人结算记录中病人类型进行退费，而不是取就诊信息表中的病人类型，防止复诊时出现异常情况
        // by liaojiguang on 2021-11-23
        if (StringUtils.isEmpty(oldOutptSettleDTO.getPatientCode())){
            oldOutptSettleDTO.setPatientCode("0");
        }
        Integer patientCodeValue = Integer.parseInt(oldOutptSettleDTO.getPatientCode());

        Map<String, Object> tkMap = new HashMap<>();  // 将页面的退款支付信息转换为map
        for (OutptPayDTO outptPayDto : tkOutptPayDTOList) {
            tkMap.put(outptPayDto.getPayCode(), outptPayDto.getPrice());
        }
        // <----诊间支付退费 2022-09-06  liuliyun start-------->
        OutptPayDTO wxOutptPayDTO = outptPayDAO.selectPaymentSettlePay(selectMap);
        BigDecimal wxTkPrice = new BigDecimal(0.00);
        for (Map.Entry<String, Object> entry : tkMap.entrySet()) {
            String tkPayCode = entry.getKey(); // 支付类型
            if(Constants.ZFFS.WX.equals(tkPayCode)){
                wxTkPrice = (BigDecimal) entry.getValue();  // 支付金额
                break;
            }
        }
        if (BigDecimalUtils.greaterZero(wxTkPrice)){
            BigDecimal orgWxPrice =wxOutptPayDTO.getPrice();
            if (BigDecimalUtils.greater(wxTkPrice,orgWxPrice)){
                throw new AppException("退费失败，微信退款金额大于结算时微信支付的金额");
            }else {
                /*todo 调用支付平台退款接口*/
                Map<String,Object> paymentParam =new HashMap<>();
                paymentSettleDTO.setOldSettleId(settleId);
                paymentSettleDTO.setSettleId(redSettleId);
                paymentParam.put("paymentSettleDTO",paymentSettleDTO);
                paymentParam.put("hospCode",hospCode);
                Map<String,Object> queryResult = outptPaymentService_consumer.updatePaymentRefund(paymentParam);
                String refundStatus = MapUtils.get(queryResult,"refundStatus"); // 支付平台退款状态
                if (Constants.ZJ_PAY_TKZT.TKCG.equals(refundStatus)){ // 退款成功
                    // 体检回调
                    if (outptSettleDTO!=null &&"1".equals(outptSettleDTO.getIsPhys())) {
                        phyIsCallBack(allCostDTOList);
                    }
                    Map insurePaitentInfo = new HashMap();
                    insurePaitentInfo.put("outptVisit", outpt); // 返回病人信息
                    insurePaitentInfo.put("isInsurePaitentPartBack", isInsurePaitentPartBack); // 是否医保病人部分退费
                    return WrapperResponse.success(insurePaitentInfo);
                }else if (Constants.ZJ_PAY_TKZT.TKSB.equals(refundStatus)||Constants.ZJ_PAY_TKZT.TKYC.equals(refundStatus)){ // 退款失败或退款异常
                    String errorMessage = MapUtils.get(queryResult,"failCause",""); // 支付平台退款状态
                    throw new AppException("退费失败，调用支付平台接口出错，错误信息如下："+errorMessage);
                }else if (Constants.ZJ_PAY_TKZT.TKZ.equals(refundStatus)){ // 退款中
                    /*todo 退款中状态需要调用退款查询接口轮询  异步还是其他方式有待讨论*/
                    this.paymentRefundQuery(paymentParam);
                }
            }
        }else {
            if (!ListUtils.isEmpty(costDTOList) && patientCodeValue < 1) {
                Map<String, Object> map = new HashMap<>();
                if (StringUtils.isEmpty(outptSettleDTO.getVisitId())) {
                    throw new AppException("未获取到就诊信息");
                }
                map.put("visitId", outptSettleDTO.getVisitId());
                map.put("hospCode", hospCode);
                OutptVisitDTO outptVisitDTOFee = outptVisitDAO.getVisitByParams(map);
                outptVisitDTOFee.setOutptCostDTOList(costDTOList);
                outptVisitDTOFee.setCrteName(crteName);
                outptVisitDTOFee.setCrteId(crteId);
                outptVisitDTOFee.setTfcsMark("tfcs"); // 退费重收标记
                // 退费重收取原始结算表中的病人类型  2022-03-28 lly
                outptVisitDTOFee.setPatientCode(oldOutptSettleDTO.getPatientCode());
                outptVisitDTOFee.setTruncPrice(outptSettleDTO.getTruncPrice()); // 退费时，原结算时舍入金额
                Map setteleParam = new HashMap();
                setteleParam.put("hospCode", hospCode);
                setteleParam.put("outptVisitDTO", outptVisitDTOFee);
                WrapperResponse wrapperResponse = outptTmakePriceFormService_consumer.saveOutptSettleMoney(setteleParam);
                if (wrapperResponse.getCode() == (-1)) {
                    throw new AppException(wrapperResponse.getMessage());
                }

                Map chargeMap = (Map) wrapperResponse.getData();
                OutptSettleDO outptSettleDo = (OutptSettleDO) chargeMap.get("outptSettle");
                OutptSettleDTO newOutptSettleDTO = new OutptSettleDTO();
                BeanUtils.copyProperties(outptSettleDo, newOutptSettleDTO);

                // 需要重新设置创建人员
                OutptVisitDTO visitDTO = (OutptVisitDTO) chargeMap.get("outptVisit");
                visitDTO.setCrteId(crteId);
                visitDTO.setCrteName(crteName);
                visitDTO.setTfcsMark("tfcs"); // 退费重收标记
                visitDTO.setTruncPrice(outptSettleDTO.getTruncPrice()); // 退费时，原结算时舍入金额

                // 2021年9月1日15:25:54 在院支付方式上扣除费用  1、先查询原支付方式 ===========官红强==start===========================================================================
                List<OutptPayDTO> oldOutptPayList = outptPayDAO.selectOutptPatByVisitIdAndSettleId(selectMap);

                Map<String, Object> oldOutptPayMap = new HashMap<>();
                if (!ListUtils.isEmpty(oldOutptPayList)) {
                    for (OutptPayDTO dto : oldOutptPayList) {
                        oldOutptPayMap.put(dto.getPayCode(), dto.getPrice());
                    }
                }
                BigDecimal otherPayCodePrice = new BigDecimal(0); // 需要其他支付方式扣除的金额
                for (Map.Entry<String, Object> entry : tkMap.entrySet()) {
                    String tkPayCode = entry.getKey(); // 支付类型
                    BigDecimal tkPrice = (BigDecimal) entry.getValue();  // 支付金额
                    if (oldOutptPayMap.containsKey(tkPayCode)) {
                        BigDecimal oldPrice = (BigDecimal) oldOutptPayMap.get(tkPayCode);  // 原支付方式的支付金额
                        // 当前支付方式的退款金额 大于 原支付方式支付金额
                        if (!BigDecimalUtils.isZero(tkPrice) && BigDecimalUtils.greater(tkPrice, oldPrice)) {
                            oldOutptPayMap.put(tkPayCode, BigDecimalUtils.subtract(oldPrice, tkPrice)); // 更新原支付方式的支付金额为，原支付金额 - 现退款金额
                        } else {
                            // 当前支付方式的退款金额与原支付方式支付金额相等
                            if (BigDecimalUtils.equals(tkPrice, oldPrice)) {
                                oldOutptPayMap.put(tkPayCode, BigDecimalUtils.negate(tkPrice)); // 原支付方式金额与退款金额一致，取负数
                            } else {
                                oldOutptPayMap.put(tkPayCode, BigDecimalUtils.subtract(oldPrice, tkPrice));
                            }
                        }
                    } else {
                        oldOutptPayMap.put(tkPayCode, BigDecimalUtils.negate(tkPrice));
                    }
                }
                for (int i = 1; i < 8; i++) {
                    if (oldOutptPayMap.containsKey(i + "")) {
                        BigDecimal oldPrice2 = (BigDecimal) oldOutptPayMap.get(i + "");
                        if (BigDecimalUtils.isZero(oldPrice2)) {
                            oldOutptPayMap.remove(i + "");
                        }
                    }
                }
                BigDecimal totalOldPriceAgain = new BigDecimal(0);
                for (Map.Entry<String, Object> entry : oldOutptPayMap.entrySet()) {
                    totalOldPriceAgain = BigDecimalUtils.add(totalOldPriceAgain, (BigDecimal) entry.getValue());
                }
                // 2021年9月1日15:58:15 ==============================================官红强==end==============================================================

                // 划价收费接口支付接口调用(支付方式默认为现金)
                List<OutptPayDO> outptPayDOlist = new ArrayList();

                // 没有使用卡支付,全部其他方式支付
                if (BigDecimalUtils.isZero(settleCardPrice)) {
//            if (!BigDecimalUtils.equals(totalOldPriceAgain, BigDecimalUtils.subtract(moneyAgain, outptSettleDo.getTruncPrice() == null? new BigDecimal(0): outptSettleDo.getTruncPrice()))) {
//                throw new AppException("退费自动重收(未使用一卡通)时，计算各支付方式支付金额出错,刷新重试");
//            }
                    for (Map.Entry<String, Object> entry : oldOutptPayMap.entrySet()) {
                        OutptPayDO OutptPayDO = new OutptPayDTO();
                        OutptPayDO.setPrice((BigDecimal) entry.getValue());
                        OutptPayDO.setPayCode(entry.getKey());
                        outptPayDOlist.add(OutptPayDO);
                    }
                    // 设置一卡通卡号与卡支付金额
                    visitDTO.setCardNo(null);
                    visitDTO.setCardPrice(null);
                    visitDTO.setProfileId(null);
                }
                // 部分卡支付，部分其他方式支付
                BigDecimal tempPrice = new BigDecimal(0);
                BigDecimal cardPriceAgain = new BigDecimal(0); // 一卡通再收金额
                if (BigDecimalUtils.greaterZero(settleCardPrice) && BigDecimalUtils.less(settleCardPrice, settleSelfPrice)) {
                    tempPrice = BigDecimalUtils.subtract(moneyAgain, settleActualPrice); // 重收金额 - 已经付现的金额
                    if (BigDecimalUtils.greaterZero(tempPrice)) { // 再收金额大于已经支付的现金金额
                        cardPriceAgain = BigDecimalUtils.subtract(tempPrice, outptSettleDo.getTruncPrice() == null ? new BigDecimal(0) : outptSettleDo.getTruncPrice());
                        // 设置一卡通卡号与卡支付金额
                        visitDTO.setCardNo(outptSettleDTO.getCardNo());
                        visitDTO.setCardPrice(cardPriceAgain);
                        visitDTO.setProfileId(outptSettleDTO.getProfileId());
                    } else {
                        // 设置一卡通卡号与卡支付金额
                        visitDTO.setCardNo(null);
                        visitDTO.setCardPrice(null);
                        visitDTO.setProfileId(null);
                    }
                    if (!BigDecimalUtils.equals(BigDecimalUtils.add(totalOldPriceAgain, outptSettleDo.getTruncPrice()), moneyAgain)) {
                        throw new AppException("退费失败，自动重收时，计算一卡通与其他支付方式支付金额出错,请前往划价收费界面手动结算");
                    }
                    for (Map.Entry<String, Object> entry : oldOutptPayMap.entrySet()) {
                        OutptPayDO OutptPayDO = new OutptPayDTO();
                        OutptPayDO.setPrice((BigDecimal) entry.getValue());
                        OutptPayDO.setPayCode(entry.getKey());
                        outptPayDOlist.add(OutptPayDO);
                    }
                }
                //  全部卡支付，退费自动收时其他支付方式为空
                if (BigDecimalUtils.equals(settleCardPrice, settleSelfPrice)) {
                    cardPriceAgain = BigDecimalUtils.subtract(moneyAgain, outptSettleDo.getTruncPrice() == null ? new BigDecimal(0) : outptSettleDo.getTruncPrice());
                    // 设置一卡通卡号与卡支付金额
                    visitDTO.setCardNo(outptSettleDTO.getCardNo());
                    visitDTO.setCardPrice(cardPriceAgain);
                    visitDTO.setProfileId(outptSettleDTO.getProfileId());
                }

                Map<String, Object> chargeParams = new HashMap<>();


                // 判断是否使用发票
//        if (StringUtils.isNotEmpty(outptSettleDTO.getInvoiceNo())) {
//            newOutptSettleDTO.setIsInvoice(true);
//        } else {
//            newOutptSettleDTO.setIsInvoice(false);
//        }
                newOutptSettleDTO.setIsInvoice(false);

                // 金额保持一致
                newOutptSettleDTO.setActualPrice(newOutptSettleDTO.getRealityPrice());
                newOutptSettleDTO.setCrteId(crteId);
                newOutptSettleDTO.setCrteName(crteName);


                chargeParams.put("outptPayDOList", outptPayDOlist);
                chargeParams.put("outptVisitDTO", visitDTO);
                chargeParams.put("outptSettleDTO", newOutptSettleDTO);
                chargeParams.put("hospCode", hospCode);
                // outptTmakePriceFormService_consumer.saveOutptSettle(chargeParams);


                /**
                 *   当取消结算的时候 出现医保和his单边账
                 *   1.医保已经取消  而his没有取消结算 这个时候就需要调用单边账（取消结算单边功能）
                 *   2.作废医保结算记录 置空医保结算id  把病人类型改完自费病人。his结算表变为自费病人
                 *   3.再把患者变成自费病人结算，结算完成以后（需要判断医保是否有作废的数据）  再修改病人类型
                 *   4.病人类型的值存在redis里面中（取消结算单边功能存入）
                 */
                selectMap.put("settleId", settleId);
                List<InsureIndividualSettleDTO> settleDTOList = outptSettleDAO.queryOutptSettle(selectMap);
                String redisKey = new StringBuilder().append(hospCode).append("^").append(visitId).
                        append("2208").append("^").toString();
                if (ListUtils.isEmpty(settleDTOList)) {
                    String redisValue = redisUtils.get(redisKey);
                    if (redisUtils.hasKey(redisKey) && StringUtils.isNotEmpty(redisValue)) {
                        OutptVisitDTO dto = new OutptVisitDTO();
                        dto.setHospCode(hospCode);//医院编码
                        dto.setId(visitId);//就诊id
                        dto.setPatientCode(redisValue);
                        outptVisitDAO.updateOutptVisit(dto);
                        redisUtils.del(redisKey);
                    }
                }
                outptTmakePriceFormService_consumer.saveOutptSettleByTf(chargeParams);
            }
        }
        // <----诊间支付退费 2022-09-06  liuliyun end-------->
        Map insurePaitentInfo = new HashMap();
        insurePaitentInfo.put("outptVisit", outpt); // 返回病人信息
        insurePaitentInfo.put("isInsurePaitentPartBack", isInsurePaitentPartBack); // 是否医保病人部分退费
        /***********End 医保病人退部分费用标识*********/

        /***********End*****************非医保病人自动收费************************************/
        // 体检回调
        if (outptSettleDTO!=null &&"1".equals(outptSettleDTO.getIsPhys())) {
            phyIsCallBack(allCostDTOList);
        }
        return WrapperResponse.success(insurePaitentInfo);
    }

    /**
     * @param outptSettleDTO
     * @Menthod outptPaymentSettleChangeRed
     * @Desrciption 对冲诊间支付结算信息
     * @Author liuliyun
     * @Date 2022/09/06 10:40
     */
    private void outptPaymentSettleChangeRed(OutptSettleDTO outptSettleDTO,String redSettleId) {
        outptSettleDTO.setSettleId(outptSettleDTO.getId());
        // 诊间支付结算表数据对冲
        PaymentSettleDO paymentSettleDO = outptSettleDAO.queryPaymentSettle(outptSettleDTO);
        if (paymentSettleDO == null) {
            throw new AppException("退费提示：未获取到诊间支付结算记录");
        }
        // 被冲红
        paymentSettleDO.setStatusCode(Constants.ZTBZ.BCH);
        outptSettleDAO.updatePaymentSettleStatus(paymentSettleDO);
        // 冲红
        paymentSettleDO.setId(SnowflakeUtils.getId());
        paymentSettleDO.setSettleId(redSettleId);
        paymentSettleDO.setRedId(paymentSettleDO.getId());
        paymentSettleDO.setOldSettleId(outptSettleDTO.getId());
        paymentSettleDO.setOneSettleId(paymentSettleDO.getOneSettleId());
        paymentSettleDO.setStatusCode(Constants.ZTBZ.CH); // 状态标志： 冲红
        // 金额置反
        paymentSettleDO.setTotalPrice(BigDecimalUtils.negate(paymentSettleDO.getTotalPrice())); // 总金额
        paymentSettleDO.setPaymentPrice(BigDecimalUtils.negate(paymentSettleDO.getPaymentPrice())); // 实际支付金额
        // 创建信息
        paymentSettleDO.setCrteId(outptSettleDTO.getCrteId());
        paymentSettleDO.setCrteName(outptSettleDTO.getCrteName());
        paymentSettleDO.setSettleTime(DateUtils.getNow());
        paymentSettleDO.setCrteTime(DateUtils.getNow());
        outptSettleDAO.insertPaymentSettleInfo(paymentSettleDO);
    }

    // 支付平台  退款查询
    public void paymentRefundQuery(Map<String,Object> paymentParam){
        Map<String,Object> result = outptPaymentService_consumer.updatePaymentRefundQuery(paymentParam);
        if (ObjectUtil.isNotEmpty(result)){
            String refundStatus = MapUtils.get(result, "refundStatus"); // 支付平台退款状态
            if (Constants.ZJ_PAY_TKZT.TKCG.equals(refundStatus)) { // 退款成功

            }else if (Constants.ZJ_PAY_TKZT.TKSB.equals(refundStatus)||Constants.ZJ_PAY_TKZT.TKYC.equals(refundStatus)){ // 退款失败、退款异常

            }else if (Constants.ZJ_PAY_TKZT.TKZ.equals(refundStatus)) { // 退款中
                 this.paymentRefundQuery(paymentParam);
            }
        }
    }

}
