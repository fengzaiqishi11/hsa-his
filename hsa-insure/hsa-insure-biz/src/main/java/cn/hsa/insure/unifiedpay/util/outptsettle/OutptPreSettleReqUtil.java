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
 * @ClassName OutptPreSettleReqUtil
 * @Deacription 门诊预结算-2206
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2206)
public class OutptPreSettleReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;

        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");
        Map<String, Object> costMapInfo = MapUtils.get(map, "costMapInfo");
        String batchNo = MapUtils.get(map, "batchNo").toString();

        // 入参，患者信息
        Map<String, Object> preSettleMap = new HashMap<>(16);
        // 人员编号
        preSettleMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        //  就诊凭证类型
        preSettleMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType());
        //  就诊凭证编号
        preSettleMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo());
        if (Constants.SF.S.equals(MapUtils.get(map, "isReadCardPay"))) {
            //  就诊凭证类型
            preSettleMap.put("mdtrt_cert_type", MapUtils.get(map, "bka895"));
            //  就诊凭证编号
            preSettleMap.put("mdtrt_cert_no", MapUtils.get(map, "bka896"));
        }
        // 卡识别码
        preSettleMap.put("card_sn", insureIndividualVisitDTO.getCardIden());
        // 证件类型
        preSettleMap.put("psn_cert_type", "01");
        // 身份证号
        preSettleMap.put("certno", insureIndividualVisitDTO.getAac002());
        // 人员类别
        preSettleMap.put("psn_type", insureIndividualVisitDTO.getBka035());
        // 姓名
        preSettleMap.put("psn_name", insureIndividualVisitDTO.getAac003());
        // 医疗类别
        preSettleMap.put("med_type", insureIndividualVisitDTO.getAka130());
        DecimalFormat df1 = new DecimalFormat("0.00");
        String realityPrice = df1.format(BigDecimalUtils.convert(costMapInfo.get("costStr").toString()));
        // 医疗费总额
        preSettleMap.put("medfee_sumamt", BigDecimalUtils.convert(realityPrice));
        // 个人结算方式
        preSettleMap.put("psn_setlway", Constants.JSFS.PTJS);
        // 就诊id
        preSettleMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        batchNo = StringUtils.isEmpty(batchNo) ? "0" : batchNo;
        // 收费批次号
        preSettleMap.put("chrg_bchno", batchNo);
        // 个人账户使用标志
        preSettleMap.put("acct_used_flag", outptVisitDTO.getIsUseAccount());
        // 险种类型
        preSettleMap.put("insutype", insureIndividualVisitDTO.getAae140());
        // 公立医院改革标志
        preSettleMap.put("pub_hosp_rfom_flag", null);
        //就诊方式 广州接口字段
        preSettleMap.put("mdtrt_mode", "0");

        HashMap commParam = new HashMap();
        checkRequest(preSettleMap);
        commParam.put("dataMap", preSettleMap);
        commParam.put("infno",Constant.UnifiedPay.OUTPT.UP_2206);

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
