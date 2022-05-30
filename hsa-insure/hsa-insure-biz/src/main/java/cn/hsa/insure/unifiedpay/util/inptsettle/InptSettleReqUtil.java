package cn.hsa.insure.unifiedpay.util.inptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InptSettleReqUtil
 * @Deacription 住院结算-2304
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.INPT.UP_2304)
public class InptSettleReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;

        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");

        Map<String,Object> dataMap = new HashMap<>();

        //结算刷卡如果是电子凭证，校验paytoken值
        /*if (ObjectUtil.isEmpty(inptVisitDTO.getMdtrtCertType())){
          throw new RuntimeException("结算刷卡未传入就诊凭证类型!");
        }
        //电子凭证
        if ("01".equals(inptVisitDTO.getMdtrtCertType())){
          dataMap.put("mdtrt_cert_type",inptVisitDTO.getMdtrtCertType());// 就诊凭证类型
          dataMap.put("mdtrt_cert_no",inptVisitDTO.getMdtrtCertNo()); // 就诊凭证编号
        }else{
          dataMap.put("mdtrt_cert_type",insureIndividualVisitDTO.getMdtrtCertType());// 就诊凭证类型
          dataMap.put("mdtrt_cert_no",insureIndividualVisitDTO.getMdtrtCertNo()); // 就诊凭证编号
        }*/

        dataMap.put("psn_no",insureIndividualVisitDTO.getAac001());// 人员编号
        dataMap.put("mdtrt_cert_type",insureIndividualVisitDTO.getMdtrtCertType());// 就诊凭证类型
        dataMap.put("mdtrt_cert_no",insureIndividualVisitDTO.getMdtrtCertNo()); // 就诊凭证编号
        if("03".equals(insureIndividualVisitDTO.getMdtrtCertType())){
            dataMap.put("card_sn",insureIndividualVisitDTO.getCardIden()); // 卡识别码
        }

        String isReadCard = MapUtils.get(map,"isReadCard");
        String bka895 = MapUtils.get(map,"bka895");
        String bka896 = MapUtils.get(map,"bka896");
        String cardIden = MapUtils.get(map,"cardIden");
        if (Constants.SF.S.equals(isReadCard) && StringUtils.isNotEmpty(bka895) && StringUtils.isNotEmpty(bka896)) {
            dataMap.put("mdtrt_cert_type",bka895);// 就诊凭证类型
            dataMap.put("mdtrt_cert_no",bka896); // 就诊凭证编号
            if("03".equals(bka895)){
                dataMap.put("card_sn",cardIden); // 就诊凭证编号
            }
        }

        // 证件类型
        dataMap.put("psn_cert_type",inptVisitDTO.getCertCode());
        dataMap.put("certno",insureIndividualVisitDTO.getAac002());// 就诊方式
        dataMap.put("psn_name",inptVisitDTO.getName());// 持卡就诊基本信息
        dataMap.put("psn_type",insureIndividualVisitDTO.getBka035());

        DecimalFormat df1 = new DecimalFormat("0.00");
        String realityPrice = df1.format(BigDecimalUtils.convert(MapUtils.get(map,"costStr")));
        dataMap.put("medfee_sumamt",BigDecimalUtils.convert(realityPrice));// 医疗费总额
        dataMap.put("psn_setlway",Constants.JSFS.PTJS); // 个人结算方式
        dataMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());// 就诊ID
        dataMap.put("acct_used_flag",MapUtils.get(map,"insureAccoutFlag"));// 个人账户使用标志
        dataMap.put("insutype",insureIndividualVisitDTO.getAae140());//险种类型
        dataMap.put("invono",""); // 发票号

        /**
         * 是否开启中途结算
         */
        if("1".equals(insureIndividualVisitDTO.getIsHalfSettle())){
            dataMap.put("mid_setl_flag",Constants.SF.S);// 中途结算标志
        }else{
            dataMap.put("mid_setl_flag",Constants.SF.F);// 中途结算标志
        }
        dataMap.put("fulamt_ownpay_amt",0.00);// 全自费金额
        dataMap.put("overlmt_selfpay",0.00);// 超限价金额
        dataMap.put("preselfpay_amt",0.00);// 先行自付金额
        dataMap.put("inscp_scp_amt",0.00);// 符合政策范围金额

        dataMap.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        dataMap.put("mdtrtarea_admvs",insureIndividualVisitDTO.getMdtrtareaAdmvs());
        dataMap.put("med_type",insureIndividualVisitDTO.getAka130());
        dataMap.put("mdtrt_mode","0");
        dataMap.put("hcard_basinfo",insureIndividualVisitDTO.getHcardBasinfo());
        dataMap.put("hcard_chkinfo",insureIndividualVisitDTO.getHcardChkinfo());

        HashMap commParam = new HashMap();
        checkRequest(commParam);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.INPT.UP_2304);

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
