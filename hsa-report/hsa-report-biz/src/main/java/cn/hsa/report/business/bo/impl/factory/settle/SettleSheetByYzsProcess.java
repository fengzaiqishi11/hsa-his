package cn.hsa.report.business.bo.impl.factory.settle;

import cn.hsa.report.business.bean.InsurCumulationInfoDTO;
import cn.hsa.report.business.bean.PsnCumulationResDTO;
import cn.hsa.report.business.bean.SettleCommonInfoDTO;
import cn.hsa.report.business.bean.SettleInfoDetailResDTO;
import cn.hsa.report.business.bean.SettleInfoResDTO;
import cn.hsa.report.business.bean.SettleSheetBaseInfoDTO;
import cn.hsa.report.business.bean.SettleSheetHeadDTO;
import cn.hsa.report.business.bean.SettleSheetInfoYzsDTO;
import cn.hsa.report.business.bean.SettleSheetPartFourDTO;
import cn.hsa.report.business.bean.SettleSheetPartFourYzsDTO;
import cn.hsa.report.business.bean.SettleSheetPartOneYzsDTO;
import cn.hsa.report.business.bean.SettleSheetPartSixYzsDTO;
import cn.hsa.report.business.bean.SettleSheetPartTwoYzsDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SettleSheetYzsProcess
 * @Deacription 一站式结算单
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
@Service
public class SettleSheetByYzsProcess extends SettleSheetProcess {

    @Override
    public SettleSheetInfoYzsDTO downLoadSettleInfo(SettleSheetBaseInfoDTO baseInfo) {
        SettleCommonInfoDTO common = getSettleCommonInfoDTO(baseInfo);

        SettleSheetInfoYzsDTO settleSheetInfoYzsDTO = new SettleSheetInfoYzsDTO();
        settleSheetInfoYzsDTO.setSheetCode((String) baseInfo.getCustomConfigMap().get("sheetCode"));
        SettleSheetHeadDTO medisnInfMap = handlerMedisnInfo(common.getSettleInfo(), baseInfo);
        settleSheetInfoYzsDTO.setMedisnInfMap(medisnInfMap);
        settleSheetInfoYzsDTO.setBaseInfoMap(handlerBaseInfo(common.getSettleInfo(), baseInfo));
        SettleSheetPartSixYzsDTO setlProcInfoMap = handlerSetlProcInfoMap(common.getSettleInfo(), common.getSettleInfoDetail());
        settleSheetInfoYzsDTO.setSetlProcInfoMap(setlProcInfoMap);
        settleSheetInfoYzsDTO.setPastFeeMap(handlerPastFee(baseInfo));
        settleSheetInfoYzsDTO.setFeeMapList(handlerInsureIndividualCost(baseInfo.getMdtrtId()));
        settleSheetInfoYzsDTO.setHandlerPolicyMap(handlerYzsPolicy(baseInfo, medisnInfMap.getHospName(), setlProcInfoMap.getSumBxFeeNumber(), common.getSettleInfo()));
        settleSheetInfoYzsDTO.setFourPartMap(super.handlerCommonInsureSettleFee(common.getSettleInfo()));
        return settleSheetInfoYzsDTO;
    }

    @Override
    public SettleSheetPartOneYzsDTO handlerBaseInfo(SettleInfoResDTO settleInfoResDTO, SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetPartOneYzsDTO partOneYzs = new SettleSheetPartOneYzsDTO();
        BeanUtils.copyProperties(super.handlerCommonBaseInfo(settleInfoResDTO, baseInfo),partOneYzs);

//        List<RegDiseInfoDTO> diseList = regDiseInfoBO.queryListByVisitId(baseInfo.getVisitId(), InsurConstants.OUT_HOSP_STATUS);
//        diseList = diseList.stream().filter(data -> data.getMaindiagFlag().equals(InsurConstants.MAINDIAG_FLAG_TURE)).collect(Collectors.toList());

//        partOneYzs.setOutDiseaseName(CollectionUtil.isEmpty(diseList) ? "" : diseList.get(0).getInsurDiagName());
        partOneYzs.setAddress(baseInfo.getAddress());
        return partOneYzs;
    }

    public SettleSheetPartSixYzsDTO handlerSetlProcInfoMap(SettleInfoResDTO settleInfo, List<SettleInfoDetailResDTO> settleInfoDetail) {
        SettleSheetPartSixYzsDTO partSix = new SettleSheetPartSixYzsDTO();
        partSix.setScale();
        partSix.setHifpPay(settleInfo.getHifpPay());
        partSix.setPsnPartAmt(settleInfo.getPsnPay());
        partSix.setFundPaySumamt(settleInfo.getFundPaySumamt());
        for (SettleInfoDetailResDTO item : settleInfoDetail) {
            // 基金类别
            String setlProcInfo = item.getSetlProcInfo();
            // 大病保险报销金额
            if ("390201".equals(setlProcInfo)) {
                partSix.setS2(BigDecimalUtils.add(partSix.getS2(), item.getFundPayamt()));
            }
            // 意外伤害
            if ("390401".equals(setlProcInfo)) {
                partSix.setS3(BigDecimalUtils.add(partSix.getS3(), item.getFundPayamt()));
            }
            // 大病补充特惠保
            if ("620101".equals(setlProcInfo)) {
                partSix.setS4(BigDecimalUtils.add(partSix.getS4(), item.getFundPayamt()));
            }
            // 医疗救助金额
            if ("610101".equals(setlProcInfo)) {
                partSix.setS5(BigDecimalUtils.add(partSix.getS5(), item.getFundPayamt()));
            }
            // 医院减免金额
            if ("630101".equals(setlProcInfo)) {
                partSix.setS6(BigDecimalUtils.add(partSix.getS6(), item.getFundPayamt()));
            }
            // 财政兜底报销金额
            if ("640101".equals(setlProcInfo)) {
                partSix.setS7(BigDecimalUtils.add(partSix.getS7(), item.getFundPayamt()));
            }
            // 其它报销金额
            if ("999996".equals(setlProcInfo)) {
                partSix.setS9(BigDecimalUtils.add(partSix.getS9(), item.getFundPayamt()));
            }
        }
//        partSix.setSumBxFeeNumber(BigDecimalUtils.addMany(partSix.getHifpPay(), partSix.getS2(), partSix.getS3(), partSix.getS4(), partSix.getS5(),
//                partSix.getS6(), partSix.getS7(), partSix.getS9()));
        partSix.setSumBxFee(BigDecimalUtils.equals(BigDecimal.ZERO, partSix.getSumBxFeeNumber()) ? "零元" : Convert.digitToChinese(partSix.getSumBxFeeNumber()));
        partSix.setScale();
        return partSix;
    }

    @Override
    public SettleSheetPartTwoYzsDTO handlerPastFee(SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetPartTwoYzsDTO partTwo = new SettleSheetPartTwoYzsDTO();
        partTwo.setScale();
        // 结算后中心累计
//        PsnCumulationReqDTO query = new PsnCumulationReqDTO();
//        query.setPsnNo(baseInfo.getPsnNo());
//        List<PsnCumulationResDTO> cumulationInfoList = insurQueryBO.getPsnCumulationInfo(query, msgId, sysUserDTO);
        List<PsnCumulationResDTO> cumulationInfoList = new ArrayList<>();
        if (CollectionUtil.isEmpty(cumulationInfoList)) {
            return partTwo;
        }
        // 累计基本医保报销金额计算公式
        String str = "D310101_BIZ2101+D310101_BIZ2102+D310101_BIZ22+D310101_BIZ2106+D310101_BIZ2107+D310101_BIZ52+D390101_BIZ2101+D390101_BIZ2102+D390101_BIZ22+D390101_BIZ2106+D390101_BIZ2107+D390101_BIZ52";
        String[] split = str.split("\\+");
        List<String> strings = Arrays.asList(split);

        for (PsnCumulationResDTO item : cumulationInfoList) {
            // 累计类别代码
            String cumTypeCode = item.getCumTypeCode();
            BigDecimal cum = item.getCum() == null ? BigDecimal.ZERO : item.getCum();
            // 累计基本医保报销金额
            if (strings.contains(cumTypeCode)) {
                partTwo.setS1(BigDecimalUtils.add(partTwo.getS1(), cum));
            }
            // 累计医疗总费用
            if ("F0000_BIZ2101".equals(cumTypeCode) || "F0000_BIZ2102".equals(cumTypeCode) ||
                    "F0000_BIZ22".equals(cumTypeCode) || "F0000_BIZ2106".equals(cumTypeCode) ||
                    "F0000_BIZ2107".equals(cumTypeCode) || "F0000_BIZ52".equals(cumTypeCode)) {
                partTwo.setS2(BigDecimalUtils.add(partTwo.getS2(), cum));
            }
            // 累计大病保险报销金额
            if ("D390201".equals(cumTypeCode)) {
                partTwo.setS3(BigDecimalUtils.add(partTwo.getS3(), cum));
            }
            // 累计扶贫特惠保报销金额
            if ("D620101".equals(cumTypeCode)) {
                partTwo.setS4(BigDecimalUtils.add(partTwo.getS4(), cum));
            }
            // 累计医疗救助金额
            if ("D610101".equals(cumTypeCode)) {
                partTwo.setS5(BigDecimalUtils.add(partTwo.getS5(), cum));
            }
            // 累计医院减免金额
            if ("D630101".equals(cumTypeCode)) {
                partTwo.setS6(BigDecimalUtils.add(partTwo.getS6(), cum));
            }
            // 累计财政兜底报销金额
            if ("D640101".equals(cumTypeCode)) {
                partTwo.setS7(BigDecimalUtils.add(partTwo.getS7(), cum));
            }
            // 累计其它报销金额
            if ("D999996".equals(cumTypeCode)) {
                partTwo.setS8(BigDecimalUtils.add(partTwo.getS8(), cum));
            }
            // 累计政策范围内费用
            if ("TQ610101_BIZ2101".equals(cumTypeCode) || "TS390201_BIZ2101".equals(cumTypeCode) ||
                    "TS610101_BIZ2101".equals(cumTypeCode) || "TQ390201_BIZ2102".equals(cumTypeCode) ||
                    "TS390201_BIZ2102".equals(cumTypeCode) || "TQ610101_BIZ2102".equals(cumTypeCode) ||
                    "TS610101_BIZ2102".equals(cumTypeCode) || "TQ610101_BIZ22".equals(cumTypeCode) ||
                    "TQ390201_BIZ22".equals(cumTypeCode) || "TS390201_BIZ22".equals(cumTypeCode) ||
                    "TS610101_BIZ22".equals(cumTypeCode)) {
                partTwo.setS9(BigDecimalUtils.add(partTwo.getS9(), cum));
            }
        }
//        partTwo.setSumBxFeeNumber(BigDecimalUtils.addMany(partTwo.getS1(), partTwo.getS3(), partTwo.getS4(), partTwo.getS5(),
//                partTwo.getS6(), partTwo.getS7(), partTwo.getS8()));
        partTwo.setSumBxFee(BigDecimalUtils.equals(BigDecimal.ZERO, partTwo.getSumBxFeeNumber()) ? "零元" : Convert.digitToChinese(partTwo.getSumBxFeeNumber()));
        partTwo.setScale();
        return partTwo;
    }

    SettleSheetPartFourYzsDTO handlerYzsPolicy(SettleSheetBaseInfoDTO baseInfo,
                                               String hospName, BigDecimal sumBxFeeNumber, SettleInfoResDTO settleInfo) {
        SettleSheetPartFourYzsDTO partFourYzs = new SettleSheetPartFourYzsDTO();
        partFourYzs.setScale();

        List<SettleSheetPartFourDTO> partFourList = handlerPolicy(baseInfo);
        if (CollectionUtil.isEmpty(partFourList)) {
            return partFourYzs;
        }

        for (SettleSheetPartFourDTO item : partFourList) {
            String polItemName = item.getPolItemName();
            BigDecimal polItemPaySum = item.getPolItemPaySum();
            if ("完全政策自付费用".equals(polItemName.trim()) || "全自费".equals(polItemName.trim()) || "超限额自付".equals(polItemName.trim())) {
                partFourYzs.setS1(BigDecimalUtils.add(partFourYzs.getS1(), polItemPaySum));
                continue;
            }
            if ("部分政策自付费用".equals(polItemName.trim()) || "乙类先自付".equals(polItemName.trim())) {
                partFourYzs.setS2(BigDecimalUtils.add(partFourYzs.getS2(), polItemPaySum));
                continue;
            }
            if ("县外就医转外自付费用".equals(polItemName.trim())) {
                partFourYzs.setS3(BigDecimalUtils.add(partFourYzs.getS3(), polItemPaySum));
                continue;
            }
            if ("大病起付线".equals(polItemName.trim())) {
                partFourYzs.setS4(BigDecimalUtils.add(partFourYzs.getS4(), polItemPaySum));
                continue;
            }
        }
        // 核算机构名称 核算人
        partFourYzs.setHospName(hospName);
        // 基本医保报销金额
        partFourYzs.setHifpPayNumber(settleInfo.getHifpPay());
        // 基本医保报销金额（大写）
        partFourYzs.setHifpPay(BigDecimalUtils.equals(BigDecimal.ZERO, partFourYzs.getHifpPayNumber()) ? "零元" : Convert.digitToChinese(partFourYzs.getHifpPayNumber()));
        // 百分比
        partFourYzs.setDivideFormat("");
        if (BigDecimalUtils.isZero(settleInfo.getHifpPay())) {
            // 如果报销金额等于0  则提高10%的报销金额还是为0
            partFourYzs.setS5(settleInfo.getHifpPay());
            // 百分比
            partFourYzs.setDivideFormat("0.00%");
        } else {
            partFourYzs.setS5(BigDecimalUtils.multiply(sumBxFeeNumber, new BigDecimal(0.10)));
            BigDecimal divide = BigDecimalUtils.divide(sumBxFeeNumber, settleInfo.getMedfeeSumamt());
            DecimalFormat decimalFormat = new DecimalFormat("0.00%");
            String divideFormat = decimalFormat.format(divide);
            // 百分比
            partFourYzs.setDivideFormat(divideFormat);
        }
        partFourYzs.setActPayDedc(settleInfo.getActPayDedc());

        List<InsurCumulationInfoDTO> cumulationInfoList = super.getInsurCumulationInfoDTO(baseInfo);

        if (CollectionUtil.isNotEmpty(cumulationInfoList)) {
            for (InsurCumulationInfoDTO item : cumulationInfoList) {
                // 累计类别代码
                String cumTypeCode = item.getCumTypeCode();
                BigDecimal cum = item.getCum() == null ? BigDecimal.ZERO : item.getCum();
                // 累计起付线(不含本次)
                if ("Q0000".equals(cumTypeCode)) {
                    partFourYzs.setSumCum(BigDecimalUtils.add(cum, partFourYzs.getSumCum()));
                    break;
                }
            }
        }
        partFourYzs.setScale();
        return partFourYzs;
    }

}
