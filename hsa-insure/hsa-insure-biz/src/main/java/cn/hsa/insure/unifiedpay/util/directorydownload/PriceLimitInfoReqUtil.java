package cn.hsa.insure.unifiedpay.util.directorydownload;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PriceLimitInfoReqUtil
 * @Deacription 医保目录限价信息查询-1318
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1318)
public class PriceLimitInfoReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);
        Map<String, Object> paramMap = new HashMap<>();
        // 查询时间点
        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S));
        // 医保目录编码
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode"));
        // 医保目录限价类型
        paramMap.put("hilist_lmtpric_type", "");
        // 医保目录超限处理方式
        paramMap.put("overlmt_dspo_way", "");
        //参保机构医保区划
        paramMap.put("insu_admdvs", "");
        // 开始日期
        paramMap.put("begndate", "");
        // 结束日期
        paramMap.put("enddate", "");
        // 有效标志
        paramMap.put("vali_flag", Constants.SF.S);
        // 唯一记录号
        paramMap.put("rid", "");
        // 表名
        paramMap.put("tabname", "");
        // 统筹区
        paramMap.put("poolarea_no", "");
        // 更新时间
        paramMap.put("updt_time", MapUtils.get(map, "updtTime"));
        // 当前页数
        paramMap.put("page_num", MapUtils.get(map, "pageNum"));
        // 本页数据量
        paramMap.put("page_size", MapUtils.get(map, "pageSize"));

        checkRequest(paramMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_1318);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
