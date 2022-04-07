package cn.hsa.insure.unifiedpay.util.deptInfo;

import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DeptInfoRevocationReqUtil
 * @Deacription 科室信息撤销-3403
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_3403)
public class DeptInfoRevocationReqUtil<T> implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> httpParam = new HashMap<>(3);

        httpParam.put("hosp_dept_codg", MapUtils.get(map,"code"));
        httpParam.put("hosp_dept_name", MapUtils.get(map,"deptName"));
        httpParam.put("begntime", MapUtils.get(map,"startTime"));

        checkRequest(map);
        return paramJson;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
