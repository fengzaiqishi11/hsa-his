package cn.hsa.insure.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: RequestInsure
 * @Describe(描述): 医保请求公共方法
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/10/29 14:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class RequestInsureHnDzpz {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    /**
     * @Menthod callNeusoft
     * @Desrciption  调用东软医保工具类
     * @param hospCode 医院编码
     * @param insureRegCode 医保机构code
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/21 19:17 
     * @Return java.lang.String
     */
    public static JSONObject callNeusoft(String hospCode, String insureRegCode, String url, String param) {
        try {
            Map<String, Object> httpParam = new HashMap<String, Object>();
            httpParam.put("url", url);
            httpParam.put("param", param);
            String result = HttpConnectUtil.doPost(httpParam);
            if (StringUtils.isEmpty(result)) {
                throw new AppException("调用动态库未返回信息，请联系管理员。");
            }
            JSONObject resultObj = JSON.parseObject(result);
            String code = resultObj.get("code").toString();
            if (!"0".equals(code)) {
                throw new AppException((String) resultObj.get("message"));
            }
            return resultObj;
        }catch (Exception e){
            throw new AppException("调用医保提示【"+e.getMessage()+"】");
        }
    }

    public static void main(String[] args) {
        RequestInsureHnDzpz re = new RequestInsureHnDzpz();
        String inData = "{\n" +
                "\"data\": {\n" +
                "\"operatorId\": \"008\",\n" +
                "\"operatorName\": \"TEST\",\n" +
                "\"officeId\": \"01\",\n" +
                "\"officeName\": \"内科\",\n" +
                "\"orgId\": \"1234567\",\n" +
                "\"businessType\": \"01101\"\n" +
                "},\n" +
                "\"orgId\": \"1234567\",\n" +
                "\"transType\": \"ec.query\"\n" +
                "}";
        re.callNeusoft("","","http://172.16.61.103:8080/SilnterfaceServer/NationEcc",inData);
    }
}
