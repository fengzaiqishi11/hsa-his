package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureUnifiedLogDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 13:49
 * @Company: 创智和宇
 **/
public interface InsureUnifiedLogDAO {

    /**
     * @Method queryPage
     * @Desrciption  分页查询医保日志记录
     * @Param insureFunctionLogDO
     *
     * @Author fuhui
     * @Date   2021/11/1 9:28
     * @Return
    **/
    List<InsureFunctionLogDO> queryPage(InsureFunctionLogDO insureFunctionLogDO);

    /**
     * @Method insertInsureFunctionLog
     * @Desrciption  新增医保日志记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/1 9:29
     * @Return
    **/
    boolean insertInsureFunctionLog(InsureFunctionLogDO insureFunctionLogDO);

    /**
     * @Method selectInsureUnifiedLog
     * @Desrciption  查询医保日志记录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/1 9:29
     * @Return
    **/
    InsureFunctionLogDO selectInsureUnifiedLog(Map<String, Object> map);
    
    /**
     * @Method 
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2022/1/4 10:29
     * @Return 
    **/
    InsureFunctionLogDO selectFunctionLogById(InsureFunctionLogDO insureFunctionLogDO);

    /**
     * @Method
     * @Desrciption
     * @Param
     * @Author wq
     * @Date 2022/09/14 10:29
     * @Return
     **/
	InsureFunctionLogDO selctSignInLog(InsureFunctionLogDO insureFunctionLogDO);
}
