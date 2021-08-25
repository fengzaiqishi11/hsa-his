package cn.hsa.insure.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureFunctionDAO;
import cn.hsa.module.insure.module.entity.InsureFunctionDO;
import cn.hsa.util.Constants;
import cn.hsa.util.RedisUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: Transpond
 * @Describe(描述): 医保机构 转发 调用指定医保服务工具类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 15:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class Transpond {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private InsureFunctionDAO insureFunctionDAO;

    @Resource
    private SpringContext springContext;


    /**
     * @param hospCode      医院编码
     * @param insureRegCode 医保编码
     * @param functionCode    功能号
     * @param param         请求参数
     * @Menthod to
     * @Desrciption 调用
     * @Author Ou·Mr
     * @Date 2020/11/9 16:23
     * @Return java.lang.Object
     */
    public <R extends Object> R to(String hospCode, String insureRegCode, String functionCode, Object param) {
        if (StringUtils.isEmpty(hospCode) || StringUtils.isEmpty(insureRegCode) || StringUtils.isEmpty(functionCode)) {
            throw new AppException("参数错误。");
        }
        InsureFunctionDO insureFunctionDO = queryInsureFunction(hospCode,insureRegCode,functionCode);
        //类地址做处理
        String funClass = insureFunctionDO.getFunctionClass();
        String beanName = insureFunctionDO.getInstanceName();
        if (StringUtils.isEmpty(funClass) || StringUtils.isEmpty(beanName)){
            throw new AppException("功能编码【"+functionCode+"】未配置class地址或者实例名。");
        }
        Object obj = springContext.getBean(beanName);
        if (obj == null){
            throw new AppException("未获取到【"+ beanName +"】实例,调用医保失败。");
        }
        int lastIndex = funClass.lastIndexOf(".");
        //String classPath = funClass.substring(0,lastIndex);
        String methedName = funClass.substring(lastIndex+1);
        Method method = ReflectionUtils.findMethod(obj.getClass(),methedName,param.getClass());
        if (method == null){
            throw new AppException("在实例中未获取到【"+ methedName +"】方法,调用医保失败。");
        }
        R result = (R)ReflectionUtils.invokeMethod(method,obj,param);
        return result;
    }
    
    /**
     * @Menthod queryInsureFunction
     * @Desrciption  查询功能医保功能号
     * @param hospCode 医院编码
     * @param insureRegCode 医保编码
     * @param functionCode 功能编码
     * @Author Ou·Mr
     * @Date 2020/11/10 10:01 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public InsureFunctionDO queryInsureFunction(String hospCode, String insureRegCode, String functionCode){
        String str = new StringBuffer(hospCode).append(insureRegCode).append("-FUNCTION").toString();
        redisUtils.del(str);
        Map<String, InsureFunctionDO> functionMap = redisUtils.get(str);
        InsureFunctionDO insureFunctionDO = null;
        if (functionMap == null || functionMap.isEmpty()){
            InsureFunctionDO param = new InsureFunctionDO();
            param.setHospCode(hospCode);
            param.setRegCode(insureRegCode);
            param.setIsValid(Constants.SF.S);
            List<InsureFunctionDO> insureFunctionDOList = insureFunctionDAO.queryFunctionAll(param);
            if (insureFunctionDOList == null || insureFunctionDOList.isEmpty()){
                throw new AppException("未找到该医保配置信息。");
            }
            Map<String,InsureFunctionDO> insureFunctionDOMap = new HashMap<String,InsureFunctionDO>();
            for (InsureFunctionDO item : insureFunctionDOList){
                String funCode = item.getFunctionCode();
                insureFunctionDOMap.put(funCode,item);
                if (functionCode.equals(funCode)){
                    insureFunctionDO = item;
                }
            }
            redisUtils.set(str,insureFunctionDOMap);
        }else{
            for (String key : functionMap.keySet()) {
                if (functionCode.equals(key)){
                    insureFunctionDO = functionMap.get(key);
                    break;
                }
            }
        }
        if (insureFunctionDO == null){
            redisUtils.del(str);
            throw new AppException("未找到功能编码对应配置信息。");
        }
        return insureFunctionDO;
    }
}