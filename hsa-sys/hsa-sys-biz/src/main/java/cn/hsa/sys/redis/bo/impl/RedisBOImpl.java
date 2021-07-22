package cn.hsa.sys.redis.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.redis.bo.RedisBO;
import cn.hsa.module.sys.redis.dao.RedisDAO;
import cn.hsa.util.Constants;
import cn.hsa.util.RedisUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sys.redis.bo.impl
 * @Class_name: RedisBOImpl
 * @Describe:  参数service接口实现层（提供给dubbo调用）
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/15  10:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class RedisBOImpl extends HsafBO implements RedisBO {

    @Resource
    RedisDAO redisDAO;

    @Resource
    RedisUtils redisUtils;

    /**
     * @Method getData
     * @Desrciption
     * @Param
     * [map]
     * key  hospCode
     * @Author liaojunjie
     * @Date   2021/1/21 11:05
     * @Return java.util.List<java.lang.Object>
     **/
    @Override
    public List<Map<String, Object>> getData(Map map) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        String key = (String) map.get("key");

        if (StringUtils.isNotEmpty(key)) {
            List<String> keys = Arrays.asList(key.split("_"));
            if (keys.size() == 3) {
                String formName = keys.get(0).toUpperCase();
                String hospCode = keys.get(1);
                String id = keys.get(2);
                String newKey = StringUtils.createKey(formName, hospCode, id);


                if (Constants.REDISKEY.BED.equals(formName)) {
                    Map<String, Object> returnMap = redisDAO.getBaseBed(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.ADVICE.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseAdvice(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.ADVICEDETAIL.equals(formName)){
                    List<Map<String, Object>> baseAdviceDetail = redisDAO.getBaseAdviceDetail(map);
                    returnList.addAll(baseAdviceDetail);

                    List<Object> cacheList = new ArrayList<>();
                    for (Map m :baseAdviceDetail){
                        cacheList.add(m.get(id));
                    }
                    cacheDetailOperate(newKey,cacheList);
                } else if(Constants.REDISKEY.DEPT.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseDept(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.RATE.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseRate(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.DISEASE.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseDisease(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.ITEM.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseItem(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.DRUG.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseDrug(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.MATERIAL.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseMaterial(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.SPECIALCALC.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseSpecial(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.DAILYFIRSTCALC.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getBaseFirst(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.PARAMETER.equals(formName)){
                    Map<String, Object> returnMap = redisDAO.getSystem(map);
                    returnList.add(returnMap);

                    cacheOperate(newKey,returnMap.get(id));
                } else if(Constants.REDISKEY.CODEDETAIL.equals(formName)){
                    List<Map<String, Object>> codeDetail = redisDAO.getCodeDetail(map);
                    returnList.addAll(codeDetail);

                    List<Object> cacheList = new ArrayList<>();
                    for (Map m :codeDetail){
                        cacheList.add(m.get(id));
                    }
                    cacheDetailOperate(newKey,cacheList);
                }
            } else {
                throw new AppException("key值格式错误");
            }
        } else {
            throw new AppException("请传入Key值");
        }
        return returnList;
    }

    public void cacheOperate(String key, Object o){
        if(redisUtils.hasKey(key)){
            redisUtils.del(key);
        }
        redisUtils.set(key,o);
    }
    public void cacheDetailOperate(String key, List<Object> objects){
        if(redisUtils.hasKey(key)){
            redisUtils.del(key);
        }
        redisUtils.set(key,objects);
    }
}
