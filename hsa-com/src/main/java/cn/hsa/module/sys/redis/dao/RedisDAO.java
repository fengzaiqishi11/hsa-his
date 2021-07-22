package cn.hsa.module.sys.redis.dao;

import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.sys.redis.dao
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/15  10:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface RedisDAO {

    /**
     * @Menthod getBaseDrug
     * @Desrciption 获取药品信息
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    Map<String, Object> getBaseDrug(Map map);

    /**
     * @Menthod getBaseMaterial
     * @Desrciption 获取材料信息
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    Map<String, Object> getBaseMaterial(Map map);

    /**
     * @Menthod getBaseItem
     * @Desrciption 获取项目信息
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    Map<String, Object> getBaseItem(Map map);

    /**
     * @Menthod getBaseAdvice
     * @Desrciption 获取医嘱信息
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    Map<String, Object> getBaseAdvice(Map map);

    /**
     * @Menthod getBaseAdviceDetail
     * @Desrciption 获取医嘱明细数据
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    List<Map<String, Object>> getBaseAdviceDetail(Map map);

    /**
     * @Menthod getBaseDept
     * @Desrciption 获取科室信息
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    Map<String, Object> getBaseDept(Map map);

    /**
     * @Method getBaseDisease
     * @Desrciption 获取疾病信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2021/1/20 14:46
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getBaseDisease(Map map);

    /**
     * @Method getBaseFirst
     * @Desrciption 获取每日首次计费信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2021/1/20 14:46
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getBaseFirst(Map map);

    /**
     * @Method getBaseSpecial
     * @Desrciption 获取每日特殊计费信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2021/1/20 14:46
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getBaseSpecial(Map map);

    /**
     * @Method getBaseRate
     * @Desrciption 获取频率信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2021/1/20 14:46
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getBaseRate(Map map);

    /**
     * @Method getBaseBed
     * @Desrciption 获取床位信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2021/1/20 14:47
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getBaseBed(Map map);

    /**
     * @Menthod getSystem
     * @Desrciption 获取系统参数
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    Map<String, Object> getSystem(Map map);

    /**
     * @Menthod getCodeDetail
     * @Desrciption 获取码表数据
     * @Param
     *  map  参数数据对象
     * @Author zengfeng
     * @Date   2021/1/10 17:02
     **/
    List<Map<String, Object>> getCodeDetail(Map map);

}
