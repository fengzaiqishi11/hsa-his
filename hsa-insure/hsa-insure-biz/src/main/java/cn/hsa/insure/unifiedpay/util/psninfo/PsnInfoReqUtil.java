package cn.hsa.insure.unifiedpay.util.psninfo;

import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
<<<<<<< HEAD:hsa-insure/hsa-insure-biz/src/main/java/cn/hsa/insure/unifiedpay/util/PsnInfoReqUtil.java
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.StringUtils;
=======
>>>>>>> updstream/develop:hsa-insure/hsa-insure-biz/src/main/java/cn/hsa/insure/unifiedpay/util/psninfo/PsnInfoReqUtil.java
import com.alibaba.fastjson.JSON;
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
public class PsnInfoReqUtil<T> implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map param = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> httpParam = new HashMap<>();
        String mdtrtCertType = (String) param.get("mdtrt_cert_type");
        // 证件类型 HIS住院登记的证件类型
        httpParam.put("psn_cert_type", "01");
        // 姓名
        httpParam.put("psn_name", param.get("mdtrt_cert_type"));
        // 证件号码
        httpParam.put("certno",  param.get("mdtrt_cert_type"));
        httpParam.put("begntime", param.get("begntime"));
        //  电子凭证 传值01
        if(Constant.UnifiedPay.CKLX.DZPZ.equals(mdtrtCertType)) {
            httpParam.put("mdtrt_cert_type", "01");
            httpParam.put("mdtrt_cert_no", param.get("mdtrt_cert_no"));
        }
        // 身份证 传值02
        else if(Constant.UnifiedPay.CKLX.SFZ.equals(mdtrtCertType)  ) {
            httpParam.put("mdtrt_cert_type", "02");
            httpParam.put("mdtrt_cert_no", param.get("mdtrt_cert_no"));
        }
        // 社保卡 传值03
        else if(Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)){
            httpParam.put("mdtrt_cert_type","03");
            httpParam.put("mdtrt_cert_no", param.get("mdtrt_cert_no"));
            httpParam.put("card_sn", param.get("card_sn"));
        }

        checkRequest(map);
        return paramJson;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
