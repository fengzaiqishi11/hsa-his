package cn.hsa.insure.unifiedpay.util.directorydownload;

import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DirectoryInfoReqUtil
 * @Deacription 医保目录信息查询-1312
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1312)
public class DirectoryInfoReqUtil<T> implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> paramMap = new HashMap<>(20);

        // 查询时间点
        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S));
        // 目录类别
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode"));
        //参保机构医保区划
        paramMap.put("insu_admdvs", "");
        // 开始日期
        paramMap.put("begndate", "");
        //五笔助记码
        paramMap.put("wubi", "");
        // 拼音助记码
        paramMap.put("pinyin", "");
        //医疗收费项目类别
        paramMap.put("med_chrgitm_type", "");
        // 收费项目等级
        paramMap.put("chrgitm_lv", "");
        //限制使用标志
        paramMap.put("lmt_used_flag", "");
        // 目录类别
        paramMap.put("list_type", MapUtils.get(map, "listType"));
        //医疗使用标志
        paramMap.put("med_use_flag", "");
        // 生育使用标志
        paramMap.put("matn_used_flag", "");
        //医保目录使用类别
        paramMap.put("hilist_use_type", "");
        // 限复方使用类型
        paramMap.put("lmt_cpnd_type", "");
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
        return JSON.toJSONString(paramMap);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
