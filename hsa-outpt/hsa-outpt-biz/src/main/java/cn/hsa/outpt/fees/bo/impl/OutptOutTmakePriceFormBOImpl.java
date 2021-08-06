package cn.hsa.outpt.fees.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureOutptOutFeeDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.insure.module.service.InsureIndividualBasicService;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.module.insure.module.service.InsureIndividualSettleService;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.module.insure.outpt.service.OutptService;
import cn.hsa.module.outpt.fees.bo.OutptOutTmakePriceFormBO;
import cn.hsa.module.outpt.fees.dao.*;
import cn.hsa.module.outpt.fees.dto.*;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.fees.entity.OutptPrescribeDO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public synchronized WrapperResponse updateOutptOutFee(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, OutptPayDTO outptPayDTO) {
        // TODO 必要入参获取 [就诊ID：visitId , 医院编码：hospCode , 结算ID：settleId , 费用列表信息：allCostDTOList]
        Map selectMap = new HashMap<>();
        String visitId = outptSettleDTO.getVisitId();
        String hospCode = outptSettleDTO.getHospCode();
        String settleId = outptSettleDTO.getId();
        String crteId = outptVisitDTO.getCrteId();
        String hospName = outptVisitDTO.getHospName();
        String crteName = outptVisitDTO.getCrteName();
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
        if (oldOutptSettleDTO == null) {
            throw new AppException("退费失败：未找到结算信息");
        }

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
                costDTOList.add(this.getLastFeeList(outptCostDTO, lastNum,crteId,crteName));
                moneyAgain = BigDecimalUtils.add(moneyAgain, BigDecimalUtils.multiply(outptCostDTO.getPrice(), lastNum));
            }
            backNumSum = BigDecimalUtils.add(backNumSum,outptCostDTO.getBackNum());
        }
        if (BigDecimalUtils.compareTo(backNumSum, BigDecimal.ZERO) <= 0) {
            throw new AppException("退费数量不能全为零");
        }

        // TODO 总金额核对

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

        if (!Constants.BRLX.PTBR.equals(outpt.getPatientCode())) {
            InsureIndividualBasicDTO insureIndividualBasicDTO = outptVisitDAO.getInsureBasicById(selectMap);
            if (insureIndividualBasicDTO == null) {
                throw new AppException("未进行医保登记，医保退费失败");
            }

            // 冲红医保结算数据
            selectMap.put("state",Constants.ZTBZ.ZC);
            InsureIndividualSettleDO insureIndividualSettleDO = insureIndividualSettleService_consumer.getByParams(selectMap);
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
            insureIndividualSettleDO.setSettleId(redSettleId);
            insureIndividualSettleDO.setId(SnowflakeUtils.getId());
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
            SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
            if (sys != null && Constants.SF.S.equals(sys.getValue())) {  // 调用统一支付平台
                List<Map<String,Object>> individualCostDTOList  = querySettleCost(map);
                map.put("insureCostList",individualCostDTOList);
                map.put("insureSettleId",insureSettleId);
                map.put("personalPrice",personalPrice);
                Map<String,Object> resultMap = insureUnifiedPayOutptService_consumer.UP_2208(map).getData();
                System.out.println("==========================resultMap:" + resultMap.toString());
                Map<String,Object> cancelReturnData =  MapUtils.get(resultMap,"output");
                Map<String,Object> setlInfoMap = MapUtils.get(cancelReturnData,"setlinfo");
                Boolean data = insureUnifiedPayOutptService_consumer.updateCancelFeeSubmit(map).getData();
                    if(data == true){
                        MapUtils.remove(map,"insureSettleId"); // 因为这是取消结算  所以要删除对应的费用数据
                        insureIndividualCostService_consumer.deleteOutptInsureCost(map);
                    }
                InsureIndividualSettleDTO individualSettleDTO = new InsureIndividualSettleDTO();
                individualSettleDTO.setOinfno(MapUtils.get(resultMap,"funtionCode"));
                individualSettleDTO.setOmsgid(MapUtils.get(resultMap,"msgId"));
                individualSettleDTO.setInsureSettleId(MapUtils.get(setlInfoMap,"setl_id"));



                // delete from 2021-8-6 liaojiguang
                /*if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_optins"))){
                    individualSettleDTO.setClrOptins(MapUtils.get(setlInfoMap,"clr_optins"));
                }else{
                    individualSettleDTO.setClrOptins(insureIndividualSettleDO.getClrOptins());
                }*/

                // add 2021-8-6 liaojiguang 使用就医地医保划区
                individualSettleDTO.setClrOptins(MapUtils.get(resultMap,"mdtrtareaAdmvs"));


                if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_way"))){
                    individualSettleDTO.setClrWay(MapUtils.get(setlInfoMap,"clr_way"));
                }else {
                    individualSettleDTO.setClrOptins(insureIndividualSettleDO.getClrWay());
                }
                if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_type"))){
                    individualSettleDTO.setClrType(MapUtils.get(setlInfoMap,"clr_type"));
                }
                else {
                    individualSettleDTO.setClrOptins(insureIndividualSettleDO.getClrType());
                }
                individualSettleDTO.setHospCode(hospCode);
                individualSettleDTO.setVisitId(visitId);
                individualSettleDTO.setState("2");
                individualSettleDTO.setSettleId(redSettleId);
                individualSettleDTO.setSettleState("1");
                outptVisitDAO.updateInsureSettle(individualSettleDTO);
//                insureUnifiedPayOutptService_consumer.UP_2202(map).getData();
//                insureIndividualVisitService_consumer.deleteInsureVisitById(map).getData();

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
                if (StringUtils.isEmpty(insureIndividualBasicDTO.getAaz217())) {
                    throw new AppException("医保退费失败：未获取病人医保登记号，请去医保前台系统退费");
                }
                outptService.setOutptCostUploadAndTrial(outFeeMap);
            }
        }
        /**END*****************医保病人处理********************************************************************/

       // TODO 非医保病人自动退费
        // 如果有再收的费用且是普通病人，自动收费
        if (!ListUtils.isEmpty(costDTOList) && Constants.BRLX.PTBR.equals(outpt.getPatientCode())) {
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
        Map setteleParam = new HashMap();
        setteleParam.put("hospCode",hospCode);
        setteleParam.put("outptVisitDTO",outptVisitDTOFee);
        WrapperResponse wrapperResponse = outptTmakePriceFormService_consumer.saveOutptSettleMoney(setteleParam);
        if (wrapperResponse.getCode() == (-1)) {
            throw new AppException(wrapperResponse.getMessage());
        }

        Map chargeMap = (Map) wrapperResponse.getData();
        // 划价收费接口支付接口调用(支付方式默认为现金)
        List<OutptPayDO> outptPayDOlist = new ArrayList();
        OutptPayDO OutptPayDO = new OutptPayDTO();
        OutptPayDO.setPrice(new BigDecimal(0));
        OutptPayDO.setPayCode("1");
        OutptPayDO.setPrice(moneyAgain);
        outptPayDOlist.add(OutptPayDO);

        Map<String,Object> chargeParams = new HashMap<>();

        OutptSettleDO outptSettleDo = (OutptSettleDO)chargeMap.get("outptSettle");
        OutptSettleDTO newOutptSettleDTO = new OutptSettleDTO();
        BeanUtils.copyProperties(outptSettleDo,newOutptSettleDTO);

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
        // 需要重新设置创建人员
        OutptVisitDTO visitDTO = (OutptVisitDTO) chargeMap.get("outptVisit");
        visitDTO.setCrteId(crteId);
        visitDTO.setCrteName(crteName);

        chargeParams.put("outptPayDOList",outptPayDOlist);
        chargeParams.put("outptVisitDTO",visitDTO);
        chargeParams.put("outptSettleDTO",newOutptSettleDTO);
        chargeParams.put("hospCode",hospCode);
        // outptTmakePriceFormService_consumer.saveOutptSettle(chargeParams);
        outptTmakePriceFormService_consumer.saveOutptSettleByTf(chargeParams);
        }
       /***********End*****************非医保病人自动收费************************************/
        return WrapperResponse.success(true);
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
        Map<String,String> insureCostParam = new HashMap<String,String>();
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

        List<Map<String, Object>> list = outptSettleDAO.queryOutChargePage(outptSettleDTO);
        return WrapperResponse.success(PageDTO.of(list));
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
		if (sys != null && sys.getValue().equals("1")) {  // 1:门诊医生申请  2：收费室自己决定
			list = outptSettleDAO.queryOutptPrescribesandRefundApply(outptSettleDTO);
		} else {
			list = outptSettleDAO.queryOutptPrescribes(outptSettleDTO);
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
                            && ((pharOutDistributeBatchDetailDTO.getOpdId() == null && outptCostDTO.getOpdId() == null) || pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId()))
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
                    if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
//                            && pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())
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
        outptVisitDTO.setPatientCode(Constants.BRLX.PTBR);
        outptVisitDTO.setHospCode(hospCode);
        outptVisitDTO.setId(id);
        outptVisitDAO.updateOutptVisit(outptVisitDTO);
        return true;
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

        // 门诊合同单位支付表冲红
        this.outptInsurePayChangeRed(selectDTO);

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
    private void outptInsurePayChangeRed(OutptSettleDTO outptSettleDTO) {
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
        selectOutptSettleDTO.setActualPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getActualPrice()));
        selectOutptSettleDTO.setMiPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getMiPrice()));
        selectOutptSettleDTO.setRealityPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getRealityPrice()));
        selectOutptSettleDTO.setSelfPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getSelfPrice()));
        selectOutptSettleDTO.setTotalPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getTotalPrice()));
        selectOutptSettleDTO.setTruncPrice(BigDecimalUtils.negate(selectOutptSettleDTO.getTruncPrice()));

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
    private OutptCostDTO getLastFeeList(OutptCostDTO outptCostDTO, BigDecimal lastNum,String crteId,String crteName) {
        OutptCostDTO costDto = new OutptCostDTO();
        BeanUtils.copyProperties(outptCostDTO, costDto);

        // 新主键ID
        costDto.setId(SnowflakeUtils.getId());

        // 应收数量
        costDto.setTotalNum(lastNum);
        // 发药明细汇总id
        costDto.setDistributeAllDetailId(outptCostDTO.getDistributeAllDetailId());

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
                        continue;
                    }
                    if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
                            && ((pharOutDistributeBatchDetailDTO.getOpdId() == null && outptCostDTO.getOpdId() == null) || pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId()))
                            && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId())) {
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
                        continue;
                    }
                    if (Constants.TYZT.YFY.equals(pharOutDistributeBatchDetailDTO.getStatusCode())
 //                           && pharOutDistributeBatchDetailDTO.getOpdId().equals(outptCostDTO.getOpdId())
                            && pharOutDistributeBatchDetailDTO.getItemId().equals(outptCostDTO.getItemId())) {
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

                // 创建信息
                outptCostDTO.setCrteId(outptVisitDTO.getCrteId());
                outptCostDTO.setCrteName(outptVisitDTO.getCrteName());
                outptCostDTO.setCrteTime(DateUtils.getNow());
            }
            outptCostDAO.batchInsert(outptCostDTOList);
        }
    }



}
