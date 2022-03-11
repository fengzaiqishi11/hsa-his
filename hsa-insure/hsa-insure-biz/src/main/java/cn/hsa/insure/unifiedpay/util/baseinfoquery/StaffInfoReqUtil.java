package cn.hsa.insure.unifiedpay.util.baseinfoquery;

import cn.hsa.exception.BizRtException;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName StaffInfoReqUtil
 * @Deacription 医执人员信息查询-5102
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_5102)
public class StaffInfoReqUtil<T> implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> httpParam = new HashMap<>(5);

        httpParam.put("prac_psn_type", MapUtils.get(map, "pracPsnType"));
        httpParam.put("psn_cert_type", MapUtils.get(map, "psnCertType"));
        httpParam.put("certno", MapUtils.get(map, "certno"));
        httpParam.put("prac_psn_name", MapUtils.get(map, "pracPsnName"));
        httpParam.put("prac_psn_code", MapUtils.get(map, "pracPsnCode"));

        checkRequest(httpParam);
        return JSON.toJSONString(httpParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        String prac_psn_type = MapUtils.get(param,"prac_psn_type");
        if(StringUtils.isEmpty(prac_psn_type)){
            throw new BizRtException(-1,"查询医保医执人员信息，职业人员分类不能为空");
        }
        return true;
    }

}
