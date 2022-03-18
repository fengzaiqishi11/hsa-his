package cn.hsa.report.business.bo.impl.factory.settle;


import cn.hsa.report.business.bean.SettleCommonInfoDTO;
import cn.hsa.report.business.bean.SettleInfoResDTO;
import cn.hsa.report.business.bean.SettleSheetBaseInfoDTO;
import cn.hsa.report.business.bean.SettleSheetInfoYdDTO;
import cn.hsa.report.business.bean.SettleSheetPartFiveDTO;
import cn.hsa.report.business.bean.SettleSheetPartFiveYdDTO;
import cn.hsa.report.business.bean.SettleSheetPartOneDTO;
import cn.hsa.report.business.bean.SettleSheetPartOneYdDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hutool.core.convert.Convert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @ClassName SettleSheetByZySnydProcess
 * @Deacription 住院省内异地结算单
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
@Service
public class SettleSheetByZySnydProcess extends SettleSheetProcess {

    @Override
    public SettleSheetInfoYdDTO downLoadSettleInfo(SettleSheetBaseInfoDTO baseInfo) {
        return downLoadYdSettleInfo(baseInfo);
    }

    /**
     * 省内异地、省外异地结算单流程
     *
     * @param baseInfo
     * @return
     */
    SettleSheetInfoYdDTO downLoadYdSettleInfo(SettleSheetBaseInfoDTO baseInfo) {
        SettleCommonInfoDTO common = getSettleCommonInfoDTO(baseInfo);

        SettleSheetInfoYdDTO settleSheetInfoYdDTO = new SettleSheetInfoYdDTO();

        settleSheetInfoYdDTO.setSheetCode((String) baseInfo.getCustomConfigMap().get("sheetCode"));

        settleSheetInfoYdDTO.setMedisnInfMap(handlerMedisnInfo(common.getSettleInfo(), baseInfo));

        settleSheetInfoYdDTO.setBaseInfoMap(handlerYdBaseInfo(common.getSettleInfo(), baseInfo));

        settleSheetInfoYdDTO.setPolicyMapList(handlerPolicy(baseInfo));

        settleSheetInfoYdDTO.setFeeMapList(handlerInsureIndividualCost(baseInfo.getMdtrtId()));

        settleSheetInfoYdDTO.setFourPartMap(handlerYdInsureSettleFee(common.getSettleInfo()));

        return settleSheetInfoYdDTO;
    }

    /**
     * 省内异地、省外异地基本信息
     *
     * @param settleInfoResDTO
     * @param baseInfo
     * @return
     */
    SettleSheetPartOneYdDTO handlerYdBaseInfo(SettleInfoResDTO settleInfoResDTO, SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetPartOneDTO partOne = handlerCommonBaseInfo(settleInfoResDTO, baseInfo);

        SettleSheetPartOneYdDTO partOneYd = new SettleSheetPartOneYdDTO();
        BeanUtils.copyProperties(partOne,partOneYd);

//        String mdtrtareaAdmvs = insurUtil.getMdtrtareaAdmvs(baseInfo.getAdmdvs());
//        String mdtrtareaAdmvsName = registerBO.queryAdmdvsByInsuplcAdmdvs(StringUtils.isEmpty(mdtrtareaAdmvs) ? sysUserDTO.getDistrictId() : mdtrtareaAdmvs).getInsuplcAdmdvsName();
        String mdtrtareaAdmvsName = "";
        partOneYd.setMdtrtareaAdmvsName(mdtrtareaAdmvsName);

//        List<RegDiseInfoDTO> diseList = regDiseInfoBO.queryListByVisitId(baseInfo.getVisitId(), InsurConstants.OUT_HOSP_STATUS);
//        diseList = diseList.stream().filter(data -> data.getMaindiagFlag().equals(InsurConstants.MAINDIAG_FLAG_TURE)).collect(Collectors.toList());

        partOneYd.setInDiseaseName(baseInfo.getInsurMaindiagName());
//        partOneYd.setOutDiseaseName(CollectionUtil.isEmpty(diseList) ? "" : diseList.get(0).getInsurDiagName());
        partOneYd.setVisitNationCode(baseInfo.getDscgDeptName());
        partOneYd.setBka006Name(partOne.getMedType());
        return partOneYd;
    }

    /**
     * 省内异地、省外异地结算信息
     *
     * @param setlInfo
     * @return
     */
    SettleSheetPartFiveYdDTO handlerYdInsureSettleFee(SettleInfoResDTO setlInfo) {
        SettleSheetPartFiveDTO partFive = handlerCommonInsureSettleFee(setlInfo);

        SettleSheetPartFiveYdDTO partFiveYd = new SettleSheetPartFiveYdDTO();
        partFiveYd.setScale();
        BeanUtils.copyProperties(partFive,partFiveYd);
        partFiveYd.setFundSumAmtCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getFundSumAmt()) ? "零元" : Convert.digitToChinese(partFiveYd.getFundSumAmt()));
        partFiveYd.setFundPaySumamtCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getFundPaySumamt()) ? "零元" : Convert.digitToChinese(partFiveYd.getFundPaySumamt()));
        partFiveYd.setMedfeeSumamtCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getMedfeeSumamt()) ? "零元" : Convert.digitToChinese(partFiveYd.getMedfeeSumamt()));
        partFiveYd.setHifpPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getHifpPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getHifpPay()));
        partFiveYd.setCvlservPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getCvlservPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getCvlservPay()));
        partFiveYd.setHifesPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getHifesPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getHifesPay()));
        partFiveYd.setHifobPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getHifobPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getHifobPay()));
        partFiveYd.setHifmipPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getHifmipPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getHifmipPay()));
        partFiveYd.setAcctPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getAcctPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getAcctPay()));
        partFiveYd.setActPayDedcCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getActPayDedc()) ? "零元" : Convert.digitToChinese(partFiveYd.getActPayDedc()));
        partFiveYd.setOthPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getOthPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getOthPay()));
        partFiveYd.setHospPriceCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getHospPrice()) ? "零元" : Convert.digitToChinese(partFiveYd.getHospPrice()));
        partFiveYd.setPsnCashPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getPsnCashPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getPsnCashPay()));
        partFiveYd.setBalcCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getBalc()) ? "零元" : Convert.digitToChinese(partFiveYd.getBalc()));
        partFiveYd.setMafPayCN(BigDecimalUtils.equals(BigDecimal.ZERO, partFiveYd.getMafPay()) ? "零元" : Convert.digitToChinese(partFiveYd.getMafPay()));

        return partFiveYd;
    }
}
