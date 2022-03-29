package cn.hsa.report.business.bo.impl.factory.settle;

import cn.hsa.report.business.bean.InsurCumulationInfoDTO;
import cn.hsa.report.business.bean.SettleCommonInfoDTO;
import cn.hsa.report.business.bean.SettleInfoResDTO;
import cn.hsa.report.business.bean.SettleSheetBaseInfoDTO;
import cn.hsa.report.business.bean.SettleSheetInfoZyDTO;
import cn.hsa.report.business.bean.SettleSheetPartOneDTO;
import cn.hsa.report.business.bean.SettleSheetPartTwoZyDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SettleSheetByZyProcess
 * @Deacription 普通住院结算单
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
@Service
public class SettleSheetByZyProcess extends SettleSheetProcess {

    @Override
    public SettleSheetInfoZyDTO downLoadSettleInfo(SettleSheetBaseInfoDTO baseInfo) {
        SettleCommonInfoDTO common = getSettleCommonInfoDTO(baseInfo);

        SettleSheetInfoZyDTO settleSheetInfoZyDTO = new SettleSheetInfoZyDTO();
        settleSheetInfoZyDTO.setSheetCode((String) baseInfo.getCustomConfigMap().get("sheetCode"));
        settleSheetInfoZyDTO.setMedisnInfMap(handlerMedisnInfo(common.getSettleInfo(), baseInfo));
        settleSheetInfoZyDTO.setBaseInfoMap(handlerBaseInfo(common.getSettleInfo(), baseInfo));
        settleSheetInfoZyDTO.setPastFeeMap(handlerPastFee(baseInfo));
        settleSheetInfoZyDTO.setFeeMapList(handlerInsureIndividualCost(baseInfo.getMdtrtId()));
        settleSheetInfoZyDTO.setPolicyMapList(handlerPolicy(baseInfo));
        settleSheetInfoZyDTO.setFourPartMap(super.handlerCommonInsureSettleFee(common.getSettleInfo()));
        return settleSheetInfoZyDTO;
    }

    @Override
    public SettleSheetPartOneDTO handlerBaseInfo(SettleInfoResDTO settleInfoResDTO, SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetPartOneDTO partOne = super.handlerCommonBaseInfo(settleInfoResDTO, baseInfo);
//        List<RegDiseInfoDTO> diseList = regDiseInfoBO.queryListByVisitId(baseInfo.getVisitId(), InsurConstants.OUT_HOSP_STATUS);
//        diseList = diseList.stream().filter(data -> data.getMaindiagFlag().equals(InsurConstants.MAINDIAG_FLAG_TURE)).collect(Collectors.toList());
//        partOne.setOutMaindiagName(CollectionUtil.isEmpty(diseList) ? "" : diseList.get(0).getInsurDiagName());
        return partOne;
    }


    @Override
    public SettleSheetPartTwoZyDTO handlerPastFee(SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetPartTwoZyDTO partTwo = new SettleSheetPartTwoZyDTO();
        partTwo.setScale();

        List<InsurCumulationInfoDTO> cumulationInfoList = super.getInsurCumulationInfoDTO(baseInfo);
        if (CollectionUtil.isEmpty(cumulationInfoList)) {
            return partTwo;
        }
        for (InsurCumulationInfoDTO item : cumulationInfoList) {
            String cumTypeCode = item.getCumTypeCode();
            BigDecimal cum = item.getCum() == null ? BigDecimal.ZERO : item.getCum();

            if ("C0000_BIZ2101".equals(cumTypeCode) || "C0000_BIZ2102".equals(cumTypeCode) ||
                    "C0000_BIZ22".equals(cumTypeCode) || "C0000_BIZ2106".equals(cumTypeCode) ||
                    "C0000_BIZ2107".equals(cumTypeCode) || "C0000_BIZ52".equals(cumTypeCode)) {
                partTwo.setInptCount(partTwo.getInptCount() + cum.intValue());
            }
            if ("S0000_BIZ2101".equals(cumTypeCode) || "S0000_BIZ2102".equals(cumTypeCode) ||
                    "S0000_BIZ22".equals(cumTypeCode) || "S0000_BIZ2106".equals(cumTypeCode) ||
                    "S0000_BIZ2107".equals(cumTypeCode) || "S0000_BIZ52".equals(cumTypeCode)) {
                partTwo.setS1(BigDecimalUtils.add(partTwo.getS1(), cum));
            }
            if ("F0000_BIZ2101".equals(cumTypeCode) || "F0000_BIZ2102".equals(cumTypeCode) ||
                    "F0000_BIZ22".equals(cumTypeCode) || "F0000_BIZ2106".equals(cumTypeCode) ||
                    "F0000_BIZ2107".equals(cumTypeCode) || "F0000_BIZ52".equals(cumTypeCode)) {
                partTwo.setS2(BigDecimalUtils.add(partTwo.getS2(), cum));
            }
            if ("Q0000_BIZ2101".equals(cumTypeCode) || "Q0000_BIZ2102".equals(cumTypeCode) ||
                    "Q0000_BIZ22".equals(cumTypeCode) || "Q0000_BIZ2106".equals(cumTypeCode) ||
                    "Q0000_BIZ2107".equals(cumTypeCode) || "Q0000_BIZ52".equals(cumTypeCode)) {
                partTwo.setS3(BigDecimalUtils.add(partTwo.getS3(), cum));
            }
            if ("D310101_BIZ2101".equals(cumTypeCode) || "D310101_BIZ2102".equals(cumTypeCode) ||
                    "D310101_BIZ22".equals(cumTypeCode) || "D310101_BIZ2106".equals(cumTypeCode) ||
                    "D310101_BIZ2107".equals(cumTypeCode) || "D310101_BIZ52".equals(cumTypeCode) ||
                    "D390101_BIZ2101".equals(cumTypeCode) || "D390101_BIZ2102".equals(cumTypeCode) ||
                    "D310101_BIZ22".equals(cumTypeCode) || "D390101_BIZ22".equals(cumTypeCode) ||
                    "D390101_BIZ2106".equals(cumTypeCode) || "D390101_BIZ2107".equals(cumTypeCode) ||
                    "D390101_BIZ52".equals(cumTypeCode)) {
                partTwo.setS4(BigDecimalUtils.add(partTwo.getS4(), cum));
            }
            if ("Z0000_BIZ2101_LAB101".equals(cumTypeCode) || "Z0000_BIZ2102_LAB101".equals(cumTypeCode) ||
                    "Z0000_BIZ22_LAB101".equals(cumTypeCode) || "Z0000_BIZ2106_LAB101".equals(cumTypeCode) ||
                    "Z0000_BIZ2107_LAB101".equals(cumTypeCode) || "Z0000_BIZ52_LAB101".equals(cumTypeCode) ||
                    "Z0000_BIZ2101_LAB103".equals(cumTypeCode) || "Z0000_BIZ22_LAB103".equals(cumTypeCode) ||
                    "Z0000_BIZ2106_LAB103".equals(cumTypeCode) || "Z0000_BIZ52_LAB103".equals(cumTypeCode)) {
                partTwo.setS5(BigDecimalUtils.add(partTwo.getS5(), cum));
            }
            if ("Z0000_BIZ2101_LAB102".equals(cumTypeCode) ||
                    "Z0000_BIZ2102_LAB102".equals(cumTypeCode) ||
                    "Z0000_BIZ22_LAB102".equals(cumTypeCode) ||
                    "Z0000_BIZ2106_LAB102".equals(cumTypeCode) ||
                    "Z0000_BIZ2107_LAB102".equals(cumTypeCode) ||
                    "Z0000_BIZ52_LAB102".equals(cumTypeCode)) {
                partTwo.setS6(BigDecimalUtils.add(partTwo.getS6(), cum));
            }
            if ("D330101".equals(cumTypeCode)) {
                partTwo.setS7(BigDecimalUtils.add(partTwo.getS7(), cum));
            }
            if ("TS390201".equals(cumTypeCode)) {
                partTwo.setS8(BigDecimalUtils.add(partTwo.getS8(), cum));
            }
            if ("D390201".equals(cumTypeCode)) {
                partTwo.setS9(BigDecimalUtils.add(partTwo.getS9(), cum));
            }
            if ("D610101".equals(cumTypeCode)) {
                partTwo.setS10(BigDecimalUtils.add(partTwo.getS10(), cum));
            }
        }
        partTwo.setScale();
        return partTwo;
    }

}
