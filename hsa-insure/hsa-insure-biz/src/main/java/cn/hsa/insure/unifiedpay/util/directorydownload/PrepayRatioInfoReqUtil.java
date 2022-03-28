package cn.hsa.insure.unifiedpay.util.directorydownload;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PrepayRatioInfoReqUtil
 * @Deacription 医保目录先自付比例信息查询-1319
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1319)
public class PrepayRatioInfoReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> paramMap = new HashMap<>(14);

        // 查询时间点
        paramMap.put("query_date", "");
        // 医保目录编码
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode"));
        //医保目录自付比例人员类别
        paramMap.put("selfpay_prop_psn_type", "");
        // 目录自付比例类别
        paramMap.put("selfpay_prop_type", "");

        //参保机构医保区划
        paramMap.put("insu_admdvs", "");
        // 开始日期
        paramMap.put("begndate", null);
        // 结束日期
        paramMap.put("enddate", null);
        // 有效标志
        paramMap.put("vali_flag", "1");
        // 唯一记录号
        paramMap.put("rid", "");
        // 表名
        paramMap.put("tabname", null);
        // 统筹区
        paramMap.put("poolarea_no", "");
        // 更新时间
        paramMap.put("updt_time", MapUtils.get(map, "updtTime"));
        // 当前页数
        paramMap.put("page_num", MapUtils.get(map, "pageNum"));
        // 本页数据量
        paramMap.put("page_size", MapUtils.get(map, "pageSize"));

        checkRequest(paramMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_1319);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
