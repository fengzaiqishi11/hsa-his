package cn.hsa.report.business.bo.impl.factory.settle;

import cn.hsa.report.business.bean.InsurCumulationInfoDTO;
import cn.hsa.report.business.bean.SettleCommonInfoDTO;
import cn.hsa.report.business.bean.SettleSheetBaseInfoDTO;
import cn.hsa.report.business.bean.SettleSheetInfoMzDTO;
import cn.hsa.report.business.bean.SettleSheetPartTwoMzDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SettleSheetMzProcess
 * @Deacription 门诊结算单
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
@Service
public class SettleSheetByMzProcess extends SettleSheetProcess {

    @Override
    public SettleSheetInfoMzDTO downLoadSettleInfo(SettleSheetBaseInfoDTO baseInfo) {
        SettleCommonInfoDTO common = getSettleCommonInfoDTO(baseInfo);
        SettleSheetInfoMzDTO settleSheetInfoMzDTO = new SettleSheetInfoMzDTO();
        settleSheetInfoMzDTO.setSheetCode((String) baseInfo.getCustomConfigMap().get("sheetCode"));
        settleSheetInfoMzDTO.setMedisnInfMap(handlerMedisnInfo(common.getSettleInfo(), baseInfo));
        settleSheetInfoMzDTO.setBaseInfoMap(super.handlerCommonBaseInfo(common.getSettleInfo(), baseInfo));
        settleSheetInfoMzDTO.setFeeMapList(super.handlerInsureIndividualCost(baseInfo.getMdtrtId()));
        settleSheetInfoMzDTO.setPastFeeMap(handlerPastFee(baseInfo));
        settleSheetInfoMzDTO.setFourPartMap(super.handlerCommonInsureSettleFee(common.getSettleInfo()));
        return settleSheetInfoMzDTO;
    }

    @Override
    public SettleSheetPartTwoMzDTO handlerPastFee(SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetPartTwoMzDTO partTwo = new SettleSheetPartTwoMzDTO();
        partTwo.setScale();
//        List<InsurCumulationInfoDTO> cumulationInfoList = super.getInsurCumulationInfoDTO(baseInfo, msgId, sysUserDTO);
        List<InsurCumulationInfoDTO> cumulationInfoList = new ArrayList<>();
        if (CollectionUtil.isEmpty(cumulationInfoList)) {
            return partTwo;
        }
        for (InsurCumulationInfoDTO item : cumulationInfoList) {
            // 累计类别代码
            String cumTypeCode = item.getCumTypeCode();
            BigDecimal cum = item.getCum() == null ? BigDecimal.ZERO : item.getCum();

            if ("C0000_BIZ11".equals(cumTypeCode) || "C0000_BIZ13".equals(cumTypeCode) ||
                    "C0000_BIZ51".equals(cumTypeCode) || "C0000_BIZ9901".equals(cumTypeCode) ||
                    "C0000_BIZ14".equals(cumTypeCode)) {
                partTwo.setOutptCount(partTwo.getOutptCount() + cum.intValue());
            }
            // F0000_BIZ9901    业务总费用且医疗业务类别为门诊两病的累计
            // F0000_BIZ51      业务总费用且医疗业务类别为职工生育门诊的累计
            // F0000_BIZ11      业务总费用且医疗业务类别为普通门诊的累计
            // F0000_BIZ13      业务总费用且医疗业务类别为急诊的累计
            // F0000_BIZ14      业务总费用且医疗业务类别为门诊慢特病的累计
            if ("F0000_BIZ9901".equals(cumTypeCode) || "F0000_BIZ51".equals(cumTypeCode) ||
                    "F0000_BIZ11".equals(cumTypeCode) || "F0000_BIZ13".equals(cumTypeCode) ||
                    "F0000_BIZ14".equals(cumTypeCode)) {
                partTwo.setS1(BigDecimalUtils.add(partTwo.getS1(), cum));
            }
            // D390102_BIZ11  基金且城乡居民统筹基金且医疗业务类别为普通门诊的累计
            // D390101_BIZ14  基金且城乡居民统筹基金且医疗业务类别为门诊慢特病的累计
            // D310101_BIZ13  基金且城乡居民统筹基金且医疗业务类别为门诊慢特病的累计
            // D310101_BIZ14 基金且职工统筹基金且医疗业务类别为门诊慢特病的累计
            // D390101_BIZ13 基金且城乡居民统筹基金且医疗业务类别为急诊的累计
            // D390102_BIZ9901 基金且城乡居民统筹基金且医疗业务类别为急诊的累计
            if ("D390102_BIZ11".equals(cumTypeCode) || "D390101_BIZ14".equals(cumTypeCode) ||
                    "D310101_BIZ13".equals(cumTypeCode) || "D310101_BIZ14".equals(cumTypeCode) ||
                    "D390101_BIZ13".equals(cumTypeCode) || "D390102_BIZ9901".equals(cumTypeCode)) {
                partTwo.setS2(BigDecimalUtils.add(partTwo.getS2(), cum));
            }
            // D330101  大额基金支付
            if ("D330101_BIZ13".equals(cumTypeCode) || "D330101_BIZ14".equals(cumTypeCode)) {
                partTwo.setS3(BigDecimalUtils.add(partTwo.getS3(), cum));
            }
            // D320101 公务员补助支付
            if ("D320101".equals(cumTypeCode)) {
                partTwo.setS4(BigDecimalUtils.add(partTwo.getS4(), cum));
            }
            // D390201 大病保险支付
            if ("D390201".equals(cumTypeCode)) {
                partTwo.setS5(BigDecimalUtils.add(partTwo.getS5(), cum));
            }
            // D310201 个人账户支付
            if ("D310201".equals(cumTypeCode)) {
                partTwo.setS6(BigDecimalUtils.add(partTwo.getS6(), cum));

            }
            // D610101  医疗救助支付
            if ("D610101".equals(cumTypeCode)) {
                partTwo.setS7(BigDecimalUtils.add(partTwo.getS7(), cum));
            }
        }
        partTwo.setScale();
        return partTwo;
    }

}
