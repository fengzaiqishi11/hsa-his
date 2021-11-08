package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.bo
 * @class_name: InsureUnifiedLogBO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 13:40
 * @Company: 创智和宇
 **/
public interface InsureUnifiedLogBO {
    /**
     * @Method queryPage
     * @Desrciption  分页查询所有调用医保接口的日志信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/10/11 13:44
     * @Return
     **/
    PageDTO queryPage(InsureFunctionLogDO insureFunctionLogDO);

    /**
     * @Method insertInsureFunctionLog
     * @Desrciption  保存调用医保接口的日志信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/10/11 13:44
     * @Return
     **/
    boolean insertInsureFunctionLog(Map<String,Object> map);
}
