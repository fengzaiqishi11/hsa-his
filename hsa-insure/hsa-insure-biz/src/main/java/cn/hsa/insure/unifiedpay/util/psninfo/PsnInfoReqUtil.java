package cn.hsa.insure.unifiedpay.util.psninfo;

import cn.hsa.exception.BizRtException;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1101)
public class PsnInfoReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> httpParam = new HashMap<>();
        String mdtrtCertType = MapUtils.get(map,"mdtrt_cert_type");
        // 证件类型 HIS住院登记的证件类型
        httpParam.put("psn_cert_type", "01");
        // 姓名
        httpParam.put("psn_name", MapUtils.get(map,"mdtrt_cert_type"));
        // 证件号码
        httpParam.put("certno",  MapUtils.get(map,"mdtrt_cert_type"));
        httpParam.put("begntime", MapUtils.get(map,"begntime"));
        //  电子凭证 传值01
        if(Constant.UnifiedPay.CKLX.DZPZ.equals(mdtrtCertType)) {
            httpParam.put("mdtrt_cert_type", "01");
            httpParam.put("mdtrt_cert_no", MapUtils.get(map,"mdtrt_cert_no"));
        }
        // 身份证 传值02
        else if(Constant.UnifiedPay.CKLX.SFZ.equals(mdtrtCertType)  ) {
            httpParam.put("mdtrt_cert_type", "02");
            httpParam.put("mdtrt_cert_no", MapUtils.get(map,"mdtrt_cert_no"));
        }
        // 社保卡 传值03
        else if(Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)){
            httpParam.put("mdtrt_cert_type","03");
            httpParam.put("mdtrt_cert_no", MapUtils.get(map,"mdtrt_cert_no"));
            httpParam.put("card_sn", MapUtils.get(map,"card_sn"));
        }

        checkRequest(httpParam);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        String mdtrtCertType = MapUtils.get(param,"mdtrt_cert_type");
        if("03".equals(mdtrtCertType)){
            if(StringUtils.isEmpty(MapUtils.get(param,"card_sn"))){
                throw new BizRtException(-1,"使用社保卡时，卡识别码不能为空");
            }
        }
        return true;
    }

}
