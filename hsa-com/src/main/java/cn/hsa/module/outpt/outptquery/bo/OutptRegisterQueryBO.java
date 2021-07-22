package cn.hsa.module.outpt.outptquery.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.outptquery.dto.OutptRegisterDTO;

/**
 * @Package_name: cn.hsa.module.outpt.outptquery.bo
 * @class_name: OutptRegisterQueryBO
 * @Description: 门诊挂号业务逻辑层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/10 14:46
 * @Company: 创智和宇
 **/
public interface OutptRegisterQueryBO {

    /**
     * @Method: queryPage()
     * @Description: 门诊挂号分页查询
     * @Param: outptRegisterDTO门诊挂号数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/10 14:38
     * @Return: outptRegisterDTO门诊挂号数据传输对象集合
     */
    PageDTO queryPage(OutptRegisterDTO outptRegisterDTO);
}
