package cn.hsa.report.business.bo.impl.factory.settle;


import cn.hsa.report.business.bean.InsurCumulationInfoDTO;
import cn.hsa.report.business.bean.SettleCommonInfoDTO;
import cn.hsa.report.business.bean.SettleInfoDetailResDTO;
import cn.hsa.report.business.bean.SettleSheetBaseInfoDTO;
import cn.hsa.report.business.bean.SettleSheetInfoLxDTO;
import cn.hsa.report.business.bean.SettleSheetPartThreeDTO;
import cn.hsa.report.business.bean.SettleSheetPartTwoLxDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName SettleSheetYzsProcess
 * @Deacription 离休结算单
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
@Service
public class SettleSheetByZyLxProcess extends SettleSheetProcess {

    @Override
    public SettleSheetInfoLxDTO downLoadSettleInfo(SettleSheetBaseInfoDTO baseInfo) {
        SettleCommonInfoDTO common = getSettleCommonInfoDTO(baseInfo);
        SettleSheetInfoLxDTO settleSheetInfoLxDTO = new SettleSheetInfoLxDTO();
        settleSheetInfoLxDTO.setSheetCode((String) baseInfo.getCustomConfigMap().get("sheetCode"));
        settleSheetInfoLxDTO.setMedisnInfMap(handlerMedisnInfo(common.getSettleInfo(), baseInfo));
        settleSheetInfoLxDTO.setBaseInfoMap(super.handlerCommonBaseInfo(common.getSettleInfo(), baseInfo));
        settleSheetInfoLxDTO.setPastFeeMap(handlerPastFee(baseInfo));
        settleSheetInfoLxDTO.setFourPartMap(super.handlerCommonInsureSettleFee(common.getSettleInfo()));
        List<SettleSheetPartThreeDTO> partThreeList = null;

        partThreeList = super.handlerInsureIndividualCost(baseInfo.getMdtrtId());

        settleSheetInfoLxDTO.setFeeMapList(partThreeList);
        settleSheetInfoLxDTO.setPastFeeFund(getPastFeeFund(common.getSettleInfoDetail()));
        return settleSheetInfoLxDTO;
    }

    @Override
    public SettleSheetPartTwoLxDTO handlerPastFee(SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetPartTwoLxDTO partTwo = new SettleSheetPartTwoLxDTO();
        partTwo.setScale();

        List<InsurCumulationInfoDTO> cumulationInfoList = super.getInsurCumulationInfoDTO(baseInfo);

        if (CollectionUtil.isEmpty(cumulationInfoList)) {
            return partTwo;
        }
        for (InsurCumulationInfoDTO item : cumulationInfoList) {
            // 累计类别代码
            String cumTypeCode = item.getCumTypeCode();
            BigDecimal cum = item.getCum() == null ? BigDecimal.ZERO : item.getCum();
            // 住院次数
            if ("C0000".equals(cumTypeCode)) {
                partTwo.setTotalCount(partTwo.getTotalCount() + cum.intValue());
            }
            // 医疗费合计
            if ("S0000".equals(cumTypeCode)) {
                partTwo.setS1(BigDecimalUtils.add(partTwo.getS1(), cum));
            }
            // 离休基金支付
            if ("D340101".equals(cumTypeCode)) {
                partTwo.setS2(BigDecimalUtils.add(partTwo.getS2(), cum));
            }
            // 个人账户支付
            if ("D310201".equals(cumTypeCode)) {
                partTwo.setS3(BigDecimalUtils.add(partTwo.getS3(), cum));
            }
        }
        partTwo.setScale();
        return partTwo;
    }

    private BigDecimal getPastFeeFund(List<SettleInfoDetailResDTO> settleInfoDetail) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(settleInfoDetail)) {
            for (SettleInfoDetailResDTO item : settleInfoDetail) {
                // 基金支付类型
                String fundPayType = item.getFundPayType();
                // 基金支付金额
                BigDecimal fundPayamt = item.getFundPayamt();
                // 离休支付基金
                if ("340100".equals(fundPayType)) {
                    bigDecimal = BigDecimalUtils.add(bigDecimal, fundPayamt);
                }
            }
        }
        return bigDecimal;
    }

}
