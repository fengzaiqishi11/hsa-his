package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OutptSettleReqUtil
 * @Deacription 门诊结算-2207
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2207)
public class OutptSettleReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");
        Map<String, Object> costMapInfo = MapUtils.get(map, "costMapInfo");
        String batchNo = MapUtils.get(map, "batchNo").toString();

        // 入参，患者信息
        Map<String, Object> settleMap = new HashMap<>();
        // 人员编号
        settleMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        // 就诊凭证类型
        settleMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType());
        // 就诊凭证编号
        settleMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo());
        if (Constants.SF.S.equals(MapUtils.get(map, "isReadCardPay"))) {
            // 就诊凭证类型
            settleMap.put("mdtrt_cert_type", MapUtils.get(map, "bka895"));
            // 就诊凭证编号
            settleMap.put("mdtrt_cert_no", MapUtils.get(map, "bka896"));
        }
        // 卡识别码
        settleMap.put("card_sn", insureIndividualVisitDTO.getCardIden());
        // 证件类型
        settleMap.put("psn_cert_type", "01");
        // 身份证号
        settleMap.put("certno", insureIndividualVisitDTO.getAac002());
        // 人员类别
        settleMap.put("psn_type", insureIndividualVisitDTO.getBka035());
        // 姓名
        settleMap.put("psn_name", insureIndividualVisitDTO.getAac003());
        // 医疗类别
        settleMap.put("med_type", insureIndividualVisitDTO.getAka130());
        DecimalFormat df1 = new DecimalFormat("0.00");
        String realityPrice = df1.format(BigDecimalUtils.convert(costMapInfo.get("costStr").toString()));
        // 医疗费总额
        settleMap.put("medfee_sumamt", BigDecimalUtils.convert(realityPrice));
        // 个人结算方式
        settleMap.put("psn_setlway", Constants.JSFS.PTJS);
        // 就诊id
        settleMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        batchNo = StringUtils.isEmpty(batchNo) ? "0" : batchNo;
        // 就诊id
        settleMap.put("chrg_bchno", batchNo);
        // 险种类型
        settleMap.put("insutype", insureIndividualVisitDTO.getAae140());
        // 个人账户使用标志
        settleMap.put("acct_used_flag", outptVisitDTO.getIsUseAccount());
        // 发票号
        settleMap.put("invono", "");
        // 全自费金额
        settleMap.put("fulamt_ownpay_amt", "");
        // 超限价金额
        settleMap.put("overlmt_selfpay", "");
        // 先行自付金额
        settleMap.put("preselfpay_amt", "");
        // 符合政策范围金额
        settleMap.put("inscp_scp_amt", "");
        // 公立医院改革标志
        settleMap.put("pub_hosp_rfom_flag", "");

        HashMap commParam = new HashMap();
        checkRequest(settleMap);

        commParam.put("dataMap", settleMap);
        commParam.put("infno",Constant.UnifiedPay.OUTPT.UP_2207);

        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
