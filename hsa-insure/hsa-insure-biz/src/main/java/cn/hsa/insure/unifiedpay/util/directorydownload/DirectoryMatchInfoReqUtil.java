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
 * @ClassName DirectoryMatchInfoReqUtil
 * @Deacription 医药机构目录匹配信息查询-1317
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1317)
public class DirectoryMatchInfoReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> dataMap = new HashMap<>(12);
        // 查询时间点
        dataMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));
        // 定点医药机构编号
        dataMap.put("fixmedins_code", "");
        // 定点医药机构目录编号
        dataMap.put("medins_list_codg", "");
        // 定点医药机构目录名称
        dataMap.put("medins_list_name", "");
        // 参保机构医保区划
        dataMap.put("insu_admdvs", MapUtils.get(map, "insu_admdvs"));
        // 目录类别
        dataMap.put("list_type", "");
        // 医疗目录编码
        dataMap.put("med_list_codg", "");
        // 开始日期
        dataMap.put("begndate", "");
        // 有效标志
        dataMap.put("vali_flag", Constants.SF.S);
        // 更新时间
        dataMap.put("updt_time", DateUtils.format(MapUtils.get(map, "startDate"), DateUtils.Y_M_D));
        // 当前页数
        dataMap.put("page_num", 1);
        // 本页数据量
        dataMap.put("page_size", Integer.MAX_VALUE);

        checkRequest(dataMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_1317);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
