package cn.hsa.report.business.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.module.report.business.bo.ReportBaseDataBO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.RedisUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ReportConfigurationBOImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Component
@Slf4j
public class ReportBaseDataBOImpl extends HsafBO implements ReportBaseDataBO {

    @Resource
    private InsureDictService insureDictService_consumer;

    @Resource
    RedisUtils redisUtils;

    @Override
    public Map<String, String> getInsureDict(String hospCode, String regCode, String code) {
        return reloadRedisData(hospCode, regCode, code);
    }

    private Map<String, String> reloadRedisData(String hospCode, String regCode, String code) {
        String key = "insur_code_" + hospCode + "_" + regCode + "_" + code.toLowerCase();
        if (redisUtils.hasKey(key)) {
            return JSONObject.parseObject(redisUtils.get(key), Map.class);
        }

        Map map = new HashMap(3);
        map.put("hospCode", hospCode);
        map.put("insureRegCode", regCode);
        map.put("code", code);
        Map<String, String> dictMap = insureDictService_consumer.queryDictByCode(map).getData();

        if (MapUtils.isEmpty(dictMap)) {
            log.error("医保字典不存在，请配置处理！");
            throw new RuntimeException("医保字典不存在，请配置处理！");
        }
        redisUtils.set(key, JSONObject.toJSONString(dictMap));
        return dictMap;
    }

    @Override
    public String getInsureDictName(String hospCode, String regCode, String code, String value) {
        Map<String, String> dictMap = getInsureDict(hospCode, regCode, code);
        return MapUtils.isEmpty(dictMap) ? "" : dictMap.get(value);
    }

    @Override
    public String getAdmdvsName(String hospCode, String admdvsCode) {
        Map map = new HashMap(2);
        map.put("hospCode", hospCode);
        map.put("admdvsCode", admdvsCode);
        Map<String, String> admdvsMap = insureDictService_consumer.queryOneAdmdvsInfo(map).getData();
        return MapUtils.isEmpty(admdvsMap) ? "" : admdvsMap.get("admdvsName");
    }

    @Override
    public Map<String, String> getSysCode(String hospCode, String code) {
        return reloadSysCodeRedisData(hospCode, code);
    }

    @Override
    public String getSysCodeName(String hospCode, String regCode, String code, String value) {
        Map<String, String> dictMap = getSysCode(hospCode, code);
        return MapUtils.isEmpty(dictMap) ? "" : dictMap.get(value);
    }

    private Map<String, String> reloadSysCodeRedisData(String hospCode, String code) {
        String key = "sys_code_" + hospCode + "_" + code.toLowerCase();
        if (redisUtils.hasKey(key)) {
            return JSONObject.parseObject(redisUtils.get(key), Map.class);
        }

        Map map = new HashMap(2);
        map.put("hospCode", hospCode);
        map.put("code", code);
        Map<String, String> dictMap = insureDictService_consumer.querySysCodeByCode(map).getData();

        if (MapUtils.isEmpty(dictMap)) {
            log.error("系统字典不存在，请配置处理！");
            throw new RuntimeException("系统字典不存在，请配置处理！");
        }
        redisUtils.set(key, JSONObject.toJSONString(dictMap));
        return dictMap;
    }

}
