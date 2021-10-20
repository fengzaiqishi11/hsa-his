package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;

import java.util.List;

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

    List<InsureFunctionLogDO> queryPage(InsureFunctionLogDO insureFunctionLogDO);

    boolean insertInsureFunctionLog(InsureFunctionLogDO insureFunctionLogDO);
}
