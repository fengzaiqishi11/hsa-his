package cn.hsa.dzpz.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.bo.DzpzOutptBo;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: OutptBoImpl
 * @Describe(描述): 门诊医保开放统一接口 Bo实现层
 * @Author: xingyu.xie
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 8:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class DzpzOutptBOImpl extends HsafBO implements DzpzOutptBo {

    @Resource
    InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    OutptTmakePriceFormService outptTmakePriceFormService;



    /**
     * @Menthod ecQuery
     * @Desrciption 电子凭证解码
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public String ecQuery(Map<String,Object> param) {

        JSONObject requestJson = new JSONObject();

        String url = "http://218.77.183.182:443/test/localcfc/api/hsecfc/localQrCodeQuery";

        JSONObject data = new JSONObject();
        // 机构ID
        data.put("orgId",MapUtils.get(param,"orgId"));
        // 用码业务类型
        data.put("businessType","01101");
        // 收款员编号
        data.put("operatorId",MapUtils.get(param,"userId"));
        // 收款员姓名
        data.put("operatorName",MapUtils.get(param,"userName"));
        // 医保科室编号
        data.put("officeId",MapUtils.get(param,"loginDeptId"));
        // 科室名称
        data.put("officeName",MapUtils.get(param,"loginDeptName"));

        requestJson.put("data",data);
        requestJson.put("orgId",MapUtils.get(param,"orgId"));
        requestJson.put("transType", Constant.hainan.OUTPT.ecQuery);
        requestJson.put("url",url);

        return requestJson.toJSONString();

    }

    /**
     * @Menthod hosQueryInsu
     * @Desrciption 参保人信息查询
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public String hosQueryInsu(Map<String,Object> param) {
        String url = "http://10.102.12.216/mpc-local/test/pmc/api";

        JSONObject requestJson = new JSONObject();

        JSONObject data = new JSONObject();
        // 应用渠道编号
        data.put("appId","37B0389095E640F89DEE9F5C8D763E17");
        // 机构编码
        data.put("orgCodg","200544");
        // 诊断类别r
        data.put("medType",Constant.hainan.medType.XYZDJ);
        // 电子凭证授权 token
        data.put("ecToken",MapUtils.get(param,"ecToken"));
        // 证件号码
        data.put("idNo",MapUtils.get(param,"idNo"));
        // 用户姓名
        data.put("userName",MapUtils.get(param,"userName"));
        // 证件类别
        data.put("idType",MapUtils.get(param,"idType"));
        // 统筹区编码
        data.put("insuCode",MapUtils.get(param,"insuOrg"));

        JSONObject extData = new JSONObject();

        extData.put("systemNo","7");
        // 所属系统标识
        data.put("extData",extData);

        requestJson.put("data",data);
        requestJson.put("orgId","200544");
        requestJson.put("transType",Constant.hainan.OUTPT.hosQueryInsu);
        requestJson.put("url",url);

        return requestJson.toJSONString();
    }

    /**
     * @Menthod delFee
     * @Desrciption 门诊退费
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public String backFee(Map<String,Object> param) {
        String hospCode = MapUtils.get(param, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setVisitNo(MapUtils.get(param, "visitNo"));
        insureIndividualVisitDTO.setHospCode(hospCode);
        InsureIndividualVisitDTO byVisitNo = insureIndividualVisitDAO.getByVisitNo(insureIndividualVisitDTO);
        String payOrdId = byVisitNo == null ? "" : byVisitNo.getPayOrdId();
        String payToken = byVisitNo == null ? "" : byVisitNo.getPayToken();
        if ( StringUtils.isEmpty(payOrdId) || StringUtils.isEmpty(payToken) ){
           throw new AppException("支付订单号或支付token为空！");
        } else {

            param.put("payOrdId",byVisitNo.getPayOrdId());
            param.put("payOrdToken",byVisitNo.getPayOrdId());

            String url = "http://10.103.161.181:8082/pmc/api";

            Map patientInfo = MapUtils.get(param, "patientInfo");


            JSONObject requestJson = new JSONObject();

            JSONObject data = new JSONObject();
            // 应用渠道编号
            data.put("appId","37B0389095E640F89DEE9F5C8D763E17");
            // 支付订单号
            data.put("payOrdId", MapUtils.get(param,"payOrdId"));
            // 应用退款流水号
            data.put("appRefdSn", SnowflakeUtils.getId());
            // 应用退费时间
            data.put("appRefdTime", DateUtils.getNow());
            // 总退费金额
            data.put("totlRefdAmt",MapUtils.get(patientInfo,"realityPrice"));
            // 医保个人账户支付
            data.put("psnAcctRefdAmt",MapUtils.get(patientInfo,"miPrice"));
            // 基金支付
            data.put("fundRefdAmt","0");
            // 现金退费金额
            data.put("cashRefdAmt",MapUtils.get(patientInfo,"selfPrice"));
            // 电子凭证授权 Token
            data.put("ecToken",MapUtils.get(param,"ecToken"));
            // 退费类型 ALL:全部，CASH:只退现金 HI:只退医保
            data.put("refdType","ALL");

            JSONObject extData = new JSONObject();
            // 经办人
            extData.put("opter","管理员");
            // 所属系统标识
            extData.put("systemNo","7");
            // 医保拓展信息
            data.put("extData",extData);

            requestJson.put("data",data);
            requestJson.put("orgId","200544");
            requestJson.put("transType",Constant.hainan.OUTPT.hosRefundSetl);
            requestJson.put("url",url);

            return requestJson.toJSONString();
        }

    }

    /**
     * @Menthod saveFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Boolean saveUpLoadFeeResult(Map<String,Object> param) {
        outptTmakePriceFormService.seltSucCallback(param);
        return true;
    }


}
